<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加广告</title>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
 <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
 <script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script src="/etc/js/dialog.js"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="/etc/js/codemirror/codemirror.css">
<link rel="stylesheet" href="/etc/js/codemirror/fullscreen.css">
<link rel="stylesheet" href="/etc/js/codemirror/erlang-dark.css">
<script src="/etc/js/codemirror/codemirror.js"></script>
<script src="/etc/js/codemirror/xml.js"></script>
<script src="/etc/js/codemirror/fullscreen.js"></script>
<script src="/etc/js/codemirror/userdefined.js"></script>


<script type="text/javascript"  src="/etc/js/autocomplete/jquery.mockjax.js"></script>

<script type='text/javascript' src='/etc/js/autocomplete2/jquery.autocomplete.js'></script>
<link rel="stylesheet" type="text/css" href="/etc/js/autocomplete2/jquery.autocomplete.css" />


</head>
<body>
 
 <!-- 导航栏 -->
 

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
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;广告位管理
		 &nbsp;&nbsp;<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;添加广告
		
		
	  </span>
</div>
<div  style="height:90%;overflow-y:auto; " >
<s:form  id="AdcolumnAdd" name="AdcolumnAdd" action="/cms/Adv_Update.action" method="POST" enctype="multipart/form-data">
<s:token></s:token>
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- keyWords -->
<input type="hidden" name="keyWords.adcolumnId_keyword" value="<s:property value='keyWords.adcolumnId_keyword'/>">
<input type="hidden" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
<input type="hidden" name="keyWords.status_keyword" value="<s:property value='keyWords.status_keyword'/>"/>
<input type="hidden" name="keyWords.beginTime_keyword" value="<s:date name="keyWords.beginTime_keyword" format="yyyy-MM-dd HH:mm:ss"/>"/>
<input type="hidden" name="keyWords.endTime_keyword" value="<s:date name="keyWords.endTime_keyword" format="yyyy-MM-dd HH:mm:ss"/>"/>
<input type="hidden" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
<input type="hidden" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	
	<tr> 
		<th colspan="2" align="left" class="edit_title">广告信息</th>
	</tr>
	
	<!-- error message -->
	<tr> <input name="cmsAdv.advId" id="advId" type="hidden" value="<s:property value="cmsAdv.advId"/>"/>
         <input name="cmsAdv.siteId" id="siteId" type="hidden" value="<s:property value="cmsAdv.siteId"/>"/>
 
		<td width="20%" align="right"><font color="red">*</font>广告名：</td>
		<td width="80%" align="left">
			<input name="cmsAdv.name" id="corporateName" type="text" value="<s:property value="cmsAdv.name"/>"/>
			<s:if test=""></s:if>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>广告位置：</td>
		<td width="80%"> 
			<input type="text" id="adcolumn" name="adcolumnname">
	        <input type="hidden" name="cmsAdv.adcolumnId" id="theadcolumnId" value='<s:property value="cmsAdv.adcolumnId"/>'/>
			<!-- 
			<input  type="hidden" id="hidden_id" value='<s:property value="cmsAdv.adcolumnId"/>'> 
			<select name="cmsAdv.adcolumnId" id="adcolumn">
	        	<option value="1"  selected="selected">**</option>
	        </select>
	         -->
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>广告类型：</td>
		<td width="80%"> 
			<select name="cmsAdv.types" id="typess" >
	        <option value="1"   <s:if test="cmsAdv.types==1">selected="selected"</s:if>>单张</option>
	        <option value="2"  <s:if test="cmsAdv.types==2">selected="selected"</s:if> >多张切换</option>
	        <option value="3"  <s:if test="cmsAdv.types==3">selected="selected"</s:if> >FLASH</option>
	        </select>
		</td>
	</tr>
    <tr style="display:none;"> 
		<td width="20%" align="right"><font color="red">*</font>开关</td>
		<td width="80%"> 
			<input type="radio" <s:if test="cmsAdv.status==0">checked='checked'</s:if>  name="cmsAdv.status" value="0">开
			<input type="radio" <s:if test="cmsAdv.status==1">checked='checked'</s:if> name="cmsAdv.status" value="1">关
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>起始时间：</td>
		<td width="80%"> 
			<input name="cmsAdv.beginTime" id="beginTime" type="text" readonly="readonly" value="<s:date name="cmsAdv.beginTime" format="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>终止时间：</td>
		<td width="80%"> 
		<input name="cmsAdv.endTime"  id="endTime" type="text" readonly="readonly" value="<s:date name="cmsAdv.endTime" format="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">广告链接：</td>
		<td width="80%">
			<input name="cmsAdv.url" id="url" type="text" value="<s:property value="cmsAdv.url"/>" />
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">上传广告文件：</td>
		<td width="80%">
			<input type=file name="resumefile" id="resumefile" onchange="javascript:setImagePreview();" style=" border: 1px #6699CC solid;">
									<div id="localImag">
							
									<img id="preview" <s:if test="cmsAdv.uploadAdvFile!=null"> width="300px" height="120px"  </s:if> src="<s:property value="cmsAdv.uploadAdvFile"/>"/>
								
									</div>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">广告链接2：</td>
		<td width="80%">
			<input name="cmsAdv.url2" id="url2" type="text" value="<s:property value="cmsAdv.url2"/>" />
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">上传广告文件2：</td>
		<td width="80%">
			<input type=file name="resumefile2" id="resumefile2" onchange="javascript:setImagePreview2();" style=" border: 1px #6699CC solid;">
									<div id="localImag2">
									
									<img id="preview2" <s:if test="cmsAdv.uploadAdvFile2!=null"> width="300px" height="120px"  </s:if>style="diplay:none"  src="<s:property value="cmsAdv.uploadAdvFile2"/>" /> </div>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">广告链接3：</td>
		<td width="80%">
			<input name="cmsAdv.url3" id="url3" type="text" value="<s:property value="cmsAdv.url3"/>" />
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">上传广告文件3：</td>
		<td width="80%">
			<input type=file name="resumefile3" id="resumefile3" onchange="javascript:setImagePreview3();" style=" border: 1px #6699CC solid;">
									<div id="localImag3">
									
									<img id="preview3"<s:if test="cmsAdv.uploadAdvFile3!=null"> width="300px" height="120px"  </s:if> style="diplay:none"  src="<s:property value="cmsAdv.uploadAdvFile3"/>" /></div>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">广告链接4：</td>
		<td width="80%">
			<input name="cmsAdv.url4" id="url4" type="text" value="<s:property value="cmsAdv.url4"/>" />
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">上传广告文件4：</td>
		<td width="80%">
			<input type=file name="resumefile4" id="resumefile4" onchange="javascript:setImagePreview4();" style=" border: 1px #6699CC solid;">
			<div id="localImag4"  >
			
			<img id="preview4"  <s:if test="cmsAdv.uploadAdvFile4!=null"> width="300px" height="120px"  </s:if> style="diplay:none"   src="<s:property value="cmsAdv.uploadAdvFile4"/>"/></div>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">广告链接5：</td>
		<td width="80%">
			<input name="cmsAdv.p2" id="p2" type="text" value="<s:property value="cmsAdv.p2"/>" />
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">上传广告文件5：</td>
		<td width="80%">
			<input type=file name="resumefile5" id="resumefile5" onchange="javascript:setImagePreview5();" style=" border: 1px #6699CC solid;">
			<div id="localImag5"  >
			
			<img id="preview5"  <s:if test="cmsAdv.p3!=null"> width="300px" height="120px"  </s:if> style="diplay:none"   src="<s:property value="cmsAdv.p3"/>"/></div>
		</td>
	</tr>

	<tr>
		<td width="20%" align="right">广告链接6：</td>
		<td width="80%">
			<input name="cmsAdv.url6" id="url6" type="text" value="<s:property value="cmsAdv.url6"/>" />
		</td>
	</tr>
	<tr>
		<td width="20%" align="right">上传广告文件6：</td>
		<td width="80%">
			<input type=file name="resumefile6" id="resumefile6" onchange="javascript:setImagePreview6();" style=" border: 1px #6699CC solid;">
			<div id="localImag6"  >

				<img id="preview6"  <s:if test="cmsAdv.uploadAdvFile6!=null"> width="300px" height="120px"  </s:if> style="diplay:none"   src="<s:property value="cmsAdv.uploadAdvFile6"/>"/></div>
		</td>
	</tr>

	<tr> 
		<td width="20%" align="right">单位名称：</td>
		<td width="80%">
			<input type="text" name="cmsAdv.unitName"  size="45" value="<s:property value="cmsAdv.unitName"/>">
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">联系人：</td>
		<td width="80%">
			<input type="text" name="cmsAdv.linkman"  size="45" value="<s:property value="cmsAdv.linkman"/>">
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">联系方式：</td>
		<td width="80%">
			<input type="text" name="cmsAdv.contact"  size="45" value="<s:property value="cmsAdv.contact"/>">
		</td>
	</tr>
	<input name="adminType" type="hidden"   value="<s:property value="adminType"/>"/>
	<s:if test="adminType!=0">
		<tr> 
		<td width="20%" align="right">模板选择：</td>
		<td width="80%"> 
			<INPUT TYPE="button" value="选择" onclick="" class="selBtn" style="cursor: pointer;">
		</td>
	</tr>
	
	</s:if>
	<s:if test="adminType!=0">
		<tr> 
		<td width="20%" align="right"><font color="red">*</font>广告模版：</td>
		
		<td width="80%"> 
			当光标在编辑器中，按F11键切换全屏幕编辑。ESC可退出全屏幕编辑。
		</td>
	</tr>
	<tr> 
		<td colspan="2">
			<textarea   name="cmsAdv.content" id="content" cols="100" rows="8" style="height:300px;"><s:property value="cmsAdv.content"/></textarea>
		</td>
	</tr>
	</s:if>
</table>

<!-- 底部 按钮条 -->
<table width="95%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<INPUT class="saveBtn" TYPE="button" value="">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()"  type="button" value="">
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
<script type="text/javascript">

function setImagePreview() {
        var docObj=document.getElementById("resumefile");
        var imgObjPreview=document.getElementById("preview");
        
      //图片大小限制
        var fileSize = 0;         
        if (!docObj.files) {     
          var filePath = docObj.value;     
          var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
          var file = fileSystem.GetFile (filePath);     
          fileSize = file.Size;    
        } else {    
         fileSize = docObj.files[0].size;     
         }   
         var size = fileSize / 1024;  
         
         if(size>200){  
       	  var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/><span style='color:red'>图片上传大小不能超过200K！</span>";
   	      dialog("消息提示","text:"+msg ,"300px","text");
   	   	  docObj.value="";
   	   	  imgObjPreview.src = "";
   	 	  imgObjPreview.style.display = 'none';
          return false;
         }
         
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
           function setImagePreview2() {
			var docObj = document.getElementById("resumefile2");
			var imgObjPreview = document.getElementById("preview2");
			
			//图片大小限制
	        var fileSize = 0;         
	        if (!docObj.files) {     
	          var filePath = docObj.value;     
	          var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
	          var file = fileSystem.GetFile (filePath);     
	          fileSize = file.Size;    
	        } else {    
	         fileSize = docObj.files[0].size;     
	         }   
	         var size = fileSize / 1024;  
	         
	         if(size>200){  
	       	  var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/><span style='color:red'>图片上传大小不能超过200K！</span>";
	   	      dialog("消息提示","text:"+msg ,"300px","text");
	   	   	  docObj.value="";
	   	   	  imgObjPreview.src = "";
	   	 	  imgObjPreview.style.display = 'none';
	          return false;
	         }

			if (docObj.files && docObj.files[0]) {
				//火狐下，直接设img属性
				imgObjPreview.style.display = 'block';
				imgObjPreview.style.width = '300px';
				imgObjPreview.style.height = '120px';
				//imgObjPreview.src = docObj.files[0].getAsDataURL();
				//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
				imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
			} else {
				//IE下，使用滤镜
				docObj.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("localImag2");
				//必须设置初始大小
				localImagId.style.width = "300px";
				localImagId.style.height = "120px";
				//图片异常的捕捉，防止用户修改后缀来伪造图片
				try {
					localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
					localImagId.filters
							.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				} catch (e) {
					alert("您上传的图片格式不正确，请重新选择!");
					return false;
				}
				imgObjPreview.style.display = 'none';
				document.selection.empty();
			}
			return true;
		}
		function setImagePreview3() {
			var docObj = document.getElementById("resumefile3");
			var imgObjPreview = document.getElementById("preview3");
			
			//图片大小限制
	        var fileSize = 0;         
	        if (!docObj.files) {     
	          var filePath = docObj.value;     
	          var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
	          var file = fileSystem.GetFile (filePath);     
	          fileSize = file.Size;    
	        } else {    
	         fileSize = docObj.files[0].size;     
	         }   
	         var size = fileSize / 1024;  
	         
	         if(size>200){  
	       	  var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/><span style='color:red'>图片上传大小不能超过200K！</span>";
	   	      dialog("消息提示","text:"+msg ,"300px","text");
	   	   	  docObj.value="";
	   	   	  imgObjPreview.src = "";
	   	 	  imgObjPreview.style.display = 'none';
	          return false;
	         }

			if (docObj.files && docObj.files[0]) {
				//火狐下，直接设img属性
				imgObjPreview.style.display = 'block';
				imgObjPreview.style.width = '300px';
				imgObjPreview.style.height = '120px';
				//imgObjPreview.src = docObj.files[0].getAsDataURL();
				//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
				imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
			} else {
				//IE下，使用滤镜
				docObj.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("localImag3");
				//必须设置初始大小
				localImagId.style.width = "300px";
				localImagId.style.height = "120px";
				//图片异常的捕捉，防止用户修改后缀来伪造图片
				try {
					localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
					localImagId.filters
							.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				} catch (e) {
					alert("您上传的图片格式不正确，请重新选择!");
					return false;
				}
				imgObjPreview.style.display = 'none';
				document.selection.empty();
			}
			return true;
		}
		function setImagePreview4() {
				var docObj = document.getElementById("resumefile4");
			var imgObjPreview = document.getElementById("preview4");
			
			//图片大小限制
	        var fileSize = 0;         
	        if (!docObj.files) {     
	          var filePath = docObj.value;     
	          var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
	          var file = fileSystem.GetFile (filePath);     
	          fileSize = file.Size;    
	        } else {    
	         fileSize = docObj.files[0].size;     
	         }   
	         var size = fileSize / 1024;  
	         
	         if(size>200){  
	       	  var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/><span style='color:red'>图片上传大小不能超过200K！</span>";
	   	      dialog("消息提示","text:"+msg ,"300px","text");
	   	   	  docObj.value="";
	   	   	  imgObjPreview.src = "";
	   	 	  imgObjPreview.style.display = 'none';
	          return false;
	         }

			if (docObj.files && docObj.files[0]) {
				//火狐下，直接设img属性
				imgObjPreview.style.display = 'block';
				imgObjPreview.style.width = '300px';
				imgObjPreview.style.height = '120px';
				//imgObjPreview.src = docObj.files[0].getAsDataURL();
				//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
				imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
			} else {
				//IE下，使用滤镜
				docObj.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("localImag4");
				//必须设置初始大小
				localImagId.style.width = "300px";
				localImagId.style.height = "120px";
				//图片异常的捕捉，防止用户修改后缀来伪造图片
				try {
					localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
					localImagId.filters
							.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				} catch (e) {
					alert("您上传的图片格式不正确，请重新选择!");
					return false;
				}
				imgObjPreview.style.display = 'none';
				document.selection.empty();
			}
			return true;
		}
		function setImagePreview5() {
			var docObj = document.getElementById("resumefile5");
			var imgObjPreview = document.getElementById("preview5");
			
			//图片大小限制
	        var fileSize = 0;         
	        if (!docObj.files) {     
	          var filePath = docObj.value;     
	          var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
	          var file = fileSystem.GetFile (filePath);     
	          fileSize = file.Size;    
	        } else {    
	         fileSize = docObj.files[0].size;     
	         }   
	         var size = fileSize / 1024;  
	         
	         if(size>200){  
	       	  var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/><span style='color:red'>图片上传大小不能超过200K！</span>";
	   	      dialog("消息提示","text:"+msg ,"300px","text");
	   	   	  docObj.value="";
	   	   	  imgObjPreview.src = "";
	   	 	  imgObjPreview.style.display = 'none';
	          return false;
	         }

			if (docObj.files && docObj.files[0]) {
				//火狐下，直接设img属性
				imgObjPreview.style.display = 'block';
				imgObjPreview.style.width = '300px';
				imgObjPreview.style.height = '120px';
				//imgObjPreview.src = docObj.files[0].getAsDataURL();
				//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
				imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
			} else {
				//IE下，使用滤镜
				docObj.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("localImag5");
				//必须设置初始大小
				localImagId.style.width = "300px";
				localImagId.style.height = "120px";
				//图片异常的捕捉，防止用户修改后缀来伪造图片
				try {
					localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
					localImagId.filters
							.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				} catch (e) {
					alert("您上传的图片格式不正确，请重新选择!");
					return false;
				}
				imgObjPreview.style.display = 'none';
				document.selection.empty();
			}
			return true;
		}

		function setImagePreview6() {
			var docObj = document.getElementById("resumefile6");
			var imgObjPreview = document.getElementById("preview6");
			
			//图片大小限制
	        var fileSize = 0;         
	        if (!docObj.files) {     
	          var filePath = docObj.value;     
	          var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
	          var file = fileSystem.GetFile (filePath);     
	          fileSize = file.Size;    
	        } else {    
	         fileSize = docObj.files[0].size;     
	         }   
	         var size = fileSize / 1024;  
	         
	         if(size>200){  
	       	  var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/><span style='color:red'>图片上传大小不能超过200K！</span>";
	   	      dialog("消息提示","text:"+msg ,"300px","text");
	   	   	  docObj.value="";
	   	   	  imgObjPreview.src = "";
	   	 	  imgObjPreview.style.display = 'none';
	          return false;
	         }

			if (docObj.files && docObj.files[0]) {
				//火狐下，直接设img属性
				imgObjPreview.style.display = 'block';
				imgObjPreview.style.width = '300px';
				imgObjPreview.style.height = '120px';
				//imgObjPreview.src = docObj.files[0].getAsDataURL();
				//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
				imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
			} else {
				//IE下，使用滤镜
				docObj.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("localImag6");
				//必须设置初始大小
				localImagId.style.width = "300px";
				localImagId.style.height = "120px";
				//图片异常的捕捉，防止用户修改后缀来伪造图片
				try {
					localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
					localImagId.filters
							.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				} catch (e) {
					alert("您上传的图片格式不正确，请重新选择!");
					return false;
				}
				imgObjPreview.style.display = 'none';
				document.selection.empty();
			}
			return true;
		}
</script>
<script>
 $().ready(function(){

 /*
  $.ajax({
        url:'Adv_Add_ajax.action',
        type:'post',
        dataType:'json',
        success:function (d) {
	       var li='';
	         $.each(d.advlist, function (index, item) {
		         if(hid==item.adcolumnId){
		         	li+="<option value="+item.adcolumnId+" selected='selected'  >"+item.name+"</option>";
		         }else{
		         	li+="<option value="+item.adcolumnId+"   >"+item.name+"</option>";
		        }
	        });
        $("#adcolumn").html(li);
        }
 	});
  */
  
  var adcolumnArray;
	$.ajax({
		async:'false',
		url:'Adv_Add_ajax.action',
		success:function(data){
		//   data=62:xxx;
			adcolumnArray = $.map(data, function (value,key) { 
				return { value:value, data:key };
				
			});
		//	product_add_suppliersArray = suppliersArray;
			$("#adcolumn").autocomplete(adcolumnArray, {
				minChars: 0,
				max:50,
				formatItem: function(row, i, max, term) {
					return row.value;
				}
			});
			
			 var hid=$("#theadcolumnId").val();
			 var thecolumnName = "";
			 for(var j=0;j<adcolumnArray.length ; j++){
				if( hid == adcolumnArray[j].data ){
					thecolumnName = adcolumnArray[j].value;
				}
			 }
			 $("#adcolumn").val(thecolumnName);
			
			
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			//alert("aaa:"+XMLHttpRequest+"...."+textStatus+"...."+errorThrown);
			},
		dataType:'json'
	});
	
	
	function bindId(){
		var adcolumnId = 0;
		var name = $("#adcolumn").val();
		for(var i=0;i<adcolumnArray.length ; i++){
			if( name == adcolumnArray[i].value ){
				adcolumnId = adcolumnArray[i].data;
			}
		}
		$("#theadcolumnId").val(adcolumnId);
	}
	
	$(".saveBtn").bind("click",function(){
		bindId();
		$("form").submit();
	});
	
	 // function Submit(){
	//	 	bindId();
	//		$("form").submit();
	 //  }
  
 /*$("#typess").change(function(){
 var id=$("#typess").val();
        $.ajax({
        url:'Adv_Adv_temp.action',
        data:{"Advid":id},
        type:'post',
        success:function (d) {
        $("#content").html(d);
       
        }
 });
  });*/
	$("#AdcolumnAdd").validate({
            rules: {
				"cmsAdv.name": {required:true,compareTo2:true,compareTo3:true},
				"adcolumnname": {required:true,compareTo4:true},
				"cmsAdv.content": {required:true},
				"cmsAdv.beginTime":{required:true},
				"cmsAdv.endTime":{compareTo:true,required:true}
			},
         	success: function (label){
            	label.removeClass("checked").addClass("checked");
            }
    });
  jQuery.validator.addMethod("compareTo", function(value, element) {  
	    var beginTime = $("#beginTime").val();
		   if (value<beginTime){
		         return false;
		    }else{
		         return true;
		    }
    },"请正确选择时间");
    jQuery.validator.addMethod("compareTo2", function(value, element) {  
	   var advId=$("#advId").val();
       var ok="";
        $.ajax({
        url:'/cms/Adv_name_ajax.action',
        data:{"cmsAdv.name":value,"cmsAdv.advId":advId},
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
		
    },"该名已存在 ");
   jQuery.validator.addMethod("compareTo3", function(value, element) {  
     if(value.length<16){
     return true;
     }else{
     return false;
     }
     
    },"请输入小于16个字数的名字");
       
    jQuery.validator.addMethod("compareTo4", function(value, element) {  

        var ok="";
         $.ajax({
         url:'/cms/Adcolumn_name_ajax.action',
         data:{"cmsAdcolumn.name":value},
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
 	    if(ok=="1"){
 	    return true;
 	    }else{
 	    return false;
 	    }
 		
     },"该广告位置不存在 ");
 });
 $(".selBtn").click(function(){
	 		dialog("选择模板","iframe:/cms/cmsPageAction_gotoSelPage.action?cmsTemplate.type=10" ,"900px","530px","iframe"); 
	 	});
 //关闭弹出活动层并赋值
 	function closeOpenDialog(content)
 	{
 		closeThis();
 		var json = eval('(' + content + ')'); 
 			//$("#content").val(json.content);
 			editor.setValue(json.content);
 		
    }
 
 function gotoList(){
  	var pageForm= window.document.getElementById("AdcolumnAdd");
 	pageForm.action="/cms/Adv_queryAdv.action";
 	pageForm.submit();
 
  }
   

 
 </script>
 
</HTML>