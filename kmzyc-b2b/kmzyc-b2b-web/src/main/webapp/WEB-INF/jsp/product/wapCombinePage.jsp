<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
	<title>康美中药城</title>
	<jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
	 	<jsp:param name="titlePrefix" value=""></jsp:param>
	</jsp:include>
</head>
<body >
	<!--顶部登陆条-->
    <jsp:include page="/html/common/wap/portal-head.jsp">
		<jsp:param name="pageTitle" value="推荐组合"></jsp:param>
	</jsp:include>
	<!--主体内容-->
	<tiles:insertDefinition name="wapCombinePageContent"/>
	<!--底部-->
    <%@ include file="/html/common/wap/portal-foot.jsp" %>
</body>
</html>

