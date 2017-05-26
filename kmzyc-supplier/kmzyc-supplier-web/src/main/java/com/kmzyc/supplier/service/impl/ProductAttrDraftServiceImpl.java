package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.dao.ProductAttrDraftDAO;
import com.kmzyc.supplier.service.ProductAttrDraftService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pltfm.app.vobject.ProductAttrDraft;
import com.pltfm.app.vobject.ProductAttrDraftExample;

/**
 * 产品属性业务逻辑类
 * 
 * 
 */
@Service("productAttrDraftService")
public class ProductAttrDraftServiceImpl implements ProductAttrDraftService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ProductAttrDraftDAO productAttrDraftDAO;

	@Override
	public List<ProductAttrDraft> queryProductAttrDraft(ProductAttrDraft productAttr) throws ServiceException {
		ProductAttrDraftExample exam = new ProductAttrDraftExample();
		ProductAttrDraftExample.Criteria criteria= exam.createCriteria();
		criteria.andProductIdEqualTo(productAttr.getProductId());
		if(productAttr.getProductAttrType() != null){
			criteria.andProductAttrTypeEqualTo(productAttr.getProductAttrType());
		}
		if(productAttr.getRelateAttrId()!=null) {
            criteria.andRelateAttrIdEqualTo(productAttr.getRelateAttrId());
        }
		if(StringUtils.isNotBlank(productAttr.getProductAttrName())){
			criteria.andProductAttrNameEqualTo(productAttr.getProductAttrName());
		}
        exam.setOrderByClause(" RELATE_ATTR_ID ");
		try {
            return productAttrDraftDAO.selectByExample(exam);
		} catch (SQLException e) {
            logger.error("查询产品属性失败:", e);
            throw new ServiceException(e);
		}
	}
}