package com.kmzyc.promotion.app.util;

public class PurchaseUtils {

	/**
	 * 字符串数组转为int数据,要求两个数组长度一样
	 * 
	 * @param strs
	 * @param ints
	 */
	public static Long[] StrArrToIntArr(String purchaseIds) {
		// 去除最后一个逗号
		if (purchaseIds.indexOf(",") >= 0) {
			purchaseIds = purchaseIds.substring(0, purchaseIds.length() - 1);
		}
		// 将获得到的字符串，以逗号分开，获得采购单ID数组
		String[] purchaseIdArray = purchaseIds.split(",");
		// Integer类型存放采购单ID
		Long[] purchaseIdInt = new Long[purchaseIdArray.length];
		for (int i = 0; i < purchaseIdArray.length; i++) {
			purchaseIdInt[i] = Long.valueOf(purchaseIdArray[i]);
		}
		purchaseIdArray = null;// 将原数组置空

		return purchaseIdInt;
	}
}
