<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>列表选择SKU码</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-1.8.3.js"></script>

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

<form action="findAllSkuProductByPurchase.action?type=stock"
	method="post" namespace='/app' id="frm" name='frm'>
	<s:hidden name="trId" value="%{trId}"></s:hidden>
	<s:hidden name="warehouseId" value="%{warehouseId}"></s:hidden>
	<br />
	<table width="90%" class="table_search" align="center" cellpadding="0"
		cellspacing="0" style="border-collapse: collapse; font-size: 12px">
		<tr>
			<td><input type="button" class="btngreen" value="保存所选 " style="height:30px"
				onclick="selectList();"></td>
		</tr>
		<tr>
			<td align="right">标题：</td>
			<td align="left">
			<s:textfield name="viewProductSku.productTitle" cssClass="input_style"
				id="productTitle" /></td>
			<td align="right">SKU编码：</td>
			<td align="left">
			<s:textfield cssClass="input_style" name="viewProductSku.productSkuCode"
				id="keyword" /></td>
			<td align="right">品牌：</td>
			<td align="left"><input type="text" id="autocomplete" value="<s:property value='viewProductSku.searchBrandName'/>" name="viewProductSku.searchBrandName" size="32" /></td>
		</tr>
		<tr>
			<td align="right">类别：</td>
			<td align="left" colspan="4">
				<s:select list="#request.categoryList"
				name="viewProductSku.bCategoryId" id="categoryId1" listKey="categoryId" listValue="categoryName"
				headerKey="" headerValue="--一级类目--"
				onchange="change2('categoryId1','categoryId2');"></s:select> 
				<s:select list="#request.mCategoryList"
				name="viewProductSku.mCategoryId" id="categoryId2"  listKey="categoryId" listValue="categoryName" 
				headerKey="" headerValue="--二级类目--"
				onchange="change2('categoryId2','categoryId3');"></s:select> 
				<s:select list="#request.sCategoryList" id="categoryId3" 
				headerKey="" headerValue="--三级类目--" 
				name="viewProductSku.categoryId"  listKey="categoryId" listValue="categoryName"></s:select>
			</td>
		</tr>
		<tr>
			<td>
				<INPUT TYPE="button" onClick="doSearch()" class="btngray" value=" 查询 ">
			</td>
		</tr>
	</table>
	<br />

	<!-- 数据列表区域 -->
	<table id="dataList" width="98%" align="center" cellpadding="3"
		cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc"
		style="border-collapse: collapse; font-size: 12px">
		<tr>
			<th bgcolor="#99ccff" align="center" width="5%"><input type='checkbox' id='allbox'
				name='allbox' onclick='checkAll(this)'></th>
			<th bgcolor="#99ccff" align="center" width="5%">序号</th>
			<th bgcolor="#99ccff" align="center" width="15%">标题</th>
			<th bgcolor="#99ccff" align="center" width="15%">SKU编码</th>
			<th bgcolor="#99ccff" align="center" width="15%">状态</th>
		</tr>
		<s:iterator id="productiterator" value="page.dataList" status="stuts">
			<tr>
				<td bgcolor="#FFFFCC" align="center">
				<input type="checkbox"
					name="checkSkuId">
					<div style= 'display:none'><s:property value="productSkuId"/>^<s:property value="productSkuCode" />^<s:property value="productId" />^<s:property value="trId" />^<s:property value="stockQuality" />^<s:property value="productTitle" />^<s:property value="productNo" /></div>
				</td>
				<td bgcolor="#FFFFCC" align="center" width="5%"><s:property value="#stuts.index+1" /></td>
				<td align="center" bgcolor="#FFFFCC" style="word-break: break-all"><s:property
					value="productTitle" escape="false" /></td>
				<td align="center" bgcolor="#FFFFCC" style="word-break: break-all"><s:property
					value="productSkuCode" /></td>
				<td align="center" bgcolor="#FFFFCC" style="word-break: break-all"
					id="<s:property value='productSkuId'/>"><s:property
					value="#request.productStatusMap[status]" /></td>
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
</form>


<script type="text/javascript">
	function doSearch() {
		document.getElementById('pageNo').value = 1;
		document.forms['frm'].submit();
	}

	function selectList(){
		var parent_productSkuIds = new Array();
		var obj = parent.document.getElementsByName("skuIds");
		var size = obj.length;
		for(var i=0;i<size;i++){
			parent_productSkuIds.push(obj[i].value.trim());
		}
		
		var sizeI = parent_productSkuIds.length;
		
		var params = new Array();
		
		$("input[type='checkbox'][name='checkSkuId']:checked").each(function(i){
			var str = $(this).parent().children("div").text().split('^');//$(this).val().split('^');
			var skuId = str[0].trim();
			//var productSkuCode = str[1];
			for(var i=0;i<sizeI;i++){
				if(parent_productSkuIds[i].trim()==skuId){
					//alert('SKU编号为：'+productSkuCode+'的产品重复，不能添加!');
					return;
				}
				
			}

			params.push(str);
		});
		parent.closeOpenSku(params);
	}
	
	function checkedBox(){
		var parent_productSkuIds = new Array();
		//var obj = parent.document.getElementsByName("productIds");
		var obj = parent.document.getElementsByName("skuIds");
		var size = obj.length;
		for(var i=0;i<size;i++){
			parent_productSkuIds.push(obj[i].value.trim());
		}
		
		var sizeI = parent_productSkuIds.length;
		
		$("input[type='checkbox']").each(function(i){
			var str = $(this).parent().children("div").text().split('^');//$(this).val().split('^');
			var skuId = str[0].trim();
			for(var i=0;i<sizeI;i++){
				if(parent_productSkuIds[i].trim()==skuId){
					$(this).attr("checked","checked");
				}
			}
		});
	}

	checkedBox();
</script>
</BODY>
</HTML>

