package com.kmzyc.supplier.service;

import java.util.List;

import com.pltfm.app.vobject.ProductImageDraft;
import com.kmzyc.commons.exception.ServiceException;

public interface ProductImageDraftService {
	
	/**
	 * 保存图片
	 * 
	 * @param image
	 * @return
	 */
	Long saveImage(ProductImageDraft image) throws ServiceException;

	/**
	 * 根据Id删除图片
	 * 
	 * @param imageId
	 * @throws Exception
	 */
	void deleteImageById(Long imageId) throws ServiceException;

	/**
	 * 修改产品图片的默认值，将产品id为productId，图片id为imageId的变为默认，其他的变为不是默认的
	 * 
	 * @param productSkuId
	 * @param imageId
	 * @throws ServiceException
	 */
	void modifyImageDefault(Long productSkuId, Long imageId) throws ServiceException;

	/**
	 * 获取某个SKU下图片的最大排序
	 * 
	 * @param productSkuId
	 * @return
	 * @throws ServiceException
	 */
	int findMaxSortNoBySkuId(Long productSkuId) throws ServiceException;

	/**
	 * 是否图片已经存在10个
	 * @param productSkuId
	 * @return
	 * @throws ServiceException
	 */
	boolean isLimitImage(Long productSkuId) throws ServiceException;
	
    /**
     * 根据产品Id查找图片
     * @param productId
     * @return
     * @throws ServiceException
     */
    List<ProductImageDraft> findByProductId(Long productId) throws ServiceException;
    
    
    /**
     * 批量删除图片
     * @param imageIds
     * @throws ServiceException
     */
    void deleteImageBatchById(List<Long> imageIds) throws ServiceException;
}