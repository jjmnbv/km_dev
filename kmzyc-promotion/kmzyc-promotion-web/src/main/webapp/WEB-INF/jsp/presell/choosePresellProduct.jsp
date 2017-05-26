<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择产品</title>
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<%-- <script type="text/javascript" src="/etc/js/jquery.form.js"></script> --%>
<script type="text/javascript" src="/etc/js/validate/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>
<script type="text/javascript" src="/etc/js/presell/choosePresellProduct.js"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
	body{
		padding:0px;
		margin:0px;
	}
	table{
		margin-left:10px;
	}
</style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:form action="/presell/findProductSkuForPresell.action" method="POST"  namespace='/presell'  id="frmProductSkuForPresell" name='frmProductSkuForPresell' >
<s:hidden id="shopCode" name="productSku.product.shopCode"></s:hidden>
<s:hidden id="shopType" name="productSku.product.suppliterType"></s:hidden>

<br />
<table width="97%" class="table_search" align="center" cellpadding="0" cellspacing="0" style="border-collapse: collapse;font-size:12px" >
	<tr>
    	<td rowspan="2"><INPUT TYPE="button" class="btn-custom btngreen" id="selectList" value=" 保存所选 " onClick="javascript:chooseProduct();"></td>
    	<td>
			SKU编码：<s:textfield cssClass="input_style" name="productSku.productSkuCode" id="productSkuCode"/>
			&nbsp;&nbsp;&nbsp;
			产品标题：<s:textfield name="productSku.productTitle" cssClass="input_style" id="productTitle"/>
		</td>
	</tr>
	<tr>
	  <td>目录类目：
	        <s:select list="#request.categoryList"
				name="productSku.bCategoryId" id="categoryId1" listKey="categoryId" listValue="categoryName"
				headerKey="" headerValue="--一级类目--"
				onchange="change2('categoryId1','categoryId2');"></s:select> 
				<s:select list="#request.mCategoryList"
				name="productSku.mCategoryId" id="categoryId2"  listKey="categoryId" listValue="categoryName" 
				headerKey="" headerValue="--二级类目--"
				onchange="change2('categoryId2','categoryId3');"></s:select> 
				<s:select list="#request.sCategoryList" id="categoryId3" 
				headerKey="" headerValue="--三级类目--" 
				name="productSku.categoryId"  listKey="categoryId" listValue="categoryName"></s:select>
			<INPUT TYPE="submit" class="btn-custom" value="查询 ">
		</td>
	</tr>
</table>
<br />

<!-- 数据列表区域 -->
<table id="dataList" class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" >
	<tr> 
	    <th align="center" width="3%"><input type='checkbox' name='allbox' onclick='checkAll(this)'></th>
		<th align="center" width="15%">SKU编码</th>
	    <th align="center" width="22%">产品标题</th>
		<th align="center" width="20%">SKU描述</th>
		<th align="center" width="10%">品牌</th>
		<th align="center" width="10%">状态</th>
		<th align="center" width="10%">商家</th>
	</tr>
	<s:iterator id="productiterator" value="page.dataList" status="st" >
	<tr onMouseOver="this.style.backgroundColor='#def2fa'"
		onMouseOut="this.style.backgroundColor='#FFFFFF'">
		<input type="hidden" name="productNo" value="<s:property value='productNo' />" ></input>
	    <td align="center"><input type="checkbox" class="j_productSkuId" id="productSkuId" name="productSkuId" value='<s:property value="productSkuId"/>'></td>
		<td align="center"><s:property value="productSkuCode" /></td>
		<td align="center"><s:property value="productTitle" /></td>
		<td align="center">
		<s:iterator value="productSkuAttrList" >
		<s:property value='categoryAttrName'/>：<s:property value='categoryAttrValue'/>&nbsp;&nbsp;&nbsp;&nbsp;
		</s:iterator>
		</td>
		<td align="center"><s:property value="brandName" /></td>
		<td align="center"><s:property value="#request.productStatus[upStatus]" /></td>
		<td align="center"><s:property value="corporateName" /></td>
	</tr>
	</s:iterator>
</table>
<table  width="95%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td>
			<%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
		</td>
	</tr>
</table>
<br />
</s:form>

</script>
</BODY>
</HTML>

