package com.pltfm.app.threadHandler;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.cms.remote.service.DetailPublishService;
import com.pltfm.app.util.DateTimeUtils;

public class CmsProductUpShelfHandler implements Runnable {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private DetailPublishService detailPublishService;
	
	private List<Integer> productIdList;
	
    public CmsProductUpShelfHandler(DetailPublishService detailPublishService, List<Integer> productIdList) {
        this.detailPublishService = detailPublishService;
        this.productIdList = productIdList;
    }

    @Override
	public void run() {
		try{
            log.info("准备调用上架接口，上架产品ID：{}, 时间：{}。",
                    new Object[]{productIdList, DateTimeUtils.getDateTime(new Date())});
			String result = detailPublishService.publishAllPage(productIdList);
			log.info("上架调用CMS接口返回结果：{}", result);
		}catch(Exception e){
			log.error("上架调用CMS接口失败：", e);
		}
	}

	public List<Integer> getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(List<Integer> productIdList) {
		this.productIdList = productIdList;
	}

    public DetailPublishService getDetailPublishService() {
        return detailPublishService;
    }

    public void setDetailPublishService(DetailPublishService detailPublishService) {
        this.detailPublishService = detailPublishService;
    }
}
