package com.pltfm.app.dao;

import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ViewProductDetailInfoExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 产品详情DAO接口
 *
 * @author cjm
 * @since 2013-9-23
 */
public interface ViewProductDetailInfoDAO {

    /**
     * 根据产品详情条件查询产品详情信息
     *
     * @param example 产品详情条件类
     * @throws SQLException 异常
     * @return 返回值
     */
    List selectByExample(ViewProductDetailInfoExample example) throws SQLException;

    /**
     * 通过产品主键查询产品sku属性信息
     *
     * @param productId 产品主键
     * @return sku属性信息
     * @throws SQLException 异常信息
     */
    List<ProductAttr> queryProductAttrByProductId(Long productId) throws SQLException;

    /**
     * 通过草稿产品主键查询产品sku属性信息
     *
     * @param productId 产品主键
     * @return sku属性信息
     * @throws SQLException 异常信息
     */
    List<ProductAttr> queryProductAttrDraftByProductId(Long productId) throws SQLException;

    /**
     * 通过产品sku查询产品属性值信息
     *
     * @param attrValue sku属性值
     * @throws SQLException 异常信息
     */
    List<CategoryAttrValue> findByValueId(String attrValue) throws SQLException;
}