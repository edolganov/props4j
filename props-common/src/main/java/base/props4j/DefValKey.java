package base.props4j;

import java.math.BigDecimal;

public interface DefValKey {
	
	String name();
	
	DefVal getDefVal();
	
	
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
		String key = name();
		Integer defVal = getDefVal().intDef();
		return props == null? defVal : props.intVal(key, defVal);
	}
	
	default Long longFrom(Props props){
		String key = name();
		Long defVal = getDefVal().longDef();
		return props == null? defVal : props.longVal(key, defVal);
	}
	
	default String strFrom(Props props){
		String key = name();
		String defVal = getDefVal().strDef();
		return props == null? defVal : props.strVal(key, defVal);
	}
	
	default Boolean boolFrom(Props props){
		String key = name();
		Boolean defVal = getDefVal().boolDef();
		return props == null? defVal : props.boolVal(key, defVal);
	}

}
