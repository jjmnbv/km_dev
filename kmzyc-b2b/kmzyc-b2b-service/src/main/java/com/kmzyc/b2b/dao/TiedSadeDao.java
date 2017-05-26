package com.kmzyc.b2b.dao;

import java.sql.SQLException;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.TiedSade;

public interface TiedSadeDao extends Dao {
  TiedSade findById(Integer id) throws SQLException;
}
