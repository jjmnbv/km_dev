package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductAttrExample;

public interface ProductAttrDAO {

    int countByExample(ProductAttrExample example) throws SQLException;

    int deleteByExample(ProductAttrExample example) throws SQLException;

    int deleteByPrimaryKey(Long productAttrId) throws SQLException;

    void insert(ProductAttr record) throws SQLException;

    void insertSelective(ProductAttr record) throws SQLException;

    List selectByExample(ProductAttrExample example) throws SQLException;

    ProductAttr selectByPrimaryKey(Long productAttrId) throws SQLException;

    int updateByExampleSelective(ProductAttr record, ProductAttrExample example) throws SQLException;

    int updateByExample(ProductAttr record, ProductAttrExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProductAttr record) throws SQLException;

    int updateByPrimaryKey(ProductAttr record) throws SQLException;

    boolean queryRelationAttrValue(Long categoryAttrValueId) throws SQLException;

    int updateByRelationId(ProductAttr attr) throws SQLException;
}