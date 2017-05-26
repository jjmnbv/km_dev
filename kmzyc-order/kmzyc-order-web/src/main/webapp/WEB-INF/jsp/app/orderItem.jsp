<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>订单详情</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="/etc/css/orderTab.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<link rel="stylesheet" href="/etc/css/demo.css" type="text/css">
		<link rel="stylesheet" href="/etc/css/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="/etc/js/urchin.js"></script>
		<script type="text/javascript" src="/etc/js/order_tab.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript">
		$(function(){

			if(window.location.href.indexOf('viewOnly')>0){
				$('#return').hide();
			}
			//返回
		    $('#return').click(function(){
		    	history.go(-1);
		    });
			
			// 支付测试
			$(".pay").click(function() {
				var orderCode = '<s:property value="order.orderCode"/>';
		        $.blockUI({ message: ($('#question').empty().load("/app/orderPayshowAction.action",{orderCode:orderCode,currid:Math.random()}))
		      	    , css: { top:'20%' ,width: '430px' }
		        });
		    });
			  
			 // 后台退换货申请
		     $('.alter').click(function(evt) {
				var orderCode = '<s:property value="order.orderCode"/>';
				var itemId = $(this).attr("name");
		   	  	if(confirm("建立退换货申请将使对应订单确认收货,是否继续操作？")){
		   	    	location="/app/orderBackdownshowAction.action?orderCode="+orderCode+"&itemId="+itemId;
		   	  	};
			});
		 	// 退换货申请
			$(".bd").click(function() {
				var orderCode = '<s:property value="order.orderCode"/>';
		        location.href="/app/orderBackdownshowBackDownAction.action?returnPage=1&orderCode="+orderCode+"&currid="+Math.random();
		    });
			
			// 退换货测试
			$(".rtest").click(function() {
				var orderItemId = $(this).attr("data-value");
				var orderCode = '<s:property value="order.orderCode"/>';
				$.post(
					'orderBackdowncomputeAction.action',
					{orderCode:orderCode,itemId:orderItemId,alterNum:1},
					function(data){
						alert(data);
					},
					'json'
				);
		    });
			
			// 评价
			$(".come").click(function() {
				location.href='/app/fillOutOrderAssess.action?orderCode=<s:property value="order.orderCode"/>&customerAccount=<s:property value="order.customerAccount"/>&orderId=<s:property value="order.orderId"/>';
		    });
			
		    // 操作
		    function operate(msg,action,data) {
		  	  if(confirm(msg)){
		  		  $.post(
			            	action,
			            	data,
			           		function(result){
					            alert(result);
		  			            if(result.indexOf("失败")>0){
		  			            	history.go(0);
		  			            }else{
		  	  			            var url = '/app/orderlistByMapAction.action';
		  	  			            <s:if test='map["byStatus"]'>
		  	  			            	url=url+'?&map["byStatus"]=<s:property value='map["byStatus"]'/>&map["status"]=<s:property value="order.orderStatus"/>';
		  	  			            </s:if>
		  	  			            location.href=url;
		  			            }
			     			}
			          );
		  	  };
		    }
			  
		    // 撤单
		    $('.cd').click(function(evt) {
			      	var orderCode = '<s:property value="order.orderCode"/>';
			      	operate(
			      			'是否确定将此订单撤销?',
			      			'/app/orderPaychangeStausAction.action',
			      			{orderCode:orderCode,status:-1}
			      	);
		    });
		    
		    // 出库
		    $('.Outbound').click(function(evt) {
			      	var orderCode = '<s:property value="order.orderCode"/>';
			      	operate(
			      			'是否确定此订单已出库?',
			      			'/app/orderPaychangeStausAction.action',
			      			{orderCode:orderCode,status:4}
			      	);
			      	/*
		          $.blockUI({ message: ($('#question').empty().load("/app/orderPayshowStockoutAction.action",{orderCode:orderCode,status:4,currid:Math.random()}))
		      	 	, css: { top:'30%', left:'35%' ,width: '310px'}
		          });
			      	*/
		    });
		    
		    // 配送
		    $('.ship').click(function(evt) {
			      	var orderCode = '<s:property value="order.orderCode"/>';
			      	operate(
			      			'是否确定此订单已配送?',
			      			'/app/orderPaychangeStausAction.action',
			      			{orderCode:orderCode,status:5}
			      	);
			      	/*
		          $.blockUI({ message: ($('#question').empty().load("/app/orderPayshowStockoutAction.action",{orderCode:orderCode,status:5,currid:Math.random()}))
		      	 	, css: { top:'30%', left:'35%' ,width: '310px'}
		          });
			      	*/
		    });
		    
		    // 完成订单
		    $('.done').click(function(evt) {
			      	var orderCode = '<s:property value="order.orderCode"/>';
			      	operate(
			      			'是否确定此订单已完成?',
			      			'/app/orderPaychangeStausAction.action',
			      			{orderCode:orderCode,status:6}
			      	);
		    });
		    
		    // 补单
		    $('.additional').click(function(evt) {
			      	var orderCode = '<s:property value="order.orderCode"/>';
			      	operate(
			      			'是否确定补单?',
			      			'/app/orderPayadditionalAction.action',
			      			{orderCode:orderCode}
			      	);
		    });
		    
		    // 处理标记 
		    $('.handle').click(function(evt) {
			      	var orderCode = '<s:property value="order.orderCode"/>';
			      	operate(
			      			'是否确定标记此订单为已处理?',
			      			'/app/orderPayhandleAction.action',
			      			{orderCode:orderCode,state:2}
			      	);
		    });
		    // 取消标记 
		    $('.unhandle').click(function(evt) {
			      	var orderCode = '<s:property value="order.orderCode"/>';
			      	operate(
			      			'是否确定取消标记此订单?',
			      			'/app/orderPayhandleAction.action',
			      			{orderCode:orderCode,state:1}
			      	);
		    });
		    // 修改订单信息
		    $('.chinfo').click(function(evt) {
		    	var orderCode = '<s:property value="order.orderCode"/>';
				location = "/app/orderItemtoChangeInfoAction.action?orderCode="+orderCode;
		    });

		    // 修改运费
		    $('.updateFee').click(function(evt) {
		    	var orderCode = '<s:property value="order.orderCode"/>';
				location = "/app/orderItemtoChangeFeeAction.action?orderCode="+orderCode;
		    });
		    
		    // 通过
		    $('.pass').click(function(evt) {
			      	var orderCode = '<s:property value="order.orderCode"/>';
			      	operate(
			      			'是否确定将此订单审核通过?',
			      			'/app/orderPaychangeStausAction.action',
			      			{orderCode:orderCode,status:25}
			      	);
		    });
		    // 驳回 
		    $('.veto').click(function(evt) {
			      	var orderCode = '<s:property value="order.orderCode"/>';
			      	operate(
			      			'是否确定将此订单驳回?',
			      			'/app/orderPaychangeStausAction.action',
			      			{orderCode:orderCode,status:-2}
			      	);
		    });
		    
			
		});
		</script>
		<style type="text/css">
			.tdv{
				vertical-align:middle;
			}
			.hr1{
				height:1px;
				border:none;
				border-top:1px solid #C8D9CF;
				text-align: center;
				color:#606060;
			}
		</style>
	</head>
	<body>
	<s:set name="parent_name" value="'业务操作'" scope="request"/>
	<s:set name="name" value="'订单管理'" scope="request"/>
	<s:set name="son_name" value="'订单详情'" scope="request"/>
	<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<div style="margin:10px">
			<button id="return" class="backBtn"></button>&nbsp;&nbsp;&nbsp;&nbsp;
				<%--
				<button class="cd" <s:if test="order.orderStatus!=1&&order.orderStatus!=2">disabled="disabled"</s:if>>撤单</button>&nbsp;&nbsp;&nbsp;&nbsp;
				
				<button class="pay" <s:if test="order.orderStatus!=1">disabled="disabled"</s:if>>支付测试</button>&nbsp;&nbsp;&nbsp;&nbsp;
				--%>
				<button class="Outbound" style="height:30px;" <s:if test="order.orderStatus!=3" >disabled="disabled"</s:if>>确认出库</button>&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="ship" style="height:30px;" <s:if test="order.orderStatus!=4">disabled="disabled"</s:if>>确认配送</button>&nbsp;&nbsp;&nbsp;&nbsp;
			 	<s:if test="order.handleState!=2">
				<button class="handle" style="height:30px;">处理标记</button>&nbsp;&nbsp;&nbsp;&nbsp;
				</s:if>
				<s:else>
				<s:if test="or
				-+der.handleAccount==loginAccount">\
				<button class="unhandle" style="height:30px;">取消标记</button>&nbsp;&nbsp;&nbsp;&nbsp;
				</s:if>
				</s:else>			
				<s:if test="isAdditional">
				<button class="btn-custom additional">
				<s:if test="order.orderStatus==1">付款</s:if><s:if test="order.orderType==7 &&order.orderStatus==1">支付定金</s:if><s:if test="order.orderType==7 &&order.orderStatus==23">支付尾款</s:if><s:if test="order.orderStatus==-1">退款</s:if><s:if test="order.orderStatus == 2">锁库存</s:if>补单
				</button>&nbsp;&nbsp;&nbsp;&nbsp;
				</s:if>
				<s:if test="order.orderStatus==1||order.orderStatus==2">
				<button class="btn-custom chinfo">修改订单信息</button>&nbsp;&nbsp;&nbsp;&nbsp;
				</s:if>
				
				<s:if test="order.orderStatus==1">
				<button class="btn-custom updateFee">修改运费</button>&nbsp;&nbsp;&nbsp;&nbsp;
				</s:if>
				<%--
				<s:if test="isExt">
				<button class="pass">审核通过</button>&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="veto">驳回订单</button>&nbsp;&nbsp;&nbsp;&nbsp;
				</s:if>
				<button class="done" <s:if test="order.orderStatus!=5">disabled="disabled"</s:if>>确认完成</button>&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="bd" <s:if test="(order.orderStatus!=6&&order.orderStatus!=7)||order.isReturning!=0">disabled="disabled"</s:if>>退换货</button>
			    <button class="come" <s:if test="order.orderStatus!=6">disabled="disabled"</s:if>>评价</button>
				 --%>
		</div>
		<div id="maind" style="width:90%;margin-left:5%;text-algin:center;">
		
			<ul id="tabs">
				<li class="visit"><b>基本信息</b></li>
				<li><b>操作流水</b></li>
				<li><b>支付流水</b></li>
				<li><b>发票信息</b></li>
				<li><b>优惠信息</b></li>
				<li><b>物流信息</b></li>
				<%--
				<li><b>路由信息</b></li>
				<li><b>拆分详情</b></li>
				 --%>
			</ul>		
	<div id="content">
				<div class="ct">
				<p><h4>订单号：<s:property value="order.orderCode"/>&nbsp;&nbsp;&nbsp;&nbsp;状态：<s:property value="order.orderStatusStr"/>&nbsp;&nbsp;&nbsp;&nbsp;下单账号:<s:property value="order.customerAccount"/></h4></p>
				<p><h4>收货人信息</h4>
				收货人：<s:property value="order.consigneeName"/><br/>
				地址：<s:property value="order.consigneeAddr"/><br/>
				固定电话：<s:property value="order.consigneeTel"/><br/>
				手机号码：<s:property value="order.consigneeMobile"/><br/>
			<%--删除邮件业务 	电子邮件：<s:property value="order.email"/></p> --%>
				<s:if test="order.orderType==7">
					<h4 style="display : inline;">尾款支付通知手机：</h4><s:property value="order.informPayTel"/><br/>
				</s:if>
				<p><h4>支付及配送方式</h4>
				支付方式：<s:property value="order.payMethodStr"/><br/>
				配送方式：<s:property value="order.deliveryDateTypeStr"/><br/>
				运费：<s:property value="order.fare"/></p>
				<p><h4>发票信息</h4>
				<s:if test="order.invoiceInfoType!=null||order.invoiceInfoTitle!=null||order.invoiceInfoContent!=null">
					类型：普通发票<br/>
					抬头：<s:property value="order.invoiceInfoTitle"/>
				</s:if>
				<s:else>
				 买家未要求开发票
				</s:else>
			    <p><h4>备注信息</h4>
				订单备注：<s:property value="order.orderDescription"/><br/>
				后台备注：<s:property value="order.orderOperationRemark"/><br/>
				</p>
				<br/>
				<table class="list_table" width="100%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
					<tr>
				   		 <th width="9%">商品SKU</th>
				   		 <th width="16%">商品名称</th>
				   		 <th width="5%">规格</th>
				   		 <th width="4%">套餐</th>
				   		 <th width="5%">供应商</th>
				   		 <s:if test="order.orderType!=7">
				   		 	<th width="8%">康美中药城价</th>
				   		 </s:if>
				   		 <s:else>
				   		 	<th width="12%">预售价</th>
				   		 </s:else>
				   		 <th width="6%">佣金比例</th>
				   		 <th width="6%">应结金额</th>
				   		 <th width="4%">PV值</th>
				   		<!--  <th width="4%">收益</th> -->
				   		 <th width="3%">数量</th>
				   		 <th width="6%">仓库编号</th>
				   		 <th width="5%">批次号</th>
				   		 <th width="6%">实收小计</th>
				   		 <s:if test="order.orderType!=7">
				   		 	<th width="5%">药品/器械</th>
				   		 </s:if>
				   		 <th width="8%">操作</th>
					</tr>
					
					<s:iterator id="orderItem" value="items">
					<tr>
				   	 	 <td class="tdv"><a href="<s:property value='cmsPagePath'/><s:property value='#orderItem.productSkuId'/>.shtml" class="fn-blue" target="_blank"><s:property value="#orderItem.commoditySku"/></a></td>
				   	 	 <td class="tdv"><s:property value="#orderItem.commodityTitle"/></td>
				   	 	 <td class="tdv"><s:property value="#orderItem.commoditySkuDescription"/></td>
				   	 	 <td class="tdv"><s:property value="#orderItem.suitId"/></td>
				   	 	 <td class="tdv"><s:property value="#orderItem.supplier"/></td>
				   	 	 <td class="tdv">
				   	 	 	<s:if test="order.orderType==7">
				   	 	 		<s:property value="order.amountPayable/#orderItem.commodityNumber"/>
					   	 	 	<br/><hr class="hr1">
					   	 	 		定金：<s:property value="order.depositSum/#orderItem.commodityNumber"/><s:if test="paidDeposit>0&&paidDeposit!=null">  (已支付)</s:if><s:else>  (未支付)</s:else><br/>
					   	 	 	<br/><hr class="hr1">
					   	 	 		尾款：<s:property value="finalPayment/#orderItem.commodityNumber"/><s:if test="noFinalPayment>0">  (已支付)</s:if><s:else>  (未支付)</s:else>
				   	 	 	</s:if>
				   	 	 	<s:else>
				   	 	 		<s:number name="#orderItem.commodityUnitPrice"/>
				   	 	 	</s:else>
				   	 	 </td>
				   	 	 <td class="tdv"><s:number name="#orderItem.commissionRate*100"/>%</td>
				   	 	 <td class="tdv">
				   	 	 	<s:number name="#orderItem.settlementLoan"/>
				   	 	 </td>
				   	 	 <td class="tdv"><s:number name="#orderItem.commodityPv*#orderItem.commodityNumber"/></td>
				   	 	<%--  <td class="tdv"><s:number name="#orderItem.costIncomeMoney*#orderItem.commodityNumber"/></td> --%>
				   	 	 <td class="tdv"><s:property value="#orderItem.commodityNumber"/></td>
				   	 	 <td class="tdv"><s:property value="%{formatDouble(#orderItem.warehouseId)}"/></td>
				   	 	 <td class="tdv"><s:property value="#orderItem.commodityBatchNumber"/></td>
				   	 	 <td class="tdv"><s:number name="#orderItem.commodityUnitIncoming*#orderItem.commodityNumber>0?#orderItem.commodityUnitIncoming*#orderItem.commodityNumber:0"/></td>
				   	 	 <s:if test="order.orderType!=7">
				   	 	 	<td class="tdv"><s:property value="#orderItem.extAttrType==1?'是':'否'"/></td>
				   	 	 </s:if>
				   		 <td class="tdv">
				   		 <%-- s:if test="#order.isnotparentorder&&#order.orderStatus&ge;3&&#orderItem.isReturning==0"--%>
					   		 <s:if test="order.orderStatus>=3&&order.orderStatus!=4&&order.orderStatus!=16&&order.orderStatus!=20&&order.orderStatus!=21&&order.orderStatus!=22&&order.orderStatus!=23&&#orderItem.isReturning==0">
					 		 	<a href="javascript:void(0);" class="alter" name="<s:property value='#orderItem.orderItemId'/>">退换货申请</a>
							 </s:if>
						 	
						 </td>
				   	 	 <%-- 
				   	 	 <td><s:property value="%{formatdouble(#orderItem.warehouseId)}"/><button data-value="<s:property value='#orderItem.orderItemId'/>" class="rtest">退换货测试</button></td>
						  --%>
					</tr>
					</s:iterator>
				</table>
				<div style="float:right;margin-right:10%">
					<s:if test="order.orderType!=7">
						<p>
						商品总额：<s:property value="order.commoditySum"/><br/>
						+运费：<s:property value="order.fare"/><br/>
						<s:if test="0!=plusDiscount">
						+加价购: <s:property value="plusDiscount"/><br/>
					    </s:if>
						<s:if test="0!=fullDdiscount">
						-满减: <s:property value="fullDdiscount"/><br/>
					    </s:if>
					    <s:if test="0!=order.orderDiscount">
						-打折: <s:property value="order.orderDiscount"/><br/>
					    </s:if>
						——————————<br/>
						=应付金额：<s:property value="actualpay"/><br/>
						-已付金额：<s:property value="orderpay"/><br/>
						<s:if test="0!=couponpay">
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|—优惠券: <s:property value="couponpay"/><br/>
					    </s:if>
					   <%--  <s:if test="0!=reservepay">
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|—预备金： <s:property value="reservepay"/><br/>
					    </s:if> --%>
					    <s:if test="0!=balancepay">
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|—余额： <s:property value="balancepay"/><br/>
					    </s:if>
					    <s:if test="0!=bankpay">
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|—网银/信用卡：<s:property value="bankpay"/><br/>
					    </s:if>
					    <s:if test="0!=platformpay">
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|—在线：<s:property value="platformpay"/><br/>
					    </s:if>
					    <s:if test="0!=onlinepay">
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|—在线：<s:property value="onlinepay"/><br/>
						</s:if>
						<s:if test="null==order.parentOrderCode">
						——————————<br/>
						=未付金额：<s:property value="notpay<0?0.00:notpay"/>
						</s:if>
						</p>
					</s:if>
					<s:else>
							商品总额：<s:property value="order.amountPayable"/><br/>
								+运费：0.00<br/>
							——————————<br/>
							=应付金额：<s:property value="order.amountPayable"/><br/>
							-已付定金：<s:if test="paidDeposit>0&&paidDeposit!=null"><fmt:formatNumber value="${order.depositSum}" pattern="0.00"/></s:if><s:else>0.00</s:else><br/>
							-已付尾款：<s:if test="noFinalPayment>0&&noFinalPayment!=null"><fmt:formatNumber value="${noFinalPayment}" pattern="0.00"/></s:if><s:else>0.00</s:else><br/>
							——————————<br/>
								=未付金额：
								<fmt:formatNumber value="${order.amountPayable-(((paidDeposit>0&&paidDeposit!=null)&&order.depositSum>0)?order.depositSum:0.00)-(noFinalPayment==0||noFinalPayment==null?0.00:noFinalPayment)}" pattern="0.00"/>
					</s:else>	
				</div>		
				</div>
				<div class="ct" id="ct_cate">
				<br/>
				<table class="list_table" width="100%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
					<tr>
					   	<th>流水号</th>
					   	<th>时间</th>
					   	<th>操作人</th>
					   	<th>操作类型</th>
					   	<th>订单总金额</th>
					   	<th>订单状态</th>
					   	<th>操作信息</th>
					</tr>
					<s:iterator id="orderOperate" value="operates">
					<tr>
				   	 	 <td><s:property value="#orderOperate.statementId"/></td>
				   	 	 <td><s:date name="#orderOperate.nowOperateDate" format="yyyy-MM-dd HH:mm:ss" /></td>
				   	 	 <td><s:property value="#orderOperate.nowOperator"/></td>
				   	 	 <td><s:property value="#orderOperate.nowOperateTypeStr"/></td>
				   	 	 <td><s:property value="#orderOperate.nowOrderSum"/></td>
				   	 	 <td><s:property value="#orderOperate.nowOrderStatusStr"/></td>
				   	 	 <td><s:property value="#orderOperate.operateInfo"/></td>
					</tr>
					</s:iterator>
				</table>				
				</div>
				<div class="ct" id="ct_sku">
				<br/>
				<table class="list_table" width="100%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
					<tr>
					   	<th>流水号</th>
					   	<th>支付方式</th>
					   	<th>支付平台</th>
					   	<th>支付状态</th>
					   	<th>客户账号</th>
					   	<th>支付金额</th>
					   	<th>生成时间</th>
					   	<th>支付完成时间</th>
					   	<th>第三方支付流水号</th>
					   	<th>付/退款</th>
					   	<th>优惠券编号</th>
					   	<th>备注</th>
					</tr>
					<s:iterator id="orderPay" value="pays">
					<tr>
				   	 	 <td><s:property value="#orderPay.payStatementNo"/></td>
				   	 	 <td><s:property value="#orderPay.paymentWayStr"/></td>
				   	 	 <td><s:property value="#orderPay.platFormName"/></td>
				   	 	 <td><s:property value="#orderPay.stateStr"/></td>
				   	 	 <td><s:property value="#orderPay.account"/></td>
				   	 	 <td><s:property value="#orderPay.orderMoney"/></td>
				   	 	 <td><s:date name="#orderPay.createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
				   	 	 <td><s:date name="#orderPay.endDate" format="yyyy-MM-dd HH:mm:ss" /></td>
				   	 	 <td><s:property value="#orderPay.outsidePayStatementNo"/></td>
				   	 	 <td><s:property value="#orderPay.flagStr"/></td>
				   	 	 <!-- 
				   	 	 <td><s:property value="#orderPay.preferentialNo"/></td>
				   	 	  -->
				   	 	 <td>
				   	 	 	<s:if test="null!=#orderPay.preferentialNo">
				   	 	 		<s:text name="format.num.zero">
				   	 	 			<s:param value="#orderPay.preferentialNo"/>
				   	 			</s:text>
				   	 		</s:if>
				   	 	 </td>
				   	 	 <td><s:property value="#orderPay.payInfo"/></td>
					</tr>
					</s:iterator>
				</table>				
				</div>
				<div class="ct" id="ct_dyna" >
				<p>发票类型：<s:property value="invoice.createTypeStr"/>&nbsp;&nbsp;&nbsp;&nbsp;流水号：<s:property value="invoice.invoiceId"/></p>
				<table class="list_table" width="100%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
					<tr>
					   	<th>发票明细号</th>
					   	<th>发票流水号</th>
					   	<th>开票项目代码</th>
					   	<th>开票项目名称</th>
					   	<th>单价</th>
					   	<th>数量</th>
					   	<th>折扣额</th>
					   	<th>行金额</th>
					   	<th>附注</th>
					   	<th>是否扣除</th>
					</tr>
					<s:iterator id="invoiceItem" value="invoiceItems">
					<tr>
				   	 	 <td><s:property value="#invoiceItem.invoiceItemId"/></td>
				   	 	 <td><s:property value="#invoiceItem.invoiceId"/></td>
				   	 	 <td><s:property value="#invoiceItem.invoiceItemCode"/></td>
				   	 	 <td><s:property value="#invoiceItem.invoiceItemName"/></td>
				   	 	 <td><s:property value="#invoiceItem.unitPrice"/></td>
				   	 	 <td><s:property value="#invoiceItem.invoiceItemNumber"/></td>
				   	 	 <td><s:property value="#invoiceItem.discountAmount"/></td>
				   	 	 <td><s:property value="#invoiceItem.lineSum"/></td>
				   	 	 <td><s:property value="#invoiceItem.note"/></td>
				   	 	 <td><s:property value="#invoiceItem.deductionFlag"/></td>
					</tr>
					</s:iterator>
				</table>				
				</div>
				<div class="ct">
				<br/>
				<table class="list_table" width="100%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
					<tr>
					   	<th>优惠明细号</th>
					   	<th>订单号</th>
					   	<th>订单明细号</th>
					   	<th>优惠类型</th>
					   	<th>优惠来源</th>
					   	<th>活动规则编号</th>
					   	<th>优惠金额/规则号</th>
					</tr>
					<s:iterator id="orderPreferential" value="preferentials">
					<tr>
				   	 	 <td><s:property value="#orderPreferential.order_preferential_id"/></td>
				   	 	 <td><s:property value="#orderPreferential.orderCode"/></td>
				   	 	 <td><s:property value="#orderPreferential.orderItemId"/></td>
				   	 	 <td><s:property value="#orderPreferential.orderPreferentialTypeStr"/></td>
				   	 	 <td><s:property value="#orderPreferential.orderPreferentialSource"/></td>
				   	 	 <td><s:property value="#orderPreferential.orderPreferentialCode"/></td>
				   	 	 <td><s:property value="#orderPreferential.orderPreferentialSum"/></td>
					</tr>
					</s:iterator>
				</table>				
				</div>
				<div class="ct">
				<br/>
				<table class="list_table" width="100%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
					<tr>
					   	<th>物流公司代码</th> 
					   	<th>物流单号</th> 
					</tr>
					<tr>
					   	<td style="vertical-align:middle"><s:property value="expressSubscription.logisticsCode"/></td> 
					   	<td style="vertical-align:middle"><s:property value="expressSubscription.logisticsNo"/></td> 
					</tr>
					<tr>
					   	<th colspan="2">物流信息</th> 
					</tr>
					<s:iterator id="track" value="expressSubscription.expressTrackList" >
					<tr>
						<td style="vertical-align:middle">时间：<s:date name="#track.trackDate" format="yyyy-MM-dd HH:mm:ss" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td  style="vertical-align:middle">物流信息：<s:property value="#track.trackMsg"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
					</s:iterator>	
				</table>				
				</div>
			</div>
		</div>
		<div id="question" style="display:none"></div>
	</body>
</html>