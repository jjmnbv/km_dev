<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加安全问题答案</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>

 <script type="text/javascript">

 $().ready(function() {
 	
$("#bnesAnswerInfoAdd").validate({
	rules: { 
		"bnesAnswerInfo.accountLogin":{required: true,maxlength:70,unusualChar:true,checkSafeQuestion:true},
		"bnesAnswerInfo.answerContent":{required: true,maxlength:70,unusualChar:true}
		},
		success: function (label){
            label.removeClass("checked").addClass("checked");
        }
	});
	 
});

 jQuery.validator.addMethod("checkSafeQuestion", function(value, element) {
 	 	var rows = 0;
 	 	 var safeQuestionId=$("#safeQuestionId").val(); 
 	 	var id=$("#accountId").val(); 
 			$.ajax({
 				async:false,
 				url:"bnesAnswerInfo_checkSafeQuestion.action",
 				type:"POST",
 				data:"safeQuestion_Id=" +safeQuestionId+"&account_Id="+id,
 				dataType:"json",
 				success:function(json){
 						rows = json;
 				}
 			});
 			if(rows==0){
 			return true;
 			
 			}else{
 			 return false;
 	 		}	
	}, "此账户已存此安全问题,请不选择相同的");	
 //弹出层 选择账号
	function popUpAccount() {
	    dialog("选择账号","iframe:/accounts/accountInfo_popUpAccount.action" ,"900px","340px","iframe");
	}
	//关闭弹出窗口 
	function closeOpenAccount(accountId,account,name,amount,n_LoginId){
	    closeThis();
	    document.forms[0].loginId.value = n_LoginId;
	    document.forms[0].accountId.value = accountId;
		document.forms[0].accountLogin.value = account;
		$("#accountLogin").focus();
	}
	function aa(){
	$("#accountLogin").focus();
	}
</script>



</head>
<body>


	<!-- 导航栏 -->
	<s:set name="parent_name" value="'安全认证'" scope="request" />
	<s:set name="name" value="'安全问题答案'" scope="request" />
	<s:set name="son_name" value="'添加'" scope="request" />
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
    <div  style="height:90%;overflow-y:auto; " >
	<s:form id="bnesAnswerInfoAdd" action="/accounts/bnesAnswerInfo_add.action" method="POST"
		namespace='/question'>


		<!-- 数据编辑区域 -->
		<table width="60%" class="edit_table" cellpadding="3" cellspacing="0"
			border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">

			<tr>
				<td colspan="2" align="left" class="edit_title">安全问题答案信息</td>
			</tr>

			<tr>
				<td width="20%" align="right"><font color="red">*</font>账户号：</td>
				<td width="80%">
				 <input name="bnesAnswerInfo.accountLogin" type="text" id="accountLogin" readonly="readonly"/>
				 <input name="bnesAnswerInfo.accountId" type="hidden" id="accountId" />
				 <input name="bnesAnswerInfo.loginId" type="hidden" id="loginId" />
				 <input type="button"  value="选择" onclick="popUpAccount()" />
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="right">安全问题：</td>
				<td width="80%">
					<select name="bnesAnswerInfo.safeQuestionId" id="safeQuestionId" onchange="aa()">
						<s:iterator value="safeList" var="safe" id="safe">
							<option value="<s:property value='n_safe_question_id'/>"><s:property value="question_name"/></option>
						</s:iterator>
					</select>
				</td>
			</tr>

			<tr>
				<td width="20%" align="right"><font color="red">*</font>安全问题答案：</td>
				<td width="80%"><input name="bnesAnswerInfo.answerContent"
					type="text" id="answerContent" /></td>
			</tr>


		</table>


		<!-- 底部 按钮条 -->
		<table width="60%" class="edit_bottom" height="30" border="0"
			cellpadding="0" cellspacing="0">
			<tr>
				<td align="left"><input class="saveBtn" type="submit" value=" ">
					&nbsp;&nbsp; <input class="backBtn" onclick="gotoList()"
					type="button" value=" ">
				</td>
				<td width="20%" align="center"></td>
			</tr>
		</table>

		<br>
		<br>

	</s:form>
<SCRIPT LANGUAGE="JavaScript">
		function gotoList() {
			location.href = "/accounts/bnesAnswerInfo_pageList.action";
		}
	</SCRIPT>
	</div>
	<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</BODY>
</HTML>