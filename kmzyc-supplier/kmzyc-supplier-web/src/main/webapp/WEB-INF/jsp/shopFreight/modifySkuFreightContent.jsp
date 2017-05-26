<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="运费管理-单品运费设置"></jsp:param>
</jsp:include>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
<meta name="renderer" content="webkit|ie-comp|ie-stand"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>运费管理-单品运费设置</title>
</head>
<body>
<div class="block-content collapse in">
	<form class="form-horizontal"><legend>设置运费</legend>
		<fieldset>
			<input id="productSkuId" type="hidden" value="<s:property value="viewProductSku.productSkuId" />" />
			<s:hidden id="productId" name="viewProductSku.productId"></s:hidden>
			<div class="control-group">
				<label class="control-label" ><span class="required">*</span>是否免邮： </label>
				<div class="controls line-no">
					<s:radio  list="#request.freeStatusMap" name="viewProductSku.freeStatus" ></s:radio>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><span class="required">*</span>修改运费： </label>
				<div class="controls">
					<s:textfield cssClass="ui-input freightValue" maxlength="7" id="freight" name="viewProductSku.freight"></s:textfield>
				</div>
			</div>
			<div class="btnnobgmini">
				<a class="btn btn-success saveSkuFreight">确认</a>
				<a class="btn backList">取消</a>
			</div>
		</fieldset>
	</form>
</div>
</body>
</html>