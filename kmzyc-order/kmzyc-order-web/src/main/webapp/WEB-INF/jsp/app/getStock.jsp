<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>调用产品系统查询库存演示</title>
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/etc/css/notifier-base.css">
<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="/etc/css/notifier-theme-plastic.css">

</head>
<body>
<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'调用接口测试'" scope="request"/>
<s:set name="son_name" value="'查询库存'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<br/>


<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
	<tr>
		<th>SKU编码</th>
		<th>仓库ID</th>
		<th>库存数</th>
	</tr>
	<s:iterator value="stockMap"  var="map">
	<tr>
		<td>
			<s:property value="#map.key"/>
		</td>
			<s:iterator value="#map.value" var ="ss" >	
				<td><s:property value="#ss.key"/></td>
				<td><s:property value="#ss.value"/></td>				
			</s:iterator>
	</tr>
	</s:iterator>
</table>	
</body>