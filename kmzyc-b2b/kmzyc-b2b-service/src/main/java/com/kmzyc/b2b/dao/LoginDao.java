package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.vo.EraInfo;
import com.pltfm.app.vobject.EmailInfo;

@SuppressWarnings("unchecked")
public interface LoginDao extends Dao {
    public User queryByUser(User user) throws SQLException;

    /**
     * 验证邮箱是否注册
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public List queryListByEmail(User user) throws SQLException;


    /**
     * 验证手机号是否注册
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public List queryListByMobile(User user) throws SQLException;

    /**
     * 验证用户邮箱和手机号是否存在
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public List<User> queryRegistUserByMobilOrEmail(User user) throws SQLException;

    /**
     * 根据skuID查询出关联的skuID集合
     * 
     * @param productSkuId
     * @return
     * @throws SQLException
     */
    public List queryRelationDetail(Long productSkuId) throws SQLException;

    /**
     * 根据SKU查询出产品相关信息
     * 
     * @param list
     * @return
     * @throws SQLException
     */
    public List queryProductInfo(List<Long> list) throws SQLException;

    /**
     * 根据用户ID获取邮箱验证信息
     * 
     * @param loginId
     * @return
     * @throws Exception
     */
    public EmailInfo queryValidMailByLoginId(long loginId) throws Exception;

    /**
     * 查询临时用户
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public User queryTempUser(User user) throws SQLException;

    /**
     * 更新临时用户
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public boolean updateTempUserInfo(User user) throws SQLException;

    /**
     * 根据登录ID获取用户信息
     * 
     * @param loginId
     * @return
     * @throws Exception
     */
    public User getUserByLoginId(long loginId) throws Exception;

    /**
     * 用户类型
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public long queryCustomerType(User user) throws SQLException;

    /**
     * 校验用户名
     * 
     * @param uName
     * @return
     * @throws SQLException
     */
    public int validUserName(String uName) throws SQLException;

    public List<User> queryMobilExist(String mobil) throws SQLException;

    public List<User> queryEmailExist(String email) throws SQLException;

    public User queryUserByLoginId(String loginId) throws SQLException;

    public User findLoginInfoByOrderCode(Map<String, Object> map) throws SQLException;

    /**
     * 验证用户是否存在：注册、修改用户名、手机号、邮箱
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public Map checkUserExists(User user) throws SQLException;

    // 判断手机号码是否已被占用
    public List<User> getListByMobile(User selectUser) throws SQLException;

    public List<User> listByMobile(String mobile) throws SQLException;


    /**
     * 根据用户名、邮箱、手机查询用户信息
     * 
     * @param loginAccount
     * @return
     * @throws SQLException
     */
    public User queryUserInfoByNEM(String loginAccount) throws SQLException;

    /**
     * 根据用户名、邮箱、手机查询用户信息 包括禁用账户
     * 
     * @param loginAccount
     * @return
     * @throws SQLException
     */
    public User queryAllUserInfoByName(String loginAccount) throws SQLException;

    /**
     * 查询时代会员是否存在
     * 
     * @param userCode
     * @return
     * @throws SQLException
     */
    public EraInfo findTimesInfo(String userCode) throws SQLException;

    public User selectOrganInfo(Map map) throws SQLException;

    /**
     * 查询用户手机号码
     * 
     * @param loginId
     * @throws SQLException
     */
    public Map<String, String> queryUserMobileForVShop(Long loginId) throws SQLException;

    /**
     * 验证用户是否存在：注册、修改用户名、手机号、邮箱
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public Map checkUserExistsForVshop(User user) throws SQLException;

    public User selectPromOrganInfo(List<Long> promIds) throws SQLException;

    /**
     * 根据登录名查询用户信息
     * 
     * @param loginAccount
     * @return
     * @throws SQLException
     */
    public User queryUserByLoginAccount(String loginAccount) throws SQLException;

    /**
     * 用户登录
     * 
     * @param params
     * @return
     * @throws SQLException
     */
    public User userLogin(User params) throws SQLException;

    /**
     * 根据条件用户信息
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public User queryUser(User user) throws SQLException;
}
