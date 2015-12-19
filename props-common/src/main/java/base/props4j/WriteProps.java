package base.props4j;



public interface WriteProps extends Props {

	
	void putVal(String key, String val);
	
	default void putVal(String key, Integer val) {
		putVal(key, val == null? (String) null : val.toString());
	}

	default void putVal(String key, Long val) {
		putVal(key, val == null? (String) null : val.toString());
	}

	default void putVal(String key, Boolean val) {
		putVal(key, val == null? (String) null : val.toString());
	}
	
	default void putVal(String key, Class<?> val) {
		putVal(key, val == null? null : val.getName());
	}
	
	default void removeVal(String key) {
		putVal(key, (String)null);
	}
	
	
	//def val holder
	default void putVal(DefValKey key, String val) {
		putVal(key.name(), val);
	}
	
	default void putVal(DefValKey key, Integer val) {
		putVal(key.name(), val);
	}

	default void putVal(DefValKey key, Long val) {
		putVal(key.name(), val);
	}

	default void putVal(DefValKey key, Boolean val) {
		putVal(key.name(), val);
	}
	
	default void putVal(DefValKey key, Class<?> val) {
		putVal(key.name(), val);
	}
	
	default void removeVal(DefValKey key) {
		removeVal(key.name());
	}
	
	default void putDefVal(DefValKey key) {
		putVal(key.name(), key.strDef());
	}

}
