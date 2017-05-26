<%@page contentType="text/html;charset=UTF-8" import="com.pltfm.sys.util.StaticParams"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改个人信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.validate.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/messages_cn.js" type="text/javascript"></Script>
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
  				
				if (reg.test(value)==false){
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
			jQuery.validator.addMethod("checkLoginEmail", function(value, element) {
				var n_LoginId = $("#n_LoginId").val();
				
		 	 	var rows = 0;
		 			$.ajax({
		 				async:false,
		 				url:"personalBasicInfo_checkLoginEail.action",
		 				type:"POST",
		 				data:"email=" + value+"&n_LoginId="+n_LoginId,
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
		 			
			}, "重复的邮箱地址");
	
$("#personalBasicInfoUpdateForm").validate({
	rules: { 
		"name":{rangelength:[2,16],userName:true},
		"nickname":{maxlength:16,unusualChar:true},
		"d_Birthday":{afterDate:true}
		},
		success: function (label){
            label.removeClass("checked").addClass("checked");
        }
	});
	 
});
</script>
<style type="text/css">

.inputC {vertical-align:middle;}

</style>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'客户资料'" scope="request"/>
<s:set name="name" value="'个人客户'" scope="request"/>
<s:set name="son_name" value="'修改'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
 <div  style="height:90%;overflow-y:auto; " >
<s:form action="personalBasicInfo_update.action" id="personalBasicInfoUpdateForm" enctype="multipart/form-data" method="POST"  namespace='/userInfo'>
<s:token/>
<input name="loginInfo.loginAccount" id="loginAccount" type="hidden" value="<s:property value='loginInfo.loginAccount'/>" readonly="readonly" />
<input name="loginInfo.n_LoginId" id="n_LoginId" type="hidden" value="<s:property value='loginInfo.n_LoginId'/>" readonly="readonly"/>
<input name="personalBasicInfo.n_PersonalId" id="n_PersonalId" type="hidden" value="<s:property value='personalBasicInfo.n_PersonalId'/>" readonly="readonly"/>
<input name="personalityInfo.n_PersonalityId" id="n_PersonalityId" type="hidden" value="<s:property value='personalityInfo.n_PersonalityId'/>" readonly="readonly"/>
<input name="healthYgenericInfo.n_HealthYgenericId" id="n_HealthYgenericId" type="hidden" value="<s:property value='healthYgenericInfo.n_HealthYgenericId'/>" readonly="readonly"/>
<input name="accountInfo.n_AccountId" id="n_AccountId" type="hidden" value="<s:property value='accountInfo.n_AccountId'/>" readonly="readonly"/>
<input name="loginInfo.email" id="email" type="hidden" value="<s:property value='loginInfo.email'/>"/>
<input name="loginInfo.mobile" id="mobile" type="hidden" value="<s:property value='loginInfo.mobile'/>" />

<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- 数据编辑区域 -->
<table width="92%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="6" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>

	<tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个人信息</td>
	</tr>
	<tr> 
		<td width="10%" align="right">会员账号：</td>
		<td width="10%"> 
			<s:property value="personalBasicInfo.loginAccount"/>
		</td>
		<td width="10%" align="right">姓名：</td>
		<td width="10%">
			<input name="personalBasicInfo.name" id="name" type="text" value="<s:property value='personalBasicInfo.name'/>" /> 
		</td>
		<td width="10%" align="right">性别：</td>
		<td width="10%">		
		<s:if test="personalBasicInfo.sex==0">
			<input class="inputC" type="radio" name="sex" value="0" checked="checked" />男
			<input class="inputC" type="radio" name="sex" value="1" />女
		</s:if>
		<s:else>
			<input class="inputC" type="radio" name="sex" value="0"  />男
			<input class="inputC" type="radio" name="sex" value="1" checked="checked"/>女
		</s:else>
		</td>
	</tr>
	
	<tr> 
		<%-- <td align="right">总部会员编号：</td>
		<td> 
			<s:property value="personalBasicInfo.cardNum"/>
		</td> --%>
		<td align="right">手机号码：</td>
		<td> 
			<s:property value="personalBasicInfo.mobile"/>
		</td>
		<td  align="right">手机状态：</td>
		<td> 
			<s:if test="personalBasicInfo.isMobile==1">已验证</s:if>
			<s:else>未验证</s:else>
		</td>
		<td align="right">账号状态：</td>
		<td > 
			<s:if test="personalBasicInfo.n_Status==0">禁用</s:if>
			<s:if test="personalBasicInfo.n_Status==1">可用</s:if>
		</td>
	</tr>
	<%-- <tr>
		
		<td  align="right">邮箱地址：</td>
		<td> 
			<s:property value="personalBasicInfo.email"/>
		</td>
		<td  align="right">邮箱状态：</td>
		<td> 
			<s:if test="personalBasicInfo.isEmail==1">已验证</s:if>
			<s:else>未验证</s:else>
		</td>
	</tr> --%>
	<tr>
		<%-- <td align="right">客户级别：</td>
		<td> 
			<s:property value="personalBasicInfo.levelName"/>
		</td> --%>
		<td align="right">客户来源：</td>
		<td>
			<s:if test="#request.isThird==1">
				授权登录
			</s:if>
            <s:else>
            	<s:property value="#request.registerPlatformMap[personalBasicInfo.registerPlatform]" />
            </s:else>
		</td>
		<td align="right">客户身份：</td>
		<td> 
			<s:if test="personalBasicInfo.isCommonUser==1">普通用户</s:if>
			<s:if test="personalBasicInfo.isSupplier==1">，供应商</s:if>
			<s:if test="personalBasicInfo.isPurchaser==1">，采购商</s:if>
			<s:if test="personalBasicInfo.isTourist==1">，游客</s:if>
			<s:if test="personalBasicInfo.isEra==1">，康美中药城</s:if>
			<s:if test="personalBasicInfo.isSpreader==1">，云商</s:if>
			<s:if test="personalBasicInfo.isCrowder==1">，机构</s:if>
		</td>
		<td align="right">终端来源：</td>
		<td> 
			<s:property value="#request.registerDeviceMap[personalBasicInfo.registerDevice]" />
		</td>
	</tr>
	<tr>
		
		<td align="right">昵称：</td>
		<td> 
			<input  name="personalityInfo.nickname" id="nickname" type="text" value="<s:property value='personalityInfo.nickname'/>" />
		</td>
		<td align="right">创建时间：</td>
		<td> 
			<s:date name="personalBasicInfo.d_CreateDate" format="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	<tr>
		<td align="right">生日：</td>
		<td>
			<input name="personalBasicInfo.d_Birthday"   onchange="this.focus()"   id="d_Birthday" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:(new Date())})" readonly   type="text" value="<s:date   name='personalBasicInfo.d_Birthday'  format='yyyy-MM-dd'/>"/>
		</td>
		<td align="right">证件类型：</td>
		<td>
			<s:if test="personalBasicInfo.n_CertificateType==0">身份证</s:if>
			<s:if test="personalBasicInfo.n_CertificateType==1">护照</s:if>
			<s:if test="personalBasicInfo.n_CertificateType==2">回乡证</s:if>
		</td>
		<td align="right">证件号码：</td>
		<td>
			<s:property value="personalBasicInfo.certificateNumber"/>
		</td>
	</tr>
	<tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账户信息</td>
	</tr>
	<tr> 
		<td align="right">账户金额：</td>
		<td   >
			<s:property value="accountInfo.n_AccountAmount"/>
		</td>
		<td align="right">账户可用金额：</td>
		<td   >
			<s:property value="accountInfo.amountAvlibal"/>
		</td>
		<td align="right">账户冻结金额：</td>
		<td >
			<s:property value="accountInfo.amountFrozen"/>
		</td>
	</tr>
	<%-- <s:if test="personalBasicInfo.isEra!=1"> --%> 
	<tr> 
		<td align="right">总积分：</td>
		<td colspan = "4"> 
			<s:property value="personalityInfo.n_TotalIntegral"/>
		</td>
		<%-- <td align="right">可用积分：</td>
		<td> 
			<s:property value="personalityInfo.n_AvailableIntegral"/>
		</td> --%>
		<!-- 
		<td width="20%" align="right"  rowspan="4">头像照片：</td>
		<td width="20%"    rowspan="4">
		<img title="头像" width="100" height="100" style="cursor: pointer;" src="<s:property value='personalityInfo.headSculpture'/>" />
		</td>
		 -->
		 <td colspan="2"></td>
	</tr>
	<%-- </s:if> --%>
	
	<s:if test="personalBasicInfo.isEra==1">
	<tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时代会员信息</td>
	</tr>
	<tr> 
		<td align="right">康美中药城会员ID：</td>
		<td  >
			<s:property value="eraInfo.eraInfoId"/>
		</td>
		<td align="right">时代会员编号：</td>
		<td  >
			<s:property value="eraInfo.eraNo"/>
		</td>
		<td align="right">时代等级：</td>
		<td>
			<s:property value="eraInfo.eraGradeName"/>
		</td>
	</tr>
	<tr> 
		<td align="right">手机号码：</td>
		<td   >
			<s:property value="eraInfo.contactInformation"/>
		</td>
		<td align="right">邮箱地址：</td>
		<td>
			
		</td>
		<td align="right">联系电话：</td>
		<td >
			<s:property value="eraInfo.contactInformation"/>
		</td>
	</tr>
	<tr> 
		<%-- <td align="right">时代订单积分：</td>
		<td   >
			<s:property value="eraInfo.expIntegralValue"/>
		</td> --%>
		<td align="right">推荐人编号：</td>
		<td   >
			<s:property value="eraInfo.recommendedNo"/>
		</td>
		<td align="right">时代会员信息更新时间：</td>
		<td colspan="3">
			<s:date name="eraInfo.modifyDate" format="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	</s:if>
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
    location.href="/userInfo/personalBasicInfo_pageList.action";
}

</SCRIPT>
</BODY>
<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>