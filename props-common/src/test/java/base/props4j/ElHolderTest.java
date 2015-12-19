package base.props4j;

import base.props4j.impl.ElHolder;
import base.props4j.junit.AssertExt;

import java.util.HashMap;

import org.junit.Test;

public class ElHolderTest extends AssertExt {
	
	@Test
	public void test_cache(){
		
		HashMap<String, String> map = new HashMap<>();
		map.put("a", "a"); //no el
		map.put("b", "${c}"); //has el
		map.put("c", "${d}"); //no val for el - like "no el"
		
		ElHolder holder = new ElHolder((key, def)->{
			String out = map.get(key);
			return out == null? def : out;
		});
		
		//fill cache
		assertEquals("a", holder.getValAfterEl("a"));
		assertEquals("a", holder.getValAfterEl("a", "b"));
		assertEquals("${d}", holder.getValAfterEl("b"));
		assertEquals("${d}", holder.getValAfterEl("c"));
		assertEquals("some", holder.getValAfterEl("d", "some"));
		
		//from cache
		map.put("a", "${d}"); 
		map.put("d", "d");
		assertEquals("${d}", holder.getValAfterEl("a"));
		assertEquals("${d}", holder.getValAfterEl("b")); //from cache
		assertEquals("${d}", holder.getValAfterEl("c")); //from cache
		assertEquals("d", holder.getValAfterEl("d"));
		
		//update cache
		map.put("d", "d2");
		holder.clearCache();
		assertEquals("d2", holder.getValAfterEl("a"));
		assertEquals("d2", holder.getValAfterEl("b"));
		assertEquals("d2", holder.getValAfterEl("c"));
		assertEquals("d2", holder.getValAfterEl("d"));
		
		//from cache
		map.put("d", "d3");
		assertEquals("d2", holder.getValAfterEl("a")); //from cache
		assertEquals("d2", holder.getValAfterEl("b")); //from cache
		assertEquals("d2", holder.getValAfterEl("c")); //from cache
		assertEquals("d3", holder.getValAfterEl("d"));
		
		holder.clearCache();
		assertEquals("d3", holder.getValAfterEl("a"));
		assertEquals("d3", holder.getValAfterEl("b"));
		assertEquals("d3", holder.getValAfterEl("c"));
		assertEquals("d3", holder.getValAfterEl("d"));
	}

}
