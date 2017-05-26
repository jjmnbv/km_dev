package com.pltfm.sys.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pltfm.sys.model.SysUser;

public class CheckLoginFilter extends HttpServlet implements Filter {

  private static final long serialVersionUID = 1L;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) servletRequest;
    String url = req.getRequestURI();
    SysUser sessionUser = (SysUser) req.getSession().getAttribute("sysUser");
    if ("/sys/gotoSysUserLogin.action".equals(url) || "/sys/loginSysUser.action".equals(url)) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else if ((url.contains(".action") || url.contains(".do")) && sessionUser == null) {
      ((HttpServletResponse) servletResponse).sendRedirect("/sys/gotoSysUserLogin.action");
    } else {
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {

  }

}
