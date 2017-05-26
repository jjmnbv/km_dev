package com.kmzyc.b2b.third.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alipay.api.domain.AlipayUserDetail;
import com.kmzyc.b2b.third.service.ThirdAccountInfoService;
import com.kmzyc.b2b.third.util.AlipayNotify;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 支付宝登录的请求处理类
 * 
 * @author Administrator
 * 
 */
@Controller("alipayAfterLoginRedirectAction")
@Scope("prototype")
public class AlipayAfterLoginRedirectAction extends ThirdLoginAction {
  /**
	 * 
	 */
  private static final long serialVersionUID = -510905357068526453L;
  // private static Logger log = LoggerFactory.getLogger(AlipayAfterLoginRedirectAction.class);
  private static Logger log = LoggerFactory.getLogger(AlipayAfterLoginRedirectAction.class);

  @Resource(name = "thirdAccountInfoService")
  private ThirdAccountInfoService thirdAcctInfoService;

  public String alipayAfterLoginRedirect() throws IOException, Exception {
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();
    response.setCharacterEncoding("utf-8");

    // 获取支付宝GET过来反馈信息
    Map<String, String> params = new HashMap<String, String>();
    Map<String, String[]> requestParams = request.getParameterMap();
    for (Map.Entry<String, String[]> iter : requestParams.entrySet()) {
      String name = iter.getKey();
      String[] values =iter.getValue();
      String valueStr = StringUtils.join(values,",");

      params.put(name, valueStr);
    }
    // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

    log.info("ora-params-------" + params);
    // 支付宝用户号
    String user_id = request.getParameter("user_id");
    // 授权令牌

    // 本地环境
    // String real_name = new
    // String(params.get("real_name").getBytes("ISO-8859-1"),"utf-8");
    // params.put("real_name", real_name);
    // 测试环境
    // String real_name = new
    // String(params.get("real_name").getBytes("ISO-8859-1"),"utf-8");

    // 生产环境
    String real_name = request.getParameter("real_name");
    params.put("real_name", real_name);

    // 移除params中传递过来的我们自己的参数,不然会导致验证失败
    params.remove(ThirdConstant.IS_COMEFROMBINDMANAGE);
    params.remove("clientIp");
    log.info("remove the unRelated  parameter...." + params);
    log.info("---------utf-8---real_name-------------" + real_name);

    // 计算得出通知验证结果
    boolean verify_result = AlipayNotify.verify(params);
    if (verify_result) {
      AlipayUserDetail userDetail = new AlipayUserDetail();
      userDetail.setAlipayUserId(user_id);
      userDetail.setRealName(params.get("real_name"));
      request.getSession().setAttribute("thirdLoginType", ThirdConstant.THIRD_ACCOUNT_TYPE_ALIPAY);
      request.getSession().setAttribute("nikeName", userDetail.getRealName());
      request.getSession().setAttribute("openId", userDetail.getAlipayUserId());
      request.getSession().setAttribute("userImg", null);

      // 是否是从绑定管理中心过来主动绑定
      String isComeFromBindManage =
          request.getParameter(ThirdConstant.IS_COMEFROMBINDMANAGE);
      log.info("comeFromBindManage=======================" + isComeFromBindManage);
      // 执行和康美会员绑定关系的返回结果map
      Map<String, Object> resultMap = null;

      // 如果是从正式会员那块过来的主动绑定操作
      if ("Y".equals(isComeFromBindManage)) {
        String loginId =
            String.valueOf(request.getSession().getAttribute(Constants.SESSION_USER_ID));
        System.out
            .println("1.是通过康美中药城方式登录进来.... 2.通过第三方登录账号绑定的正式用户登录进来........ 3.第三方账号登录进来的临时会员成为正式会员后所作的操作..."
                + loginId);

        // 和正式会员绑定的公共方法,具体注释请见service层
        resultMap =
            thirdAcctInfoService.commonBindWithNormalMember(userDetail, "alipay", loginId, null);

        if (null != (String) resultMap.get("errorMsg")
            && !"".equals(resultMap.get("errorMsg"))) {
          request.setAttribute("errorMsg", resultMap.get("errorMsg"));
          log.info("SinaAfterLoginRedirectAction ---> 和正式会员绑定发生异常;" + resultMap.get("errorMsg"));
          return "error";
        } else {
          // 该第三方账号已和其它康美会员绑定
          if (resultMap.containsKey("alreadyBind")) {
            log.info("errorMsg:该第三方账号已和其它康美账号绑定!");
            this.bindTip = "Y_alipay"; // 提示用户该第三方账号已经绑定其它会员
            return "toBindManage";
          }
          // 绑定成功去到指定的页面 去到绑定管理页面
          return "toBindManage";
        }
      }
      int cookie = cookieUserSource();
      // 第三方普通登录业务逻辑,具体注释请看service层
      resultMap =
          thirdAcctInfoService.commonBizAbountThirdLogin(userDetail, "alipay", null,
              Constants.REGISTER_DEVICE_PC, cookie);

      if (null != (String) resultMap.get("errorMsg")
          && !"".equals(resultMap.get("errorMsg"))) {
        request.setAttribute("errorMsg", resultMap.get("errorMsg"));
        log.info("errorMsg:" + resultMap.get("errorMsg"));
        return "error";
      }

      // 将完善信息所需要相关信息存入session,并非登录信息
            com.kmzyc.b2b.model.User loginUser =
                    (com.kmzyc.b2b.model.User) resultMap.get("loginUser");
      log.info("loginUser...." + loginUser.toString());
      // 将第三方账户所对应的康美会员的信息写入到session
      super.putLoginInfoToSession(loginUser, request.getParameter("clientIp"));

      // 首页地址
      String redirectPath = ConfigurationUtil.getString("portalPath");

      // 提示其完善信息
      if ("toFillInfo".equals(resultMap.get("pageResult"))) {
        // 是需要完善信息的正式会员,需要完善信息后才可以操作某些菜单
        ServletActionContext.getRequest().getSession()
            .setAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER, "Y");
        return (String) resultMap.get("pageResult");
      }
      // 如果该第三方账号所对应的康美会员已是完善信息的,则让其去到首页
      response.sendRedirect(redirectPath);
    } else {
      System.out.println("验证失败");
      log.info("验证失败");
      request.setAttribute("errorMsg", "验证失败");
      return "error";
    }
    return null;
  }
}
