<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<div class="logo-box">
	<div class="l-w i-registration-logo">
		<a id="indexUrl" hidefocus="true" href="">
		<img id="indexImg" width="303" height="77" src=""></a>
	</div>
</div>

<s:form id="sform" name="sform" action="queryTrailOrderList.action"
	method="post" theme="simple">
	<s:hidden name="page" id="page" />
	
	<input name="orderMainCode" type="hidden" value="" id="code"/>
	<input name="orderMainStatus" type="hidden" value="" id="status"/>
	<input name="orderTrailInfo.emailAddress"  type="hidden" id="orderTrailInfo.emailAddress" value="${orderTrailInfo.emailAddress}"/>
	<input name="orderTrailInfo.mobileNumber" type="hidden" id="orderTrailInfo.mobileNumber" value="${orderTrailInfo.mobileNumber}"/>	
	<s:token></s:token>
	<div id="registration-form">
		<div class="o-mt">
			<div class="Inquiry">
				<div class="ui-form">
					<s:select name="searchKeyword.createDate"
						id="searchKeyword.createDate" list="createDateOptionsMap"
						 cssClass="sele j_queryTrailOrderListDate" />
					<s:select name="searchKeyword.orderStatus"
						id="searchKeyword.orderStatus"
						list="#{0:'全部状态',1:'未付款',2:'已付款',3:'配货中',4:'已发货',5:'已完成',7:'已取消'}"
						 cssClass="sele j_queryTrailOrderListStatus" />
					<s:textfield name="searchKeyword.keyword" id="searchKeyword.keyword"  maxlength="20"
						cssClass="Inquiry-text j_dealKeyword" />
					<input name="search" id="search" type="button" value="搜 索" class="bti btn j_queryTrailOrderList">

				</div>
			</div>
		</div>	
		<div class="user-m fn-t10">
			<div class="mt">
				<ul class="tab">
					<li class="curr"><s></s><b></b><a>全部订单</a></li>
				</ul>
			</div>
			<div class="mc">
				<div class="ui-table">
					<table class="ui-table user-table">
						<thead>
							<tr>
								<th>订单编号</th>
								<th>订单商品</th>
								<th>收货人</th>
								<th>下单日期</th>
								<th>总金额</th>
								<th>订单状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="#request.pagintion.recordList" var="orderMainVar" status="orderMainStatus">
								<tr <s:if test="#orderMainVar.parentOrderCode != null">class="strike "</s:if><s:if test="#orderMainVar.orderStatus==16">class="strike strike-hd"</s:if>>
									<td <s:if test="#orderMainVar.parentOrderCode != null">class="strike-bd-first"</s:if><s:if test="#orderMainVar.orderStatus==16">class="strike-hd-first"</s:if>>
									<a class="fn-blue" href="queryTrailOrderDetail.action?orderMainId=${orderMainVar.orderId}&orderTrailInfo.emailAddress=${orderTrailInfo.emailAddress}&orderTrailInfo.mobileNumber=${orderTrailInfo.mobileNumber}&backFlag=${backFlag}">${orderMainVar.orderCode}</a>
									</td>
									<td class="fn-text-left td-s2">
									<s:if test="#orderMainVar.orderStatus != 16">
									<s:iterator value="#orderMainVar.orderItemList" var="orderItemVar" status="orderItemStatus">
									<input value="${orderItemVar.productSkuId}"  name="rows${orderMainVar.orderId}"  type="hidden" /> 
											<s:if test="#orderItemStatus.count%4 == 0">
												<br />
											</s:if>
											<a name="productlink" href="${cmsPagePath}${orderItemVar.productSkuId}.shtml" target="_blank"> 
											<img  src="${productImgServerUrl}${orderItemVar.defaultProductImage.imgPath7}" title="${orderItemVar.commodityName}" onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'">
											</a>
										</s:iterator>
									</s:if>
									</td>
									<td>${orderMainVar.consigneeName}</td>
									<td><s:date name="#orderMainVar.createDate" format="yyyy-MM-dd" /><br />
									    <s:date name="#orderMainVar.createDate" format="HH:mm:ss" /></td>
									<td>
                                        <s:if test="#orderMainVar.parentOrderCode != null">&nbsp;</s:if>
                                        <s:else><fmt:formatNumber value="${orderMainVar.amountPayable}" type="currency" pattern="#,##0.00元"/></s:else>
									</td>
										<s:if test="#orderMainVar.orderStatus == -1"><td>已取消</td></s:if>
								        <s:if test="#orderMainVar.orderStatus == 1"><td class="fn-red">未付款</td></s:if>
	                                    <s:if test="#orderMainVar.orderStatus == 2 || #orderMainVar.orderStatus == 20|| #orderMainVar.orderStatus == 21|| #orderMainVar.orderStatus == 22"><td>已付款</td></s:if>
								        <s:if test="#orderMainVar.orderStatus == 3 || #orderMainVar.orderStatus == 4|| #orderMainVar.orderStatus == 15|| #orderMainVar.orderStatus == 18"><td>配货中</td></s:if>
								        <s:if test="#orderMainVar.orderStatus == 5"><td>已发货</td></s:if>
								        <s:if test="#orderMainVar.orderStatus == 6"><td>已完成</td></s:if>
								        <s:if test="#orderMainVar.orderStatus == 7"><td>已评价</td></s:if>
								        <s:if test="#orderMainVar.orderStatus == 12"><td class="fn-red">送货失败</td></s:if>
										<s:if test="#orderMainVar.orderStatus == 16"><td>已拆分</td></s:if>
									<td><s:if test="#orderMainVar.orderStatus == -1">
											<p class="fn-blue">
												<span><a href="queryTrailOrderDetail.action?orderMainId=${orderMainVar.orderId}&orderTrailInfo.emailAddress=${orderTrailInfo.emailAddress}&orderTrailInfo.mobileNumber=${orderTrailInfo.mobileNumber}&backFlag=${backFlag}">查看</a></span>
												<span><a href="javascript:void(0);" class="j_byAgain" data-orderCode="rows${orderMainVar.orderId}">再购买</a></span> 

											</p>
										</s:if> <s:if test="#orderMainVar.orderStatus == 1">
											<p>
												<span>
												<%-- <a href="payOrderInit.action?orderCode=${orderMainVar.orderCode}&payMethod=5&rechargeOrOrderFlag=2" class="red-btn"" class="red-btn">付款</a> --%>
												<a href="javascript:void(0);" data-orderCode="${orderMainVar.orderCode}" class="red-btn j_pay">付款</a>
												</span>
											</p>
											<p class="fn-blue">
												<span><a href="queryTrailOrderDetail.action?orderMainId=${orderMainVar.orderId}&orderTrailInfo.emailAddress=${orderTrailInfo.emailAddress}&orderTrailInfo.mobileNumber=${orderTrailInfo.mobileNumber}&backFlag=${backFlag}">查看</a></span>
											</p>
										</s:if> <s:if test="#orderMainVar.orderStatus == 2|| #orderMainVar.orderStatus == 20|| #orderMainVar.orderStatus == 21|| #orderMainVar.orderStatus == 22">
											<p class="fn-blue">
												<span><a href="queryTrailOrderDetail.action?orderMainId=${orderMainVar.orderId}&orderTrailInfo.emailAddress=${orderTrailInfo.emailAddress}&orderTrailInfo.mobileNumber=${orderTrailInfo.mobileNumber}&backFlag=${backFlag}">查看</a></span>
												<span><a href="javascript:void(0);" class="j_byAgain" data-orderCode="rows${orderMainVar.orderId}">再购买</a></span>
											</p>
										</s:if> 
										<s:if test=" #orderMainVar.orderStatus == 3 || #orderMainVar.orderStatus == 4">
											<p class="fn-blue">
												<span><a href="queryTrailOrderDetail.action?orderMainId=${orderMainVar.orderId}&orderTrailInfo.emailAddress=${orderTrailInfo.emailAddress}&orderTrailInfo.mobileNumber=${orderTrailInfo.mobileNumber}&backFlag=${backFlag}">查看</a></span>
												<span><a href="javascript:void(0);" class="j_byAgain" data-orderCode="rows${orderMainVar.orderId}">再购买</a></span> 
											</p>
										</s:if>
										<s:if test="#orderMainVar.orderStatus == 5">
											<p class="fn-blue">
												<span><a href="queryTrailOrderDetail.action?orderMainId=${orderMainVar.orderId}&orderTrailInfo.emailAddress=${orderTrailInfo.emailAddress}&orderTrailInfo.mobileNumber=${orderTrailInfo.mobileNumber}&backFlag=${backFlag}">查看</a></span>
											    <span><a href="javascript:void(0);" class="j_byAgain" data-orderCode="rows${orderMainVar.orderId}">再购买</a></span>
											</p>
										</s:if> <s:if test="#orderMainVar.orderStatus == 6">
											<p class="fn-blue">
												<span><a href="queryTrailOrderDetail.action?orderMainId=${orderMainVar.orderId}&orderTrailInfo.emailAddress=${orderTrailInfo.emailAddress}&orderTrailInfo.mobileNumber=${orderTrailInfo.mobileNumber}&backFlag=${backFlag}">查看</a></span>
												<span><a href="javascript:void(0);" class="j_byAgain" data-orderCode="rows${orderMainVar.orderId}">再购买</a></span>
											</p>
										</s:if> 
										<s:if test="#orderMainVar.orderStatus == 7">
											<p class="fn-blue">
												<span><a href="queryTrailOrderDetail.action?orderMainId=${orderMainVar.orderId}&orderTrailInfo.emailAddress=${orderTrailInfo.emailAddress}&orderTrailInfo.mobileNumber=${orderTrailInfo.mobileNumber}&backFlag=${backFlag}">查看</a></span>
												<span><a href="javascript:void(0);" class="j_byAgain" data-orderCode="rows${orderMainVar.orderId}">再购买</a></span>
											</p>
										</s:if> 
										<s:if test="#orderMainVar.orderStatus == 12">
											<p>
												<span><a href="javascript:void(0)" class="fn-red">联系客服</a></span>
											</p>
											<p class="fn-blue">
												<span><a href="queryTrailOrderDetail.action?orderMainId=${orderMainVar.orderId}&orderTrailInfo.emailAddress=${orderTrailInfo.emailAddress}&orderTrailInfo.mobileNumber=${orderTrailInfo.mobileNumber}&backFlag=${backFlag}">查看</a></span>
												<span><a href="javascript:void(0);" class="j_byAgain" data-orderCode="rows${orderMainVar.orderId}">再购买</a></span>
											</p>
										</s:if>
										<s:if test="#orderMainVar.orderStatus == 16">
		                                    <p class="fn-blue">
		                                    	<span><a href="queryTrailOrderDetail.action?orderMainId=${orderMainVar.orderId}&orderTrailInfo.emailAddress=${orderTrailInfo.emailAddress}&orderTrailInfo.mobileNumber=${orderTrailInfo.mobileNumber}&backFlag=${backFlag}">查看</a></span>
		                                    </p>
										</s:if>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<div class="fn-tr fn-t10">
					<a href="javascript:void(0)" style="float:left;" date-backFlag="1" class="btn-cancel j_btn_back"><span>返回</span></a>
						<div class="ui-page">

							<!-- 分页组件 -->
							<tiles:insertDefinition name="pagination" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</s:form>
