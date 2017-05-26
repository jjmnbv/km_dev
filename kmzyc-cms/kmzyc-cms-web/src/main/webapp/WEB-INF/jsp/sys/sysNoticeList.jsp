<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.sys.model.SysNotice"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告管理</title>
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

<s:if test='rtnMessage.equals("operateOK")'>
<SCRIPT LANGUAGE="JavaScript">
<!--
	alert("操作成功!");
//-->
</SCRIPT>
</s:if>

<s:form action="/sys/gotoNoticeList.action" method="POST" >


<!-- 标题条 -->
<div class="pagetitle">系统公告管理:</div>


<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="button" value="+ 新增 " onclick="gotoAdd();">
			<input class="btngreen" type="button" value="- 删除 "  onclick="deleteSelected('delId');">
		</td>
	    <td width="10%" align="center"></td>
	</tr>
</table>


<!-- 查询条件区域 -->
<table  width="98%" class="searcharea" border="0" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">标题：</td>
		<td>
		    <input name="noticeTitle" type="text" value="">
		</td>
		<td align="right">类型：</td>
		<td>
			<%
			  java.util.List paramList = StaticParams.getParamListByCd("all","noticeType");
			  request.setAttribute("paramList",paramList);
			%>
			<s:select name="noticeType" emptyOption="true" list="#request.paramList" listKey="paramValue" listValue="paramName" />
		</td>
		<td align="right">状态：</td>
		<td>
			<%
			  java.util.List paramList2 = StaticParams.getParamListByCd("all","noticeSt");
			  request.setAttribute("paramList2",paramList2);
			%>
			<s:select name="noticeSt" emptyOption="true" list="#request.paramList2" listKey="paramValue" listValue="paramName" />
		</td>
		<td></td>
	</tr>
	<tr>
		<td align="right">创建时间：</td>
        <td colspan="5">
			<input name="searchDate1" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;&nbsp;
		    <input name="searchDate2" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		</td>
		<td align="center">
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
		<th align="center" width="40%">公告标题</th>
		<th align="center" width="15%">公告类型</th>
		<th align="center" width="20%">创建时间</th>
		<th align="center" width="10%">状态</th>
		<th align="center" width="10%">操作</th>
	</tr>
	<s:iterator id="dataObj" value="page.dataList">
	<%SysNotice itObj = (SysNotice)request.getAttribute("dataObj");%>
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox" name="delId"  value='<s:property value="noticeId"/>'>
		</td>
		<td align="center">
		    <a href="#" onclick="gotoUpdate(<s:property value='noticeId'/>)"><s:property value="noticeTitle"/></a>
		</td>
		<td align="center">
			<% 
			   String noticeType = itObj.getNoticeType();  //类型 
			   if(noticeType!=null  &&  "2".equals(noticeType)){  //控制颜色
			%>
			       <font color="red">
			<%
			   }
			%>
		    <%=StaticParams.getParamNameByCd("all","noticeType",noticeType)%>
			<%
			   if(noticeType!=null  &&  "2".equals(noticeType)){  //控制颜色
			%>
			       </font>
			<%
			   }	
			%>
		</td>
		<td align="center"><s:date name="createDate" format="yyyy-MM-dd" /></td>
		<td align="center">
			<% String noticeSt = itObj.getNoticeSt();  //状态 %>
		    <%=StaticParams.getParamNameByCd("all","noticeSt",noticeSt)%>
		</td>
		<td align="center"><INPUT TYPE="button" class="btngray" value=" 修改 " onclick="gotoUpdate(<s:property value='noticeId'/>)"></td>
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
    location.href="/sys/gotoSysNoticeAdd.action";
}

function gotoUpdate(id){
    location.href="/sys/gotoNoticeUpdate.action?noticeId="+id;
}

function doDelete(name){
	if(confirm("确定要删除所选项吗？")){
		document.forms[0].action="/sys/deleteSysNotice.action";
		document.forms[0].submit();
	}
}
</SCRIPT>

</BODY>
</HTML>