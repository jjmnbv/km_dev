<!-- 此页已废弃，整合入orderAssess -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<kh:html 
    keywords="" 
	description="" 
	title="追加商品评分-康美中药城"
	css="core/reset,core/layout,core/function,lib/module,lib/tpl,pages/user"  
	js="jquery-core,member/memberCommon">
	<link rel="shortcut icon" href="affixes/images/logos/favicon.png" type="image/x-icon"/>
<title>康美中药城-追加商品评价</title>

<body>

	<!--头部LOGO及搜索-->
<%-- 	<tiles:insertDefinition name="memberCenterHead"/> --%>
	<%@ include file="/html/common/portal-common-head.jsp" %> 
	
	<!--购物导航-->
<%-- 	<tiles:insertDefinition name="shoppingNavigation"/> --%>
	
	 <div class="i-page fn-clear">
		<!--会员中心导航-->
		<tiles:insertDefinition name="memberCenterLeft"/>
		<tiles:insertDefinition name="appendAssessContent"/>
	 </div>
	 
	<!--底部-->
<%@ include file="/html/common/portal-common-foot.jsp" %>
</body>
</kh:html>

