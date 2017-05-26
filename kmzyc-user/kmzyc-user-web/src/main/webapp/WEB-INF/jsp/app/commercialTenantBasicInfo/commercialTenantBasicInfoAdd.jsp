<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加商户信息</title>
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
 				url:"commercialTenantBasicInfo_checkLoginAccount.action",
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
$("#commercialTenantBasicInfoAdd").validate({
	rules: { 
		"loginInfo.loginAccount":{required: true,checkLoginAccount:true,unusualChar:true,rangelength:[6,20]},
		"loginInfo.loginPassword":{required: true,rangelength:[6,16]},
		"reg_loginPassword":{required: true,equalTo:"#loginPassword"},
		"accountInfo.paymentpwd":{required: true,rangelength:[6,16]},
		"reg_paymentpwd":{required: true,equalTo:"#paymentpwd"},
		"corporateName":{required: true,maxlength:16,userName:true},
		"contactsName":{required: true,maxlength:16,userName:true},
		"loginInfo.mobile":{required: true,cellphone:true},
		/* "loginInfo.email":{required: true,email:true,unusualChar:true,maxlength:30}, */
		"certificateNumber":{maxlength:30,checkCertificateNumber:true,unusualChar:true},
		"contactsDepartment":{maxlength:16,unusualChar:true},
		"fixedPhone":{maxlength:16,unusualChar:true,isTel:true},
		"corporateLocation":{maxlength:16,unusualChar:true},
		"corporateProperty":{maxlength:16,unusualChar:true},
		"corporateTrade":{maxlength:16,unusualChar:true},
		"n_EnterpriseNumberOfPeople":{maxlength:16,number:true,unusualChar:true},
		"d_FoundDate":{maxlength:16,afterDate:true},
		"postalcode":{maxlength:16,number:true,unusualChar:true},
		"myFile":{checkFile:true},
		"organizationCode":{maxlength:16,unusualChar:true},
		"enterpriseLegalRepresentativ":{maxlength:16,unusualChar:true},
		"enterpriseRealmName":{maxlength:16,unusualChar:true},
		"personalityInfo.interest":{maxlength:16,unusualChar:true},
		"personalityInfo.microblogAddress":{maxlength:16,unusualChar:true},
		"personalityInfo.qqNumber":{maxlength:20,number:true,unusualChar:true},
		"personalityInfo.personalityAutograph":{maxlength:165,unusualChar:true}
		},
		success: function (label){
            label.removeClass("checked").addClass("checked");
        }
	});
	 
});
</script>
 <!-- 导航栏 -->
<s:set name="parent_name" value="'客户资料'" scope="request"/>
<s:set name="name" value="'商户'" scope="request"/>
<s:set name="son_name" value="'添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form enctype="multipart/form-data" id="commercialTenantBasicInfoAdd" action="commercialTenantBasicInfo_add.action" method="POST"  namespace='/userInfo' >
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
		<td colspan="6" align="left" class="edit_title">商户信息</td>
	</tr>
    <tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;登录信息</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>会员账号：</td>
		<td width="25%"  colspan="6"> 
			<input name="loginInfo.loginAccount" id="loginAccount" type="text" value="" />
		</td>
		</tr>
		<tr>
		<td width="20%" align="right"><font color="red">*</font>登录密码：</td>
		<td width="25%"> 
			<input name="loginInfo.loginPassword" id="loginPassword" type="password" value=""/>
		</td>

		<td width="20%" align="right"><font color="red">*</font>确认密码：</td>
		<td width="25%" colspan="4"> 
			<input name="reg_loginPassword" id="reg_loginPassword" type="password" value=""/>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>支付密码：</td>
		<td width="25%"> 
			<input name="accountInfo.paymentpwd" id="paymentpwd" type="password" value="" />
		</td>
		<td width="20%" align="right"><font color="red">*</font>确认支付密码：</td>
		<td width="25%"  colspan="4"> 
			<input name="reg_paymentpwd" id="reg_paymentpwd" type="password" value=""/>
		</td>
	</tr>
	<tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户基本信息</td>
	</tr>
	<tr>
	<td width="20%" align="right"><font color="red">*</font>商户类别：</td>
	<td width="25%"> 
			<select name="loginInfo.n_CustomerTypeId">
			<s:iterator value="customerTypeList" id="customerTypeList">
				<option value="<s:property value='customerTypeId'/>"><s:property value='name'/></option>
			</s:iterator>
			</select>
		</td> 
		<td width="20%" align="right"><font color="red">*</font>公司名称：</td>
		<td width="25%">
			<input name="corporateName" id="corporateName" type="text" value=""/>
		</td>
		<td width="20%" align="right"><font color="red">*</font>联系人姓名：</td>
		<td width="25%"> 
			<input name="contactsName" id="contactsName" type="text" value="" />
		</td> 
	</tr>
    <tr> 
		
		<td width="20%" align="right"><font color="red">*</font>手机号码：</td>
		<td width="25%"> 
			<input name="loginInfo.mobile" id="mobile" type="text" value="" />
		</td>
	<!--删除 	<td width="20%" align="right"><font color="red">*</font>邮箱地址：</td>
		<td width="25%"> 
			<input name="loginInfo.email" id="email" type="text" value="" />
		</td> -->
		<td width="20%" align="right">证件类型：</td>
		<td width="25%">
			<select name="n_CertificateType" id="n_CertificateType">
	        <option value="0" selected="selected">身份证</option>
	        <option value="1">护照</option>
	        <option value="2">回乡证</option>
	        </select>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">证件号码：</td>
		<td width="25%">
			<input name="certificateNumber" id="certificateNumber" type="text" />
		</td>
		<td width="20%" align="right">联系人所在部门：</td>
		<td width="25%"> 
			<input name="contactsDepartment" id="contactsDepartment" type="text"/>
		</td>
		<td width="20%" align="right">固定电话：</td>
		<td width="25%">
			<input name="fixedPhone" id="fixedPhone" type="text" />
		</td> 
	</tr>
	 <tr> 
		
		<td width="20%" align="right">公司所在地：</td>
		<td width="25%">
			<input name="corporateLocation" id="corporateLocation" type="text" />
		</td>
		<td width="20%" align="right">公司性质：</td>
		<td width="25%">
			<input name="corporateProperty" id="corporateProperty" type="text" />
		</td> 
		<td width="20%" align="right">公司行业：</td>
		<td width="25%">
			<input name="corporateTrade" id="corporateTrade" type="text" />
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">企业人数：</td>
		<td width="25%">
			<input name="n_EnterpriseNumberOfPeople" id="n_EnterpriseNumberOfPeople" type="text" />
		</td>
		<td width="20%" align="right">成立日期：</td>
		<td width="25%">
			<input name="d_FoundDate"    id="d_FoundDate" type="text" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:(new Date())})" />
		</td> 
		<td width="20%" align="right">邮政编码：</td>
		<td width="25%">
			<input name="postalcode" id="postalcode" type="text" />
		</td> 
	</tr>
		<tr> 
		
		<td width="20%" align="right">组织代码：</td>
		<td width="25%">
			<input name="organizationCode" id="organizationCode" type="text" />
		</td>
		<td width="20%" align="right">企业法定代表人：</td>
		<td width="25%">
			<input name="enterpriseLegalRepresentativ" id="enterpriseLegalRepresentativ" type="text"  />
		</td> 
		<td width="20%" align="right">企业域名：</td>
		<td width="25%">
			<input name="enterpriseRealmName" id="enterpriseRealmName" type="text" />
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">上传营业执照图片：</td>
		<td width="25%"   colspan="6">
			<input name="myFile" id="uploadBusinessLicencePictur" type="file" />
		</td>
	</tr>
	<tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个性信息</td>
	</tr>
	<tr> 
	
		<td width="20%" align="right">QQ号：</td>
		<td width="25%" >
			<input name="personalityInfo.qqNumber" id="qqNumber" type="text"/>
		</td>
			<td width="20%" align="right">微博地址：</td>
		<td width="25%"  colspan="4">
			<input name="personalityInfo.microblogAddress" id="microblogAddress" type="text" />
		</td>
	</tr>
		<tr> 
		<td width="20%" align="right">个性签名：</td>
		<td width="25%"   colspan="6">
		<textarea style="width:557px;height:30px"  name="personalityInfo.personalityAutograph" id="personalityAutograph"   >
		</textarea>
		</td>
	</tr>
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<INPUT class="saveBtn" TYPE="submit" value="">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value="">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form>
</div>
<SCRIPT LANGUAGE="JavaScript">

function gotoList(){
    location.href="/userInfo/commercialTenantBasicInfo_pageList.action";
}

</SCRIPT>
</BODY>
<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>