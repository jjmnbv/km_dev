package com.kmzyc.promotion.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.promotion.app.vobject.CarryStockOutDetailVO;
import com.kmzyc.promotion.app.vobject.ProductStock;
import com.kmzyc.promotion.app.vobject.ProductStockExample;
import com.kmzyc.promotion.app.vobject.ProductStockPurchase;
import com.kmzyc.promotion.app.vobject.StockOutAndDetail;

@SuppressWarnings("unchecked")
public interface ProductStockDAO {
	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_STOCK
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	int countByExample(ProductStockExample example) throws SQLException;

	int countByExampleByProductTitle(ProductStockExample example) throws SQLException;

	int countByExampleForAlarm(ProductStockExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_STOCK
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	int deleteByExample(ProductStockExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_STOCK
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	int deleteByPrimaryKey(Long stockId) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_STOCK
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	void insert(ProductStock record) throws SQLException;

	public Long insertProductStock(ProductStock stock) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_STOCK
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	void insertSelective(ProductStock record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_STOCK
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	List selectByExample(ProductStockExample example) throws SQLException;

	public List selectByExample(ProductStockExample example, int skip, int max) throws SQLException;

	public List selectByExampleByProductTitle(ProductStockExample example, int skip, int max) throws SQLException;

	public List selectByExampleForAlarm(ProductStockExample example, int skip, int max) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_STOCK
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	ProductStock selectByPrimaryKey(Long stockId) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_STOCK
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	int updateByExampleSelective(ProductStock record, ProductStockExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_STOCK
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	int updateByExample(ProductStock record, ProductStockExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_STOCK
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	int updateByPrimaryKeySelective(ProductStock record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table PRODUCT_STOCK
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	int updateByPrimaryKey(ProductStock record) throws SQLException;

	int updateByPrimaryKey2(ProductStock record) throws SQLException;

	List<ProductStock> checkProductStockBySku(ProductStock stock) throws SQLException;

	public int increaseStockQuantity(ProductStock stock) throws SQLException;

	public int decreaseStockQuantity(ProductStock stock) throws SQLException;

	public int increaseInTransitQuantity(ProductStock stock) throws SQLException;

	public int decreaseInTransitQuantity(ProductStock stock) throws SQLException;

	public int increaseOrderQuantity(ProductStock stock) throws SQLException;

	public int batchIncreaseOrderQuantity(ProductStock stock) throws SQLException;

	public int batchIncreaseOrderQuantityForAfter(ProductStock stock) throws SQLException;

	public int batchDecreaseOrderQuantity(List<ProductStock> stockList) throws SQLException;

	public int decreaseOrderQuantity(ProductStock stock) throws SQLException;

	/**
	 * 批量添加新的入库信息
	 * 
	 * @param parameterList
	 *            新的入库信息集合
	 * @throws SQLException
	 */
	public int batchAddForStock(List<ProductStock> parameterList) throws SQLException;

	/**
	 * 批量更新库存
	 * 
	 * @param statementName
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 */
	public int batchUpdateForStock(String statementName, List<ProductStock> parameterList) throws SQLException;

	/**
	 * 根据仓库和产品的skuId查询库存信息
	 * 
	 * @param warehouseId
	 * @param product_sku_id
	 * @return
	 * @author luoyi
	 */
	public ProductStock selectByWareAndSkuId(Long warehouseId, Long product_sku_id) throws SQLException;

	/**
	 * 订单系统远程调用查询sku库存数量
	 * 
	 * @param stockList
	 * @return
	 * @throws SQLException
	 */
	public List<ProductStock> remoteSelectStockQuantity(List<ProductStock> stockList) throws SQLException;

	/**
	 * 根据产库id 以及sku id 查询产品库存集合出来
	 * 
	 * @param stockList
	 * @return
	 * @throws SQLException
	 */

	public List<ProductStock> selectProdcutStockList(List<Map<String, Long>> stockList) throws SQLException;

	/**
	 * 仓库停用时，检测仓库集合中是否有与库存信息关联的数据
	 * 
	 * @param stockList
	 * @return
	 * @throws SQLException
	 */
	public long checkStockByWarehouseList(List<ProductStock> stockList) throws SQLException;

	public int batchUpdateForStockPurchase(String statementName, List<ProductStockPurchase> parameterList)
			throws SQLException;

	/**
	 * 根据skuId 查询出 库存表中 对于的数量 即stockQuanttiy- order_quality 数量
	 * 
	 * @param skuId
	 * @return
	 * @throws SQLException
	 */
	public Integer selectRemainQuantityBySkuId(Long skuId) throws SQLException;

	/**
	 * 根据skuId 查询出此sku的所有库存列表
	 * 
	 * @param skuId
	 * @return
	 * @throws SQLException
	 */
	public List<ProductStock> selectStockListBySkuId(Long skuId) throws SQLException;

	public Map<Long, ProductStock> selectProdcutStockListByStockList(List<ProductStock> stockList) throws SQLException;

	/**
	 * 检查锁库存的sku是否符合条件（不符合条件的：锁库存数量>可用库存量）
	 * 
	 * @param stockList
	 * @return
	 * @throws SQLException
	 */
	public List<ProductStock> checkAvailableStockForLock(List<ProductStock> stockList) throws SQLException;

	/**
	 * 售后检查锁库存的sku是否符合条件（不符合条件的：锁库存数量>可用库存量）
	 * 
	 * @param stockList
	 * @return
	 * @throws SQLException
	 */
	public List<ProductStock> checkAvailableStockForLockForAfter(List<ProductStock> stockList) throws SQLException;

	/**
	 * 检查订单出库、换货出库是否符合出库条件
	 * 
	 * @param stockList
	 * @return
	 * @throws SQLException
	 */
	public List<ProductStock> checkStockOutForOrder(List<ProductStock> stockList) throws SQLException;

	/**
	 * 检查其它出库是否符合出库条件
	 * 
	 * @param stockList
	 * @return
	 * @throws SQLException
	 */
	public List<ProductStock> checkStockOutForOther(List<ProductStock> stockList) throws SQLException;

	public List<ProductStock> checkAvailableStockForUnLock(List<ProductStock> stockList) throws SQLException;

	public Long findProductSkuStockCountBySkuId(Long productSkuId) throws SQLException;

	public int countByExampleByUser(ProductStockExample ps) throws SQLException;

	public List<ProductStock> selectByExampleByUser(ProductStockExample ps, int skip, int max) throws SQLException;

	/**
	 * 根据仓库ID和skuCode查找库存集合
	 * 
	 * @param caOutDetailVOs
	 * @return
	 * @throws SQLException
	 */
	public abstract List<ProductStock> findProductStockListByWarehouseSkuCode(List<CarryStockOutDetailVO> caOutDetailVOs)
			throws SQLException;

	public Integer getProductStockCount(List<ProductStock> stockList) throws SQLException;

	public List<StockOutAndDetail> findStockIdMapByWarehouseAndSkuId(List<StockOutAndDetail> detailList)
			throws SQLException;

	/**
	 * 获取库存列表
	 * 
	 * @param stockList
	 * @return
	 * @throws SQLException
	 */
	public Map<Long, ProductStock> getProductStockMap(List<ProductStock> stockList) throws SQLException;

	public List<ProductStock> getProductStockList(List<ProductStock> stockList) throws SQLException;
}