<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出库单管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"	type="text/css" />
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">

<script src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>
<script type="text/javascript" src="/etc/js/warehouse/stockOut.js"></script>


<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/jquery.blockUI.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/dialog-common.js" type="text/javascript"></script>


</head>
<body>
<script type="text/javascript">
	$().ready(function() {
		 $("#stockOutDetailListAddfrm").validate();
	});
</script>

<s:set name="parent_name" value="'出库单管理'" scope="request" />
<s:set name="name" value="'出库单添加'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	
<s:form action="addstockOutDetail" method="post" 
 namespace='/app' id="stockOutDetailListAddfrm" name='stockOutDetailListAddfrm' >
	<s:token/>  
	<!-- 数据编辑区域 -->
	<table  width="95%" class="edit_table" align="center" cellpadding="1"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;margin-bottom:-31px">
		<!-- error message -->
		<s:if test="rtnMessage != null">
			<tr>
				<td colspan="9" align="left"><font color="red"><s:property
					value='rtnMessage' /></font></td>
			</tr>
		</s:if>
		
		<tr>
			<th colspan="9" align="center" class="edit_title">出库细目单基本信息</th>
		</tr>
		<tr>
		  <td  align="left" style="border-right-style:none"><font color="red">*</font>仓库
		 
		  
		  	<s:select  list="#request.warehouseInfoStatusMap" name="stockOut.warehouseId"  id="warehouseId1" headerKey="" headerValue="请选择仓库" reg="[^0]" tip="请选择"/>
		  </td>
		    <td  align="left"   style="border-left-style:none"  class="eidt_rowTitle"><font color="red">*</font>经手人:
		 
		  	<s:select  list="#request.sysHandlerMap" onchange="changeUserName()" listKey="key" listValue="value" name="stockOut.userId"  id="userId" headerKey="" headerValue="请选择用户" reg="[^0]" tip="请选择"/>
		  	<s:hidden name="stockOut.userName"></s:hidden>
		  </td>
		</tr>
			
			
		</table>	
			
		<table id="dataTable" width="95%" class="edit_table" align="center" cellpadding="1"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">	
			
			
		 <tr  style="display:none">  </tr>
			  
			  <tr style="display:none"> </tr>	
			
	<tr>
	    <th align="center"><input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)'></th>
		<th align="center" width="25%">产品SKU</th>
		<th align="center" width="15%">产品标题</th>
		<th align="center" width="10%">出库数量</th>
		<th align="center" width="10%">出库单价</th>
		<th align="center" width="" colspan="3">备注:</th>
	</tr>
	
</table>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center">
					<input type="hidden" id="count" value="0">
					<input class="btnStyle"  id="saveButton" style="height: 30px;"  type="button" value="保存" onClick="submitForm();"/>&nbsp;&nbsp;
					<input class="btnStyle" type="button"  style="height: 30px;"  value="增加" onClick="popSelectProductSku();" />&nbsp;&nbsp;
					<input class="btnStyle"  type="button"  style="height: 30px;"  value="删除" onClick="batchDeleteRows();"/>&nbsp;&nbsp;
					<input class="btnStyle" type="button"  style="height: 30px;"  value="返回" onClick="gotoqueryStockOutList();"/>&nbsp;&nbsp;
				</td>
		</tr>
	</table>

	<br />
	<br />

</s:form>

</body>
</html>


