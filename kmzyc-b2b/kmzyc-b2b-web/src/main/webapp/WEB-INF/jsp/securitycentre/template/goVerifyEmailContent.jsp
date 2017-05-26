<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="l-right user-m">
<div class="o-mt">
<h2>邮箱验证</h2>
</div>

<!--进度条-身份验证-->
<div class="procedure fn-t10 fn-b10">
<ul>
	<li class="black">1、验证身份<b class="background-dg"></b></li>
	<li class="green"><s:if
		test="#request.verifEmailInfo.modifyStatus">2、修改邮箱</s:if> <s:else>
						2、验证邮箱
						</s:else><b class="background-gl"></b></li>
	<li class="greyl">3、完成</li>
</ul>
</div>
<!--进度条-身份验证end--> <!--文本框-->
<div class="email-con">
<form id="postVerifyEmail" class="ch-fm"
	action="postVerifyEmail.action" method="post">
<ul>


	<s:if test="#request.verifEmailInfo.modifyStatus">
		<li>
		<input type="hidden"  value="${verifEmailInfo.loginId}" name="loginId">
		<div class="e-box">已用邮箱：</div>
		<strong class="fn-green" id="emailas"><s:property
			value='#request.verifEmailInfo.emailAddress' /></strong> <input type="hidden"
			value="updateemail" name="uptype" id="updateEmail">
			
			</li>
	</s:if>
	<li>
	<div class="e-box">请输入邮箱：</div>
	<s:if test="#request.verifEmailInfo.modifyStatus">
		<input type="text" name="verifEmailInfo.emailAddress" class="email-p"
			id="emailAddress" value="">
			<input type="hidden"  value="${verifEmailInfo.loginId}" name="loginId">
	</s:if> <s:else>
		<input type="text" name="verifEmailInfo.emailAddress" class="email-p"
			id="emailAddress" value="${verifEmailInfo.emailAddress}">
			<input type="hidden"  value="${verifEmailInfo.loginId}" name="loginId">
	</s:else>
	<div id="emailerrdiv" style="display: none;" class="red-text fn-red">您输入的邮箱格式不正确</div>
	<div id="emailerrdiv1" style="display: none;" class="red-text fn-red">您输入的邮箱已被占用</div>
	<div id="emailNotdiff" style="display: none;" class="red-text fn-red">您输入的邮箱和已用邮箱不能相同</div>
	<div id="emailerrdiv2" style="display: none;" class="red-text fn-red">请输入邮箱</div>
	<div id="emailerrdiv3" style="display: none;" class="red-text fn-red">您的邮箱已被验证</div>
	</li>
	<li>
	<div class="e-box">验证码：</div>
	<input type="text" class="email-yzm" id="verifyCode"
		name="verifEmailInfo.verifyCode">
	<div class="yzm-wb fn-blue"><span style="float: left;"><a
		href="javascript:void(0);" class="j_btnSubmitCode">&nbsp;<img
		id="imageCode" width="80" height="30" />&nbsp; </a></span><a
		href="javascript:void(0);" class="j_btnSubmitCode">看不清？换一张</a></div>
	<div id="verifyCodeerrdiv" style="display: none;"
		class="red-text fn-red">您输入的验证码不正确</div>
	<div id="verifyCodeerrdiv1" style="display: none;"
		class="red-text fn-red">请输入验证码</div>
	</li>
	<input type="hidden" name="verifEmailInfo.modifyStatus"
		value="<s:property
							value='#request.verifEmailInfo.modifyStatus' />">
</ul>
<div class="button"><a href="javascript:void(0);"
	class="btn-submit j_btnSubmit"><span>提交</span></a></div>
</form>
</div>
<!--文本框--> <!--fn-right end--></div>
</div>