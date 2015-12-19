package base.props4j.impl;


import static base.props4j.util.FileUtil.*;
import static base.props4j.util.MapUtil.*;
import static base.props4j.util.Util.*;
import static java.util.Collections.*;
import static java.util.concurrent.TimeUnit.*;
import base.props4j.util.ExecutorsUtil;
import base.props4j.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;


public class FileProps extends BaseProps {
	
	public static final int DEF_UPDATE_TIME_MS = 30_000;

	private static Logger log = Util.getLog(FileProps.class);
	
	private static ScheduledExecutorService syncService;
	private static synchronized ScheduledExecutorService getExecutorService(){
		if(syncService == null){
			syncService = ExecutorsUtil.newScheduledThreadPool("FileProps-sync", 1);
		}
		return syncService;
	}

	private final File file;
	private volatile long curModified;
	private volatile Properties state = new Properties();
	
	public FileProps(String propsPath){
		this(new File(propsPath));
	}
	
	public FileProps(File propsFile){
		this(
			propsFile, 
			DEF_UPDATE_TIME_MS);
	}
	
	public FileProps(
			File propsFile, 
			long updateTimeMs){
		
		this.file = propsFile;
		
		updateFromFileIfNeed();
		
		updateTimeMs = updateTimeMs > 0 && updateTimeMs < 50? 50 : updateTimeMs;
		if(updateTimeMs > 0){
			getExecutorService().scheduleWithFixedDelay(
				() -> updateFromFileIfNeed(), 
				updateTimeMs, 
				updateTimeMs, 
				MILLISECONDS);
		}

		
	}


	public File getFile(){
		return file;
	}

	@Override
	protected String getValImpl(String key, String defaultVal) {
		Properties props = state;
		return props.containsKey(key)? 
				(String)props.get(key) 
				: defaultVal;
	}
	

	@Override
	protected void putValImpl(String key, String val) {
		putObjVal(key, val);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, String> toMap() {
		HashMap<String, String> out = new HashMap<>((Map)state);
		return out;
	}


	
	private synchronized void putObjVal(String key, String val){
		
		Properties props = state;
		
		Properties newProps = new Properties();
		newProps.putAll(props);
		if(val == null) newProps.remove(key);
		else newProps.put(key, val);
		saveToFile(newProps);
		
		fireChangedEvent(singleton(key));
	}
	
	
	@SuppressWarnings("unchecked")
	public synchronized void updateFromFileIfNeed() {
		
		long lastModified = file.lastModified();
		if(curModified == lastModified) 
			return;
		
		log.info("load props from "+file.getAbsolutePath());
		
		Properties newState = null;
		try {
			
			FileInputStream is = new FileInputStream(file);
			Properties fromFile = new Properties();
			fromFile.load(new InputStreamReader(is, "UTF-8"));
			is.close();
			
			newState = fromFile;
			
		}
		//empty props
		catch(FileNotFoundException e){
			newState = new Properties();
		}
		//invalid read
		catch (Exception e) {
			newState = null;
			log.severe("can't read props:"+ e);
		}
		
		if(newState == null) 
			return;
		
		Properties oldState = state;
		
		this.curModified = lastModified;
		this.state = newState;
		
		Set<String> keys = getUpdatedKeys(oldState, newState);
		if( ! isEmpty(keys)) {
			fireChangedEvent(keys);
		}
	}


	private void saveToFile(Properties newState) {
		
		log.info("save props to "+file.getAbsolutePath());
		
		StringBuilder sb = new StringBuilder();
		sb.append("#Props from app");
		for (Entry<Object, Object> entry : newState.entrySet()) {
			sb.append("\n"+entry.getKey()+"="+entry.getValue());
		}
		
		
		try {
			replaceFileUTF8(file, sb.toString());
		}catch (Exception e) {
			log.severe("can't saveToFile: "+ e);
			return;
		}
		
		this.curModified = file.lastModified();
		this.state = newState;
	}
	

	@Override
	public String toString() {
		return "FileProps [file=" + file + "]";
	}
	
	
	
	public static FileProps createPropsWithoutUpdate(File propsFile){
		return new FileProps(propsFile, 0);
	}
	
	public static List<FileProps> createFileProps(Collection<String> paths){
		
		LinkedList<FileProps> list = new LinkedList<>();
		
		for (String path : paths) {
			File file = new File(path);
			if( ! file.exists()){
				log.severe("can't find file by path: "+path);
				continue;
			}
			list.addFirst(new FileProps(file));
		}
		return list;
	}

	
	
}
