<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加专家信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.validate.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/messages_cn.js" type="text/javascript"></Script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 <script src="/etc/js/dialog.js"></script>
</head>
<body>
<script type="text/javascript"> 
$().ready(function() {
jQuery.validator.addMethod("checkLoginAccount", function(value, element) {
 	 	var rows = 0;
 			$.ajax({
 				async:false,
 				url:"personalbasic_checkLoginAccount.action",
 				type:"POST",
 				data:"loginAccount=" + value,
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
 			
	}, "重复的会员账号");
	//特殊字符串验证
	jQuery.validator.addMethod("checkCertificate", function(value, element) {  
		var tel=/^[a-zA-Z][a-zA-Z0-9]{8,18}$/;
		return this.optional(element) || (tel.test(value)); 
	}, "必须由数字、字母组成并且以字母数字开头,至少8位");
	
 var customError = "";
			$.validator.addMethod("checkCertificateNumber", function(value, element) {
			   var returnVal = true;
			    var rtl=document.getElementById("n_CertificateType");
  				var i = rtl.value;
  				
  				if(i==0){
  				var reg=/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
				if (reg.test(value) === false){
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
					}
  				}
				$.validator.messages.checkCertificateNumber = customError;
				return returnVal;
			}, "error " + customError);
$("#personalBasicInfoAddForm").validate({
	rules: { 
		"loginInfo.loginAccount":{required: true,unusualChar:true ,checkLoginAccount:true,rangelength:[6,20]},
		"loginInfo.loginPassword":{required: true,rangelength:[6,16],unusualChar:true},
		"reg_loginPassword":{required: true,equalTo:"#loginPassword"},
		"accountInfo.paymentpwd":{required: true,rangelength:[6,16],unusualChar:true},
		"reg_paymentpwd":{required: true,equalTo:"#paymentpwd"},
		"personalBasicInfo.name":{required: true,rangelength:[2,16],userName:"#name"},
		"loginInfo.mobile":{required: true,cellphone:true,maxlength:11},
		"loginInfo.email":{required: true,email:true,maxlength:30,unusualChar:true},
		"personalBasicInfo.n_Age":{digits: true,range:[1,99]},
		"personalBasicInfo.certificateNumber":{maxlength:30,checkCertificateNumber:true,unusualChar:true},
		"personalBasicInfo.location":{maxlength:16,unusualChar:true},
		"personalBasicInfo.hometownLocation":{maxlength:16,unusualChar:true},
		"personalBasicInfo.educationalStatus":{maxlength:16,unusualChar:true},
		"personalBasicInfo.workUnit":{maxlength:16,unusualChar:true},
		"mdicalExcusieInfo.hospitalLevel":{maxlength:16,unusualChar:true},
		"mdicalExcusieInfo.theDepartment":{maxlength:16,unusualChar:true},
		"mdicalExcusieInfo.professionalExprtise":{maxlength:16,unusualChar:true},
		"mdicalExcusieInfo.n_certificateType":{maxlength:16,unusualChar:true},
		"mdicalExcusieInfo.certificateNumber":{checkCertificate:true,maxlength:18,unusualChar:true},
		"mdicalExcusieInfo.auditingPhone":{maxlength:20,isTel:true},		
		"personalBasicInfo.professionType":{maxlength:16,unusualChar:true},
		"personalBasicInfo.profession":{maxlength:16,unusualChar:true},
		"personalBasicInfo.n_AnnualIncome":{digits: true,range:[1,1000000000],maxlength:16,number:true,unusualChar:true},
		"personalityInfo.nickname":{maxlength:16,unusualChar:true},
		"personalityInfo.personalityAutograph":{maxlength:165,unusualChar:true},
		"personalityInfo.interest":{maxlength:16,unusualChar:true},
		"personalityInfo.microblogAddress":{maxlength:16,unusualChar:true},
		"personalityInfo.qqNumber":{maxlength:20,number:true,unusualChar:true},
		"healthYgenericInfo.healthyState":{maxlength:165,unusualChar:true},
		"healthYgenericInfo.eatingHabits":{maxlength:165,unusualChar:true},
		"healthYgenericInfo.smokingAndAlcoholHistory":{maxlength:165,unusualChar:true},
		"healthYgenericInfo.pastMedicalHistory":{maxlength:165,unusualChar:true},
		"healthYgenericInfo.socialSecurityNumber":{isSecurityNumber:true,maxlength:10,unusualChar:true},
		"healthYgenericInfo.healthySeekAdviceFrom":{maxlength:165,unusualChar:true},
		"personalityInfo.character":{maxlength:16,unusualChar:true},
		"personalBasicInfo.d_Birthday":{afterDate:true}
		},
		success: function (label){
            label.removeClass("checked").addClass("checked");
        }
	});
}); 	
</script>
<!-- 导航栏 -->
<s:set name="parent_name" value="'专家信息'" scope="request"/>
<s:set name="name" value="'专家信息'" scope="request"/>
<s:set name="son_name" value="'添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:scroll; " >
<s:form action="personalbasic_add.action" method="POST" enctype="multipart/form-data" id="personalBasicInfoAddForm"  namespace='/personalbasic' >
<s:token/>
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- 数据编辑区域 -->
<table width="90%" class="edit_table" cellpadding="4" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="6" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<th colspan="6" align="left" class="edit_title">专家信息</th>
	</tr>
	<tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;登录信息</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>会员账号：</td>
		<td width="20%" colspan="6"> 
			<input name="loginInfo.loginAccount" id="loginAccount" type="text" value=""  />
		</td>
		
	</tr>
	<tr>
	<td width="20%" align="right"><font color="red">*</font>登录密码：</td>
		<td width="20%"> 
			<input name="loginInfo.loginPassword" id="loginPassword" type="password" value="" />
		</td> 
		<td width="20%" align="right"><font color="red">*</font>确认登录密码：</td>
		<td width="20%" colspan="4"> 
			<input name="reg_loginPassword" id="reg_loginPassword" type="password" value=""/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>支付密码：</td>
		<td width="20%"> 
			<input name="accountInfo.paymentpwd" id="paymentpwd" type="password" value="" />
		</td> 
		<td width="20%" align="right"><font color="red">*</font>确认支付密码：</td>
		<td width="20%" colspan="4"> 
			<input name="reg_paymentpwd" id="reg_paymentpwd" type="password" value=""/>
		</td>
	</tr>
    <tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个人基本资料</td>
	</tr>
	    <tr> 
		<td width="20%" align="right"><font color="red">*</font>姓名：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.name" id="loginInName"   type="text" value=""/>
		</td>
		<td width="20%" align="right"><font color="red">*</font>手机号码：</td>
		<td width="20%"> 
			<input name="loginInfo.mobile" id="mobile" type="text" value="" />
			
		</td>
		<td width="20%" align="right"><font color="red">*</font>邮箱地址：</td>
		<td width="20%"> 
			<input name="loginInfo.email" id="email" type="text" value="" />
			
		</td>
	</tr>
  	<tr> 
		<td width="20%" align="right">性别：</td>
		<td width="20%"> 	
		<input type="radio" name="personalBasicInfo.sex" value="0" checked="checked" />男
		<input type="radio" name="personalBasicInfo.sex" value="1" />女
		</td>
		<td width="20%" align="right">年龄：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.n_Age" type="text"  value=""/>
		</td> 
		<td width="20%" align="right">生日：</td>
		<td width="20%"> 
		<input name="personalBasicInfo.d_Birthday" id="d_Birthday" type="text"
		 readonly   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:(new Date())})"  />
		</td>
	</tr>
     <tr> 
		<td width="20%" align="right"> 证件类型：</td>
		<td width="20%"> 
	        <select name="personalBasicInfo.n_CertificateType" id="n_CertificateType">
	        <option value="0" select>身份证</option>
	        <option value="1">护照</option>
	        <option value="2">回乡证</option>
	        </select>
		</td>
		<td width="20%" align="right">证件号码：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.certificateNumber" id="certificateNumber" type="text"   value=""/>
		</td>
		<td width="20%" align="right">所在地：</td>
		<td width="20%"  > 
			<input name="personalBasicInfo.location" type="text" value=""/>
		</td> 
	</tr>
	<tr> 
		<td width="20%" align="right">故乡所在地：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.hometownLocation" type="text"  value=""/>
		</td>
		<td width="20%" align="right">教育状况：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.educationalStatus" type="text"  value=""/>
		</td> 
		<td width="20%" align="right">职业类型：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.professionType" type="text"  value=""/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">职业：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.profession" type="text"  value=""/>
		</td>
		<td width="20%" align="right">年收入：</td>
		<td width="20%" colspan="4" > 
			<input name="personalBasicInfo.n_AnnualIncome" type="text"  value=""/>
		</td>
	</tr>		
    <tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个性信息</td>
	</tr>
	
	<tr>		
		<td width="20%" align="right">昵称：</td>
		<td width="20%" >
			<input  name="personalityInfo.nickname" id="nickname" type="text" require="false"  value="" />
		</td>
		<td width="20%" align="right">个性签名：</td>
		<td width="20%"  colspan="4">
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
			<input name="personalityInfo.character" id="character" type="text"   value="" />
		</td>
		<td width="20%" align="right">兴趣爱好：</td>
		<td width="20%" >
			<input name="personalityInfo.interest" id="interest" type="text"  value="" />
		</td>
	</tr>
	<tr> 
		
		<td width="20%" align="right">QQ号：</td>
		<td width="20%" >
			<input name="personalityInfo.qqNumber" id="qqNumber" type="text"  value="" />
		</td>
		<td width="20%" align="right">微博地址：</td>
		<td width="20%" colspan="4">
			<input name="personalityInfo.microblogAddress" id="microblogAddress" type="text" value="" />
		</td> 
	</tr>
	 <tr> 
		<td  colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;医务专属信息</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">所属医院：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.workUnit" type="text" value=""/>
		</td>
		<td width="20%" align="right">医院等级：</td>
		<td width="20%"> 
			<input name="mdicalExcusieInfo.hospitalLevel" type="text"   value=""/>
		</td>
		<td width="20%" align="right">所属科室：</td>
		<td width="20%"> 
			<input name="mdicalExcusieInfo.theDepartment"  value=""/>
		</td>	
	</tr>
	<tr>
	    <td width="20%" align="right">专业专长：</td>
		<td width="20%"> 
			<input name="mdicalExcusieInfo.professionalExprtise" type="text"  value=""/>
		</td> 
		<td width="20%" align="right">证书类型：</td>
		<td width="20%"> 
		 <select name="mdicalExcusieInfo.n_certificateType">
	        <option value="0" >医生资格证</option>
	        <option value="1" >医生执业证</option>
	        </select>
	      	</td>
		<td width="20%" align="right">证书编号：</td>
		<td width="20%"> 
			<input name="mdicalExcusieInfo.certificateNumber" type="text"   value=""/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">审核电话：</td>
		<td width="20%" colspan="6"> 
			<input name="mdicalExcusieInfo.auditingPhone" type="text"   value=""/>
		</td>
	</tr>
	 <tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;健康类属信息</td>
	</tr>
	<tr> 
		<td width="20%" align="right">社保号：</td>
		<td width="20%" >
			<input name="healthYgenericInfo.socialSecurityNumber" id="socialSecurityNumber" type="text" value="" />
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
		<td width="25%"  colspan="4">
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
</div>
<SCRIPT LANGUAGE="JavaScript">
function gotoList(){
    location.href="/personalbasic/personalbasic_pageList.action";
}

</SCRIPT>
</BODY>
<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>