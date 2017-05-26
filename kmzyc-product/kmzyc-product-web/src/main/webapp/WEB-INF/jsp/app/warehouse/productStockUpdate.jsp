<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改库存信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript" src="/etc/js/warehouse/productStock.js"></script>
<script type="text/javascript" src="/etc/js/warehouse/stock_validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
</head>
<body>

<s:set name="parent_name" value="'库存管理'" scope="request"/>
<s:set name="name" value="'基础资料'" scope="request"/>
<s:set name="son_name" value="'库存修改'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/stockUpdate.action" method="POST" namespace='/app' id="stockForm" name="stockForm" >
	<s:hidden name="stock.stockId"/>
	<s:hidden name="type" id="stockType"></s:hidden>
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
		    	<s:property value="#request.warehouseInfoMap[stock.warehouseId]"/><s:hidden name="stock.warehouseId"/>
		  	 </td>
		</tr>
		
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>产品SKU：</th>
			<td>
				<s:property value="stock.skuAttValue"/> 
				<s:hidden name="stock.skuAttValue"/>
				<s:hidden name="stock.skuAttributeId" id="skuAttributeId" />
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>商品名称：</th>
			<td>
				<s:property value="stock.productName"/><s:hidden name="stock.productName"/>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>商品编号：</th>
			<td>
				<s:property value="stock.productNo" /><s:hidden name="stock.productNo"/>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>库存数量：</th>
		  <td>
			<s:textfield name="stock.stockQuality" id="stockQuality" size="32" maxlength="32" /></td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>告警数量：</th>
			<td>
				<s:textfield name="stock.alarmQuality" id="alarmQuality" size="32" maxlength="32" />
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">订购数量：</th>
			<td>
				<input name="orderQuality" id="orderQuality" value='<s:property value="stock.orderQuality"/>' size="32" maxlength="512" disabled="disabled" style="background:#BEBEBE"/>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">增加/减少订购数量：</th>
			<td>
				<s:textfield name="stock.changeOrderQuatity" id="changeOrderQuatity" size="32" maxlength="32" />
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
			<td align="center"><INPUT class="saveBtn" TYPE="submit"
				value="">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" class="backBtn" onClick="gotoList()" />
			<td width="20%" align="center"></td>
		</tr>
	</table>

	<br>
	<br>
	
	<s:hidden name="checkedId" id="checkedId"/>
	<s:hidden name="stockForSelectPara.skuAttValue"/>
	<s:hidden name="stockForSelectPara.productName"/>
	<s:hidden name="stockForSelectPara.productNo"/>
	<s:hidden name="stockForSelectPara.warehouseId"/>
	<s:hidden name="stockForSelectPara.beginQuantity"/>
	<s:hidden name="stockForSelectPara.endQuantity"/>
	<s:hidden name="page.pageNo"/>

</s:form>

<s:form action="/app/stockShow.action" method="POST"  namespace='/app' id="stockShowForm">
	<s:hidden name="checkedId" id="checkedId"/>
	<s:hidden name="stockForSelectPara.skuAttValue"/>
	<s:hidden name="stockForSelectPara.productName"/>
	<s:hidden name="stockForSelectPara.productNo"/>
	<s:hidden name="stockForSelectPara.warehouseId"/>
	<s:hidden name="page.pageNo"/>
</s:form>
<s:form action="/app/stockShowAlarm.action" method="POST"  namespace='/app' id="stockShowAlarmForm">
	<s:hidden name="checkedId" id="checkedId"/>
	<s:hidden name="stockForSelectPara.skuAttValue"/>
	<s:hidden name="stockForSelectPara.productName"/>
	<s:hidden name="stockForSelectPara.productNo"/>
	<s:hidden name="stockForSelectPara.warehouseId"/>
	<s:hidden name="stockForSelectPara.beginQuantity"/>
	<s:hidden name="stockForSelectPara.endQuantity"/>
	<s:hidden name="page.pageNo"/>
</s:form>

</BODY>
</HTML>


