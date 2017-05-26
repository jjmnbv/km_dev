package com.kmzyc.promotion.optimization.cache;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.page.Pagination;
import com.kmzyc.promotion.app.dao.PromotionInfoDao;
import com.kmzyc.promotion.app.service.PromotionRuleDataService;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.constant.PromotionConstant;
import com.kmzyc.promotion.optimization.service.PromotionProductNewService;
import com.kmzyc.promotion.sys.util.RedisCacheUtil;
import com.kmzyc.promotion.sys.util.StringUtil;
import com.kmzyc.promotion.util.RedisTemplate;

@Component("promotionProcess")
public class PromotionProcess {
    @Resource
    private PromotionInfoDao promotionInfoDao;
    @Resource
    private PromotionRuleDataService promotionRuleDataService;

    @Resource
    private PromotionProductNewService promotionProductNewService;
    @Resource
    private PromotionProductCacheProcess promotionProductCacheProcess;
    @Resource
    RedisCacheUtil redisCacheUtil;
    @Resource
    RedisTemplate redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PromotionProcess.class);

    @Resource
    TaskExecutor taskExecutor;

    public void createOrUpdatePromotionCacheTask(final long promotionId) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String key = promotionId + "";
                int issycncache = PromotionConstant.PROM_ISSYCNCACHE_3;
                PromotionInfo promotionInfo = new PromotionInfo();
                promotionInfo.setPromotionId(promotionId);
                try {
                    if (!redisTemplate.tryLock(key, 60 * 10)) {
                        logger.info("key:{},加锁失败，活动缓存正在创建,promotionId：{}。请稍候在试。", key, promotionId);
                        return;
                    }
                    issycncache = PromotionConstant.PROM_ISSYCNCACHE_1;
                    promotionInfo.setIsSycnCache(issycncache);
                    // 正在同步 1
                    promotionInfoDao.updatePromotionIsSycnCacheByPromotionId(promotionInfo);
                    boolean sycflag = createOrUpdatePromotionCache(promotionId);
                    promotionInfo.setPromotionId(promotionId);
                    if (sycflag) {
                        // 同步成功 2
                        issycncache = PromotionConstant.PROM_ISSYCNCACHE_2;
                    } else {
                        // 同步失败 3
                        issycncache = PromotionConstant.PROM_ISSYCNCACHE_3;
                    }
                } catch (SQLException e) {
                    logger.error(
                            "createOrUpdatePromotionCacheTask更新促销活动表是否同步缓存状态失败,新的状态值issycncache:{},promotionid:{}.",
                            issycncache, promotionId, e);
                    // 同步失败 3
                    issycncache = PromotionConstant.PROM_ISSYCNCACHE_3;
                } finally {
                    promotionInfo.setIsSycnCache(issycncache);
                    try {
                        promotionInfoDao.updatePromotionIsSycnCacheByPromotionId(promotionInfo);
                    } catch (SQLException e) {
                        logger.error(
                                "createOrUpdatePromotionCacheTask更新促销活动表是否同步缓存状态失败,新的状态值issycncache:{},promotionid:{}.",
                                issycncache, promotionId, e);
                    }
                    redisTemplate.release(key);
                }
            }
        });
    }

    /**
     * 创建单个促销活动缓存
     * 
     * @param promotionId 活动id
     * @return
     */
    private boolean createOrUpdatePromotionCache(long promotionId) {
        try {
            logger.info("创建promotion缓存开始,promotionId:{}", promotionId);
            PromotionInfo info = promotionInfoDao.getPromotionInfoById(promotionId);
            if (info == null) {
                logger.info("创建promotion缓存失败，活动不存在,promotionId：{}。", promotionId);
                return false;
            }
            boolean ok = createOrUpdatePromotionCache(info);
            logger.info("创建promotion缓存结束,promotionId:{},ok={}", promotionId, ok);
            return ok;
        } catch (Exception e) {
            redisCacheUtil.deletePromotionCache(promotionId);
            logger.error("创建promotion缓存异常,promotionId:{}", promotionId, e);
        }
        return false;
    }

    private boolean createOrUpdatePromotionCache(PromotionInfo info) throws Exception {
        long promotionId = info.getPromotionId().longValue();
        if (info.getProductFilterType().intValue() == 1 && info.getShopSort().intValue() == 1) {
            logger.error("更新指定活动缓存失败,promotionId：{}，活动商品筛选类型全场，商家类别所有。", promotionId);
            return false;
        }
        Date end = info.getEndTime();
        // if (info.getStatus().intValue() != 2) {
        // logger.error("更新指定活动缓存失败,promotionId：{}，活动还未发布。", promotionId);
        // return false;
        // }
        if (info.getStatus() == null || info.getStatus().intValue() != 2 || end == null
                || end.before(new Date())) {// 无效活动（撤销或者终止导致）
            promotionProductCacheProcess.createProductPriceCache(info);// 同步处理
            return redisCacheUtil.deletePromotionCache(promotionId);
        }

        // List<PromotionRuleData> ruldate =
        // promotionRuleDataDAO.selectPromotionRuleList(promotionId);
        List<JSONObject> prizeEntityMap =
                promotionRuleDataService.selectPromotionRuleAndPrizeEntity(info);
        redisCacheUtil.savePromotionInfo(info, prizeEntityMap);
        redisCacheUtil.deletePromotionProductCache(promotionId);
        Pagination page = new Pagination();
        int num = 500;
        page.setNumperpage(num);
        page.setPage(1);
        while (true) {
            try {
                // 第一次创建读db
                Map<String, Double> list =
                        promotionProductNewService.queryPromotionProductSkuIdMapFromDb(info, page);
                if (!StringUtil.isEmpty(list)) {
                    redisCacheUtil.savePromotionProductInfo(promotionId, end, list);
                }
                if (list == null || list.size() < num) {
                    break; // return false;
                }
            } finally {
                page.setPage(page.getPage() + 1);
            }
        }
        promotionProductCacheProcess.createProductPriceCache(info);// 同步处理

        return true;
    }

    /** 只更新活动基本数据 */
    public void updatePromotionInfoNew(final long promotionId) {
        // mkw 20151229 add 异步处理
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                PromotionInfo info = null;

                logger.info("开始更新promotion基本信息缓存，promotionId：{}。", promotionId);
                // 查询促销
                try {
                    info = promotionInfoDao.getPromotionInfoById(promotionId);
                    if (info == null) {
                        logger.warn("更新promotion基本信息缓存失败，活动不存在,promotionId：{}。", promotionId);
                        return;
                    }
                } catch (SQLException e) {

                    logger.error("查询promotion基本信息缓存失败promotionId：{}。", promotionId);
                    return;
                }

                // 同步缓存
                try {
                    Date end = info.getEndTime();
                    if (end.before(new Date())) {
                        promotionProductCacheProcess.createProductPriceCache(info);// 同步处理
                        redisCacheUtil.deletePromotionCache(promotionId);
                    } else {
                        List<JSONObject> prizeEntityMap =
                                promotionRuleDataService.selectPromotionRuleAndPrizeEntity(info);
                        redisCacheUtil.savePromotionInfo(info, prizeEntityMap);
                    }
                    info.setIsSycnCache(PromotionConstant.PROM_ISSYCNCACHE_2);
                    logger.info("更新promotion基本信息缓存over，promotionId：{}。", promotionId);
                    // return true;
                } catch (Exception e) {
                    info.setIsSycnCache(PromotionConstant.PROM_ISSYCNCACHE_3);
                    logger.error("更新promotion基本信息缓存异常，promotionId={}", promotionId, e);
                }

                // 同步记录
                try {
                    promotionInfoDao.updatePromotionIsSycnCacheByPromotionId(info);
                } catch (SQLException e) {
                    logger.error("根据促销活动ID更新 促销活动表 的 是否已同步缓存状态异常，promotionId={},新状态为{}",
                            promotionId, info.getIsSycnCache(), e);
                }
            }
        });
    }
}
