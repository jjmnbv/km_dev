package com.pltfm.app.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.pltfm.app.dao.SaltInfoDAO;
import com.pltfm.app.vobject.SaltInfo;

/**
 *
 */
@Repository(value = "saltInfoDAO")
public class SaltInfoDAOImpl implements SaltInfoDAO {

    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    @Override
    public SaltInfo querySaltInfo(UserBaseInfo userInfoVo) throws SQLException {

        return (SaltInfo) this.sqlMapClient.queryForObject("SaltInfo.querySaltInfo",userInfoVo);
    }

    @Override
    public void insertSaltInfo(SaltInfo saltInfo) throws SQLException{

        this.sqlMapClient.insert("SaltInfo.insertSaltInfo",saltInfo);
    }
}
