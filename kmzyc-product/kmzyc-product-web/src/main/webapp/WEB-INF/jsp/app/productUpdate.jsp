<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/addproduct.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<link href="/etc/autocomplete/autocompletestyles.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
<script language='JavaScript' src="/etc/js/dialog-common.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<script charset="utf-8"  src="/kindeditor/kindeditor.js"></script> 
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"  ></script> 
<script type="text/javascript" src="/etc/js/97dater/WdatePicker.js"></script>
<script type="text/javascript" src="/etc/js/product/product_update.js"></script>
<script type="text/javascript" src="/etc/js/kindeditor_add.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.mockjax.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/etc/autocomplete/demo.js"></script>
<title>产品发布</title>
</head>
<body>
<div style="position:absolute;align:center;top:20px;left:30px">
	<s:form action="/app/productUpdateToAddDraft.action" method="POST" id="frm" name='frm' enctype="multipart/form-data" >
<%-- 	<s:token/> --%>
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
            <input type="hidden" name="product.productId" id="productId" value="<s:property value="product.id" />" />
            <s:hidden name="product.productType" ></s:hidden>
            <s:hidden name="product.shopCode" ></s:hidden>
            <table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
                <tr>
                    <th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>产品类别：</th>
                    <td width="80%">
                        <s:select list="#request.categoryList"
                            name="product.bCategoryId" id="categoryId1" listKey="categoryId" listValue="categoryName"
                            headerKey="" headerValue="--一级类目--"
                            onchange="change2('categoryId1','categoryId2');"></s:select>
                            <s:select list="#request.mCategoryList"
                            name="product.mCategoryId" id="categoryId2"  listKey="categoryId" listValue="categoryName"
                            headerKey="" headerValue="--二级类目--"
                            onchange="change2('categoryId2','categoryId3');"></s:select>
                            <s:select list="#request.sCategoryList" id="categoryId3"
                            headerKey="" headerValue="--三级类目--"
                            name="product.categoryId"  listKey="categoryId" listValue="categoryName"></s:select>
                    </td>
                </tr>
                <tr>
                    <th width="30%" align="right" class="eidt_rowTitle">产品类型：</th>
                    <td width="70%">
                        <s:property value="#request.productTypeMap[product.productType]"/>
                    </td>
                </tr>

                <tr>
                    <th align="right" class="eidt_rowTitle"><font color="red">*</font>品牌：</th>
                    <td>
                        <%--优化需求,针对已上架的商品放开对品牌的修改--%>
                        <s:hidden name="product.brandName" id="brandName" />
                        <s:hidden name="product.brandId" id="brandId" />
                        <input type="text" id="autocomplete" value="<s:property value="product.prodBrand.brandName" />" size="32" />

                    </td>
                </tr>
                <tr>
                    <th align="right" class="eidt_rowTitle">产品编码：</th>
                    <td>
                    <s:property value="product.productNo" />
                    <s:hidden name="product.productNo"/>
                    </td>
                </tr>
                <tr>
                    <th align="right" class="eidt_rowTitle">商家：</th>
                    <td>
                        <s:if test="product.merchantName==null">
                            康美
                        </s:if>
                        <s:else>
                            <s:property value="product.merchantName" />
                        </s:else>
                    </td>
                </tr>
                <tr>
                    <th align="right" class="eidt_rowTitle"><font color="red">*</font>产品名称：</th>
                    <td><label> <input class="{required:true}" name="product.productName"
                    id="productName" size="80" maxlength="50" value="<s:property value="product.name" />" /></label></td>
                </tr>
                <tr>
                    <th align="right" class="eidt_rowTitle"><font color="red">*</font>产品主标题：</th>
                    <td>
                    <label><input class="{required:true}" name="product.productTitle"
                        id="productTitle" size="100" maxlength="160" value="<s:property value="product.productTitle" />" /></label></td>
                </tr>
                <tr>
                    <th align="right" class="eidt_rowTitle">产品副标题：</th>
                    <td><s:textfield name="product.productSubtitle" id="productSubtitle"  cssClass="input_style" size="100" maxlength="160" /></td>
                </tr>
                <tr>
                    <th align="right" class="eidt_rowTitle">关键词(seo)：</th>
                    <td><s:textfield name="product.keyword" id="keyword"  cssClass="input_style" size="100" maxlength="200" /></td>
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
                        <s:select list="#request.approvalTypeMap" dataType="Require" msg="批准文类型必须选择"  name="product.approvalType" id="approvalType"></s:select>
                    </td>
                </tr>
                <tr>
                    <th align="right" class="eidt_rowTitle"><span id="approvalNoText">批准文号</span>：</th>
                    <td>
                        <s:textfield name="product.approvalNo" id="approvalNo" size="32" maxlength="32" />
                        <input type="hidden" id="approvalNoHidden" value="<s:property value="product.approvalNo" />" />
                    </td>
                </tr>
                <tr>
                    <th align="right" class="eidt_rowTitle"><span id="productDescText">备注</span>：</th>
                    <td>
                        <label><s:textarea name="product.productDesc" id="productDesc" rows="8" cols="45"/></label>
                        <input type="hidden" id="productDescHidden" value="<s:property value="product.productDesc" />" />
                    </td>
                </tr>
            </table>
            <br />
		</div>
		<div class="ct" id="ct_cate">
            <s:set name="i" value="0" ></s:set>
            <s:set name="categoryAttrList" value="product.productAttrList.{?#this.productAttrType==1 && #this.isSku==0}"></s:set>
            <s:if test="#categoryAttrList != null && #categoryAttrList.size()>0">
                <table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
                    <s:iterator value="#categoryAttrList" status="st">
                    <tr>
                        <th width="30%" align="right" class="eidt_rowTitle">
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
                        <s:if test="isReq==1"><font color="red">*</font></s:if><s:property value='productAttrName'/>：
                        </th>
                        <td width="70%" align="left">
                            <s:if test="inputType==0">
                                <input id="<s:property value='#i'/>" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue" <s:if test="isReq==1">class="{required:true}"</s:if> size="40" type="text" value="<s:property value='productAttrValue'/>" />
                            </s:if>
                            <s:elseif test="inputType==1">
                                <s:if test="categoryAttrValueList != null && categoryAttrValueList.size()>0" >
                                    <s:radio list="categoryAttrValueList" listKey="categoryAttrValueId" listValue="categoryAttrValue" name="product.productAttrDraftList[%{#i}].productAttrValue" value="productAttrValue"/>
                                </s:if>
                            </s:elseif>
                            <s:elseif test="inputType==2">
                                <s:if test="categoryAttrValueList != null && categoryAttrValueList.size()>0" >
                                    <s:checkboxlist list="categoryAttrValueList" listKey="categoryAttrValueId" listValue="categoryAttrValue" name="product.productAttrDraftList[%{#i}].productAttrValues" value="checkBoxIds"></s:checkboxlist>
                                </s:if>
                            </s:elseif>
                            <s:elseif test="inputType==3">
                                <s:if test="categoryAttrValueList != null && categoryAttrValueList.size()>0" >
                                    <s:select name="product.productAttrDraftList[%{#i}].productAttrValue" list="categoryAttrValueList" listKey="categoryAttrValueId" listValue="categoryAttrValue" value="productAttrValue"></s:select>
                                </s:if>
                            </s:elseif>
                        </td>
                    </tr>
                    <s:set name="i" value="#i + 1" ></s:set>
                    </s:iterator>
                </table>
                <br />
            </s:if>
		</div>
		<div class="ct" id="ct_sku">
		<s:set name="skuAttrList" value="product.productAttrList.{?#this.productAttrType==1 && #this.isSku==1}"></s:set> 
		<s:if test="#skuAttrList != null && #skuAttrList.size()>0">
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<s:iterator value="#skuAttrList" status="st">
				<tr class="isCheckBoxListTr"> 
					<th width="30%" align="right" class="eidt_rowTitle">
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
					<s:if test="isReq==1"><font color="red">*</font></s:if><s:property value='productAttrName'/>：
					</th>
					<td width="70%" align="left"> 
						<s:checkboxlist list="categoryAttrValueList" disabled="true" listKey="categoryAttrValueId" listValue="categoryAttrValue" name="product.productAttrDraftList[%{#i}].productAttrValues" value="checkBoxIds"></s:checkboxlist>
						<s:if test="#request.skuNewAttrList.containsKey(relateAttrId)">
							<s:checkboxlist cssClass="newAttr" list="#request.skuNewAttrList.get(relateAttrId)" listKey="newAttr" listValue="newAttr" name="newAttr_%{#s.index}"></s:checkboxlist>
						</s:if>
						<%--&nbsp;&nbsp;
						<a href="javascript:void(0);" onclick="addSkuValue(this.parentNode);">添加</a>--%>
					
					</td>
				</tr>
				<s:set name="i" value="#i + 1" ></s:set>
				</s:iterator>
			</table>
		</s:if>
			<table id="skuDataTable" width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr id="firstSkuViewTr">
					<th align="center">
						SKU编码
					</th>
					<s:iterator value="product.productSkus[0].productSkuAttrList" var="sku" status="st" >
						<th align="center">
							<s:property value='#sku.categoryAttrName' escape="false" />
						</th>
					</s:iterator>
					<th align="center">
						操作
					</th>
					<th align="center">
						状态
					</th>
				</tr>
				<s:iterator value="product.productSkus" var="sku" status="st" >
					<tr class='oldSkuDataTr'>
						<s:set name="oldSkus" value="''" ></s:set>
						<td align="center">
							<s:property value="#sku.productSkuCode" />
						</td>
						<s:iterator value="#sku.productSkuAttrList" var="attr" status="st" >
							<s:if test="newAttr != null">
								<s:set name="oldSku" value="categoryAttrId + ':' + categoryAttrName + ':' + '@' + categoryAttrValue" ></s:set>
							</s:if>
							<s:else>
								<s:set name="oldSku" value="categoryAttrId + ':' + categoryAttrName + ':' + categoryAttrValueId" ></s:set>
							</s:else>
							<td align="center">
								<s:property value="categoryAttrValue" />
							</td>
							<s:set name="oldSkus" value="#oldSkus + #oldSku" ></s:set>
							<s:if test="#st.count != #sku.productSkuAttrList.size()">
								<s:set name="oldSkus" value="#oldSkus +','" ></s:set>
							</s:if>
						</s:iterator>
						<input type='hidden' name='oldskuCheckedId' value='<s:property value="#oldSkus" />' />
						<input type="hidden" name="oldSkuCode" value="<s:property value="#sku.productSkuCode" />" />
						<td align='center'>
							<!--
								<input type='button' value='删除' onclick='removeSkuTr(this.parentNode.parentNode,<s:property value="productSkuId" />);' class='btnStyle' />
							-->
							<input type="button" class="btnStyle" value="编辑图片" onClick="openSkuListDialog(<s:property value="#sku.productSkuId" />);" />
						</td>
						<td align="center">
							<input type="hidden" name="product.productSkuDrafts[<s:property value='#st.index'/>].productSkuId" value="<s:property value="#sku.productSkuId" />" >
							<s:radio list="#{'0':'无效','1':'有效'}" listKey="key" listValue="value" name="product.productSkuDrafts[%{#st.index}].status" value="#sku.status" ></s:radio>						
						</td>
					</tr>
				</s:iterator>
			</table>
			<br />
			
		</div>
		<div class="ct" id="ct_dyna" >
		<input id="isOTC" type="hidden" value="<s:property value='#request.isOTC' />" >
		<s:set name="definitionAttrList" value="product.productAttrList.{?#this.productAttrType==2}"></s:set>
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr id="AttrValueTR"> 
					<th colspan="2" align="left" class="modeltitle"><INPUT id="addAttrValue" class=" btn-custom btngreen" type="button" value="添加动态属性"></th>
				</tr>
				<s:if test="#request.isOTC">
					<s:set name="isOTCAttr" value="true" ></s:set>
				</s:if>
				<s:if test="#definitionAttrList != null && #definitionAttrList.size()>0">
				<s:iterator value="#definitionAttrList" status="s">
					<s:if test="#request.isOTC">
						<s:if test='productAttrName == "通用名称" || productAttrName == "商品名称" ||
										productAttrName == "成份" || productAttrName == "规格" ||
										productAttrName == "作用类别" || productAttrName == "适应症/功能主治" ||
										productAttrName == "用法用量" || productAttrName == "用法用量2" || 
										productAttrName == "不良反应" || productAttrName == "不良反应2" || productAttrName == "不良反应3" ||
										productAttrName == "禁忌" || productAttrName == "禁忌2" || productAttrName == "禁忌3" ||
										productAttrName == "注意事项" || productAttrName == "注意事项2" || productAttrName == "注意事项3" ||
										productAttrName == "药物相互作用" || productAttrName == "药物相互作用2" || 
										productAttrName == "药理作用" || productAttrName == "药理作用2" || 
										productAttrName == "批准文号" || productAttrName == "生产企业" '>
							<tr class="isOtcTr">
								<th align="right">
									<s:set name="isOTCAttr" value="false" ></s:set>
									<s:property value="productAttrName"/>：
									<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="<s:property value='productAttrName'/>" />
									<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="<s:property value='sortno'/>"/>
								</th>
								<td align="left">
									<!--<input type="text" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue" maxlength="300" value="<s:property value='productAttrValue'/>" />-->
									<textarea name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue" rows="3" cols="80"><s:property value='productAttrValue'/></textarea>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr>
								<th align="right"><font color="red">*</font><input type="text" style="color:#000" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" maxlength="300" value="<s:property value='productAttrName'/>" onClick="javascript:if(this.value=='属性名') {this.value='';this.style.color='#000'}" onBlur="javascript:if(this.value==''){this.value='属性名';this.style.color='#999'} "/></th>
								<td align="left"><input type="text" style="color:#000" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue" maxlength="300" value="<s:property value='productAttrValue'/>" onClick="javascript:if(this.value=='属性值') {this.value='';this.style.color='#000'}" onBlur="javascript:if(this.value==''){this.value='属性值';this.style.color='#999'} "/>
								<a href="#" onClick="delAttrValue(this);">&nbsp;<img title="删除" style="cursor: pointer;" src="/etc/images/little_icon/delete.png" onclick="deleteProduct(46168)"></a></td>
							</tr>
						</s:else>
					</s:if>
					<s:else>
						<tr>
							<th align="right"><font color="red">*</font><input type="text" style="color:#000" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" maxlength="300" value="<s:property value='productAttrName'/>" onClick="javascript:if(this.value=='属性名') {this.value='';this.style.color='#000'}" onBlur="javascript:if(this.value==''){this.value='属性名';this.style.color='#999'} "/></th>
							<td align="left"><input type="text" style="color:#000" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue" maxlength="300" value="<s:property value='productAttrValue'/>" onClick="javascript:if(this.value=='属性值') {this.value='';this.style.color='#000'}" onBlur="javascript:if(this.value==''){this.value='属性值';this.style.color='#999'} "/>
							<a href="#" onClick="delAttrValue(this);">&nbsp;<img title="删除" style="cursor: pointer;" src="/etc/images/little_icon/delete.png" onclick="deleteProduct(46168)"></a></td>
						</tr>
					</s:else>
					<s:set name="i" value="#i + 1" ></s:set>
				</s:iterator>
				</s:if>
				<s:if test="#request.isOTC && #isOTCAttr">
					<tr class="isOtcTr">
							<th align="right">
								通用名称：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="通用名称"/>
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="1"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								商品名称：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="商品名称" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="2"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								成份：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="成份" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="3"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								规格：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="规格"/>
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="4"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								作用类别：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="作用类别" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="5"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								适应症/功能主治：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="适应症/功能主治" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="6"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								用法用量：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="用法用量"/>
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="7"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								用法用量2：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="用法用量2"/>
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="7"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								不良反应：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="不良反应" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="8"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								不良反应2：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="不良反应2" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="8"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								不良反应3：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="不良反应3" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="8"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								禁忌：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="禁忌" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="9"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								禁忌2：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="禁忌2" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="9"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								禁忌3：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="禁忌3" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="9"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								注意事项：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="注意事项" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="10"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								注意事项2：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="注意事项2" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="10"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								注意事项3：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="注意事项3" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="10"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								药物相互作用：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="药物相互作用"/>
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="11"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								药物相互作用2：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="药物相互作用2"/>
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="11"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								药物相互作用3：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="药物相互作用3"/>
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="11"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								药理作用：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="药理作用" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="12"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								药理作用2：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="药理作用2" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="12"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								批准文号：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="批准文号" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="13"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
						<tr class="isOtcTr">
							<th align="right">
								生产企业：
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrName" value="生产企业" />
								<input type="hidden" name="product.productAttrDraftList[<s:property value='#i'/>].sortno" value="14"/>
							</th>
							<td align="left">
								<textarea rows="3" cols="80" name="product.productAttrDraftList[<s:property value='#i'/>].productAttrValue"></textarea>
								<s:set name="i" value="#i + 1" ></s:set>
							</td>
						</tr>
				</s:if>
			</table>
			<br />
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
									<td align="left"><input type="checkbox" name="product.operationAttrIds" <s:if test="operationAttrList.get(#index).isSelect==true"> checked="checked" </s:if> value="<s:property value='operationAttrList.get(#index).operationAttrId'/>"/> &nbsp;&nbsp;<s:property value='operationAttrList.get(#index).operationAttrName'/></td>
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
			<br />
		</div>
		<div class="ct">
			<textarea id="editor_id" name="product.introduce"  
				style="height:400px;width:1014px;resize:none;"> 
				 <s:property value="product.introduce"/>
			</textarea>
			<br />
		</div>
		
		<!-- lazy -->
		<div class="editor_change" style="visibility:hidden" ></div>
        <div style="visibility:hidden">
            <textarea id="editor_lazy" name="product.introduceLazy" >
                <s:property value="product.introduceLazy"/>
            </textarea>
        </div>
				
		<s:if test="product.productType != 0">
			<div class="ct" id="certificateProductDiv">
				<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse" id="certificateTR">
					<s:iterator value="product.cerfificateList" >
						<tr>
							<th align="right" class="eidt_rowTitle" width="30%"><s:property value="#request.certificateTypeMAP[fileType]"/>：</th>
							<td>
								<input type="file" name="certificateFiles" data-fileType="<s:property value="fileType"/>" data-pscId="<s:property value="pscId"/>" />
								<s:if test="filePath != null">
									&nbsp;&nbsp;
									已有资质文件：
									<a target="_blank" href="<s:property value='certificateViewPath'/><s:property value='filePath' />"><s:property value='fileName' /></a>
									<input type="hidden" class="exsitFileName" value="<s:property value='fileName' />" />
								</s:if>
							</td>
						</tr>
					</s:iterator>
				</table>
				<br />
			</div>
		</s:if>
	</div>
	<table>
		<tbody>
			<tr align="center">
				<td align="center">
                    <input id="submitButton" onclick="submitOperation();" class="btn-custom btnStyle"
                           type="button" value="提交"/>&nbsp;&nbsp;
                    <input class="btn-custom btnStyle" type="button" value="返回" onClick="gotoListForView();"/>
				</td>
			</tr>
		</tbody>
	</table>
	<input type="hidden" id="indexCount" value="<s:property value="#i" />" />
	</s:form>
</div>

<s:form action="/app/productShow.action" method="POST" namespace='/app' id="listfrm" name='listfrm'>
	<s:hidden type="hidden" name="checkedId"/>
	<s:hidden name="productForSelectPara.productNo"/>
	<s:hidden name="productForSelectPara.productTitle"/>
	<s:hidden name="productForSelectPara.bCategoryId"/>
	<s:hidden name="productForSelectPara.mCategoryId"/>
	<s:hidden name="productForSelectPara.categoryId"/>
	<s:hidden name="productForSelectPara.status"/>
	<s:hidden name="productForSelectPara.keyword"/>
	<s:hidden name="productForSelectPara.brandId"/>
	<s:hidden name="productForSelectPara.searchBrandName"/>
	<s:hidden name="productForSelectPara.bCategoryName"/>
	<s:hidden name="productForSelectPara.mCategoryName"/>
	<s:hidden name="productForSelectPara.searchCategoryName"/>
	<s:hidden name="page.pageNo"/>
</s:form>
</body>
</html>