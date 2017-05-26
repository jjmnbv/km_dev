package com.kmzyc.mailmobile.dao;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.persistence.Dao;
import com.kmzyc.mailmobile.model.KeyWords;

public interface KeyWordsDao extends Dao{
	List<KeyWords> findKeyWords() throws SQLException;
}
