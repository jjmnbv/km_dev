<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<div class="fn-p20">
	<div class="ui-breadcrumb">
		<span>订单管理/订单信息管理/康美中药城同步订单
	</div>
	
	<s:form action="showOrderListForKMB2B" method="post" id="frm" name="frm" namespace="order">
		<div class="ui-well ui-well-form">
			<div class="ui-form ui-form-inline fn-minus-mr20">
				<s:hidden name="page" id="page" />
				<div class="ui-form-item">
					<label class="ui-form-label" for="">订单号： </label> <s:textfield name="orderSyncCodeForSearch" placeholder="请输入订单号" cssClass="ui-input" cssStyle="width:150px;" />
				</div>
				<div class="ui-form-item">
					<label class="ui-form-label" for="">下单账号： </label> <s:textfield name="orderSyncAccount" placeholder="请输入下单账号" cssClass="ui-input"/>
				</div>
				<div class="ui-form-item">
					<label class="ui-form-label" for="">中药城编号： </label> <s:textfield name="outSyncCode" placeholder="请输入中药城编号" cssClass="ui-input"/>
				</div>
				<div class="ui-form-item"><label class="ui-form-label" for="">
					同步状态： </label> 
			   		<s:select name="syncFlag" list="#request.orderSyncFlagMap" listKey="key" listValue="value" headerKey="" headerValue="全部" cssClass="ui-form-select"></s:select>
				</div>
				<div class="ui-form-item">
					<a href="javascript:void(0);" class="ui-button ui-button-success j_allOrderListForKMB2B_search"><i class="ui-icon ui-icon-search"></i>搜索</a>
				</div>
				<div class="ui-form-item">
					<input class="ui-button ui-button-success ui-button-lg" id="sycCheckedOrder" value="同步已勾选的订单" />
				</div>
				<div class="ui-form-item">
					<input class="ui-button ui-button-success ui-button-lg" id="sycFailedOrder" value="同步所有失败订单" />
				</div>
			</div>
		</div>
	
		<div class="fn-clear fn-mb10">
		<!-- 分页组件 --> <tiles:insertDefinition
			name="pagination" />
		</div>
	
		<table class="ui-table table-bordered fn-mt10">
			<thead>
				<th class="col-w-10"><input type='checkbox' id='allbox' name='allbox'  class='j_list_allbox'></th>
				<th class="col-w-120">订单号</th>
				<th>下单账号</th>
				<th>中药城编号</th>
				<th>类型</th>
				<th>订单状态</th>
				<th class="col-w-55">订单金额</th>
				<th class="col-w-30">PV值</th>
				<th class="col-w-120">支付时间</th>
				<th>同步状态</th>
				<th class="col-w-120">同步时间</th>
				<th class="col-w-80">操作</th>
			</thead>
			<tbody>
				<s:iterator value="pagintion.recordList" id="shopMain">
					<tr>
						<td>
							<input type="checkbox" name="orderMainCodes" value='<s:property value="orderCode"/>'>
						</td>
						<td><s:if test="orderStatus==16">[主订单]</s:if><s:if test="parentOrderCode!=null">[子订单]</s:if><s:property value="orderCode" /></td>
						<td><s:property value="customerAccount" /></td>
						<td><s:property value="outCode" /></td>
						<td><s:property value="#request.orderTypegMap[orderType]" /></td>
						<td><s:property value="orderStatusStr" /></td>
						<td><s:property value="commoditySum" /></td>
						<td><s:property value="orderPv" /></td>
						<td><s:date name="payDate" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td><s:property value="#request.orderSyncFlagMap[syncFlag]" /></td>
						<td><s:date name="syncDate" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td>
							<img title="查看" style="cursor: pointer;"
								src="${staticUrl}${imageBaseUrl}/little_icon/chakan1.png"
								data-orderCode="<s:property value="orderCode" />" class="j_view_order"/> 
							<img title="同步订单" style="cursor: pointer;"
								src="${staticUrl}${imageBaseUrl}/little_icon/tijiaoshenhe1.png"
								data-orderCode="<s:property value="orderCode" />" class="j_tongbu_order"/> 
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<div class="fn-clear fn-mt10">
		<!-- 分页组件 --> <tiles:insertDefinition
			name="paginationBottom" />
		</div>
	</s:form>
</div>
