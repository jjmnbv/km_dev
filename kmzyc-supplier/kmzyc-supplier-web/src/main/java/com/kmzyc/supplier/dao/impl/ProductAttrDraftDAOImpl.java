package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.dao.ProductAttrDraftDAO;
import com.pltfm.app.vobject.ProductAttrDraftExample;
@Repository("productAttrDraftDAO")
public class ProductAttrDraftDAOImpl implements ProductAttrDraftDAO {

	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public List selectByExample(ProductAttrDraftExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_DRAFT_ATTR.ibatorgenerated_selectByExample", example);
	}
}