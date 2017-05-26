package com.kmzyc.promotion.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.km.framework.mq.bean.KmMsg;
import com.kmzyc.promotion.app.dao.PromotionInfoDao;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.jms.SearchMessageSender;
import com.kmzyc.promotion.app.service.ProductPromotionMQService;
import com.kmzyc.promotion.app.service.PromotionInfoService;
import com.kmzyc.promotion.app.service.PromotionProductService;
import com.kmzyc.promotion.app.util.CodeUtils;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.optimization.vo.ProductPromotion;
import com.kmzyc.promotion.sys.util.RedisCacheUtil;
import com.kmzyc.promotion.sys.util.StringUtil;
import com.kmzyc.promotion.util.PromotionCacheUtil;

@Service("productPromotionMQService")
@Scope("singleton")
public class ProductPromotionMQServiceImpl implements ProductPromotionMQService {

    @Resource
    private SearchMessageSender sendMessageSender;
    @Resource
    private PromotionInfoService promotionInfoService;
    @Resource
    private PromotionProductService promotionProductService;// queryPromotionProductList;

    @Resource
    private PromotionInfoDao promotionInfoDao;

    @Resource
    RedisCacheUtil redisCacheUtil;
    @Resource
    PromotionCacheUtil promotionCacheUtil;
    private static Logger logger = LoggerFactory.getLogger(ProductPromotionMQServiceImpl.class);

    @Override
    public void promotionOnlineNotify() throws Exception {
        updatePromotionOnlineStatus();// 触发器修改活动状态
        // 发送消息
        promotionOnlineNotify("2001", "2002");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "transactionManager")
    public void promotionOnlineNotify(String indexCode, String cmsCode) throws Exception {
        // 获取需要通知MQ的活动
        List<Integer> promotionTypes = new ArrayList<Integer>();
        promotionTypes.add(PromotionTypeEnums.DISCOUNT.getValue());
        promotionTypes.add(PromotionTypeEnums.SALE.getValue());
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("isSycnIndex", 0);
        conditionMap.put("promotionTypes", promotionTypes);
        List<PromotionInfo> list = promotionInfoDao.getPromotionInfoList(conditionMap);
        if (list == null || list.isEmpty()) {
            return;
        }
        // throw new Exception();
        setSendMapNoPage(indexCode, cmsCode, list);
    }

    /**
     * 设置mq数据 产品数据查询不分页
     * 
     * @param indexCode
     * @param cmsCode
     * @param list
     * @throws Exception
     */
    private void setSendMapNoPage(String indexCode, String cmsCode, List<PromotionInfo> list)
            throws Exception {
        Map<String, Object> productPriceCms = null;
        Map<String, Object> productSkuPriceMap = null;
        Map<String, Object> indexSkuPriceMap = null;
        Map<String, Long> productIdMap = null;
        List<PromotionInfo> updateList = null;
        for (PromotionInfo promotionInfo : list) {
            Integer promotionType = promotionInfo.getPromotionType();
            Date startTime = promotionInfo.getStartTime();
            if (startTime.after(new Date())) {// 活动还未开始
                continue;
            }
            if (!promotionType.equals(PromotionTypeEnums.DISCOUNT.getValue())
                    && !promotionType.equals(PromotionTypeEnums.SALE.getValue())) {
                continue;
            }
            List<ProductSku> skuList =
                    promotionProductService.queryProductsByPromotionInfo(promotionInfo);
            if (skuList == null || skuList.isEmpty()) {
                continue;
            }
            for (ProductSku sku : skuList) {
                Long skuId = sku.getProductSkuId();
                ProductPromotion pp =
                        promotionCacheUtil.getProductPromtoionInfoCahce(skuId.toString());
                BigDecimal price = BigDecimal.valueOf(sku.getPrice());
                if (pp != null) {
                    price = pp.calculateFinalPrice(price);
                }
                if (null != price) {
                    productSkuPriceMap = Maps.newHashMap();
                    if (indexSkuPriceMap == null) {
                        indexSkuPriceMap = Maps.newHashMap();
                    }
                    productSkuPriceMap.put("price", price.doubleValue());
                    if (pp != null && pp.getPricePromotion() != null) {
                        // 正在参加的特价，打折活动产品
                        productSkuPriceMap.put("dataType", 1);
                    } else {
                        // 普通产品
                        productSkuPriceMap.put("dataType", 0);
                    }
                    indexSkuPriceMap.put(sku.getProductSkuId().toString(), productSkuPriceMap);
                }
                if (productIdMap == null) {
                    productIdMap = Maps.newHashMap();
                }
                productIdMap.put(sku.getProductId().toString(), sku.getProductId());
            }
            if (updateList == null) {
                updateList = Lists.newArrayList();
            }
            updateList.add(promotionInfo);
        }
        if (!StringUtil.isEmpty(indexSkuPriceMap)) {
            sendMessage(indexSkuPriceMap, CodeUtils.DESTINATION_PROMOTION_SEARCH, indexCode);
            logger.info("mq推送的数据：productSkuPriceMap={}", indexSkuPriceMap);
            productSkuPriceMap.clear();
            indexSkuPriceMap.clear();
        }
        if (!StringUtil.isEmpty(productIdMap)) {
            if (productPriceCms == null) {
                productPriceCms = Maps.newHashMap();
            }
            productPriceCms.put("1", productIdMap);
            sendMessage(productPriceCms, CodeUtils.DESTINATION_PROMOTION_CMS, cmsCode);
            logger.info("mq推送的数据：productPriceCms={}", productPriceCms);
            productIdMap.clear();
            productPriceCms.clear();
        }

        // 发送完毕 更新活动同步状态（分页处理）
        if (!StringUtil.isEmpty(updateList)) {
            int pageCount = 0;
            if (updateList.size() % 500 == 0) {
                pageCount = updateList.size() / 500;
            } else {
                pageCount = updateList.size() / 500 + 1;
            }

            for (int i = 0; i < pageCount; i++) {
                List<PromotionInfo> selist = null;
                if (i == pageCount - 1) {
                    selist = updateList.subList(i * 500, updateList.size());
                } else {
                    selist = updateList.subList(i * 500, (i + 1) * 500);
                }
                updateIsSyncIndex(selist);
            }
        }
    }

    private void sendMessage(Map<String, Object> productIndexPriceMap, String destinationId,
            String code) {
        if (!productIndexPriceMap.isEmpty()) {
            logger.info("发送需要更新商品数据到MQ,data:" + productIndexPriceMap + ",code:" + code);

            // 报文编号为2000标识产品系统
            KmMsg kmMsg = new KmMsg(code, false);
            kmMsg.getMsgData().put("data", productIndexPriceMap);
            // 发送消息
            this.sendMessageSender.sendQueueMessage(destinationId, kmMsg);
        } else {
            logger.info("暂时没有需要更新商品数据到MQ。" + "code:" + code);
        }
    }

    /**
     * 批量更新同步状态
     * 
     * @param list
     * @throws SQLException
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateIsSyncIndex(List<PromotionInfo> list) throws SQLException {
        List<Long> pids = new ArrayList<Long>();
        for (PromotionInfo promotionInfo : list) {
            pids.add(promotionInfo.getPromotionId());
        }

        promotionInfoService.updateIsSyncIndex(pids, 1);
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updatePromotionOnlineStatus() throws Exception {
        return promotionInfoDao.updatePromotionOnlineStatus();
    }

    @Override
    public void promotionOnlineIndexNotity(List<ProductSku> skuList, String indexCode)
            throws Exception {
        Map<String, Object> productSkuPriceMap = null;
        Map<String, Object> indexSkuPriceMap = null;
        for (ProductSku sku : skuList) {
            Long skuId = sku.getProductSkuId();
            ProductPromotion pp = promotionCacheUtil.getProductPromtoionInfoCahce(skuId.toString());
            BigDecimal price = BigDecimal.valueOf(sku.getPrice());
            if (pp != null) {
                price = pp.calculateFinalPrice(price);
            }
            if (null != price) {
                productSkuPriceMap = Maps.newHashMap();
                if (indexSkuPriceMap == null) {
                    indexSkuPriceMap = Maps.newHashMap();
                }
                productSkuPriceMap.put("price", price.doubleValue());
                if (pp != null && pp.getPricePromotion() != null) {
                    // 正在参加的特价，打折活动产品
                    productSkuPriceMap.put("dataType", 1);
                } else {
                    // 普通产品
                    productSkuPriceMap.put("dataType", 0);
                }
                indexSkuPriceMap.put(sku.getProductSkuId().toString(), productSkuPriceMap);
            }
        }
        if (!StringUtil.isEmpty(indexSkuPriceMap)) {
            sendMessage(indexSkuPriceMap, CodeUtils.DESTINATION_PROMOTION_SEARCH, indexCode);
            logger.info("mq推送的数据：productSkuPriceMap={}", indexSkuPriceMap);
            productSkuPriceMap.clear();
            indexSkuPriceMap.clear();
        }
    }


}
