<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预备金审核详情</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/etc/css/lottery.css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
	function operate(operateId){
		if(operateId==4){
			location.href = "/userInfo/reserverApplyInfo_PageList.action";
		} else {
			document.getElementById("operateId").value=operateId;
			/* location.href = "/userInfo/reserverApplyInfo_submitApplyInfo.action";
			document.reserverApplyDetailForm.submit(); */
		}
	  }
	
	
	
	$().ready(function() {
		
		jQuery.validator.addMethod("cellphone1",function(value, element){
			var tel =/^(((13[0-9]{1})|(15[0-9]{1})|(14[0-9]{1})|(18[0-9]{1}))+\d{8})$/ ; 
			return this.optional(element) || (tel.test(value)); 
		},"请正确填写手机号码");
		
		
		
		$("#reForm").validate({
			rules: {
				"reserverApplyInfo.phone":{required: true,cellphone1:true},
				"reserverApplyInfo.description":{required: true,maxlength:500},
				"reserverApplyInfo.applyLimit":{required: true,number: true,maxlength:11},
				"reserverApplyInfo.auditingRemark":{required: true,maxlength:500},
				"reserverApplyInfo.settlementType":{required:true}
			},
			success: function (label){
	            label.removeClass("checked").addClass("checked");
	        },
			errorPlacement:function(error,element) {
				if (element.attr("type") == "radio"){
					error.insertAfter("#settlementTypeerror");
				} else {
					error.insertAfter(element);
				}
			} 
		 });
	});
</script>
</head>
<body>
<s:set name="parent_name" value="'预备金管理'" scope="request"/>
<s:set name="name" value="'预备金审核详情'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<!-- 查看预备金审核详情 -->
<s:form id="reForm" name="reserverApplyDetailForm"  action="/userInfo/reserverApplyInfo_submitApplyInfo.action" method="post">
<input type="hidden" name="reserverApplyInfo.applyNotesId" value="<s:property value='reserverApplyInfo.applyNotesId'/>"/>
<input type="hidden" name="reserverApplyInfo.loginId" value="<s:property value='reserverApplyInfo.loginId'/>"/>
<input type="hidden" name="reserverApplyInfo.reserveId" value="<s:property value='reserverApplyInfo.reserveId'/>"/>
<table width="90%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="edit_title">预备金审核详情</th>
	</tr>
	<tr>
	<td width="20%"  class="eidt_rowTitle" align="right">申请人账号:</td>
	<td width="80%"><s:property value="accountInfo.loginAccount"/></td>
	</tr>
	<tr>
	<td width="20%"  class="eidt_rowTitle" align="right">申请公司:</td>
	<td width="80%"><s:property value="reserverApplyInfo.corporateName"/></td>
	</tr>
	<tr>
	<td width="20%"  class="eidt_rowTitle" align="right">注册邮箱:</td>
	<td width="80%"><s:property value="accountInfo.email"/></td>
	</tr>
	<tr>
	<td width="20%"  class="eidt_rowTitle" align="right">注册手机号码:</td>
	<td width="80%"><s:property value="accountInfo.mobile"/></td>
	</tr>
	<tr>
	<td width="20%"  class="eidt_rowTitle" align="right">申请类型:</td>
	<td width="80%">
		<s:if test="reserverApplyInfo.applyType==1">
			开通
		</s:if> 
		<s:elseif test="reserverApplyInfo.applyType==2">
			变更
		</s:elseif>
	</td>
	</tr>
	<s:if test="%{reserverApplyInfo.applyType==2}">
		<tr>
			<td width="20%"  class="eidt_rowTitle" align="right">可用/总额度:</td>
			<td width="80%"><s:property value="%{formatDouble(reserverInfo.remainLimit)}"/>&nbsp;/&nbsp;<s:property value="%{formatDouble(reserverInfo.totalLimit)}"/></td>
		</tr>
		<tr>
		<td width="20%"  class="eidt_rowTitle" align="right">结算周期:</td>
		<td width="80%"><s:if test="%{settlementType==1}">月度结算</s:if>
			<s:elseif test="%{settlementType==2}" >季度结算</s:elseif>
			<s:elseif test="%{settlementType==2}">半年结算</s:elseif>
			<s:else>年度结算</s:else></td>
		</tr>
	</s:if>
	<tr>
	<td width="20%"  class="eidt_rowTitle" align="right">申请时间:</td>
	<td width="80%"><s:date name = "reserverApplyInfo.applyDate" format="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<td width="20%"  class="eidt_rowTitle" align="right">审核状态:</td>
		<td width="80%"><s:if test="%{reserverApplyInfo.status==1}">待审核
		</s:if> <s:elseif test="%{reserverApplyInfo.status==2}">审核通过</s:elseif>
		<s:elseif test="%{reserverApplyInfo.status==3}">审核通过</s:elseif>
		</td>
	</tr>
	<s:if test="editId==1">
		<tr>
		<td width="20%"  class="eidt_rowTitle" align="right">联系电话:</td>
		<td width="80%"><s:property value="reserverApplyInfo.phone"/></td>
		</tr>
		<tr>
			<td width="20%"  class="eidt_rowTitle" align="right">申请描述:</td>
			<td width="80%"><s:property value="reserverApplyInfo.description"/></td>
			</tr>
			<tr>
			<td width="20%"  class="eidt_rowTitle" align="right">申请额度:</td>
			<td width="80%"> ￥<s:property value="%{formatDouble(reserverApplyInfo.applyLimit)}"/></td>
			</tr>
			<tr>
			<td width="20%"  class="eidt_rowTitle" align="right">还款周期:</td>
			<td width="80%" ><s:radio disabled="true" name="reserverApplyInfo.settlementType" list="%{#{'1':'月度结算','2':'季度结算','3':'半年结算','4':'年度结算'}}" theme="simple" ></s:radio></td>
			</tr>
			<tr>
			<td width="20%"  class="eidt_rowTitle" align="right">审核备注:</td>
			<td width="80%"><s:property value="reserverApplyInfo.auditingRemark"/></td>
		</tr>
	</s:if>
	<s:else>
		<tr>
		<td width="20%"  class="eidt_rowTitle" align="right">还款周期:</td>
		<s:if test="%{reserverApplyInfo.applyType==2}">
			<td width="80%"><s:radio disabled="true" id="reserverApplyInfo.settlementType"  name="reserverApplyInfo.settlementType" list="%{#{'1':'月度结算','2':'季度结算','3':'半年结算','4':'年度结算'}}" theme="simple"></s:radio><lable id="settlementTypeerror"></lable></td>
		</s:if>
		<s:else>
			<td width="80%"><s:radio id="reserverApplyInfo.settlementType" name="reserverApplyInfo.settlementType" list="%{#{'1':'月度结算','2':'季度结算','3':'半年结算','4':'年度结算'}}" theme="simple"></s:radio><lable id="settlementTypeerror"></lable></td>
		</s:else>
		</tr>
		<tr>
		<td width="20%"  class="eidt_rowTitle" align="right">联系电话:</td>
		<td width="80%"><input id="reserverApplyInfo.phone" name="reserverApplyInfo.phone" type="text" value="<s:property value="reserverApplyInfo.phone"/>" maxlength="11">
		</td>
		</tr>
		<tr>
		<td width="20%"  class="eidt_rowTitle" align="right">申请描述:</td>
		<td width="80%"><textarea id="reserverApplyInfo.description" name="reserverApplyInfo.description" rows="70" cols="5"class="valid" maxlength="500"><s:property value="reserverApplyInfo.description"/></textarea></td>
		</tr>
		<tr>
		<td width="20%"  class="eidt_rowTitle" align="right">申请额度:</td>
		<td width="80%"><input id="reserverApplyInfo.applyLimit" name="reserverApplyInfo.applyLimit" type="text" value="<s:property value='%{formatDouble(reserverApplyInfo.applyLimit)}'/>" onKeyUp="this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3')" maxlength="11"></td>
		</tr>
		<tr>
		<td width="20%"  class="eidt_rowTitle" align="right">审核备注:</td>
		<td width="80%"> <textarea id="reserverApplyInfo.auditingRemark" name="reserverApplyInfo.auditingRemark" rows="70" cols="5" class="valid" maxlength="500"><s:property value="reserverApplyInfo.auditingRemark"/></textarea></td>
		</tr>
	</s:else>
	<tr> 
		<th colspan="2" align="center" class="edit_title"> 
		  <s:if test="editId==2">
		  		<input type="submit" style="height: 30px;" value="保存" onClick="operate(1)"/>
		  </s:if>
		  <s:elseif test="editId==3">
		  		 <input type="submit" class="btn-custom" value="审核通过" onClick="operate(2)"/>
			    <input type="submit" class="btn-custom" value="审核拒绝" onClick="operate(3)"/>
		  </s:elseif>
		  <input type="button" class="backBtn" value="" onClick="operate(4)"/>
	    </th>
	</tr>
</table>
<input type="hidden" id="operateId" name="operateId" value="">
</s:form>
<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</body>
</html>

