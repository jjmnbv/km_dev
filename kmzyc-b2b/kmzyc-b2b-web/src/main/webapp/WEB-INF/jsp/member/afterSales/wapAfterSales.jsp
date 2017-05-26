<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
        <jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
    	<jsp:param name="titlePrefix" value="售后服务"></jsp:param>
    	 <jsp:param name="isWap" value="1"></jsp:param>
    </jsp:include>
</head>
<body class="l-w1000">
 <!--头部LOGO及搜索-->
     <jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="售后服务"></jsp:param>
	    </jsp:include> 
	<!--订单内容-->
	<tiles:insertDefinition name="wapAfterSales"/>	
<!--底部-->
   <%@ include file="/html/common/wap/portal-foot.jsp" %>  
</body>
</html>