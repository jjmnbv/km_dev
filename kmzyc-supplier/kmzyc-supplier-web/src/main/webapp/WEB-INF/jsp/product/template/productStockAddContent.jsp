<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="fn-p20">
	<div class="ui-breadcrumb">
			<span>商品管理/库存管理/库存列表/商品库存添加</span>
	</div>	
	<form class="ui-form ui-form-info fn-mt20" action="/product/addProductStock.action" method="post" id="frm">
	<fieldset>
		<legend>商品库存基本信息</legend>
			<s:hidden name="productStock.skuAttributeId" id="skuAttributeId" />
			<s:hidden name="productStock.productId" id="productId" />
			<input type="hidden" id="basePath" value="${basePath}"/>
		<div class="ui-form-item">
			<label class="ui-form-label" for=""><font color="red">*</font>所属仓库：</label>
			<s:select list="#request.suppliersWarehouseMap" listKey="key" listValue="value" name="productStock.warehouseId" id="warehousID" headerKey="-1" headerValue="--选择仓库--"></s:select>
			<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_shopName_error" style="display: none;">
				<i class="ui-icon ui-icon-error"></i><span id="shop_error_msg"></span>
			</p>
		</div>
		<div class="ui-form-item">
			<label class="ui-form-label" for=""><font color="red">*</font>商品SKU：</label>
			<s:textfield cssClass="ui-input" name="productStock.skuAttValue" id="skuAttValue" maxlength="40" readonly="true"/>&nbsp;&nbsp;<input id="submitButton" class="ui-button ui-button-success j_enableStock_list" type="button" value="选择"/>
		</div>
		<div class="ui-form-item">
			<label class="ui-form-label" for=""><font color="red">*</font>商品名称：</label>
			<s:textfield cssClass="ui-input" name="productStock.productName" id="productName" maxlength="150" readonly="true"/>
		</div>
		<div class="ui-form-item">
			<label class="ui-form-label" for=""><font color="red">*</font>商品编号：</label>
			<s:textfield cssClass="ui-input" name="productStock.productNo" id="productNo" maxlength="150" readonly="true"/>
		</div>
		<div class="ui-form-item">
			<label class="ui-form-label" for=""><font color="red">*</font>库存数量：</label>
			<s:textfield cssClass="ui-input" id="quantity" name="productStock.stockQuality" maxlength="150"/>
		</div>
		<div class="ui-form-item">
			<label class="ui-form-label" for="">备注：</label>
			<s:textarea cssClass="ui-input" name="productStock.remark" id="introduce" cols="100" rows="3"/>
			<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_introduce_error" style="display: none;">
				<i class="ui-icon ui-icon-error"></i><span id="introduce_error_msg"></span>
			</p>
		</div>
		<table class="ui-table ui-table-noborder fn-mt20">
			<tbody>
				<tr align="center">
					<th class="col-w-120 fn-text-rt">&nbsp;</th>
					<td align="left"><input id="submitButton" class="ui-button ui-button-success ui-button-lg j_add_productStock" type="button"
						value="提交">
						&nbsp;&nbsp;<input type="button" class="ui-button ui-button-default ui-button-lg j_productStock_list" value="返回" />
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>