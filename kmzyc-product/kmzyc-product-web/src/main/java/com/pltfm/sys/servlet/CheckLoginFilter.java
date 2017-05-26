package com.pltfm.sys.servlet;

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

import com.pltfm.sys.model.SysUser;

public class CheckLoginFilter extends HttpServlet implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		String url = req.getRequestURI();
		// System.err.println("-----------" + url);
		SysUser sessionUser = (SysUser) req.getSession()
				.getAttribute("sysUser");
		String retrunUrl = "";
		if (url.indexOf("/sys/gotoSysUserLogin.action")>=0 
//				"/sys/gotoSysUserLogin.action".equals(url)
//				|| "/sys/loginSysUser.action".equals(url)
				|| url.indexOf("/sys/loginSysUser.action")>=0 
				|| "/basedata/uploadProductImage.do".equals(url)
				||"/app/uploadProductImageDraft.do".equals(url)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		if ((url.contains(".action") || url.contains(".do"))
				&& sessionUser == null) {
			if ((url.contains(".action") || url.contains(".do"))) {
				retrunUrl = buildOriginalURL(req);
			}
			Map parameters = req.getParameterMap();
			if (parameters.get("topMenuId") != null)
				retrunUrl = "";
			((HttpServletResponse) servletResponse)
					.sendRedirect("/sys/gotoSysUserLogin.action?retrunUrl="
							+ retrunUrl);
			return;
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	private String buildOriginalURL(HttpServletRequest request) {
		StringBuffer originalURL = request.getRequestURL();
		Map<String, String[]> parameters = request.getParameterMap();
		if (parameters != null && parameters.size() > 0) {
			originalURL.append("?");
			for (Map.Entry<String, String[]> entry:  parameters.entrySet()) {
				String key = entry.getKey();
				String[] values = entry.getValue();
				for (int i = 0; i < values.length; i++) {
					originalURL.append(key).append("=").append(values[i]).append("&");
				}
			}
		}
		return originalURL.toString();
	}

}