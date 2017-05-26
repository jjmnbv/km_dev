package com.pltfm.app.threadHandler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.cms.remote.service.DetailPublishService;

public class CmsProductDownShelfHandler implements Runnable {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private DetailPublishService detailPublishService;
	
	private List<Integer> productIdList;
	
    public CmsProductDownShelfHandler(DetailPublishService detailPublishService, List<Integer> productIdList) {
        this.detailPublishService = detailPublishService;
        this.productIdList = productIdList;
    }

    public DetailPublishService getDetailPublishService() {
        return detailPublishService;
    }

    public void setDetailPublishService(DetailPublishService detailPublishService) {
        this.detailPublishService = detailPublishService;
    }

    @Override
	public void run() {
		try{
			log.info("准备调用下架接口!");
            String url = detailPublishService.publishAllPage(productIdList);
            log.info(new StringBuilder("完成调用下架接口。下架调用CMS接口返回结果：").append(url).toString());
		}catch(Exception e){
			log.error("调用CMS下架接口失败：", e);
		}
	}

	public List<Integer> getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(List<Integer> productIdList) {
		this.productIdList = productIdList;
	}

}
