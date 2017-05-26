<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="商品详情"></jsp:param>
</jsp:include>
<title>商品详情</title>
<style type="text/css">
	#guige{height: 208px;overflow-y: scroll;display: block;}
</style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>

<div class="container-fluid">
<div class="row-fluid">

<jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_product.jsp"></jsp:include>

<div class="content" id="content" data-url="${basePath}">
<div class="row-fluid"><%--block--%>
<div class="block_01">
<div class="navbar-inner">
<ul class="breadcrumb">
    <i class="icon-home"></i>
    <li>商品 <span class="divider">/</span></li>
    <li>商品详情页面</li>
</ul>
</div>
<div class="block-content collapse in"><!--开始-->
<table cellpadding="0" cellspacing="0" border="0"
	class="table  table-bordered">
	<tr class="tablesbg">
		<td colspan="2" class="shoptLt">商品基本信息</td>
		<input type="hidden" value="0" id="pIndex" />
	</tr>
	<tr>
		<td class="width140 shoptR">商品分类：</td>
		<td class="tdleft">
			<s:property value="productMainVo.categoryList[2].categoryName"/> > <s:property value="productMainVo.categoryList[1].categoryName"/> > <s:property value="productMainVo.categoryList[0].categoryName"/>
		</td>
	</tr>
	<tr>
		<td class="shoptR">商品主标题：</td>
		<td class="tdleft">
			<s:property value="productMainVo.product.productTitle"/>
		</td>
	</tr>
	<tr>
		<td class="shoptR">商品副标题：</td>
		<td class="tdleft">
			<s:property value="productMainVo.product.productSubtitle"/>
		</td>
	</tr>
	<tr>
		<td class="shoptR">商品简称：</td>
		<td class="tdleft">
			<s:property value="productMainVo.product.name"/>
		</td>
	</tr>
	<tr>
		<td class="shoptR">店内分类：</td>
		<td class="tdleft">
			<s:property value="#request.shopCategoryName"/>
		</td>
	</tr>
	<tr>
		<td class="shoptR">商品品牌：</td>
		<td class="tdleft">
			<s:property value="productMainVo.product.prodBrand.brandName"/>
		</td>
	</tr>
	<s:if test="productMainVo.product.productSkus[0].productSkuAttrList == null || productMainVo.product.productSkus[0].productSkuAttrList.size() == 0">
		<tr>
			<td class="shoptR">销售单价：</td>
			<td class="tdleft">
				<s:property value="productMainVo.product.productSkus[0].price" /> 元
			</td>
		</tr>
		<tr>
			<td class="shoptR">市场价：</td>
			<td class="tdleft">
				<s:property value="productMainVo.product.productSkus[0].markPrice" /> 元
			</td>
		</tr>
		<tr>
			<td class="shoptR">推广服务费：</td>
			<td class="tdleft">
				<s:property value="productMainVo.product.productSkus[0].pvValue" /> 元
			</td>
		</tr>
		<tr>
			<td class="shoptR">重量：</td>
			<td class="tdleft">
				<s:property value="productMainVo.product.productSkus[0].unitWeight" /> 克
			</td>
		</tr>
		<tr>
			<td class="shoptR">商品库存：</td>
			<td class="tdleft">
				<s:property value="productMainVo.product.productSkus[0].stock" />
			</td>
		</tr>
		<tr>
			<td class="shoptR">商家货号：</td>
			<td class="tdleft">
				<s:property value="productMainVo.product.productSkus[0].sellerSkuCode" />
			</td>
		</tr>
	</s:if>
	<s:else>
	<s:set name="skuAttrList" value="productMainVo.product.productAttrList.{?#this.productAttrType==1 && #this.isSku==1}"></s:set>
		<s:iterator value="#skuAttrList" status="st0">
			<tr>
				<td class="shoptR">
					<s:property value='productAttrName'/>：
				</td>
				<td class="tdleft"> 
					<s:property value='productAttrValue'/>
				</td>
			</tr>
		</s:iterator>
		<tr style="" id="kucun">
			<td class="shoptR">产品SKU：</td>
			<td class="tdleft trbg01" id="guige">
				<table cellspacing="0" cellpadding="0" border="0" class="table  table-bordered">
					<tr class="trbg02">
					  <td class="width120">SKU编码</td>
					  <s:iterator value="productMainVo.product.productSkus[0].productSkuAttrList" status="st0">
					  	<td class="width120"><s:property value='categoryAttrName'/></td>
					  </s:iterator>
					  <td class="width120">市场价</td>
					  <td class="width120">销售单价</td>
                      <td class="width120">推广服务费</td>
					  <td class="width120">重量</td>
					  <td class="width120">库存</td>
					  <td class="width120">商家货号</td>
					  <td class="width120">状态</td>
					  <td class="width120">操作</td>
					</tr>
					<s:iterator value="productMainVo.product.productSkus">
						<tr>
						  <td><s:property value='productSkuCode'/></td>
						  <s:iterator value="productSkuAttrList" >
						  	<td>
								<s:property value='categoryAttrValue'/>
						  	</td>
						  </s:iterator>
						  <td><s:property value='markPrice'/> 元</td>
						  <td><s:property value='price'/> 元</td>
                          <td><s:property value='pvValue'/> 元</td>
						  <td><s:property value='unitWeight'/> 克</td>
						  <td><s:property value='stock'/></td>
						  <td><s:property value='sellerSkuCode'/></td>
						  <td><s:property value="status == 0 ? '无效':'有效'" /></td>
						  <td><input type="button" value="查看图片" data-skuid="<s:property value='productSkuId'/>" class="btn btn-success j_view_productImages"/></td>
						</tr>
					</s:iterator>
				</table>
			</td>
		</tr>
	</s:else>
	<tr>
		<td class="shoptR">关键词(SEO)：</td>
		<td class="tdleft">
			<s:property value="productMainVo.product.keyword"/>
		</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="0" border="0"
	class="table  table-bordered">
	<tbody>
		<tr class="tablesbg">
			<td colspan="2" class="shoptLt">商品详情描述</td>
		</tr>
		<tr>
			<td class="width140 shoptR">基本属性：</td>
			<td class="tdleft">
                <s:set name="categoryAttrList" value="productMainVo.product.productAttrList.{?#this.productAttrType==1 && #this.isSku==0}"></s:set>
                <s:if test="#categoryAttrList != null && #categoryAttrList.size()>0">
                    <table width="100%" border="0" cellspacing="5" class="newform">
                        <tbody>
                        <s:iterator value="#categoryAttrList" status="st">
                            <s:if test="#st.odd == true">
                                <tr>
                            </s:if>
                            <td class="width120 shoptR">
                                <s:property value='productAttrName'/>：
                            </td>
                            <td class="textL" width="40%">
                                <s:property value='productAttrValue'/>
                            </td>
                            <s:if test="#st.odd == false || #st.last">
                                </tr>
                            </s:if>
                        </s:iterator>
                        </tbody></table>
                </s:if>
			</td>
		</tr>
		<%--<tr>
			<td class="shoptR">运营属性：</td>
			<td class="tdleft">
			<div class="duoxian">
				<ul>
					<s:set name="operationAttrList" value="productMainVo.product.productAttrList.{?#this.productAttrType==3}"></s:set>
					<s:iterator value="#operationAttrList" var="operAttr" status="st">
						<li>
							<s:property value='productAttrName'/>
						</li>
					</s:iterator>
				</ul>
			</div>
			</td>
		</tr>--%>
		<tr>
			<td class="shoptR">自定义属性：</td>
			<td class="tdleft">
				<s:set name="definitionAttrList" value="productMainVo.product.productAttrList.{?#this.productAttrType==2}"></s:set>
				<s:iterator value="#definitionAttrList" status="st1">
					<li>
						<s:property value='productAttrName'/>：<s:property value='productAttrValue'/>
					</li>
				</s:iterator>
			</td>
		</tr>
		 <!--资质认证开始-->
		 <s:if test="#request.isCertificate==1">
         <tr>
              <td class="width140 shoptR">资质认证：<p style="color: #db0000">（每一页都需加盖开店主体红章）</p></td>
              <td class="tdleft">
                  <div class="control-group" style="font-weight: bold;padding-left: 30px; padding-bottom:10px;border-bottom: 1px #ddd dotted;">
                    请上传产品注册证书或者备案凭证、持有广告审查批准文号的还需公示广告审查批准文号；<br>
					海外购商品需提供：进口货物报关单资质、进口保健食品批准证书、商品出入境检验检疫卫生证书、商品出入境检验检疫卫生证明等资质。
                    <p style="font-weight: normal; color: #999;">上传的资质文件需加盖企业主体红章，图片尺寸800px*800px以上，大小1M以内，格式png/jpg/jpeg格式，可上传多张</p>
                  </div>
                  <div class="control-group gmp">
                  <s:set name="certificateFileList" value="product.certificateFileList"></s:set>
					<s:if test="#certificateFileList != null && #certificateFileList.size>0">
					<s:iterator value="#certificateFileList" status="s">
					<li class="old_certifile"><img src="<s:property value='certiFilePreviewPath'/><s:property value='filePath'/>" alt="正面">
					<p>证书名称：<s:property value='fileName'/></p>
					<p>证书编号：<s:property value='fileCode'/></p>
					<p>有效期至：<s:date name='validTime' format='yyyy-MM-dd'/></p>
					</li>
					</s:iterator>	
					</s:if>
                  </div>
                <div class="control-group">
                  <label class="control-label" style="width: 140px;float: left;">广告审查批准文号： </label>
                  <s:set name="certificateFileList" value="product.certificateFileList"></s:set>
					<s:if test="#certificateFileList != null && #certificateFileList.size>0">
					<s:iterator value="#certificateFileList" status="s">
						<s:if test="#s.index == 0">
						<div class="controls" style="margin-left: 140px;">
		                	<span><s:property value='advertApprovalNo'/></span>
		                </div>
						</s:if>
					</s:iterator>
					</s:if>
                </div>
                </td>
            </tr>
          </s:if>
          <!--资质认证结束-->
		<tr>
			<td class="shoptR">商品描述：</td>
			<td class="tdleft">
				<textarea id="editor_id" name="productMainVo.product.introduce"  
					style="height:400px;width:99%;resize:none;">
                    <s:property value="productMainVo.product.introduce"/></textarea>
			</td>
		</tr>
	</tbody>
</table>
<div class="form-actions"><a href="javascript:;" data-type="<s:property value='type'/>" data-showType="<s:property value="#request.showType"/>" data-shopId="<s:property value="#request.shopId"/>"  class="btn btn-large btnBack"> <i class="icon-chevron-left"></i> 返回</a></div>
</div>
</div>
</div>
</div>
</div>
</div>
<s:form action="" method="POST" id="frm" name='frm'>
    <input type="hidden" value="<s:property value="auditStatus" />" name="auditStatus" id="auditStatus" />
</s:form>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>