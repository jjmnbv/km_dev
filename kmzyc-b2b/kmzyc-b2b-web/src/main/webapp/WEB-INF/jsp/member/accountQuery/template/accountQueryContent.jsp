<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<s:form id="sform" name="sform" action="queryConsumptionDetail.action" method="post" theme="simple">
	<s:hidden name="page" id="page" />
	<s:token></s:token>
	<div class="l-right user-m">
		<div class="o-mt">
			<h2>账号查询</h2>
		</div>
		<div class="m-w w-noborder fn-clear fn-t10">
			<div class="wh">
				<h3>我的余额</h3>
			</div>
			<div class="wc">
				<div class="OrderInfo">
					<ul class="Order-Number fn-clear">
						<li>账号余额：<strong class="fn-red">￥<fmt:formatNumber value="${accountInfo.amountAvlibal}" type="currency" pattern="#,##0.00"/>
						</strong></li>

					</ul>
				</div>
			</div>
		</div>
		<div class="m-w w-noborder fn-clear fn-t10">
			<div class="wh">
				<h3>账号消费明细查询</h3>
			</div>
			<div class="wc">
				<div class="Inquiry">
					<div class="ui-form">
						<label>超始日期：</label>
						<input type="text" name="searchKeyword.startDate" id="startDate" value="${searchKeyword.startDate}" class="u-text-date"/>　
						<label>截止日期：</label>
						<input type="text" name="searchKeyword.endDate" id="endDate" value="${searchKeyword.endDate}"  class="u-text-date"/>　
						<input name="search" id="search" type="button" value="查 询" class="bti btn">
					</div>
				</div>
				<p class="fn-text-center fn-red">只提供即日起，前三个月内的交易信息查询</p>
				<div class="ui-table">
					<table class="ui-table user-table ">
						<thead>
							<tr>
								<th>时间</th>
								<th>订单编号</th>
								<th>消费总金额</th>
								<th>优惠券</th>
								<th>余额支付</th>
								<th>在线支付</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="#request.pagintion.recordList" var="entryVar" status="entryStatus">
							<tr>
								<td><s:date name="#entryVar.payDate" format="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${entryVar.orderCode}</td>
								<td><fmt:formatNumber value="${entryVar.amountPayable}" type="currency" pattern="#,##0.00元"/>
								</td>
								<td>
									<s:if test="#entryVar.preferentialPay > 0"><fmt:formatNumber value="${entryVar.preferentialPay}" type="currency" pattern="#,##0.00元"/>
									</s:if>
									<s:else>--</s:else>
								</td>
								<td>
									<s:if test="#entryVar.balancePay > 0"><fmt:formatNumber value="${entryVar.balancePay}" type="currency" pattern="#,##0.00"/>
									元</s:if>
									<s:else>--</s:else>
								</td>
								<td>
									<s:if test="#entryVar.otherPay > 0"><fmt:formatNumber value="${entryVar.otherPay}" type="currency" pattern="#,##0.00"/>
									元</s:if>
									<s:else>--</s:else>
								</td>
							</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="fn-tr fn-t10">
			<div class="ui-page">
				<!-- 分页组件 -->
       			<tiles:insertDefinition name="pagination"/>
			</div>
		</div>
	</div>
</s:form>
</div>
