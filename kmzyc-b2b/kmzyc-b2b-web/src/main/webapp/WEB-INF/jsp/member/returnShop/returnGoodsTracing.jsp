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
    	<jsp:param name="titlePrefix" value="退换货跟踪"></jsp:param>
    </jsp:include>
</head>
<body class="l-w1000">
	<!--顶部登陆条-->
    <%@ include file="/html/common/portal-common-head.jsp" %>
    
    <div class="i-page fn-clear"> 
		<!--会员中心导航-->
		<tiles:insertDefinition name="memberCenterLeft"/>
		<!--主体内容-->
		<tiles:insertDefinition name="returnGoodsTraceContent"/>
	</div>
	
	<!--底部-->
    <%@ include file="/html/common/portal-common-foot.jsp" %>
</body>
</html>