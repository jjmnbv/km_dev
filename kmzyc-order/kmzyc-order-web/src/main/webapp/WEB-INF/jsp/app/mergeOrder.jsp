<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单合并演示</title>
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
</head>
<body>
<h3>订单合并演示结果</h3>
	<s:iterator value="pageList">
		<s:property value="orderId" /><br/>
		<s:property value="orderCode" /><br/>
		<s:property value="customerAccount" /><br/>
		<s:property value="customerId" /><br/>
		<s:property value="orderStatues" /><br/>
		<s:property value="payDate" /><br/>
	</s:iterator>
</body>