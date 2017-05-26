<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="l-right user-m">
	<div class="o-mt">
		<h2>安全中心</h2>
	</div>
	<s:if test="#request.securityCentreInfo.safeLevel==1">
		<div class="safe-level tips-yellow">
			安全级别<strong>中级</strong><span>(建议您启动全部安全设置，以保证账户及资金安全)</span>
		</div>
	</s:if>
	<s:if test="#request.securityCentreInfo.safeLevel==2">
		<div class="safe-level tips-green">
			安全级别<strong>高级</strong><span>(建议您启动全部安全设置，以保证账户及资金安全)</span>
		</div>
	</s:if>
	<s:if test="#request.securityCentreInfo.safeLevel==0">
		<div class="safe-level tips-red">
			安全级别<strong>低级</strong><span>(建议您启动全部安全设置，以保证账户及资金安全)</span>
		</div>
	</s:if>
	<%--
	<s:if test="#request.securityCentreInfo.safeLevel==0">
		<div class="safe-level tips-red">
			安全级别<strong>低级</strong><span>(建议您启动全部安全设置，以保证账户及资金安全)</span>
		</div>
	</s:if>--%>
	<div class="safe-content">
		<s:if test='#request.isTimesMember!="Y"'>
			<ul>
				<li class="ico-spoh ico-spoh-cor"></li>
				<li class="f">登录密码：</li>
				<li class="red-text">互联网账号存在被盗风险，建议您定期更改密码以保护账户安全</li>
				<li class="modify">
					<!-- 屏蔽掉第三方账号登录进来的需要完善信息的正式会员(这里的需要完善信息的正式会员时没有密码的) 20140418 add by maliqun-->
						<a href="/member/goChangePassword.action">修改</a>
				</li>
			</ul>
		</s:if>
		<input type="hidden" id="mobVa" value="${securityCentreInfo.mobileValidate}" />
<%--
		<s:if test="#request.securityCentreInfo.emailValidate==true">
			<ul>
				<!-- 已经通过手机验证   style="display:none;"  -->
				<li class="ico-spoh ico-spoh-cor"></li>
				<li class="f">邮箱验证<span class="ico-spoh ico-spoh-yel-s"></span></li>
				<li class="text">您已经通过邮箱验证，验证邮箱为：<strong> <s:property
							value="#request.securityCentreInfo.emailAddress" />
				</strong></li>
				<li class="modify"><a
					href="/member/goEmailVerifyPassword.action?verifEmailInfo.modifyStatus=true">修改</a></li>
			</ul>

		</s:if>
		<s:else>
			<ul>
				<!-- 您未通过验证   style="display:none;" -->
				<li class="ico-spoh ico-spoh-war"></li>
				<li class="f">邮箱验证<span class="ico-spoh ico-spoh-gray-s"></span></li>
				<li class="text">验证后，可用于快速找回登录密码，接收账户余额变动提醒。<strong></strong></li>
				<li class="pay-passwod">
					<!-- 屏蔽掉第三方账号登录进来的需要完善信息的正式会员(这里的需要完善信息的正式会员时没有密码的) 20140418 add by maliqun-->
					<s:if test='#session.isTempMember=="Y"'>	
						<a class="btn-submit"  href="javascript:void(0)" ><span id="skipSpan_email">立即验证</span></a>
					</s:if>
					<s:else>
						<a class="btn-submit" href="/member/goEmailVerifyPassword.action?verifEmailInfo.modifyStatus=false"><span>立即验证</span></a>
					</s:else>			
				</li>
			</ul>
		</s:else>--%>

		<s:if test="#request.securityCentreInfo.mobileValidate==true">
			<ul>
				<!-- 已经通过手机验证   style="display:none;"  -->
				<li class="ico-spoh ico-spoh-cor"></li>
				<li class="f">手机验证<span class="ico-spoh ico-spoh-yel-s"></span></li>
				<li class="text">您验证的手机：<strong> <s:property
							value="#request.securityCentreInfo.mobileNumber" />
				</strong></li>
				<li class="modify"><a
					href="/member/goMobileVerifyPassword.action?verifyMobileInfo.modifyStatus=true">修改</a></li>
			</ul>
		</s:if>
		<s:else>
			<ul>
				<!-- 尚未通过手机验证  ---->
				<li class="ico-spoh ico-spoh-war"></li>
				<li class="f">手机验证<span class="ico-spoh ico-spoh-gray-s"></span></li>
				<li class="text">为了您的帐户安全</li>
				<li class="pay-passwod">
					<!-- 屏蔽掉第三方账号登录进来的需要完善信息的正式会员(这里的需要完善信息的正式会员时没有密码的) 20140418 add by maliqun-->
					<s:if test='#session.isTempMember=="Y"'>	
						<a class="btn-submit"  href="javascript:void(0)" ><span id="skipSpan_mobile">立即验证</span></a>
					</s:if>
					<s:else>
						<a class="btn-submit" href="/member/goMobileVerifyPassword.action?verifyMobileInfo.modifyStatus=false"><span>立即验证</span></a>
					</s:else>
				</li>
			</ul>
		</s:else>

		<s:if test="#request.securityCentreInfo.payPasswordInvocation==false">
			<ul>
				<!-- 尚未启用支付密码  -->
				<li class="ico-spoh ico-spoh-war"></li>
				<li class="f">支付密码<span class="ico-spoh ico-spoh-war-s"></span></li>
				<!-- <li class="text">
					<p>启用支付密码后，在使用账户中余额或优惠劵等资产时，需输入支付密码。</p>
				</li> -->
				<li class="text" style="margin-top:-20px;">
					<p style="height: 20px;">启用支付密码后可以使用账户余额功能(使用时须验证)</p>
					<p style="height: 20px;">启用支付密码须通过<h style="color: red;">手机验证</h>并<h style="color: red;">完成过一笔在线支付5元及以上订单</h></p>
					<p>(商家只需验证手机即可启用支付密码)</p>
				</li>
				<li class="pay-passwod">
				<!-- 已经通过手机验证 -->
				<s:if test="#request.securityCentreInfo.mobileValidate==true">
				<a class="btn-submit j_gotoTelValidate"><span>立即启用</span></a>
				</s:if>
				<!-- 未通过手机验证 -->
				<s:if test="#request.securityCentreInfo.mobileValidate==false">
					<!-- 屏蔽掉第三方账号登录进来的需要完善信息的正式会员(这里的需要完善信息的正式会员时没有密码的) 20140418 add by maliqun-->
					<s:if test='#session.isTempMember=="Y"'>	
						<a class="btn-submit"  href="javascript:void(0)" ><span id="skipSpan_password">立即启用</span></a>
					</s:if>
					<s:else>
						<a class="btn-submit j_gotoTelValidate" data-url="<%=basePath%>"><span>立即启用</span></a>
					</s:else>
				
				</s:if>
					</li>
				<li style="display: none;" class="pay-xg"><a href="javascript:void(0)">忘记支付密码</a><a
					href="javascript:void(0)">修改支付密码</a></li>
			</ul>

		</s:if>
		<s:else>
			<ul>
				<!-- 已经启用支付密码  -->
				<li class="ico-spoh ico-spoh-cor"></li>
				<li class="f">支付密码<span class="ico-spoh ico-spoh-cor-s"></span></li>
				<li class="text"> <span
					class="left">安全程度：</span> <span class="right">建议您设置更高强度的密码</span></li>
				<li style="width: 195px;" class="modify"><a
					href="/member/goPaySetting.action">支付管理</a>&nbsp;&nbsp;<a
					href="/member/goPayVerifyPassword.action?changePayPasswordInfo.modifyStatus=true">密码修改</a></li>
				<li style="display: none;" class="pay-xg"><a href="javascript:void(0)">忘记支付密码</a><a
					href="javascript:void(0)">修改支付密码</a></li>
			</ul>
		</s:else>
	</div>

	<div style="display: none;" class="safety-tips">
		<!--此功能暂时不启用-->
		<h2>安全服务提示</h2>
		<p>1.确认您登录的是京东网址 <%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>，注意防范进入钓鱼网站，不要轻信各种即时通讯工具发送的商品或支付链接，谨防网购诈骗。</p>
		<p>2.建议您安装杀毒软件，并定期更新操作系统等软件补丁，确保账户及交易安全。</p>
	</div>
</div>
</div>
