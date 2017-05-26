<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改专家登录信息</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
</head>
<body>

<s:form action="/logininfo/logininfo_update.action" method="POST"  namespace='/logininfo' >

<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">

<!-- 标题条 -->
<div class="pagetitle">专家登录信息:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="saveBtn" TYPE="submit" value="  ">
		</td>
	    <td width="10%" align="center"><a href="#" onClick="gotoList();"><input class="backBtn" onclick="gotoList()" type="button" value=""></a></td>
	</tr>
</table>

<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<th colspan="2" align="left" class="modeltitle">专家登录信息</th>
		<input name="loginInfo.n_LoginId" type="hidden" require="true" 
			dataType="LimitB" max="15" min="1"  value="<s:property value="loginInfo.n_LoginId"/>"/>
	</tr>
   
   <tr> 
		<th width="20%" align="right"><font color="red">*</font>客户类型：</th>
		<td width="80%"> 
		<input name="n_CustomerTypeId" type="text" disabled="disabled" require="true" 
			dataType="LimitB" max="15" min="1"  value="专家"/>
			<input name="loginInfo.n_CustomerTypeId" type="hidden" require="true" 
			dataType="LimitB" max="15" min="1"  value="<s:property value="loginInfo.n_CustomerTypeId"/>"/>
		</td>
	</tr>
	
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>客户级别：</th>
		<td width="80%"> 	
		<select name="loginInfo.n_LevelId">
		<s:iterator id="custiterator"  value="userLevelList">
		
		<option <s:if test="n_level_id==loginInfo.n_LevelId">select</s:if> value="<s:property value="n_level_id"/>"><s:property value="level_name"/></option>
		</s:iterator>
		</select>
		</td>
	</tr>
	
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>登录帐号：</th>
		<td width="80%"> 
			<input name="loginInfo.loginAccount" type="text" require="true" 
			dataType="LimitB" max="16" min="1" msg="帐号，不能为空" value="<s:property value="loginInfo.loginAccount"/>"/>
		</td>
	</tr>
	
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>手机号：</th>
		<td width="80%"> 
			<input name="loginInfo.mobile" type="text" require="true" 
			dataType="LimitB" max="40" min="1"  value="<s:property value='loginInfo.mobile'/>"/>
		</td>
	</tr>
	
    <tr> 
		<th width="20%" align="right"><font color="red">*</font>电子邮箱：</th>
		<td width="80%"> 
			<input name="loginInfo.email" type="text" 
			require="true" dataType="LimitB" max="22" min="1"  value="<s:property value='loginInfo.email'/>"/>
		</td>
	</tr>
     <tr> 
		<th width="20%" align="right"><font color="red">*</font>帐号状态：</th>
		<td width="80%">    
		<select name="loginInfo.n_Status">
		<option <s:if test="loginInfo.n_Status==0">select</s:if> value="0">启用</option>
		<option <s:if test="loginInfo.n_Status==0">select</s:if> value="1">禁用</option>
		</select>
		</td>
	</tr>
</table>


<!-- 底部 按钮条 -->
<table width="98%" align="center" class="bottombuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="center">
			<INPUT class="saveBtn" TYPE="submit" value="  ">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onClick="gotoList();"><input class="backBtn" onclick="gotoList()" type="button" value=""></a>
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form>
<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoList(){
    location.href="/logininfo/logininfo_pageList.action?customerTypeId=1";
}

</SCRIPT>
</BODY>
</HTML>