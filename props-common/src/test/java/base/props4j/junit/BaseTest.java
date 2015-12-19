package base.props4j.junit;



import base.props4j.util.FileUtil;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.TestName;


@Ignore
public abstract class BaseTest extends AssertExt {
	
	public static final String ROOT_PATH = "./test-out";
	public static final File ROOT_DIR = new File(ROOT_PATH);
	
	static {
		FileUtil.deleteDirRecursive(ROOT_DIR);
		ROOT_DIR.mkdirs();
	}
	
	public String TEST_PATH;
	public File TEST_DIR;
	protected boolean createDir = true;
	
	@Rule
	public TestName name = new TestName();
	
	@Before
	public void createDir(){
		TEST_PATH = ROOT_PATH + "/"+getClass().getSimpleName()+"@"+name.getMethodName()+"__"+System.currentTimeMillis();
		if(createDir){
			TEST_DIR = new File(TEST_PATH);
			TEST_DIR.mkdir();
		}
	}
	
	public String testPath(String path){
		return TEST_PATH+path;
	}
	
	public void assertTestFileExists(String path){
		if( ! path.startsWith("/")) path = "/" + path;
		assertFileExists(testPath(path));
	}
	
	public void assertTestFileNotExists(String path){
		assertFileNotExists(testPath(path));
	}
	
	
	public static String path(File parent, File... pathElems){
		StringBuilder sb = new StringBuilder();
		sb.append(parent.getPath());
		for(File child : pathElems) {
			sb.append("/").append(child.getName());
		}
		return sb.toString();
	}

}
