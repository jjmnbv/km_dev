<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<!-- 
<link href="/etc/css/style_sys_main.css" rel="stylesheet" type="text/css" />
 -->
<script type="text/javascript" src="/etc/js/97dater/WdatePicker.js"></script> 
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<style type="text/css">
<!-- html{scrollbar-face-color:#A4DDFF;scrollbar-highlight-color:#AAE8FD;scrollbar-shadow-color:#0095E1;scrollbar-3dlight-color:#0095E1;scrollbar-arrow-color:#02338A;scrollbar-track-color:#C8EBF7;scrollbar-darkshadow-color:#C8EBF7;overflow-x:hidden}
@keyframes cloud-move{from{background-position:0 0}
to{background-position:-1680px 0}
}
@-o-keyframes cloud-move{from{background-position:0 0}
to{background-position:-1680px 0}
}
@-ms-keyframes cloud-move{from{background-position:0 0}
to{background-position:-1680px 0}
}
@-moz-keyframes cloud-move{from{background-position:0 0}
to{background-position:-1680px 0}
}
@-webkit-keyframes cloud-move{from{background-position:0 0}
to{background-position:-1680px 0}
}
.ui-cloud{position:fixed;left:0;bottom:0;z-index:-1;width:100%;height:170px;<!--    background:url(/etc/images/error_cloud.png) repeat-x scroll 0 0 transparent;animation:cloud-move 30s linear infinite;-o-animation:cloud-move 30s linear infinite;-ms-animation:cloud-move 30s linear infinite;-moz-animation:cloud-move 30s linear infinite;-webkit-animation:cloud-move 30s linear infinite}
body{margin:0;padding:0 0 12px 0;font-size:12px;line-height:22px;font-family:"宋体","Arial Narrow"}
a{color:#1f376d;text-decoration:none}
a:hover{color:#bd0a01;text-decoration:underline}
.fl{float:left}
.fr{float:right}
.cl{clear:both}
.mainarea{width:850px;border:0 #000 solid}
.leftarea{float:left;width:150px;margin:0 0 0 10px;padding-top:20px;border:0 green solid}
.centerarea{float:left;width:400px;margin:0 0 0 20px;padding-top:30px;border:0 green solid}
.rightarea{float:left;width:208px;margin:0 0 0 20px;padding-top:30px;border:0 green solid}
A IMG{BORDER-TOP-WIDTH:0;BORDER-LEFT-WIDTH:0;BORDER-BOTTOM-WIDTH:0;BORDER-RIGHT-WIDTH:0}
.img_bg100{BACKGROUND:url(/etc/images/img_bg100.gif) no-repeat;MARGIN:0 5px 5px 0;PADDING:4px 4px 12px 9px;OVERFLOW:hidden;WIDTH:95px;HEIGHT:85px}
.quicklinkarea{width:100px;padding-left:5px;color:silver}
.quicklinkarea li{padding-top:5px;border-bottom:1px solid silver}
#centerblockTitle{width:385px;height:21px;padding:2px 12px 0 12px;font-size:14px;font-weight:700;overflow:hidden;background-image:url(/etc/images/q_bar.png);background-repeat:no-repeat;background-position:0 -225px;color:#c5daef}
#centerblockTitle .fr{font-size:12px;font-weight:400}
#myMailArea{width:387px;height:104px;padding:10px 14px 0 6px;font-size:14px;line-height:24px;color:#BAB9B9;border-left:1px solid #D4D4D4;border-right:1px solid #D4D4D4}
#myWorkArea{width:387px;height:104px;padding:10px 14px 0 6px;font-size:14px;line-height:24px;color:#BAB9B9;border-left:1px solid #D4D4D4;border-right:1px solid #D4D4D4}
.centerblockBottom{height:2px;background-image:url(/etc/images/q_bar.png);background-position:0 -255px;background-repeat:no-repeat}
#mydater{margin-left:12px}
.rightblock{width:186px;margin-left:10px}
.rightblock h5{font-size:12px;font-weight:700;margin:2px 6px 1px 0;line-height:20px}
.rightblockTitle{height:2px;background-image:url(/etc/images/q_bar.png);background-repeat:no-repeat;background-position:0 -260px}
.rightblockBottom{height:2px;background-image:url(/etc/images/q_bar.png);background-repeat:no-repeat;background-position:0 -264px}
#rightblockInfo{height:200px;padding:6px 10px 7px 0;border-left:1px solid #d4d4d4;border-right:1px solid #d4d4d4;background:#f6f9fe}
#rightblockInfo ul{margin:0;padding:0;list-style-type:none}
#rightblockInfo li{background:url(/etc/images/q_bar.png) -392px -312px no-repeat;margin:0 0 0 4px;padding:0;font-size:12px;font-family:"宋体","Arial Narrow";list-style-type:none}
-->
</style>
</head>
<body >

<!-- 总div -->
<div class="mainarea" >
	<!-- ============== 左区域 ============= -->
	<div class="leftarea">
			<!--头像-->
			<div class="img_bg100">
				<a href='#'>
				<img src='/etc/images/noimage.jpg' alt="我的头像" width="83" border="0"></img>
				</a>
			</div>
			<!--其他链接-->
			<!--div class="quicklinkarea">
				<li>我的客户
				<li>我的任务
				<li>我的业绩
				<li>客户查询
				<li>快捷导航设置
			</div-->
	</div>
	<!-- ==============  中区域 ==============  -->
	<div class="centerarea">
            <div >
			    <span class="fl" style="font-size:16px; font-weight:bold;">欢迎登录，<s:property value="#session['sysUser'].userReal" /></span>
			    <span class="fr" style="color: #000">上次登录：<s:date name="#session['sysUser'].userLasttime" format="yyyy-MM-dd" /></span>
			</div>

            <div class="cl" style="height: 0px"></div>
            <div class="ui-cloud"></div>
	        <!-- 收件箱 -->
			<!--div id="centerblockTitle">
				<span class="fl"><a href="#">收件箱</a></span>
				<span class="fr"><a href="#">更多</a></span>
			</div>
			<div id="myMailArea">
			</div>
			<div class="centerblockBottom"></div>

            <div class="cl" style="height: 30px"></div-->

            <!-- 我的任务 -->
			<!--div id="centerblockTitle">
				<span class="fl"><a href="#">我的任务</a></span>
				<span class="fr"><a href="#">更多</a></span>
			</div>
			<div id="myWorkArea">
			</div>
			<div class="centerblockBottom"></div-->
	</div>


	<!-- ==============  右区域 ==============  -->
	<div class="rightarea">
	        <!--  日历 -->
			<!--div id="mydater"></div>
			<script>
			    WdatePicker({eCont:'mydater',onpicked:function(dp){alert('你选择的日期是:'+dp.cal.getDateStr())}})
			</script-->

            <div class="cl" style="height: 20px"></div>

			<!--  系统公告 -->
			<!--div class="rightblock">
				<div class="rightblockTitle"></div>
				<div id="rightblockInfo">
					<ul style="margin-left: 12px;border-bottom:#d4d4d4 1px solid;"><h5><a href="#">系统公告</a></h5></ul>
					<ul id="myNoticeArea"></ul>
				</div>
				<div class="rightblockBottom"></div>
			</div-->
		</div>
<!-- /end 总div -->
</div>
</body>
</html>
