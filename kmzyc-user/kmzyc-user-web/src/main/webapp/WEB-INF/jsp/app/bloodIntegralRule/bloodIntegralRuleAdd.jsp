<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加专家经验规则</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
          $("#bloodIntegralRuleAdd").validate({
               rules: {
					"code": {required:true,commonChar:true,maxlength:50},
					"discribe": {required:true,maxlength:16,unusualChar:true},
					"integralnumber":{required:true, digits:true,maxlength:9}
	        	},
	           success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
        });
     /* jQuery.validator.addMethod("checkCode", function(value, element) {
 	 	var rows = 0;
 	 	var integralRuleId = $("#integralRuleId").val();
 			$.ajax({
 				async:false,
 				url:"integralRule_ajaxCheckCode.action",
 				type:"POST",
 				data:"checkCode=" + value+"&irId="+integralRuleId,
 				dataType:"json",
 				success:function(json){
 						rows = json;
 				}
 			});
 			if(rows==1){
 				return false;
 			}else{
 	 			return true;
 	 		}
 			
	}, "编号重复，请重新填写！");*/
</script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'客户成长'" scope="request"/>
<s:set name="name" value="'专家经验'" scope="request"/>
<s:if test="bloodIntegralRule.integralRuleId==null">
<s:set name="son_name" value="'添加'" scope="request"/>
</s:if><s:else>
<s:set name="son_name" value="'修改'" scope="request"/>
</s:else>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form id="bloodIntegralRuleAdd" action="integralRule_add.action" method="POST"  namespace='/bloodIntegralRule' >
<s:token/>
<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
			
		</td>
	</tr>
	</s:if>
	<tr> 
		<th colspan="2" align="left" class="edit_title">专家经验规则</th>
		
	</tr>
   
   <tr> 
		<td width="20%"   class="eidt_rowTitle"><font color="red">*</font>经验规则编号：</td>
		<td width="80%"> 
		<input name="bloodIntegralRule.integralRuleId" id="integralRuleId" type="hidden"   value="<s:property value='bloodIntegralRule.integralRuleId'/>"/>
		<input name="bloodIntegralRule.code" id="code" type="hidden"   value="<s:property value='bloodIntegralRule.code'/>"/>	
			<s:property value="bloodIntegralRule.code"/>
		</td>
	</tr>
	<tr> 
		<td width="20%"   class="eidt_rowTitle"><font color="red">*</font>经验规则简述：</td>
		<td width="80%"> 
			<input name="discribe" id="discribe" type="text"    value="<s:property value='bloodIntegralRule.discribe'/>"/>
		</td>
	</tr>
    <tr> 
		<td width="20%"   class="eidt_rowTitle"><font color="red">*</font>经验数量：</td>
		<td width="80%"> 
			<input name="integralnumber" id="integralnumber" type="text"  value="<s:property value='bloodIntegralRule.integralnumber '/>"/>
		</td>
</table>


<!-- 底部 按钮条 -->
<table width="95%"  align="center" class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td >
			<input class="saveBtn" type="submit" value="">
             &nbsp;&nbsp;
			<input class="backBtn" type="button"  onclick="gotoList()" value="">
		</td>
	</tr>
</table>

<br><br>

</s:form>
   
<SCRIPT LANGUAGE="JavaScript">
function gotoList(){
    location.href="/bloodIntegralRule/integralRule_queryPageList.action";
}

</SCRIPT>
</BODY>
<!-- 消息提示 -->
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>