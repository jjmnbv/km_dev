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
	<jsp:param name="titlePrefix" value="商品仓库管理"></jsp:param>
</jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加商品库存</title>
</head>
<body>

<aa:zone name="centerZone">

	<span id="mainCenter" data-path="${templatePath}" data-css-path="${staticUrl}${cssBaseUrl}" data-url="${basePath}"></span>
	<tiles:insertDefinition name="productStockAddContent" />
</aa:zone>          
</body>
</html>