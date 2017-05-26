<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="返回原件"></jsp:param>
</jsp:include>
<link type="text/css" rel="stylesheet" href="${staticUrl}${cssBaseUrl }bootstrap.css?version=${version}"/><!--缓存头部共用样式 -->
<link type="text/css" rel="stylesheet" href="${staticUrl}${cssBaseUrl }bootstrap.min.css?version=${version}"/><!--缓存头部共用样式 -->
<%--<link type="text/css" rel="stylesheet" href="${staticUrl}${cssBaseUrl }css.css?version=${version}"/><!--缓存头部共用样式 -->--%>
<link type="text/css" rel="stylesheet" href="${staticUrl}${cssBaseUrl }styles.css?version=${version}"/><!--缓存头部共用样式 -->
<script type="text/javascript" src="${staticUrl}/resgys/script/seajs/2.0.2/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticUrl}/resgys/script/config.js?version=${version}"></script>
<script type="text/javascript">
    seajs.use(['${staticUrl}/resgys/script/view/jsp/supplier/logisticsCompany.js']);
</script>
<title>返回原件</title>
</head>
<body>


<!-- 物流信息弹框以及确认配送弹框 -->
<s:if test="#request.logisticsCompanyMap != null">
<div class="container-fluid">
     <div class="row-fluid">
<div class="modal">
<div class="modal-body">
<form action="" method="post" id="orderItemFrm2" name="orderItemFrm2" namespace="supplier" >
</form>
<input type="hidden" id="alterCodeId"  value="<s:property value="alterCode"/>" />
<input type="hidden" value="<s:property value='ckType'/>" name="ckType"></input>
<fieldset>
<div class="divfrom">
<ul>
	<li><label>退换货单号：</label><s:property value='alterCode'/></li>
	<li><label><span class="required">*</span>物流公司： </label>
	<s:select list="#request.logisticsCompanyMap" id="logisticsCompanyId"
						headerKey="" headerValue="请选择物流" cssClass="logisticsCompany"/>
					<p class="j_logisticsCompany_error" style="display: none;">
					<i class="ui-icon ui-icon-error"></i><span class="logisticsCompany_error_msg" style="font-size:12px;" ></span>
					</p>
	</li>
	<li><label><span class="required">*</span>物流单号：</label>	
	<input class="logisticsNo" id="logisticsNoId"  style="width: 140px;" value="<s:property value='logisticsNo'/>" maxlength="16" />
					<p class="j_logisticsNo_error" style="display: none;">
						<i class="ui-icon ui-icon-error"></i><span class="logisticsNo_error_msg" style="font-size:12px;"></span>
					
					</p>
	</li>
	</ul>
</div>
</br>
</br>
<div class="modal-footer">
	<s:if test="ckType == 1">
		<a data-dismiss="modal"
			class="btn btn-primary  j_save_pesongLogisticsInfo"
			href="javascript:void(0);">保存</a>
	</s:if> <s:else>
		<a data-dismiss="modal" class="btn btn-primary  j_save_editLogisticsInfo"
			href="javascript:void(0);">保存</a>
	</s:else>
	
	<a data-dismiss="modal" class="btn btn-primary  j_sure_close"
			href="javascript:void(0);">关闭</a>
	
</fieldset>
</div>
</div>
</div>
</div>
</s:if>













<!--<s:if test="#request.logisticsCompanyMap != null">
	<div class="content">
		<div class="row-fluid">
		<input type="hidden" id="alterCodeId"  value="<s:property value="alterCode"/>" />
				<div class="block_01 shopheight">
				<div class="block-content collapse in"> tab内容 
				<ul class="nav nav-tabs">
					<li class="active"><a href="#home" data-toggle="tab">物流信息</a></li>
					</li>
				</ul>
				<input type="hidden" value="<s:property value='ckType'/>" name="ckType"></input>
				<form action="" method="post" id="orderItemFrm2" name="orderItemFrm2" namespace="supplier" >
				</form>
				<div class="control-group">
					<label class="control-label" for="typeahead">退换货单号： </label>
					<div class="controls"><s:property value='alterCode'/><p></p>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="typeahead"><span class="required">*</span>物流公司： </label>
					<div class="controls">
					<s:select list="#request.logisticsCompanyMap" id="logisticsCompanyId"
						headerKey="" headerValue="请选择物流" cssClass="logisticsCompany"/>
					<p class="j_logisticsCompany_error" style="display: none;">
					<i class="ui-icon ui-icon-error"></i><span class="logisticsCompany_error_msg" style="font-size:12px;" ></span>
					</p>
					</div>
				</div>
				
				
				<div class="control-group">
					<label class="control-label" for="typeahead">物流单号： </label>
					<div class="controls">			
					<input class="logisticsNo" id="logisticsNoId"  style="width: 140px;" value="<s:property value='logisticsNo'/>" maxlength="16" />
					<p class="j_logisticsNo_error" style="display: none;">
						<i class="ui-icon ui-icon-error"></i><span class="logisticsNo_error_msg" style="font-size:12px;"></span>
					
					<p></p>
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="typeahead">物流单号： </label>
					<div class="controls">			
					<input class="logisticsNo" id="logisticsNoId"  style="width: 140px;" value="<s:property value='logisticsNo'/>" maxlength="16" />
					<p class="j_logisticsNo_error" style="display: none;">
						<i class="ui-icon ui-icon-error"></i><span class="logisticsNo_error_msg" style="font-size:12px;"></span>
					
					<p></p>
					</div>
				</div>
				<s:property value="ckType"/>
				<div class="form-actions">
					<s:if test="ckType == 1">
						<a data-dismiss="modal" class="btn btn-primary  j_save_pesongLogisticsInfo" href="javascript:void(0);">保存</a>
					</s:if>
					<s:else>
						<a data-dismiss="modal" class="btn btn-primary  j_save_editLogisticsInfo" href="javascript:void(0);">保存</a>
					</s:else>	
				</div>
		</div>
	</div>
	</div>
	</div>
	
</s:if>
--><!-- 
<div id="question" style="position:absolute;width:430px;height:600px;z-index:1000;display:none"></div>
 -->
<div id="question" style="display:none"></div>
</body>
</html>