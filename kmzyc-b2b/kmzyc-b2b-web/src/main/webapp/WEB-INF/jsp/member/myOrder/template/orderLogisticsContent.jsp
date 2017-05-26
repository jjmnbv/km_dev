<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>

<div class="l-right user-m">
	<div class="o-mt">
		<h2>订单跟踪动态</h2>
		<div class="OrderInfo">
		<input type="hidden" id="logisticsOrderNo"   value="<s:property value="orderMain.logisticsOrderNo" />" />
		<input type="hidden" id="logisticsCode" value="<s:property value="orderMain.logisticsCode" />" />
		<input type="hidden" id="expressComName" value="<s:property value="orderMain.logisticsName" />" />
		<input type="hidden" id="orderCode" value="<s:property value="orderMain.orderCode" />" />
		
		
			<ul class="Order-Number">
				<li>订单编号：<s:property value="orderMain.orderCode" /></li>
				<li>状态:
				    <s:if test="#request.orderMain.orderStatus == -1">已取消</s:if>
					<s:if test="#request.orderMain.orderStatus == 1">未付款</s:if>
					<s:if test="#request.orderMain.orderStatus == 2 || #request.orderMain.orderStatus == 20 || #request.orderMain.orderStatus == 21 || #request.orderMain.orderStatus == 22">已付款</s:if>
					<s:if test="#request.orderMain.orderStatus == 3 || #request.orderMain.orderStatus == 4|| #request.orderMain.orderStatus == 15|| #request.orderMain.orderStatus == 18">配货中</s:if>								
					<s:if test="#request.orderMain.orderStatus == 5">已发货</s:if> 
					<s:elseif test="#request.orderMain.orderStatus == 6">已完成</s:elseif> 
					<s:if test="#request.orderMain.orderStatus == 12">送货失败</s:if>
					<s:if test="#request.orderMain.orderStatus == 16">已拆分</s:if>
					<s:if test="#request.orderMain.orderStatus == -3">订单异常</s:if>
				</li>
				<p id="statusInfo">
					 <s:if test="listorder!=null && listorder.size >=1">
			            <strong>最新进度:</strong> <span>[<s:date name="listorder[listorder.size-1].nowOperateDate" format="yyyy-MM-dd HH:mm:ss"/>]
					    </span>
					 <s:property value="listorder[listorder.size-1].operateInfo" />	  
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
				<table class="ui-table user-table" id="orderLogisticsTable">
					<thead>
						<tr>
							<th class="td-s7">处理时间</th>
							<th class="td-s9">跟踪动态</th>
							<th>操作人</th>
						</tr>
					</thead>
					<tbody id="orderLogisticsTbody">
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
						<center id="error"  style="display:none;">快递信息查询失败，请稍后再试...</center>
					</tbody>
				</table>
				<div class="button">
					<div>
					<s:if test="backFlag==1">
					<a class="btn-submit"  href="gotoAssessProduct.action?orderCodes=<s:property value="orderMain.orderCode" />&isOrderList=<s:property  value="isOrderList" />&pagenumber=<s:property  value="pagenumber" />">
					</s:if>
					<s:elseif test="backFlag==2">
					<a class="btn-submit" href="/member/queryOrderDetail.action?orderMainCode=<s:property value="orderMain.orderCode" />">
					</s:elseif>	
					
						<span>返回</span>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--fn-right-->
</div>
<div>

 	<input type="hidden" id="orderMainId" value="<s:property value='orderMainId' />" />
 	<input type="hidden" id="orderStatusId" value="<s:property value='orderMain.orderStatus' />" />
 	<input type="hidden" id="backFlag" value="<s:property value='backFlag' />" />
 	<input type="hidden" id="orderNos" value="<s:property value='orderMain.orderCode' />" />
 	<input name="isOrderList" type="hidden" id="isOrderListId" value="<s:property  value="isOrderList" />" />
 	<input name="pagenumber" type="hidden" id="pagenumbers" value="<s:property  value="pagenumber" />" /> 
 	
</div>
