package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.Map;

import com.km.framework.page.Pagination;
import com.pltfm.app.vobject.ProductStock;



/**
 * 供应商独立库存管理 --商品库存操作dao
 * @author Administrator
 *
 */
public interface ProductStockDao {
	
	/**
	 * 查询库存列表
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	Pagination findProductStockListByPage(Pagination page)throws SQLException ;

	/**
	 * 新增一条库存记录
	 * @param para
	 * @throws SQLException
	 */
	void insert(ProductStock para) throws SQLException;

	/**
	 * 按库存Id查询库存实体
	 * @param stockId
	 * @return
	 * @throws SQLException
	 */
	ProductStock queryProductStockByStockId(String stockId) throws SQLException;

	/**
	 * 修改库存记录
	 * @param para
	 * @return
	 * @throws SQLException
	 */
	int update(ProductStock para) throws SQLException;

	/**
	 * 根据仓库id和skuNo查询是否已有库存记录
	 * @param condition
	 * @return
	 * @throws SQLException
	 */
	int queryIsExistSkuStockBySkuNo(Map condition) throws SQLException;
	
}