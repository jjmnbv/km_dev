<%@ page language="java"  import="com.kmzyc.zkconfig.ConfigurationUtil"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <div class="i-topbar">
			<div class="l-w fn-clear">
				<p class="fn-left loginbar">
					您好，欢迎来到康美中药城！[<a href="">登录</a>]&nbsp;[<a href="">注册</a>]
				</p>
				<ul class="fn-right topmenu">
					<li class="topmenu-item topmenu-item-phone"><i class="ico-phone"></i>客服热线：<strong>400-6600-518</strong></li>
					<li class="topmenu-item"><b></b><a href="">供应商入口</a></li>
					<li class="topmenu-item topmenu-item-collect"><b></b><i class="ico-collect"></i><a href="">收藏本站</a></li>
					<li class="topmenu-item"><b></b><a href="">帮助中心</a></li>
				</ul>
			</div>
		</div> -->
       <%@ include file="/html/common/portal-common-top.jsp" %>
		<div class="i-shorthead">
			<div class="l-w fn-clear">
				<h1 class="logo fn-left">

					<a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>index.html" hidefocus="true"><img height="115" src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/common/logo.png" alt="康美中药城"></a>

				</h1>
			</div>
		</div>
