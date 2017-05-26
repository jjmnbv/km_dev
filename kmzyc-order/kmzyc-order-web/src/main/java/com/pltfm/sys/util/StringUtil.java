package com.pltfm.sys.util;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Technologies.
 * </p>
 * 
 * @author
 * @version 1.0
 */

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

@SuppressWarnings("unchecked")
public class StringUtil {
  private static Logger log = Logger.getLogger(StringUtil.class);

  /**
   * Replaces all instances of oldString with newString in line.
   */
  public static final String replace(String line, String oldString, String newString) {
    int i = 0;
    if ((i = line.indexOf(oldString, i)) >= 0) {
      char[] line2 = line.toCharArray();
      char[] newString2 = newString.toCharArray();
      int oLength = oldString.length();
      StringBuffer buf = new StringBuffer(line2.length);
      buf.append(line2, 0, i).append(newString2);
      i += oLength;
      int j = i;
      while ((i = line.indexOf(oldString, i)) > 0) {
        buf.append(line2, j, i - j).append(newString2);
        i += oLength;
        j = i;
      }
      buf.append(line2, j, line2.length - j);
      return buf.toString();
    }
    return line;
  }

  /**
   * This method takes a string which may contain HTML tags (ie, &lt;b&gt;, &lt;table&gt;, etc) and
   * converts the '&lt'' and '&gt;' characters to their HTML escape sequences.
   * 
   * @param input the text to be converted.
   * @return the input string with the characters '&lt;' and '&gt;' replaced with their HTML escape
   *         sequences.
   */
  public static final String escapeHTMLTags(String input) {
    // Check if the string is null or zero length -- if so, return
    // what was sent in.
    if (input == null || input.length() == 0) {
      return input;
    }
    // Use a StringBuffer in lieu of String concatenation -- it is
    // much more efficient this way.
    StringBuffer buf = new StringBuffer(input.length());
    char ch = ' ';
    for (int i = 0; i < input.length(); i++) {
      ch = input.charAt(i);
      if (ch == '<') {
        buf.append("&lt;");
      } else if (ch == '>') {
        buf.append("&gt;");
      }
      // else if (ch == '"') {
      // buf.append('\\');
      // buf.append(ch);
      // }
      else if (ch == '"') {
        buf.append("&quot;");
      } else if (ch == '\'') {
        buf.append("&apos;");
      } else if (ch == '&') {
        buf.append("&amp;");
      }

      else if (ch == '\\') {
        buf.append('\\');
        buf.append(ch);
      } else if (ch == '\r') {
        buf.append("\\r");
      } else if (ch == '\n') {
        buf.append("\\n");
      } else {
        buf.append(ch);
      }
    }
    return buf.toString();
  }

  /**
   * This method takes a string which may contain HTML tags (ie, &lt;b&gt;, &lt;table&gt;, etc) and
   * converts the '&lt'' and '&gt;' characters to their HTML escape sequences.
   * 
   * @param input the text to be converted.
   * @return the input string with the characters '&lt;' and '&gt;' replaced with their HTML escape
   *         sequences.
   */
  public static final String viewHTMLTags(String input) {
    // Check if the string is null or zero length -- if so, return
    // what was sent in.
    if (input == null || input.length() == 0) {
      return input;
    }
    // Use a StringBuffer in lieu of String concatenation -- it is
    // much more efficient this way.
    StringBuffer buf = new StringBuffer(input.length());
    char ch = ' ';
    for (int i = 0; i < input.length(); i++) {
      ch = input.charAt(i);
      if (ch == '<') {
        buf.append("&lt;");
      } else if (ch == '>') {
        buf.append("&gt;");
      } else if (ch == ' ') {
        buf.append("&nbsp;");
      }
      // else if (ch == '"') {
      // buf.append('\\');
      // buf.append(ch);
      // }
      else if (ch == '"') {
        buf.append("&quot;");
      } else if (ch == '\'') {
        buf.append("&apos;");
      }
      // else if (ch == '&') {
      // buf.append("&amp;");
      // }

      else if (ch == '\\') {
        // buf.append('\\');
        buf.append(ch);
      } else if (ch == '\r') {
        buf.append("<br>");
      } else if (ch == '\n') {
        // buf.append("\\n");
      } else {
        buf.append(ch);
      }
    }
    return buf.toString();
  }

  /**
   * Used by the hash method.
   */
  private static MessageDigest digest = null;

  /**
   * Hashes a String using the Md5 algorithm and returns the result as a String of hexadecimal
   * numbers. This method is synchronized to avoid excessive MessageDigest object creation. If
   * calling this method becomes a bottleneck in your code, you may wish to maintain a pool of
   * MessageDigest objects instead of using this method.
   * <p>
   * A hash is a one-way function -- that is, given an input, an output is easily computed. However,
   * given the output, the input is almost impossible to compute. This is useful for passwords since
   * we can store the hash and a hacker will then have a very hard time determining the original
   * password.
   * <p>
   * In Jive, every time a user logs in, we simply take their plain text password, compute the hash,
   * and compare the generated hash to the stored hash. Since it is almost impossible that two
   * passwords will generate the same hash, we know if the user gave us the correct password or not.
   * The only negative to this system is that password recovery is basically impossible. Therefore,
   * a reset password method is used instead.
   * 
   * @param data the String to compute the hash of.
   * @return a hashed version of the passed-in String
   */
  public synchronized static final String hash(String data) {
    if (digest == null) {
      try {
        digest = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException nsae) {
        System.err.println("Failed to load the MD5 MessageDigest. "
            + "Jive will be unable to function normally.");
        // nsalog.error(e);
      }
    }
    // Now, compute hash.
    digest.update(data.getBytes());
    return toHex(digest.digest());
  }

  /**
   * Turns an array of bytes into a String representing each byte as an unsigned hex number.
   * <p>
   * Method by Santeri Paavolainen, Helsinki Finland 1996<br>
   * (c) Santeri Paavolainen, Helsinki Finland 1996<br>
   * Distributed under LGPL.
   * 
   * @param hash an rray of bytes to convert to a hex-string
   * @return generated hex string
   */
  public static final String toHex(byte hash[]) {
    StringBuffer buf = new StringBuffer(hash.length * 2);
    String stmp = "";

    for (int i = 0; i < hash.length; i++) {
      stmp = (java.lang.Integer.toHexString(hash[i] & 0XFF));
      if (stmp.length() == 1) {
        buf.append(0).append(stmp);
      } else {
        buf.append(stmp);
      }
    }
    return buf.toString();
  }

  public static final byte[] hexToBytes(String hex) {
    if (null == hex) {
      return new byte[0];
    }
    int len = hex.length();
    byte[] bytes = new byte[len / 2];
    String stmp = null;
    try {
      for (int i = 0; i < bytes.length; i++) {
        stmp = hex.substring(i * 2, i * 2 + 2);
        bytes[i] = (byte) Integer.parseInt(stmp, 16);
      }
    } catch (Exception e) {
      log.error(e);
      return new byte[0];
    }

    return bytes;
  }

  /**
   * Escapes all necessary characters in the String so that it can be used in an XML doc.
   * 
   * @param string the string to escape.
   * @return the string with appropriate characters escaped.
   */
  public static final String escapeForXML(String string) {
    // Check if the string is null or zero length -- if so, return
    // what was sent in.
    if (string == null || string.length() == 0) {
      return string;
    }
    char[] sArray = string.toCharArray();
    StringBuffer buf = new StringBuffer(sArray.length);
    char ch;
    for (int i = 0; i < sArray.length; i++) {
      ch = sArray[i];
      if (ch == '<') {
        buf.append("&lt;");
      } else if (ch == '>') {
        buf.append("&gt;");
      } else if (ch == '"') {
        buf.append("&quot;");
      } else if (ch == '&') {
        buf.append("&amp;");
      } else {
        buf.append(ch);
      }
    }
    return buf.toString();
  }

  /**
   * Unescapes the String by converting XML escape sequences back into normal characters.
   * 
   * @param string the string to unescape.
   * @return the string with appropriate characters unescaped.
   */
  public static final String unescapeFromXML(String string) {
    string = replace(string, "&lt;", "<");
    string = replace(string, "&gt;", ">");
    string = replace(string, "&quot;", "\"");
    return replace(string, "&amp;", "&");
  }

  public static final String emailFormat(String string) {
    String[] end = new String[] {"B", "kB", "MB", "GB"};
    double num = 0;
    int i = 0;
    try {
      num = (double) Integer.parseInt(string);
    } catch (Exception e) {
      num = 0;
    }

    while (num > 1024 && i < end.length) {
      num /= 1024;
      i++;
    }
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(2);

    return nf.format(num) + " " + end[i];
  }

  /**
   * 将带字符串按Byte位长度取子字符串 防止带汉字的字符串长度取错 防止出现Exception
   * 
   * @param String src 源字符串
   * @param int beginIndex 起始位置,0为第一位
   * @param int endIndex 终止位置,1为第一位
   * @return String
   * 
   * @auth LBL
   * @since 2002.11.27
   */
  public static String substr(String src, int beginIndex, int endIndex) {
    String dest = "";
    if (src == null) {
      return dest;
    }

    byte[] srcByte = src.getBytes();
    byte[] destByte = null;
    int srclen = srcByte.length;
    if (srclen <= beginIndex || beginIndex >= endIndex) {
      return "";
    }

    if (srclen >= endIndex) {
      destByte = new byte[endIndex - beginIndex];
      System.arraycopy(srcByte, beginIndex, destByte, 0, endIndex - beginIndex);
      dest = new String(destByte);
      return dest;
    } else {
      destByte = new byte[srclen - beginIndex];
      System.arraycopy(srcByte, beginIndex, destByte, 0, srclen - beginIndex);
      dest = new String(destByte);
      return dest;
    }
  }

  /**
   * 将带字符串按Byte位长度取子字符串 防止带汉字的字符串长度取错：如果出现半个汉字情况，减去这半个汉字 防止出现Exception
   * 
   * @param String src 源字符串
   * @param int byteNum 长度
   * 
   * @auth shishuai
   * @since 2009.7.18
   */
  public static String slsubstr(String src, int byteNum) {
    if (src == null || byteNum <= 0) {
      return "";
    }

    byte[] srcByte = src.getBytes();
    int srclength = srcByte.length;

    if (srclength > byteNum) {
      String cStr = new String(srcByte, byteNum - 1, 2);
      if (cStr.length() == 1 && src.indexOf(cStr) > -1) {
        byteNum--;
      }
      return new String(srcByte, 0, byteNum);
    } else {
      return src;
    }
  }

  /**
   * 处理汉字字串的substr 将带字符串按Byte位长度取子字符串 防止带汉字的字符串长度取错 防止出现Exception 防止出现半个汉字
   * 
   * @param String src 源字符串
   * @param int beginIndex 起始位置,0为第一位
   * @param int endIndex 终止位置,1为第一位
   * @param boolean ifAdd ==true 如果最后是半个汉字，返回长度加一 ==false 如果最后是半个汉字，返回长度减一
   * 
   * @return String
   * 
   * @auth LBL
   * @since 2002.11.27
   */
  public static String gbsubstr(String src, int beginIndex, int endIndex, boolean ifAdd) {
    String dest = "";
    dest = substr(src, beginIndex, endIndex);
    if (dest.length() == 0) {
      if (ifAdd) {
        dest = substr(src, beginIndex, endIndex + 1);
      } else {
        dest = substr(src, beginIndex, endIndex - 1);
      }
    }
    return dest;
  }

  /**
   * 处理汉字字串的substr 将带字符串按Byte位长度取子字符串 防止带汉字的字符串长度取错 防止出现Exception 防止出现半个汉字
   * 
   * @param String src 源字符串
   * @param int beginIndex 起始位置,0为第一位
   * @param int endIndex 终止位置,1为第一位 如果最后是半个汉字，返回长度减一
   * 
   * @return String
   * 
   * @auth LBL
   * @since 2002.11.27
   */
  public static String gbsubstr(String src, int beginIndex, int endIndex) {
    return gbsubstr(src, beginIndex, endIndex, false);
  }

  /**
   * 取带汉字字串的length 将带字符串按Byte位长度取子字符串 防止带汉字的字符串长度取错
   * 
   * @param String str 源字符串
   * 
   * @return int Byte位长度
   * 
   * @auth LBL
   * @since 2002.11.27
   */
  public static int gbStrLen(String str) {
    if (str == null) {
      return 0;
    }
    byte[] strByte = str.getBytes();
    return strByte.length;
  }

  /** 产生重复字符串 */
  public static String replicateStr(char ch, int len) {
    String tmpstr = null;
    char[] tmparr = null;

    if (len <= 0) {
      return "";
    }

    tmparr = new char[len];
    for (int i = 0; i < len; i++) {
      tmparr[i] = ch;
    }
    tmpstr = new String(tmparr);

    return tmpstr;
  }

  /**
   * 左对齐填充定长字符串 向字符串尾部添加字符填充长度 可以有汉字
   * */
  public static String lFillStr(String src, char ch, int len) {
    String dest = src;
    int srclen = gbStrLen(src);
    if (srclen > len) {
      dest = gbsubstr(src, 0, len);
      srclen = gbStrLen(dest);
    }

    dest += replicateStr(ch, len - srclen);

    return dest;
  }

  /**
   * 右对齐填充定长字符串 向字符串前部添加字符 可以有汉字
   * */
  public static String rFillStr(String src, char ch, int len) {
    String dest = src;
    int srclen = gbStrLen(src);
    if (srclen > len) {
      dest = gbsubstr(src, 0, len);
      srclen = gbStrLen(dest);
    }

    dest = replicateStr(ch, len - srclen) + dest;

    return dest;
  }

  // Convert the byte array to a character array.
  public static char[] convertByteToChar(byte[] source, int srclen) {
    if (source == null) {
      return null;
    }

    int len = source.length;
    if (len > srclen) {
      len = srclen;
    }
    char[] destChar = new char[len];
    for (int i = 0; i < len; i++) {
      if (source[i] >= 0) {
        destChar[i] = (char) source[i];
      } else {
        destChar[i] = (char) (256 + source[i]);
      }
    }
    return destChar;
  }

  // Convert the character array to a byte array.
  public static byte[] convertCharToByte(char[] source, int srclen) {
    if (source == null) {
      return null;
    }

    int len = source.length;
    if (len > srclen) {
      len = srclen;
    }
    byte[] dest = new byte[len];
    for (int i = 0; i < len; i++) {
      dest[i] = (byte) source[i];
    }
    return dest;
  }

  public static Date asDate(Object val) {
    return asDate(val, DatetimeUtil.DATE_FORMAT_YMD, null);
  }

  public static Date asDate(Object val, String pattern) {
    return asDate(val, pattern, null);
  }

  public static Date asDate(Object val, String pattern, Date def) {
    Date ret = null;
    try {
      ret = DatetimeUtil.changeToDate(val.toString(), pattern);
    } catch (Exception e) {
      ret = def;
    }
    return ret;
  }

  public static Long asLong(Object val) {
    return asLong(val, null);
  }

  public static Long asLong(Object val, Long def) {
    Long ret = null;
    try {
      ret = Long.valueOf(val.toString());
    } catch (Exception e) {
      ret = def;
    }
    return ret;
  }

  public static Integer asInteger(Object val) {
    return asInteger(val, null);
  }

  public static Integer asInteger(Object val, Integer def) {
    Integer ret = null;
    try {
      ret = Integer.valueOf(val.toString());
    } catch (Exception e) {
      ret = def;
    }
    return ret;
  }

  public static Double asDouble(Object val) {
    return asDouble(val, null);
  }

  public static Double asDouble(Object val, Double def) {
    Double ret = null;
    try {
      ret = Double.valueOf(val.toString());
    } catch (Exception e) {
      ret = def;
    }
    return ret;
  }

  public static BigDecimal asBigDecimal(Object val) {
    return asBigDecimal(val, null);
  }

  public static BigDecimal asBigDecimal(Object val, BigDecimal def) {
    BigDecimal ret = null;
    try {
      ret = new BigDecimal(val.toString());
    } catch (Exception e) {
      ret = def;
    }
    return ret;
  }

  /**
   * 将字符串str按toCount位长度分成不同的字符窜保存到list中，支持中文
   * 
   * @param String str 源字符串
   * @param int toCount 分成多少字
   * @return ArrayList
   * 
   * @auth shishuai
   * @since 2008.10.31
   */
  public static ArrayList splitStr(String str, int toCount) {
    int reInt = 0;
    if (str == null) return null;
    char[] tempChar = str.toCharArray();
    int totalChars = tempChar.length;
    ArrayList result = new ArrayList();
    int totalBytes = 0;
    int i = 0;
    while (totalBytes < str.getBytes().length) {
      StringBuffer reStr = new StringBuffer();
      int stepInt = 0;
      for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
        if ((tempChar[kk] >= 0x0021) && (tempChar[kk] <= 0x007e)) {
          reInt += 1;
        } else {
          reInt += 2;
        }

        reStr.append(tempChar[kk]);
        i++;
        stepInt++;
      }
      char[] tmp = new char[totalChars - i];
      for (int j = 0; j < tmp.length; j++) {
        tmp[j] = tempChar[stepInt + j];
      }
      tempChar = tmp;
      totalBytes += reInt;
      reInt = 0;
      result.add(reStr.toString());
    }

    return result;
  }

  /**
   * 将含有html标签的字符串content按contentSize位长度分成不同的字符窜保存到list中
   * 
   * @param String content 含有html标签的字符串
   * @param int contentSize 分成多少字
   * @return ArrayList
   * 
   * @auth shishuai
   * @since 2008.10.31
   */
  public static ArrayList splitHtmlStr(String content, int contentSize) {
    ArrayList list = new ArrayList();

    boolean end = true;
    int size = contentSize;

    int length = content.length();
    while (end) {
      String temp = "";
      /**
       * 如果内容已经少于默认数，就直接作为结尾返回
       */
      if (size >= length) {
        temp = content;
        list.add(temp);
        break;
      }
      /**
       * temp为本次截取内容段 temp2为余下的内容段
       */
      temp = content.substring(0, size);
      String temp2 = content.substring(size + 1, length);
      String session = temp;
      int a = 0;
      int b = 0;

      /**
       * 首先计算 <和>是否相等
       */
      while (temp.indexOf(" <") > -1) {
        a++;
        temp = temp.substring(temp.indexOf(" <") + 1, temp.length());
      }

      temp = session;

      while (temp.indexOf(">") > -1) {
        b++;
        temp = temp.substring(temp.indexOf(">") + 1, temp.length());
      }
      if (a != b) {
        int p = temp2.indexOf(">");
        temp = content.substring(0, size + p + 1);
        temp2 = content.substring(size + p + 2, length);
        session = temp;
      }

      /**
       * 如果相等就再计算 <P和 </p> 是否吻合
       */
      if (a == b) {
        a = 0;
        b = 0;
        temp = session;
        while (temp.indexOf(" <P") > -1) {
          a++;
          temp = temp.substring(temp.indexOf(" <") + 1, temp.length());
        }

        temp = session;

        while (temp.indexOf(" </P") > -1) {
          b++;
          temp = temp.substring(temp.indexOf(">") + 1, temp.length());
        }
        if (a == b) {
          break;
        }else {
          int p = temp2.indexOf(" </P>");
          temp = content.substring(0, size + p + 5);
          try {
            if ((size + p + 5) < length) temp2 = content.substring(size + p + 5, length);
          } catch (Exception e) {
            temp2 = "";
          }
        }
      }

      /**
       * 余下内容更新
       */
      list.add(temp);
      content = temp2;
      length = content.length();

      /**
       * 如果不存在余下内容了就结束本次操作
       */
      if (temp2.equals("") || temp2.length() < 1) end = false;
    }
    return list;
  }

  /**
   * 字符串编码转换的实现方法
   * 
   * @param str 待转换编码的字符串
   * @param newCharset 目标编码
   * @return
   * @throws UnsupportedEncodingException
   */
  public static String changeCharset(String str, String newCharset)
      throws UnsupportedEncodingException {
    if (str != null) {
      // 用默认字符编码解码字符串。
      byte[] bs = str.getBytes();
      // 用新的字符编码生成字符串
      return new String(bs, newCharset);
    }
    return null;
  }

  /*
   * 对应javascript的escape()函数, 加码后的串可直接使用javascript的unescape()进行解码
   */
  public static String escape(String src) {
    int i;
    char j;
    StringBuffer tmp = new StringBuffer();
    tmp.ensureCapacity(src.length() * 6);
    for (i = 0; i < src.length(); i++) {
      j = src.charAt(i);
      if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
        tmp.append(j);
      else if (j < 256) {
        tmp.append("%");
        if (j < 16) tmp.append("0");
        tmp.append(Integer.toString(j, 16));
      } else {
        tmp.append("%u");
        tmp.append(Integer.toString(j, 16));
      }
    }
    return tmp.toString();
  }

  /*
   * 对应javascript的unescape()函数, 可对javascript的escape()进行解码
   */
  public static String unescape(String src) {
    StringBuffer tmp = new StringBuffer();
    tmp.ensureCapacity(src.length());
    int lastPos = 0, pos = 0;
    char ch;
    while (lastPos < src.length()) {
      pos = src.indexOf("%", lastPos);
      if (pos == lastPos) {
        if (src.charAt(pos + 1) == 'u') {
          ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
          tmp.append(ch);
          lastPos = pos + 6;
        } else {
          ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
          tmp.append(ch);
          lastPos = pos + 3;
        }
      } else {
        if (pos == -1) {
          tmp.append(src.substring(lastPos));
          lastPos = src.length();
        } else {
          tmp.append(src.substring(lastPos, pos));
          lastPos = pos;
        }
      }
    }
    return tmp.toString();
  }

  /**
   * 判断字符串childStr在字符串str下是否存在
   * 
   * @param String ,String
   * @return boolean
   * @exception
   */
  public static boolean isExist(String childStr, String str) {
    if ("".equals(childStr)) { // 子字符串为空
      return false;
    } else if ("".equals(str)) { // 母字符串为空
      return false;
    } else { // 母字符串不为空
      if (str.indexOf(childStr) > -1) { // 子字符串在母字符串存在
        return true;
      } else {
        return false;
      }
    }
  }
}
