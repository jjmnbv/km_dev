<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="l-right user-m">
<div class="o-mt">
<h2>邮箱验证</h2>
</div>

<!--进度条-身份验证-->
<div class="procedure fn-t10 fn-b10">
<ul>
	<li class="green">1、验证身份<b class="background-gl"></b></li>
	<li class="greyl"><s:if
		test="#request.verifEmailInfo.modifyStatus">2、修改邮箱</s:if> <s:else>
						2、验证邮箱
						</s:else><b class="background-ll"></b></li>
	<li class="greyl">3、完成</li>
</ul>
</div>
<!--进度条-身份验证end--> <!--文本框-->
<div class="email-con">
<form class="ch-fm" action="/member/postEmailVerifyPassword.action"
	method="post" id="froms">
<ul>
	<li>
	<div class="e-box">请输入登录密码：</div>
	<input type="password" id="loginPassword" class="email-p"
		name="verifEmailInfo.loginPassword">
	<div id="oldPassworderrdiv" style="display: none;"
		class="red-text fn-red">您输入的登录密码不正确</div>
	<div id="oldPassworderrdiv1" style="display: none;"
		class="red-text fn-red">请输入登录密码</div>
	</li>

	<input type="hidden" name="verifEmailInfo.modifyStatus"
		value="<s:property
							value='#request.verifEmailInfo.modifyStatus' />">

	<li>
	<div class="e-box">验证码：</div>

	<input type="text" class="email-yzm" id="verifyCode"
		name="verifEmailInfo.verifyCode">
	<div class="yzm-wb fn-blue"><span style="float: left;"><a
		href="javascript:void(0);" class="j_btnSubmitImgCode">&nbsp;<img
		id="imageCode" width="80" height="30" />&nbsp; </a></span><a
		href="javascript:void(0);" class="j_btnSubmitImgCode">看不清？换一张</a></div>
	<div id="verifyCodeerrdiv" style="display: none;"
		class="red-text fn-red">您输入的验证码不正确</div>
	<div id="verifyCodeerrdiv1" style="display: none;"
		class="red-text fn-red">请输入验证码</div>
	</li>


</ul>
<div class="button"><a href="javascript:void(0);"
	class="btn-submit j_btnSubmit"><span>提交</span></a></div>
</form>
</div>
<!--文本框--> <!--fn-right end--></div>
</div>