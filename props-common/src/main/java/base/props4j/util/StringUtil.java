package base.props4j.util;

import static base.props4j.util.Util.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;


public class StringUtil {
	
	public static final String DEFAULT_ST_SEPARATORS = " \t\n\r\f";
	
	public static String removeAll(String str, String invalidChars){
		return replaceAll(str, invalidChars, null);
	}
	
	public static String replaceAll(String str, String invalidChars, String replacement){
		if(str == null) return null;
		char ch;
		boolean invalid;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			invalid = false;
			for (int j = 0; j < invalidChars.length(); j++) {
				if(ch == invalidChars.charAt(j)){
					invalid = true;
					break;
				}
			}
			if( ! invalid) sb.append(ch);
			else if(replacement != null )sb.append(replacement);
		}
		return sb.toString();
	}
	
	
	public static String cropWithDots(String str, int maxLenght){
		return crop(str, maxLenght, true);
	}
	
	public static String crop(String str, int maxLenght, boolean useDots){
		if(Util.isEmpty(str)){
			return str;
		}
		
		if(str.length() <= maxLenght){
			return str;
		}
		
		String out = str.substring(0, maxLenght);
		if(useDots){
			out += "...";
		}
		return out;
	}
	
	public static String createStr(char c, int length){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(c);
		}
		return sb.toString();
	}
	
	public static String randomStr(int length){
		int beginCode = 'a';
		int endCode = 'z';
		int delta = endCode - beginCode;
		Random r = new Random();
		char nextCh;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			nextCh = (char)(r.nextInt(delta+1) + beginCode);
			sb.append(nextCh);
		}
		return sb.toString();
	}
	
	public static boolean containsAny(String source, String... subs){
		if(source == null) return false;
		if(isEmpty(subs)) return true;
		for (String str : subs) {
			if(source.contains(str)){
				return true;
			}
		}
		return false;
	}
	
	public static byte[] getBytesUTF8(String str){
		try {
			return str.getBytes(UTF8);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("can't get utf8 Encoding", e);
		}
	}
	
	public static String getStrUTF8(byte[] bytes){
		try {
			return new String(bytes, UTF8);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("can't get utf8 Encoding", e);
		}
	}
	
	public static String collectionToStr(Collection<?> collection){
		return collectionToStr(collection, null, null, null, true);
	}
	
	public static String collectionToStr(Collection<?> collection, Object separator){
		return collectionToStr(collection, separator, null, null, true);
	}
	
	public static String collectionToStr(Collection<?> collection, Object beginBlock, Object endBlock){
		return collectionToStr(collection, null, beginBlock, endBlock, true);
	}
	
	public static String collectionToStr(Collection<?> collection, Object separator, Object beginBlock, Object endBlock){
		return collectionToStr(collection, separator, beginBlock, endBlock, true);
	}
	
	public static String collectionToStr(Collection<?> collection, Object separator, Object beginBlock, Object endBlock, boolean useConvert){
		
		if(collection == null) return null;
		if(separator == null) separator = ',';
		
		StringBuilder sb = new StringBuilder();
		if(beginBlock != null) sb.append(beginBlock);
		boolean first = true;
		for(Object ob : collection){
			if(!first) sb.append(separator);
			first = false;
			
			if(!useConvert) sb.append(ob);
			else sb.append(ob);
		}
		if(endBlock != null) sb.append(endBlock);
		return sb.toString();
	}
	
	public static List<String> strToList(String val){
		return strToList(val, ",", null, null);
	}
	
	public static List<String> strToListWithDefaultSeps(String val){
		return strToList(val, DEFAULT_ST_SEPARATORS, null, null);
	}
	
	public static List<String> strToList(String val, String separators){
		return strToList(val, separators, null, null);
	}
	
	public static List<String> strToList(String val, Character separator, Character beginBlock, Character endBlock){
		return strToList(val, separator == null? null : separator.toString(), beginBlock, endBlock);
	}
	
	public static List<String> strToList(String val, String separators, Character beginBlock, Character endBlock){
		if(val == null) return null;
		if(separators == null) separators = ",";
		
		if(beginBlock != null && val.indexOf(beginBlock) == 0){
			val = val.substring(1);
		}
		if(endBlock != null && val.lastIndexOf(endBlock) == val.length()-1){
			val = val.substring(0, val.length()-1);
		}
		
		ArrayList<String> out = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(val, separators);
		while(st.hasMoreTokens()){
			String token = st.nextToken();
			if( ! isEmpty(token)) out.add(token);
		}
		return out;
	}
	
	public static int countOf(String val, char target){
		if(val == null) 
			return 0;
	    int count = 0;
	    for (int i=0; i < val.length(); i++)
	        if (val.charAt(i) == target)
	             count++;
	    return count;
		
	}
	
	public static String cropByLines(String val, int maxLinesCount){
		
		if(isEmpty(val))
			return val;
		
		if(maxLinesCount < 1) 
			return "";
		
		int addedLines = 0;
		StringBuilder sb = new StringBuilder();
		int length = val.length();
		for (int i = 0; i < length; i++) {
			char ch = val.charAt(i);
			if(ch == '\n'){
				addedLines++;
				if(i < length-1 && addedLines >= maxLinesCount){
					sb.append("\n...");
					break;
				} else {
					sb.append(ch);
				}
			} 
			else {
				sb.append(ch);
			}
		}
		
		return sb.toString();
	}

}
