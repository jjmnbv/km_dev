<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
  	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
	<meta name="renderer" content="webkit|ie-comp|ie-stand"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>产品图片维护</title>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="添加图片"></jsp:param>
	</jsp:include>


</head>
  <body>
  <jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<div class="container-fluid">
<div class="row-fluid">

<jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_product.jsp"></jsp:include>
<div class="content">
<div class="row-fluid"><!-- block -->
<div class="block_01">
<div class="navbar-inner">
    <ul class="breadcrumb">
        <i class="icon-home"></i>
        <li>商品 <span class="divider">/</span></li>
        <li>添加新商品</li>
    </ul>
</div>
<s:form action="/productDraft/uploadImage.action" method="POST" id="frm" name='frm' enctype="multipart/form-data" >
<input type="hidden" name="productId" id="productId" value="<s:property value="product.productId" />" />
<input type="hidden" name="productSkuId" id="productSkuId" value="" />
<input type="hidden" name="deleteImgIds" id="deleteImgIds" />
<input type="hidden" name="auditStatus" value="0" />
<div class="block-content collapse in"><!--开始-->
  <div class="com_step">
	<ul><li class="gray"><i class="icon-step01 icon-white"></i><p>STEP.1</p><span>选择商品分类</span><i class="icon-stept"></i></li></ul>
	<ul><li class="gray"><i class="icon-step02 icon-white"></i><p>STEP.2</p><span>填写商品详情</span><i class="icon-stept"></i></li></ul>
	<ul><li><i class="icon-step03"></i><p>STEP.3</p><span>上传商品图片</span><i class="icon-stept"></i></li></ul>
	<ul><li class="gray"><i class="icon-step04 icon-white"></i><p>STEP.4</p><span>添加商品成功</span></li></ul>
  </div>
  <s:iterator value="#request.productSkuList" var="sku">
  	<div class="fbphoto" data-id="<s:property value="#sku.productSkuId" />">
  		<s:if test="#sku.attributeValues != null && #sku.attributeValues.size() > 0">
			<div class="fbphototi" id="skuId<s:property value="#sku.productSkuId" />">
				<s:iterator value="#sku.attributeValues">
					<s:property value="attribute" />：<s:property value="value" />&nbsp;&nbsp;
				</s:iterator>
			</div>
		</s:if>
		<ul id="uploadpreview<s:property value="#sku.productSkuId" />">
			<li class="puls" id="li<s:property value="#sku.productSkuId" />">
				<a href="#"><img src="<s:property value='cssPath' />images/puls.png" class="openImg" data-id="<s:property value="#sku.productSkuId" />"></a>
				<input class="openFi" type="file" name="imgFile" id="openFi<s:property value="#sku.productSkuId" />" data-id="<s:property value="#sku.productSkuId" />" style="display: none;" multiple accept="image/*" />
			</li>
		</ul>
	</div>
  </s:iterator>

	<div class="form-actions">
	    <a href="javascript:void(0);" class="btn btn-large btn-return" data-productId="<s:property value="product.productId" />"><i class="icon-chevron-left"></i> 返回上一步</a>
	    <a href="javascript:void(0);" class="btn btn-success btn-large" data-productId="<s:property value="product.productId" />"><i class="icon-folder-close icon-white"></i>保存当前商品</a>
		<a href="javascript:void(0);" class="btn btn-danger btn-large btn-submit" data-productId="<s:property value="product.productId" />"><i class="icon-arrow-up icon-white"></i>立即提交审核</a>
    </div>
	<div class="alert alert-block">
		<h4 class="alert-heading">上传要求</h4>
		1. 请使用jpg\jpeg\png等格式、单张大小不超过1M的图片。<br>
		2. 每个规格的商品最多可上传10张产品图片。<br>
		3. 每个规格的图片默认第一张为主图。<br>
		4. 图片质量要清晰，不能虚化，要保证亮度充足。<br>
		5. 操作完成后请点击“保存当前商品”或“立即提交审核”按钮，否则无法生效。
	</div>
	</div>
	</s:form>
</div>
</div>
</div>
</div>
</div>

<s:form action="" method="POST" id="nextFrm" name='nextFrm' ></s:form>

<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
  </body>
</html>
