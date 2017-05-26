<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.io.*" errorPage="" %>
<!DOCTYPE html>
<html>
<head>

    <jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
        <jsp:param name="titlePrefix" value="wap端我的收藏"></jsp:param>
    </jsp:include>
</head>

<body class="l-w1000">
	<!--顶部登录条-->
<!--头部LOGO及搜索-->
    <jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="收藏管理"></jsp:param>
	    </jsp:include> 
	<tiles:insertDefinition name="wapFavoriteContent"/>
	 
	<!--底部-->
   <%@ include file="/html/common/wap/portal-foot.jsp" %>  
</body>
 
<%--</kh:html>--%>
</html>
