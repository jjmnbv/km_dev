package com.pltfm.app.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.CategoryAttrDAO;
import com.pltfm.app.dao.CategoryAttrValueDAO;
import com.pltfm.app.dao.ProductAttrDAO;
import com.pltfm.app.enums.CategoryAttrInputType;
import com.pltfm.app.enums.IsValidStatus;
import com.pltfm.app.service.CategoryAttrService;
import com.pltfm.app.service.ProductSkuAttrDraftService;
import com.pltfm.app.service.ProductSkuAttrService;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.CategoryAttrExample;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.CategoryAttrValueExample;
import com.pltfm.app.vobject.ProductAttr;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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
    private CategoryAttrDAO categoryAttrDao;

    @Resource
    private CategoryAttrValueDAO categoryAttrValueDao;

    @Resource
    private ProductSkuAttrService productSkuAttrService;

    @Resource
    private ProductSkuAttrDraftService productSkuAttrDraftService;

    @Resource
    private ProductAttrDAO productAttrDao;

    @Override
    public Page queryCategoryAttrList(Page pageParam, CategoryAttr categoryAttr) throws ServiceException {
        int pageNo = pageParam.getPageNo();// 当前查询第几页
        if (pageNo == 0)
            pageNo = 1;// 首次查询第一页
        int pageSize = pageParam.getPageSize(); // 每页显示记录数
        int skip = (pageNo - 1) * pageSize + 1;
        int max = (pageNo - 1) * pageSize + pageSize;
        Page page = null;

        try {
            categoryAttr.setSkip(skip);
            categoryAttr.setMax(max);
            categoryAttr.setStatus(Integer.parseInt(IsValidStatus.VALID.getStatus()));
            page = categoryAttrDao.selectPageByVo(pageParam, categoryAttr);
            page.setPageNo(pageNo);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return page;
    }

    @Override
    public CategoryAttr showCategoryAttr(CategoryAttr categoryAttr) throws ServiceException {
        try {
            CategoryAttr categoryAttrResult = categoryAttrDao.selectByPrimaryKey(categoryAttr.getCategoryAttrId());
            String name = categoryAttrDao.checkIsRelationAttr(categoryAttr.getCategoryAttrId());
            if (StringUtils.isNotBlank(name)) {
                categoryAttrResult.setIsUsed(1);
            }
            CategoryAttrValueExample example = new CategoryAttrValueExample(); // 组装where查询条件的对象
            example.createCriteria().andCategoryAttrIdEqualTo(categoryAttr.getCategoryAttrId())
                    .andStatusEqualTo(Integer.parseInt(IsValidStatus.VALID.getStatus()));
            example.setOrderByClause(" CATEGORY_ATTR_VALUE_ID ");

            List<CategoryAttrValue> categoryAttrValueList = categoryAttrValueDao.selectByExample(example);
            if (CollectionUtils.isNotEmpty(categoryAttrValueList)) {
                List<Long> oldValueIds = new ArrayList();
                for (CategoryAttrValue value : categoryAttrValueList) {
                    oldValueIds.add(value.getCategoryAttrValueId());
                    if (categoryAttrResult.getInputType().intValue() != CategoryAttrInputType.TEXT.getType().intValue()
                            && productAttrDao.queryRelationAttrValue(value.getCategoryAttrValueId())) {
                            value.setIsUsed(1);
                    }
                }
                categoryAttrResult.setOldValueIds(oldValueIds);
                categoryAttrResult.setCategoryAttrValueList(categoryAttrValueList);
            }
            return categoryAttrResult;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void addCategoryAttr(CategoryAttr categoryAttr) throws ServiceException {
        try {
            categoryAttrDao.insert(categoryAttr);
            List<CategoryAttrValue> categoryAttrValueList = categoryAttr.getCategoryAttrValueList();
            if (CollectionUtils.isNotEmpty(categoryAttrValueList)) {
                for (CategoryAttrValue categoryAttrValue : categoryAttrValueList) {
                    categoryAttrValue.setCategoryAttrId(categoryAttr.getCategoryAttrId());
                    categoryAttrValueDao.insert(categoryAttrValue);
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public synchronized void updateCategoryAttr(CategoryAttr categoryAttr) throws ServiceException {
        try {
            Long categoryAttrId = categoryAttr.getCategoryAttrId();

            if (categoryAttrDao.checkIsRelationAttr(categoryAttrId) != null) {
                categoryAttrDao.updateByPrimaryKeySelective(categoryAttr);

                List<CategoryAttrValue> categoryAttrValueList = categoryAttr.getCategoryAttrValueList();
                if (categoryAttrValueList != null) {
                    for (CategoryAttrValue categoryAttrValue : categoryAttrValueList) {
                        Long categoryAttrValueId = categoryAttrValue.getCategoryAttrValueId();
                        if (categoryAttrValueId != null && categoryAttrValueDao.selectByPrimaryKey(categoryAttrValueId) != null) {
                            categoryAttrValue.setCategoryAttrId(categoryAttrId);
                            categoryAttrValueDao.updateByPrimaryKeySelective(categoryAttrValue);
                        } else {
                            categoryAttrValue.setCategoryAttrId(categoryAttrId);
                            categoryAttrValueDao.insert(categoryAttrValue);
                        }
                    }
                }

                ProductAttr pa = new ProductAttr();
                pa.setProductAttrName(categoryAttr.getCategoryAttrName());
                pa.setIsNav(categoryAttr.getIsNav());
                pa.setIsReq(categoryAttr.getIsReq());
                pa.setRelateAttrId(categoryAttrId);
                productAttrDao.updateByRelationId(pa);
            } else {
                categoryAttrDao.updateByPrimaryKeySelective(categoryAttr);
                List<CategoryAttrValue> categoryAttrValueList = categoryAttr.getCategoryAttrValueList();
                if (CollectionUtils.isNotEmpty(categoryAttrValueList)) {
                    for (CategoryAttrValue categoryAttrValue : categoryAttrValueList) {
                        if (categoryAttrValue.getCategoryAttrValueId() == null) {
                            categoryAttrValue.setCategoryAttrId(categoryAttrId);
                            categoryAttrValueDao.insert(categoryAttrValue);
                        }
                    }
                }
            }

            List<Long> deleteValueIds = categoryAttr.getDeleteValueIds();
            if (CollectionUtils.isNotEmpty(deleteValueIds)) {
                for (Long valueId : deleteValueIds) {
                    categoryAttrValueDao.deleteByPrimaryKey(valueId);
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CategoryAttr> queryCategoryAttrList(CategoryAttr categoryAttr) throws ServiceException {
        CategoryAttrExample exam = new CategoryAttrExample();
        exam.createCriteria().andCategoryIdIn(categoryAttr.getCategoryIds())
                .andStatusEqualTo(Integer.parseInt(IsValidStatus.VALID.getStatus()));
        exam.setOrderByClause("sortno");

        try {
            List<CategoryAttr> categoryAttrList = categoryAttrDao.selectByExample(exam);
            for (CategoryAttr attr : categoryAttrList) {
                CategoryAttrValueExample example = new CategoryAttrValueExample(); // 组装where查询条件的对象
                example.createCriteria().andCategoryAttrIdEqualTo(attr.getCategoryAttrId())
                        .andStatusEqualTo(Integer.parseInt(IsValidStatus.VALID.getStatus()));
                List<CategoryAttrValue> categoryAttrValueList = categoryAttrValueDao.selectByExample(example);
                if (CollectionUtils.isNotEmpty(categoryAttrValueList)) {
                    attr.setCategoryAttrValueList(categoryAttrValueList);
                }
            }
            return categoryAttrList;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public String deleteByPrimaryKey(String[] delId) throws ServiceException {
        try {
            StringBuffer sb = new StringBuffer();
            for (String id : delId) {
                String name = categoryAttrDao.checkIsRelationAttr(Long.valueOf(id));
                if (name != null) {
                    sb.append("【").append(name).append("】").append(",");
                } else {
                    categoryAttrDao.deleteByPrimaryKey(Long.valueOf(id));

                    CategoryAttrValueExample example = new CategoryAttrValueExample();
                    example.createCriteria().andCategoryAttrIdEqualTo(Long.valueOf(id));
                    categoryAttrValueDao.deleteByExample(example);
                }
            }
            if (sb.length() != 0) {
                return sb.substring(0, sb.length() - 1);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean findAttrRepeatName(CategoryAttr categoryAttr) throws ServiceException {
        try {
            int index = categoryAttrDao.findRepeatAttrName(categoryAttr);
            return index > 0;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CategoryAttr findByPrimaryKey(Long categoryAttrId) throws ServiceException {
        try {
            return categoryAttrDao.selectByPrimaryKey(categoryAttrId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
