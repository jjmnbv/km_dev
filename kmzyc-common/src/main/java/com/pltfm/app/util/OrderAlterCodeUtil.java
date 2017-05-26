package com.pltfm.app.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class OrderAlterCodeUtil {

  private final static SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssms");
  private final static ReentrantLock lock = new ReentrantLock();

  public static String generateOrderAlterCode(BigDecimal customerId) {
    lock.lock();
    try {
      String customerIdStr = customerId.toString();
      // 反转
      String body = new StringBuilder(sdf.format(new Date())).reverse().toString();
      String first = null;
      String last = null;
      String fix = null;
      String result = null;
      // 取result的第一位和最后一位
      if (customerIdStr.length() > 1) {
        first = "" + customerIdStr.charAt(0);
        last = "" + customerIdStr.charAt(customerIdStr.length() - 1);
        fix = last + first;
      } else {
        fix = customerIdStr + "0";
      }
      result = body + fix;
      return result;
    } finally {
      lock.unlock();
    }
  }

  /**
   * 生成批次号
   */
  public static String generateOrderAlterPhotoBatchNo() {
    lock.lock();
    try {
      return sdf.format(new Date());
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {
    // String s =
    generateOrderAlterPhotoBatchNo();
    // System.out.println(s);
    // System.out.println(s.length());
  }

}
