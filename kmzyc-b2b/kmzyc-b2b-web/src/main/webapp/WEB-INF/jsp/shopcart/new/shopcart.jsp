<%@ page language="java" import="java.util.*,com.kmzyc.zkconfig.ConfigurationUtil"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="kh" uri="kh" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/" %>
<% String path = request.getContextPath();
    String basePath =
            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                    path + "/";
    String wapCssPath = ConfigurationUtil.getString("CSS_JS_PATH");%>
<!Doctype html>
<html>
<head>
    <META HTTP-EQUIV="robots" CONTENT="nofollow">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
    <META HTTP-EQUIV="Expires" CONTENT="0">
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="我的购物车"></jsp:param>
    </jsp:include>
    <link rel="shortcut icon" href="<%=wapCssPath%>/images/logos/favicon.png" type="image/x-icon"/>
</head>
<body>
<%@ include file="/html/common/portal-common-top.jsp" %>

<tiles:insertDefinition name="shopcartTop" flush="true"/>

<form action="<%=basePath%>cart/listShopCar.action" id="ProductForm" name="ProductForm"></form>
<aa:zone name="shopcartList">
    <c:if test="${empty shopList}">
        <tiles:insertDefinition name="shopcartEmpty" flush="true"/>
    </c:if>
    <c:if test="${not empty shopList}">
        <tiles:insertDefinition name="shopcartContent" flush="true"/>
    </c:if>
</aa:zone>


<%@ include file="/html/common/portal-common-foot.jsp" %>
<div class="tooltip top" style="display:none;height:2px;width:100px; z-index:201" id="errorDiv">
    <div class="tooltip-inner" id="errorMessage">
        我是提示信息
    </div>
    <div class="tooltip-arrow"><i></i></div>
</div>
<div id="displayLogin2"></div>
<div class="i-loading" id="i-loading" style="display:none;">
    <div class="load-pic"></div>
    <p>正在处理中，请稍候...</p>
</div>
<div style="position:absolute; top:0; left:0; height:2000px; width:1600px;
	z-index:1; display: none" id="mark"></div>
</body>

</html>
