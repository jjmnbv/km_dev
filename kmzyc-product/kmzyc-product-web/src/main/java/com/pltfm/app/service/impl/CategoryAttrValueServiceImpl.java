package com.pltfm.app.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.CategoryAttrValueDAO;
import com.pltfm.app.enums.IsValidStatus;
import com.pltfm.app.service.CategoryAttrValueService;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.CategoryAttrValueExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * 产品类目属性值业务逻辑类
 *
 * @author humy
 * @since 2013-7-9
 */
@Service("categoryAttrValueService")
public class CategoryAttrValueServiceImpl implements CategoryAttrValueService {

    @Resource
    private CategoryAttrValueDAO categoryAttrValueDao;

    @Override
    public CategoryAttrValue queryCategoryAttrValue(Long categoryAttrValueId) throws ServiceException {
        try {
            return categoryAttrValueDao.selectByPrimaryKey(categoryAttrValueId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CategoryAttrValue> queryCategoryAttrValueList(Long categoryAttrId) throws ServiceException {
        CategoryAttrValueExample exam = new CategoryAttrValueExample();
        exam.createCriteria().andCategoryAttrIdEqualTo(categoryAttrId)
                .andStatusEqualTo(Integer.parseInt(IsValidStatus.VALID.getStatus()));
        try {
            return categoryAttrValueDao.selectByExample(exam);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String getCategoryAttrValueByValueIds(String[] categoryAttrValueIds) throws ServiceException {
        try {
            return categoryAttrValueDao.getCategoryAttrValueByValueIds(categoryAttrValueIds);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}