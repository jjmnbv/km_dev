<%@page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配送单管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
	
<script src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript" src="/etc/js/warehouse/distributionInfo.js"></script>

</head>
<body>
<script type="text/javascript">
	$().ready(function() {
		 $("#distributionInfoAddfrm").validate();
	});
</script>

<s:set name="parent_name" value="'配送单管理'" scope="request" />
<s:set name="name" value="'配送单添加'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	
<s:form action="addDistributionInfo" method="post" 
 namespace='/app' id="distributionInfoAddfrm" name='distributionInfoAddfrm' >
	<s:token></s:token>
	<!-- 数据编辑区域 -->
	<table id="dataTable" width="97%" class="edit_table" align="center" cellpadding="0"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<!-- error message -->
		<s:if test="rtnMessage != null">
			<tr>
				<td colspan="9" align="left"><font color="red"><s:property
					value='rtnMessage' /></font></td>
			</tr>
		</s:if>
		
		<tr>
			<th colspan="2" align="right"><font color="red">*</font>物流单位:</th>
			<td>
		  		<s:select cssStyle="width:100%;" list="#request.logisticsCompanyMap"  
		  		name="distributionInfo.logisticsCompany"  id="logisticsCompany" headerKey="" headerValue="请选择物流"  reg="[^0]" tip="请选择"/>
		  	</td>
		  	<th  align="center">物流单号</th>
			<td >
		  		<input size="20" type="text"  name="distributionInfo.logisticsNo" reg="^(\S{0,20})$" maxlength="20"/>		  
		  	</td>
		  	
		  	<th  align="center">联系人电话</th>
			<td colspan="5">
		  		<input size="20" type="text"  name="distributionInfo.tel" reg="^(0\d{2,3}(\-)?\d{7,8}|(1(([35][0-9])|(47)|[8][01236789]))\d{8})$" tip="请输入手机或座机(如0755-)格式" maxlength="15"/>		  
		  	</td>
		  	
		</tr>
		<tr>
		  <th colspan="2" align="right"><font color="red">*</font>仓库</th>
		 
		  <td>
		  	<s:select cssStyle="width:100%;" list="#request.warehouseInfoStatusMap" onchange="clearProductInfo()" name="distributionInfo.warehouseId"  id="warehouseId1" headerKey="" headerValue="请选择仓库"  reg="[^0]" tip="请选择"/>
		  </td>
		    <th  align="center"><font color="red">*</font>收货人:</th>
		  <td>
		  	<input style="width:100%;" type="text"   name="distributionInfo.userName"  reg="^(\S{1,10})$" tip="请输入收货人" maxlength="10"/>		  
		  </td>
		    <th  align="center"><font color="red">*</font>收货地址:</th>
		  <td colspan="2">
		  	<input style="width:100%;" type="text" name="distributionInfo.deliveryAddress"  reg="^(\S{1,30})$"  tip="请输入配送地址" maxlength="30"/>		  
		  </td>
		  <td></td>
		</tr>
			
	<tr>
	    <th align="center" width="6%"> 
	    <input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)'>	
	    </th>
			<th  align="center" width="25%">产品SKU码</th>
			<th  align="center" width="15%">产品名称</th>
			<th  align="center" width="10%">产品编号</th>
			<th  align="center" width="8%">配送数量</th>
			<th  align="center" width="10%">配送单价</th>
			<th  align="center" width="10%">小计金额</th>
			<th  align="center" width="15%">备注</th>
	</tr>
	
	<tr id="distributionInfo3">
	    <td align="center" width="6%"> 
	    <input id="productId3" type="hidden" name="distributionDetailInfoList[0].productId"  />
	    <input id="productSkuId3" type="hidden" name="distributionDetailInfoList[0].productSkuId"  />
	    <input type="checkbox" name="distributionInfoChk" />
	    </td>
		<td align="center"><input size="20" id="skuId3"  type="text" name="distributionDetailInfoList[0].productSkuValue" onBlur="ValidateProductInfo(this,1);" reg ="^.+$" tip="请正确输入SKU码" maxlength="20"/><img title="查找"  src="/etc/images/view.png"  onclick="popSelectProductSku(this.parentNode.parentNode.id);" /></td>
		<td align="center"><input size="20" id="productName3" type="text" name="distributionDetailInfoList[0].productName" readonly/></td>
		<td align="center"><input size="10" id="productNo3" type="text" name="distributionDetailInfoList[0].productNo" readonly/></td>
		<td align="center"><input size="15" id="quantity3"  type="text" name="distributionDetailInfoList[0].quantity" onKeyUp="value=value.replace(/[^0-9]/g,'')"  onblur="checkProductStock(this);" reg="^((?!0)\d{1,7}|1000000)$"  tip="请输入1-1000000的整数" maxlength="7"/></td>
		<td align="center"><input size="10" id="price3" type="text" name="distributionDetailInfoList[0].price" onChange="totalsum(this);" reg='^((?!0)\\d{1,7}|1000000)$'  tip="价格范围[1-1000000]" maxlength="7"/></td>
		<td align="center"><input size="10" id="sumId3" type="text" name="distributionDetailInfoList[0].sum" reg='^((?!0)[0-9]+\.?\d{0,8}|(0)\.{1}\d{1,8})$'  tip="价格范围[1-99999999]"  readonly="readonly"/></td>
		<td align="center"> 
			<textarea id="remark3" name="distributionDetailInfoList[0].remark" style="width: 200px; height: 23px;" cols="10" wrap="physical"  reg="^(\S{0,100})$" tip="备注不要超过100个汉字" maxlength="200"></textarea> 
		</td>
	</tr>
	
</table>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center">
				<input  class="btnStyle" type="submit" style="height:30px" value="保存"/>&nbsp;&nbsp; 
				<input  class="btnStyle" type="button" style="height:30px" value="增加"  id="addRow" onClick="addRowsFun();" />&nbsp;&nbsp;	
				<input  class="btnStyle" type="button" style="height:30px" value="删除" onClick="delRowsFun();" id="delRow" />&nbsp;&nbsp;
				<input  class="btnStyle" type="reset" style="height:30px" value="取消" />&nbsp;&nbsp;
				<input  class="btnStyle" type="button" style="height:30px" value="返回" onClick="gotodistributionInfoList();" / >&nbsp;&nbsp;				
			</td>
		</tr>
	</table>

	<br />
	<br />

</s:form>

</body>
</html>


