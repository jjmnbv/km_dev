package com.kmzyc.promotion.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.promotion.app.dao.CategoryAttrValueDAO;
import com.kmzyc.promotion.app.enums.IsValidStatus;
import com.kmzyc.promotion.app.service.CategoryAttrValueService;
import com.kmzyc.promotion.app.vobject.CategoryAttrValue;
import com.kmzyc.promotion.app.vobject.CategoryAttrValueExample;

/**
 * 产品类目属性值业务逻辑类
 * 
 * @author humy
 * @since 2013-7-9
 */
@Service("categoryAttrValueService")
@SuppressWarnings("unchecked")
public class CategoryAttrValueServiceImpl implements CategoryAttrValueService {
    /**
     * 产品类别属性值数据库接口
     */
    @Resource(name = "categoryAttrValueDAO")
    private CategoryAttrValueDAO categoryAttrValueDAO;

    /**
     * 查询类目属性值对象
     * 
     * @param categoryAttrValueId 类目属性值ID
     * @return CategoryAttrValue 类目属性值
     * @throws Exception
     */
    @Override
    public CategoryAttrValue queryCategoryAttrValue(Long categoryAttrValueId) throws Exception {
        return categoryAttrValueDAO.selectByPrimaryKey(categoryAttrValueId);
    }

    /**
     * 查询类目属性值列表
     * 
     * @param categoryAttrId 类目属性ID
     * @return List<CategoryAttrValue> 类目属性值列表
     * @throws Exception
     */
    @Override
    public List<CategoryAttrValue> queryCategoryAttrValueList(Long categoryAttrId)
            throws Exception {
        CategoryAttrValueExample exam = new CategoryAttrValueExample();
        exam.createCriteria().andCategoryAttrIdEqualTo(categoryAttrId)
                .andStatusEqualTo(Integer.parseInt(IsValidStatus.VALID.getStatus()));
        return categoryAttrValueDAO.selectByExample(exam);
    }
}
