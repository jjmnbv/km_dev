<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
  	<jsp:include page="/WEB-INF/jsp/common/template_skuimages.jsp">
		<jsp:param name="titlePrefix" value="商品图片修改"></jsp:param>
	</jsp:include>
    <title>产品图片维护</title>
  </head>
  <body>
  <aa:zone name="picZone">
  	<input type="hidden" id="backPage" value="<s:property value='type' />" />
    <div class="container fn-p20 j_container"  data-js-src="${staticUrl}${jsBaseUrl}" data-images-src="${staticUrl}${imageBaseUrl}">
    <div style="height:400px; overflow-y:scroll;">
	<h3 class="step--sub-title">产品图片上传 <span class="ui-tiptext ui-tiptext-error fn-ml10"><i class="ui-icon ui-icon-warning"></i>小提示：一个商品最多上传10张产品图片。</span></h3>
		<div>
			<!--upload-->
				<fieldset>
					<legend><s:if test="uploadType==1">产品图片上传</s:if><s:elseif test="uploadType==2">产品Flash图片上传</s:elseif></legend>
					<div class="fn-mt10 ui-well ui-well-sm">
						产品标题：<strong><s:property value="productSkuDraft.productTitle" /></strong>
						<input id="productNo" type="hidden" value="<s:property value="productSkuDraft.productNo" />" />
						<input id="productSkuId" type="hidden" value="<s:property value="productSkuDraft.productSkuId" />" />
						<input id="productId" type="hidden" value="<s:property value="productSkuDraft.productId" />" />
					</div>

						<s:iterator value="productSkuDraft.attributeValues" >
							<s:property value="attribute" />：<strong><s:property value="value" /></strong>&nbsp;&nbsp;&nbsp;
						</s:iterator>					

					<div id="fsUploadProgress" >
					</div>
				</fieldset>
				<div><span id="spanButtonPlaceholder"></span><button id="btnStartUpload" class="ui-button ui-button-success ui-button-sm fn-ml10 j_btnStartUpload" disabled="disabled" style="vertical-align:top;">点击上传</button><button id="btnCancel" class="ui-button ui-button-success ui-button-sm fn-ml10 j_cancel" disabled="disabled" style="vertical-align:top;">取消</button>
				</div>
			</div>
			<form action="/ajaxJson/updateImageSortNo.action" method="post" name="imageFrm" id="imageFrm" target="theID" >
			<div id="uploadpreview">
				<h3 class="step--sub-title fn-mt20">已上传 &nbsp;&nbsp;&nbsp;&nbsp;<span id="outputDiv"></span></h3>	
				<s:if test="#request.imageList.size() == 0"><div class="adbt" id="imageMsg" style="color:black;">该商品暂无图片</div></s:if>
				<p class="fn-mt20 fn-clear"><input type="button" value="调整图片" id="editButton" class="ui-button ui-button-success ui-button-sm j_edit_skuimages" <s:if test="#request.imageList.size() == 0"> disabled="disabled" </s:if> /></p>

				<s:iterator value="#request.imageList">		 
					<div id="pic_<s:property value="imageId" />" class="preview<s:if test='isDefault == 0'> isDefault</s:if>" >
						<input name="imageId" type="hidden" value="<s:property value="imageId" />" />
						<div>
							<img width="128" height="128" src="<s:property value="imagePath" /><s:property value="imgPath" />"></img>
						</div>
						<p class="editDataDiv" style="display: none;" >
							<s:if test='isDefault eq "0"'>
								<input type="hidden" id="checkRadioId" value="rpic_<s:property value="imageId" />" ></input>
							</s:if>
							<span>
								&nbsp;
								<img src="${staticUrl}${imageBaseUrl}little_icon/first.png" alt="默认" title="默认" id="default_img_<s:property value="imageId" />" class="j_setdefault" data-skuid='<s:property value="skuId" />' data-imageid='<s:property value="imageId" />' />
								<!-- 
									<input name='radio' <s:if test='isDefault eq "0"'>checked='checked'</s:if> type='radio' id='rpic_<s:property value="imageId" />' value='1' onclick='javascript:setMainPic(<s:property value="skuId" />,<s:property value="imageId" />,this.parentNode.parentNode.parentNode);'>
									<label for="rpic_<s:property value="imageId" />">默认</label>
								 -->
							</span>
							<span class=''>
								&nbsp;
								<img src="${staticUrl}${imageBaseUrl}little_icon/delete.png" alt="删除" title="删除" id="del_img_<s:property value="imageId" />" class="j_delpic" data-imageid='<s:property value="imageId" />' data-skuid='<s:property value="skuId" />)' />
								<!-- 
									<a href='javascript:;' onclick='delPic(this.parentNode.parentNode.parentNode,<s:property value="imageId" />,<s:property value="skuId" />)' >删除</a>
								-->
							</span>
							&nbsp;
							<img src="${staticUrl}${imageBaseUrl}little_icon/qianyi.png" alt="提前" title="提前" id="up_img_<s:property value="imageId" />" data-imageid='<s:property value="imageId" />' class="j_goforward"/>
							&nbsp;
							<img src="${staticUrl}${imageBaseUrl}little_icon/houyi.png" alt="后退" title="后退" id="down_img_<s:property value="imageId" />" data-imageid='<s:property value="imageId" />' class="j_backup"/>
						</p>
					</div>
				</s:iterator>
			</div>
			</form>
		</div>
	</div>
	<iframe name="theID" style="display: none;"></iframe>  
	</aa:zone>
  </body>
</html>
