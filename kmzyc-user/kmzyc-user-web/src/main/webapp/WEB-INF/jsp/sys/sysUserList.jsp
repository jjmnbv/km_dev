<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.min.js"></script>
<Script  src="/etc/js/sys/sysUserList.js" type="text/javascript"></Script>
<script src="/etc/js/dialog.js"></script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'系统配置'" scope="request" />
<s:set name="name" value="'操作员管理'" scope="request" />
<s:set name="son_name" value="'操作员列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form action="/sys/listSysUser.action" method="POST"  namespace='/sys' id="frm" name='frm'>
<!-- 查询条件区 -->
<table width="98%" align="center" border="0" class="content_table" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle" colspan="6">
            
		</td>
	</tr>
	<tr>
		<td align="right">部门：</td>
		<td>
			<s:select name="sysuser.deptId" emptyOption="true" list="deptList" listKey="deptId" listValue="deptName" value="%{model.deptId}"/>
		</td>
		<td align="right">用户名：</td>
        <td>
		    <input name="userName" type="text" style="height:20px" value="<s:property value='model.userName'/>">
		</td>
		<td align="right">真实姓名：</td>
		<td>
		     <input name="userReal" type="text" style="height:20px" value="<s:property value='model.userReal'/>">
		</td>
        </tr>
        <tr>
		<td colspan="6" align="right">
			<INPUT TYPE="submit" class="queryBtn" value=" ">
            <INPUT class="addBtn" TYPE="button"  onclick="gotoAdd();">
			<input class="delBtn" type="button" onClick="deleteSelected('delId');">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" cellpadding="3" align="center" cellspacing="0" border="1">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox' onclick='checkAll(this)'>
		</th>
		<th align="center" width="15%">用户名</th>
		<th align="center" width="15%">真实姓名</th>
		<th align="center" width="10%">联系电话</th>
		<th align="center" width="10%">性别</th>
		<th align="center" width="20%">所属部门</th>
		<th align="center" width="15%">岗位角色</th>
		<th align="center" width="10%">操作</th>
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
		<td align="center"><INPUT TYPE="button" class="btngray" value=" 修改 " onClick="gotoUpdate(<s:property value='userId'/>)"></td>
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
<!-- 消息提示 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</BODY>
</HTML>