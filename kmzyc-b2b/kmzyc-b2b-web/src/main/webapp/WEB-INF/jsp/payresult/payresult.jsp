<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil" 	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html>
	<head>
		<meta name="Keywords" content="" />
		<meta name="Description" content="" />
		<jsp:include page="/WEB-INF/jsp/common/template.jsp">
			<jsp:param name="titlePrefix" value="支付"></jsp:param>
		</jsp:include>
	</head>
	<body class="l-w1000">
	<s:if test="#request.rechargeOrOrderFlag!=null && #request.rechargeOrOrderFlag==1">
		<%@ include file="/html/common/portal-common-top.jsp" %>  
		<tiles:insertDefinition name="payTop" />
	</s:if>
	<s:else>
		<tiles:insertDefinition name="payResultTop" />
	</s:else>
	<s:if test="#request.orderTimeOut==false">
		<div class="payresult l-w">
			<div class="payresult-state fn-clear">
				<div class="fn-left ico-pay-suc"></div>
				<div class="payresult-cont">
				<s:if test="#request.rechargeOrOrderFlag!=null && #request.rechargeOrOrderFlag==1">
					<h3>您已申请账户余额充值，请立即在线支付</h3>
				</s:if>
				<s:else>
					<h3>订单提交成功，请您 尽快付款！</h3>
				</s:else>
				<div class="ordernum">
					<s:if test="#request.rechargeOrOrderFlag!=null && #request.rechargeOrOrderFlag==1">充值单号：</s:if>
					<s:elseif test="#request.rechargeOrOrderFlag!=null && #request.rechargeOrOrderFlag==2">订单号</s:elseif>
					<s:elseif test="#request.rechargeOrOrderFlag!=null && #request.rechargeOrOrderFlag==3">还款单号</s:elseif>
					<em>${orderCode }</em>，应付金额
					<span class="fn-red" id="payMoney">￥<fmt:formatNumber pattern="0.00"  value="${payMoney}"/></span>元
				</div>
				<s:if test="#request.rechargeOrOrderFlag!=null && #request.rechargeOrOrderFlag!=1">
					<p>您选择的支付方式是：<strong class="fn-red">在线支付</strong>。请按下方提示选择您的支付渠道完成支付操作。</p>
					<p style="text-align:left;">
						<span style="font-family:微软雅黑;font-size:14px;font-weight:normal;font-style:normal;text-decoration:none;color:#006699;">请在订单提交后的</span>
						<span style="font-family:微软雅黑;font-size:14px;font-weight:bold;font-style:normal;text-decoration:none;color:#FF0000;">24小时内</span>
						<span style="font-family:微软雅黑;font-size:14px;font-weight:normal;font-style:normal;text-decoration:none;color:#006699;">完成支付，否则订单将失效，不能支付。</span>
					</p>
				</s:if>	
				</div>
			</div>
			<div class="payresult-choose">
				<div class="ui-tab">
					<ul class="ui-tab-trigger fn-clear pay-box-trigger" id="payType">
					<s:if test="orderType != 3 && orderType != 4 && orderType != 5">
						<li class="ui-tab-trigger-item current" id="pay">支付平台</li>
						<li class="ui-tab-trigger-item" style="display: none"  id="saveCard">网上银行</li>
						<li class="ui-tab-trigger-item" style="display: none" id="accessCard">信用卡</li>
					</s:if>
					<s:else>
						<li class="ui-tab-trigger-item current" id="sdPay">时代支付</li>
					</s:else>
					</ul>
					<div class="ui-tab-cnt pay-box-cnt" >
						<div class="ui-tab-cnt-item"  id="pay-list">
							<div class="paybox-tit"><strong>支付平台</strong></div>
							支持网银与其他平台支付方式
							<div id="div2">
							<ul class="bank-list fn-clear" >
								<li class="none">
									<input type="radio" class="radio" name="payment" value="3" checked="checked"/>
									<div class="bank-info">
										<label>
											<a href="javascript:void(0);">
											<img width="125" height="28" src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/common/pay/alipay.png" alt="支付宝支付" />
											</a>
										</label>
									</div>
								</li>
								<%-- <li class="none">
									<input type="radio" class="radio" name="payment" value="7" />
									<div class="bank-info">
										<label>
											<a href="javascript:void(0);">
												<img width="125" height="28" src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/common/pay/kmtpay.png" alt="康美通支付" />
											</a>
										</label>
									</div>
								</li> --%>
								<%-- <li>
									<div class="paybox-tit"><strong>网银支付</strong> 需开通网银</div>
									<div class="ui-tab-cnt-item" id="saveCard-list">
										<input type="hidden" name="sdBankList" id="sdBankList" value="<s:property value='sdBankList'/>" />
										<input type="hidden" name="baseUrl" id="baseUrl" value="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>" />
									</div>
									<input type="hidden" name="payment" value="6" />
								</li> --%>
							</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="pay-btns fn-clear" >
					<div id="btn_order_pay">
						<a class='btn-m-grey fn-right' href='javascript:void(0);' data-orderCode="${orderCode}" data-rechargeOrOrderFlag="${rechargeOrOrderFlag}" id="orderpayId"><span>请稍候</span></a>
					</div>
				</div>
			</div>
		</div>
		<div style="width: 500px; left: -250px; top: 100px; margin-left:50%; padding-bottom:10px;display: none" class="modal-dialog" id="modaldialog">
			<div class="modal-dialog-hd"><span class="dialog-hd-tit">支付</span><a class="close" hidefocus="true"></a></div>
			<div style="width: 476px;margin: 0 auto;overflow: hidden;" class="modal-dialog-bd">
				<div class="pay-popup">
					<div class="pay-numb">
					<s:if test="#request.rechargeOrOrderFlag!=null && #request.rechargeOrOrderFlag==1">充值单号：</s:if>
					<s:if test="#request.rechargeOrOrderFlag!=null && #request.rechargeOrOrderFlag==2">订单号：</s:if>
					<s:if test="#request.rechargeOrOrderFlag!=null && #request.rechargeOrOrderFlag==3">充值单号：</s:if>
					<em>${orderCode}</em>;&nbsp;应付金额：<span class="fn-red">￥${payMoney}</span>元</div>
					<p>请您在新打开的网上银行窗口进行支付操作，支付完成前请不要关闭该窗口。</p>
					<div class="btns">
						<s:if test="#request.rechargeOrOrderFlag==1">
							<a href="/member/queryRechargeDetail.action" class="btn btn-finishpay"></a>
						</s:if>
						<s:if  test="#session.b2b_seesionUserId != null && #request.rechargeOrOrderFlag==2">
							<a href="/member/queryOrderDetail.action?orderMainCode=${orderCode}" class="btn btn-finishpay"></a>
						</s:if>
						<s:if test="#session.b2b_seesionUserId == null  && #request.rechargeOrOrderFlag==2">
							<a href="/ordertrail/viewMyOrderDetail.action?orderTrailInfo.orderNo=${orderCode}&orderTrailInfo.mobileNumber=${mobile}" class="btn btn-finishpay"></a>
						</s:if>
						<a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>helps/help-payment-faqs.shtml"  class="btn btn-payproblem fn-l10" ></a>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-mask" style="display: none;"></div>
		</s:if>
		<s:else>
		<div class="l-w">
			<div class="alert alert-warning fn-t20">
				<div class="alert-inner">
					<span class="fn-left ico-warn"></span>
					<div class="fn-block">
					<s:if test="!#request.errorMessageFlag">
						<s:if test="#request.rechargeOrOrderFlag ==2">
							<p>订单过期</p>
							<dl>
								<dt><strong>建议您：</strong></dt>
								<dd>1、请您重新拍下商品，创建交易再支付</dd>
								<dd>2、您也可以<a href="/queryOrderList.action" class="alert-link">返回我的订单</a>查看</dd>
							</dl>
						</s:if>
						<s:else>
							<p>充值单过期</p>
							<dl>
								<dt><strong>建议您：</strong></dt>
								<dd>1、请您重新<a href="/member/queryAccountInfo.action" class="alert-link">充值</a></dd>
								<dd>2、您也可以<a href="/member/queryRechargeDetail.action" class="alert-link">查看余额明细</a></dd>
							</dl>
						</s:else>
						</s:if>
						<s:else>
							<s:if test="#request.rechargeOrOrderFlag==2">
								<p>${errorMessage }</p>
								<dl>
									<dt><strong>建议您：</strong></dt>
									<dd>1、请您重新拍下商品，创建交易再支付</dd>
									<dd>2、您也可以<a href="/queryOrderList.action" class="alert-link">返回我的订单</a>查看</dd>
								</dl>
							</s:if>
							<s:else>
								<p>${errorMessage }</p>
								<dl>
									<dt><strong>建议您：</strong></dt>
									<dd>1、请您重新<a href="/member/queryAccountInfo.action" class="alert-link">充值</a></dd>
								<dd>2、您也可以<a href="/member/queryRechargeDetail.action" class="alert-link">查看余额明细</a></dd>
								</dl>
							</s:else>
						</s:else>
					</div>
				</div>
			</div>
		</div>
	</s:else>
	<input type="hidden" name="hd_pro_orderCode" id="hd_pro_orderCode" value="${orderCode}" />
	<input type="hidden" name="hd_pro_payMoney" id="hd_pro_payMoney" value="${payMoney}" />
	<input type="hidden" name="hd_pro_account" id="hd_pro_account" value="${user.loginAccount}" />
		
	<%if(null!= request.getAttribute("yqf_json") ){ %>
		<input type="hidden" id="popularize_interId" value="${yqf_interId}"/>
		<input type="hidden" id="popularize_jsonVal" value='${yqf_json}'/>
		<input type="hidden" id="popularize_source" value="${source}"/>
		<input type="hidden" id="popularize_type" value="${type}"/>
	<%} %>
	
	<%if(null!= request.getAttribute("lkt_url") ){ %>
	    <input type="hidden" id="popularize_jsonVal" value='${lkt_url}'/>
		<input type="hidden" id="popularize_source" value="${source}"/>
		<input type="hidden" id="popularize_type" value="${source}"/>
	<%} %>
	<%if(null!= request.getAttribute("duomai_url") ){ %>
	    <input type="hidden" id="popularize_jsonVal" value='${duomai_url}'/>
		<input type="hidden" id="popularize_source" value="${source}"/>
		<input type="hidden" id="popularize_type" value="${source}"/>
	<%} %>
	<%if(null!= request.getAttribute("cgw_url") ){ %>
	    <input type="hidden" id="popularize_jsonVal" value='${cgw_url}'/>
		<input type="hidden" id="popularize_source" value="${source}"/>
		<input type="hidden" id="popularize_type" value="${type}"/>
	<%} %>
	<%if("1".equals(request.getParameter("isCreate"))){ %>
	<script>_ozprm="orderid=${orderCode}&ordertotal=${payMoney}";</script>
	<%} %>
	<%@ include file="/html/common/portal-common-foot.jsp" %>
	</body>
</html>
