<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>文件上传</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;

}

.this-button-3s { /*适用于3个字有标示按钮*/
	font-size: 12px;
	color: #333333;
	border-top: 0px solid #EFEFEF;
	border-right: 0px solid #999999;
	border-bottom: 0px solid #EFEFEF;
	border-left: 0px solid #EFEFEF;
	text-decoration: none;
	FONT-FAMILY: "Tahoma", "Arial", "新宋体", "宋体";
	cursor: hand;
	background-image: url(/etc/images/button/button-3z.gif);
	height: 20px;
	width: 65px;
}
.this-button-3 { /*适用于3个字无标示按钮*/
	font-size: 12px;
	color: #333333;
	border-top: 0px solid #EFEFEF;
	border-right: 0px solid #999999;
	border-bottom: 0px solid #EFEFEF;
	border-left: 0px solid #EFEFEF;
	text-decoration: none;
	FONT-FAMILY: "Tahoma", "Arial", "新宋体", "宋体";
	cursor: hand;
	background-image: url(/etc/images/button/button_3z.gif);
	height: 20px;
	width: 65px;
}
.file{border:1px solid #333333;color:#666666;background:#eeeeee;font:normal 12px Tahoma;height:18px} 
-->
</style>
	</head>
	<body>
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td  align="center" valign="middle">
					<form action="/sys/uploadSysUploadFile.action" method="post"
						enctype="multipart/form-data" name="form1"> 
						<s:file name="doc" class="file"/>
						<input type="submit" name="Submit" value="上传" class="this-button-3">
						<input type="hidden" name="rootPath" value="<s:property value='rootPath'/>"/>
						<input type="hidden" name="relativePath" value="<s:property value='relativePath'/>"/>
						<input type="hidden" name="isDate" value="<s:property value='isDate'/>"/>
						<input type="hidden" name="fileFlag" value="<s:property value='fileFlag'/>"/>
						<input type="hidden" name="isMultiple" value="<s:property value='isMultiple'/>"/>
						<input type="hidden" name="specPath" value="<s:property value='specPath'/>"/>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
