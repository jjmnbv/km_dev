package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.promotion.app.dao.CategoryDAO;
import com.kmzyc.promotion.app.enums.CategoryType;
import com.kmzyc.promotion.app.service.CategoryService;
import com.kmzyc.promotion.app.util.CodeUtils;
import com.kmzyc.promotion.app.vobject.Category;
import com.kmzyc.promotion.app.vobject.CategoryExample;
import com.kmzyc.promotion.app.vobject.CategoryExample.Criteria;
import com.kmzyc.promotion.exception.ServiceException;

/**
 * 产品类别业务逻辑类
 * 
 * @author humy
 * @since 2013-7-9
 */
@Service("categoryService")
@SuppressWarnings("unchecked")
public class CategoryServiceImpl implements CategoryService {
  // 日志记录
  private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
  /**
   * 产品类别数据库接口
   */
  @Resource(name = "categoryDAO")
  private CategoryDAO categoryDAO;

  /**
   * 类目信息列表
   * 
   * @throws Exception 异常
   */
  @Override
  public List<Category> queryCategoryList(Category category) throws Exception {
    List<Category> list = null;
    try {
      list = categoryDAO.queryCategoryList(category);
    } catch (SQLException e) {
      logger.error("类目信息列表获取异常：", e);
      throw e;
    }
    return list;
  }

  /**
   * 进入编辑类目页面
   * 
   * @param categoryId 类目ID
   * @return Category
   * @throws Exception 异常
   */
  @Override
  public Category showCategory(Long categoryId) throws Exception {
    return categoryDAO.selectByPrimaryKey(categoryId);
  }

  /**
   * 添加类目
   * 
   * @param category 类目基本信息
   * @return
   * @throws Exception
   */
  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
      rollbackFor = ServiceException.class)
  public synchronized void addCategory(Category category) throws Exception {
    if (category.getIsPhy().equals(1)) {
      String code = categoryDAO.queryMaxCategoryNoByParentId(category.getParentId());
      if (code == null || code.isEmpty()) {
        if (category.getCategoryCode() == null || category.getCategoryCode().isEmpty()) {
          category.setCategoryCode("01");
        } else if (category.getCategoryCode().length() == 2) {
          category.setCategoryCode(category.getCategoryCode() + "01");
        } else if (category.getCategoryCode().length() == 4) {
          category.setCategoryCode(category.getCategoryCode() + "01");
        }
      } else {
        category.setCategoryCode(CodeUtils.getCode(code));
      }
    }
    categoryDAO.insert(category);
  }

  /**
   * 更新类目
   * 
   * @param category 类目基本信息
   * @return
   * @throws Exception
   */
  @Override
  public void updateCategory(Category category) throws Exception {
    categoryDAO.updateByPrimaryKeySelective(category);
  }

  /**
   * 删除类目
   * 
   * @param category 类目基本信息
   * @return
   * @throws Exception
   */
  @Override
  public void deleteCategory(Category category) throws Exception {
    categoryDAO.deleteCategory(category);
  }

  /**
   * 查询类目下的孩子节点
   * 
   * @param Category 类目基本信息
   * @return List<Category> 类目基本信息列表
   * @throws Exception
   */
  @Override
  public List<Category> queryCategoryChildrenList(Category category) throws Exception {
    List<Category> list = null;
    try {
      CategoryExample exam = new CategoryExample(); // 组装where查询条件的对象
      Criteria criteria = exam.createCriteria();
      criteria.andParentIdEqualTo(category.getCategoryId()).andStatusEqualTo(category.getStatus());
      if (category.getIsPhy() != null) {
        criteria.andIsPhyEqualTo(category.getIsPhy());
      }
      list = categoryDAO.selectByExample(exam);
    } catch (SQLException e) {
      logger.warn(e.getMessage(), e);
      throw e;
    }
    return list;
  }

  /**
   * 查询类目及其父节点
   * 
   * @param Category 类目基本信息
   * @return List<Category> 类目基本信息列表
   * @throws Exception
   */
  @Override
  public List<Category> queryCategoryParentList(Category category) throws Exception {
    List<Category> list = null;
    try {
      list = categoryDAO.queryCategoryParentList(category);
    } catch (SQLException e) {
      logger.error("查询类目及其父节点异常：", e);
      throw e;
    }
    return list;
  }

  /**
   * 查询能上架的类别
   * 
   * @param category
   * @return
   * @throws Exception
   */
  @Override
  public List<Category> queryCanUpShelfCategoryList(Category category) throws Exception {
    List<Category> list = null;
    try {
      list = categoryDAO.queryCanUpShelfCategoryList(category);
    } catch (SQLException e) {
      logger.error("查询能上架的类别异常：", e);
      throw e;
    }
    return list;
  }

  @Override
  public boolean queryRepeatName(Category category) throws Exception {
    if (categoryDAO.queryRepeatName(category) > 0) {
      return true;
    }
    return false;
  }

  @Override
  public String delCategory(Long categoryId) throws Exception {
    List<String> li = categoryDAO.findIsRelationCateId(categoryId);
    if (li != null && li.size() > 0) {
      StringBuffer sb = new StringBuffer();
      for (String str : li) {
        sb.append("【").append(str).append("】").append(",");
      }
      return sb.substring(0, sb.length() - 1);
    } else {
      categoryDAO.relationDelete(categoryId);
    }

    return null;
  }

  @Override
  public List<Category> findSomePhyCategorys() throws Exception {
    return categoryDAO.findSomePhyCategorys();
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
      rollbackFor = ServiceException.class)
  public int copyCategorys(Long[] copyCategoryIds) throws ServiceException {
    List<Category> list = null;
    Category c = null;
    Long firstParentId = new Long(0);
    for (Long categoryId : copyCategoryIds) {
      c = new Category();
      c.setCategoryId(categoryId);
      try {
        list = categoryDAO.queryCategoryList(c);
        Category firstCate = list.get(0);
        firstCate.setCategoryCode(null);

        firstParentId = firstCate.getCategoryId();

        firstCate.setIsPhy(CategoryType.BUISNESS.getKey());
        this.addCategory(firstCate);
        Category secondParent = null;
        for (int i = 1; i < list.size(); i++) {
          Category cg = list.get(i);
          cg.setIsPhy(CategoryType.BUISNESS.getKey());
          if (cg.getParentId().equals(firstParentId)) {
            secondParent = cg;
            secondParent.setParentId(firstCate.getCategoryId());
            secondParent.setCategoryCode(firstCate.getCategoryCode());
            this.addCategory(secondParent);
          } else {
            cg.setParentId(secondParent.getCategoryId());
            cg.setCategoryCode(secondParent.getCategoryCode());
            this.addCategory(cg);
          }
        }
      } catch (Exception e) {
        logger.error("查询能上架的类别异常：", e);
        throw new ServiceException(e);
      }
    }
    return 1;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
      rollbackFor = ServiceException.class)
  public void updateCategoryByBusiCatogory(Category category) throws ServiceException {
    try {
      int row = categoryDAO.updateByPrimaryKeySelective(category);
      if (row < 1) {
        throw new ServiceException("运营类目修改失败!");
      }
    } catch (SQLException e) {
      logger.error("运营类目修改失败：", e);
      throw new ServiceException(e);
    }

  }
}
