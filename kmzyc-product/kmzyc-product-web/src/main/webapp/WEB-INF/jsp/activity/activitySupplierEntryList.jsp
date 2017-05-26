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

<script type="text/javascript" src="/etc/js/activity/activitySupplierEntryList.js"></script>
</head>
<body>
<s:set name="parent_name" value="'报名管理'" scope="request" />
<s:set name="name" value="'已报名商家'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/supplierActivity/querySupplierEntryList.action" method="POST" id="frm" name='frm'>
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" height="100" align="center"
		cellpadding="0" cellspacing="0">
		<tr>
			<input type="hidden" name="activitySupplierEntry.activityId" value="<s:property value="activitySupplierEntry.activityId"/>" />
			<td align="right">公司名称：</td>
			<td align="left">
				<input name="activitySupplierEntry.companyShowName" cssClass="input_style" id="" />
			</td>
			<td align="right">店铺名称：</td>
			<td align="left">
				<input name="activitySupplierEntry.shopName" cssClass="input_style" id="" />
			</td>
			<td align="right">报名时间：</td>
			<td align="left">
				<input name="entryStartTime" type="text" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<s:property value='entryStartTime' />"  >
			至
				<input name="entryEndTime" type="text" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<s:property value='entryEndTime' />" >
			</td>
			<td>状态：&nbsp;
				<s:select list="#request.auditStatusMap"
					name="activitySupplierEntry.auditStatus" id="productStatus" headerKey=""
					headerValue="--全部状态--"></s:select>
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
			<th align="center" width="">公司名称</th>
			<th align="center" width="">店铺名称</th>
			<th align="center" width="">登录账户</th>
			<th align="center" width="">联系电话</th>
			<th align="center" width="">报名时间</th>
			<th align="center" width="">是否缴费</th>
			<th align="center" width="">状态</th>
			<th align="center">操作</th>
		</tr>
		<s:iterator id="activitySupplierEntry" value="page.dataList" status="activitySupplierEntryVo">
			<tr>
				<td align="center" width="">
					<s:property value="companyShowName" />
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
					<s:date name="entryTime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td align="center">
					<s:if test="activityPaymentStatus==2"  >
						是
					</s:if>
					<s:else>
						否
					</s:else>
				</td>
				<td align="center">
					<s:iterator value="#request.auditStatusMap"  >
					  <s:if test="auditStatus==key"  >
					  <s:property   value="value"   />  
					  </s:if>
					</s:iterator>
				</td>
				<%-- <td align="center">${activity:activityInfoDynamicStatus(activityVo)}</td> --%>
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
</html>
