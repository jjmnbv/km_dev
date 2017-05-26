<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>康美药业</title>
<link href="http://jscss.kmb2b.com/res/template/wxCard/css/common.css" rel="stylesheet" type="text/css">
<link href="http://jscss.kmb2b.com/res/template/wxCard/css/boilerplate.css" rel="stylesheet" type="text/css">
<script src="http://jscss.kmb2b.com/res/template/wxCard/css/respond.min.js"></script>
<!-- 
To learn more about the conditional comments around the html tags at the top of the file:
paulirish.com/2008/conditional-stylesheets-vs-css-hacks-answer-neither/

Do the following if you're using your customized build of modernizr (http://www.modernizr.com/):
* insert the link to your js here
* remove the link below to the html5shiv
* add the "no-js" class to the html tags at the top
* you can also remove the link to respond.min.js if you included the MQ Polyfill in your modernizr build 
-->
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>
<body>
<div class="gridContainer clearfix">
  <div id="main" class="fluid">
    <div class="error t-2">
    	<div class="etext eorderly"><s:if test="isRepeatSubmit==1">请勿重复提交,</s:if>您的卡券已成功核销，<br/>非常感谢您的光临！<br/></div><div class="etext1">您还可以到<a href="http://www.kmb2b.com/"><span>康美中药城商城（www.kmb2b.com）</a>查看更多康美商品。</span></div>
    	<a href="javascript:void(0)"  class="bin" onclick="closeWin()">关闭</a>
    </div>
    <div class="error-bg"><img src="http://jscss.kmb2b.com/res/template/wxCard/images/error_bg.png" /></div>
  </div>
</div>
</body>
<script type="text/javascript">
	function closeWin(){		
		//获取当前浏览器的信息
    	var browserInfo = window.navigator.userAgent.toLowerCase();  
		console.log(browserInfo);
	    if(browserInfo.match(/MicroMessenger/i)=="micromessenger") {  
	    	  //调用微信的关闭当前页面
		      WeixinJSBridge.call('closeWindow');
	    } else {  
	    	  //pc端关闭当前窗口
		      window.opener 	= null; 
		      window.open('', '_self'); 
		      window.close();
	    } 
	}
</script>
</html>
