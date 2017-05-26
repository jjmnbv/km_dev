<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<script type="text/javascript" src="/etc/js/activity/kindeditor_addActivityIntroduce.js"></script>
<script type="text/javascript" src="/etc/js/activity/kindeditor_addActivityQuestions.js"></script>


<script language='JavaScript' src='/etc/js/dialog-common.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
</head>
<body>
<s:set name="parent_name" value="'活动管理'" scope="request" />
<s:set name="name" value="'创建活动'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/supplierActivity/addActivity.action" method="POST" enctype="multipart/form-data" id="frm" name='frm'>
	<input type="hidden" id="1" name="categoryIds" value="222"/>
	<input type="hidden" id="1" name="categoryIds" value="333"/>
	<table width="95%" class="edit_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C7D3E2"
			style="border-collapse: collapse; font-size: 12px;">
		<tr>
			<th colspan="2" align="left" class="edit_title">创建活动</th>
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
				<s:select list="#request.activityTypeMap" headerKey="" headerValue="请选择"  name="activityType" id="activityType"></s:select>
			</td>
		</tr>
		<tr id="activityChannlTr" style="display: none;">
			<th align="right" class="eidt_rowTitle">选择渠道：</th>
			<td>
				<s:checkboxlist list="#request.activityChannlMap" name="activityChannls"></s:checkboxlist>
			</td>
		</tr>
		<tr id="activityChargeTypeTr">
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>活动费用：</th>
			<td>
				<span id="activityChargeType_1">
					<input type="radio" name="activityChargeType" checked="checked" class="activityChargeTypeClass" value="1"/>
					免费&nbsp;&nbsp;
				</span>
				<span id="activityChargeType_2">
					<input type="radio" name="activityChargeType" class="activityChargeTypeClass" value="2"/>
					固定收费<s:textfield name="fixedCharge" cssClass="input_style" id="fixedCharge" maxlength="7" size="5" onkeyup="javascript:checkInputIntFloat(this);" disabled="true"/>元&nbsp;&nbsp;
				</span>
				<span id="activityChargeType_3">
					<input type="radio" name="activityChargeType" class="activityChargeTypeClass" value="3"/>
					按推广商品数量收取，<s:textfield name="singleCharge" cssClass="input_style" id="singleCharge" maxlength="7" size="5" onkeyup="javascript:checkInputIntFloat(this);" disabled="true"/>元/个SKU&nbsp;&nbsp;
				</span>
				<span id="activityChargeType_4">
					<input type="radio" name="activityChargeType" class="activityChargeTypeClass" value="4"/>
					按推广佣金比例收取，不低于活动价<s:textfield name="commissionRate" cssClass="input_style" id="commissionRate" size="5" maxlength="7" onkeyup="javascript:checkInputIntFloat(this);"  disabled="true"/>%
				</span>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>商家范围：</th>
			<td>
				<input type="radio" name="supplierChoiceType" checked="checked" id="" value="1"/>
				全部商家&nbsp;&nbsp;
				<input type="radio" name="supplierChoiceType" id="" value="2"/>
				商家店铺评分≥<s:textfield name="greatEqualPoint" cssClass="input_style" id="greatEqualPoint" size="3" maxlength="4" onkeyup="javascript:checkInputIntFloat(this);" disabled="true"/>分&nbsp;&nbsp;
				<input type="radio" name="supplierChoiceType" id="" value="3"/>
				指定经营类目&nbsp;<input type="button" value="选择" id="choiceCategory" onclick="categorysSelect()" disabled="disabled"/>&nbsp;&nbsp;
				<input type="radio" name="supplierChoiceType" id="" value="4"/>
				指定入驻商家&nbsp;<input type="button" id="choiceSupplier" value="选择" onclick="shopSelect()" disabled="disabled"/>
				</br>
				<!-- （类目、商家）显示数据 -->
				<div id="sonPageValue"></div>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>品牌范围：</th>
			<td>
				<input type="radio" name="brandChoiceType" checked="checked" id="" value="1"/>
				全部品牌&nbsp;&nbsp;
				<input type="radio" name="brandChoiceType" id="" value="2"/>
				指定品牌&nbsp;<input type="button" id="choiceBrand" value="选择" onclick="brandSelect()" disabled="disabled"/>
				</br>
				<!-- （品牌）显示数据 -->
				<div id="sonPageBrandValue"></div>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>商家报名名额限制：</th>
			<td>
				<input type="radio" name="supplierMaximumRadio" checked="checked" value="0" id="supplierMaximumRadio_1" />
				不限制&nbsp;&nbsp;
				<input type="radio" name="supplierMaximumRadio" value="1" id="supplierMaximumRadio_2" />
				限制<input type="text" name="supplierMaximum" id="supplierMaximum" size="5" maxlength="5" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" disabled="disabled"/>个商家
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>活动商品数量限制：</th>
			<td>
				<input type="radio" name="skuMaximumRadio" checked="checked" value="0" id="skuMaximumRadio_1" />
				不限制&nbsp;&nbsp;
				<input type="radio" name="skuMaximumRadio" value="1" id="skuMaximumRadio_2" />
				限制<input type="text" name="skuMaximum" id="skuMaximum" size="5" maxlength="5" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" disabled="disabled"/>个商品
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>报名起止时间：</th>
			<td>
				<input name="entryStartTime" id="entryStartTime" autocomplete="off" readonly="readonly"
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					value="<s:property value='entryStartTime' />">&nbsp;至&nbsp;<input name="entryEndTime" id="entryEndTime" autocomplete="off"  readonly="readonly"
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					value="<s:property value='entryEndTime' />">
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>活动起止时间：</th>
			<td>
				<input name="activityStartTime" id="activityStartTime" autocomplete="off" readonly="readonly"
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					value="<s:property value='activityStartTime' />">&nbsp;至&nbsp;<input name="activityEndTime" id="activityEndTime" autocomplete="off" readonly="readonly"
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					value="<s:property value='activityEndTime' />">		
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">logo图片：</th>
			<td>
				<input type="file" id="logoFile" name="logoFile" onchange="changeLogoPath(this)"/> (支持上传jpg/png/gif格式的图片，图片大小不超过1024kb)<input
					type="hidden" id="logoPath" name="activityInfo.logoPath" value="" />
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动标签：</th>
			<td>
				<s:checkboxlist list="#request.activityLabelList" name="activityLabels"></s:checkboxlist>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动级别：</th>
			<td>
				<input type="radio" name="activityInfo.activityLevel" value="1"/>
				钻级&nbsp;&nbsp;
				<input type="radio" name="activityInfo.activityLevel" value="2"/>
				大型&nbsp;&nbsp;
				<input type="radio" name="activityInfo.activityLevel" value="3"/>
				中型&nbsp;&nbsp;
				<input type="radio" name="activityInfo.activityLevel" value="4"/>
				小型&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">所属行业：</th>
			<td>
				<s:checkboxlist list="#request.activityIndustryList" name="activityIndustrys"></s:checkboxlist>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">关键字：</th>
			<td>
				<input type="text" id="activitySeo" name="activityInfo.activitySeo" maxlength="34" size="50"/> 最多可设置5组关键字，每组不超过6个字，关键字之间用英文逗号分割
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
					value="" onClick="submitOperation()" >
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
