<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="l-right user-m">
	<div class="o-mt">
		<h2>手机验证</h2>
	</div>

	<!--进度条-身份验证-->
	<div class="procedure fn-t10 fn-b10">
			<ul style="position:relative">
				<li class="black">1、验证身份<b class="background-dg"></b></li>
				<li class="green"><s:if
						test="#request.verifyMobileInfo.modifyStatus">2、修改验证手机</s:if> <s:else>
						2、手机验证
						</s:else><b class="background-gl"></b></li>
				<li class="greyl">3、完成</li>
			</ul>
	</div>
	<!--进度条-身份验证end-->
	<!--文本框-->
	<div class="email-con">
		<form class="ch-fm" action="<%=basePath%>member/postVerifyMobile.action"
			method="post" id="forms">
			<input value='${verifyMobileInfo.loginId}'  name='loginId' style="visibility:hidden"/>
			<input value='${oldMobileNumber}' id='oldMobileNumber' style="visibility:hidden"/>
			<input value='${verifyMobileInfo.modifyStatus}' id='modifyStatus' style="visibility:hidden"/>
			<ul>    
			<li id="valid3" style="display: none; color: red;">
						该时间段内不支持发送，请于当时8点至23点内使用！</li>        
			<li id="valid2" style="display: none; color: red;">
						您当日累计获取验证码已达上限，请您次日再试！</li>
					<li id="valid0" style="display: none; color: red;">
						您获取验证码过于频繁，请稍后再试！</li>
				<s:if test="#request.verifyMobileInfo.modifyStatus">
					<li>
						<div class="e-box">旧手机号码：</div> <strong class="fn-green"><s:property
								value='#request.verifyMobileInfo.mobileNumber' /></strong>
					</li>
				</s:if>
				<li>
					<div class="e-box">我的手机号：</div> <input type="text" class="email-p" name="txtMobile" id="txtMobile">
				      	<input value='${verifyMobileInfo.loginId}'  name='loginId' style="visibility:hidden"/>
					<input value='' name='verifyMobileInfo.mobileNumber' id='mobileNumber' style="visibility:hidden"/>
					<div style="display: none;"  class="text-sm">请输入手机号</div>
					<div style="display: none;" id="wrongTel" class="red-text fn-red">请您输入正确的手机号码</div>
					<div style="display: none;" id="sameTel" class="red-text fn-red">该手机号码已使用</div>
				</li>

				<input type="hidden" name="verifyMobileInfo.modifyStatus" value="<s:property value='#request.verifyMobileInfo.modifyStatus' />">
				<li>
				    <p class="fn-gray" id="pRemainTag"></p>
					<div class="phone-btn">
						<div class="p-btn">
							<a href="javascript:void(0);" class="btn-b btn-b-disabled btn-b-sm" id="getMail"> 
							获取短信验证码
							</a>
						</div>
					</div>
					
				</li>
				<li>
					<div class="e-box">请填写短信验证码：</div><input type="text"
					class="phone-yzm" name="verifyMobileInfo.mobileVerifyCode">
					<div id="verifyMobileCodeerrdiv1" style="display: none;" class="red-text fn-red">请输入短信验证码</div>
					<div id="verifyMobileCodeerrdiv" style="display: none;"class="red-text fn-red">您输入的验证码不正确</div>
					<div id="verifyMobileCodeerrdiv2" style="display: none;"class="red-text fn-red">您输入的验证码已过期，请重新获取</div>
				</li>
				<li>
					<div class="e-box">验证码：</div> 
					<input  id="verifyCode" type="text" class="email-yzm" name="verifyMobileInfo.verifyCode">
					<div class="yzm-wb fn-blue">
						<span style="float: left;">
							<a href="javascript:void(0);" id ="loadImgCode1">&nbsp;<img id="imageCode" width="80"
								height="30"/>&nbsp;
							</a>
							</span>  
							<a href="javascript:void(0);"  id="loadImgCode">看不清？换一张</a>
					</div> <br />
					<div style="display: none;" class="text-sm">请输入验证码</div>
					<div id="verifyCodeerrdiv" style="display: none;"
						class="red-text fn-red">您输入的验证码不正确</div>
						<div id="verifyCoderNull" style="display: none;"
						class="red-text fn-red">请输入验证码</div>
				</li>

			</ul>
			<div class="button">
				<a class="btn-submit" href="javascript:void(0);"
					 id="submit"><span>提交</span></a>
			</div>
		</form>
	</div>
	<!--文本框-->
	<!--fn-right end-->
</div>
</div>