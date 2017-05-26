package com.kmzyc.b2b.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.service.ProductSkuService;
import com.kmzyc.b2b.util.TagsUtil;
import com.kmzyc.b2b.vo.BaseProduct;
import com.kmzyc.promotion.optimization.vo.ProductPromotion;
import com.kmzyc.promotion.util.PromotionCacheUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

@Service("productPriceService")
public class ProductPriceServiceImpl implements ProductPriceService {

    /**
     * 渠道
     */
    // private static String channel = ConfigurationUtil.getString("CHANNEL");
    @Resource
    private PromotionCacheUtil promotionCacheUtil;
    @Resource
    private ProductSkuService productSkuService;
    @Resource
    private TagsUtil tagsUtil;

    /**
     * 批量获取商品促销价格和促销标签
     * 
     * @param list
     * @return
     * @throws Exception
     */
    @Override
    public <T extends BaseProduct> List<T> getPriceBatch(List<T> list, Boolean isGetTag)
            throws Exception {
        if (list == null || list.isEmpty()) {
            return list;
        }
        Set<String> idSet = new HashSet<>();
        List<Long> nullPriceIdList = new ArrayList<>();
        for (T t : list) {
            if (t == null) {
                continue;
            }
            if (isGetTag) {
                Long skuId = t.getProductSkuId();
                List<String> taglist =
                        tagsUtil.getProductTags(skuId.intValue(),
                                ConfigurationUtil.getString("CHANNEL"));
                if (taglist != null && !taglist.isEmpty()) {
                    t.setTag(taglist.get(0));
                }
            }
            idSet.add(t.getProductSkuId().toString());
            t.setFinalPrice(t.getPrice());// 避免finalprice为null
            if (t.getPrice() == null) {
                nullPriceIdList.add(t.getProductSkuId());
            }
        }
        Map<String, BigDecimal> skuPriceMap = new HashMap<>();
        if (!nullPriceIdList.isEmpty()) {
            skuPriceMap = queryProductSkuPriceMap(nullPriceIdList);
        }
        // 批量
        Map<String, ProductPromotion> promotionMap =
                promotionCacheUtil.getProductPromtoionInfoCahce(idSet);
        for (BaseProduct t : list) {
            Long skuId = t.getProductSkuId();
            String skuIdString = skuId.toString();
            BigDecimal price = t.getFinalPrice();
            if (price == null) {
                price = skuPriceMap.get(skuId.toString());
            }
            t.setFinalPrice(price);
            ProductPromotion pp = promotionMap.get(skuIdString);
            if (pp != null) {
                t.setFinalPrice(pp.calculateFinalPrice(price));
                t.setPromotionInfoLebal(pp.getLabelArray());
                // bp.setPromotionStock(pp.get);
                t.setPromoBuyNum(pp.getAvailableQuantity());
                t.setMaxBuy(pp.getMax());
                t.setMinBuy(pp.getMin());
                // bp.setProductPromotion(pp);
                // bp.setPromotionInfoList(pp.getSortedOrderPromtotions());
            }
        }
        // return RedisCacheUtil.getPriceBatch(list, isGetTag, channel);
        return list;

    }

    /**
     * 批量获取商品促销价格,根据活动时间
     * 
     * @param promotionDate
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductSku> getPriceBatchByDate(String skuIds, Date promotionDate) throws Exception {
        Set<String> skuIdSet = new HashSet<>();
        List<ProductSku> productList = new ArrayList<>();
        ProductPromotion promotion;
        Map<String, ProductPromotion> productPromotions;
        List<ProductSku> productSkuList;
        if (skuIds != null && !skuIds.equals("")) {
            // 根据skuID 查询商品列表
            productSkuList = productSkuService.getProductBySkuIds(skuIds);
            // 根据skuID 查询商品列表
            CollectionUtils.addAll(skuIdSet, skuIds.split(","));
            // 批量查询出商品参加活动
            // 不在进行的场次，获取活动时间
            productPromotions =
                    promotionCacheUtil.getProductPromtoionInfoCahceByDate(skuIdSet,
                            promotionDate);
            for (ProductSku p : productSkuList) {
                promotion = productPromotions.get(p.getProductSkuId().toString());
                if (promotion != null) {
                    // 如存在活动 用计算后的价格代替原价格
                    p.setPrice(promotion.calculateFinalPrice(p.getPrice()));
                }
                productList.add(p);
            }
        }
        // return RedisCacheUtil.getPriceBatch(list, isGetTag, channel);
        return productList;

    }

    @Resource
    protected SqlMapClient sqlMapClient;

    @SuppressWarnings("unchecked")
    private Map<String, BigDecimal> queryProductSkuPriceMap(List<?> skuIds) throws SQLException {

        return sqlMapClient.queryForMap("NormalCartProduct.queryProductSkuPriceMap", skuIds,
                "skuId", "price");
    }

    /**
     * 批量获取商品促销价格和促销标签
     * 
     * @param <T>
     * @param list
     * @return
     * @throws Exception
     */
    @Override
    public <T extends BaseProduct> List<T> getPriceBatch(List<T> list) throws Exception {
        return getPriceBatch(list, false);
    }

    @Override
    public Map<String, BigDecimal> getPromotionPricetBySkuIds(Map<String, BigDecimal> map)
            throws Exception {
        Map<String, BigDecimal> resultMap = new HashMap<>();
        if (MapUtils.isNotEmpty(map)) {
            Iterator<Entry<String, BigDecimal>> it = map.entrySet().iterator();
            String skuId;
            BigDecimal price;
            ProductPromotion productPromotion;
            while (it.hasNext()) {
                Entry<String, BigDecimal> entry =  it.next();
                skuId = entry.getKey();
                price = entry.getValue();
                productPromotion =
                        promotionCacheUtil.getProductPromtoionInfoCahce(skuId);
                if (productPromotion != null) {
                    resultMap
                            .put(skuId, productPromotion.calculateFinalPrice(price));
                } else {
                    resultMap.put(skuId, price);
                }
            }
        }
        return resultMap;
    }
}
