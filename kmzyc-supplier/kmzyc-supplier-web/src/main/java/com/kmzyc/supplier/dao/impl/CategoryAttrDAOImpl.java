package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.dao.CategoryAttrDAO;
import com.pltfm.app.vobject.CategoryAttrExample;


@Repository("categoryAttrDAO")
public class CategoryAttrDAOImpl implements CategoryAttrDAO {

    @Resource
    private SqlMapClient sqlMapClient;

    public List selectByExample(CategoryAttrExample example) throws SQLException {
        return sqlMapClient.queryForList("CATEGORY_ATTR.ibatorgenerated_selectByExample", example);
    }

}