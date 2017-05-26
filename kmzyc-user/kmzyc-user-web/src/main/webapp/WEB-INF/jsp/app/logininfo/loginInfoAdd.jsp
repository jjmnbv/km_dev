<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加专家登录信息</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
</head>
<body>

<s:form action="/logininfo/logininfo_add.action" method="POST"  namespace='/logininfo' >

<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">

<!-- 标题条 -->
<div class="pagetitle">专家登录信息:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="saveBtn btngreen" TYPE="submit" value=" ">
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
		
	</tr>
   
   <tr> 
		<th width="20%" align="right"><font color="red">*</font>客户类型：</th>
		<td width="80%"> 
			<input name="n_CustomerTypeId" type="text" disabled="disabled" require="true" 
			dataType="LimitB" max="15" min="1"  value="专家"/>
			<input name="loginInfo.n_CustomerTypeId" type="hidden" require="true" 
			dataType="LimitB" max="15" min="1"  value="1"/>
		</td>
	</tr>
	
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>客户级别：</th>
		<td width="80%"> 	
		<select name="loginInfo.n_LevelId">
		<s:iterator id="custiterator"  value="userLevelList">
		<option value="<s:property value="n_level_id"/>"><s:property value="level_name"/></option>
		</s:iterator>
		</select>
		</td>
	</tr>
	
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>登录帐号：</th>
		<td width="80%"> 
			<input name="loginInfo.loginAccount" type="text" require="true" 
			dataType="LimitB" max="16" min="1" msg="帐号，不能为空" value="<s:property value=''/>"/>
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
		<option value="0">启用</option>
		<option value="1">禁用</option>
		</select>
		</td>
	</tr>
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>姓名：</th>
		<td width="80%"> 
			<input name="personalBasicInfo.name" type="text" require="true" dataType="LimitB" max="15" min="1" msg="姓名，不能为空" value=""/>
		</td>
	</tr>
	
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>性别：</th>
		<td width="80%"> 	
		<input type="radio" name="personalBasicInfo.sex" value="0" checked="checked" />男
		<input type="radio" name="personalBasicInfo.sex" value="1" />女
		</td>
	</tr>
	
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>年龄：</th>
		<td width="80%"> 
			<input name="personalBasicInfo.n_Age" type="text" require="true" dataType="LimitB" max="16" min="1" msg="年龄，不能是汉字" value=""/>
		</td>
	</tr>
	
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>生日：</th>
		<td width="80%"> 
			<input name="personalBasicInfo.d_Birthday" type="text" require="true" dataType="LimitB" max="40" min="1"  value=""/>
		</td>
	</tr>
	
    <tr> 
		<th width="20%" align="right"><font color="red">*</font>手机号：</th>
		<td width="80%"> 
			<input name="personalBasicInfo.mobile" type="text" require="true" dataType="LimitB" max="22" min="1"  value=""/>
		</td>
	</tr>
    <tr> 
		<th width="20%" align="right"><font color="red">*</font>邮箱：</th>
		<td width="80%"> 
			<input name="personalBasicInfo.email" type="text" require="true" dataType="LimitB" max="22" min="1" msg="请输入，不能为汉字" value=""/>
		</td>
	</tr>
     <tr> 
		<th width="20%" align="right"><font color="red">*</font>证件类型：</th>
		<td width="80%"> 
	        <select name="personalBasicInfo.n_CertificateType">
	        <option value="0" select>身份证</option>
	        <option value="1">护照</option>
	        <option value="0">回乡证</option>
	        </select>
		</td>
	</tr>
	 <tr> 
		<th width="20%" align="right"><font color="red">*</font>证件号码：</th>
		<td width="80%"> 
			<input name="personalBasicInfo.certificateNumber" type="text" require="true" dataType="LimitB" max="22" min="1" msg="号码，不能为汉字 " value=""/>
		</td>
	</tr>
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>所在地：</th>
		<td width="80%"> 
			<input name="personalBasicInfo.location" type="text" require="true" dataType="LimitB" max="40" min="1" msg="" value=""/>
		</td>
	</tr>
	 <tr> 
		<th width="20%" align="right"><font color="red">*</font>故乡所在地：</th>
		<td width="80%"> 
			<input name="personalBasicInfo.hometownLocation" type="text" require="true" dataType="LimitB" max="40" min="1" msg="" value=""/>
		</td>
	</tr>
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>教育状况：</th>
		<td width="80%"> 
			<input name="personalBasicInfo.educationalStatus" type="text" require="true" dataType="LimitB" max="40" min="1" msg="" value=""/>
		</td>
	</tr>
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>工作单位：</th>
		<td width="80%"> 
			<input name="personalBasicInfo.workUnit" type="text" require="true" dataType="LimitB" max="40" min="1" msg="" value=""/>
		</td>
	</tr>
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>职业类型：</th>
		<td width="80%"> 
			<input name="personalBasicInfo.professionType" type="text" require="true" dataType="LimitB" max="40" min="1" msg="" value=""/>
		</td>
	</tr>
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>职业：</th>
		<td width="80%"> 
			<input name="personalBasicInfo.profession" type="text" require="true" dataType="LimitB" max="40" min="1" msg="" value=""/>
		</td>
	</tr>
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>年收入：</th>
		<td width="80%"> 
			<input name="personalBasicInfo.n_AnnualIncome" type="text" require="true" dataType="LimitB" max="40" min="1" msg="" value=""/>
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