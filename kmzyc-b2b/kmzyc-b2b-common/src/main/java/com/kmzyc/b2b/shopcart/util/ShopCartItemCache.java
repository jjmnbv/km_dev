package com.kmzyc.b2b.shopcart.util;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.collect.Maps;
import com.kmzyc.b2b.shopcart.vo.ShopCartItem;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.optimization.vo.Promotion;

/**
 * 存放了用户购物车的item，只保存用于缓存保存
 */
public class ShopCartItemCache {

    private Map<String, String> cacheMap = Maps.newHashMap();
    private Map<String, String> saveCacheMap = Maps.newHashMap();
    private Map<String, ShopCartItem> shopCartItemMap = Maps.newHashMap();

    private transient boolean needSave = false;

    public ShopCartItemCache() {
    }

    public ShopCartItemCache(Map<String, String> cacheMap) {
        if (cacheMap != null) {
            this.cacheMap = cacheMap;
        }
    }


    public final static SimplePropertyPreFilter filter = new SimplePropertyPreFilter(
            ShopCartItem.class, "id", "ruleGifts", "meet", "giftCount", "meetData", "rulePresents",
            "additionalMoney", "gifts", "presents", "countChoosed", "countPresents");

    /**
     * 添加shopItem
     *
     * @param addCache 是否更新至缓存
     */
    public void add(ShopCartItem shopcartItem, boolean addCache) {
        String id = shopcartItem.getId();
        shopCartItemMap.put(id, shopcartItem);
        if (addCache) {

            if (shopcartItem.getPromtionInfo() != null &&
                    (shopcartItem.getPromtionInfo().getType() ==
                            PromotionTypeEnums.GIFT.getValue() ||
                            shopcartItem.getPromtionInfo().getType() ==
                                    PromotionTypeEnums.INCREASE.getValue())) {
                // 只是满赠和加价购活动存缓存
                String newValue = JSONObject.toJSONString(shopcartItem, filter,
                        SerializerFeature.DisableCircularReferenceDetect);
                String oldValue = cacheMap.get(id);
                if (!newValue.equals(oldValue)) {
                    // System.out.println("--------------------------------------");
                    // System.out.println("newValue:" + newValue);
                    // System.out.println("oldValue:" + oldValue);
                    saveCacheMap.put(id, newValue);
                    needSave = true;
                } else if (!oldValue.equals("")) {
                    saveCacheMap.put(id, oldValue);
                }

            } else {
                needSave = true;
            }
        }
    }

    public ShopCartItem get(String id) {
        ShopCartItem item = shopCartItemMap.get(id);
        if (item == null) {
            String shopCartItemStr = cacheMap.get(id);
            item = JSONObject.parseObject(shopCartItemStr, ShopCartItem.class);
            if (item == null) {
                return null;
            }
            shopCartItemMap.put(id, item);
        }
        Promotion promotion = item.getPromtionInfo();
        if (promotion != null && promotion.getEndTime().before(new Date())) {
            // cacheMap.remove(id);
            saveCacheMap.remove(id);
            needSave = true;
            // shopCartItemMap.remove(id);
            // return item;
        }

        return item;
    /*
     * ShopCartItem item = JSONObject.parseObject(shopCartItemStr, ShopCartItem.class); Promotion
     * promotion = item.getPromtionInfo(); if (promotion != null &&
     * promotion.getEndTime().before(new Date())) { cacheMap.remove(id); return null; } return item;
     */
    }


    public Map<String, ShopCartItem> getShopCartItemMap() {
        return shopCartItemMap;
    }


    public Iterator<String> iterator() {
        if (shopCartItemMap == null) {
            return null;
        }
        return shopCartItemMap.keySet().iterator();
    }

    public boolean isNeedSave() {
        return needSave;
    }

    public Map<String, String> getSaveCacheMap() {
        return saveCacheMap;
    }


}
