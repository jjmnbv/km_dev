package com.kmzyc.framework.util;

import java.io.UnsupportedEncodingException;

import org.ow2.util.base64.Base64;

import com.alibaba.fastjson.JSONObject;

public class Base64Util {
  public static void main(String[] args) throws Exception {
    JSONObject json = new JSONObject();
    json.put("name", "何文锋");
    json.put("id", "12313");
    json.put("pjk", "苡逅嘚苡逅dfserfg");
    json.put("nick", "〆﹏放丶卟下");
    // 编码
    String text = json.toJSONString();
    String ciphertext = encode(text);
    System.out.println("text:" + text);
    System.out.println("ciphertext:" + ciphertext);


    // 解码
    String text2 = decode(ciphertext);
    System.out.println("ciphertext.decode:" + text2);
    System.out.println(text2.equals(text));



  }

  static String charsetName = "utf-8";

  /** 编码 */
  public static String encode(String text) throws UnsupportedEncodingException {
    char[] charArray = Base64.encode(text.getBytes(charsetName));
    String jsonString = new String(charArray);
    return jsonString;
  }

  /** 解码 */
  public static String decode(String ciphertext) throws UnsupportedEncodingException {
    char[] charArray2 = ciphertext.toCharArray();
    byte[] byteArray = Base64.decode(charArray2);
    String encodeString = new String(byteArray, charsetName);
    return encodeString;
  }
}
