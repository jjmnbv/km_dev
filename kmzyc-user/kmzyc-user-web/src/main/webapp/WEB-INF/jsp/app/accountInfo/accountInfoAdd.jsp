<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加账户信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/dialog.js"></script>
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.validate.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/messages_cn.js" type="text/javascript"></Script>

</head>
<body>


 <script type="text/javascript">
 var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子   
 var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值.10代表X
$().ready(function() {

	jQuery.validator.addMethod("checkAccountLogin", function(value, element) {
 	 	var rows = 0;
 			$.ajax({
 				async:false,
 				url:"accountInfo_checkAccountLogin.action",
 				type:"POST",
 				data:"accountLogin=" + value,
 				dataType:"json",
 				success:function(json){
 						rows = json;
 				}
 			});
 			if(rows==1){
 				return false;
 			}else{
 	 			return true;
 	 		}
 			
	}, "重复的账户号");

	jQuery.validator.addMethod("checkAcconutId", function(value, element) {
 		var a_idCard = value.split("");    
 	    var sum = 0; // 声明加权求和变量   
 	    
 	   if(a_idCard.length!=18){
	        return false;
		 }
 		 
 	    if (a_idCard[17].toLowerCase() == 'x') {   
 	        a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作   
 	    }
 	    for ( var i = 0; i < 17; i++) {   
 	        sum += Wi[i] * a_idCard[i];// 加权求和   
 	    }   
 	    valCodePosition = sum % 11;// 得到验证码所位置   
 	    if (a_idCard[17] == ValideCode[valCodePosition]) {
 	        return true;   
 	    } else {
 	        return false;   
 	    }   
 			
	}, "身份证号码格式不正确");
	
 	
	
$("#accountInfoAdd").validate({
	rules: { 
		"accountLogin":{required: true,checkAccountLogin:true,rangelength:[6,20]},
		"paymentpwd":{required: true,rangelength:[6,16]},
		"reg_paymentpwd":{required: true,equalTo:"#paymentpwd"},
		"name":{required: true,userName:true,maxlength:16},
		"acconutId":{required:true,checkAcconutId:true},
		"mobile":{required: true,cellphone:true},
		"email":{required: true,email:true,maxlength:30},
		"address":{maxlength:85}
		},
		success: function (label){
            label.removeClass("checked").addClass("checked");
        }
	});
	 
});
 
//弹出 选择账号层
function popUpUserInfo() {
    dialog("选择会员账号","iframe:/logininfo/logininfo_queryPageBasicUserInfo.action?callBack=closeOpenUserInfo" ,"900px","460px","iframe");
}
//关闭弹出窗口 
function closeOpenUserInfo(accountId,account,name){
    closeThis();
    $("#loginAccount").val(account);
     $("#n_LoginId").val(accountId);
}

</script>
 
<s:form id="accountInfoAdd" action="accountInfo_add.action" method="POST"  namespace='/accountInfo' >

<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">


<!-- 导航栏 -->
<s:set name="parent_name" value="'账户管理'" scope="request"/>
<s:set name="name" value="'账户信息'" scope="request"/>
<s:set name="son_name" value="'添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>


<!-- 数据编辑区域 -->
<table width="60%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<td colspan="2" align="left" class="edit_title">账户信息</td>
	</tr>
   
   <tr> 
		<td width="20%" align="right"><font color="red">*</font>账户号：</td>
		<td width="80%"> 
			<input name="accountLogin" id="accountLogin" type="text" value=""/>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>登录号：</td>
		<td width="80%"> 
			<input type="hidden" name="n_LoginId" id="n_LoginId"  value="<s:property value='n_LoginId'/>">
			<input type="text" readonly="readonly" name="loginAccount" id="loginAccount" value="<s:property value='loginAccount'/>">
		  	<input type="button"  value="选中" onclick="popUpUserInfo()"/>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>支付密码：</td>
		<td width="80%"> 
			<input name="paymentpwd" id="paymentpwd" type="password" />
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>确认密码：</td>
		<td width="80%"> 
			<input name="reg_paymentpwd" id="reg_paymentpwd" type="password" />
		</td>
	</tr>
	
    <tr> 
		<td width="20%" align="right"><font color="red">*</font>账户真实姓名：</td>
		<td width="80%"> 
			<input name="name" id="name" type="text" />
		</td>
	</tr>
    <tr> 
		<td width="20%" align="right"><font color="red">*</font>身份证号码：</td>
		<td width="80%"> 
			<input name="acconutId" id="acconutId" type="text" />
		</td>
	</tr>
     <tr> 
		<td width="20%" align="right"><font color="red">*</font>手机号码：</td>
		<td width="80%"> 
			<input name="mobile" id="mobile" type="text" />
		</td>
	</tr>
	 <!--删除邮件业务 <tr> 
		<td width="20%" align="right"><font color="red">*</font>邮箱地址：</td>
		<td width="80%"> 
			<input name="email" id="email" type="text" />
		</td>
	</tr> -->
	 <tr> 
		<td width="20%" align="right">地址：</td>
		<td width="80%">
			<input name="address" id="address" type="text"  size="80"/>
		</td>
	</tr>
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="saveBtn" type="submit" value=" ">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value=" ">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form>

<SCRIPT LANGUAGE="JavaScript">

function gotoList(){
    location.href="/accounts/accountInfo_pageList.action";
}

</SCRIPT>
<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</BODY>
</HTML>