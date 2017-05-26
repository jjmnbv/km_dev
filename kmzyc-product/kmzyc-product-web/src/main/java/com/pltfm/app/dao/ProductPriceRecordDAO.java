package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProductPriceRecord;
import com.pltfm.app.vobject.ProductPriceRecordExample;
import java.sql.SQLException;
import java.util.List;

public interface ProductPriceRecordDAO {

    void insert(ProductPriceRecord record) throws SQLException;

    int batchInsertRecord(List<ProductPriceRecord> list) throws SQLException;

    /**
     * 根据商品id获取商品下面所有的sku的价格变动
     *
     * @param productId 商品id
     * @return
     * @throws Exception
     */
    List<ProductPriceRecord> getRecordList(Long productId) throws SQLException;
}