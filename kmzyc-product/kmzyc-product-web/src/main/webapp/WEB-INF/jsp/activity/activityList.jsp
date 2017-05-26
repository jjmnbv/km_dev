<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="http://kmpro.km1818.com/functions" prefix="activity" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
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
<script type="text/javascript" src="/etc/js/activity/activityInfo.js"></script>
<script src="/etc/js/97dater/WdatePicker.js"></script>

<script language='JavaScript' src='/etc/js/dialog-common.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>

</head>
<body>
<s:set name="parent_name" value="'活动管理'" scope="request" />
<s:set name="name" value="'活动列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/supplierActivity/activityList.action" method="POST" id="frm" name='frm'>
	<input type="hidden" id="invitedSuppliers" value="" />
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" height="100" align="center"
		cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">活动ID：</td>
			<td align="left"><s:textfield name="activityInfo.activityId"
				cssClass="input_style" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="10"/></td>
			<td align="right">活动名称：</td>
			<td align="left"><s:textfield name="activityInfo.activityName"
				cssClass="input_style" id="" /></td>
			<td align="right">活动类型：</td>
			<td align="left">
				<s:select list="#request.activityTypeMap"
                    name="activityInfo.activityType" id="" headerKey=""
                    headerValue="--全部--"></s:select>
			</td>
            			<td align="right">活动状态：</td>
			<td align="left">
				<s:select list="#request.activityStatusMap"
                    name="activityInfo.activityStatus" id="" headerKey=""
                    headerValue="--全部--"></s:select>
			</td>
		</tr>
		<tr>
			<td align="right">报名时间：</td>
				<td align="left"><input name="entryStartTime" readonly
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					value="<s:property value='entryStartTime' />">&nbsp;至&nbsp;<input name="entryEndTime" readonly
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					value="<s:property value='entryEndTime' />">			
				
			</td>
			<td align="right">活动时间：</td>
			<td align="left"><input name="activityStartTime" readonly
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					value="<s:property value='activityStartTime' />">&nbsp;至&nbsp;<input name="activityEndTime" readonly
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					value="<s:property value='activityEndTime' />">			
				
			</td>
            			<td>
			<input TYPE="button" onClick="doSearch()" class="queryBtn" value="">
            <input class="addBtn" type="button" onClick="gotoAdd();">
			</td>
		</tr>
	</table>

	<!-- 数据列表区域 -->
	<table width="98%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C1C8D2">
		<tr>
			<th align="center" width="5%"><input type='checkbox' id='allbox'
				name='allbox' onclick='checkAll(this)'></th>
			<th align="center" width="">活动ID</th>
			<th align="center" width="">活动名称</th>
			<th align="center" width="">活动类型</th>
			<th align="center" width="">投放渠道</th>
			<th align="center" width="10%">活动费用</th>
			<th align="center" width="">报名起止时间</th>
			<th align="center" width="">活动起止时间</th>
			<th align="center">状态</th>
			<th align="center">操作</th>
		</tr>
		<s:iterator id="activityInfo" value="page.dataList" var="activityVo">
			<tr>
				<td align="center" width=""><input type="checkbox" name="activityIdChk" value='<s:property value="activityId" />'>
				</td>
				<td align="center">
					<s:property value="activityId"/>
				</td>
				<td align="center">
					<s:property value="activityName"/>
				</td>
				<td align="center"><s:property value="#request.activityTypeMap[activityType]" /></td>
				<td align="center"><s:property value="activityChannel" /></td>
				<td align="center">
					<s:if test="chargeType==1">
						免费
					</s:if>
					<s:if test="chargeType==2">
						<s:property value="activityCharge.fixedCharge" />
					</s:if>
					<s:elseif test="chargeType==3">
						按推广商品数量收费(<s:property value="activityCharge.singleCharge" />/SKU)
					</s:elseif>
					<s:elseif test="chargeType==4">
						按推广佣金比例收费(不低于活动价的<fmt:formatNumber value="${activityCharge.commissionRate*100}" pattern="#0.00" />%)
					</s:elseif>
				</td>
				<td align="center"><s:date name="entryStartTime" format="yyyy-MM-dd HH:mm:ss" />&nbsp;至&nbsp;<s:date name="entryEndTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td align="center"><s:date name="activityStartTime" format="yyyy-MM-dd HH:mm:ss" />&nbsp;至&nbsp;<s:date name="activityEndTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td align="center">${activity:activityInfoDynamicStatus(activityVo)}</td>
				<td align="center">
					<img title="查看" style="cursor: pointer;" src="/etc/images/little_icon/search.png"  onclick="toViewActivityInfo('<s:property value="activityId" />')" />
					<s:if test="activityStatus==0 || activityStatus==7">
						<img title="修改" style="cursor: pointer;" src="/etc/images/little_icon/xiugai.png"  onclick="toModifyActivity('<s:property value="activityId" />')" />
						<img title="提交审核" style="cursor: pointer;" src="/etc/images/little_icon/tijiao.png"  onclick="submitAuditActivity('<s:property value="activityId" />')" />
						<img title="删除" style="cursor: pointer;" src="/etc/images/little_icon/delete.png"  onclick="deleteActivity('<s:property value="activityId" />')" />
					</s:if>
					<s:if test="activityStatus==1">
						<img title="撤销提审" style="cursor: pointer;" src="/etc/images/little_icon/chehui.png"  onclick="cancelSubmitAuditActivity('<s:property value="activityId" />')" />
					</s:if>
					<c:if test="${activity:isCancelActivity(activityVo)}">
						<img title="撤销审核" style="cursor: pointer;" src="/etc/images/little_icon/chehui.png"  onclick="cancelAuditActivity('<s:property value="activityId" />')" />
					</c:if>
					<c:if test="${activity:isInviteSupplier(activityVo)}">
						<img title="邀请商家" style="cursor: pointer;" src="/etc/images/little_icon/add.png"  onclick="inviteSuppliers(<s:property value='activityId' />)" />
					</c:if>
					<s:hidden name='inviteSupplierCount'></s:hidden>
					<s:if test="inviteSupplierCount!=0">
						<img title="查看邀请商家" style="cursor: pointer;" src="/etc/images/button_new/bus.png"  onclick="listInviteSuppliers(<s:property value='activityId' />)" />
					</s:if>
					<c:if test="${activity:isStopActivity(activityVo)}">
						<img title="终止活动" style="cursor: pointer;" src="/etc/images/little_icon/tupian-15.png"  onclick="stopActivity('<s:property value="activityId" />')" />
					</c:if>
					
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
