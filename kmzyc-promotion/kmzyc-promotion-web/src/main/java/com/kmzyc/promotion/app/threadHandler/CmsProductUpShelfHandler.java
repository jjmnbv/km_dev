package com.kmzyc.promotion.app.threadHandler;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.cms.remote.service.DetailPublishService;


public class CmsProductUpShelfHandler implements Runnable {
    // 日志记录
    protected Logger logger = LoggerFactory.getLogger(CmsProductUpShelfHandler.class);
    @Resource
    private DetailPublishService detailPublishService;
    private List<Integer> productIdList;

    public CmsProductUpShelfHandler(List<Integer> productIdList) {
        this.productIdList = productIdList;
    }

    @Override
    public void run() {
        try {
            // DetailPublishService detailPublishService =
            // (DetailPublishService) RemoteTool.getRemote("04", "detailPublishService");
            logger.info("准备调用上架接口!");
            logger.info(new StringBuilder("上架调用CMS接口返回结果：")
                    .append(detailPublishService.publishAllPage(productIdList)).toString());
            logger.info("完成调用上架接口!");
        } catch (Exception e) {
            logger.error("上架调用CMS接口返回结果异常：", e);
        }
    }

    public List<Integer> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<Integer> productIdList) {
        this.productIdList = productIdList;
    }

}
