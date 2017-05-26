package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ViewProductSku;
import com.kmzyc.commons.page.Page;

/**
 * 定向发布产品视图
 *
 * @author tanyunxing
 */
public interface ViewProductSkuService {

    /**
     * 根据SKUID获取产品
     *
     * @param productSkuId
     * @return
     * @throws ServiceException
     */
    ViewProductSku findByProductSkuId(Long productSkuId) throws ServiceException;

    /**
     * 根据产品Id获取SKU商品
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    List<ViewProductSku> findByProductId(Long productId) throws ServiceException;

    /**
     * 根据产品Id获取SKU商品，并关联出SKU属性
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    List<ViewProductSku> findProductAndSkuAttrByProductId(Long productId) throws ServiceException;

    /**
     * 根据skucode,获取商品。
     *
     * @param productSkuCode
     * @return
     * @throws ServiceException
     */
    ViewProductSku findByProductSkuCode(String productSkuCode) throws ServiceException;

    void searchPageByUserId(Page page, ViewProductSku viewProductSku, String string, Integer loginUserId) 
            throws ServiceException;

    void searchPageForFreightByUserId(Page page, ViewProductSku viewProductSku, Integer loginUserId) 
            throws ServiceException;
}