package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.dao.ShopProductCategoryDAO;
import com.kmzyc.supplier.model.ShopProductCategory;
import com.kmzyc.supplier.model.example.ShopProductCategoryExample;

@Repository("shopProductCategoryDAO")
public class ShopProductCategoryDAOImpl implements ShopProductCategoryDAO {

	@Resource
    private SqlMapClient sqlMapClient;

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