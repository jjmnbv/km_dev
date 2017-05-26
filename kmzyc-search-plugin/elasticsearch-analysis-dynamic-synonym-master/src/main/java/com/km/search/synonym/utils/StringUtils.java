/** 
 * project : B2C 康美健康商城
 * module : elasticsearch-analysis-dynamic-synonym 
 * package : com.km.search.analyzer.utils 
 * date: 2016年9月1日下午6:48:53 
 * Copyright (c) 2016, KM@km.com All Rights Reserved. 
 */ 
package com.km.search.synonym.utils; 

/** 
 * Stringutils 类
 * @author   KM 
 * @date   2016年9月1日 下午6:48:53 
 * @version   
 * @see       
 */
public class StringUtils {
	
	public static boolean isBlank(String str){
		return null == str || "".equals(str.trim());	
	}
	
	public static boolean isNotBlank(String str){
		return !(isBlank(str));
	}
	

}
  