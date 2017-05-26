<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<div class="fn-p20">
<div class="ui-breadcrumb">订单管理/订单信息管理/<span>历史结转列表</span>
</div>

<s:form action="orderCarryOverList.action" method="post" id="frm" name="frm"
	namespace="order">
	<div class="ui-well ui-well-form">
	<div class="ui-form ui-form-inline fn-minus-mr20"><s:hidden
		name="page" id="page" />
	<div class="ui-form-item">
	
	
	<label class="ui-form-label" for="">结转开始时间：
	</label> 
	<input type="text" id="orderBeginDate" maxlength="10" class="ui-input ui-form-date Wdate" name="orderBeginDate" value='<s:property value="orderBeginDate"/>' readonly="readonly" placeholder="请输入订单结转开始时间"
	onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',maxDate:'2100-03-10 20:59:30'})"/>
	&nbsp;结转结束时间:&nbsp; 
	<input type="text" id="orderEndDate" maxlength="10" class="ui-input ui-form-date Wdate" name="orderEndDate"  value='<s:property value="orderEndDate"/>' readonly="readonly" placeholder="请输入订单结转结束时间"
	onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',maxDate:'2100-03-10 20:59:30'})"/>

	</div>
	<div class="ui-form-item"><a href="javascript:void(0);"
		class="ui-button ui-button-success j_orderCarryOverList"><i
		class="ui-icon ui-icon-search"></i>查询</a></div>
	</div>
	</div>

	<table class="ui-table table-bordered fn-mt10">
		<thead>
			<th>结转时间</th>
			<th>结转成功的订单总数</th>
			<th>结转失败的订单总数</th>
			<th>查看结转生成文件操作</th>
		</thead>
		<tbody>
			<s:if test="null==orderCarryList">
				<tr>
					<td colspan="4" align="center">无结转订单！</td>
				</tr>
			</s:if>
			<s:iterator id="orderCarry" value="pagintion.recordList">
				<tr>
					<td><s:date name="#orderCarry.createDate"
						format="yyyy-MM-dd HH:mm:ss" /></td>
					<td><s:property value="#orderCarry.orderSum" /></td>
					<td><s:property value="#orderCarry.noOrderSum" /></td>
					<td><a class="anchors lookFile"
						title="<s:property value="#orderCarry.excelAddress.indexOf('B2B')"/>"
						href="javascript:void(0);" file-url="<s:property value='#orderCarry.excelAddress'/>">
					查看<s:if test="#orderCarry.excelAddress.indexOf('B2B')>0">康美中药城</s:if></a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<div class="fn-clear fn-mt10">
	<!-- 分页组件 --> <tiles:insertDefinition
		name="paginationBottom" />
	</div>
</s:form></div>
