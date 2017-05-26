package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.dao.ProductSkuAttrDAO;
import com.kmzyc.supplier.service.ProductSkuAttrService;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pltfm.app.vobject.ProductSkuAttr;
import com.pltfm.app.vobject.ProductSkuAttrExample;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 产品SKU属性业务逻辑类
 * 
 */
@Service("productSkuAttrService")
public class ProductSkuAttrServiceImpl implements ProductSkuAttrService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ProductSkuAttrDAO productSkuAttrDAO;
	
	public List<ProductSkuAttr> queryProductSkuAttrList(ProductSkuAttr productSkuAttr) throws ServiceException{
        try {
            ProductSkuAttrExample example = new ProductSkuAttrExample();
            example.createCriteria().andProductSkuIdEqualTo(productSkuAttr.getProductSkuId());
            example.setOrderByClause(" CATEGORY_ATTR_ID ");
            return productSkuAttrDAO.selectByExample(example);
        } catch (SQLException e) {
            logger.error("查询SKU属性失败：", e);
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public void insertProductSkuAttr(ProductSkuAttr productSkuAttr) throws ServiceException {
		try {
			productSkuAttrDAO.insertProductSkuAttr(productSkuAttr);
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
	}
}


