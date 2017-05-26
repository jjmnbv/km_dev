package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.CategoryAttrValueExample;

public interface CategoryAttrValueDAO {

    int countByExample(CategoryAttrValueExample example) throws SQLException;

    int deleteByExample(CategoryAttrValueExample example) throws SQLException;

    int deleteByPrimaryKey(Long categoryAttrValueId) throws SQLException;

    void insert(CategoryAttrValue record) throws SQLException;

    void insertSelective(CategoryAttrValue record) throws SQLException;

    List selectByExample(CategoryAttrValueExample example) throws SQLException;

    CategoryAttrValue selectByPrimaryKey(Long categoryAttrValueId) throws SQLException;

    int updateByExampleSelective(CategoryAttrValue record, CategoryAttrValueExample example) throws SQLException;

    int updateByExample(CategoryAttrValue record, CategoryAttrValueExample example) throws SQLException;

    int updateByPrimaryKeySelective(CategoryAttrValue record) throws SQLException;

    int updateByPrimaryKey(CategoryAttrValue record) throws SQLException;

    /**
     * 根据目录属性值的id数组获取目录属性值
     *
     * @param categoryAttrValueIds 目录属性值的id数组
     * @return
     * @throws SQLException
     */
    String getCategoryAttrValueByValueIds(String[] categoryAttrValueIds) throws SQLException;

}