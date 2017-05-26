package com.kmzyc.supplier.util;


import com.kmzyc.supplier.model.User;
import com.kmzyc.framework.constants.Constants;
import com.sun.org.apache.xalan.internal.xsltc.dom.Filter;
import com.whalin.MemCached.MemCachedClient;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AutoLoginFilter extends HttpServlet implements Filter, javax.servlet.Filter {

    private static final long serialVersionUID = 1L;

    WebApplicationContext wac;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String loginSessionID = null;
        User loginUser = null;
        Cookie[] cs = req.getCookies();

        MemCachedClient memCachedClient = (MemCachedClient) wac.getBean("memCachedClient");
        if (cs != null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginSessionID")) {
                    loginSessionID = c.getValue();
                    if (memCachedClient.get(loginSessionID) != null) {
                        loginUser = (User) memCachedClient.get(loginSessionID);
                        req.getSession().setAttribute(Constants.SESSION_USER_KEY, loginUser);
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        wac = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
    }

    @Override
    public boolean test(int node) {
        return false;
    }

}