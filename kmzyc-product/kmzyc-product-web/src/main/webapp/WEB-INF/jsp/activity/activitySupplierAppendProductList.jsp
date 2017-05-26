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
<s:set name="son_name" value="'追加推广明细'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/supplierActivity/findSupplierAppendProductList.action" method="POST" id="frm" name='frm'>
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" height="100" align="center"
		cellpadding="0" cellspacing="0">
		<s:hidden name="activitySkuParam.activityId" id="activityId"></s:hidden>
		<s:hidden name="activitySkuParam.supplierEntryId" id="supplierEntryId"></s:hidden>
		<s:hidden name="activitySkuParam.productSkuId" id="productSkuId"></s:hidden>
		<s:hidden name="activityType" id="activityType"></s:hidden>
		<tr align="left">
			<td align="left">商品名称：</td>
			<td align="left">
				<s:textfield name="activitySkuParam.productName" cssClass="input_style" id="" />
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
				<input type="button" class="backBtn" onclick="queryPromotionGoodsList(<s:property value='activitySkuParam.activityId' />,<s:property value='activitySkuParam.supplierEntryId' />,<s:property value='#request.activityType' />);"/>
			</td>
		</tr>
	</table>

	<!-- 数据列表区域 -->
	<table width="100%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C1C8D2">
		<tr>
			<th align="center" width="">商品名称</th>
			<th align="center" width="">品牌</th>
			<th align="center" width="">SKU编码</th>
			<th align="center" width="">单价</th>
			<th align="center" width="">活动价</th>
			<th align="center" width="">推广佣金(%)</th>
			<th align="center" width="">追加推广数量</th>
			<th align="center" width="">追加推广费用</th>
			<th align="center" width="">追加时间</th>
		</tr>
		<s:iterator id="activitSku" value="page.dataList" var="activitSkuVo">
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
					<s:property value="price"/>
				</td>
				<td align="center">
					<s:property value="activityPrice" />
				</td>
				
				<td align="center">
					<s:property value="commissionRate"/>% 
				</td>
				<td align="center">
					<s:property value="preSaleQuantity"/>
				</td>
				<td align="center">
					<s:property value="skuTotalPrice"/>
				</td>
				<td align="center">
					<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
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
