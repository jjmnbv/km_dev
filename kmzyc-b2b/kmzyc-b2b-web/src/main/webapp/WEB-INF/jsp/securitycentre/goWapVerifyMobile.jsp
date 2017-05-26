<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta name="Keywords" content="" />
<meta name="Description" content="" />
 <jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
    	<jsp:param name="titlePrefix" value="手机验证"></jsp:param>
    </jsp:include>
</head>
<body class="l-w1000">
<!--头部LOGO及搜索-->
<%--      <%@ include file="/html/common/wap/portal-head.jsp" %> --%>
 <jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="手机验证"></jsp:param>
	    </jsp:include> 
<!--安全中心内容-->
<tiles:insertDefinition name="goWapVerifyMobileContent" />
<!--底部-->
   <%@ include file="/html/common/wap/portal-foot.jsp" %>
</body>
</html>