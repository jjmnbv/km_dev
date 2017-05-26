<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


	<div class="logo-box">
   			<div class="l-w i-registration-logo page-logo">

				<a href="<!--#echo var="staticPath" -->/index.html"><img src="<!--#echo var="cssAndJsPath" -->/res/images/common/logo.png" height="115" alt="康美中药城"></a>

       			<h1>订单跟踪</h1>
   			</div>
	</div> 

<div id="registration-form">
	<div class="ordertracking fn-clear">
		<div class="order-left fn-left">
			<form action="postOrderTrailStatus.action" id="forms"
				method="post">
				<div class="title">
					<h2>跟踪订单状态</h2>
					<p class="track-info">请输入以下信息来查询您的订单目前的处理状态</p>
				</div>
				<ul class="ordertracking-form">
					<li>
						<p class="track-tit">
							订单编号：<a class="fn-blue wOrder" href="javascript:void(0)">在哪里看到订单编号？</a>
						</p>
						 <div class="track-ipt">
							<input type="text" class="i-text err-input" id="orderNo" name="orderTrailInfo.orderNo" value="">
							<div id="orderid" class="track-tips fn-red" style="display: none;">
								<i class="fn-error"></i>请输入订单编号
						    </div>
							<div id="orderselect" class="track-tips fn-red" style="display: none;">
								<i class="fn-error"></i>订单号与手机号不匹配
							</div>
							<div id="checkOrderRight" class="track-tips" style="display: none;"><i class="fn-go"></i></div>
						</div>
					</li>
					<li>
						<p class="track-tit">
							手机号：<a class="fn-blue wPhone" href="javascript:void(0)">哪个手机号码？</a>
						</p>
						<div class="track-ipt">
							<input type="text" class="i-text err-input" id="mobileNumber" name="orderTrailInfo.mobileNumber">
							<div id="mobileck" class="track-tips fn-red" style="display: none;">
								<i class="fn-error"></i>请输入手机号码
						    </div>
						    <div id="showmob" class="track-tips fn-red" style="display: none;">
								<i class="fn-error"></i>您输入的手机号码格式不正确
						    </div>
						    <div id="checkMobileRight" class="track-tips" style="display: none;"><i class="fn-go"></i></div>
						</div>
					</li>
					<li id="orderstatusVerifyCodeLi" class="track-verify">
						<p class="track-tit">验证码：</p>
						<div class="track-ipt">
							<input type="text" class="err-input" id="verifyCodes" name="orderTrailInfo.verifyCode">
							<div class="code-text">
								<a class="j_imageCode" href="javascript:void(0);">&nbsp;
									<img id="imageCode" width="80" height="30"/>&nbsp;</a>
								<a href="javascript:void(0);" class="j_imageCode">看不清？ 换一张</a>
							</div>
							<div id="verifyCodeerrdiv11" style="display: none;" class="track-tips fn-red">
							 	<i class="fn-error"></i>您输入的验证码不正确
							</div>
							<div id="verifyCodeerrdiv114" style="display: none;" class="track-tips fn-red">
								<i class="fn-error"></i>验证码不能为空
							</div>
						</div>
					</li>
					<li id="orderstatusMobileVerifyCodeLi" style="display: none;" class="mobile-verify">
						<p>
							<label>请输入短信校验码：</label>
						</p>
						<div class="track-ipt">
							<input id="mobileVerifyCode" name="orderTrailInfo.mobileVerifyCode" type="text" class="i-text err-input">
							<div id="resendMessage" class="code-text">
								<a href="javascript:void(0);" id="sendToTel1"
									class="btn-add fn-l5"> <span>重新发送短信校验码</span></a>
							</div>
							<div id="meassage" class="track-tips fn-red" style="display: none">
								<i class="fn-error"></i>短信验证码不正确或超时
							</div>
							<div id="meassagenull" style="display: none;" class="track-tips fn-red">
								<i class="fn-error"></i>短信验证码不能为空
							</div>
						</div>
						<p class="fn-gray" id="countNumberDisplay1">
						校验码已发出，请注意查收短信，如果没有收到，你可以在<strong class="fn-red" id="timeZone1">60</strong>秒后重新发送
						</p>
						<p class="fn-gray" id="countNumberDisabled1" style="display: none">
							校验码已发出，请注意查收短信，如果没有收到，你可以重新发送
						</p>
					</li>
				</ul>
				<div class="reg-btn fn-t20">
					<a id="orderstatusverifycodediv" href="javascript:void(0);">验证身份</a>
				</div>
				<div class="reg-btn fn-t20" >
					<a id="orderstatusordertraildiv" style="display: none;" href="javascript:void(0);">跟踪订单</a>
				</div>	
			</form>
		</div>
		<div class="order-right">
			<form id="queryOrderForm"
				action="queryTrailOrderList.action" method="get">
				<div class="title">
					<h2>查询我的所有历史订单</h2>
					<p class="track-info">
						如果您已经是我们的会员，请直接点击 <a class="fn-blue" href="queryOrderList.action?_r=<%=System.currentTimeMillis()%>">我的订单</a> 查看。<br/>
					          如果您还不是康美中药城会员，请输入以下信息来查询您的历史订单</p>
				</div>
				<ul class="ordertracking-form">
					<li>
						<p class="track-tit">
							电子邮件：<a class="fn-blue wEmail" href="javascript:void(0)">哪个电子邮件？</a>
						</p>
						<div class="track-ipt">
							<input type="text" class="i-text err-input" id="emailAddress" name="orderTrailInfo.emailAddress">
							<div id="emailcknull" class="track-tips fn-red" style="display: none;">
								<i class="fn-error"></i>请输入邮箱
						    </div>
						    <div id="emailck" class="track-tips fn-red" style="display: none;">
								<i class="fn-error"></i>请输入格式正确的邮箱
						    </div>
						    <div id="emailckss" class="track-tips fn-red" style="display: none;">
								<i class="fn-error"></i>没有符合此条件的订单
						    </div>
						 </div>
					</li>
					<li>
						<p class="track-tit">
							手机号：<a class="fn-blue wPhone" href="javascript:void(0)">哪个手机号码？</a>
						</p>
						<div class="track-ipt">
							<input type="text" class="i-text err-input" id="mobileNumber1" name="orderTrailInfo.mobileNumber">
							<div id="mobileck1" class="track-tips fn-red" style="display: none;">
								<i class="fn-error"></i>请输入手机号
						    </div>
						    <div id="mobileck1s" class="track-tips fn-red" style="display: none;">
								<i class="fn-error"></i>您输入的手机号码格式不正确
						    </div>
						</div>
					</li>
					<li id="queryverifycodeLi" class="track-verify">
						<p class="track-tit">验证码：</p>
						<div class="track-ipt">
							<input type="text" class="err-input" id="verifyCodes1" name="orderTrailInfo.verifyCode"><span class="pic"></span>
							<div class="code-text">
								<a href="javascript:void(0);" class="j_imageCodeTwo">&nbsp;
									<img id="imageCodeTwo" width="80" height="30"/>&nbsp;</a>
								<a href="javascript:void(0);" class="j_imageCodeTwo"> 看不清？换一张</a>
							</div>
							<div id="verifyCodeerrdiv1" style="display: none;" class="track-tips fn-red">
								<i class="fn-error"></i>您输入的验证码不正确</div>
							<div id="verifyCodeerrdiv3" style="display: none;" class="track-tips fn-red">
								<i class="fn-error"></i>验证码不能为空</div>
						</div>
					</li>

					<li id="queryMobileVerifyCodeLi" style="display: none;" class="mobile-verify">
						<p>
							<label>请输入短信校验码：</label>
						</p>
						<div class="track-ipt">
							<input id="mobileVerifyCode1" name="orderTrailInfo.mobileVerifyCode" type="text" class="i-text err-input">
							<div class="code-text">
								<a href="javascript:void(0);" id="sendToTel2"
								class="btn-add fn-l5"><span>重新发送短信校验码</span></a>
							</div>
							<div id="meassage1" class="track-tips fn-red" style="display: none">
								<i class="fn-error"></i>短信验证码不正确或超时
							</div>
							<div id="meassage1null" style="display: none;" class="track-tips fn-red">
								<i class="fn-error"></i>短信验证码不能为空
							</div>
						</div>
						<p class="fn-gray" id="countNumberDisplay">
							校验码已发出，请注意查收短信，如果没有收到，你可以在<strong class="fn-red" id="timeZone2">60</strong>秒后重新发送
						</p>
						<p class="fn-gray" id="countNumberDisabled" style="display: none">
							校验码已发出，请注意查收短信，如果没有收到，你可以重新发送
						</p>
						
					</li>
				</ul>
				<div class="reg-btn fn-t20">
					<a id="queryverifycodediv" href="javascript:void(0);">验证身份</a>
				</div>
				<div class="reg-btn fn-t20" >
					<a id="queryQueryOrderdiv" style="display: none;" href="javascript:void(0);">查询订单</a>
				</div>
			</form>
		</div>
	</div>
	<div class="fn-bgyellow fn-t10">
		<p class="fn-text-center fn-f16">如果您作为游客身份进行过购物，还可以<a href="<%=request.getContextPath()%>/member/resetPwdgoUnRegMember.action" class="fn-blue">[转为会员]</a>，可获得您购物订单的积分和更多会员权益。</p>
	</div>
</div>