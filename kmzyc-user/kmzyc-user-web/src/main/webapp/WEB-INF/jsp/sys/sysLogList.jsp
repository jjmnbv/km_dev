<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.sys.model.SysLog"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<link href="/etc/js/dtree/dtree.css" type="text/css" rel="stylesheet">
<script language="JavaScript" type="text/javascript" src="/etc/js/dtree/dtree.js" ></script>
<Script language="JavaScript" type="text/javascript" src="/etc/js/Form.js"></Script>
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/jquery-latest.pack.js" type="text/javascript"></script>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.min.js"></script>
<script src="/etc/js/dialog.js"></script>
<style>
.tableStyle1{background:#def2fa;}
</style>


</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form action="/sys/gotoLogList.action" method="POST" namespace='/sys'>

<!-- 标题条 -->
<div class="pagetitle">日志查询:</div>

<!-- 查询条件区域 -->
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">日志日期&nbsp;>=&nbsp;</td>
		<td>
			 <input name="syslogTime" type="text" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value='<s:date   name="model.syslogTime"  format="yyyy-MM-dd"/>'/>
		</td>
		<td align="center">
			<input type="submit" class="queryBtn" value=" ">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="tableStyle1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#def2fa" style="">
	<tr>
		<th align="center" width="15%">操作时间</th>
		<th align="center" width="15%">操作人</th>
		<th align="center" width="10%">操作类型</th>
		<th align="center" width="10%">操作表</th>
	</tr>
	<s:iterator id="loglist" value="page.dataList">
	<% SysLog list_obj = (SysLog)request.getAttribute("loglist"); %>
	<tr>
	    <td align="center"><s:property value="syslogTime"/></td>
		<td align="center"><s:property value="userReal"/></td>
		<td align="center">
			<%
			  String syslogType = list_obj.getSyslogType();  //类型
			%>
			<%=StaticParams.getParamNameByCd("all","syslogType",syslogType) %>
		</td>		
		<td align="center"><s:property value="tableName"/></td>
	</tr>
	</s:iterator>
</table>

<!--底部分页工具条-->
<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td>
			<%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
		</td>
	</tr>
</table>

</s:form>
</BODY>
</HTML>