package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.ProductmainTied;
import com.kmzyc.commons.page.Page;

public interface ProductmainTiedDAO {

    /**
     * 根据prod 传入的名字进行查询，分页
     *
     * @param prod
     * @param page
     * @return
     * @throws SQLException
     */
    List<ProductmainTied> selectList(ProductmainTied prod, Page page) throws SQLException;

    /**
     * 根据产品的sku值，关联表PRODUCTMAIN 以及PRODUCT_SKU 两张表查询出对应的PRODUCTMAINTIED 对象
     *
     * @param id
     * @return
     * @throws SQLException
     */
    ProductmainTied selectExampleBySku(Long id) throws SQLException;

    /**
     * 计算查询得到的总条数
     *
     * @param prod
     * @return
     * @throws SQLException
     */
    int countItem(ProductmainTied prod) throws SQLException;

    /**
     * 在剔除主sku的前提下，根据条件查询列表
     *
     * @param prod
     * @param page
     * @return
     */
    List<ProductmainTied> selectListExcepMainsku(ProductmainTied prod, Page page) throws SQLException;

    /**
     * 在剔除了主sku的前提下，根据条件计算总条数
     *
     * @param prod
     * @return
     * @throws SQLException
     */
    int countItemExceptMainsku(ProductmainTied prod) throws SQLException;

    /**
     * 根据传递进来的skuCode 集合 查询出 key 为 skuCode，value 值为 ProductmainTied 的map 对象
     *
     * @param list
     * @return
     * @throws SQLException
     */
    Map<String, ProductmainTied> getProductSkuMapBySkucode(List<String> list) throws SQLException;

    /**
     * 根据skuId 查询出 Productmain MAP 对象
     *
     * @param skuId
     * @return
     * @throws SQLException
     */
    Map<Long, ProductmainTied> getProductSkuMapBySkuId(List<Long> skuIdlist) throws SQLException;

    List<ProductmainTied> selectListByUser(ProductmainTied prod, Page page) throws SQLException;

    int countItemByUser(ProductmainTied prod) throws SQLException;

    int countItemByUserByRelation(ProductmainTied prod) throws SQLException;

    List<ProductmainTied> selectListByUserByRelation(ProductmainTied prod, Page page) throws SQLException;

    /**
     * 添加套餐入驻选择的产品列表
     *
     * @param prod
     * @param page
     * @return
     * @throws SQLException
     */
    List<ProductmainTied> selectListTaoCan(ProductmainTied prod, Page page) throws SQLException;

    /**
     * 添加套餐入驻统计商品列表数量
     */
    int countItemTaoCan(ProductmainTied prod) throws SQLException;

    /**
     * 添加套餐自营代销选择的产品列表
     *
     * @param prod
     * @param page
     * @return
     * @throws SQLException
     */
    List<ProductmainTied> selectListTaoCanZiYing(ProductmainTied prod, Page page) throws SQLException;

    /**
     * 添加套餐自营代销统计商品列表数量
     */
    int countItemTaoCanZiying(ProductmainTied prod) throws SQLException;
}