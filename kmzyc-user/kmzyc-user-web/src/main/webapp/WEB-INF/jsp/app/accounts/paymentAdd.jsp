<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript" >
function formateAmout(){
	var value = $("#amount").val();
	var amount = value.substring(0,value.indexOf(".") + 3);
	$("#amount").val(amount);
}

$().ready(function() {
	jQuery.validator.addMethod("checkAmoutLength", function(value, element) {
 		var amount = value;
 		
 	   if(amount.length>9){
	        return false;
		 }
 	   else{
 	 	   return true;
 	 	   }
 	   
	}, "输入的金额必须小于9位");
	jQuery.validator.addMethod("checkAmout", function(value, element) {
 		var amount = value;
 		
 	   if(amount>0){
	        return true;
		 }
 	   else{
 	 	   return false;
 	 	   }
 	   
	}, "输入的金额必须大于0");
	
$("#payment").validate({
	rules: { 
		
		"amount":{number: true,required: true,checkAmout: true,checkAmoutLength: true},
        "content":{maxlength:120},
        "accountLogin":{required: true}
		},
		success: function (label){
            label.removeClass("checked").addClass("checked");
        }
	});
	 
});
<%--//弹出层 选择账号--%>

	function popUpAccount() {
	 	
	    dialog("选择账号","iframe:/accounts/accountInfo_popUpAccount.action" ,"900px","340px","iframe");
	}
	
	<%-- //关闭弹出窗口 --%>
	
function closeOpenAccount(accountId,account){
    closeThis();
    document.forms[0].accountId.value = accountId;
	document.forms[0].accountLogin.value = account;
}
</script>
</head>
<body >

<%-- <!-- 导航栏 -->--%>

<s:set name="parent_name" value="'账户管理'" scope="request"/>
<s:set name="name" value="'充值'" scope="request"/>
<s:set name="son_name" value="'添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form id="payment" name="paymentForm" action="/accounts/payment_savePaymet.action" method="post">
<s:token/>
<table width="90%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<th colspan="2" align="left" class="edit_title">账户充值信息</th>
	</tr>
	  <tr> 
		<td width="20%"  class="eidt_rowTitle"><font color="red">*</font>账户号：</th>
		<td width="80%"> 
			<input type="hidden" id="accountId"   name="bnesAcctTransactionQuery.accountId" value="<s:property value='bnesAcctTransactionQuery.accountId'/>" />
						<input id="accountLogin" name="accountLogin"  type="text"  class="input_stype"
							value="<s:property value='bnesAcctTransactionQuery.accountLogin'/>"  readonly="readonly"/>
							<input type="button" class="btn-custom" value="选择" onClick="popUpAccount()"></td>
	</tr>
	 <tr> 
		<td width="20%"  class="eidt_rowTitle"><font color="red">*</font>充值流水号：</th>
		<td width="80%"> 
			<input id="accountNumber" name="accountNumber" id="input_style"  type="text"  value="<s:property value='bnesAcctTransactionQuery.accountNumber'/>" style=" background-color:#EBECED; color:#666666" readonly="readonly"/>
		</td>
	</tr>
	 <tr> 
		<td width="20%"  class="eidt_rowTitle" ><font color="red">*</font>充值金额：</th>
		<td width="80%"> 
			<input id="amount" name="amount" id="input_style"  type="text" onkeyup="this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3')" value="<s:property value='bnesAcctTransactionQuery.amount'/>" style="width:120px;" />
		</td>
	</tr>
	<tr> 
		<td width="20%"  class="eidt_rowTitle">充值备注：</th>
		<td width="80%"> 
		<textarea id="content" name="content" cols="1" rows="5" ><s:property value="bnesAcctTransactionQuery.content" /></textarea>
		</td>
	</tr>
	</table>

<%--<!-- 底部 按钮条 --> --%>

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

