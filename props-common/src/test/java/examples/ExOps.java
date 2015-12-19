package examples;

import java.io.File;

public class ExOps {
	
	public static File emptyFile(String path){
		
		File file = new File(path);
		
		file.delete();
		file.getParentFile().mkdirs();
		
		return file;
	}

}
