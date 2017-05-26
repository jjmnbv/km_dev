<%@page contentType="text/html;charset=UTF-8" import="com.pltfm.sys.util.StaticParams"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>地址清理结果</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />
	</head>
	<body>
	<br/>
	
	<div style="align:left;font-size:16px;font-weight:bold;color:red；border-bottom:2px solid #000;">&nbsp;清理的缓存地址信息如下:</div>
		<div>------------------------------------------------------------------------------------------------------</div>
		<s:iterator value="#request.clearResult" id="maps">
   			<div style="align:left;font-size:12px;">&nbsp;&nbsp;用户ID：<span style="color:red"> <s:property value="key"/></span></div>    
   			<div style="align:left;font-size:12px;color:black">&nbsp;&nbsp;地址  ：     <s:property value="value"/></div>
   			<div>------------------------------------------------------------------------------------------------------</div>
  		</s:iterator>
	</body>
</html>