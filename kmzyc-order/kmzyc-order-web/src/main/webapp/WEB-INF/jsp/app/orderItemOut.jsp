<%@page contentType="text/html;charset=UTF-8"%>
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
			
			//返回
		    $('#return').click(function(){
		    	history.go(-1);
		    });
		})
		</script>
	</head>
	<body>
		<s:set name="parent_name" value="'返利网订单管理'" scope="request"/>
		<s:set name="name" value="'返利网订单详情'" scope="request"/>
	<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<div style="margin:10px">
			<button id="return" class="backBtn"></button>&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		<div id="maind" style="width:90%;margin-left:5%;text-algin:center;">
		
			<ul id="tabs">
				<li class="visit"><b>基本信息</b></li>
			</ul>		
	<div id="content">
				<div class="ct">
				<p><h4>订单号：<s:property value="order.orderCode"/>&nbsp;&nbsp;&nbsp;&nbsp;状态：<s:property value="order.orderStatusStr"/></h4></p>
				<p><h4>收货人信息</h4>
				收货人：<s:property value="order.consigneeName"/><br/>
				地址：<s:property value="order.consigneeAddr"/><br/>
				固定电话：<s:property value="order.consigneeTel"/><br/>
				手机号码：<s:property value="order.consigneeMobile"/><br/>
				<%--删除邮件业务  电子邮件：<s:property value="order.email"/></p> --%>
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
				   		 <th >商品SKU</th>
				   		 <th >商品名称</th>
				   		 <th >规格</th>
				   		 <th >套餐</th>
				   		 <th >供应商</th>
				   		 <th >康美中药城价</th>
				   		 <th >佣金比例</th>
				   		 <th >应结金额</th>
				   		 <th >PV值</th>
				   		 <th >收益</th>
				   		 <th >数量</th>
				   		 <th >仓库编号</th>
				   		 <th >批次号</th>
				   		 <th >实收小计</th>
				   		 <th >药品/器械</th>
				   		 <th >返利网佣金比例</th>
				   		 <th >返利网佣金金额</th>
					</tr>
					
					<s:iterator id="orderItem" value="items">
					<tr>
				   	 	 <td><a href="<s:property value='cmsPagePath'/><s:property value='#orderItem.productSkuId'/>.shtml" class="fn-blue" target="_blank"><s:property value="#orderItem.commoditySku"/></a></td>
				   	 	 <td><s:property value="#orderItem.commodityTitle"/></td>
				   	 	 <td><s:property value="#orderItem.commoditySkuDescription"/></td>
				   	 	 <td><s:property value="#orderItem.suitId"/></td>
				   	 	 <td><s:property value="#orderItem.supplier"/></td>
				   	 	 <td><s:number name="#orderItem.commodityUnitPrice"/></td>
				   	 	 <td><s:number name="#orderItem.commissionRate*100"/>%</td>
				   	 	 <td><s:number name="#orderItem.settlementLoan"/></td>
				   	 	 <td><s:number name="#orderItem.commodityPv*#orderItem.commodityNumber"/></td>
				   	 	 <td><s:number name="#orderItem.costIncomeMoney*#orderItem.commodityNumber"/></td>
				   	 	 <td><s:property value="#orderItem.commodityNumber"/></td>
				   	 	 <td><s:property value="%{formatDouble(#orderItem.warehouseId)}"/></td>
				   	 	 <td><s:property value="#orderItem.commodityBatchNumber"/></td>
				   	 	 <td><s:number name="#orderItem.commodityUnitIncoming*#orderItem.commodityNumber"/></td>
				   	 	 <td><s:property value="#orderItem.extAttrType==1?'是':'否'"/></td>
				   		 <td>
				   		<s:number name="#orderItem.outRebateRate"/>%
						 </td>
						 <td>
				   		<s:number name="#orderItem.outRebateMoney"/>
						 </td>
					</tr>
					</s:iterator>
				</table>
				<div style="float:right;margin-right:10%">
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
				  <%--   <s:if test="0!=reservepay">
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
				</div>			
				</div>
			</div>
		</div>
		<div id="question" style="display:none"></div>
	</body>
</html>