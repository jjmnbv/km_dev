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
		<jsp:param name="titlePrefix" value="商品类目选择"></jsp:param>
	</jsp:include>
</head>
<body>
		<aa:zone name="centerZone">
	<div class="container fn-p20 j_container"  data-js-src="${staticUrl}${jsBaseUrl}" data-images-src="${staticUrl}${imageBaseUrl}">
	<div class="fn-p20">
	<div class="ui-breadcrumb">
		<span>促销管理/活动更新/类目列表</span>
	</div>
	<input type="hidden" id="treeData" value="<s:property value="categoryString"/>"/>
	<div class="ui-well fn-mt20">
		<a class="ui-button ui-button-success ui-button-lg fn-mr20" href="#" id ="addCategory">
		<i class="ui-icon ui-icon-add16"></i>
		保存所选
		</a>
	</div>
	


<input type="hidden" value="<s:property value='selectedIds'/>" id="selectedIds" name ="selectedIds"></input>


<table class="ui-table table-bordered fn-mt10">
							<tr style="background:#FFFFCC">
						    <td align="left" width="30%"  valign="top">
						    	<p style="text-align:left"><input type="button"  class="ui-button ui-button-success j_expandAll" href="#"  value="展开所有"/> &nbsp;|&nbsp; <input type="button"  class="ui-button ui-button-success j_unExpandAll" value="关闭所有"/></p>
						    	<div class="dtree">			
									<ul id="tree" class="ztree"></ul>
								</div>
						    </td>
						</tr>
</table>



</div>
</div>
</aa:zone>
</body>
</html>


