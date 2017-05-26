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
;
</style>

<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.mockjax.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/etc/autocomplete/demo.js"></script>


</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:set name="parent_name" value="'产品发布'" scope="request" />
<s:set name="name" value="'产品列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<input type="hidden" value="<s:property value="type" />" id="returnType" />

<s:form action="/app/findCertificateFilesProduct.action" method="POST" namespace='/app'
	id="frm" name='frm'>
	<s:hidden name="checkedId" id="checkedId"/>
	<s:hidden name="type" />
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" height="100" align="center"
		cellpadding="0" cellspacing="0">

		<tr>
			<td align="right">编码：</td>
			<td align="left"><s:textfield name="productForSelectPara.productNo"
				cssClass="input_style" id="productNo" /></td>
			<td align="right">标题：</td>
			<td align="left"><s:textfield name="productForSelectPara.productTitle"
				cssClass="input_style" id="productName" size="32"/></td>
			<td align="right">关键字：</td>
			<td align="left"><s:textfield cssClass="input_style"
				name="productForSelectPara.keyword" id="keyword" size="32"/></td>
		</tr>
		<tr>
			<td align="right">状态：</td>
			<td align="left"><s:select list="#request.productStatusMap"
				name="productForSelectPara.status" id="productStatus" headerKey=""
				headerValue="--全部状态--"></s:select></td>
			<td align="right">类别：</td>
			<td align="left" colspan="3">
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
			<td align="right">品牌：</td>
			<td align="left"><input type="text" id="autocomplete" value="<s:property value='productForSelectPara.searchBrandName'/>" name="productForSelectPara.searchBrandName" size="32" /></td>
			<td align="left" colspan="4">
			&nbsp;
				一级类目名：<s:textfield cssClass="input_style" name="productForSelectPara.bCategoryName" size="10"></s:textfield>
				二级类目名：<s:textfield cssClass="input_style" name="productForSelectPara.mCategoryName" size="10"></s:textfield>
				三级类目名：<s:textfield cssClass="input_style" name="productForSelectPara.searchCategoryName" size="10"></s:textfield>
			</td>
		</tr>
		<tr>
			<td>
				<INPUT TYPE="button" onclick="doCertificateSearch()" class="queryBtn" value="">
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
			<th align="center" width="10%">编码</th>
			<th align="center" width="">品牌</th>
			<th align="center" width="10%">状态</th>
			<th align="center">商家名称</th>
			<th align="center">资质文件</th>
			<th align="center">操作</th>
		</tr>
		<s:iterator id="productiterator" value="page.dataList" var="pro" >
			<tr>
				<td align="center" width="5%"><input type="checkbox"
					name="productIdChk"
					value='<s:property value="id"/>_<s:property value="status"/>_<s:property value="channel"/>'>
				</td>
				<td align="center">
					<s:property value="productTitle" escape="false" />
					<input type="hidden" id="st_<s:property value='id'/>" value="default" />
					<p id="pos_<s:property value='id'/>" style="margin:0px;color:red;"><s:property value="postil"/></p>
				</td>
				<td align="center"><s:property value="productNo" /></td>
				<td align="center"><s:property value="prodBrand.brandName" /></td>
				<td align="center"><s:property
					value="#request.productStatusMap[status]" /></td>
				<td align="center">
					<s:if test="merchantName==null">
						康美
					</s:if>
					<s:else>
						<s:property value="merchantName" />
					</s:else>
				</td>
				<td align="left">
					<s:iterator value="#pro.cerfificateList">
						<p align="left" style="margin: 2px;">
							<s:property value="#request.certificateTypeMAP[fileType]"/>：
							<a target="_blank" href="<s:property value='certificateViewPath' /><s:property value='filePath' />"><s:property value='fileName' /></a>
						</p>					
					</s:iterator>
				</td>
				<td align="center">
					<img title="查看" style="cursor: pointer;" src="/etc/images/little_icon/search.png"  onclick="gotoViewProduct(<s:property value='id'/>,'<s:property value="type"/>')" />
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
<s:form action="/app/productShow.action" method="POST" namespace='/app' id="listfrm" name='listfrm'>
	<s:hidden name="checkedId" id="checkedId"/>
	<s:hidden name="productForSelectPara.status" id="statusChecked"/>
</s:form>
</BODY>
</HTML>
