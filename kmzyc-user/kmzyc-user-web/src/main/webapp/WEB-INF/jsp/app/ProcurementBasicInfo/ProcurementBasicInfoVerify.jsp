<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审核采购商信息</title>
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
$("#ProcurementBasicInfoUpdate").validate({
	rules: { 
		"loginInfo.loginAccount":{required: true,rangelength:[6,20]},
		"corporateName":{required: true,maxlength:16,userName:true},
		"contactsName":{required: true,maxlength:16,userName:true},
		"loginInfo.mobile":{required: true,cellphone:true,unusualChar:true},
		"loginInfo.email":{required: true,email:true,unusualChar:true,maxlength:30},
		"contactsDepartment":{maxlength:16,unusualChar:true},
		"certificateNumber":{maxlength:30,checkCertificateNumber:true,unusualChar:true},
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
<s:set name="parent_name" value="'采购商管理'" scope="request"/>
<s:set name="name" value="'采购商审核列表'" scope="request"/>
<s:set name="son_name" value="'审核'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form enctype="multipart/form-data" id="ProcurementBasicInfoUpdate" action="commercialTenantBasicInfo_updateProcurement.action" method="POST"  namespace='/userInfo'>
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
		<td colspan="6" align="left" class="edit_title">采购商信息</td>
					<input name="loginInfo.loginAccount" id="loginAccount" disabled type="hidden" value="<s:property value='loginInfo.loginAccount'/>"/>
			<input name="loginInfo.n_LoginId" id="n_LoginId" readonly type="hidden" value="<s:property value='loginInfo.n_LoginId'/>" />
			<input name="accountInfo.n_AccountId" id="n_AccountId" type="hidden" value="<s:property value='accountInfo.n_AccountId'/>" readonly/>
		 <input name="loginInfo.n_CustomerTypeId" id="n_CustomerTypeId" type="hidden" value="<s:property value='loginInfo.n_CustomerTypeId'/>" readonly/>
	
	
	
	</tr>
      <tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;登录信息</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">会员账号：</td>
		<td width="20%"> 
			<s:property value="loginInfo.loginAccount"/>
			
		</td>
		<td width="20%" align="right">邮箱地址：</td>
		<td width="20%"> 
			<s:property value="loginInfo.email"/>
		</td>
		<td width="20%" align="right">手机号码：</td>
		<td width="20%"> 
			<s:property value="loginInfo.mobile"/>
		</td>
	
	</tr>
	
	    <tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户基本信息</td>
	</tr>
	
	<tr> 
		<td width="13%" align="right">公司名称：</td>
		<td width="20%">
			<s:property value="commercialTenantBasicInfo.corporateName"/>
		</td>
		
			<td width="13%" align="right">工商注册号：</td>
		<td width="20%">
			<s:property value="commercialTenantBasicInfo.businessLicenceRegister"/>
		</td>
		
		
			<td width="13%" align="right">信用等级：</td>
		<td width="20%">
			<s:property value="commercialTenantBasicInfo.creditRating"/>
		</td>
	
		</tr>
		
		<tr>
		
				<td width="13%" align="right">业务联系人：</td>
		<td width="13%"> 
		<s:property value="commercialTenantBasicInfo.contactsName"/>
 		</td>
		<td width="13%" align="right" >业务联系方式：</font></td>
		<td width="20%" >
		<s:property value="commercialTenantBasicInfo.fixedPhone"/>
 		
		</td>
		
			<td width="13%" align="right" >成立日期：</td>
		<td width="20%" >
		<s:date name="commercialTenantBasicInfo.foundDate" format="yyyy-MM-dd"/>
 		</tr>
	
		<tr>
		<td width="13%" align="right">公司营业范围：</td>
		<td width="20%"> 
		<s:property value="commercialTenantBasicInfo.businessScope"/>
 		
		</td>
		<td width="13%" align="right">营业执照有效期：</td>
		<td width="20%"> 
		从<s:date name="commercialTenantBasicInfo.blinceStartdate" format="yyyy-MM-dd"/>至<s:date name="commercialTenantBasicInfo.blinceEnddate" format="yyyy-MM-dd"/>
	  			<input type="hidden"   id="blinceEnddate" name="commercialTenantBasicInfo.blinceEnddate"  value="<s:property value="commercialTenantBasicInfo.blinceEnddate"/>" />
		</td> 
		<td width="13%" align="right">营业执照验证：</td>
		<td width="20%"> 
		<s:if test="commercialTenantBasicInfo.blinceVerify==0">未验证</s:if>
		<s:if test="commercialTenantBasicInfo.blinceVerify==1">已验证</s:if>
 		</td>
	</tr>
	<tr> 
		<td width="13%" align="right">总积分：</td>
		<td width="20%"> 
			<s:property value="personalityInfo.n_TotalIntegral"/>
		</td> 
		<td width="13%" align="right">可用积分：</td>
		<td width="20%"> 
			<s:property value="personalityInfo.n_AvailableIntegral"/>
		</td>
		<td width="13%" align="right">信用值：</td>
		<td width="20%"> 
			<s:property value="personalityInfo.n_IndividualCreditValue"/>
		</td>
	</tr>
	<tr>  
	<td width="13%" align="right" >营业执照图片：</td>
		<td width="20%" colspan="5"> 
		<img title="营业执照" width="200" height="110" style="cursor: pointer;" src="<s:property value='commercialTenantBasicInfo.uploadBusinessLicencePictur'/>" />
		<a href="<s:property value='commercialTenantBasicInfo.uploadBusinessLicencePictur'/>" title="单击放大" target="_blank" id="hrefId101">营业执照</a>
 		
			</td> 
	</tr>
	<tr> 
		<td width="13%" align="right"> <font color="red">*</font>企业法人姓名：</td>
		<td width="20%"> 
		<s:property value="commercialTenantBasicInfo.enterpriseLegalRepresentativ"/>

		</td> 
		<td width="13%" align="right"><font color="red">*</font>企业法人身份证号码：</td>
		<td width="20%"> 
		<s:property value="commercialTenantBasicInfo.certificateNumber"/>
 		
		</td>
			<td width="13%" align="right" >法人身份证验证：</font></td>
		<td width="20%" >
	 <s:if test="commercialTenantBasicInfo.cidVerify==0">未验证</s:if>
	<s:if test="commercialTenantBasicInfo.cidVerify==1">已验证</s:if>
 						
		</td>
	</tr>
	<tr> 
	 <td width="13%" align="right" ><font color="red">*</font>企业法人身份证：</td>
		<td width="20%" colspan="5"> 
		<img title="身份证扫描件" width="200" height="110" style="cursor: pointer;" src="<s:property value='commercialTenantBasicInfo.certificatePicture'/>" />
		<a href="<s:property value='commercialTenantBasicInfo.certificatePicture'/>" title="单击放大" target="_blank" id="hrefId101">身份证扫描件</a>
 
		</td> 
	</tr>
	<tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;增值税发票资质信息</td>
	</tr>
	
	
	<tr> 
	 <td width="13%" align="right"><font color="red">*</font>纳税人识别码：</td>
		<td width="20%"> 
	      <s:property value="commercialTenantBasicInfo.taxpayerIdnumber"/>
 
		</td> 
		<td width="13%" align="right"><font color="red">*</font>开户银行：</td>
		<td width="20%"> 
		  <s:property value="commercialTenantBasicInfo.bankOfDeposit"/>  
 		  
		</td>
			<td width="13%" align="right" >银行账号：</font></td>
		<td width="20%" >
		  <s:property value="commercialTenantBasicInfo.companyAccount"/>
 		  
		</td>
	</tr>
	<tr> 
		<td width="13%" align="right">公司注册地址：</td>
		<td width="20%"> 
		<s:property value="commercialTenantBasicInfo.corporateLocation"/>
 		</td> 
		<td width="13%" align="right"><font color="red">*</font>注册电话：</td>
		<td width="20%"> 
		 <s:property value="commercialTenantBasicInfo.mobile"/>    
 		 
		</td>
			<td width="13%" align="right" ><font color="red">*</font>增值税发票资质验证：</font></td>
		<td width="20%" >
		 <s:if test="commercialTenantBasicInfo.ataxVerify==0">未验证</s:if>
		<s:if test="commercialTenantBasicInfo.ataxVerify==1">已验证</s:if>
 		
		</td>
	</tr>
	 <tr> 
		<td width="13%" align="right"><font color="red">*</font>税务登记证(副本)验证</td>
		<td width="20%">
		 <s:if test="commercialTenantBasicInfo.trccopyVerify==0">未验证</s:if>
		<s:if test="commercialTenantBasicInfo.trccopyVerify==1">已验证</s:if>				
			 <input type="hidden" value="<s:property value="commercialTenantBasicInfo.trccopyVerify"/>"  name="commercialTenantBasicInfo.trccopyVerify" />
			
		</td> 
		<td width="13%" align="right"><font color="red">*</font>一般纳税人证书验证：</td>
		<td width="20%"> 
		<s:if test="commercialTenantBasicInfo.gtcVerify==0">未验证</s:if>
		<s:if test="commercialTenantBasicInfo.gtcVerify==1">已验证</s:if>		  
					 <input type="hidden" value="<s:property value="commercialTenantBasicInfo.gtcVerify"/>"  name="commercialTenantBasicInfo.gtcVerify" />  
		</td>
	 		<td width="13%" align="right"> </td>
		<td width="20%">
			 
		</td>
	</tr>
	
		<tr> 
		<td width="13%" align="right">企业所属区域：</td>
		<td width="20%" colspan="5"> 
			<s:if test="commercialTenantBasicInfo.n_CustomerTypeId==4">		
 	<s:select list="#request.provinceList" id="province"   disabled="true"   onchange="choiceProArea();" name="commercialTenantBasicInfo.province"  listKey="areaId" listValue="areaName"  value="#request.commercialTenantBasicInfo.province"  ></s:select>
	 <input type="hidden"   value="<s:property value="commercialTenantBasicInfo.province"/>"  name="commercialTenantBasicInfo.province" />
	
	
	<s:select list="#request.cityList" id="ci3ty"  disabled="true"  onchange="choiceCityArea();" name="commercialTenantBasicInfo.city" listKey="areaId" listValue="areaName"  value="#request.commercialTenantBasicInfo.city" ></s:select>
		 <input type="hidden"   value="<s:property value="commercialTenantBasicInfo.city"/>"  name="commercialTenantBasicInfo.city" />
	
	<s:select list="#request.areaList" id="area" disabled="true" name="commercialTenantBasicInfo.area" listKey="areaId" listValue="areaName"  value="#request.commercialTenantBasicInfo.area" 
		  ></s:select> 		
		  	 <input type="hidden"   value="<s:property value="commercialTenantBasicInfo.area"/>"  name="commercialTenantBasicInfo.area" />
		  	
		</td> 
		</s:if>
		<s:else>
		
			<s:property value="commercialTenantBasicInfo.province" />
					  	 <input type="hidden"   value="<s:property value="commercialTenantBasicInfo.province"/>"  name="commercialTenantBasicInfo.province" />
			
			<s:property value="commercialTenantBasicInfo.city" />
					  	 <input type="hidden"   value="<s:property value="commercialTenantBasicInfo.city"/>"  name="commercialTenantBasicInfo.city" />
			<s:property value="commercialTenantBasicInfo.area" />
								  	 <input type="hidden"   value="<s:property value="commercialTenantBasicInfo.area"/>"  name="commercialTenantBasicInfo.area" />
			
		
		</s:else>
	
	</tr>
	
		<tr> 
		<td width="13%" align="right">税务登记证(副本)：</td>
		<td width="20%" colspan="5"> 
	   <img title="税务登记证(副本)" width="200" height="110" style="cursor: pointer;" src="<s:property value='commercialTenantBasicInfo.taxRegCertificateCopy'/>" />
		<a href="<s:property value='commercialTenantBasicInfo.taxRegCertificateCopy'/>" title="单击放大" target="_blank" id="hrefId101">税务登记证(副本)</a>
		 <input type="hidden" value="<s:property value="commercialTenantBasicInfo.taxRegCertificateCopy"/>"  name="commercialTenantBasicInfo.taxRegCertificateCopy" />
		
			
		</td> 
	
	</tr>
	<tr> 
		<td width="13%" align="right">一般纳税人证书：</td>
		<td width="20%" colspan="5"> 
	  <img title="一般纳税人证书" width="200" height="110" style="cursor: pointer;" src="<s:property value='commercialTenantBasicInfo.taxpayerCertificate'/>" />
		<a href="<s:property value='commercialTenantBasicInfo.taxpayerCertificate'/>" title="单击放大" target="_blank" id="hrefId101">一般纳税人证书</a>
	<input type="hidden" value="<s:property value="commercialTenantBasicInfo.taxpayerCertificate"/>"  name="commercialTenantBasicInfo.taxpayerCertificate" />
	
		</td> 
	
	</tr>
 
</table>



<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
	 
			<input class="backBtn"  onclick="gotoList()" type="button" value="">
			&nbsp;&nbsp;
			<input class="btn-custom btnStyle" onClick="procurementVerify()" type="button" value="审核通过"/>
			&nbsp;&nbsp;
            <input class="btn-custom btnStyle" onClick="noPassSupplier()" type="button" value="不通过"/>
			
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form> 
</div>
<SCRIPT LANGUAGE="JavaScript">


function procurementVerify(){

	var n_LoginId=$("#n_LoginId").val();
	var blinceEnddate=$("#blinceEnddate").val();

var n_CustomerTypeId=$("#n_CustomerTypeId").val();
	 
	
	alert("审核通过！");
	location.href="/userInfo/commercialTenantBasicInfo_procurementVerifyPass.action?n_LoginId="+n_LoginId+"&n_CustomerTypeId="+4+"&blinceEnddate="+blinceEnddate;
	}


function noPassSupplier()
{

	var n_LoginId=$("#n_LoginId").val();
	var n_CustomerTypeId=$("#n_CustomerTypeId").val();
	dialog("填写不通过的理由","iframe:/userInfo/commercialTenantBasicInfo_procurementVerifyNoPass.action?n_LoginId="+n_LoginId+"&n_CustomerTypeId="+4,"550","330","iframe");

	}

function closes(){
	window.location.href="/userInfo/commercialTenantBasicInfo_procurementPageList.action";
	closeThis();
	}
function gotoList(){
    location.href="/userInfo/commercialTenantBasicInfo_procurementPageList.action";
}

</SCRIPT>
</BODY>
<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>