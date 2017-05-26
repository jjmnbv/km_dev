package com.kmzyc.supplier.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kmzyc.commons.exception.ServiceException;
import com.km.framework.page.Pagination;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ViewProductSku;
/**
 * 产品SKU业务逻辑接口
 *
 */
public interface ProductSkuService {
	
	/**
	 * SKU列表
	 * @param supplierId
	 * @param viewProductSku
	 * @param page
	 * @throws Exception
	 */
	Pagination searchPage(Long supplierId,ViewProductSku viewProductSku,Pagination page) throws ServiceException;
	
	/**
	 * SKU列表
	 * @param supplierId
	 * @param viewProductSku
	 * @param page
	 * @throws Exception
	 */
	Pagination searchForFreightPage(Long supplierId,ViewProductSku viewProductSku,Pagination page) throws ServiceException;
	
	/**
	 * 查询所有的可供添加库存的sku商品
	 * @param supplierId
	 * @param viewProductSku
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Pagination searchEnableStockByPage(Long supplierId,ViewProductSku viewProductSku,Pagination page) throws ServiceException;
	
	/**
	 * 查询产品SKU
	 * @param productSku 产品SKU基本信息
	 * @return List<ProductSku>
	 * @throws Exception
	 */
	List<ProductSku> queryProductSkuList(ProductSku productSku) throws ServiceException;
	
	/**
	 * 查看正式商品图片
	 * @param productSkuId
	 * @return
	 * @throws Exception
	 */
	List<ProductImage> findAllBySkuId(Long productSkuId) throws ServiceException;
	
	/**
	 * 根据产品Id获取SKU商品，并关联出SKU属性
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	List<ViewProductSku> findProductAndSkuAttrByProductId(Long productId) throws ServiceException;
	
	/**
	 * 获取有效的SKU
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	List<ProductSku> findIsValidProductSkusByProductId(Long productId) throws ServiceException;
	
	/**
	 * 查询单个SKU信息
	 * @param productSkuId
	 * @return
	 * @throws Exception
	 */
	ViewProductSku findSingleSkuAndAttr(Long productSkuId) throws ServiceException;

    /**
     * 修改sku
     * @param productSku
     * @return
     * @throws ServiceException
     */
	int updateProductSku(ProductSku productSku) throws ServiceException;

    /**
     * 上架
     * @param productSku
     * @throws ServiceException
     */
	void upShelfForSku(ProductSku productSku) throws ServiceException;
	
	 /**
	 * 查询新增的SKU属性并做处理
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	Map<Long,Set<String>> findAndChangeSkuNewAttr(Long productId) throws ServiceException;
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 * @throws ServiceException
	 */
	int updateProductSkuList(List<ProductSku> list) throws ServiceException;
	
	/**
	 * 批量删除
	 * @param productSkuIds
	 * @return
	 * @throws ServiceException
	 */
	int deleteProductSkuByIds(List<Long> productSkuIds) throws ServiceException;
	
	/**
	 * 获取SKU最大编号
	 * @param cateCode
	 * @return
	 * @throws ServiceException
	 */
	String findMaxSkuCodeByCateCode(String cateCode) throws ServiceException;
	
	/**
	 * 新增SKU
	 * @param productSku
	 * @throws ServiceException
	 */
	void insertProductSku(ProductSku productSku) throws ServiceException;
}