<%@page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.pltfm.sys.model.SysUser"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>

<% 
SysUser sysUser = (SysUser)request.getSession().getAttribute("sysUser");
String uri= request.getHeader("Referer");
if(uri!=null&&(uri.contains("gotoSysTop.action")||uri.contains("gotoSysLeftMenu.action"))&&sysUser==null)
{
	%> 
	<script>
	window.onload=function(){
		if(window.top!=window.self){
			window.top.location.href="/sys/loginSysUser.action";
		}
	};
	</script>
	<%
}
%>
<title>登录后台管理</title>

<link href="/etc/css/base.css" rel="stylesheet" type="text/css"/>
<link href="/etc/css/page_login.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<s:form action="loginSysUser.action" method="post" name="userLoginForm" namespace="/sys">

<table width="1003" align="center" cellpadding="0" cellspacing="0" class="table">
  <tr>
    <td height="613" valign="top">
		<table width="300px" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td height="340" colspan="2">&nbsp;</td>
		  </tr>
		  <tr>
		    <td width="100" height="36">&nbsp;</td>
		    <td valign="top"><input type="text" name="userName" value="" id="userName" style="height:24px; line-height:24px; border:none; background:none;width: 130px"/></td>
		  </tr>
		  <tr>
		    <td height="35">&nbsp;</td>
		    <td valign="top"><input type="password" name="userPwd" id="userPwd" style="height:24px; line-height:24px; border:none; background:none;width: 130px" /></td>
		  </tr>
		  <tr>
		    <td>&nbsp;</td>
		    <td valign="top">
	
			<font color="red">
				<s:if test='rtnMessage=="loginError"'>登录失败,请检查用户名和密码!</s:if>
			  	<s:if test='rtnMessage=="sessionLost"'>session已丢失,请重新登陆! </s:if>
			</font>  
		    </td>
		  </tr>
		  <tr>
		    <td colspan="2">
				<table width="250" border="0" cellpadding="0" cellspacing="0" class="mt15">
				  <tr>
				    <td width="35">&nbsp;</td>
				    <td width="135"></td> 
				    <td height="30"><input type="image" onclick="userLoginForm.submit()"  src="/etc/images/login/btn_login.gif"/></td> 
				  </tr>
				</table>
		      </td>		      
		    </tr>		    
		</table>		
	</td>
	
   </tr>
</table>

</s:form>
</body>
</html>


