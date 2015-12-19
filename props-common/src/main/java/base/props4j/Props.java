package base.props4j;

import static base.props4j.util.Util.*;
import base.props4j.api.exception.PropertyNotFoundException;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Evgeny Dolganov
 */
public interface Props extends ValsHolder {
	
	/** 
	 * Get str val by key or defaultVal if no val was found
	 */
	@Override
	String strVal(String key, String defaultVal);
	
	/** 
	 * Convert props to map
	 */
	Map<String, String> toMap();
	
	
	/**
	 * Add events listener
	 */
	void addChangedListener(PropsChangedListener l);
	
	
	//
	//Useful Methods
	//
	
	/**
	 * Check if has not null value by key
	 */
	default boolean hasKey(String key){
		return strVal(key) != null;
	}
	
	
	/**
	 * Get str val or null (if no val by key)
	 */
	default String strVal(String key) {
		return strVal(key, null);
	}
	
	/**
	 * Find str val or exception (if no val by key)
	 */
	default String findStr(String key) throws PropertyNotFoundException {
		String out = strVal(key);
		if(out == null) 
			throw new PropertyNotFoundException(key);
		return out;
	}
	
	
	/**
	 * Get int val or null (if no val by key or can't convert val to int)
	 */
	default Integer intVal(String key) {
		return intVal(key, null);
	}
	
	/**
	 * Get int val or defaultVal (if no val by key or can't convert val to int)
	 */
	default Integer intVal(String key, Integer defaultVal) {
		return tryParseInt(strVal(key), defaultVal);
	}
	
	/**
	 * Find int val or exception (if no val by key or can't convert val to int)
	 */
	default int findInt(String key){
		Integer out = intVal(key);
		if(out == null) 
			throw new PropertyNotFoundException(key);
		return out;
	}
	
	
	/**
	 * Get long val or null (if no val by key or can't convert val to long)
	 */
	default Long longVal(String key) {
		return longVal(key, null);
	}

	/**
	 * Get long val or defaultVal (if no val by key or can't convert val to long)
	 */
	default Long longVal(String key, Long defaultVal) {
		return tryParseLong(strVal(key), defaultVal);
	}
	
	/**
	 * Find long val or exception (if no val by key or can't convert val to long)
	 */
	default long findLong(String key){
		Long out = longVal(key);
		if(out == null) 
			throw new PropertyNotFoundException(key);
		return out;
	}
	
	
	/**
	 * Get bool val or null (if no val by key or can't convert val to boolean)
	 */
	default Boolean boolVal(String key) {
		return boolVal(key, null);
	}

	/**
	 * Get bool val or defaultVal (if no val by key or can't convert val to boolean)
	 */
	default Boolean boolVal(String key, Boolean defaultVal) {
		return tryParseBool(strVal(key), defaultVal);
	}
	
	/**
	 * Find bool val or exception (if no val by key or can't convert val to boolean)
	 */
	default boolean findBool(String key){
		Boolean out = boolVal(key);
		if(out == null) 
			throw new PropertyNotFoundException(key);
		return out;
	}
	
	
	/**
	 * Get double val or null (if no val by key or can't convert val to double)
	 */
	default Double doubleVal(String key) {
		return tryParseDouble(strVal(key), null);
	}
	
	/**
	 * Get double val or defaultVal (if no val by key or can't convert val to double)
	 */
	default Double doubleVal(String key, Double defaultVal) {
		return tryParseDouble(strVal(key), defaultVal);
	}
	
	/**
	 * Find double val or exception (if no val by key or can't convert val to double)
	 */
	default double findDouble(String key){
		Double out = doubleVal(key);
		if(out == null) 
			throw new PropertyNotFoundException(key);
		return out;
	}
	
	
	/**
	 * Get BigDecimal val or null (if no val by key or can't convert val to BigDecimal)
	 */
	default BigDecimal bidDecimalVal(String key) {
		return bidDecimalVal(key, null);
	}
	
	/**
	 * Get BigDecimal val or defaultVal (if no val by key or can't convert val to BigDecimal)
	 */
	default BigDecimal bidDecimalVal(String key, BigDecimal defaultVal) {
		return tryParseBigDecimal(strVal(key), defaultVal);
	}
	
	
	/**
	 * Get Class val or null (if no val by key or can't convert val to Class)
	 */
	default Class<?> classVal(String key) {
		return classVal(key, null);
	}
	
	/**
	 * Get Class val or defaultVal (if no val by key or can't convert val to Class)
	 */
	default Class<?> classVal(String key, Class<?> defaultVal) {
		String className = strVal(key);
		if(className == null) 
			return defaultVal;
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return defaultVal;
		}
	}

}
