package com.kmzyc.promotion.app.service;

import java.util.List;

import com.kmzyc.promotion.app.vobject.ProductStock;
import com.kmzyc.promotion.app.vobject.PromotionInfo;

/**
 * 创建促销商品缓存
 * 
 * @author hewenfeng
 * 
 */
public interface ProductPriceCacheService {
    /**
     * 根据所有活动批量创建缓存
     */
    public void createProductPriceCache() throws Exception;

    /**
     * 更新指定商品缓存
     * 
     * @param productSkuId
     */
    public void createProductPriceCache(Long productSkuId, List<PromotionInfo> infoList)
            throws Exception;

    /**
     * 线程根据活动更新缓存
     * 
     * @param promotionInfoId
     */
    public void notifyByPromotionInfoId(Long promotionInfoId);

    /**
     * 线程根据商品更新缓存
     * 
     * @param promotionInfoId
     */
    public void notifyByProductSkuId(Long productSkuId);

    /** 更新所有商品促销信息缓存 2013-12-6 */
    public void updateAllProductPriceCache() throws Exception;

    /** 创建全场缓存 */
    public void createGlobalProductPriceCache() throws Exception;

    /**
     * 更新活动限购产品已销售数
     * 
     * @param updateList
     * @throws Exception
     */
    public void notifyByProductSkuIdList(List<ProductStock> updateList) throws Exception;

    /**
     * 根据活动更新缓存
     * 
     * @param promotion
     */
    public void updatePriceCacheByPromotion(PromotionInfo promotion) throws Exception;
}
