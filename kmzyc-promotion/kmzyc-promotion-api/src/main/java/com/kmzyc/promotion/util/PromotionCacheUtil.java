package com.kmzyc.promotion.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.constant.PromotionConstant;
import com.kmzyc.promotion.optimization.vo.ProductPromotion;
import com.kmzyc.promotion.optimization.vo.Promotion;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;

import redis.clients.jedis.JedisCluster;

public class PromotionCacheUtil {

    @Resource
    private JedisCluster jedisCluster;

    private static Map<String, String> promotionTypeMap = null;

    /**
     * 序列化对象
     */
    public static byte[] serialize(Object obj) throws IOException {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } finally {
            if (oos != null)
                oos.close();
            if (baos != null)
                baos.close();
        }
    }

    /**
     * 反序列化对象
     */
    public static Object unserialize(byte[] bytes) throws Exception {
        if (bytes == null || 0 == bytes.length)
            return null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } finally {
            if (ois != null)
                ois.close();
            if (bais != null)
                bais.close();
        }
    }



    /**
     * 获取单个商品促销信息
     * 
     * @param skuId
     * @return
     */
    public ProductPromotion getProductPromtoionInfoCahce(String skuId) {

        return getProductPromtoionInfoFromCahce(skuId, jedisCluster);
    }

    private ProductPromotion getProductPromtoionInfoFromCahce(String skuId,
            final JedisCluster jedisCluster) {
        // ProductPromotion pp = null;
        String key = PromotionConstant.PRODUCT_PROMOTION_INFO + skuId;
        Date now = new Date();
        Set<String> promotionValues =
                jedisCluster.zrangeByScore(key, now.getTime(), Double.MAX_VALUE);
        if (StringUtil.isEmpty(promotionValues)) {
            return null;
        }
        ProductPromotion pp =
                getProductPromotionFromRedisCache(skuId, jedisCluster, now, promotionValues);
        return pp;
    }

    private ProductPromotion getProductPromotionFromRedisCache(String skuId,
            final JedisCluster jedisCluster, Date now, Set<String> promotionValues) {

        String channel = UserChannelContextHolder.getUserChannel();

        ProductPromotion pp = new ProductPromotion();
        for (Iterator<String> iterator = promotionValues.iterator(); iterator.hasNext();) {
            final String promotionId = iterator.next();
            if (StringUtil.isEmpty(promotionId)) {
                continue;
            } ;
            if (!jedisCluster.exists(PromotionConstant.PROMOTION + promotionId)) {
                continue;
            }
            Promotion promotion = getPromotionInfoFormLocalCache(jedisCluster, promotionId);
            Date startTime = promotion.getStartTime();
            if (startTime.after(now)) {// 活动还未开始
                continue;
            }
            int type = promotion.getType();
            pp.setType(type);
            if (type == PromotionTypeEnums.SALE.getValue()
                    || type == PromotionTypeEnums.DISCOUNT.getValue()) {
                if (pp.getPricePromotion() == null
                        || pp.getPricePromotion().compareTo(promotion) > 0) {
                    pp.setPricePromotion(promotion);
                }
            } else if (type == PromotionTypeEnums.COUPON.getValue()
                    || type == PromotionTypeEnums.GIFT.getValue()
                    || type == PromotionTypeEnums.INCREASE.getValue()
                    || type == PromotionTypeEnums.REDUCTION.getValue()) {
                pp.addOrderPromotion(promotion);
            } else if (type == PromotionTypeEnums.GANT.getValue()) {



                // mkw 20151118 增加附赠活动
                List<PromotionProductData> gantList =
                        this.getProductGant(jedisCluster, promotion, skuId);
                if (null != gantList && gantList.size() > 0) {
                    pp.setAffixPromoiton(promotion);
                    pp.addAffixProductList(gantList);
                }
                // end
            } else if (type == PromotionTypeEnums.SALE_APP.getValue()) {
                // mkw 20160315 增加app特价活动
                if (pp.getPricePromotionApp() == null
                        || pp.getPricePromotionApp().compareTo(promotion) > 0) {
                    pp.setPricePromotionApp(promotion);
                }
                // end
            }
        }
        List<Promotion> allPromotionList = new LinkedList<Promotion>();
        if (pp != null) {
            // mkw add 20160317 增加APP专享活动
            if (pp.getPricePromotion() != null || pp.getPricePromotionApp() != null) {// 设置 特价打折限购
                setRestrictionInfo(skuId, jedisCluster, pp);
                setRestrictionInfoBySellUpType(skuId, jedisCluster, pp, channel);
                setPromotionAssistInfo(skuId, jedisCluster, pp.getPricePromotion());
                setPromotionAssistInfo(skuId, jedisCluster, pp.getPricePromotionApp());
            }

            List<Promotion> list = pp.getSortedOrderPromtotions();
            if (list != null) {
                Collections.sort(list);
                pp.setSortedOrderPromtotions(list);
            }
            // mkw 20151118 附赠
            if (pp.getAffixPromoiton() != null) {
                allPromotionList.add(pp.getAffixPromoiton());
            }
            // end

            if (PromotionConstant.CHANNEL_APP.equals(channel)) {
                if (pp.getPricePromotionApp() != null) {
                    allPromotionList.add(pp.getPricePromotionApp());
                } else {
                    if (pp.getPricePromotion() != null) {
                        allPromotionList.add(pp.getPricePromotion());
                    }
                }
            } else {
                if (pp.getPricePromotion() != null) {
                    allPromotionList.add(pp.getPricePromotion());
                }
                if (pp.getPricePromotionApp() != null) {
                    allPromotionList.add(pp.getPricePromotionApp());
                }
            }

            if (pp.getSortedOrderPromtotions() != null) {
                allPromotionList.addAll(pp.getSortedOrderPromtotions());
            }
            if (!allPromotionList.isEmpty()) {
                StringBuilder lebal = new StringBuilder();
                for (Promotion p : allPromotionList) {
                    String tag = p.getTypeName();
                    if (lebal.indexOf(tag) < 0) {
                        lebal.append(",").append(tag);
                    }
                }
                pp.setAllPromtotions(allPromotionList);
                String lebalStr = lebal.substring(1, lebal.length());
                pp.setLabelArray(lebalStr.split(","));
            } else {
                return null;
            }
        }
        return pp;
    }

    private static Cache<String, Promotion> cache = CacheBuilder.newBuilder().maximumSize(500)
            .weakValues().expireAfterWrite(2, TimeUnit.MINUTES).build();

    private Promotion getPromotionInfoFormLocalCache(final JedisCluster jedisCluster,
            final String promotionId) {
        Promotion promotion = null;
        try {
            promotion = cache.get(promotionId, new Callable<Promotion>() {
                @Override
                public Promotion call() throws Exception {

                    return getPromotionFromReids(promotionId, jedisCluster);
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (promotion != null && (promotion.getType() == PromotionTypeEnums.SALE.getValue()
                || promotion.getType() == PromotionTypeEnums.DISCOUNT.getValue()
                || promotion.getType() == PromotionTypeEnums.SALE_APP.getValue())) {

            return getPromotionFromReids(promotionId, jedisCluster);
        }

        return promotion;
    }

    private Promotion buildPromotion(JSONObject json) {
        Long promotionId = json.getLongValue("id");
        Integer type = json.getInteger("type");
        String name = json.getString("name");
        Date endTime = json.getDate("endTime");
        Date startTime = json.getDate("startTime");
        Integer level = json.getInteger("level");
        BigDecimal gantFilterType = json.getBigDecimal("gantFilterType");
        @SuppressWarnings("unchecked")
        List<JSONObject> ruleDataAndPrizeEntityInfo =
                (List<JSONObject>) json.get("ruleDataAndPrizeEntityInfo");
        BigDecimal data = json.getBigDecimal("data");
        Promotion p = new Promotion();
        p.setId(promotionId);
        p.setName(name);
        p.setEndTime(endTime);
        p.setLevel(level);
        p.setType(type);
        p.setData(data);
        p.setStartTime(startTime);
        p.setGantFilterType(gantFilterType);
        p.setRuleDataAndPrizeEntityInfo(ruleDataAndPrizeEntityInfo);
        return p;
    }

    private Promotion getPromotionFromReids(String promotionId, JedisCluster jedisCluster) {
        String promotionInfo = jedisCluster.get(PromotionConstant.PROMOTION + promotionId);
        JSONObject json = JSONObject.parseObject(promotionInfo);
        Promotion p = buildPromotion(json);
        return p;
    }

    /**
     * 根据渠道号判断并设置产品活动的限购信息
     * 
     * @param skuId
     * @param jedis
     * @param pp
     */
    private void setRestrictionInfo(String skuId, JedisCluster jedisCluster, ProductPromotion pp) {

        // 判断是否为app调用，如果为app调用则返回app专享价）
        Promotion pricePromotion = pp.getPricePromotionForChannel();
        if (pricePromotion == null) {
            return;
        }
        Long promotionId = pricePromotion.getId();
        String restrictionInfoKey =
                PromotionConstant.PRODUCT_PROMOTION_RESTRICTION_INFO + promotionId + "_" + skuId;
        Map<String, String> value = jedisCluster.hgetAll(restrictionInfoKey);
        if (!StringUtil.isEmpty(value)) {
            String max = value.get("max");
            String min = value.get("min");
            String type = value.get("sellUpType");
            String salePrice = value.get("salePrice");
            String availableQuantity = value.get("availableQuantity");
            if (!StringUtil.isEmpty(max)) {
                pp.setMax(Integer.valueOf(max));
            }
            if (!StringUtil.isEmpty(min)) {
                pp.setMin(Integer.valueOf(min));
            }
            if (!StringUtil.isEmpty(availableQuantity)) {
                pp.setAvailableQuantity(Integer.valueOf(availableQuantity));
            }
            if (!StringUtil.isEmpty(salePrice)) {
                pp.setData(BigDecimal.valueOf(Double.valueOf(salePrice)));
            }
            if (!StringUtil.isEmpty(type)) {
                pp.setSellUpType(Integer.valueOf(type));
            }
        }
    }

    /**
     * 设置产品所参与的活动的其他信息
     * 
     * @param promotion
     */
    public void setPromotionAssistInfo(String skuId, JedisCluster jedisCluster,
            Promotion promotion) {

        Map<String, String> map =
                getProductPromotionRestrictionInfo(skuId, jedisCluster, promotion);
        if (map == null || map.isEmpty()) {
            return;
        }
        String salePrice = map.get("salePrice");
        String availableQuantity = map.get("availableQuantity");
        String type = map.get("sellUpType");
        if (!StringUtil.isEmpty(salePrice)) {
            promotion.setData(BigDecimal.valueOf(Double.valueOf(salePrice)));
        }
        if (!StringUtil.isEmpty(availableQuantity)) {
            promotion.setAvailableQuantity(Integer.valueOf(availableQuantity));
        }
        if (!StringUtil.isEmpty(type)) {
            promotion.setSellUpType(Integer.valueOf(type));
        }
    }

    /**
     * 获取商品对应活动的限购信息
     * 
     * @param skuId
     * @param jedis
     * @param promotion
     * @return
     */
    public Map<String, String> getProductPromotionRestrictionInfo(String skuId,
            JedisCluster jedisCluster, Promotion promotion) {
        if (StringUtil.isEmpty(skuId) || promotion == null) {
            return null;
        }
        String restrictionInfoKey = PromotionConstant.PRODUCT_PROMOTION_RESTRICTION_INFO
                + promotion.getId() + "_" + skuId;
        return jedisCluster.hgetAll(restrictionInfoKey);
    }

    /**
     * 根据渠道号及活动库存卖光后的类型设置限购信息
     * 
     * @param skuId
     * @param jedis
     * @param pp
     */
    public void setRestrictionInfoBySellUpType(String skuId, JedisCluster jedisCluster,
            ProductPromotion pp, String channel) {

        // 活动生效 没有活动可用量
        if (pp.getAvailableQuantity() != null && pp.getAvailableQuantity() <= 0) {
            if (pp.getSellUpType() == 2) { // 活动库存（如有）卖光后恢复原价销售

                pp.setMax(null);
                pp.setMin(null);
                pp.setSellUpType(null);
                pp.setAvailableQuantity(null);

                // 如果渠道号为APP且有普通特价则使用普通特价
                if (PromotionConstant.CHANNEL_APP.equals(channel)) {
                    pp.setPricePromotionApp(null);
                    // 重设活动限购信息,如果有普通特价则取普通特价数据
                    setRestrictionInfo(skuId, jedisCluster, pp);

                    // 如果普通特价活动库存也为空的情况
                    if (pp.getAvailableQuantity() != null && pp.getAvailableQuantity() <= 0) {
                        if (pp.getSellUpType() == 2) { // 活动库存（如有）卖光后恢复原价销售

                            pp.setMax(null);
                            pp.setMin(null);
                            pp.setSellUpType(null);
                            pp.setAvailableQuantity(null);
                            pp.setPricePromotion(null);
                        }
                    }

                } else {
                    pp.setPricePromotion(null);
                }

            }
        }

        if (!PromotionConstant.CHANNEL_APP.equals(channel)) {
            if (pp.getPricePromotionApp() != null
                    && pp.getPricePromotionApp().getAvailableQuantity() != null
                    && pp.getPricePromotionApp().getAvailableQuantity() <= 0) {
                pp.setPricePromotionApp(null);
            }
        }

    }

    /** 批量获取商品促销信息 */
    public Map<String, ProductPromotion> getProductPromtoionInfoCahce(Set<String> skuIds) {
        Map<String, ProductPromotion> map = new HashMap<String, ProductPromotion>();
        if (StringUtil.isEmpty(skuIds)) {
            return map;
        }
        Iterator<String> iterator = skuIds.iterator();
        while (iterator.hasNext()) {
            String skuId = iterator.next();
            ProductPromotion pp = null;
            pp = getProductPromtoionInfoFromCahce(skuId, jedisCluster);
            if (pp != null) {
                map.put(skuId, pp);
            }
        }

        return map;
    }

    /**
     * 批量获取商品促销信息 ，根据活动传入的时间
     */
    public Map<String, ProductPromotion> getProductPromtoionInfoCahceByDate(Set<String> skuIds,
            Date promotionDate) {
        Map<String, ProductPromotion> map = new HashMap<String, ProductPromotion>();
        if (StringUtil.isEmpty(skuIds)) {
            return map;
        }
        Iterator<String> iterator = skuIds.iterator();
        while (iterator.hasNext()) {
            String skuId = iterator.next();
            ProductPromotion pp = null;
            String key = PromotionConstant.PRODUCT_PROMOTION_INFO + skuId;
            Set<String> promotionValues =
                    jedisCluster.zrangeByScore(key, promotionDate.getTime(), Double.MAX_VALUE);
            if (StringUtil.isEmpty(promotionValues)) {
                continue;
            }
            pp = getProductPromotionFromRedisCache(skuId, jedisCluster, promotionDate,
                    promotionValues);
            if (pp != null) {
                map.put(skuId, pp);
            }
        }

        return map;
    }

    public JSONObject getPromotionCache(String promotionId) {
        return JSONObject.parseObject(jedisCluster.get(PromotionConstant.PROMOTION + promotionId));
    }

    public Set<String> getPromotionRuleCache(String promotionId, Double meetDate) {
        return null;
    }

    public static String getPromotionTagTitle(int type) {
        if (promotionTypeMap != null) {

            return promotionTypeMap.get(type + "");
        }

        synchronized (PromotionTypeEnums.class) {
            promotionTypeMap = new HashMap<String, String>();
            PromotionTypeEnums[] t = PromotionTypeEnums.values();
            for (int i = 0; i < t.length; i++) {
                promotionTypeMap.put(t[i].getValue().toString(), t[i].getTitle());
            }
        }

        return promotionTypeMap.get(type + "");
    }

    @Deprecated
    public boolean updateProductOrderQuantityCache(Collection<Map<String, Object>> collection) {
        Iterator<Map<String, Object>> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> map = iterator.next();
            String promotionId = (String) map.get("promotionId");
            String skuId = (String) map.get("skuId");
            Integer count = (Integer) map.get("count");
            String key = PromotionConstant.PRODUCT_PROMOTION_RESTRICTION_INFO + promotionId + "_"
                    + skuId;
            if (jedisCluster.hexists(key, PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD)) {
                long newCount = jedisCluster.hincrBy(key,
                        PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD, count);
                String stock =
                        jedisCluster.hget(key, PromotionConstant.PRODUCT_PROMOTION_STOCK_FIELD);
                long stockLong = Long.valueOf(stock).longValue();
                if (stockLong < newCount) {
                    jedisCluster.hset(key, PromotionConstant.PRODUCT_PROMOTION_STOCK_FIELD, stock);
                }
            }

            if (jedisCluster.hexists(key, PromotionConstant.PRODUCT_PROMOTION_ALREADY_SELL)) {
                // mkw add 20151207
                jedisCluster.hincrBy(key, PromotionConstant.PRODUCT_PROMOTION_ALREADY_SELL,
                        count * -1);
                // end
            }
        }
        // TODO 更新数据库
        return true;
    }


    /** count为负数表示扣减活动库存,count为正数表示增加活动库存 */
    public boolean updateProductOrderQuantityCache(String skuId, String promotionId, int count) {
        String key =
                PromotionConstant.PRODUCT_PROMOTION_RESTRICTION_INFO + promotionId + "_" + skuId;
        try {
            if (jedisCluster.hexists(key, PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD)) {
                long newCount = jedisCluster.hincrBy(key,
                        PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD, count);
                String stock =
                        jedisCluster.hget(key, PromotionConstant.PRODUCT_PROMOTION_STOCK_FIELD);
                if (count > 0) {
                    long stockLong = Long.valueOf(stock).longValue();
                    if (stockLong < newCount) {
                        jedisCluster.hset(key, PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD,
                                stock);
                    }
                }
            }

            if (jedisCluster.hexists(key, PromotionConstant.PRODUCT_PROMOTION_ALREADY_SELL)) {
                // mkw add 20151207
                jedisCluster.hincrBy(key, PromotionConstant.PRODUCT_PROMOTION_ALREADY_SELL,
                        count * -1);
                // end

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // TODO 更新数据库
        return true;
    }

    /** 获取活动限购信息，availableQuantity为活动可用量，max为每个会员最多可购买量 */
    public Map<String, Integer> getProductRestrictionQuantity(String skuId, String promotionId) {
        String key =
                PromotionConstant.PRODUCT_PROMOTION_RESTRICTION_INFO + promotionId + "_" + skuId;
        List<String> restrictionQuantity =
                jedisCluster.hmget(key, PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD, "max");
        if (null == restrictionQuantity || restrictionQuantity.isEmpty()) {
            return null;
        }
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (restrictionQuantity.get(0) != null) {
            Integer quantity = Integer.valueOf(restrictionQuantity.get(0));
            map.put(PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD, quantity);
        }
        if (restrictionQuantity.get(1) != null) {
            Integer quantity = Integer.valueOf(restrictionQuantity.get(1));
            map.put("max", quantity);
        }
        return map;
    }

    // 附赠商品缓存
    private static Cache<String, List<PromotionProductData>> gantcache = CacheBuilder.newBuilder()
            .maximumSize(200).weakValues().expireAfterWrite(2, TimeUnit.MINUTES).build();

    /**
     * 获取商品附赠信息
     * 
     * @param promotionId
     * @param skuId
     * @param gantFilterType -1:统一赠品，否则为指定赠品
     * @return
     */
    public List<PromotionProductData> getProductGant(final JedisCluster jedisCluster,
            final Promotion promotion, String skuId) {
        List<PromotionProductData> list = null;

        try {

            String key = "";
            if (promotion.getGantFilterType() == null
                    || promotion.getGantFilterType().compareTo(BigDecimal.valueOf(-1)) != 0) {
                // 商品筛选类型为 指定商品
                key = PromotionConstant.PRODUCT_PROMOTION_GANT_INFO + promotion.getId() + "_"
                        + skuId;
            } else {
                key = PromotionConstant.PROMOTION + promotion.getId();
            }

            final String str = jedisCluster.get(key);
            if (!StringUtil.isEmpty(str)) {
                list = gantcache.get(key, new Callable<List<PromotionProductData>>() {
                    @SuppressWarnings("finally")
                    @Override
                    public List<PromotionProductData> call() throws Exception {
                        List<PromotionProductData> list = null;
                        try {

                            if (promotion.getGantFilterType() == null || promotion
                                    .getGantFilterType().compareTo(BigDecimal.valueOf(-1)) != 0) {
                                list = JSONArray.parseArray(str, PromotionProductData.class);
                            } else {
                                list = JSONArray.parseArray(
                                        JSONObject.parseObject(str)
                                                .getString("ruleDataAndPrizeEntityInfo"),
                                        PromotionProductData.class);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            return list != null ? list : new ArrayList<PromotionProductData>();
                        }
                    }
                });
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return list != null ? list : new ArrayList<PromotionProductData>();
    }
}
