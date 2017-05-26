<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
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
<script src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script src="/etc/js/common.js"></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
<script language='JavaScript' src="/etc/js/dialog-common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:set name="parent_name" value="'产品编辑'" scope="request" />
<s:set name="name" value="'产品图片'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/findAllProductSkuDraft.action" method="POST"  namespace='/app' id="frm" name='frm'>

<s:if test='"saveOk".equals(rtnMessage)'>
    <SCRIPT LANGUAGE="JavaScript">
		alert("新增价格成功!");
	</SCRIPT>
</s:if>

<!-- 查询条件区域 -->
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td>编码：
			<s:textfield name="productSkuDraft.productNo" cssClass="input_style" id="productNo" />
		</td>
		<td>
			标题：<s:textfield name="productSkuDraft.productTitle" cssClass="input_style" id="productName" />
		</td>
		<td>
			SKU编码：<s:textfield cssClass="input_style" name="productSkuDraft.productSkuCode" id="keyword" />
		</td>
		<td>
			<INPUT TYPE="button" onClick="doSearch()" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">&nbsp;
	    	
        </th>
		<th align="center" width="15%">标题</th>
		<th align="center" width="7%">编码</th>
		<th align="center" width="8%">SKU编码</th>
		<th align="center" width="20%">基本信息</th>
		<th align="center" width="5%">市场价</th>
		<th align="center" width="7%">销售单价</th>
		<th align="center" width="5%">重量</th>
		<th align="center" width="5%">PV值</th>
		<th align="center" width="10%">类型</th>
		<th align="center" width="8%">操作</th>
	</tr>
	<s:iterator id="productiterator" value="page.dataList" status="st" >
	<tr>
	    <td align="center" width="5%">
			<s:property value="#st.index + 1" />    
		</td>
		<td align="center"><s:property value="productTitle" escape="false" /></td>
		<td align="center"><s:property value="productNo" /></td>
		<td align="center"><s:property value="productSkuCode" /></td>
		<td align="center">
			<s:iterator value="attributeValues" var="v" >
				<b><s:property value="#v.attribute" /></b>：<s:property value="#v.value" />&nbsp;&nbsp;
			</s:iterator>
		</td>
		<td align="center"><s:property value="markPrice" /></td>
		<td align="center"><s:property value="price" /></td>
		<td align="center"><s:property value="unitWeight" /></td>
		<td align="center"><s:property value="pvValue" /></td>
		<td align="center"><s:property value="#request.DraftTypeMap[opType]" /></td>
		<td align="center">
			<s:if test="status != 1">
				<s:if test="opType != 3 && opType != 5">
					<img title="添加图片" style="cursor: pointer;" src="/etc/images/little_icon/tupian.png"
                         onclick="gotoUpdate(<s:property value='productSkuId'/>)" />
					
					<s:if test="opType != 4">
						<img title="添加商品介绍" style="cursor: pointer;" src="/etc/images/little_icon/xiugai.png"
                             onclick="gotoUpdateIntroduce(<s:property value='productSkuId'/>)" />
					</s:if>
				</s:if>
			</s:if>
			<img title="查看图片" style="cursor: pointer;" src="/etc/images/little_icon/search.png"
                 onclick="skuImageView(<s:property value='productSkuId'/>)" />
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
<SCRIPT LANGUAGE="JavaScript">

function gotoUpdate(id){
    location.href="/app/toUpdateProdImageDraft.action?productSkuId="+id;
}

function gotoUpdateIntroduce(id){
    location.href="/app/toUpdateIntroduceDraft.action?productSkuId="+id;
}

function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}

function skuImageView(productSkuId){
	popDialog("/app/findImagesBySkuId.action?productSkuId="+productSkuId ,"查看图片","1000px","340px");
}

</SCRIPT>

</BODY>
</HTML>