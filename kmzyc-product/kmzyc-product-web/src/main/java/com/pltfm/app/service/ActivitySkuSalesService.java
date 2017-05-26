package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface ActivitySkuSalesService {

    /**
     * 获取活动商品SKU已售数量
     *
     * @param skuIdList
     * @param supplierEntryId 报名ID
     * @return
     */
    Map<Long, Integer> getActivitySkuSales(Long supplierEntryId, List<Long> skuIdList) throws ServiceException;

}