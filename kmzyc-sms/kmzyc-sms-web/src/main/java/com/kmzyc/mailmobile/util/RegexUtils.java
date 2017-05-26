package com.kmzyc.mailmobile.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	

	/**
	 * 判断是否为手机号码
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){  
		Pattern p = Pattern.compile("^1[0-9][0-9]{9}$");  
		Matcher m = p.matcher(mobiles);  
		return m.matches(); 
	}
}
