package com.kmzyc.supplier.dao.impl;


import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.KeyWords;
import com.kmzyc.supplier.dao.KeyWordsDao;

@Repository("KeyWordsDao")
public class KeyWordsDaoImpl implements KeyWordsDao {

    @Resource
	private SqlMapClient sqlMapClient;
	
	@Override
	public List<KeyWords> findKeyWords() throws SQLException {
		return sqlMapClient.queryForList("KeyWords.Words");
	}

}