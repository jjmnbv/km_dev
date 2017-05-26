<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改专家信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.validate.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/messages_cn.js" type="text/javascript"></Script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
</head>
<body>
<script type="text/javascript"> 
$().ready(function() {
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
		//特殊字符串验证
jQuery.validator.addMethod("checkCertificate", function(value, element) {  
	var tel=/^[a-zA-Z][a-zA-Z0-9]{8,18}$/
	return this.optional(element) || (tel.test(value)); 
}, "必须由数字、字母组成并且以字母数字开头,至少8位");
$("#personalBasicInfoAddForm").validate({
	rules: { 
		"personalBasicInfo.name":{required: true,rangelength:[2,16],userName:"#name"},
		"loginInfo.mobile":{required: true,cellphone:true,maxlength:11},
		"loginInfo.email":{required: true,email:true,maxlength:30,unusualChar:true},
		"personalBasicInfo.n_Age":{digits: true,range:[1,99]},
		"personalBasicInfo.certificateNumber":{maxlength:30,checkCertificateNumber:true},
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
<s:set name="son_name" value="'修改'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	<div  style="height:90%;overflow-y:scroll; " >
<s:form action="personalbasic_update.action" method="POST" id="personalBasicInfoAddForm" enctype="multipart/form-data"  namespace='/personalbasic' >
<s:token/>
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- 标题条 -->

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
		<th colspan="6" align="left" class="edit_title">专家信息</th>
		<input name="loginInfo.n_LoginId" type="hidden" value="<s:property value="loginInfo.n_LoginId"/>"/>
	   
	    <input name="personalBasicInfo.n_PersonalId"  type="hidden" value="<s:property value="personalBasicInfo.n_PersonalId"/>" />
	    <input name="mdicalExcusieInfo.n_medicalMattersExclusive_id" type="hidden"  value="<s:property value='mdicalExcusieInfo.n_medicalMattersExclusive_id'/>"/>
	    <input name="personalityInfo.n_PersonalityId" id="n_PersonalityId" type="hidden" value="<s:property value='personalityInfo.n_PersonalityId'/>" />
	    <input name="healthYgenericInfo.n_HealthYgenericId" id="n_HealthYgenericId" type="hidden" value="<s:property value='healthYgenericInfo.n_HealthYgenericId'/>" />	
		<input name="accountInfo.n_AccountId" id="n_AccountId" type="hidden" value="<s:property value='accountInfo.n_AccountId'/>" readonly="readonly"/>
	</tr>
	<tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;登录信息</td>
	</tr>
   <tr> 
		<td width="20%" align="right"><font color="red">*</font>会员账号：</td>
		<td width="20%" colspan="6"> 
		<input name="loginInfo.loginAccount" id="loginAccount" disabled="disabled" type="text" value="<s:property value="loginInfo.loginAccount"/>"  />
		</td>
	</tr>
	
   <tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个人基本资料</td>
   </tr>
   
   <tr> 
		<td width="20%" align="right"><font color="red">*</font>姓名：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.name" type="text" require="true" id="loginInName"  value="<s:property value='personalBasicInfo.name'/>"/>
			
		</td>
		<td width="20%" align="right"><font color="red">*</font>手机号码：</td>
		<td width="20%"> 
			<input name="loginInfo.mobile" id="mobile" type="text" value="<s:property value='loginInfo.mobile'/>"/>

		</td>
		<td width="20%" align="right"><font color="red">*</font>邮箱地址：</td>
		<td width="20%"> 
			<input name="loginInfo.email" id="email" type="text" value="<s:property value='loginInfo.email'/>" />
		
		</td>
	</tr>
  	<tr> 
		
		<td width="20%" align="right">性别：</td>
		<td width="20%"> 	
		<input type="radio" name="personalBasicInfo.sex" value="0" <s:if test="personalBasicInfo.sex==0">checked="checked" </s:if>/>男
		<input type="radio" name="personalBasicInfo.sex" value="1" <s:if test="personalBasicInfo.sex==1">checked="checked" </s:if>/>女
		</td>
		<td width="20%" align="right">年龄：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.n_Age" type="text" value="<s:property value='personalBasicInfo.n_Age'/>"/>
		</td>
		<td width="20%" align="right">生日：</td>
		<td width="20%"> 
		
			<input name="personalBasicInfo.d_Birthday" id="d_Birthday" type="text" readonly   onchange="this.focus()"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:(new Date())})"  
			 value="<s:date name="personalBasicInfo.d_Birthday" format="yyyy-MM-dd" />"/>
		</td>
	</tr>
     <tr> 
		<td width="20%" align="right">证件类型：</td>
		<td width="20%"> 
	        <select name="n_CertificateType" id="n_CertificateType">
	     <s:if test="personalBasicInfo.n_CertificateType==0">
			<option value="0" selected="selected">身份证</option>
			<option value="1">护照</option>
	        <option value="2">回乡证</option>
			</s:if>
		
			
			<s:if test="personalBasicInfo.n_CertificateType==1">
			
			<option value="1" selected="selected">护照</option>
			<option value="0" >身份证</option>
	        <option value="2">回乡证</option>
			</s:if>
			
			
			<s:if test="personalBasicInfo.n_CertificateType==2">
			  <option value="2" selected="selected">回乡证</option>
			  <option value="1">护照</option>
	      	  <option value="0">身份证</option>
			</s:if>
			  </select>
		</td> 
		<td width="20%" align="right">证件号码：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.certificateNumber" name="certificateNumber" type="text"  value="<s:property value='personalBasicInfo.certificateNumber'/>"/>
		</td>
		<td width="20%" align="right">所在地：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.location" type="text"   value="<s:property value='personalBasicInfo.location'/>"/>
		</td> 
	</tr>
	<tr> 
	<td width="20%" align="right">故乡所在地：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.hometownLocation" type="text"   value="<s:property value='personalBasicInfo.hometownLocation'/>"/>
		</td>
		<td width="20%" align="right">教育状况：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.educationalStatus" type="text"   value="<s:property value='personalBasicInfo.educationalStatus'/>"/>
		</td> 
		<td width="20%" align="right">职业类型：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.professionType" type="text"   value="<s:property value='personalBasicInfo.professionType'/>"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">职业：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.profession" type="text"
			  value="<s:property value='personalBasicInfo.profession'/>"/>
		</td> 
		<td width="20%" align="right">年收入：</td>
		<td width="20%" colspan="4">  
			<input name="personalBasicInfo.n_AnnualIncome" type="text"  value="<s:property value='personalBasicInfo.n_AnnualIncome'/>"/>
		</td>
	</tr>
	 <tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个性信息</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">昵称：</td>
		<td width="20%" >
			<input  name="personalityInfo.nickname" id="nickname" type="text" value="<s:property value='personalityInfo.nickname'/>" />
		</td>
	<td width="20%" align="right" >个性签名：</td>
		<td width="20%" colspan="3">
		<textarea name="personalityInfo.personalityAutograph" id="personalityAutograph" style="width:500px;height:35px">
		<s:property value='personalityInfo.personalityAutograph'/>
		</textarea>
		</td>
	</tr>
	<tr>
		
	</tr>
	<tr > 
	<td width="20%" align="right">性格：</td>
		<td width="20%">
			<input name="personalityInfo.character" id="character" type="text" value="<s:property value='personalityInfo.character'/>" />
		</td> 
		<td width="20%" align="right">兴趣爱好：</td>
		<td width="20%">
			<input name="personalityInfo.interest" id="interest" type="text" value="<s:property value='personalityInfo.interest'/>" />
	</td>
		<td width="20%" rowspan="2" align="right">头像照片：</td>
		<td width="20%" rowspan="2">		
	     <img title="头像" width="310" height="120" style="cursor: pointer;" src="http://10.1.0.213:82/user/<s:property value='personalityInfo.headSculpture'/>"/>
		  	<input name="myFile" id="headSculpture" type="file" value="<s:property value='personalityInfo.headSculpture'/>"/>
		</td>
	</tr>
	<tr> 
	<td width="20%" align="right">QQ号：</td>
		<td width="20%" >
			<input name="personalityInfo.qqNumber" id="qqNumber" type="text"  value="<s:property value='personalityInfo.qqNumber'/>" />
		</td>
		<td width="20%" align="right">微博地址：</td>
		<td width="20%" >
			<input name="personalityInfo.microblogAddress" id="microblogAddress" type="text" value="<s:property value='personalityInfo.microblogAddress'/>" />
		</td> 
	</tr>
    <tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;医务专属信息</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">所属医院：</td>
		<td width="20%"> 
			<input name="personalBasicInfo.workUnit" type="text"  value="<s:property value='personalBasicInfo.workUnit'/>"/>
		</td>
		<td width="20%" align="right">医院等级：</td>
		<td width="20%"> 
			<input name="mdicalExcusieInfo.hospitalLevel" type="text"  value="<s:property value='mdicalExcusieInfo.hospitalLevel'/>"/>
		</td>
		<td width="20%" align="right">所属科室：</td>
		<td width="20%"> 
			<input name="mdicalExcusieInfo.theDepartment" type="text"   value="<s:property value='mdicalExcusieInfo.theDepartment'/>"/>
		</td>		
	</tr>
	<tr> 
	<td width="20%" align="right">专业专长：</td>
		<td width="20%"> 
			<input name="mdicalExcusieInfo.professionalExprtise" type="text" " value="<s:property value='mdicalExcusieInfo.professionalExprtise'/>"/>
		</td>
		<td width="20%" align="right">证书类型：</td>
		<td width="20%"> 
		 <select name="mdicalExcusieInfo.n_certificateType">
	        <option value="0" <s:if test="mdicalExcusieInfo.n_certificateType==0">selected</s:if>>医生资格证</option>
	        <option value="1" <s:if test="mdicalExcusieInfo.n_certificateType==1">selected</s:if>>医生执业证</option>
	        </select>
	      	</td>
		<td width="20%" align="right">证书编号：</td>
		<td width="20%"> 
			<input name="mdicalExcusieInfo.certificateNumber" type="text" "  value="<s:property value='mdicalExcusieInfo.certificateNumber'/>"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">审核电话：</td>
		<td width="20%" colspan="6"> 
			<input name="mdicalExcusieInfo.auditingPhone" type="text"   value="<s:property value='mdicalExcusieInfo.auditingPhone'/>"/>
		</td>
	</tr>
	
    <tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;健康类属信息</td>
	</tr>
	<tr> 
	<td width="20%" align="right">社保号：</td>
		<td width="20%">
			<input name="healthYgenericInfo.socialSecurityNumber" id="socialSecurityNumber" type="text" value="<s:property value='healthYgenericInfo.socialSecurityNumber'/>" />
		</td> 
		<td width="20%" align="right">婚姻状态：</td>
		<td width="20%" colspan="4">
		<s:if test="healthYgenericInfo.n_MaritalStatus==0">
			<input type="radio" name="healthYgenericInfo.n_MaritalStatus" value="0" checked="checked" />保密
			<input type="radio" name="healthYgenericInfo.n_MaritalStatus" value="1" />已婚
			<input type="radio" name="healthYgenericInfo.n_MaritalStatus" value="2" />未婚
			
		</s:if>
		<s:if test="healthYgenericInfo.n_MaritalStatus==1">
			<input type="radio" name="healthYgenericInfo.n_MaritalStatus" value="0" />保密
			<input type="radio" name="healthYgenericInfo.n_MaritalStatus" value="1" checked="checked"  />已婚
			<input type="radio" name="healthYgenericInfo.n_MaritalStatus" value="2" />未婚
			
		</s:if>
		<s:if test="healthYgenericInfo.n_MaritalStatus==2">
			<input type="radio" name="healthYgenericInfo.n_MaritalStatus" value="0"  />保密
			<input type="radio" name="healthYgenericInfo.n_MaritalStatus" value="1" />已婚
			<input type="radio" name="healthYgenericInfo.n_MaritalStatus" value="2" checked="checked"/>未婚
			
		</s:if>
		</td> 
	</tr>
	<tr> 
		
		<td width="20%" align="right">有无生育：</td>
		<td width="20%">
			<s:if test="healthYgenericInfo.n_ThereIsNoFertility==0">
				<input type="radio" name="healthYgenericInfo.n_ThereIsNoFertility" value="0" checked="checked" />有
				<input type="radio" name="healthYgenericInfo.n_ThereIsNoFertility" value="1" />无	
			</s:if>
			<s:else>
				<input type="radio" name="healthYgenericInfo.n_ThereIsNoFertility" value="0" />有
				<input type="radio" name="healthYgenericInfo.n_ThereIsNoFertility" value="1" checked="checked" />无
			</s:else>
			
		</td>
		<td width="20%" align="right">血型：</td>
		<td width="20%"  colspan="4">
		<s:if test="healthYgenericInfo.bloodType==0">
			<input type="radio" name="healthYgenericInfo.bloodType" value="0" checked="checked" />A型
			<input type="radio" name="healthYgenericInfo.bloodType" value="1" />B型
			<input type="radio" name="healthYgenericInfo.bloodType" value="2" />O型
			<input type="radio" name="healthYgenericInfo.bloodType" value="3" />AB型
		</s:if>
		<s:if test="healthYgenericInfo.bloodType==1">
			<input type="radio" name="healthYgenericInfo.bloodType" value="0"  />A型
			<input type="radio" name="healthYgenericInfo.bloodType" value="1" checked="checked" />B型
			<input type="radio" name="healthYgenericInfo.bloodType" value="2" />O型
			<input type="radio" name="healthYgenericInfo.bloodType" value="3" />AB型
		</s:if>
		<s:if test="healthYgenericInfo.bloodType==2">
			<input type="radio" name="healthYgenericInfo.bloodType" value="0"  />A型
			<input type="radio" name="healthYgenericInfo.bloodType" value="1" />B型
			<input type="radio" name="healthYgenericInfo.bloodType" value="2" checked="checked" />O型
			<input type="radio" name="healthYgenericInfo.bloodType" value="3" />AB型
		</s:if>
		<s:if test="healthYgenericInfo.bloodType==3">
			<input type="radio" name="healthYgenericInfo.bloodType" value="0"  />A型
			<input type="radio" name="healthYgenericInfo.bloodType" value="1" />B型
			<input type="radio" name="healthYgenericInfo.bloodType" value="2" />O型
			<input type="radio" name="healthYgenericInfo.bloodType" value="3" checked="checked" />AB型
		</s:if>
		</td>
	</tr>
	 <tr>
		<td width="20%" align="right">健康状况：</td>
		<td width="20%" colspan="6">
		<textarea   name="healthYgenericInfo.healthyState" id="healthyState"  style="width:557px;height:35px">
		<s:property value='healthYgenericInfo.healthyState'/>
		</textarea>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right">健康咨询兴趣：</td>
		<td width="20%" colspan="6">
		<textarea  name="healthYgenericInfo.healthySeekAdviceFrom" id="healthySeekAdviceFrom"  style="width:557px;height:35px">
		<s:property value='healthYgenericInfo.healthySeekAdviceFrom'/>
		</textarea>
		</td>
	</tr>
	<tr>
	<td width="20%" align="right">饮食习惯：</td>
		<td width="20%" colspan="6">
		<textarea name="healthYgenericInfo.eatingHabits" id="eatingHabits" style="width:557px;height:35px">
		<s:property value='healthYgenericInfo.eatingHabits'/>	
		</textarea>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">烟酒史：</td>
		<td width="20%" colspan="6">
		<textarea name="healthYgenericInfo.smokingAndAlcoholHistory" id="smokingAndAlcoholHistory" style="width:557px;height:35px">
		<s:property value='healthYgenericInfo.smokingAndAlcoholHistory'/>	
		</textarea>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">过往病史：</td>
		<td width="20%" colspan="6">
		<textarea name="healthYgenericInfo.pastMedicalHistory" id="pastMedicalHistory" style="width:557px;height:35px">
	    <s:property value='healthYgenericInfo.pastMedicalHistory'/>
		</textarea>
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
<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>  
<SCRIPT LANGUAGE="JavaScript">
function gotoList(){
    location.href="/personalbasic/personalbasic_pageList.action";
}

</SCRIPT>
</BODY>
</HTML>