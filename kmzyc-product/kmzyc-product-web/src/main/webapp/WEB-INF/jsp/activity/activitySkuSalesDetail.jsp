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
<s:set name="son_name" value="'销量明细'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/supplierActivity/findActivitySkuSalesDetail.action" method="POST" id="frm" name='frm'>
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" height="100px" align="center"
		cellpadding="0" cellspacing="0">
		<s:hidden name="skuId" id="skuId"></s:hidden>
		<s:hidden name="supplierEntryId" id="supplierEntryId"></s:hidden>
		<s:hidden name="activityType" id="activityType"></s:hidden>
		<s:hidden name="activityId" id="activityId"></s:hidden>
		<tr>
			<td align="left">订单号：
				<s:textfield name="orderCodeForSearch" cssClass="input_style" id="" />
			</td>
			<td style="position:absolute;left:400px;top:80px">订单状态：
                <s:select name="orderStatusForMenuQuery" list="#request.orderStatusMapForOrderQuery" listKey="key"
					listValue="value" headerKey="" headerValue="全部"
					style="width:110px;" id="orderStatusForMenuQuery"></s:select>
			</td>
			<td style="position:absolute;left:700px;top:75px">
				<button type="submit" class="queryBtn"></button>
			</td>
			<td>
				<input type="button" class="backBtn" onclick="queryPromotionGoodsList(<s:property value='activityId' />,<s:property value='supplierEntryId' />,<s:property value='activityType' />);"/>
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
			<th align="center" width="">单价</th>
			<th align="center" width="">数量</th>
			<th align="center" width="">订单号</th>
			<th align="center" width="">下单时间</th>
			<th align="center" width="">订单状态</th>
			<th align="center">操作</th>
		</tr>
		<s:iterator id="activitySkuOrderEntry" value="page.dataList" var="activitySkuOrderEntryVo" status="st">
			<tr>
				<%-- <s:iterator id="activitySkuOrderItem" value="page.dataList.get(#st.index).orderItemList" var="activitySkuOrderItemVo">
					
					<td align="center">
						<s:property value="commodityTitle"/>
					</td>
					<td align="center">
						<s:property value="brandName"/>
					</td>
					<td align="center">
						<s:property value="commoditySku"/>
					</td>
					<td align="center">
						<s:property value="commodityUnitPrice"/>
					</td>
					<td align="center">
						<s:property value="commodityNumber"/>
					</td>
				</s:iterator> --%>
				<td align="center">
					<s:property value="productTitle"/>
				</td>
				<td align="center">
					<s:property value="brandName"/>
				</td>
				<td align="center">
					<s:property value="skuCode"/>
				</td>
				<td align="center">
					<s:property value="price"/>
				</td>
				<td align="center">
					<s:property value="quantity"/>
				</td>
				<td align="center">
					<s:property value="orderCode"/>
				</td>
				<td align="center">
					<s:date name="orderCreateTime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td align="center">
					<s:property value="orderStatus" />
				</td>
				<td align="center">
					<!--  <img title="订单详情" style="cursor: pointer;" 
					src="/etc/images/view.png"/> -->
					<button class="btn btn-mini btn-success width66 j_view_order" disabled="disabled">订单详情</button>
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
