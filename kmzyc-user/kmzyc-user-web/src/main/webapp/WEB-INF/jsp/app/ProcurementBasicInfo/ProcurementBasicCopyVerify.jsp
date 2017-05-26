<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购商详情信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<script src="/etc/js/dialog.js"></script>
 
<script type="text/javascript">
 
 
</script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'采购商管理'" scope="request"/>
<s:set name="name" value="'采购商变更审核信息'" scope="request"/>
<s:set name="son_name" value="'审核'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form enctype="multipart/form-data" id="ProcurementBasicCopyForm" name="ProcurementBasicCopyForm"  action="/userInfo/commercialTenantBasicCopy_commercialTenantBasicCopyPass.action" method="POST"  namespace='/userInfo'>
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
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商登录信息</td>
	</tr>
	<tr> 
	<td width="13%" align="right">会员账号：</td>
		<td width="20%"> 
		<s:property value="commercialTenantBasicCopyDO.loginName"/> 
			<input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.loginId"/>"  name="commercialTenantBasicCopyDO.loginId" id="loginId"/>
			<input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.commercialCopyId"/>"    id="commercialCopyId"/>
			<input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.commercialTenantId"/>"  name="commercialTenantBasicCopyDO.commercialTenantId" />
		</td> 
	</tr>
		
	<tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;企业信息</td>
	</tr>
		
	<tr> 
		<td width="13%" align="right"><font color="red">*</font>公司名称：</td>
		<td width="20%"> 
		<s:property value="commercialTenantBasicCopyDO.corporateName"/>
	<input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.corporateName"/>"  name="commercialTenantBasicCopyDO.corporateName" />
		</td> 
		<td width="13%" align="right"><font color="red">*</font>工商注册号：</td>
		<td width="20%"> 
		<s:property value="commercialTenantBasicCopyDO.businessLicenceRegister"/>
		<input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.businessLicenceRegister"/>"  name="commercialTenantBasicCopyDO.businessLicenceRegister" />
		
		</td>
		<td width="13%" align="right"><font color="red">*</font>信用等级：</td>
		<td width="20%"> 
		<s:property value="commercialTenantBasicCopyDO.creditRating"/>
	 <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.creditRating"/>"  name="commercialTenantBasicCopyDO.creditRating" />
 
		</td>	
	</tr>
		<tr> 
		<td width="13%" align="right">业务联系人：</td>
		<td width="20%"> 
		<s:property value="commercialTenantBasicCopyDO.contactsName"/>
		<input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.contactsName"/>"  name="commercialTenantBasicCopyDO.contactsName" />
		</td>
		<td width="13%" align="right" >业务联系方式：</font></td>
		<td width="20%" >
		<s:property value="commercialTenantBasicCopyDO.fixedPhone"/>
				<input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.fixedPhone"/>"  name="commercialTenantBasicCopyDO.fixedPhone" />
		
		</td>
		
			<td width="13%" align="right" >成立日期：</td>
		<td width="20%" >
		<s:date name="commercialTenantBasicCopyDO.foundDate" format="yyyy-MM-dd"/>
		<input type="hidden" value="<s:date name="commercialTenantBasicCopyDO.foundDate" format="yyyy-MM-dd"/>"  name="commercialTenantBasicCopyDO.foundDate" />
		
		</td>
	</tr>
		<tr> 
		<td width="13%" align="right">公司营业范围：</td>
		<td width="20%"> 
		<s:property value="commercialTenantBasicCopyDO.businessScope"/>
	<input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.businessScope"/>"  name="commercialTenantBasicCopyDO.businessScope" />
		
		</td>
		<td width="13%" align="right"><font color="red">*</font>营业执照有效期：</td>
		<td width="20%"> 
		从<s:date name="commercialTenantBasicCopyDO.blinceStartdate" format="yyyy-MM-dd"/>至<s:date name="commercialTenantBasicCopyDO.blinceEnddate" format="yyyy-MM-dd"/>
			<input type="hidden" value="<s:date name="commercialTenantBasicCopyDO.blinceStartdate" format="yyyy-MM-dd"/>"  name="commercialTenantBasicCopyDO.blinceStartdate" />
		<input type="hidden" value="<s:date name="commercialTenantBasicCopyDO.blinceEnddate" format="yyyy-MM-dd"/>"  name="commercialTenantBasicCopyDO.blinceEnddate" />
	
		</td> 
		<td width="13%" align="right">营业执照验证：</td>
		<td width="20%"> 
		<s:if test="commercialTenantBasicCopyDO.blinceVerify==0">未验证</s:if>
		<s:if test="commercialTenantBasicCopyDO.blinceVerify==1">已验证</s:if>
		<input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.blinceVerify"/>"  name="commercialTenantBasicCopyDO.blinceVerify" />
		</td>
		
	</tr>
	<tr> 
		<td width="13%" align="right" ><font color="red">*</font>营业执照图片：</td>
		<td width="20%" colspan="5"> 
		<img title="营业执照" width="200" height="110" style="cursor: pointer;" src="<s:property value='commercialTenantBasicCopyDO.uploadBusinessLicencePictur'/>" />
		<a href="<s:property value='commercialTenantBasicCopyDO.uploadBusinessLicencePictur'/>" title="单击放大" target="_blank" id="hrefId101">营业执照</a>
		 <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.uploadBusinessLicencePictur"/>"  name="commercialTenantBasicCopyDO.uploadBusinessLicencePictur" />
		
			</td> 
		
	</tr>
	<tr> 
		<td width="13%" align="right"> <font color="red">*</font>企业法人姓名：</td>
		<td width="20%"> 
		<s:property value="commercialTenantBasicCopyDO.enterpriseLegalRepresentativ"/>
 <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.enterpriseLegalRepresentativ"/>"  name="commercialTenantBasicCopyDO.enterpriseLegalRepresentativ" />
		
		
		</td> 
		<td width="13%" align="right"><font color="red">*</font>企业法人身份证号码：</td>
		<td width="20%"> 
		<s:property value="commercialTenantBasicCopyDO.certificateNumber"/>
		 <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.certificateNumber"/>"  name="commercialTenantBasicCopyDO.certificateNumber" />
		
		</td>
			<td width="13%" align="right" >法人身份证验证：</font></td>
		<td width="20%" >
	 <s:if test="commercialTenantBasicCopyDO.cidVerify==0">未验证</s:if>
	<s:if test="commercialTenantBasicCopyDO.cidVerify==1">已验证</s:if>
	<input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.cidVerify"/>"  name="commercialTenantBasicCopyDO.cidVerify" />
						
		</td>
	</tr>
	
	<tr> 
		<td width="13%" align="right" ><font color="red">*</font>企业法人身份证：</td>
		<td width="20%" colspan="5"> 
		<img title="身份证扫描件" width="200" height="110" style="cursor: pointer;" src="<s:property value='commercialTenantBasicCopyDO.certificatePicture'/>" />
		<a href="<s:property value='commercialTenantBasicCopyDO.certificatePicture'/>" title="单击放大" target="_blank" id="hrefId101">身份证扫描件</a>
	 <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.certificatePicture"/>"  name="commercialTenantBasicCopyDO.certificatePicture" />

		</td> 
		
	</tr>
	
	<tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;增值税发票资质信息</td>
	</tr>
	<tr> 
		<td width="13%" align="right"><font color="red">*</font>纳税人识别码：</td>
		<td width="20%"> 
	      <s:property value="commercialTenantBasicCopyDO.taxpayerIdnumber"/>
	  	 <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.taxpayerIdnumber"/>"  name="commercialTenantBasicCopyDO.taxpayerIdnumber" />

		</td> 
		<td width="13%" align="right"><font color="red">*</font>开户银行：</td>
		<td width="20%"> 
		  <s:property value="commercialTenantBasicCopyDO.bankOfDeposit"/>  
		 <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.bankOfDeposit"/>"  name="commercialTenantBasicCopyDO.bankOfDeposit" />
		  
		</td>
			<td width="13%" align="right" >银行账号：</font></td>
		<td width="20%" >
		  <s:property value="commercialTenantBasicCopyDO.companyAccount"/>
 <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.companyAccount"/>"  name="commercialTenantBasicCopyDO.companyAccount" />
		  
		</td>
	</tr>
		<tr> 
		<td width="13%" align="right">公司注册地址：</td>
		<td width="20%"> 
		<s:property value="commercialTenantBasicCopyDO.corporateLocation"/>
 <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.corporateLocation"/>"  name="commercialTenantBasicCopyDO.corporateLocation" />
		</td> 
		<td width="13%" align="right"><font color="red">*</font>注册电话：</td>
		<td width="20%"> 
		 <s:property value="commercialTenantBasicCopyDO.mobile"/>    
		 <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.mobile"/>"  name="commercialTenantBasicCopyDO.mobile" />
		 
		</td>
			<td width="13%" align="right" ><font color="red">*</font>增值税发票资质验证：</font></td>
		<td width="20%" >
		 <s:if test="commercialTenantBasicCopyDO.ataxVerify==0">未验证</s:if>
		<s:if test="commercialTenantBasicCopyDO.ataxVerify==1">已验证</s:if>
	 <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.ataxVerify"/>"  name="commercialTenantBasicCopyDO.ataxVerify" />
		
		</td>
	</tr>
	<tr> 
		<td width="13%" align="right">企业所属区域：</td>
		<td width="20%" colspan="5"> 
		
 	<s:select list="#request.provinceList" id="province"   disabled="true"   onchange="choiceProArea();" name="commercialTenantBasicCopyDO.province"  listKey="areaId" listValue="areaName"  value="#request.commercialTenantBasicCopyDO.province"  ></s:select>
	 <input type="hidden"   value="<s:property value="commercialTenantBasicCopyDO.province"/>"  name="commercialTenantBasicCopyDO.province" />
	
	
	<s:select list="#request.cityList" id="ci3ty"  disabled="true"  onchange="choiceCityArea();" 	name="commercialTenantBasicCopyDO.city" listKey="areaId" listValue="areaName"  value="#request.commercialTenantBasicCopyDO.city" ></s:select>
		 <input type="hidden"   value="<s:property value="commercialTenantBasicCopyDO.city"/>"  name="commercialTenantBasicCopyDO.city" />
	
	<s:select list="#request.areaList" id="area" disabled="true" name="commercialTenantBasicCopyDO.area" listKey="areaId" listValue="areaName"  value="#request.commercialTenantBasicCopyDO.area" 
		  ></s:select> 		
		  	 <input type="hidden"   value="<s:property value="commercialTenantBasicCopyDO.area"/>"  name="commercialTenantBasicCopyDO.area" />
		  	
		</td> 
	
	</tr>
	<tr> 
		<td width="13%" align="right"><font color="red">*</font>税务登记证(副本)验证</td>
		<td width="20%">
		 <s:if test="commercialTenantBasicCopyDO.trccopyVerify==0">未验证</s:if>
		<s:if test="commercialTenantBasicCopyDO.trccopyVerify==1">已验证</s:if>				
			 <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.trccopyVerify"/>"  name="commercialTenantBasicCopyDO.trccopyVerify" />
			
		</td> 
		<td width="13%" align="right"><font color="red">*</font>一般纳税人证书验证：</td>
		<td width="20%"> 
		<s:if test="commercialTenantBasicCopyDO.gtcVerify==0">未验证</s:if>
		<s:if test="commercialTenantBasicCopyDO.gtcVerify==1">已验证</s:if>		  
					 <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.gtcVerify"/>"  name="commercialTenantBasicCopyDO.gtcVerify" />  
		</td>
	 		<td width="13%" align="right">商户审核变更状态：</td>
		<td width="20%">
				<s:if test="commercialTenantBasicCopyDO.reviewChange==0">提交申请</s:if>
				<s:if test="commercialTenantBasicCopyDO.reviewChange==1">审核通过</s:if>
			<s:if test="commercialTenantBasicCopyDO.reviewChange==2">审核不通过</s:if>
		</td>
	</tr>
	
		<tr> 
		<td width="13%" align="right">税务登记证(副本)：</td>
		<td width="20%" colspan="5"> 
	   <img title="税务登记证(副本)" width="200" height="110" style="cursor: pointer;" src="<s:property value='commercialTenantBasicCopyDO.taxRegCertificateCopy'/>" />
		<a href="<s:property value='commercialTenantBasicCopyDO.taxRegCertificateCopy'/>" title="单击放大" target="_blank" id="hrefId101">税务登记证(副本)</a>
		 <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.taxRegCertificateCopy"/>"  name="commercialTenantBasicCopyDO.taxRegCertificateCopy" />
		
			
		</td> 
	
	</tr>
	<tr> 
		<td width="13%" align="right">一般纳税人证书：</td>
		<td width="20%" colspan="5"> 
	  <img title="一般纳税人证书" width="200" height="110" style="cursor: pointer;" src="<s:property value='commercialTenantBasicCopyDO.taxpayerCertificate'/>" />
		<a href="<s:property value='commercialTenantBasicCopyDO.taxpayerCertificate'/>" title="单击放大" target="_blank" id="hrefId101">一般纳税人证书</a>
	<input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.taxpayerCertificate"/>"  name="commercialTenantBasicCopyDO.taxpayerCertificate" />
	
		</td> 
	
	</tr>
 
	
	 
		
	
 
 
</table>

<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="backBtn"  onclick="gotoList()" type="button" value="">
		&nbsp;&nbsp;
			<input class="btn-custom btnStyle" onClick="procurementBasicCopyVerify()" type="button" value="审核通过"/>
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

function gotoList(){
    location.href="/userInfo/commercialTenantBasicCopy_VeriPageList.action";
}

function procurementBasicCopyVerify(){
 
var commercialCopyId=$("#commercialCopyId").val();
	alert("审核通过！");
	 document.ProcurementBasicCopyForm.action="/userInfo/commercialTenantBasicCopy_commercialTenantBasicCopyPass.action?commercialCopyId="+commercialCopyId;
	 document.ProcurementBasicCopyForm.submit();
	}


function noPassSupplier()
{
 
 var commercialCopyId=$("#commercialCopyId").val();
 var loginId=$("#loginId").val();
dialog("填写不通过的理由","iframe:/userInfo/commercialTenantBasicCopy_procurementVerifyNoPassSupplier.action?commercialCopyId="+commercialCopyId+"&loginId="+loginId,"550","330","iframe");
 
	}
function closes(){
	window.location.href="/userInfo/commercialTenantBasicCopy_VeriPageList.action";
	closeThis();
	}


</SCRIPT>
</BODY>
</HTML>