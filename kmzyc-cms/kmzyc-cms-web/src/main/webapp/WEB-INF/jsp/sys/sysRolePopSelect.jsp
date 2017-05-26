<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.sys.model.SysRole"%>
<%@ page import="com.pltfm.sys.model.SysUser"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择系统角色</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<%
  String userId="";
  SysUser sysuser = (SysUser)session.getAttribute("sysUser"); 
  if(sysuser!=null) userId = sysuser.getUserId().toString();
  //只有userId=1的系统管理员才能选择“总部系统管理员”角色
  String ifReadOnly = "";
%>
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
<%
    SysRole list_obj = (SysRole)request.getAttribute("oneObj");
	//只有userId=1的系统管理员才能选择“总部系统管理员”角色
	ifReadOnly = "";
	if("1".equals(list_obj.getRoleId().toString())  &&  !"1".equals(userId))
	    ifReadOnly = "disabled";
%>
	<tr>
	    <td align="center">
		    <input type="checkbox" <%=ifReadOnly%> name="delId" value='<s:property value="roleId"/>'  nvalue='<s:property value="roleName"/>'>
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


<SCRIPT LANGUAGE="JavaScript">
<!--
function selectList() {
	var roleIds = document.forms[0].delId;
	var idStr = "";
	var nameStr = "";
	for(i=0;i<roleIds.length;i++)
	{   
		if(roleIds[i].checked == true)   
		{   
				 idStr = idStr + roleIds[i].value + ",";
				 nameStr = nameStr + roleIds[i].nvalue + ",";
		}
	}
	if(idStr == ""){
        alert("没有选中任何岗位角色！");
		return false;
	}
	idStr = idStr.substring(0,idStr.lastIndexOf(","));
	nameStr = nameStr.substring(0,nameStr.lastIndexOf(","));
	//alert("idStr = " + idStr  +  "   nameStr = "+ nameStr);

    parent.closeOpenRoleDiv(idStr,nameStr);
}
//-->
</SCRIPT>


</BODY>
</HTML>

