package com.pltfm.app.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ViewShopMainDAO;
import com.pltfm.app.vobject.ViewShopMain;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component(value = "viewShopMainDAO")
public class ViewShopMainDAOImpl implements ViewShopMainDAO {

    /**
     * 数据库操作对象
     */
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;


    public List queryShopMianList(ViewShopMain viewShopMain) throws SQLException {

        return sqlMapClient.queryForList(
                "VIEW_SHOPMAIN.ibatorgenerated_queryShopMain", viewShopMain);

    }


    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }


}
