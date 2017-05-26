<%@ page language="java" import="java.util.*,com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
					<!-- 头部修改为引入文件的方式，并且通过KM.staticUrl+KM.jsBaseUrl 控制是在压缩版本还是未压缩版本-->
			<jsp:include page="/WEB-INF/jsp/common/loginTemplate.jsp">
		    	<jsp:param name="titlePrefix" value=""></jsp:param>
		    </jsp:include>
		<title>注册 - 康美中药城,中国首个线上药材市场,康美药业股份有限公司旗下</title>
        <meta name="apple-itunes-app" content="app-id=432274380" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="renderer" content="webkit" />
		<meta name="description" content="康美中药城"/>
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
		<meta content="yes" name="apple-mobile-web-app-capable" />
		<meta content="black" name="apple-mobile-web-app-status-bar-style" />
		<meta content="telephone=no" name="format-detection" />
     	<script>
     		seajs.use([KM.staticUrl+KM.cssBaseUrl+'pages/register.css',KM.staticUrl+KM.jsBaseUrl+'view/registration.js']);
        </script>
    </head>         
	<body class="l-w1000">
	    <!--头部-->
		<jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="注册"></jsp:param>
	    </jsp:include>
    <div class="container">
    	<form class="form-horizontal">
	        <div class="help-block">
	             <p class="text-danger"  id="errorMessage"  style="display: none;" >请输入正确密码！</p>
	        </div>
	        <div class="form-group">
	              <input type="text" class="form-control input-lg"   name="mobile"  id="mobileTel" placeholder="请输入手机号码" aria-describedby="请输入手机号码" />      
	        </div>
       
	        <div class="form-group">
	            <input type="password" class="form-control input-lg" placeholder="请输入密码" name="user.password" id="password"  />
	        </div>
	        <div class="form-group">
	            <input type="password" class="form-control input-lg" placeholder="请确认密码"  name="confirmpassword" id="confirmpassword" />
	        </div>
	        

        
         
         <div class="form-group input-group">
            <input type="text" class="form-control input-lg" placeholder="验证码"  value=""  name="txtveCode"  id="txtveCode"   />
            <span class="captcha-img">
                <a href="javascript:void(0);" id="changeImgLink1" class="inputimg">
		    	 <img id="imageCode"  width="80"	height="30"  src="/codeImage.action?imgKey=regImg"/></a>
            </span>
        </div>
        
        <div class="form-group">
		         <div class="input-group">
		            <input type="text" class="form-control input-lg" placeholder="请输入收到的验证码" name="smsCode"  id="smsCode" />
		            <span id="basic-addon2" class="input-group-btn   bg-c-gray">
		            <a id="getMail" class="btn btn-success" >获取验证码</a></span>
		         </div>
	        </div>
        
        
        <div class="form-group">
            <div class="checkbox">
                <label> 
                   <input type="checkbox" name="CheckboxGroup" id="CheckboxGroup" checked="checked" value="checkbox"/>我已阅读并接受<a href="/helps/wapRegistProtocol.shtml"  target="_blank">《用户协议》</a> 
                </label>
            </div>
        </div>
        <div class="form-group">
            <a href="javascript:void(0);"  id="regesitBtn" class="btn btn-success btn-block">注册</a>
        </div>
        	 
        
        
        <div class="form-group form-group2">
            我已注册现在就去<a href="wapLogin.jsp"> 登录</a>
        </div>
 
    </form>
    
           
       <form id="mobileForm" action="wapRegSuccess.action"  method="post">
	        	<input name="mobile" type="hidden"  id="mobile"></input>
	        	<input name="info" type="hidden"  id="info"></input>
	        </form>
</div>
	    
	    
	    
	    
	    <!--底部开始-->        
		<%@ include file="/html/common/wap/portal-foot.jsp" %>  
	    <!--底部 -->
	</body>
</html>
