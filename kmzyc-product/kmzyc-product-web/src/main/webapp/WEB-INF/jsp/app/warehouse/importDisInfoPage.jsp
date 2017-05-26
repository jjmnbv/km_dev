<%@page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<style>
/*文件上传样式：*/
.upfile ul{ width:100%; float:left; list-style:none; overflow:hidden; margin-top:5px;}
.upfile ul li{ width:280px; line-height:40px; float:left; color:#999;}
.upfile ul li label{float:left; text-align:right; margin-right:10px;color:#333; width:80px;line-height:40px;}
.uploader{position:relative;display:inline-block;overflow:hidden;cursor:default;padding:0;float: left;}
.filename{float:left;display:inline-block;outline:0 none;height:31px;width:150px;margin:0;padding:8px 10px;overflow:hidden;cursor:default;white-space:nowrap; border-bottom:1px solid;color:#777; border-left:1px solid;color:#777; border-top:1px solid;color:#777; border-right:1px solid;color:#777}
.button{cursor: pointer;float:left;height:30px;display:inline-block;outline:0 none;padding:8px 12px;margin:0;cursor:pointer;border:1px solid;}
.uploader input[type=file]{position:absolute;top:0;right:0;bottom:0;border:0;padding:0;margin:0;height:30px;cursor:pointer;filter:alpha(opacity=0);-moz-opacity:0;-khtml-opacity:0;opacity:0}
.white .button{cursor: pointer;color:#fff;background:#369;background:-moz-linear-gradient(top,#369 0%,#369 100%);background:-webkit-gradient(linear,left top,left bottom,color-stop(0%,#369),color-stop(100%,#369));filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#369',endColorstr='#369',GradientType=0);border-color:#369}
.white:hover .button{background:#369;background:-moz-linear-gradient(top,#369 0%,#369 100%);background:-webkit-gradient(linear,left top,left bottom,color-stop(0%,#369),color-stop(100%,#369));filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#369',endColorstr='#369',GradientType=0)}
.edit_table td{padding: 5px;}
</style>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配送单管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">

<script src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="/etc/js/warehouse/distributionInfo.js"></script>

</head>
<body>
<s:set name="parent_name" value="'配送单管理'" scope="request" />
<s:set name="name" value="'导入订单配送信息'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	<!-- 数据编辑区域 -->
<form id="importDisFrm" enctype="multipart/form-data">
	<table id="dataTable" width="97%" class="edit_table" align="center" cellpadding="0"cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<tr>
			<th align="right" width="10%"><font color="red">*</font>导入文件:</th>
			<td >
				<div class="uploader white">
                  <input type="text" id ="show_file_path" class="filename" readonly="readonly"/>
                  <input type="button" name="file" class="btn-custom" value="浏览"/>
                  <input type="file" id="importDisFile" name="importDisFile" size="30" onchange="showFileInfo();"/>&nbsp;&nbsp;
                </div>
                <input class="btn-custom" type="button" style="height:30px" value="导入" onclick="doImportDisInfo();"/>&nbsp;&nbsp;
		  		<a href="/app/downloadDistributionTemplate.action" ><input class="btn-custom" type="button" style="height:30px" value="下载导入模板"/></a>
		  	</td>
		</tr>
		<tr>
		  	<th align="right">
		  		<font color="red">*</font>导入结果:
		  	</th>
	  		<td colspan="2">
	  			<textarea id="importResult" readonly="readonly"></textarea>	  
  			</td>
		</tr>
		<tr>
	  		<td colspan="3">
	  			<input type="button" class="btn-custom btnStyle" value="返回" onclick="gotodistributionInfoList();">
  			</td>
		</tr>
	</table>
</form>
</body>
</html>


