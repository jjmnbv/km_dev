package com.kmzyc.b2b.action.member;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.LoginInfo;
import com.kmzyc.b2b.model.UserInfo;
import com.kmzyc.b2b.service.EraInfoService;
import com.kmzyc.b2b.service.member.UserInfoService;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.user.remote.service.EraInfoRemoteService;

/**
 * 个人信息访问入口
 * 
 * @author lijianjun
 * 
 */
@SuppressWarnings("unchecked")
@Controller("userInfoAction")
@Scope("prototype")
public class UserInfoAction extends BaseAction {
  private static final long serialVersionUID = 5543775073789693006L;
  // private static Logger logger = Logger.getLogger(UserInfoAction.class);
  private static Logger logger = LoggerFactory.getLogger(UserInfoAction.class);

  @Resource(name = "userInfoServiceImpl")
  private UserInfoService userInfoService;
  @Resource(name = "eraInfoServiceImpl")
  private EraInfoService eraInfoService;
  @Resource
  private EraInfoRemoteService eraInfoRemoteService;

  private UserInfo userInfo;
  private LoginInfo loginInfo;
  private EraInfo era;
  private ReturnResult returnResult;

  private int eraInfoId;
  private String eraNo;

  /**
   * 初始化个人信息
   * 
   * @return
   */
  public String goUserInfo() {
    // 获取缓存用户id
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    logger.info("初始化会员个人信息，参数：userID：" + userId);
    try {
      era = eraInfoService.queryEraInfoByLoginId(userId);
    } catch (Exception e) {
      logger.error("判断是否为直销用户失败：" + e.getMessage(), e);
      return ERROR;
    }
    if (era == null) {
      try {
        // 根据登录id获取个人信息和邮件手机验证状态
        userInfo = userInfoService.queryUserInfoById(userId.intValue());
        loginInfo = userInfoService.queryStatus(userId.intValue());
        // 判断email和phone是否验证 设置emailStatus 0 未验证：1 验证
        if (StringUtils.isNotBlank(loginInfo.getEmail())) {
          loginInfo.setEmailStatus(1);
        }
        if (StringUtils.isNotBlank(loginInfo.getMobile())) {
          loginInfo.setMobileStatus(1);
        }
      } catch (SQLException e) {
        logger.error("初始化个人信息失败" + e.getMessage(), e);
        return ERROR;
      } catch (Exception e) {
        logger.error("初始化个人信息失败" + e.getMessage(), e);
        return ERROR;
      }
      return SUCCESS;
    } else {
      return "zxsuccess";
    }
  }

  public String updateZxUserInfo() {
    // 获取缓存用户id
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    logger.info("初始化会员个人信息，参数：userID：" + userId);
    try {
      // EraInfoRemoteService eraInfoRemoteService =
      // (EraInfoRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
      // "eraInfoRemoteService");
            com.kmzyc.b2b.vo.EraInfo ss = new com.kmzyc.b2b.vo.EraInfo();
      ss.setEraInfoId(new BigDecimal(eraInfoId));
      ss.setEraNo(eraNo);
      Integer in = eraInfoRemoteService.updateEraInfo(ss);
      if (in == 1) {
        logger.info("初始化会员个人信息，参数：userID：" + userId + ",eraiInfoId:");
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "更新成功！", null);
      } else {
        logger.error("初始化会员个人信息，参数：userID：" + userId + ",eraiInfoId:");
        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "更新异常！", null);
      }
    } catch (Exception e) {
      logger.error("人个信息更新异常：" + e);
    }
    return SUCCESS;
  }

  /**
   * 修改个人信息
   * 
   * @return
   */
  public String updateUserInfo() {
    // 获取缓存用户id
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    logger.info("编辑会员个人信息，参数：userID：" + userId);
    try {
      if (userId == null) {
        returnResult = new ReturnResult(InterfaceResultCode.NOT_LOGIN, "未登录", null);
      } else {
        if (userId.compareTo(userInfo.getLoginId().longValue()) != 0) {
          logger.error("非当前用户修改个人信息，currentUserID：" + userId);
          returnResult = new ReturnResult(InterfaceResultCode.FAILED, "非当前用户", null);
        } else {

          // 20141030 maliqun add 将userInfo中的某些值传递为了给webservice调用方便
          // begin 此段代码请勿删除,后续需要嵌入
          UserInfo detailUser = userInfoService.queryUserInfoById(userId.intValue());
          userInfo.setMobile(detailUser.getMobile());
          userInfo.setAccountLogin(detailUser.getAccountLogin());
          // 20141030 maliqun add end

          userInfoService.updateUserInfo(userInfo);
          returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "修改成功", null);
        }
      }
    } catch (SQLException e) {
      logger.error("修改个人信息失败" + e.getMessage(), e);
      return ERROR;
    } catch (Exception e) {
      logger.error("修改个人信息失败" + e.getMessage(), e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "修改失败", null);
    }
    return SUCCESS;
  }

  public LoginInfo getLoginInfo() {
    return loginInfo;
  }

  public void setLoginInfo(LoginInfo loginInfo) {
    this.loginInfo = loginInfo;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }

  public ReturnResult getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult returnResult) {
    this.returnResult = returnResult;
  }

  public EraInfo getEra() {
    return era;
  }

  public void setEra(EraInfo era) {
    this.era = era;
  }

  public int getEraInfoId() {
    return eraInfoId;
  }

  public void setEraInfoId(int eraInfoId) {
    this.eraInfoId = eraInfoId;
  }

  public String getEraNo() {
    return eraNo;
  }

  public void setEraNo(String eraNo) {
    this.eraNo = eraNo;
  }

}
