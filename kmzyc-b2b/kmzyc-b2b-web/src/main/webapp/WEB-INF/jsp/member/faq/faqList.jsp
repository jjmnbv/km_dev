<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
    	<jsp:param name="titlePrefix" value="FAQ常见问题"></jsp:param>
    </jsp:include>
</head>


<body>
	<!--顶部登陆条-->
	<%@ include file="/html/common/help-head.jsp" %>
	
	<div class="help-con fn-clear">
		<%@ include file="/html/common/fnLeft.jsp" %>
		<!--购物导航-->
		<tiles:insertDefinition name="faqListContent"/>
	</div>
	
	<!--底部-->
	<%@ include file="/html/common/portal-common-foot.jsp" %>
</body>
</html>