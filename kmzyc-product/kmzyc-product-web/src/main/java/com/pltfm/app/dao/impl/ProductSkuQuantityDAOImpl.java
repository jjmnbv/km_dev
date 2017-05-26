package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductSkuQuantityDAO;
import com.pltfm.app.vobject.ProductSkuQuantity;
import com.pltfm.app.vobject.ProductSkuQuantityExample;

@Repository("productSkuQuantityDao")
public class ProductSkuQuantityDAOImpl extends BaseDao implements ProductSkuQuantityDAO {

    public int countByExample(ProductSkuQuantityExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("PRODUCT_SKU_QUANTITY.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ProductSkuQuantityExample example) throws SQLException {
        return sqlMapClient.delete("PRODUCT_SKU_QUANTITY.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long productSkuQuantityId) throws SQLException {
        ProductSkuQuantity key = new ProductSkuQuantity();
        key.setProductSkuQuantityId(productSkuQuantityId);
        return sqlMapClient.delete("PRODUCT_SKU_QUANTITY.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public void insert(ProductSkuQuantity record) throws SQLException {
        sqlMapClient.insert("PRODUCT_SKU_QUANTITY.ibatorgenerated_insert", record);
    }

    public void insertSelective(ProductSkuQuantity record) throws SQLException {
        sqlMapClient.insert("PRODUCT_SKU_QUANTITY.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ProductSkuQuantityExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU_QUANTITY.ibatorgenerated_selectByExample", example);
    }

    public List<ProductSkuQuantity> selectByExampleList(ProductSkuQuantityExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU_QUANTITY.ibatorgenerated_selectByListExample", example);
    }

    public ProductSkuQuantity selectByPrimaryKey(Long productSkuQuantityId) throws SQLException {
        ProductSkuQuantity key = new ProductSkuQuantity();
        key.setProductSkuQuantityId(productSkuQuantityId);
        ProductSkuQuantity record = (ProductSkuQuantity) sqlMapClient.queryForObject("PRODUCT_SKU_QUANTITY.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(ProductSkuQuantity record, ProductSkuQuantityExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PRODUCT_SKU_QUANTITY.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(ProductSkuQuantity record, ProductSkuQuantityExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PRODUCT_SKU_QUANTITY.ibatorgenerated_updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(ProductSkuQuantity record) throws SQLException {
        return sqlMapClient.update("PRODUCT_SKU_QUANTITY.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(ProductSkuQuantity record) throws SQLException {
        return sqlMapClient.update("PRODUCT_SKU_QUANTITY.ibatorgenerated_updateByPrimaryKey", record);
    }

    private static class UpdateByExampleParms extends ProductSkuQuantityExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductSkuQuantityExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public List selectByExample(ProductSkuQuantityExample example, int pageNo, int pageSize) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU_QUANTITY.ibatorgenerated_selectByExample", (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    public Map<Long, ProductSkuQuantity> getLastSaleSkuIdMap(List<Long> skuIdList) throws SQLException {
        return sqlMapClient.queryForMap("PRODUCT_SKU_QUANTITY.getLastSaleSkuIdMap", skuIdList, "productSkuId");
    }

    @Override
    public void batchUpdate(List<ProductSkuQuantity> list) throws SQLException {
        batchUpdate(list, "PRODUCT_SKU_QUANTITY.batchUpdateSaleQuantityBySkuCode");
    }

    @Override
    public void batchInsert(List<ProductSkuQuantity> list) throws SQLException {
        batchinsert(list, "PRODUCT_SKU_QUANTITY.ibatorgenerated_insertSelective");
    }
}