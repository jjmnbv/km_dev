<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.sys.model.SysDept"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索选择部门</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
</head>
<body>

<s:form action="searchDeptPage.action" method="POST"  namespace='/sys'>

<div class="title">【搜索并选择部门】</div>
<br>
<table class="table_search" width="90%" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<th align="right">部门名称：</th>
		<td>
		     <input name="deptName" type="text" value="<s:property value='model.deptName'/>">
		</td>
	<!--
		<th align="right">负责人：</th>
        <td>
		    <input name="deptManager" type="text" value="<s:property value='model.deptManager'/>">
		</td>
	-->
		<td>
			<INPUT TYPE="submit" value=" 查询 ">
		</td>
	</tr>
</table>

<br>

<table width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
	<tr>
	    <th align="center" width="10%">选择</th>
		<th bgcolor="#99ccff" align="center" width="25%">部门名称</th>
	<!--
		<th bgcolor="#99ccff" align="center" width="10%">负责人</th>
		<th bgcolor="#99ccff" align="center" width="15%">联系电话</th>
		<th bgcolor="#99ccff" align="center" width="20%">所在地</th>
		<th bgcolor="#99ccff" align="center" width="10%">运作方式</th>
	-->
        <th bgcolor="#99ccff" align="center" width="10%">注册时间</th>
	</tr>
	<s:iterator id="custiterator" value="deptList">
	<%SysDept list_obj = (SysDept)request.getAttribute("custiterator");%>
	<tr>
	    <td bgcolor="#FFFFCC" align="center">
		    <input type="button" value=" 选择 " onclick="selectThisDept('<s:property value="deptId"/>','<s:property value="deptName"/>')"/>
		</td>
		<td bgcolor="#FFFFCC" align="center"><s:property value="deptName"/></td>
		<td bgcolor="#FFFFCC" align="center"><s:property value="deptCd"/></td>
	</tr>
	</s:iterator>
</table>

<br><br>


</s:form>
<SCRIPT LANGUAGE="JavaScript">
<!--
function selectThisDept(deptId,deptName) {
	//alert("custId="+custId);
	window.opener.refreshList(deptId,deptName);
    window.close();
}
//-->
</SCRIPT>

</BODY>
</HTML>

