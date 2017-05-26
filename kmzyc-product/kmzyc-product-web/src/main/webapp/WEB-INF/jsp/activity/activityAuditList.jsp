<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动中心</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/autocomplete/autocompletestyles.css" type="text/css" rel="stylesheet">
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
</style>

<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<Script type="text/javascript" src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="/etc/autocomplete/jquery.mockjax.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/etc/autocomplete/demo.js"></script>

</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:set name="parent_name" value="'活动管理'" scope="request" />
<s:set name="name" value="'活动信息审核'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/supplierActivity/activityAuditList.action" method="POST" namespace='/supplierActivity"' id="frm" name='frm'>
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" align="center"
		cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">活动ID：</td>
			<td align="left"><s:textfield name="activityInfo.activityId" maxlength="10"
				cssClass="input_style" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/></td>
			<td align="right">活动名称：</td>
			<td align="left"><s:textfield name="activityInfo.activityName"
				cssClass="input_style" id="" /></td>
			<td align="right">活动类型：</td>
			<td align="left"><s:select list="#request.activityTypeMap" 
				name="activityInfo.activityType" id="activityType" headerKey="" headerValue="全部"></s:select></td>
			<td align="right">报名时间：</td>
			<td><input name="entryStartTimeSearch"
				type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
				value="<s:property value='entryStartTimeSearch' />">&nbsp;到&nbsp;<input name="entryEndTimeSearch"
				type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
				value="<s:property value='entryEndTimeSearch' />"></td>
		</tr>
		<tr>
			<td align="right">活动时间：</td>
			<td><input name="activityStartTimeSearch"
				type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
				value="<s:property value='activityStartTimeSearch'/>">&nbsp;到&nbsp;<input name="activityEndTimeSearch"
				type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
				value="<s:property value='activityEndTimeSearch' />"></td>
			<td align="right">状态：</td>
			<td align="left">
				<select class="seletor" disabled="disabled">
				<option selected="selected" disabled="disabled">待审核</option>
				</select>
			</td>
			<td><input TYPE="button" onClick="doSearch()" class="queryBtn" value=""></td>
		</tr>
		</tr>
	</table>
	
	<!-- 数据列表区域 -->
	<table width="98%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C1C8D2">
		<tr>
			<th align="center" width="">活动ID</th>
			<th align="center" width="">活动名称</th>
			<th align="center" width="">活动类型</th>
			<th align="center" width="">活动费用</th>
			<th align="center" width="">报名起止时间</th>
			<th align="center" width="">活动起止时间</th>
			<th align="center">状态</th>
			<th align="center">操作</th>
		</tr>
		<s:iterator id="activityInfo" value="page.dataList">
			<tr>
				<td align="center"><s:property value="activityId"/></td>
				<td align="center"><s:property value="activityName"/></td>
				<td align="center"><s:property value="#request.activityTypeMap[activityType]"/></td>
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
				<td align="center">从<s:date name="entryStartTime" format="yyyy-MM-dd HH:mm:ss" /><br>到
					<s:date name="entryEndTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<input type="hidden"  id="entryStartTime_<s:property value='activityId'/>" value="<s:property value='entryStartTime'/>"/>	
					<input type="hidden"  id="entryEndTime_<s:property value='activityId'/>" value="<s:property value='entryEndTime'/>"/>	
				<td align="center">从<s:date name="activityStartTime" format="yyyy-MM-dd HH:mm:ss" /><br>到
					<s:date name="activityEndTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<input type="hidden"  id="activityStartTime_<s:property value='activityId'/>" value="<s:property value='activityStartTime'/>"/>
					<input type="hidden"  id="activityEndTime_<s:property value='activityId'/>" value="<s:property value='activityEndTime'/>"/>
				<td align="center"><s:property value="#request.auditStatusMap[auditStatus]"/></td>
				<td>
					<s:if test="auditStatus==0">
						<img title="审核通过" style="cursor: pointer;" src="/etc/images/button_new/submit.png"  onclick="auditActivityById(<s:property value='activityId'/>)" />&nbsp;&nbsp;
					</s:if>
					<img TYPE="button"  style="cursor: pointer;" title="查看活动信息" src="/etc/images/view.png"
							onClick="queryActivityInfoById(<s:property value='activityId'/>)">
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

<script type="text/javascript">
	function doSearch(){
		$('#frm').attr("action","/supplierActivity/activityAuditList.action");
		$('#pageNo').val(1);
		document.forms['frm'].submit();
	}

	function queryActivityInfoById(activityId){
		window.location.href="/supplierActivity/findActivityInfoById.action?activityInfo.activityId="+activityId+"&pageType=activityAuditPage";
	}
	
	function auditActivityById(activityId){
		if(confirm('确定审核通过?')){
			$.ajax({
				dataType:'json',
				url:'/supplierActivity/auditActivityById.action',
				data:{'activityInfo.activityId':activityId,
					'activityInfo.entryStartTime': $('#entryStartTime_'+activityId).val(),
					'activityInfo.entryEndTime':$('#entryEndTime_'+activityId).val(),
					'activityInfo.activityStartTime':$('#activityStartTime_'+activityId).val(),
					'activityInfo.activityEndTime':$('#activityEndTime_'+activityId).val() },
				success:function(data){
					alert(data.msg);
					if(data.result==false){
						return;
					}else{
						doSearch();
					}
				},
				error:function(){
					alert('请求失败，请稍后重试或与管理员联系！');
				}
			});
		}
	}
	
</script>

</body>
</html>
