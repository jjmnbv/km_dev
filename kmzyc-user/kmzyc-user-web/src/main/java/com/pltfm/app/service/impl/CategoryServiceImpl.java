package com.pltfm.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.CategoryDAO;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.vobject.Category;

@Component(value = "categoryService")
public class CategoryServiceImpl implements CategoryService {
  @Resource(name = "categoryDAO")
  private CategoryDAO categoryDAO;

  // 获取类目逻辑处理
  public List<Category> selectCategoryById(Long parentId) throws Exception {
    return categoryDAO.selectCategoryById(parentId);
  }

  // 类目反推获取三级目录
  public List<Category> selectBackCategoryById(Long cateroryId) throws Exception {
    return categoryDAO.selectBackCategoryById(cateroryId);
  }



}
