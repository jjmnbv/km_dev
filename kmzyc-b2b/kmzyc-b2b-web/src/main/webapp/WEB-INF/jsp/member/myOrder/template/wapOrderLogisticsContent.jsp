<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>

	<input type="hidden" id="logisticsOrderNo"   value="<s:property value="orderMain.logisticsOrderNo" />" />
   <input type="hidden" id="logisticsCode" value="<s:property value="orderMain.logisticsCode" />" />
 <input type="hidden" id="expressComName" value="<s:property value="orderMain.logisticsName" />" />
   <input type="hidden" id="orderCode" value="<s:property value="orderMain.orderCode" />" />
<div class="container">
           <div class="list-box ">
                    <ul>
                        <li><span>订单编号：</span><span><s:property value="orderMain.orderCode" /></span></li>
                        <li><span>状态：</span>
                        <span class="text-success">
									<s:if test="#request.orderMain.orderStatus == -1">已取消</s:if>
									<s:if test="#request.orderMain.orderStatus == -3">异常订单</s:if>
									<s:if test="#request.orderMain.orderStatus == 1">未付款</s:if>
									<s:if test="#request.orderMain.orderStatus == 2 || #request.orderMain.orderStatus == 20|| #request.orderMain.orderStatus == 21|| #request.orderMain.orderStatus == 22">已付款</s:if>
									<s:if test="#request.orderMain.orderStatus == 3 || #request.orderMain.orderStatus == 4|| #request.orderMain.orderStatus == 15|| #request.orderMain.orderStatus == 18">配货中</s:if>								
									<s:if test="#request.orderMain.orderStatus == 5">已发货</s:if> 
									<s:elseif test="#request.orderMain.orderStatus == 6">已完成</s:elseif> 
									<s:if test="#request.orderMain.orderStatus == 12">送货失败</s:if>
									<s:if test="#request.orderMain.orderStatus == 16">已拆分</s:if>
						</span>
						</li>
                        <li  id="statusInfo">
                     <s:if test="listorder!=null && listorder.size >=1">
			             <span>最新动态：</span><span>
				             <s:iterator value="#request.orderAllStates" var="orderState" status="orderStates">
					             <s:if test="${orderStates.first}">
					             	[<s:date name="#orderState.NOWOPERATEDATE" format="yyyy-MM-dd HH:mm:ss"/>] 
					             	${orderState.OPERATEINFO}
					             </s:if>
		                     </s:iterator>
			             </span>
				     </s:if>
				     <s:elseif test="expressPath.data!=null">
					     <span>最新动态：</span><span>[<s:date name="expressPath.data[0].ftime" format="yyyy-MM-dd HH:mm:ss"/>] <s:property value="expressPath.data[0].context" /> </span>
				 	</s:elseif>
					 <s:else>
					                                暂无进度
					 </s:else>
                        </li>
                    </ul>
                </div>
      
</div>
<div class="container">
     <div class="ibox float-e-margins"> 
                <div class="list-box ">
                    <h3><span>处理时间</span><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;跟踪动态</span></h3>
                    <ul id="orderLogisticsTbody">
                          <s:if test="listorder!=null">
                          	   <s:iterator value="#request.orderAllStates" var="orderState" status="orderStates" >
		                           <li>
		                           	  <span class="col-sm-6"><s:date name="#orderState.NOWOPERATEDATE" format="yyyy-MM-dd HH:mm:ss"/></span>
		                              <span class="col-sm-6">${orderState.OPERATEINFO}</span>
		                           </li>
	                          </s:iterator>
                          </s:if>
                    </ul>
                </div>
            </div>
</div>
    <center id="error"  style="display:none;">快递信息查询失败，请稍后再试...</center>
   <div class="form-group"><a class="btn btn-success btn-block" href="javascript:history.go(-1);"> 
   <s:if test="backFlag==2">返回订单详情</s:if><s:elseif test="backFlag==3">返回订单列表</s:elseif></a></div>
  
   
   
   
    <input type="hidden" id="orderMainId" value="<s:property value='orderMainId' />" />
 	<input type="hidden" id="orderStatusId" value="<s:property value='orderTrailInfo.orderStatus' />" />
 	<input type="hidden" id="backFlag" value="<s:property value='backFlag' />" />
 	<input type="hidden" id="orderNos" value="<s:property value="orderTrailInfo.orderNo" />" />
 	<input name="isOrderList" type="hidden" id="isOrderListId" value="<s:property  value="isOrderList" />" />
 	<input name="pagenumber" type="hidden" id="pagenumbers" value="<s:property  value="pagenumber" />" /> 


  