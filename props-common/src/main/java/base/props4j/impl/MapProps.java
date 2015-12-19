package base.props4j.impl;


import static java.util.Collections.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MapProps extends BaseProps {
	
	ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
	
	public MapProps(){}
	
	public MapProps(Map<String, String> initVals) {
		map.putAll(initVals);
	}

	@Override
	protected String getValImpl(String key, String defaultVal) {
		return map.containsKey(key)? 
				map.get(key) 
				: defaultVal;
	}

	@Override
	protected void putValImpl(String key, String val) {
		if(val == null) map.remove(key);
		else map.put(key, val);
		
		fireChangedEvent(singleton(key));
	}
	
	@Override
	public Map<String, String> toMap() {
		HashMap<String, String> out = new HashMap<>(map);
		return out;
	}

}
