<%@ page language="java" import="java.util.*,com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<div class="l-right user-m">
	<div class="o-mt">
		<h2>订单信息</h2>
		<span id="uId" data-type="${orderMain.customerId}"></span>
		<div class="OrderInfo">
			<input type="hidden" id="logisticsOrderNo" value="${orderMain.logisticsOrderNo }" />
			<input type="hidden" id="logisticsCode" value="${orderMain.logisticsCode }" />
			<input type="hidden" id="expressComName" value="${orderMain.logisticsName }" />
			<input type="hidden" id="orderCode" value="${orderMain.orderCode }" />
			<input type="hidden" id="orderMainId" value="${orderMainId}" />
		 	<input type="hidden" id="orderStatusId" value="${orderMain.orderStatus}" />
		 	<input type="hidden" id="isOrderAssess" value="${isOrderAssess}" />
			<ul class="Order-Number">
				<li>订单编号：${orderMain.orderCode}</li>
				<li>状态:
					<c:choose>
						<c:when test="${orderMain.orderStatus == -1}">已取消</c:when>
						<c:when test="${orderMain.orderStatus == 1}">
							未付款<a href="javascript:void(0);" data-orderCode="${orderMain.orderCode}" class="red-btn j_pay">付款</a>
						</c:when>
						<c:when test="${orderMain.orderStatus == 2 || orderMain.orderStatus == 20 || orderMain.orderStatus == 21 || orderMain.orderStatus == 22}">已付款</c:when>
						<c:when test="${orderMain.orderStatus == -2}">取消审核中</c:when>
						<c:when test="${orderMain.orderStatus == 3 ||orderMain.orderStatus == 4 ||orderMain.orderStatus == 15 ||orderMain.orderStatus == 18 }">配货中</c:when>
						<c:when test="${orderMain.orderStatus == 5}">
							已发货
							<a href="javascript:void(0);" data-orderCode="${orderMain.orderCode}" data-type="6" class="green-btn fn-r5 btnp10 j_sureTosureOrder">确认收货</a>	
						</c:when>
						<c:when test="${orderMain.orderStatus == 6}">
							已完成
							<c:if test="${orderMain.assessStatus == 1}">
								<span><a href="/member/gotoAssessProduct.action?isOrderList=4&orderCodes=${orderMain.orderCode}" class="yellow-btn">评价</a></span>
							</c:if>
						</c:when>
						<c:when test="${orderMain.orderStatus == 12}">送货失败</c:when>
						<c:when test="${orderMain.orderStatus == 16}">已拆分</c:when>
						<c:when test="${orderMain.orderStatus == -3}">订单异常</c:when>
					</c:choose>
				</li>
				<c:if test="${orderMain.orderStatus == 16}">
				<li style="display:block;">子订单：
					<c:forEach items="${sonOrderMainList}" var="sonOrder">
					<a href="/member/queryOrderDetail.action?orderMainCode=${sonOrder.orderCode}" class="fn-blue">${sonOrder.orderCode}</a>						
					</c:forEach>
				</li>
				</c:if>
				<p id="statusInfo">
				<c:choose>
				<c:when test="${expressPath.data!=null}">
					<strong>最新进度:</strong>
					<span>[<fmt:formatDate value="${expressPath.data[0].ftime}" pattern="yyyy-MM-dd HH:mm:ss" />]</span>
            		${expressPath.data[0].context }
				</c:when>
				<c:when test="${listorder!=null && fn:length(listorder) >= 1}">
					<strong>最新进度:</strong>
					<span>[<fmt:formatDate value="${listorder[fn:length(listorder)-1].nowOperateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>]</span>
            		${listorder[fn:length(listorder)-1].operateInfo  }
				</c:when>
				<c:otherwise>暂无进度</c:otherwise>
				</c:choose>
					<a class="fn-blue j_orderLogisticsUrl" href="initOrderLogistics.action?orderMain.orderStatus=${orderMain.orderStatus}&orderMain.customerId=${orderMain.customerId}&orderMain.logisticsOrderNo=${orderMain.logisticsOrderNo}&orderMain.logisticsName=${orderMain.logisticsName}&orderMain.logisticsCode=${orderMain.logisticsCode}&orderMainId=${orderMainId}&orderMain.orderCode=${orderMain.orderCode}&backFlag=2">进度跟踪</a>
				</p>
			</ul>
			<c:choose>
			<c:when test="orderMain.orderStatus == 16&&isParentOrder==1">
			<div class="fn-clear">
				<div class="oper-shop oper-shopbi-61">
		      		<ul>
		     		<c:choose>
		     		<c:when test="${orderMain.orderStatus == -1 || orderMain.orderStatus == 1}">
	                	<li class="oper-shop1"><span></span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>2</span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
                	</c:when>
	            	<c:otherwise>
	                	<li class="oper-shop3"><span>1</span><i></i></li>
	                   	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
	                   	<li class="oper-shop1"><b></b><span></span></li>
                	</c:otherwise>
		     		</c:choose>
		         	</ul>
		     	</div>
        		<div class="operatxt operatxt-148">
		      		<ul>
			     		<li id="operatxtShop1" class="operatxt-shop3">提交订单
			     		<c:forEach items="${listorder}" var="order">
			     			<c:if test="${order.nowOperateType==1}">
			     			<p><fmt:formatDate value="${order.nowOperateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p><c:set var="OperateDate" value="#root.nowOperateDate"/>
			     			</c:if>
			     		</c:forEach>
			       		</li>
			         	<li id="operatxtShop2" class="operatxt-shop3">付款到卖家
			         	<c:choose>
			         	<c:when test="${orderMain.orderType==2}"><p><fmt:formatDate value="${OperateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p></c:when>
			         	<c:otherwise>
			         	<c:forEach items="${listorder}" var="order">
			     			<c:if test="${order.nowOperateType==2 && ordernowOrderStatus==2}">
			     			<p><fmt:formatDate value="${order.nowOperateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
			     			</c:if>
			     		</c:forEach>
			         	</c:otherwise>
			         	</c:choose>
						</li>
						<li id="operatxtShop3" class="operatxt-shop1">已拆单
						<c:forEach items="${listorder}" var="order">
			     			<c:if test="${order.nowOperateType==4}">
			     			<p><fmt:formatDate value="${order.nowOperateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
			     			</c:if>
			     		</c:forEach>
                		</li>
		        	</ul>
		 		</div>
			</div>
			</c:when>
			<c:otherwise>
			<div class="fn-clear">
				<div class="oper-shop oper-shopbi-61">
		      		<ul>
		       		<c:choose>
		       		<c:when test="${orderMain.orderStatus == -1}">
	                	<li class="oper-shop1"><span></span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>2</span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>5</span></li>
                	</c:when>
	            	<c:when test="${orderMain.orderStatus == 1}">
	                	<li class="oper-shop1"><span></span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>2</span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>5</span></li>
                	</c:when>
	            	<c:when test="${orderMain.orderStatus == 2 || orderMain.orderStatus ==3 ||orderMain.orderStatus==4
							|| orderMain.orderStatus==15 ||orderMain.orderStatus==16||orderMain.orderStatus==18 || orderMainVar.orderStatus == 20|| orderMainVar.orderStatus == 21|| orderMainVar.orderStatus == 22}">
	                	<li class="oper-shop3"><span>1</span><i></i></li>
	                   	<li class="oper-shop1"><b></b><span></span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>5</span></li>
                	</c:when>
	            	<c:when test="${orderMain.orderStatus == 5}">
	                	<li class="oper-shop3"><span>1</span><i></i></li>
	                   	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
	                   	<li class="oper-shop1"><b></b><span></span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>5</span></li>
                	</c:when>
                	<c:when test="${orderMain.orderStatus == 6 && isOrderAssess=='yes' }" >
	                	<li class="oper-shop3"><span>1</span><i></i></li>
	                   	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
	                   	<li class="oper-shop3"><b></b><span>3</span><i></i></li>
	                   	<li class="oper-shop3"><b></b><span>4</span><i></i></li>
	                   	<li class="oper-shop1"><b></b><span></span></li>
                	</c:when>
	            	<c:when test="${orderMain.orderStatus == 6 && isOrderAssess!='yes'}">
	                	<li class="oper-shop3"><span>1</span><i></i></li>
	                   	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
	                   	<li class="oper-shop3"><b></b><span>3</span><i></i></li>
	                   	<li class="oper-shop1"><b></b><span></span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>5</span></li>
                	</c:when>
                	<c:otherwise>
                		<li class="oper-shop1"><span></span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>2</span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
	                   	<li class="oper-shop2"><b></b><span>5</span></li>
                	</c:otherwise>
		       		</c:choose>
		          	</ul>
		    	</div>
        		<div class="operatxt operatxt-148">
					<ul>
					<c:choose>
					<c:when test="${isParentOrder==0}">
						<li id="operatxtShop1" class="operatxt-shop1">提交订单
						<c:forEach items="${rootOrderOperateStateList}"  var="root">
                       	<c:if test="${root.nowOperateType==1}">
						<p><fmt:formatDate value="${root.nowOperateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
						<c:set var="OperateDate" value="${root.nowOperateDate}" />
				     	</c:if>
				     	</c:forEach>
			        	</li>
				 		<li id="operatxtShop2" class="operatxt-shop2">付款到卖家
				  		<c:choose>
				  		<c:when test="${orderMain.orderType==2}">
				  		<p><fmt:formatDate value="${OperateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
				  		</c:when>
				  		<c:otherwise>
				  		<c:forEach items="${rootOrderOperateStateList}"  var="root">
				  		<c:if test="${root.nowOperateType==2 && root.nowOrderStatus==2}">
				  		<p><fmt:formatDate value="${root.nowOperateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
				  		</c:if>
						</c:forEach>				  			
				  		</c:otherwise>
				  		</c:choose>
				     	</li>
					</c:when>
					<c:otherwise>
						<li id="operatxtShop1" class="operatxt-shop1">提交订单
						<c:forEach items="${rootOrderOperateStateList}"  var="root">
                       	<c:if test="${root.nowOperateType==1}">
						<p><fmt:formatDate value="${root.nowOperateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
						<c:set var="OperateDate" value="${root.nowOperateDate}" />
				     	</c:if>
				     	</c:forEach>
			          	</li>
			        	<li id="operatxtShop2" class="operatxt-shop2">付款到卖家
						<c:set var="payed" value="0" />
						<c:choose>
				  		<c:when test="${orderMain.orderType==2}">
				  		<p><fmt:formatDate value="${OperateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
				  		</c:when>
				  		<c:otherwise>
				  		<c:forEach items="${rootOrderOperateStateList}"  var="root">
				  		<c:if test="${root.nowOperateType==2 && root.nowOrderStatus==2 && payed==0}">
				  		<c:set var="payed" value="1" />
				  		<p><fmt:formatDate value="${root.nowOperateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
				  		</c:if>
						</c:forEach>				  			
				  		</c:otherwise>
				  		</c:choose>							
			         	</li>
					</c:otherwise>
					</c:choose>						
			    		<li id="operatxtShop3" class="operatxt-shop2">卖家已发货
			         	<c:set var="sended" value="0"/>
			         	<c:forEach items="${rootOrderOperateStateList}"  var="root">
				  		<c:if test="${root.nowOperateType==7 && root.nowOrderStatus==2 && sended==0}">
				  		<c:set var="sended" value="1"/>
				  		<p><fmt:formatDate value="${root.nowOperateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
				  		</c:if>
						</c:forEach>
						</li>
			           	<li id="operatxtShop4" class="operatxt-shop2">确认收货
				      	<c:forEach items="${rootOrderOperateStateList}"  var="root">
				  		<c:if test="${root.nowOperateType==9}">
				  		<p><fmt:formatDate value="${root.nowOperateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
				  		</c:if>
						</c:forEach>
			       		</li>
			        	<li id="operatxtShop5" class="operatxt-shop2">评价
			        	<c:forEach items="${rootOrderOperateStateList}"  var="root">
				  		<c:if test="${root.nowOperateType==13}">
				  		<p><fmt:formatDate value="${root.nowOperateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
				  		</c:if>
						</c:forEach>
			         	</li>
		        	</ul>
		 		</div>
			</div>
			</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="user-m fn-t10">
		<div class="mt"><h3 class="curr">结算信息</h3></div>
		<div class="mc">
			<div class="user-column fn-bggray fn-clear">
				<ul class="rightline no-space">
					<h2>收货人信息</h2> 
					<li>收货人：${orderMain.consigneeName}</li>
					<li>收货地址：${orderMain.consigneeAddr}</li>
					<li>手机号码：${orderMain.consigneeMobile}</li>
					<li>固定电话：${orderMain.consigneeTel}</li>
				</ul>
				<ul class="rightline">
					<h2>支付及配送方式</h2>
					<li>支付方式：${orderMain.payMethodValue}</li>
					<li>配送方式：快递 ￥<fmt:formatNumber value="${orderMain.fare}" type="currency" pattern="#,##0.00元"/><br/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<c:choose>
					<c:when test="${orderMain.deliveryDateType==1}">工作日送货</c:when>
					<c:when test="${orderMain.deliveryDateType==2}">休息日送货</c:when>
					<c:otherwise>工作日/休息日皆可送货</c:otherwise>
					</c:choose>
					<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<c:if test="${orderMain.confirmDelivery==0}">送货前不用电话确认</c:if>
					<c:if test="${orderMain.confirmDelivery!=0}">送货前需要电话确认</c:if>
					</li>
					<c:if test="${orderMain.orderStatus != 16}">
					<li>物流公司：${orderMain.logisticsName}</li>
					<li>运单号码：${orderMain.logisticsOrderNo}</li> 
					</c:if> 
				</ul>
				<ul> 
					<h2>发票信息</h2>
					<c:choose>   
					<c:when test="${orderMain.invoiceInfoType!=null||orderMain.invoiceInfoTitle!=null||orderMain.invoiceInfoContent!=null}">
					<li>类型：${orderMain.invoiceInfoType}</li>
					<li>抬头：${orderMain.invoiceInfoTitle}</li>
					</c:when>
					<c:otherwise>买家未要求开发票</c:otherwise>
					</c:choose>
				</ul>
				<p class="remark fn-red"><strong>备注：</strong>${orderMain.orderDescription}</p>
			</div>
            <c:if test="${isParentOrder == 1}">
			<h3>支付信息</h3>
			<c:set var="sumPay" value="0.00" />
			<c:set var="countOnlinePay" value="0.00" />
			<c:set var="payMoney1" value="0.00" />
			<c:set var="payMoney2" value="0.00" />
			<c:forEach items="${orderPayStatementList}"  var="ops">
			<c:choose>
			<c:when test="${ops.paymentWay==1}"><c:set var="payMoney1" value="${ops.orderMoney}" /></c:when>
			<c:when test="${ops.paymentWay==2}"><c:set var="payMoney2" value="${ops.orderMoney}" /></c:when>
			<c:when test="${ops.paymentWay>2}"><c:set var="countOnlinePay" value="${ops.orderMoney+countOnlinePay}" /></c:when>
			</c:choose>
			<c:set var="sumPay" value="${sumPay+ops.orderMoney}" />
			</c:forEach>
			<div class="user-column fn-bggray fn-clear">
				<ul class="fn-w100">
            		<li><label>总金额：</label>
                		<span class="fn-gray">商品总额 ￥
                		<fmt:formatNumber value="${orderMain.originalOrderSum}" type="currency" pattern="#,##0.00元"/>
                		-满减 ￥<fmt:formatNumber value="${orderMain.discountSum}" type="currency" pattern="#,##0.00元"/>
                		+加价购￥<fmt:formatNumber value="${addPrice}" type="currency" pattern="#,##0.00元"/>
                     	+运费￥<fmt:formatNumber value="${orderMain.fare}" type="currency" pattern="#,##0.00元"/>
                     	<c:choose>
                     	<c:when test="${orderMain.orderType==3||orderMain.orderType==4||orderMain.orderType==5}">
                     	- 会员折扣 ￥   <fmt:formatNumber value="${orderMain.orderDisCount}" type="currency" pattern="#,##0.00元"/>
                     	</c:when>
                     	<c:when test="${orderMain.orderType!=3&&orderMain.orderType!=4&&orderMain.orderType!=5&& orderMain.orderDisCount!=0}">
                     	- 会员折扣 ￥   <fmt:formatNumber value="${orderMain.orderDisCount}" type="currency" pattern="#,##0.00元"/> 
                     	</c:when>
                     	</c:choose>
               			 = <strong class="fn-red">￥<fmt:formatNumber value="${orderMain.amountPayable}" type="currency" pattern="#,##0.00元"/></strong>
               			</span>
               		</li>
                  	<li><label>支付信息：</label><span class="fn-gray">优惠券支付￥<fmt:formatNumber value="${payMoney2}" type="currency" pattern="#,##0.00元"/> + 余额支付￥<fmt:formatNumber value="${payMoney1}" type="currency" pattern="#,##0.00元"/>+ 在线支付￥<fmt:formatNumber value="${countOnlinePay}" type="currency" pattern="#,##0.00元"/> =  <strong class="fn-red">￥<fmt:formatNumber value="${sumPay}" type="currency" pattern="#,##0.00元"/> </strong></span></li>
                	<%-- <li class="fn-clear">
                   		<dl class="order-track-item">
                       		<dt>完成订单可获得：</dt>
                         	<dd class="fn-gray">
                         	<c:choose>
                         	<c:when test="${b2b_login_remark!='02'}">
                         	<p>会员积分 ${orderMain.totalCredit}分</p>
                         	</c:when>
                         	<c:otherwise>
                         	<p>预计可获时代积分<span style="color:red;"> ${orderMain.orderPv}</span>分</p>
                         	<c:if test="${orderMain.pv!=null}"><p>实际获得时代积分<span style="color:red;"> ${orderMain.pv}</span>分（扣除退换货影响）</p></c:if>
                         	</c:otherwise>
                         	</c:choose>
                        	<c:if test="${null!=couponList }">
                        	<c:forEach items="${couponList}" var="coupon">
                        	<p><fmt:formatNumber value="${coupon.couponMoney}" type="currency" pattern="#,##0.00"/>元优惠券，
                        	<c:choose>
                        		<c:when test="${coupon.productFilterTypeStr=='1'}">全场可用</c:when>
                        		<c:otherwise>部分商品可用</c:otherwise>
                        	</c:choose>，  
                        	<c:choose>
                        		<c:when test="${coupon.timeType==1}">有效期至 <fmt:formatDate value="${coupon.endtime}" pattern="yyyy-MM-dd HH:mm:ss"/></c:when>
                        		<c:when test="${coupon.timeType==2}">有效天数${coupon.couponValidDay }天</c:when>
                        	</c:choose>
                        	</p>
                        	</c:forEach>
                        	</c:if>
                     		</dd>
                  		</dl>
                 	</li> --%>
              	</ul>
 			</div>
			</c:if>
			<h3>订单商品</h3>
			<c:choose>
			<c:when test="${orderMain.orderStatus == 16}"><p class="fn-bgred">订单已拆分，商品列表请到各子订单中分别查看。</p></c:when>
			<c:otherwise>
			
			<c:set var="consumptionRate" value="0" />
			<c:if test="${orderMain.parentOrderCode!=null}">
			<c:forEach items="${orderMain.orderItemList}" var="oI">
			<c:if test="${consumptionRateMap[oI.defaultProductImage.skuId]>0}">
			<c:set var="consumptionRate" value="1" />
			</c:if>
			</c:forEach>
			</c:if>
			
			<div class="ui-table">
				<table class="ui-table user-table">
				<thead>
					<tr>
						<th class="td-s4">商品</th>
                		<th>购买数量</th>
                    	<th>生产批号</th>
                    	<th class="td-s2">金额小计</th>
                     	<c:if test="${orderMain.consumptionAmount>0.00||consumptionRate!=0}">
                  		<th>返利金额</th>
                     	</c:if>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${orderMain.orderItemList}" var="orderItemVar">
					<tr>
						<td>
							<a name="productlink" href="${cmsPagePath}${orderItemVar.defaultProductImage.skuId}.shtml" target="_blank">
								<b class="img"><img  src='${productImgServerUrl}${orderItemVar.defaultProductImage.imgPath7}' title="${orderItemVar.commodityName}" onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"></b>
								<div class="td-name fn-text-left fn-blue">${orderItemVar.commodityName}</div>
							</a>
						</td>
						<td>${orderItemVar.commodityNumber}</td>
						<td>${orderItemVar.commodityBatchNumber}</td>
						<td><fmt:formatNumber value="${orderItemVar.commoditySum}" type="currency" pattern="#,##0.00元"/></td>
						<c:if test="${orderMain.consumptionAmount>0.00||consumptionRate!=0}">
						<td>
						<c:choose>
						<c:when test="${orderMain.parentOrderCode!=null}">
						  <c:choose>
						  <c:when test="${consumptionRateMap[orderItemVar.defaultProductImage.skuId]>0}">
						  ${consumptionRateMap[orderItemVar.defaultProductImage.skuId]}元</c:when>
						  <c:otherwise>--</c:otherwise>
						  </c:choose>	
						</c:when>
						<c:when test="${orderItemVar.spreadEffect.consumptionAmount>0}">
						<fmt:formatNumber value="${orderItemVar.spreadEffect.consumptionAmount}" type="currency" pattern="#,##0.00元"/>
						</c:when>
						<c:otherwise>--</c:otherwise>
						</c:choose>	
						</td>
						</c:if>
					</tr>
				</c:forEach>
				</tbody>
				</table>
			</div>
			</c:otherwise>
			</c:choose>
            <div class="fn-t20 fn-clear">
				<a href="/member/queryOrderList.action" class="btn-cancel"><span>返回订单列表</span></a>
				<span class="fn-right">
				<c:choose>
				<c:when test="orderMain.orderStatus == 1"><a href="javascript:void(0);" data-orderCode="${orderMain.orderCode}" class="red-btn fn-r5 btnp10 j_pay">付款</a></c:when>
				<c:when test="orderMain.orderStatus == 5"><a href="javascript:void(0);" data-orderCode="${orderMainVar.orderCode}" data-type="6" class="green-btn fn-r5 btnp10 j_sureTosureOrder">确认收货</a></c:when>
				<c:when test="orderMain.orderStatus == 6 && orderMain.assessStatus== 0"><a href="/member/gotoAssessProduct.action?isOrderList=4&orderCodes=${orderMain.orderCode}"  class="yellow-btn btnp10">评价</a></c:when>
				</c:choose>
				</span>
			</div>
		</div>
	</div>
</div>
</div>
