<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>CPS订单标识信息列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
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
		<script  src="/etc/js/97dater/WdatePicker.js"></script>
	</head>
	<body>
		<s:set name="parent_name" value="'业务操作'" scope="request"/>
		<s:set name="name" value="'CPS管理'" scope="request"/>
		<s:set name="son_name" value="'CPS订单标识信息列表查询'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<form id="orderListSearch" action="/app/cpsOrderFlagList.action" method="POST">
		<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
			<tr>
				<th align="right">创建时间从：</th>
		        <td>
				    <input class="Wdate condition" id="dateStart" name="map['dateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'dateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['dateStart']"/>'>
				</td>
				<th align="right">创建时间到：</th>
		        <td>
					<input class="Wdate condition" id="dateEnd" name="map['dateEnd']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'dateStart\')}'})" type="text" value='<s:property value="map['dateEnd']"/>'>
				</td>
				<th align="right">订单号：</th>
		        <td>
				    <input class="condition" name="map['orderCode']" type="text" value='<s:property value="map['orderCode']"/>'>
				</td>
			</tr>
			<tr>
				<th align="right">创建日期范围：</th>
		        <td>
				    <select class="condition" id="dateRange" name="map['dateRange']" style="width:156px">
				   		<option value="" <s:if test='map["dateRange"]==""'>selected="selected"</s:if> >全部</option>
				   		<option value="today" <s:if test='map["dateRange"]=="today"'>selected="selected"</s:if> >今天</option>
				   		<option value="ysday" <s:if test='map["dateRange"]=="ysday"'>selected="selected"</s:if> >昨天</option>
				   		<option value="tsweek" <s:if test='map["dateRange"]=="tsweek"'>selected="selected"</s:if> >本周</option>
				   		<option value="lsweek" <s:if test='map["dateRange"]=="lsweek"'>selected="selected"</s:if> >上周</option>
				   		<option value="tsmonth" <s:if test='map["dateRange"]=="tsmonth"'>selected="selected"</s:if> >本月</option>
				   		<option value="lsmonth" <s:if test='map["dateRange"]=="lsmonth"'>selected="selected"</s:if> >上月</option>
				   </select>
				</td>
		        <th>活动ID:</th>
				<td>
				    <input class="condition" name="map['campaignid']" type="text" value='<s:property value="map['campaignid']"/>'>
				</td>
		        <th>数据来源:</th>
				<td>
				    <input class="condition" name="map['source']" type="text" value='<s:property value="map['source']"/>'>
                    					<input type="submit" class="queryBtn" value=""/>
                    					<input type="reset" value=" 重置 " class="btn-custom"/>
					<input type="button" value=" 清空 " id="clean" class="btn-custom"/>

				</td>
			</tr>
		</table>
		<br/>
		<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
			<tr>
		   		<th>订单编号</th>
		   		<th>活动ID</th>
		   		<th>反馈标签</th>
		   		<th>来源</th>
		   		<th>渠道</th>
		   		<th>佣金类型</th>
		   		<th>创建时间</th>
			</tr>
			<s:iterator id="outSide" value="page.dataList">
			<tr>
		   		 <td><a href="/app/orderItemdetailAction.action?orderCode=<s:property value='#outSide.ORDERCODE'/>&map['byStatus']=<s:property value='map["byStatus"]'/>"><s:property value="#outSide.ORDERCODE"/></a></td>
		   		 <td><s:property value="#outSide.CAMPAIGNID"/></td>
		   		 <td><s:property value="#outSide.FEEDBACK"/></td>
		   		 <td><s:property value="#outSide.SOURCE"/></td>
		   		 <td><s:property value="#outSide.CHANNEL"/></td>
		   		 <td><s:property value="#outSide.COMMISSIONTYPE"/></td>
		   		 <td><s:property value="#outSide.CREATEDATE"/></td>
			</tr>
			</s:iterator>
		</table>
		<br/>
		<table class="page_table" width="98%" align="center">
		   <tr>
		     <td align="right"><s:include  value="/WEB-INF/jsp/public/pager.jsp"/></td>
		   </tr>
		</table>
		</form>
		<div id="question" style="display:none"></div>
	</body>
</html>