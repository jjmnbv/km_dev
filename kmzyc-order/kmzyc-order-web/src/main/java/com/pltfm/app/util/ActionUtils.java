package com.pltfm.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.pltfm.app.entities.SellerSettlementCriteria;

@SuppressWarnings("unchecked")
public class ActionUtils {

  /**
   * 此类用于Action类中常用方法集成
   */

  /**
   * 用于从前台获取查询条件，并将查询条件放置于Map形式的查询条件集合中 并将非空的查询条件放入request中
   * 
   * @param request
   * @param map
   * @param strs jsp页面查询条件name值数组
   */
  public static void putValueIntoMap(HttpServletRequest request, Map map, String[] strs) {
    if (null == strs) {
      return;
    }
    for (String s : strs) {
      String str = request.getParameter(s);
      if (StringUtils.isNotBlank(str)) {
        map.put(s, str.trim());
        request.setAttribute(s, str);
      }
    }
  }

  public static void putValueIntoMap(HttpServletRequest request, SellerSettlementCriteria criteria,
      String[] strs) {
    if (null == strs) {
      return;
    }
    for (String s : strs) {
      String str = request.getParameter(s);
      if (StringUtils.isNotBlank(str)) {
        str = str.trim();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (s.equals("_settlementNo")) {
          criteria.setSettlementNo(str);
        }
        if (s.equals("_sellerName")) {
          criteria.setSellerName(str);
        }
        if (s.equals("_startDate")) {
          Date start = null;
          try {
            start = format.parse(str);
          } catch (ParseException e) {
            e.printStackTrace();
          }
          criteria.setStartDate(start);
        }
        if (s.equals("_endDate")) {
          Date end = null;
          try {
            end = format.parse(str);
          } catch (ParseException e) {
            e.printStackTrace();
          }
          criteria.setEndDate(end);
        }
        if (s.equals("_settlementStatus")) {
          criteria.setSettlementStatus(Short.parseShort(str));
        }
        if (s.equals("_sellerId")) {
          criteria.setSellerId(str);
        }
        request.setAttribute(s, str);
      }
    }
  }
}
