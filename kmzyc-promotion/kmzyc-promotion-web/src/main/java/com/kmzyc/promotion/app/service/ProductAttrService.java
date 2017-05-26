package com.kmzyc.promotion.app.service;

import java.util.List;

import com.kmzyc.promotion.app.vobject.ProductAttr;

/**
 * 产品属性业务逻辑接口
 * 
 * @author humy
 * @since 2013-7-9
 */
public interface ProductAttrService {

	/**
	 * 增加产品属性
	 * 
	 * @param productAttr
	 *            产品属性基本信息
	 * @return
	 * @throws Exception
	 */
	void addProductAttr(ProductAttr productAttr) throws Exception;

	/**
	 * 查询产品属性
	 * 
	 * @param productAttr
	 *            产品属性基本信息
	 * @return List<ProductAttr>
	 * @throws Exception
	 *             异常
	 */
	List<ProductAttr> queryProductAttr(ProductAttr productAttr) throws Exception;

	/**
	 * 更新产品属性
	 * 
	 * @param productAttr
	 *            产品属性基本信息
	 * @return
	 * @throws Exception
	 */
	void updateProductAttr(ProductAttr productAttr) throws Exception;

	/**
	 * 删除产品属性
	 * 
	 * @param productAttr
	 *            产品属性基本信息
	 * @return
	 * @throws Exception
	 */
	void deleteProductAttr(ProductAttr productAttr) throws Exception;
}
