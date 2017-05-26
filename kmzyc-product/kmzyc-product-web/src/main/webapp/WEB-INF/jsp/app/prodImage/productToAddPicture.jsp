<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>产品图片维护</title>
    <script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
	<link href="/etc/js/swf/skin.css" rel="stylesheet" type="text/css" />
	<link href="/etc/js/swf/ad.css" rel="stylesheet" type="text/css" />
	<link href="/etc/js/swf/swfupload.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/etc/js/swf/fileprogress.js"></script>
	<script type="text/javascript" src="/etc/js/swf/handlers.js"></script>
	<script type="text/javascript" src="/etc/js/swf/swfupload.js"></script>
	<script type="text/javascript" src="/etc/js/swf/swfupload.queue.js"></script>
	<script type="text/javascript" src="/etc/js/swf/util.js"></script>
	<script type="text/javascript">
	var swfupload;
	$(document).ready(function() {
		swfupload = new SWFUpload({
			// Backend Settings
			upload_url: "/basedata/uploadProductImage.action",	// Relative to the SWF file (or you can use absolute paths)

			file_post_name: "file",
			// File Upload Settings
			file_size_limit : "8192",	// 4MB
			file_types : "*.jpg;*.gif;*.jpeg;*.png;*.bmp",
			file_types_description : "图片",
			file_upload_limit : "20",
			file_queue_limit : "0",

			// Event Handler Settings (all my handlers are in the Handler.js file)
			file_dialog_start_handler : fileDialogStart,//点击上传按钮时执行
			file_queued_handler : fileQueued,//选择文件，点击确定后执行
			file_queue_error_handler : fileQueueError,//加载文件出错
			file_dialog_complete_handler : fileDialogComplete,//正确加载文件完成后
			upload_start_handler : uploadStart,
			upload_progress_handler : uploadProgress,//点击上传按钮执行
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadComplete,

			// Button Settings
			button_image_url : "/etc/js/swf/swfBtn.png",	// Relative to the SWF file
			button_placeholder_id : "spanButtonPlaceholder",
			button_width: 61,
			button_height: 22,
			// Flash Settings
			flash_url : "/etc/js/swf/swfupload.swf",

			custom_settings : {
				progressTarget : "fsUploadProgress",
				cancelButtonId : "btnCancel",
				uploadButtonId : "btnStartUpload"
			},
			
			// Debug Settings
			debug: false
		});
		
		$('#btnBack').click(function(){
			window.location = '/app/findAllSkuProduct.action';
		});
     });
     
	 var upnum = 1;
	 var checkRadioId = "";
	 
     function flashUploadStart() {  
    	//需要修改的地方，获取产品Id
    	swfupload.addPostParam("productSkuId",$("#productSkuId").val());
    	swfupload.addPostParam("productNo",$("#productNo").val());
     	swfupload.startUpload();
     	
     	if($("#uploadpreview div").length == 0){
     		upnum = 1;
     	}
     	
     }

	 
     function uploadpreview(serverData) { 
     	var data = serverData.split(",");
		var picId = data[0];
		var productSkuId = data[1];
     	var review = document.createElement("div");
		review.id = "pic_"+picId;
     	var div = document.createElement("div");
     	var image = document.createElement("img");
     	var p = document.createElement("p");
     	review.className = "preview";
     	image.src = data[2];
     	image.width = 256;
     	image.height = 192;
     	
     	if(upnum == 1){
     		p.innerHTML = "<span><input name='radio' checked='checked' type='radio' id='rpic_"+picId+"' value='1' onclick='javascript:setMainPic("+productSkuId+","+picId+");'><label for='rpic_"+picId+"'>默认</label></span><span class=''>&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:;' onclick='delPic(this.parentNode.parentNode.parentNode,\""+data[0]+"\","+productSkuId+")' >删除</a></span>";
     		ajaxsetMainPic(productSkuId,picId);
     		checkRadioId = "rpic_"+picId;
     	}else{
     		p.innerHTML = "<span><input name='radio' type='radio' id='rpic_"+picId+"' value='1' onclick='javascript:setMainPic("+productSkuId+","+picId+");'><label for='rpic_"+picId+"'>默认</label></span><span class=''>&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:;' onclick='delPic(this.parentNode.parentNode.parentNode,\""+data[0]+"\","+productSkuId+")' >删除</a></span>";
     	}
     	div.appendChild(image);
     	review.appendChild(div);
     	review.appendChild(p);
     	document.getElementById("uploadpreview").appendChild(review);

		upnum++;
     }
	 
	 function setMainPic(productSkuId,picId){
	 	if(confirm('您是否确定要设置这张图为主图？')){
	 		ajaxsetMainPic(productSkuId,picId);
	 	}else{
	 		$("#"+checkRadioId).attr("checked",true);
	 	}
	 }
	 
	 function ajaxsetMainPic(productSkuId,picId){
		 $.post(
			"/basedata/modifyProductImageDefault.action",
			{productSkuId:productSkuId,picId:picId},//需要修改的地方
			function(data){
				if(data=="success"){
					$("input[id='rpic_"+picId+"']")[0].checked = "checked";
					checkRadioId = "rpic_"+picId;
				}else{
					$("#"+checkRadioId).attr("checked",true);
				}
			}
		);
	 }
	 
	 function delPic(arg,picId,productSkuId){
	 	if(confirm('您确定要删除这张图吗？')){
			$.post("/basedata/deleteProductImage.action", 
				{picId : picId}, 
				function(data) {
					if (data=="success") {
						$(arg).remove();
						if($("#"+checkRadioId)[0]==undefined){
							if($("input[type='radio']")[0]){
								var idText = $("input[type='radio']")[0].id;
								var id = idText.substring(idText.indexOf("_")+1,idText.length);
								ajaxsetMainPic(productSkuId,id);
							}
						};
					}
				});
		}
	}
</script>

<style type="text/css">
.preview{
	width:256px;
	height:220px;
}
.preview p{
	margin-top:0px;
}

.preview div {
	width:256px;
	height:192px;
	margin:0px;
}

.preview div img {
    max-width: 100%;
    overflow: hidden;
}
</style>

  </head>
  
  <body>
  	
    <div class="container">
	<div class="wide">
	<div class="adbt" style="color:black;">产品图片上传</div>
		<div class="userprofile batchupload"  style="float:left;width:270px;">
			<!--upload-->
				<fieldset>
					<legend><s:if test="uploadType==1">产品图片上传</s:if><s:elseif test="uploadType==2">产品Flash图片上传</s:elseif></legend>
					<p id="uploadTip">小提示：一次最多上传20张产品图片，上传完后有必要可以设置产品主图。</p>
					<p style="font-size:14px;">
						产品名称：<b><s:property value="viewProductSku.procuctName" /></b>
						<input id="productNo" type="hidden" value="<s:property value="viewProductSku.productNo" />" />
						<input id="productSkuId" type="hidden" value="<s:property value="viewProductSku.productSkuId" />" />
					</p>
					<p style="font-size:14px;">
						<s:iterator value="#request.attrList" >
							<s:property value="categoryAttrName" />：<b><s:property value="categoryAttrValue" /></b>&nbsp;&nbsp;&nbsp;
						</s:iterator>					
					</p>
					<div class="" id="fsUploadProgress">
					
					</div>
				</fieldset>
				<p class="btm"><span id="spanButtonPlaceholder"></span><button id="btnStartUpload" class="btn-custom" onclick="javascript:flashUploadStart();" disabled="disabled">点击上传</button><button id="btnCancel" class="btn-custom" onclick="cancelQueue(swfupload);" disabled="disabled">取消</button><input class="btn-custom btnStyle" type="button" value="返回" onClick="gotoList();" ></p>
			</div>
			<div id="uploadpreview" class="" style="float:left;width:850px;" >
				<p class="">已上传：&nbsp;&nbsp;&nbsp;&nbsp;<span id="outputDiv"></span></p>			 
			</div>
		</div>
	</div>
	</div>
	
  </body>
</html>
