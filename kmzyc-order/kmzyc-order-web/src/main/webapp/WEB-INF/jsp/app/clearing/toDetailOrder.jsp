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
	<script>
		function exportDate(){
			var sno=$('#settlementNo').val();
			$.ajax({
	            async: false,
	            url: '/app/exportDetailOrder.action?sno=' + sno + '&toType=yun',
	            cache:false,
	            type:'POST',
	            success: function (data) {
		            try{
	            	var start=data.indexOf('\'path\':\'');
	            	var end=data.indexOf('.xls');
	            	var url=data.substring(start+8,end+4);
	            	$('#downLoadLink').attr('href',url).find('span').click();
		            }catch(e){
		            	alert('生成报表出错！');
			        }
	            	isSub=false;
	            },
	            error:function(){
					alert('生成报表出错！');
		        }
	        });
		}
	</script>
	</head>
	<body>
	<a href="#" id="downLoadLink" style="display: none;" target="_blank"><span id="downLoadSp" ></span></a>
	<s:set name="parent_name" value="'商家结算管理'" scope="request"/>
	<s:set name="name" value="'结算单管理'" scope="request"/>
	<s:set name="son_name" value="'结算单详情'" scope="request"/>
	<s:include value="/WEB-INF/jsp/public/title.jsp" />

		<div style="margin:10px">
			<button class="backBtn" id="return"></button>
		</div>

<div id="maind" style="width:90%;margin-left:5%;text-algin:center;">
<div id="content">
 <form action="toDetailOrder.action" method="Post" id="settlement_form">
	   <table  width="100%" style="font-size:15px">
	   <tr >
		   <td align="right" width="20%" >结算单号：</td>
		   <td><b>${info.settlementNo }</b>
		    <input type="hidden" id = "settlementNo"  value="${info.settlementNo }"/>
		   </td>
	   </tr>
	    <tr >
		   <td align="right">结算账期：</td>
		   <td><b>${info.settlementPeriod } </b>(${info.settlementPeriodExp})
		   		<input type="hidden" name="sellerSettlement.settlementPeriod" value="${info.settlementPeriod }">
		   		<input type="hidden" name="sellerSettlement.settlementId" value="${info.settlementId }">
		   		<input type="hidden" name="sellerSettlement.currSettleAccounts" value="${info.currSettleAccounts }">
		   		<input type="hidden" name="sellerSettlement.diffAdjSum" value="${info.diffAdjSum }">
		   </td>
	   </tr>
	    <tr >
		   <td align="right">商家/店铺：</td>
		   <td><b>${info.shopName != null ? info.shopName : info.sellerName }</b>
		   		<input type="hidden" name="sellerSettlement.sellerId" value="${info.sellerId }">
		   </td>
	   </tr>
	   <tr>
		   <td align="right">结算状态：</td>
		   <td><b>
		   <c:if test="${info.settlementStatus ==1  }"> 未确认</c:if>
		   <c:if test="${info.settlementStatus ==2  }"> 商家已确认</c:if>
		   <c:if test="${info.settlementStatus ==3  }"> 运营已确认</c:if>
		   <c:if test="${info.settlementStatus ==4  }"> 财务审核通过</c:if>
		   <c:if test="${info.settlementStatus ==5  }"> 财务审核拒绝</c:if>
		   <c:if test="${info.settlementStatus ==6  }"> 已结出</c:if></b>
		 </tr>
	   <tr>
	   		<td >&nbsp; </td>
		   <td>
			   <div    style="background-color: #888888;z-index:10;position:absolute" class="log_show">
			   
				   <c:if test="${not empty info.operates}">
				  		<div  class="one_log">${info.operates[0]}</div>
				  	 
				  		 <div class="hid_log"  style="display: none"> 
				  	 		<c:forEach var="data" items="${info.operates }" varStatus="id">
				  	 			<span  class="other_log">${data}</span><br>
				  	 		</c:forEach>
			  	 		
			  	 		 </div>	
				  	</c:if>
			  	
			   </div>
			   &nbsp; 
		   </td>
	   </tr>
	   <tr>
		   <td align="right">结算金额：</td>
		   <td align="left">
				   <table class="list_table" width="80%" align="left" cellpadding="3"
						cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc"
						style="border-collapse: collapse; font-size: 15px">
							   
				   <tr  >
				   	<th colspan="2">期内货款汇总  	</th>
				   	<th colspan="2">运费汇总</th>
				   	<th colspan="2">期内佣金汇总</th>
				   	<th colspan="2">期内推广服务费汇总</th>
				   	<th>差异调整</th>
				   	<th>本期应结金额</th>
				   	
				   </tr>
				     <tr >
				   	<td><a href="showHurlProductPage.action?sno=${info.settlementNo }&flag=1">妥投实收</a></td>
				   	<td><a href="showSettlementRefundPage.action?sno=${info.settlementNo }&flag=1">退款金额</a></td>
				   	<td><a href="showHurlFarePage.action?sno=${info.settlementNo }&flag=1">妥投运费</a></td>
				   	<td><a href="showSettlementRefundPage.action?sno=${info.settlementNo }&flag=1">退货返运费</a></td>
				   	<td><a href="showHurlProductPage.action?sno=${info.settlementNo }&flag=1">妥投佣金</a></td>
				   	<td><a href="showSettlementRefundPage.action?sno=${info.settlementNo }&flag=1">退款佣金</a></td>
				   	<td><a href="showHurlProductPage.action?sno=${info.settlementNo }&flag=1">妥投推广服务费</a></td>
				   	<td><a href="showSettlementRefundPage.action?sno=${info.settlementNo }&flag=1">退款返推广服务费</a></td>
				   	<td><a href="showDiffAdjPage.action?sno=${info.settlementNo }&flag=1&period=${info.settlementPeriod }">明细</a></td>
				   	<td></td>
				   </tr>
				     <tr onMouseOver="this.style.backgroundColor='#7BB5E2'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'">
				   	<td><c:if test="${info.receiveSum >=0}">+</c:if><fmt:formatNumber type="NUMBER" value="${info.receiveSum }" pattern="#0.00#" /></td>
				   	<td><c:if test="${info.refundSum >=0}">-</c:if><fmt:formatNumber type="NUMBER" value="${info.refundSum }" pattern="#0.00#" /></td>
				   	<td><fmt:formatNumber type="NUMBER" value="${info.fareSum }" pattern="+#0.00#" /></td>
				    <td><fmt:formatNumber type="NUMBER" value="${info.returnFareSum }" pattern="-#0.00#" /></td>
				   	<td><fmt:formatNumber type="NUMBER" value="${info.commissionSum }" pattern="-#0.00#" /></td>
				   	<td><fmt:formatNumber type="NUMBER" value="${info.refundComSum }" pattern="+#0.00#" /></td>
				   	<td><fmt:formatNumber type="NUMBER" value="${info.addpvsum }" pattern="-#0.00#" /></td>
				   	<td><fmt:formatNumber type="NUMBER" value="${info.returnpvsum }" pattern="+#0.00#" /></td>
				   	<td><c:if test="${info.diffAdjSum >=0}">+</c:if><fmt:formatNumber type="NUMBER" value="${info.diffAdjSum }" pattern="#0.00#" /></td>
				   	<td></td>
				   </tr>
				   
				    <tr bgcolor="#c8d9cf" >
				   	<td colspan="2"><c:if test="${info.receiveSum >= info.refundSum}">+</c:if><fmt:formatNumber type="NUMBER" value="${info.receiveSum - info.refundSum}" pattern="#0.00#" /></td>
				   	<td colspan="2"><c:if test="${info.fareSum >= info.returnFareSum}">+</c:if><fmt:formatNumber type="NUMBER" value="${info.fareSum - info.returnFareSum}" pattern="#0.00#" /></td>
				   	
				   	<td colspan="2">
				   	<c:if test="${info.commissionSum > info.refundComSum}">-
				   		<fmt:formatNumber type="NUMBER" value="${info.commissionSum -info.refundComSum }" pattern="#0.00#" />
				   	</c:if>
				   	<c:if test="${info.commissionSum <= info.refundComSum }">+
				   		<fmt:formatNumber type="NUMBER" value="${info.refundComSum - info.commissionSum }" pattern="#0.00#" />
				   	</c:if>
				   	</td>
				   	<td colspan="2"><fmt:formatNumber type="NUMBER" value="${info.returnpvsum - info.addpvsum  }" pattern="#0.00#" /></td>
				   	<td><c:if test="${info.diffAdjSum >=0}">+</c:if><fmt:formatNumber type="NUMBER" value="${info.diffAdjSum  }" pattern="#0.00#" /></td>
				   	<td><c:if test="${info.currSettleAccounts >0}">+</c:if><fmt:formatNumber type="NUMBER" value="${info.currSettleAccounts+info.diffAdjSum  }" pattern=" #0.00#" /></td>
				   </tr>
				   </table>
		   
		   
		   
		   
		   </td>
	   </tr>
	   <tr>
		   <td align="right">商家确认意见：</td>
		   <td>
		   <c:out value="${info.sellerConfirmation }" escapeXml="true"/>
		   </td>
	   </tr>
	   <tr>
		   <td align="right">运营确认意见：</td>
		   <td>
		   <%-- <c:if test="${(info.settlementStatus !=2 and  info.settlementStatus !=5) or (roleName ne 'yun' and roleName ne 'guan') }">
		   <c:out value="${info.operateConfirmation }" escapeXml="true"/>
		   </c:if> --%>
		   <input type="hidden" name="sellerSettlement.settlementNo" value="${info.settlementNo }">
		   <textarea style="width: 80%; height: 40px;" name="sellerSettlement.operateConfirmation" 
		   	 <c:if test="${info.settlementStatus !=2 and info.settlementStatus !=5}">disabled="disabled"</c:if>
		   ><c:out value="${info.operateConfirmation }" escapeXml="true"/></textarea>
		   </td>
		   <br>
	   </tr>
 		<tr>
		   <td align="right">财务审核意见：</td>
		   <td>
			   <textarea rows="0" cols="87" name="sellerSettlement.financialConfirmation"
			  	 <c:if test="${info.settlementStatus !=3 or (roleName ne 'cai' and roleName ne 'guan')}">disabled="disabled"</c:if>
			   ><c:out value="${info.financialConfirmation }" escapeXml="true"/></textarea>
		   <%-- <c:if test="${info.settlementStatus !=3 or (roleName ne 'cai' or roleName ne 'guan')}">
			   <c:out value="${info.financialConfirmation }" escapeXml="true"/>
		   </c:if> --%>
			   <br>
			  </td>
	   </tr>

	   
	   </table>
	   
	
  
		<div>
        	<h3><span>本账期发起差异调整明细 （</span><span>计</span><span>入下期结算）</span></h3> 
       <c:if test="${toType eq 'yun' }">  
        	<c:if test="${(info.settlementStatus ==2 || info.settlementStatus == 5) && (roleName eq 'yun' or roleName eq 'guan') }">
        		<input type="button" value="添加差异调整" id="addDiffAdj" name="${info.settlementNo }" d-name="${info.settlementPeriod }"> 	
        	</c:if>
        </c:if>
        	<p>
        </div>

		<c:set var="total_money" value="0" />
		<table class="list_table" width="98%" align="center" cellpadding="3"
			cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc"
			style="border-collapse: collapse; font-size: 15px">
			

			<tr align="center" bgcolor="#888888" height="30px"
				style="color: #ffffff">
				<th>调整项ID</th>
				<th>调整说明</th>
				<th>调整时间</th>
				<th>调整人</th>
				<th>结算状态</th>
				<th>调整金额</th>
				<th>删除</th>
			</tr>
			<c:if test="${not empty diffs}">

				<c:forEach var="item" items="${diffs }">
					<tr onMouseOver="this.style.backgroundColor='#7BB5E2'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'"> 
						<td>${item.diffAdjId }</td>
						<td><b>${item.adjTitle }</b><br> ${item.adjDetail }</td>
						<td><fmt:formatDate value="${item.adjTime }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${item.userName }</td>
						<td>
							<!--0 false  1 true  --> 
							<c:if test="${item.settmentStatus ==0 }">未结算</c:if> 
							<c:if test="${item.settmentStatus ==1 }">已计入<a href="toDetailOrder.action?sno=${item.calcSettmentNo}">[${item.calcSettmentPeriod}]</a>账期</c:if>
						</td>
						<td><fmt:formatNumber type="NUMBER" value="${item.adjMoney }"
								pattern="#0.00#" /> <c:set var="total_money"
								value="${total_money + item.adjMoney }" /></td>
						<td>
						<c:if test="${(info.settlementStatus ==2 ||  info.settlementStatus ==5) && item.isDelete == 0}">
							<%-- <a href="javascript:;" class="delete_item" name="${item.diffAdjId }">删除</a> --%>
							<%-- <button id="delete_item" class="delBtn"  name="${item.diffAdjId }"></button> --%>
							<img id="delete_item" alt="删除" title="删除" src="/etc/images/button_new/delete.png" onClick="delItem(${item.diffAdjId })" style="cursor: pointer;"/>
						</c:if>
						</td>
					</tr>
				</c:forEach>
			</c:if>

			<tr bgcolor="#c8d9cf">
				<td>合计调整</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>${total_money }</td>
				<td></td>
			</tr>
		</table>
		<div align="center"><input type = "button" class="btn-custom" value="导出" onClick="exportDate()"></div>
</form>
			<c:if
				test="${(info.settlementStatus ==2 or  info.settlementStatus ==5) and (roleName eq 'yun' or roleName eq 'guan') and toType eq 'yun'}">
				<input type="button" value="已完成核对调整，确认结算单" id="submit_save">
				<input type="button" value="先保存，暂不确认" id="save">
			</c:if>
			<c:if test="${info.settlementStatus ==3 and (roleName eq 'cai' or roleName eq 'guan') and toType eq 'cai'}">
				<input type="button" value="审核通过，并结出到商家余额" id="audit_success"> 
				<input type="button" value="审核拒绝，退回运营重新核对" id="audit_error"> 	
			</c:if>
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
/* 		    	var targetUrl = "";
		    	var toType = '${toType}';
		    	if(toType!=null && toType!="" && toType=="yun"){
		    		targetUrl = "/app/listClearingOrders.action?flag=1";
			    	window.location.href = targetUrl;
		    	}else if(toType!=null && toType!="" && toType=="cai"){
		    		targetUrl = "/app/finacialAuditList.action?flag=2";
			    	window.location.href = targetUrl;
		    	}else{ */
	    		window.history.go(-1);  
		    });
			
		    $('#submit_save').click(function(){
		    	if(!confirm("确认本期结算单吗？请确定您已经核对本期结算单，并已经完成所有差异调整。确认后将进入财务审核环节，请通知财务人员进行审核。"))return;
		    	$('#submit_save').prop('disabled', true);
		    	$('#save').prop('disabled', true);
		    	$("#settlement_form").attr("action","saveAndSubmitOperateConfirmation.action").submit();
		    });
		    
		    $('#save').click(function(){
			 //	alert("保存操作成功！结算单需经过运营确认后才进入下一环节")
			 	$('#save').prop('disabled', true);
		    	$("#settlement_form").attr("action","saveOperateConfirmation.action").submit();
		    });
			
	        $('#audit_success').click(function(){
		    	if(!confirm("确认审核通过吗？审核通过后应结金额将结出到商家账户余额中。同时本期结算单处理完毕。"))return;		    	
		    	 $('#audit_success').prop('disabled', true);
		    	 $('#audit_error').prop('disabled', true);
		    	$("#settlement_form").attr("action","saveAndSubmitFinancialConfirmation.action?audit=4").submit();
		    });
		    
		    $('#audit_error').click(function(){
		    	if(!confirm("确认审核拒绝吗？拒绝后结算单需重新经过运营核对确认后再行财务审核"))return;
		    	 $('#audit_error').prop('disabled', true);
		    	 $('#audit_success').prop('disabled', true);
		    	$("#settlement_form").attr("action","saveAndSubmitFinancialConfirmation.action?audit=5").submit();
		    });  
		    
		    
		    
			$(".log_show").mouseover(function(){
				
				$(".hid_log").show();
				$(".one_log").hide();
				
				
			});
			$(".log_show").mouseout(function(){
				$(".one_log").show();
				$(".hid_log").hide();
				
			}) ;
			
			/* $('#delete_item').bind("click",function(){
				if(!confirm("确认删除？"))return;
				var $td = $(this);
				var id = $td.attr("name");
		    }); */
			  $("#addDiffAdj").click(function() {
					var sno = $(this).attr("name");
					var period = $(this).attr("d-name");
				    $.blockUI({ message: ($('#question').empty().load("showAddDiffAdjPage.action",{sno:sno,period:period}))
				  	    , css: {position:'absolute',top:'20%' ,width: '550px' },overlayCSS: { cursor: 'default' }
				    });
				});
		});
	 
		function delItem(snoid){
			if(!confirm("确认删除？"))return;
			 $.post('deleteDiffAdj.action', {sno:snoid},
		           		function(result){
				            if(result.indexOf("error")>0){
				            	alert("操作出错");
				            	//history.go(0);
				            }else{
				            //	$td.parent().parent().remove();
				            	location.reload();
				            }
		     			}
	         );	
		}
	</script>
</html>