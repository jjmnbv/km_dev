<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>

    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
    	<jsp:param name="titlePrefix" value="余额明细"></jsp:param>
    </jsp:include>
</head>
<body>
	<!--会员信息头部-->
    <%@ include file="/html/common/portal-common-head.jsp" %> 
    
    <div class="i-page fn-clear">
		<!-- 会员信息左部 -->
		<tiles:insertDefinition name="memberCenterLeft"/>
		<!-- 充值明细 -->
		<tiles:insertDefinition name="rechargeDetailsContent"/>
	</div>
		
	<!-- 通用尾部信息 -->
    <%@ include file="/html/common/portal-common-foot.jsp" %>
</body>
</html>