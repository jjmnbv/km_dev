<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值修改</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script type="text/javascript" >

</script>
</head>
<body >

	
		<%-- 导航栏--%>

<s:set name="parent_name" value="'账户管理'" scope="request"/>
<s:set name="name" value="'充值'" scope="request"/>
<s:set name="son_name" value="'修改'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form  name="paymentForm" action="/accounts/payment_editPayement.action" method="post">
<s:token/>
<input type="hidden" id="accountTransactionId" name="accountTransactionId"   value="<s:property value='bnesAcctTransactionQuery.accountTransactionId'/>" />
<input type="hidden" id="status" name="status"   value="<s:property value='bnesAcctTransactionQuery.status'/>" />
<input type="hidden" id="type" name="type"   value="<s:property value='bnesAcctTransactionQuery.type'/>" />
<INPUT TYPE="hidden" name="modifieId" id="modifieId" value="<s:property value="#session['sysUser'].userId"/>">
<INPUT TYPE="hidden" name="accountId" id="accountId" value="<s:property value="bnesAcctTransactionQuery.accountId"/>">

<table width="60%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<th colspan="2" align="left" class="edit_title">账户充值信息修改</th>
	</tr>
	  <tr> 
		<td width="20%"  class="eidt_rowTitle"><font color="red">*</font>账户号：</th>
		<td width="80%"> 
			<input id="accountLogin" name="accountLogin" id="input_style"  type="text"  value="<s:property value='bnesAcctTransactionQuery.accountLogin'/>" />
		</td>
	</tr>
	 <tr> 
		<td width="20%"  class="eidt_rowTitle"><font color="red">*</font>充值流水号：</th>
		<td width="80%"> 
			<input id="accountNumber" name="accountNumber" id="input_style"  type="text"  value="<s:property value='bnesAcctTransactionQuery.accountNumber'/>" />
		</td>
	</tr>
	 <tr> 
		<td width="20%"  class="eidt_rowTitle"><font color="red">*</font>充值金额：</th>
		<td width="80%"> 
			<input id="amount" name="amount" id="input_style"  type="text"  value="<s:property value='bnesAcctTransactionQuery.amount'/>" />
		</td>
	</tr>
	<tr> 
		<td width="20%"  class="eidt_rowTitle">充值备注：</th>
		<td width="80%"> 
			<input id="content" name="content" id="input_style"  type="text"  value="<s:property value='bnesAcctTransactionQuery.content'/>" />
			
		</td>
	</tr>
	</table>

<%-- 底部 按钮条--%>

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

</s:form>
<SCRIPT LANGUAGE="JavaScript">
function gotoList(){
    location.href="/accounts/payment_showPayment.action";
}
</SCRIPT>
</body>
</html>