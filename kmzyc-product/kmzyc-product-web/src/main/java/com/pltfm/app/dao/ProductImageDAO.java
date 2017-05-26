package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageExample;
import java.sql.SQLException;
import java.util.List;

public interface ProductImageDAO {

    int countByExample(ProductImageExample example) throws SQLException;

    int deleteByExample(ProductImageExample example) throws SQLException;

    int deleteByPrimaryKey(Long imageId) throws SQLException;

    Long insert(ProductImage record) throws SQLException;

    void insertSelective(ProductImage record) throws SQLException;

    List selectByExample(ProductImageExample example) throws SQLException;

    ProductImage selectByPrimaryKey(Long imageId) throws SQLException;

    int updateByExampleSelective(ProductImage record, ProductImageExample example) throws SQLException;

    int updateByExample(ProductImage record, ProductImageExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProductImage record) throws SQLException;

    int updateByPrimaryKey(ProductImage record) throws SQLException;

    int findMaxSortNoByProductSkuId(Long productSkuId) throws SQLException;
    
    void updateProductImage(List<ProductImage> list) throws SQLException;
    
    int findCountsByProductSkuId(Long productSkuId) throws SQLException;
    
    int deleteImagesBySkuId(Long skuId) throws SQLException;
    
}