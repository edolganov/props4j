package base.props4j.util;

public class NumberUtil {
	
	
	public static String zeroFormattedStr(int number, int length){
		return zeroFormattedStr((long)number, length);
	}
	
	
	public static String zeroFormattedStr(long number, int length){
		String numberStr = String.valueOf(number);
		int size = numberStr.length();
		int additionZeros = length - size;
		if(additionZeros > 0){
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < additionZeros; i++) {
				sb.append('0');
			}
			sb.append(numberStr);
			numberStr = sb.toString();
		}
		return numberStr;
	}
	
	public static long getLongFromZeroFormattedStr(String str){
		
		if(Util.isEmpty(str)){
			throw new IllegalArgumentException("str is empty");
		}
		
		int nonZeroIndex = -1;
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i) != '0'){
				nonZeroIndex = i;
				break;
			}
		}
		if(nonZeroIndex == -1){
			return 0;
		}
		String number = str.substring(nonZeroIndex);
		return Long.valueOf(number);
	}

}
