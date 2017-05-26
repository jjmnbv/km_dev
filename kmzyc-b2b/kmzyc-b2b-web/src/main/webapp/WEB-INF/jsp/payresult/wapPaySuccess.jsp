<%@page language="java"
	import="com.kmzyc.zkconfig.ConfigurationUtil"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
	<jsp:param name="titlePrefix" value="支付结果"></jsp:param>
</jsp:include>
</head>
<body>
	<c:if test="${sessionScope.b2b_login_remark != '03'}">
	<jsp:include page="/html/common/wap/portal-head.jsp">
		<jsp:param name="pageTitle" value="支付结果"></jsp:param>
	</jsp:include>
	</c:if>
	<div class="container">
		<div class="ui-tipbox ui-tipbox-success">
			<s:if test="#request.payFlag">
				<div class="ui-tipbox-icon">
					<i class="icon-uniE635"></i>
				</div>
				<div class="ui-tipbox-content">
					<h3 class="ui-tipbox-title">支付成功！</h3>
				</div>
			</s:if>
			<s:else>
				<div class="ui-tipbox-icon">
					<i class="icon-uniE626 font-w5 warn-danger"></i>
				</div>
				<div class="ui-tipbox-content">
					<h3 class="ui-tipbox-title">支付失败！</h3>
				</div>
			</s:else>
			<p class="ui-tipbox-explain">
				<a class="btn btn-my btn-success btn-sm"
					href="<%=ConfigurationUtil.getString("portalPath_WAP")%>/member/queryWapOrderDetail.action?orderMainCode=${orderCode}"
					role="button">订单详情</a>
			</p>
		</div>
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
	<c:if test="${sessionScope.b2b_login_remark != '03'}">
	<%@ include file="/html/common/wap/portal-foot.jsp"%>
	</c:if>
</body>
</html>
