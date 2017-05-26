<%@page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>
<!DOCTYPE html>
<html>
	<head>
	 	<jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
	     	<jsp:param name="titlePrefix" value="购物车"></jsp:param>
	    </jsp:include>
	</head>
	<body>
		<c:if test="${sessionScope.b2b_login_remark != '03'}">
 		<jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="购物车"></jsp:param>
	    </jsp:include>
	    </c:if>
		<form action="/cart/listWapShopCar.action" id="modifProductForm" name="modifProductForm"></form>
		<aa:zone name="shopCarList">
			<c:if test="${empty shopList}">
				<tiles:insertDefinition name="wapShopCartEmpty" flush="true"/>
			</c:if>
			<c:if test="${not empty shopList}">
				<tiles:insertDefinition name="wapShopCartContent" flush="true"/>
			</c:if>
		</aa:zone>
		<c:if test="${sessionScope.b2b_login_remark != '03'}">
	    <%@ include file="/html/common/wap/portal-foot.jsp" %>
	    </c:if>
		<div class="loadingbox" id="loadingbox" style="display:none;width: 130px; z-index: 999999; position: fixed; border-radius: 10px; cursor: pointer; left: 50%; top: 30%; margin-left: -65px; background: rgba(238,238,238,1);">
			<div class="topPart" style="width: 100%; text-align: center; padding-top: 10px; padding-left: 35%;">
				<img src="<%=ConfigurationUtil.getString("cssAndJsPath") %>/reswap/images/new2/loading23.gif" style="max-width: 100%; padding: 0px;" />
			</div>
			<p class="font" style="text-align: center; font-size: 16px; color: rgb(102, 102, 102); margin: 5px 0px 10px;" id="tipsTxt">正在加载...</p>
		</div>
	</body>
</html>
