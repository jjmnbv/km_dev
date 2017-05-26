<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>

    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="资讯信息"></jsp:param>
    </jsp:include>
</head>
<body >
	<!--顶部登陆条-->
    <%@ include file="/html/common/portal-common-head.jsp" %>  
	<!--主体内容-->
	<tiles:insertDefinition name="newsContent"/>
	<!--底部-->
    <%@ include file="/html/common/portal-common-foot.jsp" %>
</body>
</html>

