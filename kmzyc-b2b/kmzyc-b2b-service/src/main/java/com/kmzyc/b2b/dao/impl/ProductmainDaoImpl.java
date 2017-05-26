package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.kmzyc.b2b.dao.ProductmainDao;
import com.kmzyc.b2b.model.Productmain;
import com.km.framework.persistence.impl.DaoImpl;

@Component
public class ProductmainDaoImpl extends DaoImpl implements ProductmainDao {
  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  @Override
  public Productmain findProductMainByProductSkuID(Long productSkuID) throws SQLException {
    return (Productmain) sqlMapClient.queryForObject("Productmain.findProductMainByProductSkuID",
        productSkuID);
  }

  @Override
  public Integer findProductMainByTypeAndID(Long productSkuID) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("Productmain.findProductMainByTypeAndID",
        productSkuID);
  }

@Override
public Productmain findProductSupplyType(Long productId) throws SQLException {
	return (Productmain) sqlMapClient.queryForObject("Productmain.findProductSupplyType",
			productId);
}

}
