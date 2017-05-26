<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
	<meta name="renderer" content="webkit|ie-comp|ie-stand"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="商品修改"></jsp:param>
</jsp:include>
<title>商品修改</title>
<style type="text/css">
    label{display: inline;}
    .trbg01 a{cursor: pointer;}
    #guige{height: 300px;overflow: scroll;display: block;}
    #guige::-webkit-scrollbar-track {background-color: #EEEEEE;-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;}
    #guige::-webkit-scrollbar-thumb {background-color: #ccc; -webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;}
    #guige::-webkit-scrollbar-button {background-color: none;}
    #guige::-webkit-scrollbar-corner {background-color: black;}
    #guige::-webkit-scrollbar {width: 10px;}
    #kucun{height: 300px;}
</style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_product.jsp"></jsp:include>

<div class="content" id="content" data-url="${basePath}">
<div class="row-fluid">
<div class="block_01">
<div class="navbar-inner">
<ul class="breadcrumb">
	<i class="icon-home"></i>
	<li>商品 <span class="divider">/</span></li>
	<li>修改商品</li>
</ul>
</div>
<input type="hidden" id="treeData" value="<s:property value="jsonDataForTreeNode"/>"/>
<input type="hidden" id="supplierType" value="<s:property value='#session.seesionKmb2bSupplierType' />" />
<s:form action="modifyProductDraft" method="POST" namespace='/productDraft'  enctype="multipart/form-data"   
        id="saveProductFrm" name='saveProductFrm'>
<s:hidden name="auditStatus"></s:hidden>
<input type="hidden" name="delCertiFileImage"/>
<div class="block-content collapse in"><!--开始-->
    <s:if test="'formImage' == type">
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
    </s:if>
<s:set name="i" value="0" ></s:set>
<table cellpadding="0" cellspacing="0" border="0"
	class="table  table-bordered">
	<tr class="tablesbg">
		<td colspan="2" class="shoptLt">商品基本信息</td>
		<input type="hidden" value="0" id="pIndex" />
		<input type="hidden" name="product.productId" id="productId" value="<s:property value="product.productId" />" />
		<input type="hidden" name="product.productNo" id="productNo" value="<s:property value="product.productNo" />" />
		<s:hidden name="product.opType" id="productOpType" />
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
			<s:textfield name="product.productTitle" id="productTitle"
                         cssClass="span8" size="80" maxlength="50" data-provide="typeahead" data-items="4" />
		</td>
	</tr>
	<tr>
		<td class="shoptR">商品副标题：</td>
		<td class="tdleft">
			<s:textfield name="product.productSubtitle" id="productSubtitle"
                         size="80" cssClass="span8" maxlength="50" data-provide="typeahead" data-items="4" />
		</td>
	</tr>
	<tr>
		<td class="shoptR"><span class="required">*</span>商品简称：</td>
		<td class="tdleft">
            <s:textfield name="product.productName" id="productName"
                         size="80" cssClass="span8" maxlength="20" data-provide="typeahead" data-items="4" />
		</td>
	</tr>
	<input type="hidden" id="isHasShop" value="<s:property value="#request.isHasShop"/> "/>
	<s:if test="#request.isHasShop">
	<tr>
		<td class="shoptR"><span class="required">*</span>店内分类：</td>
		<td class="tdleft">
			<s:if test="#request.isExistShopCateCreateBySelf">
				<a href="javascript:void(0);" class="j_choose_shopCategoryForDraftUpdate">&nbsp;选择&nbsp;</a>
			</s:if>
			 &nbsp;&nbsp;&nbsp;&nbsp;已选分类:&nbsp;&nbsp; <span id="shopCategoryName">${shopCategoryName }
			</span>
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
			<s:if test='product.upTime != null'>
				<s:property value="product.prodBrand.brandName" />
				<s:hidden name="product.prodBrand.brandName" id="brandName"/>
				<s:hidden name="product.brandId"/>
			</s:if>
			<s:else>
				<input id="autocomplete" type="text" class="width200" value="<s:property value="product.prodBrand.brandName" />" size="32" name="product.prodBrand.brandName">
				<%-- <input type="hidden" value="<s:property value="product.prodBrand.brandName" />" name="product.prodBrand.brandName" id="brandName" /> --%>
				<input type="hidden" value="<s:property value="product.brandId" />" name="product.brandId" id="brandId" />
                <a href="javascript:void(0)" class="j_to_add_prod_brand_draft">>>现在就去申请新品牌</a>
			</s:else>
		</td>
	</tr>
	<tr>
		<td class="shoptR"><span class="required">*</span>销售单价：</td>
		<td class="tdleft">
            <input type="text" class="span2" id="pPrice" name="product.price"
                   data-provide="typeahead" data-items="4"
                   value="<s:property value='product.productSkuDrafts[0].price' />"> 元
            <a data-original-title="价格必须是0.01~9999999之间的数字，且不能高于市场价。
                此价格为商品实际销售价格，如果商品存在规格，该价格显示最低价格。"
               href="#" data-toggle="tooltip" data-placement="right" title="">
                <i class="icon-question-sign"></i>
            </a>
		</td>
	</tr>
	<tr>
		<td class="shoptR"><span class="required">*</span>市场价：</td>
		<td class="tdleft">
            <input type="text" class="span2" id="pMarkPrice" name="product.marketPrice"
                   data-provide="typeahead" data-items="4"
                   value="<s:property value='product.productSkuDrafts[0].markPrice' />"> 元
            <a data-original-title="价格必须是0.01~9999999之间的数字，此价格仅为市场参考售价，请根据该实际情况认真填写。"
               href="#" data-toggle="tooltip" data-placement="right" title="">
                <i class="icon-question-sign"></i>
            </a>
		</td>
	</tr>
    <tr>
        <td class="shoptR"><span class="required">*</span>推广服务费：</td>
        <td class="tdleft">
            <input type="text" class="span2" id="pPvValue" name="product.pvValue"
                   data-provide="typeahead" data-items="4"
                   value="<s:property value='product.productSkuDrafts[0].pvValue' />"> 元
            <a data-original-title="推广服务费必须是1~9999999之间的数字，最多保留两位小数。"
               href="#" data-toggle="tooltip" data-placement="right" title="">
                <i class="icon-question-sign"></i>
            </a>
        </td>
    </tr>
	<tr>
		<td class="shoptR"><span class="required">*</span>重量：</td>
		<td class="tdleft">
            <input type="text" class="span2" id="pUnitWeight" name="product.unitWeight"
                   data-provide="typeahead" data-items="4"
                   value="<s:property value='product.productSkuDrafts[0].unitWeight' />"> 克
            <a data-original-title="重量必须是0.01~9999999之间的数字。"
               href="#" data-toggle="tooltip" data-placement="right" title="">
                <i class="icon-question-sign"></i>
            </a>
		</td>
	</tr>
	<tr>
		<td class="shoptR">
			<s:if test="product.productSkuDrafts[0].attributeValues == null || product.productSkuDrafts[0].attributeValues.size() == 0">
				<input type="hidden" id="singleNoSkuAttr" value="yes" />
				<input type="hidden" id="singleNoSkuAttrId" value="<s:property value="product.productSkuDrafts[0].productSkuId" />" >
				<input type="hidden" id="pStatus" value="<s:property value="product.productSkuDrafts[0].status" />" >
				<div class="oldSkuDataTr">
					<input type="hidden" class="hideProductSkuId" value="<s:property value="product.productSkuDrafts[0].productSkuId" />" >
				</div>
			</s:if>
			<span class="required">*</span>商品库存：
		</td>
		<td class="tdleft">
            <input type="text" class="span2" id="pStock" name="product.stock" maxlength="8"
                   data-provide="typeahead" data-items="4" value="<s:property value='product.productSkuDrafts[0].stock' />">
            <a data-original-title="商铺库存数量必须为0~99999999之间的整数。"
               href="#" data-toggle="tooltip" data-placement="right" title="">
                <i class="icon-question-sign"></i>
            </a>
		</td>
	</tr>
	<tr>
		<td class="shoptR">商家货号：</td>
		<td class="tdleft">
            <input type="text" class="span2" id="pSellerSkuCode"
                   name="product.sellerSkuCode" data-provide="typeahead"
                   data-items="4" maxlength="17"
                   value="<s:property value='product.productSkuDrafts[0].sellerSkuCode' />">
		</td>
	</tr>
	<s:set name="skuAttrList" value="product.productAttrDraftList.{?#this.productAttrType==1 && #this.isSku==1}"></s:set>
	<s:if test="#skuAttrList != null && #skuAttrList.size()>0">
		<s:iterator value="#skuAttrList" status="st">
			<tr class="isCheckBoxListTrDraft">
				<td class="shoptR skuName">
					<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrId" value="<s:property value='productAttrId'/>"/>
					<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productId" value="<s:property value='productId'/>"/>
					<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].isSku" value="<s:property value='isSku'/>"/>
					<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].isReq" value="<s:property value='isReq'/>"/>
					<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].inputType" value="<s:property value='inputType'/>"/>
					<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].isNav" value="<s:property value='isNav'/>"/>
					<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="<s:property value='sortno'/>"/>
					<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="<s:property value='productAttrName'/>"/>
					<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].relateAttrId" value="<s:property value='relateAttrId'/>"/>
					<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrType" value="<s:property value='productAttrType'/>"/>
					<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].opType" value="2"/>
					<s:if test="isReq==1"><font class="fn-red">*</font></s:if><label><s:property value='productAttrName'/></label>：
				</td>
				<td class="tdleft trbg01 skuValue">
					<s:checkboxlist cssClass="cboxtop" list="categoryAttrValueList" listKey="categoryAttrValueId" listValue="categoryAttrValue" name="product.productAttrDraftList[%{#i}].productAttrValues" value="checkBoxIds"></s:checkboxlist>
					<s:if test="#request.skuNewAttrList.containsKey(relateAttrId)">
						<s:checkboxlist cssClass="cboxtop newSkuCheck" list="#request.skuNewAttrList.get(relateAttrId)"
                                        name="newAttr_%{#i}"></s:checkboxlist>
					</s:if>
					&nbsp;&nbsp;
					<a href="javascript:;" data-toggle="collapse" class="addCheckSkuAttr"><i class="icon-plus"></i> 新增规格</a>
				</td>
			</tr>
			<s:set name="i" value="#i + 1" ></s:set>
		</s:iterator>
	</s:if>
	<input type="hidden" id="toDeleteSkuIds" name="toDeleteSkuIds" value="" />
	<s:if test="product.productSkuDrafts[0].attributeValues != null && product.productSkuDrafts[0].attributeValues.size() > 0">
	<tr style="" id="kucun">
		<td class="shoptR">库存配置：</td>
		<td class="tdleft trbg01" id="guige">
			<table cellspacing="0" cellpadding="0" border="0" class="table table-bordered" id="skuTable">
				<tr class="trbg02">
				  <s:iterator value="product.productSkuDrafts[0].attributeValues" status="st0">
				  	<td><s:property value='attribute'/></td>
				  </s:iterator>
				  <td class="width120">市场价</td>
				  <td class="width120">销售单价</td>
                  <td class="width120">推广服务费</td>
				  <td class="width120">重量</td>
				  <td class="width120">库存</td>
				  <td class="width120">商家货号</td>
				  <td class="width60">操作</td>
				  <td class="width50">状态</td>
				</tr>
				<s:iterator value="product.productSkuDrafts" var="sku" status="sta" >
					<s:set name="oldSkus" value="''" ></s:set>
					<tr class='oldSkuDataTr'>
					  <s:iterator value="#sku.attributeValues" var="attr" status="st" >
					  	<td>
                            <s:if test="'true' == isNewAttr">
                                <s:set name="oldSku" value="categoryAttrId + ':' + attribute + ':' + '@' + value + '#' + categoryAttrValueId" ></s:set>
							</s:if>
							<s:else>
								<s:set name="oldSku" value="categoryAttrId + ':' + attribute + ':' + categoryAttrValueId" ></s:set>
							</s:else>
                            <s:property value="value" />
							<s:set name="oldSkus" value="#oldSkus + #oldSku" ></s:set>
							<s:if test="#st.count != #sku.attributeValues.size()">
								<s:set name="oldSkus" value="#oldSkus +','" ></s:set>
							</s:if>
					  	</td>
					  </s:iterator>
					  <td>
					  	<input type="text" class="span10 skuMarkPrice" maxlength="9" name="product.productSkuDrafts[<s:property value='#sta.index'/>].markPrice" value="<s:property value="#sku.markPrice" />" >
					  </td>
					  <td>
					  	<input type="text" class="span10 skuPrice" maxlength="9" name="product.productSkuDrafts[<s:property value='#sta.index'/>].price" value="<s:property value="#sku.price" />" >
					  </td>
					  <td>
					  	<input type="text" class="span10 skuPvValue" maxlength="9" name="product.productSkuDrafts[<s:property value='#sta.index'/>].pvValue" value="<s:property value="#sku.pvValue" />" >
					  </td>
					  <td>
					  	<input type="text" class="span10 skuUnitWeight" maxlength="9" name="product.productSkuDrafts[<s:property value='#sta.index'/>].unitWeight" value="<s:property value="#sku.unitWeight" />" >
					  </td>
					  <td>
					  	<input type="text" class="span10 skuStock" maxlength="8" name="product.productSkuDrafts[<s:property value='#sta.index'/>].stock" value="<s:property value="#sku.stock" />" >
					  </td>
					  <td>
					  	<input type="text" class="span10" name="product.productSkuDrafts[<s:property value='#sta.index'/>].sellerSkuCode" maxlength="17" value="<s:property value="#sku.sellerSkuCode" />" >
					  </td>
					  <td>
					  	<button class="btn btn-danger j_removeSkuTrDraft" deleteId="<s:property value='productSkuId'/>">删除</button>
					  	<input type='hidden' name='oldskuCheckedId' value='<s:property value="#oldSkus" />' />
					  	<input type="hidden" class="hideProductSkuId" value="<s:property value="#sku.productSkuId" />" >
					  </td>
					  <td>
					  	<input type="hidden" name="product.productSkuDrafts[<s:property value='#sta.index'/>].productSkuId" value="<s:property value="#sku.productSkuId" />" >
						<s:radio list="#{'0':'无效','1':'有效'}" listKey="key" listValue="value" name="product.productSkuDrafts[%{#sta.index}].status" value="#sku.status" ></s:radio>
					  </td>
					</tr>
				</s:iterator>
			</table>
		</td>
	</tr>
	</s:if>
	<tr>
		<td class="shoptR">关键词(SEO)：</td>
		<td class="tdleft">
			<s:textfield cssClass="span5" name="product.keyword" id="keyword"
                         size="80" maxlength="32" data-provide="typeahead" data-items="4" />
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
				<s:set name="categoryAttrList" value="product.productAttrDraftList.{?#this.productAttrType==1 && #this.isSku==0}"></s:set>
                <s:if test="#categoryAttrList != null && #categoryAttrList.size()>0">
                <table width="100%" border="0" cellspacing="5" class="newform">
                <tbody>
                <s:iterator value="#categoryAttrList" status="st">
                    <s:if test="#st.odd == true">
                        <tr>
                    </s:if>
                    <td class="width120 shoptR ct_cate">
                        <input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrId" value="<s:property value='productAttrId'/>"/>
                        <input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productId" value="<s:property value='productId'/>"/>
                        <input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].isSku" value="<s:property value='isSku'/>"/>
                        <input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].isReq" value="<s:property value='isReq'/>"/>
                        <input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].inputType" value="<s:property value='inputType'/>"/>
                        <input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].isNav" value="<s:property value='isNav'/>"/>
                        <input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="<s:property value='sortno'/>"/>
                        <input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="<s:property value='productAttrName'/>"/>
                        <input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].relateAttrId" value="<s:property value='relateAttrId'/>"/>
                        <input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrType" value="<s:property value='productAttrType'/>"/>
                        <input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].opType" value="2"/>
                        <label><s:if test="isReq==1"><font color="red">*</font></s:if>
                            <s:property value='productAttrName'/>：
                        </label>
                    </td>
                    <td class="textL" width="40%">
                        <s:if test="inputType==0">
                            <input class="ui-input span5"  id="<s:property value='#i'/>"
                                   name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValues"
                                   <s:if test="isReq==1">class="{required:true}"</s:if> size="40"
                                   type="text" value="<s:property value='productAttrValue'/>" />
                        </s:if>
                        <s:elseif test="inputType==1">
                            <s:if test="categoryAttrValueList != null && categoryAttrValueList.size()>0" >
                                <s:radio list="categoryAttrValueList"
                                         listKey="categoryAttrValueId" listValue="categoryAttrValue"
                                         name="product.productAttrDraftList[%{#i}].productAttrValues"
                                         value="productAttrValue"/>
                            </s:if>
                        </s:elseif>
                        <s:elseif test="inputType==2">
                            <s:if test="categoryAttrValueList != null && categoryAttrValueList.size()>0" >
                                <s:checkboxlist list="categoryAttrValueList"
                                                listKey="categoryAttrValueId" listValue="categoryAttrValue"
                                                name="product.productAttrDraftList[%{#i}].productAttrValues"
                                                value="checkBoxIds"></s:checkboxlist>
                            </s:if>
                        </s:elseif>
                        <s:elseif test="inputType==3">
                            <s:if test="categoryAttrValueList != null && categoryAttrValueList.size()>0" >
                                <s:select name="product.productAttrDraftList[%{#i}].productAttrValues"
                                          list="categoryAttrValueList"
                                          listKey="categoryAttrValueId" listValue="categoryAttrValue"
                                          value="productAttrValue" cssClass="ui-form-select span5"></s:select>
                            </s:if>
                        </s:elseif>
                    </td>
                    <s:if test="#st.odd == false || #st.last">
                        </tr>
                    </s:if>
                <s:set name="i" value="#i + 1" ></s:set>
                </s:iterator>
                </tbody>
                </table>
                </s:if>
			</td>
		</tr>
		<%--
		<tr>
			<td class="shoptR">运营属性：</td>
			<td class="tdleft">
			<div class="duoxian">
			<s:if test="operationAttrList != null && operationAttrList.size()>0">
				<ul>
					<s:iterator value="operationAttrList" var="operAttr" status="st">
						<li>
							<input id="op<s:property value='#st.index'/>" type="checkbox" <s:if test="#operAttr.isSelect==true"> checked="checked" </s:if> name="product.operationAttrIds" value="<s:property value='#operAttr.operationAttrId'/>">
							<label for="op<s:property value='#st.index'/>"><s:property value='#operAttr.operationAttrName'/></label>
						</li>
					</s:iterator>
				</ul>
			</s:if>
			</div>
			</td>
		</tr> --%>
		<tr>
			<td class="shoptR">自定义属性：</td>
			<td class="tdleft">
				<s:set name="definitionAttrList" value="product.productAttrDraftList.{?#this.productAttrType==2}"></s:set>
				<s:iterator value="#definitionAttrList">
					<div class="in collapse ct_dyna" style="height: auto;">
						<input type="text" class="span2 dynaName" id="typeahead" name="product.productAttrDraftList.productAttrName" value="<s:property value='productAttrName'/>" maxlength="40" data-provide="typeahead" data-items="4">&nbsp;
						<input type="text" class="span2 dynaValue" id="typeahead" name="product.productAttrDraftList.productAttrValue" value="<s:property value='productAttrValue'/>" maxlength="40" data-provide="typeahead" data-items="4">&nbsp;
						<button class="btn j_delAttrValue">取消</button>
					</div>
				</s:iterator>
				<a type="button" class="btn j_addAttrValue_draft" data-toggle="collapse"><i class="icon-plus"></i> 添加动态属性</a>
			</td>
		</tr>
		
		<tr>
			<td class="shoptR">商品描述：</td>
			<td class="tdleft">
				<textarea id="editor_id" name="product.introduce"
                          style="height:400px;width:99%;resize:none;">
                    <s:property value="product.introduce"/></textarea>
				<!-- lazy -->
				<div class="editor_change" style="display:none" ></div>
				<div style="display:none">
					<textarea id="editor_lazy" name="product.introduceLazy" ><s:property value="product.introduceLazy"/></textarea>
				</div>
			</td>
		</tr>
	</tbody>
</table>
<div class="form-actions">
    <s:if test="'formImage' == type">
        <s:hidden name="type"></s:hidden>
        <a href="javascript:;" class="btn btn-large btnBack">
            <i class="icon-chevron-left"></i>返回上一步</a>
        <a href="javascript:void(0);" class="btn btn-success btn-large saveProduct">
            下一步，上传商品图片
            <i class="icon-chevron-right icon-white"></i>
        </a>
    </s:if>
    <s:else>
        <s:if test="type != 'fromWait'">
            <button class="btn btn-large btnBack">返回</button>
        </s:if>
        <button class="btn btn-success btn-large saveProduct" data-opType="1">保存</button>
    </s:else>
</div>
</div>
<input type="hidden" id="indexCount" value="<s:property value="#i" />" />
</s:form>
</div>
</div>
</div>
<s:form action="" method="POST" id="backFrm" name='backFrm'>
    <s:hidden name="auditStatus"></s:hidden>
    <s:hidden name="type" id="type"></s:hidden>
    <s:hidden name="product.categoryId"/>
    <input type="hidden" name="product.bCategoryId" value="<s:property value='#request.bCategoryId'/>" />
    <input type="hidden" name="product.mCategoryId" value="<s:property value="#request.mCategoryId" />" />
</s:form>

<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>

</body>
</html>