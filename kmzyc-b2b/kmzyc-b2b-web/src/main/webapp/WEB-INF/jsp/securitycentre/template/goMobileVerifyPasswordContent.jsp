<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="l-right user-m">
	<div class="o-mt">
		<h2>手机验证</h2>
	</div>

	<!--进度条-身份验证-->
	<div class="procedure fn-t10 fn-b10">
		<ul>
			<li class="green">1、验证身份<b class="background-gl"></b></li>
			<li class="greyl"><s:if
					test="#request.verifyMobileInfo.modifyStatus">2、修改验证手机</s:if> <s:else>
						2、手机验证
						</s:else><b class="background-ll"></b></li>
			<li class="greyl">3、完成</li>
		</ul>
	</div>
	<!--进度条-身份验证end-->
	<!--文本框-->
	<div class="email-con">
		<form class="ch-fm"
			action="<%=basePath%>member/postMobileVerifyPassword.action" method="post"
			id="forms">
			<%--<center style="color: red;">	<s:if test="#session.b2b_login_remark=='02'">
				请输入帐号登录密码进行身份验证！
				</s:if>
				</center>--%>
			<ul>
				<li>
					<input type="password" name="password11111" style="display:none;"/>
					<div class="e-box">请输入登录密码：</div> 
					<input type="password"
					class="email-p" name="verifyMobileInfo.loginPassword">
					<div style="display: none;" class="text-sm">请输入正确的密码</div>
					<div id="oldPassworderrdiv" style="display: none;" class="red-text fn-red">您输入的密码不正确</div>
				    <div id="oldPassworderrdiv1" style="display: none;" class="red-text fn-red">请输入密码</div>
				</li>

				<input type="hidden" name="verifyMobileInfo.modifyStatus"
					value="<s:property value='#request.verifyMobileInfo.modifyStatus' />">

				<li>
					<div class="e-box">验证码：</div> <input type="text" class="email-yzm"
					id="verifyCodes" name="verifyMobileInfo.verifyCode">
					<div class="yzm-wb fn-blue">
						<span style="float: left;">
						<a href="javascript:void(0);" id="loadImgCode1">&nbsp;
							<img id="imageCode" width="80"height="30"/>&nbsp;
						</a>
						</span> <a href="javascript:void(0);" id="loadImgCode"> 看不清？换一张</a>
					</div> <br />
					<div style="display: none;" class="text-sm">请输入验证码</div>
					<div id="verifyCodeerrdiv" style="display: none;" class="red-text fn-red">您输入的验证码不正确</div>
					<div id="verifyCoderNull" style="display: none;" class="red-text fn-red">请输入验证码</div>
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