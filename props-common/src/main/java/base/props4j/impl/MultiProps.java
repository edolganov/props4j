package base.props4j.impl;

import static base.props4j.impl.ElOps.*;
import static base.props4j.util.Util.*;
import base.props4j.Props;
import base.props4j.PropsChangedListener;
import base.props4j.api.exception.PropertyNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class MultiProps implements Props {
	
	private static Logger log = getLog(MultiProps.class);
	
	private ArrayList<Props> list = new ArrayList<Props>();
	private CopyOnWriteArrayList<PropsChangedListener> listeners = new CopyOnWriteArrayList<>();
	private ElHolder elHolder = new ElHolder(this::getValWithoutEl);
	
	public MultiProps() {}
	
	public MultiProps(Collection<? extends Props> sources){
		
		for (Props source : sources) 
			add(source);
		
		log.info("added sources: "+sources);
	}
	
	public MultiProps(Props... sources){
		
		for (Props source : sources) 
			add(source);
		
		log.info("added sources: "+Arrays.toString(sources));
	}
	
	
	public void addSource(Props source){
		addSource(source, list.size());
	}

	public void addSource(Props source, int index){
		
		add(source, index);
		
		log.info("added source: "+source+", index="+index);
	}
	
	private void add(Props source){
		add(source, list.size());
	}
	
	private void add(Props source, int index){
		list.add(index, source);
		source.addChangedListener((keys)->fireChangedEvent(keys));
	}
	
	public void resetSources(Props... sources){
		
		HashSet<String> keys = new HashSet<>();
		
		//remove old
		for (Props old : list) {
			keys.addAll(old.toMap().keySet());
		}
		list.clear();
		
		//add new
		for (Props source : sources) {
			addSource(source);
			keys.addAll(source.toMap().keySet());
		}
		
		fireChangedEvent(keys);
	}
	
	public int getSourceCount(){
		return list.size();
	}
	
	@Override
	public void addChangedListener(PropsChangedListener l) {
		listeners.add(l);
	}
	
	private void fireChangedEvent(Set<String> keys) {
		for (PropsChangedListener l : listeners) {
			l.onChanged(keys);
		}
		elHolder.clearCache();
	}
	
	

	@Override
	public String findStr(String key) throws PropertyNotFoundException {
		for (Props source : list) {
			try {
				return source.findStr(key);
			} catch(PropertyNotFoundException e){}
		}
		throw new PropertyNotFoundException(key);
	}

	@Override
	public String strVal(String key, String defaultVal) {
		return elHolder.getValAfterEl(key, defaultVal);
	}
	
	private String getValWithoutEl(String key, String defaultVal) {
		setSkipEl(true);
		try {
			
			for (Props source : list) {
				String result = source.strVal(key);
				if(result != null) 
					return result;
			}
			return defaultVal;
			
		} finally {
			setSkipEl(false);
		}
	}

	

	@Override
	public Map<String, String> toMap() {
		HashMap<String, String> out = new HashMap<>();
		for (int i = list.size()-1; i > -1; i--) {
			out.putAll(list.get(i).toMap());
		}
		return out;
	}


	@Override
	public String toString() {
		return "MultiProps [list=" + list + "]";
	}
	
	

}
