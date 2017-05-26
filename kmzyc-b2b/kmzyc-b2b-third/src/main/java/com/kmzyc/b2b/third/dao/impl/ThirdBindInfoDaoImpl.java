package com.kmzyc.b2b.third.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.third.dao.ThirdBindInfoDao;
import com.kmzyc.b2b.third.model.ThirdAccountInfo;
import com.kmzyc.b2b.third.model.ThirdBindInfo;

/**
 * 实现类
 * 
 * @author Administrator 2014-03-18
 */
@Repository("thirdBindInfoDao")
public class ThirdBindInfoDaoImpl extends DaoImpl implements ThirdBindInfoDao {

    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    @Override
    public void insert(ThirdBindInfo entity) throws SQLException {
        sqlMapClient.insert("ThirdBindInfo.saveBindInfo", entity);
    }

    @Override
    public int updateBindInfo(ThirdBindInfo entity) throws SQLException {
        return sqlMapClient.update("ThirdBindInfo.updateBindStatus", entity);
    }

    @Override
    public void deleteBindInfo(ThirdBindInfo condition) throws SQLException {
        sqlMapClient.delete("ThirdBindInfo.deleteBindInfo", condition);
    }

    @Override
    public List<ThirdBindInfo> queryBindInfoByLoginId(String loginId) throws SQLException {
        return (List<ThirdBindInfo>) sqlMapClient.queryForList("ThirdBindInfo.queryBindInfo",
                loginId);
    }

    @Override
    public ThirdBindInfo queryIsBindTourist(ThirdAccountInfo condition) throws SQLException {
        return (ThirdBindInfo) sqlMapClient.queryForObject("ThirdBindInfo.queryIsBindTourist",
                condition);
    }

    @Override
    public String queryLoginIdByThridAcctInfo(ThirdAccountInfo condition) throws SQLException {
        return (String) sqlMapClient.queryForObject(
                "ThirdBindInfo.queryLoginIdByOpenIdAndAcctType", condition);
    }

    @Override
    public void deleteBindInfoByOpenId(ThirdBindInfo condition) throws SQLException {
        sqlMapClient.delete("ThirdBindInfo.deleteBindInfoByOpenId", condition);
    }

}
