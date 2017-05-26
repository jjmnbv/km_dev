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
	    <s:if test="#request.changePayPasswordInfo.modifyStatus">
			<h2>修改支付密码</h2>
		</s:if>
		<s:else>
		<h2>启用支付密码</h2>
		</s:else>
		
	</div>

	<!--进度条-身份验证-->
	<div class="procedure fn-t10 fn-b10">
		<div class="box01 fn-clear">
			<ul>
				<li class="green">1、验证身份<b class="background-gl"></b></li>
				<li class="greyl"><s:if
						test="#request.changePayPasswordInfo.modifyStatus">2、修改支付密码</s:if>
					<s:else>
						2、启用支付密码
						</s:else><b class="background-ll"></b></li>
				<li class="greyl">3、完成</li>
			</ul>
		</div>
	</div>
	
	<input name="mobileNumber" id="mobileNumber" type="hidden" value="${mobileNumber}" />
	<input name="modifyStatus" id="modifyStatus" type="hidden" value="${changePayPasswordInfo.modifyStatus}" />
	<!--进度条-身份验证end-->
	<!--文本框-->
	<div class="email-con">
		<form id="postVerifyEmail" class="ch-fm"
			action="<%=basePath%>member/postPayVerifyPassword.action" method="post" >
			 <s:if test="mobileNumber==null">
             <center>为了您的帐户安全，请去验证手机！<a href="/member/goMobileVerifyPassword.action?verifyMobileInfo.modifyStatus=false" style="color:red;">立即前往>></a></center>
             </s:if>
             <s:else>
			<ul>
			<li id="valid3" style="display: none; color: red;">
						该时间段内不支持发送，请于当时8点至23点内使用！</li>
			    <li id="valid2" style="display:none;color:red;">
				
					您当日累计获取验证码已达上限，请您次日再试！
				
			    </li>
			    <li id="valid0" style="display:none;color:red;">
				
					您获取验证码过于频繁，请稍后再试！
				
				</li>
				<li>
					<div class="e-box">已验证手机：</div> <span class="fn-f16" id="modify">${verifyMobileInfo.mobileNumber}</span>
				</li>
				<input type="hidden" name="changePayPasswordInfo.modifyStatus"
					value="<s:property value='#request.changePayPasswordInfo.modifyStatus' />">
				<li>
				<p class="fn-gray" id="pRemainTag"></p>
					<div class="phone-btn">
						<div class="p-btn">
							<a href="javascript:void(0);" class="p-btn-sub"
								id="getMail"> <span>获取短信校验码</span>
							</a>
						</div>
					</div>
				</li>
				<li>
					<div class="e-box">请填写短信校验码：</div>
					<input type="text" class="phone-yzm" name="changePayPasswordInfo.mobileVerifyCode">
					<div id="verifyMobileCodeerrdiv1" style="display: none;" class="red-text fn-red">请输入短信校验码</div>
					<div id="verifyMobileCodeerrdiv" style="display: none;" class="red-text fn-red">您输入的短信校验码不正确</div>
					<div id="verifyMobileCodeerrdiv2" style="display: none;"class="red-text fn-red">您的输入的短信校验码已超时，请重新获取</div>
				</li>

				<li>
					<div class="e-box">验证码：</div> <input id="verifyCode" type="text" class="email-yzm"
					name="changePayPasswordInfo.verifyCode">
					<div class="yzm-wb fn-blue">
						<span style="float: left;"><a href="javascript:void(0);"
							 id="loadImgCode1">&nbsp;<img id="imageCode" width="80"
								height="30" />&nbsp;
						</a></span><a href="javascript:void(0);" id="loadImgCode">看不清？换一张</a>
					</div> <br />
					<div id="verifyCodeerrdiv" style="display: none;"
						class="red-text fn-red">您输入的验证码不正确</div>
					<div id="verifyCoderNull" style="display: none;"
						class="red-text fn-red">请输入验证码</div>
				</li>
			</ul>
			<div class="button">
				<a href="javascript:void(0);" class="btn-submit"
					 id="submit"><span>提交</span></a>
			</div>
			</s:else>
		</form>
	</div>
	<!--文本框-->
	<!--fn-right end-->
</div>
</div>