<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>详细账户信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.validate.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/messages_cn.js" type="text/javascript"></Script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'账户管理'" scope="request"/>
<s:if test="edit=='edit'">
	<s:set name="name" value="'账户编辑'" scope="request" />
</s:if> <s:else>
	<s:set name="name" value="'账户信息'" scope="request"/>
</s:else>
<s:set name="son_name" value="'详细信息'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form id="accountInfoUpdate" action="accountInfo_update.action" method="POST"  namespace='/accountInfo' >
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<INPUT TYPE="hidden" id="edit" name="edit" value="<s:property value='edit'/>">
<!-- 数据编辑区域 -->
<table  width="90%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="6" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<td colspan="6" align="left" class="edit_title">账户信息</td>
	</tr>
   
   <tr> 
		<td width="12%" align="right">账户号：</td>
		<td width="22%"> 
			<s:property value='accountInfo.accountLogin'/>
		</td>
		<td width="15%" align="right">登录号：</td>
		<td width="20%"> 
			<s:property value='accountInfo.loginAccount'/>
		</td> 
		<td width="15%" align="right">客户类别：</td>
		<td width="25%"> 
			<s:property value='accountInfo.customerName'/>
		</td>
	</tr>
	<tr> 
		<td width="12%" align="right">账户金额：</td>
		<td width="22%">
		  <s:property value="%{formatDouble(accountInfo.n_AccountAmount)}"/>
		
		</td>
		<td width="15%" align="right">账户可用金额：</td>
		<td width="20%">
		  <s:property value="%{formatDouble(accountInfo.amountAvlibal)}"/>
		
		</td>
		<td width="15%" align="right">账户冻结金额：</td>
		<td width="25%">
		 <s:property value="%{formatDouble(accountInfo.amountFrozen)}"/>
		</td>
	</tr>
    <tr> 
		<td width="12%" align="right">账户真实姓名：</td>
		<td width="22%"> 
			<s:property value='accountInfo.name'/>
		</td>
		<td width="15%" align="right">证件号码：</td>
		<td width="20%"> 
			<s:property value='accountInfo.acconutId'/>
		</td>
		<td width="15%" align="right">手机号码：</td>
		<td width="25%"> 
			<s:property value='accountInfo.mobile'/>
		</td>
	</tr>
	 <tr> 
		<%--删除邮件 <td width="12%" align="right">邮箱地址：</td>
		<td width="22%"> 
			<s:property value='accountInfo.email'/>
		</td> --%>
		<td width="15%" align="right">地址：</td>
		<td width="20%">
			<s:property value='accountInfo.address'/>
		</td>
		<td width="15%" align="right">账户创建日期：</td>
		<td width="25%">
			<s:date name="accountInfo.d_CreateDate" format="yyyy-MM-dd"/>
		</td>
	</tr>
</table>


<!-- 底部 按钮条 -->
<!--<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			
			<s:if test="%{showType!=4}">
		   <input class="backBtn"  onclick="gotoList(<s:property value='showType'/>)" type="button" value=" ">
		   </s:if>
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>-->

<br><br>

</s:form>
</div>
<SCRIPT LANGUAGE="JavaScript">

function gotoList(val){
    if(val!=null){
    window.close();
    }else{
    	var ed=$("#edit").val();
   	 	if(ed=="edit"){
   	 	location.href="/accounts/accountInfo_pageEditList.action";
	   	 } else {
	   		location.href="/accounts/accountInfo_pageList.action";
	   	 }
    }
}

</SCRIPT>
</BODY>
</HTML>