package com.pltfm.app.dao.impl;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductStockDAO;
import com.pltfm.app.vobject.CarryStockOutDetailVO;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.ProductStockExample;
import com.pltfm.app.vobject.ProductStockPurchase;
import com.pltfm.app.vobject.StockOutAndDetail;

@Repository("productStockDao")
public class ProductStockDAOImpl extends BaseDao implements ProductStockDAO {

    public int countByExample(ProductStockExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("PRODUCT_STOCK.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int countByExampleByProductTitle(ProductStockExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("PRODUCT_STOCK.ibatorgenerated_countByExampleByProductTitle", example);
        return count.intValue();
    }

    public int countByExampleForAlarm(ProductStockExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("PRODUCT_STOCK.ibatorgenerated_countByExample2", example);
        return count.intValue();
    }

    public int deleteByExample(ProductStockExample example) throws SQLException {
        return sqlMapClient.delete("PRODUCT_STOCK.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long stockId) throws SQLException {
        ProductStock key = new ProductStock();
        key.setStockId(stockId);
        return sqlMapClient.delete("PRODUCT_STOCK.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public void insert(ProductStock record) throws SQLException {
        sqlMapClient.insert("PRODUCT_STOCK.ibatorgenerated_insert", record);
    }

    public Long insertProductStock(ProductStock stock) throws SQLException {
        return (Long) getSqlMapClientTemplate().insert("PRODUCT_STOCK.ibatorgenerated_insert", stock);

    }

    public void insertSelective(ProductStock record) throws SQLException {
        sqlMapClient.insert("PRODUCT_STOCK.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ProductStockExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.ibatorgenerated_selectByExample", example);
    }

    public List selectByExample(ProductStockExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.ibatorgenerated_selectByExample", example, skip, max);
    }

    public List selectByExampleByProductTitle(ProductStockExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.ibatorgenerated_selectByExampleByProductTitle", example, skip, max);
    }

    public List selectByExampleForAlarm(ProductStockExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.ibatorgenerated_selectByExample2", example, skip, max);
    }

    public ProductStock selectByPrimaryKey(Long stockId) throws SQLException {
        ProductStock key = new ProductStock();
        key.setStockId(stockId);
        return (ProductStock) sqlMapClient.queryForObject("PRODUCT_STOCK.ibatorgenerated_selectByPrimaryKey", key);
    }

    public int updateByExampleSelective(ProductStock record, ProductStockExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PRODUCT_STOCK.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(ProductStock record, ProductStockExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PRODUCT_STOCK.ibatorgenerated_updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(ProductStock record) throws SQLException {
        return sqlMapClient.update("PRODUCT_STOCK.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(ProductStock record) throws SQLException {
        return sqlMapClient.update("PRODUCT_STOCK.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey2(ProductStock record) throws SQLException {
        return sqlMapClient.update("PRODUCT_STOCK.ibatorgenerated_updateByPrimaryKeySelective2", record);
    }

    private static class UpdateByExampleParms extends ProductStockExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductStockExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    public int increaseOrderQuantity(ProductStock stock) throws SQLException {
        return sqlMapClient.update("PRODUCT_STOCK.increaseOrderQuantity", stock);
    }

    public int decreaseOrderQuantity(ProductStock stock) throws SQLException {
        return sqlMapClient.update("PRODUCT_STOCK.decreaseOrderQuantity", stock);
    }

    public int batchUpdateForStock(String statementName, List<ProductStock> parameterList) throws SQLException {
        return super.batchUpdateData(statementName, parameterList);
    }

    @Override
    public List<ProductStock> remoteSelectStockQuantity(List<ProductStock> stockList) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.remoteSelectStockQuantity", stockList);
    }

    @Override
    public int batchAddForStock(List<ProductStock> parameterList) throws SQLException {
        return super.batchinsertNt(parameterList, "PRODUCT_STOCK.ibatorgenerated_insert");
    }

    @Override
    public List<ProductStock> selectProductStockList(List<Map<String, Long>> stockList) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.selectProductStockList", stockList);
    }

    @Override
    public long checkStockByWarehouseList(List<ProductStock> stockList) throws SQLException {
        Long count = (Long) sqlMapClient.queryForObject("PRODUCT_STOCK.checkStockByWarehouseList", stockList);
        return count.longValue();
    }

    @Override
    public int batchIncreaseOrderQuantity(ProductStock stock) throws SQLException {
        return sqlMapClient.update("PRODUCT_STOCK.batchIncreaseOrderQuantity", stock);
    }

    @Override
    public Integer selectRemainQuantityBySkuId(Long skuId) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("PRODUCT_STOCK.selectRemainQuantity", skuId);
    }

    @Override
    public List<ProductStock> selectStockListBySkuId(Long skuId) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.selectStockListBySkuId", skuId);
    }

    @Override
    public Map<Long, ProductStock> selectProductStockListByStockList(List<ProductStock> stockList) throws SQLException {
        return sqlMapClient.queryForMap("PRODUCT_STOCK.selectProductStockListByStockList", stockList, "stockId");
    }

    @Override
    public List<ProductStock> checkAvailableStockForUnLock(List<ProductStock> stockList) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.checkAvailableStockForUnLock", stockList);
    }

    @Override
    public int batchDecreaseOrderQuantity(List<ProductStock> stockList) throws SQLException {
        return super.batchUpdateNt(stockList, "PRODUCT_STOCK.batchDecreaseOrderQuantity");
    }

    @Override
    public Long findProductSkuStockCountBySkuId(Long productSkuId) throws SQLException {
        Object obj = sqlMapClient.queryForObject("PRODUCT_STOCK.queryRealStockBySkuId", productSkuId);
        return obj == null ? null : (Long) obj;
    }

    @Override
    public List<ProductStock> checkStockOutForOrder(List<ProductStock> stockList) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.checkStockOutForOrder", stockList);
    }

    @Override
    public List<ProductStock> checkStockOutForOther(List<ProductStock> stockList) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.checkStockOutForOther", stockList);
    }

    public List<ProductStock> findProductStockListByWarehouseSkuCode(List<CarryStockOutDetailVO> caOutDetailVOs) 
            throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.findListByWareIdSkuCode", caOutDetailVOs);
    }

    @Override
    public List<StockOutAndDetail> findStockIdMapByWarehouseAndSkuId(List<StockOutAndDetail> detailList)
            throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.findStockIdMapByWarehouseAndSkuId", detailList);
    }

    @Override
    public List<ProductStock> checkAvailableStockForLockForAfter(List<ProductStock> stockList) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.checkAvailableStockForLockForAfter", stockList);
    }

    @Override
    public int batchIncreaseOrderQuantityForAfter(ProductStock stock) throws SQLException {
        return sqlMapClient.update("PRODUCT_STOCK.batchIncreaseOrderQuantityForAfter", stock);
    }

    @Override
    public Map<Long, ProductStock> getProductStockMap(List<ProductStock> stockList) throws SQLException {
        return sqlMapClient.queryForMap("PRODUCT_STOCK.getProductStockList", stockList, "stockId");
    }

    @Override
    public List<ProductStock> getProductStockList(List<ProductStock> stockList) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.getProductStockList", stockList);
    }

    @Override
    public List<Long> findIsExistSkuStock(List<ProductStock> stockList) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.findIsExistSkuStock", stockList);
    }

    @Override
    public int updateSkuStockForSupplierBatch(List<ProductStock> stockList) throws SQLException {
        return super.batchUpdateData("PRODUCT_STOCK.updateSkuStockForSupplier", stockList);
    }

    public Map<String, Object> getWarehouseIdBySkuCode(List<ProductStock> list) throws SQLException {
        return sqlMapClient.queryForMap("PRODUCT_STOCK.getWarehouseIdBySkuCode", list, "SKU_ATT_VALUE", "WAREHOUSE_ID");
    }

    @Override
    public List<ProductStock> findByProductId(Long productId) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_STOCK.findByProductId", productId);
    }

    @Override
    public void batchUpdatePromotionSell(List<ProductStock> stockList) throws SQLException {
        batchUpdate(stockList, "PRODUCT_STOCK.batchUpdatePromotionSell");
    }

    @Override
    public void batchUpdatePromotionSellMin(List<ProductStock> stockList) throws SQLException {
        batchUpdate(stockList, "PRODUCT_STOCK.batchUnlockUpdatePromotionSell");
    }

}