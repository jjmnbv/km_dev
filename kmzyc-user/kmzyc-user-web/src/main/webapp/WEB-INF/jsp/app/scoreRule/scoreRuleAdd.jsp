<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加客户积分规则</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
 <script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript">
       $(document).ready(function(){
          var sonId  =  $("#sonCustomerId").val();
	       var  parentId  =  $("#parentId").val();
	      if(sonId!=""){
	         //querySonCustomerType(parentId,sonId);
	      }
          $("#scoreRuleAdd").validate({
               rules: {
					"code": {required:true,commonChar:true,checkCode:true,maxlength:50},
					"discribe": {required:true,maxlength:100},
					"scoreExpress":{required:true,specialChar:true,expressCheck:true,maxlength:50},
					"remark":{maxlength:85},
					"dayMax":{number:true,maxlength:15}
	        	},
	           success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
        });
          /**通过customer类别id 查询子客户类别 **/
        function querySonCustomerType(id,value){
             	$.ajax({
 				async:false,
 				url:"userLevel_ajaxOperateCustomerType.action",
 				type:"POST",
 				data:"customerId=" + id,
 				dataType:"json",
 				success:function(data){
 					if((data!=null||data!="")&&data.length>0){
 					   $("#customer option").remove();
 					     $("#customer").show();
	 					for(var i=0;i<data.length;i++){
	 					       if(value!=""&&value==data[i].customerTypeId){
			 					 $("#customer").append("<option value='"+data[i].customerTypeId+"' selected>"+data[i].name+"</option>");
			 					}else{
			 					 $("#customer").append("<option value='"+data[i].customerTypeId+"'>"+data[i].name+"</option>");
			 					}
	 					}
 				    }else{
 				        $("#customer option").remove();
 				        $("#customer").hide();
 				    }
 			   }
 			});
        }
      //验证规则编号是否唯一
      jQuery.validator.addMethod("checkCode", function(value, element) {
 	 	var rows = 0;
 	 	var ruleId = $("#ruleId").val();
 			$.ajax({
 				async:false,
 				url:"scoreRule_ajaxCheckCode.action",
 				type:"POST",
 				data:"checkCode=" + value+"&scoreRuleId="+ruleId,
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
 			
	}, "编号重复，请重新添写！");
	  //验证计算表达式是否正确 
      jQuery.validator.addMethod("expressCheck", function(value, element) {
      	var rows ="";
 			$.ajax({
 				async:false,
 				url:"scoreRule_ajaxCheckExpress.action",
 				type:"POST",
 				data:"checkCode="+encodeURIComponent(value),
 				dataType:"json",
 				success:function(json){
 						rows = json;
 				}
 			});
 			if(rows=="no"){
 				return false;
 			}else{
 	 			return true;
 	 		}
 			
	}, "计算表达式验编写错误");
</script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'客户成长'" scope="request"/>
<s:set name="name" value="'积分规则'" scope="request"/>
<s:set name="son_name" value="'添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form id="scoreRuleAdd" action="scoreRule_operateSave.action" method="POST"  namespace='/growing' >
<s:token/>
<table width="95%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="edit_title">客户积分规则</th>
	</tr>
   <tr> 
		<td width="20%"   class="eidt_rowTitle"><font color="red">*</font>积分规则编号：</th>
		<td width="80%"> 
			<input name="code" type="text"   value="<s:property value='scoreRule.code'/>"/>
		</td>
	</tr>
	<!-- 
		<tr> 
		<td width="20%"   class="eidt_rowTitle"><font color="red">*</font>客户类别：</th>
		<td width="80%"> 
			<s:select name="clientType" value="%{scoreRule.clientType}" list="customerTypeList"  listKey="customerTypeId"  listValue="name" onchange="querySonCustomerType(this.value,null)"></s:select>
		    <select id="customer" name="customer_son_id"  style="display:none;"></select>
	         <input type="hidden"  id="sonCustomerId" value="<s:property value='scoreRule.customer_son_id'/>">
		     <input type="hidden"  id="parentId"  value="<s:property value='scoreRule.clientType'/>"/>
		</td>
	</tr>
	 -->
	<tr> 
		<td width="20%"   class="eidt_rowTitle"><font color="red">*</font>积分规则名称：</th>
		<td width="80%"> 
			<input name="discribe" type="text"    value="<s:property value='scoreRule.discribe'/>"/>
		</td>
	</tr>
	
    <tr> 
		<td width="20%"   class="eidt_rowTitle"><font color="red">*</font>积分表达式：</th>
		<td width="80%"> 
			<input name="scoreExpress"  type="text"  value="<s:property value='scoreRule.scoreExpress'/>" />
		</td>
	</tr>
	
	 <tr> 
		<td width="20%"    class="eidt_rowTitle">每日上限：</th>
		<td width="80%"> 
			<input name="dayMax"  type="text"  value="<s:property value='scoreRule.dayMax'/>" />
		</td>
	</tr>
	<tr> 
		<td width="20%"   class="eidt_rowTitle">积分说明：</th>
		<td width="80%"> 
		     <s:textarea name="remark"  rows="2" cols="60"  value="%{scoreRule.remark}" cssStyle="height:60px"></s:textarea>
		</td>
	</tr>
	<s:if test="scoreRule.expireLimit==1">
		<tr> 
			<td width="20%"   class="eidt_rowTitle">生效时间：</th>
			<td width="80%">
			每月18日 
				<input type="text" id="d523" class="Wdate" readonly="readonly" value="<s:property value = 'scoreRule.expireStartDate' />"  name="scoreRule.expireStartDate" onclick="WdatePicker({el:'d523',dateFmt:'HH:mm:ss',minDate:'00:00:00', maxDate:'#F{$dp.$D(\'d524\')}'})" />
				-
				<input type="text" id="d524" class="Wdate" readonly="readonly" value="<s:property value = 'scoreRule.expireEndDate' />"  name="scoreRule.expireEndDate" onclick="WdatePicker({el:'d524',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'d523\')}', maxDate:'23:59:59'})" />
			</td>
		</tr>
	</s:if>
	<!-- 
	 <tr> 
		<td width="20%"    class="eidt_rowTitle">获得积分值：</th>
		<td width="80%"> 
			<input name="scoreValue"  type="text"  value="<s:property value='scoreRule.scoreValue'/>" />
		</td>
	</tr>
	 -->
	 <input  type="hidden" id="status"  name="status"  value="<s:property value='scoreRule.status'/>"/>
	 <input  type="hidden" id="ruleOrder"  name="ruleOrder"  value="<s:property value='scoreRule.ruleOrder'/>"/>
	 <input  type="hidden" id="expireLimit"  name="expireLimit"  value="<s:property value='scoreRule.expireLimit'/>"/>
	 <input  type="hidden" id="expireStartDate"  name="expireStartDate"  value="<s:property value='scoreRule.expireStartDate'/>"/>
	 <input  type="hidden" id="expireEndDate"  name="expireEndDate"  value="<s:property value='scoreRule.expireEndDate'/>"/>
	 <input  type="hidden" id="scoreValue"  name="scoreValue"  value="<s:property value='scoreRule.scoreValue'/>"/>
     <input  type="hidden" id="clientType"  name="clientType"  value="<s:property value='scoreRule.clientType'/>"/>
	 <input  type="hidden" id="ruleId"  name="n_scoreRuleId"  value="<s:property value='scoreRule.n_scoreRuleId'/>"/>
	 <input  type="hidden"  name="n_created"  value="<s:property value='scoreRule.n_created'/>"/>
	 <input  type="hidden"  name="d_createDate"  value="<s:property value='scoreRule.d_createDate'/>"/>
	</tr>
</table>


<!-- 底部 按钮条 -->
<table width="95%"  align="center" class="edit_bottom" height="20" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td >
			<s:submit cssClass="saveBtn" value=" "></s:submit>
             &nbsp;&nbsp;
			<input class="backBtn" type="button"  onclick="gotoList()" value="">
		</td>
	</tr>
</table>
</s:form>
<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoList(){
    location.href="/growing/scoreRule_queryPageList.action";
}

</SCRIPT>
</BODY>
</HTML>