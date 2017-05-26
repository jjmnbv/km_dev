<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.opensymphony.xwork2.ognl.OgnlValueStack"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改操作员</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
</head>
<body onkeydown="changeKey();">

<%
  OgnlValueStack stack = (OgnlValueStack)request.getAttribute("struts.valueStack");
  String userSex = (String)stack.findValue("model.userSex");
%>


<s:form action="doUpdateSysUser.action" method="POST"  namespace='/sys' onsubmit="return Validator.Validate(this,3)">

<!-- hidden properties -->
<INPUT TYPE="hidden" name="userId" value="<s:property value='model.userId'/>">

<!-- 标题条 -->
<div class="pagetitle">修改操作员:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="submit" value=" 保存 ">
		</td>
        <td width="10%" align="center"><a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a></td>
	</tr>
</table>



<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<!-- error message -->
	
	<tr> 
		<th colspan="2" align="left" class="modeltitle">基本信息</th>
	</tr>

	<tr> 
		<th width="30%" align="right"><font color="red">*</font>用户名：</th>
		<td> 
			<input name="userName" type="text" require="true" dataType="LimitB" max="10" min="1" msg="用户名必输，且不超过10个字符" value="<s:property value='model.userName'/>"/>
		</td>
	</tr>
	<tr> 
		<th width="30%" align="right">密码：</th>
		<td> 
			<input name="newPwd1" type="text" require="false" dataType="LimitB" max="10" min="1" msg="密码必输，且不超过10个字符" value="<s:property value='model.userPwd'/>"/>
		</td>
	</tr>
	<tr> 
		<th width="30%" align="right">真实姓名：</th>
		<td> 
			<input name="userReal" type="text" require="false" dataType="LimitB" max="20" min="0" msg="真实姓名不超过10个汉字" value="<s:property value='model.userReal'/>"/>
		</td>
	</tr>
	<tr> 
		<th width="20%" align="right">昵称：</th>
		<td width="80%"> 
			<input name="userAlias" type="text" require="false" dataType="LimitB" max="20" min="0" msg="昵称不超过10个汉字" value="<s:property value='model.userAlias'/>"/>&nbsp;&nbsp;(填写管理员发布新闻时的笔名。)
		</td>
	</tr>
	<tr> 
		<th width="30%" align="right">性别：</th>
		<td> 
			<input name="userSex" type="radio" value="1" <%="1".equals(userSex)?"checked":""%> />
			<img src="/etc/images/boy.gif">男 &nbsp;&nbsp;
			<input name="userSex" type="radio" value="0"  <%="0".equals(userSex)?"checked":""%>/>
			<img src="/etc/images/girl.gif">女
		</td>
	</tr>
	<tr> 
		<th width="30%" align="right">身份证号码：</th>
		<td> 
			<input name="userCardno" type="text" require="false" dataType="LimitB" max="20" min="0" msg="身份证号码不超过20个字符" value="<s:property value='model.userCardno'/>"/>
		</td>
	</tr>
	<tr> 
		<th width="30%" align="right">联系电话：</th>
		<td> 
			<input name="userPhone" type="text" require="true" dataType="LimitB" max="20" min="0" msg="联系电话不超过20个字符" value="<s:property value='model.userPhone'/>"/>
		</td>
	</tr>

	<tr> 
		<th width="30%" align="right">生日：</th>
		<td> 
			<input name="userBirthday" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value='<s:date   name="model.userBirthday"  format="yyyy-MM-dd"/>'/>
		</td>
	</tr>

	<tr> 
		<th width="30%" align="right">备注：</th>
		<td> 
			<textarea name="userRemark" cols="50" rows="4" wrap="PHYSICAL" require="false" dataType="LimitB" max="200" msg="备注不要超过100个汉字"><s:property value='model.userRemark'/></textarea> 
		</td>
	</tr>
</table>


<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="modeltitle">权限信息</th>
	</tr>
	<tr> 
		<th width="30%" align="right"><font color="red">*</font>所属部门：</th>
		<td> 
		    <input name="deptId" type="hidden" value="<s:property value='model.deptId'/>">
			<input name="deptName" type="text"  class="inpt_gray" readonly="true" dataType="Require" msg="所属部门必选" value="<s:property value='deptName'/>"/>
			<INPUT TYPE="button" class="button-2s" value="选择" onclick="popSelectDept()">
		</td>
	</tr>
	<tr> 
		<th width="30%" align="right"><font color="red">*</font>岗位角色：</th>
		<td> 
			<INPUT TYPE="hidden" name="roleListStr" value="<s:property value='model.roleIdsStr'/>">
			<INPUT TYPE="text" class="inpt_gray" readonly="true" name="roleName" value="<s:property value='model.roleName'/>"  dataType="Require" msg="岗位角色必选">
			<INPUT TYPE="button" class="button-2s" value="选择" onclick="popSelectRole()">
		</td>
	</tr>

<!-- 底部 按钮条 -->
<table width="98%" align="center" class="bottombuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="center">
			<INPUT class="btngreen" TYPE="submit" value=" 保 存 ">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a>
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>


</s:form>
<SCRIPT LANGUAGE="JavaScript">
<!--
//弹出层 选择部门
function popSelectDept() {
    dialog("选择部门","iframe:/sys/gotoPopSelectDept.action" ,"500px","340px","iframe");
}

function closeOpenDept(deptId,deptName){
    closeThis();
    document.forms[0].deptId.value = deptId;
	document.forms[0].deptName.value = deptName;
}


//弹出层 选择角色
function popSelectRole() {
    dialog("选择岗位角色","iframe:/sys/gotoPopSelectRole.action" ,"500px","340px","iframe");
}
function closeOpenRoleDiv(roleId,roleName){
    closeThis();
    document.forms[0].roleListStr.value = roleId;
	document.forms[0].roleName.value = roleName;
}



function gotoList(){
    location.href="/sys/listSysUser.action";
}




//光标移动
function changeKey()
{
	var tr=event.srcElement.getAttribute("type");
	if("textarea"!=tr && "button" != tr)
	{
			if(13 == event.keyCode)
			{
				event.keyCode=9;
			}
  }
}
//-->
</SCRIPT>
</BODY>
</HTML>