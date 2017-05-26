<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template_page.jsp">
	<jsp:param name="titlePrefix" value="修改运费"></jsp:param>
</jsp:include>

<link type="text/css" rel="stylesheet" href="${staticUrl}${cssBaseUrl }bootstrap.css?version=${version}"/><!--缓存头部共用样式 -->
<link type="text/css" rel="stylesheet" href="${staticUrl}${cssBaseUrl }bootstrap.min.css?version=${version}"/><!--缓存头部共用样式 -->
<%--<link type="text/css" rel="stylesheet" href="${staticUrl}${cssBaseUrl }css.css?version=${version}"/><!--缓存头部共用样式 -->--%>
<link type="text/css" rel="stylesheet" href="${staticUrl}${cssBaseUrl }styles.css?version=${version}"/><!--缓存头部共用样式 -->
<script type="text/javascript" src="${staticUrl}/resgys/script/seajs/2.0.2/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticUrl}/resgys/script/config.js?version=${version}"></script>
<script type="text/javascript">
    seajs.use(['${staticUrl}/resgys/script/view/jsp/order/orderUpdateFare.js']);
</script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改运费</title>
</head>
<body>

<div class="block-content collapse in">
<form class="form-horizontal" id="orderInfoFrm">

<input id="orderCode" type="hidden" name="infoMap['orderCode']" value='<s:property value="order.orderCode"/>'/>
<fieldset>
	<legend>运费信息&nbsp;&nbsp;<font style="color:#379945;">订单号：<s:property value="order.orderCode"/></font></legend>
	</br>
<div class="control-group"><label class="control-label"
	for="typeahead"><span class="required">*</span>运&nbsp;&nbsp;费： </label>
<div class="controls"><input type="text" class="width120" value="<s:property value='order.fare'/>" id="newFare" data-provide="typeahead"></div>
</div>
</fieldset>
<div class="btnnobgmini">
<a href="javascript:void(0);" class="btn btn-primary" id="updateFare">保存</a>
<a href="javascript:void(0);" class="btn btn-primary" id="closeId">取消</a></div>
</form>
</body>
</html>