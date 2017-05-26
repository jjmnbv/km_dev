package com.kmzyc.b2b.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.km.framework.common.util.MD5;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.EraInfoService;
import com.kmzyc.b2b.service.RegistService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.util.HttpUtils;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.b2b.vo.VerifyMobileInfo;
import com.kmzyc.framework.ajax.AjaxUtil;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.user.remote.service.EraInfoRemoteService;
import com.kmzyc.util.StringUtil;
import com.pltfm.app.vobject.LoginInfo;

@Controller("sdInfoAction")
@Scope("prototype")
@SuppressWarnings({"unchecked", "unused"})
public class SdInfoAction extends BaseAction {

  private static final long serialVersionUID = -5188081452511392331L;

  private static Logger logger = LoggerFactory.getLogger(SdInfoAction.class);

  @Resource(name = "eraInfoServiceImpl")
  private EraInfoService eraInfoService;

  @Resource(name = "registServiceImp")
  private RegistService registservice;

  @Resource(name = "securityCentreServiceImpl")
  private SecurityCentreService securityCentreService;

  @Resource
  private EraInfoRemoteService eraInfoRemoteService;


  private EraInfo erainfo;

  private User user;

  /**
   * 时代会员id
   */
  private String sdAccountId;

  public String setUserNameForSd() {
    Map<String, Object> mapConditon = new HashMap<String, Object>();
    try {
      HttpServletRequest request = this.getRequest();
      HttpSession session = request.getSession();
      // mapConditon.put("loginId", sdAccountId);
      mapConditon.put("loginId", session.getAttribute(Constants.SESSION_USER_ID));
      erainfo = eraInfoService.selectEranInfoById(mapConditon);
    } catch (Exception e) {
      logger.info("查询时代会员id出错，id为:" + sdAccountId + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 时代会员绑定会员
   * 
   * @return
   */
  public String sdBindMember() {
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = this.getSession();
    Map<String, Object> map = new HashMap<String, Object>();

    // 登录账号
    String strLoginAccount = null;
    // 手机号
    String strMobile = null;
    // 密码
    String strPwd = null;

    // 页面输入手机验证码
    String mobileVerifyCode = null;
    // 登录ID
    Long loginId = null;
    // 验证码
    String veCode = null;
    try {
      // 完善资料后的会员手机号为已验证，对应的康美账号用户名为m_手机号码
      String sessionmobileVerifyCode =
          String.valueOf(session.getAttribute("OrderTrailMobileVerifyCode"));
      mobileVerifyCode = request.getParameter("mobileVerifyCode");
      strMobile = request.getParameter("user.mobile");
      veCode = request.getParameter("veCode");
      strLoginAccount = "m_" + strMobile.trim();
      strPwd = request.getParameter("user.loginPassword");
      loginId = Long.parseLong(request.getParameter("user.loginId"));

      // 验证输入的绑定注册信息
      if (!checkSdBindMemberData(strMobile, strPwd, veCode)) {
        logger.error("临时会员绑定过程中出错：注册信息非法");
        return ERROR;
      }

      // 进行手机验证
      VerifyMobileInfo verifyMobileInfo = new VerifyMobileInfo();
      verifyMobileInfo.setMobileNumber(strMobile);
      verifyMobileInfo.setMobileVerifyCode(mobileVerifyCode);
      verifyMobileInfo.setLoginId(loginId);
      user = securityCentreService.getUserByLoginId(loginId);

      securityCentreService
          .verifyMobile(verifyMobileInfo, user, sessionmobileVerifyCode, strMobile);
      LoginInfo loginInfo = new LoginInfo();
      loginInfo.setLoginAccount(strLoginAccount);
      loginInfo.setLoginPassword(MD5.getMD5Str(strPwd));
      loginInfo.setMobile(strMobile);
      loginInfo.setN_LoginId(Integer.parseInt(request.getParameter("user.loginId")));

      logger.info("时代用户开始绑定普通用户，loginId为：" + loginInfo.getN_LoginId() + ",IP是"
          + HttpUtils.getIP(request));
      // EraInfoRemoteService erainfoRemoteService = (EraInfoRemoteService) RemoteTool
      // .getRemote(Constants.REMOTE_SERVICE_CUSTOMER, "eraInfoRemoteService");
      logger.info("获取用户远程接口：erainfoRemoteService 成功,开始调用用户远程接口绑定用户信息");
      Integer result = eraInfoRemoteService.updateLogin(loginInfo);
      if (result > 0) {
        User user = new User();
        user.setLoginId(loginInfo.getN_LoginId().longValue());
        User userInfo = registservice.queryTempUser(user);
        // 赠送优惠券、积分、推送给总部信息
        Map<String, String> reMap = registservice.sdBindMember(userInfo);
        if (reMap.get("success") == null) {
          logger.error("绑定过程中出错：" + reMap.get("Exception"));
          return ERROR;
        } else {
          session.setAttribute(Constants.SESSION_Zx_USER_NAME, strLoginAccount);
          userInfo.setMobile(loginInfo.getMobile());
          map.put("result", "yes");
        }
        AjaxUtil.writeJSONToResponse(map);
        return NONE;
      } else { // 绑定用户失败
        return ERROR;
      }
    } catch (Exception e) {
      logger.error("时代会员绑定会员失败" + e.getMessage(), e);
      return ERROR;
    }
  }

  /**
   * 验证输入的绑定信息
   * 
   * @param paramMobile 手机号
   * @param paramPwd 密码
   * @param paramCode 验证码
   * @return
   */
  private boolean checkSdBindMemberData(final String paramMobile, final String paramPwd,
      final String paramCode) {
    boolean checkResult = true;
    // 验证注册信息合法性
    logger.info((!StringUtil.withinRange(paramMobile, 11, 16)) + "==="
        + (!StringUtil.withinRange(paramPwd, 8, 20)));

    if (StringUtil.isEmpty(paramMobile) || StringUtil.isEmpty(paramPwd)
        || StringUtil.isEmpty(paramCode) || (!StringUtil.withinRange(paramMobile, 11, 16))
        || (!StringUtil.withinRange(paramPwd, 8, 20))) {
      checkResult = false;
    }
    return checkResult;
  }



  public EraInfo getErainfo() {
    return erainfo;
  }

  public void setErainfo(EraInfo erainfo) {
    this.erainfo = erainfo;
  }

  public String getSdAccountId() {
    return sdAccountId;
  }

  public void setSdAccountId(String sdAccountId) {
    this.sdAccountId = sdAccountId;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
