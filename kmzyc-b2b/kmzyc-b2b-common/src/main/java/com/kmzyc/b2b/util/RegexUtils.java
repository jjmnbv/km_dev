package com.kmzyc.b2b.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

  /**
   * 判断是否为手机号码
   * 
   * @param mobiles
   * @return
   */
  public static boolean isMobileNO(String mobiles) {
    Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
    Matcher m = p.matcher(mobiles);
    return m.matches();
  }

  /**
   * 判断是否为邮箱格式
   * 
   * @param email
   * @return
   */
  public static boolean checkEmail(String email) {
    boolean flag = false;
    try {
      String check =
          "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
      Pattern regex = Pattern.compile(check);
      Matcher matcher = regex.matcher(email);
      flag = matcher.matches();
    } catch (Exception e) {
      flag = false;
    }
    return flag;
  }

  public static void main(String[] args) {

    System.out.println(RegexUtils.checkEmail("40740870@qq.com"));

  }

}
