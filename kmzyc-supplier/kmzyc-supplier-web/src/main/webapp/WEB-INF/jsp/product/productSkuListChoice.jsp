<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>商品选择</title>
	<style type="text/css">
		img{
			border: 1px solid #dbdbdb;
		}
		.isDefault{
			border-color: #379945;
			border-width: 2px;
		}
		body {
			overflow-y: scroll;background-color:#fff!important;
		}
	</style>
	<jsp:include page="/WEB-INF/jsp/common/template.jsp">
		<jsp:param name="titlePrefix" value="促销商品选择"></jsp:param>
	</jsp:include>
</head>
<body>

<div class="block-content collapse in">	
	<div class="ui-well fn-mt20">
		<a class="btn btn-success j_select_product" href="#">保存</a>
	</div>
	
	<s:form action="findAllProductMainSku.action" method="post" id="frm" name="frm">
		<input type="hidden" value="<s:property value="spanId" />" id="spanId" name ="spanId"/>
		<input type="hidden" value="<s:property value="promotionId" />" id="promotionId" name ="promotionId"/>
		<input type="hidden" value="<s:property value="promotionType" />" id="promotionType" name ="promotionType"/>
		<input type="hidden" value="<s:property value="divIndex" />" id="divIndex" name ="divIndex"/>
		<s:hidden name="page" id="page" />
		<!-- 查询条件 s-->
		<div class="com_topform">
			<ul>
				<li><span>商品编码：</span></li>
				<li>
					 <s:textfield name="viewProductSku.productNo" placeholder="" cssClass="ui-input"/>
				</li>
				<li><span >商品名称： </span>
				</li>
				<li>
					<s:textfield name="viewProductSku.procuctName"  placeholder="" cssClass="ui-input"/>
				</li>
				<li>
					<span >SKU编码：</span>
				</li>
				<li>
					<s:textfield name="viewProductSku.productSkuCode"  placeholder="" cssClass="ui-input"/>
				</li>
				<li>
					<a href="javascript:void(0);" class="btn btn-primary j_productMainSkuList_search"><i class="icon-search icon-white"></i>搜索</a>
				</li>
			</ul>
		</div>
		<!-- 查询条件 e-->

		<table cellpadding="0" cellspacing="0" border="0" class="table com_tablest">
			<tbody>
				<tr>
					<td class="width50">
						<input type="checkbox" name="uniform_on" id="uniform_on" class="uniform_on"/>
					</td>
					<td class="width150">商品名称</td>
					<td class="width100">商品编码</td>
					<td class="width100">SKU编码</td>
					<td class="width210">SKU信息</td>
					<td class="width50">状态</td>
				</tr>
			</tbody>
		</table>
		<table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
			<s:iterator value="pagintion.recordList" id="product">
			<tbody>
			<tr>
				<td class="width50"><input type="checkbox" name="productIdChk" value='<s:property value="productSkuId"/>'>
				</td>
				<td class="width150"><s:property value="procuctName" /></td>
				<td class="width100"><s:property value="productNo" /></td>
				<td class="width100"><s:property value="productSkuCode" /></td>
				<td class="width210">
					<s:if test="viewSkuAttrs!=null || viewSkuAttrs.size!=0">
						<s:iterator value="viewSkuAttrs" var="v">
							<s:property value="#v.categoryAttrName" />：<s:property value="#v.categoryAttrValue" />&nbsp;&nbsp;
						</s:iterator>
					</s:if>
				</td>
				<td class="width50"><s:property value="#request.productStatusMap[status]" /></td>
			</tr>
			</tbody>
			</s:iterator>
		</table>
		<div class="fn-clear fn-mt10">
			<tiles:insertDefinition name="paginationBottom" />
		</div>
	</s:form>

	<s:form action="addPromotionProduct" method="POST" namespace='/promotion' id="addPromotionProduct" name="addPromotionProduct">
	</s:form>
</div>
</body>
</html>