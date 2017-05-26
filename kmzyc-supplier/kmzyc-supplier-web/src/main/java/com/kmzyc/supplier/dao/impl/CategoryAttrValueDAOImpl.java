package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.dao.CategoryAttrValueDAO;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.CategoryAttrValueExample;
@Repository("categoryAttrValueDAO")
public class CategoryAttrValueDAOImpl implements CategoryAttrValueDAO {

	@Resource
    private SqlMapClient sqlMapClient;

	@Override
    public CategoryAttrValue selectByPrimaryKey(Long categoryAttrValueId) throws SQLException {
        CategoryAttrValue key = new CategoryAttrValue();
        key.setCategoryAttrValueId(categoryAttrValueId);
        return (CategoryAttrValue) sqlMapClient.queryForObject("CATEGORY_ATTR_VALUE.ibatorgenerated_selectByPrimaryKey", key);
    }

	@Override
	public List selectByExample(CategoryAttrValueExample example) throws SQLException {
		return sqlMapClient.queryForList("CATEGORY_ATTR_VALUE.ibatorgenerated_selectByExample", example);
	}
}