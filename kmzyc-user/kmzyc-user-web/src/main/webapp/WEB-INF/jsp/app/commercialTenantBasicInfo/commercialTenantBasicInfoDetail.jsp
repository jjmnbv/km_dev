<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户详情信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
/** 通过登录账号id查询账户详细信息  **/
 function queryAccountInfo(id){
     openwindow("/accounts/accountInfo_showAccountInfo.action?showType=1&accountInfo.n_LoginId="+id,"_blank",800,650); 
 }
 /** 通过登录账号id查询收货地址信息 **/
 function queryAddressInfo(id){
     openwindow("/accounts/address_queryPageList.action?viewOnly=1&showType=1&address.loginId="+id,"_blank",1000,650);
 }
  /** 通过登录账号id查询消息中心信息 **/
 function queryMessageInfo(accountId){
      openwindow("/messageCenter/messageCenter_pageList.action?viewOnly=1&showType=1&bnesMessageCenter.accountId="+accountId,"_blank",1000,650);
 }
 /**通过登录账号查询消费电子卷 **/
 function queryVouchersInfo(id){
      openwindow("/accounts/vouchers_showVouchersList.action?showType=1&loginId="+id,"_blank",1000,650);
 }
 function queryAccTrationInfo(id){
     openwindow("/accounts/tration_showTrationList.action?showType=1&loginId="+id,"_blank",1000,650); 
 
 }
 /** 通过登录账号查询积分明细信息 **/
 function queryScoreInfo(n_loginId){
      
      openwindow("/accounts/scoreInfo_queryPageList.action?showType=1&loginId="+n_loginId,"_blank",1000,650);
 }
 
 /** 通过登录账号id冻结登录账号 **/
 function queryLoginAccountForzen(id,loginAccount){
 
       openwindow("/loginfrozenRecord/frozenRecord_preAdd.action?showType=1&bnesFrozenRecord.username="+loginAccount+"&bnesFrozenRecord.loginId="+id,"_blank",1000,650);

       }
 
 /**通过登录账号id解冻登录账户**/
 function queryLoginAccountThraw(id){
 
     openwindow("/loginfrozenRecord/frozenRecord_preUpdate.action?showType=1&bnesFrozenRecord.loginId="+id,"_blank",1000,650);
     
 }
 
 function openwindow(url,name,iWidth,iHeight){
	var url; //转向网页的地址;
	var name; //网页名称，可为空;
	var iWidth; //弹出窗口的宽度;
	var iHeight; //弹出窗口的高度;
	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	window.open(url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
 }
 
</script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'客户资料'" scope="request"/>
<s:set name="name" value="'商户'" scope="request"/>
<s:set name="son_name" value="'详情'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form enctype="multipart/form-data" id="commercialTenantBasicInfoUpdate" action="commercialTenantBasicInfo_update.action" method="POST"  namespace='/userInfo'>
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
		<td colspan="6"  align="center" >
		 <input type="button" value="账户信息"   onclick="queryAccountInfo(<s:property value='loginInfo.n_LoginId'/>)"  />&nbsp;
			<input type="button" value="地址管理"   onclick="queryAddressInfo(<s:property value='loginInfo.n_LoginId'/>)"/>&nbsp;
			<input type="button" value="消息中心"  onclick="queryMessageInfo('<s:property value='loginInfo.n_LoginId'/>')" />&nbsp;
			<input type="button" value="消费电子券" onclick="queryVouchersInfo(<s:property value='loginInfo.n_LoginId'/>)" />&nbsp;
			<input type="button" value="交易记录"  onclick="queryAccTrationInfo(<s:property value='loginInfo.n_LoginId'/>)" />&nbsp;
		    <input type="button" value="积分明细" onclick="queryScoreInfo('<s:property value='loginInfo.n_LoginId'/>')" />
		</td>
	</tr>
    <tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;登录信息</td>
	</tr>
	
	<tr> 
		<td width="13%" align="right">会员账号：</td>
		<td width="20%"> 
			<s:property value="loginInfo.loginAccount"/>
		</td>
		<td width="13%" align="right">邮箱地址：</td>
		<td width="20%"> 
			<s:property value="loginInfo.email"/>
		</td>
		<td width="13%" align="right">手机号码：</td>
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
		<td width="13%" align="right">信用等级说明:</td>
		<td width="20%"> 
		 <s:property value="commercialTenantBasicInfo.ratingDescription"/>
		</td>
	</tr>
	<tr>  
	<td width="13%" align="right" >营业执照图片：</td>
		<td width="20%" colspan="5"> 
		
		<s:if test="commercialTenantBasicInfo.uploadBusinessLicencePictur!=null">
		<img title="营业执照" width="200" height="110" style="cursor: pointer;" src="<s:property value='showImage'/><s:property value='commercialTenantBasicInfo.uploadBusinessLicencePictur'/>" />
		<a href="<s:property value='showImage'/><s:property value='commercialTenantBasicInfo.uploadBusinessLicencePictur'/>" title="单击放大" target="_blank" id="hrefId101">营业执照</a>
 		</s:if>
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
		<s:if test="commercialTenantBasicInfo.certificatePicture!=null">
		<img title="身份证扫描件" width="200" height="110" style="cursor: pointer;" src="<s:property value='showImage'/><s:property value='commercialTenantBasicInfo.certificatePicture'/>" />
		<a href="<s:property value='showImage'/><s:property value='commercialTenantBasicInfo.certificatePicture'/>" title="单击放大" target="_blank" id="hrefId101">身份证扫描件</a>
		</s:if>
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
	 		<td width="13%" align="right">商户状态：</td>
		<td width="20%">
		<s:if test="commercialTenantBasicInfo.isValid==0">禁用</s:if>
		<s:if test="commercialTenantBasicInfo.isValid==1">可用</s:if>	 
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
								<s:property value="commercialTenantBasicInfo.city" />
								<s:property value="commercialTenantBasicInfo.area" />
		
		</s:else>
	
	</tr>
	
		<tr> 
		<td width="13%" align="right">税务登记证(副本)：</td>
		<td width="20%" colspan="5"> 
		<s:if test="commercialTenantBasicInfo.taxRegCertificateCopy!=null">
	   <img title="税务登记证(副本)" width="200" height="110" style="cursor: pointer;" src="<s:property value='showImage'/><s:property value='commercialTenantBasicInfo.taxRegCertificateCopy'/>" />
		<a href="<s:property value='showImage'/><s:property value='commercialTenantBasicInfo.taxRegCertificateCopy'/>" title="单击放大" target="_blank" id="hrefId101">税务登记证(副本)</a>
		</s:if>
		 <input type="hidden" value="<s:property value="commercialTenantBasicInfo.taxRegCertificateCopy"/>"  name="commercialTenantBasicInfo.taxRegCertificateCopy" />
		</td> 
	
	</tr>
	<tr> 
		<td width="13%" align="right">一般纳税人证书：</td>
		<td width="20%" colspan="5"> 
		<s:if test="commercialTenantBasicInfo.taxpayerCertificate!=null">
	  <img title="一般纳税人证书" width="200" height="110" style="cursor: pointer;" src="<s:property value='showImage'/><s:property value='commercialTenantBasicInfo.taxpayerCertificate'/>" />
		<a href="<s:property value='showImage'/><s:property value='commercialTenantBasicInfo.taxpayerCertificate'/>" title="单击放大" target="_blank" id="hrefId101">一般纳税人证书</a>
	<input type="hidden" value="<s:property value="commercialTenantBasicInfo.taxpayerCertificate"/>"  name="commercialTenantBasicInfo.taxpayerCertificate" />
	</s:if>
		</td> 
	
	</tr>
 
</table>

<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
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
</HTML>