package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.dao.ProductSkuAttrDAO;
import com.pltfm.app.vobject.ProductSkuAttr;
import com.pltfm.app.vobject.ProductSkuAttrExample;

@Repository("productSkuAttrDAO")
public class ProductSkuAttrDAOImpl extends BaseDAO implements ProductSkuAttrDAO {
    
    public List selectByExample(ProductSkuAttrExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU_ATTR.ibatorgenerated_selectByExample", example);
    }

	@Override
	public void insertProductSkuAttr(ProductSkuAttr productSkuAttr) throws SQLException {
		sqlMapClient.insert("PRODUCT_SKU_ATTR.ibatorgenerated_insert", productSkuAttr);
	}
}