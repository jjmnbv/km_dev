<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template_page.jsp">
	<jsp:param name="titlePrefix" value="修改订单信息"></jsp:param>
</jsp:include>

<link type="text/css" rel="stylesheet" href="${staticUrl}${cssBaseUrl }bootstrap.css?version=${version}"/><!--缓存头部共用样式 -->
<link type="text/css" rel="stylesheet" href="${staticUrl}${cssBaseUrl }bootstrap.min.css?version=${version}"/><!--缓存头部共用样式 -->
<%--<link type="text/css" rel="stylesheet" href="${staticUrl}${cssBaseUrl }css.css?version=${version}"/><!--缓存头部共用样式 -->--%>
<link type="text/css" rel="stylesheet" href="${staticUrl}${cssBaseUrl }styles.css?version=${version}"/><!--缓存头部共用样式 -->
<script type="text/javascript" src="${staticUrl}/resgys/script/seajs/2.0.2/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticUrl}/resgys/script/config.js?version=${version}"></script>
<script type="text/javascript">
    seajs.use(['${staticUrl}/resgys/script/view/jsp/order/orderEdite.js']);
</script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改订单信息</title>
</head>
<style>body{background-color:#fff!important;}</style>
<body>
<div class="container-fluid" >
     <div class="row-fluid" style="min-width:600px!important">
<div class="block-content collapse in">
<form class="form-horizontal" id="orderInfoFrm">

<input id="orderCode" type="hidden" name="infoMap['orderCode']" value='<s:property value="order.orderCode"/>'/>
<fieldset>
	<legend>收货信息  &nbsp;&nbsp;<font style="color:#379945;">订单号：<s:property value="order.orderCode"/></font></legend>
<div class="control-group">
<label class="control-label" for="typeahead"><span class="required">*</span>收货人： </label>
<div class="controls"><input type="text" class="width200" name="infoMap['consigneeName']" value="<s:property value='order.consigneeName'/>" id="receiver" data-provide="typeahead" data-items="4"></div>
</div>

<div class="control-group"><label class="control-label"
	for="typeahead"><span class="required">*</span>手机： </label>
<div class="controls"><input type="text" class="width200" name="infoMap['consigneeMobile']" value="<s:property value='order.consigneeMobile'/>" id="telephone" data-provide="typeahead" data-items="4"></div>
</div>

<div class="control-group"><label class="control-label"
	for="typeahead">固定电话： </label>
<div class="controls"><input type="text" class="width200" name="infoMap['consigneeTel']" value="<s:property value='order.consigneeTel'/>" id="typeahead" data-provide="typeahead" data-items="4"></div>
</div>

<div class="control-group"><label class="control-label"
	for="typeahead">邮箱： </label>
<div class="controls"><input type="text" class="width200" name="infoMap['email']" value="<s:property value='order.email'/>" id="typeahead" data-provide="typeahead" data-items="4"></div>
</div>

<div class="control-group"><label class="control-label"
	for="typeahead">配送方式： </label>
<div class="controls">
<select id="deliveryDateType" name="infoMap['deliveryDateType']" value='<s:property value="order.deliveryDateType"/>' class="ui-form-select" >
  				<option value="1" <s:if test="order.deliveryDateType==1">selected='true'</s:if> >工作日送货</option>
  				<option value="2" <s:if test="order.deliveryDateType==2">selected='true'</s:if>>休息日送货</option>
  				<option value="3" <s:if test="order.deliveryDateType==3">selected='true'</s:if>>工作日/休息日皆可送货</option>
	   	 	</select>
</div>
</div>

<div class="control-group"><label class="control-label"
	for="typeahead"><span class="required">*</span>收货地址： </label>
<div class="controls"><input type="text" class="width300" name="infoMap['consigneeAddr']" value="<s:property value='order.consigneeAddr'/>" id="address" data-provide="typeahead" data-items="4"></div>
</div>
 </fieldset>
 
 
 
 <fieldset>
	<legend>发票信息</legend>
<div class="control-group"><label class="control-label"
	for="typeahead">是否开具发票： </label>
<div class="controls">
<select id="hasInvoice" class="ui-form-select" name="infoMap['hasInvoice']" >
    				<option value="Y" <s:if test="null!=order.invoiceInfoType">selected="selected"</s:if>>是</option>
    				<option value="N" <s:if test="null==order.invoiceInfoType">selected="selected"</s:if>>否</option>
			   	</select>
</div>
</div>

<div class="control-group"><label class="control-label"
	for="typeahead">发票抬头： </label>
<div class="controls"><input type="text" class="width200" name="infoMap['invoiceInfoTitle']" value="<s:property value='order.invoiceInfoTitle'/>"  data-provide="typeahead" data-items="4"></div>
</div>

 </fieldset>

<div class="btnnobgmini">
<a href="javascript:void(0);" class="btn btn-primary j_sure_editOrderInfo">保存</a>
<a href="javascript:void(0);" class="btn btn-primary j_sure_backOrderItemDetail">取消</a></div>
</fieldset>
</form>
</div>
</div>
</div>


</body>
</html>