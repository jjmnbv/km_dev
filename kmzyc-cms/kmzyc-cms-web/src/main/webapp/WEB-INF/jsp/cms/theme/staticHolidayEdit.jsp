<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>添加专题页</title>
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js">
</script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js">
</script>
		<script type="text/javascript" src="/etc/js/messages_cn.js">
</script>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />


		<link rel="stylesheet"
			href="/resource/kindeditor/themes/default/default.css" />
		<link rel="stylesheet"
			href="/resource/kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="/resource/kindeditor/kindeditor.js">
</script>
		<script charset="utf-8" src="/resource/kindeditor/lang/zh_CN.js">
</script>
		<script charset="utf-8"
			src="/resource/kindeditor/plugins/code/prettify.js">
</script>

<link rel="stylesheet" href="/etc/js/codemirror/codemirror.css">
<link rel="stylesheet" href="/etc/js/codemirror/fullscreen.css">
<link rel="stylesheet" href="/etc/js/codemirror/erlang-dark.css">
<script src="/etc/js/codemirror/codemirror.js"></script>
<script src="/etc/js/codemirror/xml.js"></script>
<script src="/etc/js/codemirror/fullscreen.js"></script>
<script src="/etc/js/codemirror/userdefined.js"></script>
		<script>
var editor;
KindEditor.ready(function(K) {
	var templateType = $("#templateType").val();
	editor = K.create('textarea[name="infor.Content_content"]', {
		cssPath : '/resource/kindeditor/plugins/code/prettify.css',
		uploadJson : '/resource/kindeditor/jsp/upload_json.jsp',
		fileManagerJson : '/resource/kindeditor/jsp/file_manager_json.jsp',
		allowFileManager : true
	});
});
$(function() {
	var templateType = $("#templateType").val();
	if (templateType != 3) {
		$("#content1").show();
		$("#content2").hide();
	} else {
		$("#content1").hide();
		$("#content2").show();
	}
});
function choiceArea() {
	var templateType = $("#templateType").val();
	if (templateType != 3) {
		$("#content1").show();
		$("#content2").hide();
	} else {
		$("#content1").hide();
		$("#content2").show();
	}
}

function checkSubmit() {
	editor.sync();
	$("form").submit();
}

function gotoList() {
	var pageForm = window.document.getElementById("pageForm");
	pageForm.action = "/cms/cmsThemeAction_queryStaticHolidayList.action";
	pageForm.submit();
}
</script>
	</head>
	<body>

		<!-- 导航栏 -->


		<style type="text/css">
.listTitle {
	height: 40px;
	line-height: 40px;
	background: #D6F2D9;
	border-bottom: 1px solid #079346;
	vertical-align: middle;
	font-size: 14;
	color: #028043;
	margin: 0px;
}

.listTitle span {
	padding-left: 20px;
	height: 30px;
	line-height: 30px;
	vertical-align: middle;
	margin-top: 5px;
}

.listTitle span img {
	vertical-align: middle;
}
</style>
		<!-- 导航栏 -->
		<div width="100%" class="listTitle" height="40">
			<span> <img src="/etc/images/icon_02.png" />&nbsp;&nbsp; <img
					src="/etc/images/icon_08.png" />&nbsp;&nbsp;基础功能&nbsp;&nbsp; <img
					src="/etc/images/icon_08.png" />&nbsp;&nbsp;静态专题页管理 &nbsp;&nbsp;<img
					src="/etc/images/icon_08.png" />&nbsp;&nbsp;专题页 </span>
		</div>
		<div style="height: 90%; overflow-y: auto;">
			<s:form id="pageForm" name="pageForm"
				action="/cms/cmsThemeAction_staticHolidayEdit.action" method="POST"
				enctype="multipart/form-data">
				<s:token></s:token>
				<!-- hidden properties -->
				<INPUT TYPE="hidden" id="inforId" name="infor.inforId"
					value="<s:property  value="infor.inforId" />">
				<input type="hidden" id="siteId" name="infor.siteId" value="<s:property  value="infor.siteId"/>"/>
				
				<!-- 数据编辑区域 -->
				<table width="60%" class="edit_table" cellpadding="3"
					cellspacing="0" border="1" bordercolor="#C7D3E2"
					style="border-collapse: collapse">
					<tr>
						<th colspan="2" align="left" class="edit_title">
							专题页信息
						</th>
					</tr>
					<!-- error message -->
					<tr>
						<td width="20%" align="right">
							<font color="red">*</font>专题名称：
						</td>
						<td width="80%" align="left">
							<input name="infor.name"  type="text"
								value="<s:property  value="infor.name" />" />
						</td>
					</tr>
					

					<tr>
						<td width="20%" align="right">
							<font color="red">*</font>输出路径：
						</td>
						<td width="80%">
							<input name="infor.content" id="infor.content" type="text"
								value="<s:property  value="infor.content"/>"><font color="red">例如：/test.html</font>
						</td>
					</tr>
					
					<tr>
						<td width="20%" align="right">
							模板类型
						</td>
						<td width="80%">
							<select name="infor.templateType" id="templateType" >
							
                                 <option value="7" <s:if test="infor.templateType==7">selected="selected"</s:if>>
										窄版静态专题页模板
								</option>
								<option value="8" <s:if test="infor.templateType==8">selected="selected"</s:if>>
										宽版静态专题页模板
								</option>
								<option value="9" <s:if test="infor.templateType==9">selected="selected"</s:if>>
										商家入驻模板
								</option>

						        <option value="17"
									<s:if test="infor.templateType==17">selected="selected"</s:if>>
									招商入驻
								</option>
								<option value="18"
									<s:if test="infor.templateType==18">selected="selected"</s:if>>
									合作模式
								</option>
								<option value="19"
									<s:if test="infor.templateType==19">selected="selected"</s:if>>
									商家规则
								</option>
								<option value="20"
									<s:if test="infor.templateType==20">selected="selected"</s:if>>
									商家店铺
								</option>
								<option value="21"
									<s:if test="infor.templateType==21">selected="selected"</s:if>>
									活动中心
								</option>
								<option value="22"
									<s:if test="infor.templateType==22">selected="selected"</s:if>>
									商户服务
								</option>
										
								
							
							</select>
						</td>

					</tr>
					<tr>
						<td width="20%" align="right">
							内容
						</td>
						<td width="80%" id="content1">
							<textarea name="infor.Content_content" cols="100" rows="8" style="width: 100%; height: 300px; visibility: hidden;"><s:property value="infor.Content_content"/></textarea>
						</td>
						<td id="content2" style="display: none;">
						         当光标在编辑器中，按F11键切换全屏幕编辑。ESC可退出全屏幕编辑。
							<textarea name="infor.InformContent" id="content" cols="100" rows="12" style="width: 100%; height: 400px;"><s:property value="infor.Content_content"/></textarea>
						  
						</td>
					</tr>

					<tr>
						<td width="20%" align="right">
							页面标题：
						</td>
						<td width="80%">
							<input name="infor.title" id="infor.title" type="text"
								value="<s:property  value="infor.title"/>">
						</td>
					</tr>
					<tr>
						<td width="20%" align="right">
							页面关键字：
						</td>
						<td width="80%">
							<input name="infor.key" id="infor.key" type="text"
								value="<s:property  value="infor.key"/>">
						</td>
					</tr>
					<tr>
						<td width="20%" align="right">
							页面描述：
						</td>
						<td width="80%">
							<textarea name="infor.description" cols="100" rows="8"style="width:100%; height:100px;"><s:property value="infor.description"/></textarea>
						</td>
					</tr>
					
					<tr>
						<td width="20%" align="right">
							页面脚本内容：
						</td>
						<td width="80%">
							<textarea name="infor.contentJs" cols="100" rows="8"style="width:100%; height:100px;"><s:property value="infor.contentJs"/></textarea>
						</td>
					</tr>
					
					<tr>
						<td width="20%" align="right">
							页面样式内容：
						</td>
						<td width="80%">
							<textarea name="infor.contentCss" cols="100" rows="8"style="width:100%; height:100px;"><s:property value="infor.contentCss"/></textarea>
						</td>
					</tr>


				</table>


				<!-- 底部 按钮条 -->
				<table width="60%" class="edit_bottom" height="30" border="0"
					cellpadding="0" cellspacing="0">
					<tr>
						<td align="left">
							<INPUT class="saveBtn" type="submit" value="">
							&nbsp;&nbsp;
							<input class="backBtn" onclick="gotoList()" type="button"
								value="">

						</td>
						<td width="20%" align="center"></td>
					</tr>
				</table>

				<br>
				<br>

			</s:form>




		</div>

	</BODY>
	<!-- 消息提示页面 -->


	<!-- 消息提示 -->
	<div width="100%">
		<!-- 消息提示 -->


	</div>

	<script>
	
$().ready(
		function() {
			$("#pageForm").validate( {
				rules : {
					"infor.content" : {
						required : true,
						maxlength : 20,
						compareTo : true
					},
					"infor.name" : {
						required : true,
						maxlength : 100,
						compareTo2 : true,
						compareTo3 : true
					},
					"infor.title" : {
						maxlength : 100
					},
					"infor.key" : {
						maxlength : 100
					},
					"infor.description" : {
						maxlength : 500
					}
				},
				success : function(label) {
					label.removeClass("checked").addClass("checked");

				}
			});

			
jQuery.validator.addMethod("compareTo", function(value, element) {
	var id = $("#inforId").val();
	var ok = "";
	$.ajax( {
		url : '/cms/cmsPromotion_outPutValidate.action',
		data : {
			"outPut" : value,
			"outPutType" : 4,
			"promotionId" : id
		},
		async : false,
		type : 'post',
		success : function(d) {
			if (d == "0") {
				ok = "0";
			} else {
				ok = "1";
			}
		}

	});
	if (ok == "0") {
		return true;
	} else {
		return false;
	}

}, "该文件已存在 ");


jQuery.validator.addMethod("compareTo3", function(value, element) {
	var inforId = $("#inforId").val();
	var ok = "";
	$.ajax( {
		url : '/cms/Information_name_ajax.action',
		data : {
			"infor.name" : value,
			"infor.inforId" : inforId
		},
		async : false,
		type : 'post',
		success : function(d) {
			if (d == "0") {
				ok = "0";
			} else {
				ok = "1";
			}
		}

	});
	if (ok == "0") {
		return true;
	} else {
		return false;
	}

}, "该名已存在 ");
jQuery.validator.addMethod("compareTo2", function(value, element) {
	if (value.length > 200) {
		return false;
	} else {
		return true;
	}
}, "名称不能长于200个汉字 ");
});


function choiceArea(){
		    var templateType = $("#templateType").val();
		    if(templateType!=9){
		      $("#content1").show();
		      $("#content2").hide();
		    }else{
		      $("#content1").hide();
		      $("#content2").show();
		    }
		}

/*
$(function(){
		     var templateType = $("#templateType").val();
		    if(templateType!=9){
		      $("#content1").show();
		      $("#content2").hide();
		    }else{
		      $("#content1").hide();
		      $("#content2").show();
		    }
		});
		*/
/*

function Submit() {
	$("#output").attr("disabled", false);
	$("form").submit();

}*/
</script>

</HTML>