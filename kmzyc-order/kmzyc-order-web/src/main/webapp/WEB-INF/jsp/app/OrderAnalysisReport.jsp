<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单分析表</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/etc/js/urchin.js"></script>
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<script src="/etc/js/97dater/WdatePicker.js"></script>
<script type="text/javascript">
var isSub=false;
function exportFile(){
	if(isSub){
		return;
	}
	isSub=true;
	var startDate=$('#startDate').val();
	var endDate=$('#endDate').val();
	var channel=$('#channel').val();
	$.ajax({
        async: false,
        url: '/app/orderAnalysisReport.action',
        cache:false,
        type:'POST',
        data: {"map['startDate']": startDate,"map['endDate']": endDate, 'channel': channel},
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
	<s:set name="son_name" value="'订单分析表 '" scope="request" />
	<s:include value="/WEB-INF/jsp/public/title.jsp" />
	<s:form action="orderAnalysisReportAction" method="post" name="SaleReportForm">
		<table width="98%" align="center" border="0" class="table_search">
			<tr>
				<td align="right">开始时间</td>
				<td><input class="Wdate condition"
					id="startDate" name="map['startDate']"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\'),\'%y-%M-%d %H:%m:%s\'}'})"
					type="text" value='<s:property value="map['startDate']"/>'>
				</td>
				<td align="right">结束时间</td>
				<td><input class="Wdate condition"
					id="endDate" name="map['endDate']"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'startDate\')}'})"
					type="text" value='<s:property value="map['endDate']"/>'></td>
			<%--删除渠道 	<td align="right">渠道</td>
				<td>
					<select name="channel">
						<option value="-1">-----请选择-----</option>
						<s:iterator value="channelList" var="channel">
						<s:property value="#channel.orderDictionaryCode"/>
							<option value="<s:property value='#channel.orderDictionaryCode'/>" <s:if test='map["channel"]==#channel.orderDictionaryCode'>selected="selected"</s:if> ><s:property value='#channel.orderDictionaryValue'/></option>
						</s:iterator>
					</select>
				</td>  --%>
                				<td align="right">
					<input type="reset" value=" 重置 " class="btn-custom"/>&nbsp;&nbsp;
					<input type="submit" class="queryBtn" value=""/>&nbsp;&nbsp;
					<input type="button" value="导出查询结果" class="btn-custom"onClick="exportFile()">&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		<table class="list_table" width="98%" align="center" cellpadding="3"
			cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc"
			style="border-collapse: collapse; font-size: 12px">
			<tr>
				<th>区域</th>
				<th>订单总数</th>
				<th>总金额</th>
				<th>单均金额</th>
				<th>商品总数</th>
				<th>单均商品</th>
				<th>单均运费</th>
				<th>退换单数</th>
			</tr>
			<s:iterator value="page.dataList" id="map">
				<tr>
					<td>
						<s:property value="#map.PROVINCE"/>
					</td>
					<td>
						<s:number name="#map.ORDERCOUNT"/>
					</td>
					<td>
						<s:property value="#map.TOTALAMOUNT"/>
					</td>
					<td>
						<s:property value="#map.AVGORDERAMOUNT"/>
					</td>
					<td>
						<s:number name="#map.PRODUCTCOUNT"/>
					</td>
					<td>
						<s:property value="#map.AVGPRODUCTORDER"/>
					</td>
					<td>
						<s:property value="#map.AVGORDERFARE"/>
					</td>
					<td>
						<s:number name="#map.VETO"/>
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
<script type="text/javascript">
	function exportQueryResult(){
		
	}
</script>
</html>