<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<script type="text/javascript" src="/etc/js/activity/activityPayment.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:set name="parent_name" value="'活动管理'" scope="request" />
<s:set name="name" value="'渠道推广费用管理'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/supplierActivity/queryActivityPromotionCost.action" method="POST" id="frm" name='frm'>
	<input type="hidden" name="activitySupplierEntry.supplierEntryId" value="<s:property value="activitySupplierEntry.supplierEntryId"/>" />
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" height="100" align="center"
		cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">公司名称：</td>
			<td align="left">
				<s:textfield name="activitySupplierEntry.companyShowName" cssClass="input_style"  />
			</td>
			<td align="right">登录账户：</td>
			<td align="left">
				<s:textfield name="activitySupplierEntry.loginAccount" cssClass="input_style"  />
			</td>
			<td align="right">活动名称：</td>
			<td align="left">
				<s:textfield name="activitySupplierEntry.activityInfo.activityName" cssClass="input_style" />
			</td>
			<td align="right">活动id：</td>
			<td align="left">
				<s:textfield name="activitySupplierEntry.activityId" cssClass="input_style" maxlength="10"
				onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
			</td>
			<td align="right">活动状态：</td>
			<td align="left">
				<select name="activitySupplierEntry.activityInfo.activityStatus" class="seletor">
					<option value="" <s:if test="activitySupplierEntry.activityInfo.activityStatus==null">selected="selected"</s:if>>--全部--</option>
					<option value="5" <s:if test="activitySupplierEntry.activityInfo.activityStatus==5">selected="selected"</s:if>>活动进行中</option>
					<option value="6" <s:if test="activitySupplierEntry.activityInfo.activityStatus==6">selected="selected"</s:if>>活动已结束</option>
					<option value="8" <s:if test="activitySupplierEntry.activityInfo.activityStatus==8">selected="selected"</s:if>>活动终止</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">退款状态：</td>
			<td align="left">
				<select name="activitySupplierEntry.activityPaymentStatus" class="seletor">
				<option value="" <s:if test="activitySupplierEntry.activityPaymentStatus==null">selected="selected"</s:if>>--全部--</option>
				<option value="3" <s:if test="activitySupplierEntry.activityPaymentStatus==3">selected="selected"</s:if>>待退款(无需退款)</option>
				<option value="4" <s:if test="activitySupplierEntry.activityPaymentStatus==4">selected="selected"</s:if>>已退款</option>
			</select>
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
			<th align="center" width="">活动id</th>
			<th align="center" width="">公司名称</th>
			<th align="center" width="">登录账户</th>
			<th align="center" width="">活动名称</th>
			<th align="center" width="">活动状态</th>
			<th align="center" width="">已缴活动费用(元)</th>
			<th align="center" width="">剩余活动费用(元)</th>
			<th align="center" width="">退款状态</th>
			<th align="center" width="">操作</th>
		</tr>
		<s:iterator id="activitySupplierEntry" value="page.dataList" status="activitySupplierEntryVo">
			<tr>
				<td align="center">
					<s:property value="activityId" />
				</td>
				<td align="center" width="">
					<s:property value="companyShowName" />
				</td>
				<td align="center">
					<s:property value="loginAccount" />
				</td>
				<td align="center">
					<s:property value="activityInfo.activityName" />
				</td>
				<td align="center">
					<s:property value="#request.activityStatusMap[activityInfo.activityStatus]" />
				</td>
				<td align="center">
					<s:property value="activityTotalPrice" />
				</td>
				<td align="center">
					<s:property value="residualActivityPrice" />
				</td>
				<td align="center">
					<s:property value="#request.activityPaymentStatusMap[activityPaymentStatus]" />
				</td>
				<td align="center">
					<img  style="cursor: pointer;" title="查看消费明细" src="/etc/images/button_new/view.png"
							onClick="queryActivitySupplierCostById(<s:property value='supplierEntryId'/>)">
					<s:if test="activityInfo.activityStatus==6 && activityPaymentStatus==3">
						<img style="cursor: pointer;" title="退款" src="/etc/images/little_icon/jiage.png"
							onClick="activityEndRefundment('<s:property value='activityId'/>','<s:property value='supplierEntryId'/>');">
					</s:if>
					<s:if test="activityInfo.activityStatus==8 && activityPaymentStatus==3">
						<img style="cursor: pointer;" title="退款" src="/etc/images/little_icon/jiage.png"
							onClick="activityCancleRefundment('<s:property value='activityId'/>','<s:property value='supplierEntryId'/>');">
					</s:if>
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
</s:form>
<script type="text/javascript">
	function queryActivitySupplierCostById(supplierEntryId){
		window.location.href="/supplierActivity/queryActivitySupplierCostDetail.action?activitySku.supplierEntryId="+supplierEntryId;
	}
</script>
</html>
