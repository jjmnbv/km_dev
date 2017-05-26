<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改模板</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script src="/etc/js/dialog.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
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
 
 
 	$("#editTemplateForm").validate({
         rules: {
				"cmsTemplate.name": {required:true,unique:true,maxlength:42},
				"cmsTemplate.theme":{required:true,maxlength:42},
				"cmsTemplate.content":{required:true},
				"cmsTemplate.remark":{maxlength:84}
	        	},
	     success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
 });
 
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
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;模板管理&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;修改模板
	  </span>
</div>
<div  style="height:90%;" >
<s:form id="editTemplateForm" name="editTemplateForm" action="/cms/cmsTemplateAction_editTemplate.action" method="POST" enctype="multipart/form-data">
<s:token></s:token>
<!-- keyWords -->
<input type="hidden" name="keyWords.id_keyword" value="<s:property value='keyWords.id_keyword'/>">
<input type="hidden" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
<input type="hidden" name="keyWords.theme_keyword" value="<s:property value='keyWords.theme_keyword'/>">
<input type="hidden" name="keyWords.status_keyword" value="<s:property value='keyWords.status_keyword'/>"/>
<input type="hidden" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
<input type="hidden" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
<input type="hidden" name="keyWords.type_keyword" value="<s:property value='keyWords.type_keyword'/>">
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="edit_title">模板信息</th>
	</tr>
	<!-- error message -->
	<input name="cmsTemplate.templateId" id="templateId" type="hidden" value="<s:property value="cmsTemplate.templateId"/>"/>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>模板名称：</td>
		<td width="80%">
			<input name="cmsTemplate.name" id="name" type="text" value="<s:property value="cmsTemplate.name"/>"/>
		</td>
	</tr>
    <tr> 
		<td width="20%" align="right"><font color="red">*</font>模板主题：</td>
		<td width="80%"> 
			<input name="cmsTemplate.theme" id="theme" type="text" value="<s:property value="cmsTemplate.theme"/>" class="newWidth"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>状态：</td>
		<td width="80%">
			<select name="cmsTemplate.status">
	        <option value="0" <s:if test="cmsTemplate.status==0">selected="selected"</s:if>>有效</option>
	        <option value="1" <s:if test="cmsTemplate.status==1">selected="selected"</s:if>>无效</option>
	        </select>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>模板类型：</td>
		<td width="80%">
			<select name="cmsTemplate.type" >
	        <option value="13" <s:if test="cmsTemplate.type==0">selected="selected"</s:if>>窗口变量模板</option>
	        <option value="12" <s:if test="cmsTemplate.type==12">selected="selected"</s:if>>页面变量模板</option>
	        </select>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>模板内容：</td>
		
		<td width="80%"> 
			当光标在编辑器中，按F11键切换全屏幕编辑。ESC可退出全屏幕编辑。
		</td>
	</tr>
	<tr> 
		<td colspan="2">
			<textarea   name="cmsTemplate.content" id="content" cols="100" rows="8" style="height:300px;"><s:property value="cmsTemplate.content"/></textarea>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">备注：</td>
		<td width="80%"> 
			<textarea name="cmsTemplate.remark" id="remark"><s:property value="cmsTemplate.remark"/></textarea>
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
			&nbsp;&nbsp;
			<s:if test="cmsTemplate.type==2">
			<INPUT class="saveBtn" TYPE="button" value="全部更新" onclick="allUpdateTemp()">
			</s:if>
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form>




</div>

</BODY>
<script type="text/javascript">
//名字惟一性
jQuery.validator.addMethod("unique", function(value, element) {
 		var ok="";
 		var templateId=$("#templateId").val();
	 	$.ajax({
	 		url:"/cms/cmsTemplateAction_check.action?cmsTemplate.templateId="+templateId,
	 		async:false,
	 		data:{"cmsTemplate.name":value},
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
//返回
 function gotoList(){
   var pageForm= window.document.getElementById("editTemplateForm");
 	pageForm.action="/cms/cmsTemplateAction_queryTemplate.action";
 	pageForm.submit();
 }
function allUpdateTemp(){
	  var pageForm= window.document.getElementById("editTemplateForm");
 	pageForm.action="/cms/cmsTemplateAction_updateAdvTemplate.action";
 	pageForm.submit();
	
}
</script>
</HTML>