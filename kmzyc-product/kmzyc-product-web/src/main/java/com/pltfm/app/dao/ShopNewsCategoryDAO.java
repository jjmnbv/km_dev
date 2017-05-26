package com.pltfm.app.dao;

import java.sql.SQLException;
import com.kmzyc.supplier.model.ShopNewsCategory;

public interface ShopNewsCategoryDAO {

    int deleteByPrimaryKey(Long sncId) throws SQLException;

    void insert(ShopNewsCategory record) throws SQLException;

    void insertSelective(ShopNewsCategory record) throws SQLException;

    ShopNewsCategory selectByPrimaryKey(Long sncId) throws SQLException;

    int updateByPrimaryKeySelective(ShopNewsCategory record) throws SQLException;

    int updateByPrimaryKey(ShopNewsCategory record) throws SQLException;

    /**
     * 查询店铺是否绑定了资讯类别
     * @param shopId
     * @return
     * @throws SQLException
     */
    Integer selectByShopIdCount(Long shopId) throws SQLException;
}