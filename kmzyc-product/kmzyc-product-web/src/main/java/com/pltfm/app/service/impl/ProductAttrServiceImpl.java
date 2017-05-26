package com.pltfm.app.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.ProductAttrDAO;
import com.pltfm.app.service.ProductAttrService;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductAttrExample;
import com.pltfm.app.vobject.ProductAttrExample.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@Service("productAttrService")
public class ProductAttrServiceImpl implements ProductAttrService {

    @Resource
    private ProductAttrDAO productAttrDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void addProductAttr(ProductAttr productAttr) throws ServiceException {
        try {
            productAttrDao.insert(productAttr);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductAttr> queryProductAttr(ProductAttr productAttr) throws ServiceException {
        ProductAttrExample exam = new ProductAttrExample();
        Criteria criteria = exam.createCriteria();
        criteria.andProductIdEqualTo(productAttr.getProductId());
        if (productAttr.getProductAttrType() != null) {
            criteria.andProductAttrTypeEqualTo(productAttr.getProductAttrType());
        }
        if (productAttr.getRelateAttrId() != null)
            criteria.andRelateAttrIdEqualTo(productAttr.getRelateAttrId());
        if (StringUtils.isNotBlank(productAttr.getProductAttrName())) {
            criteria.andProductAttrNameEqualTo(productAttr.getProductAttrName());
        }
        if (productAttr.getChildrenAttrType() != null) {
            criteria.andChildrenAttrTypeEqualsTo(productAttr.getChildrenAttrType());
        }
        exam.setOrderByClause(" RELATE_ATTR_ID,sortno");

        try {
            return productAttrDao.selectByExample(exam);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateProductAttr(ProductAttr productAttr) throws ServiceException {
        try {
            productAttrDao.updateByPrimaryKeySelective(productAttr);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteProductAttr(ProductAttr productAttr) throws ServiceException {
        ProductAttrExample exam = new ProductAttrExample();
        Criteria criteria = exam.createCriteria();
        criteria.andProductIdEqualTo(productAttr.getProductId());
        if (productAttr.getProductAttrType() != null) {
            criteria.andProductAttrTypeEqualTo(productAttr.getProductAttrType());
        }
        if (StringUtils.isNotBlank(productAttr.getProductAttrName())) {
            criteria.andProductAttrNameEqualTo(productAttr.getProductAttrName());
        }

        try {
            productAttrDao.deleteByExample(exam);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteDraftByProductId(Long productId) throws ServiceException {
        ProductAttrExample exa = new ProductAttrExample();
        exa.createCriteria().andProductIdEqualTo(productId);

        try {
            productAttrDao.deleteByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}


