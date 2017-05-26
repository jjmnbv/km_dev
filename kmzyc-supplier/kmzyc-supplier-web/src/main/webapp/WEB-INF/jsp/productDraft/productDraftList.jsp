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
	<jsp:param name="titlePrefix" value="待售中的商品"></jsp:param>
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
	<li>商品 <span class="divider">/</span></li>
	<s:if test="auditStatus == null || auditStatus == 2">
		<li>已审核待上架</li>
	</s:if>
	<s:if test="auditStatus == 6">
		<li>审核未通过</li>
	</s:if>
	<s:if test="auditStatus == 1">
		<li>审核中</li>
	</s:if>
	<s:if test="auditStatus == 0">
		<li>未审核</li>
	</s:if>
</ul>
</div>
<div class="block-content collapse in"><!--开始-->
<input type="hidden" value="<s:property value="rtnMessage" />" id="rtnMessage" />
<ul class="nav nav-tabs">
	<li <s:if test="auditStatus == null || auditStatus == 2">class="active"</s:if> data-id="2"><a href="/productDraft/showProductDraftList.action?auditStatus=2">已审核待上架</a></li>
	<li <s:if test="auditStatus == 6">class="active"</s:if> data-id="6"><a href="/productDraft/showProductDraftList.action?auditStatus=6">审核未通过</a></li>
	<li <s:if test="auditStatus == 1">class="active"</s:if> data-id="1"><a href="/productDraft/showProductDraftList.action?auditStatus=1">审核中</a></li>
	<li <s:if test="auditStatus == 0">class="active"</s:if> data-id="0"><a href="/productDraft/showProductDraftList.action?auditStatus=0">未审核</a></li>
	<li <s:if test="auditStatus == 4">class="active"</s:if> data-id="4"><a href="/product/showList.action?auditStatus=4">已下架</a></li>
	<li <s:if test="auditStatus == -2">class="active"</s:if> data-id="-2"><a href="/product/showList.action?auditStatus=-2">违规下架</a></li>
	<button class="btn btn-danger btnright" id="btnAddProduct"><i class="icon-th-large icon-white"></i>添加新品</button>
</ul>
<!--搜索开始-->
<s:form action="/productDraft/showProductDraftList.action" method="post" id="frm" name="frm">
	<s:hidden name="auditStatus" id="auditStatus" ></s:hidden>
	<s:hidden name="clickUrl" id="clickUrl"></s:hidden>
    <s:hidden name="page" id="page" />
	<div class="com_topform">
	<ul>
        <s:if test="auditStatus == 4"><%--未审核、审核中、审核未通过、已审核待上架中没有商品编码搜索，去掉select控件--%>
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
        </s:if>
        <s:else>
            <li><label>商品主标题：</label>
                <input name="product.productTitle" type="text" value="<s:property value='product.productTitle' />">
            </li>
        </s:else>
		<s:if test="#request.shopCategoryList!=null &&#request.shopCategoryList.size>0">
			<li><label>本店分类：</label>
			<select id="selectError" name="shopcategoryIdForQuery">
			<%--<optgroup label="所有" class="selA">&nbsp;&nbsp;&nbsp;所有</optgroup>--%>
			<option value="">所有</option>
			<s:iterator value="#request.shopCategoryList">
				<optgroup label="<s:property value="categoryName"/>" class="selA"><s:property value="categoryName"/></optgroup>
				<s:iterator	value="shopCategoryChildrenList">
					<s:if test="shopcategoryIdForQuery==null">
						<option value="<s:property value="shopCategoryId"/>" >　<s:property value="categoryName"/></option>
					</s:if>
					<s:else>
						<option value="<s:property value="shopCategoryId"/>" <s:if test="shopcategoryIdForQuery==shopCategoryId">selected='true'</s:if>>　<s:property value="categoryName"/></option>
					</s:else>
					
				</s:iterator>
			</s:iterator>
			</select>
			</li>
		</s:if>
		
		<li><label>品牌：</label><input type="text" class="width200" id="autocomplete" size="32"
				name="product.searchBrandName" value="<s:property value="product.searchBrandName" />" /></li>
		<li>
		<button class="btn btn-primary j_productDraftList_search"><i
			class="icon-search icon-white"></i> 搜索</button>
		</li>
	</ul>
	</div>
	<!--搜索结束-->

    <div class="com_tablesbnt">
        <ul>
            <%--<li>
                <button class="btn j_list_allbox"><i class="icon-ok"></i> 全选</button>
            </li>--%>
            <li>
                <label class="cboxtop">
                    <input class="uniform_on" type="checkbox" id="uniform_on" name="uniform_on">
                    全选
                </label>
            </li>
            <s:if test="auditStatus == 2">
                <li>
                    <button class="btn" id="upShelf"><i class="icon-arrow-up"></i> 上架</button>
                </li>
            </s:if>
            <li>
                <button class="btn btnAllRemove"><i class=" icon-remove"></i> 删除</button>
            </li>
        </ul>
    </div>

	<table cellpadding="0" cellspacing="0" border="0"
		class="table com_tablest">
		<thead></thead>
		<tbody>
			<tr>
				<td>商品</td>
				<td class="width100">单价(元)</td>
				<td class="width100">库存</td>
				<td class="width100">品牌</td>
				<td class="width100">创建时间</td>
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
					<th colspan="5" class="textc">
						<input type="checkbox" name="productIds" value='<s:property value="productId"/>' class="checkt">
						<input type="hidden" id="st<s:property value="productId"/>" value="<s:property value="status"/>" />
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
                        <table width="100%" border="0" class="newform">
                            <tbody>
                            <tr>
                                <td width="120px">
                                    <a href="javascript:;" class="pull-left j_preview_productDraft" target="_blank"
                                       data-productid="<s:property value='productId'/>" >
                                        <img class="thumbnail" src=<s:if test="#product.productSkuDrafts[0].viewImgPath!=null">'
                                        <s:property value="imagePath"/><s:property value="#product.productSkuDrafts[0].viewImgPath"/>'</s:if><s:else>'<s:property value="cssJsPath"/>images/noimg.gif'</s:else>/>
                                    </a>
                                </td>
                                <td class="textc">
                                    <a href="javascript:;" target="_blank" data-productid="<s:property value='productId'/>"
                                       class="j_preview_productDraft"
                                       title="<s:property value="productTitle" />"><s:property value="productTitle" /></a>
                                    <br>
                                    <a class="label" data-toggle="collapse" data-parent="#accordion"
                                       href="#<s:property value="productId" />"><i class="icon-xia icon-white"></i>SKU规格</a>
                                </td>
                                <td width="100px"><s:property value="#product.productSkuDrafts[0].price"/> 元</td>
                                <td width="100px"><s:property value="#product.productSkuDrafts[0].stock"/></td>
                            </tr>
                            </tbody>
                        </table>
					<%--<div class="media textc"><a class="pull-left j_preview_productDraft" target="_blank" href="javascript:;" data-productid="<s:property value='productId'/>" ><img
						class="thumbnail" src=<s:if test="#product.productSkuDrafts[0].viewImgPath!=null">'<s:property value="imagePath"/><s:property value="#product.productSkuDrafts[0].viewImgPath"/>'</s:if><s:else>'<s:property value="cssJsPath"/>images/noimg.gif'</s:else>></a>
					<div class="com_title">
					<ul>
						<li><span><a href="javascript:;" target="_blank" data-productid="<s:property value='productId'/>" class="j_preview_productDraft"
							title="<s:property value="productTitle" />"><s:property value="productTitle" /></a><br>
						<a class="label" data-toggle="collapse" data-parent="#accordion"
							href="#<s:property value="productId" />"><i class="icon-xia icon-white"></i>SKU规格</a></span>
                            <p><s:property value="#product.productSkuDrafts[0].price"/> 元</p><p><s:property value="#product.productSkuDrafts[0].stock"/></p>
						</li>
					</ul>
					</div>
					</div>--%>
					</td>
					<td class="width100"><s:property value="prodBrand.brandName" /></td>
					<td class="width100"><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td class="width100">
						<s:if test="auditStatus == null || auditStatus == 2">
							已审核待上架
						</s:if>
						<s:if test="auditStatus == 6">
							审核未通过
						</s:if>
						<s:if test="auditStatus == 1">
							审核中
						</s:if>
						<s:if test="auditStatus == 0">
							未审核
						</s:if>
						<br>
						<a href="javascript:;" target="_blank" data-productid="<s:property value="productId" />" class="j_preview_productDraft" >预览商品详情</a>
					</td>
					<td class="width100">
						<s:if test="auditStatus == 2">
							<button class="btn btn-mini width66 btn-success upselfProduct" data-productid="<s:property value='productId'/>">上架</button>
							<br>
							<button class="btn btn-mini width66 viewDetail" data-id="<s:property value="productId" />">查看</button>
							<br>
							<button class="btn btn-mini width66 j_update_product_draft" data-productid="<s:property value="productId" />">编辑</button>
							<br>
							<button class="btn btn-mini width66 updatePic" data-productid="<s:property value='productId'/>">管理图片</button>
						</s:if>
						<s:if test="auditStatus == 6">
							<button class="btn btn-mini width66 btn-success j_reason_product_draft" data-productid="<s:property value='productId'/>">查看原因</button>
							<input type="hidden" value="<s:property value="reasons" />；<s:property value="priceReasons" />" id="reasons_<s:property value='productId'/>" />
							<br>
							<button class="btn btn-mini width66 j_update_product_draft" data-productid="<s:property value="productId" />">编辑</button>
							<br>
							<button class="btn btn-mini width66 updatePic" data-productid="<s:property value='productId'/>">管理图片</button>
							<br>
							<button class="btn btn-mini width66 submitTheAudit" data-type="0" data-productid="<s:property value='productId'/>">提交审核</button>
							<br>
							<button class="btn btn-mini width66 viewDetail" data-id="<s:property value="productId" />">查看</button>
						</s:if>
						<s:if test="auditStatus == 1">
							<button class="btn btn-mini width66 btn-success viewDetail" data-id="<s:property value="productId" />">查看</button>
							<br>
							<button class="btn btn-mini width66 desSubmitTheAudit" data-type="1" data-productid="<s:property value='productId'/>">撤回申请</button>
						</s:if>
						<s:if test="auditStatus == 0">
							<button class="btn btn-mini width66 btn-success submitTheAudit" data-type="0" data-productid="<s:property value='productId'/>">申请审核</button>
							<br>
							<button class="btn btn-mini width66 viewDetail" data-id="<s:property value="productId" />">查看</button>
							<br>
							<button class="btn btn-mini width66 j_update_product_draft" data-productid="<s:property value="productId" />">编辑</button>
							<br>
							<button class="btn btn-mini width66 updatePic" data-productid="<s:property value='productId'/>">管理图片</button>
						</s:if>
						<br>
						<button class="btn btn-mini width66 j_delete_product" data-productid="<s:property value="productId"/>">删除</button>
					</td>
				</tr>
			</tbody>
		</table>
		<div id="<s:property value="productId" />" class="collapse">
		<div class="sukinfo">
		<ul>
			<s:iterator value="productSkuDrafts" var="sku">
				<li>
					<img class="thumbnail" src=<s:if test="#sku.viewImgPath!=null">'<s:property value="imagePath"/><s:property value="#sku.viewImgPath"/>'</s:if><s:else>'<s:property value="cssJsPath"/>images/noimg.gif'</s:else>>
					<p>SKU编码:<f1><s:property value="#sku.productSkuCode"/></f1></p>
					<p>市场价:<f2>¥<s:property value="#sku.markPrice"/></f2></p>
					<p>销售单价:<f2>¥<s:property value="#sku.price"/></f2></p>
					<p>重量:<f2><s:property value="#sku.unitWeight"/></f2></p>
					<p>库存:<f3><s:property value="#sku.stock"/></f3></p>
					<p>商家货号:<f3><s:property value="#sku.sellerSkuCode"/></f3></p>
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
<s:form action="productuUpShelf" method="POST" namespace='/ajaxJson' id="productuUpShelf" name='productuUpShelf'></s:form>
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