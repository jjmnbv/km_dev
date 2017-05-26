<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<% String staticUrl = ConfigurationUtil.getString("CSS_JS_PATH"); %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>扫码下载app-康美中药城商城</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<style type="text/css">
.modal-dialog-bg {position: fixed;top: 0;right: 0;bottom: 0;left: 0;z-index: 2000;background-color: #000; opacity:.5;}
.fx-img { position:fixed; top:0; left:0; z-index:2001;width:100%;}
.fx-img1 { position:fixed; top:0; left:0; z-index:2002;width:100%;}
.fx-img2 { position:fixed; bottom:0; left:0; z-index:2001;width:100%;}
.fx-img img,.fx-img1 img,.fx-img2 img { width:100%;}
</style>
</head>
<body>
<!--分享提示-->
<s:if test="appType=='ios'">
<div class="fx-img1" style="display:;"><img src="<%=staticUrl%>images/fx-wap-ios-img.png"/></div>
<div class="fx-img2" style="display:;"><img src="<%=staticUrl%>images/fx-wap-ios-img2.png"/></div>
</s:if>
<s:elseif test="appType=='android'">
<div class="fx-img" style="display:;"><img src="<%=staticUrl%>images/fx-wap-img.png"/></div>
</s:elseif>
<div class="modal-dialog-bg"  style="display:;"></div>
</body>
</html>
