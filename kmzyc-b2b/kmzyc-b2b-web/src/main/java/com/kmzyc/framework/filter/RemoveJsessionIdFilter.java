package com.kmzyc.framework.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 移除掉url中所存在的jsessionId
 * 
 * @author Administrator 20140808 maliqun create
 * 
 */
public class RemoveJsessionIdFilter extends HttpServlet implements Filter {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (!(request instanceof HttpServletRequest)) {
      chain.doFilter(request, response);
      return;
    }

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    // 如果含有,则清除掉
    if (httpRequest.isRequestedSessionIdFromURL()) {
      HttpSession session = httpRequest.getSession();
      if (session != null) session.invalidate();
    }

    HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {
      @Override
      public String encodeRedirectUrl(String url) {
        return url;
      }

      public String encodeRedirectURL(String url) {
        return url;
      }

      public String encodeUrl(String url) {
        return url;
      }

      public String encodeURL(String url) {
        return url;
      }
    };
    chain.doFilter(request, wrappedResponse);
  }

  @Override
  public void destroy() {}

  @Override
  public void init(FilterConfig arg0) throws ServletException {}

}
