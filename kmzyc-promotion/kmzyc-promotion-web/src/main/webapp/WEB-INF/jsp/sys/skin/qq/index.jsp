<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>管理平台-platform2.0</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="renderer" content="webkit|ie-comp|ie-stand"> 
<link href="/etc/css/style_sys_skin.css" rel="stylesheet" type="text/css" />
</head>
<frameset rows="80,*" cols="*"  border="1" framespacing="0" frameborder="NO">
	<frame src="/sys/gotoSysTop.action" name="top" scrolling="NO" >
	<frameset cols="170,5,*" rows="*" frameborder="NO" border="1" framespacing="0" id="lkoamenu_frame" name="lkoamenu_frame">
		<frame src="/sys/gotoSysLeft.action" name="left" scrolling="NO" noresize onchange="check()">
		<frame src="/sys/gotoSysCompart.action" name="left2" scrolling="NO" noresize onchange="check()">
		<frame src="/sys/gotoSysMain.action" name="main" scrolling="yes">
    </frameset>
</frameset><noframes></noframes>
</html>