<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改专题页</title>
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
 	$("#editPageForm").validate({
         rules: {
				"cmsPage.name": {required:true,unique:true,maxlength:42},
				"cmsPage.outputPath":{required:true,compareTo:true,maxlength:42},
				"cmsPage.title":{required:true,maxlength:300},
				"cmsPage.content":{required:true},
				"cmsPage.seo":{maxlength:300},
				"cmsPage.keywords":{maxlength:300},
				"cmsPage.describe":{maxlength:300},
				"cmsPage.remark":{maxlength:84}
	        	},
	     success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
 });
jQuery.validator.addMethod("unique", function(value, element) {
 		var ok="";
 		var pageId=$("#pageId").val();
	 	$.ajax({
	 		url:"/cms/cmsPageAction_check.action",
	 		async:false,
        	type:'post',
	 		data:"cmsPage.name="+value+"&cmsPage.pageId="+pageId,
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
jQuery.validator.addMethod("compareTo", function(value, element) {  
      var id=$("#pageId").val();
       var ok="";
        $.ajax({
        url:'/cms/cmsPromotion_outPutValidate.action',
        data:{"outPut":value,"outPutType":1,"promotionId":id},
        async:false,
        type:'post',
        success:function (d) {
         if(d=="0"){
         	ok="0";
         }else{
         	ok="1";
         }
        }
       
	    });
	    if(ok=="0"){
	    return true;
	    }else{
	    return false;
	    }
		
    },"该路径已存在 ");
	
	function gotoList(id){
   var pageNo=$("#pageNo").val();
  if(id==0){
  location.href="/cms/cmsPageAction_queryPage.action?adminType=0";
  }else{
  //location.href="/cms/cmsPageAction_queryPage.action";
  	var pageForm= window.document.getElementById("editPageForm");
 	pageForm.action="/cms/cmsPageAction_queryForPage.action";
 	pageForm.submit();
  }}
    function Submit(){
  
		$("form").submit();

   }
 
	
	
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
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;页面管理&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;页面修改
	  </span>
</div>
<div  style="height:95%;" >
<s:form  id="editPageForm" name="editPageForm" action="/cms/cmsPageAction_editPage.action" method="POST" enctype="multipart/form-data">
<!-- hidden properties -->
<s:token></s:token>
<input type="hidden" id="pageId" name="cmsPage.pageId" value="<s:property value="cmsPage.pageId"/>" />
<input type="hidden" name="cmsPage.templateId" value="<s:property value="cmsPage.templateId"/>" id="templateId">
<input type="hidden" name="cmsPage.siteId" value="<s:property value="cmsPage.siteId"/>" id="stylesId">
<input type="hidden" name="pageNo" id="pageNo" value="<s:property value="pageNo"/>"/>
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- keyWords -->
<input type="hidden" name="keyWords.id_keyword" value="<s:property value='keyWords.id_keyword'/>">
<input type="hidden" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
<input type="hidden" name="keyWords.outPath_keyword" value="<s:property value='keyWords.outPath_keyword'/>">
<input type="hidden" name="keyWords.status_keyword" value="<s:property value='keyWords.status_keyword'/>"/>
<input type="hidden" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
<input type="hidden" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">

	<tr> 
		<th colspan="2" align="left" class="edit_title">页面信息</th>
	</tr>
	<!-- error message -->
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>页面名称：</td>
		<td width="80%">
			<input name="cmsPage.name" id="name" type="text" value="<s:property value="cmsPage.name"/>"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>发布状态：</td>
		<td width="80%">
			<select name="cmsPage.status">
	        <option value="0" <s:if test="cmsPage.status==0">selected="selected"</s:if>>未发布</option>
	        <option value="1" <s:if test="cmsPage.status==1">selected="selected"</s:if>>已发布</option>
	        <option value="2" <s:if test="cmsPage.status==2">selected="selected"</s:if>>已修改</option>
	        </select>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>发布类型：</td>
		<td width="80%">
			
	        <s:if test="cmsPage.publicType==9">
	        	动态专题页
	        </s:if>
	        <input name="cmsPage.publicType" id="publicType" value="9" type="hidden" />
		</td>
	</tr>
	<s:if test="adminType!=0">
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>页面输出路径：</td>
		<td width="80%"> 
			<input name="cmsPage.outputPath" id="outputPath" type="text" value="<s:property value="cmsPage.outputPath"/>" class="newWidth"/>
		</td>
	</tr>
	</s:if>
	<s:else>
	    	<tr> 
		<td width="20%" align="right"><font color="red">*</font>页面输出路径：</td>
		<td width="80%"> 
			<input name="cmsPage.outputPath" id="outputPath" type="text" value="<s:property value="cmsPage.outputPath"/>" class="newWidth" readonly="readonly"/>
		</td>
	</tr>
	
	</s:else>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>页面标题：</td>
		<td width="80%"> 
			<input name="cmsPage.title" id="title" type="text" value="<s:property value="cmsPage.title"/>" class="newWidth"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">搜索引擎优化：</td>
		<td width="80%"> 
			<input name="cmsPage.seo" id="contactsName" type="text" value="<s:property value="cmsPage.seo"/>" class="newWidth"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">关键字：</td>
		<td width="80%"> 
			<input name="cmsPage.keywords" id="contactsName" type="text" value="<s:property value="cmsPage.keywords"/>" class="newWidth"/>
		</td>
	</tr>
	<s:if test="adminType==0">
	<input name="adminType" type="hidden"  value="0"/>
	</s:if>
	<s:if test="adminType!=0">
	
	
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>页面内容：</td>
		
		<td width="80%"> 
			当光标在编辑器中，按F11键切换全屏幕编辑。ESC可退出全屏幕编辑。
		</td>
	</tr>
	<tr> 
		<td colspan="2">
			<textarea   name="cmsPage.content" id="content" cols="100" rows="8" style="height:300px;"><s:property value="cmsPage.content"/></textarea>
		</td>
	</tr>
	
	</s:if>
	<!--<tr> 
		<td width="20%" align="right">公司：</td>
		<td width="80%"> 
			<input name="cmsPage.company" id="contactsName" type="text" value="<s:property value="cmsPage.company"/>" class="newWidth"/>
		</td>
	</tr>
	--><tr> 
		<td width="20%" align="right">描述：</td>
		<td width="80%"> 
			<textarea name="cmsPage.describe" class="newWidth"><s:property value="cmsPage.describe"/></textarea>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">备注：</td>
		<td width="80%"> 
			<textarea name="cmsPage.remark" class="newWidth"><s:property value="cmsPage.remark"/></textarea>
		</td>
	</tr>
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
		   <INPUT class="saveBtn" onClick="Submit()" TYPE="button" value="">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList(<s:property value="adminType"/>)"  type="button" value="">
	
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form >
</div>
</BODY>
</HTML>