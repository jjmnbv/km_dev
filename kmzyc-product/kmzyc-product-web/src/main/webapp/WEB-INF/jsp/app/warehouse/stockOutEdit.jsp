<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出库单管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">

<script src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/jquery.blockUI.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/dialog-common.js" type="text/javascript"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="/etc/js/warehouse/stockOut.js"></script>

</head>
<body>
<script type="text/javascript">
	$().ready(function() {
		 $("#stockOutDetailListAddfrm").validate();
	});
</script>

<s:set name="parent_name" value="'出库单管理'" scope="request" />
<s:set name="name" value="'出库单修改'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	
<s:form action="editStockOutDetail" method="post" 
 namespace='/app' id="stockOutDetailListEditfrm" name='stockOutDetailListEditfrm'>
	<s:hidden name="stockOut.stockOutId"></s:hidden>	
	<!-- 数据编辑区域 -->
	<table  width="95%" class="edit_table" align="center" cellpadding="3"
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
		  <td  align="left"   style="border-right-style:none"  ><font color="red">*</font>仓库
		 
		 
		  	<s:select list="#request.warehouseInfoStatusMap" onchange="clearProductInfo();" onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();" name="stockOut.warehouseId"  id="warehouseId1" headerKey="" headerValue="--请选择仓库--" reg="[^0]" tip="请选择"/>		  
		  </td>
		    <td  align="left"   style="border-left-style:none"           class="eidt_rowTitle"><font color="red">*</font>经手人:</th>
		  
		    <s:select list="#request.sysHandlerMap" onchange="changeUserName()"  name="stockOut.userId"  id="userId" headerKey="" headerValue="--请选择用户--" reg="[^0]" tip="请选择"/>
		  	<s:hidden name="stockOut.userName" id="userName"></s:hidden> 	  
		  </td>
		</tr>
			
			</table>
			
		<table id="dataTable" width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">	
			
					
		 <tr  style="display:none">  </tr>
			  
			  <tr style="display:none"> </tr>	
	<tr>
	   <th align="center"><input type='checkbox' id='allbox'
					name='allbox' onclick='checkAll(this)'></th>
				<th align="center" width="20%">产品SKU</th>
				<th align="center" width="15%">产品标题</th>
				<th align="center" width="10%">出库数量</th>
				<th align="center" width="10%">出库单价</th>
				<th align="center" width="15%" colspan='3'>备注:</th>
	</tr>
	<s:iterator value="stockOutDetailList"  status="stuts" > 
	
	<s:set var ="count" value="#stuts.index"/>
				<tr>
				    <td align="center" width="5%" > 
				 	<input type="checkbox" name="stockOutIdChk" value="<s:property value='detailId'/>" />	
				 	<input type="hidden" name="stockOutDetailList[<s:property value='#stuts.index'/>].str_productId" value="<s:property value='productId'/>"/>
			    	<input type="hidden" name="stockOutDetailList[<s:property value='#stuts.index'/>].str_skuId"  value="<s:property value='productSkuId'/>" />
			    	<input type="hidden" name="stockOutDetailList[<s:property value='#stuts.index'/>].productNo"  value="<s:property value='productNo'/>" />
			    	<input type="hidden" name="stockOutDetailList[<s:property value='#stuts.index'/>].productName"  value="<s:property value='productName'/>" />
			    	<input type="hidden" name="stockOutDetailList[<s:property value='#stuts.index'/>].productSkuValue"  value="<s:property value='productSkuValue'/>" />
				    <input type="hidden" value="<s:property value='detailId'/>" name="stockOutDetailList[<s:property value='#stuts.index'/>].detailId"/>
				</td>
				<td align="center">
					<s:property value='productSkuValue'/>
				</td>
				<td align="center">
					<s:property value='productName'/>
				</td>
				<td align="center">
					<input size="10" onkeyup="value=value.replace(/[^0-9]/g,'')" type="text" name="stockOutDetailList[<s:property value='#stuts.index'/>].str_quantity" reg="^((?!0)\d{1,6}|1000000)$"  tip="请输入1-1000000的整数" maxlength="7" value="<s:property value='quantity'/>"/>
				</td>
				<td align="center">
					<input size="10" type="text" name="stockOutDetailList[<s:property value='#stuts.index'/>].str_stockOutPrice" reg="^((?!0)[0-9]+\.?\d{0,6}|(0)\.{1}\d{1,6}|1000000)$"  tip="价格范围[1-1000000]" maxlength="7" value="<s:property value='price'/>"/>
				</td>	
				<td> 
					<textarea name="stockOutDetailList[<s:property value='#stuts.index'/>].remark" style="width: 180px; height: 23px;" cols="10" wrap="physical" maxlength="80" tip="备注不要超过80个汉字" ><s:property value='remark'/></textarea>
				</td>
				<input type="hidden" name="skuIds" value="<s:property value='productSkuId'/>" />
				</tr>
	</s:iterator>
</table>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center">
					<input type="hidden" id="count" value="${count+1}">
					<input class="btnStyle"  id="saveButton" type="button" value="保存" onclick="updateForm();"/>&nbsp;&nbsp;
					<input class="btnStyle" type="button" value="增加" onclick="popSelectProductSku();" />&nbsp;&nbsp;
					<input class="btnStyle"  type="button" value="删除" onclick="batchDeleteRows();"/>&nbsp;&nbsp;
					<input class="btnStyle" type="button" value="返回" onclick="gotoqueryStockOutList();"/>&nbsp;&nbsp;
				</td>
		</tr>
	</table>

	<br />
	<br />

</s:form>

</body>
</html>


