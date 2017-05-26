<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/addproduct.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<link href="/etc/autocomplete/autocompletestyles.css" type="text/css" rel="stylesheet">
<link  rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script charset="utf-8"  src="/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"  ></script> 
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/product_add.js"></script>
<script type="text/javascript" src="/etc/js/kindeditor_add.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.mockjax.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/etc/autocomplete/demo.js"></script>
<script type="text/javascript" src="/etc/js/97dater/WdatePicker.js"></script>
<title>产品发布</title>
</head>
<body>
<iframe name="theID" style="display: none;"></iframe>  
<div style="position:absolute;align:center;top:20px;left:30px">
	<s:form action="/app/productAdd.action" enctype="multipart/form-data"
		method="POST" id="frm" name='frm' target="theID">
		<ul id="tabs">
			<li class="visit"><b>基本信息</b></li>
			<li><b>基本属性</b></li>
			<li><b>SKU规格</b></li>
			<li><b>自定义属性</b></li>
			<li><b>运营属性</b></li>
			<li><b>商品介绍</b></li>
			<s:if test="product.productType != 0">
				<li><b>资质文件</b></li>
			</s:if>
		</ul>
	
		<div id="content">
			<div class="ct">
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<!-- error message -->
				<s:if test="rtnMessage != null">
					<tr>
						<td colspan="2" align="center"><font color="red"><s:property
							value='rtnMessage' /></font></td>
					</tr>
				</s:if>
				<tr>
					<th width="30%" align="right" class="eidt_rowTitle">产品类别：</th>
					<td width="70%">
				        <s:hidden name="product.categoryId"/>
				        ${bCategoryName} > ${mCategoryName} > <s:property value='product.categoryName'/>
				        <s:hidden name="product.categoryName" id="categoryName"/>
					</td>
				</tr>
				<tr>
					<th width="30%" align="right" class="eidt_rowTitle">产品类型：</th>
					<td width="70%">
				        <s:hidden name="product.productType"/>
				        <s:property value="#request.productTypeMap[product.productType]"/>
					</td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle"><font color="red">*</font>品牌：</th>
					<td>
						<s:hidden name="product.brandName" id="brandName" />
						<s:hidden name="product.brandId" id="brandId" />
				        <input type="text" id="autocomplete" size="32" />
					</td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle"><font color="red">*</font>商家：</th>
					<td>
						<s:hidden id="shopCodeName" />
						<s:hidden name="product.shopCode" id="shopCode" />
						<input type="text" id="autocomplete_forSuppliers" size="32" />
					</td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle"><font color="red">*</font>产品名称：</th>
					<td><label> <input class="{required:true,unusualChar:true}" name="product.productName"
						id="productName" size="80" maxlength="50" value="<s:property value="product.name" />" /> </label></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle"><font color="red">*</font>产品主标题：</th>
					<td>
						<label><input class="{required:true,unusualChar:true}" name="product.productTitle"
							id="productTitle" size="100" maxlength="160" value="<s:property value="product.productTitle" />" /></label></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">产品副标题：</th>
					<td><s:textfield name="product.productSubtitle" id="productSubtitle" size="100" maxlength="160" /></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">关键词(seo)：</th>
					<td><s:textfield name="product.keyword" id="keyword" size="100" maxlength="200" /></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">包装清单：</th>
					<td><s:textfield name="product.packListing" id="packListing" size="32" maxlength="32" /></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">ERP产品编号：</th>
					<td><s:textfield name="product.erpProductCode" id="erpProductCode" size="32" maxlength="32" /></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">批准文类型：</th>
					<td>
						<s:select list="#request.approvalTypeMap"  name="product.approvalType" id="approvalType"></s:select>
					</td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle"><span id="approvalNoText">批准文号</span>：</th>
					<td>
						<s:textfield name="product.approvalNo" id="approvalNo" size="32" maxlength="32" />
                        <input type="hidden" id="approvalNoHidden" value="" />
					</td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle"><span id="productDescText">备注</span>：</th>
					<td>
						<label><s:textarea name="product.productDesc" id="productDesc" rows="8" cols="45"/></label>
                        <input type="hidden" id="productDescHidden" />
					</td>
				</tr>
			</table>
			</div>
			<div class="ct" id="ct_cate">
				<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
					<s:if test="product.categoryAttrList != null && product.categoryAttrList.size()>0">
					<s:iterator value="product.categoryAttrList" status="st">
					<s:if test="isSku==0">
					<tr> 
						<th width="30%" align="right" class="eidt_rowTitle">
						<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].categoryAttrId" value="<s:property value='categoryAttrId'/>"/>
						<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].isSku" value="<s:property value='isSku'/>"/>
						<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].isReq" value="<s:property value='isReq'/>"/>
						<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].inputType" value="<s:property value='inputType'/>"/>
						<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].isNav" value="<s:property value='isNav'/>"/>
						<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].sortno" value="<s:property value='sortno'/>"/>
						<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].categoryAttrName" value="<s:property value='categoryAttrName'/>"/>
						<s:if test="isReq==1"><font color="red">*</font></s:if><s:property value='categoryAttrName'/>：
						</th>
						<td width="70%" align="left"> 
							<s:if test="inputType==0">
								<input id="<s:property value='#st.index'/>" maxlength="32" name="product.categoryAttrList[<s:property value='#st.index'/>].categoryAttrValues" class="input_style<s:if test="isReq==1"> {required:true}</s:if>" size="40" type="text" value="<s:property value='categoryAttrValue'/>" />  
							</s:if>
							<s:elseif test="inputType==1">
								<s:if test="categoryAttrValueList != null && categoryAttrValueList.size()>0" >
									<s:radio list="categoryAttrValueList"  listKey="categoryAttrValueId" listValue="categoryAttrValue" name="product.categoryAttrList[%{#st.index}].categoryAttrValues" value="categoryAttrValue"/>
								</s:if>
							</s:elseif>
							<s:elseif test="inputType==2">
								<s:if test="categoryAttrValueList != null && categoryAttrValueList.size()>0" >
									<s:checkboxlist list="categoryAttrValueList" listKey="categoryAttrValueId" listValue="categoryAttrValue" name="product.categoryAttrList[%{#st.index}].categoryAttrValues" value="checkBoxIds" ></s:checkboxlist>
								</s:if>
							</s:elseif>
							<s:elseif test="inputType==3">
								<s:if test="categoryAttrValueList != null && categoryAttrValueList.size()>0" >
									<s:select name="product.categoryAttrList[%{#st.index}].categoryAttrValues" list="categoryAttrValueList" listKey="categoryAttrValueId" listValue="categoryAttrValue" value="categoryAttrValue"></s:select>
								</s:if>
							</s:elseif>
						</td>
					</tr>
					</s:if>
					</s:iterator>
					</s:if>
				</table>
			</div>
			<div class="ct" id="ct_sku">
				<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
					<s:if test="product.categoryAttrList != null && product.categoryAttrList.size()>0">
					<s:iterator value="product.categoryAttrList" status="st">
					<s:if test="isSku==1">
						<tr class="isCheckBoxListTr"> 
							<th width="30%" align="right" class="eidt_rowTitle">
							<input type="hidden" class="input_style"  name="product.categoryAttrList[<s:property value='#st.index'/>].categoryAttrId" value="<s:property value='categoryAttrId'/>"/>
							<input type="hidden" class="input_style" name="product.categoryAttrList[<s:property value='#st.index'/>].isSku" value="<s:property value='isSku'/>"/>
							<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].isReq" value="<s:property value='isReq'/>"/>
							<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].inputType" value="<s:property value='inputType'/>"/>
							<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].isNav" value="<s:property value='isNav'/>"/>
							<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].sortno" value="<s:property value='sortno'/>"/>
							<input type="hidden" name="product.categoryAttrList[<s:property value='#st.index'/>].categoryAttrName" value="<s:property value='categoryAttrName'/>"/>
							<input type="hidden" class="attrNameHide" value="<s:property value='categoryAttrName'/>" >
							<s:if test="isReq==1"><font color="red">*</font></s:if><s:property value='categoryAttrName'/>：
							</th>
							<td width="70%" align="left"> 
								<s:if test="inputType==2">
									<s:if test="categoryAttrValueList != null && categoryAttrValueList.size()>0" >
										<s:checkboxlist onclick="createProductSku();" list="categoryAttrValueList" listKey="categoryAttrValueId" listValue="categoryAttrValue" name="product.categoryAttrList[%{#st.index}].categoryAttrValues" value="checkBoxIds"></s:checkboxlist>
										<%--&nbsp;&nbsp;
										<a href="javascript:void(0);" onclick="addSkuValue(this.parentNode);">添加</a> --%>
									</s:if>
								</s:if>
							</td>
						</tr>
					</s:if>
					</s:iterator>
					</s:if>
				</table>
				
				<table id="skuDataTable" width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				</table>
			</div>
			<div class="ct" id="ct_dyna" >
				<input id="isOTC" type="hidden" value="<s:property value='#request.isOTC' />" >
				<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse" id="AttrValueTR">
					<tr> 
						<th colspan="2" align="left" class="modeltitle"><INPUT id="addAttrValue" class="btngreen" TYPE="button" value="添加动态属性"></th>
					</tr>
					<s:if test="#request.isOTC">
						<tr class="isOtcTr">
							<th align="right">
								通用名称：
								<input type="hidden" name="product.productAttrDraftList[0].productAttrName" value="通用名称"/>
								<input type="hidden" name="product.productAttrDraftList[0].sortno" value="1"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[0].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								商品名称：
								<input type="hidden" name="product.productAttrDraftList[1].productAttrName" value="商品名称" />
								<input type="hidden" name="product.productAttrDraftList[1].sortno" value="2"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[1].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								成份：
								<input type="hidden" name="product.productAttrDraftList[2].productAttrName" value="成份" />
								<input type="hidden" name="product.productAttrDraftList[2].sortno" value="3"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[2].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								规格：
								<input type="hidden" name="product.productAttrDraftList[3].productAttrName" value="规格"/>
								<input type="hidden" name="product.productAttrDraftList[3].sortno" value="4"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[3].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								作用类别：
								<input type="hidden" name="product.productAttrDraftList[4].productAttrName" value="作用类别" />
								<input type="hidden" name="product.productAttrDraftList[4].sortno" value="5"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[4].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								适应症/功能主治：
								<input type="hidden" name="product.productAttrDraftList[5].productAttrName" value="适应症/功能主治" />
								<input type="hidden" name="product.productAttrDraftList[5].sortno" value="6"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[5].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								用法用量：
								<input type="hidden" name="product.productAttrDraftList[6].productAttrName" value="用法用量"/>
								<input type="hidden" name="product.productAttrDraftList[6].sortno" value="7"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[6].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								用法用量2：
								<input type="hidden" name="product.productAttrDraftList[7].productAttrName" value="用法用量2"/>
								<input type="hidden" name="product.productAttrDraftList[7].sortno" value="7"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[7].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								不良反应：
								<input type="hidden" name="product.productAttrDraftList[8].productAttrName" value="不良反应" />
								<input type="hidden" name="product.productAttrDraftList[8].sortno" value="8"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[8].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								不良反应2：
								<input type="hidden" name="product.productAttrDraftList[9].productAttrName" value="不良反应2" />
								<input type="hidden" name="product.productAttrDraftList[9].sortno" value="8"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[9].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								不良反应3：
								<input type="hidden" name="product.productAttrDraftList[10].productAttrName" value="不良反应3" />
								<input type="hidden" name="product.productAttrDraftList[10].sortno" value="8"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[10].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								禁忌：
								<input type="hidden" name="product.productAttrDraftList[11].productAttrName" value="禁忌" />
								<input type="hidden" name="product.productAttrDraftList[11].sortno" value="9"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[11].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								禁忌2：
								<input type="hidden" name="product.productAttrDraftList[12].productAttrName" value="禁忌2" />
								<input type="hidden" name="product.productAttrDraftList[12].sortno" value="9"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[12].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								禁忌3：
								<input type="hidden" name="product.productAttrDraftList[13].productAttrName" value="禁忌3" />
								<input type="hidden" name="product.productAttrDraftList[13].sortno" value="9"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[13].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								注意事项：
								<input type="hidden" name="product.productAttrDraftList[14].productAttrName" value="注意事项" />
								<input type="hidden" name="product.productAttrDraftList[14].sortno" value="10"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[14].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								注意事项2：
								<input type="hidden" name="product.productAttrDraftList[15].productAttrName" value="注意事项2" />
								<input type="hidden" name="product.productAttrDraftList[15].sortno" value="10"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[15].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								注意事项3：
								<input type="hidden" name="product.productAttrDraftList[16].productAttrName" value="注意事项3" />
								<input type="hidden" name="product.productAttrDraftList[16].sortno" value="10"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[16].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								药物相互作用：
								<input type="hidden" name="product.productAttrDraftList[17].productAttrName" value="药物相互作用"/>
								<input type="hidden" name="product.productAttrDraftList[17].sortno" value="11"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[17].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								药物相互作用2：
								<input type="hidden" name="product.productAttrDraftList[18].productAttrName" value="药物相互作用2"/>
								<input type="hidden" name="product.productAttrDraftList[18].sortno" value="11"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[18].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								药理作用：
								<input type="hidden" name="product.productAttrDraftList[19].productAttrName" value="药理作用" />
								<input type="hidden" name="product.productAttrDraftList[19].sortno" value="12"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[19].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								药理作用2：
								<input type="hidden" name="product.productAttrDraftList[20].productAttrName" value="药理作用2" />
								<input type="hidden" name="product.productAttrDraftList[20].sortno" value="12"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[20].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								批准文号：
								<input type="hidden" name="product.productAttrDraftList[21].productAttrName" value="批准文号" />
								<input type="hidden" name="product.productAttrDraftList[21].sortno" value="13"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[21].productAttrValue"></textarea>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								生产企业：
								<input type="hidden" name="product.productAttrDraftList[22].productAttrName" value="生产企业" />
								<input type="hidden" name="product.productAttrDraftList[22].sortno" value="14"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[22].productAttrValue"></textarea>
							</td>
						</tr>
					</s:if>
					<s:if test="product.productAttrDraftList != null && product.productAttrDraftList.size()>0">
					<s:iterator value="product.productAttrDraftList">
					<tr>
						<th align="right"><font color="red">*</font><input type="text" style="color:#000" name="product.productAttrDraftList.productAttrName" maxlength="20" value="" onClick="javascript:if(this.value=='属性名') {this.value='';this.style.color='#000'}" onBlur="javascript:if(this.value==''){this.value='属性名';this.style.color='#999'} "/></th>
						<td align="left"><input type="text" style="color:#000" name="product.productAttrDraftList.productAttrValue" maxlength="300" value="" onClick="javascript:if(this.value=='属性值') {this.value='';this.style.color='#000'}" onBlur="javascript:if(this.value==''){this.value='属性值';this.style.color='#999'} "/>
						<a href="#" onClick="delAttrValue(this);">&nbsp;删除&nbsp;</a></td>
					</tr>
					</s:iterator>
					</s:if>
				</table>
			</div>
			<div class="ct">
				<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
					<s:if test="operationAttrList != null && operationAttrList.size()>0">
						<s:if test='operationAttrList.size() % 3 == 0'>
							<s:set name="sizes" value="operationAttrList.size() / 3" ></s:set>
						</s:if>
						<s:else>
							<s:set name="sizes" value="operationAttrList.size() / 3 + 1" ></s:set>
						</s:else>
						<s:set name="index" value="0" ></s:set>
						<s:iterator begin="1" end="#sizes" status="st" >
							<tr> 
								<s:iterator begin="0" end="2" >
									<s:if test="operationAttrList.size() > #index ">
										<td align="left"><input type="checkbox" name="product.operationAttrIds" value="<s:property value='operationAttrList.get(#index).operationAttrId'/>"/> &nbsp;&nbsp;<s:property value='operationAttrList.get(#index).operationAttrName'/></td>
										<s:set name="index" value="%{#index + 1}"></s:set>
									</s:if>
									<s:else>
										<td>&nbsp;</td>
									</s:else>
								</s:iterator>
							</tr>
						</s:iterator>
					</s:if>
				</table>
			</div>
			<div class="ct">
				<textarea id="editor_id" name="product.introduce"  
					style="height:400px;width:1014px;resize:none;"> 
					 <s:property value="product.introduce"/>
				</textarea>
			</div>
			<s:if test="product.productType != 0">
                <div class="ct">
                    <table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse" id="AttrValueTR">
                        <s:iterator value="#request.certificateTypeMAP" var="map" >
                            <tr>
                                <th align="right" class="eidt_rowTitle" width="30%"><s:property value="#map.value"/>：</th>
                                <td>
                                    <input type="file" name="certificateFiles" data-mapValue="<s:property value="#map.value"/>" data-mapKey="<s:property value="#map.key"/>" />
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
			</s:if>
		</div>
		
		<br />
		<table>
			<tbody>
				<tr align="center">
					<td align="center">
                        <input id="submitButton"  class="saveBtn" type="button" onClick="submitOperation();"/>&nbsp;&nbsp;
						<input type="button" class="backBtn" onClick="gotoList()" />
					</td>
				</tr>
			</tbody>
		</table>
		
		<!-- lazy -->
        <div class="editor_change" style="visibility:hidden" ></div>
        <div style="visibility:hidden">
            <textarea id="editor_lazy" name="product.introduceLazy" >
                <s:property value="product.introduceLazy"/>
            </textarea>
        </div>
	</s:form>
</div>
</body>
</html>