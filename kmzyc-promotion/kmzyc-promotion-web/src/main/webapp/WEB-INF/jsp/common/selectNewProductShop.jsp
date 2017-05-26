<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<title>选择产品品牌</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
	.tableStyle1{font-size:12px;}
</style>
</head>
<body>
<s:form action="/common/selectNewProductShop.action" method="POST"  namespace='/common' id="frm" name='frm'>
<s:hidden name="paramsIds" id="paramsIds"></s:hidden>
<input type="hidden" name="shopCodes" id="shopCodes" value="${shopCodes}"></input>
<table width="97%" class="table_search" align="center" cellpadding="0" cellspacing="0" style="border-collapse: collapse;font-size:12px" >
	<tr>
    	<td >
    	<INPUT TYPE="button" class="btn-custom btngreen" id="selectList" value="保存所选 " onClick="javascript:save();"></td>
    	<td>名称：<s:textfield name="record.corporateName" cssClass="input_style" id="corporateName"/>
		</td>
		<td><INPUT TYPE="submit" class="btn-custom btngray" value="查询 "></td>
	</tr>
</table>
<!-- 数据列表区域 -->
<table width="98%" class="list_table"  cellpadding="3" align="center" cellspacing="0" border="1">
	<tr>
	    <th align="center" width="5%">
            
		</th>
		<th align="center" width="15%">代码</th>
		<th align="center" width="15%">名称</th>
		<th align="center" width="15%">类型</th>
		
	</tr>
	<c:forEach var="obj" items="${page.dataList}"> 
	<tr>
	    <td align="center" width="5%">
		    <input type="radio"" name="selectedId" id="selectedId${obj.supplierId}" title="${obj.corporateName}"  value='${obj.supplierId}'>
		</td>
		<td align="center">${obj.supplierId} </td>
		<td align="center">${obj.corporateName} </td>
		<td align="center">${shopMap[obj.supplierType]}</td>
		
	</tr>
	</c:forEach>
</table>
<!-- 分页按钮区 -->
<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td>
			<%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
		</td>
	</tr>
</table><br/>
</s:form>
</BODY>
<SCRIPT LANGUAGE="JavaScript">

function save(){
	var o = $('input[name="selectedId"]:checked');
	try{
		parent.receiveProductShop(o);
	}catch (e){
		alert('请父级页面实现receiveProductShop(o)方法');
	}
}
$(document).ready(function(){
	var shopCodes = $("#shopCodes").val();
	if (shopCodes) {
		hanld(shopCodes);
	}
});

function hanld(paramsIds){
	var ids = paramsIds.split(",");
	for(var i=0;i<ids.length;i++){
		var id = ids[i];
		if(id){
			var markId='selectedId'+id;
			document.getElementById(markId).checked=true;
		}
	}
}

</SCRIPT>
</HTML>