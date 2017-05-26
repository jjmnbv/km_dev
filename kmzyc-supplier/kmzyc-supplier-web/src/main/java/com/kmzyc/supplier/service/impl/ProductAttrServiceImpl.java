package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.dao.ProductAttrDAO;
import com.kmzyc.supplier.service.OperationAttrService;
import com.kmzyc.supplier.service.ProductAttrService;
import com.pltfm.app.enums.ProductAttrType;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductAttrExample;
import com.pltfm.app.vobject.ProductAttrExample.Criteria;

/**
 * 产品属性业务逻辑类
 */
@Service("productAttrService")
public class ProductAttrServiceImpl implements ProductAttrService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "productAttrDAO")
    private ProductAttrDAO productAttrDAO;

    @Resource(name = "operationAttrService")
    private OperationAttrService operationAttrService;

    @Override
    public List<ProductAttr> queryProductAttr(ProductAttr productAttr) throws ServiceException {
        ProductAttrExample exam = new ProductAttrExample();
        Criteria criteria = exam.createCriteria();
        criteria.andProductIdEqualTo(productAttr.getProductId());
        if (productAttr.getProductAttrType() != null) {
            criteria.andProductAttrTypeEqualTo(productAttr.getProductAttrType());
        }
        if (productAttr.getRelateAttrId() != null) {
            criteria.andRelateAttrIdEqualTo(productAttr.getRelateAttrId());
        }
        if(StringUtils.isNotBlank(productAttr.getProductAttrName())){
            criteria.andProductAttrNameEqualTo(productAttr.getProductAttrName());
        }
        exam.setOrderByClause(" RELATE_ATTR_ID ");
        try {
            return productAttrDAO.selectByExample(exam);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateCategoryAttrValue(Product product, List<ProductAttr> prodAttrList) throws ServiceException {
        try {
            if (CollectionUtils.isNotEmpty(prodAttrList)) {
                for (ProductAttr productAttr : prodAttrList) {
                    if (productAttr.getProductAttrId() == null) {
                        continue;
                    }

                    String[] values = productAttr.getProductAttrValues();
                    if (values != null && values.length > 0) {
                        if (values.length == 1) {
                            productAttr.setProductAttrValue(values[0]);
                        } else {
                            productAttr.setProductAttrValue(StringUtils.join(values, ","));
                        }
                    } else {
                        productAttr.setProductAttrValue("");
                    }
                }
                int result = productAttrDAO.updateObject(prodAttrList);
                if (result == 0) {
                    throw new ServiceException("产品类目属性更新，先进行数据处理失败.");
                }
            }
        } catch (Exception e) {
            log.error("产品类目属性更新，先进行数据处理失败", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateDefinitionAttrValue(Product product, Long productId, List<ProductAttr> productAttrList) throws ServiceException {
        ProductAttrExample exa = new ProductAttrExample();
        exa.createCriteria().andProductIdEqualTo(productId).andProductAttrTypeEqualTo(ProductAttrType.DEFINITION.getType());
        try {
            productAttrDAO.deleteByExample(exa);
            if (CollectionUtils.isNotEmpty(productAttrList)) {
                List<ProductAttr> list = new ArrayList();
                productAttrList.stream()
                        .filter(productAttr -> productAttr != null && productAttr.getProductId() == null)
                        .forEach(productAttr -> {
                            productAttr.setProductId(productId);
                            productAttr.setProductAttrType(ProductAttrType.DEFINITION.getType());
                            list.add(productAttr);
                        });
                if (CollectionUtils.isNotEmpty(list)) {
                    int result = productAttrDAO.insertList(list);
                    if (result == 0) throw new ServiceException("自定义属性更新失败。");
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateOperationAttrValue(Product product, Long productId, Long[] operationAttrIds) throws ServiceException {
        ProductAttrExample exa = new ProductAttrExample();
        exa.createCriteria().andProductIdEqualTo(productId).andProductAttrTypeEqualTo(ProductAttrType.OPERATION.getType());
        try {
            productAttrDAO.deleteByExample(exa);
            if (operationAttrIds != null && operationAttrIds.length > 0) {
                List<ProductAttr> list = new ArrayList<ProductAttr>();
                ProductAttr productAttr = null;
                for (Long operationAttrId : operationAttrIds) {
                    productAttr = new ProductAttr();
                    productAttr.setProductId(productId);
                    productAttr.setProductAttrType(ProductAttrType.OPERATION.getType());
                    productAttr.setRelateAttrId(operationAttrId);
                    OperationAttr operationAttr = operationAttrService.queryOperationAttr(operationAttrId);
                    productAttr.setProductAttrName(operationAttr.getOperationAttrName());
                    productAttr.setProductAttrValue(operationAttr.getOperationAttrId().toString());
                    productAttr.setIsNav(operationAttr.getIsNav());
                    list.add(productAttr);
                }
                if (CollectionUtils.isNotEmpty(list)) {
                    int result = productAttrDAO.insertList(list);
                    if (result == 0) {
                        throw new ServiceException("插入产品属性失败。");
                    }
                }
            }
        } catch (Exception e) {
            log.error("运营属性更新失败。", e);
            throw new ServiceException(e);
        }
    }
}
