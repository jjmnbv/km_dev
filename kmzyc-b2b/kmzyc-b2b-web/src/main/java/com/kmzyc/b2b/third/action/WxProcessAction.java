package com.kmzyc.b2b.third.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.third.handler.WxProcessHandler;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.km.framework.action.BaseAction;

/**
 * 微信处理类(包括对推送消息和事件的响应)
 * 
 * @author Administrator 2014-05-12
 * 
 */
@Controller("wxProcessAction")
@Scope("prototype")
public class WxProcessAction extends BaseAction {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  private static Logger log = LoggerFactory.getLogger(WxProcessAction.class);

  /**
   * 处理推送过来的消息
   * 
   * @return
   */
  public String processHandler(HttpServletRequest request, HttpServletResponse response) {

    // 消息处理类
    String responseContent = WxProcessHandler.processHandler(request);
    log.info("WxProcessAction响应推送的xml消息" + responseContent);
    PrintWriter out = null;
    try {
      // 响应出去
      out = response.getWriter();
      out.print(responseContent);
      out.flush();
      out.close();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return "error";
    }
    return null;
  }

  /**
   * 处理来自微信服务器的请求(大多数是用户的推送)
   * 
   * @return
   * @throws UnsupportedEncodingException
   */
  public String processRequest() throws UnsupportedEncodingException {
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();

    log.info("WxProcessAction进入processRequest请求...");

    // 做签名验证,验证开发者填写的url是否是真实安全的
    if ("GET".equals(request.getMethod())) {
      checkSignature(request, response);
      return null;
    } else {
      // 防乱码
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");
      log.info("WxProcessAction处理来自微信服务器的POST请求(推送处理)---------->" + request.getRequestURL());
      // 处理推送请求
      return processHandler(request, response);
    }
  }

  /**
   * 做token验证,成为开发者第一步要做的就是验证token的安全性,成功之后,事件和消息的推送微信服务器将推送到开发者填的url上
   */
  private void checkSignature(HttpServletRequest request, HttpServletResponse response) {

    String echostr = request.getParameter("echostr");
    String timestamp = request.getParameter("timestamp");
    String nonce = request.getParameter("nonce");
    String signature = request.getParameter("signature");

    System.out.println("微信传入的参数:" + request.getQueryString());

    if (StringUtils.isEmpty(echostr) || StringUtils.isEmpty(timestamp)
        || StringUtils.isEmpty(nonce) || StringUtils.isEmpty(signature)) {
      log.error("********微信开发者认证请求传入参数为空！！");
      System.out.println("微信开发者认证请求传入参数为空.....");
    } else {
      System.out.println("********微信传入参数：" + request.getQueryString());
      String[] arrays = {ThirdConstant.WX_TOKEN, timestamp, nonce};
      // 将微信传入参数排序，并拼接成字符串进行SHA1加密
      Arrays.sort(arrays);
      String tempStr = StringUtils.join(arrays);
      String encodeStr = DigestUtils.shaHex(tempStr);
      response.setContentType("text/html");
      if (encodeStr.equals(signature)) {
        log.error("********微信开发者认证请求校验成功！！");
        try {
          //TODO fixed Servlet reflected cross site scripting vulnerability
          response.getWriter().print(echostr);
        } catch (IOException e) {
          log.error(e.getMessage(),e);
        }
      } else {
        log.error("********微信开发者认证请求校验失败！！");
      }
    }
  }

}
