package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.kmzyc.b2b.dao.TiedSadeDao;
import com.kmzyc.b2b.model.TiedSade;
import com.km.framework.persistence.impl.DaoImpl;

@Component
public class TiedSadeDaoImpl extends DaoImpl implements TiedSadeDao {

  @Override
  public TiedSade findById(Integer id) throws SQLException {
    return (TiedSade) findById("TiedSade.findById", id);
  }

}
