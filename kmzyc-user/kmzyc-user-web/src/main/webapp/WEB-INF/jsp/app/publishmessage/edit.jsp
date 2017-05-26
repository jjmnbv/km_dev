<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>修改消息信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />
		<script src="/etc/js/dialog.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js">
</script>
		<script type="text/javascript" src="/etc/js/messages_cn.js">
</script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script src="/etc/js/dialog.js">
</script>
		<script type="text/javascript">
$(document).ready(function() {

      var releaseObject = $("#releaseObject").val();
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
    if(releaseObject==1){
    	
        
		var sonId = $("#sonCustomerId").val();
		var parentId = $("#parentId").val();
		if (sonId != "") {
	
			querySonCustomerType(parentId, sonId);
		}
    }

	$("#publishMessageForm").validate( {
		rules : {
			"bnesInfoPrompt.title" : {
				required : true
			},
			"bnesInfoPrompt.content" : {
				required : true
			},
			"bnesInfoPrompt.type" : {
				required : true
			},
			"bnesInfoPrompt.status" : {
				required : true
			},
			"bnesInfoPrompt.isTime" : {
				required : true
			},
			"bnesInfoPrompt.releaseDate":{
			required:true,myvalidator:true}
		},
		success : function(label) {
			label.removeClass("checked").addClass("checked");
		}
	});

});



/**通过customer类别id 查询子客户类别 **/
function querySonCustomerType(id, value) {
	$.ajax( {
		async : false,
		url : "/growing/userLevel_ajaxOperateCustomerType.action",
		type : "POST",
		data : "customerId=" + id,
		dataType : "json",
		success : function(data) {
			if ((data != null || data != "") && data.length > 0) {
				$("#customer option").remove();
				$("#customer").show();
				for ( var i = 0; i < data.length; i++) {
					if (value != "" && value == data[i].customerTypeId) {
						$("#customer").append(
								"<option value='" + data[i].customerTypeId
										+ "' selected>" + data[i].name
										+ "</option>");
					} else {
						$("#customer").append(
								"<option value='" + data[i].customerTypeId
										+ "'>" + data[i].name + "</option>");
					}
				}
			} else {
				$("#customer option").remove();
				$("#customer").hide();
			}
		}
	});
	//getExpendMin();
}

//弹出 多选账号页面
function popUpUserInfo() {
	dialog(
			"选择账号",
			"iframe:/logininfo/logininfo_checkboxInfoList.action?callBack=closeOpenUserInfo",
			"900px", "500px", "iframe");
}
//关闭弹出窗口 
function closeOpenUserInfo(loginIds, loginNames) {
	closeThis();
	//   $("#loginAccount").val("");
	//   $("#n_LoginId").val("");
	$("#loginAccount").val(loginNames);
	$("#n_LoginId").val(loginIds);
	//   alert("loginIds:"+loginIds+"loginNames:"+loginNames);
}
function cole() {
     var releaseDate =document.getElementById("releaseDate");
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
		<s:set name="son_name" value="'修改'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: scroll;">
			<INPUT TYPE="hidden" name="isEnable" value="1">
			<s:form action="publishMessage_edit.action" id="publishMessageForm"
				method="POST" namespace='/messageCenter'>
				<s:token/>
				<!-- 数据编辑区域 -->
				<table width="80%" class="edit_table" cellpadding="3"
					cellspacing="0" border="1" bordercolor="#C7D3E2"
					style="border-collapse: collapse">
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
							<input type="hidden" name="bnesInfoPrompt.infoPromptId"
								value="<s:property value='bnesInfoPrompt.infoPromptId'/>">
							<input type="text" name="bnesInfoPrompt.title" id="title"
								value="<s:property value='bnesInfoPrompt.title' />" />

						</td>
					</tr>
					<tr>
						<td width="20%" align="right">
							<font color="red">*</font>消息内容：
						</td>
						<td width="80%">
							<textarea rows="5" cols="20" name="bnesInfoPrompt.content"><s:property value='bnesInfoPrompt.content' /></textarea>
						</td>
					</tr>
					<tr>
						<td width="20%" align="right">
							<font color="red">*</font>消息类型：
						</td>
						<td width="80%">

							<select name="bnesInfoPrompt.type">
								<option value="1"
									<s:if test="bnesInfoPrompt.type==1">selected</s:if>>
									安全消息
								</option>
								<option value="2"
									<s:if test="bnesInfoPrompt.type==2">selected</s:if>>
									产品信息
								</option>
								<option value="3"
									<s:if test="bnesInfoPrompt.type==3">selected</s:if>>
									订单信息
								</option>
							</select>
						</td>
					</tr>
						<input type="hidden" value="<s:property value='bnesInfoPrompt.releaseObject'/>" id="releaseObject"/>
					<s:if test="bnesInfoPrompt.releaseObject==1">
						<tr>
							<td width="20%" align="right">
								客户类别
							</td>
							<td width="80%">
							
							
							  	<s:select id="customerType" name="customerType"  list="customerTypeList"  listKey="customerTypeId"   value="%{customerTypeId}" listValue="name"  onchange="querySonCustomerType(this.value,null)"></s:select>
	        <select id="customer" name="customerTypeSonId"  style="display:none;" ></select>
	         <input type="hidden"  id="sonCustomerId" value="<s:property value='customerTypeSonId'/>">
		     <input type="hidden"  id="parentId"  value="<s:property value='customerTypeId'/>"/>
		     
		     
							</td>
						</tr>
					</s:if>
					<s:if test="bnesInfoPrompt.releaseObject==2">
						<tr>
							<td width="20%" align="right">
								指定个人：
							</td>
							<td width="80%">
								<input id="n_LoginId" type="hidden"
									value="<s:property value='loginIds'/>" name="loginIds">
								<input id="loginAccount" type="text"
									value="<s:property value='loginNames'/>" name="loginNames"
									readonly="readonly">
								<input type="button" value="选择" onclick="popUpUserInfo();" />
							</td>
						</tr>
					</s:if>

					<tr>
						<td width="20%" align="right">
							<font color="red">*</font>发布状态：
						</td>
						<td width="80%">
							<select name="bnesInfoPrompt.status" id="status" onchange="cole()">
								<option value="1"
									<s:if test="bnesInfoPrompt.status==1">selected</s:if>>
									已发布
								</option>
								<option value="0"
									<s:if test="bnesInfoPrompt.status==0">selected</s:if>>
									待发布
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="20%" align="right">
							<font color="red">*</font>发布日期：
						</td>
						<td width="80%">
							<input type="text" name="bnesInfoPrompt.releaseDate" 
								value="<s:date name="bnesInfoPrompt.releaseDate" format="yyyy-MM-dd" />"
								onClick="getselectvalue()" id="releaseDate" readonly="readonly">
						</td>
					</tr>
					<tr>
						<td width="20%" align="right">
							<font color="red">*</font>是否定时发布：
						</td>
						<td width="80%">
							<select name="bnesInfoPrompt.isTime">
								<option value="0"
									<s:if test="bnesInfoPrompt.isTime==0">selected</s:if>>
									否
								</option>
								<option value="1"
									<s:if test="bnesInfoPrompt.isTime==1">selected</s:if>>
									是
								</option>
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
							<input class="backBtn" onclick="gotoList()" type="button"
								value=" ">
						</td>
						<td width="20%" align="center"></td>
					</tr>
				</table>

				<br>
				<br>

			</s:form>
		</div>
		<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoList(){
    location.href="/acctBusiness/publishMessage_list.action";
}

</SCRIPT>
	</BODY>
</HTML>