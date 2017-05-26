<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  	<head>
		<title>产品图片维护</title>
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
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
		var upnums;
		$(document).ready(function() {
			var counts = $("#swfUploadCounts").val();
			upnums = new Array(counts);
			for(var i = 0;i<counts;i++){
				upnums[i] = 1;
			}

			swfupload = new Array(counts);
			for(var i = 0;i<counts;i++){
				swfupload[i] = new SWFUpload({
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
					button_placeholder_id : "spanButtonPlaceholder"+i,
					button_width: 61,
					button_height: 22,
					// Flash Settings
					flash_url : "/etc/js/swf/swfupload.swf",
					custom_settings : {
						progressTarget : "fsUploadProgress"+i,
						cancelButtonId : "btnCancel"+i,
						uploadButtonId : "btnStartUpload"+i
					},
					// Debug Settings
					debug: false
				});
			}

			$('#btnSave').click(function(){
				if($("input[type='button'][value='保存']").length>0){
					alert("请保存图片顺序！");
					return;
				}
				window.location = '/app/productDraftShow.action?type=product';
			});
		 });

		function flashUploadStart(index) {
			//需要修改的地方，获取产品Id
			swfupload[index].addPostParam("productSkuId",$("#productSkuId"+index).val());
			swfupload[index].addPostParam("productNo",$("#productNo").val());
			swfupload[index].addPostParam("productId",$("#productId").val());
			swfupload[index].addPostParam("uploadpreview","uploadpreview_"+index);
			swfupload[index].startUpload();

			if($("#uploadpreview_"+index+" div").length == 0){
				upnums[index] = 1;
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
			image.src = data[2];
			image.width = 128;
			image.height = 128;
			var index = parseInt(data[3].substring(data[3].indexOf("_")+1,data[3].length));
			if(upnums[index] == 1){
				review.className = "preview isDefault";
			}else{
				review.className = "preview";
			}

			var str = "";
			if(upnums[index] == 1){

				str = "<span><img src='/etc/images/little_icon/first.png' alt='默认' title='默认' id='rpic_"+picId+"' onclick='javascript:setMainPic("+productSkuId+","+picId+","+index+",this.parentNode.parentNode.parentNode);' /></span>";
				str += "<span class=''><img src='/etc/images/little_icon/delete.png' alt='删除' title='删除' onclick='delPic(this.parentNode.parentNode.parentNode,\""+data[0]+"\","+productSkuId+","+index+",\""+data[3]+"\")' /></span>";
				str += "<img src='/etc/images/little_icon/qianyi.png' alt='提前' title='提前' onclick='goForward(this.parentNode.parentNode);' />";
				str += "<img src='/etc/images/little_icon/houyi.png' alt='后退' title='后退' onclick='backUp(this.parentNode.parentNode);' />";

				p.innerHTML = str;
				$.post(
					"/app/modifyProductImageDraftDefault.action",
					{productSkuId:productSkuId,picId:picId},//需要修改的地方
					function(data){
						if(data=="success"){

						}
					}
				);
			}else{
				str = "<span><img src='/etc/images/little_icon/first.png' alt='默认' title='默认' id='rpic_"+picId+"' onclick='javascript:setMainPic("+productSkuId+","+picId+","+index+",this.parentNode.parentNode.parentNode);' /></span>";
				str += "<span class=''>&nbsp;<img src='/etc/images/little_icon/delete.png' alt='删除' title='删除' onclick='delPic(this.parentNode.parentNode.parentNode,\""+data[0]+"\","+productSkuId+","+index+",\""+data[3]+"\")' /></span>";
				str += "<img src='/etc/images/little_icon/qianyi.png' alt='提前' title='提前' onclick='goForward(this.parentNode.parentNode);' />";
				str += "<img src='/etc/images/little_icon/houyi.png' alt='后退' title='后退' onclick='backUp(this.parentNode.parentNode);' />";

				p.innerHTML = str;
			}

			if(document.getElementById(data[3]).getElementsByTagName("input")[0].value == "调整图片"){
				p.style.display = "none";
			}

			div.appendChild(image);
			review.appendChild(_input);
			review.appendChild(div);
			review.appendChild(p);
			document.getElementById(data[3]).appendChild(review);
			document.getElementById(data[3]).getElementsByTagName("input")[0].disabled = "";
			upnums[index]++;
		}

        function setMainPic(productSkuId, picId, index, arg) {
            $(arg).addClass("isDefault").siblings().removeClass("isDefault");
            if ($(arg).parent().children("div:first")[0] == arg) {
                return;
            }
            $(arg).parent().children("div:first").before(arg);
        }

        function ajaxsetMainPic(productSkuId, picId, index) {
            $.post("/app/modifyProductImageDraftDefault.action",
                {productSkuId: productSkuId, picId: picId},//需要修改的地方
                function (data) {
                    if (data == "success") {
                        $("#uploadpreview_" + index).find("div.preview:first").addClass("isDefault")
                            .siblings().removeClass("isDefault");
                    }
                }
            );
        }

		function delPic(arg,picId,productSkuId,index,divId){
			if(confirm('您确定要删除这张图吗？')){
			$.post("/app/deleteProductImageDraft.action",
				{picId : picId},
				function(data) {
					if (data=="success") {
						$p = $(arg).parent();
						$(arg).remove();
						if($p.find(".preview").length==0){
							$p.find(".btnStyle").val("调整图片");
							$p.find(".btnStyle").attr("disabled","disabled");
						}else{
							if($("#uploadpreview_"+index).find("div.isDefault").length==0){
								$("#uploadpreview_"+index).find("div.preview:first").addClass("isDefault");
								var idText = $("#uploadpreview_"+index).children("div:first")[0].id;
								var id = idText.substring(idText.indexOf("_")+1,idText.length);
								ajaxsetMainPic(productSkuId,id,index);
							}
						}
					}
				},"text");
			}
		}

		function goForward(arg){
			$(arg).prev("div").before(arg);
			if(arg==$(arg).parent().children("div:first")[0]){
				$(arg).parent().children("div:first").addClass("isDefault").siblings().removeClass("isDefault");
			}
		}

		function backUp(arg){
			var flag = false;
			if(arg==$(arg).parent().children("div:first")[0]){
				flag = true;
			}
			$(arg).next("div").after(arg);
			if(flag){
				$(arg).parent().children("div:first").addClass("isDefault").siblings().removeClass("isDefault");
			}
		}

		function editImage(arg,frmId){
			if(arg.value=="调整图片"){
				$(arg).parent().parent().find(".editDataDiv").css("display","");
				arg.value = "保存";
			}else{
				$(arg).parent().parent().find(".editDataDiv").css("display","none");
				arg.value = "调整图片";
				$("#"+frmId).submit();
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
		.adbt{
			float:none;
			width:98%;
			height:auto;
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
  	<input type="hidden" value="<s:property value='#request.swfUploadCounts' />" id="swfUploadCounts" />
 	<div class="container">
 		<input id="productNo" type="hidden" value="<s:property value="product.productNo" />" />
 		<input id="productId" type="hidden" value="<s:property value="productId" />" />
 		<div class="adbt" style="color:black;">
	 		产品图片上传（小提示：一个SKU商品最多上传10张产品图片, 单张图片大小不超过1M。）
	 		<p id="uploadTip">产品名称：<b><s:property value="product.productName" /></b></p>
 		</div>
 		<s:iterator value="#request.productSkuList" var="data" status="st" >
 		<input id="productSkuId<s:property value='#st.index' />" type="hidden" value="<s:property value="#data.productSkuId" />" />
		<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse; font-size: 12px;margin-left:0px;margin-top:10px;">
			<tr>
				<td>
					<div class="userprofile batchupload"  style="float:left;width:270px;clear: both;">
						<!--upload-->
						<fieldset>
							<p style="font-size:14px;">
								<s:if test='#data.value.size() == 0'>
									无SKU规格
								</s:if>
								<s:else>
									<s:iterator value="#data.attributeValues" >
										<s:property value="attribute" />：<b><s:property value="value" /></b>&nbsp;&nbsp;&nbsp;
									</s:iterator>
								</s:else>
							</p>
							<div class="" id="fsUploadProgress<s:property value='#st.index' />"></div>
						</fieldset>
						<p class="btm">
							<span id="spanButtonPlaceholder<s:property value='#st.index' />"></span>
							<button id="btnStartUpload<s:property value='#st.index' />" class="table_btn" onclick="javascript:flashUploadStart(<s:property value='#st.index' />);" disabled="disabled">点击上传</button>
							<button id="btnCancel<s:property value='#st.index' />" class="table_btn" onclick="cancelQueue(swfupload[<s:property value='#st.index' />]);" style="visibility: hidden;" disabled="disabled">取消</button>
						</p>
					</div>
				</td>			
				<td>
					<form action="/app/updateImageSortNoDraft.action" method="post" name="imageFrm" id="imageFrm_<s:property value='#st.index' />" target="theID" >
						<div id="uploadpreview_<s:property value='#st.index' />" class="" style="float:left;width:850px;" >
							<p style="margin:0px;margin-bottom: 3px;" ><input type="button" value="调整图片" disabled="disabled" class="btnStyle" onclick="editImage(this,'imageFrm_<s:property value='#st.index' />');"/></p>
						</div>
					</form>
				</td>			
			</tr>
		</table>
		</s:iterator>
		<button id="btnSave" class="table_btn" style="margin-top:10px;" >完成</button>
	</div>
	<iframe name="theID" style="display: none;"></iframe>  
  </body>
</html>
