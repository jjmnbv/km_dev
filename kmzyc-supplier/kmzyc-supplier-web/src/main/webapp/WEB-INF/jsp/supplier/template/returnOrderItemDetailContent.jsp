<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!--  
<s:if test="#request.logisticsCompanyMap != null">
<div id="logisticsCompanyDiv" style="display: none;">
	<div class="ui-well ui-well-form">
		<div class="ui-form ui-form-info fn-mt20">
			<fieldset>
				<legend>请填写物流信息</legend>
				<div class="ui-form-item">
					<label class="ui-form-label" for=""><font color="red">*</font>物流公司：</label>
					<s:select list="#request.logisticsCompanyMap"
						headerKey="" headerValue="请选择物流" cssClass="ui-form-select logisticsCompany"/>
					<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_logisticsCompany_error" style="display: none;">
						<i class="ui-icon ui-icon-error"></i><span class="logisticsCompany_error_msg" style="font-size:12px;" ></span>
					</p>
				</div>
				<div class="ui-form-item">
					<label class="ui-form-label" for=""><font color="red">*</font>物流单号：</label>
					<input class="ui-input logisticsNo" type="text" style="width: 140px;"
						value="<s:property value='logisticsNo'/>"
						maxlength="16" />
					<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_logisticsNo_error" style="display: none;">
						<i class="ui-icon ui-icon-error"></i><span class="logisticsNo_error_msg" style="font-size:12px;"></span>
					</p>
				</div>
				<div class="ui-form-item">
					<input class='ui-button ui-button-success j_sure_editLogisticsInfo' type='button'  value='确定'>
				</div>
			</fieldset>
		</div>
	</div>
</div>
</s:if>
-->
<input type="hidden" id="isRefresh" value="N" />
<form action="showOrderItemDetails.action" method="post" id="orderItemFrm1" name="orderItemFrm1" namespace="supplier" >
	<input type="hidden" name="orderCode"  value="<s:property value="order.orderCode"/>" >
</form>
<form action="findAllReturnNotes.action" method="post" id="orderListFrm1" name="orderListFrm1" namespace="supplier" >
	<s:hidden name="nstatus"></s:hidden>
</form>
<!-- maliqun add end -->

<div style="margin:10px">
	<input type="hidden" id="orderCode"  value="<s:property value="order.orderCode"/>" >
	<input class='ui-button ui-button-success backBtn1' id="return1" type='button'  value='返回'>&nbsp;&nbsp;&nbsp;&nbsp;
		<%--
		<button class="cd" <s:if test="order.orderStatus!=1&&order.orderStatus!=2">disabled="disabled"</s:if>>撤单</button>&nbsp;&nbsp;&nbsp;&nbsp;
		
		<button class="pay" <s:if test="order.orderStatus!=1">disabled="disabled"</s:if>>支付测试</button>&nbsp;&nbsp;&nbsp;&nbsp;
		--%>
		<s:if test="order.orderStatus == 4">
			<input class='ui-button ui-button-success' id="sureDelivery" type='button'  value='确认配送'>&nbsp;&nbsp;&nbsp;&nbsp;
		</s:if>
		<!-- maliqun add begin-->
		<s:if test="orderStatusForMenuQuery == 4">
			<input class='ui-button ui-button-success' id="sureDelivery" type='button'  value='确认配送'>&nbsp;&nbsp;&nbsp;&nbsp;
		</s:if>
		<!-- maliqun add end -->
		<s:if test="order.orderStatus==1||order.orderStatus==2">
			<input class='ui-button ui-button-success' id="editOrderInfo" type='button'  value='修改订单信息'>&nbsp;&nbsp;&nbsp;&nbsp;
		</s:if>
		<s:if test="order.orderStatus == 1">
		<input class='ui-button ui-button-success' id="editOrderYouFei" type='button'  value='修改邮费'>&nbsp;&nbsp;&nbsp;&nbsp;
		</s:if>
		<%--
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
		<%--
		<li><b>路由信息</b></li>
		<li><b>拆分详情</b></li>
		 --%>
		 <!-- 20150331 maliqun add begin -->
		 <li><b>物流信息</b></li>
		  <!-- 20150331 maliqun add end -->
	</ul>		
<div id="content">
	<div class="ct1">
		<p><h4>订单号：<s:property value="order.orderCode"/>&nbsp;&nbsp;&nbsp;&nbsp;状态：<s:property value="order.orderStatusStr"/></h4></p>
		<p><h4>收货人信息</h4>
		收货人：<s:property value="order.consigneeName"/><br/>
		地址：<s:property value="order.consigneeAddr"/><br/>
		固定电话：<s:property value="order.consigneeTel"/><br/>
		手机号码：<s:property value="order.consigneeMobile"/><br/>
		电子邮件：<s:property value="order.email"/></p>
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
		<table class="ui-table table-bordered fn-mt10">
			<thead>
				<tr>
			   		<th width="9%">商品SKU</th>
			   		<th>商品名称</th>
			   		<th>规格</th>
			   		<th>供应商</th>
			   		<th width="9%">康美中药城价格</th>
			   		<th>佣金比例</th>
			   		<th>应结货款</th>
			   		<s:if test="#session.seesionKmb2bSupplierType == 4">
			   			<th>PV</th>
			   			<th>合作方收益</th>
			   		</s:if>
			   		<th>商品数量</th>
			   		<th>仓库编号</th>
			   		<th width="9%">批次号</th>
			   		<th>单品实收</th>
			   		<th>药品/医疗器械</th>
			   		<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator id="orderItem" value="items">
					<tr>
				   	 	 <td><a href="<s:property value='cmsPagePath'/><s:property value='#orderItem.productSkuId'/>.shtml" class="fn-blue" target="_blank"><s:property value="#orderItem.commoditySku"/></a></td>
				   	 	 <td><s:property value="#orderItem.commodityName"/></td>
				   	 	 <td><s:property value="#orderItem.commoditySkuDescription"/></td>
				   	 	 <td><s:property value="#orderItem.supplier"/></td>
				   	 	 <td><s:property value="#orderItem.commodityUnitPrice"/></td>
				   	 	 <td><s:property value="#orderItem.commissionRate"/></td>
				   	 	 <td><s:property value="#orderItem.settlementLoan"/></td>
				   	 	 <s:if test="#session.seesionKmb2bSupplierType == 4">
					   	 	 <td><s:property value="#orderItem.commodityPv"/></td>
					   	 	 <td><s:property value="#orderItem.costIncomeRatio"/></td>
					   	 </s:if>
				   	 	 <td><s:property value="#orderItem.commodityNumber"/></td>
				   	 	 <td><s:property value="#orderItem.warehouseId.intValue()"/></td>
				   	 	 <td><s:property value="#orderItem.commodityBatchNumber"/></td>
				   	 	 <td><s:property value="#orderItem.commodityUnitIncoming"/></td>
				   	 	 <td><s:property value="#orderItem.extAttrType==1?'是':'否'"/></td>
				   		 <td>
				   		 <%-- s:if test="#order.isnotparentorder&&#order.orderStatus&ge;3&&#orderItem.isReturning==0"
				   		 <s:if test="order.orderStatus>=3&&order.orderStatus!=16&&#orderItem.isReturning==0">
				 		 <a href="javascript:void(0);" class="alter" name="<s:property value='#orderItem.orderItemId'/>">退换货申请</a>
						 </s:if>
						 --%>
						 </td>
				   	 	 <%-- 
				   	 	 <td><s:property value="%{formatdouble(#orderItem.warehouseId)}"/><button data-value="<s:property value='#orderItem.orderItemId'/>" class="rtest">退换货测试</button></td>
						  --%>
					</tr>
				</s:iterator>
			</tbody>
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
			<!-- 
			-优惠：<s:property value="discount"/><br/>
			 -->
			——————————<br/>
			=应付金额：<s:property value="actualpay"/><br/>
			-已付金额：<s:property value="orderpay"/><br/>
			<s:if test="0!=couponpay">
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|—优惠券: <s:property value="couponpay"/><br/>
		    </s:if>
		    <s:if test="0!=reservepay">
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|—预备金： <s:property value="reservepay"/><br/>
		    </s:if>
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
			——————————<br/>
			=未付金额：<s:property value="notpay<0?0.00:notpay"/>
			</p>
		</div>			
	</div>
	<div class="ct1" id="ct_cate">
		<br/>
		<table class="ui-table table-bordered fn-mt10">
			<thead>
				<tr>
				   	<th width="7%">流水号</th>
				   	<th width="12%">时间</th>
				   	<th>操作人</th>
				   	<th>操作类型</th>
				   	<th>订单总金额</th>
				   	<th>订单状态</th>
				   	<th width="30%">操作信息</th>
				</tr>
			</thead>
			<tbody>
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
			</tbody>
		</table>				
	</div>
	<div class="ct1" id="ct_sku">
		<br/>
		<table class="ui-table table-bordered fn-mt10">
			<thead>
				<tr>
				   	<th width="10%">流水号</th>
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
				</tr>
			</thead>
			<tbody>
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
				   	 	 		<s:property value="#orderPay.preferentialNo.intValue()"/>
				   	 		</s:if>
				   	 	 </td>
					</tr>
				</s:iterator>
			</tbody>
		</table>				
	</div>
	<div class="ct1" id="ct_dyna" >
		<p>发票类型：<s:property value="invoice.createTypeStr"/>&nbsp;&nbsp;&nbsp;&nbsp;流水号：<s:property value="invoice.invoiceId"/></p>
		<table class="ui-table table-bordered fn-mt10">
			<thead>
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
			</thead>
			<tbody>
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
			</tbody>
		</table>				
	</div>
	<div class="ct1">
		<br/>
		<table class="ui-table table-bordered fn-mt10">
			<thead>
				<tr>
				   	<th>优惠明细号</th>
				   	<th>订单号</th>
				   	<th>订单明细号</th>
				   	<th>优惠类型</th>
				   	<th>优惠来源</th>
				   	<th>活动规则编号</th>
				   	<th>优惠金额/规则号</th>
				</tr>
			</thead>
			<tbody>
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
			</tbody>
		</table>				
	</div>
		<%--
		<div class="ct">
		<br/>
		<table class="list_table" width="100%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
			<tr>
			   	<th>流水号</th>
			   	<th>建立日期</th>
			   	<th>发货单号</th>
			   	<th>物流单号</th>
			   	<th>收件人</th>
			   	<th>配送方式</th>
			</tr>
			<s:iterator id="orderRouters" value="routers">
			<tr>
		   	 	 <td><s:property value="#orderRouters.commodityCode"/></td>
		   	 	 <td><s:property value="#orderRouters.commodityName"/></td>
		   	 	 <td><s:property value="#orderRouters.commodityUnitPrice"/></td>
		   	 	 <td><s:property value="#orderRouters.credits"/></td>
		   	 	 <td><s:property value="#orderRouters.commodityNumber"/></td>
		   	 	 <td><s:property value="#orderRouters.warehouseId"/></td>
			</tr>
			</s:iterator>
		</table>				
		</div>
		<div class="ct">
			<h1>拆分结构</h1>
			<h6>[ 订单号:  ]</h6>
			<div class="content_wrap">
				<div class="zTreeDemoBackground left">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
				<div class="right">
				
				</div>
			</div>	
		</div>
		 --%>
	<!-- maliqun add  20150331 新增物流追踪信息选项卡  begin-->	 
	<div class="ct1" id="expressInfo">
			<br/>
			<p><strong>订单号 :</strong><s:property value="order.orderCode"/></p>
			<p><strong>订单状态：</strong>
			
			<s:if test="order.orderStatus == -1">已取消</s:if>
			<s:if test="order.orderStatus == 1">未付款</s:if>
			<s:if test="order.orderStatus == 2">已付款</s:if>
			<s:if test="order.orderStatus == 3 || order.orderStatus == 4|| order.orderStatus == 15|| order.orderStatus == 18">配货中</s:if>								
			<s:if test="order.orderStatus == 5">已发货</s:if> 
			<s:elseif test="order.orderStatus == 6">已完成</s:elseif> 
			<s:if test="order.orderStatus == 12">送货失败</s:if>
			<s:if test="order.orderStatus == 16">已拆分</s:if>			
			</p>
			<p>
					 <s:if test="#request.expressPathInfo.data!=null">					 
					     <strong>最新进度:</strong> <span>[<s:property value="#request.expressPathInfo.data[0].ftime"/>]
					     </span>
						 <s:property value="#request.expressPathInfo.data[0].context" /> 
				 	</s:if>
					 <s:elseif test="#request.orderSysOpFlowList!=null">
			            <strong>最新进度:</strong> <span>[<s:date name="#request.orderSysOpFlowList[0].nowOperateDate" format="yyyy-MM-dd HH:mm:ss"/>]
					    </span>
					 <s:property value="#request.orderSysOpFlowList[0].operateInfo" />	  
				     </s:elseif>
				     
					 <s:else>
					                                暂无进度
					 </s:else>
				</p>
			<table class="ui-table table-bordered fn-mt10">
					<thead>
						<tr>
						   	<th>处理时间</th>
						   	<th>跟踪动态</th>
						   	<th>操作人</th>
						</tr>
					</thead>
					<tbody>
					
					 <s:if test="#request.expressPathInfo.data!=null">
						  <s:iterator value="#request.expressPathInfo.data" var="itemVar" status="itemStatus" >
								<tr>
									<td>
									<s:property value="ftime"/>
									</td>
									<td class="fn-text-left"><s:property value="context"/></td>
									<td><s:property value="opera"/></td>
								</tr>
							</s:iterator>					    
					 </s:if>
					 <s:elseif test="#request.orderSysOpFlowList!=null">
					     <s:iterator value="#request.orderSysOpFlowList" var="itemVar" status="itemStatus" >
							<tr>
								<td>
								<s:date name="#itemVar.nowOperateDate" format="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td class="fn-text-left"><s:property value="operateInfo"/></td>
								<td><s:property value="nowOperator"/></td>
							</tr>
						</s:iterator>
					 </s:elseif>	
					</tbody>
				</table>				
	</div>
	<!-- maliqun add  20150331 新增物流追踪信息选项卡  end-->	 	 
		 
	</div>
</div>
<!-- 
<div id="question" style="position:absolute;width:430px;height:600px;z-index:1000;display:none"></div>
 -->
<div id="question" style="display:none"></div>

