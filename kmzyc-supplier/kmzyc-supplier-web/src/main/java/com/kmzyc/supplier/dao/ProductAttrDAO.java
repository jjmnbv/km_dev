package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductAttrExample;

public interface ProductAttrDAO {

	List selectByExample(ProductAttrExample example) throws SQLException;
	
	int updateObject(List<ProductAttr> list) throws SQLException;
	
	int deleteByExample(ProductAttrExample example) throws SQLException;
	
	int insertList(List<ProductAttr> list) throws SQLException;
	
}