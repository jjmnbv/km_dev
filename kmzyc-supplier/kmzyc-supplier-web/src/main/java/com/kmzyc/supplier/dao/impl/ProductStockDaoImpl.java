package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.dao.ProductStockDao;
import com.pltfm.app.vobject.ProductStock;

/**
 * 
 * @author Administrator
 *
 */
@Repository("productStockDao")
public class ProductStockDaoImpl extends BaseDAO implements ProductStockDao{

	@Override
	public Pagination findProductStockListByPage(Pagination page) throws SQLException {
		return findPaginationByPage(sqlMapClient, "PRODUCT_STOCK.queryProductStockListByCondition",
				"PRODUCT_STOCK.queryProductStockCountByCondition", page);
	}

	@Override
	public void insert(ProductStock para) throws SQLException {
		sqlMapClient.insert("PRODUCT_STOCK.insertProductStock", para);
	}

	@Override
	public int update(ProductStock para) throws SQLException {
		return sqlMapClient.update("PRODUCT_STOCK.updateProductStock", para);
	}

	@Override
	public ProductStock queryProductStockByStockId(String stockId) throws SQLException {
		return (ProductStock)sqlMapClient.queryForObject("PRODUCT_STOCK.queryProductStockByStockId",stockId);
	}

	@Override
	public int queryIsExistSkuStockBySkuNo(Map condition) throws SQLException {
		return (Integer)sqlMapClient.queryForObject("PRODUCT_STOCK.queryProductStockIsExist", condition);
	}

}