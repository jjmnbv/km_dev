<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/images/style.css" rel="stylesheet" type="text/css" media="screen" />
<title>upload</title>
<style type="text/css">
<!--
body {
	margin-top: 5px;
}
-->
</style>

</head>
<body>


<form name="form1" method="post" action="uploadFile.action" enctype="multipart/form-data">
<input type="hidden" name="toPerform" value="edit">

<table height="50">

	<tr>
	  <td width="87%" height="20" align="left" bgcolor="#FFFFFF"><input type="file" name="upFile" id="fileupload"/>&nbsp;&nbsp;
      <input type="submit" name="sub" value="上 传"></td>
	</tr>
</table>
${uploadResult}
</form>

</body>
</html>
