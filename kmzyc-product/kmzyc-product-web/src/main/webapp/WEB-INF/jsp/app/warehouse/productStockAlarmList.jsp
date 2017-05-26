<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>库存管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
	.tableStyle1{font-size:12px;}
</style>
<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/warehouse/productStock.js"></script>

<script type="text/javascript">

</script>

</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:set name="parent_name" value="'库存管理'" scope="request" />
<s:set name="name" value="'告警库存列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/stockShowAlarm.action" method="POST"  namespace='/app' id="frm" name='frm'>
<s:hidden name="checkedId" id="checkedId"/>
<s:hidden name="type" id="stockType"></s:hidden>
<input type="hidden" id="rtnMsg" value="${msg}" />

<!-- 查询条件区域 -->
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	
	<tr> 
		<!-- 根据查询字段的多少判断colspan-->
	    <td width="80%" valign="middle" colspan="4">
           
       	</td>
	</tr>
	
	<tr>
	  <td>SKU编码：
	     <s:textfield name="stockForSelectPara.skuAttValue" cssClass="input_style" id="skuAttValue" />	  </td>
	  <td>产品标题：
	    <s:textfield name="stockForSelectPara.product.productTitle" cssClass="input_style" /></td>
	  <td>产品编码：
	  	<s:textfield name="stockForSelectPara.productNo" cssClass="input_style" id="productNo" />
	  </td>
	  <td>仓库选择：
	  	<s:select list="#request.warehouseInfoMap" name="stockForSelectPara.warehouseId" headerKey="" headerValue="--选择仓库--"></s:select>
	  </td>
    </tr>
    <tr>
    	<td>库存数量：
    		大于等于<s:textfield name="stockForSelectPara.beginQuantity" size="5" cssClass="input_style" /> 小于 
    		<s:textfield name="stockForSelectPara.endQuantity" size="5" cssClass="input_style" />
    	</td>
    	<td>
			<INPUT TYPE="button" onClick="doSearch()" class="queryBtn" value="">
            <INPUT class="addBtn" TYPE="button" value="" onClick="gotoAdd();">		
		</td>
    </tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		<th></th>
		<th align="center" width="12%">所属仓库</th>
		<th align="center" width="20%">产品标题</th>
		<th align="center" width="12%">产品编号</th>
		<th align="center" width="12%">SKU编码</th>
		<th align="center" width="">库存数量</th>
		<th align="center" width="">告警数量</th>
		<th align="center" width="">订购数量</th>
		<th align="center" width="">在途数量</th>
		<th align="center" width="15%">操作</th>
	</tr>
	<s:iterator id="stockiterator" value="page.dataList">
	<tr>
		<td><input type="hidden" name="stockIdChk" value='<s:property value="stockId"/>'></td>
		<td align="center"><s:property value='#request.warehouseInfoMap[warehouseId]' /></td>
		<td align="center"><s:property value="product.productTitle" escape="false"/></td>
		<td align="center"><s:property value="productNo" /></td>
		<td align="center"><s:property value="skuAttValue" /></td>
		<td align="center"><font color="red"><s:property value="stockQuality" /></font></td>
		<td align="center"><s:property value="alarmQuality" /></td>
		<td align="center"><s:property value="orderQuality" /></td>
		<td align="center"><s:property value="inTransitQuality" /></td>
		<td align="center">
			<img title="修改" style="cursor: pointer;" src="/etc/images/button_new/modify.png"  onclick="gotoUpdate(<s:property value='stockId'/>)" />
			<img title="查看" style="cursor: pointer;" src="/etc/images/button_new/select.png"  onclick="gotoViewStock(<s:property value='stockId'/>)" />		</td>
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
<s:if test='!msg.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
		alert(document.getElementById("rtnMsg").value);
	</SCRIPT>
</s:if>
</s:form>
<s:form action="/app/stockShow.action" method="POST"  namespace='/app' id="stockShowForm" name='stockShowForm'>
	<s:hidden name="checkedId" id="checkedId"/>
</s:form>
</BODY>
</HTML>
