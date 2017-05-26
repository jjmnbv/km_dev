<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="l-right user-m">
	<div class="o-mt">
		<h2>我的积分</h2>
		<div class="OrderInfo">
			<ul class="ullist-x">
				<li class="fn-left fn-r10">您的当前可用积分： <strong class="fn-red"><s:property value="scoreView.curScore" />分</strong>
				<li>会员级别： <strong class="fn-green"><s:property value="scoreView.curLevel"/>，您已消费总额为￥<fmt:formatNumber value="${scoreView.totalConsume}" type="currency" pattern="#,##0.00元"/>
				，再消费￥<fmt:formatNumber value="${scoreView.nextConsume}" type="currency" pattern="#,##0.00元"/>
				就升级为<s:property value="scoreView.nextLevel"/>。</strong>
				</li>
			</ul>
		</div>
	</div>
	<div class="user-m fn-t10">
		<div class="mt">
			<ul class="tab">
				<li><s></s><b></b><a href="queryPointsRecordList.action">积分记录</a></li>
				<li><s></s><b></b><a href="queryPointsExchangeCouponList.action">积分兑换优惠劵</a></li>
				<li class="curr"><s></s><b></b><a href="queryPointsRuleList.action">会员积分规则</a></li>
			</ul>
		</div>
	</div>

	<div class="mc">
		<h3>会员级别</h3>
		<div class="ui-table">
			<table class="ui-table user-table ">
				<tr>
					<th width="20%">会员名称</th>
					<th width="20%">累计消费金额（元）</th>
					<th width="20%">上年度消费额（元）</th>
					<th>享受服务</th>
				</tr>
				<s:iterator value="#request.userLevelList">
					<tr>
						<td><s:property value="levelName" /></td>
						<td><fmt:formatNumber value="${expendMin}" type="currency" pattern="#,##0.00"/>
						<td><fmt:formatNumber value="${yearMin}" type="currency" pattern="#,##0.00"/>
						</td>
						<td><s:property value="remark" /></td>
					</tr>
				</s:iterator>
			</table>
		</div>
		<h3>积分获得方式</h3>
		<div class="ui-table">
			<table class="ui-table user-table ">
				<tr>
					<th>积分规则项</th>
					<th width="35%">获得积分值</th>
					<th width="15%">每日上限</th>
				</tr>
				<s:iterator value="#request.scoreRuleList" var="scoreView">
					<tr>
						<td>
							<s:property value="discribe" />
						</td>
						<td><s:property value="scoreValue" /></td>
						<s:if test='#scoreView.dayMax != null'>
						<td><s:property value="dayMax" />分</td>
						</s:if>
						<s:else>
						<td>无上限</td>
						</s:else>
					</tr>
				</s:iterator>
			</table>
		</div>
		<ul class="integration-text">
			<h2>积分获取及使用常见问题</h2>
			<li>1.当积分已达到某一兑换积分标准时，顾客可将累计积分依照网站相应积分及兑换标准兑换成优惠劵，网站将即时从用户会员积分中扣减相应积分。</li>
			<li>2.您兑换的优惠购物券为电子券，有效期为一年，自成功兑换之日起计算。</li>
			<li>3.康美中药城购物券仅限本ID使用，不能折算为现金、也不能再次兑换为积分。</li>
			<li>4.每日至多换取三张优惠券。</li>
			<li>5.各项回馈项目（包含各项商品、服务或抵用券等）及兑换标准及兑换规则均以兑换当时最新回馈活动公告或目录为准。</li>
			<li>6.部分兑换商品有数量限制的，兑完为止。</li>
			<li>7.公告或目录如有有效期限的，逾期即不得兑换。</li>
			<li>8.有效期限为：积分以年度为单位进行两年滚动清零，每年12月31日执行。</li>
		</ul>
	</div>
</div>
</div>