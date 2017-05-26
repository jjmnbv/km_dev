<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<Script  src="/etc/js/sys/sysRoleList.js" type="text/javascript"></Script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.min.js"></script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'系统配置'" scope="request" />
<s:set name="name" value="'角色管理'" scope="request" />
<s:set name="son_name" value="'角色列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form action="listSysRole.action" method="POST"  namespace='/sys'>
<!-- 搜索区-->
<table width="98%" align="center" border="0" class="content_table" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle" colspan="3">
           
	</tr>
	<tr>
		<td align="right">角色名称：</td>
		<td>
		     <input name="roleName"  style="height:20px" type="text" value="<s:property value='model.roleName'/>">
		</td>
		<td align="right">
			<INPUT TYPE="submit" class="queryBtn" value="">
             <INPUT class="addBtn" TYPE="button"  onclick="gotoAdd();">
			<input class="delBtn" type="button" onClick="deleteSelected('delId');">
		</td>
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" cellpadding="3" align="center" cellspacing="0" border="1">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox' onclick='checkAll(this)'>
		</th>
		<th align="center" width="25%">角色名称</th>
		<th align="center" width="40%">角色职责说明</th>
		<th align="center" width="15%">角色修改</th>
		<th align="center" width="15%">权限管理</th>
	</tr>
	<s:iterator id="custiterator" value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox" name="delId"  value='<s:property value="roleId"/>'>
		</td>
		<td align="center">
		    <a href="javascript:gotoUpdate(<s:property value='roleId'/>)"><s:property value="roleName"/></a>
		</td>
		<td style="word-break:break-all" align="center"><s:property value="roleExplain"/></td>
		<td align="center"><INPUT TYPE="button" class="btngray" value=" 修改 " onClick="gotoUpdate(<s:property value='roleId'/>)"></td>
		
		<td align="center">
		    <INPUT TYPE="button" value=" 菜单授权 " class="btngray" onClick="gotoRoleMenuEdit(<s:property value='roleId'/>,'<s:property value="roleName"/>')">
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
<!-- 消息提示 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>

</BODY>
</HTML>

