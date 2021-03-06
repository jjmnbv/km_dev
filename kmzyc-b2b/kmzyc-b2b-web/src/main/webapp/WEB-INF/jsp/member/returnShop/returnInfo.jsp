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
            <jsp:include page="/WEB-INF/jsp/common/template.jsp">
    	<jsp:param name="titlePrefix" value="退换货记录"></jsp:param>
    </jsp:include>
</head>
<body class="l-w1000">
	<!--头部LOGO及搜索-->
    <%@ include file="/html/common/portal-common-head.jsp" %> 
    
    <div class="i-page fn-clear">
	    <!--会员中心导航-->
		<tiles:insertDefinition name="memberCenterLeft"/>	
		<!--订单内容-->
		<tiles:insertDefinition name="returnInfoContent"/>	
    </div>
	
	<!--底部-->
    <%@ include file="/html/common/portal-common-foot.jsp" %>
</body>
</html>

