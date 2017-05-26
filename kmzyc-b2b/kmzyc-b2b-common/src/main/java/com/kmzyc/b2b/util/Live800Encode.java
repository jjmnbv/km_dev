package com.kmzyc.b2b.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * live800客户信息接口数据加密工具类
 * 
 * @author river
 * 
 */
public class Live800Encode {
  private static final String[] hexDigits =
      {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

  static String byteArrayToHexString(byte[] b) {
    StringBuilder resultSb = new StringBuilder();
    for (int i = 0; i < b.length; i++) {
      resultSb.append(byteToHexString(b[i]));
    }
    return resultSb.toString();
  }

  static String byteToHexString(byte b) {
    int n = b;
    if (n < 0) {
      n += 256;
    }
    int d1 = n / 16;
    int d2 = n % 16;
    return hexDigits[d1] + hexDigits[d2];
  }

  /**
   * @param origin String
   * @return String
   * @throws Exception
   */
  public static String getMD5Encode(String origin) throws Exception {
    if (!inited) {
      throw new Exception("MD5 算法实例初始化错误！");
    }
    if (origin == null) {
      return null;
    }
    byte[] temp = null;
    synchronized (md) {
      temp = md.digest(origin.getBytes());
    }

    return byteArrayToHexString(temp);

  }

  public static String getEncryptKey() {
    return ConfigurationUtil.getString("live800.encrypt.key");
  }

  private static MessageDigest md = null;
  private static boolean inited = false;
  static {
    try {
      md = MessageDigest.getInstance("MD5");
      inited = true;
    } catch (NoSuchAlgorithmException ex) {
      inited = false;
    }
  }
}
