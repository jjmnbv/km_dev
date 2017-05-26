<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String cssjsPath=ConfigurationUtil.getString("CSS_JS_PATH");
%>
<s:form id="sform" name="sform" action="queryWapReturnShopList.action" method="post" theme="simple">
	<s:hidden name="page" id="page"/>
	<input type="hidden" value="${productImgServerUrl}" id="productImgServerUrl"/>
	<input type="hidden" value="<%=cssjsPath%>" id="cssJsPath"/>
	<s:token></s:token>
	<div class="header-search">
	  	<input class="search-txt" type="text" placeholder="搜索你想找的商品" autocomplete="off" name="searchKeyword.keyword" value="${searchKeyword.keyword}">
	 	<span class="search-icon icon-uniE036 j_searchSpan"></span>
	</div>
	<div class="container category-box">
		<div id="body">
 		<s:iterator value="pagintion.recordList" var="var" status="st">
  		<section class="saleinfo">
    		<div class="order-list">
      			<ul class="text-list">
        			<li>退换货编号：<span class="text-success">${var.orderAlterCode}</span></li>
        			<li>状态：
        			<s:if test="#var.proposeStatus == -2"><span class="text-error">已驳回</span></s:if>
					<s:elseif test="#var.proposeStatus == -1"><span class="text-error">已取消</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 1"><span class="text-success">已提交待审核</span></s:elseif>
              		<s:elseif test="#var.proposeStatus == 2 && #var.alterType == 2"><span class="text-success">已通过待换货</span></s:elseif>
              		<s:elseif test="#var.proposeStatus == 2 && #var.alterType == 1"><span class="text-success">已通过待退货</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 3 && #var.alterType == 1"><span class="text-success">已退货待取件</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 3 && #var.alterType == 2"><span class="text-success">已换货待取件</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 4"><span class="text-success">已取件待质检</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 51&& #var.alterType == 4"><span class="text-success">
						<s:if test="#var.alterType==4">已通过待退款</s:if><s:else>已质检待退款</s:else>
					</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 51&& #var.alterType != 4"><span class="text-success">已质检待退款</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 52"><span class="text-success">已质检待发货</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 53"><span class="text-success">已驳回待返回原件</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 54"><span class="text-success">换货转退货</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 59"><span class="text-success">退款中</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 61"><span class="text-success">已退款待确认</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 62"><span class="text-success">已发货待签收</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 63"><span class="text-success">已返回原件待签收</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 7"><span class="text-success">已完成</span></s:elseif>
					<s:elseif test="#var.proposeStatus == 12"><span class="text-error">送货失败</span></s:elseif>
        			</li>
        			<li>订单编号：<span class="text-success">${var.orderCode}</span></li>
        			<li>申请时间：<span class="text-muted"><s:date name="#var.createDate" format="yyyy-MM-dd HH:mm:ss"/></span></li>
      			</ul>
      			<div class="btnabs">
      				<s:if test="#var.proposeStatus == 1 ">
        			<a href="javascript:void(0)" data-code="${var.orderAlterCode}" data-new-status="-1" data-org-status="${var.proposeStatus}" data-org-type="${var.alterType}" class="btn btn-default btn-my btn-sm js_cancelOrderAlter">取消</a>
      				</s:if> 
      				<s:elseif test="#var.proposeStatus == 2">
					<a href="javascript:void(0)" data-code="${var.orderAlterCode}" class="btn btn-default btn-my btn-sm js_doReturn"><s:if test="#var.alterType == 2">换货</s:if><s:elseif test="#var.alterType == 1">退货</s:elseif> </a>
					</s:elseif>
					<s:elseif test="#var.proposeStatus == 61">
					<a href="javascript:void(0)" data-code="${var.orderAlterCode}" data-new-status="7" data-org-status="${var.proposeStatus}" class="btn btn-default btn-my btn-sm js_cancelOrderAlter">确认收款</a>
					</s:elseif>
					<s:elseif test="#var.proposeStatus == 62">
					<a href="javascript:void(0)" data-code="${var.orderAlterCode}" data-new-status="7" data-org-status="${var.proposeStatus}" class="btn btn-default btn-my btn-sm js_cancelOrderAlter">确认收货</a>
					</s:elseif>
					<s:elseif test="#var.proposeStatus == 63">
					<a href="javascript:void(0)" data-code="${var.orderAlterCode}" data-new-status="7" data-org-status="${var.proposeStatus}" class="btn btn-default btn-my btn-sm js_cancelOrderAlter">确认收货</a></p>
					</s:elseif>
					<s:elseif test="#var.proposeStatus == 12">
					<a href="javascript:void(0)" class="fn-red">联系客服</a>
					</s:elseif>
					<a href="/member/checkWapReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}" class="btn btn-success btn-my btn-sm">跟踪</a>
      			</div>
    		</div>
    		<ul class="tabs-lst">
      			<li>
        			<div class="list-thumb">
        				<a href="/member/queryWapReturnDetail.action?orderAlterCode=${var.orderAlterCode}">
        				<img src="${productImgServerUrl}${var.orderItem.defaultProductImage.imgPath7}" title="${orderItemVar.commodityName}" onerror="this.onerror='';this.src='<%=cssjsPath %>images/default__logo_err60_60.jpg'"/>
        				</a>
        			</div>
        			<div class="list-descriptions">
          				<div class="list-descriptions-wrapper">
	            			<div class="product-name"><a href="/member/queryWapReturnDetail.action?orderAlterCode=${var.orderAlterCode}">${var.orderItem.commodityName}</a></div>
	            			<div class="price-spot"> 数量：<strong class="product-price">${var.alterNum}</strong></div>
          				</div>
          				<a href="/member/queryWapReturnDetail.action?orderAlterCode=${var.orderAlterCode}" class="discounts icon-uniE61F"></a>
        			</div>
      			</li>
    		</ul>
  		</section>
  		</s:iterator>
 	</div>
</div>
<s:if test="${pagintion.totalpage>1}">
<div class="body-load text-center text-success" id="page2" data-value="${pagintion.totalpage}"><i class="icon icon-spinner icon-xs"></i>加载中，请稍后...</div>
</s:if>
<div style="display:none;"id="pageError" class="body-load text-center text-error"><i class="icon icon-frown-o icon-xs"></i>加载失败，请重试...</div>
</s:form>
