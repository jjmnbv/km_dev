<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

    <!--内容区域-->
    <section class="page-content">
      <s:form id="sform" name="sform" action="queryOrderList.action" method="post" theme="simple">
      <input name="isSureOk" type="hidden" value="<s:property value='isSureOk' />" id="isSureOk">
<input name="orderCodes1" type="hidden" value="<s:property value='orderMainCode' />" id="orderMainCode"> </input>
<input name="orderMainCode" type="hidden" value="" id="code">
<input name="orderUserid" type="hidden" value="" id="userId">
<input name="orderMainStatus" type="hidden" value="" id="status" />
<input name="isOrderDetail" type="hidden" value="1" id="isOrderDetail"/>

<%-- 
<input name="productImgServerUrl"  type="hidden" value="${productImgServerUrl}" id="productImgServerUrl"/>
<input name="cssJsPath" type="hidden" vlaue="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>" id="cssJsPath"/> --%>






<!--内容区域-->
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="btnabs">
                    <a href="initWapOrderLogistics.action?orderMain.orderStatus=${orderMain.orderStatus}&orderMain.customerId=${orderMain.customerId}
					&orderMain.logisticsOrderNo=${orderMain.logisticsOrderNo}
					&orderMain.logisticsName=${orderMain.logisticsName}
					&orderMain.logisticsCode=${orderMain.logisticsCode}
					&orderMainId=${orderMainId}
					&orderMain.orderCode=${orderMain.orderCode}&backFlag=2" class="btn btn-my btn-success btn-sm">
                                                       订单跟踪</a></div>
                    <ul class="text-list">
                        <li>订单编号：<span class="text-success">${orderMain.orderCode}</span></li>
                         <s:if test="#request.orderMain.orderStatus == 16">
                        <li>包裹数：<span><s:property  value="sonOrderMainList.size"/>个</span></li>
                        </s:if>
                            <s:else>
                            <p>包裹数：1个</p>
                            </s:else>
                        
                        
                        <li>进度：
                            <s:if test="#request.orderMain.orderType == 7">
                            	<font>&nbsp;</font>
                            </s:if>
                            <s:else>
                            	<ul class="steps steps-sm">
	                            	<s:if test="#request.orderMain.orderStatus == 16&&isParentOrder==1">
		                                <li class="active">
		                                    <span class="step">1</span>
		                                    <span class="title">提交订单</span>
		                                </li>
		                                <li class="active">
		                                    <span class="step">2</span>
		                                    <span class="title">完成支付</span>
		                                </li>
		                                <li class="active">
		                                    <span class="step">3</span>
		                                    <span class="title">已拆单</span>
		                                </li>
		                             </s:if>
		                              <s:else>
		                                <s:if test="#request.orderMain.orderStatus == 1 || #request.orderMain.orderStatus == -1">
		                               		<li class="active">
			                                    <span class="step">1</span>
			                                    <span class="title">提交订单</span>
			                                </li>
			                                <li>
			                                    <span class="step">2</span>
			                                    <span class="title">完成支付</span>
			                                </li>
			                                <li>
			                                    <span class="step">3</span>
			                                    <span class="title">发货</span>
			                                </li>
			                                <li>
			                                    <span class="step">4</span>
			                                    <span class="title">确认收货</span>
			                                </li>
			                                <li>
			                                    <span class="step">5</span>
			                                    <span class="title">评价</span>
			                                </li>
		                               </s:if>
		                               
		                               <s:elseif  test="#request.orderMain.orderStatus == 2 || #request.orderMain.orderStatus ==3 ||#request.orderMain.orderStatus==4
											|| #request.orderMain.orderStatus==15 ||#request.orderMain.orderStatus==16||#request.orderMain.orderStatus==18 ||#request.orderMain.orderStatus==20||#request.orderMain.orderStatus==21||#request.orderMain.orderStatus==22">
		                               		<li class="active">
			                                    <span class="step">1</span>
			                                    <span class="title">提交订单</span>
			                                </li>
			                                <li class="active">
			                                    <span class="step">2</span>
			                                    <span class="title">完成支付</span>
			                                </li>
			                                <li>
			                                    <span class="step">3</span>
			                                    <span class="title">发货</span>
			                                </li>
			                                <li>
			                                    <span class="step">4</span>
			                                    <span class="title">确认收货</span>
			                                </li>
			                                <li>
			                                    <span class="step">5</span>
			                                    <span class="title">评价</span>
			                                </li>
		                               </s:elseif>
		                               
		                               <s:elseif test="#request.orderMain.orderStatus == 5">
		                               		<li class="active">
			                                    <span class="step">1</span>
			                                    <span class="title">提交订单</span>
			                                </li>
			                                <li class="active">
			                                    <span class="step">2</span>
			                                    <span class="title">完成支付</span>
			                                </li>
			                                <li class="active">
			                                    <span class="step">3</span>
			                                    <span class="title">发货</span>
			                                </li>
			                                <li>
			                                    <span class="step">4</span>
			                                    <span class="title">确认收货</span>
			                                </li>
			                                <li>
			                                    <span class="step">5</span>
			                                    <span class="title">评价</span>
			                                </li>
		                               </s:elseif>
		                               
		                                <s:elseif test="#request.orderMain.orderStatus == 6 && #request.isOrderAssess=='yes' ">
		                               		<li class="active">
			                                    <span class="step">1</span>
			                                    <span class="title">提交订单</span>
			                                </li>
			                                <li class="active">
			                                    <span class="step">2</span>
			                                    <span class="title">完成支付</span>
			                                </li>
			                                <li class="active">
			                                    <span class="step">3</span>
			                                    <span class="title">发货</span>
			                                </li>
			                                <li class="active">
			                                    <span class="step">4</span>
			                                    <span class="title">确认收货</span>
			                                </li>
			                                <li class="active">
			                                    <span class="step">5</span>
			                                    <span class="title">评价</span>
			                                </li>
		                             </s:elseif>
		                               <s:elseif test="#request.orderMain.orderStatus == 6 && #request.isOrderAssess!='yes' ">
		                               		<li class="active">
			                                    <span class="step">1</span>
			                                    <span class="title">提交订单</span>
			                                </li>
			                                <li class="active">
			                                    <span class="step">2</span>
			                                    <span class="title">完成支付</span>
			                                </li>
			                                <li class="active">
			                                    <span class="step">3</span>
			                                    <span class="title">发货</span>
			                                </li>
			                                <li class="active">
			                                    <span class="step">4</span>
			                                    <span class="title">确认收货</span>
			                                </li>
			                                <li>
			                                    <span class="step">5</span>
			                                    <span class="title">评价</span>
			                                </li>
		                               </s:elseif>
		                               <s:else>
		                               		<li class="active">
			                                    <span class="step">1</span>
			                                    <span class="title">提交订单</span>
			                                </li>
			                                <li>
			                                    <span class="step">2</span>
			                                    <span class="title">完成支付</span>
			                                </li>
			                                <li>
			                                    <span class="step">3</span>
			                                    <span class="title">发货</span>
			                                </li>
			                                <li>
			                                    <span class="step">4</span>
			                                    <span class="title">确认收货</span>
			                                </li>
			                                <li>
			                                    <span class="step">5</span>
			                                    <span class="title">评价</span>
			                                </li>
		                               </s:else>
		                              </s:else>
		                            </ul>  
                            </s:else>
                            </li>
                        <li>
                        
                         <s:if test="expressPath.data!=null">
				                                  最新动态<span class="text-muted">&lt;[<s:date name="expressPath.data[0].ftime" format="yyyy-MM-dd HH:mm:ss"/>]
                            <s:property value="expressPath.data[0].context" /> </span>
				        </s:if>
				  
				       <s:elseif test="listorder!=null && listorder.size >=1">
				       		<s:iterator value="#request.orderAllStates" var="orderState" status="orderStates">
					             <s:if test="${orderStates.first}">
					             	 最新动态<span>[<s:date name="#orderState.NOWOPERATEDATE" format="yyyy-MM-dd HH:mm:ss"/>] 
					             	${orderState.OPERATEINFO}
					             </s:if>
		                     </s:iterator>
                          </span>
				       </s:elseif>
				               <s:else>暂无动态  </s:else>			 
                        </li>         
                    </ul>
                </div>
            </div>
            <s:if test="${orderMain.consumptionAmount>0}">
            <div class="ibox float-e-margins">
            <div class="ibox-title">
                    <h5>可获返利</h5>
                    <strong class="pull-right text-danger">￥<fmt:formatNumber value="${orderMain.consumptionAmount}" type="currency" pattern="#,##0.00元"/></strong>
                </div>
            </div>
            </s:if>
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                     <s:set var="sumPay" value="0.00" />
				  <s:set var="countOnlinePay" value="0.00" />
				  <s:set var="payMoney1" value="0.00" />
				  <s:set var="payMoney2" value="0.00" />
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
                    <h5>应付金额</h5>
                    <strong class="pull-right text-danger">￥<fmt:formatNumber value="${moneyStates.amountPayable}" type="currency" pattern="#,##0.00元"/></strong><br>
                </div>
                
                <s:if test="orderMain.orderType == 7">
                	<div class="ibox-content">
	                    <ul class="list-unstyled">
	                        <li>商品总金额：<span class="text-danger pull-right">￥<fmt:formatNumber value="${moneyStates.originalOrderSum}" type="currency" pattern="#,##0.00元"/></span></li>
	                        <li class="clearfix">
	                            <div class="col-sm-1"></div>
	                            <div class="col-sm-11 sell-le-border p-r-0">
	                            	<s:if test="orderMain.orderType == 7">
	                                	<s:if test="#request.moneyStates.payStates == 'no'">
	                                		<p class="sell-le-quan">定金(未支付)<span class="text-danger pull-right">￥<fmt:formatNumber value="${moneyStates.depositSum}" type="currency" pattern="#,##0.00元"/></span></p>
				                       		<p class="sell-le-quan">尾款(未支付)<span class="text-danger pull-right">￥<fmt:formatNumber value="${moneyStates.finalPayment}" type="currency" pattern="#,##0.00元"/></span></p>
	                                	</s:if>
	                                	<s:elseif test="#request.moneyStates.payStates == 'deposit'">
	                                		<p class="sell-le-quan">定金(已支付)<span class="text-danger pull-right">￥<fmt:formatNumber value="${moneyStates.depositSum}" type="currency" pattern="#,##0.00元"/></span></p>
				                       		<p class="sell-le-quan">尾款(未支付)<span class="text-danger pull-right">￥<fmt:formatNumber value="${moneyStates.finalPayment}" type="currency" pattern="#,##0.00元"/></span></p>	
	                                	</s:elseif>
	                                	<s:elseif test="#request.moneyStates.payStates == 'final'">
	                                		<p class="sell-le-quan">定金(已支付)<span class="text-danger pull-right">￥<fmt:formatNumber value="${moneyStates.depositSum}" type="currency" pattern="#,##0.00元"/></span></p>
				                       		<p class="sell-le-quan">尾款(已支付)<span class="text-danger pull-right">￥<fmt:formatNumber value="${moneyStates.finalPayment}" type="currency" pattern="#,##0.00元"/></span></p>	
	                                	</s:elseif>
	                                	<s:else>
				                       		<p class="sell-le-quan">定金(未支付)<span class="text-danger pull-right">￥<fmt:formatNumber value="${moneyStates.depositSum}" type="currency" pattern="#,##0.00元"/></span></p>
				                       		<p class="sell-le-quan">尾款(未支付)<span class="text-danger pull-right">￥<fmt:formatNumber value="${moneyStates.finalPayment}" type="currency" pattern="#,##0.00元"/></span></p>
				                       	</s:else>
				                   </s:if>
	                            </div>
	                        </li>
	                        <li>配送费：<span class="text-danger pull-right">￥<fmt:formatNumber value="${orderMain.fare}" type="currency" pattern="#,##0.00元"/></span></li>
	                    </ul>
	                </div>
                </s:if>
                <s:else>
                	<div class="ibox-content">
	                  <s:set var="sumPay" value="0.00" />
					  <s:set var="countOnlinePay" value="0.00" />
					  <s:set var="payMoney1" value="0.00" />
					  <s:set var="payMoney2" value="0.00" />
	                 
	                     <s:iterator value="#request.orderPayStatementList" var="orderPaystartment">
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
	                    <ul class="list-unstyled">
	                        <li>商品总金额：<span class="text-danger pull-right">￥<fmt:formatNumber value="${orderMain.originalOrderSum}" type="currency" pattern="#,##0.00元"/></span></li>
	                        <li>快递费用：<span class="text-danger pull-right">+￥<fmt:formatNumber value="${orderMain.fare}" type="currency" pattern="#,##0.00元"/></span></li>
	                        <li>加购价：<span class="text-danger pull-right">+￥<s:property  value="addPrice" />元</span></li>
	                        <li>满减金额：<span class="text-danger pull-right">-￥<fmt:formatNumber value="${orderMain.discountSum}" type="currency" pattern="#,##0.00元"/></span></li>
	                        <li>余额支付：<span class="text-danger pull-right">-￥<fmt:formatNumber value="${payMoney1}" type="currency" pattern="#,##0.00元"/></span></li>
	                        <li>优惠券支付：<span class="text-danger pull-right">-￥<fmt:formatNumber value="${payMoney2}" type="currency" pattern="#,##0.00元"/> </span></li>
	                        <li>在线支付：<span class="text-danger pull-right">-￥<fmt:formatNumber value="${countOnlinePay}" type="currency" pattern="#,##0.00元"/></span></li>
	                   </ul>
	                </div>
                </s:else>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>收货人信息</h5>
                </div>
                <div class="ibox-content">
                    <ul class="text-list">
                        <li>${orderMain.consigneeName}<span>,${orderMain.consigneeMobile}</span></li>
                        <li>${orderMain.consigneeAddr}</li>
                    </ul>
                </div>
            </div>
            <s:if test="orderMain.orderType == 7">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                    <h5>尾款通知手机</h5>
	                    <strong style="float: right !important;">${informpayTels}</strong>
	                </div>
	            </div>
            </s:if>
            
            
              <!-- 拆单-->
             <s:if test="#request.orderMain.orderStatus == 16">
              <s:iterator value="#request.sonOrderMainList" var="sonOrderMainList" status="sonOrderMainStatus">
              
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>包裹<s:property value="#sonOrderMainStatus.index+1"/></h5>
                   
                    
                             <s:if test="#sonOrderMainList.orderStatus == 12">
                         
                          <a href="javascript:void(0);" class="pull-right label label-warning">联系客服</a>
                         </s:if>
                        <s:if test="#sonOrderMainList.orderStatus == 2 || #sonOrderMainList.orderStatus == 25 || #sonOrderMainList.orderStatus == 20|| #sonOrderMainList.orderStatus == 21|| #sonOrderMainList.orderStatus == 22">
                       <a href="javascript:void(0);" data-orderId="rows<s:property value="#sonOrderMainList.orderId"/>"  class="pull-right label label-warning j_byAgain">再购买</a>
                       <a href="script:void(0)" data-orderType="<s:property value="#sonOrderMainList.orderType"/>" data-orderStatus="<s:property value="#sonOrderMainList.orderStatus"/>" 
                       data-orderCode="<s:property value="#sonOrderMainList.orderCode"/>" data-userId="${orderMain.customerId}" data-status="-1" class="pull-right label label-warning j_Cancel">
                                                    取消订单</a> 
                        </s:if>
                        <s:if test="#sonOrderMainList.orderStatus == 5">
                        <a href="javascript:void(0);" data-orderId="rows<s:property value="#sonOrderMainList.orderId"/>"   class="pull-right label label-warning j_byAgain">再购买</a>
                      <a href="javascript:void(0);"  data-orderCode="<s:property value="#sonOrderMainList.orderCode"/>" data-status="6"  class="pull-right label label-warning j_sureTosureOrder">确认收货
                      </a>
                       </s:if>
                       <s:if test="#sonOrderMainList.orderStatus == 3 || #sonOrderMainList.orderStatus == 4">
                        <a href="javascript:void(0);" data-orderId="rows<s:property value="#sonOrderMainList.orderId"/>"  class="pull-right label label-warning j_byAgain">再购买</a>
                       </s:if>
                       <s:if test="#sonOrderMainList.orderStatus == 6">
                       <a href="javascript:void(0);" data-orderId="rows<s:property value="#sonOrderMainList.orderId"/>"  class="pull-right label label-warning j_byAgain">再购买</a>
                        <a class="pull-right label label-warning" href="applyWapPrepare.action?searchKeyword.keyword=<s:property value="#sonOrderMainList.orderCode"/>&checkOverDate=false">退换货</a>
                       
                       </s:if>
                </div>
                <div class="ibox-content category-box">
                    <ul class="tabs-lst">   
                    <s:iterator value="#sonOrderMainList.orderItemList" var="orderItemVar" status="orderItemStatus">
                        <li>
                         <input value="${orderItemVar.defaultProductImage.skuId}"  name="rows<s:property value='#sonOrderMainList.orderId'/>"  type="hidden" /> 
                            <div class="list-thumb"><a href="${productPicPathWAP}${orderItemVar.defaultProductImage.skuId}.shtml"> 
                          <img src='${productImgServerUrl}${orderItemVar.defaultProductImage.imgPath7}'  title="${orderItemVar.commodityName}" onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"> </a></div>
                            <div class="list-descriptions">
                                <div class="list-descriptions-wrapper">
                                    <div class="product-name">
                                        <a href="${productPicPathWAP}${orderItemVar.defaultProductImage.skuId}.shtml">${orderItemVar.commodityName}</a><span style="color:red;">X${orderItemVar.commodityNumber}</span>
                                    </div>
                                    <div class="price-spot">
                                        <strong class="product-price">
                                       	<s:if test="${orderItemVar.commodityType==4}">
                                    		   赠品 
                                        </s:if>
                                       	<s:if test="${orderItemVar.commodityType==6}">
                                    		   附赠
                                        </s:if>
                                        <s:else>
											￥<fmt:formatNumber value="${orderItemVar.commoditySum}" type="currency" pattern="#,##0.00元"/>
                                        </s:else>
                                        </strong> 
                                    </div>
                                       <s:if test="${orderItemVar.commodityType!=4}">
                                    <div class="price-spot">
                                    <s:if test="consumptionRateMap[#orderItemVar.defaultProductImage.skuId]>0">
                                   <span class="product-price">
                                 		  可获返利金额：               
                                 		  <s:property value="consumptionRateMap[#orderItemVar.defaultProductImage.skuId]" />元 <%-- 
                        <fmt:formatNumber  value="consumptionRateMap[#orderItemVar.defaultProductImage.skuId]*#orderItemVar.commodityNumber" type="number" pattern="#,##0.00元"/>
                                 --%>      </span>
                                 </s:if>
                                    </div>
                                    </s:if>
                                    
                                </div>
                            </div>
                        </li>
                        </s:iterator>  
                    </ul>
									<a href="initWapOrderLogistics.action?orderMain.orderStatus=<s:property value="#sonOrderMainList.orderStatus"/>&orderMain.customerId=<s:property value="#sonOrderMainList.customerId"/>&orderMain.logisticsOrderNo=<s:property value="#sonOrderMainList.logisticsOrderNo"/>&orderMain.logisticsName=<s:property  value="#sonOrderMainList.logisticsName"/>&orderMain.logisticsCode=<s:property  value="#sonOrderMainList.logisticsCode"/>&orderMainId=<s:property value="#sonOrderMainList.orderId"/>&orderMain.orderCode=<s:property  value="#sonOrderMainList.orderCode"/>&backFlag=2"
										class="btn btn-success btn-block">订单跟踪</a>
								</div>
                
                
            </div>
             </s:iterator>
           </s:if>
           <!-- 不 拆单-->
           <s:else>
            <div class="ibox float-e-margins">
            	<s:if test="orderMain.orderType == 7">
         			<div class="ibox-title">
	                    <h5>包裹1</h5>
	                    <div class="ibox-tools">
	                    	<s:if test="orderMain.orderStatus == 1">
					  		<s:if test="${allState.depositEndTime == 'yes'}">
								<span class="pull-right" id="frontMoneySpan">
						            <a href="script:void(0)" data-orderCode="${orderMain.orderCode}"  class="btn btn-danger btn-my btn-sm j_pay_deposit">支付定金</a>
						            <a href="script:void(0)" data-orderType="${orderMain.orderType}" data-orderStatus="${orderMain.orderStatus}" data-orderCode="${orderMain.orderCode}" data-userId="${orderMain.customerId}" data-status="-1"class="btn btn-primary btn-my btn-sm j_Cancel">取消订单</a>
								</span>
					  	 	</s:if>
						</s:if>  
			            <s:if test="orderMain.orderStatus == 23">
					  		<s:if test="${allState.finalpayStartTime == 'no'}"> 
					  			<s:if test="${allState.finalpayEndTime == 'yes'}">
									<span class="pull-right" id="finalPaymentSpan">
							            <a href="script:void(0)" data-orderCode="${orderMain.orderCode}"  class="btn btn-danger btn-my btn-sm j_pay_retainage">支付尾款</a>
							        </span>
						        </s:if>
						        <s:else>
					  	 		<span class="pull-right" id="finalPaymentSpan" style="display:none;">
						            <a href="script:void(0)" data-orderCode="${orderMain.orderCode}"  class="btn btn-danger btn-my btn-sm j_pay_retainage">支付尾款</a>
						        </span>
					  	 		</s:else>
					  	 	</s:if>
					  	 	<s:else>
					  	 		<span class="pull-right" id="finalPaymentSpan" style="display:none;">
						            <a href="script:void(0)" data-orderCode="${orderMain.orderCode}"  class="btn btn-danger btn-my btn-sm j_pay_retainage">支付尾款</a>
						        </span>
					  	 	</s:else>
						</s:if>  
			            <s:if test="orderMain.orderStatus == 2 || orderMain.orderStatus == 25|| orderMain.orderStatus == 20|| orderMain.orderStatus == 21|| orderMain.orderStatus == 22">
					  		<s:if test="${allState.deliveryEndTime == 'no'}"> 
					  			<span class="pull-right">
						            <a class="btn btn-primary btn-my btn-sm"  href="applyWapPrepare.action?searchKeyword.keyword=${orderMain.orderCode}&checkOverDate=false">申请赔付</a>
								</span>
					  	 	</s:if>
						</s:if>  
						<s:if test="orderMain.orderStatus == -1">
							<span class="pull-right">
							<a href="javascript:void(0);" class="btn btn-primary btn-my btn-sm j_delete"  data-orderCode="${orderMain.orderCode}"  data-userId="${orderMain.customerId}">删除</a>
							</span>
						</s:if>
			           <s:if test="orderMain.orderStatus == 12">
			             <span class="pull-right">
			                <a href="javascript:void(0);" class="btn btn-danger btn-xs">
			                	联系客服</a>
			             </span>
			           </s:if>
			          <s:if test="orderMain.orderStatus == 5">
			            <span class="pull-right">
			            <a href="javascript:void(0);"  data-orderCode="${orderMain.orderCode}" data-status="6"  class="btn btn-my btn-success btn-sm j_sureTosureOrder">确认收货</a>
			            </span> 
			          </s:if>
			          <s:if test="orderMain.orderStatus == 3 || orderMain.orderStatus == 4 || orderMain.orderStatus == 15">
			           	<s:if test="${allState.deliveryEndTime == 'no'}"> 
					  			<span class="pull-right">
						            <a class="btn btn-primary btn-my btn-sm"  href="applyWapPrepare.action?searchKeyword.keyword=${orderMain.orderCode}&checkOverDate=false">申请赔付</a>
								</span>
					  	</s:if>
			         </s:if>
			         <s:if test="orderMain.orderStatus == 6">
			         	<s:if test="${allState.judgeSalesReturn == 'yes'}">
			         		<s:if test="${allState.finishDate == 'yes'}">
								<span class="pull-right">
						          <a class="btn btn-primary btn-my btn-sm"  href="applyWapPrepare.action?searchKeyword.keyword=${orderMain.orderCode}&checkOverDate=false">退换货</a>
						        </span>
					  	 	</s:if>
				  	 	</s:if>
			         </s:if>
	                    	
	                    </div>
	                </div>
         		</s:if>
         		<s:else>
         			<div class="ibox-title">
	                    <h5>包裹1</h5>
	                          <s:if test="#sonOrderMainList.orderStatus == 12">
	                         
	                            <a href="javascript:void(0);" class="pull-right label label-warning">联系客服</a>
	                         </s:if>
	                          <s:if test="#sonOrderMainList.orderStatus == -1">
			                       <a href="javascript:void(0);" data-orderId="rows<s:property value="#sonOrderMainList.orderId"/>"  class="pull-right label label-warning j_byAgain">再购买</a>
			                       <a href="script:void(0)"  
			                       data-orderCode="<s:property value="#sonOrderMainList.orderCode"/>" data-userId="${orderMain.customerId}" 
			                       class="pull-right label label-warning j_delete">删除</a> 
	                        </s:if>
	                        <s:if test="#sonOrderMainList.orderStatus ==1">
			                       <a href="javascript:void(0);" data-orderCode="${orderMain.orderCode}"  class="pull-right label label-warning j_byAgain">再购买</a>
			                       <a script:void(0)" data-orderType="${orderMain.orderType}" data-orderStatus="${orderMain.orderStatus}" data-orderCode="${orderMain.orderCode}" data-userId="${orderMain.customerId}" data-status="-1"
			                       class="pull-right label label-warning j_Cancel">取消订单</a> 
	                        </s:if>
	                         <s:if test="#sonOrderMainList.orderStatus == 2 || #sonOrderMainList.orderStatus == 25 || #sonOrderMainList.orderStatus == 20|| #sonOrderMainList.orderStatus == 21|| #sonOrderMainList.orderStatus == 22">
	                       <a href="javascript:void(0);" data-orderId="rows<s:property value="#sonOrderMainList.orderId"/>"  class="pull-right label label-warning j_byAgain">再购买</a>
	                       <a href="script:void(0)" data-orderType="<s:property value="#sonOrderMainList.orderType"/>" data-orderStatus="<s:property value="#sonOrderMainList.orderStatus"/>" 
	                       data-orderCode="<s:property value="#sonOrderMainList.orderCode"/>" data-userId="${orderMain.customerId}" data-status="-1" class="pull-right label label-warning j_Cancel">
	                                                    取消订单</a>    
	                        </s:if>
	                        
	                        
	                        <s:if test="#sonOrderMainList.orderStatus == 5">
	                        <a href="javascript:void(0);" data-orderId="rows<s:property value="#sonOrderMainList.orderId"/>"   class="pull-right label label-warning j_byAgain">再购买</a>
	                         <a href="javascript:void(0);"  data-orderCode="<s:property value="#sonOrderMainList.orderCode"/>" data-status="6"  class="pull-right label label-warning j_sureTosureOrder">确认收货
	                      </a>
	                       </s:if>
	                       <s:if test="#sonOrderMainList.orderStatus == 3 || #sonOrderMainList.orderStatus == 4">
	                        <a href="javascript:void(0);" data-orderId="rows<s:property value="#sonOrderMainList.orderId"/>"  class="pull-right label label-warning j_byAgain">再购买</a>
	                       </s:if>
	                       <s:if test="#sonOrderMainList.orderStatus == 6">
	                       <a href="javascript:void(0);" data-orderId="rows<s:property value="#sonOrderMainList.orderId"/>"  class="pull-right label label-warning j_byAgain">再购买</a>
	                        <a class="pull-right label label-warning" href="applyWapPrepare.action?searchKeyword.keyword=<s:property value="#sonOrderMainList.orderCode"/>&checkOverDate=false">退换货</a>
	                       
	                       </s:if>
	                </div>
         		</s:else>
                <div class="ibox-content category-box">
                    <ul class="tabs-lst">   
  
                      <s:iterator value="#request.orderMain.orderItemList" var="orderItemVar" status="orderItemStatus">
                        <li>
                        
                         
                         
                            <div class="list-thumb"><a href="${productPicPathWAP}${orderItemVar.defaultProductImage.skuId}.shtml"> 
                          
                          <img src='${productImgServerUrl}${orderItemVar.defaultProductImage.imgPath7}'title="${orderItemVar.commodityName}" onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"> </a></div>
                            <div class="list-descriptions">
                                <div class="list-descriptions-wrapper">
                                    <div class="product-name">
                                        <a href="${productPicPathWAP}${orderItemVar.defaultProductImage.skuId}.shtml">${orderItemVar.commodityName}</a> <span style="color:red;">X${orderItemVar.commodityNumber}</span>
                                    </div>
                                    <div class="price-spot">
                                        <strong class="product-price">
                                        <s:if test="${orderItemVar.commodityType==4}">
                                    		   赠品 
                                        </s:if>
                                        <s:if test="${orderItemVar.commodityType==6}">
                                    		   附赠
                                        </s:if>
                                        <s:else>
											￥<fmt:formatNumber value="${orderItemVar.commoditySum}" type="currency" pattern="#,##0.00元"/>
                                        </s:else></strong>
                                    </div>
                                      <s:if test="${orderItemVar.commodityType!=4}">
                                      <div class="price-spot">
                                      <s:if test="${orderItemVar.spreadEffect.consumptionAmount>0}">
                                   <span class="product-price">
                                 		  可获返利金额：<fmt:formatNumber value="${orderItemVar.spreadEffect.consumptionAmount}" type="currency" pattern="#,##0.00元"/>
                                 	</span>
                                 </s:if>	
                                 	</div>
                                     </s:if>
                                </div>
                            </div>
                        </li>
                        </s:iterator>  
                    </ul>
                    
                      <a href="initWapOrderLogistics.action?orderMain.orderStatus=${orderMain.orderStatus}&orderMain.customerId=${orderMain.customerId}&orderMain.logisticsOrderNo=${orderMain.logisticsOrderNo}&orderMain.logisticsName=${orderMain.logisticsName}&orderMain.logisticsCode=${orderMain.logisticsCode}&orderMainId=${orderMainId}&orderMain.orderCode=${orderMain.orderCode}&backFlag=2" class="btn btn-success btn-block">
				<%-- 	
                     <a href="initWapOrderLogistics.action?orderMain.orderStatus=<s:property value="#sonOrderMainList.orderStatus"/>
                                &orderMain.customerId=<s:property value="#sonOrderMainList.customerId"/>
                                &orderMain.logisticsOrderNo=<s:property value="#sonOrderMainList.logisticsOrderNo"/>
                                &orderMain.logisticsName=<s:property  value="#sonOrderMainList.logisticsName"/>
                                &orderMain.logisticsCode=<s:property  value="#sonOrderMainList.logisticsCode"/>
                                &orderMainId=<s:property value="#sonOrderMainList.orderId"/>
                                &orderMain.orderCode=<s:property  value="#sonOrderMainList.orderCode"/>" class="btn btn-success btn-block"> --%>
                   订单跟踪</a>
                </div>
            </div>
        </s:else>
              <div class="ibox float-e-margins clearfix">
                <div class="list-box box-w50 pull-left">
                    <h3>支付及配送方式</h3>
                    <ul>
                        <li>支付方式：${orderMain.payMethodValue}</li>
                        <li>配送方式：快递 ￥<fmt:formatNumber value="${orderMain.fare}" type="currency" pattern="#,##0.00元"/><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                       <s:if test="${orderMain.deliveryDateType==1}">工作日送货</s:if>
                       <s:elseif test="${orderMain.deliveryDateType==2}">休息日送货</s:elseif>
                       <s:else>工作日/休息日皆可送货</s:else>
                       </li>
                         <s:if test="isMainOrder == 0">
                              <li>物流公司：${orderMain.logisticsName}</li>
                              <li>运单号：${orderMain.logisticsOrderNo}</li>
                          </s:if>
                    </ul>
                </div>
                <div class="list-box box-w50">
                    <h3>发票信息</h3>
                    <ul>
                       
                        
                        <s:if test="#request.orderMain.invoiceInfoType!=null||#request.orderMain.invoiceInfoTitle!=null||#request.orderMain.invoiceInfoContent!=null">
                                <li>发票类型：${orderMain.invoiceInfoType}</li>
                                <li>发票抬头：：${orderMain.invoiceInfoTitle}</li>
                                </s:if>
                                
                                <!-- <li>发票内容：明细</li> -->
                                <s:else>
                                <li class="help-block">买家未要求开发票</li>
                                </s:else>
                    </ul>
                   
                </div>
            </div>
            <s:if test="orderMain.orderType == 7">
            	<s:if test="orderMain.orderStatus == 1 || orderMain.orderStatus == 23">
	             	<div class="ibox-content category-box">
						<strong class="btn btn-success btn-block" style="background-color:#d9534f;border-color:#d43f3a;" id="countDownStrong" text="${orderMain.orderCode}">${allState.countDown}</strong>
					</div>
				</s:if>
			</s:if>	  
        </div>
    </div>
</div>
   </s:form>
    </section>
