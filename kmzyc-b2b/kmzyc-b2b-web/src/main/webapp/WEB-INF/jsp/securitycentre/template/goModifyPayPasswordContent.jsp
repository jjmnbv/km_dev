<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="l-right user-m">
<div class="o-mt"><s:if
	test="#request.changePayPasswordInfo.modifyStatus">
	<h2>修改支付密码</h2>
</s:if> <s:else>
	<h2>启用支付密码</h2>
</s:else></div>

<!--进度条-身份验证-->
<div class="procedure fn-t10 fn-b10">
<div class="box02 fn-clear">
<ul>
	<li class="black">1、验证身份<b class="background-dg"></b></li>
	<li class="green"><s:if
		test="#request.changePayPasswordInfo.modifyStatus">2、修改支付密码</s:if> <s:else>
						2、启用支付密码
						</s:else><b class="background-gl"></b></li>
	<li class="greyl">3、完成</li>
</ul>
</div>
</div>
<!--进度条-身份验证end--> <!--文本框-->
<div class="email-con">
<form id="postVerifyEmail" class="ch-fm"
	action="<%=basePath%>member/postModifyPayPassword.action" method="post">
<input type="hidden" name="safeLevel" id="safeLevel" value="">
<input type="password" name="password11111" style="display:none;"/>
<input type="hidden" name="changePayPasswordInfo.modifyStatus" id="modifyStatus" value="${changePayPasswordInfo.modifyStatus}">
<ul>
	<li>
	<div class="e-box">设置支付密码：</div>
	<input type="password" class="email-p" name="changePayPasswordInfo.payPassword">
	<div class="fn-clear">
	   <span style="display: none;" class="ico-spoh progb-safe-l mar-t4"></span> 
	   <span style="display: none;" class="ico-spoh progb-safe-c mar-t4"></span> 
	   <span style="display: none;" class="ico-spoh progb-safe-r mar-t4"></span>
	</div>

	<div id="newPassworderrdiv" style="display: none;" class="red-text fn-red">密码长度在8-20字符之间，不能用纯数字/英文字母！</div>
	<div id="newPassworderrdiv3" style="display: none;" class="red-text fn-red">密码不能为空或全空格!</div>
	<div id="newPassworderrdiv1" style="display: none;" class="red-text fn-red">支付密码不能和登录密码相同，请重新输入</div>
	<div id="newPassworderrdiv2" style="display: none;" class="red-text fn-red">新密码不能和原密码相同，请重新输入</div>
	</li>
	<li>
	<div class="e-box">重新输入支付密码：</div>
	<div class="text-sm">
	   <input type="password" class="email-p" name="changePayPasswordInfo.verifyPayPassword">
	</div>
	<div style="display: none;" class="red-text fn-red">请输入正确的密码</div>
	<div id="verifyNewPassworderrdiv" style="display: none;" class="red-text fn-red">两次输入的密码不一致</div>
	</li>
	<li>
	<div class="e-box">验证码：</div>
	<input type="text" class="email-yzm" id="verifyCode" name="changePayPasswordInfo.verifyCode">
	<div class="yzm-wb fn-blue">
	  <span style="float: left;">
		<a href="javascript:void(0);" id="loadImgCode1">&nbsp;
		   <img id="imageCode" width="80" height="30" />&nbsp; 
		 </a>
	  </span>
	  <a href="javascript:void(0);" id="loadImgCode">看不清？换一张</a>
	</div>
	<br />
	<div id="verifyCodeNullDiv" style="display: none;"class="red-text fn-red">请输入验证码</div>
	<div id="verifyCodeerrdiv" style="display: none;"class="red-text fn-red">您输入的验证码不正确</div>
	</li>
</ul>
<div class="button"><a href="javascript:void(0);"
	class="btn-submit" id="submit"><span>提交</span></a></div>
</form>
</div>
<!--文本框--> <!--fn-right end--></div>
</div>