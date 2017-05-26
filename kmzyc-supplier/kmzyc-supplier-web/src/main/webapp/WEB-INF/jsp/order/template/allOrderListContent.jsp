<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<div class="fn-p20">
	<div class="ui-breadcrumb">
		<span>
			订单管理/订单信息管理/
			<s:if test=' nstatus != null && nstatus !="" '>有效订单</s:if>
			<s:elseif test='initStatus != null && initStatus == 6'>
				<s:if test='assess == "true"'>
					已评价订单
				</s:if>
				<s:else>
					已完成订单
				</s:else>
			</s:elseif>
			<s:else>所有订单</s:else>
		</span>
	</div>
	
	<s:form action="showAllOrderList" method="post" id="frm" name="frm" namespace="order">
		<div class="ui-well ui-well-form">
			<div class="ui-form ui-form-inline fn-minus-mr20">
				<s:hidden name="page" id="page" />
				<div class="ui-form-item">
					<label class="ui-form-label" for="">订单号： </label> <s:textfield name="orderCodeForSearch" placeholder="请输入订单号" cssClass="ui-input" cssStyle="width:150px;" />
				</div>
				<div class="ui-form-item">
					<label class="ui-form-label" for="">下单账号： </label> <s:textfield name="orderAccount" placeholder="请输入下单账号" cssClass="ui-input"/>
				</div>
				<div class="ui-form-item">
					<label class="ui-form-label" for="">收货人： </label> <s:textfield name="consignee" placeholder="请输入收货人" cssClass="ui-input"/>
				</div>
				<div class="ui-form-item">
					<label class="ui-form-label" for="">收货人电话： </label> <s:textfield name="consigneePhone" placeholder="请输入收货人电话" cssClass="ui-input"/>
				</div>
				<div class="ui-form-item">
					<label class="ui-form-label" for="">下单时间： </label>
					<input type="text" placeholder="请输入下单开始时间" maxlength="10" readonly="readonly" class="ui-input ui-form-date" name="orderBeginDate" value='<s:property value="orderBeginDate" />' id="orderBeginDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'orderEndDate\'),\'%y-%M-%d %H:%m:%s\'}'})" />
					&nbsp;至&nbsp;
					<input type="text" placeholder="请输入下单结束时间" maxlength="10" readonly="readonly" class="ui-input ui-form-date" name="orderEndDate" value='<s:property value="orderEndDate" />' id="orderEndDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'orderBeginDate\')}'})" />
					<!--
					<input type="text" placeholder="请输入下单开始时间" maxlength="10" class="ui-input ui-form-date" name="orderBeginDate" value='' id="orderBeginDate" />
					&nbsp;至&nbsp;
					<input type="text" placeholder="请输入下单结束时间" maxlength="10" class="ui-input ui-form-date" name="orderEndDate" value='' id="orderEndDate" />	
					-->	
				</div>
				<div class="ui-form-item">
					<label class="ui-form-label" for="">支付时间： </label>
					<input type="text" placeholder="请输入支付开始时间" maxlength="10" readonly="readonly" class="ui-input ui-form-date" name="payBeginDate" value='<s:property value="payBeginDate" />' id="payBeginDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'payEndDate\'),\'%y-%M-%d %H:%m:%s\'}'})" />
					&nbsp;至&nbsp;
					<input type="text" placeholder="请输入支付结束时间" maxlength="10" readonly="readonly" class="ui-input ui-form-date" name="payEndDate" value='<s:property value="payEndDate" />' id="payEndDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'payBeginDate\'),\'%y-%M-%d %H:%m:%s\'}'})" />
				</div>
				<br />
				<div class="ui-form-item">
					<label class="ui-form-label" for="">商品编号： </label> <s:textfield name="productCode" placeholder="请输入商品编号" cssClass="ui-input"/>
				</div>
				<div class="ui-form-item">
					<label class="ui-form-label" for="">商品名称： </label> <s:textfield name="productName" placeholder="请输入商品名称" cssClass="ui-input"/>
				</div>
				<div class="ui-form-item"><label class="ui-form-label" for="">
					订单状态： </label> 
					<s:if test=' nstatus != null && nstatus !="" '>
						<select class="ui-form-select" name="nstatus" id="nstatus" >
							<option value="1,-1,-2">有效订单</option>
						</select>
			   		</s:if>
			   		<s:elseif test='initStatus != null && initStatus == 6'>
			   			<select class="ui-form-select" name="initStatus">
							<option value="6">已完成订单</option>
						</select>
			   		</s:elseif>
			   		<s:else>
			   			<s:select name="orderStatus" list="orderStatusMap" listKey="key" listValue="value"  headerKey="" headerValue="全部" cssClass="ui-form-select"></s:select>
			   		</s:else>
				</div>
				<div class="ui-form-item">
					<a href="javascript:void(0);" class="ui-button ui-button-success j_allOrderList_search"><i class="ui-icon ui-icon-search"></i>搜索</a>
				</div>
				<div class="ui-form-item">
					<a href="javascript:void(0);" class="ui-button ui-button-success j_allOrderList_carryOver"><i class="ui-icon ui-icon-search pl-con-ad"></i>订单批量结转</a>
				</div>
			</div>
		</div>
	
		<div class="fn-clear fn-mb10">
		<!-- 分页组件 --> <tiles:insertDefinition
			name="pagination" />
		</div>
	
		<table class="ui-table table-bordered fn-mt10">
			<thead>
				<th class="col-w-180">订单号</th>
				<th>收货人</th>
				<th>订单状态</th>
				<th>订单金额</th>
				<th class="col-w-120">订单生成时间</th>
				<th class="col-w-180">配送方式</th>
				<th>支付方式</th>
				<!-- 
				<th>处理状态</th>
				 -->
				<th>操作</th>
			</thead>
			<tbody>
				<s:iterator value="pagintion.recordList" id="shopMain">
					<tr>
						<td><s:if test="orderStatus==16">[主订单]</s:if><s:if test="parentOrderCode!=null">[子订单]</s:if><s:property value="orderCode" /></td>
						<td><s:property value="consigneeName" /></td>
						<td><s:property value="orderStatusStr" /></td>
						<td><s:property value="amountPayable" /></td>
						<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td><s:property value="deliveryDateTypeStr" /></td>
						<td><s:property value="payMethodStr" /></td>
						<!-- 
						<td><s:property value="handleState==2?'已处理':'未处理'"/></td>
						 -->
						<td>
							<img title="查看" style="cursor: pointer;"
								src="${staticUrl}${imageBaseUrl}/little_icon/chakan1.png"
								data-orderCode="<s:property value="orderCode" />" class="j_view_order"/> 
							<img title="备注" style="cursor: pointer;"
								src="${staticUrl}${imageBaseUrl}/little_icon/peizhu1.png"
								data-orderCode="<s:property value="orderCode" />" data-remark="<s:property value="orderOperationRemark" />" class="j_remark_order"/>
							<s:if test='initStatus != null && initStatus == 6 && assess == "true"'>
								<img title="查看评价" style="cursor: pointer;"
								src="${staticUrl}${imageBaseUrl}/little_icon/yulan1.png"
								data-orderCode="<s:property value="orderCode" />" class="j_assess_order"/>
							</s:if> 
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
