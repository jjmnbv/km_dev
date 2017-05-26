<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
     <jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
    	<jsp:param name="titlePrefix" value="wap端我的优惠券"></jsp:param>
    </jsp:include>
</head>


<body class="l-w1000">
	<!--头部LOGO及搜索-->
    <jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="我的优惠券"></jsp:param>
	    </jsp:include> 
	  
		 
	<tiles:insertDefinition name="wapcouponListContent"/>
	 
	<!--底部-->
   <%@ include file="/html/common/wap/portal-foot.jsp" %>  
</body>
 

