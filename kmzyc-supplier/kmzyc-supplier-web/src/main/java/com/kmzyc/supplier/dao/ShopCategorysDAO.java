package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.ShopCategorys;
import com.kmzyc.supplier.model.example.ShopCategorysExample;

public interface ShopCategorysDAO {

    int countByExample(ShopCategorysExample example) throws SQLException;

    int deleteByExample(ShopCategorysExample example) throws SQLException;

    int deleteByPrimaryKey(Long shopCategoryId) throws SQLException;

    void insert(ShopCategorys record) throws SQLException;

    void insertSelective(ShopCategorys record) throws SQLException;

    List selectByExample(ShopCategorysExample example) throws SQLException;

    ShopCategorys selectByPrimaryKey(Long shopCategoryId) throws SQLException;

    int updateByExampleSelective(ShopCategorys record, ShopCategorysExample example) throws SQLException;

    int updateByExample(ShopCategorys record, ShopCategorysExample example) throws SQLException;

    int updateByPrimaryKeySelective(ShopCategorys record) throws SQLException;

    int updateByPrimaryKey(ShopCategorys record) throws SQLException;

}