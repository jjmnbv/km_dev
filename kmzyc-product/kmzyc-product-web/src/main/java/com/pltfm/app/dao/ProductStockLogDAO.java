package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.ProductStockLog;
import com.pltfm.app.vobject.ProductStockLogExample;
import com.kmzyc.commons.page.Page;

public interface ProductStockLogDAO {

    int countByExample(ProductStockLogExample example) throws SQLException;

    int deleteByExample(ProductStockLogExample example) throws SQLException;

    int deleteByPrimaryKey(Long logId) throws SQLException;

    void insert(ProductStockLog record) throws SQLException;

    void insertSelective(ProductStockLog record) throws SQLException;

    List selectByExample(ProductStockLogExample example,Page page) throws SQLException;

    ProductStockLog selectByPrimaryKey(Long logId) throws SQLException;

    int updateByExampleSelective(ProductStockLog record, ProductStockLogExample example) throws SQLException;

    int updateByExample(ProductStockLog record, ProductStockLogExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProductStockLog record) throws SQLException;

    int updateByPrimaryKey(ProductStockLog record) throws SQLException;
    
    void batchAddStockLog(List<ProductStockLog> productStockLogList) throws SQLException;
}