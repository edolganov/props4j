package base.props4j;

import static base.props4j.impl.ElOps.*;
import base.props4j.junit.AssertExt;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ElOpsTest extends AssertExt {
	
	
	@Test
	public void test_processEl(){
		
		Map<String, String> map = new HashMap<>();
		ValsHolder vals = (key, defVal)->{
			String out = map.get(key);
			return out == null? defVal : out;
		};

		//no el
		{
			map.put("a", "aVal");
			assertEquals(null, processEl(null, vals));
			assertEquals("", processEl("", vals));
			assertEquals("a", processEl("a", vals));
			assertEquals("abc", processEl("abc", vals));
			assertEquals("$", processEl("$", vals));
			assertEquals("${", processEl("${", vals));
			assertEquals("${a", processEl("${a", vals));
			assertEquals("${a ", processEl("${a ", vals));
			assertEquals("$ {a}", processEl("$ {a}", vals));
			assertEquals("$ {a}", processEl("$ {a}", vals));
			assertEquals("$ {a}", processEl("$ {a}", vals));
			assertEquals("$${{a}", processEl("$${{a}", vals));
			
			assertEquals(false, processElIfNeed(null, vals).second);
			assertEquals(false, processElIfNeed("abc", vals).second);
		}
		
		//unknown el
		{
			assertEquals("${a }", processEl("${a }", vals));
			assertEquals("${a } ", processEl("${a } ", vals));
			assertEquals(" ${a }", processEl(" ${a }", vals));
			assertEquals(" ${a } ", processEl(" ${a } ", vals));
			assertEquals(" ${ a} ", processEl(" ${ a} ", vals));
			assertEquals(" ${ a } ", processEl(" ${ a } ", vals));
			assertEquals("${b}", processEl("${b}", vals));
			assertEquals(" ${b}", processEl(" ${b}", vals));
			assertEquals(" ${b} ", processEl(" ${b} ", vals));
			
		}
		
		//1 level of el
		{
			map.put("b", "bVal");
			
			assertEquals("aVal", processEl("${a}", vals));
			assertEquals("aVal/123", processEl("${a}/123", vals));
			assertEquals("$aVal", processEl("$${a}", vals));
			assertEquals("bVal", processEl("${b}", vals));
			assertEquals("bValaVal", processEl("${b}${a}", vals));
			assertEquals("bVal aVal ${c}", processEl("${b} ${a} ${c}", vals));
			
			assertEquals(true, processElIfNeed("${a}", vals).second);
		}
		
		//2 level of el
		{
			map.put("a", "${b}");
			map.put("b", "${c}");
			assertEquals("${c}", processEl("${a}", vals));
			assertEquals("${c}", processEl("${b}", vals));
			
			map.put("c", "1");
			assertEquals("1", processEl("${a}", vals));
			assertEquals("1", processEl("${b}", vals));
			
			map.put("a", "${");
			map.put("b", "c}");
			map.put("c", "1");
			assertEquals("${${b}", processEl("${a}${b}", vals));
			assertEquals("1", processEl("${a}c}", vals));
		}
		
		//loop
		{
			map.put("a", "${b}");
			map.put("b", "${a}");
			assertEquals("${a}", processEl("${a}", vals));
			
			map.put("a", "${b}");
			map.put("b", "${c}");
			map.put("c", "${a}");
			assertEquals("${b}", processEl("${a}", vals));
			
			map.put("a", "${b}");
			map.put("b", "${c}");
			map.put("c", "${d}");
			map.put("d", "${a}");
			assertEquals("${c}", processEl("${a}", vals));
			
			assertEquals(true, processElIfNeed("${a}", vals).second);
		}
		
	}
	
	
}
