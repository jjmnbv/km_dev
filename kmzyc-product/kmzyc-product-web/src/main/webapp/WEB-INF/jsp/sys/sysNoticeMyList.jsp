<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.sys.util.CachedParams"%>
<%@ page import="com.pltfm.sys.model.SysNotice"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的公告</title>
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

<s:form action="/sys/gotoMyNoticeList.action" method="POST" >


<!-- 标题条 -->
<div class="pagetitle">我的公告箱:</div>


<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
			<input class="btngreen" type="button" value="- 删除 "  onclick="doDeleteMyNotice('delId');">
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
			  java.util.Map pMap = CachedParams.getParamListByCd("all","noticeType");
			  request.setAttribute("pMap",pMap);
			%>
			<s:select name="noticeType" emptyOption="true" list="#request.pMap" listKey="key" listValue="value" />
		</td>
		<td></td>
		<td></td>
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
		<th align="center" width="20%">接收时间</th>
		<th align="center" width="20%">操作</th>
	</tr>
	<s:iterator id="dataObj" value="page.dataList">
	<%SysNotice itObj = (SysNotice)request.getAttribute("dataObj");%>
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox" name="delId"  value='<s:property value="noticeId"/>'>
		</td>
		<td align="center">
		    <!-- 标题 -->
			<% 
			   String isRead = itObj.getNoticeSt();  //是否已读
			   if(isRead!=null  &&  "0".equals(isRead)){  //未读加粗
			%>
			       <b>
			<%
			   }
			%>
		    <a href="#" onclick="gotoDetail(<s:property value='noticeId'/>)"><s:property value="noticeTitle"/></a>
			<%
			   if(isRead!=null  &&  "0".equals(isRead)){  //未读加粗
			%>
			       </b>
			<%
			   }
			%>
		</td>
		<td align="center">
		    <!-- 类型 -->
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
		<td align="center"><INPUT TYPE="button" class="btngray" value=" 查看 " onclick="gotoDetail(<s:property value='noticeId'/>)"></td>
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

function gotoDetail(id){
    location.href="/sys/gotoNoticeDetail.action?noticeId="+id;
}

function doDeleteMyNotice(name){
	if(confirm("确定要删除所选项吗？")){
		document.forms[0].action="/sys/deleteMyNotice.action";
		document.forms[0].submit();
	}
}
</SCRIPT>

</BODY>
</HTML>