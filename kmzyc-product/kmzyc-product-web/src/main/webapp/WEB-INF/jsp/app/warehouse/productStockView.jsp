<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看库存信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js"
	type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="/etc/js/warehouse/productStock.js"></script>
</head>
<body>

<s:set name="parent_name" value="'库存管理'" scope="request"/>
<s:set name="name" value="'基础资料'" scope="request"/>
<s:set name="son_name" value="'库存查看'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/stockUpdate.action" method="POST" namespace='/app'
	onsubmit="">
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
			<th width="20%" align="right" class="eidt_rowTitle">所属仓库：</th>
		    <td width="80%">
		  		<s:property value="#request.warehouseInfoMap[stock.warehouseId]" />
		  	 </td>
		</tr>
		
		<tr>
			<th align="right" class="eidt_rowTitle">产品SKU：</th>
			<td>
				<s:property value="stock.skuAttValue" />
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">商品名称：</th>
			<td>
				<s:property value="stock.productName" />
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">商品编号：</th>
			<td>
				<s:property value="stock.productNo" />
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">库存数量：</th>
		  <td>
		  	<s:property value="stock.stockQuality" />
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">告警数量：</th>
			<td>
				<s:property value="stock.alarmQuality" />
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">备注：</th>
			<td><label> <s:property value="stock.remark" /></label></td>
		</tr>
	</table>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center">
				<input type="button" class="backBtn" onClick="gotoList()" />
			</td>
			<td width="20%" align="center"></td>
		</tr>
	</table>

	<br>
	<br>

</s:form>

<s:form action="/app/stockShow.action" method="POST"  namespace='/app' id="stockShowForm" name='stockShowForm'>
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


