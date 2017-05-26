<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择系统角色</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/etc/js/sys/sysRolePopSelect.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form action="gotoPopSelectRole.action" method="POST"  namespace='/sys'>

<br>

<table width="90%" class="table_search" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<th align="right">角色名称：</th>
		<td>
		     <input name="roleName" type="text" value="<s:property value='model.roleName'/>">
		</td>
		<th align="right">&nbsp;</th>
        <td>&nbsp;</td>
		<td>
			<INPUT TYPE="submit" class="btngray" value=" 查询 ">
		</td>
	</tr>
</table>

<div style="margin: 10px 0px 6px 20px">
    <INPUT TYPE="button" class="btngreen" value=" 保存所选 " onclick="selectList();">
</div>

<table width="90%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
	<tr>
	    <th bgcolor="#99ccff" align="center" width="10%">选择</th>
		<th bgcolor="#99ccff" align="center" width="40%">角色名称</th>
		<th bgcolor="#99ccff" align="center" width="50%">角色职责说明</th>
	</tr>
	<s:iterator id="oneObj" value="dataList">
	<tr>
	
	    <td align="center">
		    <input type="checkbox"  <s:if test="roleId==1">disabled="disabled"</s:if> name="delId" id='<s:property value="roleId"/>'  value='<s:property value="roleName"/>'>
		</td>
		<td bgcolor="#FFFFCC" align="center"><b><s:property value="roleName"/></b></td>
		<td bgcolor="#FFFFCC" align="left" style="word-break:break-all"><s:property value="roleExplain"/></td>
	</tr>
	</s:iterator>
</table>

<div style="margin: 6px 0px 10px 20px">
    <INPUT TYPE="button" class="btngreen" value=" 保存所选 " onclick="selectList();">
</div>

<br>

</s:form>


</BODY>
</HTML>
