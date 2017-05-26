<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<title>机构全局设置</title>
</head>
<s:set name="parent_name" value="'机构管理'" scope="request" />
<s:set name="name" value="'全局变量'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form action="" id="globalSettingForm">
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="0" bordercolor="#C1C8D2">
			<tr>
				<td width="15%" style="text-align: right">机构功能：</td>
				<td style="text-align: left">&nbsp;<span style="color: red">*</span>&nbsp;&nbsp;
					<s:radio list="#{1:'开启&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',0:'关闭'}" name="globalVariable.isFunctionValid"></s:radio>
				</td>
			</tr>
			<tr>
				<td width="15%" style="text-align: right" name=isVerificationMode>手机注册验证方式：</td>
				<td style="text-align: left">&nbsp;<span style="color: red">*</span>&nbsp;&nbsp;
					<s:radio list="#{0:'短信验证',1:'语音验证'}" name="globalVariable.isVerificationMode"></s:radio>
				</td>
			</tr>
			<tr>
				<td width="15%" style="text-align: right">申请机构账号审核：</td>
				<td style="text-align: left">&nbsp;<span style="color: red">*</span>&nbsp;&nbsp;
					<s:radio list="#{1:'自动审核',0:'人工审核'}" name="globalVariable.isInstitutionAudit"></s:radio>
				</td>
			</tr>
			<tr>
				<td width="15%" style="text-align: right">机构推广有效期：</td>
				<td style="text-align: left">&nbsp;<span style="color: red">*</span>&nbsp;&nbsp;
					<input type="text" name="globalVariable.validDate"  class="number"
					required="required" maxlength="5"
					value="<s:property value='globalVariable.validDate'/>" />月
				</td>
			</tr>
			<tr>
				<td width="15%" style="text-align: right">机构注册分利现金：</td>
				<td style="text-align: left">&nbsp;<span style="color: red">*</span>&nbsp;&nbsp;
					<input type="text" name="globalVariable.clearingAmount" class="number" maxlength="5"
					value="<s:property value='globalVariable.clearingAmount'/>" />元/人
				</td>
			</tr>
			<tr style="border: 0px">
				<td></td>
				<td><input type="button" class="saveBtn"
					style="text-align: left" id="subBtn" /></td>
			</tr>
		</table>
	</s:form>
</body>
<script type="text/javascript">

	function validateForm() {
		$("#globalSettingForm").validate({
			rules : {
				"globalVariable.validDate" : {
					required : true,
					number:true,
					digits:true,
					min:1
				},
				"globalVariable.clearingAmount" : {
					required : true,
					number:true,
					min:0.01
				}
			},
			messages : {
				"globalVariable.validDate" : {
					required : "请输入机构推广有效期",
					number : "机构推广有效期只能为数字",
					digits:"只能输入整数",
					min : "请输入大于0的值"
				},
				"globalVariable.clearingAmount" : {
					required : "请输入机构注册分利现金",
					number : "机构注册分利现金只能为数字",
					min : "请输入大于0的值"
				}
			},
			success : function(label) {
				label.removeClass("checked").addClass("checked");
			}
		})
	}

	
	
	
	function ajaxSubmit() {
		$.ajax({
			type : 'post',
			dataType : 'json',
			url : "/crowd/updateGlobalVariable.action",
			data : $("#globalSettingForm").serialize(),
			async : false,
			success : function(data) {
				var code = data.code;
				if (code == 0) {
					alert("保存成功");
					window.location.href = window.location.href;
				} else {
					alert(data.module);
					return;
				}
			}
		})
	}

	$(function() {
		validateForm();
		$("#subBtn").click(function() {
			if ($("#globalSettingForm").valid()) {
				ajaxSubmit();
			}
		});
	})
</script>

</html>