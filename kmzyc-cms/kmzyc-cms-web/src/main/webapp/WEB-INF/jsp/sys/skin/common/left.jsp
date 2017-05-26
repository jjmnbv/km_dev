<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<link href="/etc/css/style_sys_skin.css" rel="stylesheet" type="text/css" />
</head>

<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<table width="180" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="180" height="86" valign="middle" ><div id="menutop"><br/>
        <span style='font-family:"宋体";color:#AE0000;font-size:14px;font-weight:bold;'>
        <s:property value="topMenuName" />
        </span></div></td>
  </tr>
  <tr>
    <td width="180" align="center" valign="top" background="/etc/images/sys/menu_bg.gif"><div id="menubg">
        <iframe style="z-index:2;visibility:inherit;width:100%;height:100%;" name="bottom" src="/sys/gotoSysLeftMenu.action?topMenuId=<s:property value='topMenuId'/>" frameborder="0"></iframe>
      </div>
      <script   language="Javascript">   
	  window.onload=function()   
	  {   
	  window.document.getElementById("menubg").style.height=document.documentElement.clientHeight-97;   
	  }   
	</script></td>
  </tr>
  <tr>
    <td width="180" height="11" valign="bottom"><div id="menudi"></div></td>
  </tr>
</table>
</body>
</html>
