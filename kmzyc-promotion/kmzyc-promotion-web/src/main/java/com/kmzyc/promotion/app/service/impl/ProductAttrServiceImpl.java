package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.promotion.app.dao.ProductAttrDAO;
import com.kmzyc.promotion.app.service.ProductAttrService;
import com.kmzyc.promotion.app.vobject.ProductAttr;
import com.kmzyc.promotion.app.vobject.ProductAttrExample;
import com.kmzyc.promotion.app.vobject.ProductAttrExample.Criteria;
import com.kmzyc.promotion.exception.ServiceException;

/**
 * 产品属性业务逻辑类
 * 
 * @author humy
 * @since 2013-7-9
 */
@Service("productAttrService")
@SuppressWarnings("unchecked")
public class ProductAttrServiceImpl implements ProductAttrService {
    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(ProductAttrServiceImpl.class);
    /**
     * 产品属性数据库接口
     */
    @Resource(name = "productAttrDAO")
    private ProductAttrDAO productAttrDAO;

    /**
     * 增加产品属性
     * 
     * @param productAttr 产品属性基本信息
     * @return
     * @throws Exception
     */
    @Override
    public void addProductAttr(ProductAttr productAttr) throws Exception {
        productAttrDAO.insert(productAttr);
    }

    /**
     * 查询产品属性
     * 
     * @param productAttr 产品属性基本信息
     * @return List<ProductAttr>
     * @throws Exception 异常
     */
    @Override
    public List<ProductAttr> queryProductAttr(ProductAttr productAttr) throws Exception {
        ProductAttrExample exam = new ProductAttrExample();
        Criteria criteria = exam.createCriteria();
        criteria.andProductIdEqualTo(productAttr.getProductId());
        if (productAttr.getProductAttrType() != null) {
            criteria.andProductAttrTypeEqualTo(productAttr.getProductAttrType());
        }
        if (productAttr.getRelateAttrId() != null) {
            criteria.andRelateAttrIdEqualTo(productAttr.getRelateAttrId());
        }
        if (StringUtils.isNotBlank(productAttr.getProductAttrName())) {
            criteria.andProductAttrNameEqualTo(productAttr.getProductAttrName());
        }
        List<ProductAttr> productAttrList = null;
        try {
            exam.setOrderByClause(" RELATE_ATTR_ID ");
            productAttrList = productAttrDAO.selectByExample(exam);
        } catch (SQLException e) {
            logger.error("查询产品属性异常：", e);
            return null;
        }
        return productAttrList;
    }

    /**
     * 更新产品属性
     * 
     * @param productAttr 产品属性基本信息
     * @return
     * @throws ServiceException
     */
    @Override
    public void updateProductAttr(ProductAttr productAttr) throws ServiceException {
        try {
            productAttrDAO.updateByPrimaryKeySelective(productAttr);
        } catch (SQLException e) {
            logger.error("更新产品属性异常：", e);
        }
    }

    /**
     * 删除产品属性
     * 
     * @param productAttr 产品属性基本信息
     * @return
     * @throws Exception
     */
    @Override
    public void deleteProductAttr(ProductAttr productAttr) throws Exception {
        ProductAttrExample exam = new ProductAttrExample();
        Criteria criteria = exam.createCriteria();
        criteria.andProductIdEqualTo(productAttr.getProductId());
        if (productAttr.getProductAttrType() != null) {
            criteria.andProductAttrTypeEqualTo(productAttr.getProductAttrType());
        }
        if (StringUtils.isNotBlank(productAttr.getProductAttrName())) {
            criteria.andProductAttrNameEqualTo(productAttr.getProductAttrName());
        }
        productAttrDAO.deleteByExample(exam);
    }
}
