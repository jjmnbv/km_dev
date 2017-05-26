<%@page contentType="text/html;charset=UTF-8" import="com.pltfm.app.util.OrderRiskKey,java.util.Map,com.pltfm.app.entities.OrderRiskCondition"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@page import="java.math.BigDecimal"%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>风控设置 </title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript">
		function saveSetting(){
			if(confirm('确认风控设置无误？')){
				var setting='';
				var sumScore=0,score=0,max=0;
				var inputEorro=false;
				$('.settingProperties').each(function(){
					var obj=$(this);
					if('checkbox'==obj.attr('type')){
						setting+=this.id+(obj.is(':checked')?',1':',0')+($('#'+this.id+'_score').is(':checked')?',1':',0')+','+max+'@';
					}else{
						score=parseInt($('#'+this.id+'_score').val());
						if(score<0){
							inputEorro=true;
							return false;
						}
						max=parseInt($('#'+this.id+'_max').val());
						setting+=this.id+','+obj.val()+','+score+','+max+'@';
						sumScore+=score;
					}
				});
				if(parseInt($('.sumScore').val())>sumScore){
					alert('阀值大于风险分值总和！');
				}else{
					$.ajax({
						async: false,
						url: '/app/saveOrderRiskCondition.action?setting='+setting,
						cache:false,
						type:'post',
						success: function (data) {
							if(null!=data&&'null'!=data&&data.length>0){
								alert('保存成功！');
							}
							window.location.reload();
						},
						error:function(){
							window.location.reload();
						}
					});
				}
			}
		}
		</script>
	</head>
	<body>
		<s:set name="parent_name" value="'订单风控'" scope="request"/>
		<s:set name="name" value="'风控设置 '" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<%
			Map<String, OrderRiskCondition> map=(Map<String, OrderRiskCondition>)request.getAttribute("result");
			if(null!=map&&null!=map.get(OrderRiskKey.ORDER_RISK_THRESHOLD)){  
		%>
		<table class="table_search" id="setting" width="98%" align="center" cellpadding="10" cellspacing="10" >
			<tr>
		        <th align="right">风控阀值设置：</th>
				<td colspan="2">
					风险评分大于等于
					<input type="hidden" maxlength="4" id="<%=OrderRiskKey.ORDER_RISK_THRESHOLD %>_max" value="<%=map.get(OrderRiskKey.ORDER_RISK_THRESHOLD).getMax() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
					<input class="settingProperties sumScore" size="10" type="text" style="width: 40px;" style="width:80px;"
					 id="<%=OrderRiskKey.ORDER_RISK_THRESHOLD %>" value="<%=map.get(OrderRiskKey.ORDER_RISK_THRESHOLD).getCondition() %>" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" />
					分的订单须经过人工评估才可进入后续流程；
					<input type="hidden" id="<%=OrderRiskKey.ORDER_RISK_THRESHOLD %>_score" value="0">
				</td>
			</tr>
			<tr>
		        <th align="right">自动加黑名单：</th>
				<td colspan="2">
					人工评估为异常订单时，自动将
					<input type="hidden" maxlength="4" id="<%=OrderRiskKey.ORDER_RISK_AUTO_ADD_BLACK_LIST %>_max" value="<%=map.get(OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_MONEY).getMax() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
					<input class="settingProperties" <%if(BigDecimal.ONE.compareTo(map.get(OrderRiskKey.ORDER_RISK_AUTO_ADD_BLACK_LIST).getCondition())==0){ %>checked="checked"<%} %> type="checkbox" id="<%=OrderRiskKey.ORDER_RISK_AUTO_ADD_BLACK_LIST %>" value="<%=map.get(OrderRiskKey.ORDER_RISK_AUTO_ADD_BLACK_LIST).getCondition() %>"  />
					下单账号&nbsp;
					<input type="checkbox" <%if(1==map.get(OrderRiskKey.ORDER_RISK_AUTO_ADD_BLACK_LIST).getScore()){ %>checked="checked"<%} %> id="<%=OrderRiskKey.ORDER_RISK_AUTO_ADD_BLACK_LIST %>_score" value="<%=map.get(OrderRiskKey.ORDER_RISK_AUTO_ADD_BLACK_LIST).getScore() %>" />
					收货手机&nbsp;加入风控黑名单；</td>
			</tr>
			<tr style="background-color: #def2fa">
				<th colspan="3" style="color:#000;" align="left">风控条件设置</th>
			</tr>
			<tr>
		        <th align="center" style="color:#000; background-color: #def2fa;">判断维度</th>
		        <th align="center" style="color:#000; background-color: #def2fa;">风控条件</th>
		        <th align="center" style="color:#000; background-color: #def2fa;">风险分值</th>
			</tr>
			<tr>
		        <th align="right" rowspan="2" valign="top">订单维度：</th>
		        <td align="left">
		        	订单在线支付金额占总金额比重少于
		        	<input type="hidden" maxlength="4" id="<%=OrderRiskKey.ORDER_RISK_ORDER_PAY_RATE %>_max" value="<%=map.get(OrderRiskKey.ORDER_RISK_ORDER_PAY_RATE).getMax() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
		        	<input class="settingProperties" type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_ORDER_PAY_RATE %>" value="<%=map.get(OrderRiskKey.ORDER_RISK_ORDER_PAY_RATE).getCondition() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
					%的
				</td>
		        <td align="center">
					<input type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_ORDER_PAY_RATE %>_score" value="<%=map.get(OrderRiskKey.ORDER_RISK_ORDER_PAY_RATE).getScore() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
				</td>
			</tr>
			<tr>
		        <td align="left">
		        	订单金额超过
		        	<input type="hidden" maxlength="4" id="<%=OrderRiskKey.ORDER_RISK_ORDER_MONEY_MAX %>_max" value="<%=map.get(OrderRiskKey.ORDER_RISK_ORDER_MONEY_MAX).getMax() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
		        	<input class="settingProperties" type="text" style="width: 60px;" id="<%=OrderRiskKey.ORDER_RISK_ORDER_MONEY_MAX %>" value="<%=map.get(OrderRiskKey.ORDER_RISK_ORDER_MONEY_MAX).getCondition() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')"  />
		        	元 的
		        </td>
		        <td align="center">
					<input type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_ORDER_MONEY_MAX %>_score" value="<%=map.get(OrderRiskKey.ORDER_RISK_ORDER_MONEY_MAX).getScore() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
				</td>
			</tr>
			<tr>
				<td align="left"></td>
		        <td align="left">
		        	入驻商家订单PV积分占订单实收减去佣金的金额比重高于
		        	<input type="hidden" maxlength="4" id="<%=OrderRiskKey.ORDER_RISK_ORDER_PV_RATE %>_max" value="<%=map.get(OrderRiskKey.ORDER_RISK_ORDER_PV_RATE).getMax() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
		        	<input class="settingProperties" type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_ORDER_PV_RATE %>" value="<%=map.get(OrderRiskKey.ORDER_RISK_ORDER_PV_RATE).getCondition() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')"  />
		        	%的
		        </td>
		        <td align="center">
					<input type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_ORDER_PV_RATE %>_score" value="<%=map.get(OrderRiskKey.ORDER_RISK_ORDER_PV_RATE).getScore() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
				</td>
			</tr>
			<tr>
		        <th align="right" rowspan="3" valign="top">消费者维度：</th>
		        <td align="left">
		        	同一个收货手机前溯24小时（支付时间）内有效订单超过
		        	<input type="hidden" maxlength="4" id="<%=OrderRiskKey.ORDER_RISK_SAME_CONSIGNEE_MOBILE %>_max" value="<%=map.get(OrderRiskKey.ORDER_RISK_SAME_CONSIGNEE_MOBILE).getMax() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
					<input class="settingProperties" type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_SAME_CONSIGNEE_MOBILE %>" value="<%=map.get(OrderRiskKey.ORDER_RISK_SAME_CONSIGNEE_MOBILE).getCondition() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
					个的
		        </td>
		        <td align="center">
					<input type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_SAME_CONSIGNEE_MOBILE %>_score" value="<%=map.get(OrderRiskKey.ORDER_RISK_SAME_CONSIGNEE_MOBILE).getScore() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
				</td>
			</tr>
			<tr>
		       	<td align="left">
		       		同一个下单账号前溯24小时（支付时间）内有效订单超过
		       		<input type="hidden" maxlength="4" id="<%=OrderRiskKey.ORDER_RISK_SAME_CUSTOMER_ACCOUNT %>_max" value="<%=map.get(OrderRiskKey.ORDER_RISK_SAME_CUSTOMER_ACCOUNT).getMax() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
		       		<input class="settingProperties" type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_SAME_CUSTOMER_ACCOUNT %>" value="<%=map.get(OrderRiskKey.ORDER_RISK_SAME_CUSTOMER_ACCOUNT).getCondition() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
					个的
				</td>
		        <td align="center">
		        	<input type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_SAME_CUSTOMER_ACCOUNT %>_score" value="<%=map.get(OrderRiskKey.ORDER_RISK_SAME_CUSTOMER_ACCOUNT).getScore() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
				</td>
			</tr>
			<tr>
		       	<td align="left">
		       		<input type="hidden" maxlength="4" id="<%=OrderRiskKey.ORDER_RISK_SCM_RECENT_TIME %>_max" value="<%=map.get(OrderRiskKey.ORDER_RISK_SCM_RECENT_TIME).getMax() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
		       		同一收货手机上一有效订单支付时间间隔小于
		       		<input class="settingProperties" type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_SCM_RECENT_TIME %>" value="<%=map.get(OrderRiskKey.ORDER_RISK_SCM_RECENT_TIME).getCondition() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
					分钟 的
		       	</td>
		        <td align="center">
		        	<input type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_SCM_RECENT_TIME %>_score" value="<%=map.get(OrderRiskKey.ORDER_RISK_SCM_RECENT_TIME).getScore() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
				</td>
			</tr>
			<tr>
		        <th align="right" rowspan="2" valign="top">商家|店铺维度：</th>
		        <td align="left">
		        	同一商家当日有效订单数超过<input type="text" style="width: 40px;" maxlength="4" id="<%=OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_COUNT %>_max" value="<%=map.get(OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_COUNT).getMax() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />，
		        	且比上一自然日超过
		        	<input class="settingProperties" type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_COUNT %>" value="<%=map.get(OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_COUNT).getCondition() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
					%的（按支付时间）
		        </td>
		        <td align="center">
		        	<input type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_COUNT %>_score" value="<%=map.get(OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_COUNT).getScore() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')"  />
				</td>
			</tr>
			<tr>
		        <td align="left">
		        	同一商家当日有效订单金额超过<input type="text" style="width: 60px;" maxlength="10" id="<%=OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_MONEY %>_max" value="<%=map.get(OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_MONEY).getMax() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />，
		        	且比上一自然日超过
		        	<input class="settingProperties" type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_MONEY %>" value="<%=map.get(OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_MONEY).getCondition() %>" onKeyUp="this.value=this.value.replace(/[^\d]/g,'')" />
					%的
				</td>
		        <td align="center">
		        	<input type="text" style="width: 40px;" id="<%=OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_MONEY %>_score" value="<%=map.get(OrderRiskKey.ORDER_RISK_COMMERCE_YEST_ORDER_MONEY).getScore() %>"  onkeyup="this.value=this.value.replace(/[^\d]/g,'')" />
				</td>
			</tr>
			<tr>
		        <td align="center" colspan="3">
		        	<input type="button" id="btnsub" name="btnsub" class="btn-custom" value="提交" onClick="saveSetting();"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn-custom" id="back" name="back" value="重置" onClick="window.location.reload();" />
		        </td>
			</tr>
		</table>
        <br>
		<%}else{ %>
		<a href="/app/OrderRiskConditionInit.action">无风控条件设置，请点击初始化风控条件设置！</a>
		<%} %>
	</body>
</html>