package com.kmzyc.promotion.app.threadHandler;

import java.util.List;

import com.kmzyc.promotion.app.service.ProductSkuService;
import com.kmzyc.promotion.sys.util.AppApplicationContextUtil;

public class ProductPriceHandler implements Runnable {

	private List<Long> skuIdList;

	public ProductPriceHandler(List<Long> list) {
		this.skuIdList = list;
	}

	@Override
	public void run() {
		ProductSkuService productSkuService = (ProductSkuService) AppApplicationContextUtil.getApplicationContext()
				.getBean("productSkuService");
		productSkuService.updateCachePrice(skuIdList);
	}

}
