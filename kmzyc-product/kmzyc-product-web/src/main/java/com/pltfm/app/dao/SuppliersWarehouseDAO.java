package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.SuppliersWarehouse;
import com.kmzyc.supplier.model.example.SuppliersWarehouseExample;

public interface SuppliersWarehouseDAO {

    int countByExample(SuppliersWarehouseExample example) throws SQLException;

    int deleteByExample(SuppliersWarehouseExample example) throws SQLException;

    int deleteByPrimaryKey(Long supWarehouseId) throws SQLException;

    void insert(SuppliersWarehouse record) throws SQLException;

    void insertSelective(SuppliersWarehouse record) throws SQLException;

    List selectByExample(SuppliersWarehouseExample example) throws SQLException;

    SuppliersWarehouse selectByPrimaryKey(Long supWarehouseId) throws SQLException;

    int updateByExampleSelective(SuppliersWarehouse record, SuppliersWarehouseExample example) throws SQLException;

    int updateByExample(SuppliersWarehouse record, SuppliersWarehouseExample example) throws SQLException;

    int updateByPrimaryKeySelective(SuppliersWarehouse record) throws SQLException;

    int updateByPrimaryKey(SuppliersWarehouse record) throws SQLException;
    
    List<SuppliersWarehouse> selectByPrimarySupplierId(Long supplierId) throws SQLException;
}