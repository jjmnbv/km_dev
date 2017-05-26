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
</style>
	<jsp:include page="/WEB-INF/jsp/common/template_skuimages.jsp">
		<jsp:param name="titlePrefix" value="促销商品选择"></jsp:param>
	</jsp:include>
</head>
<body>
<aa:zone name="centerZone">
<div class="fn-p20">
	<div class="ui-breadcrumb">
		<span>活动管理/活动管理列表</span>
	</div>
	
	<div class="ui-well fn-mt20">
	<s:if test="type!='check'"><a class="ui-button ui-button-success ui-button-lg fn-mr20 j_add_product" id="addConflictPromotion" href="#">
		<i class="ui-icon ui-icon-add16"></i>
		添加
		</a>
		</s:if>	
		<a class="ui-button ui-button-success ui-button-lg fn-mr20 j_delete_ConflictpromotionList" href="#">
		<i class="ui-icon ui-icon-add16 sj-icon-ad"></i>
		取消
		</a>
	</div>
<s:form action="selectConflictPromotion.action" method="post" id="frm" name="frm">
<input type="hidden" value="<s:property value='selectedIds'/>" id="selectedIds" name ="selectedIds"></input>
<input type="hidden" name="promotion.startTime" value="<s:date name='promotion.startTime'  format='yyyy-MM-dd HH:mm:ss'/>" id="startTime"/>
<input type="hidden" name="promotion.endTime" value="<s:date name='promotion.endTime'  format='yyyy-MM-dd HH:mm:ss'/>" id="endTime"/>
<div class="ui-well ui-well-form">
<div class="ui-form ui-form-inline fn-minus-mr20">

	<s:hidden name="page" id="page" />
	<div class="ui-form-item"><label class="ui-form-label" for="">
	活动名称： </label> <s:textfield name="promotion.promotionName" placeholder="" 
		cssClass="ui-input"/></div>
	<div class="ui-form-item"><label class="ui-form-label" for="">
	审核状态： </label><s:select list="#{0:'全部',1:'未审核',2:'已审核'}" listKey="key" listValue="value"
					  name="promotion.status"></s:select></div>
	
	<div class="ui-form-item"><label class="ui-form-label" for="">
	活动类型： </label><s:select list="%{#request.promotionTypeMap}" headerKey="" headerValue="全部" listKey="key" listValue="value"
					  name="promotion.promotionType"></s:select></div>
	<div class="ui-form-item"><a href="javascript:void(0);"
		class="ui-button ui-button-success j_conflictPromotionList_search"><i
		class="ui-icon ui-icon-search"></i>搜索</a></div>

</div>

</div>


<table class="ui-table table-bordered fn-mt10">
	<thead>
		<th class="col-w-15"><input type='checkbox' id='allbox'
			name='allbox'  class='j_list_allbox'></th>
	
		<th class="col-w-40">名称</th>
		<th class="col-w-20">类型</th>
	
		<th class="col-w-30">开始时间</th>
		<th class="col-w-30">结束时间</th>
		
		
		<th class="col-w-25">优先级</th>
		
	</thead>
	<tbody>

		<s:iterator value="pagee.dataList" id="promotion">
			<tr>
				<td>
					<input type="checkbox" name="promotionId" value='<s:property value="promotionId"/>' title='<s:property value="promotionName"/>' >
					<input type="hidden" id="st<s:property value="promotionId"/>" value="<s:property value="status"/>" />
					<input type="hidden" id="status<s:property value='promotionId'/>" value="<s:property value='status'/>"/></td>
				</td>
				<td><s:property value="promotionName" /></td>
				<td  title="<s:property value='%{#request.promotionTypeMap[promotionTypeId]'/>"><s:property value="#request.promotionTypeMap[promotionType]" /></td>
				
				<td><s:date name="startTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td><s:date name="endTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				
				
				<!--<td><s:property value="channel" /></td>-->
				<td align="center">
					<input title='数字越大，优先级越高，按Esc退出修改'
					style="display:none" size="2" 
					value="<s:property value='promotionPriority' />" id="promotionPriority" name="promotionPriority" />
					<span id="noEditpricrity" style="display:inline"><s:property value="promotionPriority" /></span>&nbsp;
					
					</td>
				
			</tr>
		</s:iterator>
	</tbody>
</table>

</s:form>
</div>
</aa:zone>

</body>
</html>