<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加客户等级</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
 <script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script type="text/javascript" >
       $(document).ready(function(){
          var sonId  =  $("#sonCustomerId").val();
	       var  parentId  =  $("#parentId").val();
	      if(sonId!=""){
	         querySonCustomerType(parentId,sonId);
	      }
	      getExpendMin();
          $("#userLevelAdd").validate({
               rules: {
					"code": {required:true,commonChar:true,checkCode:true,maxlength:20},
					"level_name": {required:true,maxlength:16},
					"expend_max":{required:true,number:true,compareTo:true,maxlength:22},
					"expend_min":{required:true, number:true,maxlength:22},
					"score_max":{number:true,scoreCompare:true,maxlength:22,keyNulls:true},
					"score_min":{number:true,maxlength:22},
					"valid":{required:true,digits:true,validate:true,maxlength:8},
					"yearMin":{ number:true,maxlength:22},
					"remark":{maxlength:120}
	        	},
	         success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
        });
      //验证客户等级编号是否唯一
         jQuery.validator.addMethod("checkCode", function(value, element) {
 	 	var rows = 0;
 	 	var levelId = $("#n_level_id").val();
 			$.ajax({
 				async:false,
 				url:"userLevel_ajaxCheckCode.action",
 				type:"POST",
 				data:"checkCode=" + value+"&userLevelId="+levelId,
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
	
	   //消费金额大小值判断 
     jQuery.validator.addMethod("validate", function(value, element) {  
		   if (value<=0){
		         return false;
		    }else{
		         return true;
		    }
    },"有效期必须要大于0"); 
     //消费金额大小值判断 
     jQuery.validator.addMethod("compareTo", function(value, element) {  
	    var num = $("#expend_min").val();
		   if (value<=parseInt(num)){
		         return false;
		    }else{
		         return true;
		    }
    },"消费金额最大值不能小于或等于消费金额最小值"); 
     
      //积分范围值判断 
     jQuery.validator.addMethod("scoreCompare", function(value, element) {  
	    var num = $("#score_min").val();
		   if (value<=parseInt(num)){
		         return false;
		    }else{
		         return true;
		    }
    },"积分最大值不能小于或等于积分最小值");   
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
 			getExpendMin();
        }
        
        /** 通过客户类型获取消费金额最小值  **/ 
        function  getExpendMin(){
          var levelId= $("#n_level_id").val();
            if( levelId!=""){
            return;
            }
             var customerTypeId = $("#customerType").val();
             var customerSonId = $("#customer").html(); 
             var value = $("#customer").val();
             if(customerSonId!=""){
                   customerTypeId=value;
             }
             	$.ajax({
 				async:false,
 				url:"userLevel_ajaxOperateExpend.action",
 				type:"POST",
 				data:"customerId=" + customerTypeId,
 				dataType:"json",
 				success:function(data){
 			          $("#expend_min").val(data);
 			 	}
 			});
        }
</script>
</head>
<body >
<!-- 导航栏 -->
<s:set name="parent_name" value="'客户成长'" scope="request"/>
<s:set name="name" value="'客户等级设置'" scope="request"/>
<s:if test="userLevel.n_level_id==null">
<s:set name="son_name" value="'添加'" scope="request"/>
</s:if><s:else>
<s:set name="son_name" value="'修改'" scope="request"/>
</s:else>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form id="userLevelAdd" action="userLevel_operateSave.action" method="post"  namespace="/growing" >
<s:token/>
<!-- 数据编辑区域 -->
<table width="60%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="edit_title">客户等级信息</th>
	</tr>
   
   <tr> 
		<td width="25%"  class="eidt_rowTitle"><font color="red">*</font>客户级别编号：</th>
		<td width="75%" > 
			<input id="code" name="code" id="input_style"  type="text"  value="<s:property value='userLevel.code'/>" />
		</td>
	</tr>
		<tr> 
		<td width="25%" class="eidt_rowTitle"><font color="red">*</font>客户类别：</th>
		<td width="75%" > 
			<s:select id="customerType" name="n_customer_type_id"  list="customerTypeList"  listKey="customerTypeId"   value="%{userLevel.n_customer_type_id}" listValue="name"  onchange="querySonCustomerType(this.value,null)"></s:select>
	        <select id="customer" name="customer_son_id"  style="display:none;" onchange="getExpendMin()"></select>
	         <input type="hidden"  id="sonCustomerId" value="<s:property value='userLevel.customer_son_id'/>">
		     <input type="hidden"  id="parentId"  value="<s:property value='userLevel.n_customer_type_id'/>"/>
		</td>
	</tr>
	<tr> 
		<td width="25%" class="eidt_rowTitle"><font color="red">*</font>客户级别名称：</th>
		<td width="75%"> 
			<input name="level_name" type="text"    value="<s:property value='userLevel.level_name'/>" />
		</td>
	</tr>
    <tr> 
		<td width="25%" class="eidt_rowTitle"><font color="red">*</font>消费额最小值：</th>
		<s:if test="%{userLevel.n_level_id!=null}">
		<td width="75%"> 
			<input id="expend_min" name="expend_min" type="text"    value="<s:property value='%{formatDouble(userLevel.expend_min)}'/>" />
		</td>
		</s:if>
		<s:else>
		<td width="75%"> 
			<input id="expend_min" readonly name="expend_min" type="text"    value="<s:property value='%{formatDouble(userLevel.expend_min)}'/>"  />
		</td>
		</s:else>
	</tr>
	  <tr> 
        <td width="25%"  class="eidt_rowTitle"><font color="red">*</font>消费额最大值：</th>
		<td width="75%"> 
			<input id="expend_max" name="expend_max" type="text"    value="<s:property value='%{formatDouble(userLevel.expend_max)}'/>"  />
		</td>
	</tr>
     <tr> 
		<td width="25%" class="eidt_rowTitle">积分最小值：</th>
		<td width="75%"> 
			<input id="score_min" name="score_min" type="text"   value="<s:property value='%{formatDouble(userLevel.score_min)}'/>" />
		</td>
	</tr>
	 <tr> 
		<td width="25%"  class="eidt_rowTitle"></font>积分最大值：</th>
		<td width="75%"> 
			<input name="score_max"    type="text"    value="<s:property value='userLevel.score_max'/>" />
		</td>
	</tr>
	 <tr> 
		<td width="25%"  class="eidt_rowTitle"></font>年度最低消费额度(元)：</th>
		<td width="75%"> 
			<input id="yearMin" name="yearMin"    type="text"    value="<s:property value='userLevel.yearMin'/>" />
		</td>
	</tr>
	 <tr> 
		<td width="25%"  class="eidt_rowTitle"></font>等级享受服务描述：</th>
		<td width="75%"> 
			<textarea id="remark" name="remark" cols="1" rows="5" ><s:property value="userLevel.remark" /></textarea>
			
		</td>
	</tr>
	 <tr> 
		<td width="25%" class="eidt_rowTitle"><font color="red">*</font>有效期(月)：</th>
		<td width="75%"> 
			<input name="valid" type="text"   value="<s:property value='userLevel.valid'/>" />
		</td>
	 <input  type="hidden" id="n_level_id" name="n_level_id"  value="<s:property value='userLevel.n_level_id'/>"/>
		<input  type="hidden"  name="n_created"  value="<s:property value='userLevel.n_created'/>"/>
		<input  type="hidden"  name="d_create_date"  value="<s:property value='userLevel.d_create_date'/>"/>
	</tr>
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
		    <s:submit value="" cssClass="saveBtn" />
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value=" ">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>
<br><br>
</s:form>   
<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
<SCRIPT LANGUAGE="JavaScript">
function gotoList(){
    location.href="/growing/userLevel_queryPageList.action";
}
</SCRIPT>
</BODY>
</HTML>