package base.props4j.impl;

import static base.props4j.impl.ElOps.*;
import base.props4j.ValsHolder;
import base.props4j.api.model.Pair;

import java.util.concurrent.ConcurrentHashMap;

public class ElHolder {
	
	private ValsHolder valsHolder;
	private ConcurrentHashMap<String, CacheData> cache;

	public ElHolder(ValsHolder props) {
		this.valsHolder = props;
		this.cache = new ConcurrentHashMap<>();
	}
	
	public String getValAfterEl(String key){
		return getValAfterEl(key, null);
	}
	
	public String getValAfterEl(String key, String defVal){
		
		CacheData fromCache = cache.get(key);
		if(fromCache != null){
			if( ! fromCache.hasEl)
				return valsHolder.strVal(key, defVal);
			return fromCache.val;
		}
		
		String initVal = valsHolder.strVal(key, defVal);
		Pair<String, Boolean> result = processElIfNeed(initVal, valsHolder);
		boolean updatedByEl = result.second;
		String out = result.first;
		
		CacheData toCache = updatedByEl? new CacheData(out) : new CacheData();
		cache.put(key, toCache);
		
		return out;
	}
	
	public void clearCache(){
		cache.clear();
	}
	
	
	
	
	private static class CacheData {
		
		boolean hasEl;
		String val;
		 
		CacheData() {
			this.hasEl = false;
		}
		
		CacheData(String val) {
			this.val = val;
			this.hasEl = true;
		}
		
	}

}
