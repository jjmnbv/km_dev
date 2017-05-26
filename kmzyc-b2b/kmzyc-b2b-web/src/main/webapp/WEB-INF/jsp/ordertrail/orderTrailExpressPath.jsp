<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
            <title>订单跟踪-物流详情</title>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
    	<jsp:param name="titlePrefix" value="订单物流"></jsp:param>
    </jsp:include>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

</head>
	<body class="l-w1000">
		<!--顶部登陆条-->
        <%@ include file="/html/common/portal-common-top.jsp" %>  

		<tiles:insertDefinition name="orderTrailExpressPathContent" />

		<!--底部-->
        <%@ include file="/html/common/portal-common-foot.jsp" %>  
	</body>
</html>