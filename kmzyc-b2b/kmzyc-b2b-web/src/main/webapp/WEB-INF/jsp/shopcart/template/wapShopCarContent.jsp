<%@page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil,java.util.Date,com.kmzyc.framework.constants.Constants" pageEncoding="UTF-8"%>
<%
String picpath = ConfigurationUtil.getString("picPath");
String wapCssPath = ConfigurationUtil.getString("CSS_JS_PATH");
String wapPath = ConfigurationUtil.getString("staticPath_WAP");
String wapPortal= ConfigurationUtil.getString("portalPath_WAP");
String wapstatic =ConfigurationUtil.getString("productPicPath_WAP");
Long randomTime=new Date().getTime();
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="container">
	<section class="category-box">	  
		<div class="">  
			<c:if test="${not empty shopList }">
				<c:forEach items="${shopList.sellerShopSet}" var="shop">
					<div id="j_shopIndex${shop.sellerId}">	    		    	
					<div class="lst">
						<div class="lst-hd bg-green">
							<input type="checkbox" noneCheckedIds="${shop.noneCheckIds}" checkedIds="${shop.checkIds}" class="icon-checkbox j_checkShop" />
							<label class="cor-white">${shop.shopName }</label>
							<c:if test="${not empty fareMap }">
	          					<c:choose>
		          					<c:when test="${shop.checkTotalMoney >= fareMap[shop.sellerId].freePrice}">
		          						<span class="label label-warning">已免运费</span> 
		          					</c:when>			          				
					    			<c:when test="${shop.checkTotalMoney < fareMap[shop.sellerId].freePrice}" >
							      		<span class="label label-warning">运费 ${fareMap[shop.sellerId].firstHeavyFreight} 元,满    ${fareMap[shop.sellerId].freePrice}元免运费</span> 
									</c:when>
									<c:otherwise><span class="label label-warning">已免运费</span></c:otherwise>
		    					</c:choose>			    		          	
	          				</c:if>
		        		</div>
					</div>
					<c:forEach items="${shop.shopCartItemSet}" var="shopCartItemSet">
						<!--<c:set value="${shopCartItemMap.value}" var="shopCartItem"/>-->
						
						<div class="lst">					
							<!-- 满赠商品 s -->
							<c:if test="${shopCartItemSet.promtionInfo.type == 3}">
								<c:if test="${not empty shopCartItemSet.rulePresents}">
									<!-- 选择赠品窗口 -->
									<div style="display: none;" class="pop-up-box" name="itemGift_" id="itemGift_${shopCartItemSet.id}">
										<div class="pop-up-tit">
											<a href="javascript:void(0);" class="close padri-2  j_showGiftUi" data="${shopCartItemSet.id}">×</a>请选择满赠赠品
										</div>
										<div class="pur-shop-box">
											
											<c:forEach items="${shopCartItemSet.rulePresents}" var="rulePresent">
												<div class="ppup-list">
													<c:set value="${rulePresent.value}" var="prensentList" />										
													<c:set value="${shopCartItemSet.rulePresents[shopCartItemSet.defaultPresents]}" var="rulePresentList" />
													<div class="check-wrapper">															 											
														<c:choose>																														
															<c:when test="${not empty shopCartItemSet.defaultPresents}">
																<c:set var="flag" value="0"></c:set>																	
																<c:forEach items="${prensentList}" var="gift">
																	<c:if test="${flag ne 1}">																							
																		<input type="radio" class="shop-radio j_checkGift" style="display:none;" 
														 					name="itme_${shopCartItemSet.id}"  data-item="${shopCartItemSet.id}"
																			maxnum="${gift.maxAmount}"  myid="${shopCartItemSet.id}_${gift.id}" 
																			num="${gift.amount}" value="${gift.id}" data-presents="${gift.meetData}"
																			id=	"${shop.sellerId}_${shopCartItemSet.id}_${shopCartItemSet.promtionInfo.type}_${rulePresent.key }_${gift.id}"																 																						
																			<c:if test="${rulePresent.key ==  shopCartItemSet.defaultPresents}">checked</c:if>/>	
																																																						
																		<label for="${shop.sellerId}_${shopCartItemSet.id}_${shopCartItemSet.promtionInfo.type}_${rulePresent.key }_${gift.id}" class="shop-radio"/>	
																																																				
																		<c:set var="flag" value="1"/>
																	</c:if>																																														
																</c:forEach>																											
															</c:when>																												
														</c:choose>
													</div>													
													<c:choose>													
														<c:when test="${fn:length(rulePresent.value) > 1}">
															<div class="ppup-prod clearfix">
																<ul>							
																	<c:forEach items="${prensentList}" var="gift">
																		<li>
																			<div class="ppup-prodimg clearfix">
																				<div class="list-thumb">	
																					<c:choose>
																						<c:when test="${empty giftStockMap[gift.id] or giftStockMap[gift.id] < gift.amount}">
																							<div class="ts-kc-pf">已送完</div>
																						</c:when>																						
																					</c:choose>																			
																					
																					<img onerror="this.onerror='';this.src='<%=wapCssPath%>images/default__logo_err60_60.jpg'"
																			        src="<%=picpath%>${fn:replace(gift.img, '.jpg', '_7.jpg')}" width="67" height="67">																				   
																				</div>
																				<div class="ppup-prod-quantity">x${gift.amount}</div>
																			</div>
																			<div class="ppup-prodtit"><a href="<%=wapstatic%>${gift.id}.html" target="_blank">${gift.name }</a></div>
																		</li>	
																	</c:forEach>																								
																</ul>
															</div>
														</c:when>
														<c:otherwise>
															<c:forEach items="${prensentList}" var="gift">
																<div class="list-descriptions tabs-lst ">
																	<div class="list-thumb">
																		<a href="<%=wapstatic%>${gift.id}.html" target="_blank">
																			
																			<c:choose>
																				<c:when test="${empty giftStockMap[gift.id] or giftStockMap[gift.id]<gift.amount}">
																					<div class="ts-kc-pf">已送完</div>
																				</c:when>																				
																			</c:choose>		
																		
																			<img onerror="this.onerror='';this.src='<%=wapCssPath%>images/default__logo_err60_60.jpg'"
																	        src="<%=picpath%>${fn:replace(gift.img, '.jpg', '_7.jpg')}" width="67" height="67">
																	   	</a>
																	</div>
																	<div class="list-descriptions-wrapper">
																		<div class="product-name cart-name">
																			<a href="<%=wapstatic%>${gift.id}.html" target="_blank">${gift.name }</a>
																		</div>	
																		<div class="clearfix price-mk">																			
																			<span class="option pull-right padri-5">																				
																				x${gift.amount}																																								
																			</span>
																		</div>																
																	</div>
																</div>
															</c:forEach>
														</c:otherwise>
													</c:choose>														
												</div>
											</c:forEach>
										</div>
										<div class=" ppup-btn clearfix">
											<ul>
												<li>已选赠品<span class="text-danger" >
												${empty shopCartItemSet.defaultPresents?"0":shopCartItemSet.countPresents}</span>件
													<input type="hidden" id="label_${shopCartItemSet.id}" value="${shopCartItemSet.giftCount}"/>
												</li>
												<li><a href="javascript:void(0);" data-item="${shopCartItemSet.id}"
													data-org="${shopCartItemSet.presents}" class="btn btn-my btn-success btn-sm j_submitPresent">确定</a></li>
											</ul>
										</div>
									</div>
									<!-- 选择赠品窗口 -->
								</c:if>
							</c:if>     				
							<!-- 满赠商品 e -->

							<!-- 换购商品 s -->
							<c:if test="${shopCartItemSet.promtionInfo.type == 5}">
								<c:if test="${not empty shopCartItemSet.ruleGifts}">
									<!-- 选择换购商品窗口 -->
									<div style="display: none;" class="pop-up-box" name="itemGift_" id="itemGift_${shopCartItemSet.id}">
										<div class="pop-up-tit">
											<a href="javascript:void(0);" class="close padri-2 j_showGiftUi" data="${shopCartItemSet.id}">×</a>换购商品
										</div>
										<div class="pur-shop-box">											
											<c:forEach items="${shopCartItemSet.ruleGifts}" var="gift">
												<div class=" ppup-list">	
												
													<div class="check-wrapper">	
														<input type="checkbox" class="icon-checkbox j_checkGift" myid="${shopCartItemSet.id}_${gift.value.dataId}" 
															name="itme_${shopCartItemSet.id}" value="${gift.key}" num="${gift.value.amount}" 
			        										maxnum="${gift.value.maxAmount}" data-item="${shopCartItemSet.id}"												
															<c:choose>																														
																<c:when test="${not empty shopCartItemSet.gifts}">
																	<c:forEach items="${shopCartItemSet.gifts}" var="gifts">
																		<c:if test="${gifts.dataId == gift.value.dataId}">
																			checked
																		</c:if>
																	</c:forEach>															
																</c:when>
															</c:choose>	/>														
													</div>
													<div class="list-descriptions tabs-lst ">
														<div class="list-thumb">
															<c:choose>
											             		<c:when test="${empty giftStockMap[gift.value.id] or giftStockMap[gift.value.id]<1}">
											             			 <div class="ts-kc-pf">无库存</div>
											             		</c:when>											             		
											             	</c:choose>
																														
															<img onerror="this.onerror='';this.src='<%=wapCssPath%>images/default__logo_err60_60.jpg'"
													        src="<%=picpath%>${fn:replace(gift.value.img, '.jpg', '_7.jpg')}" width="67" height="67">														   
														</div>
														<div class="list-descriptions-wrapper">
															<div class="product-name cart-name">
																<a href="<%=wapstatic%>${gift.value.id}.html" target="_blank">${gift.value.name }</a>
															</div>
															<div class="clearfix price-mk">
																<span class="pull-left text-danger jg-wz">
																	￥<fmt:formatNumber pattern="0.00" value="${gift.value.price}" />
																</span>
																<span class="option pull-right padri-5">
																	<input type="text" class="op-txt j_giftProductInput" type="text" myid="inputNum_${shopCartItemSet.id}_${gift.value.dataId}" 
				               											data-max="${gift.value.maxAmount}" data-org="${gift.value.amount}" value="1"/>															
																</span>
															</div>
														</div>
													</div>
												</div>
											</c:forEach>
										</div>
										<div class=" ppup-btn clearfix">
											<ul>
												<li>已换购
													<span class="text-danger">${empty shopCartItemSet.countChoosed?"0":shopCartItemSet.countChoosed}</span>/
													<label >${shopCartItemSet.giftCount} </label>件
													<input type="hidden" id="label_${shopCartItemSet.id}" value="${shopCartItemSet.giftCount}"/>
												</li>
												<li><a href="javascript:void(0);" data-item="${shopCartItemSet.id}" class="btn btn-my btn-success btn-sm j_submitGift">确定</a></li>
											</ul>
										</div>
									</div>
									<!-- 选择换购商品窗口 -->
								</c:if>
							</c:if>
							<!-- 换购商品 e -->										
							
							
							<!-- 显示是否有活动 s -->
							<c:if test="${not empty shopCartItemSet.promtionInfo}">
								<div class="lst">
									<div class="shop-tit clearfix">
										<div class="pull-left width70"> 
											<span class="shop-tit-icon  kuan45">
												<a class="${shopCartItemSet.meet?'label label-danger bq-font':'label label-danger bg-gray bq-font'} ">
												${shopCartItemSet.promtionInfo.typeName }</a><i class="${shopCartItemSet.meet ? 'tux3' : 'tux3-gray'}"></i>
											</span>
											<span class="shop-tit-wz kuan72">${shopCartItemSet.promtionInfo.name }</span>
										</div>
										<c:choose>
											<c:when test="${shopCartItemSet.meet}">											
												<c:if test="${not empty shopCartItemSet.tag}">													
													<a class="pull-right btn-right-block checkbox-inline btn-success j_showGiftUi" data="${shopCartItemSet.id}">
														${shopCartItemSet.tag}>
													</a>
												</c:if>
											</c:when>
											<c:otherwise>
												<c:if test="${shopCartItemSet.promtionInfo.type == 5 || shopCartItemSet.promtionInfo.type == 3}">
													<!--<div class="pull-right ri-bq">凑单>></div>  -->
												</c:if>
											</c:otherwise>
										</c:choose>										
										
							        </div>					        
								</div>
							</c:if>
							<!-- 显示是否有活动 e -->
							
							<!-- 显示已选择换购商品 -->
							<c:if test="${not empty shopCartItemSet.gifts}">							
								<ul class="tabs-lst">
								<c:forEach items="${shopCartItemSet.gifts}" var="gift">								
						        	<li class="border-0 ">
							           <div class="check-wrapper yuand" mytype="gift_${shopCartItemSet.id}" value="${shopCartItemSet.id}_${gift.dataId}" giftId="${gift.id}" num="${gift.amount}"></div>
							           <div class="list-descriptions bord-cor">
											<div class="list-thumb">
							              		<a target="_blank" href="<%=wapstatic%>${gift.id}.html"><img src="<%=picpath%>${fn:replace(gift.img, '.jpg', '_7.jpg')}"></a>
											</div>
											<div class="list-descriptions-wrapper">
								              	<div class="product-name cart-name">								              		
								                	<a target="_blank" href="<%=wapstatic%>${gift.id}.html">								                		
								                		[换购]&nbsp;${gift.name }
								                	</a>								                	
								                </div>
								               
								                <div class="clearfix price-mk">
								                	<span class="pull-left text-danger jg-wz">
								                		￥<fmt:formatNumber pattern="0.00" value="${gift.price }" />
								                	</span>
								                	<span class="option pull-right"> 
									                  	<input type="text" class="op-txt" value="${gift.amount }" disabled="disabled"/ >									                  	
								                  	</span>							                 	
								                </div>
											</div>
											<c:if test="${product.check}">
							                	
								                	<a href="javascript:void(0);"><i data-itemId="${shopCartItemSet.id}" data-productId="${gift.dataId}" class="discounts icon-uniE602 newsc j_deleteGift"></i></a>											                
							                	
						                	</c:if>
							            </div>							             
						             	<!-- 错误提示 s -->
						             	<c:choose>
						             		<c:when test="${empty giftStockMap[gift.id] or giftStockMap[gift.id]<1}">
						             			 <div data-shllerId=${shop.sellerId } data-valid="error" class="prompt text-danger">无库存</div>
						             		</c:when>
						             		<c:when test="${ not empty giftStockMap[gift.id] and giftStockMap[gift.id]<gift.amount}">
						             			 <div data-shllerId=${shop.sellerId } data-valid="error" class="prompt text-danger">库存不足</div>
						             		</c:when>
						             	</c:choose>
										<!-- 错误提示 e -->							            							            
									</li>									
								</c:forEach>	
								</ul>
					      	</c:if>
					      	<!-- 显示已选择换购商品 -->
					      	
					      	<!-- 显示已送赠品 -->
							<c:if test="${not empty shopCartItemSet.defaultPresents}">
								<ul class="tabs-lst">								
									<c:set value="${shopCartItemSet.rulePresents[shopCartItemSet.defaultPresents]}" var="rulePresentList" />								
									<c:forEach items="${rulePresentList}" var="gift">
							        	<li class="border-0 ">
								           <div class="check-wrapper yuand" mytype="gift_${shopCartItemSet.id}" value="${shopCartItemSet.id}_${gift.id}" giftId="${gift.id}" num="${gift.amount}"></div>
								           <div class="list-descriptions bord-cor">
												<div class="list-thumb">
								              		<a target="_blank" href="<%=wapstatic%>${gift.id}.html"><img src="<%=picpath%>${fn:replace(gift.img, '.jpg', '_7.jpg')}"></a>
												</div>
												<div class="list-descriptions-wrapper">
									               <div class="product-name cart-name">
									                	<a target="_blank" href="<%=wapstatic%>${gift.id}.html">								                		
									                		[赠品]&nbsp;${gift.name }
									                	</a>
									                </div>
									                <div class="clearfix price-mk">
									                	<span class="pull-left text-danger jg-wz">
									                		￥<fmt:formatNumber pattern="0.00" value="${gift.price }" />
									                	</span>	
									                	<span class="option pull-right"> 
										                  	<input type="text" class="op-txt" value="${gift.amount }" disabled="disabled"/ >										                  	
									                  	</span>							                 	
									                </div>
												</div>
								            </div>	
								            <!-- 错误提示 s -->
								            <c:if  test="${empty giftStockMap[gift.id] or giftStockMap[gift.id]<gift.amount}">												
												 <div class="prompt text-danger">已送完</div>
											</c:if>
											<!-- 错误提示 e -->				            							            
										</li>
									</c:forEach>								
								
								</ul>
					      	</c:if>
					      	<!-- 显示已送赠品 -->
							
							<!-- 遍历商品  s-->						
							<c:forEach items="${shopCartItemSet.carProducts }" var="cartProduct">						
							 	<c:set value="${cartProduct}"  var="key"/>
								<c:set value="${normalMap[key]}" var="product"/>
								<c:set value="${productErrorReminder[key]}"  var="productReminder"/>
        						<c:set value="${empty productReminder or productReminder.normal}" var="normal"/>
        						
								<!-- 单品 -->
								<c:if test="${fn:startsWith(key, 'n_')}">
							        <ul class="tabs-lst">
										<li>
								            <div class="check-wrapper">
								              <input type="checkbox" class="icon-checkbox j_childProduct" name="product_${key}" id="product_${key}"
								              	value="${key}" ${normal ? '' : 'disabled="disabled"'} 
								              	<c:if test="${product.check}">checked</c:if>/>
								            </div>
								            <div class="list-descriptions">
												<div class="list-thumb">
								              		<a target="_blank" href="<%=wapstatic%>${product.id}.html"><img src="<%=picpath%>${fn:replace(product.imagePath, '.jpg', '_7.jpg')}"></a>
												</div>
												<div class="list-descriptions-wrapper">
									                <div class="product-name cart-name">
									                	<a ruleInfo="" target="_blank" href="<%=wapstatic%>${product.id}.html">
									                		<c:set var="salePromotion" value="${product.productPromotion.pricePromotion}"/>
									                		<c:if test="${not empty salePromotion}">												         		
												         		<span class="bq-green-13 ">${salePromotion.typeName}</span>												         		
												         	</c:if>
									                		${product.title}
									                	</a>
									                </div>
									                <div class="clearfix price-mk">
									                	<span class="pull-left text-danger jg-wz">
									                		￥<fmt:formatNumber pattern="0.00" value="${product.finalPrice }" />
									                	</span>
									                 	<span class="option pull-right"> 
									                 		<a href="javascript:void(0);" class="${product.amount > 1 ? 'btn-add':'btn-del'} ${normal?'j_delProduct':''}" data-min="${product.min}:${product.minCode}" 
										                  		data-key="${key}" data-cid="${product.id}">-</a>
										                  	<input type="text" class="op-txt j_textProduct" value="${product.amount }" maxlength="4" data-org="${product.amount}" 
										                  		data-min="${product.min}:${product.minCode}" data-max="${product.max}:${product.maxCode}"
													            id="productInput_${key}" data-key="${key}" data-cid="${product.id}" ${normal?'':'disabled="disabled"'} >										                  	
										                  	<a href="javascript:void(0);" class="btn-add ${normal?'j_addProduct':''}" data-max="${product.max}:${product.maxCode}" 
										                  		data-key="${key}" data-cid="${product.id}">+</a>
									                  	</span> 
									                </div>
									                <a href="javascript:void(0);"><i data="${key}" class="discounts icon-uniE602 newsc j_deleteProductShopCart"></i></a>
												</div>												
								            </div>
								            
								            <!-- 错误提示 s -->
								            <c:if test="${not empty productErrorReminder[key]}">												
												 <div data-shllerId=${shop.sellerId } ${product.check?'data-valid="error"':''} class="prompt text-danger">${productErrorReminder[key].message}</div>
											</c:if>
											<!-- 错误提示 e -->
											
											<c:if test="${not empty product.productPromotion.affixPromoiton and not empty product.productPromotion.affixProductList}">
												<div class="prompt2">	
												<dl class="list-entry clearfix">	
												<dt><span class="new-hz-label label-danger">附赠&nbsp;</span></dt>	
												<dd>			
												<c:forEach items="${product.productPromotion.affixProductList}" var="affixProductList">
									       				
								       				<p>
								       					<span class="new-hz-yc"><a target='_blank' href="<%=wapstatic%>${affixProductList.productSkuId}.html">									       					
								       						${affixProductList.productTitle }&nbsp;${affixProductList.skuAttrValue }
								       						</a>
								       					</span>									       				
									       				<c:choose>
									       					<c:when test="${empty giftStockMap[fn:trim(affixProductList.productSkuId)] or giftStockMap[fn:trim(affixProductList.productSkuId)] == 0}">
									       						<span class="text-danger new-hz-sl">
									       							× ${affixProductList.num * product.amount}&nbsp;&nbsp;已送完
											       				</span>
									       					</c:when>
									       					<c:when test="${giftStockMap[fn:trim(affixProductList.productSkuId)] < affixProductList.num * product.amount }">
									       						<span class="text-danger new-hz-sl">
									       							× ${affixProductList.num * product.amount}&nbsp;&nbsp;库存不足
											       				</span>
									       					</c:when>
									       					<c:otherwise>
									       						<span class="text-danger new-hz-sl">
									       							× ${affixProductList.num * product.amount}
											       				</span>
									       					</c:otherwise>
									       				</c:choose>
									       				     
									       			</p>
									       		</c:forEach> 
									       		</dd>
									       		</dl>
									       		</div>       	
									        </c:if>	
											
								            <div class="cxyf-mk clearfix">
								            	<c:set var="orderPromotionSet" value="${product.productPromotion.sortedOrderPromtotions}"/>
								            	<c:if test="${not empty orderPromotionSet}">
								            		<a href="javascript:void(0);" class="pull-left hd-mk j_showPromotionInfo" data="${key}">
								            			促销活动优惠 <i class="icon-uniE61D"></i>
								            		</a>
								            		<!-- 促销活动优惠弹出框 s -->
													<div  id="promotion_box_${key}" name="promotion_box_" class="hd-popup" style="display:none;">
														<div class="hd-ppup-tit">
															<a href="javascript:void(0);" class="hd-ppup-wz j_showPromotionInfo" data="${key}">促销活动优惠 <i class="icon-uniE61D"></i></a>
														</div>														
														<div class="hd-ppup-list">
															<c:forEach items="${orderPromotionSet}" varStatus="status" var="orderPromotion" >
																<div class="row pad5">
																	<div class="col-xs-12">
																		<input name="promotion_box_${key}" id="${shop.sellerId}_${shopCartItemSet.id}_${key}_${orderPromotion.id}" type="radio" 
																			<c:if test="${shopCartItemSet.promtionInfo.id == orderPromotion.id}">checked</c:if> 
																			value="${orderPromotion.id}" class="shop-radio" style="display:none;"/>																		
																		<label for="${shop.sellerId}_${shopCartItemSet.id}_${key}_${orderPromotion.id}" class="shop-radio">
																		<span class="text-danger">${orderPromotion.typeName}</span>&nbsp;&nbsp;${orderPromotion.name}</label>																		
																	</div>																	
																</div>
															</c:forEach>															
															<div class="row pad5">																	
																	<div class="col-xs-12">
																		<input name="promotion_box_${key}" id="${shop.sellerId}_${shopCartItemSet.id}_${key}_0" type="radio" 
																			<c:if test="${shopCartItemSet.promtionInfo.id == orderPromotion.id}">checked</c:if> 
																			value="0" class="shop-radio" style="display:none;"/>																		
																		<label for="${shop.sellerId}_${shopCartItemSet.id}_${key}_0" class="shop-radio">不使用优惠</label>																		
																	</div>	
																</div>															
															<div class="ppup-btn boder-top text-center">
																<a href="javascript:void(0);" class="btn btn-my btn-danger btn-sm j_showPromotionInfo" data="${key}">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																<a href="javascript:void(0);" data="${key}" org_data="${key},${product.productPromotion.defaultOrderPromtotion.id}" class="btn btn-my btn-success btn-sm j_selectPromotionInfo">确定</a>
															</div>
														</div>
													</div>																			      	
											      	<!-- 促销活动优惠弹出框 e -->							            		
								            	</c:if>								            	
								            	<span class="pull-right">
								            	<%--
													<c:if test="${isLogin and loginType =='02' and product.pvalue>0}">
												  		<p class="red">积分: <fmt:formatNumber pattern="0.00" value="${product.pvalue* product.amount}"/>&nbsp;
												  	</c:if>	 --%>											  	
								            		<c:if test="${product.check and product.freight > 0}">
														加收运费<font class="text-danger">
															<fmt:formatNumber pattern="0.00" value="${product.freight * product.amount}" />
							            				</font>
						            				</c:if>
								            	</span>
								            </div>								           					            							            
										</li>																					
							        </ul>
								</c:if>
								<!-- 单品 -->
								<!-- 套餐 -->
								<c:if test="${fn:startsWith(key, 'c_')}">
									<div class="lst">									
								        <div class="lst-hd">
									        <div class=" clearfix tc-tit">
										        <span class="pull-left">
													<input type="checkbox" class="icon-checkbox j_childProduct" name="comipition_${product.id}" id="comipition_${product.id}"
														value="${key}" ${normal ? '' : 'disabled="disabled"'} 
														<c:if test="${product.check}">checked</c:if>/>
										        </span>        
										        <font class="pull-left">
										        	<a class="label label-danger bq-font">套餐</a><i class="tux3"></i>										        	
										        </font>
										        <label class="text-danger pull-left">
										        	￥<fmt:formatNumber pattern="0.00" value="${product.finalPrice }" />										        						        	 
										        </label>										        					       
										        <font class="option pull-left padri-2">									        								                	
										       		<a href="javascript:void(0);" class="${product.amount > 1 ? 'btn-add':'btn-del'} ${normal?'j_delProduct':''}" data-min="${product.min}:${product.minCode}" 
								                  		data-key="${key}" data-cid="${product.id}">-</a>
								                  	<input type="text" class="op-txt j_textProduct" value="${product.amount }" maxlength="4" data-org="${product.amount}" 
								                  		data-min="${product.min}:${product.minCode}" data-max="${product.max}:${product.maxCode}"
											            id="productInput_${key}" data-key="${key}" data-cid="${product.id}" ${normal?'':'disabled="disabled"'} >										                  	
								                  	<a href="javascript:void(0);" class="btn-add ${normal?'j_addProduct':''}" data-max="${product.max}:${product.maxCode}" 
								                  		data-key="${key}" data-cid="${product.id}">+</a>
										        </font>										        	 
										        <a href="javascript:void(0);"><i data="${key}" class="discounts icon-uniE602 tcsc j_deleteProductShopCart"></i></a>
									        </div>
									        <!-- 错误提示 s -->
								            <c:if test="${not empty productErrorReminder[key]}">												
												 &nbsp;&nbsp;&nbsp;&nbsp;
												 <em class="prompt text-danger" data-shllerId=${shop.sellerId } ${product.check?'data-valid="error"':''}>
												 	${productErrorReminder[key].message}
												 </em>
											</c:if>
											<!-- 错误提示 e -->	
								        </div>								       
										<ul class="tabs-lst">
											<c:forEach items="${product.productList }" var="c_product">
												<li class="border-0 ">
													<div class="check-wrapper yuand"></div>
													<div class="list-descriptions bord-cor">
														<div class="list-thumb">
															<a target="_blank" href="<%=wapstatic%>${c_product.productSkuId}.html">
															<img src="<%=picpath%>${fn:replace(c_product.imagePath, '.jpg', '_7.jpg')}"></a>
														</div>
														<div class="list-descriptions-wrapper">
															<div class="product-name cart-name">
																<a target="_blank" href="<%=wapstatic%>${c_product.productSkuId}.html">${c_product.title}&nbsp;&nbsp;${c_product.skuAttrValue}</a>&nbsp;&nbsp;&nbsp;&nbsp;X&nbsp;${c_product.amount * product.amount}
															</div>
															<div class="clearfix price-mk">
																<span class="pull-left text-danger jg-wz">
																	￥<fmt:formatNumber pattern="0.00" value="${c_product.finalPrice}" /> 
																</span>
															</div>
														</div>
													</div>													
												</li>
											</c:forEach>
										</ul>										
									</div>
								</c:if>
								<!-- 套餐 -->
							</c:forEach>
							<!-- 遍历商品  e-->
							
							<!-- 小计 s -->	
							<c:if test="${not empty shopCartItemSet.promtionInfo}">							
								<div class="js-mk">								
									小计：<a class="text-danger">
										<span>￥<fmt:formatNumber pattern="0.00" value="${shopCartItemSet.checkTotalMoney }"/></span>
									</a>
								</div>
							</c:if>
							<!-- 小计 e -->							
						</div>
					</c:forEach>	
								
					<!-- 总计 s -->
					<div class="payment-total-bar">
						<div class="cart-info"> 
							<strong class="cart-total">
								总计（不含运费 )：<span>￥<fmt:formatNumber pattern="0.00" value="${shop.checkTotalMoney}"/></span>
							</strong> 
							<span class="sale-off"> 
								加购价: <span class="bottom-bar-price">+￥<fmt:formatNumber pattern="0.00" value="${shop.additionalMoney}"/></span> 
								满减: <span class="bottom-bar-price">-￥<fmt:formatNumber pattern="0.00" value="${shop.reductionMoney}"/></span> 								
							</span> 
							<a class="btn-right-block checkbox-inline btn-success j_gotoSettlement" data-islogin="${isLogin}" 
								data-check="${shop.checkIds}" data-sellerId="${shop.sellerId}">
								去结算(${shop.checkedProductCount})
							</a>
						</div>
					</div>
					<!-- 总计 e -->
				</c:forEach>
				<div class="lst">
					<div class="lst-hd">
						<input type="checkbox" class="icon-checkbox j_checkAll" />
						<span>全选</span>
						<span><a  href="javascript:void(0);" data="${shopcart.checkedProductCount}" class="deleteShopChecked">删除</a></span>
						<span><a target="_blank" href="<%=wapPath %>">继续购物</a></span>
					</div>
				</div>	
			</c:if>
		</div>
	</section>
</div>

<form id="gotoSettlementForm" action="/wap/wapSettlement.action?_r=<%=randomTime %>" data-wapPortal="<%=wapPortal %>" method="post">
	<input name="ids" value="" type="hidden"/>
</form>
