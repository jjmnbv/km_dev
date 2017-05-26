<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="l-right user-m">
<div class="o-mt">
<h2>邮箱验证</h2>
</div>

<!--进度条-身份验证-->
<div class="procedure fn-t10 fn-b10">
<ul>
	<li class="black">1、验证身份<b class="background-dd"></b></li>
	<li class="black"><s:if
		test="#request.verifEmailInfo.modifyStatus">2、修改邮箱</s:if> <s:else>
						2、验证邮箱
						</s:else><b class="background-dg"></b></li>
	<li class="green">3、完成</li>
</ul>
</div>
<!--进度条-身份验证end--> <!--文本框-->
<div class="email-verify">
<div class="box1">
<p class="one"><strong>已发送验证邮件至：</strong> <s:property value="emails" />
<span>请立即完成验证，邮箱验证不通过则验证邮箱失败。</span></p>
<p class="two">验证邮件24小时内有效，请尽快登录您的邮箱点击验证链接完成验证</p>
<a class="btn-submit" href="${emiaurl}"  target="_blank"><span>查看验证邮件</span></a></div>
<div class="box2">
<h6>没有收到邮件？</h6>
<p>1、请检查您的垃圾箱或者广告箱，邮件有可能被误认为垃圾或广告邮件；</p>
<p>2、如果垃圾箱或者广告箱也没有收到，请尝试 <%=ConfigurationUtil.getString("mail.smtp.from")%>添加为收件人白名单（可参考邮箱白名单设置），并<a
	href="javascript:void(0);" class="fn-blue reEmail">重发验证邮件</a></p>
<p id="err" class="red-text fn-red" style="display: none;">发送失败，请刷新后重试</p>
</div>
</div>
<!--文本框--> <!--fn-right end--></div>
</div>
