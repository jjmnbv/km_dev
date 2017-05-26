package com.pltfm.app.util;

import org.apache.log4j.Logger;

import java.math.BigDecimal;

/**
 * 通过javascript使其字符串表达式变为java计算表达式
 * 
 * @author zhl
 * @since 2013-08-13
 * 
 */
public class JavaScriptExpress {
  private static final Logger logger = Logger.getLogger(JavaScriptExpress.class);

  public static BigDecimal calcExpress(String scoreexpress, BigDecimal amount) {
    BigDecimal result = null;
    try {
      if (null != scoreexpress) {
        if (null != amount) {
          if (scoreexpress.indexOf('*') > 0) {
            result = amount.multiply(new BigDecimal(scoreexpress.replace("A*", "")));
          } else if (scoreexpress.indexOf('+') > 0) {
            result = amount.add(new BigDecimal(scoreexpress.replace("A+", "")));
          } else if (scoreexpress.indexOf('/') > 0) {
            result = amount.divide(new BigDecimal(scoreexpress.replace("A/", "")), 4,
                BigDecimal.ROUND_HALF_UP);
          } else if (scoreexpress.indexOf('-') > 0) {
            result = amount.subtract(new BigDecimal(scoreexpress.replace("A-", "")));
          } else {
            result = new BigDecimal(scoreexpress);
          }
        } else {
          result = new BigDecimal(scoreexpress);
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return result;
  }

  public static BigDecimal calcExpress(String scoreexpress, String amountStr) {
    BigDecimal result = null;
    try {
      if (null != scoreexpress) {
        if (null != amountStr) {
          BigDecimal amount = new BigDecimal(amountStr);
          if (scoreexpress.indexOf('*') > 0) {
            result = amount.multiply(new BigDecimal(scoreexpress.replace("A*", "")));
          } else if (scoreexpress.indexOf('+') > 0) {
            result = amount.add(new BigDecimal(scoreexpress.replace("A+", "")));
          } else if (scoreexpress.indexOf('/') > 0) {
            result = amount.divide(new BigDecimal(scoreexpress.replace("A/", "")), 4,
                BigDecimal.ROUND_HALF_UP);
          } else if (scoreexpress.indexOf('-') > 0) {
            result = amount.subtract(new BigDecimal(scoreexpress.replace("A-", "")));
          } else {
            result = new BigDecimal(scoreexpress);
          }
        } else {
          result = new BigDecimal(scoreexpress);
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return result;
  }
}
