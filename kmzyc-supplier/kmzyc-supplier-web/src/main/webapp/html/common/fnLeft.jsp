<%@page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
<!--内容区-->
        <div class="l-w i-breadcrumb">
			<h1><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/index.html">帮助中心</a></h1>
			<span id="breadcrumb_subTitle" style="display: none;">&gt;<span id="subTitle"></span></span>
		</div>
        <!--面包屑breadcrumb END-->
		
  <div class="l-w fn-clear">
          <div class="l-left">
            <div class="mykm">
              <div class="mt">
                <h2><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/index.html">帮助中心</a></h2>
              </div>
              <div class="mc">
			  <dl>
                  <dt>关于我们<b class="user-img"></b></dt>
                  <dd id="dl1" style="display: block;">
                    <div id="about" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-about.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-about.shtml">关于康美中药城</a></div>
                    <div id="contact" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-contact.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-contact.shtml">联系我们</a></div>
                    <div id="qualification" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-qualification.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-qualification.shtml">专来资质</a></div>
					<div id="promises" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-promises.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-promises.shtml">服务承诺</a></div>
                  </dd>
                </dl>
				
              	<dl>
                  <dt>会员相关<b class="user-img"></b></dt>
                  <dd id="dl2" style="display: block;">
                    <div id="guidelines" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-guidelines.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-guidelines.shtml">会员须知</a></div>
                    <div id="regAndLogin" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-reg-and-login.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-reg-and-login.shtml">注册登录</a></div>
                    <div id="agreement" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-member-agreement.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-member-agreement.shtml">会员注册协议</a></div>
                    <div id="level" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-point-and-level.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-point-and-level.shtml">积分和等级</a></div>
					<div id="password" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-forget-password.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-forget-password.shtml">忘记密码</a></div>
					<div id="member" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-tourist-to-member.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-tourist-to-member.shtml">积分和等级</a></div>
                  </dd>
                </dl>
                <dl>
                  <dt>购物指南<b class="user-img"></b></dt>
                  <dd id="dl3" style="display: block;">
                      <div id="guidance" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-shopping-guidance.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-shopping-guidance.shtml">购物流程与结算</a></div>
					  <div id="shopping" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-tourist-shopping.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-tourist-shopping.shtml">免注册购物</a></div>
                      <div id="goods" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-find-goods.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-find-goods.shtml">查找商品</a></div>
                      <div id="invoice" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-invoice.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-invoice.shtml">发票制度</a></div>
					  <div id="tracking" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-order-tracking.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-order-tracking.shtml">订单跟踪</a></div>
                  </dd>
                </dl>
                <dl>
                  <dt>支付配送<b class="user-img"></b></dt>
                  <dd id="dl4" style="display: block;">
                    <div id="payment" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-payment.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-payment.shtml">支付方式</a></div>
					<div id="balance" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-account-balance.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-account-balance.shtml">余额充值</a></div>
                    <div id="faqs" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-payment-faqs.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-payment-faqs.shtml">支付常见问题</a></div>
                    <div id="shipping" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-shipping.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-shipping.shtml">配送方式</a></div>
					<div id="range" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-shipping-range.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-shipping-range.shtml">配送范围</a></div>
					<div id="received" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-received.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-received.shtml">收货验货</a></div>
                  </dd>
                </dl>
                <dl>
                  <dt>售后服务<b class="user-img"></b></dt>
                  <dd id="dl5" style="display: block;">
                    <div id="sale" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-after-sale.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-after-sale.shtml">售后服务</a></div>
                    <div id="refunding" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-changing-or-refunding.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-changing-or-refunding.shtml">退换货政策</a></div>
                    <div id="guidance" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-CR-guidance.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-CR-guidance.shtml">退换货流程</a></div>
                    <div id="evaluation" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-shopping-evaluation.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-shopping-evaluation.shtml">商品评分</a></div>
                  </dd>
                </dl>
				
				<dl>
                  <dt>其它帮助<b class="user-img"></b></dt>
                  <dd id="dl6" style="display: block;">
                    <div id="coupon" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-coupon.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-coupon.shtml">优惠劵使用</a></div>
                    <div id="number" data_id="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-license-number.shtml"><a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-license-number.shtml">批准文号说明</a></div>
                    <div id="queryFAQList" data_id="<%=ConfigurationUtil.getString("portalPath")%>/member/queryFAQList.action"><a href="<%=ConfigurationUtil.getString("portalPath")%>/member/queryFAQList.action">常见问题</a></div>
                  </dd>
                </dl>
                
              </div>
            </div>
          </div>
          <!--fn-left end-->
