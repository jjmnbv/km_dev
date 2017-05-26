<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
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
<script type="text/javascript" src="/etc/js/sea.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>
<script charset="utf-8"  src="/kindeditor/kindeditor.js"></script> 
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"  ></script> 
<script charset="utf-8"  src="/kindeditor/kindeditor.js"></script> 
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" src="/etc/js/product_add.js"></script>
<script type="text/javascript" src="/etc/js/kindeditor_show.js"></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
<script language='JavaScript' src="/etc/js/dialog-common.js"></script>

<title>查看产品信息</title>
</head>
<body>
 <input value='<s:property value="product.introduce" />' id="productContent" type="hidden" />

<div style="position:absolute;align:center;top:20px;left:30px">
	<div id="content">
		<div>
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
				<td colspan="4" align="center">
				<s:if test="product.status==0">
					<input type="button" value="提交审核" class="btn-custom btnStyle"  onclick="submitTheAudit('<s:property value="product.id"/>')" />
				</s:if>
                <s:if test="type=='recheck'">
                    <input type="button" value="违规下架" class="btn-custom btnStyle"
                           onclick="illegalDown('<s:property value="product.id"/>')" />
                </s:if>
                <s:elseif test="type=='illegal'">
                    <input type="button" value="重新上架" class="btn-custom btnStyle"
                           onclick="illegalUp('<s:property value="product.id"/>')" />
                </s:elseif>
				&nbsp;&nbsp;
				<input type="button" value="产品预览" class="btn-custom btnStyle" onClick="previewProductInfoPage('<s:property value="product.id"/>')" />
				&nbsp;&nbsp;
				<input type="button" class="backBtn" onClick="gotoListForView()" />
				</td>
				</tr>
				<tr>
					<th style="background-color: #def2fa;" align="left" class="eidt_rowTitle" colspan="4">基本信息</th>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle" colspan="1">产品名称：</th>
					<td colspan="3" style="font-weight:bold;"><s:property value="product.name"/></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle" colspan="1">产品主标题：</th>
					<td colspan="3" style="font-weight:bold;"><s:property value="product.productTitle"/></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle" colspan="1">产品副标题：</th>
					<td colspan="3" style="font-weight:bold;"><s:property value="product.productSubtitle"/></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle" colspan="1">产品批注：</th>
					<td colspan="3" style="font-weight:bold;"><span style="color:red;"><s:property value="product.postil"/></span></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle" colspan="1">产品类别：</th>
					<td style="font-weight:bold;">${bCategoryName} > ${mCategoryName} > ${sCategoryName}</td>
					<th align="right" class="eidt_rowTitle" colspan="1">产品类型：</th>
					<td style="font-weight:bold;"><s:property value="#request.productTypeMap[product.productType]" /></td>
				</tr>
				<tr>
					<th width="15%" align="right" class="eidt_rowTitle">产品编码：</th>
					<td width="35%"><s:property value="product.productNo"/></td>
					<th  width="15%" align="right" class="eidt_rowTitle">关键词(seo)：</th>
					<td  width="35%"><s:property value="product.keyword"/></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">包装清单：</th>
					<td><s:property value="product.packListing"/></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">品牌：</th>
					<td><s:property value="product.prodBrand.brandName"/></td>
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
					<th align="right" class="eidt_rowTitle">批文类型：</th>
					<td><s:property value="#request.approvalTypeMap[product.approvalType]"/></td>
					<th align="right" class="eidt_rowTitle">批文号：</th>
					<td><s:property value="product.approvalNo"/></td>
				</tr>
				<tr>
					<th align="right" class="eidt_rowTitle">ERP产品编号：</th>
					<td><s:property value="product.erpProductCode"/></td>
					<th align="right" class="eidt_rowTitle" colspan="1">备注：</th>
					<td colspan="3"><label><s:property value="product.productDesc"/></label></td>
				</tr>
			</table>
		</div>
		<s:set name="categoryAttrList" value="product.productAttrList.{?#this.productAttrType==1 && #this.isSku==0}"></s:set> 
		<s:if test="#categoryAttrList != null && #categoryAttrList.size()>0">
		<div>
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<th style="background-color: #def2fa;" align="left" class="eidt_rowTitle" colspan="4">类目属性</th>
				</tr>
				<s:iterator value="#categoryAttrList"  status='st'>
					<s:if test="(#st.index+1)%2!=0">
						<tr> 
					</s:if>
						<th width="15%" align="right" class="eidt_rowTitle">
							<s:property value='productAttrName'/>：
						</th>
						<td width="35%" align="left"> 
							<s:property value='productAttrValue'/>
						</td>
					<s:if test="(#st.index+1)%2==0">
						</tr>
					</s:if>
				</s:iterator>
			</table>
		</div>
		</s:if>
		<s:set name="skuAttrList" value="product.productAttrList.{?#this.productAttrType==1 && #this.isSku==1}"></s:set>
		<s:if test="#skuAttrList != null && #skuAttrList.size()>0">
            <div>
                <table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
                    <tr>
                        <th style="background-color: #def2fa;" align="left" class="eidt_rowTitle" colspan="2">销售属性</th>
                    </tr>
                    <s:iterator value="#skuAttrList" status="st0">
                        <s:if test="(#st0.index+1)%2!=0">
                        <tr>
                        </s:if>
                            <th width="15%" align="right" class="eidt_rowTitle">
                            <s:property value='productAttrName'/>：
                            </th>
                            <td align="left">
                                <s:property value='productAttrValue'/>
                            </td>
                        <s:if test="(#st0.index+1)%2!=0">
                        </tr>
                        </s:if>
                    </s:iterator>
                </table>
            </div>
		</s:if>
		<div>
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<th style="background-color: #def2fa;" align="left" class="eidt_rowTitle" colspan="2">产品SKU</th>
				</tr>
				<s:iterator value="product.productSkus">
				<tr> 
					<th width="15%" align="right" class="eidt_rowTitle">
					SKU编号：
					</th>
					<td align="left">
					<s:property value='productSkuCode'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:iterator value="productSkuAttrList" >
					<s:property value='categoryAttrName'/>：<s:property value='categoryAttrValue'/>&nbsp;&nbsp;&nbsp;&nbsp;
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
		<s:set name="definitionAttrList" value="product.productAttrList.{?#this.productAttrType==2}"></s:set>
		<s:if test="#definitionAttrList != null && #definitionAttrList.size()>0">
            <div>
                <table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
                    <tr>
                        <th style="background-color: #def2fa;" align="left" class="eidt_rowTitle" colspan="4">自定义属性</th>
                    </tr>
                    <s:iterator value="#definitionAttrList" status="st1">
                        <s:if test="(#st1.index+1)%2!=0">
                            <tr>
                        </s:if>
                            <th align="right" width="15%"><s:property value='productAttrName'/>：</th>
                            <td align="left" width="35%"><s:property value='productAttrValue'/></td>
                        <s:if test="(#st1.index+1)%2==0">
                            </tr>
                        </s:if>
                    </s:iterator>
                </table>
            </div>
		</s:if>

		<s:set name="operationAttrList" value="product.productAttrList.{?#this.productAttrType==3}"></s:set>
		<s:if test="#operationAttrList != null && #operationAttrList.size()>0">
		<div>
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<th style="background-color: #def2fa;" align="left" class="eidt_rowTitle">运营属性</th>
				</tr>
				<tr>
					<td align="left">
						<s:iterator value="#operationAttrList">&nbsp;&nbsp;<s:property value='productAttrName'/>&nbsp;&nbsp;|</s:iterator>
					</td>
					
				</tr>
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
				<td align="left">
                    <textarea id="editor_id" name="product.introduce"
                        style="height:400px;width:1014px;resize:none;">
                         <s:property value="product.introduce"/>
                    </textarea>
				</td>
			</tr>
			<tr>
			<td align="center">
				<input type="button" class="backBtn" onClick="gotoListForView()" />
			</td>
			</tr>
			</table>
		</div>
	</div>
</div>
<div id="reason" style="display: none;">
    <textarea id="reasonArea" rows="10" cols="100" style="resize: none;" maxlength="80"></textarea>
</div>

<s:form action="/app/productShow.action" method="POST" namespace='/app' id="listfrm" name='listfrm'>
	<s:hidden type="hidden" name="checkedId"/>
	<s:hidden name="productForSelectPara.productNo"/>
	<s:hidden name="productForSelectPara.productTitle"/>
	<s:hidden name="productForSelectPara.bCategoryId"/>
	<s:hidden name="productForSelectPara.mCategoryId"/>
	<s:hidden name="productForSelectPara.categoryId"/>
	<s:hidden name="productForSelectPara.status" id="statusChecked"/>
	<s:hidden name="productForSelectPara.keyword"/>
	<s:hidden name="productForSelectPara.brandId"/>
	<s:hidden name="productForSelectPara.searchBrandName"/>
	<s:hidden name="productForSelectPara.bCategoryName"/>
	<s:hidden name="productForSelectPara.mCategoryName"/>
	<s:hidden name="productForSelectPara.searchCategoryName"/>
	<s:hidden name="page.pageNo"/>
	<s:hidden name="type" id="returnType" ></s:hidden>
</s:form>
</body>
<script type="text/javascript">
	function skuImageView(productSkuId){
		popDialog("/basedata/findSomeImageBySkuId.action?productSkuId="+productSkuId ,"查看图片","800px","340px","iframe");
	}
	function skuIntroduceView(productSkuId){
		popDialog("/app/findIntroduceBySkuId.action?productSkuId="+productSkuId ,"SKU商品介绍","1000px","400px");
		
	}
</script>
</html>