package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.CategorysDao;
import com.kmzyc.b2b.model.Categorys;
import com.km.framework.persistence.impl.DaoImpl;

@Service
public class CategorysDaoImpl extends DaoImpl implements CategorysDao {
  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  // 获取子类目列表
  public List<Categorys> queryCategorysSub(Map map) throws SQLException {
    List<Categorys> categorysList = sqlMapClient.queryForList("Categorys.queryCategorysSub", map);
    return categorysList;
  }


  // 所有商品父类
  public List selectCategoryParent(String categoryIds) throws SQLException {

    return sqlMapClient.queryForList("Categorys.abatorgenerated_selectCategoryParent", categoryIds);
  }



  public List selectByparentId(Long parentId) throws SQLException {
    List list = sqlMapClient.queryForList("Categorys.abatorgenerated_selectByparentId", parentId);
    return list;
  }

}
