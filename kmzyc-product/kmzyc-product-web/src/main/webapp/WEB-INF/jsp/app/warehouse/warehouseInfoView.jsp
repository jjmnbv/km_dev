<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看仓库信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js"
	type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="/etc/js/warehouse/warehouseInfo.js"></script>
</head>
<body>

<s:set name="parent_name" value="'仓库管理'" scope="request"/>
<s:set name="name" value="'基础资料'" scope="request"/>
<s:set name="son_name" value="'仓库查看'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/warehouseUpdate.action" method="POST" namespace='/app'
	onsubmit="">
	<s:hidden name="warehouseInfo.warehouseId"/>
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
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>所在地区：</th>
		  <td width="80%">
		  	${supperArea} ${warehouseInfo.areaName}
		  </td>
		</tr>
		
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>仓库名称：</th>
			<td><label> <s:property value="warehouseInfo.warehouseName" /></label></td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">仓库编码：</th>
			<td><s:property value="warehouseInfo.warehouseNo" /></td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>仓库覆盖区域：</th>
			<td>
			<s:property value="warehouseInfo.overlayArea" />
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">仓库状态：</th>
			<td><s:property value="#request.warehouseStatusMap[warehouseInfo.status]" /></td>
		</tr>
		<tr>
		  <th align="right" class="eidt_rowTitle">仓库描述：</th>
		  <td><label><s:property value="warehouseInfo.depict" /></label></td>
	  	</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">备注：</th>
			<td><label><s:property value="warehouseInfo.remark" /></label></td>
		</tr>
		<tr>
			<th colspan="2" align="left" class="edit_title">外部仓库对应信息</th>
		</tr>
		<tr>
		  <th align="right" class="eidt_rowTitle">外部系统名称：</th>
		  <td><s:property value="#request.systemCodeMap[warehouseInfo.warehouseRelation.systemCode]" /></td>
	  	</tr>
		<tr>
		  <th align="right" class="eidt_rowTitle">外部系统仓库编码：</th>
		  <td><s:property value="warehouseInfo.warehouseRelation.externalWarehouseCode" /></td>
	  	</tr>
		<tr>
		  <th align="right" class="eidt_rowTitle">仓库对应状态：</th>
		  <td><s:property value="#request.correspondingDataStatusMap[warehouseInfo.warehouseRelation.status]" /></td>
	  	</tr>
	</table>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center">
				<input type="button" class="backBtn" onClick="gotoListForView();" />
			</td>
		</tr>
	</table>

	<br>
	<br>

</s:form>

<s:form action="/app/warehouseShow.action" method="POST"  namespace='/app' id="warehouseListForm" name='warehouseListForm'>
	<s:hidden type="hidden" name="checkedId"/>
	<s:hidden name="warehouseForSelectPara.warehouseNo"/>
	<s:hidden name="warehouseForSelectPara.warehouseName"/>
	<s:hidden name="warehouseForSelectPara.status" id="warehouseStatus"/>
	<s:hidden name="warehouseForSelectPara.merchantCode"/>
	<s:hidden name="warehouseForSelectPara.pAreaId"/>
	<s:hidden name="warehouseForSelectPara.areaId"/>
	<s:hidden name="page.pageNo"/>
</s:form>
</BODY>
</HTML>


