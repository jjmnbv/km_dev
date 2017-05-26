<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品发布</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<script src="/etc/js/jquery-1.8.3.js"></script>
<style type="text/css">
	.edit_title{
		padding:0px;
		font-size:12px;
	}
</style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:set name="parent_name" value="'产品管理'" scope="request" />
<s:set name="name" value="'产品发布'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<!-- 数据编辑区域 -->
<table width="98%" class="edit_table" align="center" cellpadding="3" cellspacing="0"
       border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;margin-left:12px;float:left;">
	<tr> 
		<th align="center" class="edit_title">商品信息</th>
		<th align="center" class="edit_title" width="80px">市场价</th>
		<th align="center" class="edit_title" width="80px">成本价</th>
		<th align="center" class="edit_title" width="80px">销售单价</th>
		<th align="center" class="edit_title" width="110px">重量（单位：克）</th>
		<th align="center" class="edit_title" width="110px">PV值（康美中药城）</th>
		<th align="center" class="edit_title" width="110px">条形码</th>
	</tr>
	<s:iterator value="product.productSkuDrafts" >
		<tr> 
			<td align="left">
				<b>商品名称</b>：<s:property value='product.productName'/>&nbsp;&nbsp;
				<b>商家名称</b>：
				<s:if test="product.merchantName==null">
						康美
				</s:if>
				<s:else>
					<s:property value="product.merchantName" />
				</s:else>&nbsp;&nbsp;
				<s:iterator value="attributeValues">
					<b><s:property value="attribute" /></b>：<s:property value="value" />&nbsp;&nbsp;
				</s:iterator>
			</td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='markPrice != null'><s:property value="%{getText('{0,number,##.##}',{markPrice})}" /></s:if><s:else>暂无价格</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='costPrice != null'><s:property value="%{getText('{0,number,##.##}',{costPrice})}" /></s:if><s:else>暂无价格</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='price != null'><s:property value="%{getText('{0,number,##.##}',{price})}" /></s:if><s:else>暂无价格</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='unitWeight != null'><s:property value="%{getText('{0,number,##.##}',{unitWeight})}" /></s:if><s:else>暂无重量</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='pvValue != null'><s:property value="%{getText('{0,number,##.##}',{pvValue})}" /></s:if><s:else>0.0</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:property value="skuBarCode" /></td>
		</tr>
	</s:iterator>
</table>
<br />
<!-- 底部 按钮条 -->
<table width="98%" align="left" class="edit_bottom" height="30"
	border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;float: left;clear: left;margin-top:30px;">
	<tr>
		<td align="center">
			<input type="button" class="backBtn" onclick="gotoList()" />
		</td>
	</tr>
</table>

<s:form action="/app/productDraftAuditShow.action" method="POST" namespace='/app' id="listfrm" name='listfrm'>
	<s:hidden type="hidden" name="checkedId"/>
	<s:hidden name="productForSelectPara.productNo"/>
	<s:hidden name="productForSelectPara.productName"/>
	<s:hidden name="productForSelectPara.bCategoryId"/>
	<s:hidden name="productForSelectPara.mCategoryId"/>
	<s:hidden name="productForSelectPara.categoryId"/>
	<s:hidden name="productForSelectPara.status"/>
	<s:hidden name="productForSelectPara.keyword"/>
	<s:hidden name="productForSelectPara.brandId"/>
	<s:hidden name="page.pageNo"/>
	<s:hidden name="type" />
</s:form>
</body>
<script language="JavaScript">
	function gotoList(){
		document.getElementById("listfrm").submit();
	}
</script>
</html>