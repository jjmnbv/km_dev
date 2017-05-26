<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品重量</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
	.tableStyle1{font-size:12px;}
</style>
<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>
<script src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:if test='"weightSuccess".equals(type)'>
	<script LANGUAGE="JavaScript">
	<!--
		alert("修改重量成功!");
	//-->
	</script>
</s:if>

<s:set name="parent_name" value="'产品重量管理'" scope="request" />
<s:set name="name" value="'产品列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<form action="/app/productShow.action?type=weight" method="POST"  namespace='/app' id="frm" name='frm'>
    <s:hidden name="type"/>
<!-- 查询条件区域 -->
<table width="98%" class="content_table" align="center" height="100" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">编码：</td>
		<td align="left"><s:textfield name="productForSelectPara.productNo"
			cssClass="input_style" id="productNo" /></td>
		<td align="right">标题：</td>
		<td align="left"><s:textfield name="productForSelectPara.productTitle"
			cssClass="input_style" id="productName" size="32"/></td>
		<td align="right">类别：</td>
		<td align="left">
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
	</tr>
	<tr>
		<td align="right">状态：</td>
		<td align="left"><s:select list="#request.productStatusMap"
			name="productForSelectPara.status" id="productStatus" headerKey=""
			headerValue="--全部状态--"></s:select></td>
		<td align="right">关键字：</td>
		<td align="left">
            <s:textfield cssClass="input_style" name="productForSelectPara.keyword" id="keyword" size="32"/>
        </td>
	</tr>
	<tr>
		<td align="right">品牌：</td>
		<td align="left">
            <s:select list="#request.productBrandMap" name="productForSelectPara.brandId" id="brandId"
                      headerKey="" headerValue="--全部品牌--"></s:select>
        </td>
		<td>
            <input TYPE="button" onClick="doSearch()" class="queryBtn" value=""/>
        </td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table id="mytable" width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">&nbsp;
        </th>
		<th align="center" width="15%">产品标题</th>
		<th align="center" width="15%">编码</th>
		<th align="center" width="15%">品牌</th>
		<th align="center" width="15%">关键字</th>
		<th align="center" width="15%">状态</th>
		<th align="center" width="15%">操作</th>
	</tr>
	<s:iterator id="productiterator" value="page.dataList" status="st" >
	<tr>
	    <td align="center" width="5%">
	    	<s:property value="#st.index + 1" />
		<td align="center"><s:property value="productTitle" escape="false" /></td>
		<td align="center"><s:property value="productNo" /></td>
		<td align="center"><s:property value="brandName" /></td>
		<td align="center"><s:property value="keyword" /></td>
		<td align="center"><s:property value="#request.productStatusMap[status]" /></td>
		<td align="center">
			<s:if test='status != 3'>
				<img title="修改重量" style="cursor: pointer;" src="/etc/images/button_new/modify.png"  onclick="gotoUpdateWeight(<s:property value='id'/>,'update')" />
			</s:if>
			<img title="查看重量" style="cursor: pointer;" src="/etc/images/button_new/select.png"  onclick="gotoUpdateWeight(<s:property value='id'/>,'view')" />
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
</form>
</body>
<script type="text/javascript">
	function gotoUpdateWeight(id,arg){
		location.href = "/dire/findSingleProduct.action?para=weight&productId="+id+"&type="+arg;
	}
</script>
</html>