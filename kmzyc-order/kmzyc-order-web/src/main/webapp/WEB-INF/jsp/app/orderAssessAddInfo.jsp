<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生成订单评价页面</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/etc/css/notifier-base.css">
<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="/etc/css/notifier-theme-plastic.css">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/jquery.dragndrop.js"></script>
<script type="text/javascript" src="/etc/js/showframe.js"></script>
<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/etc/js/urchin.js"></script>
<Script src="/etc/js/Form.js" type="text/javascript"></Script>
<Script  src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript">
window.onload = function() 
{ 
document.getElementById('assessContext').onkeydown = function() 
{    
    if(this.value.length >= 100) 
      event.returnValue = false; 
} 
} 



	function formSubmit(){
		var assessInfo=document.getElementById("assessContext").value
		var stav1=document.getElementsByName("state1");
		var stav2=document.getElementsByName("state2");
		var stav3=document.getElementsByName("state3");
		var a1 = 0;
		var a2 = 0;
		var a3 = 0;
		for(var i=0;i<stav1.length;i++){
			if(!stav1[i].checked){
				a1++;
			}
		}
		for(var i=0;i<stav2.length;i++){
			if(!stav2[i].checked){
				a2++;
			}
		}
		for(var i=0;i<stav3.length;i++){
			if(!stav3[i].checked){
				a3++;
			}
		}
		if(a1 == stav1.length){
			alert('请对本次服务进行评价！');
			return;
			}else if(a2 == stav2.length){
				alert('请对本次服务进行评价！');
				return;
			}else if(a3 == stav3.length){
				alert('请对本次服务进行评价！');
				return;
			}else if (assessInfo==""){
				alert('请填写评价内容！');
				return;
			}
	   document.orderAssessInfoAdd.submit();
	}
</script>

</head>

<body >

 <s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单评价管理'" scope="request"/>
<s:set name="son_name" value="'订单评价添加'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<br/>
<form  name="orderAssessInfoAdd" action="/app/assessInfoSave.action" method="post">
<!-- 数据编辑区域 -->
<table width="60%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="5" align="left" class="edit_title">欢迎你对本次购物进行评价</th>
	</tr>
   
	<tr> 
		<td width="20%" class="eidt_rowTitle"><font color="red">*</font>订单编号：</th>
		<td width="80%" colspan="4" > 
		<font ><s:property value='orderAssessInfo.orderCode'/></font>
		<input name="orderAssessInfo.guestNum"   type="hidden"  value="<s:property value='orderAssessInfo.guestNum'/>" />
		<input name="orderAssessInfo.orderId"   type="hidden"  value="<s:property value='orderAssessInfo.orderId'/>" />
			<input name="orderAssessInfo.orderCode"   type="hidden"  value="<s:property value='orderAssessInfo.orderCode'/>" /> 
		</td>
	</tr>

		 
  <s:iterator id="assessType" value="assessTypeList">
	<tr>
		<td colspan="5">
		   <input name="assessType"  value=" <s:property value='#assessType.orderDictionaryCode'/>" type="hidden"/>
		   <input name="assessName"  value="<s:property value='#assessType.orderDictionaryValue'/>" type="hidden"/>
		   <s:property value="#assessType.orderDictionaryValue"/>   
		</td> 
	</tr>
	<tr>
		<s:iterator id="assessGrade"  value="assessGradeList">
			<td><input type="radio"  name="state<s:property value='#assessType.orderDictionaryKey'/>"  value="<s:property value='#assessGrade.orderDictionaryKey'/>" /><s:property value="#assessGrade.orderDictionaryValue"/></td>
		</s:iterator>
	</tr>	      
		    
  </s:iterator>
    <tr> 
		<td width="20%" class="eidt_rowTitle"><font color="red">*</font>评价内容：</th>
		
		
		<td width="80%" colspan="4"> 
			<textarea rows="4" cols="30" id="assessContext"   maxlength="10"  name="orderAssessInfo.assessContext" ></textarea> 
		</td>
		
	</tr>
</table>
 
 
<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="saveBtn" type="button"  onclick="formSubmit()" value=" ">
		</td>
	</tr>
</table>
</form>
 
</BODY>
</HTML>