<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/autocomplete/autocompletestyles.css" type="text/css" rel="stylesheet">
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
;
</style>
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<Script type="text/javascript" src="/etc/js/97dater/WdatePicker.js"></Script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:set name="parent_name" value="'活动管理 '" scope="request" />
<s:set name="name" value="'推广费用管理'" scope="request" />
<s:set name="son_name" value="'销售明细'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/supplierActivity/queryActivitySupplierCostDetail.action" method="POST" id="frm" name='frm'>
	<input type="hidden" name="activitySku.supplierEntryId" value="<s:property value="activitySku.supplierEntryId"/>" />
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" align="center"
		cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">商品标题：</td>
			<td align="left">
				<s:textfield name="activitySku.productTitle" cssClass="input_style" id="" />
			</td>
			<td align="right">SKU编码：</td>
			<td align="left">
				<s:textfield name="activitySku.productSkuCode" cssClass="input_style" id="" />
			</td>
			<td>
				<input TYPE="submit" class="queryBtn" value="">
			</td>
		</tr>
	</table>
	<!-- 数据列表区域 -->
	<table width="98%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C1C8D2">
		<tr>
			<th align="center" width="">商品标题</th>
			<th align="center" width="">SKU编码</th>
			<th align="center" width="">商品售价</th>
			<th align="center" width="">活动价</th>
			<th align="center" width="">预销数量</th>
			<th align="center" width="">推广佣金(%)</th>
			<th align="center" width="">已销数量</th>
			<th align="center" width="">剩余活动费用</th>
		</tr>
		<s:iterator id="activitySku" value="page.dataList" >
			<tr>
				<td align="center" width="">
					<pre><s:property value="productTitle" /></pre>
				</td>
				<td align="center">
					<s:property value="productSkuCode" />
				</td>
				<td align="center">
					<fmt:formatNumber value="${price}" pattern="#0.00" />
				</td>
				<td align="center">
					<s:property value="activityPrice" />
				</td>
				<td align="center">
					<s:property value="preSaleQuantity" />
				</td>
				<td align="center">
					<s:property value="commissionRate" />%
				</td>
				<td align="center">
					<s:property value="saleQuantity" />
				</td>
				<td align="center">
					<s:property value="residualSkuPrice" />
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
	<!-- 底部 按钮条 -->
	<table width="48%" align="left" class="edit_bottom" height="30"
			border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;float: left;clear: left;margin-top:30px;">
		<tr>
			<td align="center">
				<input type="button" class="backBtn" onClick="queryActivityPromotionCost()" />
			</td>
			<td width="20%" align="center"></td>
		</tr>
	</table>
	<br>
</s:form>
<script type="text/javascript">
	function queryActivityPromotionCost(){
		window.location.href="/supplierActivity/queryActivityPromotionCost.action";
	}
</script>

</html>
