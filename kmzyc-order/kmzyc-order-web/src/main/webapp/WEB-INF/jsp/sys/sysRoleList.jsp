<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.sys.model.SysRole"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form action="listSysRole.action" method="POST"  namespace='/sys'>

<s:if test='"updateOK".equals(rtnMessage)'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert("修改角色信息成功!");
	//-->
	</SCRIPT>
</s:if>
<s:if test="'saveOk'.equals(rtnMessage)">
<tr> 
    <SCRIPT LANGUAGE="JavaScript">
	<!--
		alert("新增角色成功!");
	//-->
	</SCRIPT>
</tr>
</s:if>
<s:if test="'deleteOK'.equals(rtnMessage)">
<tr> 
    <SCRIPT LANGUAGE="JavaScript">
	<!--
		alert("删除角色成功!");
	//-->
	</SCRIPT>
</tr>
</s:if>

<!-- 标题条 -->
<div class="pagetitle">角色管理:</div>

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
		<td align="right">角色名称：</td>
		<td>
		     <input name="roleName" type="text" value="<s:property value='model.roleName'/>">
		</td>
		<td align="center">
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
		<th align="center" width="25%">角色名称</th>
		<th align="center" width="40%">角色职责说明</th>
		<th align="center" width="15%">角色修改</th>
		<th align="center" width="15%">权限管理</th>
	</tr>
	<s:iterator id="custiterator" value="dataList">
	<%SysRole list_obj = (SysRole)request.getAttribute("custiterator");%>
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox" name="delId"  value='<s:property value="roleId"/>'>
		</td>
		<td align="center">
		    <a href="javascript:gotoUpdate(<s:property value='roleId'/>)"><s:property value="roleName"/></a>
		</td>
		<td style="word-break:break-all" align="center"><s:property value="roleExplain"/></td>
		<td align="center"><INPUT TYPE="button" class="btngray" value=" 修改 " onclick="gotoUpdate(<s:property value='roleId'/>)"></td>
		
		<td align="center">
		    <INPUT TYPE="button" value=" 菜单授权 " class="btngray" onclick="gotoRoleMenuEdit(<s:property value='roleId'/>,'<s:property value="roleName"/>')">
		</td>
	</tr>
	</s:iterator>
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
    location.href="/sys/gotoSysRoleAdd.action";
}

function gotoUpdate(roleId){
    location.href="/sys/gotoSysRoleUpdate.action?roleId="+roleId;
}


function gotoRoleMenuEdit(roleId, roleName){
	location.href = "/sys/listMenuByRole.action?roleId="+roleId;
}

function gotoRoleCatgroupEdit(roleId){
	location.href = "/sys/gotoRoleCatgroupAction.action?sysRole.roleId="+roleId;
}

function checkAll(ck)
{
  for (var i=0;i<ck.form.all.tags("input").length;i++){
    var ele = ck.form.all.tags("input")[i];
    /*var ct = ele.getAttribute("type");*/
    if ((ele.type=="checkbox")){
      if(ck.checked!=ele.checked)
        ele.click();
    }
  }
}

function checkSelected(sName){
   window.event.returnValue = false;
   var chs = document.getElementsByName(sName);
   for(var i=0;i<chs.length;i++){
   	  var ele = chs[i];
   	  if(ele.type=="checkbox" && ele.checked==true)
	   	  return true;
   }
   return false;
}

function deleteSelected(sName)
{

   if (!checkSelected(sName))
   {
      alert("请选择要删除的数据！");
      return false;
   }
   if (confirm("你确定要删除选中的数据？"))
   {
      doDelete(sName);
   }
}

function doDelete(){
	document.forms[0].action="deleteSysRole.action";
	document.forms[0].submit();
}
//-->
</SCRIPT>

</BODY>
</HTML>

