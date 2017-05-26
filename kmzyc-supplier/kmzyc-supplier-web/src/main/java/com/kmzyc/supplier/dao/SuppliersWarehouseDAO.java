package com.kmzyc.supplier.dao;


import com.kmzyc.supplier.model.SuppliersWarehouse;
import com.kmzyc.supplier.model.example.SuppliersWarehouseExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 供应商仓库关系DAO
 *
 * @createDate 2013/12/25
 * @author luoyi
 */
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

}