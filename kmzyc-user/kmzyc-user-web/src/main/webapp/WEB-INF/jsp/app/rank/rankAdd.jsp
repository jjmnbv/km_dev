<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加客户头衔信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
</head>
<body>
<script type="text/javascript">  
       $(document).ready(function(){
          var sonId  =  $("#sonCustomerId").val();
	       var  parentId  =  $("#parentId").val();
	      if(sonId!=null||sonId!=""){
	         querySonCustomerType(parentId,sonId);
	      }
	      getExpendMin();
	       getCode();
          $("#rankAdd").validate({
               rules: {
					"rank.rankName": {required:true,maxlength:16,userName:true,checkrankName:true},
					"scoreMins":{required:true,digits:true,maxlength:9},
					"rank.scoreMax":{required:true,compareTo:true,digits:true,maxlength:9}
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
 				url:"rank_ajaxOperateCustomerType.action",
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
 			 getCode();
        }
        
 /* jQuery.validator.addMethod("checkCode", function(value, element) {
 	 	var rows = 0;
 	 		var rankId = $("#rankId").val();
 			$.ajax({
 				async:false,
 				url:"rank_checkCode.action",
 				type:"POST",
 				data:"checkCode=" + value+"&rank_Id="+rankId,
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
	       	
		jQuery.validator.addMethod("checkrankName", function(value, element){
		var rows =0;
		var rankId = $("#rankId").val();		
		$.ajax({
			async:false,
			url:"rank_checRarkName.action",
			type:"POST",
			data:"checRarkName=" + value+"&rank_Id="+rankId,
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
 			
	}, "头衔名称重复，请重新填写！"); 
	 //经验最小值
     jQuery.validator.addMethod("compareTo", function(value, element) {  
	    var num = $("#scoreMins").val();
		   if (parseInt(num)>=parseInt(value)){
		         return false;
		    }else{
		         return true;
		    }
    },"经验最大值必须大于经验最小值"); 
    
	 /** 通过客户类型获取经验最小值  **/ 
        function  getExpendMin(){
          var rankId= $("#rankId").val();
            if( rankId!=""){
            return;
            }
             var customerTypeId = $("#customerTypeId").val();
             var customerSonId = $("#customer").html(); 
             var value = $("#customer").val();
             if(customerSonId!=""){
                   customerTypeId=value;
             }
             	$.ajax({
 				async:false,
 				url:"rank_ajaxOperateExpend.action",
 				type:"POST",
 				data:"customerId=" + customerTypeId,
 				dataType:"json",
 				success:function(data){
 			          $("#scoreMin").val(data);
 			          $("#scoreMins").val(data);
 			 	}
 			});
 			getCode();
        }
       /** 通过客户类型获最新编号  **/ 
        function  getCode(){
             var customerTypeId = $("#customerTypeId").val();
             var customerSonId = $("#customer").html(); 
             var value = $("#customer").val();
             if(customerSonId!=""){
                   customerTypeId=value;
             }
             	$.ajax({
 				async:false,
 				url:"rank_ajaxOperateCode.action",
 				type:"POST",
 				data:"customerId=" + customerTypeId,
 				dataType:"json",
 				success:function(data){
 			          $("#code").val(data);
 			          $("#codes").val(data);
 			 	}
 			});
        }
         

</script>
<!-- 导航栏 -->
<s:set name="parent_name" value="'客户头衔'" scope="request"/>
<s:set name="name" value="'客户头衔'" scope="request"/>
<s:set name="son_name" value="'添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	<div  style="height:90%;overflow-y:scroll; " >
<s:form action="rank_operateSave.action" method="POST" id="rankAdd" namespace='/rank'   >
<s:token/>
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- 数据编辑区域 -->
<table width="80%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<td colspan="2" align="left" class="edit_title">添加客户头衔信息</td>
		<input name="rank.rankId" id="rankId" type="hidden"  value="<s:property value='rank.rankId'/>"/>
	</tr>
   
   <tr> 
		<td width="20%" align="right"><font color="red">*</font>头衔编号：</td>
		<td width="80%"> 
			<input name="codes" id="codes" disabled value="<s:property value='rank.code'/>"  type="text" />
			<input name="rank.code" id="code" value="<s:property value='rank.code'/>"  type="hidden" />
		    <span id="megcode"></span>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>头衔名称：</td>
		<td width="80%"> 
			<input name="rank.rankName"   id="rankName" />
		<span id="megrankName"></span>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>客户类型：</td>
		<td width="80%"> 
		<s:select name="customerTypeId" id="customerTypeId"  list="customerTypeList"  listKey="customerTypeId"   value="%{rank.customerTypeId}" listValue="name"  onchange="querySonCustomerType(this.value,null)"></s:select>
	        <select id="customer" name="customer_son_id" onchange="getExpendMin()" style="display:none;"></select>
	        <input type="hidden"  id="sonCustomerId" value="<s:property value='rank.customer_son_id'/>">
		     <input type="hidden"  id="parentId"  value="<s:property value='rank.customerTypeId'/>"/>
		</td>
	</tr>
	
	<tr> 
	 <td width="20%" align="right"><font color="red">*</font>积分/经验值最小值：</td>
		<s:if test="%{rank.rankId!=null}">
		<td width="80%"> 
			<input id="scoreMins" name="scoreMins" type="text" disabled="disabled"   value="<s:property value='rank.scoreMin'/>" />
			<input id="scoreMin" name="rank.scoreMin" type="hidden"    value="<s:property value='rank.scoreMin'/>" />
		</td>
		</s:if>
		<s:else>
		<td width="80%"> 
			<input id="scoreMins" name="scoreMins" type="text" disabled="disabled"   value="<s:property value='rank.scoreMin'/>" />
			<input id="scoreMin" name="rank.scoreMin" type="hidden"    value="<s:property value='rank.scoreMin'/>" />
		</td>
		</s:else>
	</tr>
	
    <tr> 
		<td width="20%" align="right"><font color="red">*</font>积分/经验值最大值：</td>
		<td width="80%"> 
			<input name="rank.scoreMax" id="scoreMax" type="text" />
		   <span id="megscoreMax"></span>
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

</s:form>
</div>   
<SCRIPT LANGUAGE="JavaScript">
function gotoList(){
    location.href="/rank/rank_queryPageList.action";
}

</SCRIPT>
</BODY>
<!-- 消息提示 -->
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>