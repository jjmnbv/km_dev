<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改角色</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
</head>
<body onkeydown="changeKey();">
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form action="doUpdateSysRole.action" method="POST"  namespace='/sys' onsubmit="return  Validator.Validate(this,3)">

<!-- hidden properties -->
<INPUT TYPE="hidden" name="roleId" value="<s:property value='model.roleId'/>">

<!-- 标题条 -->
<div class="pagetitle">添加角色:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="submit" value=" 保存 ">
		</td>
	    <td width="10%" align="center"><a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a></td>
	</tr>
</table>

<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
    <!-- error message -->
    
	<tr> 
		<th colspan="2" align="left" class="modeltitle">角色信息</th>
	</tr>

	<tr> 
		<th width="20%" align="right"><font color="red">*</font>角色名称：</th>
		<td> 
			<input name="roleName" type="text" require="true" dataType="LimitB" max="40" min="1" msg="角色名称必输，且不超过10个汉字" value="<s:property value='model.roleName'/>"/>
		</td>
	</tr>

	<tr> 
		<th align="right">角色职责：</th>
		<td> 
			<textarea name="roleExplain" cols="50" rows="4" wrap="PHYSICAL" require="false" dataType="LimitB" max="200" msg="备注不要超过100个汉字"><s:property value='model.roleExplain'/></textarea> 
		</td>
	</tr>

	<tr> 
		<th align="right">备注：</th>
		<td> 
			<textarea name="roleRemark" cols="50" rows="4" wrap="PHYSICAL" require="false" dataType="LimitB" max="100" msg="备注不要超过50个汉字"><s:property value='model.roleRemark'/></textarea> 
		</td>
	</tr>
	</table>


<!-- 底部 按钮条 -->
<table width="98%" align="center" class="bottombuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="center">
			<INPUT class="btngreen" TYPE="submit" value=" 保 存 ">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a>
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>
<br><br>


</s:form>
<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoList(){
    location.href="/sys/listSysRole.action";
}



//光标移动
function changeKey()
{
	var tr=event.srcElement.getAttribute("type");
	if("textarea"!=tr && "button" != tr)
	{
			if(13 == event.keyCode)
			{
				event.keyCode=9;
			}
  }
}
//-->
</SCRIPT>
</BODY>
</HTML>