﻿<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<head>
<link href="/etc/css/style_sys_skin.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript">
function Show_Hide_Menu()
{
  if(window.parent.lkoamenu_frame.cols=="0,7,*")
  {
    document.getElementById("menuICON").src="/etc/images/sys/compart_button.gif";
    document.getElementById("menuICON").alt="隐藏菜单"
    window.parent.lkoamenu_frame.cols="160,7,*";
  }
  else
  {
    document.getElementById("menuICON").src="/etc/images/sys/compart_button1.gif";
    document.getElementById("menuICON").alt="显示菜单"
    window.parent.lkoamenu_frame.cols="0,7,*";
  }
}
</script>
</head>

<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<div id="compart_qq">
  <p><img src="/etc/images/sys/compart_button.gif" style="cursor:hand" alt="隐藏菜单" width="5" height="94" onClick="javascript:Show_Hide_Menu();" id="menuICON" /></p>
</div>
<script   language="Javascript">   
	  window.onload=function()   
	  {   
	  window.document.getElementById("compart_qq").style.height=document.documentElement.clientHeight;   
	  }   
	</script>
</body>
</html>
