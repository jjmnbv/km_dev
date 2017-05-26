<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	pageContext.setAttribute("cssJsPath",ConfigurationUtil.getString("CSS_JS_PATH"));
	pageContext.setAttribute("wapstatic",ConfigurationUtil.getString("productPicPath_WAP"));
	pageContext.setAttribute("wapPath",ConfigurationUtil.getString("staticPath_WAP"));
	pageContext.setAttribute("productImgPath",ConfigurationUtil.getString("PRODUCT_IMG_PATH"));
%>
<div>
	<c:forEach items="${relationList}" var="relation">
	<section class="d-title">
  	<div>
  		<a href="javascript:void(0);" class="hd_combine" data-value="${relation.relationId }">
       	<div class="text fn-left">
           	<h2>${relation.relationName}</h2>
           	<span>${relation.relationIntroduce}</span>
       	</div>
       	</a>
       	<div class="fn-right"><a href="javascript:void(0);"><button class="btn btn-success cb_easyBuy" data-value="${relation.relationId }">一键购买</button></a></div>
 	</div>
	</section>
	<section class="lst">
   	<ul class="tabs-lst" data-value="${relation.relationId }" id="combine_${relation.relationId }">
   		<c:forEach items="${relation.prdList}" var="detail">
		<li class="checkbox">
			<label>
				<div class="check-wrapper">
			    	<input type="checkbox" <c:if test="${detail.stock < 1 || detail.status != 3}">disabled="disabled" </c:if> class="icon-checkbox icon-checkbox2 input_detail cb_easyBuy_${relation.relationId }" value="${detail.relationSkuId}" data-value="${detail.productCount }" data-value1="${detail.relationSkuPrice }" data-value2="${detail.marketPrice }" data-value3="${relation.relationId }" data-value4="${detail.productTile }" />
			    </div>
			    <div class="list-descriptions">
			 		<div class="list-thumb"><a target="_blank" href="${wapPath}/products/${detail.relationSkuId}.html"><img onerror='this.src="${cssJsPath}images/default__logo_err60_60.jpg"' src="${productImgPath}${detail.imagePath6}" /></a></div>
			  		<div class="list-descriptions-wrapper">
						<div class="product-name cart-name"><a target="_blank" href="${wapPath}/products/${detail.relationSkuId}.html">${detail.productTile}</a></div>
			          	<div class="clearfix price-mk">
							<p class="text-danger jg-wz"><fmt:formatNumber value="${detail.relationSkuPrice }" type="number" pattern="￥0.00" /></p>
			              	<c:choose>
		             		<c:when test="${detail.stock < 1}">
		        			<p class="text-danger jg-wz">库存不足，无法购买</p>
		             		</c:when>
		             		<c:when test="${detail.status != 3}">
		             		<p class="text-danger jg-wz">已下架，无法购买</p>
		             		</c:when>
		             		</c:choose>
			  			</div>
			   		</div>
				</div> 
			</label>           
			<label>
       </li>
       </c:forEach>
   	</ul>  
 	</section>
 	<section class="d-title show_combine" data-value="${relation.relationId }" id="collapse_${relation.relationId }" style="display: none;">
		<div class="package-box col-lg-9">
      		<ul>
      		<c:forEach items="${relation.prdList}" var="detail">
          		<li>
          			<div class="list-descriptions">
          				<div class="list-thumb">
          					<a href="javascript:void(0);"><img onerror='this.src="${cssJsPath}images/default__logo_err60_60.jpg"' src="${productImgPath}${detail.imagePath6}" /></a>
          				</div>
          			</div>
          		</li>
          	</c:forEach>	
      		</ul>
 		</div>
 		<div class="col-lg-3"><a href="javascript:void(0);" data-value="${relation.relationId }" class="icon-uniE61D"></a></div>
 	</section>
	<section class="d-title">    
	<div class="fn-right"> 
		<a href="${wapPath}/suit/${relation.relationId }.html"><button class="btn btn-success">组合详情</button></a>
		<c:if test="${not empty relation.relationVideo}">
 		<a href="${relation.relationVideo}"  target="_blank"><button class="btn btn-success">做法视频</button></a>
 		</c:if>
	</div>
	</section>
	</c:forEach>
	<div class="cart-btns-fixed">
		<div class="col-lg-8 fn-left btn-success">
        	<p>组方价：¥<font id="totalPrice">0.00</font></p>
   			<span>市场总价:¥<font id="marketPrice">0.00</font></span>
		</div>
		<div class="col-lg-4 fn-right btn-warning"><a href="javascript:void(0);" id="add_shopcar">加入购物车</a></div> 
	</div>
</div>
