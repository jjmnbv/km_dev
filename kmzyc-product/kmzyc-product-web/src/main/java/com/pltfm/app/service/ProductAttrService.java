package com.pltfm.app.service;

import java.util.List;

import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.ProductAttr;
import com.kmzyc.commons.exception.ServiceException;

public interface ProductAttrService {
    
    /**
     * 增加产品属性
     *
     * @param productAttr 产品属性基本信息
     * @return
     * @throws ServiceException 
     */
    void addProductAttr(ProductAttr productAttr) throws ServiceException ;

    /**
     * 查询产品属性
     *
     * @param productAttr 产品属性基本信息
     * @return List<ProductAttr>
     * @throws ServiceException  异常
     */
    List<ProductAttr> queryProductAttr(ProductAttr productAttr) throws ServiceException ;

    /**
     * 更新产品属性
     *
     * @param productAttr 产品属性基本信息
     * @return
     * @throws ServiceException 
     */
    void updateProductAttr(ProductAttr productAttr) throws ServiceException ;

    /**
     * 删除产品属性
     *
     * @param productAttr 产品属性基本信息
     * @return
     * @throws ServiceException 
     */
    void deleteProductAttr(ProductAttr productAttr) throws ServiceException ;

    /**
     * 删除产品属性草稿表信息
     *
     * @param productId
     * @throws ServiceException 
     */
    void deleteDraftByProductId(Long productId) throws ServiceException ;

}
