<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="退换货列表"></jsp:param>
</jsp:include>
<title>退换货列表</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<div class="container-fluid">
<div class="row-fluid"><!--左侧菜单开始-->
<jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_order.jsp"></jsp:include>
<!--左侧菜单结束-->

<div class="content">
<div class="row-fluid"><!-- block -->
<div class="block_01"><!--订单详情开始-->
<div class="navbar-inner">
<ul class="breadcrumb">
	<i class="icon-home"></i>
	<li>订单 <span class="divider">/</span></li>
	<li>退换货订单详情</li>
</ul>
</div>
<div class="orders">
<div class="span6">
<div class="otitle">订单信息</div>
<s:form action="gotoReturnNotesEdit.action" method="post" id="frm" name="frm">
	<s:if test='order.status == 1'>
		<s:set name="statusName" value="'确认到货'" scope="request" />
		<s:property value="#statusName" />
	</s:if>
	<s:if test='order.status == 2'>
		<s:set name="statusName" value="'进行质检'" scope="request" />
	</s:if>
	<s:if test='order.status == 3'>
		<s:set name="statusName" value="'同意退货'" scope="request" />
	</s:if>

	<!-- 隐藏域 -->
	<s:hidden name="order.returnId" id="returnId"></s:hidden>
	<s:hidden name="order.custName"></s:hidden>
	<s:hidden name="order.productNo"></s:hidden>
	<s:hidden name="order.productId"></s:hidden>
	<s:hidden name="order.productName"></s:hidden>
	<s:hidden name="order.productSkuId"></s:hidden>
	<s:hidden name="order.productSku"></s:hidden>
	<s:hidden name="order.productCounts"></s:hidden>
	<s:hidden name="order.orderdetailId"></s:hidden>
	<s:hidden name="order.orderType"></s:hidden>
	<s:hidden name="order.orderCode" id="orderCode"></s:hidden> <!-- 此处的orderCode存入的其实是退换货单号 -->
	<s:hidden name="order.warehouseId"></s:hidden>
	<s:hidden name="order.unitPrice"></s:hidden>
	<s:hidden name="order.totalPrice"></s:hidden>
	<s:hidden id="orderStatus" name="order.status"></s:hidden>
	<s:hidden id="handleResult" name="order.handleResult"></s:hidden>
	<s:hidden id="distributionId" name="distributionInfo.distributionId"></s:hidden>
	<s:hidden id="stockOutId" name="stockOut.stockOutId"></s:hidden>
	<input type="hidden" value="<s:property value='order.handleResult'/>" name="handleResult"/>
	<input type="hidden" value="<s:property value='order.returnId'/>" name="returnId"/>
	<input type="hidden" name="alterCode" value="<s:property value="orderAlter.orderAlterCode"/>">
	<s:hidden name="viewType"></s:hidden>
</s:form>
<input type="hidden" id="isRefresh1" value="N" />
<input type="hidden" id="alterCodeId"  value="<s:property value="orderAlter.orderAlterCode"/>" >
<input type="hidden" id="orderCodeForAlter"  value="<s:property value="orderAlter.orderCode"/>" >
<input type="hidden" id="preferentialAmount" value="<s:property value="orderAlter.preferentialAmount"/>"></input>

<form action="" method="post" id="checkOderFrm" name="checkOderFrm" namespace="order" >
</form>
<!-- 返回 -->
<form action="findAllReturnNotes.action" method="post" id="frmListNot" name="orderListFrmNot" namespace="supplier" >
</form>

<form action="" method="post" id="orderListConditionFrm1" name="orderListConditionFrm1" namespace="supplier" >
</form>

<div class="ordersli">
<ul>
	<li><label>申请类型：</label><fontred><s:property value='orderAlter.alterTypeStr'/></fontred></li>
	<li class="bbian"><label>原订单编号：</label><s:property value="orderAlter.orderCode"/></li>
	<li><label>退换货单编号：</label><s:property value='orderAlter.orderAlterCode'/></li>
	<li><label>商品名称：</label>
		<a href='<s:property value="cmsPagePath"/><s:property value="item.productSkuId"/>.shtml' target="_blank"><s:property value="item.commodityName"/></a>
	</li>
	<li><label>商品SKU：</label><s:property value="item.commoditySku"/></li>
	<li><label>商品单价：</label><s:property value="item.commodityUnitPrice"/>元 </li>
	<li><label>商品数量：</label><s:property value="orderAlter.alterNum"/>件</li>
	<%--<li><label>实收合计：</label><s:property value="item.commodityUnitIncoming * item.commodityNumber"/>元</li>
	item.commodityUnitIncoming=<s:property value='item.commodityUnitIncoming'/>
	item.commodityUnitPrice=<s:property value='item.commodityUnitPrice'/>
	--%>
	<li><label>买家账号：</label><s:property value="orderMain.customerAccount" /></li>
	<li><label>收货信息：</label><s:property value="orderAlter.name"/> <s:property value="orderAlter.phone"/></li>
	<li><label></label><s:property value="orderAlter.province"/><s:property value="orderAlter.city"/><s:property value="orderAlter.area"/><s:property value="orderAlter.address"/></li>
</ul>
</div>
</div>

<div class="span6 ordersL">
<div class="ortitle"><i class="icon-orders"></i> 订单状态：<s:property value="orderAlter.proposeStatusStr"/></div>
<!-- 按钮区begin -->
<div class="controls charttop">
<button class="btn btn-danger" id="returnNot">返回</button>

<!-- 如果状态为待审核 -->
<s:if test="orderAlter.proposeStatus==1">
	<button class="btn btn-danger j_audit_returnOrder"> 审 核</button>
</s:if>
<s:if test="orderAlter.proposeStatus ==54">
	<button class="btn btn-danger" id="reCheckOder" >重新审核</button>
</s:if>
<s:if test="orderAlter.proposeStatus ==51">
	<button class="btn btn-danger" id="retMoney" >确认退款</button>
</s:if> 
<s:if test="orderAlter.proposeStatus ==53">
	<button class="btn btn-danger" id="retYuanJan" data-clickType="0">返回原件</button>
</s:if> 
<s:if test="isAdditional"> <!-- 好像暂时这块并没有启用 -->
	<button class="btn btn-danger" id="isAdditional">补单</button>
</s:if>
<s:if test="orderAlter.proposeStatus >= 3">
	<s:if test="order.handleResult gt 2">
		<button class="btn btn-danger changeStatus" id="btnChange" data-Status="1"
				data-value="<s:property value='#request.statusName' />">
			<span id="showName"> <s:property value='#request.statusName' /></span>
		</button>
		<button <s:if test="order.status == 1">style="display:none;"</s:if>
				class="btn btn-danger changeStatus" id="btnRefuse" data-Status="2">拒绝退货</button>
	</s:if>
</s:if>
<!-- 确认配送 -->
<s:if test="orderAlter.proposeStatus==52">
	<button class="btn btn-danger" id="peiSong" data-Status="2" data-clickType="1">确认配送</button>
</s:if>
</div>

<!-- 按钮区end -->
<div class="liushui">
<ul>
<li>● 操作流水：</li>	
<s:if test="listOrderAlterOperates!=null && listOrderAlterOperates.size>0">  
<s:set name="listSize" value="listOrderAlterOperates.size"/>
<s:iterator id="orderOperate" value="listOrderAlterOperates" status="index">
	<s:if test="#index.index<=0">
		<li>
		<p><s:date name="#orderOperate.nowOperateDate" format="yyyy-MM-dd HH:mm:ss" />
		   <s:property value="#orderOperate.operateInfo"/>
		</p>
		</li>
	</s:if>
	<s:if test=" #listSize>1 && #index.index==1">
		<li><a class="label collapsed" data-toggle="collapse"
			data-parent="#accordion" href="#expDetail"><i
			class="icon-xia icon-white"></i> 明细</a></li>
		<li id="expDetail" class="collapse">
	</s:if>
	<s:if test="#index.index>=1">
		<p><s:date name="#orderOperate.nowOperateDate" format="yyyy-MM-dd HH:mm:ss" />
		   <s:property value="#orderOperate.operateInfo"/>
		</p>
	</s:if>
	<s:if test="#listSize>1 && #index.index==(#listSize-1)">
		</li>
	</s:if>
</s:iterator>
</s:if>
<s:else>
	<li>
		<p>该退换货订单暂时未产生任何流水!</p>
	</li>
</s:else>
</ul>
</div>
</div>
<!--订单详情结束-->
</div>
<ul class="nav nav-tabs">
	<li class="active"><a href="#home">申请明细</a></li>
</ul>
<div class="tuifuoli">
<ul>
	<li><label>申请类型：</label><fontred><s:property value='orderAlter.alterTypeStr'/></fontred></li>
	<li><label>申请票据：</label><s:if test='orderAlter.evidence == 1'>有发票</s:if><s:else>无发票</s:else></li>
	<li><label>申请时间：</label><s:date name="orderAlter.createDate" format="yyyy-MM-dd HH:mm:ss"/></li>
	<li class="bbian"><label>申请原因：</label>
		<div class="forp">
			<s:if test="orderAlter.alterComment!=null"><s:property value='orderAlter.alterComment'/></s:if>
			<s:else>无</s:else>
		</div>
	</li>
	<li class="bbian">
	<label>上传图片：</label>
	<s:if test="photoList.size>0">
	<s:iterator value="photoList" id="potoUrl">
		<a href='<s:property value='showPath'/><s:property value='#potoUrl.url'/>' target="_blank">
			<img class="thumbnail" src="<s:property value='showPath'/><s:property value='#potoUrl.url'/>">
		</a>
    </s:iterator>
	</s:if>
	<s:else>
		该退货换订单没有上传图片!
	</s:else>
	</li>
	<s:if test="orderAlter.proposeStatus!=1">
	<li class="bbian"><label>商家审核说明：</label>
		<div class="forp">
			<s:if test="orderAlter.auditComment!=null"><s:property value="orderAlter.auditComment"/></s:if>
			<s:else>商家未填写审核说明</s:else>
		</div>
	</li>
	</s:if>
</ul>
</div>
</br>
	<s:if test="orderAlter.proposeStatus!=1 || orderAlter.proposeStatus==-2 ">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#home">退换货相关信息</a></li>
		</ul>
		<div class="tuifuoli">
			<ul>
				<%--<li>
                      <label>赔付金额：</label>￥<s:property value='orderAlter.ruturnMoney==null?0.00:orderAlter.ruturnMoney'/>元&nbsp;&nbsp;（共计￥<s:property value='orderAlter.ruturnSum==null?0.00:orderAlter.ruturnSum'/>元退款到余额或银行账户）
                    </li>--%>
				<s:if test="orderAlter.alterType==4">
					<li><label>退商品定金：</label><s:property value="orderAlter.deposit"/>元</li>
					<li><label>退商品尾款：</label><s:property value="orderAlter.finalmoney"/>元</li>
					<li><label>定金补偿：</label><s:property value="orderAlter.compensate"/>元</li>
					<li>
						<label>赔付总金额：</label>共计￥<s:property value='orderAlter.ruturnSum==null?0.00:orderAlter.ruturnSum'/>元退款到余额或银行账户
					</li>
				</s:if>
				<s:else>
					<li>
					<label>商品退款金额：</label>￥<s:property value='orderAlter.ruturnMoney==null?0.00:orderAlter.ruturnMoney'/>元
				</li>
				<li>
					<label>退货返运费：</label>￥<s:property value='orderAlter.returnFare==null?0.00:orderAlter.returnFare'/>元
				</li>
				<li>
					<label>赔付总金额：</label>共计￥<s:property value='orderAlter.ruturnSum==null?0.00:orderAlter.ruturnSum'/>元退款到余额或银行账户
				</li>
					<%--<s:if test="orderAlter.proposeStatus == 7">
                        <s:if test="orderAlter.customerLogisticsName!=null || orderAlter.customerLogisticsName!=null">
                            <li><label>退回快递信息：</label><s:if test="orderAlter.customerLogisticsName!=null"><s:property value='orderAlter.customerLogisticsName'/></s:if> <s:else>无</s:else> &nbsp;&nbsp;运单号：<s:if test="orderAlter.customerLogisticsNo!=null"><s:property value='orderAlter.customerLogisticsNo'/></s:if><s:else>无</s:else></li>
                        </s:if>
                    </s:if>
                    <s:if test="orderAlter.proposeStatus==63">
                        <li><label>退回快递信息：</label><s:if test="orderAlter.customerLogisticsName!=null"><s:property value='orderAlter.customerLogisticsName'/></s:if> <s:else>无</s:else> &nbsp;&nbsp;运单号：<s:if test="orderAlter.customerLogisticsNo!=null"><s:property value='orderAlter.customerLogisticsNo'/></s:if><s:else>无</s:else></li>
                    </s:if>
                    <s:if test="orderAlter.proposeStatus == 7">
                        <s:if test="orderAlter.logisticsName!=null || orderAlter.logisticsOrderNo!=null">
                            <li><label>换货快递信息：</label><s:if test="orderAlter.logisticsName!=null"><s:property value='orderAlter.logisticsName'/></s:if><s:else>无</s:else>&nbsp;&nbsp; 运单号：<s:if test="orderAlter.logisticsOrderNo!=null"><s:property value='orderAlter.logisticsOrderNo'/></s:if><s:else>无</s:else></li>
                        </s:if>
                    </s:if>
                    <s:if test="orderAlter.proposeStatus==62">
                        <li><label>换货快递信息：</label><s:if test="orderAlter.logisticsName!=null"><s:property value='orderAlter.logisticsName'/></s:if><s:else>无</s:else>&nbsp;&nbsp; 运单号：<s:if test="orderAlter.logisticsOrderNo!=null"><s:property value='orderAlter.logisticsOrderNo'/></s:if><s:else>无</s:else></li>
                    </s:if>
                    --%>
				<li><label>买家快递信息：</label>
					<s:if test="orderAlter.customerLogisticsName!=null"><s:property
							value='orderAlter.customerLogisticsName'/></s:if>
					<s:else>无</s:else>&nbsp;&nbsp; &nbsp;&nbsp;
					运单号：<s:if test="orderAlter.customerLogisticsNo!=null"><s:property
							value='orderAlter.customerLogisticsNo'/></s:if>
					<s:else>无</s:else>
				</li>
				<li><label>商家快递信息：</label>
					<s:if test="orderAlter.logisticsName!=null"><s:property
							value='orderAlter.logisticsName'/></s:if>
					<s:else>无</s:else> &nbsp;&nbsp; &nbsp;&nbsp;
					运单号：<s:if test="orderAlter.logisticsOrderNo!=null"><s:property
							value='orderAlter.logisticsOrderNo'/></s:if>
					<s:else>无</s:else>
				</li>
					<%--<li><label>商家退换货地址：</label>
                    <div class="forp"><s:property value="orderAlter.province"/><s:property value="orderAlter.city"/><s:property value="orderAlter.area"/><s:property value='orderAlter.address'/></div>
                    </li>
                    <li><label>买家收货人：</label>
                    <div class="forp"><s:property value='orderAlter.name'/> 买家电话：<s:property value='orderAlter.phone'/></div>
                    </li>
                  --%>
				</s:else>
			</ul>
		</div>
	</s:if>
	<div>
		<!-- 如果可以审核则需要有审核按钮 -->
		<%--<s:if test="orderAlter.proposeStatus==1">
            <a href="#auditDialog"
                class="btn btn-danger btn-large j_audit_returnOrder"
                data-toggle="modal"><i class="icon-eye-open icon-white"></i> 审 核</a>
        </s:if>
        --%>
		<!--弹出层开始 style="display:none;"-->
		<div id="auditDialog" class="modal hide" style="display:none;">
			<div class="modal-header">
				<h3>确认提交
				</h3>
			</div>
			<div class="modal-body">
				<form class="form-horizontal">
					<fieldset>
						<div class="divfrom">
							<ul>
								<li><label>审核说明：</label>
									<textarea name="comment" id="comment" class="input-xlarge textarea"
											  placeholder="审核说明 ..." style="height: 100px"></textarea></li>
								<li><label>商品退款金额：</label>
									<s:if test="orderAlter.alterType==1">
										<input style="width:120px" type="text" data-provide="typeahead" data-items="4" name="" id="returnMoney"
											   value="<s:property value='item.commodityUnitIncoming*orderAlter.alterNum'/>"/>
									</s:if>
									<s:elseif test="orderAlter.alterType==2" >
										<input style="width:120px" type="text" data-provide="typeahead" data-items="4" name="" id="returnMoney"
											   value="0.00"/>
									</s:elseif>
									<s:elseif test="orderAlter.alterType==4" >
										<input style="width:120px" type="text" data-provide="typeahead" data-items="4" name="" id="returnMoney"
											   readonly="true"
											   value="<s:property value='orderAlter.ruturnSum'/>"/>
									</s:elseif>
									<s:else>
										<input style="width:120px" type="text" data-provide="typeahead" data-items="4" name="" id="returnMoney"/>
									</s:else>
									元 （通过申请时填写）
									<input type="hidden" id="alterType" value="<s:property value="orderAlter.alterType"/>"/>
									<s:if test="orderAlter.alterType==4" >
										<input type="hidden" id="maxReturnMoney" value="<s:property value='orderAlter.ruturnSum'/>"/>
									</s:if>
									<s:else>
										<input type="hidden" id="maxReturnMoney"
											   value="<s:property value='item.commodityUnitIncoming*orderAlter.alterNum'/>"/>
									</s:else>
									<!-- 该笔订单的最大实付金额,临界值  orderAlter.ruturnMoney-->
									<input type="hidden" id="maxReturnFare"
										   value="<s:property value='orderMain.fare-totalReturnFare'/>"/>
									<!-- 该笔订单的运费总额,临界值 -->
								</li>
								<li><label>退货返运费：</label>
									<input type="text" data-provide="typeahead" data-items="4" name="" id="returnFare"
										   <s:if test="orderAlter.alterType==4" >readonly="true"</s:if>
										   value="<s:if test="orderAlter.proposeStatus!=54"><s:property value='orderAlter.returnFare==null?0.00:orderAlter.returnFare'/></s:if><s:else>0.00</s:else>"/>
									元 （通过申请时填写）
								</li>
							</ul>
						</div>
						<div class="modal-footer">
							<br/>
							<a data-dismiss="modal" class="btn btn-primary j_sure" href="javascript:void(0);">通过申请</a>
							<!-- 换货转退货后的操作将驳回申请按钮屏蔽 -->
							<s:if test="orderAlter.proposeStatus !=54">
								<a data-dismiss="modal" class="btn j_refuse" href="javascript:void(0);">驳回申请</a>
							</s:if>
						</div>
				</fieldset>
			</form>
		</div>
	</div>
<!--弹出层结束-->

<!-- 返回原件以及配送信息 begin-->
<s:if test="#request.logisticsCompanyMap != null">
	<div id="logisticsDiv" style="display: none;">
		<div class="ui-well ui-well-form">
			<div class="ui-form ui-form-info fn-mt20">
				<fieldset>
					<legend>请填写物流信息</legend>
					<div class="ui-form-item">
						<label class="ui-form-label">退换货单号: </label><span><s:property value='alterCode'/></span>
						<p></p>
					</div>
					<div class="ui-form-item">
						<label class="ui-form-label"><font color="red">*</font>物流公司:</label>
						<s:select list="#request.logisticsCompanyMap"
								  headerKey="" headerValue="请选择物流" cssClass="ui-form-select logisticsCompany"
								  id="logisticsCompanyId"/>
					</div>
					<div class="ui-form-item">
						<label class="ui-form-label"><font color="red">*</font>物流单号:</label>
						<input class="ui-input logisticsNo" id="logisticsNoId" type="text" style="width: 140px;"
							   value="<s:property value='logisticsNo'/>"
							   maxlength="30"/>
					</div>
					<div class="ui-form-item">
						<input class='ui-button ui-button-success j_sure_editLogisticsInfo' type='button' value='确定'>
							&nbsp;&nbsp;&nbsp;
						<input class='ui-button ui-button-success j_sure_close' type='button' value='关闭'>
					</div>
				</fieldset>
			</div>
		</div>
	</div>
</s:if>
<!-- 返回原件弹出框 -->
<%--<div class="modal hide" id="returnDiv" style="display:none">
<div class="modal-body">
<input type="hidden" id="ckType"/>
<fieldset>
<div class="divfrom">
<ul>
	<li><label>退换货单号：</label><s:property value='alterCode'/></li>
	<li><label><span class="required">*</span>物流公司： </label>
	<s:select list="#request.logisticsCompanyMap" id="logisticsCompanyId"
						headerKey="" headerValue="请选择物流" cssClass="logisticsCompany"/>
					<p class="j_logisticsCompany_error" style="display: none;">
					<i class="ui-icon ui-icon-error"></i><span class="logisticsCompany_error_msg" style="font-size:12px;" ></span>
					</p>
	</li>
	<li><label><span class="required">*</span>物流单号：</label>	
	<input class="logisticsNo" id="logisticsNoId"  style="width: 140px;" value="<s:property value='logisticsNo'/>" maxlength="30" />
					<p class="j_logisticsNo_error" style="display: none;">
						<i class="ui-icon ui-icon-error"></i><span class="logisticsNo_error_msg" style="font-size:12px;"></span>
					
					</p>
	</li>
	</ul>
</div>
</br>
</br>
<div class="modal-footer"></div>
<a data-dismiss="modal" class="btn btn-primary saveType"
			href="javascript:void(0);" id="save_editLogisticsInfo">保存 </a>
	<a data-dismiss="modal" class="btn btn-primary  j_sure_close"
			href="javascript:void(0);">关闭</a>
</fieldset>
</div>
</div>
--%>

<!-- 返回原件及配送信息 end-->
</div>
</div>
<div id="question" style="display:none"></div>
</div>
</div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>