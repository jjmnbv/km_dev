package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.dao.LoginDao;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.vo.EraInfo;
import com.pltfm.app.vobject.EmailInfo;

@SuppressWarnings("unchecked")
@Repository
public class LoginDaoImp extends DaoImpl implements LoginDao {
    @javax.annotation.Resource(name = "sqlMapClient")
    private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

    @Override
    public User queryByUser(User user) throws SQLException {
        return (User) sqlMapClient.queryForObject("User.findUser", user);
    }

    /**
     * 验证邮箱是否注册
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public List queryListByEmail(User user) throws SQLException {
        return sqlMapClient.queryForList("User.verifyEmail", user);

    }

    /**
     * 验证用户邮箱和手机号是否存在
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public List<User> queryRegistUserByMobilOrEmail(User user) throws SQLException {
        return sqlMapClient.queryForList("User.queryRegistUserByMobilOrEmail", user);
    }

    @Override
    public List queryListByMobile(User user) throws SQLException {
        return this.sqlMapClient.queryForList("User.verifyMobile", user);
    }

    @Override
    public List queryRelationDetail(Long productSkuId) throws SQLException {
        return this.sqlMapClient.queryForList("RelevanceSKU.findById", productSkuId);
    }

    @Override
    public List queryProductInfo(List<Long> list) throws SQLException {

        return this.sqlMapClient.queryForList("RelevanceSKU.findInfoBySkuId", list);
    }

    /**
     * 根据用户ID获取邮箱验证随机码
     * 
     * @param loginId
     * @return
     * @throws Exception
     */
    @Override
    public EmailInfo queryValidMailByLoginId(long loginId) throws Exception {
        List list = this.sqlMapClient.queryForList("User.queryTattedCodeByLoginId", loginId);
        if (null != list && !list.isEmpty())
            return (EmailInfo) list.get(0);
        return null;
    }

    /**
     * 查询临时用户
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public User queryTempUser(User user) throws SQLException {
        return (User) sqlMapClient.queryForObject("User.queryTempUserByMailAndMobile", user);
    }

    /**
     * 更新临时用户
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public boolean updateTempUserInfo(User user) throws SQLException {
        sqlMapClient.update("User.updateTempUserInfo", user);
        return true;
    }

    /**
     * 根据登录ID获取用户信息
     * 
     * @param loginId
     * @return
     * @throws Exception
     */
    @Override
    public User getUserByLoginId(long loginId) throws Exception {

        return (User) sqlMapClient.queryForObject("User.getUserByLoginId", loginId);
    }

    /**
     * 用户类型
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public long queryCustomerType(User user) throws SQLException {
        List list = sqlMapClient.queryForList("User.queryCustomerType", user);
        Long result = -1L;
        if (null != list && !list.isEmpty()) {
            result = (Long) list.get(0);
        }
        return result;
    }

    /**
     * 校验用户名
     * 
     * @param uName
     * @return
     * @throws SQLException
     */
    @Override
    public int validUserName(String uName) throws SQLException {
        Long rs = (Long) sqlMapClient.queryForObject("User.validUserName", uName);
        if (null == rs)
            rs = 0L;
        return rs.intValue();
    }

    @Override
    public List<User> queryMobilExist(String mobil) throws SQLException {
        return this.sqlMapClient.queryForList("User.queryMobilExist", mobil);
    }

    @Override
    public List<User> queryEmailExist(String email) throws SQLException {
        return this.sqlMapClient.queryForList("User.queryEmailExist", email);
    }

    @Override
    public User queryUserByLoginId(String loginId) throws SQLException {
        return (User) this.sqlMapClient.queryForObject("User.queryUserByLoginId",
                Long.parseLong(loginId));
    }

    @Override
    public User findLoginInfoByOrderCode(Map<String, Object> map) throws SQLException {
        return (User) this.sqlMapClient.queryForObject("User.findLoginInfoByOrderCode", map);
    }

    /**
     * 验证用户是否存在：注册、修改用户名、手机号、邮箱
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public Map checkUserExists(User user) throws SQLException {
        List list = sqlMapClient.queryForList("User.checkUserExists", user);
        Map map = null;
        if (null != list && !list.isEmpty())
            map = (HashMap) list.get(0);
        return map;
    }

    // 判断手机号码是否被占用
    @Override
    public List<User> getListByMobile(User selectUser) throws SQLException {
        return this.sqlMapClient.queryForList("User.getVerifyMobile", selectUser);
    }

    @Override
    public List<User> listByMobile(String mobile) throws SQLException {
        return this.sqlMapClient.queryForList("User.VerifyMobile", mobile);
    }


    /**
     * 根据用户名、邮箱、手机查询用户信息
     * 
     * @param loginAccount
     * @return
     * @throws SQLException
     */
    @Override
    public User queryUserInfoByNEM(String loginAccount) throws SQLException {
        return (User) sqlMapClient.queryForObject("User.SQL_QUERY_USER_INFO_BY_NEM", loginAccount);
    }


    @Override
    public User queryAllUserInfoByName(String loginAccount) throws SQLException {
        return (User) sqlMapClient.queryForObject("User.queryAllUserInfoByNameForApp", loginAccount);
    }

    @Override
    public EraInfo findTimesInfo(String userCode) throws SQLException {

        return (EraInfo) sqlMapClient.queryForObject("User.findTimesUser", userCode);
    }

    @Override
    public User selectOrganInfo(Map map) throws SQLException {
        return (User) sqlMapClient.queryForObject("User.selectOrganInfo", map);

    }

    /**
     * 查询用户手机号码
     * 
     * @param loginId
     * @throws SQLException
     */
    @Override
    public Map<String, String> queryUserMobileForVShop(Long loginId) throws SQLException {
        return (Map<String, String>) sqlMapClient.queryForObject(
                "User.SQL_QUERY_USER_MOBILE_FOR_VSHOP", loginId);
    }

    /**
     * 验证用户是否存在：注册、修改用户名、手机号、邮箱
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public Map checkUserExistsForVshop(User user) throws SQLException {
        List list = sqlMapClient.queryForList("User.checkUserExistsForVshop", user);
        Map map = null;
        if (null != list && !list.isEmpty())
            map = (HashMap) list.get(0);
        return map;
    }

    @Override
    public User selectPromOrganInfo(List<Long> promIds) throws SQLException {
        return (User) sqlMapClient.queryForObject("User.selectPromOrganInfo", promIds);
    }

    /**
     * 根据登录名查询用户信息
     * 
     * @param loginAccount
     * @return
     * @throws SQLException
     */
    @Override
    public User queryUserByLoginAccount(String loginAccount) throws SQLException {
        return (User) sqlMapClient.queryForObject("User.SQL_QUERY_USER_BY_LOGIN_ACCOUNT",
                loginAccount);
    }

    /**
     * 用户登录
     * 
     * @param params
     * @return
     * @throws SQLException
     */
    @Override
    public User userLogin(User params) throws SQLException {
        return (User) sqlMapClient.queryForObject("User.SQL_QUERY_USER_LOGIN", params);
    }

    @Override
    public User queryUser(User user) throws SQLException {
        List<User> list = this.sqlMapClient.queryForList("User.queryUser", user);
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

}
