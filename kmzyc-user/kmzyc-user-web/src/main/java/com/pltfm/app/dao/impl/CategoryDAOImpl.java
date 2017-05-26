package com.pltfm.app.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.CategoryDAO;
import com.pltfm.app.vobject.Category;

/**
 * @author lijianjun
 */
@Component(value = "categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {
  /**
   * 数据库操作对象
   */
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  // 获取类目
  @SuppressWarnings("unchecked")
  public List<Category> selectCategoryById(Long parentId) throws Exception {
    List<Category> categoryList = sqlMapClient.queryForList("CATEGORY.selectById", parentId);
    return categoryList;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Category> selectBackCategoryById(Long categoryId) throws Exception {
    List<Category> categoryList = sqlMapClient.queryForList("CATEGORY.selectBack", categoryId);
    return categoryList;
  }



}
