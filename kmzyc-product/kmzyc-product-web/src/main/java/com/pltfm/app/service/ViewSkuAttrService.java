package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ViewSkuAttr;

/**
 * SKU关联属性视图
 * 
 * @author tanyunxing
 * 
 */
public interface ViewSkuAttrService {
	
	/**
	 * 根据SKUId获取SKU属性
	 * @param skuId
	 * @return
	 * @throws ServiceException
	 */
	List<ViewSkuAttr> findBySkuId(Long skuId) throws ServiceException;

	/**
	 * 根据属性值确定SkuId
	 * @param productId
	 * @param valueId
	 * @return
	 * @throws ServiceException
	 */
	Long findSkuIdByAttrAndValue(Long productId, List<Long> valueId) throws ServiceException;

	/**
	 * 根据产品Id获取Sku属性以及属性值
	 * @param productId
	 * @return
	 * @throws ServiceException
	 */
	List<ViewSkuAttr> findProductAttrAndValueByProductId(Long productId) throws ServiceException;

}