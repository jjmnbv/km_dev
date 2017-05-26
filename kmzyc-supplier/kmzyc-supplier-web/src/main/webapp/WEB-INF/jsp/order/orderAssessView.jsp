<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template_page.jsp">
	<jsp:param name="titlePrefix" value="查看评价"></jsp:param>
</jsp:include>

<link href="${staticUrl}/resgys/style/default/base.css?version=${version}" rel="stylesheet" type="text/css">
<link href="${staticUrl}/resgys/style/default/tpl.css?version=${version}" rel="stylesheet" type="text/css">
<link href="${staticUrl}/resgys/style/default/common.css?version=${version}" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticUrl}/resgys/script/seajs/2.0.2/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticUrl}/resgys/script/config.js?version=${version}"></script>
<script type="text/javascript">
    seajs.use(['${staticUrl}/resgys/script/view/jsp/order/orderAssessView.js']);
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看评价</title>
</head>
<body>
<aa:zone name="centerZone">
	<span id="mainCenter" data-path="${templatePath}" data-css-path="${staticUrl}${cssBaseUrl}"></span>
	<tiles:insertDefinition name="orderAssessViewContent" />
</aa:zone>
</body>
</html>