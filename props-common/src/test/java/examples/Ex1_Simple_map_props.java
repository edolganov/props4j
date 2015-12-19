package examples;

import static base.props4j.util.Util.*;
import base.props4j.impl.MapProps;


/**
 * Example 1: Simple MapProps and basic get-put methods
 *
 * @author Evgeny Dolganov
 */
public class Ex1_Simple_map_props {
	
	public static void main(String[] args) {
		
		
		//simple concurrent-ready map props
		MapProps props = new MapProps();
		
		
		
		//put any simple type
		props.putVal("key1", 1);
		props.putVal("key2", "val2");
		props.putVal("key3", true);
		props.putVal("key4", new Object().toString());
		
		
		
		//put null too
		String nullVal = null;
		props.putVal("key5", nullVal);
		
		
		
		//get any simple type
		String strVal_1 = props.strVal("key1"); //"1"
		long longVal_1 = props.longVal("key1"); //1L
		Boolean boolVal_1 = props.boolVal("key1"); //false
		int defVal=  props.intVal("unknown-key", 42); //no key in props
		
		
		//print [1, 1, false, 42]
		System.out.println(
			list(
				strVal_1, 
				longVal_1, 
				boolVal_1,
				defVal));

		
	}

}
