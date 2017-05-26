<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<link href="/etc/css/style_sys_skin.css" rel="stylesheet"
			type="text/css" />
<style >
#leftMenu { padding: 0px; margin: 0px; background: #E1F3E7; }
</style>
</head>
<body background="#E1F3E7">
<div id="leftMenu" width="100%" >
  <div style="background: #336699; width:160px;border-bottom:1px solid #079346;height: 40px; padding-left: 10px; text-align: left; line-height: 35px; color: #fff; font-size: 15px;"> <span style="font-size:14px;font-weight:bold;color:#028043;"> <img src="/etc/images/sys/icon_01.png"  style="vertical-align: middle; ">&nbsp;
    <s:property value="topMenuName"/>
    </span> 订单系统 </div>
  <div id="menubg" >
    <iframe 
					style="filter:chroma(color=#ffffff); visibility: inherit; width: 100%; height: 100%; "
					name="bottom"
					src="/sys/gotoSysLeftMenu.action?topMenuId=<s:property value='topMenuId'/>"
					frameborder="0"  allowTransparency="true" ></iframe>
  </div>
  <script language="Javascript">
				window.onload = function() {
					window.document.getElementById("menubg").style.height = document.documentElement.clientHeight - 97;
				}
			</script> 
</div>
</body>
</html>
