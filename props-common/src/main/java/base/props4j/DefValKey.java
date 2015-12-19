package base.props4j;

import java.math.BigDecimal;

/**
 * Useful for enum keys 
 * @author Evgeny Dolganov
 */
public interface DefValKey {
	
	/**
	 * Key name for props
	 */
	String getName();
	
	/**
	 * Default value for props
	 */
	DefVal getDefVal();
	
	
	//
	//Useful Methods
	//
	
	default String strDef(){
		return getDefVal().strDef();
	}
	
	default Boolean boolDef(){
		return getDefVal().boolDef();
	}
	
	default Integer intDef(){
		return getDefVal().intDef();
	}
	
	default Long longDef(){
		return getDefVal().longDef();
	}
	
	default Double doubleDef(){
		return getDefVal().doubleDef();
	}
	
	default BigDecimal bigDecimalDef(){
		return getDefVal().bigDecimalDef();
	}
	
	
	default Integer intFrom(Props props){
		String key = getName();
		Integer defVal = getDefVal().intDef();
		return props == null? defVal : props.intVal(key, defVal);
	}
	
	default Long longFrom(Props props){
		String key = getName();
		Long defVal = getDefVal().longDef();
		return props == null? defVal : props.longVal(key, defVal);
	}
	
	default String strFrom(Props props){
		String key = getName();
		String defVal = getDefVal().strDef();
		return props == null? defVal : props.strVal(key, defVal);
	}
	
	default Boolean boolFrom(Props props){
		String key = getName();
		Boolean defVal = getDefVal().boolDef();
		return props == null? defVal : props.boolVal(key, defVal);
	}
	
	default Double doubleFrom(Props props){
		String key = getName();
		Double defVal = getDefVal().doubleDef();
		return props == null? defVal : props.doubleVal(key, defVal);
	}

}
