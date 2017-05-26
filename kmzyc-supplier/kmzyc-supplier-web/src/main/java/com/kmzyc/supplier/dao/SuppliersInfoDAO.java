package com.kmzyc.supplier.dao;

import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.model.example.SuppliersInfoExample;

import java.sql.SQLException;
import java.util.List;

public interface SuppliersInfoDAO {

    int countByExample(SuppliersInfoExample example) throws SQLException;

    int deleteByExample(SuppliersInfoExample example) throws SQLException;

    int deleteByPrimaryKey(Long supplierId) throws SQLException;

    void insert(SuppliersInfo record) throws SQLException;

    void insertSelective(SuppliersInfo record) throws SQLException;

    List selectByExample(SuppliersInfoExample example) throws SQLException;

    SuppliersInfo selectByPrimaryKey(Long supplierId) throws SQLException;

    int updateByExampleSelective(SuppliersInfo record, SuppliersInfoExample example) throws SQLException;

    int updateByExample(SuppliersInfo record, SuppliersInfoExample example) throws SQLException;

    int updateByPrimaryKeySelective(SuppliersInfo record) throws SQLException;

    int updateByPrimaryKey(SuppliersInfo record) throws SQLException;

    /**
     * 根据商户id查询供应商数据
     *
     * @param supplier
     * @return
     * @throws SQLException
     */
    SuppliersInfo selectByMerchantId(SuppliersInfo supplier) throws SQLException;

}