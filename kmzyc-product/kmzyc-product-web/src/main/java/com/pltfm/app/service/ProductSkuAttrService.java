package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProductSkuAttr;

import java.util.List;
import java.util.Map;

/**
 * 产品SKU属性业务逻辑接口
 *
 * @author humy
 * @since 2013-7-9
 */
public interface ProductSkuAttrService {

    /**
     * 添加产品SKU属性
     *
     * @param productSkuAttr 产品SKU属性基本信息
     * @return
     * @throws ServiceException
     */
    void addProductSkuAttr(ProductSkuAttr productSkuAttr) throws ServiceException;

    /**
     * 查询SKU属性
     *
     * @param productSkuAttr
     * @return List<ProductSkuAttr>
     * @throws ServiceException
     */
    List<ProductSkuAttr> queryProductSkuAttrList(ProductSkuAttr productSkuAttr) throws ServiceException;

    /**
     * 删除产品SKU属性
     *
     * @param productSkuId 产品SKU Id
     * @return
     * @throws ServiceException
     */
    void deleteProductSkuAttr(Long productSkuId) throws ServiceException;

    boolean queryByAttrValueId(Long attrValueId) throws ServiceException;

    String querySkuAttrValueBySkuId(Long productSkuId) throws ServiceException;

    /**
     * 查询新增的SKU属性并做处理
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    Map<Long, List<ProductSkuAttr>> findAndChangeSkuNewAttr(Long productId) throws ServiceException;

    /**
     * 根据productId删除SKU属性
     *
     * @param productId 产品id
     */
    void deleteProductSkuAttrByProductId(Long productId) throws ServiceException;

}
