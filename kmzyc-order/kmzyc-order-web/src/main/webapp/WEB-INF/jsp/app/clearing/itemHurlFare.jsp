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
	<s:set name="son_name" value="'妥投订单运费明细'" scope="request"/>
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
				<li class="visit"><b><a href="showHurlFarePage.action?sno=${info.settlementNo }&flag=1">运费明细</a></b></li>
				<li ><b><a href="showSettlementRefundPage.action?sno=${info.settlementNo }&flag=1">退款明细</a></b></li>
				<li><b><a href="showDiffAdjPage.action?sno=${info.settlementNo }&flag=1&period=${info.settlementPeriod }">差异调整明细</a></b></li>
			</ul>	

	<div id="content">
	<form action="showHurlFarePage.action" method="POST">
		<table width="98%" class="table_search" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="right">订单编号：</th>
				<td><input value="<s:property value="hurlFareCriteria.orderCode" />" name="hurlFareCriteria.orderCode" type="text"></td>
				<th align="right">收货人：</th>
				<td><input value="<s:property value="hurlFareCriteria.consigneeName" />" name="hurlFareCriteria.consigneeName" type="text"></td>
				
				<th align="right">收货地址：</th>
				<td><input value="<s:property value="hurlFareCriteria.consigneeAddr" />" name="hurlFareCriteria.consigneeAddr" type="text"></td>
				
							
				<td align="center"><INPUT TYPE="submit" class="button-blue-1" value=" 查询 ">
					<INPUT TYPE="button" class="button-blue-1 outputFile" value=" 导出 ">
					</td>
			</tr>
			<tr>
				<th align="right">完成时间：</th>
				<td colspan="3"><input name="hurlFareCriteria.startDate" id="startDate"
					value='<fmt:formatDate value="${hurlFareCriteria.startDate}" pattern="yyyy-MM-dd HH:mm:ss" />' type="text"
					class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',})" /><strong>&nbsp;
					至&nbsp;</strong><input name="hurlFareCriteria.endDate" id="endDate"
					value='<fmt:formatDate value="${hurlFareCriteria.endDate}" pattern="yyyy-MM-dd HH:mm:ss" />' type="text"
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
				<th>收货人</th>
				<th>手机号码</th>
				<th>收货地址</th>
				<th>运费金额 </th>
				<th>订单完成时间</th>
			</tr>
			<c:if test="${not empty page.dataList}">
			
				<c:forEach var="item" items="${page.dataList }"> 
				
					<tr onMouseOver="this.style.backgroundColor='#7BB5E2'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'">
					<td align="center"><input type="checkbox" class="checkbox" onclick="cheOrUnAll(this)" value="${item.settlementFareId }" /></td>
					<td><a href="orderItemdetailAction.action?orderCode=${item.orderCode }">${item.orderCode }</a></td>
					<td>${item.consigneeName }</td>
					<td>${item.consigneeMobile }</td>
					<td>${item.consigneeAddr }</td>
					<td><fmt:formatNumber type="NUMBER" value="${item.fare }" pattern="###.##"></fmt:formatNumber></td>
					<td><fmt:formatDate value="${item.settlementTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
					
				</c:forEach>
			</c:if>
			
			<tr>
				<td colspan="5">合计 </td>
				<td><fmt:formatNumber type="NUMBER" value="${sums.sumFare }" pattern="#0.00#"/></td>
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
				
				 $.blockUI({ message: ($('#question').empty().load("outputHurlFareData.action",{sno:sno,period:period}))
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