package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.dao.ProdBrandDAO;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProdBrandExample;

@Repository("prodBrandDao")
public class ProdBrandDaoImpl implements ProdBrandDAO {
	
	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public List<ProdBrand> selectByExample(ProdBrandExample example) throws SQLException {
		return sqlMapClient.queryForList("PROD_BRAND.ibatorgenerated_selectByExample", example);
	}

	@Override
	public int countByExample(ProdBrandExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject("PROD_BRAND.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	@Override
	public List selectByExample(ProdBrandExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("PROD_BRAND.ibatorgenerated_selectByExample", example, skip, max);
	}

}
