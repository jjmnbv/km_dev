<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
	<meta name="renderer" content="webkit|ie-comp|ie-stand"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="品牌管理"></jsp:param>
</jsp:include>
<title>待售中的商品</title>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>

<div class="container-fluid">
<div class="row-fluid">
<jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_product.jsp"></jsp:include>
<div class="content">
<div class="row-fluid"><!-- block -->
<div class="block_01">
<div class="navbar-inner">
<ul class="breadcrumb">
	<i class="icon-home"></i>
	<li>商品<span class="divider">/</span></li>
	<li>品牌管理<span class="divider">/</span></li>
	<s:if test="status == 3">
		<li>审核通过</li>
	</s:if>
	<s:elseif test="status == 1">
		<li>审核中</li>
	</s:elseif>
	<s:elseif test="status == 2">
		<li>审核未通过</li>
	</s:elseif>
</ul>
</div>
<div class="block-content collapse in"><!--开始-->
<input type="hidden" value="<s:property value="rtnMessage" />" id="rtnMessage" />
<ul class="nav nav-tabs">
	<li <s:if test="status == 3">class="active"</s:if> data-id="3">
        <a href="/prodBrandDraft/showProdBrandDraftList.action?status=3">审核通过</a></li>
	<li <s:if test="status == 1">class="active"</s:if> data-id="1">
        <a href="/prodBrandDraft/showProdBrandDraftList.action?status=1">审核中</a></li>
	<li <s:if test="status == 2">class="active"</s:if> data-id="2">
        <a href="/prodBrandDraft/showProdBrandDraftList.action?status=2">审核未通过</a></li>
    <a href="/prodBrandDraft/toProdBrandDraftInfo.action?status=${status}"
       class="btn btn-danger btnright j_add_prod_brand_draft">
        <i class="icon-tags icon-white"></i> 申请新品牌</a>
</ul>

<s:form action="/prodBrandDraft/showProdBrandDraftList.action" method="post" id="frm" name="frm">
	<s:hidden name="status" id="status"></s:hidden>
    <s:hidden name="page" id="page" />
    <table cellpadding="0" cellspacing="0" border="0" class="table com_tablest" >
        <thead></thead>
        <tbody>
            <tr>
                <td>品牌名称</td>
                <td width="12%">国籍</td>
                <td width="12%">LOGO图片</td>
                <td width="150px">英文名称</td>
                <td width="200px">中文拼音</td>
                <td width="10%">状态</td>
                <td width="12%">操作</td>
            </tr>
        </tbody>
    </table>

	<s:iterator value="pagintion.recordList" id="prod_brand_draft">
		<table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
			<thead>
			</thead>
			<tbody>
				<tr>
					<td><s:property value="brandName"/></td>
					<td width="12%"><s:property value="nation" /></td>
					<td width="12%">
                        <img alt="已上传品牌图片" width="118" height="46"
                             src='<s:property value="imagePath"/><s:property value="logoPath"/>'/>
                    </td>
					<td width="150px" class="tdword"><s:property value="engName" /></td>
					<td width="200px" class="tdword"><s:property value="chnSpell" /></td>
					<td width="10%">
                        <s:if test="status == 1">
                            审核中
                        </s:if>
                        <s:elseif test="status == 2">
                            审核未通过
                        </s:elseif>
                        <s:elseif test="status == 3">
                            审核通过
                        </s:elseif>
					</td>
					<td width="12%">
						<s:if test="status == 3 || status == 1"><%--审核通过与审核中的只有查看--%>
                            <button class="btn btn-mini width66 btn-success j_view_prod_brand_draft"
                                    data-id="<s:property value="brandId"/>">查看详情</button>
						</s:if>
						<s:if test="status == 2"><%--审核未通过--%>
                            <button class="btn btn-mini width66 btn-success j_reason_prod_brand_draft"
                                    data-id="<s:property value="brandId"/>">查看原因</button>
                            <input type="hidden" value="<s:property value="reasons" />"
                                   id="reasons_<s:property value='brandId'/>" />
                            <br>
                            <button class="btn btn-mini width66 j_update_prod_brand_draft"
                                    data-id="<s:property value="brandId" />">编辑</button>
							<br>
                            <button class="btn btn-mini width66 j_delete_prod_brand_draft"
                                    data-id="<s:property value="brandId"/>">删除</button>
						</s:if>
					</td>
				</tr>
			</tbody>
		</table>
	</s:iterator> <%--结束--%>
	<div class="fn-clear fn-mt10">
	<!-- 分页组件 --> <tiles:insertDefinition name="paginationBottom" />
	</div>
</s:form>
</div>
<s:form action="" method="post" id="addFrm" name="addFrm"></s:form>
<s:form action="" method="post" id="updatePicFrm" name="updatePicFrm"></s:form>
<s:form action="" method="post" id="deleteBatchFrm" name="deleteBatchFrm"></s:form>
</div>
</div>
</div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>