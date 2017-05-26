<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>类目属性管理</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<style type="text/css">
	.tableStyle1{font-size:12px;}
	.list_table tr {background-color: #EEF9F3;}
</style>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery-1.4.4.min.js"></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
<script language='JavaScript' src="/etc/js/dialog-common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:form action="/app/queryCategoryAttrList.action" method="POST"  id="frm" name='frm'>
<input type="hidden" name="categoryAttr.categoryId" value="<s:property value="categoryAttr.categoryId"/>"/>
<!-- 查询条件区域 -->
<table  width="98%" class="content_table"  height="36" align="center" cellpadding="0" cellspacing="0" >
	<tr> 
	    <td valign="middle" colspan="2">
            <INPUT class="addBtn" TYPE="button" value="" onclick="gotoAdd();">
			<input class="delBtn" type="button" value=""  onclick="deleteSelected('delId');">
		</td>
	</tr>
<!-- 
	<tr>
		<td width="300px">属性名称：<input name="categoryAttrName" class="input_style" type="text" value="">
		</td>
		<td width="300px">输入类型：<s:select style="width:120px" name="categoryAttr.inputType" list="#{0:'文本框',1:'单选框',2:'多选框',3:'下拉框'}" listKey="key" listValue="value" ></s:select>
		</td>
		<td>
			<INPUT TYPE="button" onclick="doSearch()" class="queryBtn" value="">
		</td>
	</tr>
 -->
</table>

<!-- 数据列表区域 -->
<table width="98%" class="list_table"  cellpadding="3" align="center" cellspacing="0" border="1">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox' onclick='checkAll(this)'>
		</th>
		<th align="center" width="10%">属性名称</th>
		<th align="center" width="10%">输入类型</th>
		<th align="center" width="10%">必填属性</th>
		<th align="center" width="10%">导航属性</th>
		<th align="center" width="10%">SKU属性</th>
		<th align="center" width="8%">排序</th>
		<th align="center" width="12%">操作</th>
	</tr>
	<s:iterator id="custiterator" value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox" name="delId"  value='<s:property value="categoryAttrId"/>'>
		</td>
		<td align="center"><s:property value="categoryAttrName"/></td>
		<td align="center">
		<s:if test="inputType == 0">文本框</s:if>
		<s:elseif test="inputType == 1">单选框</s:elseif>
		<s:elseif test="inputType == 2">多选框</s:elseif>
		<s:elseif test="inputType == 3">下拉框</s:elseif>
		</td>
		<td align="center">
		<s:if test="isReq == 1">是</s:if>
		<s:elseif test="isReq == 0">否</s:elseif>
		</td>
		<td align="center">
		<s:if test="isNav == 1">是</s:if>
		<s:elseif test="isNav == 0">否</s:elseif>
		</td>
		<td align="center">
		<s:if test="isSku == 1">是</s:if>
		<s:elseif test="isSku == 0">否</s:elseif>
		</td>
		<td align="center">
			<s:property value="sortno"/>
		</td>
		<td align="center">
			<img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="gotoUpdate(<s:property value='categoryAttrId'/>)" />
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
</table>

<br><br>


</s:form>
<SCRIPT LANGUAGE="JavaScript">
function closePopDiv(){
	closeThis();
	alert("保存完毕！");
	location.reload();
}

function gotoAdd(){
	popDialog('/app/showCategoryAttr.action?categoryAttr.categoryId=<s:property value="categoryAttr.categoryId"/>',"新增类目属性","500px","340px");
}

function gotoUpdate(categoryAttrId){
	popDialog('/app/showCategoryAttr.action?categoryAttr.categoryAttrId='+categoryAttrId,"修改类目属性","500px","340px");
}


function doDelete(name){
	document.forms['frm'].action='/app/deleteCategoryAttr.action';
	document.forms['frm'].submit();
}
function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}


</SCRIPT>

</BODY>
</HTML>