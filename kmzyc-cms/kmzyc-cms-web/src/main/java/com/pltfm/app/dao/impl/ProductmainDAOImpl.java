package com.pltfm.app.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ProductmainDAO;
import com.pltfm.app.vobject.Productmain;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component(value = "productmainDAO")
public class ProductmainDAOImpl implements ProductmainDAO {
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;


    /**
     * 跟据供应商查询产品
     */
    public List selectByExampleWithoutBLOBs(Productmain p) throws SQLException {
        List list = sqlMapClient.queryForList("PRODUCTMAIN.ibatorgenerated_selectByPrimaryKey", p);
        return list;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }


    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }
}
