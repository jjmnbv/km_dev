package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.example.SupplierFareExample;
import com.kmzyc.supplier.model.SupplierFare;

public interface SupplierFareDAO {

    int countByExample(SupplierFareExample example) throws SQLException;

    int deleteByExample(SupplierFareExample example) throws SQLException;

    int deleteByPrimaryKey(Long fareId) throws SQLException;

    void insert(SupplierFare record) throws SQLException;

    Long insertSelective(SupplierFare record) throws SQLException;

    List selectByExample(SupplierFareExample example) throws SQLException;

    SupplierFare selectByPrimaryKey(Long fareId) throws SQLException;

    int updateByExampleSelective(SupplierFare record, SupplierFareExample example) throws SQLException;

    int updateByExample(SupplierFare record, SupplierFareExample example) throws SQLException;

    int updateByPrimaryKeySelective(SupplierFare record) throws SQLException;

    int updateByPrimaryKey(SupplierFare record) throws SQLException;

    /**
     * 根据供应商id查询运费信息
     *
     * @param supplierId
     * @return
     * @throws SQLException
     */
    SupplierFare selectBySupplierId(Long supplierId) throws SQLException;

    /**
     * 根据供应商id集合查询多个供应商运费信息
     *
     * @return
     * @throws SQLException
     */
    List<SupplierFare> selectSupplierFareInfoList(List<Long> supplierIds) throws SQLException;
}