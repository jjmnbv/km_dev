<%@page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息窗口</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js"
	type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="/etc/js/warehouse/stockOut.js"></script>

</head>
<body>


<br/><br/><br/><br/><br/><br/>

<table width="50%" align="center" style="border: 1px solid #99CCFF;" border="0"  cellspacing="0" cellpadding="4">

	<s:if test="rtnMessage != null">
		<tr>
			<td align="center">
			    <img src="/etc/images/info_48.png">
			</td>
		
			<td  align="left" valign="middle" style="color: red; font-size:16px;">
			<s:property	value='rtnMessage' /></td>
		</tr>
	</s:if>
	<s:else>
	 <tr>
	    <td align="left" colspan="2" style="background-color:#6666FF;">
		    &nbsp;&nbsp;系统错误提示404：
		</td>
	</tr>	
	 <tr>
	    <td align="center">
		    <img src="/etc/images/info_48.png">
		</td>
		<td align="left" valign="middle" style="color: red; font-size:16px;">数据错误或重复提交数据,请重试！</td>
	</tr>
</s:else>
	<tr>
		<td></td>
		<td align="left" valign="middle"><input class="btnStyle" type="button" value="返回"
			onclick="location.href='javascript:history.go(-1);'"/ >&nbsp;&nbsp;
		</td>
	</tr>
</table>
</body>
</html>


