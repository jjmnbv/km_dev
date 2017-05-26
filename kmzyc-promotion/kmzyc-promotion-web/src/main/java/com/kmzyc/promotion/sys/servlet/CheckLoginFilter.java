package com.kmzyc.promotion.sys.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kmzyc.promotion.sys.model.SysUser;

@SuppressWarnings("unchecked")
public class CheckLoginFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = 1L;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		String url = req.getRequestURI();
		// System.err.println("-----------" + url);
		SysUser sessionUser = (SysUser) req.getSession().getAttribute("sysUser");
		String retrunUrl = "";
		if (url.indexOf("/sys/gotoSysUserLogin.action") >= 0
				// "/sys/gotoSysUserLogin.action".equals(url)
				// || "/sys/loginSysUser.action".equals(url)
				|| url.indexOf("/sys/loginSysUser.action") >= 0 || "/basedata/uploadProductImage.do".equals(url)
				|| "/app/uploadProductImageDraft.do".equals(url)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		if ((url.contains(".action") || url.contains(".do")) && sessionUser == null) {
			if ((url.contains(".action") || url.contains(".do"))) {
				retrunUrl = buildOriginalURL(req);
			}
			Map parameters = req.getParameterMap();
			if (parameters.get("topMenuId") != null)
				retrunUrl = "";
			((HttpServletResponse) servletResponse).sendRedirect("/sys/gotoSysUserLogin.action?retrunUrl=" + retrunUrl);
			return;
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	private String buildOriginalURL(HttpServletRequest request) {
		StringBuffer originalURL = request.getRequestURL();
		Map parameters = request.getParameterMap();
		if (parameters != null && parameters.size() > 0) {
			originalURL.append("?");
			for (Iterator iter = parameters.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String[] values = (String[]) parameters.get(key);
				for (int i = 0; i < values.length; i++) {
					originalURL.append(key).append("=").append(values[i]).append("&");
				}
			}
		}
		return originalURL.toString();
	}

}
