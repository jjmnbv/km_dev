<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="商品添加"></jsp:param>
</jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加商品基本资料</title>
<style type="text/css">
	label{display: inline;}
	.trbg01 a{cursor: pointer;}
	#guige{min-height: 98px; max-height:230px; overflow-y: auto; display: block;}
	#guige::-webkit-scrollbar-track {background-color: #EEEEEE;-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;}
	#guige::-webkit-scrollbar-thumb {background-color: #ccc; -webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;}
	#guige::-webkit-scrollbar-button {background-color: none;}
	#guige::-webkit-scrollbar-corner {background-color: black;}
	#guige::-webkit-scrollbar {width: 10px;}
</style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<div class="container-fluid">
<div class="row-fluid">
<jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_product.jsp"></jsp:include>
<div class="content" id="content" data-url="${basePath}">
<div class="row-fluid"><!-- block -->
<div class="block_01">
<div class="navbar-inner">
<ul class="breadcrumb">
	<i class="icon-home"></i>
	<li>商品 <span class="divider">/</span></li>
	<li>添加新商品</li>
</ul>
</div>
<input type="hidden" id="supplierType" value="<s:property value='#session.seesionKmb2bSupplierType' />" />
<input type="hidden" id="treeData" value="<s:property value="jsonDataForTreeNode"/>"/>
<s:form action="/productDraft/productAddDraft.action" method="POST" id="frm" name='frm' enctype="multipart/form-data" >
<div class="block-content collapse in"><!--开始-->
<div class="com_step">
<ul>
	<li class="gray"><i class="icon-step01 icon-white"></i>
	<p>STEP.1</p>
	<span>选择商品分类</span><i class="icon-stept"></i></li>
</ul>
<ul>
	<li><i class="icon-step02"></i>
	<p>STEP.2</p>
	<span>填写商品详情</span><i class="icon-stept"></i></li>
</ul>
<ul>
	<li class="gray"><i class="icon-step03 icon-white"></i>
	<p>STEP.3</p>
	<span>上传商品图片</span><i class="icon-stept"></i></li>
</ul>
<ul>
	<li class="gray"><i class="icon-step04 icon-white"></i>
	<p>STEP.4</p>
	<span>添加商品成功</span></li>
</ul>
</div>
<!--添加商品开始-->
<table cellpadding="0" cellspacing="0" border="0"
	class="table  table-bordered">
	<tr class="tablesbg">
		<td colspan="2" class="shoptLt">商品基本信息</td>
		<input type="hidden" value="0" id="pIndex" />
	</tr>
	<tr>
		<td class="width140 shoptR"><span class="required">*</span>商品分类：</td>
		<td class="tdleft">
			${bCategoryName} > ${mCategoryName} > ${sCategoryName}
			<s:hidden name="product.categoryId" id="categoryId"/>
			<s:hidden name="product.categoryName" id="categoryName"/>
		</td>
	</tr>
	<tr>
		<td class="shoptR"><span class="required">*</span>商品主标题：</td>
		<td class="tdleft">
			<s:textfield cssClass="span8" name="product.productTitle" id="productTitle"
                         size="80" maxlength="50" data-provide="typeahead" data-items="4"/>
		</td>
	</tr>
	<tr>
		<td class="shoptR">商品副标题：</td>
		<td class="tdleft">
			<s:textfield name="product.productSubtitle" id="productSubtitle"
                         size="80" cssClass="span8" maxlength="50" data-provide="typeahead" data-items="4"/>
		</td>
	</tr>
	<tr>
		<td class="shoptR"><span class="required">*</span>商品简称：</td>
		<td class="tdleft">
            <s:textfield name="product.productName" id="productName"
                   size="80" cssClass="span5" maxlength="20" ata-provide="typeahead" data-items="4"/>
		</td>
	</tr>
	<input type="hidden" id="isHasShop" value="<s:property value="#request.isHasShop"/> "/>
	<s:if test="#request.isHasShop">
	<tr>
		<td class="shoptR"><span class="required">*</span>店内分类：</td>
		<td class="tdleft">
		
			<s:if test="#request.isExistShopCateCreateBySelf">
				<a href="javascript:void(0);" class="j_choose_shopCategory">&nbsp;选择&nbsp;</a>
			</s:if>
			 &nbsp;&nbsp;&nbsp;&nbsp;已选分类:&nbsp;&nbsp; <span id="shopCategoryName"><s:property value="defaultShopCateName"/></span>
			<s:hidden name="shopCategoryId" id="shopCategoryId"/>
			<s:hidden name="defaultShopCateId" id="defaultShopCateId"/>
			<s:hidden name="defaultShopCateName" id="defaultShopCateName"/>
			<s:if test="!#request.isExistShopCateCreateBySelf">
                <a data-original-title="您还没有创建店铺分类，店铺分类有助于更好地展示商品。"
                   href="#" data-toggle="tooltip" data-placement="right" title="">
                    <i class="icon-question-sign"></i>
                </a>
                <a href="javascript:void(0)" class="j_to_add_prod_shop_category">>>现在就去创建分类</a>
			</s:if>
		</td>
	</tr>
	</s:if>
	<tr>
		<td class="shoptR"><span class="required">*</span>商品品牌：</td>
		<td class="tdleft">
			<input type="text" class="width200" id="autocomplete" value="<s:property value="product.prodBrand.brandName" />" size="32" name="product.prodBrand.brandName">
			<input type="hidden" value="<s:property value="product.brandId" />" name="product.brandId" id="brandId" />
            <a href="javascript:void(0)" class="j_to_add_prod_brand_draft">>>现在就去申请新品牌</a>
		</td>
	</tr>
	<tr>
		<td class="shoptR"><span class="required">*</span>销售单价：</td>
		<td class="tdleft"><input type="text" class="span2" name="product.price"
			id="pPrice" data-provide="typeahead" data-items="4" maxlength="10"> 元
            <a data-original-title="价格必须是0.01~9999999之间的数字，最多保留两位小数，且不能高于市场价。
                此价格为商品实际销售价格，如果商品存在规格，该价格显示最低价格。"
               href="#" data-toggle="tooltip" data-placement="right" title="">
                <i class="icon-question-sign"></i>
            </a>
		</td>
	</tr>
	<tr>
		<td class="shoptR"><span class="required">*</span>市场价：</td>
		<td class="tdleft"><input type="text" class="span2" name="product.marketPrice"
			id="pMarkPrice" data-provide="typeahead" data-items="4" maxlength="10"> 元
            <a data-original-title="价格必须是0.01~9999999之间的数字，最多保留两位小数，此价格仅为市场参考售价，请根据该实际情况认真填写。"
               href="#" data-toggle="tooltip" data-placement="right" title="">
                <i class="icon-question-sign"></i>
            </a>
		</td>
	</tr>
	<tr>
		<td class="shoptR"><span class="required">*</span>推广服务费：</td>
		<td class="tdleft"><input type="text" class="span2" name="product.pvValue"
			id="pPvValue" data-provide="typeahead" data-items="4" maxlength="10"> 元
            <a data-original-title="推广服务费必须是1~9999999之间的数字，最多保留两位小数。"
               href="#" data-toggle="tooltip" data-placement="right" title="">
                <i class="icon-question-sign"></i>
            </a>
		</td>
	</tr>
	<tr>
		<td class="shoptR"><span class="required">*</span>重量：</td>
		<td class="tdleft"><input type="text" class="span2" name="product.unitWeight"
			id="pUnitWeight" data-provide="typeahead" data-items="4" maxlength="10"> 克
            <a data-original-title="重量必须是0.01~9999999之间的数字，最多保留两位小数。"
               href="#" data-toggle="tooltip" data-placement="right" title="">
                <i class="icon-question-sign"></i>
            </a>
		</td>
	</tr>
	<tr>
		<td class="shoptR"><span class="required">*</span>商品库存：</td>
		<td class="tdleft"><input type="text" class="span2" name="product.stock"
			id="pStock" data-provide="typeahead" data-items="4" maxlength="8" >
            <a data-original-title="商铺库存数量必须为0~99999999之间的整数。"
               href="#" data-toggle="tooltip" data-placement="right" title="">
                <i class="icon-question-sign"></i>
            </a>
		</td>
	</tr>
	<tr>
		<td class="shoptR">商家货号：</td>
		<td class="tdleft"><input type="text" class="span2" name="product.sellerSkuCode"
			id="pSellerSkuCode" data-provide="typeahead" data-items="4" maxlength="17">
		</td>
	</tr>
	<s:if test="product.categoryAttrList != null && product.categoryAttrList.size()>0">
		<s:iterator value="product.categoryAttrList" status="st">
			<s:if test="isSku==1">
				<tr class="isCheckBoxListTrDraftAdd"> 
					<td class="shoptR skuName">
						<input type="hidden" class="input_style"  name="product.categoryAttrList[<s:property value='#st.index'/>].categoryAttrId" value="<s:property value='categoryAttrId'/>"/>
						<input type="hidden" class="input_style" name="product.categoryAttrList[<s:property value='#st.index'/>].isSku" value="<s:property value='isSku'/>"/>
						<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].isReq" value="<s:property value='isReq'/>"/>
						<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].inputType" value="<s:property value='inputType'/>"/>
						<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].isNav" value="<s:property value='isNav'/>"/>
						<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].sortno" value="<s:property value='sortno'/>"/>
						<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].categoryAttrName" value="<s:property value='categoryAttrName'/>"/>
						<s:if test="isReq==1"><font color="red">*</font></s:if><label><s:property value='categoryAttrName'/></label>：
					</td>
					<td class="tdleft trbg01 dxh40 skuValue"> 
						<s:if test="categoryAttrValueList != null && categoryAttrValueList.size()>0" >
							<s:checkboxlist cssClass="cboxtop" list="categoryAttrValueList" listKey="categoryAttrValueId" listValue="categoryAttrValue" name="product.categoryAttrList[%{#st.index}].categoryAttrValues" value="checkBoxIds"></s:checkboxlist>
						</s:if>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" data-toggle="collapse" class="addCheckSkuAttr"><i class="icon-plus"></i> 新增规格</a>
					</td>
				</tr>
			</s:if>
		</s:iterator>
	</s:if>
	<tr>
		<td class="shoptR">关键词(SEO)：</td>
		<td class="tdleft">
			<s:textfield name="product.keyword" id="keyword"
                         size="80" cssClass="span5" maxlength="32" data-provide="typeahead" data-items="4"/>
		</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="0" border="0"
	class="table  table-bordered">
	<tbody>
		<tr class="tablesbg">
			<td colspan="2" class="shoptLt">商品详情描述</td>
		</tr>
		<tr>
			<td class="width140 shoptR">基本属性：</td>
			<td class="tdleft">
				<s:set name="categoryAttrList" value="product.categoryAttrList"></s:set>
                <s:if test="#categoryAttrList != null && #categoryAttrList.size()>0">
                    <table width="100%" border="0" cellspacing="5" class="newform">
                    <tbody>
                    <s:set name="i" value="0"></s:set>
                    <s:iterator value="#categoryAttrList" status="st" var="item">
                        <s:if test="isSku==0">
                            <s:set name="i" value="#i + 1" ></s:set>
                            <s:if test="#i%2 == 1">
                                <tr>
                            </s:if>
                            <td class="width120 shoptR ct_cate">
                                <input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].categoryAttrId" value="<s:property value='categoryAttrId'/>"/>
                                <input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].isSku" value="<s:property value='isSku'/>"/>
                                <input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].isReq" value="<s:property value='isReq'/>"/>
                                <input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].inputType" value="<s:property value='inputType'/>"/>
                                <input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].isNav" value="<s:property value='isNav'/>"/>
                                <input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].sortno" value="<s:property value='sortno'/>"/>
                                <input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].categoryAttrName" value="<s:property value='categoryAttrName'/>"/>
                                <label><s:if test="isReq==1"><font color="red">*</font></s:if><s:property value='categoryAttrName'/>：</label>
                            </td>
                            <td class="textL" width="40%">
                                <s:if test="inputType==0">
                                    <input class="span5" data-provide="typeahead" data-items="4"
                                           id="<s:property value='#st.index'/>" maxlength="32"
                                           name="product.categoryAttrList[<s:property value='#st.index'/>].categoryAttrValues"
                                           class="input_style<s:if test="isReq==1"> {required:true}</s:if>" size="40"
                                           type="text" value="<s:property value='categoryAttrValue'/>" />
                                </s:if>
                                <s:elseif test="inputType==1">
                                    <s:if test="categoryAttrValueList != null && categoryAttrValueList.size()>0" >
                                        <s:radio list="categoryAttrValueList"
                                                 listKey="categoryAttrValueId" listValue="categoryAttrValue"
                                                 name="product.categoryAttrList[%{#st.index}].categoryAttrValues"
                                                 value="categoryAttrValue"/>
                                    </s:if>
                                </s:elseif>
                                <s:elseif test="inputType==2">
                                    <s:if test="categoryAttrValueList != null && categoryAttrValueList.size()>0" >
                                        <s:checkboxlist list="categoryAttrValueList"
                                                        listKey="categoryAttrValueId" listValue="categoryAttrValue"
                                                        name="product.categoryAttrList[%{#st.index}].categoryAttrValues"
                                                        value="checkBoxIds" ></s:checkboxlist>
                                    </s:if>
                                </s:elseif>
                                <s:elseif test="inputType==3">
                                    <s:if test="categoryAttrValueList != null && categoryAttrValueList.size()>0" >
                                        <s:select name="product.categoryAttrList[%{#st.index}].categoryAttrValues"
                                                  list="categoryAttrValueList"
                                                  listKey="categoryAttrValueId" listValue="categoryAttrValue"
                                                  value="categoryAttrValue" cssClass="ui-form-select span5"></s:select>
                                    </s:if>
                                </s:elseif>
                            </td>
                            <s:if test="#i%2 == 0 || #st.last">
                            </tr>
                            </s:if>
                        </s:if>
                    </s:iterator>
                    </tbody>
                    </table>
                </s:if>
			</td>
		</tr>
		<%-- 彭博修改  产品需求暂时屏蔽
		<tr>
			<td class="shoptR">运营属性：</td>
			<td class="tdleft">
			<div class="duoxian">
			<s:if test="operationAttrList != null && operationAttrList.size()>0">
				<ul>
					<s:iterator value="operationAttrList" var="operAttr" status="st">
						<li>
							<input id="op<s:property value='#st.index'/>" type="checkbox" name="product.operationAttrIds" value="<s:property value='#operAttr.operationAttrId'/>">
							<label for="op<s:property value='#st.index'/>"><s:property value='#operAttr.operationAttrName'/></label>
						</li>
					</s:iterator>
				</ul>
			</s:if>
			</div>
			</td>
		</tr>
		 --%>
		<tr>
			<td class="shoptR">自定义属性：</td>
			<td class="tdleft">
				<a type="button" class="btn j_addAttrValue_draft" data-toggle="collapse"><i class="icon-plus"></i> 添加动态属性</a>
			</td>
		</tr>
		<tr>
			<td class="shoptR">商品描述：</td>
			<td class="tdleft">
				<textarea id="editor_add_id" name="product.introduce"
                          style="height:400px;width:99%;resize:none;">
                    <s:property value="product.introduce"/></textarea>
				<!-- lazy -->
				<div class="editor_change_add" style="display:none" ></div>
				<div style="display:none">
					<textarea id="editor_lazy_add" name="product.introduceLazy" ></textarea>
				</div>
			</td>
		</tr>
	</tbody>
</table>
<!--添加商品结束-->

<div class="form-actions"><a href="javascript:;"
	class="btn btn-large j_returnBack"> <i class="icon-chevron-left"></i> 返回上一步</a> <a
	href="javascript:void(0);" class="btn btn-success btn-large j_submit_addProduct"> 下一步，上传商品图片
<i class="icon-chevron-right icon-white"></i></a></div>
</div>
</s:form>
    <s:form name="backFrm" id="backFrm" method="post">
        <s:hidden name="product.categoryId"></s:hidden>
        <input type="hidden" name="product.bCategoryId" value="<s:property value='#request.bCategoryId'/>" />
        <input type="hidden" name="product.mCategoryId" value="<s:property value="#request.mCategoryId" />" />
    </s:form>
</div>
</div>
</div>
</div>
</div>

<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>