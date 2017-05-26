package com.kmzyc.product.remote.service;

import com.kmzyc.commons.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface SaleRankRemoteService {

    /**
     * 根据传递进来的categoryId 集合 ，按照上个月的销量排行，返回producId 集合
     *
     * @param categoryIdList
     * @return
     * @throws ServiceException
     */
    List<Integer> getProuductCountByCategoryIdList(List<Long> categoryIdList) throws ServiceException;

    /**
     * 根据所有的skuCode ,按照上个月的销量排行，返回productId 集合
     *
     * @return
     * @throws ServiceException
     */
    List<Integer> getProductCountByAllProductSkuList() throws ServiceException;

    /**
     * 获取搜索页面的热销栏目的商品
     *
     * @return
     * @throws ServiceException
     */
    List<Map<String, Object>> findSectionsHotSell(String saleType) throws ServiceException;
}