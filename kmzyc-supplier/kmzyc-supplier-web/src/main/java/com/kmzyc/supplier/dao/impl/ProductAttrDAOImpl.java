package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.dao.ProductAttrDAO;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductAttrExample;
@Repository("productAttrDAO")
public class ProductAttrDAOImpl extends BaseDAO<ProductAttr> implements ProductAttrDAO {

	@Override
	public List selectByExample(ProductAttrExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_ATTR.ibatorgenerated_selectByExample", example);
	}

	@Override
	public int updateObject(List<ProductAttr> list) throws SQLException {
		return super.batchUpdateData("PRODUCT_ATTR.updateByPrimaryKey", list);
	}

	@Override
	public int deleteByExample(ProductAttrExample example) throws SQLException {
        return sqlMapClient.delete("PRODUCT_ATTR.ibatorgenerated_deleteByExample", example);
	}

	@Override
	public int insertList(List<ProductAttr> list) throws SQLException {
		return super.batchInsertData("PRODUCT_ATTR.ibatorgenerated_insert", list);
	}

}