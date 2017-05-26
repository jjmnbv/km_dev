<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <link href="/etc/js/swfupload/default.css" rel="stylesheet" type="text/css" />
   	<script type="text/javascript" src="/etc/js/swfupload/swfupload.js"></script>
   	<script type="text/javascript" src="/etc/js/swfupload/swfupload.queue.js"></script>
    <script type="text/javascript" src="/etc/js/swfupload/fileprogress.js"></script>
    <script type="text/javascript" src="/etc/js/swfupload/handlers.js"></script>
   	<!-- 初始化swfupload 对象-->
   <script type="text/javascript">
		var upload1, upload2;

		var uploadSuccess = function(file,data){
			
			document.getElementById("imgShowDiv").innerHTML = "<img src='/upload/NIKE.jpg'>";
			
			
		};
		
		
		window.onload = function() {
			upload1 = new SWFUpload({
				// Backend Settings
				upload_url: "/basedata/uploadProductImage.action",
				post_params: {"picSESSID" : "songhao"},
				file_post_name: "file",
				// File Upload Settings
				file_size_limit : "102400",	// 100MB
				file_types : "*.*",
				file_types_description : "All Files",
				file_upload_limit : "10",
				file_queue_limit : "0",

				// Event Handler Settings (all my handlers are in the Handler.js file)
				file_dialog_start_handler : fileDialogStart,
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,

				// Button Settings
				button_image_url : "/etc/js/swfupload/XPButtonUploadText_61x22.png",
				button_placeholder_id : "spanButtonPlaceholder1",
				button_width: 61,
				button_height: 22,
				
				// Flash Settings
				flash_url : "/etc/js/swfupload/swfupload.swf",
				

				custom_settings : {
					progressTarget : "fsUploadProgress1",
					cancelButtonId : "btnCancel1"
				},
				
				// Debug Settings
				debug: false
			});
	     };
	    
	     
	     function flashUploadStart() {     	
	    	 upload1.startUpload();
	      }
	     
	     
	</script>
  </head>
  
  <body>
  <div id="content">
	<h2>Multi-Instance Demo</h2>
    <form action="pictureAction" method="post" name="thisform" enctype="multipart/form-data">
    	<p>This page demonstrates how multiple instances of SWFUpload can be loaded on the same page.
			It also demonstrates the use of the graceful degradation plugin and the queue plugin.</p>
		<table>
			<tr valign="top">
				<td>
					<div>
						<div class="fieldset flash" id="fsUploadProgress1">
							<span class="legend">Large File Upload Site</span>
						</div>
						<div style="padding-left: 5px;">
							<span id="spanButtonPlaceholder1"></span>
							<input id="btnCancel1" type="button" value="Cancel Uploads" onclick="cancelQueue(upload1);" disabled="disabled" style="margin-left: 2px; height: 22px; font-size: 8pt;" />
							<input type="button" value="开始上传" onclick="flashUploadStart"  />
							<br />
						</div>
					</div>
				</td>
			</tr>
		</table>
    </form>
    <div id="imgShowDiv">
    	
    </div>
    </div>
    
  </body>
</html>
