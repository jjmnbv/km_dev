<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>窗口数据管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/windowData.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>

		<style type="text/css">
			.list_table tr input
			{
				width:220px;
			}
		</style>
	</head>
	<body>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="dateForm" id="dateForm" action="/cms/cmsWindowDataAction_savaUserDefineDateMod.action" method="post" enctype="multipart/form-data">
		<s:token></s:token>
		<input type="hidden" id="windowId" name="windowId" value="<s:property value='windowId' />">
		<input type="hidden" id="pageId" name="pageId" value='<s:property value="pageId"/>'>
		<input type="hidden" id="adminType" name="adminType" value='<s:property value="adminType"/>'>
		<input type="hidden" id="cmsWindowData.windowDataId" name="cmsWindowData.windowDataId" value="<s:property value='cmsWindowData.windowDataId' />">
			<!-- 数据列表区域 -->
			
			<table width="95%" class="list_table" cellpadding="3" align="center" id="list_table">	
				<tr>
					<td style="text-align: right;">数据类型:</td>
					<td style="text-align: left">
						<select name="cmsWindowData.user_defined_type" onchange="disableUpload(this)" id="user_defined_type">
							 <option value="0" <s:if test="cmsWindowData.user_defined_type==0">selected="selected"</s:if>>文本</option>
	        				 <option value="1" <s:if test="cmsWindowData.user_defined_type==1">selected="selected"</s:if>>图片</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">数据名称:</td>
					<td style="text-align: left"><input type="text" name="cmsWindowData.user_defined_name" id="user_defined_name" value="<s:property value="cmsWindowData.user_defined_name"/>"></td>
				</tr>
				<tr>
					<td style="text-align: right;">URL:</td>
					<td style="text-align: left"><input type="text" name="cmsWindowData.user_defined_url" id="user_defined_url" value="<s:property value="cmsWindowData.user_defined_url"/>"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">自定义数据:</td>
					<td style="text-align: left"><input type="text" name="cmsWindowData.remark" id="remark" value="<s:property value="cmsWindowData.remark"/>"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">图片地址:</td>
					<td><!-- <input type="file" id="user_defined_picpath" name="cmsWindowData.user_defined_picpath" <s:if test="cmsWindowData.user_defined_type==0">disabled="disabled"</s:if>/></td>-->
					<input type=file name="img" id="user_defined_picpath" <s:if test="cmsWindowData.user_defined_type==0">disabled="disabled"</s:if> 
						onchange="javascript:setImagePreview();" style=" border: 1px #6699CC solid;display:block;"/>
					<img id="preview" <s:if test="cmsWindowData.user_defined_picpath!=null"> width="300px" height="120px"</s:if><s:if test="cmsWindowData.user_defined_type==0">style="float:left;display:none;"</s:if> 
						src="<s:property value="cmsWindowData.picFullPath"/>" />
					</td>
				</tr>
			</table>
			<!-- 底部 按钮条 -->
			<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
				<tr> 
					<td align="left">
						<INPUT class="saveBtn" TYPE="submit" value="">
					</td>
				</tr>
			</table>
		</s:form> 
		
		<!-- 消息提示页面 --> 
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
	<script type="text/javascript">
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

