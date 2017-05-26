package com.kmzyc.b2b.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.kmzyc.b2b.model.PromotionInfo;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;

/**
 * 促销活动工具类
 * 
 * @author hewenfeng
 * 
 */
public class PromotionInfoUtil {
  public static final String DO_NOT_STACK = "all";
  public static Map<Integer, String> typeNameMap = PromotionInfoUtil.getTypenamemap();
  public static final Map<Integer, PromotionTypeEnums> promotionTypeEnumsMap =
      PromotionInfoUtil.getPromotionTypeEnumsMap();

  // public static SimpleDateFormat sdf = new
  // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  // public static PromotionInfo toPromotionInfo(Promotion promotion){
  // return null;
  // if(promotion==null)
  // return null;
  // PromotionInfo info = new PromotionInfo();
  // info.setPromotionName(promotion.getPromotionName());
  // info.setPromotionInfoId(promotion.getPromotionId());
  // info.setPriority(promotion.getPromotionPriority());
  // info.setPromotionType(promotion.getPromotionTypeId());
  // info.setRuleId(promotion.getPromotionRuleId());
  // info.setTitle(promotion.getSlogan());
  // info.setStartTime(promotion.getStartTime());
  // info.setEndTime(promotion.getEndTime());
  // info.setMutualIds(promotion.getMutexPromotionId());
  // info.setProductFilterType(promotion.getProductFilterType());
  // info.setPromotionTypeName(typeNameMap.get(promotion.getPromotionTypeId()));
  // return info;
  // }

  public static JSONArray toJsonArray(List<PromotionInfo> infoList) {
    if (infoList == null || infoList.isEmpty()) {
      return null;
    }
    JSONArray jsonArray = new JSONArray();
    for (PromotionInfo p : infoList) {
      if (p == null) continue;
      jsonArray.add(p.toJsonObject());
    }
    return jsonArray;
  }

  public static Map<Integer, String> getTypenamemap() {
    if (typeNameMap == null) {
      typeNameMap = getPromotionTypeMap();
    }
    return typeNameMap;
  }

  public static Map<Integer, String> getPromotionTypeMap() {
    Map<Integer, String> map = new HashMap<Integer, String>();
    PromotionTypeEnums[] ts = PromotionTypeEnums.values();
    for (PromotionTypeEnums pt : ts) {
      map.put(pt.getValue(), pt.getTitle());
    }
    return map;
  }

  public static Map<Integer, PromotionTypeEnums> getPromotionTypeEnumsMap() {
    Map<Integer, PromotionTypeEnums> map = new HashMap<Integer, PromotionTypeEnums>();
    PromotionTypeEnums[] ts = PromotionTypeEnums.values();
    for (PromotionTypeEnums pt : ts) {
      map.put(pt.getValue(), pt);
    }
    return map;
  }

    public static PromotionInfo toPromotionInfo(
            com.kmzyc.promotion.app.vobject.PromotionInfo promotion) {
    if (promotion == null) return null;
    PromotionInfo info = new PromotionInfo();
    info.setPromotionName(promotion.getPromotionName());
    info.setPromotionInfoId(promotion.getPromotionId());
    // info.setPriority(promotion.getPriority());
    info.setPromotionType(promotion.getPromotionType());
    info.setTitle(promotion.getPromotionTitle());
    info.setStartTime(promotion.getStartTime());
    info.setEndTime(promotion.getEndTime());
    info.setMutualIds(promotion.getMutualIds());
    info.setProductFilterType(promotion.getProductFilterType());
    info.setPrivilegeData(promotion.getPrivilegeData());
    info.setMeetData(promotion.getMeetData());
    info.setPromotionTypeName(typeNameMap.get(promotion.getPromotionType()));
    info.setPromotionData(promotion.getPromotionData());
    return info;
  }

  public static List<PromotionInfo> toPromotionInfo(
            List<com.kmzyc.promotion.app.vobject.PromotionInfo> promotionList) {
    if (promotionList == null) return null;
    if (promotionList.isEmpty()) return null;
    List<PromotionInfo> infoList = new ArrayList<PromotionInfo>();
        for (com.kmzyc.promotion.app.vobject.PromotionInfo promotion : promotionList) {
      PromotionInfo info = toPromotionInfo(promotion);
      infoList.add(info);
    }
    return infoList;
  }

  public static List<PromotionInfo> sortPromotionInfoList(List<PromotionInfo> list) {
    if (list == null || list.isEmpty()) {
      return null;
    }
    Collections.sort(list);
    return list;
  }
}
