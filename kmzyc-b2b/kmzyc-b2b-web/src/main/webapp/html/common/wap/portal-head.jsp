<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
<%
	String wapPath=ConfigurationUtil.getString("portalPath_WAP");
	String wap=ConfigurationUtil.getString("staticPath_WAP");
%>
<header>
    <div class="header-inner">
        <div class="pages-hd">
            <a class="back icon-uniE61E " href="javascript:void(0);"></a>
            <h2><%=request.getParameter("pageTitle") %></h2>
            <div class="sortlist">
                <span class="icon-uniE63B fristSpan wxico"></span>
            </div>
        </div>
        <div class="navigation" id="collapseExample" style="display:none;" >
            <ul class="b2b_list">
                <li>
                    <a href="<%=wap %>"  >
                        <span class="menu-icon icon-uniE63E"></span>
                        <span class="menu-txt">首页</span>
                    </a>
                </li>
                <li>
                    <a href="<%=wap %>/wap/new_category.html" class="waptop lk_category">
                        <span class="menu-icon icon-uniE63A"></span>
                        <span class="menu-txt">分类</span>
                    </a>
                </li>
                <li>
                    <a href="<%=wapPath %>/cart/listWapShopCar.action" class="waptop lk_shopcar">
                        <span class="menu-icon icon-uniE62D"></span>
                        <span class="menu-txt">购物车</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0);" class="j_goToMemerCenter on"  >
                        <span class="menu-icon icon-uniE611"></span>
                        <span class="menu-txt">个人中心</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</header>
