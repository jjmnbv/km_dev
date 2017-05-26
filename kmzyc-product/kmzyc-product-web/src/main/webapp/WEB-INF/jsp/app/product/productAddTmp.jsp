<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加产品基本资料</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></script>
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<script src="/etc/js/product/product.js"></script>
<script type="text/javascript">
function brandChange(){   
	$('#brandName').val($('#brandId').find("option:selected").text());
}

function categoryChange(){   
	$('#categoryName').val($('#categoryId3').find("option:selected").text());
}
</script>
</head>
<body>

<s:set name="parent_name" value="'产品管理'" scope="request"/>
<s:set name="name" value="'基础资料'" scope="request"/>
<s:set name="son_name" value="'产品添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/productAdd.action" method="POST" namespace='/app'
	 onsubmit="return Validator.Validate(this,2)&&checkSelected()">

	<!-- 数据编辑区域 -->
	<table width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<!-- error message -->
		<s:if test="rtnMessage != null">
			<tr>
				<td colspan="2" align="center"><font color="red"><s:property
					value='rtnMessage' /></font></td>
			</tr>
		</s:if>
		<tr>
			<th colspan="2" align="left" class="edit_title">基本信息</th>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>产品类别：</th>
			<td width="80%">
				<s:select list="#request.categoryList" id="categoryId1" listKey="categoryId" listValue="categoryName" headerKey="" headerValue="--一级类目--" onchange="change1('categoryId1','categoryId2');"></s:select>
			    <select id="categoryId2" onchange="change1('categoryId2','categoryId3');">
			    	<option value="">--二级类目--</option>
	            </select>
				<select id="categoryId3" name="product.categoryId" onchange="categoryChange()">
				    <option value="">--三级类目--</option>
		        </select>
		        <s:hidden name="product.categoryName" id="categoryName"/>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>品牌：</th>
			<td>
				<label>
					<s:select list="#request.productBrandMap" name="product.brandId" id="brandId" headerKey="" headerValue="--全部品牌--" onchange="brandChange();"></s:select>
					<s:hidden name="product.brandName" id="brandName"/>
				</label>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>产品名称：</th>
			<td><label> <s:textfield name="product.name"
				id="productName" size="32" maxlength="128"  require="true"
				dataType="LimitB" max="64" min="1" msg="产品名称不能为空，且不超过64个字符"/> </label></td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">产品编码：</th>
			<td><s:textfield name="product.productNo" id="productNo" size="32" maxlength="32" /> </td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">产品标题：</th>
			<td><s:textfield name="product.productTitle" id="productTitle" size="32" maxlength="512" /></td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">关键词(seo)：</th>
			<td><s:textfield name="product.keyword" id="keyword" size="32" maxlength="32" /></td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">包装清单：</th>
			<td><s:textfield name="product.packListing" id="packListing" size="32" maxlength="32" /></td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">批准文类型：</th>
			<td>
				<s:select list="#request.approvalTypeMap" name="product.approvalType" id="approvalType" headerKey="" headerValue="--批文类型--"></s:select>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">批准文号：</th>
			<td><s:textfield name="product.approvalNo" id="approvalNo" size="32" maxlength="32" /></td>
		</tr>
		<tr>
			<th align="right">支持到货通知：</th>
			<td><s:select list="#request.isNoticeMap" name="product.isNotice" id="isNotice" ></s:select></td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">备注：</th>
			<td><label> <s:textarea name="product.productDesc" id="productDesc" rows="8" cols="45"/></label></td>
		</tr>
	</table>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center"><INPUT class="saveBtn" TYPE="submit"
				value="">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" class="backBtn" onclick="javascript:history.go(-1);" />
			<td width="20%" align="center"></td>
		</tr>
	</table>

	<br>
	<br>

</s:form>

</BODY>
</HTML>


