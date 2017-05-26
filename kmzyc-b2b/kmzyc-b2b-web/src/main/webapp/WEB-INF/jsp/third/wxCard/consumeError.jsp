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
<script src="http://jscss.kmb2b.com/res/template/wxCard/css/respond.min.js"></script>
</head>
<body>
<!-- <p>错误码:errCode=<s:property value="errorCode"/></p>  --> 
<!--<p>错误详情:errMsg=<s:property value="errorMsg"/></p>  --> 
<form action="/third/toWxConsumeCode.action" id="frm" name="frm" method="post">
	<input type="hidden" name="encrypt_code" value="<s:property value="encyptCode"/>"/>
	<input type="hidden" name="card_id" value="<s:property value="cardId"/>"/>
	<input type="hidden" name="signature" value="<s:property value="signature"/>"/>
	<input type="hidden" name="cardType" value="<s:property value="cardType"/>"/>
</form>
<div class="gridContainer clearfix">
  <div id="main" class="fluid">
    <div class="error">
    	<div class="etext">卡券核销失败！<br/>
    	<s:if test="errorCode==-2">
    		您的店铺验证码输入不正确,请返回重试!
    	</s:if>
    	<s:elseif test="errorCode==-1">
    		系统出现异常,请返回再试!
    	</s:elseif>
    	<s:elseif test="errorCode==-3">
    		该请求不是来自微信!
    	</s:elseif>
    	<s:elseif test="errorCode.equals('40075')">
    		微信解码接口返回非法的加密code! 
    	</s:elseif>
    	<s:elseif test="errorCode.equals('40056')">
    		该code码尚未归属领取人!<s:property value="errorMsg"/>
    	</s:elseif>
    	<s:elseif test="errorCode.equals('timeError')">
    		请检查您的卡券是否在生效期内或者已经过期!
    	</s:elseif>
    	<s:elseif test="errorCode.equals('alreadyConsume')">
    		该卡券已被核销!
    	</s:elseif>
    	<s:else>
    		调用微信接口成功,但是微信返回错误码: errorCode=<s:property value="errorCode"/>&nbsp;&nbsp;errorMsg=<s:property value="errorMsg"/>
    		<!--  请确认您的卡券状态和店铺验证码是否正确输入。 -->
    	</s:else>   	
	</div>
    	<a href="javascript:void(0);" onclick="back()"  class="bin">返回</a>
    </div>
    <div class="error-bg"><img src="http://jscss.kmb2b.com/res/template/wxCard/images/error_bg.png" /></div>
  </div>
</div>
<script language="javascript">
	function back(){
		document.forms['frm'].submit();
	}
</script>
</body>
</html>
