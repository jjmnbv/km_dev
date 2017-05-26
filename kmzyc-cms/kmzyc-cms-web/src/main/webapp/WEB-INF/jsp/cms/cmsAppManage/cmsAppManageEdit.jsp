<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>手机端应用修改</title>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
 <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
 <script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script src="/etc/js/dialog.js"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 
<style type="text/css">
.listTitle{height:40px;line-height:40px;background:#c7e3f1;border-bottom:1px solid #c7e3f1;vertical-align:middle;font-size:14;color:#333;margin:0}
.listTitle span{padding-left:20px;height:30px;line-height:30px;vertical-align:middle;margin-top:5px}
.listTitle span img{vertical-align:middle}
.list_table tr input
			{
				width:220px;
			}
</style>
			
	</head>
	<body>
	<!-- 导航栏 -->
<div width="100%" class="listTitle"  height="40">
    <span>
	<img src="/etc/images/icon_02.png"  />&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;基础功能&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;手机端应用列表&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;修改手机端应用
	  </span>
</div>

		<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="dateForm" id="dateForm" action="/cms/cmsAppManageAction_editAppManage.action" method="post" enctype="multipart/form-data">
		<s:token></s:token>
			<!-- 数据列表区域 -->
			<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="edit_title">手机端应用信息</th>
	</tr>
	<!-- error message -->
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>手机端应用名称：</td>
		<td width="80%">
			<input name="cmsAppManage.appmanageId" id="appmanageId" type="hidden" value="<s:property value='cmsAppManage.appmanageId'/>"/>
			<select name="cmsAppManage.name" id="appName" onchange="getDownload();">
				 <s:if test='%{cmsAppManage.name=="ios版本"}'>
					<option value="ios版本" selected="selected">ios版本</option>
					<option value="android版本">android版本</option>
				</s:if> 
				<s:elseif test='%{cmsAppManage.name=="android版本"}'>
					<option value="ios版本">ios版本</option>
					<option value="android版本" selected="selected">android版本</option>
				</s:elseif> 
			</select>
			<%-- <input name="cmsAppManage.name" id="name" type="text" value="<s:property value='cmsAppManage.name'/>"/><span id="nameMsg" style="display:none;margin-left:5px;color:red;">手机端应用名称不能为空</span> --%>
		</td>
	</tr>
 	<tr> 
		<td width="20%" align="right"><font color="red">*</font>版本名：</td>
		<td width="80%">
		<input name="cmsAppManage.version" id="name" type="text" value="<s:property value='cmsAppManage.version'/>"/><span id="nameMsg" style="display:none;margin-left:5px;color:red;">版本名不能为空</span>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>版本号：</td>
		<td width="80%">
		<input name="cmsAppManage.versioncode" id="name" type="text" value="<s:property value='cmsAppManage.versioncode'/>"/><span id="nameMsg" style="display:none;margin-left:5px;color:red;">版本号不能为空</span>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">下载地址：</td>
		<td width="80%">
			<%-- <input type="file" name="resumefile" id="resumefile" style=" border: 1px #6699CC solid;">
			<input name="cmsAppManage.download" id="downloadMsg" type="text" value="<s:property value='cmsAppManage.download'/>"/><span id="downloadMsg" style="display:none;margin-left:5px;color:red;">下载地址不能为空</span> --%>
			<div id="topAndor">
			</div>
			<div id="topIos"></div>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>是否强制下载：</td>
		<td width="80%"> 
			<select name="cmsAppManage.isCoerce">
			<s:if test="cmsAppManage.isCoerce == 2">
			<option value="2" selected="selected">否</option>
			<option value="1">是</option>
			</s:if>
			<s:elseif test="cmsAppManage.isCoerce == 1">
				<option value="1" selected="selected">是</option>
				<option value="2">否</option>
			</s:elseif>
			
			
			</select>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>手机端平台：</td>
		<td width="80%"> 
			<%-- <input name="cmsAppManage.terrace" id="terrace" type="text" value="<s:property value='cmsAppManage.terrace'/>" class="newWidth"/> --%>
			<select name="cmsAppManage.terrace">
				<s:if test='%{cmsAppManage.terrace=="ios"}'>
					<option value="ios" selected="selected">ios</option>
					<option value="android">android</option>
				</s:if>
				<s:elseif test='%{cmsAppManage.terrace=="android"}'>
					<option value="ios">ios</option>
					<option value="android" selected="selected">android</option>
				</s:elseif>
			</select>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">升级的功能列表信息：</td>
		<td width="80%"> 
			<textarea name="cmsAppManage.remark" style="height:300px;width:80%" id="remark"><s:property value='cmsAppManage.remark'/></textarea>
		</td>
	</tr>
</table>
			<!-- 底部 按钮条 -->
			<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
				<tr> 
					<td align="left">
						
						<INPUT class="saveBtn" TYPE="submit" value="">
						<input class="backBtn" type="button" value="" onclick="gotoList()">
					</td>
				</tr>
			</table>
		</s:form>
		
		
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
	<script type="text/javascript">
	$(document).ready(function(){
		getDownload();
	 	$("#dateForm").validate({
	         rules: {
					"cmsAppManage.name": {required:true,maxlength:42},
					"cmsAppManage.version":{required:true,maxlength:42},
					"cmsAppManage.versioncode":{required:true,maxlength:10,digits:true},
					"cmsAppManage.isCoerce":{required:true},
					"cmsAppManage.terrace":{required:true,maxlength:42},
					"cmsAppManage.remark":{maxlength:120}
		        	},
		     success: function (label){
		            label.removeClass("checked").addClass("checked");
		            }
	          });
	 });
	
	function getDownload(){
		var appName = $("#appName option:selected").val();
		if(appName == 'ios版本'){
			$("#topAndor").empty();
			var html = '<input name="cmsAppManage.download" id="downloadMsg" type="text" value="<s:property value="cmsAppManage.download"/>"/><span id="downloadMsg" style="display:none;margin-left:5px;color:red;">下载地址不能为空</span>';
			$("#topIos").append(html);
		}else{
			$("#topIos").empty();
			$("#topAndor").append('<input name="cmsAppManage.download" id="download" type="hidden" value="<s:property value="cmsAppManage.download"/>"/><input type="file" name="resumefile" id="resumefile" style=" border: 1px #6699CC solid;">');
		}
	}

    //返回
	   function gotoList(id){
	  	var pageForm= window.document.getElementById("dateForm");
	 	pageForm.action="/cms/cmsAppManageAction_queryPageList.action";
	 	pageForm.submit();
	  }
</script>
</html>

