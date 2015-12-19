package base.props4j.junit;


import static base.props4j.util.ExceptionUtil.*;
import base.props4j.util.ExceptionUtil;
import base.props4j.util.StreamUtil;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Assert;

public class AssertExt extends Assert {
	
	public static void fail_TODO(){
		fail("TODO");
	}
	
	public static void fail_exception_expected(){
		fail_exception_expected(null);
	}
	
	public static void fail_exception_expected(String info){
		fail("Exception expected"+(info != null? ": "+info : ""));
	}
	
	public static void assertExceptionWithText(Throwable t, String... texts){
		assertTrue(t.toString(), containsAnyTextInMessage(t, texts));
	}
	
	public static void assertFileExists(String path){
		assertTrue("assertFileExists:"+path, new File(path).exists());
	}
	
	public static void assertFileNotExists(String path){
		assertTrue("assertFileNotExists:"+path, ! new File(path).exists());
	}
	
	public static void assertEquals(String expected, String actual){
		assertEquals((Object)expected, (Object)actual);
	}
	
	@SuppressWarnings("deprecation")
	public static void assertEquals(Object[] a, Object[] b){
		Assert.assertEquals(a, b);
	}
	
	public static void assertEquals(String expected, InputStream in){
		try {
			String actual = in == null? null : StreamUtil.streamToStr(in);
			assertEquals(expected, actual);
		}catch (Exception e) {
			throw ExceptionUtil.getRuntimeExceptionOrThrowError(e);
		}
	}
	
	public static void assertEquals(byte[] expected, byte[] actual){
		assertTrue("arrays are not equals", Arrays.equals(expected, actual));
	}
	

}
