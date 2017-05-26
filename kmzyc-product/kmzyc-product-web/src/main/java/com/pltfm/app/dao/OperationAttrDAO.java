package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.OperationAttrExample;

public interface OperationAttrDAO {

    int countByExample(OperationAttrExample example) throws SQLException;

    int deleteByExample(OperationAttrExample example) throws SQLException;

    int deleteByPrimaryKey(Long operationAttrId) throws SQLException;

    void insert(OperationAttr record) throws SQLException;

    void insertSelective(OperationAttr record) throws SQLException;

    List selectByExample(OperationAttrExample example) throws SQLException;

    List selectByExample(OperationAttrExample example, int skip, int max) throws SQLException;

    OperationAttr selectByPrimaryKey(Long operationAttrId) throws SQLException;

    int updateByExampleSelective(OperationAttr record, OperationAttrExample example) throws SQLException;

    int updateByExample(OperationAttr record, OperationAttrExample example) throws SQLException;

    int updateByPrimaryKeySelective(OperationAttr record) throws SQLException;

    int updateByPrimaryKey(OperationAttr record) throws SQLException;

    String findRelationAttr(Long operationAttrId) throws SQLException;

    void deleteBatch(String[] operationAttrIds) throws SQLException;

}