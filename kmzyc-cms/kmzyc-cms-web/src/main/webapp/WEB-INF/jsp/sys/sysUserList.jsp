<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.sys.model.SysUser"%>
<%@ page import="com.pltfm.sys.model.SysDept"%>
<%@ page import="com.pltfm.sys.bean.SysDeptBean"%>
<%@ page import="com.pltfm.sys.bean.SysUserBean"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员管理</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form action="/sys/listSysUser.action" method="POST"  namespace='/sys' id="frm" name='frm'>

<!-- 标题条(备) -->
<!--div id="mainbar" align="center"> 
	<ul class="mainbarlayout">
		<li> 
			<div class="orangebar_on"><div class="orangebarwords_on">操作员管理</div></div>
		<li>
			<div class="orangebar_off"> 
			    <div class="orangebarwords_off" onClick="location.href='#s02'" onMouseOver="this.style.cursor='hand'">更多</div>
			</div>
	</ul>
</div-->

<s:if test='"updateOK".equals(rtnMessage)'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert("修改操作员信息成功!");
	//-->
	</SCRIPT>
</s:if>
<s:if test='"saveOk".equals(rtnMessage)'>
    <SCRIPT LANGUAGE="JavaScript">
	<!--
		alert("新增操作员成功!");
	//-->
	</SCRIPT>
</s:if>
<s:if test='"deleteOK".equals(rtnMessage)'>
    <SCRIPT LANGUAGE="JavaScript">
	<!--
		alert("删除操作员成功!");
	//-->
	</SCRIPT>
</s:if>

<!-- 标题条 -->
<div class="pagetitle">操作员管理:</div>


<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="button" value="+ 新增 " onclick="gotoAdd();">
			<input class="btngreen" type="button" value="- 删除 "  onclick="deleteSelected('delId');">
		</td>
	    <td width="10%" align="center"><!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a--></td>
	</tr>
</table>


<!-- 查询条件区域 -->
<table  width="98%" class="searcharea" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td>部门：</td>
		<td>
			<%
			  //获取部门
			  SysDeptBean deptbean = SysDeptBean.instance();
			  java.util.List deptList =  deptbean.getSysDeptList(new SysDept());
			  request.setAttribute("deptList",deptList);
			%>
			<s:select name="deptId" emptyOption="true" list="#request.deptList" listKey="deptId" listValue="deptName" value="%{model.deptId}"/>
			
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
	<% SysUserBean userbean = SysUserBean.instance(); %>
	<s:iterator id="custiterator" value="page.dataList">
	<%SysUser list_obj = (SysUser)request.getAttribute("custiterator");%>
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox" name="delId"  value='<s:property value="userId"/>'>
		</td>
		<td align="center"><s:property value="userName"/></td>
		<td align="center"><s:property value="userReal"/></td>
		<td align="center"><s:property value="userPhone"/></td>
		<td align="center">
			<%
			  String userSex = list_obj.getUserSex();  //性别
			%>
			<%if("0".equals(userSex)){%>女<%}else if("1".equals(userSex)){%>男<%}else{%>不详<%}%>
			<!-- <%=StaticParams.getParamNameByCd("all","custSex",userSex)%>  -->
		</td>
		<td align="center">
		    <%
			  //部门名称获取
			  SysDept dept = deptbean.getSysDeptDetail(list_obj.getDeptId());
			  String deptname ="";
			  if(dept!=null) deptname=dept.getDeptName();
			%>
			<%=deptname%>
		</td>
		<td align="center">
<%
            SysUser userRole = userbean.getSysUserRoleDetail(list_obj.getUserId());
			String roleNameStr = userRole.getRoleName();
%>
            <%=roleNameStr%>
		</td>
		<td align="center"><INPUT TYPE="button" class="btngray" value=" 修改 " onclick="gotoUpdate(<s:property value='userId'/>)"></td>
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
<!--

//返回我的桌面界面
function gotoList(){
    location.href="/sys/gotoSysMain.action";
}

function gotoAdd(){
    location.href="/sys/gotoSysUserAdd.action";
}

function gotoUpdate(userId){
    location.href="/sys/gotoSysUserUpdate.action?userId="+userId;
}
function doDelete(name){
	document.forms['frm'].action="/sys/deleteSysuser.action";
	document.forms['frm'].submit();
}
</SCRIPT>

</BODY>
</HTML>
