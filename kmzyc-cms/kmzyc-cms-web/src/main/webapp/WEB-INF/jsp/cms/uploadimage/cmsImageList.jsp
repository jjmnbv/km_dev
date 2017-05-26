<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>图片列表</title>
		
    	<link rel="stylesheet" href="/etc/css/jquery-ui.min.css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/swfupload/jquery-ui-1.10.3.min.js"></script>
	   	<script type="text/javascript" src="<%=basePath%>/etc/js/swfupload/swfupload.js"></script>
	   	<script type="text/javascript" src="<%=basePath%>/etc/js/swfupload/swfupload.queue.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/etc/js/swfupload/fileprogress.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/etc/js/swfupload/handlers.js"></script>
		
		
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
		
		
		<script type="text/javascript">
		
		$(document).ready(function(){
			var timer_alert = setTimeout(function() {
				messageCloseThis();
			}, 2000);
			
    		var checks = "";
    		checks = $("#checkeds").val();
    		if(checks!=""){
        		var checkboxs = document.getElementsByName("levelId");
        		var myarr = new Array();
    			myarr = checks.split(',');
        		for(var i = 0; i<checkboxs.length; i++){
            		for(var j = 0; j<myarr.length; j++){
                		if(checkboxs[i].value==myarr[j]){
                			checkboxs[i].checked = true;
                			break;
                    	}
                	}
            	}
        	}
	   });
	   
	   
	   function jqdialog() {
			if($('.progressWrapper').length>0){
				$('.progressWrapper').remove();
			}
			$("#dialog").dialog({
				resizable: true,
				height: 500,
				width: 450,
				modal: true,
				close: function() {
             		window.location.href = "/cms/cmsImageAction_imageList.action";
         		}
			});
		}
		
		//初始化swfupload 对象
		var upload;

		window.onload = function() {
			upload = new SWFUpload({
				// 文件上传的处理程序
				upload_url: "/cms/cmsImageAction_uploadImageAll.action",
				file_post_name: "file",
				// 上传图片大小
				file_size_limit : "500KB",	// 100MB
				//可上传图片类型
				file_types : "*.jpg;*.gif;*.jpeg;*.png;*.bmp",
				file_types_description : "图片",//选取文件时的描述
				file_upload_limit : "0",//最大可以上传文件数量 0为不限制
				file_queue_limit : "10",//上传队列等待文件数的最大值

				file_dialog_start_handler : fileDialogStart,
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,

				////Flash上传按钮的图片
				button_image_url : "/etc/images/button/swfBtn.png",
				button_placeholder_id : "spanButtonPlaceholder",
				button_width: 61,
				button_height: 22,
				
				flash_url : "/etc/js/swfupload/swfupload.swf",
				
				custom_settings : {
					progressTarget : "fsUploadProgress",
					cancelButtonId : "btnCancel1"
				},
				
				// //是否调试模式
				debug: false
			});

	     }
	   
		</script>
		<style type="text/css">
			#dialog{display:none;}
		</style>
	</head>
	<body >
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'图片列表'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form class="pageForm" name="pageForm" id="pageForm" onsubmit="return checkAllTextValid(this)" action="/cms/cmsPageAction_queryForPage.action" method="post">
			<table width="98%" align="center" border="0"	class="content_table" cellpadding="0" cellspacing="0">
				<tr>
					<td width="60%" valign="middle" colspan="2">
						<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
						<INPUT class="addBtn" TYPE="button" value="" onClick="addImage()"/>
						<input class="addBtnUploadAll" type="button" onClick="jqdialog()" value="" /> 
					</td>
				</tr> 
				
			</table>
			<table width="98%" class="list_table" cellpadding="3" align="center">
				<tr>
					<!--<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAllpop(this,'levelId')">
					</th>
					--><th>名称</th>
					<th>路径</th>
					<th>操作</th>
				</tr>
			<s:iterator id="name" value="list">
				<tr>
					<!--<td width="5%">
							<input type="checkbox" name="levelId" id="levelId"
								value='<s:property value="pageId"/>' onclick="checkpromotionId(this);">
					</td>
					--><td><s:property value="name"/></td>
					<td><s:property value="path"/></td>
					<td>
						<img title="查看" style="cursor: pointer;" src="/etc/images/icon_preview.png"   onclick='viewImage("<s:property value="path"/>")' />
						<!-- <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick='deleteImage("<s:property value="name"/>")' />  -->
					</td>	
				</tr>
			</s:iterator>
			</table>
			
		</s:form>
					<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	<div id="dialog" title="图片批量添加">
	    <s:form action="/cms/cmsImageAction_uploadImageAll.action" method="post" name="thisform" enctype="multipart/form-data">
	    <s:token></s:token>
			<table>
				<tr valign="top">
					<td>
						<div>
							<div class="fieldset flash" id="fsUploadProgress">
								<span class="legend">图片上传</span>
							</div>
							<div style="padding-left: 5px;">
							<s:token></s:token>
								<span id="spanButtonPlaceholder"></span>
								<input id="btnCancel1" type="button" value="取消上传" onClick="cancelQueue(upload);" disabled="disabled" style="margin-left: 2px; height: 22px; font-size: 8pt;" />
								<br />
							</div>
						</div>
					</td>
				</tr>
			</table>
	    </s:form>
    </div>
	</body>
	<script type="text/javascript">
	
		//跳至增加页
		function addImage()
		{
			window.location.href="/cms/cmsImageAction_toAdd.action";
		}
		//查看图片
		function viewImage(imageName)
		{	
			window.open(imageName);
		}
		//删除图片
		function deleteImage(imageName)
		{
			var ok=confirm("确定删除吗？删除后将不能恢复！");
	   		if(ok==false)
	   		{
	   			return ;
	   		}
	   		else
	   		{
				window.location.href="/cms/cmsImageAction_deleteImageByName.action?imageName="+imageName;
			}
		}
	</script>
</html>
