package com.kmzyc.b2b.util.wxpay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class Sha1Util {
  static Logger logger= LoggerFactory.getLogger(Sha1Util.class);
  public final static String sha1(String s) {
    char hexDigits[] =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    try {
      byte[] btInput = s.getBytes();
      MessageDigest mdInst = MessageDigest.getInstance("sha-1");
      mdInst.update(btInput);
      byte[] md = mdInst.digest();
      int j = md.length;
      char str[] = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        str[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(str);
    } catch (Exception e) {
      logger.error(e.getMessage(),e);
      return null;
    }
  }
}
