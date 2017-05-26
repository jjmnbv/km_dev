package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.CategoryAttrValueExample;

public interface CategoryAttrValueDAO {

	CategoryAttrValue selectByPrimaryKey(Long categoryAttrValueId) throws SQLException;
	
	List selectByExample(CategoryAttrValueExample example) throws SQLException;
}