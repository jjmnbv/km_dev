<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单评价回复信息添加</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<!-- <link rel="stylesheet" type="text/css" href="/etc/css/block.css"> -->
 <link rel="stylesheet" type="text/css" href="/etc/css/jq.css"> 
 <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.4.2.min.js"></script>
 <script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" > 
    
</script>
</head>
<body >


<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单评价'" scope="request"/>
<s:set name="son_name" value="'订单评价回复添加'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<form id="orderAssessInfoReplyAdd" name="orderAssessInfoReplyAdd" action="/app/orderAssessReplyAdd1.action" method="post">
<!-- 数据编辑区域 -->


<table  class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;">
	<tr> 
		<th colspan="2" align="left" class="edit_title">订单评价信息回复</th>
	</tr>
   
   <!--  <tr>  
		<td width="20%"  class="eidt_rowTitle"><font color="red">*</font>评价信息编号：</th>
		<td width="80%" > -->
			<!-- <input id="assessInfoId" name="orderAssessReply.assessInfoId" id="input_style" readonly="true"  type="hidden"  value="<s:property value='#request.assessInfoId'/>"  /> -->
		<!-- </td>
	</tr> -->
		<tr> 
		<td width="20%" class="eidt_rowTitle"><font color="red">*</font>客户名称：</th>
		<td width="80%" > 
			<input id="replyGuestNum" name="orderAssessReply.replyGuestNum" id="input_style"  type="text"  value="<s:property value='#request.gusetAccount'/>" />
            <input id="assessInfoId" name="orderAssessReply.assessInfoId" id="input_style" readonly="true"  type="hidden"  value="<s:property value='#request.assessInfoId'/>"  /> 
		</td>
	</tr>
	 <tr> 
		<td width="20%" class="eidt_rowTitle"><font color="red">*</font>回复时间：</th>
		<td width="80%"> 
		    <input name="orderAssessReply.replyeDate"
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					value="">
		</td>
		
	</tr>
    <tr> 
		<td width="20%" class="eidt_rowTitle"><font color="red">*</font>回复内容：</th>
		
		
		<td width="80%"> 
			<textarea rows="4" cols="50" id="replyContent" name="orderAssessReply.replyContent" value=""></textarea> 
		</td>
		
	</tr>
</table>
 
 
<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="saveBtn" type="submit" value=" ">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value=" ">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>
<br><br>
</form>
 
 
 
   
 
 
<!-- 消息提示 -->
<div width="100%">
<!-- 消息提示 -->
		 
	              
</div>
<SCRIPT LANGUAGE="JavaScript"> 
function gotoList(){
    location.href="/app/orderAssessInfoDetailAction.action";
}
</SCRIPT>
</BODY>
</HTML>