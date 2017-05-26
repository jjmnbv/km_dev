package com.quartz.product;

import java.util.Date;

import javax.annotation.Resource;

import com.pltfm.app.service.ProductSkuQuantityService;
import com.pltfm.app.util.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductSkuQuantityJob {

    private Logger logger = LoggerFactory.getLogger(ProductSkuQuantityJob.class);

    @Resource
    private RedisLock redisLock;

    @Resource
    private ProductSkuQuantityService productSkuQuantityService;

    public void execute() {
        String key = "product_sku_Quantity_job_flag";
        try {
            if (!redisLock.tryLock(key, 2 * 60 * 60)) {
                logger.error("没有获取到定时更新PRODUCT_SKU_QUANLITY数量的锁，不执行。");
                return;
            }
            productSkuQuantityService.updateProductSkuQuanlityEveryMonth(new Date());
        } catch (Exception e) {
            logger.error("定时更新PRODUCT_SKU_QUANLITY中的数量出错，", e);
        } finally {
            redisLock.release(key);
        }
    }
}