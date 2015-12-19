package base.props4j;

import static base.props4j.util.FileUtil.*;
import static base.props4j.util.Util.*;
import static java.lang.Thread.*;
import base.props4j.impl.FileProps;
import base.props4j.junit.BaseTest;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;


@SuppressWarnings("rawtypes")
public class FilePropsTest extends BaseTest {
	
	@Test
	public void test_invalidChars() throws Exception {
		
		File propsFile = new File(TEST_DIR, "some.txt");
		writeFileUTF8(propsFile, "a=\\n\\t\0\\r");
		
		FileProps props = new FileProps(propsFile, 0);
		String val = props.strVal("a");
		assertEquals("\n\t\0\r", val);
		
	}
	
	
	@Test
	public void test_el() throws Exception {
		
		
		File propsFile = new File(TEST_DIR, "some.txt");
		writeFileUTF8(propsFile, "a=${b}\nb=${c}");
		
		long updateTime = 50L;
		FileProps props = new FileProps(propsFile, updateTime);
		assertEquals("${c}", props.strVal("a"));
		
		//update by api
		props.putVal("c", "cVal1");
		assertEquals("cVal1", props.strVal("a"));
		
		//update by file
		sleep(10);
		writeFileUTF8(propsFile, "a=${b}\nb=${c}\nc=cVal2");
		sleep(updateTime*2);
		
		assertEquals("cVal2", props.strVal("a"));
		
		
	}
	
	
	@Test
	public void test_utf8() throws Exception {
		File propsFile = new File(TEST_DIR, "some.txt");
		writeFileUTF8(propsFile, "\nsome=привет");
		
		FileProps props = new FileProps(propsFile);
		assertEquals("привет", props.strVal("some"));
	}

	@Test
	public void test_noPropsFile() throws Exception {
		
		File propsFile = new File(TEST_DIR, "some.txt");
		propsFile.delete();
		assertFalse(propsFile.exists());
		
		FileProps props = new FileProps(propsFile);
		
		//no file
		assertEquals(null, props.strVal("some"));
		
		//create file
		writeFileUTF8(propsFile, "\nsome=123");
		props.updateFromFileIfNeed();
		assertEquals("123", props.strVal("some"));
		
		//remove file
		propsFile.delete();
		props.updateFromFileIfNeed();
		assertEquals(null, props.strVal("some"));
		
	}
	

	@Test
	public void test_changedEvent() throws Exception{
		
		String key1 = "key1";
		String val1 = "val1";
		String val2 = "val2";
		String val3 = "val3";
		
		File file = new File(TEST_DIR, "test.props");
		writeFileUTF8(file, "#коммент\n\n"+key1+"="+val1+"\n");
		
		int updateTime = 50;
		FileProps props = new FileProps(file, updateTime);
		assertEquals(val1, props.strVal(key1));
		
		Set[] keys = {null};
		String[] curVal = {null};
		int[] countCall = {0};
		props.addChangedListener((k)->{
			keys[0] = k;
			curVal[0] = props.strVal(key1);
			countCall[0]++;
		});
		
		//update by put
		{
			props.putVal(key1, val2);
			
			assertEquals(set("key1"), keys[0]);
			assertEquals(val2, curVal[0]);
		}
		
		//remove
		{
			props.removeVal("key1");
			
			assertEquals(set("key1"), keys[0]);
			assertEquals(null, curVal[0]);
		}
		
		//update by file
		{
			//some times lastModified2 == lastModified1 because write is too fast =)
			//so wait some time to prevent it
			Thread.sleep(100);
			writeFileUTF8(file, key1+"="+val3+"\nkey2=val2");

			Thread.sleep(100);
			
			assertEquals(set("key1", "key2"), keys[0]);
			assertEquals(val3, curVal[0]);
		}
		
	}
	
	
	@Test
	public void test_read_update_save() throws Exception{
		
		
		String key1 = "key1";
		String key2 = "key2";
		String key3 = "key3";
		
		String val1 = "val1";
		String val2 = "val2";
		String val3 = "val3";
		
		File file = new File(TEST_DIR, "test.props");
		writeFileUTF8(file, "#коммент\n\n"+key1+"="+val1+"\n"+key2+"="+val2);
		
		//read
		int updateTime = 50;
		FileProps props = new FileProps(file, updateTime);
		assertEquals(val1, props.strVal(key1, ""));
		assertEquals(val2, props.strVal(key2, ""));
		
		//update
		long lastModified1 = file.lastModified();
		//some times lastModified2 == lastModified1 because write is too fast =)
		//so wait some time to prevent it
		Thread.sleep(100);
		writeFileUTF8(file, key1+"="+val2+"\n"+key3+"="+val3);
		long lastModified2 = file.lastModified();
		assertTrue(lastModified1 != lastModified2);
		
		Thread.sleep(updateTime * 2);
		assertEquals(val2, props.strVal(key1, ""));
		assertEquals("", props.strVal(key2, ""));
		assertEquals(val3, props.strVal(key3, ""));
		
		//save
		props.putVal(key2, val1);
		assertEquals(val2, props.strVal(key1, ""));
		assertEquals(val1, props.strVal(key2, ""));
		assertEquals(val3, props.strVal(key3, ""));
		{
			Properties fromFile = new Properties();
			FileInputStream is = new FileInputStream(file);
			fromFile.load(is);
			is.close();
			assertEquals(val2, fromFile.getProperty(key1));
			assertEquals(val1, fromFile.getProperty(key2));
			assertEquals(val3, fromFile.getProperty(key3));
		}

		
	}

}
