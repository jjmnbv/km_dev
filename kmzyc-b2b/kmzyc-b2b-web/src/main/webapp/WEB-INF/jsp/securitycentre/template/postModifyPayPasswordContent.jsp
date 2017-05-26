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
<div class="o-mt"><s:if
	test="#request.changePayPasswordInfo.modifyStatus">
	<h2>修改支付密码</h2>
</s:if> <s:else>
	<h2>启用支付密码</h2>
</s:else></div>

<!--进度条-身份验证-->
<div class="procedure fn-t10 fn-b10">
<ul>
	<li class="black">1、验证身份<b class="background-dd"></b></li>
	<li class="black"><s:if
		test="#request.changePayPasswordInfo.modifyStatus">2、修改支付密码</s:if> <s:else>
						2、启用支付密码
						</s:else><b class="background-dg"></b></li>
	<li class="green">3、完成</li>
</ul>
</div>
<!--进度条-身份验证end--> <!--文本框-->
<div class="l-right user-m">
<div class="i-modify fn-t10 tips-green">
<ul>
	<li>
	<s:if test="#request.changePayPasswordInfo.modifyStatus">
	<div class="tips"><i></i>恭喜，修改支付密码成功</div>
	</s:if>
	<s:else>
		<div class="tips"><i></i>恭喜，启用支付密码成功</div>
	</s:else>
	</li>
	<li class="fn-clear"><span class="progb-safe-modify-l">安全程度：</span>
	<s:if test="#request.safeLevel==0">
		<span class="ico-spoh progb-safe-l mar-t4"></span> <span class="progb-safe-modify-r">建议您设置更高强度的密码</span>
	</s:if> <s:if test="#request.safeLevel==1">
		<span class="ico-spoh progb-safe-c mar-t4"></span>  <span class="progb-safe-modify-r">建议您设置更高强度的密码</span>
	</s:if> <s:if test="#request.safeLevel==2">
		<span class="ico-spoh progb-safe-h mar-t4"></span>   <span class="progb-safe-modify-r">您的密码具有很高的安全度</span>
	</s:if></li>
	<li>您可以<span><a
		href="<%=basePath%>member/goPaySetting.action">设置支付功能</a></span></li>
</ul>
</div>
<div style="display: none;" class="safety-tips"><!--此功能暂时不启用-->
<h2>安全服务提示</h2>
<p>1.确认您登录的是康美网址<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>，注意防范进入钓鱼网站，不要轻信各种即时通讯工具发送的商品或支付链接，谨防网购诈骗。</p>
<p>2.建议您安装杀毒软件，并定期更新操作系统等软件补丁，确保账户及交易安全。</p>
</div>
</div>
<!--文本框--> <!--fn-right end--></div>
</div>
