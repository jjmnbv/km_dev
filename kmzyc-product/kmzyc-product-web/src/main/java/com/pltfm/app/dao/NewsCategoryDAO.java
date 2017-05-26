package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.NewsCategory;
import com.kmzyc.supplier.model.example.NewsCategoryExample;

public interface NewsCategoryDAO {

    int countByExample(NewsCategoryExample example) throws SQLException;

    int deleteByExample(NewsCategoryExample example) throws SQLException;

    int deleteByPrimaryKey(Long newsCategoryId) throws SQLException;

    void insert(NewsCategory record) throws SQLException;

    Long insertSelective(NewsCategory record) throws SQLException;

    List selectByExample(NewsCategoryExample example) throws SQLException;

    NewsCategory selectByPrimaryKey(Long newsCategoryId) throws SQLException;

    int updateByExampleSelective(NewsCategory record, NewsCategoryExample example) throws SQLException;

    int updateByExample(NewsCategory record, NewsCategoryExample example) throws SQLException;

    int updateByPrimaryKeySelective(NewsCategory record) throws SQLException;

    int updateByPrimaryKey(NewsCategory record) throws SQLException;

    /**
     * 查询供应商是否添加了资讯类别
     * @param supplierId
     * @return
     * @throws SQLException
     */
    Integer selectNewCategorySupplierIdCount(Long supplierId)throws SQLException;
}