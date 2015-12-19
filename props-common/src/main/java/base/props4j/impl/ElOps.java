package base.props4j.impl;

import static base.props4j.util.Util.*;
import static java.lang.Boolean.*;
import base.props4j.ValsHolder;
import base.props4j.api.model.Pair;

public class ElOps {
	
	private static ThreadLocal<Boolean> threadLocalSkipEl = new ThreadLocal<Boolean>();
	
	public static void setSkipEl(boolean val){
		if(val) threadLocalSkipEl.set(true);
		else threadLocalSkipEl.remove();
	}
	
	public static String processEl(String val, ValsHolder vals){
		Pair<String, Boolean> out = processElIfNeed(val, vals);
		return out.first;
	}

	public static Pair<String, Boolean> processElIfNeed(String val, ValsHolder vals){
		
		if(TRUE.equals(threadLocalSkipEl.get()))
			return new Pair<>(val, false);
		
		if( ! hasText(val))
			return new Pair<>(val, false);
		
		String elBegin = "${";
		
		int maxTries = 10;
		int curTry = 0;
		int fromIndex = 0;
		boolean updated = false;
		while(true){
			
			curTry++;
			if(curTry > maxTries)
				return new Pair<>(val, updated);
			
			if(fromIndex >= val.length())
				return new Pair<>(val, updated);
			
			int beginIndex = val.indexOf(elBegin, fromIndex);
			if(beginIndex < 0)
				return new Pair<>(val, updated);
			
			int keyBeginIndex = beginIndex+elBegin.length();
			int endIndex = val.indexOf('}', keyBeginIndex);
			if(endIndex < 1)
				return new Pair<>(val, updated);
			
			
			String elKey = val.substring(keyBeginIndex, endIndex);
			String elVal = vals.strVal(elKey, (String)null);
			
			if(isEmpty(elVal)){
				fromIndex = endIndex+1;
				continue;
			} else {
				updated = true;
				fromIndex = 0;
				val = val.substring(0, beginIndex) + elVal + val.substring(endIndex+1);
			}
		}

		
	}

}
