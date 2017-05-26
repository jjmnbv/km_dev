package com.kmzyc.promotion.app.service;

import java.util.List;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.ViewProductSku;

/**
 * 定向发布产品视图
 * 
 * @author tanyunxing
 * 
 */
public interface ViewProductSkuService {

    /**
     * 获得所有的定向发布产品并分页
     * 
     * @param page
     * @param viewProductSku
     * @return
     * @throws Exception
     */
    public void searchByPage(Page page, ViewProductSku viewProductSku, String type)
            throws Exception;

    /**
     * 根据SKUID获取产品
     * 
     * @param productSkuId
     * @return
     * @throws Exception
     */
    public ViewProductSku findByProductSkuId(Long productSkuId) throws Exception;

    /**
     * 根据产品Id获取SKU商品
     * 
     * @param productId
     * @return
     * @throws Exception
     */
    public List<ViewProductSku> findByProductId(Long productId) throws Exception;

    /**
     * 根据产品Id获取SKU商品，并关联出SKU属性
     * 
     * @param productId
     * @return
     * @throws Exception
     */
    public List<ViewProductSku> findProductAndSkuAttrByProductId(Long productId) throws Exception;

    /**
     * 根据skucode,获取商品。
     * 
     * @param productId
     * @return
     * @throws Exception
     */
    public ViewProductSku findByProductSkucode(String productSkuCode) throws Exception;

    /**
     * @author ljh 获得所有的产品 除去状态值为草稿 以及待审核的
     * @param page
     * @param viewProductSku
     * @param type
     * @throws Exception
     * 
     */
    public void searchByPageAndStatus(Page page, ViewProductSku viewProductSku, String type)
            throws Exception;

    public void searchShelfByPage(Page page, ViewProductSku viewProductSku, String type)
            throws Exception;

    public void searchPageByUserId(Page page, ViewProductSku viewProductSku, String string,
            Integer loginUserId) throws Exception;

    public void searchShelfByPageByUser(Page page, ViewProductSku viewProductSku, String string,
            Integer loginUserId) throws Exception;

    /**
     * 根据skucode,批量获取商品。
     * 
     * @param productId
     * @return
     * @throws Exception
     */
    public List<ViewProductSku> queryProductSkuBycodes(String skus) throws Exception;

}
