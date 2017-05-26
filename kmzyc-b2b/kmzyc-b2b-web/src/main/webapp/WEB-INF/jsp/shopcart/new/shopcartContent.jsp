<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil,java.util.*,java.lang.*,com.kmzyc.framework.constants.Constants" pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("loginUserId",session.getAttribute(Constants.SESSION_USER_ID));
	String cmsPagePath= ConfigurationUtil.getString("CMS_PAGE_PATH");
	String picPath = ConfigurationUtil.getString("picPath");
	long randomTime = new Date().getTime();
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <div class="shopcart l-w">
  <div class="shopcart-hd fn-clear">
    <h2 class="shopcart-tit fn-left">我的购物车</h2>
    <%if(null==session.getAttribute(Constants.SESSION_USER_ID)){ %>
    <span class="freight">现在<i>
    <a class="j_displayLogin" href="javascript:;"><input type="button"  class="fre-btn" value="登录"></a>
    </i>，您购物车中的商品将被永久保存</span>
    	<%} %>
    	<input id="isLogin" type="hidden" value="${isLogin}"/>
    </div>    
  <c:if test="${not empty shopList}">   
    <c:forEach items="${shopList.sellerShopSet}" var="kmzyshop">  
    <%-- 商家start--%>
   <div class="shop-list-tit">
    <div class="kmzy"><font>
      <input type="checkbox" noneCheckedIds="${kmzyshop.noneCheckIds}" checkedIds="${kmzyshop.checkIds}" class="styled j_checkShop" />
      </font>
      <font class="xp-tit" title="${kmzyshop.shopName}">${kmzyshop.shopName}</font>
      <c:if test="${not empty fareMap}">
      <i class="arrow-ico1"></i>
      <c:choose>
      <c:when test="${kmzyshop.checkTotalMoney >=fareMap[kmzyshop.sellerId].freePrice}">
            <span>已免运费</span>
      </c:when>
	  <c:when test="${kmzyshop.checkTotalMoney <fareMap[kmzyshop.sellerId].freePrice}" >
      	<span title="运费${fareMap[kmzyshop.sellerId].firstHeavyFreight}元,满 ${fareMap[kmzyshop.sellerId].freePrice}元免运费">运费 ${fareMap[kmzyshop.sellerId].firstHeavyFreight}元,满 ${fareMap[kmzyshop.sellerId].freePrice}元免运费</span> 
      </c:when>
      <c:otherwise><span>已免运费</span></c:otherwise>
      </c:choose>
      </c:if>
      </div>
    <div class="price">单价</div><div class="quantity">数量</div><div class="total">合计</div><div class="operate">操作</div>
  </div>
  <div class="shop-list-box">
     <c:forEach items="${kmzyshop.shopCartItemSet}" var="shopCartItemSet" varStatus="itemstatus">
     <c:set value="${shopCartItemSet}" var="shopcartItem"></c:set>
     <c:set var="pindex" value="0"/>
      <%-- item start--%>
     <div class="pord-mk">
		 <c:if test="${not empty shopCartItemSet.rulePresents}">
     	<%-- 满赠可选商品 --%>
     	<c:if test="${shopcartItem.promtionInfo.type==3}">
	     <div id="itemGift_${shopCartItemSet.id}" style="display: none"  data-ptype = "${shopcartItem.promtionInfo.type}"
	     	  class="pup-box-new" data="${shopCartItemSet.id}" tabindex="-1" data-widget-cid="widget-20" >
	     	 <a class="ui-dialog-close j_showGiftUi" data="${shopCartItemSet.id}"  title="关闭本框" href="javascript:;" data-role="close">×</a>
	    	<div class="ui-dialog-content" data-role="content" style="height: 100%; zoom: 1;">
	        <div class="ui-dialog-title" data-role="title">选择赠品</div>
	        <div class="ui-dialog-container">
	        <div class="pur-shop-box">
	        	<c:forEach items="${shopcartItem.rulePresents}" var="rulePresent" varStatus="status">
	        		<c:set value="${rulePresent.value}" var="prensentList"></c:set>
	        		<c:set value="${fn:length(rulePresent.value)}" var="prensentListLength"></c:set>
	        			<c:set var="flag" value="0"></c:set>
		        		<c:forEach items="${prensentList}" var="gift">
			        		<c:if test="${prensentListLength<=1 && flag ne 1}">
					        	<div class="pu-sh-list">
						        <span><input myid="${shopCartItemSet.id}_${rulePresent.key}" name="itme_${shopCartItemSet.id}"
						        data-presents="${gift.meetData}"
						        type="radio" value="${gift.id}" data-gifttempid="${gift.id}_${ status.index}"
						        num="${gift.amount}" maxnum="${gift.maxAmount}" data-item="${shopCartItemSet.id}"></span>
						        <i class="sh-pic" name="kh_giftstock_${gift.id}_${status.index }" data-kh-giftid=${gift.id } data-kh-giftstock=${empty giftStockMap[gift.id] or giftStockMap[gift.id]<gift.amount ? '0' : '1'}><a target="_blank" href="<%=cmsPagePath%>${gift.id}.html"><img
						        onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"
						        src="<%=picPath%>${fn:replace(gift.img, '.jpg', '_7.jpg')}" width="67" height="67"></a></i>
						        <font>
						        <a target="_blank" href="<%=cmsPagePath%>${gift.id}.html"><p>${gift.name}</p></a>

						        <p class="sh-jg"></p>

						        <c:if test="${empty giftStockMap[gift.id] or  giftStockMap[gift.id] < gift.amount}">
					        		<em style="color: red">已送完</em>
					        	</c:if>
						        </font>

						        <em class="sh-sl">x${gift.amount}</em>
						        </div>
						        <c:set var="flag" value="1"></c:set>
							</c:if>

					        <c:if test="${prensentListLength>1 && flag ne 1}">
					        	<div class="pu-sh-list">
						        <span><input myid="${shopCartItemSet.id}_${rulePresent.key}"
						        data-presents="${gift.meetData}" name="itme_${shopCartItemSet.id}" type="radio" value="${gift.id}"
						        num="${gift.amount}" maxnum="${gift.maxAmount}" data-item="${shopCartItemSet.id}" data-gifttempid="${gift.id}_${status.index}"></span>
						        <em class="xp-ri">
							        <c:forEach items="${prensentList}" var="prensent">
							        	<i class="sh-pic2" name="kh_giftstock_${gift.id}_${status.index}" data-kh-giftid=${prensent.id } data-kh-giftstock=${empty giftStockMap[prensent.id] or giftStockMap[prensent.id]<gift.amount ? '0' : '1'}>
							        		<a target="_blank" href="<%=cmsPagePath%>${prensent.id}.html">
									        	<img onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"
									        	src="<%=picPath%>${fn:replace(prensent.img, '.jpg', '_7.jpg')}" width="67" height="67" title="${prensent.name }">
								        	</a>

								        	<c:if test="${empty giftStockMap[prensent.id] or  giftStockMap[prensent.id]<gift.amount}">
								        		<em style="color: red">已送完</em>
								        	</c:if>

							        		<i class="sz-sl2">x${prensent.amount}</i>
							        	</i>
							       	</c:forEach>
						       	</em>
						        </div>
						        <c:set var="flag" value="1"></c:set>
					        </c:if>
				        </c:forEach>


	        	</c:forEach>
	        </div>
	        <div class="pur-shop-btn">
	        <a href="javascript:;" data-item="${shopCartItemSet.id}" data-org="${shopCartItemSet.presents}" class="cor-red j_submitPresent">确定</a><a href="javascript:;" data="${shopCartItemSet.id}" class="j_showGiftUi">取消</a>
	        </div>
	        </div>
	    	</div>
		</div>
		</c:if>
		</c:if>
		<c:if test="${not empty shopCartItemSet.ruleGifts}">
		<%-- 加价购可选商品--%>
		<c:if test="${shopcartItem.promtionInfo.type==5}">
	     <div id="itemGift_${shopCartItemSet.id}" style="display: none" data-ptype = "${shopcartItem.promtionInfo.type}"
	     	  class="pup-box-new exchange" data="${shopCartItemSet.id}" tabindex="-1" data-widget-cid="widget-20">
	     	 <a class="ui-dialog-close j_showGiftUi" data="${shopCartItemSet.id}"  title="关闭本框" href="javascript:;" data-role="close">×</a>
	    	<div class="ui-dialog-content" data-role="content" style="height: 100%; zoom: 1;">
	        <div class="ui-dialog-title" data-role="title">选择换购，最多可换购<label id="label_${shopCartItemSet.id}">${shopcartItem.giftCount}</label>件
	        ，已选${empty shopcartItem.countChoosed?"0":shopcartItem.countChoosed}件</div>
	        <div class="ui-dialog-container">
	        <div class="pur-shop-box">
	        	<c:forEach items="${shopcartItem.ruleGifts}" var="gift">
		        	<div class="pu-sh-list">
			        <span><input myid="${shopCartItemSet.id}_${gift.key}" name="itme_${shopCartItemSet.id}" type="checkbox"
			        data-stock="${empty giftStockMap[gift.value.id]?0:giftStockMap[gift.value.id]}" data-count="${gift.value.amount}"
			        value="${gift.key}" num="${gift.value.amount}" data-org="${gift.value.amount}"
			        ${(empty giftStockMap[gift.value.id] or  giftStockMap[gift.value.id]<1) ? 'disabled="disabled"':''}
			        maxnum="${shopcartItem.giftCount}" data-item="${shopCartItemSet.id}" class="j_checkGift"></span>
			        <i class="sh-pic"><a target="_blank" href="<%=cmsPagePath%>${gift.value.id}.html"><img
			        onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"
			        src="<%=picPath%>${fn:replace(gift.value.img, '.jpg', '_7.jpg')}" width="67" height="67"></a></i>
			        <font class="fo-mk">
			        <a target="_blank" href="<%=cmsPagePath%>${gift.value.id}.html"><p>${gift.value.name}</p></a>
			        <div class="sh-jg">
		              <div class="quan-mk pf-sz"> <a href="javascript:;" class="decrement ${(empty giftStockMap[gift.value.id] or  giftStockMap[gift.value.id]<1) ? '':'j_decrementGiftCount'}" >-</a>
		              <i class="quantity-mk"><input type="text" myid="inputNum_${shopCartItemSet.id}_${gift.key}"
		               data-org="${gift.value.amount}" data-max="${shopcartItem.giftCount}" class="quantity-input j_giftProductInput" value="1"
		               data-stock="${empty giftStockMap[gift.value.id]?0:giftStockMap[gift.value.id]}"
		               checked-myid="${shopCartItemSet.id}_${gift.key}" data-giftcount="${shopcartItem.giftCount}"
		               ${(empty giftStockMap[gift.value.id] or  giftStockMap[gift.value.id]<1)  ? 'disabled="disabled"':''}>
		              </i> <a href="javascript:void(0);" data-item="${shopCartItemSet.id}" class="increment ${(empty giftStockMap[gift.value.id] or  giftStockMap[gift.value.id]<1) ? '':'j_incrementGiftCount'}">+</a></div>

		              ¥<fmt:formatNumber pattern="0.00" value="${gift.value.price}"/> </div>
		              <c:if test="${empty giftStockMap[gift.value.id] or  giftStockMap[gift.value.id]<1}">
		        		<font style="color: red">无库存</font>
		        		</c:if>
			        </font> </div>
	        	</c:forEach>
	        </div>
	        <div class="pur-shop-btn">
	        <a href="javascript:;" data-item="${shopCartItemSet.id}" value class="cor-red j_submitGift">确定</a><a href="javascript:;" data="${shopCartItemSet.id}" class="j_showGiftUi">取消</a>
	        </div>
	        </div>
	    	</div>
		</div>
		</c:if>
	</c:if>
      <c:if test="${not empty shopCartItemSet.promtionInfo}">
      <div class="pool"><span>¥<fmt:formatNumber pattern="0.00" value="${shopcartItem.checkTotalMoney}"/></span>
      <i class="${shopcartItem.meet?'manj mj-icon2':'manj'}"> ${shopcartItem.promtionInfo.typeName}</i>
      <strong title="${shopcartItem.promtionInfo.name}">${fn:substring(shopcartItem.promtionInfo.name,0,20)}</strong>&nbsp;&nbsp;
      <c:if test="${shopcartItem.meet}">
      	<i class="jman">${shopcartItem.describe}</i>
          <c:if test="${not empty shopCartItemSet.tag}">
	      <a href="javascript:;" data="${shopCartItemSet.id}" show="true" class="go-pool j_showGiftUi">${shopcartItem.tag}></a>
	      </c:if>
      </c:if>
      </div>
      </c:if>
      <%--已选加价购商品 giftStockMap--%>
      <c:if test="${not empty shopCartItemSet.gifts}">

         <c:forEach items="${shopcartItem.gifts}" var="gift" varStatus="giftstatus">
         <c:set var="pindex" value="${pindex+1}"/>
         <div  class="pord-mk-div" id="product_div_${gift.id}_${itemstatus.index}_${giftstatus.index}" tabindex="-1" style="outline : none;">
         <div class="prod-list"  value="${shopCartItemSet.id}_${gift.dataId}" giftId="${gift.id}" num="${gift.amount}" mytype="gift_${shopCartItemSet.id}" >
	       	<div class="pic-box">
			<i class="xz-icon gray"></i> <span><a target="_blank" href="<%=cmsPagePath%>${gift.id}.html"><img
	        onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"
	        src="<%=picPath%>${fn:replace(gift.img, '.jpg', '_7.jpg')}" width="67" height="67" /></a></span> <font>
	         <a target="_blank" href="<%=cmsPagePath%>${gift.id}.html"> <p>[换购]&nbsp;${gift.name}
	         </p></a>
	         <c:choose>
	         	<c:when test="${empty giftStockMap[gift.id] or giftStockMap[gift.id]<1}">
	         		<p data-shllerId="${kmzyshop.sellerId }" data-cid="${gift.id}_${itemstatus.index}_${giftstatus.index}" data-valid="error" class="red">无库存,请删除</p>
	         	</c:when>
	         	<c:when test="${giftStockMap[gift.id]<gift.amount}">
	         		<p data-shllerId="${kmzyshop.sellerId }" data-cid="${gift.id}_${itemstatus.index}_${giftstatus.index}" data-valid="error" class="red">库存不足,请删除</p>
	         	</c:when>
	         </c:choose>
	        </font></div>
	        <div class="spec-box">¥<fmt:formatNumber pattern="0.00" value="${gift.price}"/></div>
	        <div class="quan-box"> ${gift.amount}</div>
	        <div class="total-box">¥<fmt:formatNumber pattern="0.00" value="${gift.price * gift.amount}"/></div>
	        <div class="oper-box">
	          <p><a href="javascript:void(0);"  class="j_deleteGift" data-itemId="${shopCartItemSet.id}" data-productId="${gift.dataId}" >删除</a></p>
	        </div>
	      </div>
	      </div>
      	</c:forEach>

      	</c:if>
   	<%--已选赠品 giftStockMap--%>
   	<c:if test="${not empty shopCartItemSet.defaultPresents}">
   	<c:set value="${shopCartItemSet.rulePresents[shopCartItemSet.defaultPresents]}" var="giftList"/>
      <div  class="pord-mk-div">
      <c:forEach items="${giftList}" var="gift">
		<c:set var="pindex" value="${pindex+1}"/>
		<div class="prod-list" value="${shopCartItemSet.id}_${shopCartItemSet.defaultPresents}" giftId="${gift.id}" num="${gift.amount}" mytype="gift_${shopCartItemSet.id}" >
			<div class="pic-box"> <i class="xz-icon gray"></i> <span><a target="_blank" href="<%=cmsPagePath%>${gift.id}.html"><img
		      onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"
		      src="<%=picPath%>${fn:replace(gift.img, '.jpg', '_7.jpg')}" width="67" height="67" /></a></span> <font>
		       <a target="_blank" href="<%=cmsPagePath%>${gift.id}.html"> <p>[赠品]&nbsp;${gift.name}
		       </p></a>
		        <c:if test="${empty giftStockMap[gift.id] or giftStockMap[gift.id]<gift.amount}">
		        	<p class="red">已送完</p>
		        </c:if>
		      </font></div>
		      <div class="spec-box">¥<fmt:formatNumber pattern="0.00" value="${gift.price}"/></div>
		      <div class="quan-box"> ${gift.amount}</div>
		      <div class="total-box">¥<fmt:formatNumber pattern="0.00" value="${gift.price * gift.amount}"/></div>
		      <div class="oper-box">
	        <p><a href="javascript:void(0);"  class="j_deleteGift" data-itemId="${shopCartItemSet.id}" data-productId="${gift.id}" ></a></p>
	      	</div>
	    </div>
   	</c:forEach>
   	</div>
   	</c:if>
        <c:forEach items="${shopcartItem.carProductSet}" var="cartProduct" varStatus="productstatus">
        <%-- product start--%>
        <c:set value="${cartProduct.pid}"  var="key"/>
        <c:set value="${normalMap[key]}"  var="product"/>
        <c:set value="${productErrorReminder[key]}"  var="productReminder"/>
        <c:set value="${empty productReminder or productReminder.normal}" var="normal"/>
        <c:set var="pindex" value="${pindex+1}"/>
        <%--taocan  --%>
   		<c:if test="${fn:startsWith(key, 'c_')}">
      <div class="pord-mk-div"  id="product_div_${product.id}_${itemstatus.index}_${productstatus.index}" tabindex="-1" style="outline : none;">
        <div class="prod-list">${pindex>0?'<div class="prod-xt"></div>':''}
        <div class="pic-box">
			<i class="xz-icon htop">
        		<input type="checkbox"  class="j_childProduct" id="product_input_${product.id}" ${normal?'':'disabled="disabled"'} value="${key}"  name="comipition_${product.id}"
        		<c:if test="${product.check}">checked='checked'</c:if>/>
			</i>
			<em class="packages">套餐</em><strong>${product.name}</strong>
		  	<p class="red" >积分: <fmt:formatNumber pattern="0.00" value="${product.pvalue*product.amount}"/></p>
        <c:if test="${not empty productReminder}"><em data-shllerId="${kmzyshop.sellerId }" ${product.check ? 'data-valid="error"' : ''} data-cid="${product.id}_${itemstatus.index}_${productstatus.index}"  class="tc-prompt">${productReminder.message}</em></c:if>
        </div>
        <div class="spec-box"><div class="jiag-mk">¥ <fmt:formatNumber pattern="0.00" value="${product.finalPrice}"/></div>
        </div>
        <div class="quan-box">
          <div class="quan-mk"> <a href="javascript:;" class="decrement ${normal?'j_reduceProduct':''}"
          data-min="${product.min}:${product.minCode}" data-key="${key}" data-cid="${product.id}" >-</a> <i class="quantity-mk">
            <input type="text" maxlength="4" class="quantity-input j_textProduct" value="${product.amount}"
            data-org="${product.amount}" data-min="${product.min}:${product.minCode}" data-max="${product.max}:${product.maxCode}"
              id="productInput_${key}" data-key="${key}" data-cid="${product.id}"  />
          </i> <a href="javascript:;" class="increment ${normal?'j_addProduct':''}" data-max="${product.max}:${product.maxCode}" data-key="${key}" data-cid="${product.id}">+</a></div>
        </div>
        <div class="total-box">¥<fmt:formatNumber pattern="0.00" value="${product.finalPrice*product.amount}"/></div>
             <div class="oper-box">
           <p><a href="javascript:void(0);"  class="j_deleteProductShopCart" data="${key}" >删除</a></p>
        </div>
      </div>
       <%--taocanchanpin --%>
      <c:forEach items="${product.productList}" var="productList">
      <div class="prod-list">${pindex>0?'<div class="prod-xt"></div>':''}
        <div class="pic-box"> <i class="xz-icon gray"></i> <span><a target="_blank" href="<%=cmsPagePath%>${productList.productSkuId}.html"><img
        onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"
        src="<%=picPath%>${fn:replace(productList.imagePath, '.jpg', '_7.jpg')}" width="67" height="67" /></a></span> <font>
         <a target="_blank" href="<%=cmsPagePath%>${productList.productSkuId}.html"> <p>  ${productList.title} &nbsp;&nbsp;${productList.skuAttrValue}
         </p></a>
        </font></div>
        <div class="spec-box"><div class="jiag-mk"></div></div>
        <div class="quan-box">${productList.amount*product.amount}</div>
        <div class="total-box"></div>
        <div class="oper-box">
          <p>&nbsp;</p>
        </div>
      </div>
      </c:forEach>
    </div>
   </c:if>
    <%-- danpin --%>
   <c:if test="${fn:startsWith(key, 'n_')}">
    <div class="pord-mk-div" id="product_div_${product.id}_${itemstatus.index}_${productstatus.index}" tabindex="-1" style="outline : none;">
      <div class="prod-list" >${pindex>1?'<div class="prod-xt"></div>':''}
        <div class="pic-box">
        <div class="pic-le">
        <i class="xz-icon" ${key}>
          <input type="checkbox" class="j_childProduct" id="product_input_${product.id}" value="${key}"  ${normal?'':'disabled="disabled"'}
          name="product_${key}" <c:if test="${product.check}">checked='checked'</c:if>/></i>
         <span><a target="_blank" href="<%=cmsPagePath%>${product.id}.html"><img
         onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"
         src="<%=picPath%>${fn:replace(product.imagePath, '.jpg', '_7.jpg')}" width="67" height="67"  clstag="clickcart|keycount|xincart|p-imglistcart"></a></span> <font>
          <a ruleInfo="" target="_blank" href="<%=cmsPagePath%>${product.id}.html"><p>${product.title}</p></a>
          <c:if test="${product.freight>0}">
          	<p class="red"><c:if test="${product.amount <= 1 }">每件</c:if>须加收运费
          		<fmt:formatNumber pattern="0.00" value="${product.freight * product.amount}"/>元</p>
          </c:if>

		  <%-- <p class="red">积分: <fmt:formatNumber pattern="0.00" value="${product.pvalue*product.amount}"/></p> --%>

          <c:if test="${not empty productReminder}">
          	<p data-shllerId="${kmzyshop.sellerId }" ${product.check ? 'data-valid="error"' : ''} data-cid="${product.id}_${itemstatus.index}_${productstatus.index}" class="red">${productReminder.message}</p>
          </c:if>
        </font>
        </div>
         <%-- 固定层级 活动--%>
        <c:if test="${not empty product.productPromotion.affixPromoiton and not empty product.productPromotion.affixProductList}">

			<c:forEach items="${product.productPromotion.affixProductList}" var="affixProductList">
       			<div class="zengp">
       				<i>【附赠】</i>
       				<a class="zp-name" target="_blank" href="<%=cmsPagePath%>${affixProductList.productSkuId}.html" title="${affixProductList.productTitle }&nbsp;${affixProductList.skuAttrValue }">
       					${affixProductList.productTitle }&nbsp;${affixProductList.skuAttrValue }
       				</a>
       				<em>× ${affixProductList.num * product.amount}</em>
       				<c:choose>
       					<c:when test="${empty giftStockMap[fn:trim(affixProductList.productSkuId)] or giftStockMap[fn:trim(affixProductList.productSkuId)] == 0}">
       						<em class="red">&nbsp;&nbsp;已送完</em>
       					</c:when>
       					<c:when test="${giftStockMap[fn:trim(affixProductList.productSkuId)] < affixProductList.num * product.amount }">
       						<em class="red">&nbsp;&nbsp;库存不足</em>
       					</c:when>
       				</c:choose>
       			</div>
       		</c:forEach>
        </c:if>
        </div>
        <%-- 活动 --%>
        <div class="spec-box">
        	<c:set var="salePromotion" value="${product.productPromotion.pricePromotion}"></c:set>
        	<c:set var="orderPromotionSet" value="${product.productPromotion.sortedOrderPromtotions}"></c:set>
        	<c:if test="${not empty salePromotion}">
         		<div class="tjia-mk">
         		<i class="tjbq">${salePromotion.typeName}</i>
         		¥<fmt:formatNumber pattern="0.00" value="${product.finalPrice}"/>
         		</div>
         	</c:if>
         	<c:if test="${empty salePromotion}">
         		<div class="jiag-mk">¥<fmt:formatNumber pattern="0.00" value="${product.finalPrice}"/></div>
         	</c:if>
         	<c:if test="${not empty orderPromotionSet}">
         	<div class="tjia-mk j_showPromotionInfo" data="${shopcartItem.promtionInfo.id}">选择优惠<i class="x-pull bor-no"></i></div>
	         <div id="promotion_box_${key}" class="popup-box popup-two" style="display: none;">
		        <div data="${key}" class="popup-tit-mk j_hiddenPromotionInfo">
		        <div class="popup-tit">请选择优惠活动</div>
		        </div>
		        <div class="popup-list">
		        <c:forEach items="${orderPromotionSet}" varStatus="status" var="orderPromotion" >
		        <p><input name="promotion_box_${key}" type="radio" value="${orderPromotion.id}" class="pop-xz"><span>[${orderPromotion.typeName}]</span>&nbsp;${orderPromotion.name} </p>
		        </c:forEach>
		        <p><input name="promotion_box_${key}" type="radio" value="0" class="pop-xz">不使用优惠</p>
		        <div class="pop-btn-mk">
		        <a href="javascript:;" data="${key}" org_data="${key},${product.productPromotion.defaultOrderPromtotion.id}" class="pop-btn detemin2 j_selectPromotionInfo">确定</a>
		        <a href="javascript:;" data="${key}" class="pop-btn j_hiddenPromotionInfo">取消</a>
		        </div>
		        </div>
	      	</div></c:if>
          </div>
        <div class="quan-box">
          <div class="quan-mk"> <a href="javascript:void(0);" class="decrement  ${normal?'j_reduceProduct':''}"
          data-min="${product.min}:${product.minCode}" data-key="${key}" data-cpid="${product.id}"   data-value2="-1" >-</a>
		  <i class="quantity-mk">
          <input type="text" ${normal?'':'disabled="disabled"'} maxlength="4" class="quantity-input j_textProduct" data-key="${key}" data-org="${product.amount}"
              data-min="${product.min}:${product.minCode}" data-max="${product.max}:${product.maxCode}" id="productInput_${key}"
               value="${product.amount}"  data-cpid="${product.id}"  >
          </i> <a href="javascript:void(0);" class="increment ${normal?'j_addProduct':''}" data-key="${key}"
          data-max="${product.max}:${product.maxCode}"
          data-cpid="${product.id}"  >+</a></div>
        </div>
        <div class="total-box">¥<fmt:formatNumber pattern="0.00" value="${product.finalPrice*product.amount}"/></div>
        <div class="oper-box">
          <p><a href="javascript:void(0);" class="j_addFavorite"  data-finalPrice="${product.finalPrice}"  data-cpid="${product.productSkuCode}"  >加入收藏</a></p>
          <p><a href="javascript:void(0);" class="j_deleteProductShopCart" data="${key}" >删除</a></p>
        </div>
      </div>
      </div>
    </c:if>
    <%-- product end--%>
     </c:forEach>
     </div>
      <%-- item end--%>
     </c:forEach>
   <%-- 商家小计start --%>
  	<div class="to-total-box">
    <div class="to-total-mk">
    <p><i>已选择<span>${kmzyshop.checkedProductCount}</span>件商品</i>总　计：</p>
    <c:if test="${kmzyshop.additionalMoney>0}"><p>加价购：</p></c:if>
    <c:if test="${kmzyshop.reductionMoney>0}"><p>满　减</p></c:if>
    </div>
    <div class="oper-box poer-right">
    <p>+ ¥<fmt:formatNumber pattern="0.00" value="${kmzyshop.uncalculateMoney}"/></p>
    <c:if test="${kmzyshop.additionalMoney>0}"><p>+ <span>¥<fmt:formatNumber pattern="0.00" value="${kmzyshop.additionalMoney}"/></span></p></c:if>
    <c:if test="${kmzyshop.reductionMoney>0}"><p>- <span>¥<fmt:formatNumber pattern="0.00" value="${kmzyshop.reductionMoney}"/></span></p></c:if>
    </div>
    </div>
    <div class="total-foot">
    <div class="to-foot-le"><%--<input name="" type="checkbox" value="" class="select-icon">全选<i class="delete"></i><a href="javascript:;">删除选中的商品</a><i class="go-shop"></i><a href="javascript:;">继续购物</a>--%>&nbsp;</div>
    <div class="to-foot-ri">商品总价（不含运费）: <span>¥<fmt:formatNumber pattern="0.00" 
    value="${kmzyshop.checkTotalMoney}"/></span></div>
      <div class="to-foot-ribtn"><a href="javascript:;"  data-check="${kmzyshop.checkIds}" data-settlementflag = "false" id="gotoSettlement_${kmzyshop.sellerId }" data-shopid="${kmzyshop.sellerId }" class="go-count j_gotoSettlement"></a></div>
    </div>
     <%-- 商家小计end--%>
  </div>
     <%-- 商家end--%>
    </c:forEach>
  </c:if>
  <div class="cart-total-foot">
    <div class="total-foot pf-to">
      <div class="to-foot-le">
          <input type="checkbox" class="styled j_checkAll"   />
        全选<i class="delete"></i><a href="javascript:void(0);" data="${shopcart.checkedProductCount}" class="deleteAllChecked">删除选中的商品</a><i class="go-shop"></i><a target="_blank" href="<%=ConfigurationUtil.getString("staticPath")%>">继续购物</a> </div>
      <div class="to-foot-ri  cart-foot-fl">商品总价（不含运费）: <span>¥<fmt:formatNumber pattern="0.00" value="${shopcart.checkTotalMoney}"/></span></div>
    </div>
    </div>
</div>
<form id="gotoSettlementForm" action="/settlement/gotoSettlement.action?_r=<%=randomTime%>" method="post">
	<input name="ids" value="" type="hidden"/>
</form>

