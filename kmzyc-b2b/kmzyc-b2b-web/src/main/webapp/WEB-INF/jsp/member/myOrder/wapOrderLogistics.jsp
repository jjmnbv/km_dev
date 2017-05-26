<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
 <jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
        <jsp:param name="titlePrefix" value="wap订单跟踪"></jsp:param>
    </jsp:include>

    <%-- <jsp:include page="/WEB-INF/jsp/common/template.jsp">
      <jsp:param name="titlePrefix" value="我的订单"></jsp:param>
       <jsp:param name="isWap" value="1"></jsp:param>
    </jsp:include> --%>
</head>

<body class="l-w1000">
	<c:if test="${sessionScope.b2b_login_remark != '03'}">
    <jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="订单跟踪"></jsp:param>
	    </jsp:include> 
    <!--订单内容-->
    </c:if>
    <tiles:insertDefinition name="wapOrderLogisticsContent"/>

<!--底部-->
	<c:if test="${sessionScope.b2b_login_remark != '03'}">
   <%@ include file="/html/common/wap/portal-foot.jsp" %>  
   </c:if>
</body>
</html>
