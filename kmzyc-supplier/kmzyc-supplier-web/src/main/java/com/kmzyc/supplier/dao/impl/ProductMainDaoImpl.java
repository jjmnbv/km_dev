package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.dao.ProductMainDao;
import com.kmzyc.supplier.vo.ProductMainVo;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductSku;

@Repository("productMainDao")
public class ProductMainDaoImpl extends BaseDAO implements ProductMainDao {

	@Override
	public Pagination findProductMainListByPage(Pagination page) throws SQLException {
		return findPaginationByPage(sqlMapClient, "PRODUCT.selectProductMainListByMerchantId",
				"PRODUCT.selectCountForProductMainByMerchantId", page);
	}

	@Override
	public Pagination queryUnrelationWithShopCategoryByPage(Pagination page) throws SQLException {
		return findPaginationByPage(sqlMapClient, "PRODUCT.queryUnRelationWithShopCategory",
				"PRODUCT.queryUnRelationCount", page);
	}

	@Override
	public Pagination queryRelationWithShopCategoryByPage(Pagination page) throws SQLException {
		return findPaginationByPage(sqlMapClient, "PRODUCT.queryAlreadyRelationWithShopCategory",
				"PRODUCT.queryRelationCount", page);
	}
	
	@Override
	public ProductMainVo findProductMainVoByProductId(Long productId) throws SQLException {
		return (ProductMainVo) sqlMapClient.queryForObject("PRODUCT.findProductMainVoByProductId", productId);
	}

	@Override
	public Product selectByPrimaryKey(Long productId) throws SQLException {
		Product product = new Product();
		product.setId(productId);
        return (Product) sqlMapClient.queryForObject("PRODUCT.product_selectByPrimaryKey2", product);
	}

	@Override
	public int updateObject(Product product) throws SQLException {
		return sqlMapClient.update("PRODUCT.updateObject", product);
	}

	@Override
	public List<ProductSku> findSingleProductAndSkusAndAttrValues(Product product) throws SQLException {
		return sqlMapClient.queryForList("PRODUCT.findSingleProductAndSkusAndAttrValues",product);
	}

	@Override
	public int updateBatch(List<Product> list) throws SQLException {
		return super.batchUpdateData("PRODUCT.updateObject", list);
	}

}