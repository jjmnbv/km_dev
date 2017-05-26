<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择管理员</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form action="/sys/listSysUser.action" method="POST"  namespace='/sys' id="frm" name='frm'>
<!-- 标题条 -->
<div class="pagetitle">选择管理员:</div>
<!-- 查询条件区域 -->
<table  width="98%" class="searcharea" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td>部门：</td>
		<td>
			<s:select name="sysuser.deptId" emptyOption="true" list="deptList" listKey="deptId" listValue="deptName" value="%{model.deptId}"/>
			
		</td>
		<td align="right">用户名：</td>
        <td>
		    <input name="userName" type="text" value="<s:property value='model.userName'/>">
		</td>
		<td align="right">真实姓名：</td>
		<td>
		     <input name="userReal" type="text" value="<s:property value='model.userReal'/>">
		</td>
		<td>
			<INPUT TYPE="submit" class="button-blue-1" value=" 查询 ">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="tableStyle1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		<th align="center" width="10%">选择</th> 
		<th align="center" width="15%">用户名</th>
		<th align="center" width="15%">真实姓名</th>
		<th align="center" width="10%">联系电话</th>
		<th align="center" width="10%">性别</th>
		<th align="center" width="20%">所属部门</th>
		<th align="center" width="15%">岗位角色</th>
	</tr>
	<s:iterator id="custiterator" value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox" name="delId"  value='<s:property value="userId"/>'>
		</td>
		<td align="center"><s:property value="userName"/></td>
		<td align="center"><s:property value="userReal"/></td>
		<td align="center"><s:property value="userPhone"/></td>
		<td align="center">
		<s:if test="userSex==1">
			男
		</s:if><s:elseif test="userSex==0">
			女
		</s:elseif>
			
		</td>
		<td align="center">
			<s:property value="deptName"/>
		</td>
		<td align="center">
			<s:property value="roleName"/>
		</td>
	</tr>
	</s:iterator>
</table>

<!-- 分页按钮区 -->
<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td>
			<%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
		</td>
	</tr>
</table>

<br><br>


</s:form>
<SCRIPT LANGUAGE="JavaScript">

function selectOneUser(userId,userReal,deptId,deptName) {
    parent.closeOpenUser(userId,userReal,deptId,deptName);
}
</SCRIPT>
</BODY>
</HTML>