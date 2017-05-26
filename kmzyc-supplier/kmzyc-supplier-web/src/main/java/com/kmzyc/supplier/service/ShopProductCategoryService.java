package com.kmzyc.supplier.service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.ShopCategorys;
import com.kmzyc.supplier.model.ShopProductCategory;

import java.util.List;
import java.util.Map;

/**
 * 产品和店内分类的关联表,一对多的关系,产品可对应同一个店铺的多个分类
 *
 * @author Administrator
 */
public interface ShopProductCategoryService {

    /**
     * @param productId
     */
    List<ShopProductCategory> queryByProductId(long productId) throws ServiceException;

    /**
     * 获取商品的的店铺分类
     * map的key如下定义
     * <note>
     *     shopCategoryId   商品店铺分类id
     *     shopCategoryName 商品店铺分类名称
     * </note>
     *
     * @param productId             商品id
     * @param needShopCategoryId    是否需要店铺分类id
     */
    Map<String, String> getProductShopCategory(Long productId, Boolean needShopCategoryId) throws ServiceException;

    /**
     * 处理店铺默认分类
     * @param defaultShopCategory   默认店铺分类
     * @param isAdd                 ture新增/false修改
     * @param result                携带当前产品的店铺分类id和名称
     * @throws ServiceException
     */
    void handleDefaultShopCategory(ShopCategorys defaultShopCategory, Boolean isAdd, Map<String, String> result)
            throws ServiceException;

    /**
     * 插入
     *
     * @param para
     * @throws Exception
     */
    void insert(ShopProductCategory para) throws ServiceException;

    /**
     * 根据产品Id已经类目Id删除操作
     *
     * @param productId
     */
    void deleteByProductId(long productId) throws ServiceException;


    /**
     * 修改店内分类
     *
     * @param productId
     * @throws Exception
     */
    void updateByProductId(long productId, String[] shopCategoryIdArray) throws ServiceException;

    /**
     * 新增商品分类
     *
     * @param productId
     * @throws Exception
     */
    void insert(long productId, String[] shopCategoryIdArray) throws ServiceException;
}