<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
        <jsp:param name="titlePrefix" value="添加收货人"></jsp:param>
    </jsp:include>
</head>

<body class="l-w1000">
	<!--顶部登录条-->
  <%--   <%@ include file="/html/common/wap/portal-head.jsp" %>
 --%>
  <jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="修改收货人信息"></jsp:param>
	    </jsp:include> 
    <!--订单内容-->
    <tiles:insertDefinition name="editConsigneeInfoContent"/>

	<!--底部-->
   <%@ include file="/html/common/wap/portal-foot.jsp" %>  
</body>
</html>

