package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuDraft;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

public interface ProductSkuDraftService {

	/**
	 * 保存
	 * @param productSkuDraft
	 * @throws ServiceException
	 */
	void addProductSkuDraft(ProductSkuDraft productSkuDraft) throws ServiceException;
	
	/**
	 * 更新价格及重量
	 * @param list
	 * @throws ServiceException
	 */
	void updateBatchByPrimaryKey(Long productId,List<ProductSkuDraft> list)throws ServiceException;
	
	/**
	 * 获取所有的SKU草稿信息
	 * @param record
	 * @return
	 * @throws ServiceException
	 */
	void searchPage(Page page,ProductSkuDraft record)throws ServiceException;
	
	/**
	 * 获取单个对象
	 * @param productSkuId
	 * @return
	 * @throws ServiceException
	 */
	ProductSkuDraft findSingleProductSku(Long productSkuId) throws ServiceException;
	
	/**
	 * 获取SKU介绍
	 * @param productSkuId
	 * @return
	 * @throws ServiceException
	 */
	ProductSkuDraft findSingleProductSkuForIntroduce(Long productSkuId) throws ServiceException;
	
	/**
	 * 判断该SKU是否符合提交审核的条件
	 * @param productId
	 * @return
	 * @throws ServiceException
	 */
	List<ProductSkuDraft> findValidProductSkus(Long productId) throws ServiceException;
	
	/**
	 * 根据产品Id获取SKU
	 * @param productId
	 * @return
	 * @throws ServiceException
	 */
	List<ProductSkuDraft> findProductSkuByProductId(Long productId) throws ServiceException;
	
	/**
	 * 根据产品Id获取SKU
	 * @param productId
	 * @return
	 * @throws ServiceException
	 */
	List<ProductSku> findProductSkuIntoOfficialByProductId(Long productId) throws ServiceException;
	
	/**
	 * 批量插入ProductSku正式表
	 * @param list
	 * @throws ServiceException
	 */
	void batchInsertOfficial(List<ProductSkuDraft> list,Long loginId,Long productId)
            throws ServiceException;
	
	/**
	 * 批量插入ProductSku草稿
	 * @param list
	 * @throws ServiceException
	 */
	void batchInsertDraft(List<ProductSkuDraft> list) throws ServiceException;
	
	/**
	 * 根据产品Id删除Sku
	 * @param productId
	 * @throws ServiceException
	 */
	void deleteDraftByProductId(Long productId) throws ServiceException;
	
	/**
	 * 根据主键删除SKU
	 * @param productSkuId
	 * @throws ServiceException
	 */
	void deleteProductSku(Long productSkuId) throws ServiceException;
	
	/**
	 * 批量更新正式表中的数据
	 * @param list
	 * @throws ServiceException
	 */
	int batchUpdateOfficialFromDraft(List<ProductSkuDraft> list, Long loginId) throws ServiceException;
	
	/**
	 * 批量更新正式表中的数据（只更新价格）
	 * @param list
	 * @throws ServiceException
	 */
	int batchUpdatePriceOnlyOfficialFromDraft(List<ProductSkuDraft> list, Long loginId)
            throws ServiceException;
	
	/**
	 * 单独的更新SKU价格，正式数据修改后，插入草稿表中
	 * @param productId
	 * @param list
	 * @throws ServiceException
	 */
	void updateSingleSkuPrice(Long productId,List<ProductSkuDraft> list) throws ServiceException;
	
	/**
	 * 审核价格
	 * @param productIdChk
	 * @param auditStatus
	 * @param reasonText
	 * @throws ServiceException
	 */
	void auditProductPrice(Long[] productIdChk,String auditStatus,String reasonText)
            throws ServiceException;
	
	/**
	 * 批量保存（数据来源正式表数据）
	 * @param list
	 * @throws ServiceException
	 */
	void batchInsertDraftFromOfficial(List<ProductSku> list) throws ServiceException;

	/**
	 * 批量更新产品SKU是否有效的状态
	 * @param list
	 * @throws ServiceException
	 */
	void batchUpdateProductSkuDraft(List<ProductSkuDraft> list) throws ServiceException;

	void searchPageByUser(Page page, ProductSkuDraft productSkuDraft) throws ServiceException;
	
	int updateSkuIntroduce(ProductSkuDraft productSkuDraft) throws ServiceException;

    boolean checkSkuPriceAndWeight(List<Long> productIds) throws ServiceException;
	
	/**
	 * 根据条形码查找商品SKU
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	List<ProductSkuDraft> findSameSkuBarCodeProductSku(Map<String,Object> map) throws ServiceException;
	
}