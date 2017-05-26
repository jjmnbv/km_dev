<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
       <jsp:param name="titlePrefix" value="收货地址"></jsp:param>
    </jsp:include>
</head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<body>
	<!--会员信息头部-->
    <%@ include file="/html/common/portal-common-head.jsp" %>
    
    <div class="i-page fn-clear">
		<!-- 会员信息左部 -->
		<tiles:insertDefinition name="memberCenterLeft"/>
		<!-- 收货地址信息 -->
		<tiles:insertDefinition name="deliveryAddressContent"/>
	</div>
	
	<!-- 通用尾部信息 -->
	<%@ include file="/html/common/portal-common-foot.jsp" %>
</body>
</html>