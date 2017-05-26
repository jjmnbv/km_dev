package com.quartz.product;

import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.util.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 更新商品sku详细信息缓存
 */
public class ProductSkuDetailsJob {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RedisLock redisLock;

    @Resource
    private ProductSkuService ProductSkuService;

    public void execute() {
        String key = "product_sku_details_job_flag";
        try {
            if (!redisLock.tryLock(key, 2 * 60 * 60)) {
                logger.error("没有获取到更新商品sku详细信息缓存的锁，不执行。");
                return;
            }
            logger.info("全局更新sku详细缓存开始！");

            ProductSkuService.updateAllProductSkuCache();

            logger.info("全局更新sku详细缓存成功！");
        } catch (Exception e) {
            logger.error("全局更新sku信息缓存出错，", e);
        } finally {
            redisLock.release(key);
        }

    }
}