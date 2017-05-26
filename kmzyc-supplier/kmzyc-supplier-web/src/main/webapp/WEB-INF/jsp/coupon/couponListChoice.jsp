<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>优惠券选择</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
	img{
		border: 1px solid #dbdbdb;
	}
	.isDefault{
		border-color: #379945;
		border-width: 2px;
	}
</style>
	<jsp:include page="/WEB-INF/jsp/common/template_skuimages.jsp">
		<jsp:param name="titlePrefix" value="促销优惠券选择"></jsp:param>
	</jsp:include>
</head>
<body>
	<aa:zone name="centerZone">
	<div class="container fn-p20 j_container"  data-js-src="${staticUrl}${jsBaseUrl}" data-images-src="${staticUrl}${imageBaseUrl}">
	<div class="fn-p20">
	<div class="ui-breadcrumb">
		<span>促销管理/优惠券选择/优惠券列表</span>
	</div>
	<div class="ui-well fn-mt20">
		<a class="ui-button ui-button-success ui-button-lg fn-mr20 j_add_coupon" href="#">
		<i class="ui-icon ui-icon-add16"></i>
		添加
		</a>
	</div>
<s:form action="gotoQueryCouponList.action" method="post" id="frm" name="frm">
<input type="hidden" value="<s:property value="spanId" />" id="spanId" name ="spanId"></input>
<s:hidden id="channel" name="coupon.channel"></s:hidden>
	<s:hidden name="page" id="page" />
<div class="ui-well ui-well-form fn-mt20">
<div class="ui-form ui-form-inline">
	<div class="ui-form-item"><label class="ui-form-label" for="">
	优惠券名称: </label> <s:textfield name="coupon.couponName" placeholder="请输入优惠券名称" 
		cssClass="ui-input"/></div>
	<div class="ui-form-item"><a href="javascript:void(0);"
		class="ui-button ui-button-success j_couponList_search"><i
		class="ui-icon ui-icon-search"></i>搜索</a></div>
</div>
</div>

<div class="fn-clear fn-mb10">
<!-- 分页组件 --> <tiles:insertDefinition
	name="pagination" />
</div>

<table class="ui-table table-bordered fn-mt10">
	<thead>
		<th class="col-w-15"><input type='checkbox' id='allbox'
			name='allbox'  class='j_list_allbox'></th>
		<th>优惠券名称</th>
		<th>发放类型</th>
		<th>优惠券抵扣金额</th>
		<th>优惠券状态</th>
		<th>优惠券最低消费金额</th>
	</thead>
	<tbody>

		<s:iterator value="pagintion.recordList" id="product">
			<tr>
				<td><input type="checkbox" name="productIdChk"
					value='<s:property value="couponId"/>' alt="<s:property value="status" />">
				</td>
				<td><s:property value="couponName" /></td>
				<td><s:property value="#request.couponGrantType[couponGivetypeId]" /></td>
				<td><s:property value="couponMoney" /></td>
				<td align="center"><s:property value="#request.couponStatus[status]" /></td>
					<td align="center"><s:property value="payLeastMoney" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="fn-tr fn-clear fn-mt10">
<!-- 分页组件 --> <tiles:insertDefinition
	name="paginationBottom" />
</div>
</s:form>
</div>
</div>
</aa:zone>
</body>
</html>


