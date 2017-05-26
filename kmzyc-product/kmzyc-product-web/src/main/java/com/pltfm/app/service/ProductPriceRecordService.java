package com.pltfm.app.service;

import java.util.List;

import com.pltfm.app.vobject.ProductPriceRecord;
import com.kmzyc.commons.exception.ServiceException;

public interface ProductPriceRecordService {

    /**
     * 批量保存
     *
     * @param list
     * @throws ServiceException
     */
    void batchInsertRecord(List<ProductPriceRecord> list) throws ServiceException;

    /**
     * 单个保存
     *
     * @param record
     * @throws Exception
     */
    void insertRecord(ProductPriceRecord record) throws ServiceException;

    /**
     * 根据商品id查询对应的sku的价格变动
     *
     * @param productId
     * @return
     */
    List<ProductPriceRecord> getRecordList(Long productId) throws ServiceException;
}
