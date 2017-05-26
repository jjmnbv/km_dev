<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>黑名单</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.validate.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/messages_cn.js" type="text/javascript"></Script>
</head>
<body>
<!-- 导航栏 -->
<!-- 导航栏 -->
<s:set name="parent_name" value="'账户管理'" scope="request"/>
<s:set name="name" value="'黑名单'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form id="accountInfoUpdate" action="saveBlackList.action" method="POST"  namespace='/accounts' >
<table  width="90%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<td width="20%" align="right">黑名单信息：</td>
		<td width="80%"> 
			<textarea rows="5" cols="20" name="blackListContent" id="blackListContent"><s:property value="blackListContent"/></textarea>
		</td>
	</tr>
</table>
<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="saveBtn" type="submit" value=" ">
            &nbsp;&nbsp;
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>
</s:form>
</div>

</BODY>
</HTML>