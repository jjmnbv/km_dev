package com.pltfm.app.threadHandler;

import java.util.List;

import com.pltfm.app.service.ProductSkuService;
import com.pltfm.sys.util.AppApplicationContextUtil;

public class ProductPriceHandler implements Runnable{

	private List<Long> skuIdList;
	
	public ProductPriceHandler(List<Long> list){
		this.skuIdList = list;
	}
	
	@Override
	public void run() {
		ProductSkuService productSkuService = (ProductSkuService) AppApplicationContextUtil.getApplicationContext().getBean("productSkuService");
		productSkuService.updateCachePrice(skuIdList);
	}

}
