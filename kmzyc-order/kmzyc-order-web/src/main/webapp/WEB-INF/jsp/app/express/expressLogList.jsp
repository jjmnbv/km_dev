<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>同步日志</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript">
		function checkSubLogForm(){
			$("#pageNo").val('1');
			return true;
		}
		
		$(function(){
			//页面加载操作
			$("#createDateStart").attr("readonly",true);
			$("#createDateEnd").attr("readonly",true);
				  });
				//end
		</script>
	</head>
	<body>
		<s:set name="parent_name" value="'业务操作'" scope="request"/>
		<s:set name="name" value="'订单管理'" scope="request"/>
		<s:set name="son_name" value="'同步日志'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<div style="height:10px;"></div>
		<form id="logListSearch" action="expressLog_pageList.action" method="POST"  onsubmit="javascript:checkSubLogForm()">
		<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
			<tr>
				<td>
					订单号：<input class="condition" name="map['orderCode']" type="text" value='<s:property value="map['orderCode']"/>' onkeyup="this.value=this.value.replace(/[, ]/g,'')">
				</td>
		        <td>
		           	物流单号：<input class="condition" name="map['logisticsNo']" type="text" value='<s:property value="map['logisticsNo']"/>' onkeyup="this.value=this.value.replace(/[, ]/g,'')">
				</td>
				<td align="right"></td>
		        <td>
				    时间：<input class="Wdate condition" id="createDateStart" name="map['createDateStart']" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'createDateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['createDateStart']"/>'>
					至
					   <input class="Wdate condition" id="createDateEnd" name="map['createDateEnd']" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'createDateStart\')}'})" type="text" value='<s:property value="map['createDateEnd']"/>'>
				</td>
				<td align="right">
					同步节点：
					<select class="condition" id="node" name="map['node']" style="width:156px">
				   		<option value=""  <s:if test="map['node']==''">selected="selected"</s:if> >全部</option>
				   		<option value="1" <s:if test='map["node"]==1'>selected="selected"</s:if> >接收物流跟踪信息</option>
				   		<option value="2" <s:if test='map["node"]==2'>selected="selected"</s:if> >提交订阅</option>
				   		<option value="3" <s:if test='map["node"]==3'>selected="selected"</s:if> >接收物流单请求 </option>
				   </select>
				</td>
			</tr>
			<tr>		
		        <td>
				   状态 ：<select class="condition" id="status" name="map['status']" style="width:156px">
					   		<option value="" <s:if test="map['status']==''">selected="selected"</s:if> >全部</option>
					   		<option value="1" <s:if test="map['status']==1">selected="selected"</s:if> >成功</option>
					   		<option value="2" <s:if test="map['status']==2">selected="selected"</s:if> >失败</option>
				   		</select>
				</td>
				<td colspan="100" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" class="queryBtn" value=""/>&nbsp;&nbsp;
					<!-- <input type="button" value="更多选项 " id="moreButton" /> -->
		        </td>
			</tr>
		</table>
		<br/>
		<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
			<tr>
		   		<th width="15%">订单号</th>
		   		<th width="5%">物流公司</th>
		   		<th width="6%">物流单号</th>
		   		<th width="10%">同步节点</th>
		   		<th width="6%">时间</th>
		   		<th width="20%">状态</th>
			</tr>
			<s:iterator id="log" value="page.dataList">
			<tr>
		   		 <td><s:property value="#log.orderCode"/></td>
		   		 <td><s:property value="#log.logisticsName"/></td>
		   		 <td><s:property value="#log.logisticsNo"/></td>
		   		 <td><s:property value="#log.nodeName"/></td>
		   		 <td>
		   		 	<s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" />
		   		 </td>
		   		 <td>
		   		 <s:if test="#log.status==1">
		   		 	<s:property value="#log.statusName"/>
		   		 </s:if>
		   		 <s:if test="#log.status==2">
		   		 	<s:property value="#log.statusName"/>&nbsp;&nbsp;[<s:property value="#log.mark"/>]
		   		 </s:if>
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
		</form>
		<div id="question" style="display:none"></div>
	</body>
</html>