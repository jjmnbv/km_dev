package com.pltfm.app.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.promotion.exception.ServiceException;
import com.pltfm.app.vobject.ProductSkuQuantity;

public interface ProductSkuQuantityService {

    /**
     * 每个月定时更新product_sku_quanlity中的数量
     *
     * @throws ServiceException
     */
    void updateProductSkuQuanlityEveryMonth(Date date) throws ServiceException;

    /**
     * 根据skuId 查询表product_sku_quantity 的对应的数量
     *
     * @param skuIdList
     * @return
     * @throws ServiceException
     */
    Map<Long, ProductSkuQuantity> getLastSaleSkuIdMap(List<Long> skuIdList) throws ServiceException;

}
