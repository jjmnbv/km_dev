<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加产品对应SkuId</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script src="/etc/js/checkout.js"></script>
</head>
<body>
 
 <!-- 导航栏 -->
 <script>
$(document).ready(function(){
 	$("#addPageForm").validate({
         rules: {
				"newSkuId": {required:true,number:true,maxlength:12},
				"skuId":{required:true,number:true,maxlength:12}
	        	},
	     success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
 });
 </script>
 

<style type="text/css">
.listTitle{height:40px;line-height:40px;background:#c7e3f1;border-bottom:1px solid #c7e3f1;vertical-align:middle;font-size:14;color:#333;margin:0}
.listTitle span{padding-left:20px;height:30px;line-height:30px;vertical-align:middle;margin-top:5px}
.listTitle span img{vertical-align:middle}
</style>
<!-- 导航栏 -->
<div width="100%" class="listTitle"  height="40">
    <span>
	<img src="/etc/images/icon_02.png"  />&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;基础功能&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;产品新旧SkuId维护&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;添加产品对应SkuId
	  </span>
</div>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:95%;" >
<s:form id="addPageForm" name="addPageForm" action="/cms/productIdUpholdAction_addUphold.action" method="POST">
<s:token></s:token>
<!-- hidden properties -->
<table width="98%" class="detail_table list_table" cellpadding="3" align="center">
<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="edit_title">产品对应SkuId信息</th>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>现有产品SkuId：</td>
		<td width="80%">
			<input name="newSkuId" id="newSkuId" type="text" value=""/><span id="nameMsg" style="display:none;margin-left:5px;color:red;">现有产品SkuId不能为空</span>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>之前产品SkuId：</td>
		<td width="80%">
			<input name="skuId" id="skuId" type="text" value=""/><span id="nameMsg" style="display:none;margin-left:5px;color:red;">之前产品SkuId不能为空</span>
		</td>
	</tr>
	
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<INPUT class="saveBtn" TYPE="submit" value="">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="javascript:window.history.back(-1)" type="button" value="">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form>
</div>
</BODY>
</HTML>