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
<s:set name="son_name" value="'销量统计'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/supplierActivity/findActivitySupplierSalesList.action" method="POST" id="frm" name='frm'>
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" align="center"
		cellpadding="0" cellspacing="0">
		<s:hidden name="supplierEntryParam.activityType" id="activityType"></s:hidden>
		<s:hidden name="supplierEntryParam.activityId" id="activityId"></s:hidden>
		<tr align="left">
			<td align="left">公司名称：</td>
			<td align="left">
				<s:textfield name="supplierEntryParam.companyShowName" cssClass="input_style" id="" />
			</td>
			<td align="right">店铺名称：</td>
			<td align="left">
				<s:textfield name="supplierEntryParam.shopName" cssClass="input_style" id="" />
			</td>
			<td align="right">登录账号：</td>
			<td align="left">
				<s:textfield name="supplierEntryParam.loginAccount" cssClass="input_style" id="" />
			</td>
			<td>
				<button type="submit" class="queryBtn"></button>
				<input type="button" class="backBtn" onClick="goBackActivityPromotionList()"/>
			</td>
		</tr>
	</table>

	<!-- 数据列表区域 -->
	<table width="98%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C1C8D2">
		<tr>
			<th align="center" width="">公司名称</th>
			<th align="center" width="">店铺名称</th>
			<th align="center" width="">登录账号</th>
			<th align="center" width="">联系电话</th>
			<th align="center" width="">已缴活动费用(元)</th>
			<th align="center" width="">总销量</th>
			<th align="center">操作</th>
		</tr>
		<s:iterator id="activitySupplierEntry" value="page.dataList" var="activitySupplierEntryVo">
			<tr>
				<td align="center">
					<s:property value="companyShowName"/>
				</td>
				<td align="center">
					<s:property value="shopName"/>
				</td>
				<td align="center">
					<s:property value="loginAccount"/>
				</td>
				<td align="center">
					<s:property value="mobile"/>
				</td>
				<td align="center">
					<s:if test="totalPayAmount==0.0">
						免费
					</s:if>
					<s:if test="totalPayAmount>0.0">
						<s:property value="totalPayAmount" />
					</s:if>
				</td>
				<td align="center">
					<s:property value="totalSalesQuantity" />
				</td>
				<td align="center">
					<img title="销量统计" style="cursor: pointer;"
						 src="/etc/images/view.png"
						 onclick="queryPromotionGoodsList(<s:property value='activityId' />,<s:property value='supplierEntryId' />,<s:property value='supplierEntryParam.activityType' />);" />
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
