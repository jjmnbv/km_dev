package com.kmzyc.supplier.util;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.HashSet;
import java.util.Set;

/**
 * 汉字转拼音
 * @author river
 *
 */
public class PinyinUtil {
	
	private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
	
	/**
	 * 获取汉字拼音
	 * @param source
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String[] getNormalPy(String source) throws BadHanyuPinyinOutputFormatCombination {
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
	    format.setVCharType(HanyuPinyinVCharType.WITH_V);
	    Set<String> result = new HashSet<String>();
	    result = getPy(source, 0, result);
	    String[] py = new String[result.size()];
	    int i=0;
	    for (String p : result) {
	    	py[i] = p;
	    	i++;
	    }
	    return py;
	}
	
	/**
	 * 获取汉字简拼
	 * @param source
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String[] getJianPy(String source) throws BadHanyuPinyinOutputFormatCombination {
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
	    format.setVCharType(HanyuPinyinVCharType.WITH_V);
	    Set<String> result = new HashSet<String>();
	    result = getJp(source, 0, result);
	    String[] py = new String[result.size()];
	    int i=0;
	    for (String p : result) {
	    	py[i] = p;
	    	i++;
	    }
	    return py;
	}
    			
	private static Set<String> getPy(String source, int index, Set<String> result) throws BadHanyuPinyinOutputFormatCombination{
		if (index == source.length()) {
			return result;
		}
		char c = source.charAt(index);
		String[] pys = null;
		// 判断是否为汉字，如果是汉字转成拼音
		if (isChinese(c)) {
			pys = PinyinHelper.toHanyuPinyinStringArray(c, format);
		} else {
			// 非汉字保存原始值
			String t = source.substring(index, index+1);
			pys = new String[1];
			pys[0] = t;
		}
		if (!result.isEmpty()) {
			Set<String> newResult = new HashSet<String>();
			for (String prefix : result) {
				String temp = prefix;
				for (String suffix : pys) {
					temp += suffix;
					newResult.add(temp);
					temp = prefix;
				}
			}
			return getPy(source, index+1, newResult);
		} else {
			for (String suffix : pys) {
				result.add(suffix);
			}
			return getPy(source, index+1, result);
		}
	}
	
	private static Set<String> getJp(String source, int index, Set<String> result) throws BadHanyuPinyinOutputFormatCombination{
		if (index == source.length()) {
			return result;
		}
		char c = source.charAt(index);
		String[] pys = null;
		// 判断是否为汉字，如果是汉字转成拼音
		if (isChinese(c)) {
			pys = PinyinHelper.toHanyuPinyinStringArray(c, format);
			for (int i=0; i<pys.length; i++) {
				String p = pys[i];
				pys[i] = p.substring(0, 1);
			}
		} else {
			// 非汉字保存原始值
			String t = source.substring(index, index+1);
			pys = new String[1];
			pys[0] = t;
		}
		if (!result.isEmpty()) {
			Set<String> newResult = new HashSet<String>();
			for (String prefix : result) {
				String temp = prefix;
				for (String suffix : pys) {
					temp += suffix;
					newResult.add(temp);
					temp = prefix;
				}
			}
			return getJp(source, index+1, newResult);
		} else {
			for (String suffix : pys) {
				result.add(suffix);
			}
			return getJp(source, index+1, result);
		}
	}
	
	public static boolean isChinese(char a) {
	     int v = (int)a;
	     return (v >=19968 && v <= 171941);
	}
	
}