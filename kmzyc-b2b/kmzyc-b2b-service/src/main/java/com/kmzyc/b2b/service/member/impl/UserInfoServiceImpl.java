package com.kmzyc.b2b.service.member.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.member.UserInfoDao;
import com.kmzyc.b2b.model.LoginInfo;
import com.kmzyc.b2b.model.UserInfo;
import com.kmzyc.b2b.service.member.UserInfoService;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.pltfm.app.vobject.HealthYgenericInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;
import com.pltfm.app.vobject.PersonalityInfo;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Resource(name = "userInfoDaoImpl")
    private UserInfoDao userInfoDao;
    // private static final String targetNameSpace =
    // ConfigurationUtil.getString("webservice_userSys_nameSpace");
    // private static final String endPoint =
    // ConfigurationUtil.getString("webservice_userSys_endpoint");
    @Autowired
    private CustomerRemoteService customerRemoteService;

    /**
     * 根据登录id查询个人信息
     */
    @Override
    public UserInfo queryUserInfoById(int loginId) throws Exception {

        return userInfoDao.queryUserInfoById(loginId);
    }

    /**
     * 根据登录id查询当前绑定邮箱手机是否已验证
     */
    @Override
    public LoginInfo queryStatus(int loginId) throws Exception {

        return userInfoDao.queryStatus(loginId);
    };

    /**
     * 修改基本个人信息
     */
    @Override
    public void updateUserInfo(UserInfo userInfo) throws Exception {
        // 更改健康信息
        HealthYgenericInfo healthYgenericInfo = new HealthYgenericInfo();
        healthYgenericInfo.setN_HealthYgenericId(userInfo.getHealthYgenericId());// 健康信息id
        healthYgenericInfo.setN_MaritalStatus(userInfo.getMaritalStatus());// 婚姻状态
        // 注册用户基本信息
        PersonalBasicInfo p = new PersonalBasicInfo();
        p.setN_PersonalId(userInfo.getPersonalId());// 个人基本信息id
        p.setProvince(userInfo.getProvince());// 省
        p.setArea(userInfo.getArea());// 区
        p.setCity(userInfo.getCity());// 市
        p.setDetailedAddress(userInfo.getDetailedaddress());// 详细地址
        p.setName(userInfo.getName());// 真实姓名
        p.setSex(userInfo.getSex());// 性别
        p.setD_Birthday(userInfo.getBirthday());// 生日
        p.setProfessionType(userInfo.getProfessionType());// 职业
        p.setLiveStatus(userInfo.getLiveStatus());// 居住状态
        // 更改个性化信息
        PersonalityInfo personalityInfo = new PersonalityInfo();
        personalityInfo.setN_PersonalityId(userInfo.getPersonalityId());// 个性化信息
        personalityInfo.setN_LoginId(userInfo.getLoginId());// 登陆id
        personalityInfo.setNickname(userInfo.getNickName()); // 昵称
        personalityInfo.setHeadSculpture(userInfo.getHeadSculpture());// 头像

        /*
         * CustomerRemoteService customerRemoteService = (CustomerRemoteService) RemoteTool
         * .getRemote(Constants.REMOTE_SERVICE_CUSTOMER, "customerRemoteService");
         */
        try {
            customerRemoteService.updateUserInfo(userInfo.getAccountLogin(), p, personalityInfo,
                    healthYgenericInfo);
        } catch (Exception e) {
            logger.error("更新个人信息失败:" + e.getMessage());
        }

        // 20141030 maliqun add begin 电商会员关系系统和总部会员关系系统同步 修改个人信息时调用webservice
        // 此段代码请勿删除,后续需要嵌入
        /*
         * String xmlStr = GenerateXMLStrUtil.generateXMlForUpdate(userInfo);
         * 
         * List<WsReturnResult> result = WebServiceUtil.sendWebService(endPoint, targetNameSpace,
         * Constants.WEBSERVICE_METHOD_FOR_UPDATE, xmlStr, Constants.WEBSERVICE_PARA_NAME); if
         * (result == null) {
         * System.out.println("UserServiceImpl ---> update -->调用webservice服务出错~");
         * logger.error("UserServiceImpl ---> update -->调用webservice服务出错~"); } else {
         * System.out.println("UserServiceImpl 执行webservice返回码:" + result.get(result.size() -
         * 1).getReturnCode() + "====具体信息:" + result.get(result.size() - 1).getMessageContent());
         * logger.info("UserServiceImpl 执行webservice返回码:" + result.get(result.size() -
         * 1).getReturnCode() + "====具体信息:" + result.get(result.size() - 1).getMessageContent()); }
         */
        // 20141030 maliqun add end
    }

    /**
     * 查询用户的可用积分
     * 
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public com.kmzyc.b2b.model.PersonalityInfo queryPersonalityByUserId(Long userId) throws Exception {

        return (com.kmzyc.b2b.model.PersonalityInfo) userInfoDao.findById(
                "PersonalityInfo.queryAvailableIntegralByUserId", userId);
    }

    /**
     * 更新用户的头像
     * 
     * @param userId
     * @param imgFileName
     * @throws Exception
     */
    @Override
    public void updateUserImg(Long userId, String imgFileName) throws SQLException {

        Map<String, Object> para = new HashMap<String, Object>();
        para.put("userId", userId);
        para.put("imgFileName", imgFileName);
        userInfoDao.update("PersonalityInfo.updateUserImg", para);
    }

    /**
     * 根据用户ID 查询用户的头像
     */
    @Override
    public com.kmzyc.b2b.model.PersonalityInfo selcetImagById(Long userId) throws SQLException {

        Map<String, Object> para = new HashMap<String, Object>();
        para.put("userId", userId);

        com.kmzyc.b2b.model.PersonalityInfo p =
                (com.kmzyc.b2b.model.PersonalityInfo) userInfoDao.findById(
                        "PersonalityInfo.queryImageByUserId", para);
        return p;
    }

}
