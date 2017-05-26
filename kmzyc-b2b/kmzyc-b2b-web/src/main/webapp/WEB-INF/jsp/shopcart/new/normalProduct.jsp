<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil,java.util.*,com.kmzyc.framework.constants.Constants" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("loginUserId",session.getAttribute(Constants.SESSION_USER_ID));
	String wapCssPath = ConfigurationUtil.getString("CSS_JS_PATH");
	 String  cmsPagePath= ConfigurationUtil.getString("CMS_PAGE_PATH");
  

%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="pord-mk-div">
      <div class="prod-list">
        <div class="pic-box">
        <div class="pic-le">
        <i class="xz-icon"><span class="checkbox" style="background-position: 0px -50px;"></span>
          <input type="checkbox" class="styled" /></i>
         <span><a href="<%=cmsPagePath%>/${id}.html"><img src="<%=wapCssPath%>/images/common/shop-img.jpg" width="67" height="67"></a></span> <font>
          <a href="<%=cmsPagePath%>/${id}.html"><p>${normalMap[id].name}</p></a>
          <c:choose> 
          <c:when test="${normalMap[id].isOutOfStock}">
          <p class="red">每件须加收运费 5.00 元</p>
             </c:when>
          </c:choose>
        </font>
        </div>
        <c:if test="false">
        <div class="zengp">
        <a href="javascript:void(0)"><i class="zp-icon">赠品</i>御宝堂 石斛 120g(10*12袋)</a> <em>× 1</em>
        </div>
        <div class="zengp"> 
        <a href="javascript:void(0)"><i class="zp-icon">赠品</i>御宝堂 石斛 120g(10*12袋)</a> <em>× 1</em>
        </div>
        </c:if>
        </div>
        <div class="spec-box">
        	<c:set var="salePromotion" value="${normalMap[id].productPromotion.pricePromotion}"></c:set>
        	<c:set var="orderPromotionSet" value="${normalMap[id].productPromotion.orderPromtotionSet}"></c:set>
        	<c:if test="${not empty salePromotion}">
         		<a href="javascript:void(0)"><div class="tjia-mk">
         		<i class="tjbq">${salePromotion.typeName}</i>
         		￥<fmt:formatNumber pattern="0.00" value="${normalMap[id].finalPrice}"/></i>
         		</div></a>
         	</c:if>
         	<c:if test="${empty salePromotion}">
         		￥<fmt:formatNumber pattern="0.00" value="${normalMap[id].finalPrice}"/>
         	</c:if>
         	<c:if test="${not empty orderPromotionSet}">
         	<div class="tjia-mk">请选择优惠活动<i class="x-pull bor-no"></i></div>
	         <div class="popup-box popup-two" style="display: none;">
		        <div class="popup-tit-mk">
		        <div class="popup-tit">请选择优惠活动</div>
		        </div>
		        <div class="popup-list">
		        <c:forEach items="${orderPromotionSet}" var="orderPromotion">
		        <p><input name="" type="radio" value="" class="pop-xz"><span>[${orderPromotion.typeName}]</span>&nbsp;${orderPromotion.name}、欲购从速！</p>
		        </c:forEach>
		        <p><input name="" type="radio" value="" class="pop-xz">不使用单品优惠</p>
		        <div class="pop-btn-mk">
		        <a href="javascript:void(0)" class="pop-btn detemin2">确定</a><a href="javascript:void(0)" class="pop-btn">取消</a>
		        </div>
		        </div>
	      	</div></c:if>
          </div>
        <div class="quan-box">
          <div class="quan-mk"> <a href="<%=cmsPagePath%>${id }.html" class="decrement">-</a> <i class="quantity-mk">
            <input type="text" class="quantity-input" value="${normalMap[id].amount}">
          </i> <a href="javascript:void(0)" class="increment">+</a></div>
        </div>
        <div class="total-box">¥<fmt:formatNumber pattern="0.00" value="${normalMap[id].finalPrice*normalMap[id].amount}"/></div>
        <div class="oper-box">
          <p><a href="javascript:void(0)">加入收藏</a></p>
          <p><a href="javascript:void(0)">删除</a></p>
        </div>
      </div>
      </div>
