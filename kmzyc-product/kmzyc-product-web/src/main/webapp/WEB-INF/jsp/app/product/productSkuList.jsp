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
<s:set name="parent_name" value="'产品发布'" scope="request" />
<s:set name="name" value="'商品运费'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/findAllSkuForFreight.action" method="POST" id="frm" name='frm'>
<!-- 查询条件区域 -->
<table  width="98%" class="content_table" align="center"  cellpadding="0" cellspacing="0" >
	<tr>
		<td>编码：
			<s:textfield name="viewProductSkuCondition.productNo" cssClass="input_style" id="productNo" />
		</td>
		<td>
			标题：<s:textfield name="viewProductSkuCondition.productTitle" cssClass="input_style" id="productName" />
		</td>
		<td>
			SKU编码：<s:textfield cssClass="input_style" name="viewProductSkuCondition.productSkuCode" id="keyword" />
		</td>
		<td>
			状态：<s:select list="#request.productStatusMap" name="viewProductSkuCondition.status"
                         id="productStatus" headerKey="" headerValue="--全部状态--"></s:select>
		</td>
		<td>
			<input type="button" onClick="doSearch()" class="queryBtn" value=""/>
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">&nbsp;
	    	
        </th>
		<th align="center" width="17%">标题</th>
		<th align="center" width="7%">编码</th>
		<th align="center" width="8%">SKU编码</th>
		<th align="center" width="19%">基本信息</th>
		<th align="center" width="5%">状态</th>
		<th align="center" width="5%">成本价</th>
		<th align="center" width="5%">市场价</th>
		<th align="center" width="6%">销售单价</th>
		<th align="center" width="4%">重量</th>
		<th align="center" width="4%">PV值</th>
		<th align="center" width="4%">运费</th>
		<th align="center" width="6%">是否免邮</th>
		<th align="center" width="4%">操作</th>
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
			<s:iterator value="viewSkuAttrs" var="v" >
				<b><s:property value="#v.categoryAttrName" /></b>：<s:property value="#v.categoryAttrValue" />&nbsp;&nbsp;
			</s:iterator>
		</td>
		<td align="center" id="<s:property value='productSkuId'/>"><s:property value="#request.productStatusMap[status]" /></td>
		<td align="center"><s:property value="skuCostPrice" /></td>
		<td align="center"><s:property value="markPrice" /></td>
		<td align="center"><s:property value="price" /></td>
		<td align="center"><s:property value="unitWeight" /></td>
		<td align="center"><s:property value="pvValue" /></td>
		<td align="center"><s:property value="freight" /></td>
		<td align="center"><s:property value="#request.freeStatusMap[freeStatus]" /></td>
		<td align="center">
			<img title="编辑运费" style="cursor: pointer;" src="/etc/images/little_icon/huifu.png"
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
<script language="JavaScript">
function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}

function skuImageView(productSkuId){
	popDialog("/app/toEditSkuFreight.action?skuId="+productSkuId ,"编辑运费","800px","440px");
}

function saveSkuFreight(){
	closeThis();
	document.forms['frm'].submit();
}
</script>
</body>
</html>