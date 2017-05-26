<%@ page language="java"  import="java.io.*,com.kmzyc.zkconfig.ConfigurationUtil"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
  <head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="Keywords" content="" />
		<meta name="Description" content="" />
		<jsp:include page="/WEB-INF/jsp/common/template.jsp">
			<jsp:param name="titlePrefix" value="支付完成"></jsp:param>
		</jsp:include>
	</head>
	<body class="l-w1000">
		<%@ include file="/html/common/portal-common-top.jsp" %>  
		<div class="shopcart l-w">
		</div>
		<div class="l-w myfavorite"></div>
		<%@ include file="/html/common/portal-common-foot.jsp" %>
	</body>
</html>
