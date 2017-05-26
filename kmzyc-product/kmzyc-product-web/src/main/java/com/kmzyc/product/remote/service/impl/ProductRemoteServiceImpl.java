package com.kmzyc.product.remote.service.impl;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.ProductRemoteService;
import com.pltfm.app.service.ProductSkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.service.CmsProductUpShelfService;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.vobject.ProductSku;

@Service("productRemoteService")
public class ProductRemoteServiceImpl implements ProductRemoteService {

	private Logger logger = LoggerFactory.getLogger(ProductRemoteServiceImpl.class);
	
	@Resource
	private ProductService productService;
	
	@Resource
	private CmsProductUpShelfService cmsProductUpShelfService;

    @Resource
	private ProductSkuService productSkuService;

	@Override
	public String previewProductInfoPage(String productId) throws ServiceException {
		return productService.previewProductInfoPage(productId);
	}

	@Override
	public ResultMessage upShelfForSupplier(List<Long> supplierIds) throws ServiceException {
		return productService.upShelfForSupplier(supplierIds);
	}

	@Override
	public ResultMessage upShelfForSku(List<ProductSku> productSku) throws ServiceException {
		ResultMessage message = new ResultMessage();
		//通知上架产品给搜索引擎
		List<Integer> cms_ids = new ArrayList<Integer>();
		List<Long> skuIdList = new ArrayList<Long>();
		for(ProductSku ps : productSku){
			cms_ids.add(Integer.valueOf(ps.getProductId().toString()));
            skuIdList.add(ps.getProductId());
        }
        try {
			//CMS上架接口
			cmsProductUpShelfService.productUpShelfByCms(cms_ids);
            productSkuService.updateProductSkuCache(skuIdList);
		} catch (Exception e) {
            logger.error("上架sku失败，", e);
			return message;
		}
		message.setIsSuccess(true);
		return message;
	}

	@Override
	public ResultMessage downShelfForSupplier(List<Long> supplierIds) throws ServiceException {
		return productService.downShelfForSupplier(supplierIds);
	}
	
}
