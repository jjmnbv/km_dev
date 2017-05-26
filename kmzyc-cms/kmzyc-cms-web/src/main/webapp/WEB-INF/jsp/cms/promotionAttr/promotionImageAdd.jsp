<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加图片</title>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
 <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
 <script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 
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
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;活动管理
		 &nbsp;&nbsp;<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;添加活动图片
		
		
	  </span>
</div>
<div  style="height:90%;overflow-y:auto; " >
<form id="promotionImageAdd" name="promotionImageAdd" action="/cms/PromotionAttr_add.action" method="POST" enctype="multipart/form-data">
<input type="hidden" name="struts.token.name" value="struts.token" />
<input type="hidden" name="struts.token" value="UFJ5Q17IYGPSOWDXRK4AI6BDFYY0QLF" />
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- 数据编辑区域 -->
<table width="60%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="edit_title">广告信息</th>
	</tr>
	<!-- error message -->
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>活动标签：</td>
		<td width="80%" align="left">
			<input name="pro.promotionTag" id="corporateName" type="text" value=""/>
		</td>
	</tr>


	<tr> 
		<td width="20%" align="right">上传活动图片：</td>

		<td width="80%">
			<input type=file name="resumefile" id="resumefile" onchange="javascript:setImagePreview();" style=" border: 1px #6699CC solid;">
									<div id="localImag"><img id="preview" width=-1 height=-1 style="diplay:none" /></div>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">上传活动图片2：</td>

		<td width="80%">
			<input type=file name="resumefile2" id="resumefile2" onchange="javascript:setImagePreview2();" style=" border: 1px #6699CC solid;">
									<div id="localImag2"><img id="preview2" width=-1 height=-1 style="diplay:none" /></div>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">上传活动图片3：</td>

		<td width="80%">
			<input type=file name="resumefile3" id="resumefile3" onchange="javascript:setImagePreview3();" style=" border: 1px #6699CC solid;">
									<div id="localImag3"><img id="preview3" width=-1 height=-1 style="diplay:none" /></div>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">上传活动图片4：</td>

		<td width="80%">
			<input type=file name="resumefile4" id="resumefile4" onchange="javascript:setImagePreview4();" style=" border: 1px #6699CC solid;">
			<div id="localImag4"><img id="preview4" width=-1 height=-1 style="diplay:none" /></div>
		</td>
	</tr>
	
	
	
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<INPUT class="saveBtn" type="submit"  value="">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value="">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</form>




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
</script>
<script>
 $().ready(function(){

  $("#AdcolumnAdd").validate({
               rules: {
					"cmsAdv.name": {required:true},
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
 });
 
 function gotoList(){
  location.href="/cms/PromotionAttr_List.action"
 }
 
 </script>
 
</HTML>