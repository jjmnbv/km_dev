<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="/WEB-INF/jsp/common/template_page.jsp">
	<jsp:param name="titlePrefix" value="退换货列表"></jsp:param>
</jsp:include>
<title>退换货列表</title>
</head>
<body>
<aa:zone name="centerZone">
	<span id="mainCenter" data-path="${templatePath}" data-css-path="${staticUrl}${cssBaseUrl}"></span>
	<!-- 退换货列表 -->
	<tiles:insertDefinition name="returnOderAuditContent" />
</aa:zone>
</body>
</html>