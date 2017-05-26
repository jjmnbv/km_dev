package com.kmzyc.user.remote.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.vo.UserBaseInfo;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.app.vobject.CommercialTenantBasicInfo;
import com.pltfm.app.vobject.Customer;
import com.pltfm.app.vobject.HealthYgenericInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;
import com.pltfm.app.vobject.PersonalityInfo;

/**
 * 客户列表信息远程接口
 * 
 * @author cjm
 * @since 2013-8-7
 */
public interface CustomerRemoteService {
    /**
     * 修改用户余额(loginId)
     * 
     * @param bnesAcctTransactionQuery
     * @param systemType
     * @throws Exception
     */
    public void UpdateUserBalance(BnesAcctTransactionQuery bnesAcctTransactionQuery, int systemType)
            throws Exception;

    /**
     * 分销邀请返利业务处理
     * 
     * @param ruleCode
     * @param loginId
     * @throws Exception
     */
    public void userReward(BigDecimal membersInvitationListId, String ruleCode, Integer loginId,
            Integer nloginId) throws Exception;

    /**
     * 受邀用户用户首次购物分销业务处理
     * 
     * @param loginId
     * @return -1:异常，0：非受邀用户；1：奖励成功
     * @throws Exception
     */
    public int userFisrtShop(Integer loginId) throws Exception;

    /**
     * 分销邀请返利版本手机验证
     * 
     * @param loginInfo
     * @return 0：验证失败，1：成功;-1:异常
     * @throws Exception
     */
    public int userMobileVerification(LoginInfo loginInfo) throws Exception;

    /**
     * 分销邀请链接注册登录
     * 
     * @param loginInfo
     * @param type
     * @param invitationId 邀请者Id
     * @param invitedOrganizatton 邀请机构
     * @return
     * @throws Exception
     */
    public Map<String, String> registerRewardLoginInfo(LoginInfo loginInfo, Integer type,
            String invitationId, String invitedOrganizatton) throws Exception;

    /**
     * 按客户信息条件查询客户信息
     * 
     * @param customer 客户信息条件
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    public Customer selectByCustomer(Customer customer, Integer type) throws Exception;

    /**
     * 注册登录信息
     * 
     * @param loginInfo 登录信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    public Map<String, String> registerLoginInfo(LoginInfo loginInfo, Integer type)
            throws Exception;


    /**
     * 注册个人信息
     * 
     * @param personalBasicInfo 个人信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    public Map<String, String> registerPersonal(PersonalBasicInfo personalBasicInfo, Integer type)
            throws Exception;

    /**
     * 注册商户信息
     * 
     * @param commercialTenantBasicInfo 商户信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    public Map<String, String> registerCommercial(
            CommercialTenantBasicInfo commercialTenantBasicInfo, Integer type) throws Exception;

    /**
     * 通过客户基本信息访问类查询客户信息
     * 
     * @param userInfoDO 客户基本信息数据访问实体
     * @return
     * @throws SQLException 异常
     */
    public List queryBasicUserInfo(UserInfoDO userInfoDO) throws SQLException;

    /**
     * 通过客户基本信息访问类查询客户信息总数
     * 
     * @param userInfoDO 客户基本信息数据访问实体
     * @return
     * @throws SQLException 异常
     */
    public Integer byCountBasicUserInfo(UserInfoDO userInfoDO) throws SQLException;

    /**
     * 通过登录id 查询客户基本信息
     * 
     * @param loginId 登录主键
     * @return
     * @throws SQLException 异常信息
     */
    public UserInfoDO selectByPrimaryKey(Integer loginId) throws SQLException;

    /**
     * 通过客户等级主键查询客户集合信息
     * 
     * @param levelId 客户等级主键
     * @return 客户信息集合
     * @throws SQLException 异常
     */
    public List selectByUserLevelId(Integer levelId) throws SQLException;

    /**
     * 通过登录id 查询客户基本信息
     * 
     * @param userInfoDO 登录主键
     * @return
     * @throws SQLException 异常信息
     */
    public UserInfoDO selectByLoginId(UserInfoDO userInfoDO) throws SQLException;

    /**
     * 通过登录UserInfoDO查询客户基本信息
     * 
     * @param userInfoDO
     * @return List<UserInfoDO>
     * @throws SQLException 异常信息
     */

    public List<UserInfoDO> selectByUserInfoDO(UserInfoDO userInfoDO) throws SQLException;

    /**
     * 通过登录ID修改登录信息
     * 
     * @param
     * @return int
     * @throws SQLException 异常信息
     */
    public int updateByPrimaryKeySelective(LoginInfo loginInfo) throws SQLException;

    /**
     * 添加个人信息
     * 
     * @return 返回值
     * @throws SQLException sql异常
     */
    public Integer insert(PersonalBasicInfo p) throws SQLException;

    /**
     * 修改个人基本信息
     * 
     * @return 返回值
     * @throws SQLException sql异常
     */
    @Deprecated
    public Integer udpatePersonalBasicInfo(PersonalBasicInfo p) throws SQLException;


    /**
     * 添加个人个性信息
     * 
     * @param record 个人个性信息实体
     * @return 返回值
     * @throws SQLException sql异常
     */
    public Integer insert(PersonalityInfo record) throws SQLException;

    /**
     * 修改个人个性信息
     * 
     * @param record 个人个性信息实体
     * @return 返回值
     * @throws SQLException sql异常
     */
    @Deprecated
    public Integer updateByPrimaryKey(PersonalityInfo record) throws SQLException;

    /**
     * 增加健康信息
     * 
     * @return 返回值
     * @throws SQLException sql异常
     */
    public Integer insertHealthInfo(HealthYgenericInfo record) throws SQLException;

    /**
     * 修改健康信息
     * 
     * @return 返回值
     * @throws SQLException sql异常
     */
    @Deprecated
    public Integer updateByHealthPKeySelective(HealthYgenericInfo record) throws SQLException;

    public List<UserInfoDO> selectByUserInfoDOByObj(UserInfoDO userInfoDO) throws SQLException;

    /**
     * 查询用户积分信息
     * 
     * @return 返回值JSON字符串
     * @throws Exception
     */
    public String pushKmScoreInfoByUserCardNum(String CardNum, int pageNo) throws Exception;

    /**
     * 修改个人信息
     * 
     * @throws Exception
     */
    public void updateUserInfo(String accountLogin, PersonalBasicInfo p,
            PersonalityInfo personalityInfo, HealthYgenericInfo healthYgenericInfo)
            throws Exception;

    UserInfoDO selectUserInfoByLoginId(Long loginId);

    /**
     * 校验用户名及登录密码是否匹配
     * 
     * @param userBaseInfo
     * @return {true:匹配 false:不匹配}
     * @throws Exception
     */
    public boolean checkUserLoginPassword(UserBaseInfo userBaseInfo) throws Exception;

    /**
     * 校验用户名及支付密码是否匹配
     * 
     * @param userBaseInfo
     * @return {true:匹配 false:不匹配}
     * @throws Exception
     */
    public boolean checkUserPayPassword(UserBaseInfo userBaseInfo) throws Exception;

    /**
     * 获取用户密码二次加密信息
     * 
     * @param userInfoVo
     * @param flag(login:表示获取登录密码 pay : 表示获取支付密码)
     * @return{失败返回 null}
     */
    public UserBaseInfo queryUserPasswordTwice(UserBaseInfo userInfoVo, String flag)
            throws Exception;
}
