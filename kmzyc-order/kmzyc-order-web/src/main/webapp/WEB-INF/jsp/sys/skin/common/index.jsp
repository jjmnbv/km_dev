<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<head>
<title>管理平台-platform2.0</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="/etc/css/style_sys_skin.css" rel="stylesheet" type="text/css" />
</head>
<frameset rows="60,*,11" cols="*"  border="0" framespacing="0" frameborder="NO">
  <frame src="/sys/gotoSysTop.action" name="top" scrolling="NO" noresize>
  <frameset cols="180,9,*" rows="*" frameborder="NO" border="0" framespacing="0" id="lkoamenu_frame" name="lkoamenu_frame">
    <frame src="/sys/gotoSysLeft.action" name="left" scrolling="NO" noresize onchange="check()">
    <frame src="/sys/gotoSysCompart.action" name="left2" scrolling="NO" noresize onchange="check()">
    <frame src="/sys/gotoSysMain.action" name="main" scrolling="yes">
  </frameset>
  <frame src="/sys/gotoSysbuttom.action" frameborder="NO" scrolling="NO" name="bottom" marginwidth="0" marginheight="0" style="BORDER-right: #aeaeae 1px solid;BORDER-BOTTOM: #aeaeae 1px solid;BORDER-left: #aeaeae 1px solid;">
</frameset>
<noframes></noframes>
</html>