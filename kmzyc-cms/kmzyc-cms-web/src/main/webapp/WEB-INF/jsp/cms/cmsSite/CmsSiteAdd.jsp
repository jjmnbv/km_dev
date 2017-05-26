<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加站点</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
 
</head>
<body>
 
 <!-- 导航栏 -->
 <script>
  $(document).ready(function(){
 	$("#addcmsSiteForm").validate({
         rules: {
				"cmsSite.name": {required:true,unique:true,maxlength:42},
				"cmsSite.engName": {required:true,unique:true,maxlength:42},
				"cmsSite.url":{required:true,maxlength:42},
				"cmsSite.templatePath":{required:true,maxlength:42},
				"cmsSite.pageOutputPath":{required:true,maxlength:42},
//				"cmsSite.channel":{required:true,maxlength:18},
				"cmsSite.remark":{maxlength:84}
	        	},
	     success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
 });
 jQuery.validator.addMethod("unique", function(value, element) {
 		var ok="";
	 	$.ajax({
	 		url:"/cms/cmsSite_check.action",
	 		async:false,
	 		data:"cmsSite.name="+value,
        	type:'post',
	 		success:function(result)
	 		{
	 			if(result==0)
	 			{
	 				ok=0;
	 			}
	 			else if(result==1)
	 			{
	 				ok=1;
	 			}
	 		}
	 	});
	 	if(ok==1)
	 	{
	 		return false;
	 	}else
	 	{
	 		return true;
	 	}
}, "该名字已存在!");

 function gotoList(){
    window.location.href="/cms/cmsSite_queryForPage.action";
 }
function ReplaceDot(obj)
{
var oldValue=obj.value;
while(oldValue.indexOf("，")!=-1)//寻找每一个中文逗号，并替换
{
obj.value=oldValue.replace("，",",");
oldValue=obj.value;
}
obj.value = oldValue;
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
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;站点管理&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;添加站点
	  </span>
</div>
<div  style="height:95%;" >
<s:form  id="addcmsSiteForm" name="addcmsSiteForm" action="/cms/cmsSite_addCmsSite.action" method="POST" enctype="multipart/form-data">
<s:token></s:token>
<input type="hidden" name="cmsSite.siteId" value="" id="siteId"/>
<input type="hidden" name="pageNo" id="pageNo" value="<s:property value="pageNo"/>"/>
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">

	<tr> 
		<th colspan="2" align="left" class="edit_title">站点信息</th>
	</tr>
	<!-- error message -->
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>站点名称：</td>
		<td width="80%">
			<input name="cmsSite.name" id="name" type="text" value=""/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>站点英文名称：</td>
		<td width="80%">
			<input name="cmsSite.engName" id="engName" type="text" value=""/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>站点域名：</td>
		<td width="80%">
			<input name="cmsSite.url" id="url" type="text" value=""/>
		</td>
	</tr>
   <tr> 
		<td width="20%" align="right"><font color="red">*</font>页面模板路径：</td>
		<td width="80%">
			<input name="cmsSite.templatePath" id="templatePath" type="text" value=""/>
		</td>
	</tr>
		<tr> 
		<td width="20%" align="right"><font color="red">*</font>页面输出路径：</td>
		<td width="80%">
			<input name="cmsSite.pageOutputPath" id="pageOutputPath" type="text" value=""/>
		</td>
	</tr>
	<%--<tr> --%>
		<%--<td width="20%" align="right"><font color="red">*</font>渠道信息：</td>--%>
		<%--<td width="80%">--%>
			<%--<input name="cmsSite.channel" id="pageOutputPath" type="text" value=""/><!-- *如果要添加多个渠道请以英文逗号隔开,如B2B,ZYC -->--%>
		<%--</td>--%>
	<%--</tr>--%>
	<tr> 
		<td width="20%" align="right">状态：</td>
		<td width="80%">
			<select name="cmsSite.status">
	        <option value="0" selected="selected">有效</option>
	        <option value="1"  >无效</option>
	        </select>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">备注：</td>
		<td width="80%"> 
			<textarea name="cmsSite.remark" class="newWidth" id="remark"></textarea>
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
</s:form >
</div>
</BODY>
</HTML>