package examples;

import static base.props4j.util.Util.*;
import base.props4j.impl.MapProps;

import java.io.IOException;


/**
 * Example 6: Expressions ${...} in values
 *
 * @author Evgeny Dolganov
 */
public class Ex6_Expressions_in_vals {
	
	public static void main(String[] args) throws IOException {
		

		MapProps props = new MapProps();
		
		props.putVal("url", "127.0.0.1");
		
		//you can use simple Expressions (EL) in vals
		props.putVal("link1", "${url}/some1");
		props.putVal("link2", "${url}/some2");
		
		String link1 = props.strVal("link1"); // 127.0.0.1/some1
		String link2 = props.strVal("link2"); // 127.0.0.1/some2
		
		//print [127.0.0.1/some1, 127.0.0.1/some2]
		System.out.println(
			list(
				link1, 
				link2));
		
		
		
		//EL is useful for a single changes in vals
		props.putVal("url", "google.com");
		
		link1 = props.strVal("link1"); // google.com/some1
		link2 = props.strVal("link2"); // google.com/some2
		
		
		//print [google.com/some1, google.com/some2]
		System.out.println(
			list(
				link1, 
				link2));
		

		
	}

}
