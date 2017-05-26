<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
<meta name="renderer" content="webkit|ie-comp|ie-stand"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>商品选择</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
	img{
		border: 1px solid #dbdbdb;
	}
	.isDefault{
		border-color: #379945;
		border-width: 2px;
	}
	body {
overflow-y: scroll;
}
</style>
	<jsp:include page="/WEB-INF/jsp/common/template_skuimages.jsp">
		<jsp:param name="titlePrefix" value="商品品牌选择"></jsp:param>
	</jsp:include>
</head>
<body>
		<aa:zone name="centerZone">
	<div class="container fn-p20 j_container"  data-js-src="${staticUrl}${jsBaseUrl}" data-images-src="${staticUrl}${imageBaseUrl}">
	<div class="fn-p20">
	<div class="ui-breadcrumb">
		<span>促销管理/活动更新/商品品牌列表</span>
	</div>
	
	<div class="ui-well fn-mt20">
		<a class="ui-button ui-button-success ui-button-lg fn-mr20" href="#" id ="addProd">
		<i class="ui-icon ui-icon-add16"></i>
		保存所选
		</a>
	</div>
	
<s:form action="selectProductBrand.action" method="post" id="frm" name="frm">

<input type="hidden" value="<s:property value='selectedIds'/>" id="selectedIds" name ="selectedIds"></input>

	<s:hidden name="page" id="page" />
<div class="ui-well ui-well-form fn-mt20">
<div class="ui-form ui-form-inline">
	<div class="ui-form-item"><label class="ui-form-label" for="">
名称： </label> <s:textfield name="prodBrand.brandName" placeholder="" 
		cssClass="ui-input"/></div>
		<div class="ui-form-item"><label class="ui-form-label" for="">
中文名称： </label> <s:textfield name="prodBrand.chnSpell" placeholder="" 
		cssClass="ui-input"/></div>
		<div class="ui-form-item"><label class="ui-form-label" for="">
英文名称： </label> <s:textfield name="prodBrand.engName" placeholder="" 
		cssClass="ui-input"/></div>
		<div class="ui-form-item"><a href="javascript:void(0);"
		class="ui-button ui-button-success j_prodList_search"><i
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
		<th >名称</th>
		<th >国籍</th>
		<th >英文名称</th>
		<th >中文拼音</th>
		
	</thead>
	<tbody>
	<s:iterator id="custiterator" value="pagee.dataList">
	<tr>
	    <td >
		    <input type="checkbox" name="selectedId" id='selectedId<s:property value="brandId"/>' title="<s:property value='brandName'/>" 
		     value='<s:property value="brandId"/>'>
		</td>
		<td align="center"><s:property value="brandName"/></td>
		<td align="center"><s:property value="nation"/></td>
		<td align="center">
			<s:property value="engName"/>
		</td>
		<td align="center">
			<s:property value="chnSpell"/>
		</td>
	</tr>
	</s:iterator>
	</tbody>
</table>
<div class="fn-clear fn-mt10">
<!-- 分页组件 --> <tiles:insertDefinition
	name="paginationBottom" />
</div>
</s:form>


</div>
</div>
</aa:zone>
</body>
</html>


