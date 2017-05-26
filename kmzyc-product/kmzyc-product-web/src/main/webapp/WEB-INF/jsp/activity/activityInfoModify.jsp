<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建活动</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link  rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
;
</style>

<script charset="utf-8"  src="/kindeditor/kindeditor.js"></script> 
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"  ></script> 

<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script src="/etc/js/97dater/WdatePicker.js"></script>
<script type="text/javascript" src="/etc/js/activity/activityInfoAdd.js"></script>
<script type="text/javascript" src="/etc/js/activity/activityInfoModify.js"></script>
<script type="text/javascript" src="/etc/js/activity/kindeditor_addActivityIntroduce.js"></script>
<script type="text/javascript" src="/etc/js/activity/kindeditor_addActivityQuestions.js"></script>


<script language='JavaScript' src='/etc/js/dialog-common.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
</head>
<body>
<s:set name="parent_name" value="'活动管理'" scope="request" />
<s:set name="name" value="'修改活动'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/supplierActivity/updateActivity.action" method="POST" enctype="multipart/form-data" id="frm" name='frm'>
	<s:hidden name="activityInfo.activityId"/>
	<table width="95%" class="edit_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C7D3E2"
			style="border-collapse: collapse; font-size: 12px;">
		<tr>
			<th colspan="2" align="left" class="edit_title">修改活动</th>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>活动名称：</th>
			<td>
				<s:textfield name="activityInfo.activityName" cssClass="input_style" maxlength="60" size="60" id="activityName"></s:textfield>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>活动类型：</th>
			<td>
				<s:select list="#request.activityTypeMap" listKey="key" listValue="value" value="<s:property value='activityInfo.activityType' />" id="activityType" disabled="true"></s:select>
				<input type="hidden" name="activityType" value="<s:property value='activityInfo.activityType' />">
			</td>
		</tr>
		<tr id="activityChannlTr" style="display: none;">
			<th align="right" class="eidt_rowTitle">选择渠道：</th>
			<td>
				<s:checkboxlist list="#request.activityChannlMap" name="activityChannls" value="<s:property value='activityInfo.activityChannls' />" ></s:checkboxlist>
			</td>
		</tr>
		<tr id="activityChargeTypeTr">
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>活动费用：</th>
			<td>
			<s:if test="activityInfo.activityType!=3">
				<span id="activityChargeType_1">
					<input type="radio" name="activityChargeType" class="activityChargeTypeClass" <s:if test="activityInfo.chargeType==1">checked="checked"</s:if> value="1"/>
					免费&nbsp;&nbsp;
				</span>
				<span id="activityChargeType_2">
					<input type="radio" name="activityChargeType" class="activityChargeTypeClass" <s:if test="activityInfo.chargeType==2">checked="checked"</s:if> value="2"/>
					固定收费<input type="text" name="fixedCharge" class="input_style" id="fixedCharge" maxlength="7" size="5" onkeyup="javascript:checkInputIntFloat(this);" <s:if test="activityInfo.chargeType!=2">disabled="disabled"</s:if> value="<s:property value='activityInfo.activityCharge.fixedCharge' />" />元&nbsp;&nbsp;
				</span>
				<span id="activityChargeType_3">
					<input type="radio" name="activityChargeType" class="activityChargeTypeClass" <s:if test="activityInfo.chargeType==3">checked="checked"</s:if> value="3"/>
					按推广商品数量收取，<input type="text" name="singleCharge" value="<s:property value='activityInfo.activityCharge.singleCharge' />" <s:if test="activityInfo.chargeType!=3">disabled="disabled"</s:if> class="input_style" id="singleCharge" maxlength="7" size="5" onkeyup="javascript:checkInputIntFloat(this);"/>元/个SKU&nbsp;&nbsp;
				</span>
			</s:if>
			<s:else>
				<span id="activityChargeType_4">
					<input type="radio" name="activityChargeType" class="activityChargeTypeClass" <s:if test="activityInfo.chargeType==4">checked="checked"</s:if> value="4"/>
					按推广佣金比例收取，不低于单价<input type="text" name="commissionRate" value="<fmt:formatNumber value="${activityInfo.activityCharge.commissionRate}" pattern="#0.00" />" <s:if test="activityInfo.chargeType!=4">disabled="disabled"</s:if> class="input_style" id="commissionRate" maxlength="7" size="5" onkeyup="javascript:checkInputIntFloat(this);"/>%
				</span>
			</s:else>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>商家范围：</th>
			<td>
					<input type="radio" name="supplierChoiceType" <s:if test="activityInfo.supplierChoiceType==1">checked="checked"</s:if> value="1"/>
					全部商家&nbsp;&nbsp;
					<input type="radio" name="supplierChoiceType" <s:if test="activityInfo.supplierChoiceType==2">checked="checked"</s:if> value="2"/>
					商家店铺评分≥<input type="text" name="greatEqualPoint" class="input_style" id="greatEqualPoint" <s:if test="activityInfo.supplierChoiceType!=2">disabled="disabled"</s:if> value="<s:property value="activityInfo.activitySupplierScore.greatEqualPoint"/>" size="3" maxlength="4" onkeyup="javascript:checkInputIntFloat(this);"/>分&nbsp;&nbsp;
					<input type="radio" name="supplierChoiceType" <s:if test="activityInfo.supplierChoiceType==3">checked="checked"</s:if> value="3"/>
					指定经营类目&nbsp;<input type="button" value="选择" id="choiceCategory" onclick="categorysSelect()" <s:if test="activityInfo.supplierChoiceType!=3">disabled="disabled"</s:if>/>&nbsp;&nbsp;
					<input type="radio" name="supplierChoiceType" <s:if test="activityInfo.supplierChoiceType==4">checked="checked"</s:if> value="4"/>
					指定入驻商家&nbsp;<input type="button" id="choiceSupplier" value="选择" onclick="shopSelect()" <s:if test="activityInfo.supplierChoiceType!=4">disabled="disabled"</s:if>/>
					</br>
					<!-- （类目、商家）显示数据 -->
					<div id="sonPageValue">
					<s:if test="activityInfo.activityCategorysList!=null && activityInfo.activityCategorysList.size>0">
						<s:iterator value="activityInfo.activityCategorysList">
							<span class="j_personInfo categoryIds<s:property value="categoryId"/>" name="categoryClick" style="display: inline;">
									<div name="categoryInfo" data-name="<s:property value="categoryName"/>" data-id="<s:property value="categoryId"/>" class="j_div" style="position:relative;margin:3px 5px 2px 0;white-space:nowrap;height:15px;line-height: 15px;cursor:pointer;border-radius:17px;border-style:solid;border-width:1px;font-size:14px;padding:2px 19px;border-color:#edb8b8;background-color:#ffeaea;color:#c30!important;display:inline-block;vertical-align:middle;">
									<em style="margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;" unselectable="on">
										<s:property value="categoryName"/>
									</em>
									<a style="position: absolute;right: -2px;top: -1px;display: inline;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;" href="javascript:delOp('categoryIds<s:property value="categoryId"/>');" hidefocus="hidefocus">
									x
									</a>
									</div>
							</span>
							<input type="hidden" id="categoryIds<s:property value="categoryId"/>" name="categoryIds" value="<s:property value="categoryId"/>"/>
						</s:iterator>
					</s:if>
					<s:if test="activityInfo.activitySupplierEntryList!=null && activityInfo.activitySupplierEntryList.size>0">
						<s:iterator value="activityInfo.activitySupplierEntryList">
							<span class="j_personInfo supplierIds<s:property value="supplierId"/>" name="supplierClick" style="display: inline;">
								<div name="supplierInfo" data-name="<s:property value="companyShowName"/>" data-id="<s:property value="supplierId"/>" class="j_div" style="position:relative;margin:3px 5px 2px 0;white-space:nowrap;height:15px;line-height: 15px;cursor:pointer;border-radius:17px;border-style:solid;border-width:1px;font-size:14px;padding:2px 19px;border-color:#edb8b8;background-color:#ffeaea;color:#c30!important;display:inline-block;vertical-align:middle;">
									<em style="margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;" unselectable="on">
										<s:property value="companyShowName"/>
									</em>
									<a style="position: absolute;right: -2px;top: -1px;display: inline;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;" href="javascript:delOp('supplierIds<s:property value="supplierId"/>');" hidefocus="hidefocus">
									x
									</a>
								</div>
							</span>
							<input type="hidden" id="supplierIds<s:property value="supplierId"/>" name="supplierIds" value="<s:property value="supplierId"/>"/>
						</s:iterator>
					</s:if>
					</div>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>品牌范围：</th>
			<td>
				<input type="radio" name="brandChoiceType" <s:if test="activityInfo.brandChoiceType==1">checked="checked"</s:if> value="1"/>
				全部品牌&nbsp;&nbsp;
				<input type="radio" name="brandChoiceType" <s:if test="activityInfo.brandChoiceType==2">checked="checked"</s:if> value="2"/>
				指定品牌&nbsp;<input type="button" id="choiceBrand" value="选择" onclick="brandSelect()" <s:if test="activityInfo.brandChoiceType!=2">disabled="disabled"</s:if>/>
				</br>
				<!-- （品牌）显示数据 -->
				<div id="sonPageBrandValue">
				<s:if test="activityInfo.activityBrandList!=null && activityInfo.activityBrandList.size>0">
					<s:iterator value="activityInfo.activityBrandList">
							<span class="j_personInfo brandIds<s:property value="brandId"/>" name="brandClick" style="display: inline;">
								<div name="brandInfo" data-name="<s:property value="brandName"/>" data-id="<s:property value="brandId"/>" class="j_div" style="position:relative;margin:3px 5px 2px 0;white-space:nowrap;height:15px;line-height: 15px;cursor:pointer;border-radius:17px;border-style:solid;border-width:1px;font-size:14px;padding:2px 19px;border-color:#edb8b8;background-color:#ffeaea;color:#c30!important;display:inline-block;vertical-align:middle;">
									<em style="margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;" unselectable="on">
										<s:property value="brandName"/>
									</em>
									<a style="position: absolute;right: -2px;top: -1px;display: inline;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;" href="javascript:delOp('brandIds<s:property value="brandId"/>');" hidefocus="hidefocus">
										x
									</a>
								</div>
							</span>
							<input type="hidden" id="brandIds<s:property value="brandId"/>" name="brandIds" value="<s:property value="brandId"/>"/>
					</s:iterator>
				</s:if>
				</div>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>商家报名名额限制：</th>
			<td>
				<input type="radio" name="supplierMaximumRadio" <s:if test="activityInfo.supplierMaximum==0">checked="checked"</s:if> value="0" id="supplierMaximumRadio_1" />
				不限制&nbsp;&nbsp;
				<input type="radio" name="supplierMaximumRadio" value="1" <s:if test="activityInfo.supplierMaximum>0">checked="checked"</s:if> id="supplierMaximumRadio_2" />
				限制<input type="text" name="supplierMaximum" id="supplierMaximum" value="<s:if test="activityInfo.supplierMaximum!=0"><s:property value="activityInfo.supplierMaximum"/></s:if>" size="5" maxlength="5" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" <s:if test="activityInfo.supplierMaximum==0">disabled="disabled"</s:if>>个商家
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>活动商品数量限制：</th>
			<td>
				<input type="radio" name="skuMaximumRadio" <s:if test="activityInfo.skuMaximum==0">checked="checked"</s:if> value="0" id="skuMaximumRadio_1" />
				不限制&nbsp;&nbsp;
				<input type="radio" name="skuMaximumRadio" value="1" <s:if test="activityInfo.skuMaximum>0">checked="checked"</s:if> id="skuMaximumRadio_2" />
				限制<input type="text" name="skuMaximum" id="skuMaximum" value="<s:if test="activityInfo.skuMaximum!=0"><s:property value="activityInfo.skuMaximum"/></s:if>" size="5" maxlength="5" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" <s:if test="activityInfo.skuMaximum==0">disabled="disabled"</s:if>>个商品
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>报名起止时间：</th>
			<td>
				<input name="entryStartTime" id="entryStartTime" autocomplete="off"  readonly="readonly"
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					value="<s:date name="activityInfo.entryStartTime" format="yyyy-MM-dd HH:mm:ss"/>">&nbsp;至&nbsp;<input name="entryEndTime" id="entryEndTime" autocomplete="off"  readonly="readonly"
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					value="<s:date name="activityInfo.entryEndTime" format="yyyy-MM-dd HH:mm:ss"/>">
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>活动起止时间：</th>
			<td>
				<input name="activityStartTime" id="activityStartTime" autocomplete="off"
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					value="<s:date name="activityInfo.activityStartTime" format="yyyy-MM-dd HH:mm:ss"/>">&nbsp;至&nbsp;<input name="activityEndTime" id="activityEndTime" autocomplete="off" 
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					value="<s:date name="activityInfo.activityEndTime" format="yyyy-MM-dd HH:mm:ss"/>">		
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">logo图片：</th>
			<td>
				<input type="file" id="logoFile" name="logoFile" onchange="changeLogoPath(this)"/><s:if test="activityInfo.logoPath!=null && activityInfo.logoPath!=''"><a href="<s:property value="imagePath" /><s:property value='activityInfo.logoPath' />" target="_blank">查看图片</a></s:if> (支持上传jpg/png/gif格式的图片，图片大小不超过1024kb)<input
					type="hidden" id="logoPath" name="activityInfo.logoPath" value="<s:property value='activityInfo.logoPath' />" />
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动标签：</th>
			<td>
				<s:iterator value="#request.activityLabelList" var="activityLabelVar">
					<input type="checkbox" name="activityLabels" value="<s:property value="activityLabelVar"/>" <s:if test="activityInfo.activityLabel.contains(#activityLabelVar)">checked="checked"</s:if>/>
					<s:property value="activityLabelVar"/>&nbsp;&nbsp;
				</s:iterator>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动级别：</th>
			<td>
				<input type="radio" name="activityInfo.activityLevel" <s:if test="activityInfo.activityLevel==1">checked="checked"</s:if> value="1"/>
				钻级&nbsp;&nbsp;
				<input type="radio" name="activityInfo.activityLevel" <s:if test="activityInfo.activityLevel==2">checked="checked"</s:if> value="2"/>
				大型&nbsp;&nbsp;
				<input type="radio" name="activityInfo.activityLevel" <s:if test="activityInfo.activityLevel==3">checked="checked"</s:if> value="3"/>
				中型&nbsp;&nbsp;
				<input type="radio" name="activityInfo.activityLevel" <s:if test="activityInfo.activityLevel==4">checked="checked"</s:if> value="4"/>
				小型&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">所属行业：</th>
			<td>
				<s:iterator value="#request.activityIndustryList" var="activityIndustryVar">
					<input type="checkbox" name="activityIndustrys" value="<s:property value="activityIndustryVar"/>" <s:if test="activityInfo.industry.contains(#activityIndustryVar)">checked="checked"</s:if>/>
					<s:property value="activityIndustryVar"/>&nbsp;&nbsp;
				</s:iterator>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">关键字：</th>
			<td>
				<input type="text" id="activitySeo" name="activityInfo.activitySeo" value="<s:property value="activityInfo.activitySeo" />" maxlength="34" size="50"/> 最多可设置5组关键字，每组不超过6个字，关键字之间用英文逗号分割
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>活动简介：</th>
			<td>
				<s:textarea name="activityInfo.activityDesc" id="activityDesc"/>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动说明：</th>
			<td>
				<textarea id="editor_activityIntroduce_id" name="activityInfo.activityIntroduce"  
					style="height:400px;width:1014px;resize:none;"> 
					 <s:property value="activityInfo.activityIntroduce"/>
				</textarea>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动答疑：</th>
			<td>
				<textarea id="editor_activityQuestions_id" name="activityInfo.activityQuestions"  
					style="height:400px;width:1014px;resize:none;"> 
					 <s:property value="activityInfo.activityQuestions"/>
				</textarea>
			</td>
		</tr>
	</table>
	<table>
		<tbody>
			<tr align="center">
				<td align="center"><input id="submitButton" class="saveBtn" type="button"
					value="" onClick="submitModify()" >
					&nbsp;&nbsp;
					<input type="button" class="backBtn" onClick="gotoList()" />
				</td>
			</tr>
		</tbody>
	</table>

	<!-- lazy -->
			<div class="editor_activityIntroduce_change" style="visibility:hidden" ></div>
			<div   style="visibility:hidden">
				<textarea id="editor_activityIntroduce_lazy" name="activityInfo.activityIntroduceLazy" > 
					 <s:property value="activityInfo.activityIntroduceLazy"/>
				</textarea>
			</div>
			
			<div class="editor_activityQuestions_change" style="visibility:hidden" ></div>
			<div   style="visibility:hidden">
				<textarea id="editor_activityQuestions_lazy" name="activityInfo.activityQuestionsLazy" > 
					 <s:property value="activityInfo.activityQuestionsLazy"/>
				</textarea>
			</div>
</s:form>

</html>
