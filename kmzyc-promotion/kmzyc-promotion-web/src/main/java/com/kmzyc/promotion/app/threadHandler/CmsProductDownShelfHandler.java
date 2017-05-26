package com.kmzyc.promotion.app.threadHandler;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.cms.remote.service.DetailPublishService;


public class CmsProductDownShelfHandler implements Runnable {
    // 日志记录
    protected Logger logger = LoggerFactory.getLogger(CmsProductDownShelfHandler.class);
    @Resource
    private DetailPublishService detailPublishService;
    private List<Integer> productIdList;

    public CmsProductDownShelfHandler(List<Integer> productIdList) {
        this.productIdList = productIdList;
    }

    @Override
    public void run() {
        try {
            // DetailPublishService detailPublishService =
            // (DetailPublishService) RemoteTool.getRemote("04", "detailPublishService");
            // System.out.println("准备调用下架接口!");
            // System.out.println(new
            // StringBuilder("下架调用CMS接口返回结果：").append(detailPublishService.publishAllPage(productIdList))
            // .toString());
            // System.out.println("完成调用下架接口!");
            logger.info("准备调用下架接口!");
            logger.info("下架调用CMS接口返回结果："
                    + (detailPublishService.publishAllPage(productIdList)).toString());
            logger.info("完成调用下架接口!");
        } catch (Exception e) {
            logger.error("下架调用CMS接口返回结果异常：", e);
        }
    }

    public List<Integer> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<Integer> productIdList) {
        this.productIdList = productIdList;
    }

}
