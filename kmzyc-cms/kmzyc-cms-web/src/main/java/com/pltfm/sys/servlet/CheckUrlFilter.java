package com.pltfm.sys.servlet;

import com.pltfm.sys.bean.SysMenuBean;
import com.pltfm.sys.model.SysMenu;
import com.pltfm.sys.model.SysUser;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.io.IOException;
import java.util.ArrayList;
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

public class CheckUrlFilter extends HttpServlet implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String url = req.getRequestURI();
        SysUser sessionUser = null;
        Subject currUser = SecurityUtils.getSubject();
        sessionUser = (SysUser) currUser.getSession().getAttribute("sysUser");
        List<String> sList = new ArrayList<String>();
        if (url.contains("/cms/authenSupplyAction_supplyAuthen.action")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 根据userid获取user信息及其所有角色串
        if (sessionUser != null) {
            SysMenuBean smb = SysMenuBean.instance();
            // 菜单集合
            List<SysMenu> smList = null;
            try {
                smList = smb.getMenuByUserId(sessionUser.getUserId());
            } catch (Exception e) {
                // TODO Auto-generated catch block
            }

            if (smList != null) {
                for (SysMenu sm : smList) {
                    sList.add(sm.getMenuLink());
                }
            }


        }
        //没登录
        if (sessionUser == null && (url.contains("/cms/cmsWindowDataAction_queryPageList.action") || url.contains("/cms/Adv_Byid.action"))) {
            ((HttpServletResponse) servletResponse)
                    .sendRedirect("/sys/gotoSysUserLogin.action");
            return;
        }
        //登录了，没菜单权限
        if (sessionUser != null && (url.contains("/cms/cmsWindowDataAction_queryPageList.action") || url.contains("/cms/Adv_Byid.action")) && (!sList.contains("/cms/cmsWindowDataAction_queryPageList.action") || !sList.contains("/cms/Adv_Byid.action"))) {
            ((HttpServletResponse) servletResponse)
                    .sendRedirect("/sys/gotoSysUserLogin.action");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
//		if((url.contains("/cms/cmsWindowDataAction_queryPageList.action") || url.contains("/cms/Adv_Byid.action")) && sList.contains("/cms/cmsWindowDataAction_queryPageList.action") || sList.contains("/cms/Adv_Byid.action")){
//			
//		}
//		if(sList.contains("/cms/cmsWindowDataAction_queryPageList.action") || sList.contains("/cms/Adv_Byid.action")){
//			filterChain.doFilter(servletRequest, servletResponse);
//			return;
//		}else{
//			((HttpServletResponse) servletResponse)
//			.sendRedirect("/sys/gotoSysUserLogin.action");
//			return;
//		}


    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}
