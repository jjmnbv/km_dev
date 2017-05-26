package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProductCertificateDraft;
import com.pltfm.app.vobject.ProductCertificateDraftExample;
import java.sql.SQLException;
import java.util.List;

public interface ProductCertificateDraftDAO {

    int countByExample(ProductCertificateDraftExample example) throws SQLException;

    int deleteByExample(ProductCertificateDraftExample example) throws SQLException;

    int deleteByPrimaryKey(Long pscId) throws SQLException;

    void insert(ProductCertificateDraft record) throws SQLException;

    void insertSelective(ProductCertificateDraft record) throws SQLException;

    List selectByExample(ProductCertificateDraftExample example) throws SQLException;

    ProductCertificateDraft selectByPrimaryKey(Long pscId) throws SQLException;

    int updateByExampleSelective(ProductCertificateDraft record, ProductCertificateDraftExample example) throws SQLException;

    int updateByExample(ProductCertificateDraft record, ProductCertificateDraftExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProductCertificateDraft record) throws SQLException;

    int updateByPrimaryKey(ProductCertificateDraft record) throws SQLException;
    
    int batchInsertDraft(List<ProductCertificateDraft> list) throws SQLException;
    
    int batchInsertOfficialFromDraft(List<ProductCertificateDraft> list) throws SQLException;
    
    void insertDraftFromOfficialByProductId(Long productId) throws SQLException;
    
}