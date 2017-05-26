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
<script src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script src="/etc/js/common.js"></script>

</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">
	    	&nbsp;
        </th>
		<th align="center" width="15%">名称</th>
		<th align="center" width="10%">编码</th>
		<th align="center" width="10%">SKU编码</th>
		<th align="center" width="30%">基本信息</th>
		<th align="center" width="15%">状态</th>
		<th align="center" width="5%">操作</th>
	</tr>
	<s:iterator id="productiterator" value="#request.productSkuList" status="st" >
	<tr>
	    <td align="center" width="5%">
			<s:property value="#st.index + 1" />    
		</td>
		<td align="center"><s:property value="procuctName" /></td>
		<td align="center"><s:property value="productNo" /></td>
		<td align="center"><s:property value="productSkuCode" /></td>
		<td align="center">
			<s:iterator value="viewSkuAttrs" var="v" >
				<b><s:property value="#v.categoryAttrName" /></b>：<s:property value="#v.categoryAttrValue" />&nbsp;&nbsp;
			</s:iterator>
		</td>
		<td align="center" id="<s:property value='productSkuId'/>"><s:property value="#request.productStatusMap[status]" /></td>
		<td align="center">
			<img title="添加图片" style="cursor: pointer;" src="/etc/images/button_new/modify.png"  onclick="gotoUpdate(<s:property value='productSkuId'/>)" />
		</td>
	</tr>
	</s:iterator>
</table>

<script language="JavaScript">
function gotoUpdate(id){
    location.href="/basedata/toUpdateProdImage.action?productSkuId="+id+"&type=updatePage";
}
</script>
</body>
</html>