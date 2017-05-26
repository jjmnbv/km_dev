<%@ page language="java" pageEncoding="UTF-8" import="java.util.*,com.kmzyc.framework.constants.Constants,com.km.framework.common.util.MD5"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  


	<div class="mt">
		<ul class="tab">
			<li class="curr li_coupon" flag="div_cp_inuse">可用优惠券<em>(${couponResult.inUseCouponSize})</em></li>
			<li class="li_coupon" flag="div_cp_outuse">不可用优惠券 (${couponResult.outUseCouponSize}) </li>
		</ul>
	</div>
	<div class="tabcon">
		<div class="inner div_coupon" id="div_cp_inuse">
			<div class="fn-mgray tip j_no_coupon_tip" <c:if test="${not empty couponResult.inUseCouponSize and 0!=couponResult.inUseCouponSize}">style="display:none;"</c:if>>
				此订单暂无可用的优惠券。 您可以
				<a target="_blank" href="/member/queryCouponList.action?couponStatus=3">查看所有优惠券</a>了解使用限制。
			</div>
			<div class="actual-form" <c:if test="${0==couponResult.inUseCouponSize}">style="display:none;"</c:if>>
				<div id="counponMoneyInfo" class="coupon-crl" style="display: inline-block;">
					<s:if test="null!=#request.couponResult.inUseCouponList">
					<s:if test='#request.accountInfo.paymentpwd==null'>
					<select class="j_couponMoney" id="couponMoney" name="couponId" data-flag='0'>
						<option  value="-1">---请选择---</option>
						<c:forEach items="${couponResult.inUseCouponList}" var="coupon">
							<s:if test="${coupon.useLimitsType}==1">
								<option value="${coupon.couponId}">${coupon.couponName}[不可和余额同时使用]</option>
	                       	</s:if>
	                       	<s:else>
								<option value="${coupon.couponId}">${coupon.couponName}</option>
	                       	</s:else>
						</c:forEach>
					</select>
					
					<%-- 为账户安全请<a class="btn-submit" href="/showSecurityCentre.action"><span>启用支付密码</span></a> --%>
					</s:if>
					<s:elseif test='#request.payRangeList.contains("2")'>
					<select class="j_couponMoney" id="couponMoney" name="couponId">
						<option  value="-1">---请选择---</option>
						<c:forEach items="${couponResult.inUseCouponList}" var="coupon">
							<s:if test="${coupon.useLimitsType}==1">
								<option value="${coupon.couponId}">${coupon.couponName}[不可和余额同时使用]</option>
	                       	</s:if>
	                       	<s:else>
								<option value="${coupon.couponId}">${coupon.couponName}</option>
	                       	</s:else>
						</c:forEach>
					</select>
					<div class="pay-pas">
					支付密码&nbsp;:&nbsp;<input type="password" value="" class="fn-text"id="couponpasswd" /> 
					</div>
					<a href="javascript:void(0);" class="btn-submit j_changeCouponMoney"><span>使用优惠券</span></a>
					</s:elseif>
					<s:else>
					<select class="j_couponMoney" id="couponMoney" name="couponId" data-flag='0'>
						<option  value="-1">---请选择---</option>
						<c:forEach items="${couponResult.inUseCouponList}" var="coupon">
							<s:if test="${coupon.useLimitsType}==1">
								<option value="${coupon.couponId}">${coupon.couponName}[不可和余额同时使用]</option>
	                       	</s:if>
	                       	<s:else>
								<option value="${coupon.couponId}">${coupon.couponName}</option>
	                       	</s:else>
						</c:forEach>
					</select>
					</s:else>
					</s:if>
				</div>
			</div>
			<div class="virtual-from"></div>
			<div class="fn-t10">
				直接激活使用：<input autocomplete="off" type="text" value="请输入您所获得优惠券激活码" size="32" class="fn-text" name="couponActivate" id="couponActivate" />
				<a href="javascript:void(0);" class="btn-submit j_couponActivate"><span>使用</span></a>
			</div>
			<div class="virtual-from" <c:if test="${0==couponResult.inUseCouponSize}">style="display:none;"</c:if>></div>
			<div class="total" id="J_total_ID" style="display: none;">
				您已选择使用优惠券<strong id="couponName">0</strong>，可以优惠<strong id="couponPrice">0.00</strong>元
				<input type="hidden" id="selectCouponId">
			</div>
		</div>
		<div class="tabcon-hide  div_coupon" style="display:none;" id="div_cp_outuse">
			<div class="inner">
				<s:if test="0!=#request.couponResult.outUseCouponSize">
				<div class="tip fn-mgray">
					此处仅展示不符合本次订单商品使用的优惠券
					<!-- <a target="_blank" href="javascript:void(0)">[了解优惠券规则]</a> -->
				</div>
				</s:if>
				<s:else>
				<div class="fn-mgray tip">
				此订单暂无不可用优惠券。 您可以
					<a target="_blank" 
					href="/member/queryCouponList.action?couponStatus=3">查看所有优惠券</a>了解使用限制。
				</div>
				</s:else>
				<div class="virtual-from">
					<div class="virtual-table"></div>
					<s:if test="0!=#request.couponResult.outUseCouponSize">
					<h4>优惠劵：</h4>
					<div class="virtual-table">
						<c:forEach var="outCouponMap" items="${couponResult.outUseCouponList}">
						<div class="virtual-table-body">
							<table>
								<tbody>
									<tr>
									  	<td class="virtual-action">
										 	<input class="hookbox" type="checkbox" disabled="disabled">
									  	</td>
									  	<td class="virtual-sum">
										 	<span class="coupon-scope">
											${outCouponMap.couponLeastPrice}-${outCouponMap.couponMoney}<font>元</font>
										 	</span>
									  	</td>
									  	<td class="virtual-type" title="${outCouponMap.couponName}" style="width:150px;">
										  <div style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width:150px;">${outCouponMap.couponName}</div>
									  	</td>
									  	<td class="virtual-vtime" style="width:180px;">
									  	 有效期至${outCouponMap.endDate}
									 	</td>
									 	<s:if test="${outCouponMap.isTimeOut}">
										<td title="优惠券未生效">
										 	<div style="color:#E4393C;overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width:375px;">优惠券未生效</div>
									  	</td>
									 	</s:if>
									  	<s:elseif test="${outCouponMap.isUseRange}">
									  	<td title="优惠券不在使用范围内">
										 	<div style="color:#E4393C;overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width:375px;">优惠券不在使用范围内</div>
									  	</td>
									  	</s:elseif>
									  	<s:else>
									  	<td title="商品总额未达到优惠券消费金额${outCouponMap.couponLeastPrice}元">
										 	<div style="color:#E4393C;overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width:375px;">商品总额未达到优惠券消费金额${outCouponMap.couponLeastPrice}元</div>
									  	</td>
									  	</s:else>
									</tr>
								</tbody>
							</table>
						</div>
						</c:forEach>
					</div>
					</s:if>
				</div>
			</div>
		</div>
	</div>
