<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.opensymphony.xwork2.ognl.OgnlValueStack"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告内容</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:form action="" method="POST" onsubmit="return  Validator.Validate(this,3)">

<!-- 标题条 -->
<div class="pagetitle">公告内容:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
			<INPUT class="btngreen" TYPE="button" value=" 删除 " onclick="doDelete()">
		</td>
	    <td width="10%" align="center"><a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a></td>
	</tr>
</table>

<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="modeltitle">公告信息</th>
	</tr>
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<th width="15%" align="right"><font color="red">*</font>标题：</th>
		<td width="85%" style="color: #000000; font-weight: bold"> 
			<s:property value='model.noticeTitle'/>
		</td>
	</tr>

	<tr> 
		<th align="right">类型：</th>
		<td style="color: #000000;"> 
			<%
			  java.util.List paramList = StaticParams.getParamListByCd("all","noticeType");
			  request.setAttribute("paramList",paramList);
			  OgnlValueStack stack = (OgnlValueStack)request.getAttribute("struts.valueStack");
              String noticeType = (String)stack.findValue("model.noticeType");
			%>
			<%=StaticParams.getParamNameByCd("all","noticeType",noticeType)%>
		</td>
	</tr>

	<tr> 
		<th align="right">内容：</th>
		<td> 
			<textarea name="noticeContent" cols="80" rows="18" wrap="PHYSICAL" require="false" dataType="LimitB" max="2000" msg="内容不要超过2000个汉字"><s:property value='model.noticeContent'/></textarea> 
		</td>
	</tr>
</table>

<!-- 底部 按钮条 -->
<table width="98%" align="center" class="bottombuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td width="90%" valign="middle">
			<INPUT class="btngreen" TYPE="button" value=" 删除 " onclick="doDelete()">
		</td>
		<td width="10%" align="center"><a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a></td>
	</tr>
</table>

<br><br>
</s:form>



<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoList(){
    location.href="/sys/gotoMyNoticeList.action";
}

//无效
function doDelete(){
	var noticeId=<s:property value='model.noticeId'/>;
	if(confirm("确定要将该公告删除吗？")){
		document.forms[0].action = "/sys/deleteMyNotice.action?delId="+noticeId;
		document.forms[0].submit();
	}
}


//-->
</SCRIPT>
</BODY>
</HTML>