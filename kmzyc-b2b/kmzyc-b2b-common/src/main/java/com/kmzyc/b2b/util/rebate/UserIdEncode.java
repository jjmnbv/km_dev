package com.kmzyc.b2b.util.rebate;

// package com.kmzyc.b2b.util.rebate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
///**
// * 邀请链接中用户ＩＤ参数加密工具类
// *
// * @author river
// * @date 2015年3月12日 下午3:19:27
// *
// */
//public class UserIdEncode {
//
//  private static final Map<Character, String> encodeTable =new HashMap<Character, String>();
//
//  private static final long MAGICNUM = Long.MAX_VALUE;
//
//  static {
//    encodeTable.put('0', "d");
//    encodeTable.put('1', "e");
//    encodeTable.put('2', "c");
//    encodeTable.put('3', "o");
//    encodeTable.put('4', "x");
//    encodeTable.put('5', "p");
//    encodeTable.put('6', "t");
//    encodeTable.put('7', "h");
//    encodeTable.put('8', "g");
//    encodeTable.put('9', "m");
//  }
//
//  /**
//   * 会员ＩＤ加密
//   *
//   * @param userid 会员ＩＤ
//   * @param
//   * @return String　加密后字符串
//   */
//  public static String encode(long userid) throws Exception {
//    long first = userid ^ MAGICNUM;
//    StringBuilder buf = new StringBuilder();
//    char[] chars = Long.toString(first).toCharArray();
//    for (int i = 0; i < chars.length; i++) {
//      buf.append(encodeTable.get(chars[i]));
//    }
//    return buf.toString();
//  }
//}
