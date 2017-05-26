<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!--内容区域-->
<div class="container">
    <ul class="steps">
        <li class="active">
            <span class="step"><i class="icon-uniE650"></i></span>
            <span class="title">身份验证</span>
        </li>
        <li>
            <span class="step"><i class="icon-uniE64F"></i></span>
            <span class="title">设置支付密码</span>
        </li>
        <li>
            <span class="step">OK</span>
            <span class="title">完成</span>
        </li>
    </ul>
    <div class="form-horizontal">
      <form id="postVerifyEmail" action="postWapPayVerifyPassword.action" method="post">
      <s:if test="mobileNumber==null">
                                        <center>为了您的帐户安全，请去验证手机！<a href="/member/goWapMobileVerifyPassword.action" style="color:red;">立即前往>></a></center>
                    </s:if>
                    <s:else>
                     <input name="mobileNumber" id="mobileNumber" type="hidden" value="${mobileNumber}" ></input>
					<input name="modifyStatus" id="modifyStatus" type="hidden" value="${changePayPasswordInfo.modifyStatus}" ></input>
        <div class="form-group">
             手机号码:${verifyMobileInfo.mobileNumber}
        </div>
        <div class="form-group">
            <input type="text" id="verifyCode" name="changePayPasswordInfo.verifyCode"  class="form-control input-lg" placeholder="请输入图形验证码">
            <span class="captcha-img">
              <a href="javascript:void(0);" id="loadImgCode1" >
                                	<img id="imageCode" width="80" height="30"> 
                                  </a>
            </span>
        </div>
           <div id="verifyCodeerrdiv" style="display: none;" class="text-danger">您输入的验证码不正确</div>
		   <div id="verifyCoderNull" style="display: none;" class="text-danger">请输入验证码</div>
        <div class="form-group">
           <div class="input-group">
              <input type="text"  name="changePayPasswordInfo.mobileVerifyCode"  aria-describedby="请输入短信验证码" placeholder="请输入短信验证码" class="form-control input-lg">
              <span class="input-group-btn" id="basic-addon2"><a  href="javascript:void(0);" id="getMail" class="btn btn-success">短信验证</a></span>
           </div>
        </div>
        <div style="display:none;" id="valid3" class="text-danger">该时间段内不支持发送，请于当时8点至23点内使用！</div>
        <div style="display:none;" id="valid2" class="text-danger">您当日累计获取验证码已达上限，请您次日再试！</div>
		<div style="display:none;" id="valid0" class="text-danger">您获取验证码过于频繁，请稍后再试！</div>
        <div id="verifyMobileCodeerrdiv1" style="display: none;" class="text-danger">请输入短信验证码</div>
		<div id="verifyMobileCodeerrdiv" style="display: none;" class="text-danger">您输入的短信验证码不正确</div>
		<div id="verifyMobileCodeerrdiv2" style="display: none;"class="text-danger">您的输入的短信验证码已超时，请重新获取</div>
        <div class="form-group">
           <a href="javascript:void(0);"  class="btn btn-success btn-block"id="submit">下一步</a>
        </div>
        </s:else>
        </form>
    </div>
</div>
<!--内容区域 end-->
