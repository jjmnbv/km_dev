package com.kmzyc.express.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.kmzyc.express.util.Page;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("unchecked")
public abstract class BaseAction extends ActionSupport implements ModelDriven {

  /**
   *uid
   */
  private static final long serialVersionUID = -7248281245574886628L;

  private Logger log = Logger.getLogger(BaseAction.class);
  public static final String PRE_PATH = "PRE_PATH";

  // private static final String cmsPagePath = SysConstants.CMS_PAGE_PATH;
  //
  // protected static String getCmspagepath() {
  // return cmsPagePath;
  // }

  /**
   * request对象
   */
  private HttpServletRequest httpServletRequest;

  /**
   * response对象
   */
  private HttpServletResponse httpServletResponse;

  /**
   * web根路径
   */
  private String webPath;

  /**
   * 分页对象
   */
  private Page page = new Page();;

  /**
   * operateResult.jsp 成功提示页面，转跳的URL设置
   */
  private String redirectURL;

  /**
   * 在构造方法中将operator从session中取出
   */
  public BaseAction() {}

  public HttpServletRequest getHttpServletRequest() {
    HttpServletRequest request = ServletActionContext.getRequest();
    try {
      request.setCharacterEncoding("UTF-8");
    } catch (Exception e) {
      log.error("字符编码异常", e);
    }
    this.httpServletRequest = request;
    return httpServletRequest;
  }

  public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
    this.httpServletRequest = httpServletRequest;
  }

  public HttpServletResponse getHttpServletResponse() {
    HttpServletResponse response = ServletActionContext.getResponse();
    response.setContentType("text/html;charset=utf-8");
    this.httpServletResponse = response;
    return httpServletResponse;
  }

  public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
    this.httpServletResponse = httpServletResponse;
  }

  public String getWebPath() {
    return webPath;
  }

  public void setWebPath(String webPath) {
    this.webPath = webPath;
  }

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public String getRedirectURL() {
    return redirectURL;
  }

  public void setRedirectURL(String redirectURL) {
    this.redirectURL = redirectURL;
  }

  @Override
  public Object getModel() {
    // TODO Auto-generated method stub
    return null;
  }

  protected String msg;

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  protected void doAjax(String msg) throws IOException {
    /*
     * 在调用getWriter之前未设置编码(既调用setContentType或者setCharacterEncoding方法设置编码),
     * HttpServletResponse则会返回一个用默认的编码(既ISO-8859-1)编码的PrintWriter实例。这样就会
     * 造成中文乱码。而且设置编码时必须在调用getWriter之前设置,不然是无效的。
     */
    HttpServletResponse response = ServletActionContext.getResponse();
    response.setContentType("text/html;charset=utf-8");
    // response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    out.println(msg);
    out.flush();
    out.close();
  }
}
