package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.ShopMain;
import com.pltfm.app.vobject.ShopForExport;
import com.kmzyc.commons.page.Page;

public interface SupplierShopService {

	/**
	 * 显示供应商店铺列表
	 * @return
	 */
	List<ShopMain> supplierShopListShow(Page page,ShopMain searchShopMain)throws ServiceException;
	
	/**
	 * 根据店铺id查询店铺信息
	 * @param shopId
	 * @return
	 * @throws ServiceException
	 */
	ShopMain supplierShopView(Long shopId)throws ServiceException;
	
	/**
	 * 启用或停用店铺
	 * @param shopMain
	 * @return
	 * @throws ServiceException
	 */
	boolean updateSupplierShopStatus(ShopMain shopMain)throws ServiceException;
	
	/**
	 * 供应商店铺审核操作
	 * @param shopMain
	 * @return
	 * @throws ServiceException
	 */
	boolean auditSupplierShopSer(ShopMain shopMain,String ckType)throws ServiceException;
	
	/**
	 * 店铺审核后推送MQ消息给搜索引擎
	 * @param ids
	 * @param opType
	 * @throws ServiceException
	 */
	void changeShopMainNotify(List<Long> ids,String opType) throws ServiceException;
	
	/**
	 * 查询所有启用的供应商的店铺
	 * @return
	 * @throws ServiceException
	 */
	Map<String,String> querySupplierShop() throws ServiceException;
	
	/**
	 * 导出供应商店铺信息
	 * @param searchShopMain
	 * @return
	 * @throws ServiceException
	 */
    List<ShopForExport> getSupplierShopInfoForExcel(ShopMain searchShopMain) throws ServiceException;
	
	/**
	 * 组装供应商店铺excel格式
	 * @param searchShopMain
	 * @throws ServiceException
	 */
	void exportExcelForSupplierShopList(ShopMain searchShopMain) throws ServiceException;
	
}