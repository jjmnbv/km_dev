<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>订单列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/autocompletestyles.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script  src="/etc/js/97dater/WdatePicker.js"></script>
		<script type="text/javascript">
		$(function(){
			//页面加载操作
			
				  });
				//end			  
		</script>
	</head>
	<body>
		<s:set name="parent_name" value="'订单系统'" scope="request"/>
		<s:set name="name" value="'已订阅列表'" scope="request"/>
		<s:set name="son_name" value="'详情'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<div style="height:10px;"></div>
		<table class="table_search" width="90%" align="center" cellpadding="0" cellspacing="0" >
			<tr>
				<td>						   
					订单号：<s:property value="expressSubscription.orderCode"/>
				</td>
		        <td>
		           	物流公司：<s:property value="expressSubscription.logisticsName"/>
				</td>
		        <td>
				       物流单号：<s:property value="expressSubscription.logisticsNo"/>
				</td>
			</tr>
		</table>
		<br/>
		<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
			<tr>
		   		<th width="50%">时间</th>
		   		<th width="50%">物流跟踪信息</th>
			</tr>
			
			<s:iterator id="track" value="expressSubscription.expressTrackList">
			<tr>
		   		 <td><s:date name="#track.trackDate" format="yyyy-MM-dd HH:mm:ss" /></td>
		   		 <td><s:property value="#track.trackMsg"/></td>
			</tr>
			</s:iterator>
		</table>
		<br/>
		<div id="question" style="display:none"></div>
	</body>
</html>