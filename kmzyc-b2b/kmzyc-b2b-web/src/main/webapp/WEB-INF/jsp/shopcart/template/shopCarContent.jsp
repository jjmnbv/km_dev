<%@ page language="java" import="java.util.*,com.kmzyc.framework.constants.Constants" pageEncoding="UTF-8"%>
<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("loginUserId",session.getAttribute(Constants.SESSION_USER_ID));
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="shopcart l-w">
	<div class="shopcart-hd fn-clear">
		<h2 class="shopcart-tit fn-left">我的购物车</h2>
		<%if(null==session.getAttribute(Constants.SESSION_USER_ID)){ %>
		<div class="fn-left fn-l10 fn-t5">现在  <a href="javascript:void(0);" id="j_login" class="btn-gray">登 录</a> ， 您购物车中的商品将被永久保存</div>
		<%} %>
	</div>
	<div class="shopcart-bd">
		<div class="shopcart-inner">
			<c:if test="${not empty kmProductMap }">
   			<div class="shopcart-inner-box" id="j_shopIndex1">
   				<c:set var="kmShopFareFlag" value="1"/>
   				<c:forEach items="${kmProductMap}" var="kmshop">
     			<div class="shopcart-head">
          			<span class="t-goods">
                  		<div class="headtit">
                        	<input type="checkbox" class="ck_check_shop_all" value="${kmshop.value.sellerId }" id="ck_check_all_${kmshop.value.sellerId }" data-value1="Y" data-value2="1"/>
                    		<a href="javascript:void(0);">${kmshop.value.sellerInfo.shopName }</a>
                    		<c:set var="priceInfo" value="${sellerPriceMap[1] }" />
                    		<c:if test="${1==kmShopFareFlag && not empty priceInfo }">
                    			<c:set var="kmShopFareFlag" value="2" />
                    			<c:set var="fareInfo" value="${priceInfo.freeFare}" />
                    			<c:if test="${not empty fareInfo }">
                    			<c:choose>
                    				<c:when test="${ priceInfo.payMoney >= fareInfo.freePrice }">
                   			<div class="headlabel"><i></i>运费：<strong>免邮</strong></div>
                    				</c:when>
                    				<c:otherwise>
                    		<div class="headlabel"><i></i>运费<strong>${fareInfo.firstHeavyFreight}</strong>元,满<strong>${fareInfo.freePrice}</strong>元免运费</div>	
                    				</c:otherwise>
                    			</c:choose>
                    			</c:if>
                   			</c:if>
                   		</div>
             		</span>
          			<span class="t-unitprice">单价</span>
             		<span>数量</span>
                  	<span>合计</span>
               		<span>操作</span>
           		</div>
      			<div class="shopcart-tbody">
      				<c:if test="${not empty kmshop.value.carProducts}">
					<c:forEach items="${kmshop.value.carProducts}" var="carProduct">
                 	<div class="item item-selected fn-clear" id="productdiv_${carProduct.value.productSkuId}">
               			<div class="item-form">
                     		<div class="cell c-checkbox">
                         		<input type="checkbox" value="${carProduct.value.productSkuId }" data-value1="${kmshop.value.sellerId }"  data-value2="1" name="productcheckbox_${carProduct.value.productSkuId}" id="productcheckbox_${carProduct.value.productSkuId}"  <c:if test="${carProduct.value.isChecked}">checked='checked'</c:if> class="checkbox j_childChange productCheckBox" />
                          	</div>
                     		<div class="cell c-goods">
                        		<div class="p-img">
                        			<a href="${cmsPagePath}${carProduct.value.productSkuId }.html" target="_blank">
	                       				<img onerror='this.src="${cssJsPath}images/default__logo_err60_60.jpg"' width="65" height="65" clstag="clickcart|keycount|xincart|p-imglistcart"
											src="${carProduct.value.imagePath7}" alt="${carProduct.value.title}">
									</a>
                        		</div>
                    			<div class="p-name">
                                   	<a href="${cmsPagePath}${carProduct.value.productSkuId }.html" data="${carProduct.value.productSkuId }"  target="_blank">${carProduct.value.title}</a>
	                   				<c:choose>
	                   					<c:when test="${carProduct.value.isChecked && not empty retypeSellerId && null!=retypeSellerId[kmshop.value.sellerId]}">
	                   					<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该产品已发生变化，请删除该商品后再添加。</p>
	                   					</c:when>
										<c:when test="${carProduct.value.isChecked && empty loginUserId && carProduct.value.maxBuy>0}">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">游客不能参加该产品的限购活动</p>
										</c:when>
										<c:when test="${carProduct.value.isChecked &&carProduct.value.isOutOfStock}">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该商品已下架,请删除该商品</p>
										</c:when>
										<c:when test="${carProduct.value.isChecked &&carProduct.value.stockCount==0}">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该商品无库存,请删除该商品</p>
										</c:when>
										<c:when test="${carProduct.value.isChecked && carProduct.value.stockCount>0&&carProduct.value.stockCount<carProduct.value.amount}">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该商品库存不足,请修改订购数量</p>
										</c:when>
										<c:when test="${carProduct.value.isChecked &&carProduct.value.minBuy>0&&carProduct.value.minBuy>carProduct.value.amount}">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该商品${carProduct.value.minBuy}件起售,请修改订购数量</p>
										</c:when>
										<c:when test="${carProduct.value.isChecked &&carProduct.value.promotionStock>0&&carProduct.value.promotionStock-carProduct.value.promotionSell<carProduct.value.amount}">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该商品活动期间库存不足,请修改订购数量</p>
										</c:when>
										<c:when test="${carProduct.value.isChecked &&carProduct.value.maxBuy>0&&carProduct.value.promoBuyNum<carProduct.value.amount}">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该商品活动期间限购${carProduct.value.maxBuy}件<c:if test="${carProduct.value.maxBuy-carProduct.value.promoBuyNum >0}">,已购买${carProduct.value.maxBuy-carProduct.value.promoBuyNum}件</c:if>,请修改订购数量</p>
										</c:when>
										<c:when test="${carProduct.value.isChecked &&3==carProduct.value.productType }">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该商品为处方药，请到我的药方进行购买</p>	
										</c:when>
									</c:choose>
									<c:if test="${carProduct.value.isChecked && 0==carProduct.value.freeStatus&&0!=carProduct.value.freight}">
										<p class="fn-red">加收运费${carProduct.value.freight*carProduct.value.amount }</p>
									</c:if>
                         		</div>
                         	</div>
                       		<div class="cell c-unitprice">
                       			<c:choose>
                       			<c:when test="${not empty carProduct.value.salePromotionInfo || not empty carProduct.value.discountPromotionInfo }">
                       			<c:if test="${not empty carProduct.value.salePromotionInfo}">
	                    		<span class="btn-red30">特价 ：¥<fmt:formatNumber pattern="0.00" value="${carProduct.value.finalPrice}"/></span>
	                       		</c:if>
								<c:if test="${not empty carProduct.value.discountPromotionInfo}">
								<span class="btn-red30">打折：¥<fmt:formatNumber pattern="0.00" value="${carProduct.value.finalPrice}"/></span>
								</c:if>
                       			</c:when>
                       			<c:otherwise>
                       			<span>¥<fmt:formatNumber pattern="0.00" value="${carProduct.value.finalPrice}"/></span>
                       			</c:otherwise>
                       			</c:choose>
                       			<%-- <c:if test="${isLoginType && carProduct.value.pvalue!=0}">
                       		  	 <br/><span>积分: <fmt:formatNumber pattern="0.00" value="${carProduct.value.pvalue}"/></span>  
                       		  	</c:if> --%>
                        	</div>
                     		<div class="cell c-quantity">
                            	<div class="quantity-form">
                                 	<a href="javascript:void(0);" class="decrement j_decrement" data-value1='${carProduct.value.productSkuId}' data-value2='-1' 
	                    				data-value3='${carProduct.value.stockCount}' data-value4="${carProduct.value.isOutOfStock}" data-value5='${carProduct.value.minBuy}' 
	                    				data-value6='${carProduct.value.maxBuy}' data-value7='${carProduct.value.promotionStock}' data-value8='${carProduct.value.promotionSell}' 
	                    				data-value9='${carProduct.value.promoBuyNum}' data-value10='${kmshop.value.sellerId }' >-</a> 
									<input title="请输入有效数字" type="text" class="quantity-text j_modifyShopCarNew"  id="amountinput_${carProduct.value.productSkuId}" name="${carProduct.value.amount}" maxlength="4"
										data-value1='${carProduct.value.productSkuId}' data-value2='0' data-value3='${carProduct.value.stockCount}' data-value4="${carProduct.value.isOutOfStock}" 
										data-value5='${carProduct.value.minBuy}' data-value6='${carProduct.value.maxBuy}' data-value7='${carProduct.value.promotionStock}' 
										data-value8='${carProduct.value.promotionSell}' data-value9='${carProduct.value.promoBuyNum}'  data-value10='${kmshop.value.sellerId }' value="${carProduct.value.amount}"  />
									<a href="javascript:void(0);" class="increment j_increment" data-value1='${carProduct.value.productSkuId}' data-value2='1' 
										data-value3='${carProduct.value.stockCount}' data-value4="${carProduct.value.isOutOfStock}" data-value5='${carProduct.value.minBuy}' 
										data-value6='${carProduct.value.maxBuy}' data-value7='${carProduct.value.promotionStock}' data-value8='${carProduct.value.promotionSell}' 
										data-value9='${carProduct.value.promoBuyNum}' data-value10='${kmshop.value.sellerId }'>+</a>
                       			</div>
                      		</div>
                 			<div class="cell c-price"><span>¥<fmt:formatNumber pattern="0.00" value="${carProduct.value.finalPrice*carProduct.value.amount}"/></span><br> 
                 			  <%-- <c:if test="${isLoginType && carProduct.value.pvalue!=0}">
                 			 <span class="pvalue">积分：<fmt:formatNumber pattern="0.00" value="${carProduct.value.pvalue*carProduct.value.amount}"/> </span>
                 			 </c:if> --%>
                 			</div>
                			<div class="cell c-remove">
                           		<p><a href="javascript:void(0);" class="cart-remove j_addFavoriteLink"  data-value1="${carProduct.value.productSkuId}" data-value2="${carProduct.value.finalPrice}" data-value3="${kmshop.value.sellerId }" data-value4="${carProduct.value.productSkuCode}">加入收藏</a></p>
                          		<p><a href="javascript:void(0);" class="cart-remove j_deleteProductFromShopCarNew" data-value1="${carProduct.value.productSkuId}" data-value3="${kmshop.value.sellerId }">删除</a></p>
                        	</div>
              			</div>
            		</div>
            		</c:forEach>
            		</c:if>
            		<c:if test="${not empty kmshop.value.compositionCarProducts}">
					<c:forEach items="${kmshop.value.compositionCarProducts}" var="composition">
            		<div class="package" id="tiedSadeproductdiv_${composition.value.id}">
               			<div class="package-head">
                      		<div class="headtit">
                           		<input type="checkbox" class="checkbox j_childChange productCheckBox" data-value1="${kmshop.value.sellerId }" data-value2="1"  <c:if test="${composition.value.isChecked}">checked='checked'</c:if> name="tiedSadeproductcheckbox_${composition.value.id}" id="tiedSadeproductcheckbox_${composition.value.id}" value="${composition.value.id}" />
                             	<a href="javascript:void(0);">${composition.value.name}</a>
                             	<c:choose>
		                       	<c:when test="${composition.value.isChecked && not empty retypeSellerId && null!=retypeSellerId[saleShop.value.sellerId]}">
			                   		<p class="stockout-tip" data-value1="${composition.value.id}">该套餐已发生变化，请删除后重新添加。</p>
			                   	</c:when>
								<c:when test="${composition.value.isChecked && empty loginUserId && composition.value.mainCarProduct.maxBuy>0}">
									<p class="stockout-tip" data-value1="${composition.value.id}">游客不能参加该套餐的限购活动</p>
								</c:when>
								<c:when test="${composition.value.isChecked &&composition.value.isOutOfStock}">
									<p class="stockout-tip" data-value1="${composition.value.id}">该套餐已下架,请删除该套餐</p>
								</c:when>
								<c:when test="${composition.value.isChecked &&composition.value.stockCount==0}">
									<p class="stockout-tip" data-value1="${composition.value.id}">该套餐无库存,请删除该套餐</p>
								</c:when>
								<c:when test="${composition.value.isChecked &&composition.value.stockCount>0&&composition.value.stockCount<composition.value.amount}">
									<p class="stockout-tip" data-value1="${composition.value.id}">该套餐库存不足,请修改订购数量</p>
								</c:when>
								<c:when test="${composition.value.isChecked && composition.value.mainCarProduct.minBuy>0&&composition.value.mainCarProduct.minBuy>composition.value.amount}">
									<p class="stockout-tip" data-value1="${composition.value.id}">该套餐${composition.value.mainCarProduct.minBuy}件起售,请修改订购数量</p>
								</c:when>
								<c:when test="${composition.value.isChecked &&composition.value.mainCarProduct.promotionStock>0&&composition.value.mainCarProduct.promotionStock-composition.value.mainCarProduct.promotionSell<composition.value.amount}">
									<p class="stockout-tip" data-value1="${composition.value.id}">该套餐活动期间库存不足,请修改订购数量</p>
								</c:when>
								<c:when test="${composition.value.isChecked &&composition.value.mainCarProduct.maxBuy>0&&composition.value.mainCarProduct.promoBuyNum<composition.value.amount}">
									<p class="stockout-tip" data-value1="${composition.value.id}">该套餐活动期间限购${composition.value.mainCarProduct.maxBuy}件<c:if test="${composition.value.mainCarProduct.maxBuy-composition.value.mainCarProduct.promoBuyNum >0}">,已购买${composition.value.mainCarProduct.maxBuy-composition.value.mainCarProduct.promoBuyNum}件</c:if>,请修改订购数量</p>
								</c:when>
								</c:choose>
                         	</div>
                          	<div class="cell c-unitprice"><span></span></div>
                           	<div class="cell c-quantity">
                         		<div class="quantity-form">
                                 	<a href="javascript:void(0);" class="decrement j_suitdecrement" data-value1='${composition.value.id}' data-value2='-1' 
										data-value3='${composition.value.stockCount}' data-value4='${composition.value.isOutOfStock}' 
										data-value5='${composition.value.mainCarProduct.minBuy }' data-value6='${composition.value.mainCarProduct.maxBuy }' 
										data-value7='${composition.value.mainCarProduct.promotionStock }' data-value8='${composition.value.mainCarProduct.promotionSell }' 
										data-value9='${composition.value.mainCarProduct.promoBuyNum}'  data-value10='${kmshop.value.sellerId }'>-</a> 
									<input type="text" title="请输入有效数字" id="com_amountinput_${composition.value.id}" maxlength="4" 
										class="quantity-text j_modifyTiedSadeShopCarNew" value="${composition.value.amount}" name="${composition.value.amount}" 
										data-value1='${composition.value.id}' data-value2='0' data-value3='${composition.value.stockCount}' data-value4='${composition.value.isOutOfStock}' 
										data-value5='${composition.value.mainCarProduct.minBuy }' data-value6='${composition.value.mainCarProduct.maxBuy }' 
										data-value7='${composition.value.mainCarProduct.promotionStock }' data-value8='${composition.value.mainCarProduct.promotionSell }' 
										data-value9='${composition.value.mainCarProduct.promoBuyNum}'  data-value10='${kmshop.value.sellerId }'/>
									<a href="javascript:void(0);" class="increment j_suitincrement" data-value1='${composition.value.id}' data-value2='1' 
										data-value3='${composition.value.stockCount}' data-value4='${composition.value.isOutOfStock}' 
										data-value5='${composition.value.mainCarProduct.minBuy }' data-value6='${composition.value.mainCarProduct.maxBuy }' 
										data-value7='${composition.value.mainCarProduct.promotionStock }' data-value8='${composition.value.mainCarProduct.promotionSell }' 
										data-value9='${composition.value.mainCarProduct.promoBuyNum}' data-value10='${kmshop.value.sellerId }' >+</a>
                             	</div>
                            
                           	</div>
                        	<div class="cell c-price">
                        		<span id="allMoney_${composition.value.id}">¥<fmt:formatNumber pattern="0.00" value="${composition.value.finalPrice*composition.value.amount}"/></span>
								<%-- <c:if test="${isLoginType&&composition.value.pvalue!=0}">
								<br/><span class="pvalue">积分：<fmt:formatNumber pattern="0.00" value="${composition.value.pvalue*composition.value.amount}"/></span>
								</c:if> --%>
							</div>
                       		<div class="cell c-remove">
                       			<p>
                       			<a href="javascript:void(0);" class="cart-remove j_deleteProductFromShopCarNew" data-value2='${composition.value.id}' data-value3="${kmshop.value.sellerId }" >删除</a>
                       			</p>
                       		</div>
                      	</div>
                      	<div class="package-tbody">
                      		<c:forEach items="${composition.value.productList}" var="ccProduct" varStatus="ccIndex">
                       		<div class="item bd-t0 item-selected fn-clear">
                          		<div class="item-form">
                                	<div class="cell c-goods">
                                    	<div class="p-img">
                                    		<a href="${cmsPagePath}${ccProduct.productSkuId}.html" target="_blank">
	                          					<img width="65" height="65" onerror='this.src="${cssJsPath}images/default__logo_err60_60.jpg"' clstag="clickcart|keycount|xincart|p-imglistcart"
												src="${ccProduct.imagePath7}" alt="${ccProduct.title}" />
											</a>
                                    	</div>
                                 		<div class="p-name">
                                      		<a href="${cmsPagePath}${ccProduct.productSkuId}.html" target="_blank">${ccProduct.title}</a>
                                     	</div>
                                	</div>
                                	<div class="cell c-unitprice">
                                		<c:choose>
		                       			<c:when test="${not empty ccProduct.salePromotionInfo || not empty ccProduct.discountPromotionInfo }">
		                       			<c:if test="${not empty ccProduct.salePromotionInfo}">
			                    		<span class="btn-red30">特价：¥<fmt:formatNumber pattern="0.00" value="${ccProduct.finalPrice}"/></span>
			                       		</c:if>
										<c:if test="${not empty ccProduct.discountPromotionInfo}">
										<span class="btn-red30">打折：¥<fmt:formatNumber pattern="0.00" value="${ccProduct.finalPrice}"/></span>
										</c:if>
		                       			</c:when>
		                       			<c:otherwise>
		                       				<span>¥<fmt:formatNumber pattern="0.00" value="${ccProduct.finalPrice}"/></span>
		                       			</c:otherwise>
		                       			</c:choose>
 									</div>
                               		<div class="cell c-quantity"><div class="quantity-form">x${ccProduct.amount}</div></div>
                             		<div class="cell c-price"><span>¥<fmt:formatNumber pattern="0.00" value="${ccProduct.price*ccProduct.amount}"/></span></div>
                           		</div>
                        	</div>
                        	</c:forEach>
               			</div>          
                   	</div>
                   	</c:forEach>
                   	</c:if>
           		</div>
				</c:forEach>
				<c:if test="${not empty meetPromotionMap[1]}">
				<div class="item increase-purchase">
					<ul class="increase-purchase-box">
						<c:forEach items="${meetPromotionMap[1]}" var="meetPromotion">
						<li class="raise-gift" id="raise-gift${meetPromotion.promotionId}">
							<span class="tips">${promotionTypeMap[meetPromotion.promotionType]}<i></i></span>
							${meetPromotion.promotionName}
							<div class="goods-list">
								<a href="javascript:void(0);" data-value1="${meetPromotion.promotionId}" class="j_showProductListUl">
									${meetPromotion.productNameView}...<fmt:formatNumber pattern="0.00" value="${meetPromotion.lastAllMoney}"/>元
								</a>
								<ul id="pruductListul_${meetPromotion.promotionId}" class="goods-list-cont j_disshowProductListUl" style="display: none;" >
									<c:forEach items="${meetPromotion.carProductMap}" var="mcProduct">
									<li>
										<a class="name" href="${cmsPagePath}${mcProduct.value.productSkuId}.html" target="_blank">${mcProduct.value.title}</a>&nbsp;&nbsp;&nbsp;&nbsp;
										<font>x</font>${mcProduct.value.amount} &nbsp;&nbsp;<fmt:formatNumber pattern="0.00" value="${mcProduct.value.finalPrice*mcProduct.value.amount}"/>元
									</li>
									</c:forEach>
									<li class="subtotal">小计：<fmt:formatNumber pattern="0.00" value="${meetPromotion.lastAllMoney}"/>元</li>
								</ul>
	                      	</div>
	                      	<c:if test="${not empty giftCarProductMap[meetPromotion.promotionId]}">
	                      	<div class="bottom">
	                    		<span class="btn-yellow fn-l5 j_showGiftProduct" data-value1="${meetPromotion.promotionId }" 
	                         			data-value2="" data-value3="${meetPromotion.promotionType}" data-value4="${meetPromotion.meetData}"
	                         			data-value5="${meetPromotion.privilegeData}" data-value6="${meetPromotion.lastAllMoney}">
	                         		<c:if test="${meetPromotion.promotionType==3}">更换赠品</c:if>
									<c:if test="${meetPromotion.promotionType==5}">选购商品</c:if>
	                         	</span>
	                         	<div style="display:none;" class="addbuytip" id="promotionGiftDiv${meetPromotion.promotionId}">
								<c:if test="${meetPromotion.promotionType==3}">
									<div class="addbuy-inner change-goods">
										<h3>我要把赠品换成：</h3>
										<select class="mySelectInfo" id="${meetPromotion.promotionId}" data-value0="1" >
										<c:forEach items="${giftCarProductMap[meetPromotion.promotionId]}" var="giftProduct">
										<c:if test="${giftProduct.marketPrice<=meetPromotion.lastAllMoney}">
											<option value="${giftProduct.productSkuId}" data-value1="${giftProduct.stockCount}">${giftProduct.title}</option>
										</c:if>
										<c:if test="${giftProduct.marketPrice>meetPromotion.lastAllMoney}">
											<option disabled="true" value="${giftProduct.productSkuId}" data-value1="${giftProduct.stockCount}">${giftProduct.title}</option>
										</c:if>
										</c:forEach>
										</select>
										<div class="change-btns">
											<a class="change-btn-submit j_selectGiftProduct"  href="javascript:void(0);">确定</a>
											<a class="change-btn-cancel j_hideGiftProduct" href="javascript:void(0);">取消</a>
										</div>
									</div>
									</c:if>
									<c:if test="${meetPromotion.promotionType==5}">
									<div class="addbuy-inner speed-listitem">
										<ul class="dayhot-list fn-clear">
										<c:forEach items="${giftCarProductMap[meetPromotion.promotionId]}" var="increaseProduct">
										<c:if test="${increaseProduct.marketPrice<=meetPromotion.lastAllMoney }">
											<li class="dayhot-item">
												<div class="p-img fn-left">
													<a target="_blank" hidefocus="true" href="${cmsPagePath}${increaseProduct.productSkuId}.html">
														<img width="100" onerror='this.src="${cssJsPath}images/default__logo_err60_60.jpg"' height="100" src="${increaseProduct.imagePath6}" alt="${increaseProduct.title}">
													</a>
												</div>
												<div class="dayhot-item-cont fn-block">
													<div class="p-name"><a target="_blank" href="${cmsPagePath}${increaseProduct.productSkuId}.html">${increaseProduct.title}</a></div>
													<div class="p-price"><strong>+ ${increaseProduct.finalPrice }&nbsp;元</strong></div>
												 		 		<c:if test="${increaseProduct.stockCount>0}">
													<div class="p-btns">
														<a class="raise-addbtn j_addIncrease" data-value1="${increaseProduct.productSkuId}" data-value2="${meetPromotion.promotionId}" 
																	data-value3="${increaseProduct.finalPrice}" data-value4="${increaseProduct.title}" data-value0="1" href="javascript:void(0);">	加价购
														</a>
													</div>
											  </c:if>
											<c:if  test="${increaseProduct.stockCount<=0}">
											<div class="p-btns"><a class="raise-addbtn j_addIncrease" style="color:red !important;">已售完</a></div>
											</c:if>
												</div>
											</li>
										</c:if>
										</c:forEach>
										</ul>
									</div>
									</c:if>
									<div class="addbuy-arrow"><i></i></div>
									<div class="addbuy-close j_addbuy-close"></div>
								</div>
	                      	</div>
	                      	</c:if>
						</li>
						<c:forEach items="${giftCarProductMap[meetPromotion.promotionId]}" var="giftProduct">
						<c:if test="${giftProduct.isChecked}">
						<li class="raise-buy" id="giftPromotion${meetPromotion.promotionId}">
						<c:if test="${meetPromotion.promotionType==3}">
							<span class="fn-red">[赠品] </span><a target="_blank" href="${cmsPagePath}${giftProduct.productSkuId}.html">&nbsp;${giftProduct.title }</a>
							<span class="fn-red"><c:if test="${giftProduct.stockCount<=0}">&nbsp;已送完!</c:if></span>
						</c:if>
						<c:if test="${meetPromotion.promotionType==5}">
							<span class="fn-red">[加<fmt:formatNumber pattern="0.00" value="${giftProduct.finalPrice}"/>元购]</span><a target="_blank" href="${cmsPagePath}${giftProduct.productSkuId}.html">&nbsp;${giftProduct.title }</a>
							<span class="fn-red"><c:if test="${giftProduct.stockCount<=0 }">&nbsp;无库存，请删除!</c:if></span>
							<a data-value1="${meetPromotion.promotionId}" data-value2="${giftProduct.productSkuId}" data-value3="1"  class="raise-del j_delIncrease"  href="javascript:void(0);">删除</a>
						</c:if>
						</li>
						</c:if>
						</c:forEach>				
						</c:forEach>
					</ul>
				</div>
				</c:if>
				<c:set var="priceInfo" value="${sellerPriceMap[1] }" />
				<div class="shopcart-toolbar fn-clear">
					<div class="total fn-right">
						<p>总　计：<span >+ ¥<font id="j_sum_1"><fmt:formatNumber pattern="0.00" value="${priceInfo.sumMoney}"/></font></span></p>
						<p>加价购：<span>+<i class="fn-red">¥<font id="j_additional_1"><fmt:formatNumber pattern="0.00" value="${priceInfo.additionalMoney}"/></font></i></span></p>
						<p>满　减：<span >-<i class="fn-red">¥<font id="j_reduction_1"><fmt:formatNumber pattern="0.00" value="${priceInfo.reductionMoney}"/></font></i></span></p>

					</div>
					<div class="amout fn-right"><span>${priceInfo.productCount}</span> 件商品</div>
				</div>
				<div class="cart-dibu">
					<div class="cart-total-2014">
						<div class="cart-button j_loginUserGotoSettlemento" data-value0="1" data-value1="${shopCar.userIsLogin}" data-value2="<%=basePath%>settlement/gotoSettlement.action" data-value3="1">去结算<i>&gt;</i></div>
 							<div class="total fn-r10 fn-right">
 							 <%-- <c:if test="${isLoginType && priceInfo.pvalue!=0}">
					   可获积分:<span><font id="j_pay_3841"><fmt:formatNumber pattern="0.00" value="${priceInfo.pvalue}"/></font></span>
					   </c:if> --%>
					   </div>	
						<div class="total fn-r10 fn-right">
							商品总价（不含运费）:<span>¥<font id="j_pay_1"><fmt:formatNumber pattern="0.00" value="${priceInfo.payMoney}"/></font></span>;
						</div>	 
					</div>
				</div>
      		</div>
      		</c:if>
      		
      		<c:if test="${not empty saleProductMap }">
			<c:forEach items="${saleProductMap}" var="saleShop">
        	<div class="shopcart-inner-box" id="j_shopIndex${saleShop.value.sellerId }">
       			<div class="shopcart-head">
                	<span class="t-goods">
                   		<div class="headtit">
                       		<input type="checkbox" value="${saleShop.value.sellerId }" class="ck_check_shop_all" id="ck_check_all_${saleShop.value.sellerId }" data-value1="N" data-value2="${saleShop.value.sellerId }"/>
                         	<a href="javascript:void(0);">${saleShop.value.sellerInfo.shopName }</a>
                       		<c:set var="priceInfo" value="${sellerPriceMap[saleShop.value.sellerId] }" />
                    		<c:if test="${not empty priceInfo }">
                    			<c:set var="fareInfo" value="${priceInfo.freeFare}" />
                    			<c:if test="${not empty fareInfo }">
                    			<c:choose>
                    				<c:when test="${ priceInfo.payMoney >= fareInfo.freePrice }">
                   			<div class="headlabel"><i></i>运费：<strong>免邮</strong></div>
                    				</c:when>
                    				<c:otherwise>
                    		<div class="headlabel"><i></i>运费<strong>${fareInfo.firstHeavyFreight}</strong>元,满<strong>${fareInfo.freePrice}</strong>元免运费</div>	
                    				</c:otherwise>
                    			</c:choose>
                    			</c:if>
                   			</c:if>
                    	</div>
           			</span>
                   	<span class="t-unitprice">单价</span>
                	<span>数量</span>
                  	<span>合计</span>
                  	<span>操作</span>
           		</div>
            	<div class="shopcart-tbody">
            		<c:if test="${not empty saleShop.value.carProducts}">
					<c:forEach items="${saleShop.value.carProducts}" var="carProduct">
              		<div class="item item-selected fn-clear">
                  		<div class="item-form">
                     		<div class="cell c-checkbox">
                         		<input type="checkbox" value="${carProduct.value.productSkuId }"  data-value1="${saleShop.value.sellerId }"  data-value2="${saleShop.value.sellerId }" id="productcheckbox_${carProduct.value.productSkuId}" name="productcheckbox_${carProduct.value.productSkuId}"  <c:if test="${carProduct.value.isChecked}">checked='checked'</c:if> class="checkbox j_childChange productCheckBox"/>
                          	</div>
                         	<div class="cell c-goods">
                           		<div class="p-img">
                           			<a href="${cmsPagePath}${carProduct.value.productSkuId }.html" target="_blank">
	                     				<img onerror='this.src="${cssJsPath}images/default__logo_err60_60.jpg"' width="65" height="65" clstag="clickcart|keycount|xincart|p-imglistcart" src="${carProduct.value.imagePath7}" alt="${carProduct.value.title}">
									</a>
                           		</div>
                          		<div class="p-name">
	          						<a href="${cmsPagePath}${carProduct.value.productSkuId }.html" class="productLink_no_exists" data="${carProduct.value.productSkuId }"  target="_blank">${carProduct.value.title}</a>
									<c:choose>
	                   					<c:when test="${carProduct.value.isChecked && not empty retypeSellerId && null!=retypeSellerId[kmshop.value.sellerId]}">
	                   					<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该产品已发生变化，请删除该商品后再添加。</p>
	                   					</c:when>
										<c:when test="${carProduct.value.isChecked &&  empty loginUserId && carProduct.value.maxBuy>0}">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">游客不能参加该产品的限购活动</p>
										</c:when>
										<c:when test="${carProduct.value.isChecked && carProduct.value.isOutOfStock}">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该商品已下架,请删除该商品</p>
										</c:when>
										<c:when test="${ carProduct.value.isChecked && carProduct.value.stockCount==0}">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该商品无库存,请删除该商品</p>
										</c:when>
										<c:when test="${ carProduct.value.isChecked && carProduct.value.stockCount>0&&carProduct.value.stockCount<carProduct.value.amount}">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该商品库存不足,请修改订购数量</p>
										</c:when>
										<c:when test="${ carProduct.value.isChecked && carProduct.value.minBuy>0&&carProduct.value.minBuy>carProduct.value.amount}">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该商品${carProduct.value.minBuy}件起售,请修改订购数量</p>
										</c:when>
										<c:when test="${ carProduct.value.isChecked && carProduct.value.promotionStock>0&&carProduct.value.promotionStock-carProduct.value.promotionSell<carProduct.value.amount}">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该商品活动期间库存不足,请修改订购数量</p>
										</c:when>
										<c:when test="${ carProduct.value.isChecked && carProduct.value.maxBuy>0&&carProduct.value.promoBuyNum<carProduct.value.amount}">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该商品活动期间限购${carProduct.value.maxBuy}件<c:if test="${carProduct.value.maxBuy-carProduct.value.promoBuyNum >0}">,已购买${carProduct.value.maxBuy-carProduct.value.promoBuyNum}件</c:if>,请修改订购数量</p>
										</c:when>
										<c:when test="${carProduct.value.isChecked && 3==carProduct.value.productType }">
										<p class="stockout-tip" data-value0="p" data-value1="${carProduct.value.productSkuId }">该商品为处方药，请到我的药方进行购买</p>	
										</c:when>
									</c:choose>
									<c:if test="${carProduct.value.isChecked && 0==carProduct.value.freeStatus&&0!=carProduct.value.freight}">
										<p class="fn-red">加收运费${carProduct.value.freight*carProduct.value.amount }</p>
									</c:if>
	          					</div>
                        	</div>
                        	<div class="cell c-unitprice">
                       			<c:choose>
                       			<c:when test="${not empty carProduct.value.salePromotionInfo || not empty carProduct.value.discountPromotionInfo }">
                       			<c:if test="${not empty carProduct.value.salePromotionInfo}">
	                    		<span class="btn-red30">特价：¥<fmt:formatNumber pattern="0.00" value="${carProduct.value.finalPrice}"/></span>
	                    	 
	                       		</c:if>
								<c:if test="${not empty carProduct.value.discountPromotionInfo}">
								<span class="btn-red30">打折：¥<fmt:formatNumber pattern="0.00" value="${carProduct.value.finalPrice}"/></span>
								</c:if>
                       			</c:when>
                       			<c:otherwise>
                       				<span>¥<fmt:formatNumber pattern="0.00" value="${carProduct.value.finalPrice}"/></span>
                       			</c:otherwise>
                       			</c:choose>
                       			<br/>
                       			 <%-- <c:if test="${isLoginType && carProduct.value.pvalue!=0||4==carProduct.value.supplierType }">
                       		 	<span  >积分:  <fmt:formatNumber pattern="0.00" value="${carProduct.value.pvalue}"/> </span>
                       		 	</c:if> --%>
                        	</div>
                      		<div class="cell c-quantity">
                             	<div class="quantity-form">
                               		<a href="javascript:void(0);" class="decrement j_decrement" data-value1='${carProduct.value.productSkuId}' data-value2='-1' 
	                  				data-value3='${carProduct.value.stockCount}' data-value4="${carProduct.value.isOutOfStock}" data-value5='${carProduct.value.minBuy}' 
	                  				data-value6='${carProduct.value.maxBuy}' data-value7='${carProduct.value.promotionStock}' data-value8='${carProduct.value.promotionSell}' 
	                  				data-value9='${carProduct.value.promoBuyNum}' data-value10='${saleShop.value.sellerId }'>-</a> 
									<input title="请输入有效数字" type="text" class="quantity-text j_modifyShopCarNew"  id="amountinput_${carProduct.value.productSkuId}" name="${carProduct.value.amount}" maxlength="4"
									data-value1='${carProduct.value.productSkuId}' data-value2='0' data-value3='${carProduct.value.stockCount}' data-value4="${carProduct.value.isOutOfStock}" 
									data-value5='${carProduct.value.minBuy}' data-value6='${carProduct.value.maxBuy}' data-value7='${carProduct.value.promotionStock}' 
									data-value8='${carProduct.value.promotionSell}' data-value9='${carProduct.value.promoBuyNum}'  data-value10='${saleShop.value.sellerId }' value="${carProduct.value.amount}" />
									<a href="javascript:void(0);" class="increment j_increment" data-value1='${carProduct.value.productSkuId}' data-value2='1' 
									data-value3='${carProduct.value.stockCount}' data-value4="${carProduct.value.isOutOfStock}" data-value5='${carProduct.value.minBuy}' 
									data-value6='${carProduct.value.maxBuy}' data-value7='${carProduct.value.promotionStock}' data-value8='${carProduct.value.promotionSell}' 
									data-value9='${carProduct.value.promoBuyNum}'  data-value10='${saleShop.value.sellerId }'>+</a>
                           		</div>
                          	</div>
                     		<div class="cell c-price"><span>¥<fmt:formatNumber pattern="0.00" value="${carProduct.value.finalPrice*carProduct.value.amount}"/>
                     		</span><br>
                     		 <%-- <c:if test="${isLoginType && carProduct.value.pvalue!=0  ||4==carProduct.value.supplierType}">
                     		<span class="pvalue">积分: <fmt:formatNumber pattern="0.00" value="${carProduct.value.pvalue*carProduct.value.amount}"/></span>          
                     		</c:if> --%>
                     		</div>
                         	<div class="cell c-remove">
                           		<p><a href="javascript:void(0);" class="cart-remove j_addFavoriteLink" data-value1="${carProduct.value.productSkuId}" data-value2="${carProduct.value.finalPrice}" data-value3="${saleShop.value.sellerId }"  data-value4="${carProduct.value.productSkuCode}">加入收藏</a></p>
                          		<p><a href="javascript:void(0);" class="cart-remove j_deleteProductFromShopCarNew" data-value1="${carProduct.value.productSkuId}" data-value3="${saleShop.value.sellerId }">删除</a></p>
                          	</div>
                    	</div>
              		</div>
              		</c:forEach>
              		</c:if>
              		<c:if test="${not empty saleShop.value.compositionCarProducts}">
					<c:forEach items="${saleShop.value.compositionCarProducts}" var="composition">
              		<div class="package" id="tiedSadeproductdiv_${composition.value.id}">
               			<div class="package-head">
                      		<div class="headtit">
                           		<input type="checkbox" class="checkbox j_childChange productCheckBox" data-value1="${saleShop.value.sellerId }" data-value2="1"  <c:if test="${composition.value.isChecked}">checked='checked'</c:if> name="tiedSadeproductcheckbox_${composition.value.id}" id="tiedSadeproductcheckbox_${composition.value.id}" value="${composition.value.id}" />
                             	<a href="javascript:void(0);">${composition.value.name}</a>
                             	<c:choose>
		                       	<c:when test="${ composition.value.isChecked &&  not empty retypeSellerId && null!=retypeSellerId[saleShop.value.sellerId]}">
			                   		<p class="stockout-tip" data-value1="${composition.value.id}">该套餐已发生变化，请删除后重新添加。</p>
			                   	</c:when>
								<c:when test="${composition.value.isChecked &&   empty loginUserId && composition.value.mainCarProduct.maxBuy>0}">
									<p class="stockout-tip" data-value1="${composition.value.id}">游客不能参加该套餐的限购活动</p>
								</c:when>
								<c:when test="${ composition.value.isChecked &&  composition.value.isOutOfStock}">
									<p class="stockout-tip" data-value1="${composition.value.id}">该套餐已下架,请删除该套餐</p>
								</c:when>
								<c:when test="${ composition.value.isChecked &&  composition.value.stockCount==0}">
									<p class="stockout-tip" data-value1="${composition.value.id}">该套餐无库存,请删除该套餐</p>
								</c:when>
								<c:when test="${composition.value.isChecked && composition.value.stockCount>0&&composition.value.stockCount<composition.value.amount}">
									<p class="stockout-tip" data-value1="${composition.value.id}">该套餐库存不足,请修改订购数量</p>
								</c:when>
								<c:when test="${ composition.value.isChecked &&  composition.value.mainCarProduct.minBuy>0&&composition.value.mainCarProduct.minBuy>composition.value.amount}">
									<p class="stockout-tip" data-value1="${composition.value.id}">该套餐${composition.value.mainCarProduct.minBuy}件起售,请修改订购数量</p>
								</c:when>
								<c:when test="${ composition.value.isChecked &&  composition.value.mainCarProduct.promotionStock>0&&composition.value.mainCarProduct.promotionStock-composition.value.mainCarProduct.promotionSell<composition.value.amount}">
									<p class="stockout-tip" data-value1="${composition.value.id}">该套餐活动期间库存不足,请修改订购数量</p>
								</c:when>
								<c:when test="${ composition.value.isChecked && composition.value.mainCarProduct.maxBuy>0&&composition.value.mainCarProduct.promoBuyNum<composition.value.amount}">
									<p class="stockout-tip" data-value1="${composition.value.id}">该套餐活动期间限购${composition.value.mainCarProduct.maxBuy}件<c:if test="${composition.value.mainCarProduct.maxBuy-composition.value.mainCarProduct.promoBuyNum >0}">,已购买${composition.value.mainCarProduct.maxBuy-composition.value.mainCarProduct.promoBuyNum}件</c:if>,请修改订购数量</p>
								</c:when>
								</c:choose>
                         	</div>
                          	<div class="cell c-unitprice"><span></span></div>
                           	<div class="cell c-quantity">
                         		<div class="quantity-form">
                               		<a href="javascript:void(0);" class="decrement j_suitdecrement" data-value1='${composition.value.id}' data-value2='-1' 
										data-value3='${composition.value.stockCount}' data-value4='${composition.value.isOutOfStock}' 
										data-value5='${composition.value.mainCarProduct.minBuy }' data-value6='${composition.value.mainCarProduct.maxBuy }' 
										data-value7='${composition.value.mainCarProduct.promotionStock }' data-value8='${composition.value.mainCarProduct.promotionSell }' 
										data-value9='${composition.value.mainCarProduct.promoBuyNum}'  data-value10='${saleShop.value.sellerId }'>-</a> 
									<input type="text" title="请输入有效数字" id="com_amountinput_${composition.value.id}" maxlength="4" 
										class="quantity-text j_modifyTiedSadeShopCarNew" value="${composition.value.amount}" name="${composition.value.amount}" 
										data-value1='${composition.value.id}' data-value2='0' data-value3='${composition.value.stockCount}' data-value4='${composition.value.isOutOfStock}' 
										data-value5='${composition.value.mainCarProduct.minBuy }' data-value6='${composition.value.mainCarProduct.maxBuy }' 
										data-value7='${composition.value.mainCarProduct.promotionStock }' data-value8='${composition.value.mainCarProduct.promotionSell }' 
										data-value9='${composition.value.mainCarProduct.promoBuyNum}'  data-value10='${saleShop.value.sellerId }'/>
									<a href="javascript:void(0);" class="increment j_suitincrement" data-value1='${composition.value.id}' data-value2='1' 
										data-value3='${composition.value.stockCount}' data-value4='${composition.value.isOutOfStock}' 
										data-value5='${composition.value.mainCarProduct.minBuy }' data-value6='${composition.value.mainCarProduct.maxBuy }' 
										data-value7='${composition.value.mainCarProduct.promotionStock }' data-value8='${composition.value.mainCarProduct.promotionSell }' 
										data-value9='${composition.value.mainCarProduct.promoBuyNum}' data-value10='${saleShop.value.sellerId }' >+</a>
                             	</div>
 
                           	</div>
                        	<div class="cell c-price">
                        		<span id="allMoney_${composition.value.id}">¥<fmt:formatNumber pattern="0.00" value="${composition.value.finalPrice*composition.value.amount}"/></span>
						         <%-- <c:if test="${isLoginType ||4==composition.value.mainCarProduct.supplierType}">
						          <br/><span class="pvalue">积分：<fmt:formatNumber pattern="0.00" value="${composition.value.pvalue*composition.value.amount}"/></span>
						         </c:if> --%>
							</div>
                       		<div class="cell c-remove">
                       			<p>
                       			<a href="javascript:void(0);" class="cart-remove j_deleteProductFromShopCarNew" data-value2='${composition.value.id}' data-value3="${saleShop.value.sellerId }" >删除</a>
                       			</p>
                       		</div>
                      	</div>
                      	<div class="package-tbody">
                      		<c:forEach items="${composition.value.productList}" var="ccProduct" varStatus="ccIndex">
                       		<div class="item bd-t0 item-selected fn-clear">
                          		<div class="item-form">
                                	<div class="cell c-goods">
                                    	<div class="p-img">
	                                    	<a href="${cmsPagePath}${ccProduct.productSkuId}.html" target="_blank">
	                        					<img width="65" height="65" onerror='this.src="${cssJsPath}images/default__logo_err60_60.jpg"' clstag="clickcart|keycount|xincart|p-imglistcart" src="${ccProduct.imagePath7}" alt="${ccProduct.title}" />
											</a>
                                    	</div>
                                 		<div class="p-name">
                                      		<a href="${cmsPagePath}${ccProduct.productSkuId}.html" target="_blank">${ccProduct.title}</a>
                                     	</div>
                                	</div>
                                	<div class="cell c-unitprice">
                                		<c:choose>
		                       			<c:when test="${not empty ccProduct.salePromotionInfo || not empty ccProduct.discountPromotionInfo }">
		                       			<c:if test="${not empty ccProduct.salePromotionInfo}">
			                    		<span class="btn-red30">特价：¥<fmt:formatNumber pattern="0.00" value="${ccProduct.finalPrice}"/></span>
			                       		</c:if>
										<c:if test="${not empty ccProduct.discountPromotionInfo}">
										<span class="btn-red30">打折：¥<fmt:formatNumber pattern="0.00" value="${ccProduct.finalPrice}"/></span>
										</c:if>
		                       			</c:when>
		                       			<c:otherwise>
		                       				<span>¥<fmt:formatNumber pattern="0.00" value="${ccProduct.finalPrice}"/></span>
		                       			</c:otherwise>
		                       			</c:choose>
									</div>
                               		<div class="cell c-quantity"><div class="quantity-form">x${ccProduct.amount}</div></div>
                               		<br>
                             		<div class="cell c-price"><span>¥<fmt:formatNumber pattern="0.00" value="${ccProduct.price*ccProduct.amount}"/></span>
                             		
                             		</div>
                           		</div>
                        	</div>
                           	</c:forEach>      
               			</div>          
                   	</div>
                   	</c:forEach>
                   	</c:if>
                   	<c:if test="${not empty meetPromotionMap[saleShop.value.sellerId]}">
					<div class="item increase-purchase">
						<ul class="increase-purchase-box">
							<c:forEach items="${meetPromotionMap[saleShop.value.sellerId]}" var="meetPromotion">
							<li class="raise-gift" id="raise-gift${meetPromotion.promotionId}">
								<span class="tips">${promotionTypeMap[meetPromotion.promotionType]}<i></i></span>
								${meetPromotion.promotionName}
								<div class="goods-list">
									<a href="javascript:void(0);" data-value1="${meetPromotion.promotionId}" class="j_showProductListUl">
										${meetPromotion.productNameView}...<fmt:formatNumber pattern="0.00" value="${meetPromotion.lastAllMoney}"/>元
									</a>
									<ul id="pruductListul_${meetPromotion.promotionId}" class="goods-list-cont j_disshowProductListUl" style="display: none;" >
										<c:forEach items="${meetPromotion.carProductMap}" var="mcProduct">
										<li>
											<a class="name" href="${cmsPagePath}${mcProduct.value.productSkuId}.html" target="_blank">${mcProduct.value.title}</a>&nbsp;&nbsp;&nbsp;&nbsp;
											<font>x</font>${mcProduct.value.amount} &nbsp;&nbsp;<fmt:formatNumber pattern="0.00" value="${mcProduct.value.finalPrice*mcProduct.value.amount}"/>元
										</li>
										</c:forEach>
										<li class="subtotal">小计：<fmt:formatNumber pattern="0.00" value="${meetPromotion.lastAllMoney}"/>元</li>
									</ul>
		                      	</div>
		                      	<c:if test="${not empty giftCarProductMap[meetPromotion.promotionId]}">
		                      	<div class="bottom">
		                    		<span class="btn-yellow fn-l5 j_showGiftProduct" data-value1="${meetPromotion.promotionId }" 
		                         			data-value2="" data-value3="${meetPromotion.promotionType}" data-value4="${meetPromotion.meetData}"
		                         			data-value5="${meetPromotion.privilegeData}" data-value6="${meetPromotion.lastAllMoney}">
		                         		<c:if test="${meetPromotion.promotionType==3}">更换赠品</c:if>
										<c:if test="${meetPromotion.promotionType==5}">选购商品</c:if>
		                         	</span>
									<div style="display:none;" class="addbuytip" id="promotionGiftDiv${meetPromotion.promotionId}">
										<c:if test="${meetPromotion.promotionType==3}">
										<div class="addbuy-inner change-goods">
											<h3>我要把赠品换成：</h3>
											<select class="mySelectInfo" id="${meetPromotion.promotionId}" data-value0="${saleShop.value.sellerId }">
											<c:forEach items="${giftCarProductMap[meetPromotion.promotionId]}" var="giftProduct">
											<c:if test="${giftProduct.marketPrice<=meetPromotion.lastAllMoney}">
												<option value="${giftProduct.productSkuId}" data-value1="${giftProduct.stockCount}">${giftProduct.title}</option>
											</c:if>
											<c:if test="${giftProduct.marketPrice>meetPromotion.lastAllMoney}">
												<option disabled="true" value="${giftProduct.productSkuId}" data-value1="${giftProduct.stockCount}">${giftProduct.title}</option>
											</c:if>
											</c:forEach>
											</select>
											<div class="change-btns">
												<a class="change-btn-submit j_selectGiftProduct"  href="javascript:void(0);">确定</a>
												<a class="change-btn-cancel j_hideGiftProduct" href="javascript:void(0);">取消</a>
											</div>
										</div>
										</c:if>
										<c:if test="${meetPromotion.promotionType==5}">
										<div class="addbuy-inner speed-listitem">
											<ul class="dayhot-list fn-clear">
											<c:forEach items="${giftCarProductMap[meetPromotion.promotionId]}" var="increaseProduct">
											<c:if test="${increaseProduct.marketPrice<=meetPromotion.lastAllMoney }">
												<li class="dayhot-item">
													<div class="p-img fn-left">
														<a target="_blank" hidefocus="true" href="${cmsPagePath}${increaseProduct.productSkuId}.html">
															<img width="100" onerror='this.src="${cssJsPath}images/default__logo_err60_60.jpg"' height="100" src="${increaseProduct.imagePath6}" alt="${increaseProduct.title}">
														</a>
													</div>
													<div class="dayhot-item-cont fn-block">
														<div class="p-name"><a target="_blank" href="${cmsPagePath}${increaseProduct.productSkuId}.html">${increaseProduct.title}</a></div>
														<div class="p-price"><strong>+ ${increaseProduct.finalPrice }&nbsp;元</strong></div>
															 		<c:if test="${increaseProduct.stockCount>0}">
													<div class="p-btns">
														<a class="raise-addbtn j_addIncrease" data-value1="${increaseProduct.productSkuId}" data-value2="${meetPromotion.promotionId}" 
																	data-value3="${increaseProduct.finalPrice}" data-value4="${increaseProduct.title}" data-value0="${saleShop.value.sellerId }" href="javascript:void(0);">	加价购
														</a>
													</div>
											  </c:if>
											<c:if  test="${increaseProduct.stockCount<=0}">
											<div class="p-btns"><a class="raise-addbtn j_addIncrease" style="color:red !important;">已售完</a></div>
											</c:if>
													</div>
												</li>
											</c:if>
											</c:forEach>
											</ul>
										</div>
										</c:if>
										<div class="addbuy-arrow"><i></i></div>
										<div class="addbuy-close j_addbuy-close"></div>
									</div>
								</div>	
		                      	</c:if>
							</li>
							<c:forEach items="${giftCarProductMap[meetPromotion.promotionId]}" var="giftProduct">
							<c:if test="${giftProduct.isChecked}">
							<li class="raise-buy" id="giftPromotion${meetPromotion.promotionId}">
							<c:if test="${meetPromotion.promotionType==3}">
								<span class="fn-red">[赠品] </span><a target="_blank" href="${cmsPagePath}${giftProduct.productSkuId}.html">&nbsp;${giftProduct.title }</a>
								<span class="fn-red"><c:if test="${giftProduct.stockCount<=0}">&nbsp;已送完!</c:if></span>
							</c:if>
							<c:if test="${meetPromotion.promotionType==5}">
								<span class="fn-red">[加<fmt:formatNumber pattern="0.00" value="${giftProduct.finalPrice}"/>元购]</span><a target="_blank" href="${cmsPagePath}${giftProduct.productSkuId}.html">&nbsp;${giftProduct.title }</a>
								<span class="fn-red"><c:if test="${giftProduct.stockCount<=0 }">&nbsp;无库存，请删除!</c:if></span>
								<a data-value1="${meetPromotion.promotionId}" data-value2="${giftProduct.productSkuId }" data-value3="${saleShop.value.sellerId }"  class="raise-del j_delIncrease"  href="javascript:void(0);">删除</a>
							</c:if>
							</li>
							</c:if>
							</c:forEach>	
							</c:forEach>
					 
							
						</ul>
							
					</div>
					</c:if>
				<div class="item increase-purchase">
				<c:if test="${isLoginType && priceInfo.supplierType==4}">	
				<div class="increase-purchase-box">
				 <h3> 康美中药城本期促销：
				 <a href="${priceInfo.url}"  target="_blank"   class="bottom btn-yellow fn-right">更多促销详细信息请点击查看&gt;&gt;</a>
				 </h3>
 				<p>${priceInfo.info}</p>
 				   </div>
				</c:if>
				   </div>
                 	<div class="shopcart-toolbar fn-clear">
                 		<div class="total fn-right">
							<p>总　计：<span >+ ¥<font id="j_sum_${saleShop.value.sellerId }"><fmt:formatNumber pattern="0.00" value="${priceInfo.sumMoney}"/></font></span></p>
							<p>加价购：<span>+ <i class="fn-red">¥<font id="j_additional_${saleShop.value.sellerId }"><fmt:formatNumber pattern="0.00" value="${priceInfo.additionalMoney}"/></font></i></span></p>
							<p>满　减：<span >- <i class="fn-red">¥<font id="j_reduction_${saleShop.value.sellerId }"><fmt:formatNumber pattern="0.00" value="${priceInfo.reductionMoney}"/></font></i></span></p>
						
						     <c:if test="${isLoginType && priceInfo.supplierType==4}">
						   <p>时代${priceInfo.eraGradeName}会员享<fmt:formatNumber pattern="0" value="${priceInfo.eraGradeRate}"/>折优惠：<span>- <i class="fn-red">¥<font id="j_reduction_${saleShop.value.sellerId }">
						   <fmt:formatNumber pattern="0.00" value="${priceInfo.discountMoney}"/><span>
						   
						   </font></i></span></p>
						  </c:if>
						</div>
						<div class="amout fn-right"><span>${priceInfo.productCount}</span> 件商品</div>
					</div>
					<div class="cart-dibu">
						<div class="cart-total-2014">
							<div class="cart-button j_loginUserGotoSettlemento" data-value0="${saleShop.value.sellerId }"  data-value1="${shopCar.userIsLogin}" data-value2="<%=basePath%>settlement/gotoSettlement.action" data-value3="${saleShop.value.sellerId }">去结算<i>&gt;</i></div>
							<div class="total fn-r10 fn-right">
							  <%-- <c:if test="${isLoginType && priceInfo.pvalue!=0  ||  priceInfo.supplierType==4}">
								可获积分:<span><font id="j_pay_${saleShop.value.sellerId }"><fmt:formatNumber pattern="0.00" value="${priceInfo.pvalue}"/></font> </span>
							  </c:if> --%>
							</div>
						 
							<div class="total fn-r10 fn-right">
								商品总价（不含运费）:<span>¥<font id="j_pay_${saleShop.value.sellerId }"><fmt:formatNumber pattern="0.00" value="${priceInfo.payMoney}"/></font></span>;
							</div>
							
						</div>
					</div>
          		</div>
       		</div>
       		</c:forEach>
       		</c:if>
   			<div class="cart-dibu cart-total-bg">
         		<div class="control fdibu fn-left">
               		<label><span class="t-checkbox"><input type="checkbox" value=""  name="" class="checkbox j_checkAll" />全选</span></label>
                  	<span class="delete"><b></b><a class="delete deleteAllCheckedLink" href="javascript:void(0);">删除选中商品</a></span>
                 	<span class="shopping"><b></b><a href="<%=ConfigurationUtil.getString("staticPath")%>">继续购物</a></span>
              	</div>
             	<div class="cart-total-2014">
             	 <%-- <c:if test="${isLoginType && shopCartTotalDTO.productPvalue!=0}">
             	 
              
             	    <div class="total fn-right">可获积分: <span> <fmt:formatNumber pattern="0.00" value="${shopCartTotalDTO.productPvalue}"/> </span></div>
             	    </c:if> --%>
                  	<div class="total fn-right">商品总价（不含运费）:<span>¥<strong id="j_sum"><fmt:formatNumber pattern="0.00" value="${shopCartTotalDTO.lastAllMoney}"/></strong></span>;</div>
           	  
           		</div>
       		</div>
		</div>
	</div>
</div>