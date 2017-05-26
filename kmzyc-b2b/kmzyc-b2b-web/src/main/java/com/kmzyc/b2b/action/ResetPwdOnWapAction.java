package com.kmzyc.b2b.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.action.common.AbstractCommonAction;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.ResetPwdService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.util.StringUtil;

@Controller("resetPwdOnWapAction")
@Scope("prototype")
public class ResetPwdOnWapAction extends AbstractCommonAction {
  private static final long serialVersionUID = 1L;

  // 短信校验有效时间
  @Value("${b2b_common_msg_valid_time}")
  private int messageValidTime;

  @Resource(name = "resetPwdServiceImpl")
  private ResetPwdService resetPwdService;
 
  @Resource(name = "loginServiceImp")
  private LoginService loginService;
  
  @Resource(name = "securityCentreServiceImpl")
  private SecurityCentreService securityCentreService;

  @Resource
  private CustomerRemoteService customerRemoteService;

  /**
   * wap进入重置密码 0手机验证码错误 -1图形验证码错误
   * 
   * @return
   */
  public void wapGetReset() {
    String result = "0";
    setAttribute("goReset", "0");
    
    Date date = getAttribute("RESET-PWD-VALID-DATE");
    String sessionCode = getParameter("sessionCode");
    String sVeCode = getAttribute("wapRegistCopy");
    removeAttribute("wapRegistCopy");
    
    if (null != date
        && StringUtil.equals(getParameter("mobile"), getAttribute("RESET-MOBILE"))
        && StringUtil.equals(sVeCode, sessionCode) 
        && StringUtil.equals(getAttribute("RESET-PWD-VALID-CODE"), getParameter("mobileCode")) //手机验证码
        && (System.currentTimeMillis() - date.getTime()) <= messageValidTime * 60 * 1000l
        && "1".equals(getAttribute("B2B_FINDPWD"))) {
      setAttribute("goReset", "1");
      result = "1";
    } else if (null == sVeCode || !sVeCode.equals(sessionCode)) {
      result = "-1";// 验证码错误
    } else {
      removeAttribute("RESET-MOBILE");
    }
    
    if (!"-1".equals(result)) {
      removeAttribute("RESET-PWD-VALID-CODE");// 手机验证码
      removeAttribute("RESET-PWD-VALID-DATE");
    }
    
    try {
      printJson(result);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * wap重置密码
   * 
   * @return
   */
  public String wapResetPwd() {
    String result = "0";
    String sessionMobile = getAttribute("RESET-MOBILE");
    List<User> userLi = null;
    String password = getParameter("password");
    try {
      if (null != password && "1".equals(getAttribute("goReset"))
          && null != sessionMobile
          && null != (userLi = loginService.checkMobileExist(sessionMobile)) && userLi.size() > 0) {
        User sessionUser = userLi.get(0);

        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setLoginId(sessionUser.getLoginId());
        userBaseInfo.setPassword(password);
        userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo,"pay");
        //SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);
        if(null != userBaseInfo ) {

          if (1 == sessionUser.getStatus()) {
            User paramsUser = new User();
            paramsUser.setLoginId(userLi.get(0).getLoginId());

            AccountInfo accountInfo = securityCentreService.getAccountInfo(paramsUser);
            if (userBaseInfo.getPassword().equalsIgnoreCase(accountInfo.getPaymentpwd())) {
              result = "-1";//-1 返回 登陆密码与支付密码不一致
            } else {
              UserBaseInfo userInfo = new UserBaseInfo();
              userInfo.setLoginId(sessionUser.getLoginId());
              userInfo.setLoginPassword(password);
              // 修改密码
              result = resetPwdService.resetPassword(userInfo) ? "1" : "0";
            }
          }
        }
      }
      if (!"-1".equals(result)) {
        removeAttribute("RESET-MOBILE");
        removeAttribute("B2B_FINDPWD");
      }
      
      printJson(result);
    } catch (Exception e) {
      logger.error("出现异常：" + e.getMessage(), e);
      return ERROR;
    }
    return null;
  }
}
