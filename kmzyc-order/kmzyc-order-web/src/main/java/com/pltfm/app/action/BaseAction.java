package com.pltfm.app.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("unchecked")
public abstract class BaseAction extends ActionSupport implements ModelDriven {

  /**
	 * 
	 */
  private static final long serialVersionUID = 3328086684770456294L;
  private Logger log = Logger.getLogger(BaseAction.class);
  public static final String PRE_PATH = "PRE_PATH";

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
  private Page page = new Page();

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
  
  /**
   * 将对象返回成Json字符串
   *
   * @param object
   */
  protected void writeJson(Object object) {
      
      String json = JSON.toJSONString(object);
      strWriteJson(json);
  }

  /**
   * 将字符串返回成Json字符串
   *
   * @param strJson
   */
  protected void strWriteJson(String strJson) {
      ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
      PrintWriter pw = null;
      try {
          pw = ServletActionContext.getResponse().getWriter();
          pw.write(strJson);
          pw.flush();
      } catch (IOException e) {
          e.printStackTrace();
      } finally {
          if (pw != null) {
              pw.close();
          }
      }
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
    HttpServletResponse response = ServletActionContext.getResponse();
    response.setContentType("text/html;charset=utf-8");
    PrintWriter out = response.getWriter();
    out.println(msg);
    out.flush();
    out.close();
  }
}
