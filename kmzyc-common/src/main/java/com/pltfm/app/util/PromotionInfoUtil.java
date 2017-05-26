package com.pltfm.app.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.pltfm.app.enums.PromotionTypeEnums;
import com.pltfm.app.vobject.Promotion;
import com.pltfm.app.vobject.PromotionInfo;

public class PromotionInfoUtil {
    public static final String DO_NOT_STACK = "all";
    public static final Map<Integer, String> typeNameMap = PromotionInfoUtil.getPromotionTypeMap();
    private static Map<Short, String> shopTypeMap = null;

    public static Map<Short, String> getShopTypeMap() {
        // TODO Auto-generated method stub
        if (shopTypeMap != null) {
            return shopTypeMap;
        }
        // 1自营；2入驻； 3代销
        Map<Short, String> shopTypeMap = new HashMap<Short, String>();
        shopTypeMap.put(Short.valueOf("1"), "自营");
        shopTypeMap.put(Short.valueOf("2"), "入驻");
        shopTypeMap.put(Short.valueOf("3"), "代销");
        return shopTypeMap;
    }

    /**
     * 活动可向后叠加
     * 
     * @param promotion
     * @return
     */
    public static boolean getDoStack(Promotion promotion) {
        return promotion.getMutexPromotionId() == null
                || !promotion.getMutexPromotionId().equals(DO_NOT_STACK);
    }

    public static PromotionInfo getMainPromotionInfo(List<PromotionInfo> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        list = sort(list);
        return list.get(0);
    }

    // public static List<Promotion> getSortPromotion(List<Promotion> list){
    // if(list==null||list.isEmpty()){
    // return null;
    // }
    // Collections.sort(list);
    // return list;
    // }
    public static String toJson(List<PromotionInfo> infoList) {
        JSONArray array = toJsonArray(infoList);
        return array.toJSONString();
    }

    public static JSONArray toJsonArray(List<PromotionInfo> infoList) {
        JSONArray jsonArray = new JSONArray();
        for (PromotionInfo p : infoList) {
            jsonArray.add(p.toJsonObject());
        }
        return jsonArray;
    }

    public static Map<Integer, String> getPromotionTypeMap() {
        // TODO Auto-generated method stub
        Map<Integer, String> map = new HashMap<Integer, String>();
        PromotionTypeEnums[] ts = PromotionTypeEnums.values();
        for (PromotionTypeEnums pt : ts) {
            map.put(pt.getValue(), pt.getTitle());
        }
        return map;
    }

    public static List<Date> subsectionPromotionDate(List<PromotionInfo> list) {
        List<Date> dateList = new ArrayList<Date>();
        Date now = new Date();
        for (PromotionInfo promotion : list) {
            if (promotion.getEndTime().before(now)) {
                continue;
            }
            dateList.add(promotion.getStartTime());
            dateList.add(promotion.getEndTime());
        }
        Collections.sort(dateList);
        // map.put("date", dateList);
        return dateList;
    }

    public static boolean isBetween(Promotion promotion, Date date) {
        return promotion.getStartTime().before(date) && promotion.getEndTime().after(date);
    }

    public static List<PromotionInfo> sort(List<PromotionInfo> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        Collections.sort(list);
        return list;
    }

    public static String getValidIds(String mutexPromotionId) {
        if (mutexPromotionId == null) {
            return null;
        }
        if (mutexPromotionId.startsWith(",")) {
            mutexPromotionId = mutexPromotionId.substring(1, mutexPromotionId.length());
        }
        if (mutexPromotionId.endsWith(",")) {
            mutexPromotionId = mutexPromotionId.substring(0, mutexPromotionId.length() - 1);
        }
        mutexPromotionId = mutexPromotionId.replaceAll("(,{2,10})", ",");
        return mutexPromotionId;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]+(.?[0-9])*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static void main(String[] args) {
        String abd = "90";
        // System.out.println(isNumeric(abd));
    }
}
