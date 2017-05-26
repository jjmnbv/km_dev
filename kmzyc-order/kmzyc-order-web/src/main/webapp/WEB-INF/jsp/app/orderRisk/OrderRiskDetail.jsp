<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>风险评估详情</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript">
		$(function(){
			var orderScore=[],customerScore=[],commerScore=[],roster=[],extVal=[];
			var sumScore=0,score=0;
			var scoreTd=null,resultTd=null,labelTd=null;
			var $html=null;
			var extHtml=null;
			var scIdx=0,sumCondition=0;
			$('#orsList tr').each(function(){
				html=$('<tr></tr>');
				var obj=$(this);
				scoreTd=obj.find('.score');
				resultTd=obj.find('.result');
				labelTd=obj.find('.label');
				extHtml=labelTd.html();
				if((scIdx=extHtml.indexOf('!@'))>0){
					sumCondition=parseInt(extHtml.substring(0,scIdx));
					$('#sumCondition').html(sumCondition);
					labelTd.html(extHtml.substring(scIdx+2,labelTd.html().length));
				}
				extHtml='';
				score=parseInt(scoreTd.attr('val'));
				if(score!=0){
					scoreTd.css({'color':'red'});
					resultTd.find('.ckeckVal').css({'color':'red'});
				}
				if(obj.attr('val')=='1'){
					if(orderScore.length==0){
						if($("#commerceId").val() != "" && $("#commerceId").val() != null){
							html.append('<th rowspan="3">订单维度：</td>');
						}else{
							html.append('<th rowspan="2">订单维度：</td>');
						}
					}
					html.append(labelTd).append(resultTd).append(scoreTd);
					orderScore.push(html);
				}else if(obj.attr('val')=='2'){
					if(customerScore.length==0){
						html.append('<th rowspan="3">消费者维度：</td>');
					}
					html.append(labelTd);
					extVal= resultTd.find('.extValue').html().split(',');
					extHtml=$('<td align="center"></td>');
					if(extVal.length==1&& null != extVal[0] && extVal[0]!=''){
						var time=parseInt(resultTd.find('.ckeckVal').html().replace(/,/g,''));
						var timeStr='';
						if(time>86400){
							timeStr+=parseInt(time/86400)+'日';
							time=time%86400;
							timeStr+=parseInt(time/3600)+'时';
							time=time%3600;
							timeStr+=parseInt(time/60)+'分'+(time%60)+'秒';
						}else{
							timeStr=parseInt(time/3600)+'时';
							time=time%3600;
							timeStr+=parseInt(time/60)+'分'+(time%60)+'秒';
						}
						extHtml.append(resultTd.find('.ckeckVal').html(timeStr)).append('&nbsp;').append('(<a href="/app/orderItemdetailAction.action?viewOnly=1&orderCode='+extVal[0]+'" target="_blank">查看明细>></a>)');
					}else if(extVal.length==4 && 'cm'==extVal[0]){
						if(checkCount(labelTd.html(),resultTd.find('.ckeckVal').html())){
							resultTd.find('.ckeckVal').css({'color':'red'});
						}
						extHtml.append(resultTd.find('.ckeckVal')).append('&nbsp;').append('(<a href="/app/orderlistByMapAction.action?map[\'consigneeMobile\']='+extVal[1]+'&map[\'payDateStart\']='+extVal[2]+'&map[\'payDateEnd\']='+extVal[3]+'" target="_blank">查看明细>></a>)');
					}else if(extVal.length==4 && 'cc'==extVal[0]){
						if(checkCount(labelTd.html(),resultTd.find('.ckeckVal').html())){
							resultTd.find('.ckeckVal').css({'color':'red'});
						}
						extHtml.append(resultTd.find('.ckeckVal')).append('&nbsp;').append('(<a href="/app/orderlistByMapAction.action?map[\'customerAccount\']='+extVal[1]+'&map[\'payDateStart\']='+extVal[2]+'&map[\'payDateEnd\']='+extVal[3]+'" target="_blank">查看明细>></a>)');
					}
					html.append(labelTd).append(extHtml).append(scoreTd);
					html.append(scoreTd);
					customerScore.push(html);
				}else if(obj.attr('val')=='3'){
					if(commerScore.length==0){
						html.append('<th rowspan="2">商家|店铺维度：</td>');
					}
					if(resultTd.find('.extValue').html().indexOf('/ 0')>0){
						resultTd.find('.ckeckVal').html('--');
					}
					html.append(labelTd).append(resultTd).append(scoreTd);
					commerScore.push(html);
				}else if(obj.attr('val')=='0'){
					html.append('<th>黑白名单维度：</td>').append(labelTd).append(resultTd).append(scoreTd);
					roster.push(html);
				}
				sumScore+=score;
			});

			function checkCount(label,checkVal){
				try{
					if(null!=label){
						var startIndex=label.indexOf('超过')+2;
						var endIndex=label.indexOf('个的');
						return parseInt(label.substring(startIndex,endIndex))<parseInt(checkVal);
					}
				}catch(e){}
				return false;
			}
			$.each(roster,function(idx,obj){
				$('#rsOrsList').append(obj);
			});
			$.each(orderScore,function(idx,obj){
				$('#rsOrsList').append(obj);
			});
			$.each(customerScore,function(idx,obj){
				$('#rsOrsList').append(obj);
			});
			$.each(commerScore,function(idx,obj){
				$('#rsOrsList').append(obj);
			});
			if(sumScore>=sumCondition){
				$('#sumScore').css({'color':'red'}).html(sumScore);
			}else{
				$('#sumScore').html(sumScore);
			}

			$('#checkSuccess').click(function(){
				if(confirm('风控通过的订单将进入后续处理流程。')){
					checkOrder(1);
				}
			});

			$('#checkException').click(function(){
				var estimateContent=$('#estimateContent').val();
				if(null==estimateContent || estimateContent.length==0){
					alert('订单评估不通过必须填写评估内容！');
				}else if(confirm('订单评估不通过将被标记为异常订单，异常订单将禁止结算和后续订单处理。')){
					checkOrder(0);
				}
			});

			$('#back').click(function(){
				history.go(-1);
			});
		});
		function checkOrder(result){
			$.ajax({
	            async: false,
	            url: '/app/orderRisk.action?t='+new Date().valueOf(),
	            cache:false,
	            type:'post',
	            data:{'orderCode':$('#orderCode').val(),'checkResult':result,'estimateContent':$('#estimateContent').val()},
	            success: function (data) {
	            	if(null!=data&&'null'!=data&&data.length>0){
						alert('订单风控评估成功，订单进入后续流程！');
						//window.location.href='/app/gotoOrderRiskList.action?map[%27status%27]=21';
						console.log("<s:property value='preSearchMapStr'/>");
						window.location.href="/app/gotoOrderRiskList.action?from=pre&mapStr="+"<s:property value='preSearchMapStr'/>"+
					    "&page.pageNo="+"<s:property value='prePageNo'/>"+"&page.pageSize="+"<s:property value='prePageSize'/>";
			        }
	            },
	            error:function(){
	            	window.location.reload();
		        }
	        });
		}
		</script>
	</head>
	<body>
		<s:set name="parent_name" value="'订单风控'" scope="request"/>
		<s:set name="name" value="'风险评估'" scope="request"/>
		<s:set name="son_name" value="'详情'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<table class="table_search" width="90%" align="center" cellpadding="0" cellspacing="0" >
			<tr>
				<th width="20%">订单号：</th>
				<td>
					<input id="orderCode" value="<s:property value="order.orderCode"/>" type="hidden" />
					<input id="commerceId" value="<s:property value="order.commerceId"/>" type="hidden" />
					<s:property value="order.orderCode"/>&nbsp;&nbsp;
					【<s:property value="order.orderStatusStr"/>】
					<a href="/app/orderItemdetailAction.action?orderCode=<s:property value='order.orderCode'/>">查看订单更多详情>></a>
				</td>
			</tr>
			<tr>
				<th>下单人：</th>
				<td><s:property value="order.customerAccount"/>(<s:property value="order.consigneeMobile"/>)</td>
			</tr>
			<tr>
				<th>收货信息：</th>
				<td>
					<s:property value="order.consigneeName"/>&nbsp;|
					<s:property value="order.consigneeMobile"/><br/>
					<s:property value="order.consigneeAddr"/>
				</td>
			</tr>
			<tr>
				<th>支付及配送：</th>
				<td>
					<s:property value="order.payMethodStr"/>&nbsp;|
					<s:property value="order.deliveryDateTypeStr"/>
				</td>
			</tr>
			<tr>
				<th>发票信息：</th>
				<td>
					<s:if test="order.invoiceInfoType!=null||order.invoiceInfoTitle!=null||order.invoiceInfoContent!=null">
					普通发票&nbsp;|<s:property value="order.invoiceInfoTitle"/>
					</s:if>
					<s:else>买家未要求开发票</s:else>
				</td>
			</tr>
			<tr>
				<th>订单备注：</th>
				<td>
					<s:property value="order.orderDescription"/>
				</td>
			</tr>
			<tr>
				<th>后台备注：</th>
				<td>
					<s:property value="order.orderOperationRemark"/>
				</td>
			</tr>
			<tr>
				<th>商品及金额：</th>
				<td>
					<table width="100%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
						<tr>
					    <th width="10%">商品SKU</th>
					    <th width="40%">商品名称</th>
					    <th width="6%">套餐</th>
					    <th width="8%">康美中药城价</th>
					    <th width="8%">佣金比例</th>
					    <th width="6%">PV值</th>
					    <th width="6%">数量</th>
					    <th width="8%">实收小计</th>
					    <th width="10%">药品/器械</th>
						</tr>
						<s:iterator id="orderItem" value="items">
						<tr>
					   	 	 <td><a href="<s:property value='cmsPagePath'/><s:property value='#orderItem.productSkuId'/>.shtml" class="fn-blue" target="_blank"><s:property value="#orderItem.commoditySku"/></a></td>
					   	 	 <td><s:property value="#orderItem.commodityTitle"/></td>
					   	 	 <td><s:property value="#orderItem.suitId"/></td>
					   	 	 <td><s:number name="#orderItem.commodityUnitPrice"/></td>
					   	 	 <td><s:number name="#orderItem.commissionRate*100"/>%</td>
					   	 	 <td><s:number name="#orderItem.commodityPv*#orderItem.commodityNumber"/></td>
					   	 	 <td><s:property value="#orderItem.commodityNumber"/></td>
					   	 	 <td><s:number name="#orderItem.commodityUnitIncoming*#orderItem.commodityNumber"/></td>
					   	 	 <td><s:property value="#orderItem.extAttrType==1?'是':'否'"/></td>
						</tr>
						</s:iterator>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					（商品总额）<s:property value="order.commoditySum"/>
					+（运费）<s:property value="order.fare"/>
					<s:if test="0!=plusDiscount">+（加价购） <s:property value="plusDiscount"/></s:if>
					<s:if test="0!=fullDdiscount">-（满减） <s:property value="fullDdiscount"/></s:if>
					<s:if test="0!=order.orderDiscount">-（打折） <s:property value="order.orderDiscount"/></s:if>
					<font>=（应付金额）<s:property value="actualpay"/></font><br/>
					<s:if test="0!=couponpay">（优惠券） <s:property value="couponpay"/><s:set name="plus" value="1"></s:set></s:if>
				   <%--  <s:if test="0!=reservepay"><s:if test="1==#plus">+</s:if>（预备金）<s:property value="reservepay"/><s:set name="plus" value="1"></s:set></s:if> --%>
				    <s:if test="0!=balancepay"><s:if test="1==#plus">+</s:if>（余额） <s:property value="balancepay"/><s:set name="plus" value="1"></s:set></s:if>
				    <s:if test="0!=bankpay"><s:if test="1==#plus">+</s:if>（网银/信用卡）<s:property value="bankpay"/><s:set name="plus" value="1"></s:set></s:if>
				    <s:if test="0!=platformpay"><s:if test="1==#plus">+</s:if>（在线）<s:property value="platformpay"/><s:set name="plus" value="1"></s:set></s:if>
				    <s:if test="0!=onlinepay"><s:if test="1==#plus">+</s:if>（在线）<s:property value="onlinepay"/><s:set name="plus" value="1"></s:set></s:if>
					<font>=（已付金额）<s:property value="orderpay"/></font>
					<s:if test="null==order.parentOrderCode">
					<br/>
					<font>（未付金额）<s:property value="notpay<0?0.00:notpay"/></font>
					</s:if>
				</td>
			</tr>										
		</table>
		<br/>
		<table id="orsList" style="display: none;">
			<s:iterator id="orderRiskScore" value="#request.orderRiskScoreList">
			<tr class="latitude" val="<s:number name="#orderRiskScore.latitude"/>">
				<td class="label"><s:property value="#orderRiskScore.label"/></td>
				<td class="result" align="center">
					<span class="ckeckVal"><s:number name="#orderRiskScore.checkValue"/></span>&nbsp;
					<span class="extValue"><s:property value="#orderRiskScore.extValue"/></span>
				</td>
				<td class="score" align="center" val="<s:number name="#orderRiskScore.score"/>"><s:number name="#orderRiskScore.score"/></td>
				<td></td>
			</tr>
			</s:iterator>
		</table>

		<table class="table_search" id="setting" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc">
			<tr>
		        <th colspan="4" style="color:white;background-color: black;">系统风控条件判断得分</th>
			</tr>
			<tr style="background-color:#def2fa;">
				<th width="20%" style="color:#fff">判断维度</th>
				<th width="50%" style="color:#fff">风险阀值</th>
				<th width="15%" style="color:#fff">检测值</th>
				<th width="10%" style="color:#fff">风险得分</th>
			</tr>
			<tbody id="rsOrsList"></tbody>
			<tr style="background-color:#def2fa;">
				<th>订单合计风险得分</th>
				<th colspan="2" align="left">风险评分大于等于<span id="sumCondition"></span> 分的订单须经过人工评估才可进入后续流程；</th>
				<th id="sumScore" style="font-weight:bold;"></th>
			</tr>
			<s:if test="21==order.orderStatus||22==order.orderStatus">
			<tr>
				<th>人工风险评估：</th>
				<th colspan="3" align="left">
					<textarea rows="3" cols="80" id="estimateContent" maxlength="100"><s:property value="order.estimateContent"/></textarea>
				</th>
			</tr>
			<tr>
				<td colspan="4" align="center">
	
					<s:if test="21==order.orderStatus"><input type="button" value="风险评估通过" id="checkSuccess" class="btn-custom"/>&nbsp;</s:if>
					<input type="button" value="评估不通过，标记为异常订单" id="checkException" class="btn-custom"/>&nbsp;
					<input type="button" value="返回" id="back" class="btn-custom"/>
				</td>
			</tr>
			</s:if>
			<s:else>
			<tr>
				<th>人工风险评估：</th>
				<th colspan="3">
					<s:property value="order.estimateContent"/><br/>
				</th>
			</tr>		
			<tr>
				<td colspan="4" align="center">
					<input type="button" value="返回" id="back"/>
				</td>
			</tr>	
			</s:else>
		</table>
        <br>
	</body>
</html>