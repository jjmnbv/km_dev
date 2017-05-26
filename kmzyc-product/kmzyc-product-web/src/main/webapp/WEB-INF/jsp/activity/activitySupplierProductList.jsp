<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://kmpro.km1818.com/functions" prefix="activity" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/autocomplete/autocompletestyles.css" type="text/css" rel="stylesheet">
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
;
</style>
<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.mockjax.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/etc/autocomplete/demo.js"></script>

<script type="text/javascript" src="/etc/js/activity/activitySupplierProductList.js"></script>
</head>
<body>
<s:set name="parent_name" value="'报名管理'" scope="request" />
<s:set name="name" value="'已报活动商品'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/supplierActivity/querySupplierProductList.action" method="POST" id="frm" name='frm'>
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" height="100" align="center"
		cellpadding="0" cellspacing="0">
		<tr>
			<input type="hidden" name="activitySku.activityId" id="activityId" value="<s:property value="activitySku.activityId"/>" />
			<td align="right">公司名称：</td>
			<td align="left">
				<input name="activitySku.corporateName" id="corporateName" cssClass="input_style" value="<s:property value="activitySku.corporateName"/>" />
			</td>
			<td align="right">店铺名称：</td>
			<td align="left">
				<input name="activitySku.shopName" id="shopName" cssClass="input_style" value="<s:property value="activitySku.shopName"/>" />
			</td>
			<td align="right">商品标题：</td>
			<td align="left">
				<input name="activitySku.productTitle" id="productTitle" cssClass="input_style" value="<s:property value="activitySku.productTitle"/>"/>
			</td>
			<td align="right">SKU：</td>
			<td align="left">
				<input name="activitySku.productSkuCode" id="productSkuCode" cssClass="input_style" value="<s:property value="activitySku.productSkuCode"/>"/>
			</td>
			<td>
				<input TYPE="submit" class="queryBtn" value="">
			</td>
			<td>
				<input type="button" onclick="exportActivitySupplierProduct();" class="btnStyle" value="导出">
			</td>
		</tr>
	</table>
	<!-- 数据列表区域 -->
	<table width="98%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C1C8D2">
		<tr>
			<th align="center" width="">公司名称</th>
			<th align="center" width="">店铺名称</th>
			<th align="center" width="">登录账户</th>
			<th align="center" width="">联系电话</th>
			<th align="center" width="">商品标题</th>
			<th align="center" width="">SKU编码</th>
			<th align="center">操作</th>
		</tr>
		<s:iterator id="activitySupplierEntry" value="page.dataList" status="activitySupplierEntryVo">
			<tr>
				<td align="center" width="">
					<s:property value="corporateName" />
				</td>
				<td align="center">
					<s:property value="shopName" />
				</td>
				<td align="center">
					<s:property value="loginAccount" />
				</td>
				<td align="center">
					<s:property value="mobile" />
				</td>
				<td align="center">
					<s:property value="productTitle" />
				</td>
				<td align="center">
					<s:property value="productSkuCode" />
				</td>
				<td align="center">
					<img title="查看报名明细" style="cursor: pointer;"
					src="/etc/images/view.png"
					onclick="querySupplierEntryDeatil(<s:property value='activityId' />,<s:property value='supplierEntryId' />);" />
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
</s:form>
</body>
</html>
