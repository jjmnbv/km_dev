package com.pltfm.app.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.ShopNewsCategory;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ShopNewsCategoryDAO;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

@Repository("shopNewsCategoryDao")
public class ShopNewsCategoryDAOImpl extends BaseDao implements ShopNewsCategoryDAO {

    public int deleteByPrimaryKey(Long sncId) throws SQLException {
        ShopNewsCategory key = new ShopNewsCategory();
        key.setSncId(sncId);
        return sqlMapClient.delete("SHOP_NEWS_CATEGORY.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public void insert(ShopNewsCategory record) throws SQLException {
        sqlMapClient.insert("SHOP_NEWS_CATEGORY.ibatorgenerated_insert", record);
    }

    public void insertSelective(ShopNewsCategory record) throws SQLException {
        sqlMapClient.insert("SHOP_NEWS_CATEGORY.ibatorgenerated_insertSelective", record);
    }

    public ShopNewsCategory selectByPrimaryKey(Long sncId) throws SQLException {
        ShopNewsCategory key = new ShopNewsCategory();
        key.setSncId(sncId);
        return (ShopNewsCategory) sqlMapClient.queryForObject("SHOP_NEWS_CATEGORY.ibatorgenerated_selectByPrimaryKey", key);
    }

    public int updateByPrimaryKeySelective(ShopNewsCategory record) throws SQLException {
        return sqlMapClient.update("SHOP_NEWS_CATEGORY.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(ShopNewsCategory record) throws SQLException {
        return sqlMapClient.update("SHOP_NEWS_CATEGORY.ibatorgenerated_updateByPrimaryKey", record);
    }

    public Integer selectByShopIdCount(Long shopId) throws SQLException{
    	return (Integer)sqlMapClient.queryForObject("SHOP_NEWS_CATEGORY.selectByShopIdCount",shopId);
    }
}