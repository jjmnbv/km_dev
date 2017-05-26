package com.pltfm.app.util;

import java.util.List;

/**
 * java.util.List处理的工具类
 * 
 * @author    
 * @since 1.0
 */
public class ListUtils {

	/**
	 * 判断java.util.List的size值是否大于0。
	 * 
	 * @param lst
	 *            java.util.List
	 * 
	 * @return 判断的布尔值
	 */
	public static boolean isNotEmpty(List lst) {
		if (lst != null && lst.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断List中是否存在相同的字符串
	 * 
	 * @param str
	 * @param list
	 * @return 判断的布尔值
	 */
	public static boolean isExist(String str, List list) {
		for (int i = 0; i < list.size(); i++) {
			String temp = list.get(i).toString();
			if (temp.equals(str)) {
				return true;
			}
		}
		return false;
	}
}
