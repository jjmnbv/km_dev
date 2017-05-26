package com.pltfm.app.service.impl;

import com.pltfm.app.dao.ProductAttrDAO;
import com.pltfm.app.service.ProductAttrService;
import com.pltfm.app.vobject.ProductAttr;

import org.springframework.stereotype.Component;

import java.sql.SQLException;

import javax.annotation.Resource;

/**
 * 产品sku属性集合业务逻辑处理类
 *
 * @author Administrator
 */
@Component(value = "productAttrService")
public class ProductAttrServiceImpl implements ProductAttrService {
    @Resource(name = "productAttrDAO")
    private ProductAttrDAO productAttrDAO;

    /**
     * 判断产品是否拥有到货通知属性
     */
    public Integer selectSkuValue(ProductAttr record) throws SQLException {
        return productAttrDAO.selectSkuValue(record);
    }

    /**
     * 判断产品是否拥有到货通知属性
     */
    public Integer selectSkuValueDraft(ProductAttr record) throws SQLException {
        return productAttrDAO.selectSkuValueDraft(record);
    }

    public ProductAttrDAO getProductAttrDAO() {
        return productAttrDAO;
    }

    public void setProductAttrDAO(ProductAttrDAO productAttrDAO) {
        this.productAttrDAO = productAttrDAO;
    }

}
