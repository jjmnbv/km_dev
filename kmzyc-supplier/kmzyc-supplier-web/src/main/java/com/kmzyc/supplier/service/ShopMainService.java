package com.kmzyc.supplier.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.ShopMain;
import com.pltfm.app.bean.ResultMessage;

public interface ShopMainService {
	
	/**
	 * 分页 查找数据
	 * @param shopMain
	 * @param page
     * @throws ServiceException
	 */
	Pagination searchPage(ShopMain shopMain,Pagination page) throws ServiceException;
	
	/**
	 * 根据ID查找店铺
	 * @param shopId
	 * @return
	 * @throws ServiceException
	 */
	ShopMain findShopMainById(Long shopId) throws ServiceException;
	
	/**
	 * 根据供应商ID查找店铺
	 * @param supplierId
	 * @return
	 * @throws ServiceException
	 */
	ShopMain findShopMainBySupplierId(Long supplierId) throws ServiceException;
	
	/**
	 * 根据供应商ID和站点查找店铺(不包含BLOB字段)
	 * @param supplierId
	 * @return
	 * @throws ServiceException
	 */
	ShopMain findShopMainByIdWithoutBLOBs(Long supplierId) throws ServiceException;
	
	/**
	 * 根据ID删除店铺
	 * @param shopId
	 * @throws ServiceException
	 */
	int deleteShopMainById(Long shopId) throws ServiceException;
	
	/**
	 * 根据supplierId查找店铺
	 * @param supplierId
	 * @return
	 * @throws ServiceException
	 */
	int findShopMain(Long supplierId) throws ServiceException;
	
	/**
	 * 修改店铺
	 * @param shopMain
	 * @return
	 * @throws ServiceException
	 */
	int updateShopMain(ShopMain shopMain) throws ServiceException;
	
	/**
	 * 修改店铺状态
	 * @param shopIdList
	 * @param status
	 * @return
	 * @throws ServiceException
	 */
	int updateStatus(List<Long> shopIdList, String status) throws ServiceException;
	
	/**
	 * 修改店铺审核状态
	 * @param shopIdList
	 * @param auditStatus
	 * @return
	 * @throws ServiceException
	 */
	int updateAuditStatus(List<Long> shopIdList,String auditStatus) throws ServiceException;
	
	/**
	 * 发布供应商店铺主页
	 * @param shopIdList
	 * @throws ServiceException
	 */
	ResultMessage publishShopMainHomePage(List<Long> shopIdList) throws ServiceException;
	
	/**
	 * 批量修改店铺默认域名
	 * @param shopMainList
	 * @return
	 * @throws ServiceException
	 */
	ResultMessage batchUpdateShopMainUrl(List<ShopMain> shopMainList) throws ServiceException;
	
	/**
	 * 删除店铺图片文件
	 * @param shopMain
	 * @return
	 * @throws ServiceException
	 */
	ResultMessage deleteShopMainFilePath(ShopMain shopMain) throws ServiceException;
	
	/**
	 * 获取某个供应商的店铺列表（如需获取所有的店铺则传NULL）
	 * @return
	 * @throws ServiceException
	 */
	List<ShopMain> findAllShopMainBySupplierId(Long supplierId) throws ServiceException;
	
	/**
	 * 预览店铺主页
	 */
	String previewHomePage(Long shopId) throws ServiceException;
	
	/**
	 * 保存商家介绍
	 * @param shopMain
	 * @return
	 * @throws ServiceException
	 */
	boolean updateShopMainDescribe(ShopMain shopMain) throws ServiceException;
}