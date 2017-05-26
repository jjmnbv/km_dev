<%@page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil,com.kmzyc.framework.constants.Constants,com.km.framework.common.util.MD5" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
pageContext.setAttribute("cssJsPath",ConfigurationUtil.getString("CSS_JS_PATH"));
pageContext.setAttribute("wapstatic",ConfigurationUtil.getString("productPicPath_WAP"));
pageContext.setAttribute("wapPath",ConfigurationUtil.getString("staticPath_WAP"));
String picpath = ConfigurationUtil.getString("picPath");
%>

<!-- 引用微信的js 20150825 mlq add -->
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<div class="container">
	<div class="row">
		<div class="col-lg-12">
 			<div class="ibox float-e-margins">
 				<div class="ibox-title">
                    <h5>收货人信息</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link" href="javascript:void(0);" id="showAddress"><i class="icon-uniE62E"></i></a>
                    </div>
                </div>
                <div class="ibox-content">    
                
               		<!-- 20150710 mlq add 新增获取微信地址按钮以及隐藏域需要用到的东西 begin -->
					<s:if test="#request.isWxAddrRequest =='wxAddress'">					
						<input type="hidden" id="nonceStr" value="<s:property value="#request.nonceStr"/>" />
						<input type="hidden" id="timeStamp" value="<s:property value="#request.timeStamp"/>" />
						<input type="hidden" id="signature" value="<s:property value="#request.signature"/>" />
						<input type="hidden" id="addrSign"  value="<s:property value="#request.addrSign"/>" />
						<input type="hidden" id="scope" value="<s:property value="#request.scope"/>" />
						<input type="hidden" id="signType" value="<s:property value="#request.signType"/>" />
					</s:if>
					
					<input type="hidden" id="isWxAddrReq" value="<s:property value="#request.isWxAddrRequest"/>"/>
					<input type="hidden" id="appId" value="<s:property value="#request.appId"/>"/>
					<!-- 20150710 mlq add 新增获取微信地址按钮   end -->   
					         	
                	<aa:zone name="addressZone">
                	<!-- isWxBrowse 的值依赖后台程序判断,所以这边放到aa:zone中 -->
                	<s:if test="#request.isWxBrowse==true">
	            		<div class="category-box">
	                  		<div class="lst" id="wxAddressDiv" style="display:none;"><a href="javascript:void(0);" class="j_getWxAddress"><div class="lst-hd lst-hd2"><font style="color:white;">使用微信地址</font></div></a></div>
	                	</div>
	                	<div id="wxAddressInfo" style="display:none"></div>
                	</s:if>
                	
                	<form action="/settlement/addAddressInfo.action" id="editAddressForm" name="editAddressForm">
                	<input id="isWap" name="isWap" type="hidden" value="1" />
                	<input type="hidden" value="" name="naddressId" id="naddressId" />
                    <s:if  test="#request.addressList!=null || #request.addressList.size>0">
                    <div>
                    	<s:iterator value="#request.addressList" var="addr">
                    	<s:if test="${addr.isChecked}">
                    	<div class="radio add_ckd">
                            <label>${addr.name}&nbsp;${addr.province}&nbsp;${addr.city}&nbsp;${addr.area}&nbsp;${addr.detailedAddress}&nbsp;${addr.cellphone} &nbsp;</label>
						</div>
                    	</s:if>
                    	</s:iterator>
                    	<div id="addressList" style="display: none;"> 
	                    	<s:iterator value="#request.addressList" var="addr">
	                        <div class="radio add_li">
	                            <label id="lb_add_${addr.addressId }">
									<input type="radio" class="hookbox j_address" name="defaultAddressId" id="rd_add_${addr.addressId }" data-value="${addr.isChecked}" value="${addr.addressId}" aname="${addr.name}"
										aprovince="${addr.province}" acity="${addr.city}" aarea="${addr.area}" adetailedAddress="${addr.detailedAddress}" acellphone=${addr.cellphone}
										<s:if test="${addr.isChecked}">checked="checked" </s:if> />
										<s:if test="${addr.isChecked}"><input type='hidden' value='${addr.addressId}' id="defaultAddressFlagId" /></s:if>
									 	${addr.name}&nbsp;
										${addr.province} ${addr.city} ${addr.area}
										${addr.detailedAddress}&nbsp;${addr.cellphone} &nbsp;
									<a href="javascript:void(0);"  class="btn-link j_editAddressInfo" data-name="${addr.name}" data-province='${addr.province}'
										data-city='${addr.city}' data-area='${addr.area}' data-detailedAddress='${addr.detailedAddress}' data-postalcode='${addr.postalcode}'
										data-cellphone='${addr.cellphone}' data-naddressId='${addr.addressId}' data-email='${addr.email}' data-telephone='${addr.telephone}'>编辑</a>
									<s:if test="${addr.status!=0 && !addr.isChecked}">							
	                                <a href="javascript:void(0);" class="btn-link j_deleteAddressInfo" class="j_deleteAddressInfo" data-addressId="${addr.addressId}">删除</a>
	                                </s:if>
								</label>
	                        </div>
	                        </s:iterator>
	                        <div class="radio">
	                            <label><input type="radio" class="hookbox j_newddress" id="j_newddress" value="" name="defaultAddressId" data-defaultFlag = "0" />&nbsp;使用新地址</label>
	                        </div>
                        </div>
                    </div>
                    </s:if>
                    <div id="editAddress" class="form-horizontal" <s:if test="#request.addressList!=null && #request.addressList.size>0">style="display: none;"</s:if>>
	                    <div class="form-group">
	                        <label class="col-sm-3 control-label">收货人<span class="asterisk">*</span></label>
	                        <div class="col-sm-9">
	                            <input type="text" class="form-control" placeholder="收货人" maxlength="15" id="name" name="name"/>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="col-sm-3 control-label">手机号码<span class="asterisk">*</span></label>
	                        <div class="col-sm-9">
	                            <input type="text" class="form-control" placeholder="手机号码" maxlength="11" id="mobile" name="mobile"/>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="col-sm-3 control-label">所在地区<span class="asterisk">*</span></label>
	                        <div class="col-sm-9">
	                            <select id="province" name="province" class="form-control inline" aria-invalid="false"></select>
	                            <select id="city" name="city" class="form-control inline" aria-invalid="false"></select>
	                            <select id="area" name="area" class="form-control inline" aria-invalid="false"></select>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="col-sm-3 control-label">邮编<span class="asterisk">*</span></label>
	                        <div class="col-sm-9">
	                            <input type="text" class="form-control" placeholder="邮编" id="postalcode" name="postalcode" maxlength="6" />
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="col-sm-3 control-label">详细地址<span class="asterisk">*</span></label>
	                        <div class="col-sm-9">
	                            <input type="text" class="form-control" placeholder="详细地址" maxlength="60" id="detailedAddress" name="detailedAddress" />
	                        </div>
	                    </div>
                    </div>
                    <div class="form-group" style="display: none;" id="j_addr_sure">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button type="button" data-p1="addressZone" id="confirmAddress" class="btn btn-my btn-success btn-sm">确认收货地址</button>
                        </div>
                    </div>
                    </form>
                    </aa:zone>
                </div>
            </div>           	
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>支付和配送信息</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link" href="javascript:void(0);" id="editPayAndDeliveryInfo"><i class="icon-uniE62E"></i></a>
                    </div>
                </div>
                <div class="ibox-content">
                	<div class="form-horizontal">
	                	<div id="paidAndDeliveryInfo">
		                 	<div class="form-group">
		                     	<label class="col-sm-4 control-label">支付方式：</label>
		                     	<div class="col-sm-8" id="payMethod">在线支付</div>
		                 	</div>
		                 	<div class="form-group">
		                     	<label class="col-sm-4 control-label">配送方式：</label>
		                     	<div class="col-sm-8" id="deliveryMethod">快递<br/>工作日/休息日皆可送货，送货前电话确认（送货前不用电话确认）</div>
		                 	</div>
		                 	<c:if test="${buyType == 'presellBuy' }">
			                 	<div class="form-group text-danger">
									<label class="col-sm-4 control-label">定金支付阶段：</label>
									<div class="col-sm-8">￥<fmt:formatNumber pattern="0.00" value="${depositPrice }"/></div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">尾款支付阶段：</label>
									<div class="col-sm-8">￥<fmt:formatNumber pattern="0.00" value="${finalPrice }"/></div>
								</div>
							</c:if>
	                 	</div>
	                 	<div id="editPayAndDelivery"  style="display: none">
		                 	<div class="form-group">
		                     	<label class="col-sm-3 control-label">支付方式：</label>
		                     	<div class="col-sm-9"><label class="checkbox-inline"><input type="checkbox" value="5" name="paymodelvalue" id="paymodelvalue" class="j_readonly" checked="checked" />在线支付</label></div>
		                 	</div>
		                 	<div class="form-group">
		                     	<label class="col-sm-3 control-label">配送方式：</label>
		                     	<div class="col-sm-9"><label class="checkbox-inline"><input name="" type="checkbox" value="1" class="j_readonly" checked="checked" />快递</label></div>
		                 	</div>
		                 	<div class="form-group">
		                     	<div class="col-sm-offset-3 col-sm-9">
		                         	<select name="deliveryTimeValue" id="deliveryTime" class="form-control">
									    <option value="1">工作日送货</option>
									    <option value="2">休息日送货</option>
									    <option value="3" selected="selected">工作日/休息日皆可送货</option>
									</select>
		                     	</div>
		                 	</div>
		                 	<div class="form-group">
		                     	<div class="col-sm-offset-3 col-sm-9">
		                         <label class="checkbox-inline">是否送货前确认：</label>
		                         <label class="checkbox-inline"><input name="isconfirmValue" id="confirmValue" type="radio" value="1" />是</label>
		                         <label class="checkbox-inline"><input name="isconfirmValue" type="radio" value="0" checked="checked" />否</label>
		                     	</div>
		                 	</div>
		                 	<c:if test="${buyType == 'presellBuy' }">
			                 	<div class="form-group text-danger">
									<label class="col-sm-4 control-label">定金支付阶段：</label>
									<div class="col-sm-8">￥<fmt:formatNumber pattern="0.00" value="${depositPrice }"/></div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">尾款支付阶段：</label>
									<div class="col-sm-8">￥<fmt:formatNumber pattern="0.00" value="${finalPrice }"/></div>
								</div>
							</c:if>
		                 	<div class="form-group">
		                     	<div class="col-sm-offset-3 col-sm-9"><button type="button" id="confirmPayAndDeliveryInfo" class="btn btn-my btn-success btn-sm">确认支付配送</button></div>
		                 	</div>
	                 	</div>
                 	</div>
                </div>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>发票信息</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link" href="javascript:void(0);" id="editInvoiceInfo"><i class="icon-uniE62E"></i></a>
                    </div>
                </div>
                <div class="ibox-content">
                    <p id="confirmedInvoiceInfo">不需要发票</p>
                    <div class="form-horizontal">
	                    <div id="showInvoice" style="display: none">
	                 		<div class="radio"><label><input type="checkbox" value="" name="isNeed" id="needInvoice">索取发票</label></div>
	                		<div class="form-group">
	                     		<label class="col-sm-3 control-label">发票类型：</label>
	                       		<div class="col-sm-9"><label class="checkbox-inline"><input type="checkbox" class="j_readonly" name="invoiceType"  checked="checked" value="普通发票" id="invoiceType" />普通发票</label></div>
	                  		</div>
	                  		<div class="form-group">
	                        	<label class="col-sm-3 control-label">发票抬头：</label>
	                        	<div class="col-sm-9"><input type="text" maxlength="50" placeholder="请输入“个人姓名”或者“企业名称”" class="form-control" id="invoiceTypeTitle" /></div>
	                      	</div>
	                      	<div class="form-group">
	                        	<label class="col-sm-3 control-label">发票内容：</label>
	                        	<div class="col-sm-9">
									<select id="invoiceContent" name="invoiceContent">
										<option selected="selected" value=" ">明细</option>
									</select>
								</div>
	                      	</div>
	                    	<div class="form-group">
	                       		<div class="col-sm-offset-3 col-sm-9"><button type="button" class="btn btn-my btn-success btn-sm" id="sureInvoiceInfo">确认发票信息</button></div>
	                    	</div>
	                    </div>
                    </div>
                </div>
            </div>
            <c:if test="${buyType != 'presellBuy' }">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                    <h5>使用优惠券</h5>
	                    <div class="ibox-tools"><a class="collapse-link" href="javascript:void(0);" id="userCouPonInfo"><i class="icon-uniE61D"></i></a></div>
	                </div>	                
	                <div class="ibox-content" id="userCoupon" style="display: none;">	
	                	<input type="hidden" id="activityChannel" value="${activityChannel }"/>                              
	                    <p id="confirmUserCouPonInfoContent" style="display: none;">已选择使用优惠券:<span id="couponName" class="text-danger"></span>&nbsp;优惠:￥<strong id="couponPrice" class="text-danger">0</strong>元</p>
	                    <div id="couponInfo" class="form-horizontal">
	                    	<p id="couponTipId" style="display:none" class="yhq-md text-danger">(您可用优惠券当中存在不能与余额同时使用的优惠券) </p>
		                  	<div class="form-group">
		      					<label class="col-sm-3 control-label">选择可用优惠券：</label>
		                       	<div class="col-sm-9">
		                       		<s:if test='null!= #request.payRangeList && #request.payRangeList.contains("2")'>
		                       			<select  name="couponId" id="couponMoney" class="form-control" data-flag="-1" autocomplete="off">
		                       				<option  value="-1">---请选择---</option>
		                       				<c:forEach items="${couponList}" var="coupon">
		                       				<s:if test="${coupon.useLimitsType}==1">
		                       					<option data-useLimitType="${coupon.useLimitsType}" value="${coupon.couponId}">[限制]${coupon.couponName}</option>
		                       				</s:if>
		                       				<s:else>
		                       					<option data-useLimitType="${coupon.useLimitsType}" value="${coupon.couponId}">${coupon.couponName}</option>
		                       				</s:else>
		                       				</c:forEach>
		                       			</select>
		                       		</s:if>
		                       		<s:else>
			                       		<select  name="couponId" id="couponMoney" class="form-control" data-flag="0" autocomplete="off">
			                       			<option  value="-1">---请选择---</option>
			                       			<c:forEach items="${couponList}" var="coupon">
			                       			<s:if test="${coupon.useLimitsType}==1">
			                       				<option data-useLimitType="${coupon.useLimitsType}" value="${coupon.couponId}">[限制]${coupon.couponName}</option>
		                       				</s:if>
		                       				<s:else>
		                       					<option data-useLimitType="${coupon.useLimitsType}" value="${coupon.couponId}">${coupon.couponName}</option>
		                       				</s:else>
			                       			</c:forEach>
			                       		</select>
		                       		</s:else>
		              			</div>
		                  	</div>
		                  	<s:if test='#request.payRangeList.contains("2")'>
		                  	<div class="form-group">
		            			<label class="col-sm-3 control-label">支付密码：</label>
		                		<div class="col-sm-9"><input type="password" maxlength="32" id="couponpasswd" class="form-control" value=""></div>
		               		</div>
		               		<div class="form-group">
		                  		<div class="col-sm-offset-3 col-sm-9"><button type="button" class="btn btn-my btn-success btn-sm" id="sureUseCoupon">使用</button></div>
		                  	</div>
		               		</s:if>
		           			<div class="form-group">
		                    	<label class="col-sm-3 control-label">直接激活使用：</label>
		                  		<div class="col-sm-9"><input placeholder="请输入优惠券激活码" class="form-control" maxlength="32" type="text" name="couponActivte" id="couponActivte" /></div>
		               		</div>
		           			<div class="form-group">
		                  		<div class="col-sm-offset-3 col-sm-9"><button type="button" id="useCouponActivte" class="btn btn-my btn-success btn-sm">激活</button></div>
		                	</div>
	                	</div>
	                </div>
	            </div>
            </c:if>
            <div class="ibox float-e-margins">
         		<div class="ibox-title">
                    <h5>商品明细</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link" id="productShow"> 共<font id="pcount">${shopCart.checkedProductCount}</font>件</a>
                    </div>
                 </div> 
                 <div class="ibox-content"> 
                   	<div class="ibox-tabs2" id="productList">
                        <ul class="tabs-lst listpic-inline gd-div">
                        	<c:if test="${not empty productMap}">
							<c:forEach var="carProductEm" items="${productMap}">
							<c:set var="carProduct" value="${carProductEm.value}"></c:set>
								<c:if test="${fn:startsWith(carProductEm.key, 'n_')}">
		                            <li>
		                            	<div class="list-thumb">
		                            		<a href="${wapPath}/products/${carProduct.id}.html" target="_blank">
		                            			<img onerror='this.src="${cssJsPath}images/default__logo_err60_60.jpg"' class="ln_productNum" data-value="${carProduct.amount }"  clstag="clickcart|keycount|xincart|p-imglistcart" src="<%=picpath%>${fn:replace(carProduct.imagePath, '.jpg', '_7.jpg')}"  title="${carProduct.title}">
		                            		</a>
		                            	</div>
		                            </li>
		                            <!-- 显示附赠商品 -->
		                            <c:if test="${not empty carProduct.productPromotion.affixPromoiton 
										and not empty carProduct.productPromotion.affixProductList}">									
										<c:forEach items="${carProduct.productPromotion.affixProductList}" var="affixProductList">	
											<c:set value="${shopCart.giftStockMap[fn:trim(affixProductList.productSkuId)]}" var="stock"/>
											<c:if test="${not empty stock and stock>=affixProductList.num}">										
											<li>
				                            	<div class="list-thumb">
				                            		<a href="${wapPath}/products/${affixProductList.productSkuId}.html" target="_blank">
				                            			<img onerror='this.src="${cssJsPath}images/default__logo_err60_60.jpg"' class="ln_productNum" 
				                            			data-value="${affixProductList.num }"  clstag="clickcart|keycount|xincart|p-imglistcart" 
				                            			src="<%=picpath%>${fn:replace(affixProductList.imagePath, '.jpg', '_7.jpg')}"  
				                            			title="附赠&nbsp;&nbsp;${affixProductList.productTitle}&nbsp;${affixProductList.skuAttrValue }">
				                            		</a>
				                            	</div>
				                            </li>
				                            </c:if>													
										</c:forEach>
	                            	</c:if>
	                            	<!-- end -->
	                            </c:if>
	                            <c:if test="${fn:startsWith(carProductEm.key, 'c_')}">
	                            	<c:forEach var="danpingCarProduct" items="${carProduct.productList}">
	                           	 	<li>
		                            	<div class="list-thumb">
		                            		<a href="${wapPath}/products/${danpingCarProduct.productSkuId}.html" target="_blank">
		                            			<img onerror='this.src="${cssJsPath}images/default__logo_err60_60.jpg"' 
		                            			class="ln_productNum"  clstag="clickcart|keycount|xincart|p-imglistcart" 
		                            			src="<%=picpath%>${fn:replace(danpingCarProduct.imagePath, '.jpg', '_7.jpg')}"  title="${danpingCarProduct.title}"/>
		                            		</a>
		                            	</div>
		                            </li>
		                            </c:forEach>
	                            </c:if>
                            </c:forEach>
                            </c:if>
                            <c:if test="${not empty shopCart.meetOrderPromotionList}">
								<c:forEach 	items="${shopCart.meetOrderPromotionList}" var="myPromotionInfo">
								<c:forEach items="${myPromotionInfo.gifts}" var="giftProduct">
								<c:set value="${shopCart.giftStockMap[giftProduct.id]}" var="stock"></c:set>
								<li>
                            	<div class="list-thumb">
                            		<a href="${wapPath}/products/${giftProduct.id}.html" target="_blank">
                            			<img onerror='this.src="${cssJsPath}images/default__logo_err60_60.jpg"' class="ln_productNum" data-value="${giftProduct.amount}"  clstag="clickcart|keycount|xincart|p-imglistcart" src="<%=picpath%>${fn:replace(giftProduct.img, '.jpg', '_7.jpg')}" title="${giftProduct.name}">
                            		</a>
                            	</div>
                                </li>
								</c:forEach>
								<c:forEach items="${myPromotionInfo.rulePresents[myPromotionInfo.defaultPresents]}" var="giftProduct">
								<c:set value="${shopCart.giftStockMap[giftProduct.id]}" var="stock"></c:set>
								<c:if test="${not empty stock and stock>=giftProduct.amount}">
									<li>
	                            	<div class="list-thumb">
	                            		<a href="${wapPath}/products/${giftProduct.id}.html" target="_blank">
	                            			<img onerror='this.src="${cssJsPath}images/default__logo_err60_60.jpg"' class="ln_productNum" data-value="${giftProduct.amount}"  clstag="clickcart|keycount|xincart|p-imglistcart" src="<%=picpath%>${fn:replace(giftProduct.img, '.jpg', '_7.jpg')}"  title="${giftProduct.name}">
	                            		</a>
	                            	</div>
	                            	</li>
                            	</c:if>
								</c:forEach>
								</c:forEach>
							</c:if>							
                        </ul>
                   	</div>
   				</div>
			</div>
			<c:if test="${buyType != 'presellBuy' }">
				<div style="display:none" id="amountAvlibalUnUseId" class="ibox float-e-margins">
			    	<div class="ibox-title">
			        	<h5>使用余额支付</h5><span class="order-tit-sm ">(您使用的优惠券限制不能与余额同时使用)</span>
			    	</div>
		        </div>
	            <div id="amountAvlibalUseId" class="ibox float-e-margins">
	                <div class="ibox-title">
	                    <h5>使用余额支付 </h5> 
	                    <div class="ibox-tools"><a class="collapse-link" href="javascript:void(0);" id="userMoneyPay"><i class="icon-uniE61D"></i></a></div>
	                </div>
	                <div class="ibox-content" id="moneyPay" style="display: none;">
	                    <div>
	                        <div class="radio" id="paidMoneyId">
	                            <label><input type="checkbox" id="userCountMoneyId" autocomplete="off" name="balance" />使用当前账户余额：<font class="text-danger" >￥<span id="mybalance"><fmt:formatNumber pattern="0.00" value="${accountInfo.amountAvlibal}" /></span></font></label>
	                        </div>
	                        <div id="payCountMoney" class="form-horizontal">
	                        	<input type="hidden" id="mobVe" value="${accountInfo.mobile}">
		                        <s:if test='#request.accountInfo.paymentpwd==null'> 
		                        <div class="form-group">
		                            <div class="col-sm-12">
		                                <span>请先启用支付密码</span> <button type="button" id="j_go_paypwd_set" class="btn btn-my btn-success btn-sm">启用支付密码</button>
		                            </div>
		                        </div>
		                        </s:if>
		                        <s:else>
		                   		<div class="form-group">
		                            <label class="col-sm-3 control-label">支付金额：</label>
		                            <div class="col-sm-9">
		                            	<input type="text" maxlength="10" value='${payMoneyInfo.minMoneyFromAmountAvlibalAndPayMoney}' oldValue='${payMoneyInfo.minMoneyFromAmountAvlibalAndPayMoney}'  name="amountAvlibal" id="amountAvlibal" onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))" class="form-control">
		                            </div>
		                        </div>
		                        <div class="form-group">
		                            <label class="col-sm-3 control-label">支付密码：</label>
		                            <div class="col-sm-9">
		                                <input type="password" maxlength="32" name="passwd" id="passwd" class="form-control" value="">
		                            </div>
		                        </div>
		                        <div class="form-group">
		                            <div class="col-sm-offset-3 col-sm-9">
		                                <button type="button" data-p1="${payMoneyInfo.compareFlag}" class="btn btn-my btn-success btn-sm surePayMoney">确定</button>
		                            </div>
		                        </div>
		                        </s:else>
	                        </div>
	                    </div>
	                </div>
	            </div>
            </c:if>
            <c:if test="${buyType == 'presellBuy' }">
	            <div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>尾款通知手机</h5>
						<div class="ibox-tools">
	                        <a class="collapse-link" href="javascript:void(0);" id="editInformpaytel" style="display: none;"><i class="icon-uniE62E"></i></a>
	                    </div>
					</div>
					<div class="ibox-content" id="showInformpaytel" style="display:none;">						
		                <div class="col-sm-8" id="informpaytelDiv"></div>
					</div>
					<div id="showEditInformpaytel" class="ibox-content" style="display:block;">
						<input type="text" class="form-control" id="informpaytel" name="informpaytel" placeholder="请输入尾款支付通知手机" />
						<div class="form-group">
                       		<div class="col-sm-offset-3 col-sm-9"><button type="button" class="btn btn-my btn-success btn-sm" id="sureInformpaytel">确认手机号</button></div>
                    	</div>
					</div>
				</div>
            </c:if>
            <c:if test="${buyType == 'presellBuy' }">
	            <div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>同意支付定金</h5>					
					</div>
					<div class="ibox-content">
						<form class="form-horizontal">
							<div class="col-sm-12">
								<label class="checkbox-inline"> <input type="checkbox" id="confirmDepositPriceInfo" value="0" class="icon-sell-checkbox" /> 预售定金不退款！
								</label>
							</div>
						</form>
					</div>
				</div>
			</c:if>
            
            <form action="/settlement/saveAndPayOrder.action" name="orderForm" id="orderForm" method="post">
            <input type="hidden" name="buyType" value="${buyType}" id="buyType"/>
			<input type="hidden" name="productCount" value="${productCount}" id="productCount"/>
			<input type="hidden" name="productSkuId" value="${productSkuId}" id="productSkuID"/>
			<input type="hidden" name="productSkuIDs" value="${productSkuIDs}" id="productSkuIDs" />
			<input type="hidden" name="wapPay" value="1" id="hdWapPay"/>
			<%String utoken =MD5.getMD5Str(Constants.PRESCRIPTION_BUY+session.getAttribute(Constants.SESSION_USER_ID)); %>
			<input type="hidden" id="hduid" name="hduid" value="<%=utoken %>" />
			<input type="hidden" id="pageFlag" value="0" />
            </form>
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>结算信息</h5>
                </div>
                <div class="ibox-content">
                    <ul class="list-unstyled">
                        
                        <c:choose>
                        	<c:when test="${buyType == 'presellBuy' }">
                        		<li>定金：<span class="text-danger pull-right">￥<fmt:formatNumber pattern="0.00" value="${depositPrice}" />元</span></li>
                        		<li>配送费：<span class="text-danger pull-right">+<span id="fare"><fmt:formatNumber pattern="0.00" value="${payMoneyInfo.fare}" /></span>元</span></li>
                        		<li>实付金额：<span class="text-danger pull-right"><span id="totalMoney"><fmt:formatNumber pattern="0.00" value="${depositPrice+payMoneyInfo.fare}" /></span>元</span></li>
                        	</c:when>
                        	<c:otherwise>
                        		<li>商品总金额：<span class="text-danger pull-right">￥<fmt:formatNumber pattern="0.00" value="${shopCart.checkTotalMoney}" />元</span></li>
                        		<li>配送费：<span class="text-danger pull-right">+<span id="fare"><fmt:formatNumber pattern="0.00" value="${payMoneyInfo.fare}" /></span>元</span></li>
                        		<li>优惠券：<span class="text-danger pull-right">-<span id="couponMoneyAvlibal"><fmt:formatNumber pattern="0.00" value="${payMoneyInfo.couponMoney}" /></span>元</span></li>
	                        	<li>余额支付：<span class="text-danger pull-right">-<span id="balancePaid"><fmt:formatNumber pattern="0.00" value="${payMoneyInfo.balanceMoney}" /></span>元</span></li>                        
                        		<li>实付金额：<span class="text-danger pull-right"><span id="totalMoney"><fmt:formatNumber pattern="0.00" value="${shopCart.uncalculateMoney-shopCart.reductionMoney +payMoneyInfo.fare+shopCart.additionalMoney-shopCart.discountMoney}" /></span>元</span></li>
                        	</c:otherwise>
                        </c:choose>
                        
                    </ul>
                    <button class="btn btn-danger btn-block btn-sm" type="button" submitorder="0" id="j_submitOrder">提交订单</button>
                </div>
            </div>
        </div>
    </div>
</div>
