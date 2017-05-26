<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/addproduct.css" type="text/css" rel="stylesheet">
<link  rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<script charset="utf-8"  src="/kindeditor/kindeditor.js"></script> 
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"  ></script> 
<script type="text/javascript" src="/etc/js/kindeditor_show.js"></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
<script language='JavaScript' src="/etc/js/dialog-common.js"></script>
<script type="text/javascript" src="/etc/js/productDraft/product.js"></script>

<title>审核产品信息</title>
</head>
<body>
<div style="position:absolute;align:center;top:20px;left:30px">
	<div id="content">
		<div>
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
				<td colspan="2" align="center">
					<input type="button" class="backBtn" onClick="gotoListForView()" />
					<input type="button" value="产品预览" class="btn-custom btnStyle" onClick="previewProductInfoPage('<s:property value="product.productId"/>')" />
				</td>
				</tr>
				<tr>
					<th style="background-color: #def2fa;" align="left" class="eidt_rowTitle" colspan="2">基本信息</th>
				</tr>
				<tr>
					<th width="30%" align="right" class="eidt_rowTitle"><font color="red">*</font>产品类别：</th>
					<td width="70%">
						${bCategoryName} > ${mCategoryName} > ${sCategoryName}
					</td>
				</tr>
				<tr>
					<th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>产品类型：</th>
					<td width="80%">
						<s:property value="#request.productTypeMap[product.productType]" />
					</td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle"><font color="red">*</font>品牌：</th>
					<td>
						<label>
							<s:property value="#request.productBrandMap[product.brandId]"/>
						</label>
					</td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle"><font color="red">*</font>商家：</th>
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
					<th align="right" class="eidt_rowTitle">产品编码：</th>
					<td>
						<s:property value="product.productNo"/>
					</td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle"><font color="red">*</font>产品名称：</th>
					<td><label><s:property value="product.productName"/></label></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">产品主标题：</th>
					<td>
						<s:property value="product.productTitle"/>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">产品副标题：</th>
					<td>
						<s:property value="product.productSubtitle"/>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">关键词(seo)：</th>
					<td><s:property value="product.keyword"/></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">包装清单：</th>
					<td><s:property value="product.packListing"/></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">市场价：</th>
					<td><s:property value="product.marketPrice"/></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">批文类型：</th>
					<td><s:property value="#request.approvalTypeMap[product.approvalType]"/></td>
				</tr>
				<tr>
                    <th align="right" class="eidt_rowTitle">批文号：</th>
					<td><s:property value="product.approvalNo"/></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">ERP产品编号：</th>
					<td><s:property value="product.erpProductCode"/></td>
				</tr>
				<tr>
                    <th align="right" class="eidt_rowTitle">备注：</th>
					<td><label><s:property value="product.productDesc"/></label></td>
				</tr>
			</table>
		</div>
		<s:if test="#request.cateAttrList != null && #request.cateAttrList.size()>0">
		<div>
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<th style="background-color: #def2fa;" align="left" class="eidt_rowTitle" colspan="2">类目属性</th>
				</tr>
				<s:iterator value="#request.cateAttrList">
				<tr> 
					<th width="30%" align="right" class="eidt_rowTitle">
						<s:property value='productAttrName'/>：
					</th>
					<td width="70%" align="left"> 
						<s:property value='valueName'/>
					</td>
				</tr>
				</s:iterator>
			</table>
		</div>
		</s:if>
		<s:if test="#request.skuAttrList != null && #request.skuAttrList.size()>0">
		<div>
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<th style="background-color: #def2fa;" align="left" class="eidt_rowTitle" colspan="2">销售属性</th>
				</tr>
				<s:iterator value="#request.skuAttrList" status="st">
				<tr> 
					<th width="30%" align="right" class="eidt_rowTitle">
						<s:property value='productAttrName'/>：
					</th>
					<td width="70%" align="left"> 
						<s:property value='valueName'/>
					</td>
				</tr>
				</s:iterator>
			</table>
		</div>
		</s:if>
		<div>
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<th style="background-color: #def2fa;" align="left" class="eidt_rowTitle">产品SKU</th>
				</tr>
				<s:iterator value="#request.skuList">
				<tr> 
					<td align="left"> 
					<s:if test=""></s:if>
					SKU编号：<s:property value='productSkuCode'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:iterator value="attributeValues" >
						<s:property value='attribute'/>：<s:property value='value'/>&nbsp;&nbsp;&nbsp;&nbsp;
					</s:iterator>
					&nbsp;&nbsp;&nbsp;&nbsp;
					状态：<s:property value="status == 0 ? '无效':'有效'" />
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="查看图片" class="btn-custom" onClick="skuImageView(<s:property value='productSkuId'/>);" />
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="商品介绍" class="btn-custom" onClick="skuIntroduceView(<s:property value='productSkuId'/>);" />
					</td>
				</tr>
				</s:iterator>
			</table>
		</div>
		<s:if test="#request.deniAttrList != null && #request.deniAttrList.size()>0">
		<div>
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<th style="background-color: #def2fa;" align="left" class="eidt_rowTitle" colspan="2">自定义属性</th>
				</tr>
				<s:iterator value="#request.deniAttrList">
				<tr>
					<th align="right" width="30%"><s:property value='productAttrName'/>：</th>
					<td align="left" width="70%"><s:property value='valueName'/></td>
				</tr>
				</s:iterator>
			</table>
		</div>
		</s:if>
		<s:if test="#request.opAttrList != null && #request.opAttrList.size()>0">
		<div>
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<th style="background-color: #def2fa;" align="left" class="eidt_rowTitle">运营属性</th>
				</tr>
				<s:iterator value="#request.opAttrList">
				<tr> 
					<td align="left"><s:property value='productAttrName'/></td>
				</tr>
				</s:iterator>
			</table>
		</div>
		</s:if>
		<s:if test="product.productType != 0">
			<div>
				<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
					<tr>
						<th colspan="2" style="background-color: #def2fa;" align="left" class="eidt_rowTitle">资质文件</th>
					</tr>
					<s:iterator value="product.cerfificateList">
					<tr> 
						<td align="right" width="30%"><s:property value="#request.certificateTypeMAP[fileType]"/>：</td>
						<td align="left" width="70%"><a target="_blank" href="<s:property value='certificateViewPath' /><s:property value='filePath' />"><s:property value='fileName' /></a></td>
					</tr>
					</s:iterator>
				</table>
			</div>
		</s:if>
		<div>
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
			<tr>
				<th style="background-color: #def2fa;" align="left" class="eidt_rowTitle">商品介绍</th>
			</tr>
			<tr> 
				<td align="left"><textarea id="editor_id" name="product.introduce"  
					style="height:400px;width:1014px;resize:none;"> 
					 <s:property value="product.introduce"/>
				</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" class="backBtn" onClick="gotoListForView()" />
				</td>
			</tr>
			</table>
		</div>
	</div>
</div>
<s:form action="/app/auditProductDraft.action" method="post" namespace="app" id="auditForm" name="auditForm">
	<s:token></s:token>
	<s:hidden name="reasonText" id="reasonText" ></s:hidden>
	<input name="productId" type="hidden" id="productId" value="<s:property value='product.productId'/>" />
	<s:hidden name="auditStatus" id="auditStatus" ></s:hidden>
</s:form>

<form action="/app/productDraftShow.action?type=product" method="POST" namespace='/app' id="listfrm" name='listfrm'>
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
	<s:hidden name="page.pageNo"/>
</form>
</body>
<script type="text/javascript">
	function skuImageView(productSkuId){
		popDialog("/app/findImagesBySkuId.action?productSkuId="+productSkuId ,"查看图片","800px","340px");
		
	}
	function skuIntroduceView(productSkuId){
		popDialog("/app/findDraftIntroduceBySkuId.action?productSkuId="+productSkuId ,"SKU商品介绍","1000px","400px");
		
	}
</script>

</html>