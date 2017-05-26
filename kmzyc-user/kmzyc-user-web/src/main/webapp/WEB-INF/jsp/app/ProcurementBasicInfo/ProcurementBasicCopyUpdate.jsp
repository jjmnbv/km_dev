<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购商变更企业信息修改</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js">
</script>
		<script type="text/javascript" src="/etc/js/messages_cn.js">
</script>
<script type="text/javascript" src="/etc/js/jsAddress.js"></Script>
<script type="text/javascript">
$().ready(function() {
 
 
$("#ProcurementBasicCopyUpdateForm").validate({
	rules: { 
	 
		"commercialTenantBasicCopyDO.businessLicenceRegister":{required: true}
	 
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
	 var creditRating =$("#creditRating").val();
	 var credeittagName=$("#creditRating").get(0).tagName;
	 if($("#creditRating").get(0).tagName==('INPUT')){
		 $("#creditRating").attr("value",t);// 
	 }
	 else{
		 
		 if(t<=2&&creditRating<=2){
			 $("#creditRating").empty(); 
			 $("#creditRating").append('<option value='+t+'>'+t+'</option>');

		 }
		 
	 }
	 
	 
 }
 
</script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'采购商管理'" scope="request"/>
<s:set name="name" value="'采购商企业信息变更审核'" scope="request"/>
<s:set name="son_name" value="'修改'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " > 
<s:form enctype="multipart/form-data" id="ProcurementBasicCopyUpdateForm" name="ProcurementBasicCopyUpdateForm"  action="commercialTenantBasicCopy_commercialTenantBasicCopyUpdate.action" method="POST"  namespace='/userInfo'>
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
		  <input type="hidden"  value="<s:property value="commercialTenantBasicCopyDO.loginName"/>"  name="commercialTenantBasicCopyDO.loginName" id="loginName"/>
	      <input type="hidden" value="<s:property value="commercialTenantBasicCopyDO.commercialCopyId"/>"    name="commercialTenantBasicCopyDO.commercialCopyId" />
	 
		</td> 
	</tr>
	<tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;企业信息</td>
	</tr>
	<tr> 
		<td width="13%" align="right"><font color="red">*</font>公司名称：</td>
		<td width="20%"> 
		<input type="text" value="<s:property value="commercialTenantBasicCopyDO.corporateName"/>"  name="commercialTenantBasicCopyDO.corporateName" id="corporateName"/>
		</td> 
		<td width="13%" align="right"><font color="red">*</font>工商注册号：</td>
		<td width="20%"> 
		<input type="text" value="<s:property value="commercialTenantBasicCopyDO.businessLicenceRegister"/>"  name="commercialTenantBasicCopyDO.businessLicenceRegister" />
		</td>
		<td width="13%" align="right"><font color="red">*</font>信用等级：</td>
		<td width="20%"> 
		 <s:if test="commercialTenantBasicCopyDO.creditRating>=2">
		 <select name="commercialTenantBasicCopyDO.creditRating"  id="creditRating">
								<option value="2"
								<s:if test="commercialTenantBasicCopyDO.creditRating==2">selected</s:if> >
								2
								</option>
								<option value="3"
									<s:if test="commercialTenantBasicCopyDO.creditRating==3">selected</s:if> >
								3
								</option> 
								<option value="4"
									<s:if test="commercialTenantBasicCopyDO.creditRating==4">selected</s:if> >
								4
								</option> 
								<option value="5"
									<s:if test="commercialTenantBasicCopyDO.creditRating==5">selected</s:if> >
								5
								</option> 
								<option value="6"
									<s:if test="commercialTenantBasicCopyDO.creditRating==6">selected</s:if> >
								6
								</option> 
								<option value="7"
									<s:if test="commercialTenantBasicCopyDO.creditRating==7">selected</s:if> >
								7
								</option> 
								<option value="8"
									<s:if test="commercialTenantBasicCopyDO.creditRating==8">selected</s:if> >
								8
								</option> 
								<option value="9"
									<s:if test="commercialTenantBasicCopyDO.creditRating==9">selected</s:if> >
								9
								</option> 
							</select>
		 </s:if>
		 <s:if test="commercialTenantBasicCopyDO.creditRating<2">
		 <input type="text" id="creditRating" value="<s:property value="commercialTenantBasicCopyDO.creditRating"/>"  name="commercialTenantBasicCopyDO.creditRating" readonly="readonly"/>
		 </s:if>
		
		</td>
	</tr>
	<tr> 
		<td width="13%" align="right">业务联系人：</td>
		<td width="20%"> 
		<input type="text" value="<s:property value="commercialTenantBasicCopyDO.contactsName"/>"  name="commercialTenantBasicCopyDO.contactsName" />				
		</td> 
		<td width="13%" align="right"> 业务联系方式：</td>
		<td width="20%"> 
		<input type="text" value="<s:property value="commercialTenantBasicCopyDO.fixedPhone"/>"  name="commercialTenantBasicCopyDO.fixedPhone" />				
		</td>
			<td width="13%" align="right" ></td>
		<td width="20%" >
		</td>
	</tr>
	<tr> 
		<td width="13%" align="right"> 营业执照有效期：</td>
		<td width="20%" colspan="3"> 
		从<input name="blinceStartdate"    id="blinceStartdate" type="text" value="<s:date name='commercialTenantBasicCopyDO.blinceStartdate' format="yyyy-MM-dd"/>"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:('#F{$dp.$D(\'blinceEnddate\')}')})"/>
		 	<span id="megblinceStartdate"></span>至
		 	<input name="blinceEnddate"    id="blinceEnddate" type="text" value="<s:date name='commercialTenantBasicCopyDO.blinceEnddate' format="yyyy-MM-dd"/>"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:('#F{$dp.$D(\'blinceStartdate\')}')})"/>
		 	<span id="megblinceEnddate"></span>
		</td> 
			<td width="13%" align="right" >营业执照验证：</td>
		<td width="20%" >
		<select name="commercialTenantBasicCopyDO.blinceVerify"  id="blinceVerify" onchange="changeCreditRating()">
								<option value="0"
								<s:if test="commercialTenantBasicCopyDO.blinceVerify==0">selected</s:if> >
								  未验证
								</option>
								<option value="1"
									<s:if test="commercialTenantBasicCopyDO.blinceVerify==1">selected</s:if> >
								 已验证
								</option> 
							</select>
		</td>
	</tr>
	
	<tr> 
		<td width="13%" align="right" ><font color="red">*</font>上传营业执照图片：</td>
		<td width="20%" colspan="5"> 
		<img title="营业执照" width="280" height="120" style="cursor: pointer;" src="<s:property value='commercialTenantBasicCopyDO.uploadBusinessLicencePictur'/>" />
	 		 <s:file name="uploadFileImage"  id="uploadFileImage" class="rg-input rg-file" placeholder="" />
			<span id="megUploadBusinessLicencePictur"></span>
			<a href="<s:property value='commercialTenantBasicCopyDO.uploadBusinessLicencePictur'/>" title="单击放大" target="_blank" id="hrefId101">营业执照</a>
		</td> 
		
	</tr>
	<tr> 
		<td width="13%" align="right">  企业法人姓名：</td>
		<td width="20%"> 
		<input type="text" value="<s:property value="commercialTenantBasicCopyDO.enterpriseLegalRepresentativ"/>"  name="commercialTenantBasicCopyDO.enterpriseLegalRepresentativ" />
		</td> 
		<td width="13%" align="right"> 企业法人身份证号码：</td>
		<td width="20%"> 
		<input type="text" value="<s:property value="commercialTenantBasicCopyDO.certificateNumber"/>"  name="commercialTenantBasicCopyDO.certificateNumber" />
		</td>
			<td width="13%" align="right" >法人身份证验证：</font></td>
		<td width="20%" >
		<select name="commercialTenantBasicCopyDO.cidVerify"  id="cidVerify" onchange="changeCreditRating()">
								<option value="0"
								<s:if test="commercialTenantBasicCopyDO.cidVerify==0">selected</s:if>>
								  未验证
								</option>
								<option value="1"
									<s:if test="commercialTenantBasicCopyDO.cidVerify==1">selected</s:if>>
								 已验证
								</option> 
							</select>
		</td>
	</tr>
	<tr> 
		<td width="13%" align="right" > 上传企业法人身份证：</td>
		<td width="20%" colspan="5"> 
		<img title="身份证扫描件" width="280" height="120" style="cursor: pointer;" src="<s:property value='commercialTenantBasicCopyDO.certificatePicture'/>" />
	 		 <s:file name="otherSidImage"  id="otherSidImage" class="rg-input rg-file" placeholder="" />
			<span id="megUploadBusinessLicencePictur"></span>
			<a href="<s:property value='commercialTenantBasicCopyDO.certificatePicture'/>" title="单击放大" target="_blank" id="hrefId101">身份证扫描件</a>
		</td> 
		
	</tr>
	
	<tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;增值税发票资质信息</td>
	</tr>
	<tr> 
		<td width="13%" align="right"> 纳税人识别码：</td>
		<td width="20%"> 
	        <input type="text" value="<s:property value="commercialTenantBasicCopyDO.taxpayerIdnumber"/>"  name="commercialTenantBasicCopyDO.taxpayerIdnumber" />
		</td> 
		<td width="13%" align="right"> 开户银行：</td>
		<td width="20%"> 
		    <input type="text" value="<s:property value="commercialTenantBasicCopyDO.bankOfDeposit"/>"  name="commercialTenantBasicCopyDO.bankOfDeposit" />
		</td>
			<td width="13%" align="right" >银行账号：</font></td>
		<td width="20%" >
		    <input type="text" value="<s:property value="commercialTenantBasicCopyDO.companyAccount"/>"  name="commercialTenantBasicCopyDO.companyAccount" />
		</td>
	</tr>
		<tr> 
		<td width="13%" align="right">公司注册地址：</td>
		<td width="20%"> 
	        <input type="text" value="<s:property value="commercialTenantBasicCopyDO.corporateLocation"/>"  name="commercialTenantBasicCopyDO.corporateLocation" />
		</td> 
		<td width="13%" align="right">注册电话：</td>
		<td width="20%"> 
		    		    <input type="text" value="<s:property value="commercialTenantBasicCopyDO.mobile"/>"  name="commercialTenantBasicCopyDO.mobile" />
		    
		</td>
			<td width="13%" align="right" ><font color="red">*</font>增值税发票资质验证：</font></td>
		<td width="20%" >
		   		<select name="commercialTenantBasicCopyDO.ataxVerify"  id="ataxVerify">
								<option value="0"
								<s:if test="commercialTenantBasicCopyDO.ataxVerify==0">selected</s:if>>
								  未验证
								</option>
								<option value="1"
									<s:if test="commercialTenantBasicCopyDO.ataxVerify==1">selected</s:if>>
								 已验证
								</option> 
							</select>
		</td>
	</tr>
	<tr> 
		<td width="13%" align="right">企业所属区域：</td>
		<td width="20%" colspan="5"> 
	       
				 
							
		<s:select list="#request.provinceList" 	id="province"     onchange="choiceProArea();" 	name="commercialTenantBasicCopyDO.province"  listKey="areaId" listValue="areaName"  value="#request.commercialTenantBasicCopyDO.province"  ></s:select>
		  					<s:select list="#request.cityList" id="ci3ty"   onchange="choiceCityArea();"	name="commercialTenantBasicCopyDO.city" listKey="areaId" listValue="areaName"  value="#request.commercialTenantBasicCopyDO.city" 
		  						></s:select>
		  					<s:select list="#request.areaList" id="area" name="commercialTenantBasicCopyDO.area" listKey="areaId" listValue="areaName"  value="#request.commercialTenantBasicCopyDO.area" 
		  						></s:select> 
		</td> 
	
	</tr>
	<tr> 
		<td width="13%" align="right"><font color="red">*</font>税务登记证(副本)验证</td>
		<td width="20%">		
		
				   		<select name="commercialTenantBasicCopyDO.trccopyVerify"  id="trccopyVerify">
								<option value="0"
								<s:if test="commercialTenantBasicCopyDO.trccopyVerify==0">selected</s:if>>
								  未验证
								</option>
								<option value="1"
									<s:if test="commercialTenantBasicCopyDO.trccopyVerify==1">selected</s:if>>
								 已验证
								</option> 
							</select>			
		</td> 
		<td width="13%" align="right"><font color="red">*</font>一般纳税人证书验证：</td>
		<td width="20%"> 	   
		<select name="commercialTenantBasicCopyDO.gtcVerify"  id="gtcVerify">
								<option value="0"
								<s:if test="commercialTenantBasicCopyDO.gtcVerify==0">selected</s:if>>
								  未验证
								</option>
								<option value="1"
									<s:if test="commercialTenantBasicCopyDO.gtcVerify==1">selected</s:if>>
								 已验证
								</option> 
							</select>	 
		</td>
		<td width="13%" align="right"></td>
		<td width="20%">    
			
		</td>
	</tr>
	
		<tr> 
		<td width="13%" align="right">税务登记证(副本)：</td>
		<td width="20%" colspan="5"> 
	    <img title="税务登记证(副本)" width="280" height="120" style="cursor: pointer;" src="<s:property value='commercialTenantBasicCopyDO.taxRegCertificateCopy'/>" />
	 		 <s:file name="certificateCopyImage"  id="certificateCopyImage" class="rg-input rg-file" placeholder="" />
			<span id="megtaxRegCertificateCopy"></span>
			<a href="<s:property value='commercialTenantBasicCopyDO.taxRegCertificateCopy'/>" title="单击放大" target="_blank" id="hrefId101">税务登记证(副本)</a>   
					
		</td> 
	
	</tr>
	<tr> 
		<td width="13%" align="right">一般纳税人证书：</td>
		<td width="20%" colspan="5"> 
	  	<img title="一般纳税人证书" width="280" height="120" style="cursor: pointer;" src="<s:property value='commercialTenantBasicCopyDO.taxpayerCertificate'/>" />
		<s:file name="certificateImage"  id="certificateImage" class="rg-input rg-file" placeholder="" />
	    <span id="megtaxpayerCertificate"></span>
		<a href="<s:property value='commercialTenantBasicCopyDO.taxpayerCertificate'/>" title="单击放大" target="_blank" id="hrefId101">一般纳税人证书</a>	
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
		&nbsp;&nbsp;
		
    
		
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

 


</SCRIPT>
</BODY>
</HTML>