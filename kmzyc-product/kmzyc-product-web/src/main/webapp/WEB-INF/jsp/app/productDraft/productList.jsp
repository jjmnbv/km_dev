<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/autocomplete/autocompletestyles.css" type="text/css" rel="stylesheet">
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
</style>

<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
<script type="text/javascript" src="/etc/js/productDraft/product.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.mockjax.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/etc/autocomplete/demo.js"></script>

</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:set name="parent_name" value="'产品编辑'" scope="request" />
<s:set name="name" value="'产品列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<form action="/app/productDraftShow.action?type=product" method="POST" namespace='/app'
	id="frm" name='frm'>
	<s:hidden name="checkedId" id="checkedId"/>
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" height="100" align="center"
		cellpadding="0" cellspacing="0">

		<tr>
			<!-- 根据查询字段的多少判断colspan-->
			<td width="80%" valign="middle" colspan="6">
            </td>
		</tr>

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
				name="bCategoryId" id="categoryId1" listKey="categoryId" listValue="categoryName"
				headerKey="" headerValue="--一级类目--"
				onchange="change2('categoryId1','categoryId2');"></s:select> 
				<s:select list="#request.mCategoryList"
				name="mCategoryId" id="categoryId2"  listKey="categoryId" listValue="categoryName" 
				headerKey="" headerValue="--二级类目--"
				onchange="change2('categoryId2','categoryId3');"></s:select> 
				<s:select list="#request.sCategoryList" id="categoryId3" 
				headerKey="" headerValue="--三级类目--" 
				name="productForSelectPara.categoryId"  listKey="categoryId" listValue="categoryName"></s:select>
			</td>
		</tr>
		<tr>
			<td align="right">状态：</td>
			<td align="left"><s:select list="#request.productDraftStatusMap"
				name="productForSelectPara.status" id="productStatus" headerKey=""
				headerValue="--全部状态--"></s:select></td>
			<td align="right">关键字：</td>
			<td align="left"><s:textfield cssClass="input_style"
				name="productForSelectPara.keyword" id="keyword" size="32"/></td>
		</tr>
		<tr>
			<td align="right">品牌：</td>
			<td align="left">
				<input type="text" id="autocomplete" value="<s:property value='productForSelectPara.searchBrandName'/>" name="productForSelectPara.searchBrandName" size="32" />
			</td>
			<td align="right">所属商家：</td>
			<td align="left">
				<input type="text" id="autocomplete_forSuppliers"  value="<s:property value='productForSelectPara.supplierNameForSearch'/>" name="productForSelectPara.supplierNameForSearch" size="32" />
			</td>
		</tr>
		<tr>
			<td align="right">产品类型：</td>
			<td align="left"><s:select list="#request.productTypeMap"
				name="productForSelectPara.productType" id="productType" headerKey=""
				headerValue="--产品类型--"></s:select>&nbsp;&nbsp;&nbsp;
            </td>
            <td colspan="4" align="right" >
                <input type="button" onClick="doSearch()" class="queryBtn" />
                <input class="addBtn" type="button" onClick="gotoAdd();"/>
			    <s:if test="page.dataList.size>0">
                    <input class="btn-custom upShelfBtn" type="button" onclick="createUp();"/>
				    <input class="btn-custom batchDelBtn" type="button" onclick="batchDeleteProductDraft();"/>
				    <input type=" button" value="批量提交审核" onClick="batchSubmitTheAudit();"
                           class="btn-custom btnStyle_09"/>
                </s:if>
            </td>
		</tr>
	</table>


	<!-- 数据列表区域 -->
	<table width="98%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C1C8D2">
		<tr>
			<th align="center" width="5%"><input type='checkbox' id='allbox'
				name='allbox' onclick='checkAll(this)'></th>
			<th align="center" width="15%">产品标题</th>
			<th align="center" width="8%">编码</th>
			<th align="center" width="8%">品牌</th>
			<th align="center" width="5%">产品类型</th>
			<th align="center" width="17%">关键字</th>
			<th align="center" width="8%">产品信息状态</th>
			<th align="center" width="">操作类型</th>
			<th align="center">商家名称</th>
			<th align="center" width="12%">操作</th>
		</tr>
		<s:iterator id="productiterator" value="page.dataList">
			<tr>
				<td align="center" width="5%">
					<input type="checkbox" name="productIds" value='<s:property value="productId"/>'>
					<input type="hidden" id="st<s:property value="productId"/>" value="<s:property value="status"/>" />
				</td>
				<td align="center"><s:property value="productTitle" escape="false" /></td>
				<td align="center"><s:property value="productNo" /></td>
				<td align="center"><s:property value="prodBrand.brandName" /></td>
				<td align="center"><s:property value="#request.productTypeMap[productType]" /></td>
				<td align="center"><s:property value="keyword" /></td>
				<td align="center"><s:property
					value="#request.productDraftStatusMap[status]" /></td>
				<td align="center"><s:property
					value="#request.DraftTypeMap[opType]" /></td>
				<td align="center">
					<s:if test="merchantName==null">
						康美
					</s:if>
					<s:else>
						<s:property value="merchantName" />
					</s:else>
				</td>
				<td align="center">
					<s:if test="status!=3 && status!=1 ">
						<img title="修改" style="cursor: pointer;" src="/etc/images/little_icon/xiugai.png"  onclick="gotoUpdate(<s:property value='productId'/>)" />
					</s:if>
					<img title="查看" style="cursor: pointer;" src="/etc/images/little_icon/search.png"  onclick="gotoViewProduct(<s:property value='productId'/>)" />
					<s:if test="status!=1">
						<img title="删除" style="cursor: pointer;" src="/etc/images/little_icon/delete.png"  onclick="deleteProduct(<s:property value='productId'/>)" />
					</s:if>
					<s:if test="status==0 && status!=2 ">
						<img title="提交审核" style="cursor: pointer;" src="/etc/images/little_icon/tijiao.png"  onclick="submitTheAudit('<s:property value="productId"/>')" />
					</s:if>
					<s:if test="status == 1">
						<img title="取消审核" style="cursor: pointer;" src="/etc/images/little_icon/chehui.png"  onclick="submitTheUnAudit('<s:property value="productId"/>')" />
					</s:if>
					<s:if test="status == 6">
						<img title="查看原因" style="cursor: pointer;" src="/etc/images/little_icon/bohui.png"  onclick="viewReasons(<s:property value='productId'/>);" />
						<input type="hidden" value="<s:property value="reasons" />" id="reasons_<s:property value='productId'/>" />
					</s:if>
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


</form>
<s:form action="/app/productuUpShelf.action" method="POST"
	namespace='/app' id="upForm" name='upForm'>
</s:form>
<s:form action="/app/productuDownShelf.action" method="POST"
	namespace='/app' id="downForm" name='downForm'>
</s:form>
<s:form action="/app/batchDeleteProductDraft.action" method="POST"
	namespace='/app' id="batchDeleteForm" name='batchDeleteForm'>
</s:form>
<s:form action="/app/productShow.action" method="POST" namespace='/app' id="listfrm" name='listfrm'>
	<s:hidden name="checkedId" id="checkedId"/>
	<s:hidden name="productForSelectPara.status" id="statusChecked"/>
</s:form>

<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />
<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert(document.getElementById("rtnMsg").value);
	//-->
	</SCRIPT>
</s:if>
</BODY>
<script type="text/javascript">
function viewReasons(productId){
	art.dialog({   
		title:'审核不通过的原因',
	    content: $("#reasons_"+productId).val(),   
	    width:500,
	    height:300,
	    drag:true,
	    lock:true,
	    cancelVal: '关闭',   
	    cancel: true //为true等价于function(){}   
	}); 
}
</script>


</HTML>

