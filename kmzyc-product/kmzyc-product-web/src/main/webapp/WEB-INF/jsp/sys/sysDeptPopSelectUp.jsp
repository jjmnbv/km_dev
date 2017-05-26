<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.sys.model.SysRole"%>
<%@ page import="com.pltfm.sys.model.SysUser"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择部门</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
</head>
<body>

<s:form action="gotoPopSelectDept.action" method="POST"  namespace='/sys'>


<table width="90%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
	<tr>
	    <th bgcolor="#99ccff" align="center" width="30%">选择</th>
		<th bgcolor="#99ccff" align="center" width="40%">部门名称</th>
		<th bgcolor="#99ccff" align="center" width="30%">部门编号</th>
	</tr>
	<tr>
	    <td align="center">
		    <INPUT TYPE="button" class="btngreen" VALUE="选择" onclick="selectOneDept('','','')">
		</td>
		<td bgcolor="#FFFFCC" align="center"><b>无父节点</b></td>
		<td bgcolor="#FFFFCC" align="center">&nbsp;</td>
	</tr>
	<s:iterator id="oneObj" value="deptList">
	<tr>
	    <td align="center">
		    <INPUT TYPE="button" class="btngreen" VALUE="选择" onclick="selectOneDept('<s:property value="deptId"/>','<s:property value="deptName"/>','<s:property value="deptLv"/>')">
		</td>
		<td bgcolor="#FFFFCC" align="center"><b><s:property value="deptName"/></b></td>
		<td bgcolor="#FFFFCC" align="center"><s:property value="deptCd"/></td>
	</tr>
	</s:iterator>
</table>


<br>


</s:form>


<SCRIPT LANGUAGE="JavaScript">
<!--
function selectOneDept(deptId, deptName) {
    parent.closeOpenDept(deptId,deptName);
}
//-->
</SCRIPT>


</BODY>
</HTML>

