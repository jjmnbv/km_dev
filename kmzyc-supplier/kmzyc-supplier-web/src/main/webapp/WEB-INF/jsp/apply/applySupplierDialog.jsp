<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template_apply.jsp">
        <jsp:param name="titlePrefix" value="供应商申请"></jsp:param>
    </jsp:include>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Insert title here</title>
</head>
<body>
<jsp:include page="/html/common/supplier-head.html"></jsp:include>
<!-- 供应商申请提示页面 已申请可撤销-->
<tiles:insertDefinition name="applySupplierDialogContent"/>
<jsp:include page="/html/common/supplier-foot.html"></jsp:include>
</body>
</html>