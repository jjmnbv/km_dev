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
<link href="http://jscss.kmb2b.com/res/template/wxCard/css/default.css"  rel="stylesheet">
<link href="http://jscss.kmb2b.com/res/template/wxCard/css/default.date.css" rel="stylesheet">
<script src="http://jscss.kmb2b.com/res/template/wxCard/css/respond.min.js"></script>
<link href="http://jscss.kmb2b.com/res/template/wxCard/css/default.css"  rel="stylesheet">
<link href="http://jscss.kmb2b.com/res/template/wxCard/css/default.date.css" rel="stylesheet">
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
<!-- isRepeatSubmit=<s:property value="isRepeatSubmit"/>  -->
<!-- 预约详情显示 begin -->
	<div class="gridContainer clearfix">
		<div id="main" class="fluid">
      <div class="toplogo">
        <div class="warn w-2">优惠使用</div>
        <div class="logo"><img src="http://jscss.kmb2b.com/res/template/wxCard/images/logo_bg2.png" /></div>
      </div>
      <div class="middle">
			  <s:if test="isRepeatSubmit==1">
					<div class="etext eorderly m-2">请勿重复提交,您的预约信息为</div>
				</s:if>
				<s:else>
					<div class="etext eorderly">您已预约成功，预约信息为</div>
				</s:else>
		    <div class="elist">
		      	<dl>
		        	<dt>姓名：</dt><dd><s:property value="reservation.username"/></dd>
		        	<dt>手机号：</dt><dd>
		        	<s:if test="reservation.telephone==null">
		        		客户未填手机号
		        	</s:if>
		        	<s:else>
		        		<s:property value="reservation.telephone"/>
		        	</s:else>
		        	</dd>
		        	<dt>领取门店：</dt><dd><s:property value="reservation.kmrsShopInfo.branchName"/></dd>
		        	<dt>门店地址：</dt><dd><s:property value="reservation.kmrsShopInfo.detailAddress"/></dd>
		        	<dt>门店电话：</dt><dd><s:property value="reservation.kmrsShopInfo.telephone"/></dd>
		        	<dt>预约日期：</dt><dd><s:date name="reservation.reservationTime" format="yyyy-MM-dd" /></dd>
		        </dl>
		    </div>
		    <a class="bin" href="javascript:void(0)" onclick="closeWin()">关闭</a>
		    <div class="mtext l-2">备注：康美药业本次收集您的个人信息，仅用于燕窝备货所需。康美将对已获取的信息部分，实行妥善保管，严格遵守相关法律法规，以保证您的合法权益不受侵害，在此感谢您对康美药业的支持与信任！</div>
		 </div>
		<!-- 核销表单 begin -->
    <div class="bottom">
	<h3 onclick="showCosumeCodeForm()"><a href="#bot" style="color:#333; text-decoration:none;">核销卡券</a></h3>
      <div id="consumeInfo"  style="display:none">
        <form id="consumeFrm" name="consumeFrm" action="/third/wxCardConsumeCode.action" method="post">
            <s:hidden name="encyptCode"/>
            <s:hidden name="cardId"/>
            <s:hidden name="signature"/>
            <s:hidden name="cardType"/>
            <div class="htext">店铺验证号</div>
            <div class="itext"><input type="text" id="verifyCode" name="shopVerifyCode" maxlength="10"/></div>
            <a href="javascript:void(0);" onclick="submitForm()" class="bin">立即使用</a>
            <div class="bwarn" id="bot">*请购买前使用</div>
          
        </form>
      </div>
    </div>
		<!-- 核销表单 end -->
		</div>
	</div>
	<script type="text/javascript">
	    //关闭当前页面
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
	    //控制表单显示
	    function showCosumeCodeForm(){
	      var style=document.getElementById("consumeInfo").style.display;
	      if(style=="none"){
	        document.getElementById("consumeInfo").style.display="inline";
	      }else{
	        document.getElementById("consumeInfo").style.display="none";
	      }	
	    }
    
    //提交核销表单
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
