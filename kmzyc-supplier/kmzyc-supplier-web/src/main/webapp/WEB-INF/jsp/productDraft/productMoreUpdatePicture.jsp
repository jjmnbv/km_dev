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
        <li>修改商品图片</li>
    </ul>
</div>
<s:form action="/productDraft/updateImage.action" method="POST" id="frm" name='frm' enctype="multipart/form-data" >
<s:hidden name="auditStatus"></s:hidden>
<input type="hidden" name="productId" id="productId" value="<s:property value="productId" />" />
<input type="hidden" name="deleteImgIds" id="deleteImgIds" />
<div class="block-content collapse in"><!--开始-->
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
			<s:iterator value="#sku.productSkuImages" var="pi" status="st">
				<li class="pbox" data-id="<s:property value='#pi.imageId' />">
					<input type="hidden" name="exsitImgIds" value="<s:property value='#sku.productSkuId' />_<s:property value='#pi.imageId' />" />
					<img src="<s:property value='imagePath' /><s:property value='#pi.imgPath5' />">
					<s:if test="#st.index == 0">
						<p class="showClass">
					</s:if>
					<s:else>
						<p class="showClass" style="display:none;">
					</s:else>
						 <i class=" icon-ok-circle"></i>默认主图
						 </p>
					<p class="bottom-box">
						<i class="setMainPic icon-fast-backward icon-white"  data-id="<s:property value="#sku.productSkuId" />"></i>
		            	<i class="goForward icon-backward icon-white" data-id="<s:property value="#sku.productSkuId" />"></i> 
		            	<i class="backUp  icon-forward icon-white" data-id="<s:property value="#sku.productSkuId" />"></i>
           			</p>
					<a class="badge badge-important pulltop cbox">×</a>
				</li>
			</s:iterator>
		
			<li class="puls" id="li<s:property value="#sku.productSkuId" />">
				<a href="#"><img src="<s:property value='cssPath' />images/puls.png" class="openImg" data-id="<s:property value="#sku.productSkuId" />"></a>
				<input class="openFi" type="file" name="imgFile" id="openFi<s:property value="#sku.productSkuId" />" data-id="<s:property value="#sku.productSkuId" />" style="display: none;" multiple accept="image/jpg,image/jpeg,image/png" />
			</li>
		</ul>
	</div>
  </s:iterator>

	<div class="form-actions">
	    <button class="btn btn-large btnBack">返回</button>
	    <button class="btn btn-success btn-large" id="saveBtn">保存</button>
    </div>
	<div class="alert alert-block">
		<h4 class="alert-heading">上传要求</h4>
		1. 请使用jpg\jpeg\png格式、单张大小不超过1M的图片。<br>
		2. 每个规格的商品最多可上传10张产品图片。<br>
		3. 每个规格的图片默认第一张为主图。<br>
		4. 图片质量要清晰，不能虚化，要保证亮度充足。<br>
		5. 操作完成后请点击“保存”按钮，否则无法生效。
	</div>
	</div>
	</s:form>
</div>
</div>
</div>
</div>
</div>
<s:form action="" method="POST" id="backFrm" name='backFrm'  >
	<s:hidden name="auditStatus"></s:hidden>
</s:form>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
  </body>
</html>
