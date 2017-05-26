<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>

		

<div class="l-left">
	<div class="mykm">
		<div class="mc">
			<dl class="curr">
				<p class="curr-title border-t-none">常见问题</p>
				<dt class="blue">购物指南<b class="user-img"></b></dt>
				<dd id="dl1" style="display: none;">
					<div id="about" data_id="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-shopping-guidance.shtml"><a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-shopping-guidance.shtml">注册与登录</a></div>
					<div id="contact" data_id="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-find-goods.shtml"><a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-find-goods.shtml">供应商入驻</a></div>
					<div id="qualification" data_id="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-coupon.shtml"><a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-coupon.shtml">购物流程</a></div>
				</dd>
			</dl>

			<dl class="curr">
				<dt>配送方式<b class="user-img"></b></dt>
				<dd id="dl2" style="display: none;">
					<div id="guidelines" data_id="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-guidelines.shtml"><a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-guidelines.shtml">配送说明</a></div>
					<div id="regAndLogin" data_id="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-reg-and-login.shtml"><a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-reg-and-login.shtml">运费说明</a></div>
					<div id="password" data_id="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-forget-password.shtml"><a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-forget-password.shtml">验收签收</a></div>
					<div id="password" data_id="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-forget-password.shtml"><a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-forget-password.shtml">发票制度</a></div>
				</dd>
			</dl>
			
			<dl class="curr">
				<dt>支付方式<b class="user-img"></b></dt>
				<dd id="dl3" style="display: none;">
					<div id="shopping" data_id="<%=ConfigurationUtil.getString("staticPath")%>/helps/infor-1412845743549.shtml"><a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/infor-1412845743549.shtml">在线支付</a></div>
					<div id="invoice" data_id="<%=ConfigurationUtil.getString("staticPath")%>/helps/infor-1412845964807.shtml"><a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/infor-1412845964807.shtml">转账付款</a></div>
					<div id="tracking" data_id="<%=ConfigurationUtil.getString("staticPath")%>/helps/infor-1412846042307.shtml"><a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/infor-1412846042307.shtml">支付常见问题</a></div>
				</dd>
			</dl>
			
			<dl class="curr">
				<dt>售后服务<b class="user-img"></b></dt>
				<dd id="dl4" style="display: none;">
					<div id="sale" data_id="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-after-sale.shtml"><a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-after-sale.shtml">退换货政策</a></div>
					<div id="refunding" data_id="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-changing-or-refunding.shtml"><a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-changing-or-refunding.shtml">退换货流程</a></div>
					<div id="returnFlow" data_id="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-CR-guidance.shtml"><a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-CR-guidance.shtml">咨询与投诉</a></div>
					<div id="evaluation" data_id="<%=ConfigurationUtil.getString("staticPath")%>/helps/infor-1412846544187.shtml"><a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/infor-1412846544187.shtml">退款说明</a></div>
				</dd>
			</dl>
		</div>
	</div>
</div> 