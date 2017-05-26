<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://kmpro.km1818.com/functions" prefix="activity" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/autocomplete/autocompletestyles.css" type="text/css" rel="stylesheet">
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
</style>

<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/activity/activityPromotionEffect.js"></script>
<script src="/etc/js/97dater/WdatePicker.js"></script>

</head>
<body>
<s:set name="parent_name" value="'活动管理'" scope="request" />
<s:set name="name" value="'活动推广效果'" scope="request" />
<s:set name="son_name" value="'查看推广商品'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/supplierActivity/findPromotionActivitySupplierSales.action" method="POST" id="frm" name='frm'>
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" height="100" align="center"
		cellpadding="0" cellspacing="0">
		<s:hidden name="activitySkuParam.activityId" id="activityId"></s:hidden>
		<s:hidden name="activitySkuParam.supplierEntryId" id="supplierEntryId"></s:hidden>
		<s:hidden name="activityType" id="activityType"></s:hidden>
		<tr align="left">
			<td align="left">商品标题：</td>
			<td align="left">
				<s:textfield name="activitySkuParam.productName" cssClass="input_style" id="" />
			</td>
			<td align="right">品牌：</td>
			<td align="left">
				<s:textfield name="activitySkuParam.brandName" cssClass="input_style" id="" />
			</td>
			<td align="right">SKU编码：</td>
			<td align="left">
				<s:textfield name="activitySkuParam.productSkuCode" cssClass="input_style" id="" />
			</td>
		</tr>
		<tr>
			<td>
				<button type="submit" class="queryBtn"></button>
			</td>
			<td>
				<input type="button" class="backBtn" onclick="goBackSupplierSalesList(<s:property value='activitySkuParam.activityId' />,<s:property value='activityType' />)"/>
			</td>
		</tr>
	</table>

	<!-- 数据列表区域 -->
	<table width="100%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C1C8D2">
		<tr>
			<th align="center" width="">商品标题</th>
			<th align="center" width="">品牌</th>
			<th align="center" width="">SKU编码</th>
			<th align="center" width="">已销数量</th>
			<th align="center">操作</th>
		</tr>
		<s:iterator id="activitySku" value="page.dataList" var="activitySkuVo">
			<tr>
				<td align="center">
					<s:property value="productName"/>
				</td>
				<td align="center">
					<s:property value="brandName"/>
				</td>
				<td align="center">
					<s:property value="productSkuCode"/>
				</td>
				<td align="center">
					<s:property value="saleQuantity"/>
				</td>
				<td align="center">
					<img title="查看销售明细" style="cursor: pointer;"
					 	 src="/etc/images/view.png"
						 onclick="querySkuSalesDetail(<s:property value='activitySkuParam.activityId' />,<s:property value='supplierEntryId' />,
						 <s:property value='productSkuId' />,
						 <s:property value='activityType' />)" />
				</td>
			</tr>
		</s:iterator>
	</table>

	<!-- 分页按钮区 -->
	<table width="98%" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td><%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
			</td>
		</tr>
	</table>

	<br>
	<br>


</s:form>

</html>
