<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
            <jsp:include page="/WEB-INF/jsp/common/template.jsp">
    	<jsp:param name="titlePrefix" value="订单详情"></jsp:param>
    </jsp:include>
</head>
	<body class="l-w1000">
		<!--顶部登陆条-->
        <%@ include file="/html/common/portal-common-top.jsp" %>
		<tiles:insertDefinition name="orderTrailDetailContent" />
		<!--底部-->
        <%@ include file="/html/common/portal-common-foot.jsp" %>
	</body>
</html>