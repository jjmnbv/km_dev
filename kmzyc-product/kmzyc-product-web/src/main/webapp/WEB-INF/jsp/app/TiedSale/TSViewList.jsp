<%@page contentType="text/html;charset=UTF-8"
	import="com.pltfm.app.maps.ProductStatusMap" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品关联</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
</style>
<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>
<script type="text/javascript">

</script>

</head>
<body>
	<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

	<s:set name="parent_name" value="'产品管理'" scope="request" />
	<s:set name="name" value="'产品关联'" scope="request" />
	
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />
	<s:form action="/tiedSale/stQuery.action" method="POST"
		namespace="tiedSale" id="frm" name='frm'>

		<!-- 查询条件区域 -->
		<table width="98%" class="content_table" align="center" height="100"
			cellpadding="0" cellspacing="0">

			<tr>
				<td>产品货物编号： <s:textfield name="product.productNo"
						cssClass="input_style" id="productNo" /></td>
				<td>名称：&nbsp;&nbsp;<s:textfield type="text" name="product.procuctName"
						cssClass="input_style" id="productName" /></td>
				<td>状态：&nbsp;&nbsp;&nbsp; <s:select list="#request.productStatusMap"
						name="product.status" id="productStatus" headerKey=""   style="width: 105px"
						headerValue="--全部状态--"></s:select>
				</td>
			</tr>
			<tr>
					<td>类别： &nbsp; &nbsp; &nbsp; &nbsp; <s:select list="#request.categoryList" id="categoryId1"
						listKey="categoryId" listValue="categoryName" headerKey="0"
						headerValue="--一级类目--"
						onchange="change1('categoryId1','categoryId2');"></s:select> <select
					id="categoryId2" onchange="change1('categoryId2','categoryId3');">
						<option value="0">--二级类目--</option>
				</select> <select id="categoryId3" name="product.categoryId">
						<option value="0">--三级类目--</option>
				</select>
				</td>
				<td>关键字：<s:textfield type="text" cssClass="input_style"
						name="product.keyword" id="keyword" /></td>
			</tr>
			<tr>
				<td>品牌：&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<s:select list="#request.productBrandMap" id="brandId"  style="width:116px"
						  headerKey="0"
						name="product.brandId" headerValue="--全部品牌 --"></s:select>
				</td>
				<td><input TYPE="button" onclick="doSearch()" class="queryBtn" value=""/>
				</td>
			</tr>
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>

				<th align="center" width="10%">产品货物编号</th>
				<th align="center" width="15%">产品SKU编号</th>
				<th align="center" width="12%">产品名称</th>
				<th align="center" width="12%">品牌</th>
				<th align="center" width="15%">关键字</th>
				<th align="center" width="15%">状态</th>
				<th align="center" width="15%">操作</th>
			</tr>
			<s:iterator id="productiterator" value="page.dataList">
				<tr>

					<td align="center"><s:property value="productNo" /></td>
					<td align="center"><s:property value="productSkuCode" /></td>
					<td align="center"><s:property value="procuctName" /></td>
					<td align="center"><s:property value="brandName" /></td>
					<td align="center"><s:property value="keyword" /></td>
					<td align="center"><s:if test="status==3">已上架</s:if> <s:elseif
							test="status==2">已审核,待上架</s:elseif> <s:elseif test="status==1">待审核</s:elseif>
						<s:elseif test="status==0">草稿</s:elseif> <s:elseif
							test="status==5">系统下架</s:elseif> <s:elseif test="status==4">已下架</s:elseif>
					</td>
					<td align="center"><img title="添加" style="cursor: pointer;"
						src="/etc/images/button_new/submit_audit.png"
						onclick="gotoAdd(<s:property value='productSkuId'/>)" /> <img
						title="查看" style="cursor: pointer;" src="/etc/images/button_new/view.png"
						onclick="gotoView(<s:property value='productSkuId'/>)" />
					</td>
				</tr>
			</s:iterator>
		</table>

		<!-- 分页按钮区 -->
		<table width="98%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td><%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
				</td>
			</tr>
		</table>

		<br>
		<br>


	</s:form>

<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	
		alert(document.getElementById("rtnMsg").value);

	</SCRIPT>
</s:if>	
	

</BODY>



<script type="text/javascript">
function gotoAdd(mainSku){
location.href="/tiedSale/tsAdd.action?product.productSkuId="+mainSku;
}

function gotoView(id){

location.href="/tiedSale/tsToView.action?tiedSade.mainSku="+id;

}

</script>



</HTML>