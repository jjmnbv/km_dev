package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.dao.CategoryAttrValueDAO;
import com.kmzyc.supplier.service.CategoryAttrValueService;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pltfm.app.enums.IsValidStatus;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.CategoryAttrValueExample;

/**
 * 产品类目属性值业务逻辑类
 * @author humy
 * @since 2013-7-9
 */
@Service("categoryAttrValueService")
public class CategoryAttrValueServiceImpl implements CategoryAttrValueService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private CategoryAttrValueDAO categoryAttrValueDAO;
	

	@Override
	public CategoryAttrValue queryCategoryAttrValue(Long categoryAttrValueId) throws ServiceException {
        try {
            return categoryAttrValueDAO.selectByPrimaryKey(categoryAttrValueId);
        } catch (SQLException e) {
            logger.error("查询类目属性值对象失败：", e);
            throw new ServiceException(e);
        }
    }

	@Override
	public List<CategoryAttrValue> queryCategoryAttrValueList(Long categoryAttrId) throws ServiceException {
		CategoryAttrValueExample exam = new CategoryAttrValueExample();
		exam.createCriteria().andCategoryAttrIdEqualTo(categoryAttrId)
                .andStatusEqualTo(Integer.parseInt(IsValidStatus.VALID.getStatus()));
        exam.setOrderByClause("CATEGORY_ATTR_VALUE_ID");
        try {
            return categoryAttrValueDAO.selectByExample(exam);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
	
}


