<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>添加消息信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript">
		 $(document).ready(function(){
		 var customError = "";
			$.validator.addMethod("myvalidator", function(value, element) {
			   var returnVal = true;
			    var rtl=document.getElementById("status");
  				var i = rtl.value;
  				var d2=new Date();
				var d3=d2.getFullYear()+"-"+(d2.getMonth()+1)+"-"+d2.getDate();
				var d4=new Date(d3.replace(/-/g, "/"));
				var d1=new Date(value.replace(/-/g, "/"));
  				if(i==0){
  				   if(Date.parse(d4) - Date.parse(d1)>0){
  				customError = "待发布信息，发布日期不能小于当前系统日期";
					returnVal = false;
					}
  				}
  				if(i==1){
  				   if(Date.parse(d4) - Date.parse(d1)<0){
  				    customError = "已发布信息，发布日期不能大于当前系统日期";
					returnVal = false;
					}
  				}
				$.validator.messages.myvalidator = customError;
				return returnVal;
			}, "error " + customError);
          $("#publishMessage").validate({
               rules: {
					"title":{required:true,maxlength:50},
					"content":{required:true,maxlength:150},
					"type":{required:true},
					"status":{required:true},
					"isTime":{required:true},
					"releaseDate":{required:true,myvalidator:true},
					"messagePlatform":{required:true}
	        	},
	         success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
        });
        
        function gotoList(){
        	 location.href= "/acctBusiness/publishMessage_list.action";
        }
        
          /**通过customer类别id 查询子客户类别 **/
        function querySonCustomerType(id,value){
             	$.ajax({
 				async:false,
 				url:"/growing/userLevel_ajaxOperateCustomerType.action",
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
 			//getExpendMin();
        }     
        //弹出 多选账号页面
function popUpUserInfo() {
    dialog("选择账号","iframe:/logininfo/logininfo_checkboxInfoList.action?callBack=closeOpenUserInfo" ,"900px","450px","iframe");
}
//关闭弹出窗口 
function closeOpenUserInfo(loginIds,loginNames){
    closeThis();
     $("#loginAccount").val("");
     $("#n_LoginId").val("");
    $("#loginAccount").val(loginNames);
     $("#n_LoginId").val(loginIds);
}
//消息发布对象控制
function showType(val){
      if(val==1){
           $("#customerShow").show();
           $("#personInfo").hide();
      }
      if(val==2){
          $("#personInfo").show();
          $("#customerShow").hide();
      }
}
function cole() {
     var releaseDate =document.getElementById("releaseDate");
    // releaseDate.value="";
     releaseDate.focus();
	} 
//布类型的日期校验
function getselectvalue() {
    var rtl=document.getElementById("status");
  var i = rtl.value;
  if(i==1){
  WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:(new Date())})
  }else{
  WdatePicker({dateFmt:'yyyy-MM-dd',minDate:(new Date())})
  }
	} 
</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户业务'" scope="request" />
		<s:set name="name" value="'消息信息'" scope="request" />
		<s:set name="son_name" value="'添加'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	   <div  style="height:90%;overflow-y:scroll; " >
		<s:form action="publishMessage_add.action" method="POST" 
			id="publishMessage" namespace='acctBusiness' name="publishMessage">
<s:token/>
			<!-- hidden properties -->
			<INPUT TYPE="hidden" name="isEnable" value="1">
			<!-- 数据编辑区域 -->
			<table width="80%" class="edit_table" cellpadding="3" cellspacing="0"
				border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<!-- error message -->
				<s:if test="rtnMessage != null">
					<tr>
						<td colspan="2" align="center">
							<font color="red"><s:property value='rtnMessage' /> </font>
						</td>
					</tr>
				</s:if>

				<tr>
					<td width="20%" align="right">
						<font color="red">*</font>消息标题：
					</td>
					<td width="80%">
						 <input type="text" name="title" id="title" size="65"/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right"> 
						<font color="red">*</font>消息内容：
					</td>
					<td width="80%">
						<textarea rows="5" cols="20" name="content" id="content"></textarea>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<font color="red">*</font>消息类型：
					</td>
					<td width="80%">
						<select name="type"  id="type">
						   	<option value="">
								请选择
							</option>
							<option value="1">
								安全消息
							</option>
							<option value="2">
								产品信息
							</option>
							<option value="3">
								订单信息
							</option>
							<option value="4">
								审核信息
							</option>
						</select>
					</td>
				</tr>	
				<tr>
					<td width="20%" align="right">
						消息发布对象：
					</td>
					<td width="80%">
					      <s:select  list="#{1:'按客户类别发布',2:'按具体客户发布'}" value="1"  onchange="showType(this.value)" name="releaseObject"></s:select>
					</td>
				</tr>			
				<tr  id="customerShow"  style="display:none;"> 
						<td width="20%" align="right">
						客户类别：
					</td>
						<td width="80%" > 
						       <s:select id="customerType" name="customerType"  list="customerTypeList"  listKey="customerTypeId"   value="%{userLevel.n_customer_type_id}" listValue="name"  onchange="querySonCustomerType(this.value,null)"></s:select>
	                          <select id="customer" name="customerTypeSonId"  style="display:none;" ></select>
	                          <input type="hidden"  id="sonCustomerId"  >
		                      <input type="hidden"  id="parentId"  />
						</td>	
				</tr>
					<tr id="personInfo"   style="display:none;">
					<td width="20%" align="right">
						指定个人：
					</td>
					<td width="80%">
					      <input id="n_LoginId" type="hidden" value="" name="loginId">
                          <input id="loginAccount" type="text" value="" name="loginAccount" readonly="readonly">
                          <input type="button"  value="选择" onclick="popUpUserInfo()"/>
					</td>
				</tr>
					<tr>
					<td width="20%" align="right">
						<font color="red">*</font>发布状态：
					</td>
					<td width="80%">
						<select name="status" id="status" onchange="cole()">		
						    <option value="">
								请选择
							</option>
							<option value="0">
								待发布
							</option>
							<option value="1">
								已发布
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<font color="red">*</font>发布日期：
					</td>
					<td width="80%">
						<input type="text" name="releaseDate"  id="releaseDate" value="" onClick="getselectvalue()" readonly="readonly">
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<font color="red">*</font>是否定时发布：
					</td>
					<td width="80%">
						<select name="isTime" id="isTime">
						    <option value="">
								请选择
							</option>
							<option value="0">
								否
							</option>
							<option value="1">
								是
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<font color="red">*</font>发送平台：
					</td>
					<td width="80%">
						<select name="messagePlatform" id="messagePlatform">
						    <option value="">
								请选择
							</option>
							<option value="1">
								b2b平台
							</option>
							<option value="2">
								供应商平台
							</option>
							<!-- <option value="3">
								云商平台
							</option> -->
						</select>
					</td>
				</tr>
			</table>
			<!-- 底部 按钮条 -->
			<table width="60%" class="edit_bottom" height="30" border="0"
				cellpadding="0" cellspacing="0">
				<tr>
					<td align="left">
						<input class="saveBtn" type="submit" value=" ">
						&nbsp;&nbsp;
						<input class="backBtn" onclick="gotoList();" type="button"
							value=" ">
					</td>
					<td width="20%" align="center"></td>
				</tr>
			</table>

			<br>
			

		</s:form>
	  </div>
	</BODY>
</HTML>