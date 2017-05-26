<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
            <jsp:include page="/WEB-INF/jsp/common/template.jsp">
    	<jsp:param name="titlePrefix" value="我的评价"></jsp:param>
    </jsp:include>
</head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<body>
    <%@ include file="/html/common/portal-common-head.jsp" %> 
    
    <div class="i-page fn-clear">	
		<!--会员中心导航-->
		<tiles:insertDefinition name="memberCenterLeft"/> 
		<tiles:insertDefinition name="orderAssessContent"/> 
	</div>
	
	<!--底部-->
    <%@ include file="/html/common/portal-common-foot.jsp" %>
</body>
</html>

