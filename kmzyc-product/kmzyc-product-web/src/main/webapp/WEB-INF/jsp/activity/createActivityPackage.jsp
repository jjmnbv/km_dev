<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动列表</title>
<script src="/etc/js/jquery-1.8.3.js"></script>


<script language='JavaScript' src='/etc/js/dialog-common.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>

<script src="/etc/js/activity/createActivityPackage.js"></script>

<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>

<style type="text/css">
body {
	padding: 0px;
	margin: 0px;
}

table {
	margin-left: 10px;
}
</style>
</head>
<body>
<s:set name="parent_name" value="'活动管理'" scope="request" />
<s:set name="name" value="'活动列表'" scope="request" />
<s:set name="son_name" value="'创建活动'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<table width="90%" class="table_search" align="center" cellpadding="0"
	cellspacing="0" style="border-collapse: collapse; font-size: 12px">
	<tr>
		<td>
			<input type="button" value="指定经营类目 " onclick="addDialog(0);" />
			&nbsp; 
			<input type="button" value="指定入驻商家" onclick="addDialog(1)" />
		</td>
	</tr>
</table>
<br />
</BODY>
</HTML>

