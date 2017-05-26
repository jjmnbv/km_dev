package com.kmzyc.framework.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.common.util.MD5;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.app.AppJsonUtils;
import com.kmzyc.supplier.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

@SuppressWarnings("unchecked")
public class LoginCheckFilter extends HttpServlet implements Filter {

    private static final long serialVersionUID = 1L;
    // 手机接口有效时间差，单位秒
    private static final String MOBILE_VALID_DIFF_TIME = ConfigurationUtil.getString("supplier_common_mobile_valid_diff_time");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String url = req.getRequestURI();
        if (url.startsWith("/app/") && url.endsWith(".action") && !"/app/illegalUrl.action".equals(url)) {
            // 手机接口过滤
            servletRequest.removeAttribute("url_check_result");
            String stamp = null;// 时间戳明文
            String stampVal = null;// 时间戳密文
            JSONObject json = AppJsonUtils.getJsonObject(req);
            if (json != null && !json.isEmpty()) {
                stamp = json.getString("stamp");
                stampVal = json.getString("stampVal");
            }
            if (!StringUtil.isEmpty(stamp) && StringUtil.equalLen(stampVal, 32)) {
                try {
                    long now = new Date().getTime();
                    if ((now - Long.parseLong(stamp)) < (Integer.parseInt(MOBILE_VALID_DIFF_TIME) * 1000)
                            && stampVal.equals(MD5.getMD5Str(stamp))) {
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                } catch (Exception e) {

                }
            }
            ((HttpServletResponse) servletResponse).sendRedirect(req.getContextPath()+ "/app/illegalUrl.action");
            return;
        } else {
            StringBuffer urlPara = req.getRequestURL();
            Enumeration en = req.getParameterNames();
            StringBuffer sb = new StringBuffer("");
            while (en.hasMoreElements()) {
                String paramName = (String) en.nextElement();
                String paramValue = req.getParameter(paramName);
                sb.append(sb.length() < 1 ? "?" : "&").append(paramName).append("=").append(paramValue);
            }
            urlPara.append(sb);
            Long uid = (Long) req.getSession().getAttribute(Constants.SESSION_USER_ID);
            String comPareUrl = url.substring(0, url.indexOf(".action"));
            comPareUrl = comPareUrl.substring(comPareUrl.lastIndexOf("/") + 1);
            if (!LoginFilterMap.getMap().containsKey(comPareUrl) && (null == uid || uid < 1)) {
                ((HttpServletResponse) servletResponse).sendRedirect(req.getContextPath() + "/html/login.html?ReturnUrl=" + urlPara);
                req.getSession().removeAttribute("loginImg");
                return;
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
