package com.kmzyc.framework.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.supplier.model.KeyWords;
import com.kmzyc.supplier.service.KeyWordsService;


public class KeyWordsFilter extends HttpServlet implements Filter {

    private static Logger logger = LoggerFactory.getLogger(KeyWordsFilter.class);

    @Resource(name = "keyWordsServiceImpl")
    private KeyWordsService keyWordsService;

    private List<KeyWords> listKeyWords = new ArrayList<KeyWords>();

    private Map<String, String> map = new HashMap<String, String>();

    private List<String> includeUrlList; // 需过滤特殊字符的请求，逗号分隔

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String includeUrl = filterConfig.getInitParameter("includeUrl");
        includeUrlList = Arrays.asList(StringUtils.split(includeUrl, ","));
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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) request;
        String requestUri = rq.getRequestURI();
        // 获取请求名称（如：payCallBack.action），不含路径
        String requestName = requestUri.substring(requestUri.lastIndexOf("/") + 1);
        if (includeUrlList.contains(requestName)) {
            Iterator its = rq.getParameterMap().values().iterator();
            while (its.hasNext()) {
                String[] params = (String[]) its.next();
                for (int i = 0; i < params.length; i++) {
                    for (String oj : map.keySet()) {
                        String key = oj;
                        params[i] = params[i].replaceAll(key, map.get(key));
                        String lowerStr = params[i].toLowerCase();
                        StringBuffer sb = new StringBuffer("");
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
