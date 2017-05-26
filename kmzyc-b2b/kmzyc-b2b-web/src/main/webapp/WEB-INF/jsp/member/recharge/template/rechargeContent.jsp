<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="l-right user-m">
	<div class="o-mt">
		<h2>余额与充值</h2>
	</div>
	<div class="m-w w-noborder fn-clear fn-t10">
		<div class="wh">
			<h3>账号余额</h3>
		</div>
		<div class="wc">
			<div class="OrderInfo">
				<ul class="Order-Number fn-clear">
					<li>账号可用余额：<strong class="fn-red">￥<fmt:formatNumber value="${accountInfo.amountAvlibal}" type="currency" pattern="#,##0.00元"/>
					</strong></li>
					<li>
						账号状态：
						<strong class="fn-red">
							<s:if test="#request.accountInfo.status == 1">有效</s:if>
							<s:if test="#request.accountInfo.status == 0">禁用</s:if>
						</strong>
                        <input type="hidden" id="account_status" value="${accountInfo.status}">
					</li>
					<p>
						<label>充值金额：</label>
						<input id="rechargeAmount" name="rechargeAmount"  type="text"  class="u-text80"><em>元</em>
						<span id="rechargeAmount_tip">只能填写大于等于10，小于等于50000的整数金额</span>
                        <span><font id="rechargeAmount_error" class="fn-red"></font></span>
                    </p>
                    <p class="fn-text-center">
						<a id="submit_a" class="btn-submit j_btn_submit"> <span>充值</span></a><a class="fn-blue" href="queryRechargeDetail.action">查看明细</a>
					</p>
					<p class="fn-red">请注意：支持国内主流银行储蓄卡充值，在线支付成功后，充值金额会在1分钟内到账；</p>
				</ul>
			</div>
		</div>
	</div>
	<div class="m-w w-noborder fn-clear fn-t10">
		<div class="wh">
			<h3>账户充值有什么用途？</h3>
		</div>
		<div class="wc">
			<div class="OrderInfo">
				<ol class="decimal">
					<li>账号充值主要用于我的账号零碎余额的汇总和使用。</li>
					<li>其他支付方式充值到我的账号上，一旦转账成功，康美中药城账号储值将不能返还，请仔细确认后再操作。</li>
					<li>康美中药城储值可以在康美中药城购物支付，（不支持手机费、事业费、加油卡、机票、彩票等其它平台支付）。</li>
					<li>购买商品进入支付页面时，系统自动读取“预存账号”，如账户不够支付商品金额，则不会显示此支付选项。</li>
					<li>请妥善保存您的康美中药城账号和密码，康美中药城账号储值不挂失、不计息、不取现，不转移合并。</li>
				</ol>
			</div>
		</div>
	</div>
</div>
</div>
