<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>窗口数据管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
			<link href="/etc/css/tpl.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/windowData.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>

	
	</head>
	<body>
		<div class="ui-dialog-z" style=" width: 650px;">
    <div class="ui-dialog-z-header">
        <h4>修改自定义信息</h4>
      
    </div>
    <div data-role="content" class="ui-dialog-z-content" >
        <div class="ui-tipbox-z-content">
            <div class="ui-tipbox ui-tipbox-success ui-tipbox-white">
                	<s:form name="dateForm" id="dateForm" action="/shop/cmsWindowDataAction_saveShopData.do" method="post" enctype="multipart/form-data">
		<s:token></s:token>
		<input type="hidden" id="windowId" name="cmsWindowData.windowId" value="<s:property value='cmsWindowData.windowId' />">
		<input type="hidden" id="pageId" name="pageId" value='<s:property value="pageId"/>'>
		<input type="hidden" id="adminType" name="adminType" value='<s:property value="adminType"/>'>
		<input type="hidden" id="cmsWindowData.windowDataId" name="cmsWindowData.windowDataId" value="<s:property value='cmsWindowData.windowDataId' />">
		<input type="hidden" name="shopI" id="shopI" value="<s:property value='shopI'/>">
                   
                        
                        
                        <div class="ui-tab">
                            
                            <div class="">
                              
                                    <div class="ui-form-item">
                                        <label for="" class="ui-label">数据名称</label>
                                        <input type="hidden" name="cmsWindowData.user_defined_type" value="<s:property value="cmsWindowData.user_defined_type"/>"/>
                                        <input type="text" class="ui-input ui-input280" name="cmsWindowData.user_defined_name" value="<s:property value="cmsWindowData.user_defined_name"/>">
                                    </div>
                                   <div class="ui-form-item">
                                        <label for="" class="ui-label">URL</label>
                                        <input type="text" class="ui-input ui-input280" name="cmsWindowData.user_defined_url" value="<s:property value="cmsWindowData.user_defined_url"/>">
                                    </div>
                                    
                                </div>
                               
                            </div>
                        </div>
                    
                </s:form>
            </div>
        </div>
    </div>
    <div class="ui-dialog-z-footer">
        <a class="btn ui-btn-save" onclick="onsave();">保存</a>
        <a class="btn ui-btn-cancel" onclick="closeWindow ();">取消</a>
    </div>
</div>
	</body>
	<script type="text/javascript">
	
	function closeWindow(){
	try {
				document.domain = 'kmb2b.com';
            	window.frameElement.trigger('close');
	        } catch (error) {
	            window.console && console.log('error',error);
	        }
}
	
	
	function onsave(){
	//	 var defined_name=$("#defined_name0").val();
		
	
	
	var pageForm= window.document.getElementById("dateForm");
	var shopI=$('#shopI').val();
		var windowId=$('#windowId').val();
	
	
	pageForm.action="/shop/cmsWindowDataAction_saveShopData.do?shopI="+shopI+'&windowId='+windowId;
	//alert(pageForm.action);
	$('#dateForm').submit()
	
	
	

	
}
		function checkImg()
		{
			var user_defined_picpath=document.getElementById("user_defined_picpath").value;
			var user_defined_type= document.getElementById("user_defined_type").value;
			var path=user_defined_picpath.split(".");
			if(user_defined_type==1&&path[path.length-1]!="jpg"&&user_defined_picpath!='')
			{
				var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>图片格式有错，请确认为jpg格式的图片";
		    	messageDialog("消息提示","text:"+msg ,"300px","102px","text");
				return false;
			}
		}
		function disableUpload(obj)
		{
			if(obj.value==0)
			{
				document.getElementById("user_defined_picpath").setAttribute("disabled", "disabled");
				document.getElementById("preview").style.display="none";
			}else
			{
				document.getElementById("user_defined_picpath").removeAttribute("disabled");
				document.getElementById("preview").style.display="";
			}
		}
		function setImagePreview() {
			var docObj = document.getElementById("user_defined_picpath");
			var imgObjPreview = document.getElementById("preview");

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
	</script>
</html>

