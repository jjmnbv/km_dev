<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>列表选择SKU码</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>

<script type="text/javascript"
	src="/etc/js/validate/easy_validator.pack.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="/etc/js/common.js"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css"    />
<script type="text/javascript" src="/etc/js/activity/brandDialog.js"></script>
<style type="text/css">
body {
	padding: 0px;
	margin: 0px;
}

table {
	margin-left: 10px;
}
</style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:form action="/supplierActivity/brandSelect.action"
	method="post" id="frm" name='frm'>
		<s:hidden name="checkedId" id="checkedId"/>
	<br />
	<table width="90%" class="table_search" align="center" cellpadding="0"
		cellspacing="0" style="border-collapse: collapse; font-size: 12px">
		<tr>
			<td>名称：<s:textfield type="text" name="prodBrand.brandName" cssClass="input_style" id="nation" /> </td>
			<td>
				<input TYPE="button" onClick="brandSearch()" class="btngray" value=" 查询 " />  
			</td>
			<td>
				<input type="button" class="btngreen" style="height:30px"value="保存所选 "	onclick="saveParentPage();" />
			</td>
		</tr>
	</table>
	<!-- 数据列表区域 -->
	<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
		<tr>
			<th  align="center" width="10%"><input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)' />全选</th>
			<th  align="center" width="30%">名称</th>
			<th  align="center" width="30%">国籍</th>
			<th  align="center" width="30%">英文名称</th>
			<th  align="center" width="30%">中文名称</th>
		</tr>
		<s:iterator id="mer" value="Page.dataList" status="stuts">
			<tr>
				<td align="center" width="10%">
					<input type="checkbox" name="supplierId" value='<s:property value="brandId" />,<s:property value="brandName" />' />
				</td>
				<td align="center" width="30%"><s:property value="brandName" /></td>
				<td align="center" width="30%"><s:property value="nation" /></td>
				<td align="center" width="30%"><s:property value="engName" /></td>
				<td align="center" width="30%"><s:property value="chnSpell" /></td>
			</tr>
		</s:iterator>
	</table>
	<table width="95%" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td><%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
			</td>
		</tr>
	</table>
	<br />
   <input type="hidden"  name="pageNum"  id="pageNum"   value="<s:property value='pageNum'/>"  />        
</s:form>   
</BODY>
<script type="text/javascript">
	checkedBox();
</script>
</HTML>

