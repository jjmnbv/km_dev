package com.kmzyc.b2b.third.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.common.util.MD5;
import com.kmzyc.b2b.app.AppJsonUtils;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.third.model.ThirdBindInfo;
import com.kmzyc.b2b.third.service.LoginInfoBindingService;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.Action;
import com.pltfm.app.vobject.LoginInfo;

/**
 * 登录信息绑定Action类
 * 
 * @author cjm
 * @since 2014-3-24
 */
@Controller("loginInfoBindingAction")
@Scope("prototype")
public class LoginInfoBindingAction extends ThirdLoginAction {

  private static final long serialVersionUID = -8699974168772965801L;

  /**
   * 登录信息业务逻辑接口
   */
  @Resource(name = "loginInfoBindingService")
  private LoginInfoBindingService loginInfoBindingService;

  @Resource(name = "loginServiceImp")
  private LoginService loginService;

  // static Logger logger = Logger.getLogger(LoginInfoBindingAction.class);
  private static Logger logger = LoggerFactory.getLogger(LoginInfoBindingAction.class);

  private String info;

  /**
   * 登录信息Bean
   */
  private LoginInfo loginInfo;

  /**
   * 是否支持活动
   */
  private String isActivity;

  private String registMobile;

  /**
   * 将登录信息更新到loginInfo表和accountInfo表中
   * 
   * @return 返回商城首页
   */
  public String bindingLoginInfo() throws Exception {

    // 验证注册信息合法性
    if (StringUtil.isEmpty(loginInfo.getEmail())
        || StringUtil.isEmpty(loginInfo.getLoginAccount())
        || StringUtil.isEmpty(loginInfo.getLoginPassword())
        || (!StringUtil.withinRangeByte(
            java.net.URLDecoder.decode(loginInfo.getLoginAccount(), "UTF-8"), 6, 20))
        || (!StringUtil.withinRange(loginInfo.getEmail(), 6, 50))
        || (!StringUtil.withinRange(loginInfo.getLoginPassword(), 6, 20))) {
      logger.error("注册过程中出错：" + "注册信息非法");
      ServletActionContext.getRequest().setAttribute("errorMsg", "注册过程中出错：" + "注册信息含非法格式");
      return Action.ERROR;
    }

    ThirdBindInfo thirdBindInfo = new ThirdBindInfo();
    final HttpSession session = ServletActionContext.getRequest().getSession();

    thirdBindInfo.setThirdAccountType(session.getAttribute("thirdLoginType").toString());
    thirdBindInfo.setOpenId(session.getAttribute("openId").toString());
    thirdBindInfo.setnLoginId(String.valueOf(session.getAttribute(Constants.SESSION_USER_ID)));
    // MD5加密
    loginInfo.setLoginPassword(MD5.getMD5Str(loginInfo.getLoginPassword()));

    // 就算用户输入的是大写字母,将其转换为小写字母放入到数据库中,在登录时会有将用户名转换成小写再进行查询的操作
    loginInfo.setLoginAccount(java.net.URLDecoder.decode(loginInfo.getLoginAccount(), "UTF-8")
        .toLowerCase());

    // 更新登录信息
    loginInfoBindingService.updateLoginInfo(loginInfo, thirdBindInfo);

    // 更新了登录信息后进行session 里面的loginUser刷新
    User updateLoginUser =
        loginService.queryUserByLoginId(String.valueOf(session
            .getAttribute(Constants.SESSION_USER_ID)));

    // 定位到首页
    String redirectPath = ConfigurationUtil.getString("portalPath");

    // User user = new User();
    // user.setLoginId(loginInfo.getN_LoginId().longValue());
    // user.setEmail(loginInfo.getEmail());
    // user.setLoginAccount(loginInfo.getLoginAccount());
    // user.setLoginPassword(MD5.getMD5Str(loginInfo.getLoginPassword()));

    // 转为正式会员后,可拥有绑定管理的操作(不再是临时会员)(其实此处也可以将该变量从session中移除)
    session.setAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER, "N");

    // 刷新登录用户的信息并去到首页
    super.putLoginInfoToSession(updateLoginUser, null);

    // 判断是否是wap请求 20150331 maliqun add 新增wap绑定管理
    String isWap = ServletActionContext.getRequest().getParameter("isWap");
    if (isWap != null && "Y".equals(isWap.toUpperCase())) {
      // wap端返回至个人中心
      redirectPath =
          ConfigurationUtil.getString("portalPath_WAP") + "/goWapMyHome.action";
    }

    ServletActionContext.getResponse().sendRedirect(redirectPath);
    return null;
  }



  /**
   * 20160126 add wap端完善信息 : 将登录信息更新到loginInfo表和accountInfo表中
   * 
   * @return 返回商城首页
   */
  public String wapFullInfo() throws Exception {

    final HttpSession session = getSession();

    ThirdBindInfo thirdBindInfo = new ThirdBindInfo();
    thirdBindInfo.setThirdAccountType(session.getAttribute("thirdLoginType").toString());
    thirdBindInfo.setOpenId(session.getAttribute("openId").toString());
    thirdBindInfo.setnLoginId(String.valueOf(session.getAttribute(Constants.SESSION_USER_ID)));
    // MD5加密
    loginInfo.setLoginPassword(MD5.getMD5Str(loginInfo.getLoginPassword()));

    // 就算用户输入的是大写字母,将其转换为小写字母放入到数据库中,在登录时会有将用户名转换成小写再进行查询的操作
    // loginInfo.setLoginAccount(java.net.URLDecoder.decode(loginInfo.getLoginAccount(), "UTF-8")
    // .toLowerCase());

    loginInfo.setLoginAccount("m_" + registMobile);
    loginInfo.setMobile(registMobile);
    loginInfo.setD_ModifyDate(new Date());

    // 更新登录信息
    loginInfoBindingService.updateLoginInfo(loginInfo, thirdBindInfo);

    // 更新了登录信息后进行session 里面的loginUser刷新
    // User updateLoginUser =
    // loginService.queryUserByLoginId(String.valueOf(session
    // .getAttribute(Constants.SESSION_USER_ID)));


    User updateLoginUser = new User();
    updateLoginUser.setLoginAccount(loginInfo.getLoginAccount());
    updateLoginUser.setLoginId((Long) session.getAttribute(Constants.SESSION_USER_ID));
    updateLoginUser.setLoginPassword(loginInfo.getLoginPassword());
    updateLoginUser.setStatus(1);

    // 定位到首页
    // String redirectPath = ConfigurationUtil.getString("portalPath");

    // User user = new User();
    // user.setLoginId(loginInfo.getN_LoginId().longValue());
    // user.setLoginAccount(loginInfo.getLoginAccount());
    // user.setLoginPassword(MD5.getMD5Str(loginInfo.getLoginPassword()));
    // user.setStatus(1);

    // 转为正式会员后,可拥有绑定管理的操作(不再是临时会员)(其实此处也可以将该变量从session中移除)
    session.setAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER, "N");

    // 刷新登录用户的信息并去到首页
    super.putLoginInfoToSession(updateLoginUser, null);

    // 判断是否是wap请求 20150331 maliqun add 新增wap绑定管理
    String redirectPath =
        ConfigurationUtil.getString("portalPath_WAP") + "/goWapMyHome.action";

    // 如果是活动支持,则去到优惠券页面
    if (StringUtils.isNotBlank(isActivity) && "y".equals(isActivity.toLowerCase())) {
      redirectPath =
          ConfigurationUtil.getString("portalPath_WAP")
              + "/member/goWapCoupon.action";
    }
    getResponse().sendRedirect(redirectPath);
    return null;
  }



  /**
   * 绑定管理 -->查询某康美正式会员所绑定的第三方账号信息
   * 
   * @return
   */
  public String queryBindInfoList() {
    HttpServletRequest request = ServletActionContext.getRequest();
    String loginId = String.valueOf(request.getSession().getAttribute(Constants.SESSION_USER_ID));

    // 存储绑定提示
    request.setAttribute("bindTip", request.getParameter("bindTip"));

    try {
      List<ThirdBindInfo> resultList = loginInfoBindingService.queryBindInfoList(loginId);

      request.setAttribute("isBindQQ", "N");
      request.setAttribute("isBindSina", "N");
      request.setAttribute("isBindWx", "N");
      request.setAttribute("isBindAlipay", "N");
      request.setAttribute("isBindTb", "N");

      for (ThirdBindInfo thirdBindInfo : resultList) {
        if ("01".equals(thirdBindInfo.getThirdAccountType())) {
          request.setAttribute("isBindQQ", "Y");
        } else if ("02".equals(thirdBindInfo.getThirdAccountType())) {
          request.setAttribute("isBindWx", "Y");
        } else if ("03".equals(thirdBindInfo.getThirdAccountType())) {
          request.setAttribute("isBindSina", "Y");
        } else if ("04".equals(thirdBindInfo.getThirdAccountType())) {
          request.setAttribute("isBindAlipay", "Y");
        } else if ("05".equals(thirdBindInfo.getThirdAccountType())) {
          request.setAttribute("isBindTb", "Y");
        }
      }

      request.setAttribute("resultList", resultList);

      // 判断是否是wap请求 20150331 maliqun add 新增wap绑定管理
      String isWap = request.getParameter("isWap");
      if (isWap != null && "Y".equals(isWap.toUpperCase())) {
        return "wapBindList";
      }

    } catch (ServiceException e) {
      logger.error("LoginInfoBindingAction ---> action获取会员绑定第三方账号信息异常" + e);
      return Action.ERROR;
    }
    return "success";
  }

  /**
   * 去到完善信息页面
   * 
   * @return
   */
  public String toFillInfo() {
    HttpServletRequest request = ServletActionContext.getRequest();
    // 判断是否是wap请求 20150331 maliqun add 新增wap绑定管理
    String isWap = request.getParameter("isWap");

    isActivity = request.getParameter("isActivity");
    if (isWap != null && "Y".equals(isWap.toUpperCase())) {
      return "toWapFillInfo";
    }

    return "toFillInfo";
  }

  /**
   * 以后再说的绑定过程
   * 
   * @return
   * @throws IOException
   */
  public String bindLater() throws IOException {
    // 直接定位到首页(登录信息的session已存)
    String redirectPath = ConfigurationUtil.getString("portalPath");

    // 判断是否是wap请求 20150331 maliqun add 新增wap绑定管理
    String isWap = ServletActionContext.getRequest().getParameter("isWap");
    if (isWap != null && "Y".equals(isWap.toUpperCase())) {
      redirectPath = ConfigurationUtil.getString("portalPath_WAP");
    }

    ServletActionContext.getResponse().sendRedirect(redirectPath);
    return null;
  }

  /**
   * 解绑操作
   * 
   * @return
   * @throws IOException
   */
  public void getOutOfBind() throws IOException {
    JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
    // app请求参数获取
    String loginId = jsonParams.getString("loginId");
    String acctType = jsonParams.getString("acctType");
    String openId = jsonParams.getString("openid");
    ReturnResult<HashMap<String, Object>> returnResult;
    ThirdBindInfo condition = new ThirdBindInfo();
    condition.setnLoginId(loginId);
    condition.setOpenId(openId);
    condition.setThirdAccountType(acctType);
    try {
      loginInfoBindingService.unBinding(condition);
      logger.info("id为" + loginId + ",的用户与acctType=" + acctType + ",openId=" + openId
          + "的第三方账号解绑成功~");
      returnResult = new ReturnResult("200", "第三方账号解绑成功!", null);
    } catch (ServiceException e) {
      logger.error("LoginInfoBindingAction --->解绑删除信息异常,openId=" + openId + ",acctType=" + acctType
          + ",异常详情如下=" + e);
      returnResult = new ReturnResult("200", "的第三方账号解绑失败!", e.getMessage());
    }
    this.printJsonString(returnResult);
  }



  /**
   * 解绑操作 20151231 add pc端和wap端解绑请求统一入口,由于之前app接口处有覆盖代码并不兼容,另起一个
   * 
   * @return
   * @throws IOException
   */
  public String getOutOfBindForPcAndWap() throws IOException {
    HttpServletRequest request = ServletActionContext.getRequest();

    // 获取所需要的一些参数
    String loginId = String.valueOf(request.getSession().getAttribute(Constants.SESSION_USER_ID));
    String acctType = request.getParameter("thirdAcctType");
    String openId = request.getParameter("openId");

    ThirdBindInfo condition = new ThirdBindInfo();
    condition.setnLoginId(loginId);
    condition.setOpenId(openId);
    condition.setThirdAccountType(acctType);
    try {
      loginInfoBindingService.unBinding(condition);
      logger.info("id为" + loginId + ",的用户与acctType=" + acctType + ",openId=" + openId
          + "的第三方账号解绑成功~");
    } catch (ServiceException e) {
      logger.error("LoginInfoBindingAction --->解绑删除信息异常,openId=" + openId + ",acctType=" + acctType
          + ",异常详情如下=" + e);
      return "error";
    }

    // 用来提示用户
    this.bindTip = "success_out";

    // 判断是否是wap请求 20150331 maliqun add 新增wap绑定管理
    String isWap = request.getParameter("isWap");
    if (isWap != null && "Y".equals(isWap.toUpperCase())) {
      return "toWapBindManage";
    }

    // 解绑后照样回到绑定管理的页面
    return "toBindManage";
  }



  public LoginInfoBindingService getLoginInfoBindingService() {
    return loginInfoBindingService;
  }

  public void setLoginInfoBindingService(LoginInfoBindingService loginInfoBindingService) {
    this.loginInfoBindingService = loginInfoBindingService;
  }

  public LoginInfo getLoginInfo() {
    return loginInfo;
  }

  public void setLoginInfo(LoginInfo loginInfo) {
    this.loginInfo = loginInfo;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }



  public String getIsActivity() {
    return isActivity;
  }



  public void setIsActivity(String isActivity) {
    this.isActivity = isActivity;
  }


  public String getRegistMobile() {
    return registMobile;
  }



  public void setRegistMobile(String registMobile) {
    this.registMobile = registMobile;
  }
}
