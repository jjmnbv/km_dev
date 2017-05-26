<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加风格</title>
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
    window.location.href="/cms/cmsStylesAction_queryForPage.action";
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

function setImagePreview() {
    var docObj=document.getElementById("resumefile");

    var imgObjPreview=document.getElementById("preview");
            if(docObj.files &&    docObj.files[0]){
                    //火狐下，直接设img属性
                    imgObjPreview.style.display = 'block';
                    imgObjPreview.style.width = '300px';
                    imgObjPreview.style.height = '120px';                    
                    //imgObjPreview.src = docObj.files[0].getAsDataURL();
  //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
  imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);

            }else{
                    //IE下，使用滤镜
                    docObj.select();
                    var imgSrc = document.selection.createRange().text;
                    var localImagId = document.getElementById("localImag");
                    //必须设置初始大小
                    localImagId.style.width = "300px";
                    localImagId.style.height = "120px";
                    //图片异常的捕捉，防止用户修改后缀来伪造图片
                   try{
                            localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
                    }catch(e){
                            alert("您上传的图片格式不正确，请重新选择!");
                            return false;
                    }
                    imgObjPreview.style.display = 'none';
                    document.selection.empty();
            }
            return true;
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
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;风格管理&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;添加风格
	  </span>
</div>
<div  style="height:95%;" >
<s:form  id="addcmsSiteForm" name="addcmsSiteForm" action="/cms/cmsStylesAction_add.action" method="POST" enctype="multipart/form-data">
<s:token></s:token>
<input type="hidden" name="cmsSite.siteId" value="" id="siteId"/>
<input type="hidden" name="pageNo" id="pageNo" value="<s:property value="pageNo"/>"/>
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">

	<tr> 
		<th colspan="2" align="left" class="edit_title">风格信息</th>
	</tr>
	<!-- error message -->
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>风格名称：</td>
		<td width="80%">
			<input name="cmsStyles.stylesName" id="stylesName" type="text" value=""/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>风格描述：</td>
		<td width="80%">
			<input name="cmsStyles.stylesDescribe" id="stylesDescribe" type="text" value=""/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>模板编号：</td>
		<td width="80%">
			<input name="cmsStyles.templateId" id="templateId" type="text" value=""/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right">
			风格脚本内容：
		</td>
		<td width="80%">
			<textarea name="cmsStyles.stylesJs" cols="100" rows="8"style="width:100%; height:100px;"><s:property value="cmsStyles.stylesJs"/></textarea>
		</td>
	</tr>
	
	<tr>
		<td width="20%" align="right">
			风格样式内容：
		</td>
		<td width="80%">
			<textarea name="cmsStyles.stylesCss" cols="100" rows="8"style="width:100%; height:100px;"><s:property value="cmsStyles.stylesCss"/></textarea>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">风格图片：</td>
		<td width="80%">
		   
			<input type=file name="resumefile" id="resumefile" onchange="javascript:setImagePreview();" style=" border: 1px #6699CC solid;">
	       <div id="localImag"><img id="preview" src="<s:property value="cmsStyles.remark"/>"  <s:if test="cmsStyles.remark!=null"> width="300px" height="120px"</s:if>  width=-1 height=-1 style="diplay:none" /></div>
	       
	       
			
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>风格内容：</td>
		
		<td width="80%"> 
			当光标在编辑器中，按F11键切换全屏幕编辑。ESC可退出全屏幕编辑。
		</td>
	</tr>
	<tr> 
		<td colspan="2">
			<textarea   name="cmsStyles.content" id="content" cols="100" rows="8" style="height:300px;"></textarea>
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