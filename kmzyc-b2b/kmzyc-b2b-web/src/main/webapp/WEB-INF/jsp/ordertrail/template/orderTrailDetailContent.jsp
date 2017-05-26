<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="logo-box">
	<div class="l-w i-registration-logo">
		<a id="indexUrl" hidefocus="true" href="">
		<img id="indexImg" width="303" height="77" src=""></a>
	</div>
</div>
<div id="registration-form">
	<div class="o-mt">
		<div class="OrderInfo">
			<ul class="Order-Number">
				<li>订单编号：${orderMain.orderCode}</li> 
				<li>状态:
				 <s:if test="#request.orderMain.orderStatus == -1">已取消</s:if>
					<s:if test="#request.orderMain.orderStatus == 1">未付款
						<a href="javascript:void(0);" date-backFlag="<s:property value="backFlag"/>" data-orderCode="${orderMain.orderCode}" data-orderMainId="<s:property value='orderMainId'/>" 
							  	data-orderEmail="${orderTrailInfo.emailAddress}" data-orderMobile="${orderTrailInfo.mobileNumber}"
							  	class="red-btn j_pay">付款</a>
					</s:if>
					<s:if test="#request.orderMain.orderStatus == 2 || #request.orderMain.orderStatus == 20|| #request.orderMain.orderStatus == 21|| #request.orderMain.orderStatus == 22">已付款</s:if>
					<s:if test="#request.orderMain.orderStatus == 3 || #request.orderMain.orderStatus == 4|| #request.orderMain.orderStatus == 15|| #request.orderMain.orderStatus == 18">配货中</s:if>								
					<s:if test="#request.orderMain.orderStatus == 5">已发货</s:if> 
					<s:elseif test="#request.orderMain.orderStatus == 6">已完成</s:elseif> 
					<s:if test="#request.orderMain.orderStatus == 12">送货失败</s:if>
					<s:if test="#request.orderMain.orderStatus == 16">已拆分</s:if>
				</li>
				<s:if test="#request.orderMain.orderStatus == 16">
					<li style=" display:block;">子订单：
						<s:iterator value="sonOrderMainList" var="sonOrderMainList">
							<a href="queryTrailOrderDetail.action?orderMainId=<s:property  value='#sonOrderMainList.orderId'/>&backFlag=<s:property value="backFlag"/>" class="fn-blue"><s:property  value="#sonOrderMainList.orderCode"/></a>
						</s:iterator>
					</li>
				</s:if>
					<p id="statusInfo">
				 <s:if test="expressPath.data!=null">
				     <strong>最新进度:</strong> <span>[<s:date name="expressPath.data[0].ftime" format="yyyy-MM-dd HH:mm:ss"/>]
				     </span>
					 <s:property value="expressPath.data[0].context" /> 
				  </s:if>
				  <s:elseif test="listorder!=null">
			          <strong>最新进度:</strong> <span>[<s:date name="listorder[0].nowOperateDate" format="yyyy-MM-dd HH:mm:ss"/>]
					</span>
					<s:property value="listorder[0].operateInfo" />	  
				  </s:elseif>
				  <s:else>暂无进度  </s:else>
				  <a class="fn-blue j_orderTrailLogisticsUrl" href="showExpressPath.action?orderTrailInfo.expressComName=${orderMain.logisticsName }&orderTrailInfo.expressNo=${orderMain.logisticsOrderNo }&orderMainId=${orderMainId}&orderTrailInfo.orderNo=${orderMain.orderCode}&orderTrailInfo.orderStatus=${orderMain.orderStatus}&backFlag=${backFlag}">进度跟踪</a>
				</p>
			</ul>
			<s:if test="#request.orderMain.orderStatus ==16 &&isParentOrder==1">
					<div class="fn-clear">
							 <div class="oper-shop oper-shopbi-61">
				            	<ul>
				            		<s:if test="#request.orderMain.orderStatus == -1">
					                	<li class="oper-shop1"><span></span><i></i></li>
					                   	<li class="oper-shop2"><b></b><span>2</span><i></i></li>
					                   	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
				                	</s:if>
					            	<s:elseif test="#request.orderMain.orderStatus == 1">
					                	<li class="oper-shop1"><span></span><i></i></li>
					                   	<li class="oper-shop2"><b></b><span>2</span><i></i></li>
					                   	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
				                	</s:elseif>
					            	<s:else>
					                	<li class="oper-shop3"><span>1</span><i></i></li>
					                   	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
					                   	<li class="oper-shop1"><b></b><span></span></li>
				                	</s:else>
				                </ul>
				             </div>
							 <div class="operatxt operatxt-148">      
				                 <ul>
					                   	 <li id="operatxtShop1" class="operatxt-shop3">提交订单
					                   	 	<s:iterator value="listorder" var="listorder">
						                   	 	<s:if test="#listorder.nowOperateType==1">
							                   	 	<p><s:date  name='#listorder.nowOperateDate'/></p>
						                   	 	</s:if>
					                   	 	</s:iterator>
					                   	 </li>
					                     <li id="operatxtShop2" class="operatxt-shop3">付款到卖家
						                     <s:iterator value="listorder" var="listorder">
						                   	 	<s:if test="#listorder.nowOperateType==2 && #listorder.nowOrderStatus==2" >
							                   	 	<p><s:date  name='#listorder.nowOperateDate'/></p>
						                   	 	</s:if>
					                   	 	</s:iterator>
					                     </li>
					                     
					                     <li id="operatxtShop3" class="operatxt-shop1">已拆单
					                     	<s:iterator value="listorder" var="listorder">
							                   	 <s:if test="#listorder.nowOperateType==4">
							                   	 	<p><s:date  name='#listorder.nowOperateDate'/></p>
						                   	 	</s:if>
					                   	 	</s:iterator>	
					                     </li> 
				                 </ul>
				             </div>
						 </div>
			</s:if>
			<s:else>
					 <div class="fn-clear">
					 <div class="oper-shop oper-shopbi-61">
		            	<ul>
		            		<s:if test="#request.orderMain.orderStatus == -1">
			                	<li class="oper-shop1"><span></span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>2</span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>5</span></li>
		                	</s:if>
			            	<s:elseif test="#request.orderMain.orderStatus == 1">
			                	<li class="oper-shop1"><span></span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>2</span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>5</span></li>
		                	</s:elseif>
			            		<s:elseif test="#request.orderMain.orderStatus == 2 || #request.orderMain.orderStatus ==3 ||#request.orderMain.orderStatus==4
									|| #request.orderMain.orderStatus==15 ||#request.orderMain.orderStatus==16||#request.orderMain.orderStatus==18 
									|| #request.orderMain.orderStatus == 20|| #request.orderMain.orderStatus == 21|| #request.orderMain.orderStatus == 22">
			                	<li class="oper-shop3"><span>1</span><i></i></li>
			                   	<li class="oper-shop1"><b></b><span></span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>5</span></li>
		                	</s:elseif>
			            	<s:elseif test="#request.orderMain.orderStatus == 5">
			                	<li class="oper-shop3"><span>1</span><i></i></li>
			                   	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
			                   	<li class="oper-shop1"><b></b><span></span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>5</span></li>
		                	</s:elseif>
		                	<s:elseif test="#request.orderMain.orderStatus == 6 && #request.isOrderAssess=='yes' " >
			                	<li class="oper-shop3"><span>1</span><i></i></li>
			                   	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
			                   	<li class="oper-shop3"><b></b><span>3</span><i></i></li>
			                   	<li class="oper-shop3"><b></b><span>4</span><i></i></li>
			                   	<li class="oper-shop1"><b></b><span></span></li>
		                	</s:elseif>
			            	<s:elseif test="#request.orderMain.orderStatus == 6 && #request.isOrderAssess!='yes'">
			                	<li class="oper-shop3"><span>1</span><i></i></li>
			                   	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
			                   	<li class="oper-shop3"><b></b><span>3</span><i></i></li>
			                   	<li class="oper-shop1"><b></b><span></span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>5</span></li>
		                	</s:elseif>
		                	<s:else >
		                		<li class="oper-shop1"><span></span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>2</span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
			                   	<li class="oper-shop2"><b></b><span>5</span></li>
		                	</s:else>
		                </ul>
		             </div>
					<div class="operatxt operatxt-148">      
		                 <ul>
		                 <s:if test="isParentOrder==0">
		                      		<li id="operatxtShop1" class="operatxt-shop1">提交订单
			                   	 	<s:iterator value="rootOrderOperateStateList" var="root">	
			                   	 	 	<s:if test="#root.nowOperateType==1">
					                   	 	<p><s:date  name='#root.nowOperateDate'/></p>
				                   	 	 </s:if>
			                   	 	</s:iterator>
			                   	 </li>
				                     <li id="operatxtShop2" class="operatxt-shop2">付款到卖家
					                     <s:iterator value="rootOrderOperateStateList" var="root">                   
					                   	 	<s:if test="#root.nowOperateType==2 && #root.nowOrderStatus==2">
					                   	 	   <p><s:date  name='#root.nowOperateDate'/></p>
				                   	 	    </s:if>
				                   	 	</s:iterator>
				                     </li>
		                      </s:if> 
		                      <s:else>
				                   	 <li id="operatxtShop1" class="operatxt-shop1">提交订单
				                   	 	<s:iterator value="listorder" var="listorder">
					                   	 	<s:if test="#listorder.nowOperateType==1">
						                   	 	<p><s:date  name='#listorder.nowOperateDate'/></p>
					                   	 	</s:if>
				                   	 	</s:iterator>
				                   	 </li>
				                     <li id="operatxtShop2" class="operatxt-shop2">付款到卖家
				                      <s:set var="payed" value="0"/>
					                     <s:iterator value="listorder" var="listorder">
					                     	<s:if test="#listorder.nowOperateType==2 && #listorder.nowOrderStatus==2&& #payed==0" >
						                   	 	<s:set var="payed" value="1"/>
						                   	 	<p><s:date  name='#listorder.nowOperateDate'/></p>
					                   	 	</s:if>		                   	 	
				                   	 	</s:iterator>
			                     </li>
			                     </s:else>
			                     <li id="operatxtShop3" class="operatxt-shop2">卖家已发货
			                     <s:set var="sended" value="0"/>
			                     	<s:iterator value="listorder" var="listorder">
					                   	 <s:if test="#listorder.nowOperateType==7 && #sended==0">
					                   		 <s:set var="sended" value="1"/>
					                   	 	<p><s:date  name='#listorder.nowOperateDate'/></p>
				                   	 	</s:if>
			                   	 	</s:iterator>
			                     	
			                     </li>
			                     <li id="operatxtShop4" class="operatxt-shop2">确认收货
				                      <s:iterator value="listorder" var="listorder">
					                   	 	<s:if test="#listorder.nowOperateType==9">
						                   	 	<p><s:date  name='#listorder.nowOperateDate'/></p>
					                   	 	</s:if>
				                   	 	</s:iterator>
			                     </li>
			                     <li id="operatxtShop5" class="operatxt-shop2">评价
			                     	 <s:iterator value="listorder" var="listorder">
			                     	 	<s:if test="#listorder.nowOrderStatus==6 && #request.isOrderAssess eq 'yes'
				                   	 		&& null!=orderAssessInfo ">
					                   	 		<p><s:date  name='orderAssessInfo.createDate'/></p>
				                   	 	</s:if>
			                   	 	</s:iterator>
			                     </li>
		                 </ul>
		             </div>
				</div>
		</s:else>
		</div>
	</div>
	<div class="user-m fn-t10">
		<div class="mt">
			<h3 class="curr">结算信息</h3>
		</div>
		<div class="mc">
				<div class="user-column fn-bggray fn-clear">
				<ul class="rightline">
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
					<s:if test="#request.orderMain.deliveryDateType==1">工作日送货</s:if>
					<s:elseif test="#request.orderMain.deliveryDateType==2">休息日送货</s:elseif>
					<s:else >工作日/休息日皆可送货</s:else><br/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:if test="#request.orderMain.confirmDelivery==0">送货前不用电话确认</s:if>
					<s:else>送货前需要电话确认</s:else>
					</li>				
					<s:if test="#request.orderMain.orderStatus != 16">	
					<li>物流公司：${orderMain.logisticsName}</li>
					<li>运单号码：${orderMain.logisticsOrderNo}</li>
					</s:if>
				</ul>
				<ul>
					<h2>发票信息</h2>
					<s:if test="#request.orderMain.invoiceInfoType!=null||#request.orderMain.invoiceInfoTitle!=null||#request.orderMain.invoiceInfoContent!=null">
						<li>类型：${orderMain.invoiceInfoType}</li>
						<li>抬头：${orderMain.invoiceInfoTitle}</li>
						<!-- <li>内容：${orderMain.invoiceInfoContent}</li> -->
					</s:if>
					<s:else>
					 买家未要求开发票
					</s:else>
				</ul>
				<p class="remark fn-red"><strong>备注：</strong>${orderMain.orderDescription}</p>
			</div>
            <s:if test="#request.isParentOrder == 1">
			 <h3>支付信息</h3>
				  <s:set var="sumPay" value="0.00" />
				  <s:set var="countOnlinePay" value="0.00" />
				  <s:set var="payMoney1" value="0.00" />
				  <s:set var="payMoney2" value="0.00" />
			 <s:if test="#request.isParentOrder == 1">				  
	             <s:iterator value="orderPayStatementList" var="orderPaystartment">
	             		<s:if test="#orderPaystartment.paymentWay==1">
	             			<s:set var="payMoney1" value="#orderPaystartment.orderMoney" />
	             		</s:if>
	             		<s:if test="#orderPaystartment.paymentWay==2">
	             			<s:set var="payMoney2" value="#orderPaystartment.orderMoney" />
	             		</s:if>
	             		<s:if test="#orderPaystartment.paymentWay>2">
	             			<s:set var="countOnlinePay" value="#countOnlinePay+#orderPaystartment.orderMoney" />
	             		</s:if>
	             		<s:set var="sumPay" value="#sumPay+#orderPaystartment.orderMoney" />
				</s:iterator>
			 </s:if>
             <div class="user-column fn-bggray fn-clear">
                 <ul class="fn-w100">
            
                 	<li><label>总金额：</label><span class="fn-gray">商品总额 ￥<fmt:formatNumber value="${orderMain.originalOrderSum}" type="currency" pattern="#,##0.00元"/> - 满减 ￥<fmt:formatNumber value="${orderMain.discountSum}" type="currency" pattern="#,##0.00元"/> 
                 		+ 加价购￥ <s:property  value="addPrice" />
                 	+ 运费 ￥<fmt:formatNumber value="${orderMain.fare}" type="currency" pattern="#,##0.00元"/> - 会员折扣 ￥   <fmt:formatNumber value="${orderMain.orderDisCount}" type="currency" pattern="#,##0.00元"/>   = <strong class="fn-red">￥<fmt:formatNumber value="${orderMain.amountPayable}" type="currency" pattern="#,##0.00元"/></strong></span></li>
                       <li><label>支付信息：</label><span class="fn-gray">优惠券支付￥<fmt:formatNumber value="${payMoney2}" type="currency" pattern="#,##0.00元"/> + 余额支付￥<fmt:formatNumber value="${payMoney1}" type="currency" pattern="#,##0.00元"/>+ 在线支付￥<fmt:formatNumber value="${countOnlinePay}" type="currency" pattern="#,##0.00元"/> =  <strong class="fn-red">￥<fmt:formatNumber value="${sumPay}" type="currency" pattern="#,##0.00元"/> </strong></span></li>
	                     <li class="fn-clear">
	                     	<dl class="order-track-item">  
	                     	   <dt>会员可获得：</dt>
	                     	     <dd class="fn-gray">
	                     	     	<p>会员积分${orderMain.totalCredit}分</p>
	                 			 <s:if test="null!=couponList && !couponList.isEmpty()">
		                     	      <s:iterator value="couponList" var="coupon">
			                     		<p><s:property value="#coupon.couponMoney"/>元优惠券，<s:property value="#coupon.productFilterTypeStr"/>，有效期<s:property value="#coupon.couponValidDay"/>天；</p>
			                    	 </s:iterator>
				                 </s:if>
	                     	     </dd>
	                       </dl>
	                     </li>
                     <span class="fn-gray"><a class="fn-blue" target="_blank" href="resetPwdgoUnRegMember.action">点这里转为会员</a></span>
                 </ul>

                 <%--<s:else>--%>
                 <%--<ul class="fn-w100">--%>
                 	<%--<li><label>总金额：</label><span class="fn-gray">￥<strong class="fn-red"><fmt:formatNumber value="${orderMain.amountPayable}" type="currency" pattern="#,##0.00元"/></strong></span></li>--%>
                    <%--<span class="fn-gray"><a class="fn-blue" target="_blank" href="resetPwdgoUnRegMember.action">点这里转为会员</a></span>--%>
                 <%--</ul>--%>
                 <%--</s:else>--%>
             </div>
            </s:if>
           
            <h3>订单商品</h3>
             <!-- 已拆分 -->
            <s:if test="#request.orderMain.orderStatus == 16">
           		 <p class="fn-bgred" >订单已拆分，商品列表请到各子订单中分别查看。</p>
            </s:if>
            <s:else>
			<div class="ui-table">
				<table class="ui-table user-table">
					<thead>
						<tr>
							<th class="td-s4">商品</th>
                            <th>购买数量</th>
                            <th>生产批号</th>
                            <th class="td-s2">金额小计</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="#request.orderMain.orderItemList"
							var="orderItemVar" status="orderItemStatus">
							<tr>
								<td>
									<a name="productlink" href="${cmsPagePath}${orderItemVar.productSkuId}.shtml" target="_blank">
									<b class="img"><img  src="${productImgServerUrl}${orderItemVar.defaultProductImage.imgPath7}" title="${orderItemVar.commodityName}" onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"></b>
									<div class="td-name fn-text-left fn-blue">${orderItemVar.commodityName}</div>
									</a>
								</td>
								<td>${orderItemVar.commodityNumber}</td>
								<td>${orderItemVar.commodityBatchNumber}</td>
								<td><fmt:formatNumber value="${orderItemVar.commoditySum}" type="currency" pattern="#,##0.00元"/>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
			</s:else>
			<div class="fn-t20 fn-clear">		

						<s:if test="#request.backFlag == 1">
							<a href="javascript:void(0)" date-backFlag="1" class="btn-cancel j_btn_back"><span>返回</span></a>
						</s:if>
						<s:elseif test="#request.backFlag == 2">
							<a href="javascript:void(0)" date-backFlag="2" 
							  data-orderEmail="${orderTrailInfo.emailAddress}" data-orderMobile="${orderTrailInfo.mobileNumber}" class="btn-cancel j_btn_back"><span>返回订单列表</span></a>
						</s:elseif>
						<s:else>
						     
						</s:else>	
						  <span class="fn-right">
							  <s:if test="#request.orderMain.orderStatus == 1 ">
							  	<a href="javascript:void(0);" date-backFlag="<s:property value="backFlag"/>" data-orderCode="${orderMain.orderCode}" data-orderMainId="<s:property value='orderMainId' />" 
							  	data-orderEmail="${orderTrailInfo.emailAddress}" data-orderMobile="${orderTrailInfo.mobileNumber}"
							  	class="red-btn fn-r5 j_pay btnp10">付款</a>
							  </s:if>
						  </span>
			</div>
		</div>
	</div>
</div>
<div>
 	<input type="hidden" id="orderMainId" value="<s:property value='orderMainId' />" />
 	<input type="hidden" id="orderStatusId" value="<s:property value='orderMain.orderStatus' />" />
 	<input type="hidden" id="isOrderAssess" value="<s:property value='isOrderAssess' />" />
</div>

