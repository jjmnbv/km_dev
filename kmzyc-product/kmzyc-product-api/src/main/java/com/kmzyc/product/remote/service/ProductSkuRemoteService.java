package com.kmzyc.product.remote.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ViewSkuAttr;

/**
 * 产品SKU外放接口
 * @author tanyunxing
 *
 */
public interface ProductSkuRemoteService {

	/**
	 * SKU属性值Id以及产品Id，查询SkuId
	 * 
	 * @param productId 产品Id
	 * @param valueId 属性值Id
	 * @return
	 * @throws ServiceException
	 */
	Long findSkuIdByAttrAndValue(Long productId,List<Long> valueId ) throws ServiceException;
	
	/**
	 * 根据产品Id获取所有SKU图片
	 * @return
	 * @throws ServiceException
	 */
	List<ProductSku> findImagesByProductId(Long productId) throws ServiceException;
	
	/**
	 * 根据产品Id获取所有SKU图片（草稿）
	 * @return
	 * @throws ServiceException
	 */
	List<ProductSku> findImagesDraftByProductId(Long productId) throws ServiceException;
	
	/**
	 * 根据产品Id获取相关SKU规格
	 * @param productId
	 * @return
	 * @throws ServiceException
	 */
	List<CategoryAttr> findSkuAttrByProductId(Long productId) throws ServiceException;
	
	/**
	 * 根据产品Id获取相关SKU规格（草稿）
	 * @param productId
	 * @return
	 * @throws ServiceException
	 */
	List<CategoryAttr> findSkuAttrDraftByProductId(Long productId) throws ServiceException;
	
	/**
	 * 根据SkuId获取SKU属性
	 * @param productSkuId
	 * @return
	 * @throws ServiceException
	 */
	List<ViewSkuAttr> findBySkuId(Long productSkuId) throws ServiceException;
	
	/**
	 * 根据SkuId获取SKU属性（草稿）
	 * @param productSkuId
	 * @return
	 * @throws ServiceException
	 */
	List<ViewSkuAttr> findDraftBySkuId(Long productSkuId) throws ServiceException;

    /**
     * 更新sku缓存
     *
     * @param productSkuIds 商品skuid集合
     * @throws ServiceException
     */
    void updateProductSkuCache(List<Long> productSkuIds) throws ServiceException;
}