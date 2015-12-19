package base.props4j;

import static base.props4j.util.Util.*;

import java.math.BigDecimal;

public class DefVal {
	
	public final String defVal;
	
	public DefVal(){
		this(null);
	}
	
	public DefVal(Object defVal){
		this.defVal = defVal == null? null : defVal.toString();
	}
	
	public String strDef(){
		return defVal;
	}
	
	public Boolean boolDef(){
		return tryParseBool(defVal, null);
	}
	
	public Integer intDef(){
		return tryParseInt(defVal, null);
	}
	
	public Long longDef(){
		return tryParseLong(defVal, null);
	}
	
	public Double doubleDef(){
		return tryParseDouble(defVal, null);
	}
	
	public BigDecimal bigDecimalDef(){
		return tryParseBigDecimal(defVal, null);
	}

}
