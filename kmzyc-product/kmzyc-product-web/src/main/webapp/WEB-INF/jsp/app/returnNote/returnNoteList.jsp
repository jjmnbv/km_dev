<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<style type="text/css">
	.tableStyle1{font-size:12px;}
</style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>


<s:set name="parent_name" value="'退货管理'" scope="request" />
<s:set name="name" value="'退货单列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/findAllReturnNotes.action" method="POST"  namespace='/app' id="frm" name='frm' >

<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />

<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert(document.getElementById("rtnMsg").value);
	//-->
	</SCRIPT>
</s:if>
<!-- 查询条件区域 -->
<table width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr> 
		<!-- 根据查询字段的多少判断colspan-->
	    
	</tr>
	<tr>
		<td align="right">
			退换货单编号：
		</td>
		<td align="left">
			<input name="order.orderCode" id="orderId" class="input_style" type="text" value="<s:property value='order.orderCode'/>">
		</td>
		<td align="right">
			产品SKU编号：
		</td>
		<td align="left">
			<input name="order.productSku" class="input_style" type="text" value="<s:property value='order.productSku'/>">
		</td>
	</tr>
	<tr>
		<td align="right">
			单据状态：
		</td>
		<td align="left">
			<s:select name="order.status" list="#request.statusMap" headerKey="" headerValue="--全部--" ></s:select>
		</td>
		<td align="right">
			单据处理结果：
		</td>
		<td align="left">
			<s:select name="order.handleResult" list="#request.handleResultMap" headerKey="" headerValue="--全部--" ></s:select>		
		</td>
        <td >
            <INPUT TYPE="button" onClick="doSearch()" class="queryBtn" value="">
			<!-- 删除 <input class="delBtn" type="button" value=""  onclick="deleteSelected('delId');"> -->
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table id="mytable" width="98%" class="list_table"  cellpadding="3" align="center" cellspacing="0" border="1">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox' onclick='checkAll(this)'>
		</th>
		<th align="center" width="15%">退换货单编号</th>
		<th align="center" width="15%">产品名称</th>
		<th align="center" width="10%">产品编号</th>
		<th align="center" width="10%">产品SKU</th>
		<th align="center" width="10%">退货数量</th>
		<th align="center" width="10%">类型</th>
		<th align="center" width="10%">单据状态</th>
		<th align="center" width="10%">单据处理结果</th>
		<th align="center" width="10%">操作</th>
	</tr>
	<s:iterator id="custiterator" value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox" name="delId"  value='<s:property value="returnId"/>'>
		</td>
		<td align="center"><s:property value="orderCode"/></td>
		<td align="center"><s:property value="productName"/></td>
		<td align="center"><s:property value="productNo"/></td>
		<td align="center">
			<s:property value="productSku"/>
		</td>
		<td align="center">
			<s:property value="productCounts"/>
		</td>
		<td align="center">
			<s:if test="orderType == 1">
				退货
			</s:if>
			<s:else>
				换货
			</s:else>
		</td>
		<td align="center">
			<s:property value="#request.statusMap[status]"/>
		</td>
		<td align="center">
			<s:property value="#request.handleResultMap[handleResult]"/>
		</td>
		<td align="center">
			<s:if test='handleResult >= 2'>
				<img title="处理" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="gotoView(<s:property value='returnId'/>,<s:property value='handleResult'/>)" />
			</s:if>
			<s:else>
				<img title="查看" style="cursor: pointer;" src="/etc/images/checking.png"  onclick="gotoView(<s:property value='returnId'/>,<s:property value='handleResult'/>)" />
			</s:else>
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
//返回我的桌面界面

function gotoView(id,result){
    location.href="/app/gotoReturnNotesEdit.action?returnId="+id+"&handleResult="+result;
}

function doDelete(name){
	document.forms['frm'].action="/app/deleteProdAppraise.action";
	document.forms['frm'].submit();
}

function doSearch(){
	
	var _v  = document.getElementById("orderId").value;
	
	if(_v!=""&&!/^\d+$/.test(_v)){
		alert("订单编号必须为数字！");
		document.getElementById("orderId").select();
		return;
	}
	
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}


</SCRIPT>

</BODY>
</HTML>