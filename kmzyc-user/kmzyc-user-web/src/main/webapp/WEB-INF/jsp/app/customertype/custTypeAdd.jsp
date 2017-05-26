<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户类别定义</title>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<link href="/etc/js/dtree/dtree.css" type="text/css" rel="stylesheet">
<script language="JavaScript" type="text/javascript" src="/etc/js/dtree/dtree.js" ></script>
<Script language="JavaScript" type="text/javascript" src="/etc/js/Form.js"></Script>
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/jquery-latest.pack.js" type="text/javascript"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>

<script type="text/javascript" >
$().ready(function() {
	jQuery.validator.addMethod("checkName", function(value, element) {

	              if (/^[^\|"'<>~&$%{}?()（）《》｛｝【】]*$/.test(value))
	                  return true
	                  else return false;
	      
 	   
	}, "输入字符不能含有|\"\'<>~&$%{}?\()（）《》｛｝【】");
	jQuery.validator.addMethod("checkCustName", function(value, element) {
		var parentId = $("#parentId").val();
		value =value.replace(/(^\s*)|(\s*$)/g, ""); 
	 	var rows = 0;
		$.ajax({
				async:false,
				url:"cust_checkCustTypeName.action",
				type:"POST",
				data:"name=" + value+"&parentId="+parentId,
				dataType:"json",
				success:function(json){
						rows = json;
				}
			});
		if(rows>0){
				return false;
			}else{
	 			return true;
	 		}

}, "输入客户类别成员重复");
	
	
$("#updateForm").validate({
	rules: { 
	 "name":{required: true,checkName: true,maxlength:15,checkCustName: true},
		"sortno":{number: true,maxlength:20},
		"description":{checkName: true,maxlength:50}
		
       
		},
		success: function (label){
            label.removeClass("checked").addClass("checked");
        }
	});
	 
});
</script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>


<s:form id="updateForm" name="updateForm" action="/customers/cust_saveCutType.action" method="POST"  >
<INPUT TYPE="hidden" name="parentId" id="parentId" value="<s:property value='bnesCustomerTypeQuery.customerTypeId'/>">
<table width="60%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="edit_title">新增客户类别成员信息:</th>
		</tr>
<tr> 
			<td width="20%"  class="eidt_rowTitle" ><font color="red">*</font>类别名称：</td>
			<td width="80%" >
				<input id ="name" name="name" type="text"  value="<s:property value='name'/>"/>
			</td>
		</tr>
	
		<tr> 
			<td width="20%"  class="eidt_rowTitle" >类别序号：</th>
			<td width="80%"> 
				<input type="text" id ="sortno" name="sortno" size="5"  value="<s:property value='sortno'/>" />  
			</td>
		</tr>
			<tr> 
			<td width="20%"  class="eidt_rowTitle" >类别描述：</td>
			<td width="80%"> 
				<textarea id ="description" name="description" cols="1" rows="5" ><s:property value="bnesCustomerTypeQuery.description" /></textarea>
			</td>
		</tr>
</table>
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> <td>
	<input type="submit" class="saveBtn"  value="" />
	
	</td>
	</tr>
	</table>
</s:form>

</BODY>
</HTML>
