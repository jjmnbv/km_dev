<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

    
<!--header 头部-->

<!--header 头部 end-->

<!--内容区域-->
<form action="goWapVerifyMobile.action" method="post" id="forms">
<input type="hidden" name="verifyMobileInfo.modifyStatus" value="<s:property value='#request.verifyMobileInfo.modifyStatus' />">
<div class="container">
    <ul class="steps">
        <li class="active">
            <span class="step"><i class="icon-uniE650"></i></span>
            <span class="title">身份验证</span>
        </li>
        <li>
            <span class="step"><i class="icon-uniE64F"></i></span>
            <span class="title">手机验证</span>
        </li>
        <li>
            <span class="step">OK</span>
            <span class="title">完成</span>
        </li>
    </ul>
    
    <div class="form-horizontal">
    <div class="help-block">
            <p  id="verifyCodeerrdiv" style="display: none;" class="text-danger">您输入的验证码不正确</p>
			<p  id="verifyCoderNull" style="display: none;" class="text-danger">请输入验证码</p>
            <p  id="oldPassworderrdiv" style="display: none;" class="text-danger">您输入的密码不正确</p>
			<p  id="oldPassworderrdiv1" style="display: none;" class="text-danger">请输入密码</p>
        </div>
        <div class="form-group">
            <input type="text" id="verifyCodes" name="verifyMobileInfo.verifyCode"  class="form-control input-lg" placeholder="请输入验证码">
            <span class="captcha-img">
               <a href="javascript:void(0);" id="loadImgCode1">
               <img id="imageCode" width="80"height="30">
               </a> 
            </span>
        </div>
        <div class="form-group">
           <input type="password" name="verifyMobileInfo.loginPassword" placeholder="请输入登录密码" class="form-control input-lg tooltips" data-original-title="" title="">
        </div>
        <div class="form-group">
         <a class="btn btn-success btn-block"  href="javascript:void(0);" id="submit">下一步</a>
          <!--   <a href="javascript:void(0)" class="btn btn-success btn-block">下一步</a> -->
        </div>
    </div>
    
</div>
</form>
<!--内容区域 end-->
