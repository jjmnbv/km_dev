package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.ProductAttrDraftExample;

public interface ProductAttrDraftDAO {

    List selectByExample(ProductAttrDraftExample example) throws SQLException;
}