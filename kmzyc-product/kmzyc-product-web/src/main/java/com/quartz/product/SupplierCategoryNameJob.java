package com.quartz.product;

import com.pltfm.app.service.SupplierAuditService;
import com.pltfm.app.util.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

import javax.annotation.Resource;

/**
 * 获取供应商所经营的类目名称
 */
public class SupplierCategoryNameJob {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RedisLock redisLock;

    @Resource
    private SupplierAuditService supplierAuditService;

    @Resource
    private TaskExecutor taskExecutor;

    public void execute() {
        String key = "supplier_category_name_job_flag";
        try {
            if (!redisLock.tryLock(key, 2 * 60 * 60)) {
                logger.error("没有获取到更新供应商所经营的类目名称缓存的锁，不执行。");
                return;
            }
            logger.info("全局更新供应商所经营的类目名称开始！");

            taskExecutor.execute(() -> supplierAuditService.updateSupplierCategoryName(1));

            taskExecutor.execute(() -> supplierAuditService.updateSupplierCategoryName(2));

            logger.info("全局供应商所经营的类目名称缓存成功！");
        } catch (Exception e) {
            logger.error("全局供应商所经营的类目名称出错，", e);
        } finally {
            redisLock.release(key);
        }

    }
}