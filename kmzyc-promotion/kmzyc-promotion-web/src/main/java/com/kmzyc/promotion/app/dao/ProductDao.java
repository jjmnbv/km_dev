package com.kmzyc.promotion.app.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.kmzyc.promotion.app.vobject.Product;
import com.kmzyc.promotion.app.vobject.ProductAttr;
import com.kmzyc.promotion.app.vobject.ProductExample;

@SuppressWarnings("unchecked")
public interface ProductDao {

	/**
	 * 保存商品基本信息
	 * 
	 * @param product
	 */
	public abstract Long insertProduct(Product product);

	public abstract List<Product> getProducts(Product product);

	int countByExample(ProductExample example) throws SQLException;

	List selectByExample(ProductExample example, int skip, int max) throws SQLException;

	List selectByExampleForPrice(ProductExample example, int skip, int max) throws SQLException;

	List selectForCertificateByExample(ProductExample example, int skip, int max) throws SQLException;

	Product selectByPrimaryKey(Long productId) throws SQLException;

	public int selectProductByName(String productName) throws SQLException;

	public String queryMaxProductNo(String inputCode) throws SQLException;

	/**
	 * 修改商品基本信息
	 * 
	 * @param record
	 * @param example
	 * @return
	 * @throws SQLException
	 */
	public int updateByExample(Product record, ProductExample example) throws SQLException;

	int updateByPrimaryKey(Product product) throws SQLException;

	/**
	 * 批量更新
	 * 
	 * @param statementName
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 */
	public int batchUpdateForProduct(String statementName, List<Product> parameterList) throws SQLException;

	public List<Product> selectSkuByProductId(Product product) throws SQLException;

	public long deleteProductById(Long productId) throws SQLException;

	/**
	 * 单个产品上架
	 */
	public int updateShelfStatus(Product product) throws SQLException;

	/**
	 * 查询可以上架的产品个数
	 */
	public List selectCountShelf(ProductExample Productex) throws SQLException;

	public int batchAuditProduct(List<Long> productIdList) throws SQLException;

	public Product getProductBySku(Long productSkuId) throws Exception;

	public Map<Long, ProductAttr> checkOperationAttr(List<Long> productIdList) throws SQLException;

	/**
	 * 根据产品编号 ，查询产品信息
	 * 
	 * @param productNo
	 * @return
	 * @throws SQLException
	 */
	public Product queryProductByProductNo(String productNo) throws SQLException;

	/**
	 * 查询sku条数
	 * 
	 * @param example
	 * @return
	 * @throws SQLException
	 */
	public int countBySkuExample(ProductExample example) throws SQLException;

	/**
	 * 查询sku数据
	 * 
	 * @param example
	 * @param skip
	 * @param max
	 * @return
	 * @throws SQLException
	 */
	public List selectBySkuExample(ProductExample example, int skip, int max) throws SQLException;

	public List selectByExampleForExport(ProductExample example) throws SQLException;

	public List<Integer> getProductIdByBrandId(Long brandId) throws SQLException;

	public int countByExampleForExport(ProductExample example) throws SQLException;

	/**
	 * 根据skuid查询产品main信息
	 * 
	 * @param skuId
	 * @return
	 * @throws SQLException
	 */
	public Product selectBySkuId(Long productSkuId) throws SQLException;
	
	public List<Map> getProducts(Collection<?> skuIds) throws SQLException;

	
}