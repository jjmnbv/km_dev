package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.ProdAppraiseAddContent;

public interface ProdAppraiseDao {

	List<ProdAppraiseAddContent> queryByOrderCode(String orderCode) throws SQLException;
	
}