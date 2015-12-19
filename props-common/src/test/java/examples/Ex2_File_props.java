package examples;

import static base.props4j.util.FileUtil.*;
import static base.props4j.util.Util.*;
import static examples.ExOps.*;
import base.props4j.impl.FileProps;

import java.io.File;
import java.io.IOException;


/**
 * Example 2: FileProps for file properties
 *
 * @author Evgeny Dolganov
 */
public class Ex2_File_props {
	
	public static void main(String[] args) throws IOException {
		

		File propsFile = emptyFile("./test-out/test.properties");
		
		//create some props file
		writeFileUTF8(propsFile, 
			"key1 = 1" + "\n"
			+ "key2 = true"+ "\n"
			+ "key3 = привет!"+ "\n"
		);
		
		
		//concurrent-ready file props
		FileProps props = new FileProps(propsFile);
		
		
		//get props from file
		int intVal_1 = props.intVal("key1");
		boolean boolVal_2 = props.boolVal("key2");
		String utf8Val_3 = props.strVal("key3");
		
		
		//print [1, true, привет!]
		System.out.println(
			list(
				intVal_1, 
				boolVal_2, 
				utf8Val_3));
		
		
		
		//write props to file
		props.putVal("key3", "hello!");
		String updatedVal = props.strVal("key3");
		
		//print hello!
		System.out.println(updatedVal);

		
	}

}
