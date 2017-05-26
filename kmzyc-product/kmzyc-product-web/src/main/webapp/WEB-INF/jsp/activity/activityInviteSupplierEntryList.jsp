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
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:set name="parent_name" value="'活动中心'" scope="request" />
<s:set name="name" value="'已邀请商家'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/supplierActivity/queryInviteSupplierEntryList.action" method="POST" id="frm" name='frm'>
	<input type="hidden" name="activitySupplierEntry.activityId" value="<s:property value="activitySupplierEntry.activityId"/>" />
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" height="100" align="center"
		cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">公司名称：</td>
			<td align="left">
				<s:textfield name="activitySupplierEntry.companyShowName" cssClass="input_style" id="" />
			</td>
			<td align="right">店铺名称：</td>
			<td align="left">
				<s:textfield name="activitySupplierEntry.shopName" cssClass="input_style" id="" />
			</td>
			<td align="right">登录账户：</td>
			<td align="left">
				<s:textfield name="activitySupplierEntry.loginAccount" cssClass="input_style" id="" />
			</td>
			<td align="right">邀请时间：</td>
			<td align="left">
				<input name="activityStartTime" value="<s:property value='activityStartTime'/>" type="text" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" >
			至
				<input name="activityEndTime" value="<s:property value='activityEndTime'/>" type="text" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" >
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
			<th align="center" width="">邀请时间</th>
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
	<!-- 底部 按钮条 -->
	<table width="48%" align="left" class="edit_bottom" height="30"
			border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;float: left;clear: left;margin-top:30px;">
		<tr>
			<td align="center">
				<input type="button" class="backBtn" onclick="queryActivityList()" />
			</td>
			<td width="20%" align="center"></td>
		</tr>
	</table>
	<br>
</s:form>
<script type="text/javascript">
	function queryActivityList(){
		window.location.href="/supplierActivity/activityList.action";
	}
</script>
</html>
