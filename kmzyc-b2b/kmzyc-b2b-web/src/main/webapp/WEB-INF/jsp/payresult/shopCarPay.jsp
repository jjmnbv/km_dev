<%@ page language="java"  import="java.io.*,com.kmzyc.zkconfig.ConfigurationUtil"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="支付完成"></jsp:param>
</jsp:include>
</head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<body class="l-w1000">
	<%@ include file="/html/common/portal-common-top.jsp" %>  
	<tiles:insertDefinition name="payTop" />
	<div class="shopcart l-w">
		<div class="shopcart-hd fn-clear">
		<s:if test="#request.rechargeOrOrderFlag==1">
			<h2 class="pay-f-title fn-left">充值支付</h2>
		</s:if>
		<s:else>
			<h2 class="pay-f-title fn-left">订单支付</h2>
		</s:else>
		</div>
		<s:if test="#request.payFlag">
				<!--支付成功 -->
				<div class="shopcart-pay-s tips-green fn-t10">
					<div class="failure">
						<span class="error"></span> <span class="error-text  fn-l10">
							<s:if test="#request.rechargeOrOrderFlag==1">
								充值支付成功！
							</s:if>
							<s:else>
								订单支付成功！
							</s:else>
					</span>
					</div>
					<div class="xinxi-text fn-t10">
						<dl>
							<dt>交易结果信息:</dt>
							<dd class="fn-blue"><s:if test="#request.rechargeOrOrderFlag==1">充值单号</s:if><s:else>订单号</s:else>： ${payCommon.orderCode } </dd>
							<s:if test="#request.payCommon.transitionNO!=null">
								<dd>交易流水号：${payCommon.transitionNO }</dd>
							</s:if>
							<s:if test=' payCommon.moneyStr!="0" && payCommon.moneyStr!="0.0"'>
								<dd>
									支付金额 <span class="text-red">￥${payCommon.moneyStr} 元。</span>
								</dd>
							</s:if>
						</dl>
					</div>
					<div class="shopcart-btn fn-clear fn-pr15 fn-pl40">
		                  <a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>index.html"  class="btn btn-look  fn-left"></a>
		                  <s:if test="#request.rechargeOrOrderFlag==1">
							<a href="/member/queryRechargeDetail.action" class="btn-m-grey fn-left" style="margin-left:10px;">
								<span>余额明细</span>
							</a>
							</s:if>
							<s:elseif  test="#session.b2b_seesionUserId != null">
								<a href="/member/queryOrderDetail.action?orderMainCode=${orderCode}"  style="margin-left:10px;" class="btn-m-grey fn-left">
									<span>订单查询</span>
								</a>
							</s:elseif>
							<s:else>
								<a href="/ordertrail/viewMyOrderDetail.action?orderTrailInfo.orderNo=${orderCode}&orderTrailInfo.mobileNumber=${mobile}" style="margin-left:10px;" class="btn-m-grey fn-left">
									<span>订单查询</span>
								</a>
							</s:else>
				    </div>
				</div> 
				<s:if test="#request.rechargeOrOrderFlag==2">
				<div class="tishi fn-t10">
					<ul>
						<li><strong>温馨提示：</strong></li>
						<li>若订单参加送券活动，优惠券将在您确认收货24小时内发放到您的账户中，请在验收商品后及时点击确认收货，谢谢。</li>
						</ul>
				</div>
				</s:if>
		</s:if>
		<s:else> 
			<!--支付失败 -->
			<div class="shopcart-pay-f tips-red fn-t10">
	             <div class="failure">
	                <span class="error"></span>
	                <span class="error-text  fn-l10"><s:if test="#request.rechargeOrOrderFlag==1">充值单</s:if><s:else>订单</s:else>支付未成功，请稍后重新支付。</span>
	             </div>
	             <div class="xinxi-text fn-t10">
	               <dl>
	                <dt>交易结果信息：</dt>
	                <dd><s:if test="#request.rechargeOrOrderFlag==1">充值单号</s:if><s:else>订单号</s:else>： ${payCommon.orderCode }   ； </dd>
	                <dd>交易流水号：${payCommon.transitionNO } </dd>
	                <dd>支付金额 <span class="text-red">￥${payCommon.moneyStr} 元。</span></dd>
	               </dl>
	             </div>
	             <s:if test="#request.rechargeOrOrderFlag==1">
		             <div class="shopcart-btn fn-clear fn-pr15 fn-pl40">
		                  <a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>index.html" class="btn btn-look  fn-left"></a>
						  <a href="/member/queryOrderList.action" class="btn btn-find fn-l10"></a> 
				    </div>
			    </s:if>
			    <s:else>
			    	 <div class="shopcart-btn fn-clear fn-pr15 fn-pl40">
		                  <a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>index.html" class="btn btn-look  fn-left"></a>
						  <a href="/member/queryOrderList.action" class="btn btn-find fn-l10"></a> 
				    </div>
			    </s:else>
	         </div>
	         <div class="tishi fn-t10">
	           <ul>
	             <li><strong>温馨提示：</strong></li>
	             <li>由于支付接口的问题，有时候可能导致在支付平台支付成功后不能正确返回结果。如果您已经在支付平台成功完成支付操作，请将上面显示的交易结果信息提供给我们的客服人员进行人工验证。</li>
	           </ul>
	         </div>
        </s:else>
         
	</div>
	<div class="l-w myfavorite"></div>
	<%@ include file="/html/common/portal-common-foot.jsp" %>
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
	<%if(null!= request.getAttribute("cgw_url") ){ %>
	    <input type="hidden" id="popularize_jsonVal" value='${cgw_url}'/>
		<input type="hidden" id="popularize_source" value="${source}"/>
		<input type="hidden" id="popularize_type" value="${type}"/>
	<%} %>
	<%if(null!= request.getAttribute("duomai_url") ){ %>
	    <input type="hidden" id="popularize_jsonVal" value='${duomai_url}'/>
		<input type="hidden" id="popularize_source" value="${source}"/>
		<input type="hidden" id="popularize_type" value="${source}"/>
	<%} %>
</body>
</html>
