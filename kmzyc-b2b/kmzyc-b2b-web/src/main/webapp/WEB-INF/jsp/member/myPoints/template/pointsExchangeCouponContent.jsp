<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<input type="hidden" id="rtnMsg" value="<s:property value="rtnMsg"/>" />
<input type="hidden" id="result" value="<s:property value="result"/>" />
<form action="queryPointsExchangeCouponList.action" id="pointExchangeCouponForm" method="post" >
<s:hidden name="page" id="page"/>
<div class="l-right user-m">
	<div class="o-mt">
		<h2>我的积分</h2>
		<div class="OrderInfo">
			<ul class="ullist-x">
				<li class="fn-left fn-r10">您的当前可用积分： <strong class="fn-red"><s:property value="scoreView.curScore" />分</strong>
					<input type="hidden" id="myPoint" value="<s:property value="scoreView.curScore" />" />
				</li>
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
				<li id="record"><s></s><b></b><a href="queryPointsRecordList.action">积分记录</a></li>
				<li id="exchange" class="curr"><s></s><b></b><a href="queryPointsExchangeCouponList.action">积分兑换优惠劵</a></li>
				<li id="rule"><s></s><b></b><a href="queryPointsRuleList.action">会员积分规则</a></li>
			</ul>
		</div>
	</div>
	<div class="mc">
		<div class="Inquiry">
			<ul class="ullist-x fn-b10">
				<s:if test='#request.couponTypes.indexOf("5") >= 0'>
					<li>
						<input type="radio" value="512" name="couponValue" id="coupon_5"><label for="coupon_5" >5元优惠劵需500积分兑换</label>
					</li>
				</s:if>
				<s:if test='#request.couponTypes.indexOf("10") >= 0'>
					<li>
						<input type="radio" value="325" name="couponValue" id="coupon_10"><label for="coupon_10" >10元优惠劵需1000积分兑换</label>
					</li>
				</s:if>
				<s:if test='#request.couponTypes.indexOf("20") >= 0'>
					<li>
						<input type="radio" value="326" name="couponValue" id="coupon_20"><label for="coupon_20" >20元优惠劵需2000积分兑换</label>
					</li>
				</s:if>
				<s:if test='#request.couponTypes.indexOf("50") >= 0'>
					<li>
						<input type="radio" value="332" name="couponValue" id="coupon_50"><label for="coupon_50" >50元优惠劵需5000积分兑换</label>
					</li>
				</s:if>
				<s:if test='#request.couponTypes.indexOf("100") >= 0'>
					<li>
						<input type="radio" value="333" name="couponValue" id="coupon_100"><label for="coupon_100" >100元优惠劵需10000积分兑换</label>
					</li>
				</s:if>
				<li>
					<p class="fn-text-center fn-b10">
						<a class="btn-add" href="javascript:;" ><span>兑换优惠券</span></a>
					</p>
				</li>
			</ul>
			<div class="ui-form">
				<label>超始日期：</label><input id="d4311" value="<s:property value="searchKeyword.dateBegin" />" class="u-text-date Wdate"  name="searchKeyword.dateBegin" type="text" />
				<label>截止日期：</label><input id="d4312" value="<s:property value="searchKeyword.dateEnd" />" class="u-text-date Wdate"  name="searchKeyword.dateEnd" type="text" />
				<input class="bti btn" id="btnQuery" type="button" value="查 询" name="" >
			</div>
		</div>
		<div class="ui-table">
			<table class="ui-table user-table ">
				<tr>
					<th width="30%">兑换时间</th>
					<th width="20%">兑换优惠券面值（元）</th>
					<th width="10%">消费积分</th>
					<th width="10%">状态</th>
					<th width="30%">说明</th>
				</tr>
				<s:iterator value="#request.pagintion.recordList">
					<tr>
						<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td><fmt:formatNumber value="${couponValue}" type="currency" pattern="#,##0.00"/>
						</td>
						<td>-<s:property value="scoreNumber" /></td>
						<td>
							<s:if test='isStauts == 0'>
								<span style="color:red;">失败</span>
							</s:if>
							<s:else>
								成功
							</s:else>
						</td>
						<td><s:property value="remark" /></td>
					</tr>
				</s:iterator>
			</table>
		</div>
		<div class="fn-tr fn-t10">
			<div class="ui-page">
				<!-- 分页组件 -->
		        <tiles:insertDefinition name="pagination"/>
			</div>
		</div>
	</div>
</div>
</form>
</div>
