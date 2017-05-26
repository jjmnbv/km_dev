<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增公告</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
</head>
<body onkeydown="changeKey();">
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form action="" method="POST">

<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<INPUT TYPE="hidden" name="noticeSt" value="1">

<!-- 标题条 -->
<div class="pagetitle">新增公告:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="button" value=" 保存并发送 " onclick="saveAndSend()">
			<INPUT class="btngreen" TYPE="button" value="存为草稿" onclick="saveDraft()">
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
		<td width="85%"> 
			<input name="noticeTitle" type="text" size="70" require="true" dataType="LimitB" max="80" min="1" msg="标题必输，且不超过40个汉字" value="<s:property value='model.noticeTitle'/>"/>
		</td>
	</tr>

	<tr> 
		<th align="right">类型：</th>
		<td> 
			<%
			  java.util.List paramList = StaticParams.getParamListByCd("all","noticeType");
			  request.setAttribute("paramList",paramList);
			%>
			<s:select name="noticeType" emptyOption="true" list="#request.paramList" listKey="paramValue" listValue="paramName" value="1"/>
		</td>
	</tr>

	<tr> 
		<th align="right">内容：</th>
		<td> 
			<textarea name="noticeContent" cols="80" rows="12" wrap="PHYSICAL" require="false" dataType="LimitB" max="2000" msg="内容不要超过2000个汉字"><s:property value='model.noticeContent'/></textarea> 
		</td>
	</tr>
</table>


<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="modeltitle">发送</th>
	</tr>
	<tr> 
		<th width="15%" align="right">接收人：</th>
		<td width="85%"> 
		    <input type="hidden" name="userIdStr">
			<input type="text" name="userNameStr" class="inpt_gray" size="50" readonly>
			<INPUT TYPE="button" class="button-2s" value="选择" onclick="popSelectUser()">
		</td>
	</tr>
</table>


<!-- 底部 按钮条 -->
<table width="98%" align="center" class="bottombuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td width="90%" valign="middle">
			<INPUT class="btngreen" TYPE="button" value=" 保存并发送 " onclick="saveAndSend()">
			<INPUT class="btngreen" TYPE="button" value="存为草稿" onclick="saveDraft()">
		</td>
		<td width="10%" align="center"><a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a></td>
	</tr>
</table>

<br><br>
</s:form>



<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoList(){
    location.href="/sys/gotoNoticeList.action";
}

//弹出 人员选择
function popSelectUser() {
	var idStr = document.forms[0].userIdStr.value;
    var nameStr = document.forms[0].userNameStr.value;
    dialog("选择接收人","iframe:/sys/gotoPopSelectUser.action?userIdStr="+idStr+"&userNameStr="+nameStr ,"600px","340px","iframe");
}

//关闭 人员选择
function closeOpenUserDiv(idStr, nameStr){
    closeThis();
    document.forms[0].userIdStr.value = idStr;
	document.forms[0].userNameStr.value = nameStr;
}


//保存并发送
function saveAndSend(){
	var userIdStr = document.forms[0].userIdStr.value;
	if(Validator.Validate(document.forms[0],3)){
		if(userIdStr==""){
			alert("发送前必须选择接收人！");
            return false;
		}
		if(confirm("确定要发送公告吗？")){
			document.forms[0].action = "saveSysNotice.action";
			document.forms[0].submit();
		}
	}
}

//保存为草稿
function saveDraft(){
	document.forms[0].action = "saveSysNotice.action";
    document.forms[0].noticeSt.value="0";
	if(Validator.Validate(document.forms[0],3))
		document.forms[0].submit();
	else
		return false;
}




//光标移动
function changeKey()
{
	var tr=event.srcElement.getAttribute("type");
	if("textarea"!=tr && "button" != tr)
	{
			if(13 == event.keyCode)
			{
				event.keyCode=9;
			}
  }
}

//-->
</SCRIPT>
</BODY>
</HTML>