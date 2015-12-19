package examples;

import static base.props4j.util.FileUtil.*;
import static examples.ExOps.*;
import static java.lang.Thread.*;
import base.props4j.impl.FileProps;

import java.io.File;
import java.io.IOException;


/**
 * Example 3: FileProps and file updated check
 *
 * @author Evgeny Dolganov
 */
public class Ex3_File_props_update {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		

		File file = emptyFile("./test-out/test.properties");
		writeFileUTF8(file, "key1 = 1");
		
		
		//FileProps check file for updates
		//default check time: FileProps.DEF_UPDATE_TIME_MS
		//but you can change it
		long checkTime = 1000; //1sec
		FileProps props = new FileProps(file, checkTime);
		
		int val = props.intVal("key1"); //1
		System.out.println(val);
		
		//lets update file
		writeFileUTF8(file, "key1 = 2");
		
		//and wait
		System.out.println("sleep...");
		sleep(checkTime+100);
		
		//props got new values from file
		int newVal = props.intVal("key1"); //2
		System.out.println(newVal);
		

		
	}

}
