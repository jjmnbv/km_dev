package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ShopProductCategory;

import java.util.List;


/**
 * 产品和店内分类的关联表,一对多的关系,产品可对应同一个店铺的多个二级分类
 *
 * @author Administrator
 */
public interface ShopProductCategoryService {

    /**
     * 插入
     *
     * @param para
     * @throws ServiceException
     */
    void insert(ShopProductCategory para) throws ServiceException;

    /**
     * 根据产品Id已经类目Id删除操作
     *
     * @param productId
     */
    void deleteByProductId(long productId) throws ServiceException;

}