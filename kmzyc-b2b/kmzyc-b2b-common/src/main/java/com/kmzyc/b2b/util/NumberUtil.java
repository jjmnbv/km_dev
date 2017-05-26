package com.kmzyc.b2b.util;

import java.math.BigDecimal;

public class NumberUtil {
  /**
   * 格式化保留两位小数
   * 
   * @param number
   * @return
   */
  public static Double toDouble(Double number) {
    if (number == null) return null;
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
  public static Double toDouble(BigDecimal number) {
    if (number == null) return null;
    Double af = number.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    return af;
  }

  /**
   * 格式化保留两位小数
   * 
   * @param number
   * @return
   */
  public static BigDecimal toBigDecimal(BigDecimal number) {
    if (number == null) return null;
    BigDecimal af3 = number.setScale(3, BigDecimal.ROUND_HALF_UP);
    BigDecimal af2 = number.setScale(2, BigDecimal.ROUND_HALF_UP);
    if (af3.compareTo(af2) > 0) {
      return af2.add(BigDecimal.valueOf(0.01));
    }
    return af2;
  }

  public static boolean isInteger(String number){
    try {
      Integer.parseInt(number);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static void main(String[] args) {

    System.out.println(toBigDecimal(BigDecimal.valueOf(12.00215445545)));
  }

}
