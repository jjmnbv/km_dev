<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.io.*" errorPage="" %>
<!DOCTYPE html>
<html>
<head>

    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="个人中心"></jsp:param>
    </jsp:include>
</head>

<body>
	<!--portal-head  -->
    <%@ include file="/html/common/portal-common-head.jsp" %>

	<div class="i-page fn-clear">
		<!--会员中心导航-->
		<tiles:insertDefinition name="memberCenterLeft"/>
		<!--面板内容-->
		<tiles:insertDefinition name="homeContent"/>
	</div>

	<!--底部-->
	<%@ include file="/html/common/portal-common-foot.jsp" %>
    </body>
<%--</kh:html>--%>
</html>
