package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.User;
import com.kmzyc.supplier.dao.LoginDao;
import com.kmzyc.supplier.vo.CountInfo;

@Repository("loginDao")
public class LoginDaoImpl implements LoginDao {

    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public Long getUserType(String userName, String pwd) throws SQLException {
        User user = new User();
        user.setLoginAccount(userName);
        return (Long) sqlMapClient.queryForObject("User.queryCustomerType", user);
    }
    
    public User queryByUser(User user) throws SQLException {
        return (User) sqlMapClient.queryForObject("User.findUser", user);
    }

    @Override
    public CountInfo queryTotalCountForIndex(String supplierId) throws SQLException {
        return (CountInfo) sqlMapClient.queryForObject("User.queryTotalCountForIndex", supplierId);
    }
}