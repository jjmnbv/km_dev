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
	<s:set name="son_name" value="'妥投商品明细'" scope="request"/>
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
				<li class="visit"><b><a href="showHurlProductPage.action?sno=${info.settlementNo }&flag=1">妥投商品明细</a></b></li>
				<li ><b><a href="showHurlFarePage.action?sno=${info.settlementNo }&flag=1">运费明细</a></b></li>
				<li ><b><a href="showSettlementRefundPage.action?sno=${info.settlementNo }&flag=1">退款明细</a></b></li>
				<li ><b><a href="showDiffAdjPage.action?sno=${info.settlementNo }&flag=1&period=${info.settlementPeriod }">差异调整明细</a></b></li>
			</ul>	

	<div id="content">
	<form action="showHurlProductPage.action" method="POST">
		<table width="98%" class="table_search" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="right">订单编号：</th>
				<td><input value="<s:property value="hurlProductCriteria.orderCode" />" name="hurlProductCriteria.orderCode" type="text" id="orderCode"></td>
				<th align="right">SKU编号：</th>
				<td><input value="<s:property value="hurlProductCriteria.skuNo" />" name="hurlProductCriteria.skuNo" type="text" id="skuNo"></td>
				
				<th align="right">商品标题：</th>
				<td><input value="<s:property value="hurlProductCriteria.productTile" />" name="hurlProductCriteria.productTile" type="text" id="productTile"></td>
				
							
				<td align="center"><INPUT TYPE="submit" class="button-blue-1" value=" 查询 ">
					<INPUT TYPE="button" class="button-blue-1 outputFile" value=" 导出 ">
					</td>
			</tr>
			<tr>
				<th align="right">完成时间：</th>
				<td colspan="3"><input name="hurlProductCriteria.startDate" id="startDate"
					value='<fmt:formatDate value="${hurlProductCriteria.startDate}" pattern="yyyy-MM-dd HH:mm:ss" />' type="text"
					class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',})" /><strong>&nbsp;
					至&nbsp;</strong><input name="hurlProductCriteria.endDate" id="endDate"
					value='<fmt:formatDate value="${hurlProductCriteria.endDate}" pattern="yyyy-MM-dd HH:mm:ss" />' type="text"
					class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',})" /></td>

			</tr>
		</table>
		<table class="list_table" width="98%" align="center" cellpadding="3"
			cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc"
			style="border-collapse: collapse; font-size: 12px">
			<tr>
				<th><s:checkbox name='allbox' onclick='checkAll(this)' id="ckall"/></th>
				<th>订单编号</th>
				<th>SKU编号</th>
				<th>商品标题</th>
				<th>数量</th>
				<th>实收小计</th>
				<th>佣金比例</th>
				<th>佣金</th>
				<th>推广服务费</th>
				<th>应结金额</th>
				<th>订单完成时间</th>
			</tr>
			<c:if test="${not empty page.dataList}">
			
				<c:forEach var="item" items="${page.dataList }"> 
				
					<tr onMouseOver="this.style.backgroundColor='#7BB5E2'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'">
					<td align="center"><input type="checkbox" class="checkbox" value='${item.settlementHurlId }' onclick="cheOrUnAll(this)"/></td>
					<td><a href="orderItemdetailAction.action?orderCode=${item.orderCode }">${item.orderCode }</a></td>
					<td>${item.skuNo }</td>
					<td><c:if test="${item.inSuit==1 }"><span style="color:red">【套餐内】</span></c:if>${item.productTile }</td>
					<td>${item.commodityNumber }</td>
					<td><fmt:formatNumber type="NUMBER" value="${item.receiveSub }" pattern="#0.00#"></fmt:formatNumber></td>
					<td><fmt:formatNumber type="percent" value="${item.commissionRate }"></fmt:formatNumber></td>
					<td><fmt:formatNumber type="NUMBER" value="${item.commission }" pattern="#0.00#"></fmt:formatNumber></td>
					<td><fmt:formatNumber type="NUMBER" value="${item.commodityPvSum }" pattern="#0.00#"></fmt:formatNumber></td>
					<td><fmt:formatNumber type="NUMBER" value="${item.settleAccounts }" pattern="#0.00#" /></td>
					<td><fmt:formatDate value="${item.settlementTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
					
				</c:forEach>
			</c:if>
			
			<tr>
				<td colspan="4">合计 </td>
				<td>${sums.sumNum }</td>
				<td><fmt:formatNumber type="NUMBER" value="${sums.sumReceive }" pattern="#0.00#" /></td>	
				<td></td>
				<td><fmt:formatNumber type="NUMBER" value="${sums.sumComm }" pattern="#0.00#" /></td>
				<td><fmt:formatNumber type="NUMBER" value="${sums.sumPv }" pattern="#0.00#" /></td>
				<td><fmt:formatNumber type="NUMBER" value="${sums.sumAccounts }" pattern="#0.00#" /></td>		
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
				    /* var ids = "";
				    $(":checkbox:checked.checkbox").each(function(){
				    	ids = ids + this.value + ","
				    });
				    if(ids.length>0){
				    	ids = ids.substring(0,ids.length-1);
				    } */
				    var sno = $("#settlementNo").val();
					var period = $("#settlementPeriod").val();
					
				    $.blockUI({ message: ($('#question').empty().load("outputHurlProductData.action",{sno:sno,period:period}))
				  	    , css: {position:'absolute',top:'20%' ,width: '300px',  }
				    });  
		    });
		   
		});
	 

	// 15.全选事件
		/*  function checkAll(ck) {
		 	var inputs = ck.form.getElementsByTagName("input");
		 	for ( var i = 0; i < inputs.length; i++) {
		 		var ele = inputs[i];
		 		if ((ele.type == "checkbox")) {
		 			if (ck.checked != ele.checked)
		 				ele.click();
		 		}
		 	}
		 } */
	
	/* function cheOrUnAll(ck){
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