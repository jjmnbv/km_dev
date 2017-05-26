package com.kmzyc.supplier.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProductImageDraft;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuAttrDraft;
import com.pltfm.app.vobject.ProductSkuDraft;
import com.pltfm.app.vobject.ViewProductSku;

/**
 * 产品SKU业务逻辑接口
 */
public interface ProductDraftSkuService {

    /**
     * SKU列表
     *
     * @param supplierId
     * @param viewProductSku
     * @param page
     * @throws Exception
     */
    Pagination searchPage(Long supplierId, ViewProductSku viewProductSku, Pagination page) throws ServiceException;

    /**
     * 获取单个对象
     *
     * @param productSkuId
     * @return
     * @throws Exception
     */
    ProductSkuDraft findSingleProductSku(Long productSkuId) throws ServiceException;

    /**
     * 根据skuId查询产品图片列表
     * @param productSkuId
     * @return
     * @throws ServiceException
     */
    List<ProductImage> findAllBySkuId(Long productSkuId) throws ServiceException;

    /**
     * 修改SKU价格和重量
     *
     * @param productId
     * @param list
     * @return
     * @throws Exception
     */
    boolean updatePriceAndWeight(Long productId, List<ProductSkuDraft> list) throws ServiceException;

    /**
     * 获取有效的SKU
     *
     * @param productId
     * @return
     * @throws Exception
     */
    List<ProductSkuDraft> findIsValidProductSkuByProductId(Long productId) throws ServiceException;

    /**
     * 商品发布修改价格与重量
     *
     * @param productId
     * @param list
     * @return
     * @throws Exception
     */
    boolean updateSingleSkuPrice(Long productId, List<ProductSkuDraft> list) throws ServiceException;

    /**
     * 修改sku_draft的商品介绍
     *
     * @param productSkuDraft
     * @return
     * @throws Exception
     */
    boolean updateSkuIntroduce(ProductSkuDraft productSkuDraft) throws ServiceException;

    /**
     * 查询新增的SKU属性并做处理
     *
     * @param productId
     * @return
     * @throws Exception
     */
    Map<Long, Set<String>> findAndChangeSkuNewAttr(Long productId) throws ServiceException;

}