<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!--[if lt IE 7]> <html class="ie6 oldie"> <![endif]-->
<!--[if IE 7]>    <html class="ie7 oldie"> <![endif]-->
<!--[if IE 8]>    <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!-->
 <html class="">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="http://jscss.kmb2b.com/res/template/wxCard/css/common.css" rel="stylesheet" type="text/css">
<link href="http://jscss.kmb2b.com/res/template/wxCard/css/boilerplate.css" rel="stylesheet" type="text/css">
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
<script src="http://jscss.kmb2b.com/res/template/wxCard/css/respond.min.js"></script>
<!--[if lt IE 9]>
    <script>document.createElement('section')</script>
    <style type="text/css">
        .holder {
            position: relative;
            z-index: 10000;
        }
        .datepicker {
            display: block;
        }
    </style>
<![endif]-->
</head>
<body>
<form id="consumeFrm" name="consumeFrm" action="/third/wxCardConsumeCode.action" method="post">
<s:hidden name="encyptCode"/>
<s:hidden name="cardId"/>
<s:hidden name="signature"/>
<s:hidden name="cardType"/>
<div class="gridContainer clearfix">
  <div id="main" class="fluid">
  	<div class="toplogo">
      <div class="warn">优惠使用</div>
    	<div class="logo"><img src="http://jscss.kmb2b.com/res/template/wxCard/images/logo_bg.png" /></div>
    </div>
    <div class="bottom">
    	<div class="htext">店铺验证号</div>
    	<div class="itext"><input type="text" id="verifyCode" name="shopVerifyCode" maxlength="10"/></div>
    	<a href="javascript:void(0);" onclick="submitForm()" class="bin">立即使用</a>
    	<div class="bwarn">*请购买前使用</div>
    </div>
  </div>
</div>
</form>
<p>&nbsp;</p>
<script language="javascript">
	function submitForm(){
		
		var verifyCode=document.getElementById("verifyCode").value;
		if(verifyCode==""){
			alert("请您输入店铺验证码!");
			return false;
		}	
		
		var regExp=new RegExp("^[0-9]*$"); 

		if(!regExp.test(verifyCode) || verifyCode.length!=6){
			alert("请您输入6位纯数字的店铺验证码,输入字符中间和两侧请勿输入空格!");
			return false;
		}
		
		document.forms['consumeFrm'].submit();
		return false;	
	}
</script>  
</body>
</html>
