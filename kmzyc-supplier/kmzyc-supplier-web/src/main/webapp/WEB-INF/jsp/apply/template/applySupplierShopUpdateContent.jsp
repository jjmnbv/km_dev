<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="fn-p20">
	<div class="ui-breadcrumb">
			<a href="#">首页</a>/<a href="#">供应商</a>/<span>修改新增</span>
	</div>
	<form class="ui-form ui-form-info fn-mt20" action="/ajaxJson/updateShopMain.action" method="post"
          id="frm" enctype="multipart/form-data">
	<s:hidden name="shopMain.shopId"/>
	<fieldset>
		<legend>店铺基本设置</legend>
		<%--<div class="ui-form-item">
			<label class="ui-form-label"><font color="red">*</font>店铺所属站点：</label>
			<s:property value="#request.siteTypeMap[shopMain.shopSite]"/>
		</div>--%>
		<div class="ui-form-item">
			<label class="ui-form-label"><font color="red">*</font>店铺名称：</label>
			<s:textfield cssClass="ui-input" name="shopMain.shopName" maxlength="40"/>
		</div>
		<div class="ui-form-item">
			<label class="ui-form-label">店铺标题：</label>
			<s:textfield cssClass="ui-input" name="shopMain.shopTitle" maxlength="150"/>
		</div>
		<div class="ui-form-item">
			<label class="ui-form-label">店铺LOGO：</label>
			<s:file name="logoFile" id="logoFile" cssClass="ui-input j_change_logoFile"></s:file> 注：支持后缀*.jpg/*.png/*.gif
			<s:hidden name="shopMain.logoPath" id="logoPath"/>
			<input type="hidden" name="logoPathTmp" value="${shopMain.logoPath}">
			<s:if test="shopMain.logoPath!=null">
				<p id="p_logoPath">
					<img alt="店铺logo" src="${imagePath}${shopMain.logoPath}" width="142" height="50"/><a href="javascript:void(0);" class="j_delete_logoPath" data-shopid="${shopMain.shopId}">删除</a>
				</p>
			</s:if>
		</div>
		<div class="ui-form-item">
			<label class="ui-form-label">店铺展示图片：</label>
			<s:file name="shopMainFile" id="shopMainFile" cssClass="ui-input j_change_shopMainFile"></s:file> 注：建议上传图片宽高为：300 * 199,支持后缀*.jpg/*.png/*.gif
			<s:hidden name="shopMain.filePath" id="filePath"/>
			<input type="hidden" name="filePathTmp" value="${shopMain.filePath}">
			<s:if test="shopMain.filePath!=null">
				<p id="p_filePath">
					<img alt="店铺展示文件" src="${imagePath}${shopMain.filePath}"  width="300" height="199"/><a href="javascript:void(0);" class="j_delete_filePath" data-shopid="${shopMain.shopId}">删除</a>
				</p>
			</s:if>
		</div>
		<div class="ui-form-item">
			<label class="ui-form-label">商家简介：</label>
			<s:textarea cssClass="ui-input" name="shopMain.introduce" id="introduce" cols="50" rows="4"/>
			<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_introduce_error" style="display: none;">
				<i class="ui-icon ui-icon-error"></i><span id="introduce_error_msg"></span>
			</p>
		</div>
		
		<legend>SEO设置</legend>
		<div class="ui-form-item">
			<label class="ui-form-label">店铺SEO：</label>
			<s:textfield cssClass="ui-input" name="shopMain.shopSeoKey" maxlength="300"/>
		</div>		
		</fieldset>
		<table class="ui-table ui-table-noborder fn-mt20">
			<tbody>
				<tr align="center">
					<th class="col-w-120 fn-text-rt">&nbsp;</th>
					<td align="left">
                        <input id="submitButton" data-shopid='<s:property value="shopMain.shopId"/>'
                               class="ui-button ui-button-success ui-button-lg j_submit_updateShopMain"
                               type="button" value="提交"/>&nbsp;&nbsp;
						<input type="button" class="ui-button ui-button-default ui-button-lg j_shopMain_list"
                               value="返回" />
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<s:form action="shopMainList.action" method="post" id="shopMainListForm" namespace="supplier"></s:form>
