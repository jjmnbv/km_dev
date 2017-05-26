package com.kmzyc.b2b.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kmzyc.b2b.service.Live800Service;
import com.kmzyc.b2b.util.Live800Encode;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.framework.constants.Constants;
import com.opensymphony.xwork2.ActionSupport;

/**
 * live800 客服软件显示用户信息接口
 * 
 * @author river
 * 
 */
@Controller
@Scope("prototype")
public class Live800Action extends ActionSupport {

  /**
	 * 
	 */
  private static final long serialVersionUID = 8926917011309123295L;

  // private static final Logger logger = Logger.getLogger(Live800Action.class);
  private static Logger logger = LoggerFactory.getLogger(Live800Action.class);

  @Resource
  private Live800Service live800Service;

  /**
   * 获取用户基本信息与订单信息
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public String queryCustomInfo() {
    try {
      // 这个是登陆LIVE800的客服ID，便于让第三方平台记录操作者ID
      String oid = getRequest().getParameter("oid");
      logger.info("客户操作人员ID: " + oid); // live800 id

      // live800传递给第三方URL的userID，唯一值
      String userId = getRequest().getParameter("userId");
      if (StringUtils.isBlank(userId)) {
        throw new ActionException("userId 参数为空！");
      }

      // live800根据TOKEN和传递的参数计算出的时间戳，和web网页端第三方设置的timestamp是不一样的。
      String timestamp = getRequest().getParameter("timestamp");

      // live800根据TOKEN和传递的参数计算出的加密字符串hashCode，
      // 和web网页端第三方传递给LIVE800平台的hashCode是不一样的。
      String hashCode = getRequest().getParameter("hashCode");

      // 在第三方平台用MD5加密这些参数合集，生成一个加密签名字符串"safepass"
      String safepass =
          Live800Encode.getMD5Encode(URLEncoder.encode(
              userId + timestamp + Live800Encode.getEncryptKey(), "UTF-8"));

      // 把live800传递给第三方的hashCode和第三方平台计算的safepass比较
      if (safepass.equals(hashCode)) { // 合法操作
        // 获取用户基本信息
        customInfo = live800Service.getCustomInfo(Long.parseLong(userId));
        Map<String, Object> baseInfo = (Map<String, Object>) customInfo.get("baseInfo");
        String userName = MapUtils.getString(baseInfo, "loginAccount");
        // 获取用户订单信息
        Map<String, Object> args = new HashMap<String, Object>(1);
        args.put("customerAccount", userName);
        orderInfo = live800Service.getCustomOrderInfo(args);
        // 获取用户退换货信息
        args.clear();
        args.put("proposer", userName);
        exchangeInfo = live800Service.getCustomExchangeInfo(args);
      } else {// 非法操作，不返回数据
        logger.warn("非法操作，不返回数据！");
        return ERROR;
      }
    } catch (UnsupportedEncodingException e) {
      logger.error("无法对字符串进行URL Encoder，获取客户信息失败！", e);
      return ERROR;
    } catch (Exception e) {
      logger.error("获取客户信息失败！", e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 生成live800信任信息接口info参数
   * 
   * @return
   */
  public String generationInfo() {
    returnResult = new ReturnResult<Map<String, Object>>();

    try {
      // 用户登录名，唯一值
      String userName = getRequest().getParameter("userName");
      if (StringUtils.isBlank(userName)) {
        returnResult.setCode("-1");
        returnResult.setMessage("FAIL");
        return SUCCESS;
      }
      // long userId = live800Service.getUserId(userName);
      HttpServletRequest request = ServletActionContext.getRequest();
      Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);

      if (null == userId || userId == 0) {

        returnResult.setCode("-1");
        returnResult.setMessage("FAIL");
        return SUCCESS;
      }
      // 妮称
      String nickName = getRequest().getParameter("nickName");
      if (StringUtils.isBlank(nickName)) {
        nickName = userId + "";
      }
      String memo = ""; // 备注
      long ts = System.currentTimeMillis();

      // 生成hashCode参数。
      // 各参数顺序不可变
      StringBuilder hashBuf = new StringBuilder();
      hashBuf.append(userId);
      hashBuf.append(nickName);
      hashBuf.append(memo);
      hashBuf.append(ts);
      hashBuf.append(Live800Encode.getEncryptKey());
      String hashCode =
          Live800Encode.getMD5Encode(java.net.URLEncoder.encode(hashBuf.toString(), "UTF-8"));

      // 生成info参数
      StringBuilder infoBuf = new StringBuilder();
      infoBuf.append("userId=" + userId);
      infoBuf.append("&name=" + nickName);
      infoBuf.append("&memo=" + memo);
      infoBuf.append("&timestamp=" + ts);
      infoBuf.append("&hashCode=" + hashCode);
      String info = java.net.URLEncoder.encode(infoBuf.toString(), "UTF-8");

      Map<String, Object> returnObject = Maps.newHashMap();
      returnObject.put("info", info);
      returnResult.setReturnObject(returnObject);
      returnResult.setCode("200");
      returnResult.setMessage("SUCCESS");
    } catch (Exception e) {
      returnResult.setCode("404");
      returnResult.setMessage("ERROR");
      logger.error("生成live800信任信息INFO PARAM失败！", e);
    }
    return SUCCESS;
  }

  /**
   * 查询订单信息
   * 
   * @return
   */
  public String qureyOrderInfo() {
    orderResult = new ReturnResult<Map<String, Object>>();
    Map<String, Object> returnObject = Maps.newHashMap();
    try {
      Map<String, Object> args = Maps.newHashMap();
      // start（分页参数）开始记录数
      String start = getRequest().getParameter("start");
      if (StringUtils.isNotBlank(start)) {
        args.put("start", start);
      }
      // end（分页参数）结束记录数
      String end = getRequest().getParameter("end");
      if (StringUtils.isNotBlank(end)) {
        args.put("end", end);
      }
      // customerAccount客户账号
      String customerAccount = getRequest().getParameter("customerAccount");
      if (StringUtils.isNotBlank(customerAccount)) {
        args.put("customerAccount", customerAccount);
      }
      // orderCode订单号
      String orderCode = getRequest().getParameter("orderCode");
      if (StringUtils.isNotBlank(orderCode)) {
        args.put("orderCode", orderCode);
      }
      // status订单状态
      String status = getRequest().getParameter("status");
      if (StringUtils.isNotBlank(status)) {
        args.put("status", status);
      }
      // channel渠道
      String channel = getRequest().getParameter("channel");
      if (StringUtils.isNotBlank(channel)) {
        args.put("channel", channel);
      }
      // consigneeName收货人
      String consigneeName = getRequest().getParameter("consigneeName");
      if (StringUtils.isNotBlank(consigneeName)) {
        args.put("consigneeName", consigneeName);
      }
      // consigneeMobile收货电话
      String consigneeMobile = getRequest().getParameter("consigneeMobile");
      if (StringUtils.isNotBlank(consigneeMobile)) {
        args.put("consigneeMobile", consigneeMobile);
      }
      returnObject = live800Service.getCustomOrderInfo(args);
      orderResult.setReturnObject(returnObject);
      orderResult.setCode("200");
      orderResult.setMessage("SUCCESS");
    } catch (Exception e) {
      orderResult.setCode("404");
      orderResult.setMessage("ERROR");
      logger.error("生成live800信任信息INFO PARAM失败！", e);
    }
    return SUCCESS;
  }

  /**
   * 查询退换货信息
   * 
   * @return
   */
  public String qureyExchangeInfo() {
    exchangeResult = new ReturnResult<List<Map<String, Object>>>();
    List<Map<String, Object>> returnObject = Lists.newArrayList();
    try {
      Map<String, Object> args = Maps.newHashMap();
      // start（分页参数）开始记录数
      String start = getRequest().getParameter("start");
      if (StringUtils.isNotBlank(start)) {
        args.put("start", start);
      }
      // end（分页参数）结束记录数
      String end = getRequest().getParameter("end");
      if (StringUtils.isNotBlank(end)) {
        args.put("end", end);
      }
      // proposer申请人
      String proposer = getRequest().getParameter("proposer");
      if (StringUtils.isNotBlank(proposer)) {
        args.put("proposer", proposer);
      }
      // orderCode订单号
      String orderCode = getRequest().getParameter("orderCode");
      if (StringUtils.isNotBlank(orderCode)) {
        args.put("orderCode", orderCode);
      }
      // 退换货号
      String alterCode = getRequest().getParameter("alterCode");
      if (StringUtils.isNotBlank(alterCode)) {
        args.put("alterCode", alterCode);
      }
      // 状态
      String status = getRequest().getParameter("status");
      if (StringUtils.isNotBlank(status)) {
        args.put("status", status);
      }

      returnObject = live800Service.getCustomExchangeInfo(args);

      exchangeResult.setReturnObject(returnObject);
      exchangeResult.setCode("200");
      exchangeResult.setMessage("SUCCESS");
    } catch (Exception e) {
      exchangeResult.setCode("404");
      exchangeResult.setMessage("ERROR");
      logger.error("生成live800信任信息INFO PARAM失败！", e);
    }
    return SUCCESS;
  }

  private Map<String, Object> customInfo;
  private Map<String, Object> orderInfo;
  private List<Map<String, Object>> exchangeInfo;
  private ReturnResult<Map<String, Object>> returnResult;
  private ReturnResult<Map<String, Object>> orderResult;
  private ReturnResult<List<Map<String, Object>>> exchangeResult;

  public HttpServletRequest getRequest() {
    HttpServletRequest request = ServletActionContext.getRequest();
    return request;
  }

  public HttpServletResponse getResponse() {
    HttpServletResponse response = ServletActionContext.getResponse();
    return response;
  }

  public ReturnResult<Map<String, Object>> getReturnResult() {
    return returnResult;
  }

  public Map<String, Object> getOrderInfo() {
    return orderInfo;
  }

  public List<Map<String, Object>> getExchangeInfo() {
    return exchangeInfo;
  }

  public Map<String, Object> getCustomInfo() {
    return customInfo;
  }

  public ReturnResult<Map<String, Object>> getOrderResult() {
    return orderResult;
  }

  public ReturnResult<List<Map<String, Object>>> getExchangeResult() {
    return exchangeResult;
  }
}
