<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!--内容区域-->
  <form class="ch-fm" action="wapChangePasswordDone.action" id="changePass" method="post">
  <input type="hidden" name="safeLevel" id="safeLevel" value="">
<div class="container">
    <div class="form-body">
        <div class="help-block">
        <div  id="oldPassworderrdiv1" style="display: none;" class="text-danger">请输入正确的密码</div>
		<div id="oldPassworderrdiv" style="display: none;" class="text-danger">您输入的登录密码不正确</div>
        <div id="newPassworderrdiv" style="display: none;" class="text-danger">密码长度在8-20字符之间，不能用纯数字/英文字母！</div>
		<div id="newPassworderrdiv3" style="display: none;" class="text-danger">密码不能为空或全空格!</div>
		<div id="newPassworderrdiv1" style="display: none;" class="text-danger">登录密码不能和支付密码相同，请重新输入</div>
		<div id="newPassworderrdiv2" style="display: none;" class="text-danger">新登录密码不能和原登录密码相同，请重新输入</div>
        <div id="verifyNewPassworderrdiv" style="display: none;" class="text-danger">两次输入的密码不一致</div>
        <div id="verifyCodeerrdiv" style="display: none;" class="text-danger">您输入的验证码不正确</div>
		<div id="verifyCoderNull" style="display: none;" class="text-danger">请输入验证码</div>
           
        </div>
        <div class="form-group">
            <input type="password" name="changePasswordInfo.oldPassword" placeholder="旧密码" class="form-control input-lg tooltips" data-original-title="" title="">
        </div>
        <div class="form-group">
           <input type="password"  name="changePasswordInfo.newPassword" placeholder="新密码"  class="form-control input-lg tooltips" data-original-title="" title="">
        </div>
        <div class="form-group">
            <input type="password" name="changePasswordInfo.verifyNewPassword"  placeholder="确认密码" class="form-control input-lg tooltips" data-original-title="" title="">
        </div>
        <div class="form-group">
           <input type="text" placeholder="验证码" name="changePasswordInfo.verifyCode" class="form-control input-lg" placeholder="验证码">
            <span class="captcha-img">
                <a href="javascript:void(0);" onclick="loadImgCode()" id ="loadImgCode1">
	                                	<img id="imageCode" >
	             </a>
            </span>
        </div>
        <div class="form-group">
          <!--  <a href="javascript:void(0);"class="btn btn-success" id="submit">提  交</a> -->
            <a href="javascript:void(0)" class="btn btn-success btn-block" id="submit">提交</a>
        </div>
    </div>
</div>
</form>
<!--底部-->
   