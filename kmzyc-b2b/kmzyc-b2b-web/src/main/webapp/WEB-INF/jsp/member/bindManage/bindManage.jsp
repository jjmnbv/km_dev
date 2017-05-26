<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"  %>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<!DOCTYPE html>
<html>
<head>
       <jsp:include page="/WEB-INF/jsp/common/template.jsp">
   		    <jsp:param name="titlePrefix" value="绑定管理"></jsp:param>
       </jsp:include>
</head>
<body>
		<!--顶部登陆条-->
    <%@ include file="/html/common/portal-common-head.jsp" %>  
	<!--会员中心导航-->
	<tiles:insertDefinition name="memberCenterLeft"/>
	<!-- 绑定管理 -->
	<tiles:insertDefinition name="bindManageContent"/>
	<!--底部-->
	<%@ include file="/html/common/portal-common-foot.jsp" %>
</body>
</html>
