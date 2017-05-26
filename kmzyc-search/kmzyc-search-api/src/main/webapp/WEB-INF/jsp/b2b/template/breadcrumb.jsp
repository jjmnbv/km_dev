<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${!empty productList && fn:length(productList) > 0}">
<div class="l-w i-breadcrumb">
	<c:forEach items="${breadMap }" var="item" varStatus="status">
		<c:choose>
			<c:when test="${status.index == 0}">
				<h1><a href='${item.key}' style="text-decoration:none">${item.value}</a></h1>
		    </c:when>
		    <c:otherwise>
				<span>&gt;<a style="text-decoration:none" href='${item.key}'>${item.value}</a></span>
		    </c:otherwise>
		</c:choose>
	</c:forEach>
    <c:if test="${keyword != null && keyword != ''}">
    	<span>&gt;<a href='javascript:void(0)' style="text-decoration:none">"${keyword}"</a></span>
    </c:if>
</div>
</c:if>