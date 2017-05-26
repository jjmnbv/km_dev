<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="完善信息"></jsp:param>
</jsp:include>
</head>
<body class="l-w1000">
	<!-- 头部登录 -->
	<%@ include file="/html/common/portal-common-top.jsp" %>

	<!-- 完善信息内容 -->
    <tiles:insertDefinition name="setUserInfoContent"/>
    <script>_ozprm="user_id=<%=new Date().getTime()%>"</script>
    <!-- 底部内容 -->
	<%@ include file="/html/common/portal-common-foot.jsp" %>
</body>
</html>