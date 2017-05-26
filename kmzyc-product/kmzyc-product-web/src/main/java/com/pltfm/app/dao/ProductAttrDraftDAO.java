package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductAttrDraft;
import com.pltfm.app.vobject.ProductAttrDraftExample;
import java.sql.SQLException;
import java.util.List;

public interface ProductAttrDraftDAO {

    int countByExample(ProductAttrDraftExample example) throws SQLException;

    int deleteByExample(ProductAttrDraftExample example) throws SQLException;

    int deleteByPrimaryKey(Long productAttrId) throws SQLException;

    void insert(ProductAttrDraft record) throws SQLException;

    void insertSelective(ProductAttrDraft record) throws SQLException;

    List selectByExample(ProductAttrDraftExample example) throws SQLException;

    ProductAttrDraft selectByPrimaryKey(Long productAttrId) throws SQLException;

    int updateByExampleSelective(ProductAttrDraft record, ProductAttrDraftExample example) throws SQLException;

    int updateByExample(ProductAttrDraft record, ProductAttrDraftExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProductAttrDraft record) throws SQLException;

    int updateByPrimaryKey(ProductAttrDraft record) throws SQLException;
    
    int batchInsertOfficial(List<ProductAttrDraft> list) throws SQLException;
    
    void batchInsertDraft(List<ProductAttrDraft> list) throws SQLException;
    
    int batchInsertDraftFromOfficial(List<ProductAttr> list) throws SQLException;

    void batchUpdateByPrimaryKeySelective(List<ProductAttrDraft> list) throws SQLException;
}