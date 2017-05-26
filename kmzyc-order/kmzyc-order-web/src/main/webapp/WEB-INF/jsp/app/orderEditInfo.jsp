
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>




<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单评价信息编辑</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.4.2.min.js"></script>
 <script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script type="text/javascript" > 
     
</script>
</head>
<body >


<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单评价'" scope="request"/>
<s:set name="son_name" value="'订单评价编辑'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<form id="orderAssessInfoedit" name="orderAssessInfoedit" action="/app/updateOrderAssessInfo.action" method="post">
<!-- 数据编辑区域 -->
<table width="60%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="edit_title">订单评价信息编辑</th>
	</tr>
   
   <tr> 
		<td width="20%"  class="eidt_rowTitle"><font color="red">*</font>订单评价编号：</th>
		<td width="80%" > 
			<input id="orderAssessId" name="orderAssessInfo.assessInfoId" id="input_style"  type="text" readonly="true" value='<s:property value="#request.oaifo.assessInfoId" />' />
		</td>
	</tr>
		<tr> 
		<td width="20%" class="eidt_rowTitle"><font color="red">*</font>订单编号：</th>
		<td width="80%" > 
			<input id="orderId" name="orderAssessInfo.orderId" id="input_style"  type="text" readonly="true" value='<s:property value="#request.oaifo.orderId" />'/>
         
		</td>
	</tr>
	<tr> 
		<td width="20%" class="eidt_rowTitle"><font color="red">*</font>客户名称：</th>
		<td width="80%"> 
			<input id="guestNum" name="orderAssessInfo.guestNum" id="input_style"  readonly="true"  type="text"  value='<s:property value="#request.oaifo.guestNum" />'/>
		</td>
	</tr>
	
	
	
	<tr> 
		<td width="20%" class="eidt_rowTitle"><font color="red">*</font>创建时间：</th>
		
		
		<td width="80%"> 
			<input id="createDate" name="orderAssessInfo.createDate" readonly="true"  value='<s:property value="#request.oaifo.createDate" />'/> 
		</td>
		
	</tr>
	
	
	
    <tr> 
		<td width="20%" class="eidt_rowTitle"><font color="red">*</font>评价内容：</th>
		
		
		<td width="80%"> 
			<textarea rows="2" cols="20" id="assessContext" name="orderAssessInfo.assessContext"  ><s:property value="#request.oaifo.assessContext" /></textarea>
			
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
    location.href="/app/assessInfoActionList.action";
}
</SCRIPT>
</BODY>
</HTML>