package com.pltfm.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.CookiesAware;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.alibaba.fastjson.JSON;
import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport
    implements
      ServletRequestAware,
      ServletResponseAware,
      RequestAware,
      CookiesAware,
      ParameterAware,
      SessionAware,
      ApplicationAware {

  /**
   * 
   */
  private static final long serialVersionUID = -3436974352110732022L;



  protected HttpServletRequest httpServletRequest;

  protected HttpServletResponse httpServletResponse;

  protected Map request;

  protected Map session;

  protected Map application;

  protected Map parameters;

  protected Map cookiesMap;

  protected int pageSize = 10;
  protected Page page;

  protected HttpServletRequest getRequest() {
    return ServletActionContext.getRequest();
  }

  public HttpSession getSession() {
    return ServletActionContext.getRequest().getSession();
  }

  public Map getSessionMap() {
    return ActionContext.getContext().getSession();
  }

  public PrintWriter getPrintWriter() throws IOException {
    return getResponse().getWriter();
  }

  protected HttpServletResponse getResponse() {
    HttpServletResponse response = ServletActionContext.getResponse();
    response.setContentType("text/html;charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
    return response;
  }

  public void BaseAction() {
    this.httpServletRequest = ServletActionContext.getRequest();
  }

  /**
   * 小数点保留两位
   * 
   * @param s
   * @throws IOException
   */

  public String formatDouble(double s) {
    DecimalFormat fmt = new DecimalFormat("##0.00");
    return fmt.format(s);

  }

  public String formateDate(Date date) {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date);


  }

  /**
   * 将对象转换成JSON字符串，并响应回前台
   * 
   * @param object
   * @throws IOException
   */


  public void writeJson(Object object) {
    // String json = JSON.toJSONString(object);
    String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
    try {
      ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
      ServletActionContext.getResponse().getWriter().write(json);
      ServletActionContext.getResponse().getWriter().flush();
    } catch (IOException e) {

    }
  }

  protected final Log log = LogFactory.getLog(getClass());

  @Override
  public void setApplication(Map arg0) {
    // TODO Auto-generated method stub
    this.application = arg0;

  }

  @Override
  public void setSession(Map arg0) {
    // TODO Auto-generated method stub
    this.session = arg0;

  }

  @Override
  public void setParameters(Map arg0) {
    // TODO Auto-generated method stub
    this.parameters = arg0;

  }

  @Override
  public void setCookiesMap(Map arg0) {
    // TODO Auto-generated method stub
    this.cookiesMap = arg0;

  }

  @Override
  public void setRequest(Map arg0) {
    // TODO Auto-generated method stub
    this.request = arg0;

  }

  @Override
  public void setServletResponse(HttpServletResponse arg0) {
    // TODO Auto-generated method stub
    this.httpServletResponse = arg0;

  }

  @Override
  public void setServletRequest(HttpServletRequest arg0) {
    // TODO Auto-generated method stub
    this.httpServletRequest = arg0;
  }

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }



}
