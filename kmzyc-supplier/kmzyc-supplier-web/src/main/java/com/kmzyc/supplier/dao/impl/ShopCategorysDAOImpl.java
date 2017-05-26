package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.ShopCategorys;
import com.kmzyc.supplier.dao.ShopCategorysDAO;
import com.kmzyc.supplier.model.example.ShopCategorysExample;

@Repository("shopCategorysDAO")
public class ShopCategorysDAOImpl implements ShopCategorysDAO {
	
	@Resource
    private SqlMapClient sqlMapClient;

    public int countByExample(ShopCategorysExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("SHOP_CATEGORYS.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ShopCategorysExample example) throws SQLException {
        int rows = sqlMapClient.delete("SHOP_CATEGORYS.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long shopCategoryId) throws SQLException {
        ShopCategorys key = new ShopCategorys();
        key.setShopCategoryId(shopCategoryId);
        int rows = sqlMapClient.delete("SHOP_CATEGORYS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(ShopCategorys record) throws SQLException {
        sqlMapClient.insert("SHOP_CATEGORYS.ibatorgenerated_insert", record);
    }

    public void insertSelective(ShopCategorys record) throws SQLException {
        sqlMapClient.insert("SHOP_CATEGORYS.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ShopCategorysExample example) throws SQLException {
        List list = sqlMapClient.queryForList("SHOP_CATEGORYS.ibatorgenerated_selectByExample", example);
        return list;
    }

    public ShopCategorys selectByPrimaryKey(Long shopCategoryId) throws SQLException {
        ShopCategorys key = new ShopCategorys();
        key.setShopCategoryId(shopCategoryId);
        ShopCategorys record = (ShopCategorys) sqlMapClient.queryForObject("SHOP_CATEGORYS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(ShopCategorys record, ShopCategorysExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SHOP_CATEGORYS.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(ShopCategorys record, ShopCategorysExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SHOP_CATEGORYS.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(ShopCategorys record) throws SQLException {
        int rows = sqlMapClient.update("SHOP_CATEGORYS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ShopCategorys record) throws SQLException {
        int rows = sqlMapClient.update("SHOP_CATEGORYS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends ShopCategorysExample {
        private Object record;

        public UpdateByExampleParms(Object record, ShopCategorysExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}