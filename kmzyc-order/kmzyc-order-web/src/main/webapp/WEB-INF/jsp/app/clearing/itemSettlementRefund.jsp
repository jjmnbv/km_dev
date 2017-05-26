<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>结算单详情</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		
		
		<link rel="stylesheet" type="text/css" href="/etc/css/orderTab.css">
		 <link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
	<!--	<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<link rel="stylesheet" href="/etc/css/demo.css" type="text/css">
		<link rel="stylesheet" href="/etc/css/zTreeStyle.css" type="text/css">  -->
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>

	</head>
	<body>


	
	<s:set name="parent_name" value="'商家结算管理 '" scope="request"/>
	<s:set name="name" value="'结算单管理'" scope="request"/>
	<s:set name="son_name" value="'退款明细'" scope="request"/>
	<s:include value="/WEB-INF/jsp/public/title.jsp" />
	<div style="margin:10px">
			<button class="backBtn" id="return"></button>
	</div>
		

<div>
	   
	   <table  width="100%" style="font-size:15px">
	   <tr >
		   <td align="right" width="20%" >结算单号：</td>
		   <td><b>${info.settlementNo }</b></td>
	   </tr>
	    <tr >
		   <td align="right">结算账期：</td>
		   <td><b>${info.settlementPeriod } </b>(${info.settlementPeriodExp})</td>
	   </tr>
	    <tr >
		   <td align="right">商家/店铺：</td>
		   <td><b>${info.shopName != null ? info.shopName : info.sellerName }</b></td>
	   </tr>

		</table>
		<input type="hidden" value="${info.settlementPeriod }" id="settlementPeriod">
		<input type="hidden" value="${info.settlementNo }" id="settlementNo">
</div>
 <div id="maind" style="width:90%;margin-left:5%;text-algin:center;">
		
			<ul id="tabs">
				<li ><b><a href="showHurlProductPage.action?sno=${info.settlementNo }&flag=1">妥投商品明细</a></b></li>
				<li ><b><a href="showHurlFarePage.action?sno=${info.settlementNo }&flag=1">运费明细</a></b></li>
				<li class="visit"><b><a href="showSettlementRefundPage.action?sno=${info.settlementNo }&flag=1">退款明细</a></b></li>
				<li ><b><a href="showDiffAdjPage.action?sno=${info.settlementNo }&flag=1&period=${info.settlementPeriod }">差异调整明细</a></b></li>
			</ul>	

	<div id="content">
	<form action="showSettlementRefundPage.action" method="POST">
		<table width="98%" class="table_search" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="right">订单编号：</th>
				<td><input value="<s:property value="settlementRefundCriteria.orderCode" />" name="settlementRefundCriteria.orderCode" type="text"></td>
				<th align="right">SKU编号：</th>
				<td><input value="<s:property value="settlementRefundCriteria.skuNo" />" name="settlementRefundCriteria.skuNo" type="text"></td>
				
				<th align="right">商品标题：</th>
				<td><input value="<s:property value="settlementRefundCriteria.productTitle" />" name="settlementRefundCriteria.productTitle" type="text"></td>
				
							
				<td align="center"><INPUT TYPE="submit" class="button-blue-1" value=" 查询 ">
					<INPUT TYPE="button" class="button-blue-1 outputFile" value=" 导出 ">
					</td>
			</tr>
					<tr>
				<th align="right">退换货单号：</th>
				<td><input value="<s:property value="settlementRefundCriteria.orderAlterCode" />" name="settlementRefundCriteria.orderAlterCode" type="text"></td>
				
				<th align="right">服务类型：</th>
				<td colspan="2">
					<select name="settlementRefundCriteria.serviceType">
						<option value="" >所有</option>
						<option value="1" <c:if test="${settlementRefundCriteria.serviceType == 1 }">selected="selected"</c:if>>退货</option>
						<option value="2" <c:if test="${settlementRefundCriteria.serviceType == 2 }">selected="selected"</c:if>>换货</option>
						<option value="3" <c:if test="${settlementRefundCriteria.serviceType == 3 }">selected="selected"</c:if>>不退货退款</option>
						<option value="4" <c:if test="${settlementRefundCriteria.serviceType == 4 }">selected="selected"</c:if>>超时未发货赔付</option>
					</select>
				
				</td>
			</tr>
			<tr>
				<th align="right">完成时间：</th>
				<td colspan="3"><input name="settlementRefundCriteria.startDate" id="startDate"
					value='<fmt:formatDate value="${settlementRefundCriteria.startDate}" pattern="yyyy-MM-dd HH:mm:ss" />' type="text"
					class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',})" /><strong>&nbsp;
					至&nbsp;</strong><input name="settlementRefundCriteria.endDate" id="endDate"
					value='<fmt:formatDate value="${settlementRefundCriteria.endDate}" pattern="yyyy-MM-dd HH:mm:ss" />' type="text"
					class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',})" /></td>
				
				
					
					

			</tr>
		</table>
		<table class="list_table" width="98%" align="center" cellpadding="3"
			cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc"
			style="border-collapse: collapse; font-size: 12px">
			<tr>
				<th><s:checkbox name='allbox' onclick='checkAll(this)' id="ckall"/></th>
				<th>退换货单号</th>
				<th>SKU编号</th>
				<th>商品标题</th>
				<th>服务类型</th>
				<th>数量</th>
				<th>退款金额 </th>
				<th>退货返运费</th>
				<th>佣金比例</th>
				<th>退款佣金</th>
				<th>退款返推广服务费</th>
				<th>退款完成时间</th>
			</tr>
			<c:set var="total_dealNumber" value="0" />
			<c:set var="total_refundMoney" value="0" />
			<c:set var="total_commission" value="0" />
			<c:set var="total_settleAccounts" value="0" />
			<c:if test="${not empty page.dataList}">
			
				<c:forEach var="item" items="${page.dataList }"> 
				
					<tr onMouseOver="this.style.backgroundColor='#7BB5E2'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'">
					<td align="center"><input type="checkbox" class="checkbox" value="${item.settlementRefundId }" onclick="cheOrUnAll(this)"/></td>
					<td>
						<c:choose>
							<c:when test="${item.serviceType == 4 }">
								<a href="orderBackdownshowBackdownCKAction.action?returnPage=2&alterCode=${item.orderAlterCode }&orderType=7&fromType=2">${item.orderAlterCode }</a>
							</c:when>
							<c:otherwise>
								<a href="orderBackdownshowBackdownCKAction.action?returnPage=2&alterCode=${item.orderAlterCode }">${item.orderAlterCode }</a>
							</c:otherwise>
						</c:choose>
					</td>
					<td>${item.skuNo }</td>
					<td>${item.productTitle }</td>
					<td>
						<c:if test="${item.serviceType == 1 }">退货</c:if>
						<c:if test="${item.serviceType == 2 }">换货</c:if>
						<c:if test="${item.serviceType == 3 }">不退货退款</c:if>
						<c:if test="${item.serviceType == 4 }">超时未发货赔付</c:if>
					</td>
					<td>${item.dealNumber }</td>
					<td><fmt:formatNumber type="NUMBER" value="${item.refundMoney }"  pattern="#0.00#"></fmt:formatNumber></td>
					<td><fmt:formatNumber type="NUMBER" value="${item.refundFare }" pattern="#0.00#"></fmt:formatNumber></td>
					<td><fmt:formatNumber type="percent" value="${item.commissionRate }"></fmt:formatNumber></td>
					<td><fmt:formatNumber type="NUMBER" value="${item.commission }" pattern="#0.00#"></fmt:formatNumber></td>
					<td><fmt:formatNumber type="NUMBER" value="${item.refundCommodityPvSum }" pattern="#0.00#"></fmt:formatNumber></td>
					<td><fmt:formatDate value="${item.settlementTime }" pattern="yyyy-MM-dd hh:mm:ss" /></td>
					
				</tr>
					
				</c:forEach>
			</c:if>
			
			<tr>
				<td colspan="5">合计 </td>
				<td>${sums.sumNum }</td>
				<td><fmt:formatNumber type="NUMBER" value="${sums.sumRefund }" pattern="#0.00#" /></td>	
				<td><fmt:formatNumber type="NUMBER" value="${sums.sumRefundFare }" pattern="#0.00#" /></td>	
				<td></td>
				<td><fmt:formatNumber type="NUMBER" value="${sums.sumComm }" pattern="#0.00#" /></td>
				<td><fmt:formatNumber type="NUMBER" value="${sums.sumRefundPv }" pattern="#0.00#" /></td>
				<!--  <td><fmt:formatNumber type="NUMBER" value="${sums.sumAccounts }" pattern="#0.00#" /></td>	-->
				<td></td>
			</tr>
			
		</table>
		<br />
		<!-- 分页按钮区 -->
		<table class="page_table" width="98%" align="center">
			<tr>
				<td align="right"><s:include
						value="/WEB-INF/jsp/public/pager.jsp" /></td>
			</tr>
		</table>
	</form>
	
	</div>			
</div>

<div id="question" style="display:none"></div>

</body>
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
<script type="text/javascript">
	 $(document).ready(function(){
			//返回
		    $('#return').click(function(){
		    	history.go(-1);
		    });
		    $('.outputFile').click(function(){
				        var sno = $("#settlementNo").val();
						var period = $("#settlementPeriod").val();
					
					    $.blockUI({ message: ($('#question').empty().load("outputSettlementRefundData.action",{sno:sno,period:period}))
					  	    , css: {position:'absolute',top:'20%' ,width: '300px',  }
					    });
				
		    });
		   
		});
	 
	// 15.全选事件
	 /* function checkAll(ck) {
	 	var inputs = ck.form.getElementsByTagName("input");
	 	for ( var i = 0; i < inputs.length; i++) {
	 		var ele = inputs[i];
	 		if ((ele.type == "checkbox")) {
	 			if (ck.checked != ele.checked)
	 				ele.click();
	 		}
	 	}
	 }

	function cheOrUnAll(ck){
		if(!ck.checked){
			$("#ckall").attr("checked",false);
		}else{
			if($(":checkbox:checked.checkbox").length==$(":checkbox.checkbox").length){
				$("#ckall").attr("checked",true);
			}
		}
	} */
	</script>
</html>