package com.pltfm.app.dao;

import com.pltfm.app.vobject.WarehouseRelation;
import com.pltfm.app.vobject.WarehouseRelationExample;
import java.sql.SQLException;
import java.util.List;

public interface WarehouseRelationDAO {

    int countByExample(WarehouseRelationExample example) throws SQLException;

    int deleteByExample(WarehouseRelationExample example) throws SQLException;

    int deleteByPrimaryKey(Long warehouseRelationId) throws SQLException;

    Long insert(WarehouseRelation record) throws SQLException;

    void insertSelective(WarehouseRelation record) throws SQLException;

    List selectByExample(WarehouseRelationExample example) throws SQLException;

    WarehouseRelation selectByPrimaryKey(Long warehouseRelationId) throws SQLException;

    int updateByExampleSelective(WarehouseRelation record, WarehouseRelationExample example) throws SQLException;

    int updateByExample(WarehouseRelation record, WarehouseRelationExample example) throws SQLException;

    int updateByPrimaryKeySelective(WarehouseRelation record) throws SQLException;

    int updateByPrimaryKey(WarehouseRelation record) throws SQLException;
}