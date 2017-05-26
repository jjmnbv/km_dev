package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.Categorys;

public interface CategorysDao extends Dao {
  // 获取子类目列表
  List<Categorys> queryCategorysSub(Map map) throws SQLException;

  // 所有商品父类
  public List selectCategoryParent(String categoryIds) throws SQLException;

  public List selectByparentId(Long parentId) throws Exception;
}
