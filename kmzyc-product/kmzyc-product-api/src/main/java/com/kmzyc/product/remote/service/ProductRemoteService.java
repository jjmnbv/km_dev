package com.kmzyc.product.remote.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.ProductSku;

public interface ProductRemoteService {

	/**
	 * 产品预览
	 * @param productId
	 * @return
	 * @throws ServiceException
	 */
	String previewProductInfoPage(String productId) throws ServiceException;
	
	/**
	 * 指定供应商所有产品页重新上架发布一次
	 * @param supplierIds
	 * @return
	 * @throws ServiceException
	 */
	ResultMessage upShelfForSupplier(List<Long> supplierIds) throws ServiceException;
	
	/**
	 * SKU邮费修改重新上架一次
	 * @param productSkus
	 * @return
	 * @throws ServiceException
	 */
	ResultMessage upShelfForSku(List<ProductSku> productSkus) throws ServiceException;
	
	/**
	 * 下架指定供应商ID
	 * @param supplierIds
	 * @return
	 * @throws ServiceException
	 */
	ResultMessage downShelfForSupplier(List<Long> supplierIds) throws ServiceException;
}
