package base.props4j;

import static base.props4j.util.Util.*;
import static java.util.logging.Level.*;
import base.props4j.api.exception.PropertyNotFoundException;

import java.math.BigDecimal;
import java.util.Map;

public interface Props extends ValsHolder {
	
	Map<String, String> toMap();
	
	void addChangedListener(PropsChangedListener l);
	
	
	default boolean hasKey(String key){
		return strVal(key) != null;
	}
	
	default String strVal(String key) {
		return strVal(key, (String)null);
	}
	
	default String findStr(String key) throws PropertyNotFoundException {
		String out = strVal(key, (String)null);
		if(out == null) 
			throw new PropertyNotFoundException(key);
		return out;
	}
	
	default Integer intVal(String key, Integer defaultVal) {
		return tryParseInt(strVal(key), defaultVal);
	}

	default Long longVal(String key, Long defaultVal) {
		return tryParseLong(strVal(key), defaultVal);
	}

	default Boolean boolVal(String key, Boolean defaultVal) {
		return tryParseBool(strVal(key), defaultVal);
	}
	
	default Double doubleVal(String key, Double defaultVal) {
		return tryParseDouble(strVal(key), defaultVal);
	}
	
	default BigDecimal bidDecimalVal(String key, BigDecimal defaultVal) {
		return tryParseBigDecimal(strVal(key), defaultVal);
	}
	
	default Class<?> classVal(String key, Class<?> defaultVal) {
		String className = strVal(key);
		if(className == null) return defaultVal;
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			getLog(getClass()).log(WARNING, "ClassNotFoundException for key: "+key);
			return defaultVal;
		}
	}

}
