<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.model.SysMenu"%>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改客户类别信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript" >
$().ready(function() {
	jQuery.validator.addMethod("checkName", function(value, element) {

	              if (/^[^\|"'<>~&$%{}?()（）《》｛｝【】]*$/.test(value))
	                  return true
	                  else return false;
	      
 	   
	}, "输入字符不能含有|\"\'<>~&$%{}?\()（）《》｛｝【】");
	
$("#updateForm").validate({
	rules: { 
	 "name":{required: true,checkName: true,maxlength:15},
		"sortno":{number: true},
		"description":{maxlength:50}
		
       
		},
		success: function (label){
            label.removeClass("checked").addClass("checked");
        }
	});
	 
});
</script>
</head>
<body >
<s:form id="updateForm" name="updateForm" action="/customers/cust_editCustType.action" method="POST"  target="main" >

<INPUT TYPE="hidden" name="customerTypeId" id="customerTypeId" value="<s:property value='bnesCustomerTypeQuery.customerTypeId'/>">
<INPUT TYPE="hidden" name="isEnable" id="isEnable" value="<s:property value='bnesCustomerTypeQuery.isEnable'/>">
<INPUT TYPE="hidden" name="createDate" id="createDate" value="<s:property value='bnesCustomerTypeQuery.createDate'/>">
<INPUT TYPE="hidden" name="createdId" id="createdId" value="<s:property value='bnesCustomerTypeQuery.createdId'/>">
<INPUT TYPE="hidden" name="modifieId" id="modifieId" value="<s:property value="#session['sysUser'].userId"/>">



<table width="80%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
			<tr> 
		<th colspan="2" align="left" class="edit_title">修改客户类别信息:</th>
		</tr>
		<tr> 
			<td width="20%"  class="eidt_rowTitle" ><font color="red">*</font>类别名称：</td>
			<td width="80%" > 
				<input id ="name" name="name" type="text" size="40" value="<s:property value='bnesCustomerTypeQuery.name'/>"/>
			</td>
		</tr>
		
		<tr> 
			<td width="20%" class="eidt_rowTitle" >类别组ID：</td>
			<td width="80%"> 
				<input type="text" name="parentId" size="40"  disabled="disabled" value="<s:property value='bnesCustomerTypeQuery.parentId'/>"/>  
			</td>
		</tr>
		<tr> 
			<td width="20%" class="eidt_rowTitle" >类别序号：</td>
			<td width="80%"> 
				<input type="text" id="sortno"  name="sortno" size="5"  value="<s:property value='bnesCustomerTypeQuery.sortno'/>" />  
			</td>
		</tr>
		<tr> 
			<td width="20%" class="eidt_rowTitle" >类别描述：</td>
			<td width="80%"> 
			<textarea id="description" name="description" cols="1" rows="5" ><s:property value="bnesCustomerTypeQuery.description" /></textarea>
			</td>
		</tr>
		
	</table>
<table width="80%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> <td>
				<input type="submit" class="saveBtn"   value="" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="delBtn"  value="" onclick="deleteCusType();"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btngreen"  value="新增客户类别成员" onclick="addChildCusType();"/></td>
				</tr>
				</table>


</s:form>

<SCRIPT LANGUAGE="JavaScript">

function deleteCusType(){
    var parentId = "<s:property value='isParent'/>";
    if(parentId!=null&&parentId=="1"){
		alert("该客户类别有类别成员，请先删除类别成员！！！");
		return;
    }
   if(confirm("您确定要删除该客户类别吗？")){
    	window.document.updateForm.action = "cust_deleteCustType.action";
    	window.document.updateForm.target = "main";
		window.document.updateForm.submit();
	}
}
function addChildCusType(){
window.document.updateForm.action = "cust_adddCustType.action";
window.document.updateForm.submit();
}
function saveCusType(){

	window.document.updateForm.action = "cust_editCustType.action";
	window.document.updateForm.target = "main";
	window.document.updateForm.submit();
	
}
</SCRIPT>

</BODY>
</HTML>