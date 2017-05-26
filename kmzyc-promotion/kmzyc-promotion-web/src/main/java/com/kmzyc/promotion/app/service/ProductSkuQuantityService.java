package com.kmzyc.promotion.app.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.promotion.app.vobject.ProductSkuQuantity;

public interface ProductSkuQuantityService {

	/**
	 * 每个月定时更新product_sku_quanlity中的数量
	 * 
	 * @throws SQLException
	 */

	public void updateProductSkuQuanlityEveryMonth(Date date) throws Exception;

	/**
	 * 根据skuId 查询表product_sku_quantity 的对应的数量
	 * 
	 * @param skuIdList
	 * @return
	 * @throws Exception
	 */
	public Map<Long, ProductSkuQuantity> getLastSaleSkuIdMap(List<Long> skuIdList) throws Exception;

}
