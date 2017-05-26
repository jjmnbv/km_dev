<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>取消订单</title>
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
    // 撤单
     $('.cancle_order').click(function(evt) {
	      	var orderCode = $(this).attr("data-orderCode");
	      	var orderStatus = $(this).attr("data-orderStatus");
	      	operate(
	      			'是否确定取消此订单?',
	      			'/app/cancelMyOrder.action',
	      			{orderCode:orderCode,orderStatus:orderStatus}
	      	);
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
			            	reload();
			            }
	     			}
	          );
  	  };
    }

   /*  $(document).on('click', '.cancle_order', function (e) {
        e.preventDefault();
        sureToCancl($(this).attr('data-orderCode'), $(this).attr('data-type'),$(this).attr('data-userId'));//取消订单
    }); */

    function reload(){
    	var orderCode = $("#hiddenCode").val();
    	 $("#queryMyOrderForm").attr("orderCode", "/app/cancelMyOrder.action");
     	$("#queryMyOrderForm").submit();
    } 
    /**function sureToCancl(orderMainCode, orderMainStatus ,userId) {
        $("#code").val(orderMainCode);
        $("#status").val(orderMainStatus);
        $("#userId").val(userId);
        var confirg = "<div class='i-remind'><div class='remind-state'><span class='fn-left ico-warn'></span><div class='remind-cont fn-block'><h3 class='state-03'>您确定要取消该订单吗？</h3><p></p></div></div><div class='remind-btns fn-clear'><a class='btn-m-green fn-r10 j_btn_m_green' ><span>确定</span></a><a  class='btn-m-grey j_btn_m_grey'><span>取消</span></a></div></div>";
        new Dialog({
            title: '确认取消订单',
            content: confirg,
            height: '180px',
            width: '425px'
        }).after('show',function () {
                var t = this;
                t.contentElement.find('.j_btn_m_green').click(function () {
                    t.hide();//关闭窗口
                    cancelOrderMain();//添加
                });
                t.contentElement.find('.j_btn_m_grey').click(function () {
                    t.hide();//关闭窗口
                });
            }).show();
    }

**/
    function sureToCancl(orderMainCode, orderMainStatus ,userId) {
	  $("#code").val(orderMainCode);
      $("#status").val(orderMainStatus);
     if(confirm("是否取消订单")){
    	cancelOrderMain();
      }else{
        return false;
      }

    }
    function cancelOrderMain() {
        //alert('取消');
        $("#sform").attr("action", "/app/cancelMyOrder.action");

       /* var currentSearchKeyword = $("input[name='searchKeyword.keyword']").val();
        //若搜索关键字为默认提示字符，则关键字查询条件为空
        if ($.trim(currentSearchKeyword) == KEYWORD_TIPS) {
            $("input[name='searchKeyword.keyword']").attr("value", "");
        }
        **/
        $("#sform").submit();
        // 撤单
    }
});
</script>
<title>取消订单</title>
</head>
<body>
<form action="" id="sform" name="sform">
<input name="orderMainCode" type="hidden" value="" id="code">
<input name="orderMainStatus" type="hidden" value="" id="status">
</form>
<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单管理'" scope="request"/>
<s:set name="son_name" value="'取消订单'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<input type="hidden" name="hiddenCode" value="<s:property value="order.orderCode"/>">
<form action="/app/queryMyOrder.action" method="post" id="queryMyOrderForm">
<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
       <tr>
		<th align="right">请输入订单号：</th>
        <td>
		<input class="condition" name="orderCode" type="text" value="<s:property value="order.orderCode"/>">
		<input type="submit" class="queryBtn" value=""/>
		</td>
		</tr>
</table>
</form>
    <s:if test="order!=null">
	<div style="margin:10px;padding-left: 70px">
			<s:if test="order.orderStatus==1||order.orderStatus==2||order.orderStatus==20||order.orderStatus==21||order.orderStatus==22||order.orderStatus==23">
			 <a href="javascript:void(0);" class="cancle_order" data-orderCode='<s:property value="order.orderCode"/>' data-orderStatus='<s:property value="order.orderStatus"/>' data-userId='<s:property value="order.customerId"/>' data-type="-1"><button>取消订单</button></a>
			</s:if>
			<s:elseif test="order.orderStatus==-1">
			<font style="color: red">订单已经取消！</font>
			</s:elseif>
			<s:else>
			<font style="color: red">订单已经结转，无法取消，请通过退换货流程处理！</font>
			</s:else>
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
		</ul>		
<div id="content">
			<div class="ct">
			<p><h4>订单号：<s:property value="order.orderCode"/>&nbsp;&nbsp;&nbsp;&nbsp;状态：<s:property value="order.orderStatusStr"/>&nbsp;&nbsp;&nbsp;&nbsp;下单账号:<s:property value="order.customerAccount"/></h4></p>
			<p><h4>收货人信息</h4>
			收货人：<s:property value="order.consigneeName"/><br/>
			地址：<s:property value="order.consigneeAddr"/><br/>
			固定电话：<s:property value="order.consigneeTel"/><br/>
			手机号码：<s:property value="order.consigneeMobile"/><br/>
			<%--删除邮件业务   电子邮件：<s:property value="order.email"/></p> --%>
			<h4>尾款支付通知手机:<s:property value="order.informPayTel"/></h4>
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
			   		 <th>商品SKU</th>
			   		 <th>商品名称</th>
			   		 <th>规格</th>
			   		 <th>供应商</th>
			   		 <th>康美中药城价</th>
			   		 <th>佣金比例</th>
			   		 <th>应结金额</th>
			   		 <th>商品数量</th>
			   		 <th>仓库编号</th>
			   		 <th>批次号</th>
			   		 <th>单品实收</th>
			   		 <th>药品/器械</th>
			   		 <th>操作</th>
				</tr>
				<s:iterator id="orderItem" value="items">
				<tr>
			   	 	 <td><a href="<s:property value='cmsPagePath'/><s:property value='#orderItem.productSkuId'/>.shtml" class="fn-blue" target="_blank"><s:property value="#orderItem.commoditySku"/></a></td>
			   	 	 <td><s:property value="#orderItem.commodityTitle"/></td>
			   	 	 <td><s:property value="#orderItem.commoditySkuDescription"/></td>
			   	 	 <td><s:property value="#orderItem.supplier"/></td>
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
			   	 	 <td><s:property value="#orderItem.settlementLoan"/></td>
			   	 	 <td><s:property value="#orderItem.commodityNumber"/></td>
			   	 	 <td><s:property value="%{formatDouble(#orderItem.warehouseId)}"/></td>
			   	 	 <td><s:property value="#orderItem.commodityBatchNumber"/></td>
			   	 	 <td><s:property value="#orderItem.commodityUnitIncoming"/></td>
			   	 	 <td><s:property value="#orderItem.extAttrType==1?'是':'否'"/></td>
			   		 <td>
			   		 <%-- s:if test="#order.isnotparentorder&&#order.orderStatus&ge;3&&#orderItem.isReturning==0"--%>
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
		</div>
	</div>
	<!-- 
	<div id="question" style="position:absolute;width:430px;height:600px;z-index:1000;display:none"></div>
	 -->
	 </s:if>
	<div id="question" style="display:none"></div>
	
</body>
</html>

