package com.pltfm.activity.service;

import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;
import com.kmzyc.commons.page.Page;

public interface ActivityPromotionEffectService {

	/**
	 * 分页查找活动推广效果列表
	 * 
	 * @param activityParam
	 * @param page
	 */
	void queryPromotionEffectListForPage(ActivityInfo activityParam, Page page) throws ServiceException;

	/**
	 * 分页查询活动商家总销量
	 * 
	 * @param page
	 * @param supplierEntryParam
	 */
	void queryActivitySuppliersSales(Page page, ActivitySupplierEntry supplierEntryParam) throws ServiceException;

	/**
	 * 查询促销/图文活动商家已报商品销量
	 * 
	 * @param page
	 * @param activitySkuParam
	 * @throws ServiceException
	 */
	void getSPAndTxtSupplierProductSales(Page page, ActivitySku activitySkuParam) throws ServiceException;

	/**
	 * 查询渠道活动商家已报商品销量
	 * 
	 * @param page
	 * @param activitySkuParam
	 * @throws ServiceException
	 */
	void getChannelSupplierProductSales(Page page, ActivitySku activitySkuParam) throws ServiceException;

	/**
	 * 查询商家追加推广明细
	 * 
	 * @param page
	 * @param activitySkuParam
	 */
	void querySupplierAppendProductList(Page page, ActivitySku activitySkuParam) throws ServiceException;

	/**
	 * 查询SKU商品销量明细
	 * @param supplierEntryId
	 * @param skuId
	 * @param page
	 * @param map
	 */
	void querySkuSalesDetail(String supplierEntryId, String skuId, Page page, Map<String, Object> map);
}
