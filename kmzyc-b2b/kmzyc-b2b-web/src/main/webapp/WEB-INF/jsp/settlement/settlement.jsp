<%@ page language="java" pageEncoding="UTF-8"
	import="java.util.*,com.kmzyc.framework.constants.Constants,com.km.framework.common.util.MD5,com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="kh" uri="kh"%>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>

<meta name="Keywords" content="" />
<meta name="Description" content="" />

<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="结算"></jsp:param>
</jsp:include>
</head>
<%
  String path = request.getContextPath();
  String basePath =
      request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
          + path + "/";
%>

<body class="l-w1000">
	<%@ include file="/html/common/portal-common-top.jsp"%>
	<tiles:insertDefinition name="settlementTop" />
	<div class="orderinfo l-w">
		<div class="orderinfo-hd fn-clear">
			<h2 class="orderinfo-tit fn-left">填写订单信息</h2>
		</div>
		<div class="orderinfo-bd">
			<aa:zone name="addressZone">
				<div class="orderinfo-item">
					<div class="orderinfo-item-tit">
						<strong>收货人信息</strong> <span class="orderinfo-action"
							id="editAddressInfoId"
							<s:if test="#request.addressList==null || #request.addressList.size==0">style="display: none"</s:if>><a
							href="javascript:void(0);" class="j_showAddressinfo">[修改]</a></span>

					</div>
					<div class="box-wrap" id="editandSaveAddressinfo">
						<s:if
							test="#request.addressList!=null || #request.addressList.size>0">
							<div class="" id="allAddressInfo">
								<div class="sbox" id="defaultAddresssInfo">
									<div class="s-content">
										<p>
											<s:iterator value="#request.addressList" var="addr">
												<s:if test="${addr.isChecked}">
													<label> <strong>${addr.name}</strong>&nbsp;
														${addr.province} ${addr.city} ${addr.area}
														${addr.detailedAddress}&nbsp; ${addr.cellphone} &nbsp;
													</label>
												</s:if>
											</s:iterator>
										</p>
									</div>
								</div>
							</div>
						</s:if>


						<form action="/settlement/addFirstAddressInfo.action"
							id="editAddressForm" name="editAddressForm">
							<div class="box-form" id="addressListInfo"
								<s:if test="#request.addressList.size>0">style="display: none"</s:if>>

								<s:if test="#request.addressList.size==1">
									<input type="hidden" value='1' id="addrsCout">
								</s:if>
								<s:else>
									<input type="hidden" value='' id="addrsCout">
								</s:else>
								<div class="receiver-list" id="receiverList">
									<s:iterator value="#request.addressList" var="addr">
										<div class="receiver-list-item j_receiver"
											id='j_receiver_${addr.addressId}'>
											<input type="radio" class="hookbox j_hidenewAddress"
												name="defaultAddressId" id=${addr.addressId
												} value="${addr.addressId}"
												<s:if test="${addr.isChecked}">checked="checked" </s:if> />
											<s:if test="${addr.isChecked}">
												<input type='hidden' value='${addr.addressId}'
													id="defaultAddressFlagId">
											</s:if>
											<label for="${addr.addressId}" id='lab_${addr.addressId}'>
												<strong>${addr.name}</strong>&nbsp; ${addr.province}
												${addr.city} ${addr.area}
												${addr.detailedAddress}&nbsp;${addr.cellphone} &nbsp;
											</label> <span class="item-action"> <a
												id='a_${addr.addressId}' href="javascript:void(0);"
												class="j_editAddressInfo" data-name="${addr.name}"
												data-province='${addr.province}' data-city='${addr.city}'
												data-area='${addr.area}'
												data-detailedAddress='${addr.detailedAddress}'
												data-postalcode='${addr.postalcode}'
												data-cellphone='${addr.cellphone}'
												data-naddressId='${addr.addressId}'
												data-email='${addr.email}'
												data-telephone='${addr.telephone}'>编辑</a> &nbsp;
												<s:if test="#addr.status==1"> <a
												class="j_deleteAddressInfo"
												data-addressId="${addr.addressId}"
												href="javascript:void(0);">删除</a>&nbsp;</s:if>
											</span>
										</div>
									</s:iterator>
								</div>
								<!-- 这个判断看不懂 -->
								<s:if test="#request.addressList.size>9">
									<div class="receiver-list-item" >
										<input type="radio" class="hookbox j_defaultAddressId"
											date-defaultFlag="0" name="defaultAddressId" value=""
											id="defaultAddressId"
											<s:if test="#request.addressList.size==0">checked="checked"</s:if> />
										<label for="defaultAddressId">使用新地址 </label>
									</div>
								</s:if>
								<s:else>
									<div class="receiver-list-item">
										<input type="radio" class="hookbox j_defaultAddressId"
											date-defaultFlag="0" name="defaultAddressId" value=""
											id="defaultAddressId"
											<s:if test="#request.addressList.size==0">checked="checked"</s:if> />
										<label for="defaultAddressId">使用新地址 </label>
									</div>
								</s:else>

								<div class="receiver-form" id="newAddress"
									<s:if test="#request.addressList.size>0">style="display: none"</s:if>>
									<div class="list">
										<span class="label"><em>*</em>&nbsp;&nbsp;&nbsp;&nbsp;收货人：</span>
										<div class="field">
											<input type="text" class="textbox" id="name" name="name"
												maxlength="15" />
										</div>
									</div>
									<div class="list select-address">
										<span class="label"><em>*</em>所在地区：</span>
										<div class="field">
											<span><select id="province" name="province" class="j_AddAddressinfo"></select></span>
											<span><select id="city" name="city"></select></span> <span><select
												id="area" name="area"></select> </span> <em class="fn-left">邮编：</em><input
												type="text" class="textbox" id="postalcode"
												name="postalcode" maxlength="6" />
										</div>
									</div>
									<div class="list full-address">
										<span class="label"><em>*</em>详细地址：</span>
										<div class="field">
											<input type="text" class="textbox" id="detailedAddress"
												name="detailedAddress" maxlength="60" style="width: 400px;" />
										</div>
									</div>

									<div class="list" id="call_div">
										<span class="label"><em>*</em>手机号码：</span>
										<div class="field">
											<div class="phone">
												<input type="text" class="textbox" id="mobile" name="mobile"
													maxlength="11" />
											</div>
										</div>
										<input type="hidden" value="" name="naddressId"
											id="naddressId" /> <input type="hidden" value="NEW"
											name="action" id="action" />
									</div>

								</div>
								<div class="form-btn">
									<a href="javascript:void(0);" class="btn-submit"
										name="submitAddress" id="submitAddress"><span>确认收货信息</span></a>
								</div>

							</div>
						</form>

					</div>
				</div>
			</aa:zone>
		</div>
		<!-- 支付及配送 -->

		<div class="orderinfo-item">
			<div class="orderinfo-item-tit">
				<strong>支付和配送信息</strong><span class="orderinfo-action"><a
					href="javascript:void(0);" id="editPayModelId">[修改]</a></span>
			</div>
			<div class="orderinfo-item-cont">
				<div class="box-wrap">
					<div class="box-cont payment-info" id="defaultPayModelAndDelivery" style="display: none;">
							<dl>
								<dt>支付方式：</dt>
								<dd id="paymodelvalueText">
										在线支付
									</dd>
							</dl>
							<dl>
								<dt>配送方式：</dt>
								<dd id="deliveryId">
									快递 + <em class="fn-red" id="deliveryFee">￥${fare}</em>
									<p id="deliverytimeAndISconfirm">
									</p>
								</dd>
							</dl>
					</div>
					<form action="/settlement/savePaymodelDeliveryInfo.action"
						id="payModel" name="payAndDeliveryForm">
						<div class="payment">
							<dl>
								<dt>支付方式：</dt>
								<dd>
									<%-- <s:iterator value="payModelList" var="pay">
                                        <span class="label"> <input type="radio"
                                            class="hookbox" value="${pay.orderDictionaryKey}"
                                            name="paymodelvalue" id="payModelRadio" 
                                            <c:if test="${pay.orderDictionaryKey==5}">checked="checked"</c:if>                                            
                                            > 
                                            <label>${pay.orderDictionaryValue}</label></span>
                                    </s:iterator> --%>
									<span class="label"> <input type="radio" class="hookbox"
										value="5" name="paymodelvalue" id="payModelRadio"
										checked="checked" /> <label>在线支付</label></span>

								</dd>
							</dl>
							<dl>
								<dt>配送方式：</dt>
								<dd>
									<div class="fn-clear">
										<span class="label"> <input type="radio"
											class="hookbox" value="1" checked="checked" /> <label>快递</label></span>
									</div>
									<div class="express">

										<select id="deliveryTime" name="deliveryTimeValue">
											<option value="1">工作日送货</option>
											<option value="2">休息日送货</option>
											<option value="3">工作日/休息日皆可送货</option>
										</select>
									</div>
									<div class="fn-clear">
										<span class="label">是否送货前确认：</span> <span class="label"><input
											type="radio" name="isconfirmValue" class="hookbox" value="1"/>
											<label>是</label></span> <span class="label"> <input
											type="radio" name="isconfirmValue" checked="checked"
											class="hookbox" value="0"/>
											<label>否</label></span>
									</div>
									<p class="notes">
										温馨提示：<br />1.我们会努力按照您指定的时间配送，但因天气、交通等各类因素影响，您的订单有可能会有延误现象！<br />2.部分服务仅在配送区域提供，非配送区域无法选择！
									</p>
								</dd>
							</dl>
						</div>
						<div class="form-btn">
							<a class="btn-submit j_submitPayModel" id="j_submitPayModel" href="javascript:void(0);"><span>确认支付配送</span></a>
						</div>
					</form>
				</div>
			</div>
		</div>

		<!-- 发票信息 -->
		<div class="orderinfo-item">
			<div class="orderinfo-item-tit">
				<strong>发票信息</strong><span class="orderinfo-action"><a
					href="javascript:void(0);" id="editInvoiceInfoId">[修改]</a></span>
			</div>
			<div class="orderinfo-item-cont">
				<div class="box-wrap">
					<div class="box-cont" id="invoiceInfo">不需要发票</div>

					<form action="">
						<label class="invoice fn-clear" id="editInvoiceInfoForm"
							style="display: none"> <input type="checkbox" value=""
							name="isNeed" id="isNeed" />索取发票
						</label>
						<div class="payment" style="display: none" id="invoiceinfoId">
							<dl>
								<dt>发票类型：</dt>
								<dd>
									<span class="label"> <input type="radio" class="hookbox"
										checked="checked" value="普通发票" id="invoiceType"
										name="invoiceType" /> <label>普通发票</label></span>
									<c:if test="${commercialTenantBasicInfo.ataxVerify==1}">
										<span class="label"> <input type="radio"
											class="hookbox" value="增值税发票" id="invoiceType"
											name="invoiceType" /> <label>增值税发票</label> <input
											type="hidden" id="companyName"
											value="${commercialTenantBasicInfo.corporateName}" />
										</span>
									</c:if>
								</dd>
							</dl>
							<dl>
								<dt>发票抬头：</dt>
								<dd>
									<input type="text" maxlength="30" value="请输入“个人姓名”或者“企业全称”"
										class="fn-text" id="title" />
								</dd>
							</dl>
							<dl style="display: none">
								<dt>发票内容：</dt>
								<dd>
									<select id="invoiceContent" name="invoiceContent">
										<option selected="selected" value=" ">明细</option>
									</select>
								</dd>
							</dl>
						</div>
					</form>
					<div class="form-btn" id="divSureInvoiceInfo"
						style="display: none;">
						<input type="reset" style="display: none;" /> <a
							class="btn-submit" id="btnSureInvoiceInfo"
							href="javascript:void(0);"><span>确认发票信息</span></a>
					</div>
				</div>
			</div>
		</div>
		<!-- 订单商品 -->
		<div class="orderinfo-item">
			<div class="orderinfo-item-tit">
				<strong>订单商品</strong>
			</div>
			<div class="orderinfo-item-cont">
				<div class="box-wrap">
					<div class="order-review">
						<div class="order-review-slide">
							<div class="control fn-left">
								<span class="fn-red">${shopCart.checkedProductCount}件商品 </span><a
									href="javascript:void(0);" class="more j_showshopCartList">[查看商品详细列表]</a><input
									type="hidden" value="0" id="showShopCarFlag" />
							</div>
							<div class="fn-clear" id="topCooutDiv">
								<span class="slide-num fn-right"> <strong class="fn-red">${shopCart.checkedProductCount}</strong>件商品，
									金额总计（不含运费）：<em>￥ <fmt:formatNumber pattern="0.00"
											value="${shopCart.checkTotalMoney}" /></em>
								</span>
							</div>
						</div>
						<div class="order-review-info fn-hide" id="shopCartListInfoId">
							<div class="shopcart-thead fn-clear">
								<div class="column t-goods">商品</div>
								<div class="column t-quantity">数量</div>
								<div class="column t-promotion">特价/折扣</div>
								<div class="column t-price">总计</div>
							</div>
							<div class="shopcart-tbody">
								<c:if test="${not empty carPromotionProductMap}">
									<c:forEach var="carProductMap"
										items="${carPromotionProductMap}">
										<c:set value="${carProductMap.value}" var="carProduct"></c:set>
										<c:if test="${fn:startsWith(carProductMap.key, 'n_')}">
											<div class="item fn-clear">
												<div class="item-form">
													<div class="cell c-settle">
														<div class="p-img">
															<a target="_blank"
																href="${cmsRootPath}products/${carProduct.id}.html">
																<img width="65"
																onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"
																height="65"
																src="${picPath}${fn:replace(carProduct.imagePath, '.jpg', '_7.jpg')}"
																clstag="clickcart|keycount|xincart|p-imglistcart" />
															</a>
														</div>
														<div class="p-name">
															<a target="_blank"
																href="${cmsRootPath}products/${carProduct.id}.html">${carProduct.title}</a>
														</div>
													</div>
													<div class="cell c-quantity">
														<div class="quantity-form">x ${carProduct.amount}</div>
													</div>
													<%--<div class="cell c-integral">
													<fmt:formatNumber pattern="0.00"
														value="${carProduct.value.finalPrice}" />
												</div>
												 --%>
													<div class="cell c-price-x">
														<c:if test="${not empty carProduct.productPromotion.pricePromotion}">
															<s:if test="${carProduct.productPromotion.pricePromotion.type=='10'}">
															<i class="ico-sales">特价</i> </s:if>
															<s:else><i class="ico-sales">打折</i></s:else></c:if>
															¥<fmt:formatNumber pattern="0.00"
																value="${carProduct.finalPrice}" />
													</div>
													<div class="cell c-price">
														<span>￥<fmt:formatNumber pattern="0.00"
																value="${carProduct.productPriceTotal}" /></span>
													</div>
												</div>
											</div>
										</c:if>
										<c:if test="${fn:startsWith(carProductMap.key, 'c_')}">
											<div class="item-meet  item-combo">
												<div class="item-head">
													<div class="cell f-text">
														<b></b>
														<div class="f-tips">套餐更超值！</div>
													</div>
													<div class="cell f-quantity">
														<div class="quantity-form">x&nbsp;${carProduct.amount}</div>
													</div>
													<div class="cell c-discount"></div>
													<div class="cell f-price text-center" >
														<span>¥<fmt:formatNumber pattern="0.00"
																value="${carProduct.productPriceTotal}" /></span>
													</div>
												</div>
												<c:forEach var="ccProduct"
													items="${carProduct.productList}">
													<div class="item fn-clear">
														<div class="item-form">
															<div class="cell c-settle">
																<div class="p-img">
																	<a target="_blank"
																		href="${cmsRootPath}products/${ccProduct.productSkuId}.html"><img
																		width="65" height="65"
																		onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"
																		src="${picPath}${fn:replace(ccProduct.imagePath, '.jpg', '_7.jpg')}" /></a>
																</div>
																<div class="p-name">
																	<a target="_blank"
																		href="${cmsRootPath}products/${ccProduct.productSkuId}.html">${ccProduct.title}</a>
																</div>
															</div>
															<div class="cell c-quantity">
																<div class="quantity-form">x${ccProduct.amount*carProduct.amount}</div>
															</div>
															<!--
														<div class="cell c-integral"></div>-->

															<div class="cell c-promotion"></div>

															<div class="cell c-price-x">
																<span></span>
															</div>
														</div>
													</div>
												</c:forEach>
											</div>
										</c:if>
									</c:forEach>
								</c:if>
								
								<div class="cartfull full-minus">
								<c:if test="${not empty shopCart.meetOrderPromotionList or not empty carPromotionProductMap}">
									<div class="cartfull-hd">您购物车中的商品已享受以下促销优惠</div>
									<c:if test="${not empty shopCart.meetOrderPromotionList}">										
										<c:forEach items="${shopCart.meetOrderPromotionList}"
											var="myPromotionInfo">
											<div class="cartfull-item">
												<ul class="raise-rule">
													<li class="raise-gift"
														id="raise-gift${myPromotionInfo.promtionInfo.id}"><i
														class="ico-sales">${promotionTypeMap[myPromotionInfo.promtionInfo.promotionType]}</i><span>${myPromotionInfo.promtionInfo.name}</span>
														<div class="goods-list">
															<a href="javascript:void(0);"
																data-value1="${myPromotionInfo.promtionInfo.id}"
																class="j_showProductListUl">
																${myPromotionInfo.promtionInfo.name}...<fmt:formatNumber
																	pattern="0.00"
																	value="${myPromotionInfo.uncalculateMoney}" />元
															</a>
															<ul id="pruductListul_${myPromotionInfo.promtionInfo.id}"
																class="goods-list-cont j_disshowProductListUl"
																style="display: none;">
																<c:set var="totalPrice" value="0"></c:set>
																<c:forEach items="${myPromotionInfo.carProducts}"
																	var="myOrderProductId">
																	<c:set
																		value="${carPromotionProductMap[myOrderProductId]}"
																		var="product"></c:set>
																	<li><span class="name">${product.title}</span>&nbsp;&nbsp;&nbsp;&nbsp;
																		<font>x</font>${product.amount} &nbsp;&nbsp;<fmt:formatNumber
																			pattern="0.00" value="${product.productPriceTotal}" />元
																	</li>
																</c:forEach>
																<li class="subtotal">小计：<fmt:formatNumber
																		pattern="0.00"
																		value="${myPromotionInfo.uncalculateMoney}" />元
																</li>
															</ul>
														</div></li>
													<c:forEach items="${myPromotionInfo.gifts}"
														var="giftProduct">
														<c:set value="${shopCart.giftStockMap[giftProduct.id]}" var="stock"></c:set>
														<li class="raise-buy"
															id="giftPromotion${myPromotionInfo.promtionInfo.id}">
															<span class="fn-red">[加${giftProduct.price * giftProduct.amount}元购]</span>
															&nbsp; <a target="_blank"
															href="${cmsRootPath}products/${giftProduct.id}.html">${giftProduct.name} &nbsp;&nbsp;&nbsp;&nbsp;x&nbsp;${giftProduct.amount }</a>
															<c:if test="${empty stock or stock<giftProduct.amount}">&nbsp;无库存，请删除</c:if>
														</li>
													</c:forEach>
													<c:forEach
														items="${myPromotionInfo.rulePresents[myPromotionInfo.defaultPresents]}"
														var="giftProduct">
														<c:set value="${shopCart.giftStockMap[giftProduct.id]}" var="stock"></c:set>
														<c:if test="${not empty stock and stock>=giftProduct.amount}"><li class="raise-buy"
															id="giftPromotion${myPromotionInfo.promtionInfo.id}">
															<span class="fn-red">[赠品]</span>&nbsp; <a target="_blank" href="${cmsRootPath}products/${giftProduct.id}.html">${giftProduct.name}</a>
															<c:if test="${not empty stock or stock >= giftProduct.amount}">&nbsp;&nbsp;&nbsp;&nbsp;x&nbsp;${giftProduct.amount }</c:if>
														</li></c:if>
													</c:forEach>
												</ul>
											</div>
										</c:forEach>
									</c:if>
									
									<c:if test="${not empty carPromotionProductMap}">
										<c:forEach var="carProductMap" items="${carPromotionProductMap}">
											<c:set value="${carProductMap.value}" var="carProduct"></c:set>
											<c:if test="${fn:startsWith(carProductMap.key, 'n_')}">
											<c:if test="${not empty carProduct.productPromotion.affixPromoiton 
												and not empty carProduct.productPromotion.affixProductList}">
											
												<c:if test="${carProduct.productPromotion.affixPromoiton.type == 11}">													
													<div class="cartfull-item">
														<ul class="raise-rule">
															<li class="raise-gift"><i class="ico-sales"></i>
																<span>${carProduct.title}</span>
															</li>
															<c:forEach items="${carProduct.productPromotion.affixProductList}" var="affixProduct">
																<c:set value="${shopCart.giftStockMap[fn:trim(affixProduct.productSkuId)]}" var="gantstock"></c:set>
																<c:if test="${not empty gantstock and gantstock != 0}">
																<li class="raise-buy">
																	<span class="fn-red">[附赠]</span>
																	<a target="_blank" href="${cmsRootPath}products/${affixProduct.productSkuId}.html">
																		${affixProduct.productTitle } &nbsp;${affixProduct.skuAttrValue }&nbsp;&nbsp;&nbsp;x&nbsp;
																		${affixProduct.num * carProduct.amount }
																		<c:if test="${gantstock < affixProduct.num * carProduct.amount }">
																			&nbsp; 库存不足
																		</c:if>																																		
																	</a>																	
																</li>
																</c:if>
															</c:forEach>
														</ul>
													</div>													
												</c:if>												
											
											</c:if>
											</c:if>		
										</c:forEach>	
									</c:if>		
									</c:if>			
								</div>
								
								<c:if test="${not empty timeProductMap}">
									<c:forEach items="${timeProductMap}" var="timeProduct">
										<span class="fn-red">[赠品] </span>&nbsp;
									<a target="_blank"
											href="${cmsRootPath}products/${timeProduct.value.productSkuId}.html">${timeProduct.value.title}</a>x${timeProduct.value.amount}件
								<c:if test="${timeProduct.value.stockCount==0}">&nbsp;已送完</c:if>
										</br>
									</c:forEach>
								</c:if>

								<div class="shopcart-toolbar fn-clear">
									<div class="control fn-left">
										<a class="j_hideShopCarList" href="javascript:void(0);">[隐藏商品详细列表]</a>
									</div>
									<div class="total fn-right">
										<p>
											<i>总计：</i><span><strong>¥ <fmt:formatNumber
														pattern="0.00" value="${shopCart.uncalculateMoney}" />
											</strong></span>
										</p>
										<p>
											<i>加价购： </i><span class="" id="additionalMoney">+¥<fmt:formatNumber
													pattern="0.00" value="${shopCart.additionalMoney}" /></span>
										</p>
										<p>
											<i>满减：</i><span class="fn-green">-¥ <fmt:formatNumber
													pattern="0.00" value="${shopCart.reductionMoney}" /></span>
										</p>

										<p>
											<c:if test="${isLoginType && priceInfo.supplierType==4}">
												<i>时代${priceInfo.eraGradeName}会员享 <fmt:formatNumber
														pattern="0" value="${priceInfo.eraGradeRate}" />折优惠：
												</i>
												<span>-¥<fmt:formatNumber pattern="0.00"
														value="${priceInfo.discountMoney}" /></span>
											</c:if>
										</p>
									</div>
								</div>
								<div class="fn-clear">
									<span class="slide-num fn-right"> <strong class="fn-red">${shopCart.checkedProductCount}</strong>件商品，
										金额总计（不含运费）：<em>￥ <fmt:formatNumber pattern="0.00"
												value="${shopCart.checkTotalMoney}" />

									</em>
									</span>
								</div>
								<s:if
									test="#session.user != null && #session.user.loginAccount != null">
									<div class="privilege">
										<p>
											<i>可获得优惠券：</i><span>+ <fmt:formatNumber pattern="0.00"
													value="${priceInfo.couponMoney}" /></span>
										</p>
										<p>
											<i>会员积分：</i><span>+ ${priceInfo.scoreCount}分</span>
										</p>
									</div>
								</s:if>
							</div>
						</div>



						<div class="shopcart-toolbar fn-clear">
							<form action="/settlement/saveAndPayOrder.action"
								name="orderForm" id="orderForm" method="post">
								<div class="control fn-left" style="width: 500px;">
									<div class="remark">
										<%
										  if (!Constants.PRESCRIPTION_BUY.equals(request.getAttribute("buyType"))) {
										%>
										<span class="label" style="font-weight: bold;"><label><input
												type="checkbox" name="orderDescription_" id="orderDescription_" checked="checked"
												autocomplete="off" value="1" />订单备注：</label></span> <input
											autocomplete="off" type="text" maxlength="100"
											placeholder="请输入订单备注信息" value="" class="fn-text J_reminder"
											name="orderDescription" id="orderDescription" />
										<%
										  } else {
										%>
										<span class="label"><label><input
												type="checkbox" name="orderDescription_"  id="orderDescription_" autocomplete="off"
												value="1" checked="checked" />订单备注：</label></span> <input
											autocomplete="off" type="text" maxlength="100" id="orderDescription"
											value="本单为按方抓药订单，请将订单商品平均分为${buyNum}份分包" readonly="readonly"
											class="fn-text J_reminder" name="orderDescription" />
										<%
										  }
										%>
									</div>

									<div class="coupon" id="couponDiv">
										<div class="label">
											<s:if test="${!isLoginType ||priceInfo.supplierType!=4}">
												<input type="hidden" id="activityChannel" value="${activityChannel }"/>   
												<label><input type="checkbox" autocomplete="off" id="useConpon" name="preferential" value="${preferential}" />使用优惠券</label>
											</s:if>
										</div>
										<div id="big-box" style="display: none;">
										</div>
									</div>

									<div class="account fn-clear">
										<span class="label"> <c:if
												test="${!isLoginType ||priceInfo.supplierType!=4}">
												<input type="checkbox" id="userCountMoneyId" 
													autocomplete="off" name="balance" value="${balance}" />
												<label for="userCountMoneyId"> 先使用账户余额支付订单（账户余额 <strong
													class="fn-red" id="amountAvlibalText">￥<fmt:formatNumber
															pattern="0.00" value="${accountInfo.amountAvlibal}" /></strong>元）。
												
											</c:if> </label>
										</span>
									</div>
									<div class="account-import" style="display: none"
										id="accountImport">
										<input type="hidden" id="mobVe" value="${accountInfo.mobile}">
										<s:if test='#request.accountInfo.paymentpwd==null'> 
	                    					必须先启用支付密码才能使用余额支付！
	                    					<!-- <a class="btn-submit"
												href="/showSecurityCentre.action"><span>启用支付密码</span></a> -->
												<a class="btn-submit j_checkPay" href="javascript:void(0);"><span>启用支付密码</span></a>
										</s:if>
										<s:else>
											使用账户余额支付
                        						<input type="text"
												value='${payMoneyInfo.minMoneyFromAmountAvlibalAndPayMoney}'   oldValue="${payMoneyInfo.minMoneyFromAmountAvlibalAndPayMoney}"
												class="fn-text" name="amountAvlibal" id="amountAvlibal" />元；

                       							 <div class="pay-pas">
												密码:<input type="text" class="fn-text"
													id="passwd" onfocus="this.type='password'" autocomplete="off"/>
											</div>
											<a href="javascript:void(0);"
												data-p1="${payMoneyInfo.compareFlag}"
												class="btn-submit j_checkLogin"><span>使用余额</span></a>
										</s:else>
									</div>
									<div class="account-import" style="display: none"
										id="paidMoneyId">
										您已选择使用账户余额支付￥<label id="paidMoney" class="fn-red">0</label>元
									</div>

								</div>
								<input type="hidden" name="buyType" value="${buyType}"
									id="buyType" /> <input type="hidden" name="productCount"
									value="${productCount}" id="productCount" /> <input
									type="hidden" name="productSkuId" value="${productSkuId}"
									id="productSkuID" /> <input type="hidden" name="productSkuIDs"
									value="${productSkuIDs}" id="productSkuIDs" /> <input
									type="hidden" name="prescriptionAttachmentFile"
									value="${prescriptionAttachmentFile}"
									id="prescriptionAttachmentFile" />
							</form>
							<form action="refreshFare.action" name="refreshFareForm"></form>
							<aa:zone name="refreshFareZone">
								<div class="total fn-right">
									<p>
										<i>配送费：</i><span>+¥<fmt:formatNumber pattern="0.00"
												value="${payMoneyInfo.fare}" /></span>
									</p>
									<p>
										<i>使用优惠券：</i><span class="fn-green" id="couponMoneyAvlibal">-¥<fmt:formatNumber
												pattern="0.00" value="${payMoneyInfo.couponMoney}" /></span>
									</p>
									<p>
										<i>余额支付：</i><span class="fn-green" id="balancePaid">-¥<fmt:formatNumber
												pattern="0.00" value="${payMoneyInfo.balanceMoney}" /></span>
									</p>
									<p class="total-sum">
										<i>应付总额：</i><span id="totalMoney">¥<fmt:formatNumber
												pattern="0.00"
												value="${shopCart.uncalculateMoney-shopCart.reductionMoney +payMoneyInfo.fare+shopCart.additionalMoney-shopCart.discountMoney}" /></span>
									</p>

									<%-- <p>
										<i>可获得积分:

											<c:choose>
												<c:when test="${isLoginType}">
													<span> <fmt:formatNumber pattern="0.00" value="${priceInfo.productPvalue}" /></span>
												</c:when>
												<c:otherwise>
													<span>
                                                        <fmt:formatNumber pattern="0.00" value="0" />
                                                        <span id="pvalueHint">?</span>
													</span>
                                                    <div style="display: none;" id="pvalueHintDiv">注册成为康美中药城优惠顾客即可获得积分,注册方式请咨询4006-111-412</div>
												</c:otherwise>
											</c:choose>
										</i>
									</p> --%>
								</div>
							</aa:zone>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%
		  String utoken =
		      MD5.getMD5Str(Constants.PRESCRIPTION_BUY
		          + session.getAttribute(Constants.SESSION_USER_ID));
		%>
		<input type="hidden" id="hduid" name="hduid" value="<%=utoken%>" />
		<div class="shopcart-btn fn-clear">
			<%
			  if (!Constants.PRESCRIPTION_BUY.equals(request.getAttribute("buyType"))) {
			%>
			<a class="btn btn-returncart fn-left"
				href="<%=basePath%>cart/listShopCar.action" id="backToShopCar"></a>
			<%
			  }
			%>
			<a class="btn btn-confirmsubmit fn-right j_submitOrder"
				href="javascript:void(0);"></a> <input type="hidden" id="pageFlag"
				value="0">
		</div>
	</div>

	<%@ include file="/html/common/portal-common-foot.jsp"%>
	<div style="display: none; height: 2px; width: 85px;"
		class="tooltip top" id="errorDiv">
		<div class="tooltip-inner" id="errorMessage">我是提示信息</div>
		<div class="tooltip-arrow">
			<i></i>
		</div>
	</div>
	<div class="i-loading" id="i-loading" style="display: none;">
		<div class="load-pic"></div>
		<p>正在处理中，请稍候...</p>
	</div>
</body>
</html>
