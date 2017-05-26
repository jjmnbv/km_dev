<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="l-right user-m">
<div class="o-mt">
<h2>修改登录密码</h2>
</div>
<div class="email-con fn-t10">
<form class="ch-fm" action="postChangePassword.action" id="changePass"
	method="post">
	<input type="hidden" 
		name="safeLevel" id="safeLevel" value="">
<ul>
	<li>
	<input type="password" name="password11111" style="display:none;"/>
	<div class="e-box">旧密码：</div>
	<input type="password" class="email-p" name="changePasswordInfo.oldPassword">
	<div  id="oldPassworderrdiv1" style="display: none;" class="red-text fn-red">请输入正确的密码</div>
	<div id="oldPassworderrdiv" style="display: none;" class="red-text fn-red">您输入的登录密码不正确</div>
	</li>
	<li>
	<div class="e-box">请输入新的登录密码：</div>
	<input type="password" class="email-p" name="changePasswordInfo.newPassword">
	<div style="display: none;" class="text-sm">密码长度在8-20字符之间，不能用纯数字/英文字母！</div>
	<div id="newPassworderrdiv" style="display: none;" class="red-text fn-red">密码长度在8-20字符之间，不能用纯数字/英文字母！</div>
	<div id="newPassworderrdiv3" style="display: none;" class="red-text fn-red">密码不能为空或全空格!</div>
	<div id="newPassworderrdiv1" style="display: none;" class="red-text fn-red">登录密码不能和支付密码相同，请重新输入</div>
	<div id="newPassworderrdiv2" style="display: none;" class="red-text fn-red">新登录密码不能和原登录密码相同，请重新输入</div>
	</li>
	<li>
	<div class="e-box">请确认新的登录密码：</div>
	<input type="password" class="email-p" name="changePasswordInfo.verifyNewPassword">
	<div style="display: none;" class="text-sm">请重新输入新的登录密码</div>
	<div id="verifyNewPassworderrdiv" style="display: none;" class="red-text fn-red">两次输入的密码不一致</div>
	</li>
	<li>
	<div class="e-box">验证码：</div>
	<input id="j_verifyCode" type="text" class="email-yzm" name="changePasswordInfo.verifyCode">
	<div class="yzm-wb fn-blue"><span style="float: left;">
		<a href="javascript:void(0);" onclick="loadImgCode()" id ="loadImgCode1">&nbsp;
		<img id="imageCode" width="80" height="30" />&nbsp; </a></span>
		<a href="javascript:void(0);" onclick="loadImgCode()" id="loadImgCode">看不清？换一张</a>
	</div>
	<br/>
	<div style="display: none;" class="text-sm">请输入验证码</div>
	<div id="verifyCodeerrdiv" style="display: none;" class="red-text fn-red">您输入的验证码不正确</div>
	<div id="verifyCoderNull" style="display: none;" class="red-text fn-red">请输入验证码</div>
	</li>
</ul>
<div class="button"><a href="javascript:void(0);"
	class="btn-submit" id="submit"><span>提交</span></a></div>
</form>
</div>
<div style="display: none;" class="safety-tips"><!--此功能暂时不启用-->
<h2>安全服务提示</h2>
<p>1.确认您登录的是康美网址 <%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>，注意防范进入钓鱼网站，不要轻信各种即时通讯工具发送的商品或支付链接，谨防网购诈骗。</p>
<p>2.建议您安装杀毒软件，并定期更新操作系统等软件补丁，确保账户及交易安全。</p>
</div>
</div>
</div>
