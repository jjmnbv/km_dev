package com.kmzyc.promotion.app.util;

import java.math.BigDecimal;

public class NumberUtil {

	/**
	 * 格式化保留两位小数
	 * 
	 * @param number
	 * @return
	 */
	public static Double toDouble(Double number) {
		if (number == null)
			return null;
		BigDecimal big = new BigDecimal(number);
		Double af = big.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return af;
	}

	/**
	 * 格式化保留两位小数
	 * 
	 * @param number
	 * @return
	 */
	public static BigDecimal toBigDecimal(BigDecimal number) {
		BigDecimal af3 = number.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal af2 = number.setScale(2, BigDecimal.ROUND_HALF_UP);
		if (af3.compareTo(af2) > 0) {
			return af2.add(BigDecimal.valueOf(0.01));
		}
		return af2;
	}

	public static void main(String[] args) {
		// System.out.println(toDouble(1312.0520032151613132131));
	}
}
