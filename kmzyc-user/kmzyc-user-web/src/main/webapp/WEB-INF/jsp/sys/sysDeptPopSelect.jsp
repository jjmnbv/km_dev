<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择部门</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
</head>
<body>

<s:form action="gotoPopSelectDept.action" method="POST"  namespace='/sys'>

<br>

<table width="90%" class="table_search" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<th align="right">部门名称：</th>
		<td>
		     <input name="deptName" type="text" value="<s:property value='model.deptName'/>">
		</td>
		<th align="right">&nbsp;</th>
        <td>&nbsp;</td>
		<td>
			<INPUT TYPE="submit" class="btngray" value=" 查询 ">
		</td>
	</tr>
</table>

<br>

<table width="90%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
	<tr>
	    <th bgcolor="#99ccff" align="center" width="30%">选择</th>
		<th bgcolor="#99ccff" align="center" width="40%">部门名称</th>
		<th bgcolor="#99ccff" align="center" width="30%">部门编号</th>
	</tr>
	<s:iterator id="oneObj" value="deptList">
	<tr>
	    <td align="center">
		    <INPUT TYPE="button" class="btngreen" VALUE="选择" onclick="selectOneDept('<s:property value="deptId"/>','<s:property value="deptName"/>')">
		</td>
		<td bgcolor="#FFFFCC" align="center"><b><s:property value="deptName"/></b></td>
		<td bgcolor="#FFFFCC" align="center"><s:property value="deptCd"/></td>
	</tr>
	</s:iterator>
</table>


<br>


</s:form>


<SCRIPT LANGUAGE="JavaScript">
function selectOneDept(deptId, deptName) {
    parent.closeOpenDept(deptId,deptName);
}
</SCRIPT>


</BODY>
</HTML>

