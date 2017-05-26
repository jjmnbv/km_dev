<%@page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
	.tableStyle1{font-size:12px;}
</style>
<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/validate/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>
<script type="text/javascript" src="/etc/js/coupon/chooseCouponProduct.js"></script>
</head>
<body>
 
<s:form action="/coupon/chooseCouponProduct.action" method="POST"  namespace='/coupon' id="frm" name='frm'>
<input  type="hidden" name="haveChoosedPro" id="haveChoosedPro" value="<s:property value='haveChoosedPro' />" />
<input  type="hidden" name="callPath" value='<s:property value="callPath"/>'/>
<input  type="hidden" name="code" value='<s:property value="code"/>'/>
<input  type="hidden" name="supplierTypes" value='<s:property value="supplierTypes"/>'/>
<!-- 查询条件区域 -->
<table  width="98%" class="content_table" align="center" height="90" cellpadding="0" cellspacing="0"  style=" margin-top:11px; ">
	<tr>
		<td width="45%">编码：
			<s:textfield name="viewProductSku.productNo" cssClass="input_style" id="productNo" />
		</td>
		<td width="43%">
			SKU编码：<s:textfield cssClass="input_style" name="viewProductSku.productSkuCode" id="keyword" />		</td>
		<td width="12%">&nbsp;</td>
	</tr>
    	<tr>
		<td colspan="3">	
        类别：
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
		<td> 名称：<s:textfield name="viewProductSku.procuctName" cssClass="input_style" id="productName" /></td>
		 <s:if test="callPath==1">
		 	<td>
		 		<INPUT TYPE="submit"   class="queryBtn" value="">
		 	</td>
		 </s:if><s:else>
		 <td><INPUT TYPE="submit"   class="queryBtn" value=""></td>
		 </s:else>
		
	</tr>
    
</table>


<!-- 数据列表区域 -->
<table width="98%"  class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="9%">
        <input type="checkbox" name="checkbox" id="checkbox"  onclick="checkAll(this,'skuId')">
        <label for="checkbox"></label></th>
		<th align="center" width="13%">名称</th>
		<th align="center" width="22%">编码</th>
		<th align="center" width="16%">SKU编码</th>
		<th align="center" width="30%">基本信息</th>
	</tr>
	<s:iterator id="productiterator" value="page.dataList" status="st" >
	<tr>
	    <td align="center" width="9%">
			<input type="checkbox" class="j_productSkuCode" name="productSkuCode"  id='<s:property value="id"/>' calss="<s:property value='name' />"
             value='<s:property value="productSkuCode"/>'>
		</td>
		<td align="center"><s:property value="procuctName" /></td>
		<td align="center"><s:property value="productNo" /></td>
		<td align="center"><s:property value="productSkuCode" /></td>
		<td align="center">
			<s:iterator value="viewSkuAttrs" var="v" >
				<b><s:property value="#v.categoryAttrName" /></b> <s:property value="#v.categoryAttrValue" />&nbsp;&nbsp;
			</s:iterator>
		</td>
	  </tr>
	</s:iterator>
</table>

<!-- 分页按钮区 -->
<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td>
			<%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
		</td>
	</tr>
</table>

<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td style="text-align:center">
	    <input type="button" name="choose" id="ok_choose" class="btn-custom btnStyle" value="确定选择" onClick="coupproduct()"></td>
	</tr>
</table>
 




<br><br>


</s:form>
 
</BODY>
</HTML>