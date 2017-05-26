<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
	<head>
        <jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
    		<jsp:param name="titlePrefix" value="退换货记录"/>
    	 	<jsp:param name="isWap" value="1"></jsp:param>
    	</jsp:include>
	</head>
	<body>
		<jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="退换货记录"/>
	    </jsp:include> 
		<tiles:insertDefinition name="wapReturnRecords"/>	
   		<%@ include file="/html/common/wap/portal-foot.jsp" %>  
	</body>
</html>