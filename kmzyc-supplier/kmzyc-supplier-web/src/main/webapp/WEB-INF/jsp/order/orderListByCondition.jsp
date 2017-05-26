<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="订单列表"></jsp:param>
</jsp:include>
<title>订单列表</title>
</head>
<body>


<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>

<!-- 左侧内容区域开始 begin -->
<div class="container-fluid">
<div class="row-fluid">
<jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_order.jsp"></jsp:include>

<div class="content">
<!-- 表单开始begin -->	
<s:form action="/order/showOrderListByStatus.action" method="post" id="frm" name="frm">

<!-- 隐藏域 -->
<s:hidden name="titleValue" id="titleValue"/>
<s:hidden name="assess" id="access"/>
<s:hidden name="page" id="page" />

<div class="row-fluid"><!-- block -->
<div class="block_01">

	<!-- 面包屑begin -->
	<div class="navbar-inner">
	<ul class="breadcrumb">
		<i class="icon-home"></i>
        <li>订单 <span class="divider">/</span></li>
        <li>
        <%--<s:if test='"shouldFinalPay".equals(titleValue)'>
		待付尾款
		</s:if>--%>
        <s:if test='"shouldSetlle".equals(titleValue)'>
		待结转订单
		</s:if>
		<s:elseif test='"shouldShip".equals(titleValue)'>
		待发货订单
		</s:elseif>
		<s:elseif test='"alreadyShip".equals(titleValue)'>
		已发货订单
		</s:elseif>
		<s:elseif test='"shouldPay".equals(titleValue)'>
		待付款订单
		</s:elseif>
		<s:elseif test='"complete".equals(titleValue)'>
		已完成订单
		</s:elseif>
		<s:elseif test='"alreadyAccess".equals(titleValue)'>
		已评价订单
		</s:elseif>
		<s:elseif test='"canceled".equals(titleValue)'>
		已取消订单
		</s:elseif>
		<s:else>
		所有订单
		</s:else>
        </li>
	</ul>
	</div>
<!-- 面包屑end -->	
<div class="block-content collapse in"><!--开始-->

<!-- tab内容开始 -->
<ul class="nav nav-tabs" id="tabForOrder">
	<li data-src="/order/showOrderListByStatus.action" data-status=""
        <s:if test="titleValue == null">class="active"</s:if>>
        <a href="javascript:void(0);" data-toggle="tab" >所有订单</a>
    </li>
	<%--<li data-src="/order/showOrderListByStatus.action?orderStatusForMenuQuery=23&titleValue=shouldFinalPay"
	    data-status="23" data-title="shouldFinalPay">
        <a href="javascript:void(0);" data-toggle="tab" >待付尾款</a>
    </li>--%>
	<li data-src="/order/showOrderListByStatus.action?orderStatusForMenuQuery=1&titleValue=shouldPay"
        data-status="1" data-title="shouldPay"
        <s:if test="titleValue == 'shouldPay'">class="active"</s:if>>
        <a href="javascript:void(0);" data-toggle="tab" >待付款</a>
    </li>
	<li data-src="/order/showOrderListByStatus.action?orderStatusForMenuQuery=22&titleValue=shouldSetlle"
        data-status="22" data-title="shouldSetlle"
        <s:if test="titleValue == 'shouldSetlle'">class="active"</s:if>>
        <a href="javascript:void(0);" data-toggle="tab">待结转</a>
    </li>
	<li data-src="/order/showOrderListByStatus.action?orderStatusForMenuQuery=4&titleValue=shouldShip"
        data-status="4" data-title="shouldShip"
        <s:if test="titleValue == 'shouldShip'">class="active"</s:if>>
        <a href="javascript:void(0);" data-toggle="tab" >待发货</a>
    </li>
	<li data-src="/order/showOrderListByStatus.action?orderStatusForMenuQuery=5&titleValue=alreadyShip"
        data-status="5" data-title="alreadyShip"
        <s:if test="titleValue == 'alreadyShip'">class="active"</s:if>>
        <a href="javascript:void(0);" data-toggle="tab" >已发货</a>
    </li>
	<li data-src="/order/showOrderListByStatus.action?orderStatusForMenuQuery=6&titleValue=complete"
        data-status="6" data-title="complete"
        <s:if test="titleValue == 'complete'">class="active"</s:if>>
        <a href="javascript:void(0);" data-toggle="tab" >已完成</a>
    </li>
	<li data-src="/order/showOrderListByStatus.action?orderStatusForMenuQuery=-1&titleValue=canceled"
        data-status="-1" data-title="canceled"
        <s:if test="titleValue == 'canceled'">class="active"</s:if>>
        <a href="javascript:void(0);" data-toggle="tab" >已取消</a>
    </li>
	<li data-src="/order/showOrderListByStatus.action?orderStatusForMenuQuery=6&assess=true&titleValue=alreadyAccess"
        data-status="6" data-title="alreadyAccess" data-access="true"
        <s:if test="titleValue == 'alreadyAccess'">class="active"</s:if>>
        <a href="javascript:void(0);" data-toggle="tab" >已评价</a>
    </li>
</ul>

	<!--搜索表单开始-->
	<div class="com_topform">
	<ul>
		<li>
			<label>订单号：</label><s:textfield name="orderCodeForSearch" placeholder="" cssStyle="width:170px;"/>
		</li>
		<li>
			<%-- 为了控制显示顺序<s:select name="queryTypeForBuyer" list="#request.buyerQueryTypeMap" listKey="key" listValue="value"  headerValue="customerAccount" style="width:120px;"></s:select>--%>
			<select id="selectError"  class="width100" name="queryTypeForBuyer">
                <option value="customerAccount" <s:if test='queryTypeForBuyer=="customerAccount"'>selected='true'</s:if> >买家账号</option>
                <%--<option value="customerName" <s:if test='queryTypeForBuyer=="customerName"'>selected='true'</s:if>>买家姓名</option>
                <option value="customerMobile" <s:if test='queryTypeForBuyer=="customerMobile"'>selected='true'</s:if>>买家电话</option>--%>
                <option value="consigneeName" <s:if test='queryTypeForBuyer=="consigneeName"'>selected='true'</s:if>>收货人姓名</option>
                <option value="consigneeMobile" <s:if test='queryTypeForBuyer=="consigneeMobile"'>selected='true'</s:if>>收货人电话</option>
              </select>
		</li>
		<li>
			<s:textfield name="queryBuyerValue" placeholder=""/>
		</li>
		<s:if test='titleValue==null || "".equals(titleValue) || queryTypeForTime=="createDate" || queryTypeForTime=="finishDate" || "complete".equals(titleValue)'>
			<li>
				<select id="timeChoose"  class="width100" name="queryTypeForTime">
	                <option value="createDate" <s:if test='queryTypeForTime=="createDate"'>selected='true'</s:if> >下单时间</option>
	                <option value="finishDate" <s:if test='queryTypeForTime=="finishDate"'>selected='true'</s:if>>完成时间</option>
	            </select>		
			</li>		
		</s:if>		
		<s:if test='titleValue==null || "".equals(titleValue) || queryTypeForTime=="createDate" || queryTypeForTime=="finishDate" || "complete".equals(titleValue)'>
			<li id="createDateLi" <s:if test='queryTypeForTime=="finishDate"'>style='display:none;'</s:if> >
				<input type="text" placeholder="" maxlength="10" readonly="readonly" class="ui-input ui-form-date" name="orderBeginDate" style="width:180px;" value='<s:property value="orderBeginDate" />' id="orderBeginDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'orderEndDate\'),\'%y-%M-%d %H:%m:%s\'}'})" />
						&nbsp;至&nbsp;
				<input type="text" placeholder="" maxlength="10" readonly="readonly" class="ui-input ui-form-date" name="orderEndDate" style="width:180px;" value='<s:property value="orderEndDate" />' id="orderEndDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'orderBeginDate\')}'})" />	
			</li>
			<li id="finishDateLi" <s:if test='queryTypeForTime==null || queryTypeForTime=="createDate"'>style='display:none;'</s:if> >
					<input type="text" placeholder="" maxlength="10" readonly="readonly" class="ui-input ui-form-date" name="finishBeginDate" style="width:180px;"  value='<s:property value="finishBeginDate" />' id="finishBeginDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'finishEndDate\'),\'%y-%M-%d %H:%m:%s\'}'})" />
							&nbsp;至&nbsp;
					<input type="text" placeholder="" maxlength="10" readonly="readonly" class="ui-input ui-form-date" name="finishEndDate" style="width:180px;"  value='<s:property value="finishEndDate" />' id="finishEndDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'finishBeginDate\')}'})" />	
				</li>
		</s:if>
		<s:else>
		<li>
			<label>下单时间：</label>
			<input type="text" placeholder="" maxlength="10" readonly="readonly" class="ui-input ui-form-date" name="orderBeginDate" style="width:180px;" value='<s:property value="orderBeginDate" />' id="orderBeginDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'orderEndDate\'),\'%y-%M-%d %H:%m:%s\'}'})" />
					&nbsp;至&nbsp;
			<input type="text" placeholder="" maxlength="10" readonly="readonly" class="ui-input ui-form-date" name="orderEndDate" style="width:180px;" value='<s:property value="orderEndDate" />' id="orderEndDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'orderBeginDate\')}'})" />
		</li>
		</s:else>
		<li>
			<s:if test='"shouldSetlle".equals(titleValue)'>
				<input type="hidden" name="orderStatusForMenuQuery" id="orderStatusForMenuQuery" value="22"/>
			</s:if>
			<s:elseif test='"shouldShip".equals(titleValue)'>
				<input type="hidden" name="orderStatusForMenuQuery" id="orderStatusForMenuQuery" value="4"/>
			</s:elseif>
			<s:elseif test='"alreadyShip".equals(titleValue)'>
				<input type="hidden" name="orderStatusForMenuQuery" id="orderStatusForMenuQuery" value="5"/>
			</s:elseif>
			<s:elseif test='"shouldPay".equals(titleValue)'>
				<input type="hidden" name="orderStatusForMenuQuery" id="orderStatusForMenuQuery" value="1"/>
			</s:elseif>
			<s:elseif test='"complete".equals(titleValue)'>
			<input type="hidden" name="orderStatusForMenuQuery" id="orderStatusForMenuQuery" value="6"/>
			</s:elseif>
			<s:elseif test='"alreadyAccess".equals(titleValue)'>
				<input type="hidden" name="orderStatusForMenuQuery" id="orderStatusForMenuQuery" value="6"/>
			</s:elseif>
			<s:elseif test='"canceled".equals(titleValue)'>
				<input type="hidden" name="orderStatusForMenuQuery" id="orderStatusForMenuQuery" value="-1"/>
			</s:elseif>
			<%--<s:elseif test='"shouldFinalPay".equals(titleValue)'>
			<input type="hidden" name="orderStatusForMenuQuery" id="orderStatusForMenuQuery" value="23"/>
			</s:elseif>--%>
			<s:else>		
			<label>订单状态:</label>
				<s:select name="orderStatusForMenuQuery" list="#request.orderStatusMapForQuery" listKey="key"
					listValue="value" headerKey="" headerValue="全部"
					style="width:110px;" id="orderStatusForMenuQuery"></s:select>		
			</s:else>
		</li>	
		<li>
			<button class="btn btn-primary j_orderCondition_search"><i class="icon-search icon-white"></i> 搜索</button> &nbsp;&nbsp;&nbsp;
		</li>
		<!-- 待结转列表才出现 批量结转的按钮 -->
		<s:if test='"shouldSetlle".equals(titleValue)'>
		<li>
			<button class="btn btn-primary j_allOrderList_carryOver"><i class="icon-white"></i>批量结转</button>
		</li>
		</s:if>
		<li>
			<button class="btn btn-primary j_order_export">导出订单</button>
		</li>
	</ul>
	</div>
<!--搜索表单结束-->

<table cellpadding="0" cellspacing="0" border="0" class="table com_tablest">
	<tbody>
		<tr>
		  <td>商品</td>
		  <td class="width120">规格</td>
          <td class="width90">单价(元)</td>
          <td class="width90">数量</td>
		  <td class="width120">买家信息</td>
		  <td class="width120">订单金额(元)</td>
		  <td class="width80">订单状态</td>
		  <td class="width90">交易操作</td>
		</tr>
	</tbody>
</table>


<s:iterator value="pagintion.recordList">
	<table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered" >
		<thead>
			<tr class="tablesbg">
				<th colspan="5" class="textL"> 
					订单号：
					<s:if test="orderStatus==16">[主订单]</s:if>
					<s:if test="parentOrderCode!=null">[子订单]</s:if>
						<s:property value="orderCode" />&nbsp;&nbsp;&nbsp;
						下单时间：&nbsp;<s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" />
						<s:if test="finishDate!=null">&nbsp;&nbsp;&nbsp; 
							完成时间：&nbsp;<s:date name="finishDate" format="yyyy-MM-dd HH:mm:ss" />
						</s:if>
						<s:if test="orderStatus==23 && finallyPayTime!=null">&nbsp;&nbsp;&nbsp;
							<font color="red">尾款支付截止时间：&nbsp;
								<s:property value="finallyPayTime" />
								<s:date name="finallyPayTime" format="yyyy-MM-dd HH:mm:ss" />
							</font>
						</s:if>
				</th>
			</tr>
		</thead>
	<tbody>
		<tr>			
			<td>
			<s:if test="orderItemList!=null && orderItemList.size>3">
				<div class="orders3p">
			</s:if>
			<s:iterator value="orderItemList">
				<table width="100%" border="0" class="newform">
					<tbody>
					<tr>
						<td width="120px">
							<a target="_blank" href="<s:property value='cmsPagePath'/><s:property value='productSkuId'/>.shtml"
							   class="pull-left">
								<img class="thumbnail" src="<s:property value="imagePath"/><s:property value="imageUrl" />">
							</a>
						</td>
						<td class="textc">
							<a target="_blank" href="<s:property value='cmsPagePath'/><s:property value='productSkuId'/>.shtml"
							   title="<s:property value="commodityTitle" />"><s:property value="commodityTitle"/>
							</a>
						</td>
						<td width="120px">
							<s:if test="commoditySkuDescription!=null"><s:property value="commoditySkuDescription"/></s:if>
						</td>
						<td width="80px">
							<s:if test="orderType==7">
								<s:property value="amountPayable / commodityNumber"/>
							</s:if>
							<s:else>
								<s:property value="commodityUnitPrice"/>
							</s:else>
						</td>
						<td width="80px"><s:property value="commodityNumber"/></td>
					</tr>
					<s:if test="orderType==7">
						<tr>
							<td width="120px"></td>
							<td class="textc"></td>
							<td width="80px">定金</td>
							<td width="80px"><s:property value="depositSum / commodityNumber "/></td>
							<td width="80px"></td>
						</tr>
						<tr>
							<td width="120px">
							</td>
							<td class="textc">
							</td>
							<td width="80px">尾款</td>
							<td width="80px"><s:property value="(amountPayable - depositSum) / commodityNumber"/></td>
							<td width="80px"></td>
						</tr>
					</s:if>
					</tbody>
				</table>
			</s:iterator>
			<s:if test="orderItemList!=null && orderItemList.size>3">
				</div>
			</s:if>
			</td>		
				
			<td class="width120">
				<s:if test="orderType==7">
					<s:property value="customerAccount" />
					</br>
					(尾款支付通知:<s:property value="informPayTel" />)
				</s:if>
				<s:else>
					<s:property value="customerAccount" />
					</br><s:property value="consigneeName" />
					</br><s:property value="consigneeMobile" />
					<%--</br><s:property value="customerName" /></br>
					<s:property value="customerMobile" />	
					收货人姓名:<s:property value="consigneeName" /></br>
					收货人电话:<s:property value="consigneeMobile" /></br>--%>
				</s:else>
			</td>
			<td class="width120">
				<s:if test="orderType==7">
					<font color="red"><s:property value="amountPayable" /></font>
				</s:if>
				<s:else>
					<font><s:property value="amountPayable" /></font>
				</s:else>
				<br>
				<s:if test="fare>0">
				 (运费:<s:property value="fare"/>)
				</s:if>
				<s:else>
				(免运费)
				</s:else>
				<br/><s:property value="payMethodStr" />
				<s:if test="orderType==7">
					<div style="border-bottom:0px solid #cccccc;height: 46px;overflow:hidden"></div>
					<s:property value="depositSum" />
					<s:if test="orderStatus==1">(待支付)</s:if>
					<s:elseif test="orderStatus==-1">
						<s:if test="paidDeposit!=null">(已支付)</s:if>
						<s:else>(待支付)</s:else>
					</s:elseif>
					<s:else>(已支付)</s:else>
					<div style="border-bottom:0px solid #cccccc;height: 15px;overflow:hidden"></div>
					<s:property value="amountPayable - depositSum" />
					<s:if test="orderStatus==1 || orderStatus==23">(待支付)</s:if>
					<s:elseif test="orderStatus==-1">
						<s:if test="noFinalPayment!=null">(已支付)</s:if>
						<s:else>(待支付)</s:else>
					</s:elseif>
					<s:else>(已支付)</s:else>
				</s:if>
			</td>
			<td class="width80">
				<s:if test='orderStatusStr=="待风控评估"'>
					订单风控中
				</s:if>
				<s:elseif test='orderStatusStr=="风控通过"'>
					已锁库存
				</s:elseif>
				<s:else>
					<s:property value="orderStatusStr" />
				</s:else>
				<%--<br><a href="javascript:void(0);" class="j_view_order" data-orderCode="<s:property value="orderCode" />">订单详情</a>--%>
				<br>
				<s:if test="orderStatus==5 || orderStatus==6">
					<a href="javascript:void(0);" class="j_view_order" data-orderCode="<s:property value="orderCode" />" data-expressNo="<s:property value="ogisticsOrderNo"/>" data-logisticsCode="<s:property value="logisticsCode"/>" >查看物流</a>
				</s:if>
			</td>
			<td class="width90">			
			<%--<s:if test="orderStatus==2">
				<button data-orderCode="<s:property value="orderCode" />" class="btn btn-mini btn-success width66 j_carry_over">结&nbsp;&nbsp;转</button><br>
			</s:if>--%>
			<button data-orderCode="<s:property value="orderCode" />" class="btn btn-mini btn-success width66 j_view_order">查看详情</button><br>
			<s:if test="assessStatus==2">
				<button data-orderCode="<s:property value="orderCode" />" class="btn btn-mini btn-success width66 j_assess_order">查看评价</button><br>
			</s:if>
			<button data-orderCode="<s:property value="orderCode" />" data-remark="<s:property value="orderOperationRemark" />" class="btn btn-mini btn-success width66 j_remark_order">备&nbsp;&nbsp;注</button><br>
			</td>
		</tr>
	</tbody>
	</table>
</s:iterator>


<!--结束-->
</div>


<!-- 下分页组件 -->
<div class="fn-clear fn-mt10">
	<tiles:insertDefinition
	name="paginationBottom" />
</div>   


</div>
</div>
</div>



</div>
</div>
<!-- 左侧内容区域开始 end -->

</s:form>
<!-- 表单结束end -->



<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>

</body>
</html>