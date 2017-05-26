<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加仓库信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/warehouse/warehouse_validate.js"></script>
<script type="text/javascript" src="/etc/js/warehouse/warehouseInfo.js"></script>
<script src="/etc/js/97dater/WdatePicker.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<script language='JavaScript' src='/etc/js/dialog-common.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>

</head>
<body>

<s:set name="parent_name" value="'仓库管理'" scope="request"/>
<s:set name="name" value="'基础资料'" scope="request"/>
<s:set name="son_name" value="'仓库添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/warehouseAdd.action" method="POST" namespace='/app' id="wrm" name="wrm">
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
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>所在地区：</th>
		  <td width="80%">
		  	<s:select list="#request.areaList" id="areaId1" listKey="areaId" listValue="areaName" headerKey="" headerValue="--请选择省份--"
		  		onchange="choiceArea('areaId1','areaId2')"></s:select>
		  	<select id="areaId2" name="warehouseInfo.areaId" onchange="choiceCity();">
		    	<option value="">--请选择城市--</option>
            </select>
            <s:hidden name="warehouseInfo.areaName" id="areaName"/>
		  </td>
		</tr>
		
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>仓库名称：</th>
			<td><label> <s:textfield name="warehouseInfo.warehouseName"
				id="warehouseName" size="32" maxlength="32" onblur="checkWarehouseInfoName()"/> </label></td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>仓库编码：</th>
			<td><s:textfield name="warehouseInfo.warehouseNo" id="warehouseNo" size="32" maxlength="32" /> </td>
			
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>仓库覆盖区域：</th>
			<td>
			<s:textfield name="warehouseInfo.overlayArea" id="overlayArea" size="32" maxlength="512" readonly="true" />
			<s:hidden name="warehouseInfo.overlayAreaId" id="overlayAreaId" />
			<INPUT TYPE="button" class="button-2s" value="选择" onclick="popSelectArea()">
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">仓库状态：</th>
			<td><s:select list="#request.warehouseStatusMap" name="warehouseInfo.status" id="status"></s:select></td>
		</tr>
		<tr>
		  <th align="right" class="eidt_rowTitle">仓库描述：</th>
		  <td><label> <s:textarea name="warehouseInfo.depict" id="depict" rows="8" cols="45"/></label></td>
	  </tr>
		<tr>
			<th align="right" class="eidt_rowTitle">备注：</th>
			<td><label> <s:textarea name="warehouseInfo.remark" id="remark" rows="8" cols="45"/></label></td>
		</tr>
	</table>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center"><INPUT class="saveBtn" TYPE="submit"
				value="">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" class="backBtn" onClick="javascript:history.go(-1);" />
			<td width="20%" align="center"></td>
		</tr>
	</table>

	<br>
	<br>

</s:form>

</BODY>
</HTML>


