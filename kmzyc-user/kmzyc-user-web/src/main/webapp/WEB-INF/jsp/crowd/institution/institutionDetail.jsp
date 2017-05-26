<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改账户信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/dialog.js"></script>
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js"
	type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.validate.js"
	type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/messages_cn.js"
	type="text/javascript"></Script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
</head>
<body>

	<script type="text/javascript">
		
	</script>

	<s:form id="frm" action="/crowd/updateInstitutionInfo.action"
		method="POST">

		<!-- hidden properties -->
		<INPUT TYPE="hidden" name="institutionInfo.id"
			value="<s:property value='institutionInfo.id'/>">
		<INPUT TYPE="hidden" name="institutionInfo.loginId"
			value="<s:property value='institutionInfo.loginId'/>">
		<input type="hidden" id="edit" name="edit"
			value='<s:property value="edit"/>'>


		<!-- 导航栏 -->
		<s:set name="parent_name" value="'机构管理'" scope="request" />

		<s:set name="name" value="'机构信息查看'" scope="request" />



		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>


		<!-- 数据编辑区域 -->
		<table width="60%" class="edit_table" cellpadding="3" cellspacing="0"
			border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">

			<tr>
				<td colspan="2" align="left" class="edit_title">机构信息</td>
			</tr>
			<tr>
				<td width="20%" align="right">机构编码：</td>
				<td width="80%"><s:property
						value='institutionInfo.institutionCode' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">机构名称：</td>
				<td width="80%"><s:property escape="false"
						value='institutionInfo.institutionName' /></td>
			</tr>

			<tr>
				<td width="20%" align="right">机构地址：</td>
				<td width="80%"><s:property escape="false"
						value='institutionInfo.institutionAddress' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">机构联系人：</td>
				<td width="80%"><s:property escape="false"
						value='institutionInfo.institutionContactor' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">机构联系电话：</td>
				<td width="80%"><s:property
						value='institutionInfo.institutionPhonenumber' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">验证手机：</td>
				<td width="80%"><s:property value='institutionInfo.mobile' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">银行账户号：</td>
				<td width="80%"><s:property value='institutionInfo.bankAccount' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">开户行：</td>
				<td width="80%"><s:property value='institutionInfo.bankName' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">银行账户名：</td>
				<td width="80%"><s:property value='institutionInfo.bankUname' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">机构注册分利现金：</td>
				<td width="80%"><s:property
						value='institutionInfo.registRebate' /> 元/人</td>
			</tr>
			<tr>
				<td width="20%" align="right">推广有效期：</td>
				<td><s:date name="institutionInfo.spreadStartDate"
						format="yyyy-MM-dd HH:mm:ss" /> 至<s:date
						name="institutionInfo.spreadEndDate" format="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
			<tr>
				<td width="20%" align="right">引荐注册总人数：</td>
				<td width="80%"><s:property value="institutionInfo.referrerNum" /></td>
			</tr>
			<tr>
				<td width="20%" align="right">机构注册分利总现金：</td>
				<td width="80%"><s:property value="institutionInfo.referrerSum" /></td>
			</tr>
			<tr>
				<td width="20%" align="right">业务员：</td>
				<td width="80%"><s:property value='institutionInfo.bagmanName' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">创建日期：</td>
				<td width="80%"><s:date name="institutionInfo.createDate"
						format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td width="20%" align="right">认证图片：</td>
				<td width="80%"><s:iterator id="accountiterator"
						value="imageList" status="s">
						<img width="100" height="100"
							src="<s:property value='imgUrl' /><s:property value='imageUrl' />"
							style="cursor: pointer;" title=""
							id="src<s:property value='#s.index+1'/>">
						<br>
					</s:iterator></td>
			</tr>
			<tr>
				<td width="20%" align="right">审核人：</td>
				<td width="80%"><s:property value='institutionInfo.auditorName' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">审核日期：</td>
				<td width="80%"><s:date name="institutionInfo.auditeDate"
						format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td width="20%" align="right">审核备注：</td>
				<td width="80%"><s:property value='institutionInfo.auditeMemo' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">状态：</td>
				<td width="80%"><s:radio name="institutionInfo.status"
						disabled="true" id="status" list="#{1:'有效',0:'无效'}" /></td>
			</tr>
		</table>


		<!-- 底部 按钮条 -->
		<table width="60%" class="edit_bottom" height="30" border="0"
			cellpadding="0" cellspacing="0">
			<tr>
				<td align="left"><input class="backBtn" onclick="gotoList()"
					type="button" value=" "></td>
				<td width="20%" align="center"></td>
			</tr>
		</table>

		<br>
		<br>

	</s:form>

	<SCRIPT LANGUAGE="JavaScript">
		$("#src1").mouseover(function() {
			bigger("src1");
		});
		$("#src1").mouseleave(function() {
			smaller("src1");
		});
		$("#src2").mouseover(function() {
			bigger("src2");

		});
		$("#src2").mouseleave(function() {
			smaller("src2");
		});
		$("#src3").mouseover(function() {
			bigger("src3");

		});
		$("#src3").mouseleave(function() {
			smaller("src3");
		});
		function bigger(id) {

			document.getElementById(id).style.width = '400px';
			document.getElementById(id).style.height = '400px';
		}
		function smaller(id) {

			document.getElementById(id).style.width = '100px';
			document.getElementById(id).style.height = '100px';
		}
		function submitForm() {
			$("#frm").validate({
				rules : {
					"institutionInfo.institutionCode" : {
						required : true,
						maxlength : 80
					}

				},
				messages : {
					"institutionInfo.institutionCode" : '请输入合法的机构编码'
				},
				success : function(label) {
					label.removeClass("checked").addClass("checked");
				}
			});
			$("#frm").submit();
		}
		function gotoList() {
			location.href = "/crowd/queryInstitutionList.action";

		}
	</SCRIPT>
</BODY>
<!-- 消息提示页面 -->
<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>