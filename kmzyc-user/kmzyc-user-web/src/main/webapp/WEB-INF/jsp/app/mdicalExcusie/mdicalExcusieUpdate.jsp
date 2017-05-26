<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改医务专属信息</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
</head>
<body>

<s:form action="/mdicalExcusie/mdicalExcusie_update.action" method="POST"  namespace='/mdicalExcusie' >

<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">

<!-- 标题条 -->
<div class="pagetitle">医务专属信息:</div>

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
		<th colspan="2" align="left" class="modeltitle">医务专属信息</th>
		<input name="mdicalExcusieInfo.n_medicalMattersExclusive_id" type="hidden"  value="<s:property value='mdicalExcusieInfo.n_medicalMattersExclusive_id'/>"/>
	</tr>
   
   <tr> 
		<th width="20%" align="right"><font color="red">*</font>姓名：</th>
		<td width="80%"> 
			<input name="mdicalExcusieInfo.name" type="text" require="true" 
			dataType="LimitB" max="15" min="1" msg="姓名，不能为空" value="<s:property value='mdicalExcusieInfo.name'/>"/>
		</td>
	</tr>
	
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>所在城市：</th>
		<td width="80%"> 	
		<input name="mdicalExcusieInfo.theCity" type="text" require="true" 
			dataType="LimitB" max="15" min="1" msg="所在城市，不能为空" value="<s:property value='mdicalExcusieInfo.theCity'/>"/>
		</td>
	</tr>
	
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>所属医院：</th>
		<td width="80%"> 
			<input name="mdicalExcusieInfo.theHospital" type="text" require="true" 
			dataType="LimitB" max="16" min="1" msg="所属医院，不能为空" value="<s:property value='mdicalExcusieInfo.theHospital'/>"/>
		</td>
	</tr>
	
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>医院等级：</th>
		<td width="80%"> 
			<input name="mdicalExcusieInfo.hospitalLevel" type="text" require="true" 
			dataType="LimitB" max="40" min="1"  value="<s:property value='mdicalExcusieInfo.hospitalLevel'/>"/>
		</td>
	</tr>
	
    <tr> 
		<th width="20%" align="right"><font color="red">*</font>所属科室：</th>
		<td width="80%"> 
			<input name="mdicalExcusieInfo.theDepartment" type="text" 
			require="true" dataType="LimitB" max="22" min="1"  value="<s:property value='mdicalExcusieInfo.theDepartment'/>"/>
		</td>
	</tr>
     <tr> 
		<th width="20%" align="right"><font color="red">*</font>职业称号：</th>
		<td width="80%">    
		<input name="mdicalExcusieInfo.professionName" type="text" 
		require="true" dataType="LimitB" max="22" min="1"  value="<s:property value='mdicalExcusieInfo.professionName'/>"/>
		</td>
	</tr>
	 <tr> 
		<th width="20%" align="right"><font color="red">*</font>专业专长：</th>
		<td width="80%"> 
			<input name="mdicalExcusieInfo.professionalExprtise" type="text" require="true" 
			dataType="LimitB" max="22" min="1" msg="号码，不能为汉字 " value="<s:property value='mdicalExcusieInfo.professionalExprtise'/>"/>
		</td>
	</tr>
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>证书类型：</th>
		<td width="80%"> 
		 <select name="mdicalExcusieInfo.n_certificateType">
	        <option value="0" <s:if test="mdicalExcusieInfo.n_certificateType==0">selected</s:if>>医生资格证</option>
	        <option value="1" <s:if test="mdicalExcusieInfo.n_certificateType==1">selected</s:if>>医生执业证</option>
	        </select>
	      	</td>
	</tr>
	 <tr> 
		<th width="20%" align="right"><font color="red">*</font>证书编号：</th>
		<td width="80%"> 
			<input name="mdicalExcusieInfo.certificateNumber" type="text" require="true"
			 dataType="LimitB" max="40" min="1"  value="<s:property value='mdicalExcusieInfo.certificateNumber'/>"/>
		</td>
	</tr>
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>审核电话：</th>
		<td width="80%"> 
			<input name="mdicalExcusieInfo.auditingPhone" type="text" require="true" 
			dataType="LimitB" max="40" min="1"  value="<s:property value='mdicalExcusieInfo.auditingPhone'/>"/>
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
    location.href="/mdicalExcusie/mdicalExcusie_pageList.action";
}

</SCRIPT>
</BODY>
</HTML>