package com.kmzyc.b2b.util.wxpay;

import java.util.Objects;

public class MD5SignUtil {
  public static String sign(String content, String key) throws SDKRuntimeException {
    String signStr = "";
    if (Objects.equals("", key)) {
      throw new SDKRuntimeException("key不能为空");
    }
    if (Objects.equals("", content)) {
      throw new SDKRuntimeException("内容不能为空");
    }
    signStr = content + "&key=" + key;
    return MD5Util.MD5(signStr).toUpperCase();

  }

  public static boolean verifySignature(String content, String sign, String md5Key) {
    String signStr = content + "&key=" + md5Key;
    String calculateSign = MD5Util.MD5(signStr).toUpperCase();
    String tenpaySign = sign.toUpperCase();
    return Objects.equals(calculateSign, tenpaySign);
  }
}
