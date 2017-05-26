package com.kmzyc.framework.filter;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.b2b.model.KeyWords;
import com.kmzyc.b2b.service.KeyWordsService;

public class KeyWordsFilter extends HttpServlet implements Filter {

  // private static Logger logger = Logger.getLogger(KeyWordsFilter.class);
  private static Logger logger = LoggerFactory.getLogger(KeyWordsFilter.class);
  private static final long serialVersionUID = -1796957013219743036L;

  @Resource(name = "keyWordsServiceImpl")
  private KeyWordsService keyWordsService;
  private List<KeyWords> listKeyWords = new ArrayList<KeyWords>();
  private Map<String, String> map = new HashMap<String, String>();

  private List<String> excludeUrlList; // 无需过滤特殊字符的请求，逗号分隔

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    String excludeUrl = filterConfig.getInitParameter("excludeUrl");
    excludeUrlList = Arrays.asList(StringUtils.split(excludeUrl, ","));
    // 获取过滤的关键字放入map集合中
    try {
      listKeyWords = keyWordsService.findKeyWords();
    } catch (Exception e) {
      logger.error("获取过滤关键字错误" + e.getMessage(), e);
    }
    for (int i = 0; i < listKeyWords.size(); i++) {
      map.put(listKeyWords.get(i).getKeyWords(), listKeyWords.get(i).getRepWords());
    }
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest rq = (HttpServletRequest) request;
    String requestUri = rq.getRequestURI();
    // 获取请求名称（如：payCallBack.action），不含路径
    String requestName = requestUri.substring(requestUri.lastIndexOf("/") + 1);
    if (!excludeUrlList.contains(requestName)) {
      Iterator its = rq.getParameterMap().values().iterator();
      while (its.hasNext()) {
        String[] params = (String[]) its.next();
        for (int i = 0; i < params.length; i++) {
          for (String oj : map.keySet()) {
            String key = oj;
            params[i] = params[i].replaceAll(key, map.get(key));
            String lowerStr = params[i].toLowerCase();
            StringBuilder sb = new StringBuilder("");
            int fromIndex = 0;
            int endIndex = 0;
            while ((endIndex = lowerStr.indexOf(key.toLowerCase(), fromIndex)) >= 0) {
              sb.append(params[i].substring(fromIndex, endIndex)).append(map.get(key));
              fromIndex = endIndex + key.length();
            }
            sb.append(params[i].substring(fromIndex));
            params[i] = sb.toString();
          }
        }
      }
    }
    chain.doFilter(rq, response);
  }
}
