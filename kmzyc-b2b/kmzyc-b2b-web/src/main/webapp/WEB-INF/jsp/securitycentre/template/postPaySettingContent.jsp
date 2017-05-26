<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="l-right user-m">
	<div class="o-mt">
		<h2>支付功能设置</h2>
	</div>

	<!--进度条-身份验证-->
	<!--进度条-身份验证end-->
	<!--文本框-->
	<div class="l-right user-m">
		<div class="i-modify fn-t10 tips-green">
			<ul>
				<li>
					<div class="tips">
						<i></i>你已经成功设置支付功能。<span><a href="showSecurityCentre.action">返回安全中心</a></span>
	
					</div>
				</li>

			</ul>
		</div>
		<div style="display: none;" class="safety-tips">
			<!--此功能暂时不启用-->
			<h2>安全服务提示</h2>
			<p>1.确认您登录的是康美网址 <%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>，注意防范进入钓鱼网站，不要轻信各种即时通讯工具发送的商品或支付链接，谨防网购诈骗。</p>
			<p>2.建议您安装杀毒软件，并定期更新操作系统等软件补丁，确保账户及交易安全。</p>
		</div>
	</div>
	<!--文本框-->
	<!--fn-right end-->
</div>
</div>
