<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta charset="utf-8" />
		<jsp:include page="/WEB-INF/jsp/common/loginTemplate.jsp">
			<jsp:param name="titlePrefix" value=""></jsp:param>
		</jsp:include>
		<title>登录</title>
		<meta name="apple-itunes-app" content="app-id=432274380" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="renderer" content="webkit" />
		<meta name="description" content="康美中药城"/>
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
		<meta content="yes" name="apple-mobile-web-app-capable" />
		<meta content="black" name="apple-mobile-web-app-status-bar-style" />
		<meta content="telephone=no" name="format-detection" />
		<script>
			try{
	        	if('1'==<%=ConfigurationUtil.getString("enable_ssl")%>&&'https:' != document.location.protocol){
		        	var idx=0;
		        	var returnUrl='';
		        	var url=window.location.href;
		        	if((idx=url.indexOf('returnUrl'))>0 || (idx=url.indexOf('ReturnUrl'))>0){
		        		returnUrl=url.replace('returnUrl','ReturnUrl').substring(idx,url.length);
			        }
					window.location.href='<%=ConfigurationUtil.getString("sslLoginPath")%>/html/wapLogin.jsp?'+returnUrl;
				}
		   	}catch(e){}
		   	seajs.use([KM.staticUrl+KM.cssBaseUrl+'pages/register.css',KM.staticUrl+KM.jsBaseUrl+'view/wapLogin.js']);
		</script>
	</head>
	<body>
   		<jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="登录"></jsp:param>
	    </jsp:include>
	    <input type="hidden" id="enable_ssl" value="<%=ConfigurationUtil.getString("enable_ssl")%>"/>
		<div class="container">
		<div class="form-horizontal">
			<div class="help-block"><p class="text-danger" id="errorMessage" style="display: none;"></p></div>
			<div class="form-group">
				<input type="text" class="form-control input-lg" placeholder="手机号" name="txtloginAccount" id="txtloginAccount" />
			</div>
			<div class="form-group">
				<input type="password" name="loginPassword" id="loginPassword" class="form-control input-lg" placeholder="请输入密码" />
			</div>
			<div class="form-group input-group" style="display: none;" id="loginVCodeBox">
				<input type="text" class="form-control input-lg" name="loginVCode" id="loginVCode" value="" placeholder="验证码" />
				<span class="captcha-img">
					<a href="javascript:void(0);" class="inputimg codeImg"><img id="imageCode" class="codeImg" width="80" height="30" src="" /></a>
				</span>
			</div>
			<div class="form-group">
				<div style="float: right;">
					<a href="javascript:void(0);" id="link_forget_pass">忘记密码</a>
				</div>
			</div>
			<div class="form-group">
				<a href="javascript:void(0)" class="btn btn-success btn-block" id="btnSubLogin" >登录</a>
			</div>
		</div>
		</div>
	 	<!--底部开始--> 
		<%@ include file="/html/common/wap/portal-foot.jsp" %> 
		<!--底部 -->
	</body> 
</html>
