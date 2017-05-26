package com.kmzyc.promotion.util;

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

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
  /**
   * 验证一个字符串是否为空
   * 
   * @param str
   * @return 如果为空返回真，如果不为空返回假1111
   */
  public static boolean isEmpty(String str) {
    if (str == null || "".equals(str)) return true;
    String pattern = "\\S";
    Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
    Matcher m = p.matcher(str);
    return !m.find();
  }

  public static boolean isEmpty(Object o) {
    if (o == null) {
      return true;
    }
    if (o instanceof String) {
      return isEmpty((String) o);
    }
    if (o instanceof Collection<?>) {
      return ((Collection<?>) o).isEmpty();
    }
    if (o instanceof Map<?, ?>) {
      return ((Map<?, ?>) o).isEmpty();
    }
    return isEmpty(o.toString());
  }
}
