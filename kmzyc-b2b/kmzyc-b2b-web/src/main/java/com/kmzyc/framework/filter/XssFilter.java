package com.kmzyc.framework.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by KM on 2016/12/7.
 */
public class XssFilter extends HttpServlet implements Filter {

    FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }


    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {


        chain.doFilter(new XssHttpServletRequestWrapperNew((HttpServletRequest) request), response);


    }


}