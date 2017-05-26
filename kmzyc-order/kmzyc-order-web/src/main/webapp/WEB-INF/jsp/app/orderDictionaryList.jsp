<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单字典</title>
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
</head>
<body>
	<s:iterator value="listOrderDictionary">
	<s:iterator value="orderItemMap" var="or">
			<s:iterator value="#or.value" >
						
			</s:iterator>
	</s:iterator>
		<s:property value="orderDictionaryId" /><br/>
		<s:property value="orderDictionaryType" /><br/>
		<s:property value="orderDictionaryKey" /><br/>
		<s:property value="orderDictionaryCode" /><br/>
		<s:property value="orderDictionaryValue" /><br/>
		<s:property value="orderDictionaryComment" /><br/>
	</s:iterator>
</body>