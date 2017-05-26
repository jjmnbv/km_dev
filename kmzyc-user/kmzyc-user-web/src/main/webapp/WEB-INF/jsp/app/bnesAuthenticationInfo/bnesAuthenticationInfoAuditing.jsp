<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实名认证审核详情信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.validate.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/messages_cn.js" type="text/javascript"></Script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript">
$(document).ready(function(){
    var n_PersonalId  =  $("#n_PersonalId").val();

    var n_CommercialTenantId  =  $("#n_CommercialTenantId").val();

    var examinationValue = $("#examinationValue").val();
    
    if(examinationValue=="1"){
		$(".saveBtn").hide();
    }
    
    if(n_PersonalId!=""){
       $(".commercialTenantBasicInfo").hide();
       $(".personalBasicInfo").show();
    }
    
    if(n_CommercialTenantId!=""){
        $(".personalBasicInfo").hide();
        $(".commercialTenantBasicInfo").show();
     }

    
 });
</script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'安全认证'" scope="request"/>
<s:set name="name" value="'实名认证'" scope="request"/>
<s:set name="son_name" value="'实名认证审核'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form enctype="multipart/form-data" id="bnesAuthenticationInfoUpdate" action="bnesAuthenticationInfo_update.action" method="POST"  namespace='/accounts'>
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
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
		<td colspan="2" align="left" class="edit_title">实名认证审核信息</td>
	</tr>
   
	
	<tr> 
		<td width="20%" align="right">账户号：</td>
		<td width="80%"> 
			<s:property value="accountInfo.accountLogin"/>
			
		</td>
	</tr>
	
	
	<tr> 
		<td width="20%" align="right">客户类别：</td>
		<td width="80%">
			<s:property value="accountInfo.customerName"/>
		</td>
	</tr>
	
    <tr class="personalBasicInfo"> 
		<td width="20%" align="right">姓名：</td>
		<td width="80%"> 
			<s:property value="personalBasicInfo.name"/>
			<input type="hidden" id="n_PersonalId" name="personalBasicInfo.n_PersonalId" value="<s:property value='personalBasicInfo.n_PersonalId'/>"/>
			
		</td>
	</tr>
    
	<tr class="personalBasicInfo"> 
		<td width="20%" align="right">手机号码：</td>
		<td width="80%"> 
			<s:property value="personalBasicInfo.mobile"/>
		</td>
	</tr>
	
		<tr class="personalBasicInfo"> 
		<td width="20%" align="right">证件类型：</td>
		<td width="80%">
			<s:if test="personalBasicInfo.n_CertificateType==0">
	       	 身份证
			</s:if>
		
			<s:if test="personalBasicInfo.n_CertificateType==1">
			护照
			</s:if>
			
			<s:if test="personalBasicInfo.n_CertificateType==2">
			回乡证
			</s:if>
		</td>
	</tr>
	<tr class="personalBasicInfo"> 
		<td width="20%" align="right">证件号码：</td>
		<td width="80%">
			<s:property value="personalBasicInfo.certificateNumber"/>
		</td>
	</tr>
	
	<tr class="personalBasicInfo"> 
		<td width="20%" align="right">证件图片正反面：</td>
		<td width="80%"> 
		<img title="证件图片正反面" width="100" height="80" style="cursor: pointer;" src="/UploadImages/<s:property value='personalBasicInfo.certificatePicture'/>" />
		</td>
	</tr>
	
	<tr class="personalBasicInfo"> 
		<td width="20%" align="right">证件有效时间：</td>
		<td width="80%"> 
			<s:property value="personalBasicInfo.certificateActiveTime"/>
		</td>
	</tr>
	
	<tr class="personalBasicInfo"> 
		<td width="20%" align="right">常用地址：</td>
		<td width="80%"> 
			<s:property value="personalBasicInfo.location"/>
		</td>
	</tr>
	
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">法人代表真实姓名：</td>
		<td width="80%"> 
			<s:property value="commercialTenantBasicInfo.enterpriseLegalRepresentativ"/>
			<input type="hidden" id="n_CommercialTenantId" name="commercialTenantBasicInfo.n_CommercialTenantId" value="<s:property value='commercialTenantBasicInfo.n_CommercialTenantId'/>"/>
		</td>
	</tr>
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">手机号码：</td>
		<td width="80%"> 
			<s:property value="commercialTenantBasicInfo.mobile"/>
		</td>
	</tr>
	
		<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">证件类型：</td>
		<td width="80%">
			<s:if test="commercialTenantBasicInfo.n_CertificateType==0">
	       	 身份证
			</s:if>
		
			<s:if test="commercialTenantBasicInfo.n_CertificateType==1">
			护照
			</s:if>
			
			<s:if test="commercialTenantBasicInfo.n_CertificateType==2">
			回乡证
			</s:if>
		</td>
	</tr>
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">证件号码：</td>
		<td width="80%">
			<s:property value="commercialTenantBasicInfo.certificateNumber"/>
		</td>
	</tr>
	
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">证件图片正反面：</td>
		<td width="80%"> 
		<img title="证件图片正反面" width="100" height="80" style="cursor: pointer;" src="/UploadImages/<s:property value='commercialTenantBasicInfo.certificatePicture'/>" />
		</td>
	</tr>
	
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">法人代表归属地：</td>
		<td width="80%"> 
			<s:property value="commercialTenantBasicInfo.location"/>
		</td>
	</tr>
	
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">企业名称：</td>
		<td width="80%"> 
			<s:property value="commercialTenantBasicInfo.corporateName"/>
		</td>
	</tr>
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">营业执照注册号：</td>
		<td width="80%"> 
			<s:property value="commercialTenantBasicInfo.businessLicenceRegister"/>
		</td>
	</tr>
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">营业执照所在地：</td>
		<td width="80%"> 
			<s:property value="commercialTenantBasicInfo.businessLicenceLocation"/>
		</td>
	</tr>
	
     <tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">营业期限：</td>
		<td width="80%"> 
			<s:property value="commercialTenantBasicInfo.businessTimeLimit"/>
		</td>
	</tr>
	 <tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">公司所在地：</td>
		<td width="80%">
			<s:property value="commercialTenantBasicInfo.corporateLocation"/>
		</td>
	</tr>
	
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">联系电话：</td>
		<td width="80%">
			<s:property value="commercialTenantBasicInfo.fixedPhone"/>
		</td>
	</tr>
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">营业执照扫描副本：</td>
		<td width="80%">
		<img title="营业执照扫描副本" width="100" height="80" style="cursor: pointer;" src="/UploadImages/<s:property value='commercialTenantBasicInfo.uploadBusinessLicencePictur'/>" />
		</td>
	</tr>
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">组织机构代码：</td>
		<td width="80%">
			<s:property value="commercialTenantBasicInfo.organizationCode"/>
		</td>
	</tr>
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">营业范围：</td>
		<td width="80%">
			<s:property value="commercialTenantBasicInfo.businessScope"/>
		</td>
	</tr>
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">注册资金：</td>
		<td width="80%">
			<s:property value="commercialTenantBasicInfo.registerBankroll"/>
		</td>
	</tr>
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">传真：</td>
		<td width="80%">
			<s:property value="commercialTenantBasicInfo.fixedPhone"/>
		</td>
	</tr>
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">银行开户名：</td>
		<td width="80%">
			<s:property value="commercialTenantBasicInfo.bankAccountName"/>
		</td>
	</tr>
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">开户银行：</td>
		<td width="80%">
			<s:property value="commercialTenantBasicInfo.bankOfDeposit"/>
		</td>
	</tr>
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">开户银行支行名称：</td>
		<td width="80%">
			<s:property value="commercialTenantBasicInfo.bankOfDepositBranchName"/>
		</td>
	</tr>
	<tr class="commercialTenantBasicInfo"> 
		<td width="20%" align="right">公司对公账户：</td>
		<td width="80%">
			<s:property value="commercialTenantBasicInfo.companyAccount"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">认证方式：</td>
		<td width="80%">
			<input type="radio" name="authenticationMode" value="0" checked="checked" />快捷认证
			<input type="radio" name="authenticationMode" value="1" />普通认证
		</td>
	</tr>

	<tr> 
		<td width="20%" align="right">认证审批是否通过：</td>
		<td width="80%">
			<input type="radio" name="examinationValue" value="1" checked="checked" />是
			<input type="radio" name="examinationValue" value="0" />否
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">认证描述备注：</td>
		<td width="80%">
			<input type="hidden" name="bnesAuthenticationInfo.authenticationId" value="<s:property value='bnesAuthenticationInfo.authenticationId'/>"/>
			
			<input type="hidden" name="bnesAuthenticationInfo.examinationValue" id="examinationValue" value="<s:property value='bnesAuthenticationInfo.examinationValue'/>"/>
			
			<textarea name="certificateDiscription" rows="6" cols="50"></textarea>
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
    location.href="/accounts/bnesAuthenticationInfo_returnPageList.action";
}

</SCRIPT>
</BODY>
</HTML>