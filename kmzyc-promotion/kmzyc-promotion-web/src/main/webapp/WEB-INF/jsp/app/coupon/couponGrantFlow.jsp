<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<title>促销活动列表</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}

</style>
<script type="text/javascript">
function submitForm(){
	var grantId =  document.getElementById("couponGrantId").value;
	if(!/^[0-9]*$/.test(grantId)){
        alert("优惠券id只能输入数字!");
        return;
    }
	document.getElementById("queryForm").submit();
}


</script>
</head>
<s:set name="parent_name" value="'促销管理'" scope="request"/>
<s:set name="name" value="'优惠券流水管理'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form name="sectionsForm" method="post"
		action="/coupon/queryCouponGrantFlow.action" id = "queryForm">
		
		<!-- 标题条 -->
		
		
		<!-- 查询条件区域 -->
		<table width="98%" class="searcharea" align="center" cellpadding="0"
			cellspacing="0" style="margin: 20px;">
			<tr>
				<td>订单号：<input name="couponGrantFlow.orderCode" id="orderCode" type="text" 
					class="input_style" value="<s:property value='couponGrantFlow.orderCode' />"></td>
				
				<td>优惠券发放id：<input name="couponGrantFlow.couponGrantId" id="couponGrantId" type="text" 
					class="input_style" value="<s:property value='couponGrantFlow.couponGrantId' />"></td>
				<td align="right">操作类型：</td>
				<td><s:select name="couponGrantFlow.couponGrantFlowType"
						id="couponGrantFlowType" list="#request.CouponGrantFlowTypeMap" headerKey=""
						headerValue="---全部类型---">
					</s:select></td>
				<td align="right" colspan="1"><INPUT TYPE="button"  onclick="javascript:submitForm();" class="queryBtn"
					value=""></td>
				
			</tr>
			
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2" style="margin: 20px;">
			<tr>
				<th align="center" width="10%">优惠券名称</th>
				<th align="center" width="10%">订单号</th>
				<th align="center" width="15%">操作时间</th>
				<th align="center" width="10%">操作人</th>
				<th align="center" width="10%">用户</th>
				<th align="center" width="10%">操作类型</th>
				<th align="center" width="45%">备注</th>
				
			</tr>
			<s:iterator  value="page.dataList">
				<tr onMouseOver="this.style.backgroundColor='#def2fa'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'">
					
					<td align="center"><s:property value="couponName"/></td>
					<td align="center"><s:property value="orderCode"/></td>
					<td align="center"><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td align="center"><s:property value="operatingPersonName"/></td>
					<td align="center"><s:property value="customer"/></td>
					<td align="center"><s:property  value="#request.CouponGrantFlowTypeMap[couponGrantFlowType]" /> 
                    
                    </td>
					<td align="center"><s:property value="remark"/></td>
					
				</tr>
			</s:iterator>
		</table>

		<!-- 分页按钮区 -->
		<table width="98%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td><%@ include file="/WEB-INF/jsp/public/pagerList.jsp"%>
				</td>
			</tr>
		</table>
	</s:form>
</body>

</html>

