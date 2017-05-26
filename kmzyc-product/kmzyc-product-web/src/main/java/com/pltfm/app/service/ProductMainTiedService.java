package com.pltfm.app.service;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProductmainTied;
import com.kmzyc.commons.page.Page;

public interface ProductMainTiedService {

    /**
     * 根据prod 对象中的productName 关联查询表PRODUCTMAIN  PRODUCT_SKU  表查询出
     * PRODUCTMAINTIED 对象集合
     *
     * @param prod 对象，主要需要其中的  ProductName属性
     * @param page 分页对象
     * @throws ServiceException 异常
     */
    void selectList(ProductmainTied prod, Page page) throws ServiceException;

    /**
     * 根据产品的SKU 值 查询出与其对应的ProductmainTied 对象
     *
     * @param id sku 值
     * @return
     * @throws ServiceException 异常
     */
    ProductmainTied findObjectBySku(Long id) throws ServiceException;

    /**
     * 分页查询出非SKUID 值的分页信息
     *
     * @param prod
     * @param page
     * @throws ServiceException
     */
    void selectListNotMainSKU(ProductmainTied prod, Page page) throws ServiceException;

    /**
     * 计算非skuId 的的总数
     *
     * @param prod
     * @return
     * @throws ServiceException
     */
    int countItemExceptMainsku(ProductmainTied prod) throws ServiceException;

    /**
     * 根据传递进来的skuCode 集合 查询出 key 为 skuCode，value 值为 ProductmainTied 的map 对象
     *
     * @param list
     * @return
     * @throws SQLException
     */
    Map<String, ProductmainTied> getProductSkuMapBySkucode(List<String> list) throws SQLException;

    /**
     * 产品关联列表
     *
     * @param productTied
     * @param page
     * @param loginUserId
     * @throws ServiceException
     */
    void selectList(ProductmainTied productTied, Page page, Integer loginUserId) throws ServiceException;

    void selectListByRelation(ProductmainTied prod, Page page, Integer userId) throws ServiceException;

    /**
     * 添加套餐是显示的产品列表
     *
     * @param prod
     * @param page
     * @throws ServiceException
     */
    void selectListTaoCan(ProductmainTied prod, Page page) throws ServiceException;

    /**
     * 添加套餐自营代销显示的产品列表
     *
     * @param prod
     * @param page
     * @throws ServiceException
     */
    void selectListTaoCanZiying(ProductmainTied prod, Page page) throws ServiceException;

}