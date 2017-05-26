package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.Categorys;

public interface CategorysService {
  // 获取子类目列表
  List<Categorys> queryCategorysSub(Map map) throws SQLException;

  // 获取所有窗口

  public List queryCategoryWindow(String windowFormat) throws SQLException;

  // 所有商品父类
  public List selectCategoryParent(String categoryIds) throws SQLException;


}
