<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.model.SysMenu"%>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改菜单信息</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<script language="JavaScript" src="/etc/js/dialog-up.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/jquery-latest.pack.js" type="text/javascript"></script>
</head>
<body onkeydown="changeKey();">

<form name="updateForm" action="doUpdateSysMenu.action" method="POST"  namespace='/sys' target="main" onsubmit="return Validator.Validate(this,3)">
<INPUT TYPE="hidden" name="menuId" id="menuId" value="<s:property value='model.menuId'/>">
<INPUT TYPE="hidden" name="menuLv" id="menuLv" value="<s:property value='model.menuLv'/>">
<INPUT TYPE="hidden" name="menuSt" id="menuLv" value="<s:property value='model.menuSt'/>">
<INPUT TYPE="hidden" name="isEnable" id="menuLv" value="<s:property value='model.isEnable'/>">
<INPUT TYPE="hidden" name="createDate" id="menuLv" value="<s:property value='model.createDate'/>">
<INPUT TYPE="hidden" name="createUser" id="menuLv" value="<s:property value='model.createUser'/>">
<INPUT TYPE="hidden" name="updateUser" value="<s:property value="#session['sysUser'].userId"/>">
	<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
		<tr> 
			<th colspan="2" align="left" class="modeltitle">修改菜单信息:<s:property value='model.menuName'/></th>
		</tr>
		<tr> 
			<th align="right"><font color="red">*</font>菜单名称：</th>
			<td> 
				<input name="menuName" type="text" size="40" require="true" dataType="LimitB" max="40" min="1" msg="菜单名称必输，且不超过20个汉字" value="<s:property value='model.menuName'/>"/>
			</td>
		</tr>
		<tr> 
			<th width="20%" align="right">菜单图片：</th>
			<td width="80%"> 
			<input name="menuImg" size="30"  msg="非法的文件格式" type="file" accept="jpg,gif,png" value="<s:property value='model.menuImg'/>"/>
			</td>
		</tr>
		<tr> 
			<th width="20%" align="right">链接地址：</th>
			<td width="80%"> 
				<input type="text" name="menuLink" size="40"  value="<s:property value='model.menuLink'/>"/>  
			</td>
		</tr>
		<tr> 
			<th width="20%" align="right">菜单序号：</th>
			<td width="80%"> 
				<input type="text" name="sortno" size="5"  value="<s:property value='model.sortno'/>" require="false" dataType="Integer" msg="序号请输入整数" />  
			</td>
		</tr>
		<tr> 
			<th width="20%" align="right">上级ID：</th>
			<td width="80%"> 
				<input type="text" name="menuUpid" size="5"  value="<s:property value='model.menuUpid'/>" require="false" dataType="Integer" msg="上级ID请输入整数" />  
			</td>
		</tr>
		<tr>
			<th align="right">菜单备注：</th>
			<td> 
				<textarea name="menuRemark" cols="40" rows="4" wrap="PHYSICAL" require="false" dataType="LimitB" max="200" msg="备注不要超过100个汉字"><s:property value='model.menuRemark'/></textarea> 
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" class="btngreen"  value="  保存  "/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btngreen"  value=" 删除" onclick="deleteThisClass();"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btngreen"  value=" 新增下级菜单" onclick="addSonClass();"/>
			</td>
		</tr>
	</table>




</form>

<SCRIPT LANGUAGE="JavaScript">
<!--
function deleteThisClass(){
    var isHaveSon = "<s:property value='isHaveSon'/>";
    if(isHaveSon!=null&&isHaveSon=="1"){
		alert("该菜单下面有子菜单，请不要删除！！！");
		return;
    }
    if(confirm("您确定要删除该菜单吗？")){
    	window.document.updateForm.action = "gotoSysMenuDelete.action";
    	window.document.updateForm.target = "main";
		window.document.updateForm.submit();
	}
}
function addSonClass(){
	var menuId = window.document.getElementById("menuId").value;
	var menuLv = window.document.getElementById("menuLv").value;
	if(menuLv>=0){
		menuLv = Number(menuLv)+1;
	}
	dialog("新增子菜单","iframe:gotoSysMenuAdd.action?menuUpid="+menuId+"&menuLv="+menuLv,"500px","310px","iframe");
}

function closePopDiv(){
	closeThis();
}

function closePopDiv2(){
	closeThis();
	alert("保存完毕！");
	window.document.updateForm.action = "listMenu.action";
	window.document.updateForm.target = "main";
	window.document.updateForm.submit();
}
-->
</SCRIPT>

</BODY>
</HTML>