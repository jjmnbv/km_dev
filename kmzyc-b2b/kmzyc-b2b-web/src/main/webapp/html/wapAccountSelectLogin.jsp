<%@ page language="java" import="java.util.*,com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
  Random rnum = new Random(new Date().getTime());
   Double ran=  rnum.nextDouble();
  String ranS=ran.toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

			<!-- 头部修改为引入文件的方式，并且通过KM.staticUrl+KM.jsBaseUrl 控制是在压缩版本还是未压缩版本-->
			<jsp:include page="/WEB-INF/jsp/common/loginTemplate.jsp">
		    	<jsp:param name="titlePrefix" value=""></jsp:param>
		    </jsp:include>
		    

<meta charset="utf-8">
<title>账号选择</title>
<meta name="apple-itunes-app" content="app-id=432274380">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit" />
<meta name="description" content="康美中药城"/>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<script>
   seajs.use([KM.staticUrl+KM.cssBaseUrl+'pages/wapAccountSelectLogin.css',KM.staticUrl+KM.jsBaseUrl+'view/wapAccountSelectLogin.js']);
</script>




<script type="text/javascript" src="http://pv.sohu.com/cityjson" charset="UTF-8"></script> 
 <script >
  window.onload=function(){
	  if(returnCitySN.cip){
		  document.getElementById("clientIp").value=returnCitySN.cip;
	  }else{
		  document.getElementById("clientIp").value="";
	  }
	  //20150310 maliqun add begin wap在微信内置浏览器中打开需要放开微信登录方式
	  var browserInfo = window.navigator.userAgent.toLowerCase();  
	 // console.log("浏览器信息="+browserInfo);
	  if(browserInfo.match(/MicroMessenger/i)=="micromessenger") {  
		  document.getElementById("wxLogin").style.display="block";
	  } else {  
		  document.getElementById("wxLogin").style.display="none";
	  } 	
	  //20150310 maliqun add end wap在微信内置浏览器中打开需要放开微信登录方式
  }
</script>


</head>

 
 <body>
   
   		<jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="账号选择"></jsp:param>
	    </jsp:include>
	    
	    <section class="w-bulletin">
	    	<img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>reswap/images/w-bulletin-img1.jpg">
	    	<img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>reswap/images/w-bulletin-img2.jpg" style="display:none;">
	    </section>
	    
	    <!--内容区域 update by hl ,wap 改版--> 
	    <div class="container">
	        <input type="hidden"  name="clientIp" id="clientIp"/>
		    <div class="wxsq-primary">
		        <a href="javascript:void(0);" id="accountRegist"><div class="kmtitle"><p class="kmtext2"> 我要注册康美中药城账号</p></div></a>
		        <div class="kmtitler"><i class="icon-uniE61F"></i></div>
		    </div>
		    <div class="wxsq-primary">
		        <a href="javascript:void(0);" id="accountLogin"><div class="kmtitle"><p class="kmtext2">我已经有了康美中药城账号</p></div></a>
		        <div class="kmtitler"><i class="icon-uniE61F"></i></div>
		    </div>
		    <div id="wxLogin" class="wxsq-primary" style="display: none">
		        <div class="weiico"><span class="icon-uniE655"></span></div>
		        <a href="javascript:void(0);" id="idForWxLogin"><div class="wxtitle"><h2 class="wxtext">微信账号购买</h2><p class="wxtext2">授权微信账号登录商城</p></div></a>
		        <div class="wxtitler"><i class="icon-uniE61F"></i></div>
		    </div>
		    <div class="wxsq-primary">
		        <div class="weiico"><span class="icon-uniE652"></span></div>
		        <a href="javascript:void(0);" id="qqBtnLogin"><div class="wxtitle"><h2 class="wxtext">QQ账号购买</h2><p class="wxtext2">授权QQ账号登录商城</p></div></a>
		        <div class="wxtitler"><i class="icon-uniE61F"></i></div>
		    </div>
		</div>
				    
	    <!--底部开始-->        
		<%@ include file="/html/common/wap/portal-foot.jsp" %>  
	    <!--底部 -->
	</body> 
	
	
	
	
</html>
