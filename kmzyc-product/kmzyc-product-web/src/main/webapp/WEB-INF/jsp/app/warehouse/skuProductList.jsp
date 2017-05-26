<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择地区</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-1.8.3.js"></script>

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

<form action="/app/findAllSkuProduct.action?type=stock" method="POST"  namespace='/basedata'  id="frm" name='frm' >
<br />
<table width="90%" class="table_search" align="center" cellpadding="0" cellspacing="0" style="border-collapse: collapse;font-size:12px" >
	<tr>
		<td><input TYPE="button" class="btngreen" value=" 保存所选 " style="height:30px" onClick="selectList();"/></td>
		<td align="right">
			标题：<s:textfield name="viewProductSku.productTitle" cssClass="input_style" />
			&nbsp;&nbsp;&nbsp;
			SKU编码：<s:textfield cssClass="input_style" name="viewProductSku.productSkuCode" id="keyword" />
			&nbsp;&nbsp;&nbsp;
			<INPUT TYPE="button" onClick="doSearch()" class="btngray" value="查询"/>
		</td>
	</tr>
</table>
<br />

<!-- 数据列表区域 -->
<table id="dataList" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
	<tr>
	    <th bgcolor="#99ccff" align="center" width="5%">&nbsp;</th>
		<th bgcolor="#99ccff" align="center" width="15%">标题</th>
		<th bgcolor="#99ccff" align="center" width="15%">名称</th>
		<th bgcolor="#99ccff" align="center" width="10%">编码</th>
		<th bgcolor="#99ccff" align="center" width="15%">SKU编码</th>
		<th bgcolor="#99ccff" align="center" width="10%">状态</th>
	</tr>
	<s:iterator id="productiterator" value="page.dataList" status="st" >
	<tr>
	    <td bgcolor="#FFFFCC" align="center">
	    <input type="checkbox" name="checkSkuId" >
	    <div style= 'display:none'><s:property value="productSkuId"/>^<s:property value="productSkuCode" />^<s:property value="productId" />^<s:property value="procuctName" escape="false" />^<s:property value="productNo" /></div>
	    </td>
		<td align="center" bgcolor="#FFFFCC" style="word-break:break-all" ><s:property value="productTitle" escape="false" /></td>
		<td align="center" bgcolor="#FFFFCC" style="word-break:break-all" ><s:property value="procuctName" escape="false" /></td>
		<td align="center" bgcolor="#FFFFCC" style="word-break:break-all" ><s:property value="productNo" /></td>
		<td align="center" bgcolor="#FFFFCC" style="word-break:break-all" ><s:property value="productSkuCode" /></td>
		<td align="center" bgcolor="#FFFFCC" style="word-break:break-all"  id="<s:property value='productSkuId'/>"><s:property value="#request.productStatusMap[status]" /></td>
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
</form>

<script type="text/javascript">

function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}

function selectList(){
	if($("input[type='checkbox']:checked").length!=1){
		alert("只能选择一项！");
		return;
	}
	
	var str =  $("input[type='checkbox']:checked").parent().children("div").text().split('^');//$("input[type='checkbox']:checked").val().split("^");
	var skuAttributeId = str[0];
	var skuAttValue = str[1];
	var productId = str[2];
	var productName = str[3];
	var productNo = str[4];
	parent.closeOpenSku(skuAttributeId,skuAttValue,productId,productName,productNo);
}

</script>
</body>
</html>

