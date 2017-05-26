package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.supplier.dao.ProdAppraiseDao;
import com.kmzyc.supplier.model.ProdAppraiseAddContent;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository("prodAppraiseDao")
public class ProdAppraiseDaoImpl implements ProdAppraiseDao {

	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public List<ProdAppraiseAddContent> queryByOrderCode(String orderCode) throws SQLException {
		return sqlMapClient.queryForList("PROD_APPRAISE.queryByOrderCode", orderCode);
	}

}