package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.pltfm.app.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ShopProductCategoryDAO;
import com.pltfm.app.vobject.ShopProductCategory;
import com.pltfm.app.vobject.ShopProductCategoryExample;

@Repository("shopProductCategoryDao")
public class ShopProductCategoryDAOImpl extends BaseDao implements ShopProductCategoryDAO {

    public int countByExample(ShopProductCategoryExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("SHOP_PRODUCT_CATEGORY.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ShopProductCategoryExample example) throws SQLException {
        int rows = sqlMapClient.delete("SHOP_PRODUCT_CATEGORY.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
        ShopProductCategory key = new ShopProductCategory();
        key.setId(id);
        int rows = sqlMapClient.delete("SHOP_PRODUCT_CATEGORY.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(ShopProductCategory record) throws SQLException {
        sqlMapClient.insert("SHOP_PRODUCT_CATEGORY.ibatorgenerated_insert", record);
    }

    public void insertSelective(ShopProductCategory record) throws SQLException {
        sqlMapClient.insert("SHOP_PRODUCT_CATEGORY.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ShopProductCategoryExample example) throws SQLException {
        List list = sqlMapClient.queryForList("SHOP_PRODUCT_CATEGORY.ibatorgenerated_selectByExample", example);
        return list;
    }

    public ShopProductCategory selectByPrimaryKey(Long id) throws SQLException {
        ShopProductCategory key = new ShopProductCategory();
        key.setId(id);
        ShopProductCategory record = (ShopProductCategory) sqlMapClient.queryForObject("SHOP_PRODUCT_CATEGORY.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(ShopProductCategory record, ShopProductCategoryExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SHOP_PRODUCT_CATEGORY.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(ShopProductCategory record, ShopProductCategoryExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SHOP_PRODUCT_CATEGORY.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(ShopProductCategory record) throws SQLException {
        int rows = sqlMapClient.update("SHOP_PRODUCT_CATEGORY.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ShopProductCategory record) throws SQLException {
        int rows = sqlMapClient.update("SHOP_PRODUCT_CATEGORY.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    private static class UpdateByExampleParms extends ShopProductCategoryExample {
        private Object record;

        public UpdateByExampleParms(Object record, ShopProductCategoryExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}