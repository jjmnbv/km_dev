package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.dao.ViewSkuAttrDao;
import com.pltfm.app.vobject.ViewSkuAttr;

@Repository("viewSkuAttrDao")
public class ViewSkuAttrDaoImpl extends DaoImpl implements ViewSkuAttrDao {

  private static Logger logger= LoggerFactory.getLogger(ViewSkuAttrDao.class);
  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  @SuppressWarnings("unchecked")
  @Override
  public List<ViewSkuAttr> findBySkuId(Long skuId) {

    try {
      return (List<ViewSkuAttr>) sqlMapClient.queryForList("ProductSkuAttr.querySKUAttr", skuId);
    } catch (SQLException e) {
      logger.error(e.getMessage(),e);
      return null;
    }
  }

}
