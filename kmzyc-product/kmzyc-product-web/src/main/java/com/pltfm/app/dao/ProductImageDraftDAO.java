package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProductImageDraft;
import com.pltfm.app.vobject.ProductImageDraftExample;
import java.sql.SQLException;
import java.util.List;

public interface ProductImageDraftDAO {

    int countByExample(ProductImageDraftExample example) throws SQLException;

    int deleteByExample(ProductImageDraftExample example) throws SQLException;

    int deleteByPrimaryKey(Long imageId) throws SQLException;

    Long insert(ProductImageDraft record) throws SQLException;

    void insertSelective(ProductImageDraft record) throws SQLException;

    List selectByExample(ProductImageDraftExample example) throws SQLException;
    
    List selectByExampleIntoOfficial(ProductImageDraftExample example) throws SQLException;

    ProductImageDraft selectByPrimaryKey(Long imageId) throws SQLException;

    int updateByExampleSelective(ProductImageDraft record, ProductImageDraftExample example) throws SQLException;

    int updateByExample(ProductImageDraft record, ProductImageDraftExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProductImageDraft record) throws SQLException;

    int updateByPrimaryKey(ProductImageDraft record) throws SQLException;

    int findMaxSortNoByProductSkuId(Long productSkuId) throws SQLException;
    
    void updateProductImage(List<ProductImageDraft> list) throws SQLException;
    
    int findCountsByProductSkuId(Long productSkuId) throws SQLException;
    
    int batchInsertOfficial(List<ProductImageDraft> list) throws SQLException;
    
    int batchDeleteDraft(List<ProductImageDraft> list) throws SQLException;
    
    List<ProductImageDraft> findOfficialToDraftByProductId(Long productId) throws SQLException;

    int batchInsertDraftFromOfficial(List<ProductImageDraft> list) throws SQLException;

}