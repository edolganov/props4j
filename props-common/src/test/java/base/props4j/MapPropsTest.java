package base.props4j;

import base.props4j.impl.MapProps;
import base.props4j.junit.BaseTest;

import org.junit.Test;


public class MapPropsTest extends BaseTest {
	
	
	@Test
	public void test_el() throws Exception {
		
		MapProps props = new MapProps();
		
		props.putVal("a", "${b}");
		props.putVal("b", "${c}");
		assertEquals("${c}", props.strVal("a"));
		
		//update by api
		props.putVal("c", "cVal1");
		assertEquals("cVal1", props.strVal("a"));
		
		
	}

}
