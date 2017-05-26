package com.pltfm.app.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class DelegatingServletProxy
 */
public class DelegatingServletProxy extends GenericServlet {
  private static final Logger log = Logger.getLogger(DelegatingServletProxy.class);
  private String targetBean;
  private Servlet proxy;
  private static final long serialVersionUID = 1L;

  /**
   * @see GenericServlet#GenericServlet()
   */
  public DelegatingServletProxy() {
    super();
  }

  /**
   * 被代理的servlet的service方法
   */
  @Override
  public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException,
      IOException {
    proxy.service(arg0, arg1);
  }

  @Override
  public void init() throws ServletException {
    // 得到被代理的servlet的名字，在web.xml中配好：<servlet-name>initialServlet</servlet-name>
    this.targetBean = getServletName();
    log.info("加载的初始化的servlet名称为：" + targetBean.getClass().getName());
    // 根据上述操作得到的servlet的名字去找到该servlet
    getServletBean();
    // 实际执行被代理的servlet的init方法
    proxy.init(getServletConfig());

  }

  private void getServletBean() {
    // 由spring容器获得上下文对象
    WebApplicationContext wac =
        WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
    // 得到被代理的servlet对象
    this.proxy = (Servlet) wac.getBean(targetBean);

  }

}
