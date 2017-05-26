package com.pltfm.app.service;

import com.pltfm.app.vobject.ProductAttr;

import java.sql.SQLException;

/**
 * 产品sku属性集合service
 *
 * @author zhl
 * @since 2013-12-02
 */
public interface ProductAttrService {
    /**
     * 通过sku实体查询该产品是否拥有到货通知属性
     */
    public Integer selectSkuValue(ProductAttr record) throws SQLException;

    /**
     * 通过sku实体查询该产品草稿是否拥有到货通知属性
     */
    public Integer selectSkuValueDraft(ProductAttr record) throws SQLException;

}
