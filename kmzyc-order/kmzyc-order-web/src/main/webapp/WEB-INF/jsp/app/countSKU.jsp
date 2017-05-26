<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出货量演示</title>
<script type="text/javascript" src="/etc/js/97dater/WdatePicker.js"></script>
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
<s:set name="son_name" value="'查询出货量'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<br/>
<form id="returnSearch" action="/app/ordercountSKUAction.action" method="POST">
<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
	<tr>		
		<th align="right">sku：</th>
        <td>
			<input type="text" name="SKU" value=""/>
			<!-- 
			<input type="hidden" name="SKU" value="-1"/>
			 -->
		</td>
		<th align="right">时间从：</th>
        <td><input type="text" name="begin" value='<s:property value="begin"/>' 
        id="begin" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'end\')}',maxDate:'2020-10-01'})"/></td>
		<th align="right">时间到：</th>
        <td><input type="text" name="end" value='<s:property value="end"/>' 
        id="end" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'begin\')}',maxDate:'2020-10-01'})"/></td>
	</tr>
	<tr>
		<td colspan="2"></td>
		<td colspan="2" algin="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" class="queryBtn" value="">
		</td>
		<td colspan="2"></td>
	</tr>
</table>

<br/>

<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
	<tr>
		<th>SKU编码</th>
		<th>出货量</th>
	</tr>
    <s:iterator value="skuMap.keySet()" id="id">
    <tr>
    	<td>
        	<s:property value="#id"/>
        </td>
        <td>
        	<s:property value="skuMap.get(#id)"/>
        </td>
    </tr>
    </s:iterator>
</table>	
</body>