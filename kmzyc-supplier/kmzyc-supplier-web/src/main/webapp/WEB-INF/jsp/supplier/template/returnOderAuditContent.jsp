<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<form action="/supplier/gotoReturnNotesEdit.action" method="post" id="ckReturnFrm" name="ckReturnFrm" namespace="supplier" >
	<s:hidden name="orderCode"></s:hidden>
		<s:hidden name="order.returnId" id="returnId" ></s:hidden>
			<s:hidden name="order.custName" ></s:hidden>
			<s:hidden name="order.productNo" ></s:hidden>
			<s:hidden name="order.productId" ></s:hidden>
			<s:hidden name="order.productName" ></s:hidden>
			<s:hidden name="order.productSkuId" ></s:hidden>
			<s:hidden name="order.productSku" ></s:hidden>
			<s:hidden name="order.productCounts" ></s:hidden>
			<s:hidden name="order.orderdetailId" ></s:hidden>
			<s:hidden name="order.orderType" ></s:hidden>
			<s:hidden name="order.orderCode" id="orderCode" ></s:hidden>
			<s:hidden name="order.warehouseId" ></s:hidden>
			<s:hidden name="order.unitPrice" ></s:hidden>
			<s:hidden name="order.totalPrice" ></s:hidden>
			<s:hidden id="orderStatus" name="order.status" ></s:hidden>
			<s:hidden id="handleResult" name="order.handleResult" ></s:hidden>
			<s:hidden id="distributionId" name="distributionInfo.distributionId" ></s:hidden>
			<s:hidden id="stockOutId" name="stockOut.stockOutId" ></s:hidden>
			<input type="hidden" value="<s:property value='order.handleResult'/>" name="handleResult"></input>
		  <input type="hidden" value="<s:property value='order.returnId'/>" name="returnId" ></input>
		  <input type="hidden" name="alterCode"  value="<s:property value="orderAlter.orderAlterCode"/>" >
</form>
<form action="showAllOrderList.action" method="post" id="orderListFrm" name="orderListFrm" namespace="order" >
	<s:hidden name="nstatus"></s:hidden>
</form>

<input type="hidden" id="alterCodeId" value="<s:property value="alterCode"/>"></input>

<input type="hidden" id="preferentialAmount" value="<s:property value="orderAlter.preferentialAmount"/>"></input>
<div style="margin:10px">
	<input type="hidden" id="orderCode"  value="<s:property value="order.orderCode"/>" >
	<input class='ui-button ui-button-success' id="ckReturn" type='button' data-alterCode='<s:property value="alterCode"/>'  value='返回'>&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<div id="maind" style="width:90%;margin-left:5%;text-algin:center;">
	<ul id="tabs">
	
	</ul>		
<div id="content">
	<div class="ct">
		<form id="backForm">
  <div class="m-w w-noborder fn-clear fn-t10">
  	<div class="wh"><h3>申请明细：</h3></div>
      <div class="wc">
          <ul class="form-info ">
          	<li><div class="upload-photos-form "><span>服务类型：<font color="blue"><s:property value='orderAlter.alterTypeStr'/></font></span>&nbsp;&nbsp;&nbsp;&nbsp;<span>申请凭据：<font color="blue"><s:if test='orderAlter.evidence == 1'>有发票</s:if><s:else>无发票</s:else></font></span>&nbsp;&nbsp;&nbsp;<span>商品数量：<font color="blue"><s:property value='orderAlter.alterNum'/></font></span></div></li>
              <li><div class="upload-photos-form"><span>商品名称：<font color="blue"><a href='<s:property value="cmsPagePath"/><s:property value="item.productSkuId"/>.shtml' class="fn-blue" target="_blank">[<s:property value="item.commoditySku"/>]&nbsp;&nbsp;<s:property value="item.commodityName"/></a></font></span>&nbsp;&nbsp;&nbsp;<span>商品编号：<font color="blue"><s:property value='orderAlter.skuCode'/></font></span></div></li>
              <li class=""><label>问题描述：</label><div class="sh-name item"><textarea name="" cols="50" rows="30" style="widows: 600px;height: 50px;" class="Complaints-text" readonly="readonly"><s:property value='orderAlter.alterComment'/></textarea></div></li>
              <li>
              </li><li class=""><label>审核说明：</label><div class="sh-name item"><textarea name="comment" id="comment" cols="50" rows="30" style="widows: 600px;height: 50px;" class="Complaints-text"></textarea></div></li>
              <li>
              </li><li>&nbsp;</li>
              <label>上传图片：</label>
              <div class="sh-name">
                  <ul class="upload-photos">	
                   <s:iterator value="photoList" id="potoUrl">
                  	 	<li><a href='<s:property value='showPath'/><s:property value='#potoUrl.url'/>' target="_blank"><img style="height: 50px;widows: 50px;" src="<s:property value='showPath'/><s:property value='#potoUrl.url'/>"></a></li>
                  	 </s:iterator>
                  </ul>
              </div>
          </ul>
      </div>
  </div>
  <br>
  <br>
  <div class="m-w w-noborder fn-clear fn-t10">
  	<div class="wh"><h3>退换赔付信息：</h3><span style="color:red;margin-left:40px"><s:if test="isSuit">由于该订单包含活动商品，请手动计算！</s:if></span></div>
      <div class="wc">
          <ul class="form-info ">
          	  <li><span>
          	  赔付信息：
          	 
          	 <br>商品退款金额<font color="blue">￥<input class="cg" type="text" name="" id="returnMoney" value="<s:property value='orderAlter.ruturnMoney==null?0.00:orderAlter.ruturnMoney'/>"></font>元，
          	 <!--  <br>补&nbsp;偿&nbsp;运&nbsp;费&nbsp;&nbsp;&nbsp;&nbsp;<font color="blue">￥<input class="cg" type="text" name="" readonly="readonly" id="fareSubsidy" value="<s:property value='orderAlter.fareSubsidy==null?0.00:orderAlter.fareSubsidy'/>"></font>元， -->
          	 <br>共计<font color="blue">￥<span id="returnSum"><s:property value='orderAlter.ruturnSum==null?0.00:orderAlter.ruturnSum'/></span></font>元退款到余额及银行账户.
          	  </span></li>
          	    <s:if test="orderAlter.alterType==2">
             <s:if test="orderAlter.proposeStatus == 1">
               <li><span>退回快递信息：<font color="blue">尚未退货 </font></span></li>
              <li><span>换货快递信息：<font>尚未发货</font></span></li>
              </s:if>
              <s:else>
                <li><span>退回快递信息：<font color="blue"><s:property value='orderAlter.customerLogisticsName'/></font>&nbsp;&nbsp;运单号：<font color="blue"><s:property value='orderAlter.customerLogisticsNo'/></font></span></li>
              <li><span>换货快递信息：<font color="blue"><s:property value='orderAlter.logisticsName'/></font>&nbsp;&nbsp;运单号：<font color="blue"><s:property value='orderAlter.logisticsOrderNo'/></font></span></li>
              </s:else>
              <li><span>商家给买家换货快递信息：<font color="blue"><s:property value="orderAlter.province"/><s:property value="orderAlter.city"/><s:property value="orderAlter.area"/><s:property value='orderAlter.address'/></font></span></li>
              <li><span>买家收货人：<font color="blue"><s:property value='orderAlter.name'/></font></span></li>
              <li><span>买家电话：<font color="blue"><s:property value='orderAlter.phone'/></font></span></li>
          	</s:if>
              <li>
              <input class='ui-button ui-button-success ckPassOrVto' data-sta='1'  type='button'  value='通过'>&nbsp;&nbsp;&nbsp;&nbsp;
              <input class='ui-button ui-button-success ckPassOrVto'  data-sta='0' type='button'  value='驳回'>&nbsp;&nbsp;&nbsp;&nbsp;
			  </li> 
          </ul>
      </div>
  </div>
</form>
		
		
	</div>
	
	
	</div>
</div>
<!-- 
<div id="question" style="position:absolute;width:430px;height:600px;z-index:1000;display:none"></div>
 -->
<div id="question" style="display:none"></div>

