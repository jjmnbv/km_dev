<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>CPS请求明细列表</title>
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
		<s:set name="son_name" value="'CPS请求明细列表查询'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<form id="orderListSearch" action="/app/cpsRequestList.action" method="POST">
		<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
			<tr>
				<th align="right">请求时间从：</th>
		        <td>
				    <input class="Wdate condition" id="createdateStart" name="map['createdateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'createdateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['createdateStart']"/>'>
				</td>
				<th align="right">请求时间到：</th>
		        <td>
					<input class="Wdate condition" id="createdateEnd" name="map['createdateEnd']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'createdateStart\')}'})" type="text" value='<s:property value="map['createdateEnd']"/>'>
				</td>
				<th align="right">访问系统：</th>
		        <td>
				    <input class="condition" name="map['systemCode']" type="text" value='<s:property value="map['systemCode']"/>'>
				</td>
			</tr>
			<tr>
				<th align="right">请求日期范围：</th>
		        <td>
				    <select class="condition" id="createdateRange" name="map['createdateRange']" style="width:156px">
				   		<option value="" <s:if test='map["createdateRange"]==""'>selected="selected"</s:if> >全部</option>
				   		<option value="today" <s:if test='map["createdateRange"]=="today"'>selected="selected"</s:if> >今天</option>
				   		<option value="ysday" <s:if test='map["createdateRange"]=="ysday"'>selected="selected"</s:if> >昨天</option>
				   		<option value="tsweek" <s:if test='map["createdateRange"]=="tsweek"'>selected="selected"</s:if> >本周</option>
				   		<option value="lsweek" <s:if test='map["createdateRange"]=="lsweek"'>selected="selected"</s:if> >上周</option>
				   		<option value="tsmonth" <s:if test='map["createdateRange"]=="tsmonth"'>selected="selected"</s:if> >本月</option>
				   		<option value="lsmonth" <s:if test='map["createdateRange"]=="lsmonth"'>selected="selected"</s:if> >上月</option>
				   </select>
				</td>
		        <th  align="right">请求接口：</th>
				<td>
				    <input class="condition" name="map['serviceName']" type="text" value='<s:property value="map['serviceName']"/>'>
				</td>
                				<td colspan="2" >
                                <input type="submit" class="queryBtn" value=""/>
					<input type="reset" value=" 重置 " class="btn-custom"/>
					<input type="button" value=" 清空 " id="clean" class="btn-custom"/>
					
		        </td>
			</tr>
		</table>
		<br/>
		<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
			<tr>
		   		<th>访问的系统</th>
		   		<th>请求接口</th>
		   		<th>请求来源</th>
		   		<th>请求IP</th>
		   		<th>请求用户ID</th>
		   		<th>请求参数</th>
		   		<th>请求状态</th>
		   		<th>请求时间</th>
		   		<th>请求结束时间</th>
			</tr>
			<s:iterator id="request" value="page.dataList">
			<tr>
		   		 <td><s:property value="#request.SYSTEMCODE"/></td>
		   		 <td><s:property value="#request.SERVICENAME"/></td>
		   		 <td><s:property value="#request.REQUESTSOURCE"/></td>
		   		 <td><s:property value="#request.REQUESTIP"/></td>
		   		 <td><s:property value="#request.CUID"/></td>
		   		 <td><div style="width:350px;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"><s:property value="#request.REQUESTPARAMS"/></div></td>
		   		 <td><s:property value="#request.REQUESTSTATUS"/></td>
		   		 <td><s:property value="#request.REQUESTSTARTDATE"/></td>
		   		 <td><s:property value="#request.REQUESTENDDATE" /></td>
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