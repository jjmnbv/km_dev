package com.kmzyc.supplier.dao;

import com.pltfm.app.vobject.ProductSkuAttr;
import com.pltfm.app.vobject.ProductSkuAttrExample;
import java.sql.SQLException;
import java.util.List;

public interface ProductSkuAttrDAO {
	
	List selectByExample(ProductSkuAttrExample example) throws SQLException;
	
	void insertProductSkuAttr(ProductSkuAttr productSkuAttr) throws SQLException;
}