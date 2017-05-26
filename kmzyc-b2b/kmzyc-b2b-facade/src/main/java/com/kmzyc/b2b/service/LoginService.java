package com.kmzyc.b2b.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.tempuri.IMemberInfo;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.framework.exception.ServiceException;

@SuppressWarnings("unchecked")
public interface LoginService {
    // 登录
    public User login(User user) throws SQLException;

    public boolean checkRegistUserMobilOrEmailExist(User user) throws SQLException;

    public List<User> checkMobileExist(String mobile);

    public List<User> checkEmailExist(String email);

    /**
     * 获取关联的SKUid
     * 
     * @param SKUID
     * @return
     * @throws SQLException
     */
    public List queryRelationSKUID(Long SKUID) throws SQLException;

    /**
     * 根据SKU查询出产品相关信息
     * 
     * @param list
     * @return
     * @throws SQLException
     */
    public List queryProductInfo(List<Long> list) throws SQLException;

    /**
     * 判断客户是否登录
     * 
     * @param request
     * @return user如果为null则客户未登录
     */
    public User verifyLogin(HttpServletRequest request);

    /**
     * 登录记录，每天首次登录添加积分
     * 
     * @param user
     * @param clientIp
     * @return
     * @throws Exception
     */
    public boolean loginRecord(User user, String clientIp) throws Exception;

    /**
     * 根据loginId查询用户信息
     * 
     * @param loginId
     * @return
     * @throws ServiceException
     */
    public User queryUserByLoginId(String loginId) throws ServiceException;

    User findLoginInfoByOrderCode(Map<String, Object> map) throws ServiceException;

    /**
     * 根据验证手机查询用户信息
     * 
     * @param mobile
     * @return
     * @throws ServiceException
     */
    public List<User> queryListByMobile(String mobile) throws ServiceException;

    /**
     * 根据用户名、邮箱、手机查询用户信息
     * 
     * @param loginAccount
     * @return
     * @throws ServiceException
     */
    public User queryUserInfoByNEM(String loginAccount) throws ServiceException;

    /**
     * 根据用户名、邮箱、手机查询用户信息 包括禁用账户
     * 
     * @param loginAccount
     * @return
     * @throws ServiceException
     */
    public User queryAllUserInfoByName(String loginAccount) throws Exception;

    /**
     * 获取时代系统接口
     * 
     * @return
     * @throws Exception
     */
    public IMemberInfo findWebservice() throws Exception;

    /**
     * 查询时代会员是否存在于商城系统中
     * 
     * @param userCode
     * @return
     * @throws ServiceException
     */
    public EraInfo findUserOBJ(String userCode) throws ServiceException;

    /**
     * 更新时代会员信息
     * 
     * @param einfo
     * @return
     * @throws ServiceException
     */
    public Boolean updateEraInfo(EraInfo einfo) throws ServiceException;

    /**
     * 插入时代会员信息
     * 
     * @param einfo
     * @return
     * @throws ServiceException
     */
    public Boolean insertEraInfo(EraInfo einfo) throws ServiceException;

    /**
     * 通过webservice接口更新时代信息
     * 
     * @param einfo
     * @return
     * @throws ServiceException
     */
    public Boolean updateRemoteEraInfo(EraInfo einfo) throws ServiceException;

    /**
     * 根据登录帐号实时获取时代会员折扣
     * 
     * @param loginNo
     * @return
     * @throws ServiceException
     */
    public BigDecimal getactualDiscount(String loginNo) throws ServiceException;

    public User selectOrganInfo(Map map) throws ServiceException;

    /**
     * 查询用户手机号码
     * 
     * @param loginId
     * @throws ServiceException
     */
    public Map<String, String> queryUserMobileForVShop(Long loginId) throws ServiceException;

    /**
     * 用户登录
     * 
     * @param params
     * @return
     * @throws ServiceException
     */
    public User userLogin(User params) throws ServiceException;

    /**
     * 时代用户登录
     * 
     * @param loginName
     * @param loginPass
     * @return
     * @throws ServiceException
     */
    public User timeLogin(String loginName, String loginPass, int registerDevice)
            throws ServiceException;

    /**
     * 根据用户卡号查询用户信息
     * 
     * @param cardNum
     * @return User
     * @throws ServiceException
     */
    //public User queryUserByCardNum(String cardNum) throws ServiceException;

    /**
     * 登录后事件
     * 
     * @param user
     * @throws ServiceException
     */
    public void doAfterLogin(User user, final String tempId, final String platform, final String ip)
            throws ServiceException;



    /**
     * 根据用户卡号和手机号码
     * 
     * @param cardNum,mobile
     * @return String
     * @throws ServiceException
     */
    /*public Map<String, String> queryWebServiceCardNumAndMobile(String cardNum, String mobile)
            throws ServiceException;*/

    /**
     * 根据条件用户信息
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public User queryUser(User user) throws ServiceException;

    /**
     * 根据登录用户名及密码获取时代用户信息
     * @param loginName
     * @param loginPass
     * @return
     */
    public EraInfo queryEraInfoForWebService(String loginName, String loginPass);

    /**
     * 时代会员信息转换
     * @param jobj
     * @return
     */
    public EraInfo getEraInfoOBJ(JSONObject jobj);
}
