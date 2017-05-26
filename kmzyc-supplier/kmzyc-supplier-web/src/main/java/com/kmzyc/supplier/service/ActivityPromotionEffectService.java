package com.kmzyc.supplier.service;

import java.util.Map;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivitySku;

public interface ActivityPromotionEffectService {

	/**
	 * 分页查询已参与的活动推广效果列表
	 * 
	 * @param page
	 * @param supplierId
	 */
	Pagination queryPromotionEffectList(Long supplierId, ActivityInfo activityParam, Pagination page) throws ServiceException;

	/**
	 * 分页查询已参与的促销/图文活动已报商品销量列表
	 * 
	 * @param page
	 * @param activitySkuParam
	 * @return
	 * @throws Exception
	 */
	Pagination querySPAndTxtActivitySalesList(Long supplierId, Pagination page, ActivitySku activitySkuParam)
			throws ServiceException;

	/**
	 * 分页查询已参与渠道活动已报商品销量列表
	 * 
	 * @param page
	 * @param activitySkuParam
	 * @throws Exception
	 */
	Pagination queryChannelActivitySalesList(Long supplierId, Pagination page, ActivitySku activitySkuParam)
			throws ServiceException;

	/**
	 * 分页查询参与活动的追加推广明细列表
	 * 
	 * @param page
	 * @param activitySkuParam
	 */
	Pagination queryAppendProductDetail(Long supplierId, Pagination page, ActivitySku activitySkuParam)
			throws ServiceException;

	/**
	 * 查询SKU商品销量明细
	 * 
	 * @param supplierEntryId
	 * @param skuId
	 * @param page
	 * @param map
	 */
	Pagination querySkuSalesDetail(String supplierEntryId, String skuId, Pagination page, Map<String, Object> map)
            throws ServiceException;

}
