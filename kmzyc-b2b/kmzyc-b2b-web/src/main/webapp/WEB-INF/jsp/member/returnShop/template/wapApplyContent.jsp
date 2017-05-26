<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<input type="hidden" id="b2bssion" value="<s:property value='#session.b2b_login_remark'/>"/>
<s:hidden name="page" id="page"/>
<s:token></s:token>
<!--内容区域-->
<div class="container category-box">
    <div class="header-search">
        <form id="searchForm" name="searchForm" action="applyWapPrepare.action" method="post" theme="simple">
            <input class="search-txt" type="text"  value="<s:property value="searchKeyword.keyword"/>"  placeholder="搜索你想找的商品" autocomplete="off" id="searchKeyword.keyword" name="searchKeyword.keyword">
        </form>
        <span class="search-icon icon-uniE036 j_searchSpan"></span>
    </div>
    <div id="body">
    <s:iterator value="pagintion.recordList" var="var" status="st">
    <section class="saleinfo">
        <div class="order-list">
            <ul class="text-list">
                <li>订单编号：<span class="text-success">${var.orderCode}</span></li>
                <li>下单时间：<span class="text-muted"><s:date name="#var.createDate" format="yyyy-MM-dd"/>  <s:date name="#var.createDate" format="HH:mm:ss"/></span></li>
                <li>确认收货：<span class="text-muted"><s:date name="#var.finishDate" format="yyyy-MM-dd"/>  <s:date name="#var.finishDate" format="HH:mm:ss"/></span></li>
            </ul>
            </div>
        	<ul class="tabs-lst">
         	<s:iterator value="#var.orderItemList" var="orderItemVar" status="orderItemStatus">
            	<li>
                	<div class="list-thumb">
	                	<a href="script:void(0)">
	                 		<img src="${productImgServerUrl}${orderItemVar.defaultProductImage.imgPath7}" title="${orderItemVar.commodityName}" onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"/>
	               	 	</a>
               	 	</div>
                	<div class="list-descriptions">
                    	<div class="list-descriptions-wrapper">
                        	<div class="product-name">
                       		<a href="${productPicPath_WAP}${orderItemVar.defaultProductImage.skuId}.shtml" title="${orderItemVar.commodityName}">${orderItemVar.commodityName}</a></div>
                       		<div class="price-spot">数量：<strong class="product-price">${orderItemVar.commodityNumber}</strong>
                           		<p class="help-block">
                           			<s:if test="#orderItemVar.isReturning == 0" >
                       			  		<a href="script:void(0)" class="label label-danger" id="apply" data-id="${orderItemVar.orderItemId}" data-pv="${orderItemVar.commodityPv}"  data-type="<s:property value='#session.b2b_login_remark'/>">申请</a>
                        				</s:if>
                        				<s:else>
                        				<a href="javascript:void(0)" class="label btn-success">已申请</a>
                        				</s:else>
                           		</p>
                       		</div>
                   		</div>
                	</div>
           	 	</li>
            </s:iterator>
        	</ul>
    	</section>
	</s:iterator>
</div>
<s:if test="${pagintion.totalpage>1}">
<div class="body-load text-center text-success" id="page" data-value="${pagintion.totalpage}"><i class="icon icon-spinner icon-xs"></i>加载中，请稍后...</div>
</s:if>
<div style="display:none;"id="pageError" class="body-load text-center text-error"><i class="icon icon-frown-o icon-xs"></i>加载失败，请重试...</div>  
</div>
