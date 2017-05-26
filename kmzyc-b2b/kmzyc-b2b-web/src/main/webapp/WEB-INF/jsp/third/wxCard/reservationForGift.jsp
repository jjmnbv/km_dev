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
<script src="http://jscss.kmb2b.com/res/template/wxCard/css/jquery.1.7.0.js"></script>
<script src="http://jscss.kmb2b.com/res/template/wxCard/css/picker.js"></script>
<script src="http://jscss.kmb2b.com/res/template/wxCard/css/picker.date.js"></script>
<script src="http://jscss.kmb2b.com/res/template/wxCard/css/legacy.js"></script>
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
<!-- 提交预约信息表单 begin -->
<form action="/third/saveReservation.action" id="reservationFrm" name="reservationFrm" method="post">
	<s:hidden name="encyptCode"/>
	<s:hidden name="cardId"/>
	<s:hidden name="signature"/>
	<s:hidden name="cardType"/>
<div class="gridContainer clearfix">
  <div id="main" class="fluid">
  	<div class="toplogo">
      <div class="warn w-2">优惠使用</div>
    	<div class="logo"><img src="http://jscss.kmb2b.com/res/template/wxCard/images/logo_bg2.png" /></div>
    </div>
    <div class="middle">
    	<h3>预约领取</h3>
    	<div class="mtext"><span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span><input type="text" id="username" name="reservation.username"  maxlength="12"/></div>
    	<div class="mtext"><span>手&nbsp;&nbsp;机&nbsp;&nbsp;号：</span><input type="text" id="telephone" name="reservation.telephone" maxlength="16"/></div>
    	<div class="mtext m-2">（可选填）</div>
    	<div class="mtext"><span>领取门店：</span>
      	<select name="reservation.shopId" class="sele s-2" id="shopId">
			<option value="-1" selected="selected">--请选择--</option>
		    <s:iterator value="#request.kmrsShopList" var="shopInfo">
				<option value="<s:property value="shopId"/>" selected="selected"><s:property value="branchProvince"/>市<s:property value="branchName"/></option>
			</s:iterator> 
		</select>
		</div>
    	<div class="mtext"><span>领取日期：</span>
      	<section class="section">
          <form style="margin:0; padding:0; font-size:0.4em;">
          	<input id="input_01" class="datepicker"  name="reservation.reservationTime" type="text" />
              <fieldset>
                  <h2 style="margin:0; padding:0;"><label for="input_01"></label></h2>             
              </fieldset>
          </form>
          <div id="container"></div>
      </section>
      <script type="text/javascript">
  
          var $input = $( '.datepicker' ).pickadate({
              formatSubmit: 'yyyy/mm/dd',
							 format: 'yyyy-mmmm-d',
							//  monthsFull: [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月' ],
              min: [2015, 2, 2],
              max: [2015, 2, 12],
              container: '#container'
          })
      </script>
      </div>
    	<a href="javascript:void(0)" class="bin" onclick="submitReservationFrm()">立即预约</a>
    	<div class="mtext l-2">备注：康美药业本次收集您的个人信息，仅用于燕窝备货所需。康美将对已获取的信息部分，实行妥善保管，严格遵守相关法律法规，以保证您的合法权益不受侵害，在此感谢您对康美药业的支持与信任！</div>
    </div>
    </form>
	<!-- 提交预约信息表单 end -->

	<!-- 核销表单 begin -->
				
	<div class="bottom">
		<h3 onclick="showCosumeCodeForm()"><a href="#bot" style="color:#333; text-decoration:none;">核销卡券</a></h3>
      	<div id="consumeInfo" style="display:none">
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
	</div>
<!-- 核销表单 end -->	
<script language="javascript">
	//提交预约列表
	function submitReservationFrm(){
		var username=document.getElementById("username").value;
		var telephone=document.getElementById("telephone").value;
		var shopId=document.getElementById("shopId").value;
		var time=document.getElementById("input_01").value;
		
		if(username==""){
			alert("请您输入姓名!");
			return false;
		}
		
	//	if(telephone==""){
	//		alert("请您输入您的手机号!");
	//		return false;
	//	}	
		
		if(shopId=="-1"){
			alert("请您选择预约领取的门店!");
			return false;
		}	
		
		if(time==""){
			alert("请选择您需要预约的时间!");
			return false;
		}	
		
		var regExp=new RegExp("^[0-9]*$"); 

		if(telephone!=""){
			if(!regExp.test(telephone) || telephone.length!=11){
				alert("请您输入11位纯数字的手机号码!");
				return false;
			}
		}
		document.forms['reservationFrm'].submit();
		return;
	}
		
	//控制表单显示
	function showCosumeCodeForm(){
		var style=document.getElementById("consumeInfo").style.display;
		if(style=="none"){
			//document.getElementById("showButton").value="收起核销信息";
			document.getElementById("consumeInfo").style.display="inline";
		}else{
		//	document.getElementById("showButton").value="打开核销信息";
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
