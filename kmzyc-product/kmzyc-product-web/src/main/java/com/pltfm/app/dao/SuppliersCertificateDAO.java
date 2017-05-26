package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.example.SuppliersCertificateExample;

public interface SuppliersCertificateDAO {
    
    int countByExample(SuppliersCertificateExample example) throws SQLException;

    int deleteByExample(SuppliersCertificateExample example) throws SQLException;

    int deleteByPrimaryKey(Long scId) throws SQLException;

    void insert(SuppliersCertificate record) throws SQLException;

    void insertSelective(SuppliersCertificate record) throws SQLException;

    List selectByExample(SuppliersCertificateExample example) throws SQLException;

    SuppliersCertificate selectByPrimaryKey(Long scId) throws SQLException;

    int updateByExampleSelective(SuppliersCertificate record, SuppliersCertificateExample example) throws SQLException;

    int updateByExample(SuppliersCertificate record, SuppliersCertificateExample example) throws SQLException;

    int updateByPrimaryKeySelective(SuppliersCertificate record) throws SQLException;

    int updateByPrimaryKey(SuppliersCertificate record) throws SQLException;
    
    List<SuppliersCertificate> selectSuppIdList(Long suppId)throws SQLException;

    SuppliersCertificate selectSuppIdPath(String path)throws SQLException;
}