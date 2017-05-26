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
<ul>
	<li class="black">1、验证身份<b class="background-dd"></b></li>
	<li class="black"><s:if
		test="#request.verifyMobileInfo.modifyStatus">2、修改验证手机</s:if> <s:else>
						2、手机验证
						</s:else><b class="background-dg"></b></li>
	<li class="green">3、完成</li>
</ul>
</div>
<!--进度条-身份验证end--> <!--文本框-->
<div class="i-modify fn-t10 tips-green">
<ul>
	
	<s:if test="#request.securityCentreInfo.payPasswordInvocation==false">
	<li>
	<div class="tips"><i></i>恭喜您，手机验证成功</div>
	</li>
	<li>为了您支付账户的安全，请启用<span><a
		href="<%=basePath%>member/goPayVerifyPassword.action?changePayPasswordInfo.modifyStatus=false">支付密码</a></span></li>
	<li>
	</s:if>
	<s:else>
	<li>
	<div class="tips"><i></i>恭喜您，手机验证成功<span><a
		href="showSecurityCentre.action">返回安全中心</a></span></div>
	</li>
	
	</s:else>

</ul>
</div>
<!--文本框--> <!--fn-right end--></div>
</div>