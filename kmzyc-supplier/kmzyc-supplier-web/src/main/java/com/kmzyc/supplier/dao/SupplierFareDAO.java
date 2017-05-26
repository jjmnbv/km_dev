package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.SupplierFare;
import com.kmzyc.supplier.model.example.SupplierFareExample;

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

    Pagination findFareListByPage(Pagination page) throws SQLException;

    int countFareBySupplierId(Long supplierId) throws SQLException;

}