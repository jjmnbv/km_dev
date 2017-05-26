package com.kmzyc.promotion.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BillCodeUtils {

	/**
	 * 使用该方法 获取单据号
	 * 
	 * @param billType
	 *            参数为单据类型 从工具类BillPrefix 类中获得
	 * @return 单据号
	 */
	public synchronized static String getBillCode(String billType) {
		StringBuilder code = new StringBuilder(billType);
		code = code.append(getDateTime("yyyyMMddHHmmssSSS", new Date()));
		return code.toString();
	}

	private static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		df = new SimpleDateFormat(aMask);
		returnValue = df.format(aDate);
		return (returnValue);
	}

}
