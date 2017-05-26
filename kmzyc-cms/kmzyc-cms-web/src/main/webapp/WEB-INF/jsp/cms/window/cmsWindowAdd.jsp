<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加窗口</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>

<link rel="stylesheet" href="/etc/js/codemirror/codemirror.css">
<link rel="stylesheet" href="/etc/js/codemirror/fullscreen.css">
<link rel="stylesheet" href="/etc/js/codemirror/erlang-dark.css">
<script src="/etc/js/codemirror/codemirror.js"></script>
<script src="/etc/js/codemirror/xml.js"></script>
<script src="/etc/js/codemirror/fullscreen.js"></script>
<script src="/etc/js/codemirror/userdefined.js"></script>
 
</head>
<body>
 
 <!-- 导航栏 -->
 <script>
  $(document).ready(function(){
 	$("#addWindowForm").validate({
         rules: {
				"cmsWindow.name": {required:true,unique:true,maxlength:42},
				"cmsWindow.theme":{required:true,maxlength:42},
				"cmsWindow.content":{required:true},
				"cmsWindow.remark":{maxlength:84}
	        	},
	     success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
 });
 jQuery.validator.addMethod("unique", function(value, element) {
 		var ok="";
	 	$.ajax({
	 		url:"/cms/cmsWindowAction_check.action",
	 		async:false,
	 		data:"cmsWindow.name="+value,
        	type:'post',
	 		success:function(result)
	 		{
	 			if(result=="success")
	 			{
	 				ok="success";
	 			}
	 			else if(result=="fail")
	 			{
	 				ok="fail";
	 			}
	 		}
	 	});
	 	if(ok=="fail")
	 	{
	 		return false;
	 	}else
	 	{
	 		return true;
	 	}
}, "该名字已存在!");
	function gotoList(){
	   //var pageNo=$("#pageNo").val();
	   //location.href="/cms/cmsWindowAction_queryWindowByKey.action";
	   var pageForm= window.document.getElementById("addWindowForm");
	   pageForm.action="/cms/cmsWindowAction_queryWindowPage.action"; 
 	   pageForm.submit();
	}
	$(function(){
	 	$(".selBtn").click(function(){
	 		dialog("选择模板","iframe:/cms/cmsWindowAction_gotoSelPage.action?cmsTemplate.type=0" ,"900px","530px","iframe"); 
	 	});
	});
 	function closeOpenDialog(content)
 	{
        closeThis();
        var json = eval('(' + content + ')'); 
        editor.setValue(json.content);
 		$("#templateId").val(json.templateId);
    }
 </script>
 

<style type="text/css">
.listTitle{height:40px;line-height:40px;background:#c7e3f1;border-bottom:1px solid #c7e3f1;vertical-align:middle;font-size:14;color:#333;margin:0}
.listTitle span{padding-left:20px;height:30px;line-height:30px;vertical-align:middle;margin-top:5px}
.listTitle span img{vertical-align:middle}
</style>
<!-- 导航栏 -->
<div width="100%" class="listTitle"  height="40">
    <span>
	<img src="/etc/images/icon_02.png"  />&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;基础功能&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;窗口管理&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;添加窗口
	  </span>
</div>
<div  style="height:95%;" >
<s:form id="addWindowForm" name="addWindowForm" action="/cms/cmsWindowAction_addWindow.action" method="POST" enctype="multipart/form-data">
<s:token></s:token>
<input type="hidden" name="cmsWindow.templateId" value="" id="templateId"/>
<input type="hidden" name="pageNo" id="pageNo" value="<s:property value="pageNo"/>"/>
<!-- keyWords -->
<input type="hidden" name="keyWords.id_keyword" value="<s:property value='keyWords.id_keyword'/>">
<input type="hidden" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
<input type="hidden" name="keyWords.theme_keyword" value="<s:property value='keyWords.theme_keyword'/>">
<input type="hidden" name="keyWords.status_keyword" value="<s:property value='keyWords.status_keyword'/>"/>
<input type="hidden" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
<input type="hidden" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
<input type="hidden" name="keyWords.type_keyword" value="<s:property value='keyWords.type_keyword'/>">
<input type="hidden" name="adminType" id="adminType" value="<s:property value='adminType'/>">
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">

	<tr> 
		<th colspan="2" align="left" class="edit_title">窗口信息</th>
	</tr>
	<!-- error message -->
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>窗口名称：</td>
		<td width="80%">
			<input name="cmsWindow.name" id="name" type="text" value=""/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>状态：</td>
		<td width="80%">
			<select name="cmsWindow.status">
	        <option value="0" selected="selected">有效</option>
	        <option value="1">无效</option>
	        </select>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">数据类型：</td>
		<td width="80%">
			<select name="cmsWindow.paramsType">
	        <option value="0" >系统传参</option>
	        <option value="1"  selected="selected">人工绑定</option>
	         <option value="2" >运营类目</option>
	        </select>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>窗口主题：</td>
		<td width="80%"> 
			<input name="cmsWindow.theme" id="theme" type="text" value="" class="newWidth"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">模板选择：</td>
		<td width="80%"> 
			<INPUT TYPE="button" value="选择" onclick="" class="selBtn" style="cursor: pointer;">
		</td>
	</tr>

	
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>窗口内容：</td>
		
		<td width="80%"> 
			当光标在编辑器中，按F11键切换全屏幕编辑。ESC可退出全屏幕编辑。
		</td>
	</tr>
	<tr> 
		<td colspan="2">
			<textarea   name="cmsWindow.content" id="content" cols="100" rows="8" style="height:300px;"></textarea>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">备注：</td>
		<td width="80%"> 
			<textarea name="cmsWindow.remark" class="newWidth" id="remark"></textarea>
		</td>
	</tr>
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<INPUT class="saveBtn" TYPE="submit" value="">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value="">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form>
</div>
</BODY>
</HTML>