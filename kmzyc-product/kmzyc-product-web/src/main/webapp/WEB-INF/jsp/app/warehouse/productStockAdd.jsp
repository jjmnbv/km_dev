<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加库存信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/warehouse/stock_validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/warehouse/productStock.js"></script>
<script src='/etc/js/dialog-common.js' type='text/javascript'></script>
<script src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>

</head>
<body>

<s:set name="parent_name" value="'库存管理'" scope="request"/>
<s:set name="name" value="'基础资料'" scope="request"/>
<s:set name="son_name" value="'库存添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/stockAdd.action" method="POST" namespace='/app' id="stockForm" name="stockForm" onsubmit="return ">
	<s:token></s:token>
	<!-- 数据编辑区域 -->
	<table width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<!-- error message -->
		<s:if test="rtnMessage != null">
			<tr>
				<td colspan="2" align="center"><font color="red"><s:property
					value='rtnMessage' /></font></td>
			</tr>
		</s:if>
		<tr>
			<th colspan="2" align="left" class="edit_title">基本信息</th>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>所属仓库：</th>
		    <td width="80%">
		  		<s:select list="#request.warehouseInfoStatusMap" id="warehouseId" name="stock.warehouseId" headerKey="" headerValue="--请选择仓库--"></s:select>
		  	 </td>
		</tr>
		
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>商品SKU：</th>
			<td>
				<s:textfield name="stock.skuAttValue" id="skuAttValue" size="32" maxlength="64" readonly="true"/> 
				<s:hidden name="stock.skuAttributeId" id="skuAttributeId" />
				<s:hidden name="stock.productId" id="productId" />
				<INPUT TYPE="button" class="button-2s" value="选择" onclick="popSelectProductSku()">
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>商品名称：</th>
			<td>
				<s:textfield name="stock.productName" id="productName" size="32" maxlength="128" readonly="true"/> 
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>商品编号：</th>
			<td>
				<s:textfield name="stock.productNo" id="productNo" size="32" maxlength="32" readonly="true"/> 
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>库存数量：</th>
		  <td>
			<s:textfield name="stock.stockQuality" id="stockQuality" size="32" maxlength="22" /></td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>告警数量：</th>
			<td>
				<s:textfield name="stock.alarmQuality" id="alarmQuality" size="32" maxlength="32" />
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">备注：</th>
			<td><label> <s:textarea name="stock.remark" id="remark" rows="8" cols="45"/></label></td>
		</tr>
	</table>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center"><INPUT class="saveBtn" TYPE="button"
				value="" onclick="checkSku();">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" class="backBtn" onClick="gotoList()" />
			<td width="20%" align="center"></td>
		</tr>
	</table>

	<br>
	<br>

</s:form>


<SCRIPT LANGUAGE="JavaScript">
	function gotoList() {
		location.href = "/app/stockShow.action";
	}
	
	function popSelectProductSku(){
		//dialog("查看所有SKU商品","iframe:/app/findAllSkuProduct.action?type=stock" ,"700px","420px","iframe",50);
		popDialog('/app/findAllSkuProduct.action?type=stock','查看所有SKU商品',900,470);
	}
	
	function closeOpenSku(skuAttributeId,skuAttValue,productId,productName,productNo){
	    closeThis();
	    document.forms[0].productId.value = productId;
	    document.forms[0].productName.value = productName;
	    document.forms[0].productNo.value = productNo;
	    document.forms[0].skuAttributeId.value = skuAttributeId;
		document.forms[0].skuAttValue.value = skuAttValue;
		//checkSku();
	}

</SCRIPT>

</BODY>
</HTML>


