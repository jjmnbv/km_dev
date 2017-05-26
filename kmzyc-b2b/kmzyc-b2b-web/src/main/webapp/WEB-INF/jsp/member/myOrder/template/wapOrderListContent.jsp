<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!--内容区域-->

<div class="container category-box">
    <s:form id="sform" name="sform" action="queryOrderList.action" method="post" theme="simple">
    <s:if test="searchKeyword.orderStatus==1">
    <input id="selectOrderStatus" type="hidden" value="1"/>
    </s:if>
    <s:elseif test="searchKeyword.orderStatus==2">
    <input id="selectOrderStatus" type="hidden" value="2"/>
    </s:elseif>
    <s:else>
    <input id="selectOrderStatus" type="hidden" value="0"/>
    </s:else>
    
    
    <input name="isSureOk" type="hidden" value="<s:property value='isSureOk' />" id="isSureOk">
	<input name="orderCodes1" type="hidden" value="<s:property value='orderMainCode' />" id="orderMainCode"> </input>
	<input name="orderMainCode" type="hidden" value="" id="code">
	<input name="orderUserid" type="hidden" value="" id="userId">
	<input name="orderMainStatus" type="hidden" value="" id="status"></input>
	<input name="productImgServerUrl"  type="hidden" value="${productImgServerUrl}" id="productImgServerUrl"/>
	<input name="cssJsPath" type="hidden" vlaue="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>" id="cssJsPath"/>
	<s:if test="#request.pagintion.recordList.size==0">
	<div class="help-block text-center">
		<i class="menu-icon icon-uniE62D"></i>
			 您还没有相关订单，
	            <a class="shop-co-red" href="<%=ConfigurationUtil.getString("staticPath_WAP")%>/index.html">去首页</a>&nbsp;&nbsp;挑选喜欢的商品!
	</div>
    </s:if> 
    <div id="body">
	<s:iterator value="#request.pagintion.recordList" var="orderMainVar" status="orderMainStatus">
    <section class="saleinfo">
        <div class="order-list">
            <ul class="text-list">
                <li>订单金额：<strong class="text-danger">￥<fmt:formatNumber value="${orderMainVar.amountPayable}" type="currency" pattern="#,##0.00元"/></strong></li>
                <s:if test="#orderMainVar.orderType==7">
               		<c:forEach var="orderMainMoney" items="${reserveList}" varStatus="orderMainMoneys"> 
					  	<c:if test="${orderMainMoney.orederCode == orderMainVar.orderCode}"> 
					  		<c:set var="amountPayable" value="${orderMainMoney.amountPayable}" scope="request"  />
					  		<c:set var="depositSum" value="${orderMainMoney.depositSum}" scope="request"  />
					  		<li>定金金额：<font><%=request.getAttribute("depositSum")%></font></li>
               				<li>尾款金额：<font><%=request.getAttribute("amountPayable")%></font></li>
					  	 </c:if>
					 </c:forEach>
                </s:if>
                <li>订单编号：<a name="productlink" href="queryWapOrderDetail.action?orderMainCode=${orderMainVar.orderCode}" >${orderMainVar.orderCode}</a></li>
                <li>下单时间：<span class="text-muted"><s:date name="#orderMainVar.createDate" format="yyyy-MM-dd"/>  <s:date name="#orderMainVar.createDate" format="HH:mm:ss"/></span></li>
            </ul>
            <div class="btnabs-tr0">
    <a href="initWapOrderLogistics.action?orderMain.orderStatus=${orderMainVar.orderStatus}&orderMain.customerId=${orderMainVar.customerId}
					&orderMain.logisticsOrderNo=${orderMainVar.logisticsOrderNo}
					&orderMain.logisticsName=${orderMainVar.logisticsName}
					&orderMain.logisticsCode=${orderMainVar.logisticsCode}
					&orderMainId=${orderMainVar.orderId}
					&orderMain.orderCode=${orderMainVar.orderCode}&backFlag=3" class="btn btn-my btn-success btn-sm">
            	订单跟踪
            	</a>
            </div>
        </div>
        
        <ul class="tabs-lst listpic-inline">
            <li>
                <s:iterator value="#orderMainVar.orderItemList" var="orderItemVar" status="orderItemStatus">
                  <s:if test="#orderItemStatus.index<3">
                <input value="${orderItemVar.defaultProductImage.skuId}"  name="rows${orderMainVar.orderId}"  type="hidden" /> 
                <div class="list-thumb">
                <a name="productlink" href="queryWapOrderDetail.action?orderMainCode=${orderMainVar.orderCode}" >
                <img  src="${productImgServerUrl}${orderItemVar.defaultProductImage.imgPath7}" title="${orderItemVar.commodityName}" onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'">
                </a> 
                </div>
                </s:if>
                </s:iterator>
                 <s:if test="#orderMainVar.orderItemList.size>3">...</s:if>
                <s:if test="#orderMainVar.orderItemList.size==1">
                <div class="list-descriptions">
                    <div class="list-descriptions-wrapper">
                        <div class="product-name">
                        <a name="productlink" href="queryWapOrderDetail.action?orderMainCode=${orderMainVar.orderCode}" >
						${orderItemVar.commodityName}
						</a>
						</div>
                    </div>
                </div>
                </s:if>
            </li>
        </ul>
        
            
        <div class="help-block">
            <div class="pull-left">
                订单状态：	<s:if test="#orderMainVar.orderStatus == -1"><span class="text-danger" >已取消</span></s:if>
				<s:if test="#orderMainVar.orderStatus == 1"><span class="text-danger" class="fn-red">未付款</span></s:if>
	            <s:if test="#orderMainVar.orderStatus == 2 || #orderMainVar.orderStatus == 20|| #orderMainVar.orderStatus == 21|| #orderMainVar.orderStatus == 22"><span class="text-danger">已付款</span></s:if>
	            <s:if test="#orderMainVar.orderStatus == 25"><span class="text-danger">已审核</span></s:if>
				<s:if test="#orderMainVar.orderStatus == 3 || #orderMainVar.orderStatus == 4|| #orderMainVar.orderStatus == 15|| #orderMainVar.orderStatus == 18"><span class="text-danger">配货中</span></s:if>
				<s:if test="#orderMainVar.orderStatus == 5"><span class="text-danger<s:property value='#orderMainStatus.index' />">已发货</span></s:if>
				<s:if test="#orderMainVar.orderStatus == 6"><span class="text-danger<s:property value='#orderMainStatus.index' />">已完成</span></s:if>
				<s:if test="#orderMainVar.orderStatus == 7"><span class="text-danger">已评价</span></s:if>
				<s:if test="#orderMainVar.orderStatus == 12"><span class="text-danger" class="fn-red">送货失败</span></s:if>
				<s:if test="#orderMainVar.orderStatus == 16"><span class="text-danger">已拆分</span></s:if>
				<s:if test="#orderMainVar.orderStatus == -3"><span class="text-danger" >异常订单</span></s:if>
				<s:if test="#orderMainVar.orderStatus == 23"><span class="text-danger" >待付尾款</span></s:if>
            </div>
            
            <s:if test="#orderMainVar.orderType==7">
	            <s:if test="#orderMainVar.orderStatus == 1">
	            	<c:forEach var="orderMainState" items="${reserveList}" varStatus="orderMainStates">
					  	<c:if test="${orderMainState.orederCode == orderMainVar.orderCode}"> 
					  		<s:if test="${orderMainState.depositEndTime == 'yes'}"> 
								<span class="pull-right" name="frontMoneySpan" text="${orderMainVar.orderCode}" >
						            <a href="script:void(0)" data-orderCode="${orderMainVar.orderCode}"  class="btn btn-danger btn-my btn-sm j_pay_deposit">支付定金</a>
						            <a href="script:void(0)" data-orderType="${orderMainVar.orderType}" data-orderStatus="${orderMainVar.orderStatus}" data-orderCode="${orderMainVar.orderCode}" data-userId="${orderMainVar.customerId}" data-status="-1"class="btn btn-primary btn-my btn-sm j_Cancel">取消订单</a>
								</span>
					  	 	</s:if>
					  	 </c:if>
					 </c:forEach>
					
				</s:if>  
	            <s:if test="#orderMainVar.orderStatus == 23">
	            	<c:forEach var="orderMainState" items="${reserveList}" varStatus="orderMainStates">
					  	<c:if test="${orderMainState.orederCode == orderMainVar.orderCode}"> 
					  		<s:if test="${orderMainState.finalpayStartTime == 'no'}"> 
					  			<s:if test="${orderMainState.finalpayEndTime == 'yes'}">
									<span class="pull-right" name="finalPaymentSpan" text="${orderMainVar.orderCode}" >
							            <a href="script:void(0)" data-orderCode="${orderMainVar.orderCode}"  class="btn btn-danger btn-my btn-sm j_pay_retainage">支付尾款</a>
							        </span>
						        </s:if>
						        <s:else>
						        	<span class="pull-right" name="finalPaymentSpan" text="${orderMainVar.orderCode}" style="display:none;">
							            <a href="script:void(0)" data-orderCode="${orderMainVar.orderCode}"  class="btn btn-danger btn-my btn-sm j_pay_retainage">支付尾款</a>
							        </span>
						        </s:else>
					  	 	</s:if>
					  	 	<s:else>
					  	 		<span class="pull-right" name="finalPaymentSpan" text="${orderMainVar.orderCode}" style="display:none;">
						            <a href="script:void(0)" data-orderCode="${orderMainVar.orderCode}"  class="btn btn-danger btn-my btn-sm j_pay_retainage">支付尾款</a>
						        </span>
					  	 	</s:else>
					  	 </c:if>
					 </c:forEach>
		             
				</s:if>  
	            <s:if test="#orderMainVar.orderStatus == 2 || #orderMainVar.orderStatus == 25|| #orderMainVar.orderStatus == 20|| #orderMainVar.orderStatus == 21|| #orderMainVar.orderStatus == 22 || #orderMainVar.orderStatus == 3 || #orderMainVar.orderStatus == 4 || #orderMainVar.orderStatus == 15">
					<c:forEach var="orderMainState" items="${reserveList}" varStatus="orderMainStates">
					  	<c:if test="${orderMainState.orederCode == orderMainVar.orderCode}"> 
					  	<s:if test="${orderMainState.judgeCompensate == 'yes'}"> 
					  		<s:if test="${orderMainState.deliveryEndTime == 'no'}"> 
					  			<span class="pull-right">
						            <a class="btn btn-primary btn-my btn-sm"  href="applyWapPrepare.action?searchKeyword.keyword=${orderMainVar.orderCode}&checkOverDate=false">申请赔付</a>
								</span>
					  	 	</s:if>
					  	 </s:if>	
					  	 </c:if>
					 </c:forEach>
				</s:if>  
				
				<s:if test="#orderMainVar.orderStatus == -1">
					<span class="pull-right">
					<a href="javascript:void(0);" class="btn btn-primary btn-my btn-sm j_delete"  data-orderCode="${orderMainVar.orderCode}"  data-userId="${orderMainVar.customerId}">删除</a>
					</span>
				</s:if>
	           <s:if test="#orderMainVar.orderStatus == 12">
	             <span class="pull-right">
	                <a href="javascript:void(0);" class="btn btn-danger btn-xs">
	                	联系客服</a>
	             </span>
	           </s:if>
	          <s:if test="#orderMainVar.orderStatus == 5">
	            <span class="pull-right">
	            <a href="javascript:void(0);"  data-orderCode="${orderMainVar.orderCode}" data-status="6"  class="btn btn-my btn-success btn-sm j_sureTosureOrder">确认收货</a>
	            </span> 
	          </s:if>
	    <!--        <s:if test="#orderMainVar.orderStatus == 3 || #orderMainVar.orderStatus == 4 || #orderMainVar.orderStatus == 15">
	          	<c:forEach var="orderMainState" items="${reserveList}" varStatus="orderMainStates">
					  	<c:if test="${orderMainState.orederCode == orderMainVar.orderCode}"> 
					  		<s:if test="${orderMainState.deliveryEndTime == 'no'}"> 
					  			<span class="pull-right">
						            <a class="btn btn-primary btn-my btn-sm"  href="applyWapPrepare.action?searchKeyword.keyword=${orderMainVar.orderCode}&checkOverDate=false">申请赔付</a>
								</span>
					  	 	</s:if>
					  	</c:if>
				</c:forEach>
	         </s:if> -->
	         <s:if test="#orderMainVar.orderStatus == 6">
         		<c:forEach var="orderMainState" items="${reserveList}" varStatus="orderMainStates">
				  	<c:if test="${orderMainState.orederCode == orderMainVar.orderCode}">
				  		<s:if test="${orderMainState.judgeSalesReturn == 'yes'}"> 
				  			<s:if test="${orderMainState.finishDate == 'yes'}"> 
								<span class="pull-right">
						          <a class="btn btn-primary btn-my btn-sm"  href="applyWapPrepare.action?searchKeyword.keyword=${orderMainVar.orderCode}&checkOverDate=false">退换货</a>
						      	</span>
					  	 	</s:if>
				  		</s:if>
				  	 </c:if>
				 </c:forEach>
	         </s:if>
			</s:if>
			
			<s:else>
	            <s:if test="#orderMainVar.orderStatus == -1">
					<span class="pull-right">
					<a href="javascript:void(0);" data-orderId="rows${orderMainVar.orderId}"  class="btn btn-warning btn-my btn-sm j_byAgain">再购买</a>
					<a href="javascript:void(0);" class="btn btn-primary btn-my btn-sm j_delete"  data-orderCode="${orderMainVar.orderCode}"  data-userId="${orderMainVar.customerId}">删除</a>
					</span>
				</s:if>
	           <s:if test="#orderMainVar.orderStatus == 12">
	             <span class="pull-right">
	                <a href="javascript:void(0);" class="btn btn-danger btn-xs">
	                	联系客服</a>
	                </span>
	           </s:if>
	           <s:if test="#orderMainVar.orderStatus == 1">
	            <span class="pull-right">
	            <a href="script:void(0)" data-orderCode="${orderMainVar.orderCode}"  class="btn btn-danger btn-my btn-sm j_pay">立即付款</a>
	            <a href="script:void(0)" data-orderType="${orderMainVar.orderType}" data-orderStatus="${orderMainVar.orderStatus}" data-orderCode="${orderMainVar.orderCode}" data-userId="${orderMainVar.customerId}" data-status="-1"class="btn btn-primary btn-my btn-sm j_Cancel">取消订单</a> 
	            </span>
	           </s:if> 
	           <s:if test="#orderMainVar.orderStatus == 2 || #orderMainVar.orderStatus == 25|| #orderMainVar.orderStatus == 20|| #orderMainVar.orderStatus == 21|| #orderMainVar.orderStatus == 22">
	            <span class="pull-right">
	            <a href="javascript:void(0);" data-orderId="rows${orderMainVar.orderId}"  class="btn btn-warning btn-my btn-sm j_byAgain">再购买</a>
	            <a href="script:void(0)" data-orderType="${orderMainVar.orderType}" data-orderStatus="${orderMainVar.orderStatus}" data-orderCode="${orderMainVar.orderCode}" data-userId="${orderMainVar.customerId}" data-status="-1"class="btn btn-primary btn-my btn-sm j_Cancel">取消订单</a>
	            </span>
	           </s:if>
	          <s:if test="#orderMainVar.orderStatus == 5">
	            <span class="pull-right">
	            <a href="javascript:void(0);" data-orderId="rows${orderMainVar.orderId}"   class="btn btn-warning btn-my btn-sm j_byAgain">再购买</a>
	            <a href="javascript:void(0);"  data-orderCode="${orderMainVar.orderCode}" data-status="6"  class="btn btn-my btn-success btn-sm j_sureTosureOrder">确认收货</a>
	            </span> 
	          </s:if>
	          <s:if test="#orderMainVar.orderStatus == 3 || #orderMainVar.orderStatus == 4">
	           <span class="pull-right">
	           <a href="javascript:void(0);" data-orderId="rows${orderMainVar.orderId}"  class="btn btn-warning btn-my btn-sm j_byAgain">再购买</a>
	           </span>
	         </s:if>
	         <s:if test="#orderMainVar.orderStatus == 6">
	          <span class="pull-right"><a href="javascript:void(0);" data-orderId="rows${orderMainVar.orderId}"  class="btn btn-warning btn-my btn-sm j_byAgain">再购买</a>
	          <a class="btn btn-primary btn-my btn-sm"  href="applyWapPrepare.action?searchKeyword.keyword=${orderMainVar.orderCode}&checkOverDate=false">退换货</a>
	          </span>
	         </s:if>
         </s:else>
        </div>
        <s:if test="#orderMainVar.orderType==7">
            <s:if test="#orderMainVar.orderStatus == 1">
				<c:forEach var="orderMainTime" items="${reserveList}" varStatus="orderMainTimes">
				  	<c:if test="${orderMainTime.orederCode == orderMainVar.orderCode}"> 
				  		<c:set var="surplus" value="${orderMainTime.time}" scope="request"  />
						<div class="order-sell text-danger"><font name="countDown" title="${orderMainVar.orderCode}" ><%=request.getAttribute("surplus")%></font>
				  	 </c:if>
				 </c:forEach>
			</s:if>  
            <s:if test="#orderMainVar.orderStatus == 23">
	             <c:forEach var="orderMainTime" items="${reserveList}" varStatus="orderMainTimes">
				  	<c:if test="${orderMainTime.orederCode == orderMainVar.orderCode}"> 
				  		<c:set var="surplus" value="${orderMainTime.time}" scope="request"  />
						<div class="order-sell text-danger"><font name="countDown" title="${orderMainVar.orderCode}" ><%=request.getAttribute("surplus")%></font>
				  	 </c:if>
				 </c:forEach>
			</s:if>  
		</s:if>	
    </section>
    </s:iterator>
    </div>
     <c:if test="${pagintion.totalpage>1}">
                     <div class="body-load text-center text-success" id="page" data-value="${pagintion.totalpage}"><i class="icon icon-spinner icon-xs"></i>加载中，请稍后...</div>
<%-- 	<a href="javascript:void(0)" id="page" data-value="${pagintion.totalpage}" class="j_topage ui-page-item ui-page-item-next">下一页</a> --%></c:if>
                    
                    <div style="display:none;"id="pageError" class="body-load text-center text-error"><i class="icon icon-frown-o icon-xs"></i>加载失败，请重试...</div>
    
    </s:form>
</div>
<!--内容区域-->

 
