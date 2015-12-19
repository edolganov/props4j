package base.props4j.impl;

import static base.props4j.util.Util.*;
import base.props4j.PropsChangedListener;
import base.props4j.WriteProps;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public abstract class BaseProps implements WriteProps {
	
	protected Logger log = getLog(getClass());
	
	private CopyOnWriteArrayList<PropsChangedListener> listeners = new CopyOnWriteArrayList<>();
	private ElHolder elHolder = new ElHolder(this::getValImpl);
	
	@Override
	public void addChangedListener(PropsChangedListener l) {
		listeners.add(l);
	}
	
	protected void fireChangedEvent(Set<String> keys){
		for (PropsChangedListener l : listeners) {
			l.onChanged(keys);
		}
		elHolder.clearCache();
	}
	
	@Override
	public final String strVal(String key, String defaultVal){
		return elHolder.getValAfterEl(key, defaultVal);
	};
	
	@Override
	public final void putVal(String key, String val) {
		putValImpl(key, val);
	}
	
	protected abstract String getValImpl(String key, String defaultVal);
	
	protected abstract void putValImpl(String key, String val);
	

}
