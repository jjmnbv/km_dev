package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.pltfm.app.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ProductAttrDAO;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductAttrExample;

@Repository("productAttrDao")
public class ProductAttrDAOImpl extends BaseDao<ProductAttr> implements ProductAttrDAO {

    public int countByExample(ProductAttrExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("PRODUCT_ATTR.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ProductAttrExample example) throws SQLException {
        return sqlMapClient.delete("PRODUCT_ATTR.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long productAttrId) throws SQLException {
        ProductAttr key = new ProductAttr();
        key.setProductAttrId(productAttrId);
        return sqlMapClient.delete("PRODUCT_ATTR.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public void insert(ProductAttr record) throws SQLException {
        sqlMapClient.insert("PRODUCT_ATTR.ibatorgenerated_insert", record);
    }

    public void insertSelective(ProductAttr record) throws SQLException {
        sqlMapClient.insert("PRODUCT_ATTR.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ProductAttrExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_ATTR.ibatorgenerated_selectByExample", example);
    }

    public ProductAttr selectByPrimaryKey(Long productAttrId) throws SQLException {
        ProductAttr key = new ProductAttr();
        key.setProductAttrId(productAttrId);
        return (ProductAttr) sqlMapClient.queryForObject("PRODUCT_ATTR.ibatorgenerated_selectByPrimaryKey", key);
    }

    public int updateByExampleSelective(ProductAttr record, ProductAttrExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PRODUCT_ATTR.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(ProductAttr record, ProductAttrExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PRODUCT_ATTR.ibatorgenerated_updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(ProductAttr record) throws SQLException {
        return sqlMapClient.update("PRODUCT_ATTR.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(ProductAttr record) throws SQLException {
        return sqlMapClient.update("PRODUCT_ATTR.ibatorgenerated_updateByPrimaryKey", record);
    }

    private static class UpdateByExampleParms extends ProductAttrExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductAttrExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public boolean queryRelationAttrValue(Long categoryAttrValueId) throws SQLException {
        int result = (Integer) sqlMapClient.queryForObject("PRODUCT_ATTR.queryRelationAttrValue", categoryAttrValueId);
        if (result > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int updateByRelationId(ProductAttr attr) throws SQLException {
        return sqlMapClient.update("PRODUCT_ATTR.updateByRelationId", attr);
    }
}