<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商户信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js">
</script>
		<script src="/etc/js/dialog.js">
</script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js">
</script>
		<script type="text/javascript" src="/etc/js/messages_cn.js">
</script>
		<script type="text/javascript" src="/etc/js/jsAddress.js">
</script>
 
</head>
<body>
<script type="text/javascript">
$().ready(function() {

 
$("#commercialTenantBasicInfoUpdate").validate({
	rules: { 
		"loginInfo.loginAccount":{required: true,rangelength:[6,20]},
		"corporateName":{required: true},
		"n_EnterpriseNumberOfPeople":{maxlength:16,number:true,unusualChar:true},
		"businessLicenceRegister":{required: true},
		"d_FoundDate":{maxlength:16,afterDate:true },
		"myFile":{checkFile:true }
 		 
		},
		success: function (label){
            label.removeClass("checked").addClass("checked");
        }
	});
	 
});

function choiceProArea()
{

		var warehouseHtml;
		if($("#province").val()=="" || $("#province").val()=="0"){
			warehouseHtml = '<option value="">--请选择城市--</option>';
			$('#ci3ty').html(warehouseHtml)
			return false;
		}
		var areaId=+$('#province').val()
		$.ajax({
			dataType:'json',
			url:'/userInfo/commercialTenantBasicCopy_getChildrenArea.action?areaId='+areaId,
			error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
			success:function(date){ 
           
				var size = date.length;
				for(var i=0;i<size;i++){
					warehouseHtml += '<option value="'+date[i].areaId+'">'+date[i].areaName+'</option>';
				}
				$('#ci3ty').html(warehouseHtml);
				$.ajax({
					dataType:'json',
					url:'/userInfo/commercialTenantBasicCopy_getChildrenArea.action?areaId='+date[0].areaId,
					error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
					success:function(date){
					 
						var size = date.length;
						var areaHtml
						for(var i=0;i<size;i++){
							areaHtml += '<option value="'+date[i].areaId+'">'+date[i].areaName+'</option>';
						}
						$('#area').html(areaHtml);
					}
				});
			}
		});
}

function choiceCityArea()
{
		var areaId=+$('#ci3ty').val()
		$.ajax({
					dataType:'json',
					url:'/userInfo/commercialTenantBasicCopy_getChildrenArea.action?areaId='+areaId,
					error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
					success:function(date){
						var size = date.length;
						var areaHtml
						for(var i=0;i<size;i++){
							areaHtml += '<option value="'+date[i].areaId+'">'+date[i].areaName+'</option>';
						}
						$('#area').html(areaHtml);
					}
				});
}

function changeCreditRating(){
	 var blinceVerify=$("#blinceVerify").val()-0;
	 var cidVerify=$("#cidVerify").val()-0;
	 var t = blinceVerify+cidVerify;
	 var creditRating =$("#creditRating").val()-0;
	 var credeittagName=$("#creditRating").get(0).tagName;
	 if($("#creditRating").get(0).tagName==('INPUT')){
		 $("#creditRating").attr("value",t);// 
	 }
	 else {
		 
		 if(t<=2&&creditRating<=2){
			 $("#creditRating").empty(); 
			 $("#creditRating").append('<option value='+t+'>'+t+'</option>');

		 }
		 
	 }
	 
	 
}
</script>
<!-- 导航栏 -->
<s:set name="parent_name" value="'采购商管理'" scope="request"/>
<s:set name="name" value="'采购商审核列表'" scope="request"/>
<s:set name="son_name" value="'修改'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form enctype="multipart/form-data" id="commercialTenantBasicInfoUpdate" action="commercialTenantBasicInfo_updateProcurement.action" method="POST"  namespace='/userInfo'>
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
	</tr>
    <tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;登录信息</td>
	</tr>
	
	<tr> 
		<td width="13%" align="right"><font color="red">*</font>会员账号：</td>
		<td width="20%" > 
			<input name="loginInfo.loginAccount" id="loginAccount" disabled type="text" value="<s:property value='loginInfo.loginAccount'/>"/>
			<input name="loginInfo.n_LoginId" id="n_LoginId" readonly="readonly" type="hidden" value="<s:property value='loginInfo.n_LoginId'/>" />
			<input name="accountInfo.n_AccountId" id="n_AccountId" type="hidden" value="<s:property value='accountInfo.n_AccountId'/>" readonly="readonly"/>
	 <input name="loginInfo.n_CustomerTypeId" id="n_CustomerTypeId" type="hidden" value="<s:property value='loginInfo.n_CustomerTypeId'/>" readonly="readonly"/>
 
		</td>
			<td width="13%" align="right"><font color="red">*</font>邮箱地址：</td>
			<td width="20%" > 
						<input name="loginInfo.email" id="email"  disabled type="text" value="<s:property value='loginInfo.email'/>"/>
			
			</td>
				<td width="13%" align="right"><font color="red">*</font>手机号码：</td>
				<td width="20%" > 
				
		<input name="loginInfo.mobile" id="mobile"  type="text"  disabled value="<s:property value='loginInfo.mobile'/>"/>
			
			</td>
		
	</tr>
	<tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;采购商基本信息</td>
	</tr>
	<tr>
	<td width="13%" align="right"><font color="red">*</font>商户类别：</td>
	<td width="20%"> 
			<select name="loginInfo.n_CustomerTypeId"  disabled  >
			<s:iterator value="customerTypeList" id="customerTypeList">
				<s:if test="customerTypeId==loginInfo.n_CustomerTypeId">
					<option selected="selected" value="<s:property value='customerTypeId'/>"><s:property value='name'/></option>
				</s:if>
				
				<s:else>
					<option value="<s:property value='customerTypeId'/>"><s:property value='name'/></option>
				</s:else>
				
			</s:iterator>
			</select>
		</td>
			<td width="13%" align="right"><font color="red">*</font>公司名称：</td>
		<td width="20%"> 
		<input type="text" value="<s:property value="commercialTenantBasicInfo.corporateName"/>"  name="commercialTenantBasicInfo.corporateName" id="corporateName"/>
		<input name="commercialTenantBasicInfo.n_CommercialTenantId" id="n_CommercialTenantId" type="hidden" value="<s:property value="commercialTenantBasicInfo.n_CommercialTenantId"/>" />
			<input name="personalityInfo.n_PersonalityId" id="n_PersonalityId" readonly="readonly" type="hidden" value="<s:property value="personalityInfo.n_PersonalityId"/>" />
		
		</td> 
		<td width="13%" align="right"><font color="red">*</font>工商注册号：</td>
		<td width="20%"> 
		<input type="text" value="<s:property value="commercialTenantBasicInfo.businessLicenceRegister"/>"  name="commercialTenantBasicInfo.businessLicenceRegister" />
		</td>
	</tr>
    <tr> 
		 <td width="13%" align="right"><font color="red">*</font>信用等级：</td>
		<td width="20%"> 
		 <s:if test="commercialTenantBasicInfo.creditRating>=2">
		 <select name="commercialTenantBasicInfo.creditRating"  id="creditRating">
								<option value="2"
								<s:if test="commercialTenantBasicInfo.creditRating==2">selected</s:if> >
								2
								</option>
								<option value="3"
									<s:if test="commercialTenantBasicInfo.creditRating==3">selected</s:if> >
								3
								</option> 
								<option value="4"
									<s:if test="commercialTenantBasicInfo.creditRating==4">selected</s:if> >
								4
								</option> 
								<option value="5"
									<s:if test="commercialTenantBasicInfo.creditRating==5">selected</s:if> >
								5
								</option> 
								<option value="6"
									<s:if test="commercialTenantBasicInfo.creditRating==6">selected</s:if> >
								6
								</option> 
								<option value="7"
									<s:if test="commercialTenantBasicInfo.creditRating==7">selected</s:if> >
								7
								</option> 
								<option value="8"
									<s:if test="commercialTenantBasicInfo.creditRating==8">selected</s:if> >
								8
								</option> 
								<option value="9"
									<s:if test="commercialTenantBasicInfo.creditRating==9">selected</s:if> >
								9
								</option> 
							</select>
		 </s:if>
		 <s:if test="commercialTenantBasicInfo.creditRating<2">
		 <input type="text" id="creditRating" value="<s:property value="commercialTenantBasicInfo.creditRating"/>"  name="commercialTenantBasicInfo.creditRating" readonly="readonly"/>
		 </s:if>
		</td>
			<td width="13%" align="right"> 业务联系人：</td>
		<td width="20%"> 
		<input type="text" value="<s:property value="commercialTenantBasicInfo.contactsName"/>"  name="commercialTenantBasicInfo.contactsName" />				
		</td> 
		<td width="13%" align="right"><font color="red">*</font>业务联系方式：</td>
		<td width="20%"> 
		<input type="text" value="<s:property value="commercialTenantBasicInfo.fixedPhone"/>"  name="commercialTenantBasicInfo.fixedPhone" />				
		</td>
	 
	</tr>
	<tr> 
	<td width="13%" align="right"><font color="red">*</font>营业执照有效期：</td>
		<td width="20%" colspan="3"> 
		从<input name="blinceStartdate"    id="blinceStartdate" type="text" value="<s:date name='commercialTenantBasicInfo.blinceStartdate' format="yyyy-MM-dd"/>"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:('#F{$dp.$D(\'blinceEnddate\')}')})"/>
		 	<span id="megblinceStartdate"></span>至
		 	<input name="blinceEnddate"    id="blinceEnddate" type="text" value="<s:date name='commercialTenantBasicInfo.blinceEnddate' format="yyyy-MM-dd"/>"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:('#F{$dp.$D(\'blinceStartdate\')}')})"/>
		 	<span id="megblinceEnddate"></span>
		</td> 
			<td width="13%" align="right" >营业执照验证：</td>
		<td width="20%" >
		<select name="commercialTenantBasicInfo.blinceVerify"  id="blinceVerify" onchange="changeCreditRating()">
								<option value="0"
								<s:if test="commercialTenantBasicInfo.blinceVerify==0">selected</s:if> >
								  未验证
								</option>
								<option value="1"
									<s:if test="commercialTenantBasicInfo.blinceVerify==1">selected</s:if> >
								 已验证
								</option> 
							</select>
		</td>
	</tr>
	 <tr>		
			<td width="13%" align="right" ><font color="red">*</font>上传营业执照图片：</td>
		<td width="20%" colspan="5"> 
		<img title="营业执照" width="280" height="120" style="cursor: pointer;" src="<s:property value='commercialTenantBasicInfo.uploadBusinessLicencePictur'/>" />
	 		 <s:file name="uploadFileImage"  id="uploadFileImage" class="rg-input rg-file" placeholder="" />
			<span id="megUploadBusinessLicencePictur"></span>
			<a href="<s:property value='commercialTenantBasicInfo.uploadBusinessLicencePictur'/>" title="单击放大" target="_blank" id="hrefId101">营业执照</a>
		</td> 
		
	</tr>
	<tr> 
	<td width="13%" align="right"> <font color="red">*</font>企业法人姓名：</td>
		<td width="20%"> 
		<input type="text" value="<s:property value="commercialTenantBasicInfo.enterpriseLegalRepresentativ"/>"  name="commercialTenantBasicInfo.enterpriseLegalRepresentativ" />
		</td> 
		<td width="13%" align="right"><font color="red">*</font>企业法人身份证号码：</td>
		<td width="20%"> 
		<input type="text" value="<s:property value="commercialTenantBasicInfo.certificateNumber"/>"  name="commercialTenantBasicInfo.certificateNumber" />
		</td>
			<td width="13%" align="right" >法人身份证验证：</font></td>
		<td width="20%" >
		<select name="commercialTenantBasicInfo.cidVerify"  id="cidVerify" onchange="changeCreditRating()">
								<option value="0"
								<s:if test="commercialTenantBasicInfo.cidVerify==0">selected</s:if>>
								  未验证
								</option>
								<option value="1"
									<s:if test="commercialTenantBasicInfo.cidVerify==1">selected</s:if>>
								 已验证
								</option> 
							</select>
		</td>
	</tr>
		<tr>  
		<td width="13%" align="right" ><font color="red">*</font>上传企业法人身份证：</td>
		<td width="20%" colspan="5"> 
		<img title="身份证扫描件" width="280" height="120" style="cursor: pointer;" src="<s:property value='commercialTenantBasicInfo.certificatePicture'/>" />
	 		 <s:file name="otherSidImage"  id="otherSidImage" class="rg-input rg-file" placeholder="" />
			<span id="megUploadBusinessLicencePictur"></span>
			<a href="<s:property value='commercialTenantBasicInfo.certificatePicture'/>" title="单击放大" target="_blank" id="hrefId101">身份证扫描件</a>
		</td> 
	</tr>
		<tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;增值税发票资质信息</td>
	</tr> 
	 <tr> 
		<td width="13%" align="right"><font color="red">*</font>纳税人识别码：</td>
		<td width="20%"> 
	        <input type="text" value="<s:property value="commercialTenantBasicInfo.taxpayerIdnumber"/>"  name="commercialTenantBasicInfo.taxpayerIdnumber" />
		</td> 
		<td width="13%" align="right"><font color="red">*</font>开户银行：</td>
		<td width="20%"> 
		    <input type="text" value="<s:property value="commercialTenantBasicInfo.bankOfDeposit"/>"  name="commercialTenantBasicInfo.bankOfDeposit" />
		</td>
			<td width="13%" align="right" >银行账号：</font></td>
		<td width="20%" >
		    <input type="text" value="<s:property value="commercialTenantBasicInfo.companyAccount"/>"  name="commercialTenantBasicInfo.companyAccount" />
		</td>
	</tr>
		<tr> 
		<td width="13%" align="right">公司注册地址：</td>
		<td width="20%"> 
	        <input type="text" value="<s:property value="commercialTenantBasicInfo.corporateLocation"/>"  name="commercialTenantBasicInfo.corporateLocation" />
		</td> 
		<td width="13%" align="right"><font color="red">*</font>注册电话：</td>
		<td width="20%"> 
		    		    <input type="text" value="<s:property value="commercialTenantBasicInfo.mobile"/>"  name="commercialTenantBasicInfo.mobile" />
		    
		</td>
			<td width="13%" align="right" ><font color="red">*</font>增值税发票资质验证：</font></td>
		<td width="20%" >
		   		<select name="commercialTenantBasicInfo.ataxVerify"  id="ataxVerify">
								<option value="0"
								<s:if test="commercialTenantBasicInfo.ataxVerify==0">selected</s:if>>
								  未验证
								</option>
								<option value="1"
									<s:if test="commercialTenantBasicInfo.ataxVerify==1">selected</s:if>>
								 已验证
								</option> 
							</select>
		</td>
	</tr>
	<tr> 
		<td width="13%" align="right"><font color="red">*</font>税务登记证(副本)验证</td>
		<td width="20%">		
		
				   		<select name="commercialTenantBasicInfo.trccopyVerify"  id="trccopyVerify">
								<option value="0"
								<s:if test="commercialTenantBasicInfo.trccopyVerify==0">selected</s:if>>
								  未验证
								</option>
								<option value="1"
									<s:if test="commercialTenantBasicInfo.trccopyVerify==1">selected</s:if>>
								 已验证
								</option> 
							</select>			
		</td> 
		<td width="13%" align="right"><font color="red">*</font>一般纳税人证书验证：</td>
		<td width="20%"> 	   
		<select name="commercialTenantBasicInfo.gtcVerify"  id="gtcVerify">
								<option value="0"
								<s:if test="commercialTenantBasicInfo.gtcVerify==0">selected</s:if>>
								  未验证
								</option>
								<option value="1"
									<s:if test="commercialTenantBasicInfo.gtcVerify==1">selected</s:if>>
								 已验证
								</option> 
							</select>	 
		</td>
		<td width="13%" align="right"></td>
		<td width="20%">    
			
		</td>
	</tr>
	<tr> 
		<td width="13%" align="right">企业所属区域：</td>
		<td width="20%" colspan="5"> 
	       
				 
				<s:if test="commercialTenantBasicInfo.n_CustomerTypeId==4">			
		<s:select list="#request.provinceList" id="province"     onchange="choiceProArea();" name="commercialTenantBasicInfo.province"  listKey="areaId" listValue="areaName"  value="#request.commercialTenantBasicInfo.province"  ></s:select>
		  					<s:select list="#request.cityList" id="ci3ty"   onchange="choiceCityArea();" name="commercialTenantBasicInfo.city"  listKey="areaId"  listValue="areaName"  value="#request.commercialTenantBasicInfo.city" 
		  						></s:select>
		  					<s:select list="#request.areaList" id="area" name="commercialTenantBasicInfo.area" listKey="areaId" listValue="areaName"  value="#request.commercialTenantBasicInfo.area" 
		  						></s:select> 
		  						
		  						 </s:if>
		  					 
		</td> 
	
	</tr>
	
	<tr> 
		<td width="13%" align="right">税务登记证(副本)：</td>
		<td width="20%" colspan="5"> 
	    <img title="税务登记证(副本)" width="280" height="120" style="cursor: pointer;" src="<s:property value='commercialTenantBasicInfo.taxRegCertificateCopy'/>" />
	 		 <s:file name="certificateCopyImage"  id="certificateCopyImage" class="rg-input rg-file" placeholder="" />
			<span id="megtaxRegCertificateCopy"></span>
			<a href="<s:property value='commercialTenantBasicInfo.taxRegCertificateCopy'/>" title="单击放大" target="_blank" id="hrefId101">税务登记证(副本)</a>   
					
		</td> 
	
	</tr>
	<tr> 
		<td width="13%" align="right">一般纳税人证书：</td>
		<td width="20%" colspan="5"> 
	  	<img title="一般纳税人证书" width="280" height="120" style="cursor: pointer;" src="<s:property value='commercialTenantBasicInfo.taxpayerCertificate'/>" />
		<s:file name="certificateImage"  id="certificateImage" class="rg-input rg-file" placeholder="" />
	    <span id="megtaxpayerCertificate"></span>
		<a href="<s:property value='commercialTenantBasicInfo.taxpayerCertificate'/>" title="单击放大" target="_blank" id="hrefId101">一般纳税人证书</a>	
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
    location.href="/userInfo/commercialTenantBasicInfo_procurementPageList.action";
}

</SCRIPT>
</BODY>
<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>