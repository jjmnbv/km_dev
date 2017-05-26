package com.pltfm.app.dao;

import com.pltfm.app.vobject.Category;

import java.util.List;

/**
 * 数据访问对象接口
 * 
 * @author Administrator
 */
public interface CategoryDAO {
  /**
   * 获取类目信息
   * 
   * @param category
   * @return
   * @throws Exception
   */
  List<Category> selectCategoryById(Long parentId) throws Exception;

  /**
   * 类目反推获取三级目录
   * 
   * @param parentId
   * @return
   * @throws Exception
   */
  List<Category> selectBackCategoryById(Long parentId) throws Exception;
}
