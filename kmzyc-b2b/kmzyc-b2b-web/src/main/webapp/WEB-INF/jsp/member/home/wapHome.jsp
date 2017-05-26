<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.io.*" errorPage="" %>
<!DOCTYPE html>
<html>
<head>

 	<head>
	 	<jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
	     	<jsp:param name="titlePrefix" value="会员中心"></jsp:param>
	    </jsp:include>
	</head>
</head>

<body class="l-w1000">
	<!--顶部登录条-->
  <%--   <%@ include file="/html/common/wap/portal-head.jsp" %> --%>
		<jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="个人中心"></jsp:param>
	    </jsp:include> 
	<tiles:insertDefinition name="wapHomeContent"/>
	 
	<!--底部-->
   <%@ include file="/html/common/wap/portal-foot.jsp" %>  
</body>
 
<%--</kh:html>--%>
</html>
