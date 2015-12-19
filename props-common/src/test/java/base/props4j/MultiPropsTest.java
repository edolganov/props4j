package base.props4j;

import static base.props4j.util.Util.*;
import base.props4j.api.exception.PropertyNotFoundException;
import base.props4j.impl.FileProps;
import base.props4j.impl.MapProps;
import base.props4j.impl.MultiProps;
import base.props4j.junit.BaseTest;

import java.io.File;
import java.util.Set;

import org.junit.Test;

@SuppressWarnings("rawtypes")
public class MultiPropsTest extends BaseTest {
	
	
	@Test
	public void test_el() throws Exception {
		
		FileProps props1 = new FileProps(new File(TEST_DIR, "test.props"));
		MapProps props2 = new MapProps();
		MultiProps multi = new MultiProps(props1, props2);
		
		props1.putVal("a", "${b}");
		props2.putVal("b", "${c}");		
		assertEquals("${c}", multi.strVal("a"));
		
		props1.putVal("c", "valFromProp1");
		assertEquals("valFromProp1", multi.strVal("a"));
		
		props2.putVal("c", "valFromProp2");
		assertEquals("valFromProp1", multi.strVal("a"));
		
		props1.putVal("b", "${c}");
		assertEquals("valFromProp1", multi.strVal("a"));
		
		props1.removeVal("b");
		assertEquals("valFromProp1", multi.strVal("a"));
		
		props1.removeVal("c");
		assertEquals("valFromProp2", multi.strVal("a"));
		
	}
	
	
	
	@Test
	public void test_update(){
		
		
		FileProps props1 = new FileProps(new File(TEST_DIR, "test1.props"));
		FileProps props2 = new FileProps(new File(TEST_DIR, "test2.props"));
		
		props1.putVal("key1", "1");
		props2.putVal("key2", "2");
		
		MultiProps multi = new MultiProps(props1);
		multi.addSource(props2);
		
		Set[] keys = {null};
		String[] curVal1 = {null};
		String[] curVal2 = {null};
		String[] curVal3 = {null};
		multi.addChangedListener((k)->{
			keys[0] = k;
			curVal1[0] = multi.strVal("key1");
			curVal2[0] = multi.strVal("key2");
			curVal3[0] = multi.strVal("key3");
		});
		
		//change in 1
		props1.putVal("key1", "11");
		assertEquals(set("key1"), keys[0]);
		assertEquals("11", curVal1[0]);
		assertEquals("2", curVal2[0]);
		
		
		//change in 2
		props2.putVal("key2", "22");
		assertEquals(set("key2"), keys[0]);
		assertEquals("11", curVal1[0]);
		assertEquals("22", curVal2[0]);
		
		
		//chagne in 2 but not set
		props2.putVal("key1", "1");
		assertEquals(set("key1"), keys[0]);
		assertEquals("11", curVal1[0]);
		assertEquals("22", curVal2[0]);
		
		props2.removeVal("key2");
		assertEquals(set("key2"), keys[0]);
		assertEquals("11", curVal1[0]);
		assertEquals(null, curVal2[0]);
		
		
		//reset
		MapProps props3 = new MapProps(map("key3", "3"));
		multi.resetSources(props3);
		assertEquals(set("key1", "key3"), keys[0]);
		assertEquals(null, curVal1[0]);
		assertEquals(null, curVal2[0]);
		assertEquals("3", curVal3[0]);
		
		//update after reset
		props3.putVal("key3", "33");
		assertEquals(set("key3"), keys[0]);
		assertEquals(null, curVal1[0]);
		assertEquals(null, curVal2[0]);
		assertEquals("33", curVal3[0]);
	}
	
	
	
	@Test
	public void test_simpe(){
		
		FileProps props1 = new FileProps(new File(TEST_DIR, "test1.props"));
		FileProps props2 = new FileProps(new File(TEST_DIR, "test2.props"));
		
		props1.putVal("key1", "1");
		props1.putVal("key2", "2");
		
		props2.putVal("key3", "3");
		props2.putVal("key4", "4");
		
		MultiProps multi = new MultiProps(props1, props2);
		
		//read
		{
			assertEquals("1", multi.findStr("key1"));
			assertEquals("2", multi.findStr("key2"));
			assertEquals("3", multi.findStr("key3"));
			assertEquals("4", multi.findStr("key4"));
			
			assertEquals(new Integer(1), multi.intVal("key1", -1));
			assertEquals(new Integer(2), multi.intVal("key2", -1));
			assertEquals(new Integer(3), multi.intVal("key3", -1));
			assertEquals(new Integer(4), multi.intVal("key4", -1));
			
			assertEquals(new Long(1), multi.longVal("key1", -1L));
			assertEquals(new Long(2), multi.longVal("key2", -1L));
			assertEquals(new Long(3), multi.longVal("key3", -1L));
			assertEquals(new Long(4), multi.longVal("key4", -1L));
		}
		
		//unknown
		{
			assertEquals(null, multi.strVal("key-unknown", (String)null));
			assertEquals("@", multi.strVal("key-unknown", "@"));
			
			try {
				multi.findStr("key-unknown");
				fail_exception_expected();
			}catch(PropertyNotFoundException e){
				//ok
			}
		}
		
		//source update
		{
			props1.putVal("key1", "11");
			assertEquals("11", multi.findStr("key1"));
		}
		
		//rewrite by order
		{
			props1.putVal("key3", 1);
			assertEquals("1", multi.findStr("key3"));
			assertEquals("1", multi.toMap().get("key3"));
			
			props1.removeVal("key3");
			assertEquals("3", multi.findStr("key3"));
		}
		
	}

}
