<%@page language="java"
	import="com.kmzyc.zkconfig.ConfigurationUtil"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
  String wapCssPath = ConfigurationUtil.getString("cssAndJsPath");
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
	<jsp:param name="titlePrefix" value="支付"></jsp:param>
</jsp:include>
</head>
<body>
	<c:if test="${sessionScope.b2b_login_remark != '03'}">
	<jsp:include page="/html/common/wap/portal-head.jsp">
		<jsp:param name="pageTitle" value="支付"></jsp:param>
	</jsp:include>
	</c:if>
	<div class="container">
		<s:if test="#request.orderTimeOut==false">
			<div class="form-horizontal">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5 class="ibox-h5-add">订单提交成功，请尽快支付</h5>
						<div class="ibox-tools"></div>
					</div>
					<div class="ibox-content">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-4 control-label"> <s:if
										test="#request.rechargeOrOrderFlag!=null && #request.rechargeOrOrderFlag==1">充值单号：</s:if>
									<s:elseif
										test="#request.rechargeOrOrderFlag!=null && #request.rechargeOrOrderFlag==2">订&nbsp;&nbsp;单&nbsp;&nbsp;号：</s:elseif>
									<s:elseif
										test="#request.rechargeOrOrderFlag!=null && #request.rechargeOrOrderFlag==3">还款单号：</s:elseif>
								</label>
								<div class="col-sm-8">${orderCode }</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">应付金额：</label>
								<div class="col-sm-8">
									<strong class="text-danger">￥<font id="orderAmount"><fmt:formatNumber
												pattern="0.00" value="${payMoney}" /></font>元
									</strong>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="form-horizontal">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5 class="ibox-h5-add">
							支付平台<span class="ibox-h5-span">支持网银与其他平台支付方式</span>
						</h5>
						<div class="ibox-tools"></div>
					</div>
					<div class="ibox-content">
							<s:if test="isWxPay == 0">
								<ul class="bank-list fn-clear" >
								<li class="none">
									<label class="defray-logo"> <input type="hidden"
										value="<s:property value='openid'/>" name="openid" id="openid" />
										<input type="radio" class="payment" name="payment"
										checked="checked" value="5"> <span
										class="defray-logo-img" class="pay-btn" title="微信支付"> <img
											alt="微信支付"
											src="<%=wapCssPath%>/reswap/images/template/weixinzhifu.png" />
									</span>
									</label>
								</li>
								</ul>	
							</s:if>
							<s:if test="isWxPay != 0">
		            		<label class="defray-logo">
		              			<input type="radio" class="payment" name="payment" <s:if test="isWxPay != 0"> checked="checked" </s:if> value="7">
		              			<span class="defray-logo-img" class="pay-btn" title="康美通支付">
		                  			<img alt="康美通支付" src="<%=wapCssPath%>/reswap/images/template/kmtpay.png" />
		              			</span> 
		              			</label>
		              			<c:if test="<%=(Calendar.getInstance().get(Calendar.MONTH)==7&&Calendar.getInstance().get(Calendar.DATE)>7) ||(Calendar.getInstance().get(Calendar.MONTH)==8&&Calendar.getInstance().get(Calendar.DATE)>=1)
									|| (Calendar.getInstance().get(Calendar.MONTH)==9&&Calendar.getInstance().get(Calendar.DATE)<19)%>">
		              			<span class="recommended">
					                <i class="icon-recommended">推荐</i>
					                <p style="margin-left: 10px;">首次使用康美钱包满60元立减10元</p>
					            </span> 
                               </c:if>
		            		
		            		<label class="defray-logo">
		              			<input type="radio" class="payment" name="payment" value="3">
		              			<span class="defray-logo-img" class="pay-btn" title="支付宝支付">
		                  			<img alt="支付宝支付" src="<%=wapCssPath%>/reswap/images/template/logo1.png" />
		              			</span> 
		            		</label>
		            		
							
							</s:if>
						
					</div>
				</div>
				<div class="form-group">
					<a href="javascript:void(0)" class="btn btn-success btn-block"
						id="wapPayLink" data-orderCode='${orderCode}'
						data-rechargeOrOrderFlag='${rechargeOrOrderFlag}' submitorder="0">去支付</a>
				</div>
			</div>
		</s:if>
		<s:else>
			<div class="ui-tipbox ui-tipbox-success">
				<div class="ui-tipbox-icon">
					<i class="icon-uniE626 font-w5 warn-danger"></i>
				</div>
				<div class="ui-tipbox-content">
					<p class="ui-tipbox-title">
						<s:if
							test="!#request.errorMessageFlag && #request.rechargeOrOrderFlag ==2">订单过期</s:if>
						<s:else>${errorMessage }</s:else>
					</p>
					<p class="ui-tipbox-explain">
					<dl>
						<dt>
							<strong>建议您：</strong>
						</dt>
						<dd>1、请您重新拍下商品，创建交易再支付</dd>
						<dd>
							2、您也可以<a href="/queryOrderList.action" class="alert-link">返回我的订单</a>查看
						</dd>
					</dl>
					</p>
				</div>
			</div>
		</s:else>
	</div>
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
	<c:if test="${sessionScope.b2b_login_remark != '03'}">
	<%@ include file="/html/common/wap/portal-foot.jsp"%>
	</c:if>
</body>
</html>
