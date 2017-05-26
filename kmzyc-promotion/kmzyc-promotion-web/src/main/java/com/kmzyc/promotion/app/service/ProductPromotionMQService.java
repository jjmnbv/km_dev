package com.kmzyc.promotion.app.service;

import java.util.List;

import com.kmzyc.promotion.app.vobject.ProductSku;

/**
 * 数据更新MQ接口
 * 
 * @author hewenfeng
 * 
 */
public interface ProductPromotionMQService {

    /**
     * 活动上线时更新价格区间到MQ
     * 
     * @param promotion
     */
    public void promotionOnlineNotify() throws Exception;

    /**
     * 更新活动上线过期状态
     */
    public Integer updatePromotionOnlineStatus() throws Exception;

    /**
     * 
     * 通过sku(普通产品，促销产品),发送mq消息到搜索系统,
     *
     * @author Administrator
     * @param skuList
     * @throws Exception
     */
    public void promotionOnlineIndexNotity(List<ProductSku> skuList, String indexCode)
            throws Exception;

}
