<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>新增预售</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script src="/etc/js/dialog.js"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>

<script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>

<script type="text/javascript"  src="/etc/js/presell/presellProductAdd.js"></script>
<style type="text/css">
.emDiv, .sbDiv {
	float: left;
	position: relative;
	margin: 3px 5px 2px 0;
	white-space: nowrap;
	height: 15px;
	line-height: 15px;
	cursor: pointer;
	border-radius: 17px;
	border-style: solid;
	border-width: 1px;
	font-size: 14px;
	padding: 2px 19px;
	border-color: #edb8b8;
	background-color: #ffeaea;
	color: #c30 !important;
	display: inline-block;
	vertical-align: middle;
}

em {
	margin-left: -8px;
	vertical-align: top;
	display: inline-block;
	font-style: normal;
	text-decoration: none;
	white-space: nowrap;
	line-height: 15px;
	cursor: pointer;
	font-size: 14px;
}
.aclose, .deleteP {
	position: absolute;
	right: -2px;
	top: -1px;
	text-decoration: none;
	font-family: verdana;
	border-radius: 0 17px 17px 0;
	font-weight: bold;
	padding: 2px 5px 2px 3px;
	border-width: 1px;
	border-style: solid;
	border-color: #edb8b8 !important;
	color: #c30 !important;
}
</style>
</head>
<body>

	<s:set name="parent_name" value="'预售管理 '" scope="request" />
	<s:set name="name" value="'预售商品管理'" scope="request"/>
	<s:set name="son_name" value="'新增'" scope="request"/>
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	<s:form action="/presell/savePresellProduct.action" method="POST" namespace='/presell' id="presellProductAddForm">
	<!--   隐藏数据域!-->
	 <input id="shopSort" type="hidden" name="promotionPresell.shopSort" > 
	 <input id="supplierId" type="hidden" name="promotionPresell.supplierId">
	<!-- 数据编辑区域 -->
	<table width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<tr>
			<th colspan="2" align="left" class="edit_title">新增预售</th>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font
				color="red">*</font>预售标题：</th>
			<td width="80%"><input type="text"
				name="promotionPresell.presellTitle" id="presellTitle" size="60"
				maxlength="60"
				value="<s:property value='promotionPresell.presellTitle' />"
				style="width: 400px" title="仅后台可见"
				onblur="showErrorMessage('presellTitle','预售标题不能为空','presellTitle')" />
				<input style="display: none" />
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>商家类别：</th>
			<td width="80%"><s:radio  
					list="#request.presellSupplierTypeMap" value="1" 
				    name="selectSupplierType" id="selectSupplierType" onchange="supplierTypeChange(this.value)"></s:radio>
				<input class="selectEm" type="button" id="selectEm"
				disabled="disabled" onClick="selectSupplier()"  value="选择">
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">所属商家：</th>
			<td width="80%" id="shopValues">
				<div id="showSuppliers">所有商家</div> 
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font
				color="red">*</font>添加预售商品：</th>
			<td width="80%" id="shopValues">
			<input class="selectEm" type="button" id="selectEm"
				 onclick="gotoAddProduct()" style="" value="选择">
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"></th>
			<td width="80%" id="shopValues">
				<table width="98%" class="list_table" align="center" cellpadding="3"
					cellspacing="0" border="1" bordercolor="#C1C8D2">
					<tr>
						<th align="center" width="15%">产品标题</th>
						<th align="center" width="15%">SKU</th>
						<th align="center" width="10%">品牌</th>
						<th align="center" width="10%">单价</th>
						<th align="center" width="10%">实际库存</th>
						<th align="center" width="10%">预售价</th>
						<th align="center" width="10%">定金</th>
						<th align="center" width="10%">预售库存</th>
					</tr>
					<tbody id="productContent">
		            </tbody>
				</table>
				<br>
				<span>单价≥预售价＞定金＞0，定金最高不得超过商品单价的20%.</span>
			</td>
		</tr>

	</table>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center"><input type="button" id="submitPresellPro" class="btn-custom" value="保存" onclick="submitPresell()"> &nbsp;&nbsp;&nbsp;&nbsp; <input
				type="button" class="backBtn" onClick="goBack()" />
			<td width="20%" align="center"></td>
		</tr>
	</table>

	<br>
	<br>
</s:form>
</body>

</html>


