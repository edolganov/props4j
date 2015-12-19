package examples;

import static base.props4j.util.FileUtil.*;
import static base.props4j.util.Util.*;
import static examples.ExOps.*;
import base.props4j.impl.FileProps;
import base.props4j.impl.MultiProps;

import java.io.File;
import java.io.IOException;


/**
 * Example 4: MultiProps for many props sources
 *
 * @author Evgeny Dolganov
 */
public class Ex4_Multi_props {
	
	public static void main(String[] args) throws IOException {
		

		//you have many files
		File file1 = emptyFile("./test-out/test1.properties");
		File file2 = emptyFile("./test-out/test2.properties");
		
		writeFileUTF8(file1, "key1 = 1");
		writeFileUTF8(file2, "key2 = 2");
		
		
		//and single props
		MultiProps props = new MultiProps();
		props.addSource(new FileProps(file1));
		props.addSource(new FileProps(file2));
		
		String val1 = props.strVal("key1");
		String val2 = props.strVal("key2");
		
		
		//print [1, 2]
		System.out.println(
			list(
				val1, 
				val2));
		

		
	}

}
