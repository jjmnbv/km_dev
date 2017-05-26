<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.opensymphony.xwork2.ognl.OgnlValueStack"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的信息</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<script language="javascript">
function fPopUpCalendarDlg(ctrlobj)
{
	showx = event.screenX - event.offsetX - 4 - 210 ; // + deltaX;
	showy = event.screenY - event.offsetY + 18; // + deltaY;
	newWINwidth = 210 + 4 + 18;

	var retval = window.showModalDialog("/etc/js/calendar.htm", ctrlobj, "dialogWidth:235px; dialogHeight:218px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; location:no; scrollbars:no; Resizable:no; "  );
	
}
</script>
</head>
<body onkeydown="changeKey();">
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<%
  OgnlValueStack stack = (OgnlValueStack)request.getAttribute("struts.valueStack");
  String userSex = (String)stack.findValue("model.userSex");
%>


<s:form action="doSysUserPwdModify.action" method="POST"  namespace='/sys' onsubmit="return  Validator.Validate(this,3)">

<!-- hidden properties -->
<INPUT TYPE="hidden" name="userId" value="<s:property value='model.userId'/>">

<s:if test='rtnMessage.equals("updateOK")'>
    <SCRIPT LANGUAGE="JavaScript">
    <!--
        alert("密码修改成功!可用新密码登陆！");
    //-->
</SCRIPT>
</s:if>
<s:if test='rtnMessage.equals("updateFalse")'>
    <SCRIPT LANGUAGE="JavaScript">
    <!--
        alert("密码修改失败!");
    //-->
    </SCRIPT>
</s:if>

<!-- 标题条 -->
<div class="pagetitle">密码修改:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
    
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="submit" value=" 保存 ">
		</td>
        <td width="10%" align="center"><!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a--></td>
	</tr>
</table>

<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr>
        <th width="30%" align="right"><font color="red">*</font>用户名：</th>
        <td> 
            <s:property value='model.userName'/>
        </td>
    </tr>
    <tr>
        <th width="30%" align="right"><font color="red">*</font>真实姓名：</th>
        <td> 
            <s:property value='model.userReal'/>
        </td>
    </tr>
    <tr>
        <th width="30%" align="right"><font color="red">*</font>原密码：</th>
        <td> 
            <input name="userPwd" type="password" require="true" dataType="LimitB" max="20" min="1" msg="输入原密码，且不超过20个字符" value="<s:property value=''/>"/>
        </td>
    </tr>
    <tr> 
        <th width="30%" align="right"><font color="red">*</font>新密码：</th>
        <td> 
            <input name="newPwd1" type="password" require="true" dataType="LimitB" max="20" min="1" msg="输入新密码，且不超过20个字符" value="<s:property value=''/>"/>
        </td>
    </tr>
    <tr> 
        <th width="30%" align="right"><font color="red">*</font>再次输入密码</th>
        <td> 
            <input name="newPwd2" type="password" require="true" dataType="Repeat" to="newPwd1" msg="两次密码输入不一致" value="<s:property value=''/>"/>
        </td>
    </tr>
</table>

<!-- 底部 按钮条 -->
<table width="98%" align="center" class="bottombuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td width="90%" valign="middle">
			<INPUT class="btngreen" TYPE="submit" value=" 保存 ">
		</td>
		<td width="10%" align="center"><!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a--></td>
	</tr>
</table>


</s:form>
<SCRIPT LANGUAGE="JavaScript">
<!--

//返回我的桌面界面
function gotoList(){
    location.href="/sys/gotoSysMain.action";
}

//弹出选择客户窗口
function popSearchDeptPage(){

	var URL="/sys/gotoSearchDeptPage.action";
	window.open(URL,"deptLeaveWord","width=600,height=400,top=100,left=200,toolbar=0,menubar=0,scrollbars=1,resizable=0,location=0,status=1");
}

function refreshList(deptId,deptName){
	document.forms[0].deptId.value = deptId;
	document.forms[0].deptName.value = deptName;
}



function checkSelect(){
	//校验身份证合法性
	if(!isIDno(document.forms[0].userCardno.value))
	{
        return false;
	}	

    return true;
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