<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%-- <%@ page import="com.pltfm.sys.model.SysMail"%> --%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已发送</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:if test='rtnMessage.equals("deleteOk")'>
<SCRIPT LANGUAGE="JavaScript">
<!--
	alert("删除成功!");
//-->
</SCRIPT>
</s:if>

<s:form action="/sys/gotoSendList.action" method="POST" >


<!-- 标题条 -->
<div class="pagetitle">已发送:</div>


<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
			<input class="btngreen" type="button" value="- 删除 "  onclick="deleteSelected('delId');">
		</td>
	    <td width="10%" align="center"></td>
	</tr>
</table>


<!-- 查询条件区域 -->
<table  width="98%" class="searcharea" border="0" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right" width="10%">标题：</td>
		<td width="20%">
		    <input name="mailTitle" type="text" value='<s:property value="mailTitle"/>'>
		</td>
        <td align="right" width="10%">创建时间：</td>
        <td colspan="5" width="45%">
			<input name="searchDate1" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;&nbsp;
		    <input name="searchDate2" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		</td>
        <td align="center" width="15%">
			<INPUT TYPE="submit" class="button-blue-1" value=" 查询 ">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="tableStyle1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox' onclick='checkAll(this)'>
		</th>
		<th align="center" width="20%">收件人</th>
		<th align="center" width="55%">标题</th>
		<th align="center" width="20%">时间</th>
	</tr>
	<s:iterator id="dataObj" value="page.dataList">
	<%Object itObj = (Object)request.getAttribute("dataObj");%>
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox" name="delId"  value='<s:property value="mailId"/>'>
		</td>
		<td align="center">
            <s:property value="userName"/>
		</td>
		<td align="center">
            <a href="#" onclick="gotoView(<s:property value='mailId'/>)" style="text-decoration:none;">
                <s:if test="mailTitle!=null && mailTitle.length()>30">
                    <s:property value="mailTitle.substring(0,30)"/>...
                </s:if>
                <s:else>
                    <s:property value="mailTitle"/>
                </s:else>
            </a>
		</td>
		<td align="center"><s:date name="sendTime" format="yyyy-MM-dd HH:mm" /></td>
	</tr>
	</s:iterator>
</table>

<!-- 分页按钮区 -->
<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td>
			<%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
		</td>
	</tr>
</table>

<br><br>


</s:form>
<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoAdd(){
    location.href="/sys/gotoSysMailAdd.action";
}

function gotoView(id){
    location.href="/sys/gotoMailView.action?mailId="+id+"&fromView=sendList";
}

function doDelete(name){
	document.forms[0].action="/sys/deleteSel.action";
	document.forms[0].submit();
}
</SCRIPT>

</BODY>
</HTML>