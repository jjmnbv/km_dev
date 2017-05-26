package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.supplier.dao.CategoryAttrDAO;
import com.kmzyc.supplier.dao.CategoryAttrValueDAO;
import com.kmzyc.supplier.service.CategoryAttrService;
import com.pltfm.app.enums.IsValidStatus;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.CategoryAttrExample;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.CategoryAttrValueExample;


/**
 * 产品类别业务逻辑类
 * 
 * @author humy
 * @since 2013-7-9
 */
@Service("categoryAttrService")
public class CategoryAttrServiceImpl implements CategoryAttrService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private CategoryAttrDAO categoryAttrDAO;
	
	@Resource
	private CategoryAttrValueDAO categoryAttrValueDAO;

	public List<CategoryAttr> queryCategoryAttrList(CategoryAttr categoryAttr) throws ServiceException {
		CategoryAttrExample exam = new CategoryAttrExample();
		exam.createCriteria().andCategoryIdIn(categoryAttr.getCategoryIds())
				.andStatusEqualTo(Integer.parseInt(IsValidStatus.VALID.getStatus()));
		exam.setOrderByClause("sortno");
        try {
            List<CategoryAttr> categoryAttrList = categoryAttrDAO.selectByExample(exam);
            for (CategoryAttr attr : categoryAttrList) {
                CategoryAttrValueExample example = new CategoryAttrValueExample();
                example.createCriteria().andCategoryAttrIdEqualTo(attr.getCategoryAttrId())
                        .andStatusEqualTo(Integer.parseInt(IsValidStatus.VALID.getStatus()));
                example.setOrderByClause("CATEGORY_ATTR_VALUE_ID");
                List<CategoryAttrValue> categoryAttrValueList = categoryAttrValueDAO.selectByExample(example);
                if (categoryAttrValueList != null && categoryAttrValueList.size() > 0)
                    attr.setCategoryAttrValueList(categoryAttrValueList);
            }
            return categoryAttrList;
        } catch (SQLException e) {
            logger.error("查询类目属性列表失败 ：", e);
            throw new ServiceException(e);
        }
    }
}
