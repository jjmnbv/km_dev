package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProductCertificate;
import com.pltfm.app.vobject.ProductCertificateExample;
import java.sql.SQLException;
import java.util.List;

public interface ProductCertificateDAO {

    int countByExample(ProductCertificateExample example) throws SQLException;

    int deleteByExample(ProductCertificateExample example) throws SQLException;

    int deleteByPrimaryKey(Long pscId) throws SQLException;

    void insert(ProductCertificate record) throws SQLException;

    void insertSelective(ProductCertificate record) throws SQLException;

    List selectByExample(ProductCertificateExample example) throws SQLException;

    ProductCertificate selectByPrimaryKey(Long pscId) throws SQLException;

    int updateByExampleSelective(ProductCertificate record, ProductCertificateExample example) throws SQLException;

    int updateByExample(ProductCertificate record, ProductCertificateExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProductCertificate record) throws SQLException;

    int updateByPrimaryKey(ProductCertificate record) throws SQLException;
}