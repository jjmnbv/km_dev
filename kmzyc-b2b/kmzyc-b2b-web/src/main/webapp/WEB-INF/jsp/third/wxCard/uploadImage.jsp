<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
</head>
<body>
<form action="https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=${access_token}" id="frm" name="frm" method="post"  enctype="multipart/form-data">
	<input name="buffer" type="file"/>
	<input type="button" onclick="submitForm()" value="提交"/>
</form>
<p>浏览器信息如下:</p>
<p id="isWxBrowser"></p>
<!-- 测试正式环境的礼品券情况 -->
<p>测试礼品券预约</p>
<form action="http://www.kmb2b.com/third/toWxConsumeCode.action" id="testForm" name="testForm" method="post">
	<input name="encrypt_code" value="XXIzTtMqCxwOaawoE91+VJdsFmv7b8g0VZIZkqf4GWA60Fzpc8ksZ/5ZZ0DVkXdE"/>
			<input  name="card_id" value="pFXg-txAmAqBPOa3a4cZMhd2DyiETest3"/>
			<input  name="signature" value="signature"/>
			<input name="cardType" value="gift"/>
			<input type="button" onclick="goToSubmit()" value="测试"/>
</form>
<p>测试现金券核销</p>
<form action="http://www.kmb2b.com/third/toWxConsumeCode.action" id="testForm2" name="testForm2" method="post">
	<input name="encrypt_code" value="XXIzTtMqCxwOaawoE91+VCqwsvOwfz6uoFNaYU1YdvmbBntlXUAQeGTM1MLdB28U"/>
			<input  name="card_id" value="pFXg-t-cfhlMHKsWY0WmRj_RzMXM"/>
			<input  name="signature" value="signature"/>
			<input name="cardType" value="cash"/>
			<input type="button" onclick="goToSubmit2()" value="测试现金券核销"/>
</form>

</body>
<script type="text/javascript">
	function submitForm(){
		document.forms['frm'].submit();
	}
	
	function goToSubmit(){
		alert("可以提交");
		document.forms['testForm'].submit();
	}
	
	function goToSubmit2(){
		alert("可以提交");
		document.forms['testForm2'].submit();
	}
	
	
	window.onload=isWxBrowser;
	
	//判断是否是微信浏览器
	function isWxBrowser(){
		 var browserInfo = window.navigator.userAgent.toLowerCase();  
		 console.log(browserInfo);
		 if(browserInfo.match(/MicroMessenger/i)=="micromessenger") {  
		      document.getElementById("isWxBrowser").innerHTML="是微信内置浏览器,详情如下:"+browserInfo;
		  } else {  
		      document.getElementById("isWxBrowser").innerHTML="非微信内置浏览器,详情如下:"+browserInfo;
		  } 		    
		    //alert("当前请求的路径=="+window.location.href);
	}
	
	
	//立即购买
	 $('#promptlyBuy').on('click', function () {
		 var quantity = document.getElementById("quantity").value;
		 var targetUrl=portalPath + "/wap/wapSettlement.action?productSkuID="+productSkuId+"&productVary="+quantity;
		 
		 //获得浏览器内置信息
		 var browserInfo = window.navigator.userAgent.toLowerCase();  
		 console.log("浏览器信息="+browserInfo);
		 var isWxBrowser=false;  //标识是否是微信浏览器
		 
		 if(browserInfo.match(/MicroMessenger/i)=="micromessenger") {  
		      console.log("是微信内置浏览器,详情如下:"+browserInfo);
		      isWxBrowser=true;
		 }else{
			 window.location.href=targetUrl;  
		 }
		 
		 //如果是微信内置浏览器
		 if(isWxBrowser){
		    //判断用户是否已经登录
	        $.getJSON(portalPath + "/loginInfoAction.action?callback=?", function (json) {
	            var data = eval(json);
	            //如果用户未登录,则默认其去微信登录
	            if (data.returnObject.mark != 1) {
	            	 //对传入的url进行编码
	       		 	targetUrl=encodeURIComponent(targetUrl);
	            	window.location.href=portalPath +"/third/wxIndexForWap.action?comeFrom=authorizeForWap&returnUrl="+targetUrl;
	            } else {
	             	window.location.href=targetUrl;  
	            }
	        });
		 }
	 });
	
	
</script>
</html>
