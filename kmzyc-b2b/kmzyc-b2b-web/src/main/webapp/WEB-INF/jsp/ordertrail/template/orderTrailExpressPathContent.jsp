<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="logo-box">
	<div class="l-w i-registration-logo">
		<a id="indexUrl"   hidefocus="true" href=""><img id="indexImg" width="303" height="77" src=""></a>
	</div>
</div>
<div id="registration-form">
	<div class="o-mt">
		<div class="OrderInfo">
			<ul class="Order-Number">
				<li>订单编号：<s:property value="#request.orderTrailInfo.orderNo" /></li>
				<li>状态:
					<s:if test="orderTrailInfo.orderStatus == -1">已取消</s:if>
					<s:if test="orderTrailInfo.orderStatus == 1">未付款</s:if> 
					<s:if test="orderTrailInfo.orderStatus == 2 || orderTrailInfo.orderStatus == 20 || orderTrailInfo.orderStatus == 21 || orderTrailInfo.orderStatus == 22">已付款</s:if>
					<s:if test="orderTrailInfo.orderStatus == 3 || orderTrailInfo.orderStatus == 4 || orderTrailInfo.orderStatus == 15 || orderTrailInfo.orderStatus == 18">配货中</s:if> 
					<s:if test="orderTrailInfo.orderStatus == 5">已发货</s:if> 
					<s:if test="orderTrailInfo.orderStatus == 6">已完成</s:if> 
					<s:if test="orderTrailInfo.orderStatus == 7">已评价</s:if> 
					<s:if test="orderTrailInfo.orderStatus == 12">送货失败</s:if>
					<s:if test="orderTrailInfo.orderStatus == 16">已拆分</s:if>
				</li>
				<p id="statusInfo">
					  <s:if test="listorder!=null">
			            <strong>最新进度:</strong> <span>[<s:date name="listorder[0].nowOperateDate" format="yyyy-MM-dd HH:mm:ss"/>]
					    </span>
					 <s:property value="listorder[0].operateInfo" />	  
				     </s:if>
				     <s:elseif test="expressPath.data!=null">
					     <strong>最新进度:</strong> <span>[<s:date name="expressPath.data[0].ftime" format="yyyy-MM-dd HH:mm:ss"/>]
					     </span>
						 <s:property value="expressPath.data[0].context" /> 
				 	</s:elseif>
					 <s:else>
					                                暂无进度
					 </s:else>
				</p>
			</ul>
		</div>
	</div>
	<div class="user-m fn-t10">
		<div class="mc">
			<div class="ui-table">
				<table class="ui-table user-table">
					<thead>
						<tr>
							<th class="td-s7">处理时间</th>
							<th class="td-s9">跟踪动态</th>
							<th>操作人</th>
						</tr>
					</thead>
					<tbody id="orderTrailTbody">
						 <s:if test="listorder!=null">
					    <s:iterator value="listorder" var="itemVar" status="itemStatus" >
							<tr>
								<td>
								<s:date name="#itemVar.nowOperateDate" format="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td class="fn-text-left">${itemVar.operateInfo}</td>
								<td>${itemVar.nowOperator}</td>
							</tr>
						</s:iterator>
					 </s:if>
					 <s:elseif test="expressPath.data!=null">
					      <s:iterator value="expressPath.data" var="itemVar" status="itemStatus" >
							<tr>
								<td>
								<s:date name="#itemVar.ftime" format="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td class="fn-text-left">${itemVar.context}</td>
								<td>${itemVar.opera}</td>
							</tr>
						</s:iterator>
					 </s:elseif>
					</tbody>
				</table>
				<div class="button">
					<div>
						<a href="queryTrailOrderDetail.action?backFlag=${backFlag}&orderMainId=${orderMainId}"
							class="btn-submit"><span>返回订单详情</span></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div>
 	<input type="hidden" id="orderMainId" value="<s:property value='orderMainId' />" />
 	<input type="hidden" id="orderStatusId" value="<s:property value='orderTrailInfo.orderStatus' />" />
</div>
