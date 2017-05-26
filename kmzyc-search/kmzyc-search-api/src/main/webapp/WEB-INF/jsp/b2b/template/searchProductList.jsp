<%@ page language="java"  import="com.kmzyc.search.facade.config.Configuration"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<input id="query" type="hidden" value='${keyword}'>
<div class="m-prosales-cont">
	<ul class="fn-clear m-prosales-list">
	<c:forEach items="${productList }" var="product" varStatus="status">
		<li name="p_item" class="m-prosales-item" >
			<div class="p-img">
				<a title="${product.subTitle}" href="<%=Configuration.getContextProperty("detailPath_B2B")%>${product.skuId}.shtml" hidefocus="true" target="_blank" >
					<c:if test="${product.tags!=null && fn:length(product.tags) > 0 }">
						<b class="ico-sale">${product.tags[0]}</b>
					</c:if>
					<img alt="${product.subTitle}" src="<%=Configuration.getContextProperty("picPath_B2B")%>${product.image}" onerror="this.src='<%=Configuration.getContextProperty("CSS_JS_PATH_B2B")%>images/default__logo_err240_240.jpg'">
				</a>
			</div>
			<div class="p-name">
				<a href="<%=Configuration.getContextProperty("detailPath_B2B")%>${product.skuId}.shtml" target="_blank" >
				<c:if test="${product.prodType == 3 }">
					<strong class="fn-red">[处方药]</strong>
				</c:if>
				${product.title} ${product.attrVals }
				<span class="fn-red fn-l5">${product.subTitle}</span></a>
			</div>
			<div class="p-tag"><del>市场价：￥<fmt:formatNumber type="number" value="${product.mprice }" pattern="0.00" /></del></del></div>
			<div class="p-price">
				<strong>￥<fmt:formatNumber type="number" value="${product.price}" pattern="0.00" /></strong>				
				<%-- <del class="pointsTag">
				 	积分：<fmt:formatNumber type="number" value="${product.skupv }" pattern="0.00" />
				</del> --%>				
			</div>
			<div class="p-tag">
				<c:forEach items="${product.tags }" var="tag" varStatus="tstauts">
					<c:if test="${!tstauts.first }">
						<i class="ico-tag">${tag }</i>
					</c:if>
				</c:forEach>
			</div>
			<div class="p-btns">
				<c:choose>
					<c:when test="${product.prodType == 3 }">
						<a class="btn-red30" href="<%=Configuration.getContextProperty("detailPath_B2B")%>${product.skuId}.shtml">查看详情</a>
<%-- 						<a name="prescript" class="btn-green prescript" style="cursor:pointer;" data-sid='${product.skuId }' href="<%=Configuration.getContextProperty("portalPath_B2B")%>/member/innitMyPresList.action?comeFrom=CMS&saveSku=${product.skuCode }&savePrice=${product.price}&saveNumber=1" >按方抓药</a> --%>
					</c:when>
					<c:otherwise>
						<!-- maliqun add 20150615  begin 如果库存为零,则显示暂时缺货 -->
						<c:choose>
							<c:when test="${product.stock <=0}">
								<a name="outOfStockBtn" class="btn-gray" style="cursor:pointer;" disabled="true" data-sid='${product.stock }' >暂时缺货</a>
							</c:when>
							<c:otherwise>				
								<a name="shopcartBtn" class="btn-green shopcart" style="cursor:pointer;" data-sid='${product.skuId }' >加入购物车</a>
								<div id="flyItem" class="fly_item" data-center="1288,-805" style="left: 335px; top: 998px; visibility: hidden; transform: translate(1288px, -805px);">
									<img src="<%=Configuration.getContextProperty("CSS_JS_PATH_B2B")%>images/item-pic.jpg" width="40" height="40">
								</div>
							</c:otherwise>
						</c:choose>
					   <!-- maliqun add 20150615  end 如果库存为零,则显示暂时缺货 -->
					</c:otherwise>
				</c:choose>
				<a name="favoriteBtn" class="btn-collection fn-l10" href="javascript:void(0)" sid='${product.skuCode }' pri='${product.price }'><strong>+</strong>收藏</a>
			</div>
			<div class="shop-name">
				<c:choose>
					<c:when test="${empty product.shopId}">
						<span>康美自营</span>
					</c:when>
					<c:otherwise>
						<a href="<%=Configuration.getContextProperty("staticPath_B2B")%>/supply/${product.shopId}/index.html" target="_blank" >${product.shopName }</a>
					</c:otherwise>
				</c:choose>
			</div>
		</li>
		<c:choose>
			<c:when test="${status.count % 4 == 0 && !status.last}">
				</ul>
				<ul class="fn-clear m-prosales-list">
			</c:when>
			<c:when test="${status.last}">
				</ul>
			</c:when>
		</c:choose>
	</c:forEach>
</div>