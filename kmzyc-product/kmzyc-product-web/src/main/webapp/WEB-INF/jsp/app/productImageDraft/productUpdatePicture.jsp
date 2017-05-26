<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  	<head>
    <title>产品图片维护</title>
    <script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
	<link href="/etc/js/swf/skin.css" rel="stylesheet" type="text/css" />
	<link href="/etc/js/swf/ad.css" rel="stylesheet" type="text/css" />
	<link href="/etc/js/swf/swfupload.css" rel="stylesheet" type="text/css" />
	<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
	<script type="text/javascript" src="/etc/js/swf/fileprogress.js"></script>
	<script type="text/javascript" src="/etc/js/swf/handlers.js"></script>
	<script type="text/javascript" src="/etc/js/swf/swfupload.js"></script>
	<script type="text/javascript" src="/etc/js/swf/swfupload.queue.js"></script>
	<script type="text/javascript" src="/etc/js/swf/util.js"></script>
	<script type="text/javascript">
	var swfupload;
	var upnum = 1;
	$(document).ready(function() {
		swfupload = new SWFUpload({
			// Backend Settings
			upload_url: "/app/uploadProductImageDraft.do",	// Relative to the SWF file (or you can use absolute paths)

		
			file_post_name: "file",
			// File Upload Settings
			file_size_limit : "1024",	// 1MB
			file_types : "*.jpg;*.gif;*.jpeg;*.png;*.bmp",
			file_types_description : "图片",
			file_upload_limit : "0",
			file_queue_limit : "10",

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
			if($("input[type='button'][value='保存']").length>0){
				alert("请保存图片顺序！");
				return;
			}
			if($("#backPage").val() == "image"){
				window.location = "/app/findProductSkusAndAttrValues.action?type=image&productId="+$("#productId").val()
			}else{
				window.location = '/app/findAllProductSkuDraft.action';
			}
		});
		
     });
    
	 
	function flashUploadStart() {
    	//需要修改的地方，获取产品Id
    	swfupload.addPostParam("productSkuId",$("#productSkuId").val());
    	swfupload.addPostParam("productNo",$("#productNo").val());
    	swfupload.addPostParam("productId",$("#productId").val());
     	swfupload.startUpload();
     	
     	if($("#uploadpreview div").length == 0){
     		upnum = 1;
     	}
	}

	function uploadImagePreview(serverData) {
     	var data = serverData.split(",");
		var picId = data[0];
		var productSkuId = data[1];
     	var review = document.createElement("div");
		review.id = "pic_"+picId;
		var _input = document.createElement("input");
		_input.type = "hidden";
		_input.value = picId;
		_input.name = "imageId";
     	var div = document.createElement("div");
     	var image = document.createElement("img");
     	var p = document.createElement("p");
     	p.className = "editDataDiv";
     	
     	if($("#editButton").val()=="调整图片"){
     		p.style.display = "none";
     	}
     	
     	image.src = data[2];
     	image.width = 128;
     	image.height = 128;
     	
     	if($("#uploadpreview div.preview").length>0){
     		upnum++;
     	}
     	
     	var str = "";
     	
     	if(upnum == 1){
     		review.className = "preview isDefault";
     	}else{
     		review.className = "preview";
     	}
     	
     	if(upnum === 1){
     		str = "<span><img src='/etc/images/little_icon/first.png' alt='默认' title='默认' id='rpic_"+picId+"' onclick='javascript:setMainPic("+productSkuId+","+picId+",this.parentNode.parentNode.parentNode);'></span>";
     		str += "<span class=''><img src='/etc/images/little_icon/delete.png' alt='删除' title='删除' onclick='delPic(this.parentNode.parentNode.parentNode,\""+data[0]+"\","+productSkuId+")' /></span>";
     		str += "<img src='/etc/images/little_icon/qianyi.png' alt='提前' title='提前' onclick='goForward(this.parentNode.parentNode);' />";
     		str += "<img src='/etc/images/little_icon/houyi.png' alt='后退' title='后退' onclick='backUp(this.parentNode.parentNode);' />";
     		
     		p.innerHTML = str;
     		ajaxsetMainPic(productSkuId,picId);
     		$("#editButton").removeAttr("disabled");
     	}else{
     		str = "<span><img src='/etc/images/little_icon/first.png' alt='默认' title='默认' id='rpic_"+picId+"' onclick='javascript:setMainPic("+productSkuId+","+picId+",this.parentNode.parentNode.parentNode);'></span>";
     		str += "<span class=''><img src='/etc/images/little_icon/delete.png' alt='删除' title='删除' onclick='delPic(this.parentNode.parentNode.parentNode,\""+data[0]+"\","+productSkuId+")' /></span>";
     		str += "<img src='/etc/images/little_icon/qianyi.png' alt='提前' title='提前' onclick='goForward(this.parentNode.parentNode);' />";
     		str += "<img src='/etc/images/little_icon/houyi.png' alt='后退' title='后退' onclick='backUp(this.parentNode.parentNode);' />";
     		
     		p.innerHTML = str;
     	}
    	div.appendChild(image);
    	review.appendChild(_input);
     	review.appendChild(div);
     	review.appendChild(p);
     	document.getElementById("uploadpreview").appendChild(review);
     	if($("#imageMsg")){
     		$("#imageMsg").remove();
     	}
     	upnum++;
	}
	 
	function setMainPic(productSkuId,picId,arg){
    	 $(arg).addClass("isDefault").siblings().removeClass("isDefault");
    	 if($("#uploadpreview").children("div:first")[0]==arg){
    		 return;
    	 }
    	 $("#uploadpreview").children("div:first").before(arg);
	}
 	 
	function ajaxsetMainPic(productSkuId,picId){
		$.post(
 			"/app/modifyProductImageDraftDefault.action",
 			{productSkuId:productSkuId,picId:picId},//需要修改的地方
 			function(data){
 				if(data=="success"){
 					
 				}else{
 					
 				}
 			}
 		);
	}
	 
 	function delPic(arg,picId,productSkuId){
	 	if(confirm('您确定要删除这张图吗？')){
			$.post("/app/deleteProductImageDraft.action", 
				{picId : picId}, 
				function(data) {
					if (data=="success") {
						$(arg).remove();
						if($(".preview").length==0){
							$("#editButton").val("调整图片");
							$("#editButton").attr("disabled","disabled");
						}else{
							if($(".isDefault").length==0){
								$("#uploadpreview").children("div:first").addClass("isDefault");
								var idText = $("#uploadpreview").children("div:first")[0].id;
								var id = idText.substring(idText.indexOf("_")+1,idText.length);
								ajaxsetMainPic(productSkuId,id);
							}
						}
					}else{
						alert("系统发生错误，删除失败，请联系管理员！");
					}
				},"text");
		}
 	}
 	
 	
 	function goForward(arg){
 		$(arg).prev("div").before(arg);
 		if(arg==$("#uploadpreview").children("div:first")[0]){
 			$(arg).addClass("isDefault").siblings().removeClass("isDefault");
 		}
 	}
 	
 	function backUp(arg){
 		var flag = false;
 		if(arg==$("#uploadpreview").children("div:first")[0]){
 			flag = true;
 		}
 		$(arg).next("div").after(arg);
 		if(flag){
 			$("#uploadpreview").children("div:first").addClass("isDefault").siblings().removeClass("isDefault");
 		}
 	}
 	
 	function editImage(arg){
 		if(arg.value=="调整图片"){
 			$(".editDataDiv").css("display","");
 			arg.value = "保存";
 		}else{
 			$(".editDataDiv").css("display","none");
 			arg.value = "调整图片";
 			$("#imageFrm").submit();
 		}
 	}
	</script>
	<style type="text/css">
.preview{
	width:128px;
	height:152px;
	border-width: 2px;
}
.preview p{
	margin-top:0px;
	height:24px;
}

.preview div {
	width:128px;
	height:128px;
	margin:0px;
}

.preview div img {
    max-width: 100%;
    overflow: hidden;
}

.editDataDiv img{
	cursor: pointer;
}

.isDefault{
	border-color: #078C43;
	border-width: 2px;
}
</style>
  	</head>
  	<body>
  	<input type="hidden" id="backPage" value="<s:property value='type' />" />
    <div class="container">
	<div class="wide">
	<div class="adbt" style="color:black;">产品图片上传</div>
		<div class="userprofile batchupload"  style="float:left;width:270px;">
			<!--upload-->
				<fieldset>
					<legend><s:if test="uploadType==1">产品图片上传</s:if><s:elseif test="uploadType==2">产品Flash图片上传</s:elseif></legend>
					<p id="uploadTip">小提示：一个商品最多上传10张产品图片, 单张图片大小不超过1M。</p>
					<p style="font-size:14px;">
						产品名称：<b><s:property value="productSkuDraft.productName" /></b>
						<input id="productNo" type="hidden" value="<s:property value="productSkuDraft.productNo" />" />
						<input id="productSkuId" type="hidden" value="<s:property value="productSkuDraft.productSkuId" />" />
						<input id="productId" type="hidden" value="<s:property value="productSkuDraft.productId" />" />
					</p>
					<p style="font-size:14px;">
						<s:iterator value="productSkuDraft.attributeValues" >
							<s:property value="attribute" />：<b><s:property value="value" /></b>&nbsp;&nbsp;&nbsp;
						</s:iterator>					
					</p>
					<div class="" id="fsUploadProgress">
					
					</div>
				</fieldset>
				<p class="btm"><span id="spanButtonPlaceholder"></span><button id="btnStartUpload" class="table_btn" onclick="javascript:flashUploadStart();" disabled="disabled">点击上传</button><s:if test='type != "image"'><button id="btnBack" class="table_btn">返回</button></s:if><button id="btnCancel" class="table_btn" onclick="cancelQueue(swfupload);" style="visibility:hidden;" disabled="disabled">取消</button></p>
			</div>
			<form action="/app/updateImageSortNoDraft.action" method="post" name="imageFrm" id="imageFrm" target="theID" >
			<div id="uploadpreview" class="" style="float:left;width:780px;" >
				<p class="">已上传：&nbsp;&nbsp;&nbsp;&nbsp;<span id="outputDiv"></span></p>	
				<s:if test="#request.imageList.size() == 0"><div class="adbt" id="imageMsg" style="color:black;">该商品暂无图片</div></s:if>
				<p><input type="button" value="调整图片" id="editButton" class="btnStyle" onclick="editImage(this);" <s:if test="#request.imageList.size() == 0"> disabled="disabled" </s:if> /></p>
				
				<s:iterator value="#request.imageList">		 
					<div id="pic_<s:property value="imageId" />" class="preview<s:if test='isDefault == 0'> isDefault</s:if>" >
						<input name="imageId" type="hidden" value="<s:property value="imageId" />" />
						<div>
							<img width="128" height="128" src="<s:property value="imagePath" /><s:property value="imgPath8" />"/>
						</div>
						<p class="editDataDiv" style="display: none;" >
							<s:if test='isDefault eq "0"'>
								<input type="hidden" id="checkRadioId" value="rpic_<s:property value="imageId" />" />
							</s:if>
							<span>
								<img src="/etc/images/little_icon/first.png" alt="默认" title="默认" onclick='javascript:setMainPic(<s:property value="skuId" />,<s:property value="imageId" />,this.parentNode.parentNode.parentNode);' />
								<%--
									<input name='radio' <s:if test='isDefault eq "0"'>checked='checked'</s:if> type='radio' id='rpic_<s:property value="imageId" />' value='1' onclick='javascript:setMainPic(<s:property value="skuId" />,<s:property value="imageId" />,this.parentNode.parentNode.parentNode);'>
									<label for="rpic_<s:property value="imageId" />">默认</label>
								 --%>
							</span>
							<span class=''>
								<img src="/etc/images/little_icon/delete.png" alt="删除" title="删除" onclick='delPic(this.parentNode.parentNode.parentNode,<s:property value="imageId" />,<s:property value="skuId" />)' />
								<%--
									<a href='javascript:;' onclick='delPic(this.parentNode.parentNode.parentNode,<s:property value="imageId" />,<s:property value="skuId" />)' >删除</a>
								--%>
							</span>
							<img src="/etc/images/little_icon/qianyi.png" alt="提前" title="提前" onclick="goForward(this.parentNode.parentNode);" />
							<img src="/etc/images/little_icon/houyi.png" alt="后退" title="后退" onclick="backUp(this.parentNode.parentNode);" />
						</p>
					</div>
				</s:iterator>
			</div>
			</form>
		</div>
	</div>
	</div>
	<iframe name="theID" style="display: none;"></iframe>  
  </body>
</html>
