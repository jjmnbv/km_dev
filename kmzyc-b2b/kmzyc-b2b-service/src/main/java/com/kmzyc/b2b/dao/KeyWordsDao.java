package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.KeyWords;

public interface KeyWordsDao extends Dao {
  List<KeyWords> findKeyWords() throws SQLException;
}
