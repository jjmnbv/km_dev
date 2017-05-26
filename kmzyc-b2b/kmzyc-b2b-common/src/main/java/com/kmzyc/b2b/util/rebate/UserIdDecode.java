package com.kmzyc.b2b.util.rebate;

// package com.kmzyc.b2b.util.rebate;
//
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//
//import com.google.common.base.Preconditions;
//import com.google.common.collect.Maps;
//
//public class UserIdDecode {
//  private static final Map<Character, Integer> decodeTable = Maps.newHashMap();
//
//  private static final long MAGICNUM = Long.MAX_VALUE;
//
//  static {
//    decodeTable.put('d', 0);
//    decodeTable.put('e', 1);
//    decodeTable.put('c', 2);
//    decodeTable.put('o', 3);
//    decodeTable.put('x', 4);
//    decodeTable.put('p', 5);
//    decodeTable.put('t', 6);
//    decodeTable.put('h', 7);
//    decodeTable.put('g', 8);
//    decodeTable.put('m', 9);
//  }
//
//  public static long decode(String id) throws Exception {
//    Preconditions.checkNotNull(id);
//    Preconditions.checkArgument(StringUtils.isNotBlank(id));
//
//    StringBuilder buf = new StringBuilder();
//    char[] chars = id.toCharArray();
//    for (int i = 0; i < chars.length; i++) {
//      buf.append(decodeTable.get(chars[i]));
//    }
//    long temp = Long.parseLong(buf.toString());
//    return temp ^ MAGICNUM;
//  }
//}
