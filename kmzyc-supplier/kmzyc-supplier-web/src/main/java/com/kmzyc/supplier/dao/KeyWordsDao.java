package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.KeyWords;

public interface KeyWordsDao{

	List<KeyWords> findKeyWords() throws SQLException;
}