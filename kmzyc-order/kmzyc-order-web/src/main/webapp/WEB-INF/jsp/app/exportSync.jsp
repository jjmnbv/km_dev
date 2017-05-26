<%@ page contentType="application/msexcel;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
   response.setHeader("Content-disposition","inline; filename=data.xls");
%>
<html>
	<head>
		<title>订单同步数据</title>
	</head>
	<body>
  		<table width="100%" align="center"  border="1" >
			<tr>
	   			<th width="10%">订单号</th>
	   			<th width="10%">下单账号</th>
	   			<th width="10%">时代会员编号</th>
	   			<th width="10%">类型</th>
	   			<th width="8%">订单状态</th>
	   			<th width="8%">订单金额</th>
		   		<th width="10%">支付时间</th>
		   		<th width="8%">PV值</th>
		   	<%-- 	<th width="8%">收益值</th> --%>
		   		<th width="8%">同步状态</th>
		   		<th width="10%">同步时间</th>
		   		<th width="10%">完成时间</th>
		   		<th width="8%">实际同步pv值</th>
		   		<%-- <th width="8%">实际同步收益值</th> --%>
			</tr>
			<s:iterator id="order" value="#request.dataList">
			<tr>
	   		 	<td style="vnd.ms-excel.numberformat:@">
	   		 		<s:if test="#order.orderStatus==16">&nbsp;&nbsp;&nbsp;&nbsp;[主]</s:if><s:if test="#order.parentOrderCode!=null">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┗</s:if><s:property value="#order.orderCode"/>
	   		 		<s:if test="null!=#order.orderOperationRemark"><br/><font style="color:red;"><s:property value="#order.orderOperationRemark"/></font></s:if>	
	   		 	</td>
	   		 	<td><s:property value="#order.customerAccount"/></td>
	   		 	<td><s:property value="#order.outCode"/></td>
	   		 	<td>
	   		 		<s:if test="#order.orderType==1">普通商品订单</s:if>
			    	<s:elseif test="#order.orderType==2">抽奖商品订单</s:elseif>
			     	<s:elseif test="#order.orderType==3">时代二次购物</s:elseif>
			     	<s:elseif test="#order.orderType==4">时代注册</s:elseif>
			     	<s:elseif test="#order.orderType==5">时代升级</s:elseif>
				</td>	 
	   		 	<td><s:property value="#order.orderStatusStr"/></td>
	   		 	<td><s:property value="#order.commoditySum"/></td>
	   		 	<td><s:date name="#order.payDate" format="yyyy-MM-dd HH:mm:ss" /></td>
	   		 	<td><s:property value="#order.orderPv"/></td>
	   		 	<%-- <td><s:property value="#order.orderProfit"/></td> --%>
	   		 	<td>
	   		 		<s:if test="#order.syncFlag==1">成功</s:if>
			     	<s:elseif test="#order.syncFlag==0">失败</s:elseif>
			     	<s:else> 未同步</s:else>
			 	</td>
	   		 	<td><s:date name="#order.syncDate" format="yyyy-MM-dd HH:mm:ss" /></td>
	   		 	<td><s:date name="#order.finishDate" format="yyyy-MM-dd HH:mm:ss" /></td>
	   		 	<td><s:property value="#order.syncPv"/></td>
	   		 	<%-- <td><s:property value="#order.syncProfit"/></td> --%>
			</tr>
			</s:iterator>
		</table>
	</body>
</html>