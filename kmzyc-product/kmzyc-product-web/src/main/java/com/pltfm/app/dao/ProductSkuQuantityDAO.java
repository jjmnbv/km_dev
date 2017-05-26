package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProductSkuQuantity;
import com.pltfm.app.vobject.ProductSkuQuantityExample;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductSkuQuantityDAO {

    int countByExample(ProductSkuQuantityExample example) throws SQLException;

    int deleteByExample(ProductSkuQuantityExample example) throws SQLException;

    int deleteByPrimaryKey(Long productSkuQuantityId) throws SQLException;

    void insert(ProductSkuQuantity record) throws SQLException;

    void insertSelective(ProductSkuQuantity record) throws SQLException;

    List selectByExample(ProductSkuQuantityExample example) throws SQLException;

    List selectByExample(ProductSkuQuantityExample example, int pageNo, int pageCount) throws SQLException;

    ProductSkuQuantity selectByPrimaryKey(Long productSkuQuantityId) throws SQLException;

    int updateByExampleSelective(ProductSkuQuantity record, ProductSkuQuantityExample example) throws SQLException;

    int updateByExample(ProductSkuQuantity record, ProductSkuQuantityExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProductSkuQuantity record) throws SQLException;

    int updateByPrimaryKey(ProductSkuQuantity record) throws SQLException;

    Map<Long, ProductSkuQuantity> getLastSaleSkuIdMap(List<Long> skuIdList) throws SQLException;

    List<ProductSkuQuantity> selectByExampleList(ProductSkuQuantityExample example) throws SQLException;

    /**
     * 批量修改 ProductSkuQuantity list
     *
     * @param list
     * @throws SQLException
     */
    void batchUpdate(List<ProductSkuQuantity> list) throws SQLException;

    /**
     * 批量添加 ProductSkuQuantity list
     *
     * @param list
     * @throws SQLException
     */
    void batchInsert(List<ProductSkuQuantity> list) throws SQLException;

}