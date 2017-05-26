package com.kmzyc.promotion.app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.ProductmainTied;

public interface ProductMainTiedService {

    /**
     * 根据prod 对象中的productName 关联查询表PRODUCTMAIN PRODUCT_SKU 表查询出 PRODUCTMAINTIED 对象集合
     * 
     * @param prod 对象，主要需要其中的 ProductName属性
     * @param page 分页对象
     * @throws Exception 异常
     */
    public void selectList(ProductmainTied prod, Page page) throws Exception;

    /**
     * 根据产品的SKU 值 查询出与其对应的ProductmainTied 对象
     * 
     * @param id sku 值
     * @return
     * @throws Exception 异常
     */

    public ProductmainTied findObjectBySku(Long id) throws Exception;

    /**
     * 计算查询出来的记录总条数
     * 
     * @param prod
     * @return
     * @throws Exception
     */

    public int countItem(ProductmainTied prod) throws Exception;

    /**
     * 分页查询出非SKUID 值的分页信息
     * 
     * @param prod
     * @param page
     * @throws Exception
     */

    public void selectListNotMainSKU(ProductmainTied prod, Page page) throws Exception;

    /**
     * 计算非skuId 的的总数
     * 
     * @param prod
     * @return
     * @throws Exception
     */

    public int countItemExceptMainsku(ProductmainTied prod) throws Exception;

    /**
     * 根据传递进来的skuCode 集合 查询出 key 为 skuCode，value 值为 ProductmainTied 的map 对象
     * 
     * @param list
     * @return
     * @throws SQLException
     */
    public Map<String, ProductmainTied> getProductSkuMapBySkucode(List<String> list)
            throws SQLException;

    /**
     * 根据skuCode 查询出productmain 对象出来
     * 
     * @param skuCode
     * @return
     * @throws SQLException
     */
    public ProductmainTied getProductmainTied(String skuCode) throws SQLException;

    /**
     * 根据skuId 集合查询出对应的productMainTied 对象集合出来
     * 
     * @param skuList
     * @return
     * @throws SQLException
     */
    public List<ProductmainTied> queryProductMainTiedsBySkuIdList(List<Long> skuList)
            throws SQLException;

    public void selectList(ProductmainTied productTied, Page page, Integer loginUserId)
            throws Exception;

    public void selectListByRelation(ProductmainTied prod, Page page, Integer userId)
            throws Exception;

}
