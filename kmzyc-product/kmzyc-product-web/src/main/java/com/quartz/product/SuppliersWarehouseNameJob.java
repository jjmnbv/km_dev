package com.quartz.product;

import com.pltfm.app.service.SupplierAuditService;
import com.pltfm.app.util.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

import javax.annotation.Resource;

/**
 * 获取供应商的仓库名称
 */
public class SuppliersWarehouseNameJob {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RedisLock redisLock;

    @Resource
    private SupplierAuditService supplierAuditService;

    public void execute() {
        String key = "suppliers_warehouse_name_job_flag";
        try {
            if (!redisLock.tryLock(key, 2 * 60 * 60)) {
                logger.error("没有获取到更新获取供应商的仓库名称缓存的锁，不执行。");
                return;
            }
            logger.info("全局更新获取供应商的仓库名称开始！");

            supplierAuditService.updateSuppliersWarehouseName();

            logger.info("全局获取供应商的仓库名称缓存成功！");
        } catch (Exception e) {
            logger.error("全局获取供应商的仓库名称出错，", e);
        } finally {
            redisLock.release(key);
        }

    }
}