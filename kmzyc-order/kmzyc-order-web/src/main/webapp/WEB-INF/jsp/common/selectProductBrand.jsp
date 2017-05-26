<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>

<title>选择产品品牌</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
	.tableStyle1{font-size:12px;}
</style>
<SCRIPT LANGUAGE="JavaScript">

function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}
function save(){
	var o = $('input[name="selectedId"]:checked');
	try{
		parent.receiveProductPrand(o);
	}catch (e){
		alert('请父级页面实现receiveProductPrand(o)方法');
	}
}
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
$(document).ready(function(){
	if (art.dialog.data('selectedIds')) {
		var paramsIds = art.dialog.data('selectedIds');
		hanld(paramsIds);
	}
})
</SCRIPT>
</head>
<body>
<s:form action="/common/selectProductBrand.action" method="POST"  namespace='/common' id="frm" name='frm'>
<!-- 查询条件区域 -->
<table  width="98%" class="content_table"  height="100" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td>名称：<input name="brandName" class="input_style" type="text" value="<s:property value='model.brandName'/>">
		</td>
		<td align="right">英文名称：
		    <input name="engName" class="input_style" type="text" value="<s:property value='model.engName'/>">
		</td>
		<td align="right">中文拼音：
		     <input name="chnSpell" class="input_style" type="text" value="<s:property value='model.chnSpell'/>">
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<INPUT TYPE="button" onclick="doSearch()" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table"  cellpadding="3" align="center" cellspacing="0" border="1">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox' onclick='checkAll(this)'>
		</th>
		<th align="center" width="15%">名称</th>
		<th align="center" width="15%">国籍</th>
		<th align="center" width="10%">英文名称</th>
		<th align="center" width="10%">中文拼音</th>
		
	</tr>
	<s:iterator id="custiterator" value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox" name="selectedId" id='selectedId<s:property value="brandId"/>' title="<s:property value='brandName'/>" 
		     value='<s:property value="brandId"/>'>
		</td>
		<td align="center"><s:property value="brandName"/></td>
		<td align="center"><s:property value="nation"/></td>
		<td align="center">
			<s:property value="engName"/>
		</td>
		<td align="center">
			<s:property value="chnSpell"/>
		</td>
	</tr>
	</s:iterator>
</table>
<!-- 分页按钮区 -->
<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td>
			<%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
		</td>
	</tr>
	<tr>
			<td align="center"><INPUT class="saveBtn" TYPE="submit"
				value="" onclick="return save();">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
			<input type="button" class="backBtn" onClick="javascript:parent.myDialog.close();" />
			<td width="20%" align="center"></td>
		</tr>
</table><br/>
</s:form>
</BODY>
</HTML>