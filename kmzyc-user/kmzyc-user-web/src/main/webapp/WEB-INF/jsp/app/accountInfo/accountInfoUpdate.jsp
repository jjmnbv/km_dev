<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改账户信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/dialog.js"></script>
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.validate.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/messages_cn.js" type="text/javascript"></Script>
</head>
<body>

 <script type="text/javascript">
 var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子   
 var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值.10代表X
 
 $().ready(function() {
 	jQuery.validator.addMethod("checkAcconutId", function(value, element) {
 		if(value.length>0){
 			
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
 		} else {
 			return true;
 		}
	}, "证件号码格式不正确");
	
 	
$("#accountInfoUpdate").validate({
	rules: { 
		"name":{userName:true,maxlength:16},
		"acconutId":{checkAcconutId:true},
		"mobile":{cellphone:true},
		"email":{email:true},
		"address":{maxlength:16}
		},
		success: function (label){
            label.removeClass("checked").addClass("checked");
        }
	});
	 
	 //ajax判断帐户名是否存在
	 jQuery.validator.addMethod("checkAccountLogin", function(value, element) {
		 //获取初始化的登录名
		 var accountLoginHidden=$("#accountLoginHidden").val();
		 if(accountLoginHidden==value){
			 return true;
		 }
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
	 
	 //初始化加载判断是否需要登录名必填验证
	 var eg=$("#edit").val();
	 if(eg=="edit"){
		 $("#accountLogin").rules("add",{required:true,checkAccountLogin:true,rangelength:[6,20]});
	 }
	 
});

//弹出 选择账号层
 function popUpUserInfo() {
     dialog("选择会员账号","iframe:/logininfo/logininfo_queryPageBasicUserInfo.action?callBack=closeOpenUserInfo" ,"900px","500px","iframe");
 }
 //关闭弹出窗口 
 function closeOpenUserInfo(accountId,account,name){
     closeThis();
     $("#loginAccount").val(account);
      $("#n_LoginId").val(accountId);
 }
  
</script>

<s:form id="accountInfoUpdate" action="accountInfo_update.action" method="POST"  namespace='/accountInfo' >

<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<input type="hidden"  id="edit" name="edit" value='<s:property value="edit"/>'>


<!-- 导航栏 -->
<s:set name="parent_name" value="'账户管理'" scope="request"/>
<s:if test="edit=='edit'">
	<s:set name="name" value="'账户编辑'" scope="request" />
</s:if> <s:else>
	<s:set name="name" value="'账户信息'" scope="request" />
</s:else>
<s:set name="son_name" value="'修改'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>


<!-- 数据编辑区域 -->
<table  width="60%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
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
		   <!--对是否来自账户编辑列表业务处理  -->
		    <s:if test="edit =='edit'">
		    <input name="accountLogin"  id="accountLogin"  value="<s:property value='accountInfo.accountLogin'/>"  maxlength="20"/>
		    <input name="n_LoginId"   type="hidden" value="<s:property value='accountInfo.n_LoginId'/>"/>
		    </s:if> <s:else>
		    <s:property value='accountInfo.accountLogin'/>
		    </s:else>
		     <input name="accountLoginHidden"  id="accountLoginHidden" type="hidden" value="<s:property value='accountInfo.accountLogin'/>"/>
			<input name="n_AccountId" id="n_AccountId" type="hidden" value="<s:property value='accountInfo.n_AccountId'/>"/>
			<span id="megAccountLogin"></span>
		</td>
	</tr>
	

    <tr> 
		<td width="20%" align="right">账户真实姓名：</td>
		<td width="80%"> 
			<input name="name" id="name" type="text"  value="<s:property value='accountInfo.name'/>"/>
		</td>
	</tr>
    <tr> 
		<td width="20%" align="right">证件号码：</td>
		<td width="80%"> 
			<input name="acconutId" id="acconutId" type="text" value="<s:property value='accountInfo.acconutId'/>"/>
		</td>
	</tr>
     <tr> 
		<td width="20%" align="right">手机号码：</td>
		<td width="80%"> 
			<input name="mobile" id="mobile" type="text" value="<s:property value='accountInfo.mobile'/>"/>
		</td>
	</tr>
	<%--  <tr> 
		<td width="20%" align="right">邮箱地址：</td>
		<td width="80%"> 
			<input name="email" id="email" type="text" value="<s:property value='accountInfo.email'/>"/>
		</td>
	</tr> --%>
	 <tr> 
		<td width="20%" align="right">地址：</td>
		<td width="80%">
			<input name="address" id="address" type="text"  value="<s:property value='accountInfo.address'/>"/>
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
	 var ed=$("#edit").val();
	 if(ed=="edit"){
		 location.href="/accounts/accountInfo_pageEditList.action";
	 } else {
		 location.href="/accounts/accountInfo_pageList.action";
	 }
   
}

</SCRIPT>
</BODY>
<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>