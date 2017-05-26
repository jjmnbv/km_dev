<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@page import="com.pltfm.cms.parse.PathConstants"%>
<%@page import="java.io.File"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商广告位</title>
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
 <%
	String imageOut = PathConstants.cmsPicPath();
	imageOut = imageOut+"/";
%>
 <!-- 导航栏 -->
 <script>

 function gotoList(id){
 	var pageForm= window.document.getElementById("cmsThemeAction_edit");
 	pageForm.action="/cms/cmsThemeAction_queryThemeList.action";
 	pageForm.submit();
 
 }
 
 //跳转模板选择页
	$(function(){
	 	$(".selBtn").click(function(){
	 		dialog("选择模板","iframe:/cms/cmsPageAction_gotoSelPage.action?cmsTemplate.type=11" ,"900px","530px","iframe"); 
	 	});
	});
 
 //关闭弹出窗口
 	function closeOpenDialog(content)
 	{
 		closeThis();
 		var json = eval('(' + content + ')'); 
 		editor.setValue(json.content);
 		$("#templateId").val(json.templateId);
 		
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
.listTitle {
	height: 40px;
	line-height:40px;
	background: #D6F2D9;
	border-bottom: 1px solid #079346;
	vertical-align: middle;
	font-size: 14;
	color: #028043;
	margin:0px;
}
.listTitle span{
 padding-left:20px;
 height:30px;
 line-height:30px;
 vertical-align: middle;
 margin-top:5px;
}
.listTitle span img{
   vertical-align: middle;
}
</style>

<!-- 导航栏 -->
<div width="100%" class="listTitle"  height="40">
    <span>
	<img src="/etc/images/icon_02.png"  />&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;基础功能&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;主题管理
		
		 &nbsp;&nbsp;<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;主题编辑
		
		
	  </span>
</div>
<div  style="height:90%;overflow-y:auto; " >
<s:form   action="/cms/cmsThemeAction_edit.action" method="POST" enctype="multipart/form-data">
<s:token></s:token>
<input type="hidden" name="struts.token.name" value="struts.token" />
<input type="hidden" name="struts.token" value="UFJ5Q17IYGPSOWDXRK4AI6BDFYY0QLF" />
<!-- hidden properties -->





<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">

	<tr> 
		<th colspan="2" align="left" class="edit_title">主题信息</th>
	</tr>
	<!-- error message -->
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>主题名称：</td>
		<td width="80%">
		    <s:if test="cmsTheme.themeId!=null">
		      
		      
		        <input type="text" name="cmsTheme.name" value="<s:property value="cmsTheme.name"/>" >
		    </s:if>
		    <s:else>
		           <input type="text" name="cmsTheme.name" value="" >
		    </s:else>
		 
			<input name="cmsTheme.themeId"  type="hidden" value="<s:property value="cmsTheme.themeId"/>"/>
			<input name="cmsTheme.siteId"  type="hidden" value="<s:property value="cmsTheme.siteId"/>"/>
		
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">主题图片：</td>
		<td width="80%">
		   
			<input type=file name="resumefile" id="resumefile" onchange="javascript:setImagePreview();" style=" border: 1px #6699CC solid;">
	       <div id="localImag">
	       <s:if test="cmsTheme.picture.contains('http:')">
	       		<a href="<s:property value="cmsTheme.picture" />" target="_blank" >
	       </s:if>
	       		<img id="preview" src="<s:property value="cmsTheme.picture"/>" <s:if test="cmsTheme.picture!=null"> width="300px" height="120px"</s:if>  width=-1 height=-1 style="diplay:none" />
	       <s:if test="cmsTheme.picture.contains('http:')">
	       		</a>
	       </s:if>
	       
	       </div>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>主题类型：</td>
		<td width="80%">
		    
				<select name="cmsTheme.type">
		        <option value="0" <s:if test="cmsTheme.type==0">selected="selected"</s:if> >动态主题页</option>
		        <option value="1"  <s:if test="cmsTheme.type==1">selected="selected"</s:if>>静态主题页</option>
		         <option value="2"  <s:if test="cmsTheme.type==2">selected="selected"</s:if>>供应商店铺默认版</option>
		          <option value="3"  <s:if test="cmsTheme.type==3">selected="selected"</s:if>>供应商店铺默认简版</option>
		          <option value="7"  <s:if test="cmsTheme.type==7">selected="selected"</s:if>>供应商店铺药房一版</option>
		          <option value="8"  <s:if test="cmsTheme.type==8">selected="selected"</s:if>>供应商店铺药房简一版</option>
		          <option value="10"  <s:if test="cmsTheme.type==10">selected="selected"</s:if>>供应商店铺药房二版</option>
		          <option value="11"  <s:if test="cmsTheme.type==11">selected="selected"</s:if>>供应商店铺药房三版</option>
		          <option value="12"  <s:if test="cmsTheme.type==12">selected="selected"</s:if>>供应商店铺药房四版</option>
		          <option value="13"  <s:if test="cmsTheme.type==13">selected="selected"</s:if>>供应商店铺药房简四版</option>
		          <option value="14"  <s:if test="cmsTheme.type==14">selected="selected"</s:if>>供应商店铺药房五版</option>
		          <option value="15"  <s:if test="cmsTheme.type==15">selected="selected"</s:if>>供应商店铺药房六版</option>
		           <option value="9"  <s:if test="cmsTheme.type==9">selected="selected"</s:if>>供应商店铺美食版</option>
		           <option value="4"  <s:if test="cmsTheme.type==4">selected="selected"</s:if>>供应商店铺其他页面</option>
		           <option value="5"  <s:if test="cmsTheme.type==5">selected="selected"</s:if>>供应商店铺头部页面</option>
		            <option value="6"  <s:if test="cmsTheme.type==6">selected="selected"</s:if>>供应商店铺动态头部页面</option>
		        </select>
		</td>
	</tr>
	<!-- 
<tr> 
		<td width="20%" align="right">页面变量模板选择：</td>
		<td width="80%"> 
			<INPUT TYPE="button" value="选择" onclick="" class="selBtn" style="cursor: pointer;">
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>主题内容：</td>
		
		<td width="80%"> 
			当光标在编辑器中，按F11键切换全屏幕编辑。ESC可退出全屏幕编辑。
		</td>
	</tr>
	
	<tr> 
		<td colspan="2">
			<textarea   name="cmsTheme.content" id="content" cols="100" rows="8" style="height:300px;"></textarea>
		</td>
	</tr>
	 -->
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
<!-- 消息提示页面 -->
		

<!-- 消息提示 -->
<div width="100%">
<!-- 消息提示 -->
		 
	              
</div>
</HTML>
