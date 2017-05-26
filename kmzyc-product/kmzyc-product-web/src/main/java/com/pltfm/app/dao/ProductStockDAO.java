package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.CarryStockOutDetailVO;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.ProductStockExample;
import com.pltfm.app.vobject.ProductStockPurchase;
import com.pltfm.app.vobject.StockOutAndDetail;
import com.kmzyc.commons.exception.ServiceException;

public interface ProductStockDAO {

    int countByExample(ProductStockExample example) throws SQLException;

    int countByExampleByProductTitle(ProductStockExample example) throws SQLException;

    int countByExampleForAlarm(ProductStockExample example) throws SQLException;

    int deleteByExample(ProductStockExample example) throws SQLException;

    int deleteByPrimaryKey(Long stockId) throws SQLException;

    void insert(ProductStock record) throws SQLException;

    Long insertProductStock(ProductStock stock) throws SQLException;

    void insertSelective(ProductStock record) throws SQLException;

    List selectByExample(ProductStockExample example) throws SQLException;

    List selectByExample(ProductStockExample example, int skip, int max) throws SQLException;

    List selectByExampleByProductTitle(ProductStockExample example, int skip, int max) throws SQLException;

    List selectByExampleForAlarm(ProductStockExample example, int skip, int max) throws SQLException;

    ProductStock selectByPrimaryKey(Long stockId) throws SQLException;

    int updateByExampleSelective(ProductStock record, ProductStockExample example) throws SQLException;

    int updateByExample(ProductStock record, ProductStockExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProductStock record) throws SQLException;

    int updateByPrimaryKey(ProductStock record) throws SQLException;

    int updateByPrimaryKey2(ProductStock record) throws SQLException;

    int increaseOrderQuantity(ProductStock stock) throws SQLException;

    int batchIncreaseOrderQuantity(ProductStock stock) throws SQLException;

    int batchIncreaseOrderQuantityForAfter(ProductStock stock) throws SQLException;

    int batchDecreaseOrderQuantity(List<ProductStock> stockList) throws SQLException;

    int decreaseOrderQuantity(ProductStock stock) throws SQLException;

    /**
     * 批量添加新的入库信息
     *
     * @param parameterList 新的入库信息集合
     * @throws SQLException
     */
    int batchAddForStock(List<ProductStock> parameterList) throws SQLException;

    /**
     * 批量更新库存
     *
     * @param statementName
     * @param parameterList
     * @return
     * @throws SQLException
     */
    int batchUpdateForStock(String statementName, List<ProductStock> parameterList) throws SQLException;

    /**
     * 订单系统远程调用查询sku库存数量
     *
     * @param stockList
     * @return
     * @throws SQLException
     */
    List<ProductStock> remoteSelectStockQuantity(List<ProductStock> stockList) throws SQLException;

    /**
     * 根据产库id 以及sku id 查询产品库存集合出来
     *
     * @param stockList
     * @return
     * @throws SQLException
     */
    List<ProductStock> selectProductStockList(List<Map<String, Long>> stockList) throws SQLException;

    /**
     * 仓库停用时，检测仓库集合中是否有与库存信息关联的数据
     *
     * @param stockList
     * @return
     * @throws SQLException
     */
    long checkStockByWarehouseList(List<ProductStock> stockList) throws SQLException;

    /**
     * 根据skuId 查询出 库存表中 对于的数量   即stockQuanttiy- order_quality 数量
     *
     * @param skuId
     * @return
     * @throws SQLException
     */
    Integer selectRemainQuantityBySkuId(Long skuId) throws SQLException;

    /**
     * 根据skuId 查询出此sku的所有库存列表
     *
     * @param skuId
     * @return
     * @throws SQLException
     */
    List<ProductStock> selectStockListBySkuId(Long skuId) throws SQLException;

    Map<Long, ProductStock> selectProductStockListByStockList(List<ProductStock> stockList) throws SQLException;

    /**
     * 售后检查锁库存的sku是否符合条件（不符合条件的：锁库存数量>可用库存量）
     *
     * @param stockList
     * @return
     * @throws SQLException
     */
    List<ProductStock> checkAvailableStockForLockForAfter(List<ProductStock> stockList) throws SQLException;

    /**
     * 检查订单出库、换货出库是否符合出库条件
     *
     * @param stockList
     * @return
     * @throws SQLException
     */
    List<ProductStock> checkStockOutForOrder(List<ProductStock> stockList) throws SQLException;

    /**
     * 检查其它出库是否符合出库条件
     *
     * @param stockList
     * @return
     * @throws SQLException
     */
    List<ProductStock> checkStockOutForOther(List<ProductStock> stockList) throws SQLException;

    List<ProductStock> checkAvailableStockForUnLock(List<ProductStock> stockList) throws SQLException;

    Long findProductSkuStockCountBySkuId(Long productSkuId) throws SQLException;

    /**
     * 根据仓库ID和skuCode查找库存集合
     *
     * @param caOutDetailVOs
     * @return
     * @throws SQLException
     */
    List<ProductStock> findProductStockListByWarehouseSkuCode(List<CarryStockOutDetailVO> caOutDetailVOs) throws SQLException;

    List<StockOutAndDetail> findStockIdMapByWarehouseAndSkuId(List<StockOutAndDetail> detailList) throws SQLException;

    /**
     * 获取库存列表
     *
     * @param stockList
     * @return
     * @throws SQLException
     */
    Map<Long, ProductStock> getProductStockMap(List<ProductStock> stockList) throws SQLException;

    List<ProductStock> getProductStockList(List<ProductStock> stockList) throws SQLException;

    /**
     * 查询已存在的库存（供应商改版）
     *
     * @param stockList
     * @return
     * @throws SQLException
     */
    List<Long> findIsExistSkuStock(List<ProductStock> stockList) throws SQLException;

    /**
     * 更新SKU库存   （供应商改版）
     *
     * @param stockList
     * @throws SQLException
     */
    int updateSkuStockForSupplierBatch(List<ProductStock> stockList) throws SQLException;

    /**
     * 根据skuCode列表获取对应的最大库存仓库id
     *
     * @param list skuCode列表
     * @return
     * @throws SQLException
     */
    Map<String, Object> getWarehouseIdBySkuCode(List<ProductStock> list) throws SQLException;

    /**
     * 根据产品ID查询库存信息（SKU只取一条记录最大的记录）
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    List<ProductStock> findByProductId(Long productId) throws SQLException;

    /**
     * 订单结算后，更新促销活动商品销售数，同时更新部分缓存
     *
     * @param stockList
     * @return
     */
    void batchUpdatePromotionSell(List<ProductStock> stockList) throws SQLException;

    /**
     * 订单取消后，更新促销活动商品销售数，同时更新部分缓存
     *
     * @param stockList
     * @return
     */
    void batchUpdatePromotionSellMin(List<ProductStock> stockList) throws SQLException;

}