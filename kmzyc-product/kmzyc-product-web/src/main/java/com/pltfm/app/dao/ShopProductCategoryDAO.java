package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.ShopProductCategory;
import com.pltfm.app.vobject.ShopProductCategoryExample;


public interface ShopProductCategoryDAO {

    int countByExample(ShopProductCategoryExample example) throws SQLException;

    int deleteByExample(ShopProductCategoryExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    void insert(ShopProductCategory record) throws SQLException;

    void insertSelective(ShopProductCategory record) throws SQLException;

    List selectByExample(ShopProductCategoryExample example) throws SQLException;

    ShopProductCategory selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(ShopProductCategory record, ShopProductCategoryExample example) throws SQLException;

    int updateByExample(ShopProductCategory record, ShopProductCategoryExample example) throws SQLException;

    int updateByPrimaryKeySelective(ShopProductCategory record) throws SQLException;

    int updateByPrimaryKey(ShopProductCategory record) throws SQLException;
}