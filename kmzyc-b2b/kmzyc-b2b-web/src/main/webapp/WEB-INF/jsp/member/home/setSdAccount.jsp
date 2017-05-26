<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>

            <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="时代会员设置用户名"></jsp:param>
    </jsp:include>
</head>

<body>


<!--portal-head  -->
   <%--  <%@ include file="/html/common/portal-common-head.jsp" %> --%>
	<%@ include file="/html/common/portal-common-top.jsp" %>
	<%-- <!--会员中心导航-->
	<tiles:insertDefinition name="memberCenterLeft"/> --%>

	<!--收藏内容-->
	<tiles:insertDefinition name="setSdAccount"/>

	<!--底部-->
	<%@ include file="/html/common/portal-common-foot.jsp" %>
</body>
 
</html>
