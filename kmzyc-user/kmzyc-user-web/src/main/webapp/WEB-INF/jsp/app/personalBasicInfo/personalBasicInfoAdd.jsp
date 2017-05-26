<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加个人信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 <script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
$().ready(function() {
   var customError = "";
			$.validator.addMethod("checkCertificateNumber", function(value, element) {
			   var returnVal = true;
			    var rtl=document.getElementById("n_CertificateType");
  				var i = rtl.value;
  				if(value!=""){
  				if(i==0){
  				var reg=/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
				if (reg.test(value) == false){
  				customError = "身份证输入错误";
					returnVal = false;
					}
  				}
  				if(i==1){
  				var regs = /^1[45][0-9]{7}|G[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]+$/;
  				   if(!regs.test(value)){
  				    customError = "护照输入错误";
					returnVal = false;
					}
  				}
  				if(i==2){
  				var regs1=/^[A-Za-z0-9]+$/;
  				   if(!regs1.test(value)){
  				    customError = "回乡证输入错误";
					returnVal = false;
					}}
  				}
				$.validator.messages.checkCertificateNumber = customError;
				return returnVal;
			}, "error " + customError);

		 	jQuery.validator.addMethod("checkLoginAccount", function(value, element) {
		 	 	var rows = 0;
		 			$.ajax({
		 				async:false,
		 				url:"personalBasicInfo_checkLoginAccount.action",
		 				type:"POST",
		 				data:"loginAccount=" + value,
		 				dataType:"json",
		 				success:function(json){
		 						rows = json;
		 				}
		 			});
		 			if(rows>0){
		 				return false;
		 			}else{
		 	 			return true;
		 	 		}
		 			
			}, "重复的会员账号");
 	
		/* 	jQuery.validator.addMethod("checkLoginEmail", function(value, element) {
		 	 	var rows = 0;
		 			$.ajax({
		 				async:false,
		 				url:"personalBasicInfo_checkLoginEail.action",
		 				type:"POST",
		 				data:"email=" + value,
		 				dataType:"json",
		 				success:function(json){
		 						rows = json;
		 				}
		 			});
		 			return parseInt(rows)==0;
			}, "重复的邮箱地址"); */
			
			jQuery.validator.addMethod("checkLoginMobilePattern", function(value, element) {
		 		return /^(0|86|17951)?(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$/.test(value);
			}, "手机号码格式不正确");
			
			jQuery.validator.addMethod("checkLoginMobile", function(value, element) {
		 	 	var rows = 0;
		 			$.ajax({
		 				async:false,
		 				url:"personalBasicInfo_checkLoginMobile.action",
		 				type:"POST",
		 				data:"mobile=" + value,
		 				dataType:"json",
		 				success:function(json){
		 						rows = json;
		 				}
		 			});
		 			return parseInt(rows)==0;
			}, "重复的手机号码");
			

	$("#personalBasicInfoAddForm").validate({
		rules: { 
			"loginInfo.loginAccount":{required: true,unusualChar:true,checkLoginAccount:true,rangelength:[6,20]},
			"loginInfo.loginPassword":{required: true,rangelength:[6,16]},
			"reg_loginPassword":{required: true,equalTo:"#loginPassword"},
			"accountInfo.paymentpwd":{required: true,rangelength:[6,16]},
			"reg_paymentpwd":{required: true,equalTo:"#paymentpwd"},
			"name":{unusualChar:true,rangelength:[2,16],userName:true},
			"loginInfo.mobile":{required: true,cellphone:true,maxlength:11,checkLoginMobilePattern:true,checkLoginMobile:true},
			/* "loginInfo.email":{required: true,email:true,maxlength:30,checkLoginEmail:true}, */
			"certificateNumber":{maxlength:30,checkCertificateNumber:"#certificateNumber"},
			"d_Birthday":{afterDate:true},
			"personalityInfo.nickname":{maxlength:16},
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
<s:set name="parent_name" value="'客户资料'" scope="request"/>
<s:set name="name" value="'个人客户'" scope="request"/>
<s:set name="son_name" value="'添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
 <div  style="height:90%;overflow-y:auto; " >
<s:form action="personalBasicInfo_add.action" enctype="multipart/form-data" id="personalBasicInfoAddForm" method="POST"  namespace='/userInfo'  >
<s:token/>
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- 数据编辑区域 -->
<table width="90%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="6" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
   	<tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;登录信息</td>
	</tr>
	<tr> 
		<td align="right"><font color="red">*</font>会员账号：</td>
		<td   colspan="6"> 
			<input name="loginInfo.loginAccount" id="loginAccount" type="text" value=""  />
		</td> 
	</tr>
		<tr> 
		<td   align="right"><font color="red">*</font>登录密码：</td>
		<td  > 
			<input name="loginInfo.loginPassword" id="loginPassword" type="password" value="" />
		</td>
		<td   align="right"><font color="red">*</font>确认登录密码：</td>
		<td   colspan="4"> 
			<input name="reg_loginPassword" id="reg_loginPassword" type="password" value=""/>
		</td>
	</tr>
	<tr> 
		<td   align="right"><font color="red">*</font>支付密码：</td>
		<td  > 
			<input name="accountInfo.paymentpwd" id="paymentpwd" type="password" value="" />
		</td>
		<td   align="right"><font color="red">*</font>确认支付密码：</td>
		<td   colspan="4"> 
			<input name="reg_paymentpwd" id="reg_paymentpwd" type="password" value=""/>
		</td>
	</tr>
    <tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个人基本资料</td>
	</tr>
    <tr> 
		<td width="8%"  align="right">姓名：</td>
		<td width="20%"  > 
			<input name="name" id="name" type="text" value=""/>
		</td>
		<td width="8%"  align="right">性别：</td>
		<td width="30%" > 
			<input type="radio" name="sex" value="0" checked="checked" />男
			<input type="radio" name="sex" value="1" />女
		</td>
		<td width="8%"  align="right">昵称：</td>
		<td width="30%" >
			<input  name="personalityInfo.nickname" id="nickname" type="text"/>
		</td> 
		<!-- 
		
		-->
	</tr>
	
	<tr> 
		<td   align="right">生日：</td>
		<td  >
			<input name="d_Birthday"   onchange="this.focus()"  id="d_Birthday" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:(new Date())})" type="text" />
		</td>
		<td   align="right"><font color="red">*</font>手机号码：</td>
		<td  > 
			<input name="loginInfo.mobile" id="mobile" type="text" value="" />
		</td>
		 
		<!-- <td   align="right"><font color="red">*</font>邮箱地址：</td>
		<td  > 
			<input name="loginInfo.email" id="email" type="text" value="" />
		</td> -->
		<!-- 
		<td   align="right">年龄：</td>
		<td  >
			<input name="n_Age" id="n_Age" type="text"  value=""/>
		</td>
		 --> 
	</tr>
	<tr> 
		<td   align="right">证件类型：</td>
		<td  >
			<select name="n_CertificateType" id="n_CertificateType">
	        <option value="0" selected="selected">身份证</option>
	        <option value="1">护照</option>
	        <option value="2">回乡证</option>
	        </select>
		</td>

		<td   align="right">证件号码：</td>
		<td  >
			<input name="certificateNumber" id="certificateNumber" type="text" />
		</td>
		<td   align="right"></td>
		<td  >
		</td>
	</tr>
	<!-- 
	<tr> 
		
		<td   align="right">故乡所在地：</td>
		<td  >
			<input name="hometownLocation" id="hometownLocation" type="text" />
		</td> 
		<td   align="right">教育状况：</td>
		<td  >
			<input name="educationalStatus" id="educationalStatus" type="text" />
		</td>
		<td   align="right">工作单位：</td>
		<td width="20%">
			<input name="workUnit" id="workUnit" type="text"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">职业类型：</td>
		<td width="20%">
			<input name="professionType" id="professionType" type="text" />
		</td>
		<td width="20%" align="right">职业：</td>
		<td width="20%">
			<input name="profession" id="profession" type="text" />
		</td> 
		<td width="20%" align="right">年收入：</td>
		<td width="20%" >
			<input name="n_AnnualIncome" id="n_AnnualIncome" type="text" />
		</td>
	</tr>
	<tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个性信息</td>
	</tr>
	<tr> 
		
		<td width="20%" align="right">个性签名：</td>
		<td width="20%" colspan="4">
		<textarea name="personalityInfo.personalityAutograph" id="personalityAutograph" style="width:500px;height:35px"></textarea>
		</td>
	</tr>	
	<tr> 
		<td width="20%" align="right">头像照片：</td>
		<td width="20%">
			<input name="myFile" id="headSculpture" type="file"/>
		</td>

		<td width="20%" align="right">性格：</td>
		<td width="20%">
			<input name="personalityInfo.character" id="character" type="text"/>
		</td>
		<td width="20%" align="right">兴趣爱好：</td>
		<td width="20%">
			<input name="personalityInfo.interest" id="interest" type="text" />
		</td>
	</tr>
	<tr> 
		
		<td width="20%" align="right">QQ号：</td>
		<td width="20%" >
			<input name="personalityInfo.qqNumber" id="qqNumber" type="text"/>
		</td>
		<td width="20%" align="right">微博地址：</td>
		<td width="20%" colspan="4">
			<input name="personalityInfo.microblogAddress" id="microblogAddress" type="text" />
		</td> 
	</tr>
    <tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;健康类属信息</td>
	</tr>
	<tr> 
	<td width="20%" align="right">社保号：</td>
		<td width="20%">
			<input name="healthYgenericInfo.socialSecurityNumber" id="socialSecurityNumber" type="text"/>
		</td> 
		<td width="20%" align="right">婚姻状态：</td>
		<td width="20%" colspan="4">
			<input type="radio" name="healthYgenericInfo.n_MaritalStatus" value="0" checked="checked" />保密
			<input type="radio" name="healthYgenericInfo.n_MaritalStatus" value="1" />已婚
			<input type="radio" name="healthYgenericInfo.n_MaritalStatus" value="2" />未婚
		</td>
	</tr>
	<tr>
	<td width="20%" align="right">有无生育：</td>
		<td width="20%">
			<input type="radio" name="healthYgenericInfo.n_ThereIsNoFertility" value="0" checked="checked" />有
			<input type="radio" name="healthYgenericInfo.n_ThereIsNoFertility" value="1" />无
		</td>
		<td width="20%" align="right">血型：</td>
		<td width="20%" colspan="4">
			<input type="radio" name="healthYgenericInfo.bloodType" value="0" checked="checked" />A型
			<input type="radio" name="healthYgenericInfo.bloodType" value="1" />B型
			<input type="radio" name="healthYgenericInfo.bloodType" value="2" />O型
			<input type="radio" name="healthYgenericInfo.bloodType" value="3" />AB型
		</td>
	</tr>
	<tr>
		<td width="20%" align="right">健康状况：</td>
		<td width="20%" colspan="6">
		<textarea   name="healthYgenericInfo.healthyState" id="healthyState"  style="width:557px;height:35px"></textarea>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right">健康咨询兴趣：</td>
		<td width="20%" colspan="6">
		<textarea  name="healthYgenericInfo.healthySeekAdviceFrom" id="healthySeekAdviceFrom"  style="width:557px;height:35px"></textarea>
		</td>
	</tr>
	<tr>
	<td width="20%" align="right">饮食习惯：</td>
		<td width="20%" colspan="6">
		<textarea name="healthYgenericInfo.eatingHabits" id="eatingHabits" style="width:557px;height:35px"></textarea>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">烟酒史：</td>
		<td width="20%" colspan="6">
		<textarea name="healthYgenericInfo.smokingAndAlcoholHistory" id="smokingAndAlcoholHistory" style="width:557px;height:35px"></textarea>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">过往病史：</td>
		<td width="20%" colspan="6">
		<textarea name="healthYgenericInfo.pastMedicalHistory" id="pastMedicalHistory" style="width:557px;height:35px"></textarea>
		</td>
	</tr>
	 -->
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<INPUT class="saveBtn" TYPE="submit" value="">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value="">
		</td>
		<td  align="center"></td>
	</tr>
</table>

<br><br>
</s:form>
</div>
<SCRIPT LANGUAGE="JavaScript">
	
	function gotoList(){
	    location.href="/userInfo/personalBasicInfo_pageList.action";
	}

</SCRIPT>
</BODY>
<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>