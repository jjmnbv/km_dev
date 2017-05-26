package com.kmzyc.mframework.filter;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.kmzyc.mailmobile.model.KeyWords;
import com.kmzyc.mailmobile.service.KeyWordsService;


public class KeyWordsFilter extends HttpServlet implements Filter {

    private static Logger logger = Logger.getLogger(KeyWordsFilter.class);
    private static final long serialVersionUID = -1796957013219743036L;

    @Resource(name = "keyWordsServiceImpl")
    private KeyWordsService keyWordsService;
    private List<KeyWords> listKeyWords = new ArrayList<>();
    private Map<String, String> map = new HashMap<>();

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // 获取过滤的关键字放入map集合中
        try {
            listKeyWords = keyWordsService.findKeyWords();
        } catch (Exception e) {
            logger.error("获取过滤关键字错误" + e.getMessage(), e);
        }
        for (KeyWords listKeyWord : listKeyWords) {
            map.put(listKeyWord.getKeyWords(), listKeyWord.getRepWords());
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) request;
        for (String[] params : rq.getParameterMap().values()) {
            for (int i = 0; i < params.length; i++) {
                for (Map.Entry<String, String> oj : map.entrySet()) {
                    String key = oj.getKey();
                    params[i] = params[i].replaceAll(key, oj.getValue());
                    String lowerStr = params[i].toLowerCase();
                    StringBuilder sb = new StringBuilder();
                    int fromIndex = 0;
                    int endIndex;
                    while ((endIndex = lowerStr.indexOf(key.toLowerCase(), fromIndex)) >= 0) {
                        sb.append(params[i].substring(fromIndex, endIndex)).append(map.get(key));
                        fromIndex = endIndex + key.length();
                    }
                    sb.append(params[i].substring(fromIndex));
                    params[i] = sb.toString();
                }
            }
        }
        chain.doFilter(rq, response);
    }
}
