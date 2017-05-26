package com.kmzyc.promotion.app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.promotion.app.vobject.ProductSkuAttr;

/**
 * 产品SKU属性业务逻辑接口
 * 
 * @author humy
 * @since 2013-7-9
 */
@SuppressWarnings("unchecked")
public interface ProductSkuAttrService {
	/**
	 * 添加产品SKU属性
	 * 
	 * @param ProductSkuAttr
	 *            产品SKU属性基本信息
	 * @return
	 * @throws Exception
	 */
	public void addProductSkuAttr(ProductSkuAttr productSkuAttr) throws Exception;

	/**
	 * 查询SKU属性
	 * 
	 * @param productSkuAttr
	 * @return List<ProductSkuAttr>
	 * @throws Exception
	 */
	public List<ProductSkuAttr> queryProductSkuAttrList(ProductSkuAttr productSkuAttr) throws Exception;

	/**
	 * 删除产品SKU属性
	 * 
	 * @param productSkuId
	 *            产品SKU Id
	 * @return
	 * @throws Exception
	 */
	public void deleteProductSkuAttr(Long productSkuId) throws Exception;

	public void updateSkuAttrByProductSkuIdBatch(List list) throws Exception;

	List querySkuIdByAttr(Map condition) throws SQLException;

	public boolean queryByAttrValueId(Long attrValueId) throws Exception;

}
