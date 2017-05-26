package com.kmzyc.supplier.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductAttr;

/**
 * 产品属性业务逻辑接口
 *
 * @author humy
 * @since 2013-7-9
 */
public interface ProductAttrService {

    /**
     * 查询产品属性
     *
     * @param productAttr 产品属性基本信息
     * @return List<ProductAttr>
     * @throws Exception 异常
     */
    List<ProductAttr> queryProductAttr(ProductAttr productAttr) throws ServiceException;

    /**
     * 产品类目属性更新，先进行数据处理
     *
     * @param product
     * @param prodAttrList
     * @throws ServiceException
     */
    void updateCategoryAttrValue(Product product, List<ProductAttr> prodAttrList) throws ServiceException;

    /**
     * 自定义属性更新
     *
     * @param product
     * @param productId
     * @param productAttrList
     * @throws ServiceException
     */
    void updateDefinitionAttrValue(Product product, Long productId, List<ProductAttr> productAttrList) throws ServiceException;

    /**
     * 运营属性更新
     *
     * @param product
     * @param productId
     * @param operationAttrIds
     * @throws ServiceException
     */
    void updateOperationAttrValue(Product product, Long productId, Long[] operationAttrIds) throws ServiceException;
}