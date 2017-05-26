<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!--内容区域-->
<div class="container">
    <ul class="steps">
        <li>
            <span class="step"><i class="icon-uniE650"></i></span>
            <span class="title">身份验证</span>
        </li>
        <li class="active">
            <span class="step"><i class="icon-uniE64F"></i></span>
            <span class="title">手机验证</span>
        </li>
        <li>
            <span class="step">OK</span>
            <span class="title">完成</span>
        </li>
    </ul>
    <div class="container">
    <form id="forms" action="postWapVerifyMobile.action" method="post">
   					<input value='${verifyMobileInfo.loginId}'  name='loginId' style="display:none;"/>
					<input value='${oldMobileNumber}' id='oldMobileNumber' style="display:none;"/>
					<input value='${verifyMobileInfo.modifyStatus}' id='modifyStatus' style="display:none;"/>
                        <div class="help-block text-error" style="display: none" id="pRemainTag" >短信已经发送到您的手机，如在120秒之内未收到短信校验码，请重新获取</div>
                        <s:if test="#request.verifyMobileInfo.modifyStatus">
							<div class="form-group"><label class="text-success">旧手机号码：
							<s:property value='#request.verifyMobileInfo.mobileNumber' /></label></div>
						</s:if>
        <div class="form-group">
           <div class="input-group">
             <input type="text" name="txtMobile" id="txtMobile" aria-describedby="请输入手机号码" placeholder="请输入手机号码" class="form-control input-lg">
     		 <input value='${verifyMobileInfo.loginId}'  name='loginId'  type="hidden"/>
             <input value='' name='verifyMobileInfo.mobileNumber' id='mobileNumber'  type="hidden"/>
            
			  <input type="hidden" name="verifyMobileInfo.modifyStatus" value="<s:property value='#request.verifyMobileInfo.modifyStatus' />">
            <span class="input-group-btn" id="basic-addon2"><a  href="javascript:void(0);" id="getMail" class="btn btn-success">短信验证</a></span>
           </div>
        </div>
         <div style="display: none;" id="wrongTel" class="text-danger">请您输入正确的手机号码</div>
			 <div style="display: none;" id="sameTel" class="text-danger">该手机号码已使用</div>
        <div class="form-group">
          	<input type="text" id="verifyCode" name="verifyMobileInfo.mobileVerifyCode" title="" data-original-title="" class="form-control input-lg tooltips" placeholder="请输入短信验证码">
        </div>
        
        <div style="display: none;" id="valid3" class="text-danger">该时间段内不支持发送，请于当时8点至23点内使用！</div>
        <div style="display: none;" id="valid2" class="text-danger">您当日累计获取验证码已达上限，请您次日再试！</div>
        <div style="display: none;" id="valid0" class="text-danger">您获取验证码过于频繁，请稍后再试！</div>
        <div id="verifyMobileCodeerrdiv1" style="display: none;" class="text-danger">请输入短信验证码</div>
		<div id="verifyMobileCodeerrdiv" style="display: none;"class="text-danger">您输入的验证码不正确</div>
		<div id="verifyMobileCodeerrdiv2" style="display: none;"class="text-danger">您输入的验证码已过期，请重新获取</div>
        <div class="form-group input-group">
        <input type="text" id="verifyCode" name="verifyMobileInfo.verifyCode" placeholder="验证码" class="form-control input-lg">
            <span class="captcha-img">
              <a href="javascript:void(0);" id ="loadImgCode1"><img id="imageCode"></a>
            </span>
        </div>
        <div class="form-group">
          <a href="javascript:void(0);" class="btn btn-success btn-block" id="submit">提交</a>
        </div>
    </form>
</div>
</div>
<!--内容区域 end-->
