package com.kmzyc.b2b.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ognl.DefaultTypeConverter;

public class EnumerationConverter extends DefaultTypeConverter {

  // private static final Log log = LogFactory.getLog(EnumerationConverter.class);
  private static Logger log = LoggerFactory.getLogger(EnumerationConverter.class);

  @Override
  public Object convertValue(Map context, Object value, Class toType) {
    if (toType.isEnum()) {
      log.info("" + toType.getSuperclass());
      log.info("convertValue: " + value + " => " + toType);
      if (value == null) {
        return null;
      }
      if (value instanceof String[]) {
        String[] ss = (String[]) value;
        if (ss.length == 1) {
          return Enum.valueOf(toType, ss[0]);
        } else {
          Object[] oo = new Object[ss.length];
          for (int i = 0; i < ss.length; i++) {
            oo[i] = Enum.valueOf(toType, ss[i]);
          }
          return oo;
        }
      }
    }
    return super.convertValue(context, value, toType);
  }

}
