package com.kmzyc.b2b.dao;

import java.sql.SQLException;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.Productmain;

public interface ProductmainDao extends Dao {

  Productmain findProductMainByProductSkuID(Long productSkuID) throws SQLException;

  Integer findProductMainByTypeAndID(Long productSkuID) throws SQLException;

  Productmain findProductSupplyType(Long productId) throws SQLException;
}
