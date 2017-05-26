<%@ page language="java"  import="com.kmzyc.search.facade.config.Configuration"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE HTML>
<html>
	<head>
	    <jsp:include page="/WEB-INF/jsp/common/b2bTemplate.jsp"></jsp:include>
	</head>
	<body>
          <%@ include file="/WEB-INF/jsp/common/b2bHeadTemplate.jsp" %>
		<!-- 面包屑 -->
		<tiles:insertDefinition name="b2bBreadcrumb"/>
		<!-- breadcrumb END -->
		<!-- 二级分类 -->
		<div class="l-w i-Search-page fn-clear">
			<div class="l-left">
				<div class="twocls-submenu">
					<!-- 类别列表 -->
					<tiles:insertDefinition name="b2bSecondCategoryList"/>
					<!-- 热门列表 -->
					<tiles:insertDefinition name="b2bSearchHotList"/>
				</div>
			</div>
			<div class="l-right">
				<!--相关搜索-->
<%-- 			  	<tiles:insertDefinition name="relevantSearch"/> --%>
                <!--商品筛选-->
			  	<tiles:insertDefinition name="b2bSearchFiltrate"/>
                <!-- 是不是想找 -->
                <tiles:insertDefinition name="b2bRelevantSearch"/>
                <!--搜索排序-->
				<tiles:insertDefinition name="b2bSearchSort"/>
				<!--产品列表-->
			    <c:choose>
				<c:when test="${productList == null || fn:length(productList) < 1}">
					<%@ include file="./template/notMatch.jsp" %>
			    </c:when>
				<c:otherwise>
					<tiles:insertDefinition name="b2bSearchProductList"/>
			    </c:otherwise>
				</c:choose>
				<!--产品分页-->
				<tiles:insertDefinition name="b2bSearchPagination"/>
			</div>
		</div>		
		<!-- oneclassify END -->	
										
		<!-- 底部导航链接 -->
			<%@ include file="/WEB-INF/jsp/common/b2bFootTemplate.jsp" %>  	
	</body>
</html>