<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>订单列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="/etc/js/urchin.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script src="/etc/js/97dater/WdatePicker.js"></script>
		<script>
			var isSub=false;
			function exportFile(){
				if(isSub){
					return;
				}
				isSub=true;
				var startDate=$('#startDate').val();
				var endDate=$('#endDate').val();
				var channel=$('#channel').val();
				var payMethodWay=$('#payMethodWay').val();
				$.ajax({
		            async: false,
		            url: '/app/saleReport.action',
		            cache:false,
		            type:'POST',
		            data: {"map['startDate']": startDate,"map['endDate']": endDate, "channel": channel,"payMethodWay":payMethodWay},
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
		<s:set name="parent_name" value="'业务操作'" scope="request" />
		<s:set name="name" value="'订单报表'" scope="request" />
		<s:set name="son_name" value="'销售统计表'" scope="request" />
		<s:include value="/WEB-INF/jsp/public/title.jsp" />
		<s:form action="saleReportsearchReportAction" method="post" name="SaleReportForm">
			<table width="98%" align="center" border="0" class="table_search">
				<tr>
					<td align="right">开始时间：</td>
					<td>
						<input class="Wdate condition" id="startDate" name="map['startDate']"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\'),\'%y-%M-%d %H:%m:%s\'}'})"
						type="text" value='<s:property value="map['startDate']"/>'>
					</td>
					<td align="right">结束时间：</td>
					<td>
						<input class="Wdate condition" id="endDate" name="map['endDate']"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'startDate\')}'})"
						type="text" value='<s:property value="map['endDate']"/>'></td>
                        					<td align="right">支付方式：</td>
					<td>
						<select id="payMethodWay" name="payMethodWay">
							<option value="-1">-----请选择-----</option>
							<s:iterator value="payMethodList" var="dict">
							<s:property value="#dict.orderDictionaryKey"/>
								<option value="<s:property value='#dict.orderDictionaryKey'/>" <s:if test='map["paymentWay"]==#dict.orderDictionaryKey'>selected="selected"</s:if> ><s:property value='#dict.orderDictionaryValue'/></option>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<%--删除渠道 <td align="right">渠道：</td>
					<td>
						<select id="channel" name="channel">
							<option value="-1">-----请选择-----</option>
							<s:iterator value="channelList" var="channel">
							<s:property value="#channel.orderDictionaryCode"/>
								<option value="<s:property value='#channel.orderDictionaryCode'/>" <s:if test='map["channel"]==#channel.orderDictionaryCode'>selected="selected"</s:if> ><s:property value='#channel.orderDictionaryValue'/></option>
							</s:iterator>
						</select>
					</td>  --%>	
                    					<td  align="center" colspan="4">
						<input type="reset" value=" 重置 " class="btn-custom"/>
						<input type="submit" class="queryBtn" value=""/>
						<input type="button" value="导出查询结果" class="btn-custom" onClick="exportFile()">
					</td>
				</tr>
			</table>
			<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse; font-size: 12px">
				<tr>
					<th>订单号</th>
					<!-- <th>渠道</th> -->
					<th>客户账号</th>
					<th>订单状态</th>
					<th>订单金额</th>
					<th>实付金额</th>
					<th>交易方式</th>
					<th>交易平台</th>
					<th>交易金额</th>
					<th>支付日期</th>
				</tr>
				<s:iterator value="page.dataList" id="map">
					<tr>
						<td>
							<s:property value="#map.ORDER_CODE"/>
						</td>
						<%-- <td>
							<s:property value="#map.ORDER_CHANNEL"/>
						</td> --%>
						<td>
							<s:property value="#map.LOGIN_ACCOUNT"/>
						</td>
						<td>
							<s:property value="#map.FLAG"/>
						</td>
						<td>
							<s:property value="#map.AMOUNT_PAYABLE"/>
						</td>
						<td>
							<s:property value="#map.ORDER_MONEY"/>
						</td>
						<td>
							<s:property value="#map.ORDER_DICTIONARY_VALUE"/>
						</td>
						<td>
							<s:property value="#map.PLATFORM_NAME"/>
						</td>						
						<td>
							<s:property value="#map.ACT_MONEY"/>
						</td>
						<td>
							<s:property value="#map.CREATE_DATE"/>
						</td>
					</tr>
				</s:iterator>
			</table>
			<br/>
			<table class="page_table" width="98%" align="center">
			   <tr>
			     <td align="right"><s:include  value="/WEB-INF/jsp/public/pager.jsp"/></td>
			   </tr>
			</table>
		</s:form>
	</body>
</html>