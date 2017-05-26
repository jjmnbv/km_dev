<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="订单详情"></jsp:param>
</jsp:include>
<title>订单详情</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<!-- 左侧内容区域开始 begin -->
<div class="container-fluid">
     <div class="row-fluid">
         <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_order.jsp"></jsp:include>
            <div class="content">
                <div class="row-fluid">
                    <!-- block -->
                    <div class="block_01">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home"></i>
                            <li>订单 <span class="divider">/</span></li>
                            <li>订单详情</li>
                        </ul>
                    </div>
                <!--订单详情开始-->

                <!-- 隐藏域begin -->
                <input type="hidden" id="orderCode"  value="<s:property value="order.orderCode"/>" >
                <input type="hidden" id="isRefresh" value="N" />

                <div class="orders">
                    <div class="span6">
                        <div class="otitle">订单信息</div>
                        <div class="ordersli">
                        <ul>
                            <li><label>收货人：</label><s:property value="order.consigneeName"/>&nbsp;&nbsp;  <s:property value="order.consigneeMobile"/> &nbsp; <s:property value="order.consigneeTel"/></li>
                            <li><label></label><s:property value="order.consigneeAddr"/></li>
                            <li><label>支付方式：</label><s:property value="order.payMethodStr"/></li>
                            <li><label>配送方式：</label><s:property value="order.deliveryDateTypeStr"/></li>
                            <li><label>运&nbsp;&nbsp;费：</label><s:if test="order.fare>0"><s:property value="order.fare"/>元</s:if><s:else>免运费</s:else></li>
                            <li><label>买家留言：</label><s:if test="order.orderDescription!=null"><s:property value="order.orderDescription"/></s:if><s:else>无</s:else></li>
                            <li><label> 后台备注：</label><s:if test="order.orderOperationRemark!=null"><s:property value="order.orderOperationRemark"/></s:if><s:else>无</s:else></li>
                            <li><label> 发票：</label><s:if test="order.invoiceInfoType!=null||order.invoiceInfoTitle!=null||order.invoiceInfoContent!=null">类型：普通发票<br/>抬头：<s:property value="order.invoiceInfoTitle"/></s:if><s:else> 买家未要求开发票</s:else></li>
                            <li class="bbian"><label>订单编号：</label><s:property value="order.orderCode"/></li>
                            <li><label>买家账号：</label><s:property value="order.customerAccount" /></li>
                            <li><label>电子邮件：</label><s:if test="order.email!=null"><s:property value="order.email"/></s:if><s:else>无</s:else></li>
                           	<s:if test="order.orderType==7">
                           		 <li><label>尾款支付通知手机：</label><s:property value="order.informPayTel"/></li>
                           	</s:if>
                            </ul>
                        </div>
                    </div>

                    <div class="span6 ordersL">
                    <div class="ortitle"><i class="icon-orders"></i> 订单状态：
                        <s:if test='order.orderStatusStr=="待风控评估"'>
                            订单风控中
                        </s:if>
                        <s:elseif test='order.orderStatusStr=="风控通过"'>
                            已锁库存
                        </s:elseif>
                        <s:else>
                            <s:property value="order.orderStatusStr"/>
                        </s:else>
                    </div>

                    <!-- 订单按钮begin -->
                    <div class="controls charttop">
                        <button class="btn btn-danger backBtn" id="return">返回</button>
                        <!-- 未付款1可以修改邮费 -->
                        <s:if test="order.orderStatus == 1">
                            <button class="btn btn-primary" id="editOrderYouFei">修改运费</button>
                        </s:if>
                        <!-- 订单状态为未付款1和已付款2 -->
                        <s:if test="order.orderStatus==1||order.orderStatus==2">
                            <button class="btn btn-primary" id="editOrderInfo">修改订单信息</button>
                        </s:if>
                        <%-- 已付款2可以结转
                        <s:if test="order.orderStatus==2">
                            <button class="btn btn-primary" data-orderCode="<s:property value="order.orderCode"/>" id="carryOverOrder">结转</button>
                        </s:if>
                        --%>
                        <!-- 已出库的可以发货 确认配送信息-->
                        <s:if test="order.orderStatus == 4">
                            <button class="btn btn-primary" id="sureDelivery">发货</button>
                        </s:if>
                        <!-- 已配送的仅限修改一次物流信息 -->
                        <s:if test="#request.canUpdateLogistic">
                            <button class="btn btn-primary" id="updateDeliveryInfo">修改配送信息</button>
                        </s:if>
                        <!-- 风控通过可以结转 -->
                        <s:if test="order.orderStatus==22">
                            <button class="btn btn-primary" data-orderCode="<s:property value="order.orderCode"/>" id="carryOverOrder">结转</button>
                        </s:if>
                    </div>

                    <!-- 物流流水显示begin -->
                    <!-- 已配送的和已完成的有物流信息可做显示 -->
                    <s:if test="order.orderStatus>=5">
                        <div class="liushui"><ul>
                        <li>● 物流信息：</li>
                        <li><p><s:property value="order.logisticsName"/>&nbsp;&nbsp;运单号:&nbsp;&nbsp;<s:property value="order.logisticsOrderNo"/></p></li>
                        <s:if test="#request.expressSubscription!=null">
							<%--
                            <li><p>寄件城市:<s:property value="expressSubscription.fromCity"/></p></li>
                            <li><p>收件城市:<s:property value="expressSubscription.toCity"/></p></li>
                            <li><p>快递状态:<s:property value="#request.expressStatusMap[expressSubscription.expressStatus]"/></p></li>
                            --%>
                            <s:iterator value="#request.expressSubscription.expressTrackList" var="tracks">
                                <li><p><s:date name="trackDate" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;<s:property value="trackMsg"/></p></li>
                            </s:iterator>
                        </s:if>
                        <s:else>
                            <li><p>该订单暂时还没有产生任何物流信息!</li>
                        </s:else>
                        </ul>
                        </div>
                    </s:if>
                    <!-- 物流流水显示end -->

                    <!-- 操作流水显示begin -->
                    <div class="liushui">
                        <ul>
                            <li>● 操作流水：</li>
                            <s:if test="operates.size>0">
                            <s:set name="opSize" value="operates.size"/>
                            <s:iterator id="orderOperate" value="operates" status="index">
                            <s:if test="#index.index<=0">
                            <li><p><s:date name="#orderOperate.nowOperateDate" format="yyyy-MM-dd HH:mm:ss" />&nbsp;&nbsp;<s:property value="#orderOperate.operateInfo"/></p></li>
                            </s:if>
                                <s:if test=" #opSize>1 && #index.index==1">
                                 <li><a class="label collapsed" data-toggle="collapse" data-parent="#accordion" href="#optDetail"><i class="icon-xia icon-white"></i> 明细</a></li>
                                 <li id="optDetail" class="collapse">
                                </s:if>
                                <s:if test="#index.index>=1">
                                <p><s:date name="#orderOperate.nowOperateDate" format="yyyy-MM-dd HH:mm:ss" />&nbsp;&nbsp;<s:property value="#orderOperate.operateInfo"/></p>
                                </s:if>
                                <s:if test="#opSize>1 && #index.index==(#opSize-1)">
                                </li>
                                </s:if>
                            </s:iterator>
                            </s:if>
                            <s:else>
                            <li><p>该订单暂时尚未产生任何操作流水!</p></li>
                            </s:else>
                        </ul>
                    </div>
                    <!-- 操作流水显示end -->
                </div>
				<!--订单详情结束-->
            </div>
            
	<!-- 发票信息 -->
	<table cellpadding="0" cellspacing="0" border="0"
		class="table  table-bordered">
		<thead>
			<tr class="tablesbg">
				<th colspan="10" class="textL">发票信息</th>
			</tr>
		</thead>
		<tbody>
			<tr class="trbg">
				<th>发票明细号</th>
				<th class="width150">发票流水号</th>
				<th class="width150">开票项目代码</th>
				<th class="width100">开票项目名称</th>
				<th class="width100">单价</th>
				<th class="width100">数量</th>
				<th class="width100">折扣额</th>
				<th class="width100">行金额(元)</th>
				<th class="width100">附注</th>
				<th class="width100">是否扣除</th>
			</tr>
			<s:if test="invoiceItems.size>0">
				<s:iterator id="invoiceItem" value="invoiceItems">
					<tr>
						<td><s:property value="#invoiceItem.invoiceItemId" /></td>
						<td class="width150"><s:property
							value="#invoiceItem.invoiceId" /></td>
						<td class="width150"><s:property
							value="#invoiceItem.invoiceItemCode" /></td>
						<td class="width100"><s:property
							value="#invoiceItem.invoiceItemName" /></td>
						<td class="width100"><s:property
							value="#invoiceItem.unitPrice" /></td>
						<td class="width100"><s:property
							value="#invoiceItem.invoiceItemNumber" /></td>
						<td class="width100"><s:property
							value="#invoiceItem.discountAmount" /></td>
						<td class="width100"><s:property value="#invoiceItem.lineSum" /></td>
						<td class="width100"><s:property value="#invoiceItem.note" /></td>
						<td class="width100"><s:property
							value="#invoiceItem.deductionFlag" /></td>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr>
					<td colspan="10">该订单没有发票信息!</td>
				</tr>
			</s:else>
		</tbody>
	</table>
<table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered" >
<thead><tr class="tablesbg">
  <th colspan="15" class="textL"> 订单详情   &nbsp;&nbsp;&nbsp;&nbsp;<s:if test="preferentials.size>0"><a class="label label-success" data-toggle="collapse" data-parent="#accordion" href="#preferentialInfo"><i class="icon-xia icon-white"></i>优惠信息</a></s:if></th></tr>
<tr>
<th colspan="15" class="textL">
<div id="preferentialInfo" class="collapse">
			<div class="sukinfo">
			<div class="liushui">
			<ul>
			<s:if test="preferentials.size>0">
				<s:set name="preferenFlag" value="false"/>
				<s:iterator id="orderPreferential" value="preferentials">
				<li><p>
				<s:if test="#orderPreferential.orderItemId==null">		
						<s:set name="preferenFlag" value="true"/>
						<span style="color:red"><s:property value="#orderPreferential.orderPreferentialTypeStr" /></span>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <!-- 优惠金额: <span style="color:red"><s:property
					value="#orderPreferential.orderPreferentialSum" />:-->优惠描述:&nbsp;&nbsp;&nbsp; <span style="color:red"><s:property value="#orderPreferential.couponId" /></span>					
				</s:if>
				</p>
				</li>
				
				<s:if test="#request.preferenFlag==false">
					<li class="charttop">该订单没有享受订单层级优惠信息!</li>
				</s:if>
			</s:iterator>
			</s:if>
			<s:else>
				<li class="charttop">该订单没有享受优惠信息!</li>
			</s:else>
			
			</ul>
			</div>
			</div>
		</div>
</th></tr>
</thead>
<tbody>
	<tr class="trbg">
		<td>商品</td>
		<td class="width100">规格</td>
		<td class="width100">SKU编码</td>
		<td class="width100">商家货号</td>
		<td class="width100">单价(元)</td>
		<td class="width100">推广服务费(元)</td>
		<td class="width100">数量</td>
		<td class="width100">优惠活动 </td>
		<td class="width100">商品金额(元)</td>
		<td class="width100">实收金额(元)</td>
		<%--<th>供应商</th>
		<th>佣金比例</th>
		<th>应结货款</th>
		<s:if test="#session.seesionKmb2bSupplierType == 4">
		<th>PV</th>
		<th>合作方收益</th>
		</s:if>
		<th width="9%">批次号</th>
		--%>
	</tr>
	<s:iterator id="orderItem" value="items" status="index">
		<tr>
			<td>
				<table width="100%" border="0" class="newform">
					<tbody>
					<tr>
						<td width="120px">
							<a target="_blank" href="<s:property value='cmsPagePath'/><s:property value='#orderItem.productSkuId'/>.shtml"
							   class="pull-left">
								<img class="thumbnail" src="<s:property value="imagePath"/><s:property value="#orderItem.imageUrl" />">
							</a>
						</td>
						<td class="textc">
							<a target="_blank" href="<s:property value='cmsPagePath'/><s:property value='#orderItem.productSkuId'/>.shtml"
							   title="<s:property value="#orderItem.commodityTitle"/>">
								<s:property value="#orderItem.commodityTitle"/>
							</a>
						</td>
					</tr>
					</tbody>
				</table>
			</td>
			<td class="width100"><s:property value="#orderItem.commoditySkuDescription"/></td>
			<td class="width100">
				<a href="<s:property value='cmsPagePath'/><s:property value='#orderItem.productSkuId'/>.shtml"
				   class="fn-blue" target="_blank"><s:property value="#orderItem.commoditySku"/>
				</a>
			</td>
			<td class="width100"><s:property value="#orderItem.sellerSkuCode"/></td>
			<td class="width100">
				<s:if test="order.orderType==7">
					<s:property value="order.amountPayable / #orderItem.commodityNumber"/>
					<br>
					定金<s:property value="order.depositSum / #orderItem.commodityNumber"/>
					<s:if test="order.orderStatus==1">(待支付)</s:if>
					<s:elseif test="order.orderStatus==-1">
						<s:if test="order.paidDeposit!=null">(已支付)</s:if>
						<s:else>(待支付)</s:else></s:elseif>
					<s:else>(已支付)</s:else>
					<br>
					尾款<s:property value="finalPay / #orderItem.commodityNumber"/>
					<s:if test="order.orderStatus==1 || order.orderStatus==23">(待支付)</s:if>
					<s:elseif test="order.orderStatus==-1">
						<s:if test="order.noFinalPayment!=null">(已支付)</s:if>
						<s:else>(待支付)</s:else></s:elseif>
					<s:else>(已支付)</s:else>
				</s:if>
				<s:else>
					<s:property value="#orderItem.commodityUnitPrice"/>
				</s:else>
			</td>
			<td class="width100">
				<fmt:formatNumber value="${orderItem.commodityPv * orderItem.commodityNumber}" type="currency" pattern="#,##0.00" />
			</td>
			<td class="width100"><s:property value="#orderItem.commodityNumber"/></td>
			<td class="width100">
				<s:if test="#orderItem.preferentialList.size>0">
					<s:iterator value="#orderItem.preferentialList">
						<span style="color:red"><s:property value="orderPreferentialTypeStr"/></span>
						<s:if test="orderPreferentialType==8">
							&nbsp;&nbsp;<s:property value="couponId"/>折
						</s:if>
					</s:iterator>
				</s:if>
				<s:else>
					无
				</s:else>
			</td>
			<td class="width100">
				<s:if test="order.orderType==7">
					<s:property value="order.amountPayable"/>
				</s:if>
				<s:else>
					<s:property value="#orderItem.commodityUnitPrice * #orderItem.commodityNumber"/>
				</s:else>
			</td>
			<td class="width100">
				<s:if test="order.orderType==7">
					<font color="red"><s:property value="order.amountPayable"/></font>
				</s:if>
				<s:else>
					<s:property value="#orderItem.commodityUnitIncoming * #orderItem.commodityNumber"/>
				</s:else>
			</td>
			<%--
		 <s:if test="#session.seesionKmb2bSupplierType == 4">
			 <td><s:property value="#orderItem.commodityPv * #orderItem.commodityNumber"/></td>
			 <td><s:property value="#orderItem.costIncomeMoney * #orderItem.commodityNumber"/></td>
		 </s:if>
		 <td><s:property value="#orderItem.supplier"/></td>
		 <td><s:property value="#orderItem.commissionRate"/></td>
		 <td><s:property value="#orderItem.settlementLoan"/></td>
		 <td><s:property value="#orderItem.warehouseId.intValue()"/></td>
		 <td><s:property value="#orderItem.commodityBatchNumber"/></td>
		 <td>s:if test="#order.isnotparentorder&&#order.orderStatus&ge;3&&#orderItem.isReturning==0"
		 <s:if test="order.orderStatus>=3&&order.orderStatus!=16&&#orderItem.isReturning==0">
		 <a href="javascript:void(0);" class="alter" name="<s:property value='#orderItem.orderItemId'/>">退换货申请</a>
		 </s:if></td>
		 <td><s:property value="%{formatdouble(#orderItem.warehouseId)}"/><button data-value="<s:property value='#orderItem.orderItemId'/>" class="rtest">退换货测试</button></td>
			--%>
		</tr>
	</s:iterator>
</tbody>
<thead>
<tr class="tablesbg">
	<th colspan="15">
		<div class="moneyli">
			<ul>
				<li>
					<label>商品总额:</label>
					<s:if test="order.orderType==7">
						<p>
							<font color="red"><s:property value="order.amountPayable"/></font>
						</p> 元
					</s:if>
					<s:else>
						<p <s:if test="order.commoditySum<=0">class="fontgay"</s:if>>
							<font color="red"><s:property value="order.commoditySum"/></font>
						</p> 元
					</s:else>
					<s:if test="order.orderType==7">
						<br/><br/>
						<div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden"></div>
					</s:if>
				</li>
				<s:if test="order.orderType==7">	
					<li><label>商品定金:</label>
						<p <s:if test="order.depositSum<=0">class="fontgay"</s:if>>
							<font color="red"><s:property value="order.depositSum"/></font>
						</p> 元
					</li>
					<li><label>商品尾款:</label>
						<p <s:if test="finalPay<=0">class="fontgay"</s:if>>
							<font color="red"><s:property value="finalPay"/></font>
						</p> 元
					</li>
				</s:if>
				<li><label>商品运费:</label>
					<p <s:if test="order.fare<=0">class="fontgay"</s:if>><s:property value="order.fare"/></p> 元
				</li>
				<s:if test="0!=plusDiscount">
					<li><label>加价购:</label>
						<p <s:if test="plusDiscount<=0">class="fontgay"</s:if>><s:property value="plusDiscount"/></p> 元
					</li>
				</s:if>
				<s:if test="0!=fullDdiscount">
					<li><label>满减:</label>
						<p <s:if test="fullDdiscount<=0">class="fontgay"</s:if>><s:property value="fullDdiscount"/></p>
						元
					</li>
				</s:if>
				<li>
					<label>应付金额:</label>
					<s:if test="order.orderType==7">
						<p>
							<font color="red"><s:property value="order.amountPayable"/></font>
						</p> 元
					</s:if>
					<s:else>
						<p <s:if test="actualpay<=0">class="fontgay"</s:if>><s:property value="actualpay"/>
						</p> 元
					</s:else>
					<s:if test="order.orderType==7">
						<br/><br/>
						<div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden"></div>
					</s:if>
				</li>
				<li>
					<label>已付金额:</label>
					<p <s:if test="orderpay<=0">class="fontgay"</s:if>>
						<s:if test="order.orderType==7">
							<s:property value="wasPayed"/>
						</s:if>
						<s:else>
							<s:property value="orderpay"/>
						</s:else>
					</p> 元
				</li>
				<s:if test="0!=couponpay">
					<li><label>优惠券:</label>
						<p <s:if test="couponpay<=0">class="fontgay"</s:if>><s:property value="couponpay"/></p> 元
					</li>
				</s:if>
				<s:if test="0!=balancepay">
					<li><label>余额支付:</label>
						<p <s:if test="balancepay<=0">class="fontgay"</s:if>><s:property value="balancepay"/></p> 元
					</li>
				</s:if>
				<s:if test="0!=bankpay">
					<li><label>网银/信用卡:</label>
						<p <s:if test="bankpay<=0">class="fontgay"</s:if>><s:property value="bankpay"/></p> 元
					</li>
				</s:if>
				<s:if test="0!=platformpay">
					<li><label>在线:</label>
						<p <s:if test="platformpay<=0">class="fontgay"</s:if>><s:property value="platformpay"/></p> 元
					</li>
				</s:if>
				<s:if test="0!=onlinepay">
					<li><label>在线:</label>
						<p <s:if test="onlinepay<=0">class="fontgay"</s:if>>
							<s:if test="order.orderType==7">
								<s:property value="wasPayed"/>
							</s:if>
							<s:else>
								<s:property value="onlinepay"/>
							</s:else>
						</p> 元
					</li>
				</s:if>
				<li><label>未付金额:</label>
					<p <s:if test="notpay<=0">class="fontgay"</s:if>>
						<s:if test="order.orderType==7">
							<s:property value="order.amountPayable - wasPayed"/>
						</s:if>
						<s:else>
							<s:property value="notpay<0?0.00:notpay"/>
						</s:else>
					</p> 元
				</li>
				<%--<li><label>订单金额:</label><p class="font22"><s:property value="orderpay"/></p> 元</li>--%>
			</ul>
		</div>
	</th>
</tr>
</thead>
</table>
</div>
</div>
</div>
</div>
</div>

<!-- 修改物流 配送修改信息框 begin -->
<s:if test="#request.logisticsCompanyMap != null">
<div id="logisticsCompanyDiv" style="display: none;">
	<div class="ui-well ui-well-form">
		<div class="ui-form ui-form-info fn-mt20">
			<fieldset>
				<legend>请填写物流信息</legend>
				<div class="ui-form-item">
					<label class="ui-form-label" ><font color="red">*</font>物流公司：</label>
					<s:select list="#request.logisticsCompanyMap"
						headerKey="" headerValue="请选择物流" cssClass="ui-form-select logisticsCompany"/>
					<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_logisticsCompany_error" style="display: none;">
						<i class="ui-icon ui-icon-error"></i><span class="logisticsCompany_error_msg" style="font-size:12px;" ></span>
					</p>
				</div>
				<div class="ui-form-item">
					<label class="ui-form-label" ><font color="red">*</font>物流单号：</label>
					<input class="ui-input logisticsNo" type="text" style="width: 140px;"
						value="<s:property value='logisticsNo'/>"
						maxlength="30" />
					<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_logisticsNo_error" style="display: none;">
						<i class="ui-icon ui-icon-error"></i><span class="logisticsNo_error_msg" style="font-size:12px;"></span>
					</p>
				</div>
				<div style="text-align:center;">
					<input class='btn btn-primary j_sure_editLogisticsInfo' type='button'  value='确定'>
				</div>
			</fieldset>
		</div>
	</div>
</div>
</s:if>
<!-- end -->

<!-- 仅限修改一次物流信息begin -->
<s:if test="#request.canUpdateLogistic">
<div id="logisticsCompanyUpdateDiv" style="display: none;">
	<div class="ui-well ui-well-form">
		<div class="ui-form ui-form-info fn-mt20">
			<fieldset>
				<legend>修改物流信息</legend>
				<div class="ui-form-item">
					<label class="ui-form-label" ><font color="red">*</font>物流公司：</label>
					<s:select list="#request.logisticsCompanyMapForUpdate"
						headerKey="" name="order.logisticsName" headerValue="请选择物流" cssClass="ui-form-select logisticsCompany"/>
					<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_logisticsCompany_error" style="display: none;">
						<i class="ui-icon ui-icon-error"></i><span class="logisticsCompany_error_msg" style="font-size:12px;" ></span>
					</p>
				</div>
				<div class="ui-form-item">
					<label class="ui-form-label" ><font color="red">*</font>物流单号：</label>
					<input class="ui-input logisticsNo" type="text" style="width: 140px;"
						value="<s:property value='order.logisticsNo'/>" maxlength="30" />
					<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_logisticsNo_error" style="display: none;">
						<i class="ui-icon ui-icon-error"></i><span class="logisticsNo_error_msg" style="font-size:12px;"></span>
					</p>
				</div>
				<div style="text-align:center;">
					<input class='btn btn-primary j_sure_updateLogisticsInfo' type='button'  value='确定'>
				</div>
			</fieldset>
		</div>
	</div>
</div>
</s:if>
<!-- 仅限修改一次物流信息end -->
<!-- 供页面刷新 -->
<form action="showOrderItemDetail.action" method="post" id="orderItemFrm" name="orderItemFrm" namespace="order" >
	 <input type="hidden" name="orderCode" value='<s:property value="order.orderCode"/>'/>
	<input type="hidden" name="backType" id="backType" value="<s:property value="#request.backType"/>" />
</form>
<form action="showOrderListByStatus.action" method="post" id="orderListConditionFrm" name="orderListConditionFrm" namespace="order" >
	<s:hidden id="orderCondition" name="orderStatusForMenuQuery"/>
	<s:hidden name="titleValue"/>
	<s:hidden name="access"/>
	<!--<input type="hidden" name="orderCodeForSearch" value="<s:property value="orderCode"/>"/>-->
</form>
<form action="findAllReturnNotes.action" method="post" id="returnOrderFrm" name="returnOrderFrm" namespace="supplier" >
	<input type="hidden" name="backType" id="backType" value="<s:property value="#request.backType"/>" />
</form>

<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>