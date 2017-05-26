package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.CategoryAttrExample;
import com.pltfm.app.vobject.Sections;


public interface CategoryAttrDAO {

    List selectByExample(CategoryAttrExample example) throws SQLException;
}