<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
<meta name="renderer" content="webkit|ie-comp|ie-stand"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>商列表</title>
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="促销商品选择"></jsp:param>
</jsp:include>
</head>
<body>
<div class="container-fluid">		


<!-- 当前位置 s-->
<div class="row-fluid">
	<div class="navbar">
		<div class="navbar-inner">
			<ul class="breadcrumb">
				<i class="icon-home"></i>
				<li>促销 <span class="divider">/</span></li>
				<li>审核管理</li>
				<li>商品查看</li>
			</ul>
		</div>
	</div>
</div>
<!-- 当前位置 e-->
	
<s:form action="queryPromotionProductListDetail.action" method="post" id="frm" name="frm">
	<s:hidden name="page" id="page" />
<input type="hidden" value="manage" name ="type"></input>
<input type="hidden" value="<s:property value="type"/>" name ="type" id="backType"></input>
<input type="hidden" value ="<s:property value="promotion.promotionId"/>" id="promotionId" name =promotionProduct.promotionId></input>
<input type="hidden" value ="<s:property value="promotion.channel"/>" id="channel" name =promotion.channel></input>


<table class="table com_tablest">
	<tbody>
		<tr>
			<td class="width50"></td>
			<td class="width150">产品名称</td>
			<td class="width100">sku编号</td>
			<td class="width200">sku属性</td>
			<s:if test="promotion.promotionType==10">
				<s:if test="promotion.nature==1">
					<td class="width50">活动特价</td>
				</s:if>
				<s:if test="promotion.nature==2">
					<td class="width50">加价</td>
				</s:if>
				<td class="width50">原始定价</td>
			</s:if>
			<s:if test="promotion.nature==1&&promotion.promotionType>=8">
				<td class="width50">最少购买</td>
				<td class="width50">最多购买</td>
				<td class="width50">活动库存</td>
			</s:if>		
		</tr>
	</tbody>
</table>	

<s:iterator value="pagee.dataList" id="promotion">
	<table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
		<tbody>
			<tr>
				<td class="width50">
					
				</td>
				<td class="width150"><s:property value="productName" /></td>
				<td class="width100"><s:property value="productSkuCode" /></td>
				<td class="width200"><s:iterator value="productSkuAttrList" >
					<s:property value='categoryAttrName'/>：<s:property value='categoryAttrValue'/>&nbsp;&nbsp;&nbsp;&nbsp;
					</s:iterator>
				</td>					  
				<s:if test="promotion.promotionType==10">
					<td class="width50"><input class="salePrice" title='<s:property value="promotionProductId"/>' 
					style="display:none" size="10px" 
					value="<s:property value="price" />" id="price" name="price" />
					<span id="noEditprice" style="display:inline"><s:property value="price" /></span>
					
					</td>
					<td class="width50"><s:property value="originalPrice" /></td>
				</s:if>
				<s:if test="promotion.nature==1&&promotion.promotionType>=8">
					<td class="width50">
						<input class="xiangou" title='<s:property value="promotionProductId"/>' 
						style="display:none" size="10px" 
						value="<s:property value="minBuy" />" id="minBuy" name="minBuy" />
						<span id="noEditprice" style="display:inline">
							<s:property value="minBuy" />
						</span>							
					</td>
					<td class="width50">
						<input class="xiangou" title='<s:property value="promotionProductId"/>' 
						style="display:none" size="10px" 
						value="<s:property value="maxBuy" />" id="maxBuy" name="maxBuy" />
						<span id="noEditprice" style="display:inline">
							<s:property value="maxBuy" />
						</span>							
					</td>
					<td class="width50">
						<input class="xiangou" title='<s:property value="promotionProductId"/>' 
						style="display:none" size="10px" 
						value="<s:property value="promotionStock" />" id="promotionStock" name="promotionStock" />
						<span id="noEditprice" style="display:inline">
							<s:property value="promotionStock" />
						</span>
					
					</td>
				</s:if>							
			</tr>
		</tbody>
	</table>
</s:iterator>	
</table>
<div class="fn-clear fn-mt10">
	<!-- 分页组件 -->
	<tiles:insertDefinition name="paginationBottom" />
</div>
</s:form>


</div>
</body>
</html>