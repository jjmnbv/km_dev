package com.kmzyc.mailmobile.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.mailmobile.dao.KeyWordsDao;
import com.kmzyc.mailmobile.model.KeyWords;

import org.springframework.stereotype.Repository;

import com.km.framework.persistence.impl.DaoImpl;

@Repository("KeyWordsDao")
public class KeyWordsDaoImpl extends DaoImpl implements KeyWordsDao {
	@javax.annotation.Resource(name = "sqlMapClient")
	private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<KeyWords> findKeyWords() throws SQLException {
		return sqlMapClient.queryForList("KeyWords.Words");
		
	}

}
