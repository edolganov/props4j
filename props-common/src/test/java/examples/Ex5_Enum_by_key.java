package examples;

import static base.props4j.util.Util.*;
import static examples.Keys.*;
import base.props4j.impl.MapProps;

import java.io.IOException;


/**
 * Example 5: Java Enums are best keys for props!
 *
 * @author Evgeny Dolganov
 */
public class Ex5_Enum_by_key {
	
	public static void main(String[] args) throws IOException {

		//empty props
		MapProps props = new MapProps();
		
		
		//Enum keys a very useful for default vals
		int defVal = Keys.planets_in_solar_system.intFrom(props); //8
		
		
		
		props.putVal(Keys.planets_in_solar_system, 9);
		int updatedVal = Keys.planets_in_solar_system.intFrom(props); //9
		
		//print [8, 9]
		System.out.println(
			list(
				defVal, 
				updatedVal));
		
		
		//with static import enums are more clean in code
		int intVal = planets_in_solar_system.intFrom(props);
		boolean boolVal = is_frog_green.boolFrom(props);
		
		//print [9, true]
		System.out.println(
			list(
				intVal, 
				boolVal));
		
		
		
		//you can use enum keys with no props at all!
		MapProps badProps = null;
		double doubleVal = planets_in_solar_system.doubleFrom(badProps); //8
		String strVal = myName.strFrom(badProps);
		
		//print [8.0, Evgeny]
		System.out.println(
			list(
				doubleVal, 
				strVal));
		

		
	}

}
