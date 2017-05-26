package com.kmzyc.supplier.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProductSkuAttr;
/**
 * 产品SKU属性业务逻辑接口
 * 
 */
public interface ProductSkuAttrService {

	/**
	 * 查询SKU属性
	 * @param productSkuAttr 
	 * @return List<ProductSkuAttr> 
	 * @throws Exception
	 */
	List<ProductSkuAttr> queryProductSkuAttrList(ProductSkuAttr productSkuAttr) throws ServiceException;
	
	/**
	 * 新增
	 * @param productSkuAttr
	 * @throws ServiceException
	 */
	void insertProductSkuAttr(ProductSkuAttr productSkuAttr) throws ServiceException;
}
