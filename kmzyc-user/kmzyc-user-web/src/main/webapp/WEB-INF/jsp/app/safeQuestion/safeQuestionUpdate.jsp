<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改安全问题</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.validate.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/messages_cn.js" type="text/javascript"></Script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">

 $().ready(function() {
 	
$("#safeQuestionSaveUpdateQuestion").validate({
	rules: { 
		"safeQuestion.question_name":{required: true,maxlength:70,unusualChar:true}
		},
		success: function (label){
            label.removeClass("checked").addClass("checked");
        }
	});
	 
});
</script>

</head>
<body>

	<!-- 导航栏 -->
	<s:set name="parent_name" value="'安全认证'" scope="request" />
	<s:set name="name" value="'安全问题'" scope="request" />
	<s:set name="son_name" value="'修改'" scope="request" />
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>



	<s:form id="safeQuestionSaveUpdateQuestion" action="/accounts/safeQuestion_saveUpdateQuestion.action" method="post"
		name="updateForm" namespace="/question" >
		<s:token/>
		<s:hidden name="safeQuestion.n_safe_question_id" />

		<!-- 数据编辑区域 -->
		<table width="60%" class="edit_table" cellpadding="3" cellspacing="0"
			border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">


			<tr>
				<td colspan="2" align="left" class="edit_title">安全问题信息</td>
			</tr>

			<tr>
				<td width="20%" align="right"><font color="red">*</font>问题名称：</td>
				<td width="80%"><input id="safeQuestion"
					name="safeQuestion.question_name"
					value="<s:property value='safeQuestion.question_name'/>" />
				</td>
			</tr>


		</table>


		<!-- 底部 按钮条 -->
		<table width="60%" class="edit_bottom" height="30" border="0"
			cellpadding="0" cellspacing="0">
			<tr>
				<td align="left"><input class="saveBtn" type="submit" value=" ">
					&nbsp;&nbsp; <input class="backBtn" onclick="gotoList()"
					type="button" value=" "></td>
				<td width="20%" align="center"></td>
			</tr>
		</table>
		<br>
		<br>

	</s:form>

	<SCRIPT LANGUAGE="JavaScript">
		function gotoList() {
			location.href = "/accounts/safeQuestion_show.action";
		}
	</SCRIPT>
<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</BODY>
</HTML>