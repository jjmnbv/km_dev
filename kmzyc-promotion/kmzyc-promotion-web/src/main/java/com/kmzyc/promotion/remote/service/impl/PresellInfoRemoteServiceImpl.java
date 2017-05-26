package com.kmzyc.promotion.remote.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.promotion.optimization.cache.PresellCache;
import com.kmzyc.promotion.optimization.vo.PresellProductVO;
import com.kmzyc.promotion.remote.service.PresellInfoRemoteService;
import com.kmzyc.promotion.util.PresellCacheUtil;
import com.kmzyc.promotion.util.RedisTemplate;

@SuppressWarnings("unchecked")
@Service("presellInfoRemoteService")
public class PresellInfoRemoteServiceImpl implements PresellInfoRemoteService {
    // 日志记录
    private static final Logger logger = LoggerFactory
            .getLogger(PresellInfoRemoteServiceImpl.class);

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    private PresellCache presellCache;

    @Resource
    private PresellCacheUtil presellCacheUtil;

    @Override
    public PresellProductVO getPresellInfo(Long skuId) {
        PresellProductVO ppv = presellCacheUtil.getPresellCache(skuId);
        return ppv;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updatePresellStock(Long presellId, Long skuId, int count) throws Exception {
        String lockKey = presellId.toString() + skuId.toString();
        logger.info("修改预售库存skuId:{} 缓存", lockKey);
        boolean lockOk = redisTemplate.tryLock(lockKey, 600);
        if (!lockOk) {
            logger.info("presellIdskuId:{} 正在修改预售库存", lockKey);
            return "presellIdskuid：" + lockKey + "正在修改预售库存";
        }
        String updateResult = "";
        try {
            updateResult = presellCache.updateProductOrderQuantityCache(presellId, skuId, count);
        } catch (Exception e) {
            logger.error("修改预售库存异常,skuId:{}", lockKey, e);
            return "skuid：" + lockKey + "修改预售库存失败";
        } finally {
            redisTemplate.release(lockKey);
        }
        logger.info(updateResult);
        return updateResult;

    }
}
