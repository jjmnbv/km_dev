<%@page contentType="text/html;charset=UTF-8"  import="com.pltfm.app.maps.ProductStatusMap" isELIgnored="false"%>
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
<script type="text/javascript" src="/etc/js/common.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>

<script type="text/javascript">

</script>

</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:set name="parent_name" value="'产品管理'" scope="request" />
<s:set name="name" value="'产品待审核列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/auditProductShow.action" method="POST"  namespace='/app' id="frm" name='frm'>
<s:hidden name="checkedId" id="checkedId"/>
<!-- 查询条件区域 -->
<table  width="98%" class="content_table" align="center" height="100" cellpadding="0" cellspacing="0" >
	<tr>
		<td width="80%" valign="middle" colspan="6">
		</td>
	</tr>
	<tr>
	  <td>编码：
	     <s:textfield name="productForSelectPara.productNo" cssClass="input_style" id="productNo" />
	  </td>
	  <td>名称：<s:textfield name="productForSelectPara.name" cssClass="input_style" id="productName" /></td>
	  <td>状态：
	  	  <s:select id="productStatus" name="productForSelectPara.status" list="#request.productStatusByAuditMap"></s:select>
      </td>
    </tr>
	<tr>
		<td>类别：
			<s:select list="#request.categoryList"
				name="productForSelectPara.bCategoryId" id="categoryId1" listKey="categoryId" listValue="categoryName"
				headerKey="" headerValue="--一级类目--"
				onchange="change2('categoryId1','categoryId2');"></s:select> 
				<s:select list="#request.mCategoryList"
				name="productForSelectPara.mCategoryId" id="categoryId2"  listKey="categoryId" listValue="categoryName" 
				headerKey="" headerValue="--二级类目--"
				onchange="change2('categoryId2','categoryId3');"></s:select> 
				<s:select list="#request.sCategoryList" id="categoryId3" 
				headerKey="" headerValue="--三级类目--" 
				name="productForSelectPara.categoryId"  listKey="categoryId" listValue="categoryName"></s:select>
	    </td>
		<td>关键字：<s:textfield cssClass="input_style" name="productForSelectPara.keyword" id="keyword" /></td>
	</tr>
	<tr>
		<td>
			品牌：<s:select list="#request.productBrandMap" name="productForSelectPara.brandId" id="brandId" headerKey="" headerValue="--全部品牌--"></s:select>
		</td>
		<td>
			<input type="button" onClick="doSearch()" class="queryBtn" value=""/>
            <s:if test="productForSelectPara.status==1">
                <input type="button" value="审核" class="btn-custom btnStyle"  onclick="batchAuditProduct()" />
            </s:if>
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="">
		    <s:if test="productForSelectPara.status==1">
	            <input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)'/>		
	        </s:if>
	        <s:else>
			<input type="hidden" id='allbox' name='allbox' value=""/>
		</s:else>
        </th>
		<th align="center" width="15%">产品名称</th>
		<th align="center" width="15%">编码</th>
		<th align="center" width="">品类</th>
		<th align="center" width="">品牌</th>
		<th align="center" width="15%">关键字</th>
		<th align="center" width="15%">状态</th>
		<th align="center" width="15%">操作</th>
	</tr>
	<s:iterator id="productiterator" value="page.dataList">
	<tr>
	    <s:if test="status==1"><td align="center" width=""><input type="checkbox" name="productIdChk"  value='<s:property value="id"/>_<s:property value="status"/>'></td></s:if>
		<s:else><td align="center" width=""><input type="hidden" name="productIdChk"  value='<s:property value="id"/>_<s:property value="status"/>'></td></s:else>
		
		<td align="center"><s:property value="name" escape="false" /></td>
		<td align="center"><s:property value="productNo" /></td>
		<td align="center"><s:property value="drugCategory.name" /></td>
		<td align="center"><s:property value="prodBrand.brandName" /></td>
		<td align="center"><s:property value="keyword" /></td>
		<td align="center"><s:property value="#request.productStatusByAuditMap[status]" /></td>
		<td align="center">
			<img title="查看" style="cursor: pointer;" src="/etc/images/little_icon/search.png"  onclick="gotoAuditViewProduct(<s:property value='id'/>)" />
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

<br><br>


</s:form>
<s:form action="/app/batchAuditProduct.action" method="POST"  namespace='/app' id="batchAuditForm" name='batchAuditForm'>
</s:form>
<s:form action="/app/auditProductShow.action" method="POST"  namespace='/app' id="auditProductShowForm" name='auditProductShowForm'>
	<s:hidden name="checkedId" id="checkedId"/>
</s:form>
</BODY>
</HTML>