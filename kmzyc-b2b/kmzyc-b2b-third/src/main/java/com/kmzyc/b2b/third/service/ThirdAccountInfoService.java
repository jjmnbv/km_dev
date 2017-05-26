package com.kmzyc.b2b.third.service;

import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.AlipayUserDetail;
import com.kmzyc.b2b.third.model.ThirdAccountInfo;
import com.kmzyc.b2b.third.model.ThirdBindInfo;
import com.kmzyc.b2b.third.model.wechat.UserInfo;
import com.kmzyc.b2b.weibo.model.User;
import com.kmzyc.framework.exception.ServiceException;
import com.qq.connect.javabeans.qzone.UserInfoBean;

/**
 * service层
 * 
 * @author Administrator 2014-03-18
 */
public interface ThirdAccountInfoService {

    /**
     * 存储QQ账户信息
     * 
     * @param openId 参数实体
     * @throws SQLException
     */
    public void saveQQAccountInfo(UserInfoBean userInfoBean, String openId) throws SQLException;

    /**
     * 存储QQ账户信息
     * 
     * @param jsonParams 参数实体
     * @throws SQLException
     */
    public void saveQQAccountInfo(JSONObject jsonParams, String openId) throws SQLException;

    /**
     * 存储微信账户信息
     * 
     * @param userInfo 参数实体
     * @throws SQLException
     */
    public void saveWxAccountInfo(UserInfo userInfo) throws SQLException;

    /**
     * 存储新浪账户信息
     * 
     * @param user 参数实体
     * @throws SQLException
     */
    public void saveSinaAccountInfo(User user) throws SQLException;

    /**
     * 存储新浪账户信息
     * 
     * @param jsonParams 参数实体
     * @throws SQLException
     */
    public void saveSinaAccountInfo(JSONObject jsonParams) throws SQLException;

    /**
     * 存储从支付宝账户信息
     * 
     * @param user
     * @throws SQLException
     */
    public void saveAlipayAccountInfo(AlipayUserDetail user) throws SQLException;

    /**
     * 存储淘宝账号信息
     * 
     * @param user
     * @throws SQLException
     */
    public void saveTaobaoAccountInfo(com.taobao.api.domain.User user) throws SQLException;

    /**
     * 根据openid和type获取实体信息
     * 
     * @param condition 主键组合实体
     * @return
     */
    public ThirdAccountInfo queryByOpenIdAndType(ThirdAccountInfo condition) throws SQLException;

    /**
     * 修改QQ用户的信息
     * 
     * @param openId
     * @throws SQLException
     */
    public int updateQQAcctInfo(UserInfoBean userInfoBean, String openId) throws SQLException;

    /**
     * 修改微信用户的信息
     * 
     * @param userInfo
     * @throws SQLException
     */
    public int updateWxAcctInfo(UserInfo userInfo) throws SQLException;

    /**
     * 修改新浪用户的信息
     * 
     * @param user
     * @throws SQLException
     */
    public int updateSinaAcctInfo(User user) throws SQLException;

    /**
     * 删除账户信息
     * 
     * @param condition
     * @throws SQLException
     */
    public void deleteAccountInfo(ThirdAccountInfo condition) throws SQLException;

    /**
     * 根据loginId查询openId
     * 
     * @param loginId
     * @throws SQLException
     */
    public String queryOpenIdByLoginId(String loginId) throws SQLException;

    /**
     * 第三方账号和正式会员绑定的执行方法
     * 
     * @param obj pageResult :页面常量 errorMsg : 错误原因提示
     * @throws ServiceException
     */
    public Map<String, Object> commonBindWithNormalMember(Object obj, String acctType,
            String loginId, String qqOpenId) throws ServiceException;

    /**
     * 第三方账号和正式会员绑定的执行方法
     * 
     * @param obj pageResult :页面常量 errorMsg : 错误原因提示
     * @throws ServiceException
     */
    public Map<String, Object> commonBindWithNormalMember4App(Object obj, String acctType,
            String loginId, String qqOpenId) throws ServiceException;

    /**
     * 将三种登录判断的方法整成一种方式(立即绑定正式会员,暂时绑定临时会员)
     * 
     * @param obj 第三方账号对象
     * @param acctType 账号类型
     * @param device （wap,pc,app）
     * @return pageResult :页面常量 errorMsg : 错误原因提示 n_login_id: 如果有需要以后可以加上
     *         表示的是第三方账号绑定的游客的登录id或者正式会员的登录id loginUser: 第三方账号绑定的康美会员对象
     */
    public Map<String, Object> commonBizAbountThirdLogin(Object obj, String acctType,
            String qqOpenId, int device, int platfrom) throws Exception;

    /**
     * 将三种登录判断的方法整成一种方式(立即绑定正式会员,暂时绑定临时会员)
     * 
     * @param obj 第三方账号对象
     * @param acctType 账号类型
     * @return pageResult :页面常量 errorMsg : 错误原因提示 n_login_id: 如果有需要以后可以加上
     *         表示的是第三方账号绑定的游客的登录id或者正式会员的登录id loginUser: 第三方账号绑定的康美会员对象
     */
    public Map<String, Object> commonBizAbountThirdLogin4App(Object obj, String acctType,
            String qqOpenId) throws Exception;

    /**
     * 新增外接账户信息
     * 
     * @param thirdAccountInfo 参数实体
     * @throws SQLException
     */
    public void saveOutAccountInfo(ThirdAccountInfo thirdAccountInfo) throws ServiceException;

    /**
     * 查询第三方帐号信息
     * 
     * @param loginId
     * @param type
     * @return
     * @throws ServiceException
     */
    public ThirdAccountInfo queryThirdAccountInfo(Long loginId, String type)
            throws ServiceException;

    /**
     * 20160104 压力测试新增 单独针对返利网用户的注册情况
     * 
     * @param fanliUser
     * @return
     * @throws ServiceException
     */
 /*   public Map<String, Object> fanliUserBindInfo(ThirdAccountInfo fanliUser, int deviceSource)
            throws ServiceException;*/

    /**
     * 使其单独保持事务一致 20160105
     * 
     * @param accountInfo
     * @param bindInfo
     * @return
     * @throws ServiceException
     */
//    public boolean addThirdInfo(ThirdAccountInfo accountInfo, ThirdBindInfo bindInfo)
//            throws ServiceException;



}
