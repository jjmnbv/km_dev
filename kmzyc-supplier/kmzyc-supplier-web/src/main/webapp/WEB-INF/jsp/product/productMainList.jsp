<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="出售中的商品"></jsp:param>
</jsp:include>
<title>出售中的商品</title>

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
		<li>商品 <span class="divider">/</span></li>
		<s:if test="auditStatus == 3">
			<li>出售中的商品</li>
		</s:if>
        <s:elseif test="auditStatus == -2">
			<li>违规下架</li>
        </s:elseif>
		<s:else>
			<li>已下架</li>
		</s:else>
	</ul>
</div>


<div class="block-content collapse in"><!--开始-->

<s:if test="auditStatus == 3">
<ul class="nav nav-tabs">
	<li class="active"><a href="#home" data-toggle="tab">出售中的商品</a></li>
	<button class="btn btn-danger btnright" id="btnAddProduct"><i
		class="icon-th-large icon-white"></i>添加新品</button>
</ul>
</s:if>
<s:if test="auditStatus == 4 || auditStatus == -2">
<ul class="nav nav-tabs">
	<li <s:if test="auditStatus == null || auditStatus == 2">class="active"</s:if> data-id="2"><a href="/productDraft/showProductDraftList.action?auditStatus=2">已审核待上架</a></li>
	<li <s:if test="auditStatus == 6">class="active"</s:if> data-id="6"><a href="/productDraft/showProductDraftList.action?auditStatus=6">审核未通过</a></li>
	<li <s:if test="auditStatus == 1">class="active"</s:if> data-id="1"><a href="/productDraft/showProductDraftList.action?auditStatus=1">审核中</a></li>
	<li <s:if test="auditStatus == 0">class="active"</s:if> data-id="0"><a href="/productDraft/showProductDraftList.action?auditStatus=0">未审核</a></li>
	<li <s:if test="auditStatus == 4">class="active"</s:if> data-id="4"><a href="/product/showList.action?auditStatus=4">已下架</a></li>
    <li <s:if test="auditStatus == -2">class="active"</s:if> data-id="-2"><a href="/product/showList.action?auditStatus=-2">违规下架</a></li>
	<button class="btn btn-danger btnright" id="btnAddProduct"><i class="icon-th-large icon-white"></i>添加新品</button>
</ul>
</s:if>
<input type="hidden" value="<s:property value='suppliersInfo.businessStatus'/>" id="businessStatusId">
<input type="hidden" value="<s:property value='suppliersInfo.closeStatus'/>" id="closeStatusId">
<input type="hidden" value="<s:property value="rtnMessage" />" id="rtnMessage" />



<!--搜索开始-->
<s:form action="/product/showList.action" method="post" id="frm" name="frm">
	<input type="hidden" value="<s:property value="auditStatus" />" name="auditStatus" id="auditStatus" />
	<s:hidden name="clickUrl" id="clickUrl"></s:hidden>
    <s:hidden name="page" id="page" />
	<div class="com_topform">
	<ul>
		<li>
		<select id="selectWord">
			<option <s:if test='product.productNo != null'>selected="selected"</s:if> value="product.productNo">商品编码</option>
			<option <s:if test='product.productTitle != null'>selected="selected"</s:if> value="product.productTitle">商品主标题</option>
		</select>
		</li>
		<li>
			<s:if test="product.productNo != null || product.productTitle == null ">
				<input id="selectWordText" name="product.productNo" type="text" value="<s:property value='product.productNo' />">
			</s:if>
			<s:else>
				<input id="selectWordText" name="product.productTitle" type="text" value="<s:property value='product.productTitle' />">
			</s:else>
		</li>
		
		<s:if test="#request.shopCategoryList!=null &&#request.shopCategoryList.size>0">
			<li><label>本店分类：</label>
			<select id="selectError" name="shopcategoryIdForQuery">
			<%--<optgroup label="所有" class="selA">&nbsp;&nbsp;&nbsp;所有</optgroup>--%>
			<option value="">所有</option>
			<s:iterator value="#request.shopCategoryList">
				<optgroup label="<s:property value="categoryName"/>" class="selA"><s:property value="categoryName"/></optgroup>
				<s:iterator	value="shopCategoryChildrenList">
					<s:if test="shopcategoryIdForQuery==null">
						<option value="<s:property value="shopCategoryId"/>">　<s:property value="categoryName"/></option>
					</s:if>
					<s:else>
						<option value="<s:property value="shopCategoryId"/>" <s:if test="shopcategoryIdForQuery==shopCategoryId">selected='true'</s:if>>　<s:property value="categoryName"/></option>
					</s:else>
					
				</s:iterator>
			</s:iterator>
			</select>
			</li>
		</s:if>
		
		
		<li><label>品牌：</label><input class="width200" type="text" id="autocomplete" size="32"
				name="product.searchBrandName" value="<s:property value="product.searchBrandName" />" /></li>
		<li>
		<button class="btn btn-primary j_productMainList_search"><i
			class="icon-search icon-white"></i> 搜索</button>
		</li>
	</ul>
	</div>
    <div class="com_tablesbnt">
        <ul>
            <li>
                <label class="cboxtop">
                    <input class="uniform_on" type="checkbox" id="uniform_on" name="uniform_on">
                     全选<%--<label for="uniform_on" class="checkboxLabel"></label>--%>
                </label>
            </li>
                <%--<li>
                <button class="btn j_list_allbox"><i class="icon-ok"></i> 全选</button>
                </li>--%>
            <li>
                <s:if test="auditStatus == 3">
                    <button class="btn j_downShelf_productMainList"><i class="icon-arrow-down"></i> 下架</button>
                </s:if>
                <s:elseif test="auditStatus == 4">
                    <button class="btn j_upShelf_productMainList"><i class="icon-arrow-up"></i> 上架</button>
                </s:elseif>
                <s:elseif test="auditStatus == -2">
                    <%--<button class="btn j_delete_productMainList"><i class="icon-remove"></i> 删除</button>--%>
                </s:elseif>
            </li>
           <%-- <li>
                <button class="btn j_delete_productMainList"><i class="icon-remove"></i> 删除</button>
            </li>--%>
        </ul>
    </div>
	<!--搜索结束-->
	<table cellpadding="0" cellspacing="0" border="0"
		class="table com_tablest">
		<thead></thead>
		<tbody>
			<tr>
				<td>商品</td>
				<td class="width100">单价(元)</td>
				<td class="width100">库存</td>
				<td class="width100">品牌</td>
				<s:if test="auditStatus == 3">
					<td class="width100">上架时间</td>
				</s:if>
				<s:else>
					<td class="width100">下架时间</td>
				</s:else>
				<td class="width100">商品状态</td>
				<td class="width100">操作</td>
			</tr>
		</tbody>
	</table>
	<s:iterator value="pagintion.recordList" id="product">
		<table cellpadding="0" cellspacing="0" border="0"
			class="table  table-bordered">
			<thead>
				<tr class="tablesbg">
					<th colspan="5" class="textc"><input type="checkbox" name="productIdChk"
						value='<s:property value="id"/>_<s:property value="status"/>_<s:property value="channel"/>' class="checkt">
					商品编码：<s:property value="productNo" /></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
                        <table width="100%" border="0" class="newform">
                            <tbody>
                            <tr>
                                <td width="120px">
                                    <a class="pull-left j_viewDetailPath_product" target="_blank" data-productid="<s:property value='id'/>" href="javascript:;">
                                        <img class="thumbnail" src='<s:property value="imagePath"/><s:property value="productSkus[0].viewImgPath"/>'></a>
                                </td>
                                <td class="textc">
                                    <a href="javascript:;" target="_blank" data-productid="<s:property value='id'/>" class="j_viewDetailPath_product"
                                       title="<s:property value="productTitle" />"><s:property value="productTitle" /></a>
                                    <br>
                                    <a class="label" data-toggle="collapse" data-parent="#accordion"
                                       href="#<s:property value="productNo" />">
                                        <i class="icon-xia icon-white"></i>SKU规格
                                    </a>
                                </td>
                                <td width="100px"><s:property value="productSkus[0].price"/> 元</td>
                                <td width="100px"><s:property value="productSkus[0].stockQuantity"/></td>
                            </tr>
                            </tbody>
                        </table>

					<%--<div class="media textc"><a class="pull-left j_viewDetailPath_product" target="_blank" data-productid="<s:property value='id'/>" href="javascript:;"><img
						class="thumbnail" src='<s:property value="imagePath"/><s:property value="productSkus[0].viewImgPath"/>'></a>
					<div class="com_title">
					<ul>
						<li><span><a href="javascript:;" target="_blank" data-productid="<s:property value='id'/>" class="j_viewDetailPath_product"
							title="<s:property value="productTitle" />"><s:property value="productTitle" /></a><br>
						<a class="label" data-toggle="collapse" data-parent="#accordion"
							href="#<s:property value="productNo" />"><i class="icon-xia icon-white"></i>SKU规格</a></span>
                            <p><s:property value="productSkus[0].stockQuantity"/></p><p><s:property value="productSkus[0].price"/> 元</p>
						</li>
					</ul>
					</div>
					</div>--%>
					</td>
					<td class="width100"><s:property value="prodBrand.brandName" /></td>
					<td class="width100">
						<s:if test="auditStatus == 3">
							<s:date name="upTime" format="yyyy-MM-dd HH:mm:ss" />
						</s:if>
						<s:else>
							<s:date name="archiveTime" format="yyyy-MM-dd HH:mm:ss" />
						</s:else>
					</td>
					<td class="width100"><s:property value="#request.productStatusMap[status]" /><br>
					<a href="javascript:;" target="_blank" class="j_viewDetailPath_product" data-productid="<s:property value='id'/>" >预览商品详情</a></td>
					<td class="width100">
                        <s:if test="auditStatus == -2">
                            <button class="btn btn-mini width66 btn-success j_reasons_product" data-productid="<s:property value='id'/>" >查看原因</button>
                            <input type="hidden" value="<s:property value="reasons" />" id="reasons_<s:property value='id'/>" />
                            <%--<br>
                            <button class="btn btn-mini width66 j_delete_product" data-productid="<s:property value='id'/>" >删除</button>--%>
                        </s:if>
                        <s:else>
                            <button class="btn btn-mini width66 btn-success j_view_product" data-productid="<s:property value='id'/>" >查看</button>
                            <br>
                            <button class="btn btn-mini width66 j_update_product" data-productid="<s:property value='id'/>" >编辑</button>
                            <br>
                            <button class="btn btn-mini width66 j_updateImage_product" data-productid="<s:property value='id'/>" >管理图片</button>
                            <br>
                            <s:if test="auditStatus == 3">
                                <button class="btn btn-mini width66 j_downShelf_product" data-productid="<s:property value='id'/>">下架</button>
                            </s:if>
                            <s:if test="auditStatus == 4">
                                <button class="btn btn-mini width66 j_upShelf_product" data-productid="<s:property value='id'/>">上架</button>
                                <%--<br>
                                <button class="btn btn-mini width66 j_delete_product" data-productid="<s:property value='id'/>" >删除</button>--%>
                            </s:if>
                        </s:else>
					</td>
				</tr>
			</tbody>
		</table>
		<div id="<s:property value="productNo" />" class="collapse">
		<div class="sukinfo">
		<ul>
			<s:iterator value="productSkus">
				<li>
                <img class="thumbnail" src=<s:if test="viewImgPath!=null">'<s:property value="imagePath"/><s:property value="viewImgPath"/>'</s:if><s:else>'<s:property value="cssJsPath"/>images/noimg.gif'</s:else>>
				<p>SKU编码:<f1><s:property value="productSkuCode"/></f1></p>
				<p>市场价:<f2>¥<s:property value="markPrice"/></f2></p>
				<p>销售单价:<f2>¥<s:property value="price"/></f2></p>
				<p>重量:<f2><s:property value="unitWeight"/></f2></p>
				<p>库存:<f3><s:property value="stockQuantity"/></f3></p>
				<p>商家货号:<f1><s:property value="sellerSkuCode"/></f1></p>
				</li>
			</s:iterator>
		</ul>
		</div>
		</div>
	</s:iterator> <!--结束--></div>
	<div class="fn-clear fn-mt10">
	<!-- 分页组件 --> <tiles:insertDefinition name="paginationBottom" />
	</div>
</s:form>
</div>
<s:form action="/ajaxJson/upShelf.action" method="POST" id="upForm" name='upForm'></s:form>
<s:form action="/ajaxJson/downShelf.action" method="POST" id="downForm" name='downForm'></s:form>
<s:form action="" method="post" id="addFrm" name="addFrm"></s:form>
<s:form action="" method="post" id="uploadImageFrm" name="uploadImageFrm"></s:form>
<s:form action="" method="post" id="deleteFrm" name="deleteFrm"></s:form>
</div>
</div>
</div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>