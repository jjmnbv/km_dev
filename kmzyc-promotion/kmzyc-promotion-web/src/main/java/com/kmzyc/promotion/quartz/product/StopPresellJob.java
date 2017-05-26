package com.kmzyc.promotion.quartz.product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kmzyc.cms.remote.service.DetailPublishService;
import com.kmzyc.promotion.app.vobject.PromotionPresellCriteria;
import com.kmzyc.promotion.optimization.cache.PresellCache;
import com.kmzyc.promotion.presell.dao.PresellManagerDao;
import com.kmzyc.promotion.presell.service.PresellManagerService;

/**
 * 自动终止预售活动
 * 
 * @author Administrator
 *
 */
@Component
public class StopPresellJob {

    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(StopPresellJob.class);
    @Resource
    private DetailPublishService detailPublishService;
    @Resource
    private PresellManagerService presellManagerService;
    @Resource
    private PresellCache presellCache;
    @Resource
    private PresellManagerDao presellManagerDao;

    public void stopPresell() {
        List<Long> presellIds = new ArrayList<Long>();
        Long presellId = null;
        List<Long> skuids = new ArrayList<Long>();
        List<Long> skuidsFinal = new ArrayList<Long>();
        PromotionPresellCriteria promotionPresellCriteria = new PromotionPresellCriteria();
        try {
            presellIds = presellManagerDao.queryPresellIdForAutoStop();
            if (null != presellIds && presellIds.size() > 0) {
                for (int i = 0; i < presellIds.size(); i++) {
                    presellId = presellIds.get(i);
                    skuids = presellManagerDao.querySkuidsByPresellId(presellId);
                    skuidsFinal.addAll(skuids);
                    logger.info("StopPresellJob.stopPresell()-- start ,presellId:{}--skuids:{}",
                            presellId, skuids);

                    presellCache.delPresellPresellIdCache(presellId);// 删除缓存（预售活动基本信息）
                    presellCache.delPresellSkuidsCache(presellId, skuids);// 删除缓存（预售商品对应的活动限购信息、预售商品对应的所有预售活动信息）
                    // 推送商品价格消息
                    presellManagerService.sendNormalProductsPrice(skuids);
                    // 调用cms接口推送详情页z
                    if (detailPublishService.remotePublishNormalPage(skuidsFinal)) {
                        logger.info(
                                "StopPresellJob.stopPresell调用cms接口：detailPublishService.remotePublishNormalPage成功");
                        // 将活动标记为已处理
                        promotionPresellCriteria.setPresellId(Long.valueOf(presellId));// 活动id
                        promotionPresellCriteria.setAutoStopFlag(1);// 已处理（1）
                        presellManagerDao.stopPresell(promotionPresellCriteria);
                    } else {
                        logger.info(
                                "StopPresellJob.stopPresell调用cms接口：detailPublishService.remotePublishNormalPage失败");
                    }
                    logger.info("StopPresellJob.stopPresell()-- end ");
                }
            }
        } catch (Exception e) {
            logger.error("StopPresellJob自动终止预售活动异常,presellIds:{}", presellId, e);
        }
    }
}
