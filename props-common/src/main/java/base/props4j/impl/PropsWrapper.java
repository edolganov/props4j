package base.props4j.impl;

import base.props4j.Props;
import base.props4j.PropsChangedListener;

import java.util.Map;

public class PropsWrapper implements Props {
	
	private Props real;
	
	public PropsWrapper(Props real) {
		this.real = real;
	}
	
	@Override
	public void addChangedListener(PropsChangedListener l) {
		real.addChangedListener((keys)-> l.onChanged(keys));
	}


	@Override
	public String strVal(String key, String defaultVal) {
		return real.strVal(key, defaultVal);
	}

	@Override
	public Map<String, String> toMap() {
		return real.toMap();
	}

	@Override
	public String toString() {
		return "PropsWrapper [real=" + real + "]";
	}

}
