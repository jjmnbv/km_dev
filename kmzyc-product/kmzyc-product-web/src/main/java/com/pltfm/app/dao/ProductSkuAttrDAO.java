package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProductSkuAttr;
import com.pltfm.app.vobject.ProductSkuAttrExample;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductSkuAttrDAO {

    int countByExample(ProductSkuAttrExample example) throws SQLException;

    int deleteByExample(ProductSkuAttrExample example) throws SQLException;

    int deleteByPrimaryKey(Long productSkuAttrId) throws SQLException;

    void insert(ProductSkuAttr record) throws SQLException;

    void insertSelective(ProductSkuAttr record) throws SQLException;

    List selectByExample(ProductSkuAttrExample example) throws SQLException;

    ProductSkuAttr selectByPrimaryKey(Long productSkuAttrId) throws SQLException;

    int updateByExampleSelective(ProductSkuAttr record, ProductSkuAttrExample example) throws SQLException;

    int updateByExample(ProductSkuAttr record, ProductSkuAttrExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProductSkuAttr record) throws SQLException;

    int updateByPrimaryKey(ProductSkuAttr record) throws SQLException;
    
    String querySkuAttrValueBySkuId(Long productSkuId) throws SQLException;
    
    List<ProductSkuAttr> findSkuNewAttr(Long productId) throws SQLException;
    
    /**
     * 根据productId删除SKU属性
     *
     * @param productId 产品id
     */
    int deleteProductSkuAttrByProductId(Long productId) throws SQLException;
    
}