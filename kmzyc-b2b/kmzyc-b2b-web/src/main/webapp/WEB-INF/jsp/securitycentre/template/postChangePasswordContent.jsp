<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="l-right user-m">
<div class="o-mt">
<h2>修改登录密码</h2>

</div>
<div class="i-modify fn-t10 tips-green">
<ul>
	<li>
	<div class="tips"><i></i>恭喜，密码修改成功</div>
	</li>
	<li class="fn-clear"><span class="progb-safe-modify-l">安全程度：</span>
	<s:if test="#request.safeLevel==0">
		<span class="ico-spoh progb-safe-l mar-t4"></span> <span class="progb-safe-modify-r">建议您设置更高强度的密码</span>
	</s:if> <s:if test="#request.safeLevel==1">
		<span class="ico-spoh progb-safe-c mar-t4"></span>  <span class="progb-safe-modify-r">建议您设置更高强度的密码</span>
	</s:if> <s:if test="#request.safeLevel==2">
		<span class="ico-spoh progb-safe-h mar-t4"></span>   <span class="progb-safe-modify-r">您的密码具有很高的安全度</span>
	</s:if></li>
	<li>请牢记新设置的密码。<span><a href="showSecurityCentre.action">返回安全中心</a></span>
	</li>
</ul>
</div>
<div style="display: none;" class="safety-tips"><!--此功能暂时不启用-->
<h2>安全服务提示</h2>
<p>1.确认您登录的是康美网址<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>，注意防范进入钓鱼网站，不要轻信各种即时通讯工具发送的商品或支付链接，谨防网购诈骗。</p>
<p>2.建议您安装杀毒软件，并定期更新操作系统等软件补丁，确保账户及交易安全。</p>
</div>
</div>
</div>
