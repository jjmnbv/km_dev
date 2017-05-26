package com.kmzyc.b2b.dao.member.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.dao.member.UserInfoDao;
import com.kmzyc.b2b.model.LoginInfo;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.model.UserInfo;

@Component
public class UserInfoDaoImpl extends DaoImpl implements UserInfoDao {
    @javax.annotation.Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    /**
     * 初始化个人信息
     */
    @Override
    public UserInfo queryUserInfoById(int loginId) throws SQLException {
        return (UserInfo) sqlMapClient.queryForObject("UserInfo.findById", loginId);
    }

    /**
     * 查询邮箱手机是否验证
     */
    @Override
    public LoginInfo queryStatus(int id) throws SQLException {
        return (LoginInfo) sqlMapClient.queryForObject("UserInfo.findStatus", id);

    }

    /**
     * 根据ID，查询对应的昵称和等级
     */
    @Override
    public User queryLeveAndNickName(int loginId) throws SQLException {
        return (User) sqlMapClient.queryForObject("User.findLeveNameAndNicName", loginId);
    }

    /**
     * 根据ID查询用户的头像
     */
    @Override
    public com.kmzyc.b2b.model.PersonalityInfo queryPersonalImageByUserId(Long userId)
            throws Exception {

        com.kmzyc.b2b.model.PersonalityInfo info;
        info =
                (com.kmzyc.b2b.model.PersonalityInfo) sqlMapClient.queryForObject(
                        "PersonalityInfo.quaryPersonImageByLoginId", userId);
        return info;
    }
}
