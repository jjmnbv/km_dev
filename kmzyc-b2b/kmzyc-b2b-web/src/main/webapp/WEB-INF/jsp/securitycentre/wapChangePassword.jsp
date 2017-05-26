<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
 <jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
    	<jsp:param name="titlePrefix" value="修改登录密码"></jsp:param>
    </jsp:include><%-- 
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
      <jsp:param name="titlePrefix" value="修改登录密码"></jsp:param>
       <jsp:param name="isWap" value="1"></jsp:param>
    </jsp:include> --%>
</head>

<body class="l-w1000">
<!--头部LOGO及搜索-->
    <%--  <%@ include file="/html/common/wap/portal-head.jsp" %> --%>
  <jsp:include page="/html/common/wap/portal-head.jsp">
	   <jsp:param name="pageTitle" value="修改登录密码"></jsp:param>
</jsp:include> 
<!-- 修改密码 -->
    <tiles:insertDefinition name="wapChangePasswordContent"/>
 
<!--底部-->
   <%@ include file="/html/common/wap/portal-foot.jsp" %>  
</body>
</html>