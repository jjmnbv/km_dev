package com.kmzyc.framework.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.RedirectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.util.StringUtil;

/***
 * Https 过滤器，强制某些特定的请求必须使用https，其他非必须入使用https自动跳转回http请求 强制 使用url设置在
 * applicationContext-common.xml中id = SSLFilter的bean的enableSSLUrls属性
 * 
 * @author xuyuliang
 * @date 2016-03-31 14:00
 *
 */
public class SSLFilter extends HttpServlet implements Filter {

  private static final long serialVersionUID = 1L;

  // private static final Logger logger = Logger.getLogger(SSLFilter.class);
  private static Logger logger = LoggerFactory.getLogger(SSLFilter.class);

  /** 启用ssl */
  private boolean sslEnable;
  /** https 地址 */
  private String defaultBaseSSLUrl;
  /** http地址 */
  private String defaultBaseUrl;
  /** 需要强制使用https请求的地址 */
  private List<String> enableSSLUrls;

  private static String HTTP_PORT = "80";
  private static String HTTPS_PORT = "443";

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    HTTP_PORT = getPort(defaultBaseUrl);
    HTTPS_PORT = getPort(defaultBaseSSLUrl);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest rq = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;
    try {
      checkProtocolRedirect(rq, res);
    } catch (RedirectException e) {
      // session set something
      res.setContentType("text/html; charset=UTF-8");
      res.sendRedirect(e.getMessage());
      return;
    }

    chain.doFilter(rq, res);
  }

  private void checkProtocolRedirect(HttpServletRequest request, HttpServletResponse response)
      throws RedirectException {
    if (!sslEnable) {
      return;
    }

    String baseUrl = getBaseUrl(request);
    boolean isSSL = baseUrl.startsWith(defaultBaseSSLUrl);

    String action = request.getServletPath();
    if (StringUtil.isEmpty(action)) {
      return;
    }

    String url = baseUrl + action;
    if (isSSL) {
      if (!enableSSLUrls.contains(action)) {
        url = url.replaceFirst(defaultBaseSSLUrl, defaultBaseUrl);
        logger.info("请求重定向：" + url);
        throw new RedirectException(url);
      }
    } else {
      if (enableSSLUrls.contains(action)) {
        if (url.startsWith(defaultBaseUrl)) {
          url = url.replaceFirst(defaultBaseUrl, defaultBaseSSLUrl);
          logger.info("请求重定向：" + url);
          throw new RedirectException(url);
        }
      }
    }
  }

  private static String getBaseUrl(HttpServletRequest request) {
    StringBuilder b = new StringBuilder();
    b.append(request.getScheme()).append("://").append(request.getServerName());

    String port = "";
    if (request.getScheme().equalsIgnoreCase("http")) {
      port = HTTP_PORT;
    } else {
      port = HTTPS_PORT;
    }

    if (!StringUtil.isEmpty(port)) {
      b.append(":").append(String.valueOf(port));
    }

    if (request.getContextPath() != null) {
      b.append(request.getContextPath());
    }

    return b.toString();
  }

  private static String getPort(String url) {
    // set the ports
    String port = "";

    int x = url.indexOf("://");
    x = url.indexOf(":", x + 3);
    if (x > 0) {
      port = url.substring(x + 1);
    }

    x = port.indexOf("/");
    if (x > 0) {
      port = port.substring(0, x);
    }

    return port;
  }

  public boolean isSslEnable() {
    return sslEnable;
  }

  public void setSslEnable(boolean sslEnable) {
    this.sslEnable = sslEnable;
  }

  public String getDefaultBaseSSLUrl() {
    return defaultBaseSSLUrl;
  }

  public void setDefaultBaseSSLUrl(String defaultBaseSSLUrl) {
    this.defaultBaseSSLUrl = defaultBaseSSLUrl;
  }

  public String getDefaultBaseUrl() {
    return defaultBaseUrl;
  }

  public void setDefaultBaseUrl(String defaultBaseUrl) {
    this.defaultBaseUrl = defaultBaseUrl;
  }

  public List<String> getEnableSSLUrls() {
    return enableSSLUrls;
  }

  public void setEnableSSLUrls(List<String> enableSSLUrls) {
    this.enableSSLUrls = enableSSLUrls;
  }
}
