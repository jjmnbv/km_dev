<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	   	<jsp:param name="titlePrefix" value="支付密码"></jsp:param>
</jsp:include>
</head>
<body>
	<!--头部LOGO及搜索-->
	<%@ include file="/html/common/portal-common-head.jsp"%>

	<div class="i-page fn-clear">
		<!--会员中心导航-->
		<tiles:insertDefinition name="memberCenterLeft" />
		<!--安全中心内容-->
		<tiles:insertDefinition name="goModifyPayPasswordContent" />
	</div>
	
	<!--底部-->
	<%@ include file="/html/common/portal-common-foot.jsp"%>
</body>
</html>