package com.pltfm.app.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.dao.CategoryDAO;
import com.pltfm.app.enums.CategoryType;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.util.CodeUtils;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.CategoryExample;
import com.pltfm.app.vobject.CategoryExample.Criteria;
import com.pltfm.app.vobject.CategoryPv;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

/**
 * 产品类别业务逻辑类
 * 
 * @author humy
 * @since 2013-7-9
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

	@Resource
	private CategoryDAO categoryDao;

	@Override
	public List<Category> queryCategoryRebateList(Category category) throws ServiceException {
		try {
            return categoryDao.queryCategoryRebateList(category);
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Category> queryCategoryList(Category category) throws ServiceException {
		try {
            return categoryDao.queryCategoryList(category);
		} catch (SQLException e) {
            throw new ServiceException(e);
		}
	}

	@Override
	public Category showCategory(Long categoryId) throws ServiceException {
        try {
            return categoryDao.selectByPrimaryKey(categoryId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public synchronized void addCategory(Category category) throws ServiceException {
        try {
            if (CategoryType.PHYSICS.getKey() == category.getIsPhy().intValue()) {
                String maxLevelCode = categoryDao.queryMaxCategoryNoByParentId(category.getParentId());
                if (StringUtils.isEmpty(maxLevelCode)) {
                    if (StringUtils.isEmpty(category.getCategoryCode())) {
                        category.setCategoryCode("01");
                    } else {
                        category.setCategoryCode(category.getCategoryCode() + "01");
                    }
                } else {
                    category.setCategoryCode(CodeUtils.getCode(maxLevelCode));
                }
            } else {
                category.setCategoryCode(null);
            }
            categoryDao.insert(category);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void updateCategory(Category category) throws ServiceException {
        try {
            categoryDao.updateByPrimaryKeySelective(category);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public List<Category> queryCategoryChildrenList(Category category) throws ServiceException {
        CategoryExample exam = new CategoryExample();
        Criteria criteria = exam.createCriteria();
        criteria.andParentIdEqualTo(category.getCategoryId()).andStatusEqualTo(category.getStatus());
        if (category.getIsPhy() != null) {
            criteria.andIsPhyEqualTo(category.getIsPhy());
        }

        try {
            return categoryDao.selectByExample(exam);
		} catch (SQLException e) {
            throw new ServiceException(e);
		}
	}

	@Override
	public List<Category> queryCategoryParentList(Category category) throws ServiceException {
		try {
            return categoryDao.queryCategoryParentList(category);
		} catch (SQLException e) {
            throw new ServiceException(e);
		}
	}

	@Override
	public boolean queryRepeatName(Category category) throws ServiceException {
        try {
            return categoryDao.queryRepeatName(category) > 0;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public String delCategory(Long categoryId) throws ServiceException {
        try {
            List<String> relationList = categoryDao.findIsRelationCateId(categoryId);
            if (CollectionUtils.isNotEmpty(relationList)) {
                return StringUtils.join(relationList.stream().map(categoryName -> "【"+categoryName+"】")
                        .collect(Collectors.toList()),",");
            } else {
                delCategoryFile(categoryId);
                categoryDao.relationDelete(categoryId);
            }
            return null;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public List<Category> findSomePhyCategories() throws ServiceException {
        try {
            return categoryDao.findSomePhyCategories();
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int copyCategories(Long[] copyCategoryIds) throws ServiceException {
		List<Category> list = null;
		Category c = null;
		Long firstParentId = Long.valueOf(0);
		for (Long categoryId : copyCategoryIds) {
			c = new Category();
			c.setCategoryId(categoryId);
			try {
				list = categoryDao.queryCategoryList(c);
				Category firstCate = list.get(0);
				firstCate.setCategoryCode(null);
				firstParentId = firstCate.getCategoryId();
				firstCate.setIsPhy(CategoryType.BUISNESS.getKey());
				addCategory(firstCate);

				Category secondParent = null;
				for (int i = 1; i < list.size(); i++) {
					Category cg = list.get(i);
					cg.setIsPhy(CategoryType.BUISNESS.getKey());
					if (cg.getParentId().equals(firstParentId)) {
						secondParent = cg;
						secondParent.setParentId(firstCate.getCategoryId());
						secondParent.setCategoryCode(firstCate.getCategoryCode());
						addCategory(secondParent);
					} else {
						cg.setParentId(secondParent.getCategoryId());
						cg.setCategoryCode(secondParent.getCategoryCode());
						addCategory(cg);
					}
				}
			} catch (Exception e) {
				throw new ServiceException(e);
			}
		}
		return 1;
	}

	@Override
	public void delCategoryFile(Long categoryId) throws ServiceException {
		Category temp = showCategory(categoryId);
		if (temp != null && StringUtils.isNotEmpty(temp.getFilePath())) {
			File f = new File(ConfigurationUtil.getString("pictureUploadPath") + temp.getFilePath());
			if (f.exists()) {
				f.delete();
			}
		}
	}

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void delRebateCategory() throws ServiceException {
        try {
            categoryDao.delRebateCategory();
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void addRebateCategory(List<Category> listCategories) throws ServiceException {
        try {
            categoryDao.addRebateCategory(listCategories);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public void queryCategoryOneLevelList(Category category, Page page) throws ServiceException {
        try {
            List<Category> list = categoryDao.queryCategoryOneLevelList(category, page);
            page.setDataList(list);
            page.setRecordCount(categoryDao.countItemCategories(category));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CategoryPv> queryPVCategoryList() throws ServiceException {
        try {
            return categoryDao.queryPVCategoryList();
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void modifyPvCategory(List<Category> categoryList) throws ServiceException {
        try {
            categoryDao.delPvCategory();
            categoryDao.addPvCategory(categoryList);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
