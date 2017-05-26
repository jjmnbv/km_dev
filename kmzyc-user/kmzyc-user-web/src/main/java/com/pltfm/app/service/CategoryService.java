package com.pltfm.app.service;

import java.util.List;

import com.pltfm.app.vobject.Category;

/**
 * 类目逻辑处理接口
 * 
 * @author Administrator
 */
public interface CategoryService {
  /**
   * 获取类目
   * 
   * @param category
   * @return
   * @throws Exception
   */
  List<Category> selectCategoryById(Long parentId) throws Exception;

  /**
   * 类目反推获取3级目录
   * 
   * @param parentId
   * @return
   * @throws Exception
   */
  List<Category> selectBackCategoryById(Long cateroryId) throws Exception;
}
