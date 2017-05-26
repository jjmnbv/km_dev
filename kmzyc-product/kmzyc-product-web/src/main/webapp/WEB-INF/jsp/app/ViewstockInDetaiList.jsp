<%@page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script src="/etc/js/dialog.js"></script>



<script>
function goqueryPurInfoList(){
document.forms[0].action="/app/quertyInList.action";
document.forms[0].submit();


}


function gotoEdit(id,type){
var pageNum=$("#pageNum").val();
	var stockType = 0;
	stockType = type;
	if(parseInt(stockType)==1){//如果是采购入库
		location.href="/app/toEditPInDetail.action?type=purchaseStockIn&stockIn.stockInId="+id+"&";	
	}else{
		location.href="/app/toEditPInDetail.action?stockIn.stockInId="+id;	
	}

}


</script>


</head>
<body>

	<s:form action="queryStockOutDetailByStockOutId" method="POST"
		namespace='/app' id="stockInForm" name='stockInForm'>
		<!-- 查询条件区域 -->

		<!--主单id -->
       <input type="hidden"  id="stockOutNotExsist"  value="<s:property value='stockOutNotExsist' />"   />
        <input type="hidden"   name="billNo"  value="<s:property value='billNo'/>"  />
  		<input type="hidden" name="stockIn.stockInId"
			value="<s:property value='stockIn.stockInId'/>" />

		<table width="98%" class="content_table" align="center" height="100"
			cellpadding="0" cellspacing="0">

			<tr>
				<td width="12%"><strong>入库单号:</strong> <s:property
						value="stockIn.stockInNo" /></td>
				<td width="10%"><strong>入库日期:</strong> <s:date
						name="stockIn.stockInDate" format="yyyy-MM-dd" /></td>

				<td width="10%"><strong>仓库:</strong> <s:iterator
						value="#request.warehouseInfoMap" id="ware">
						<s:if test="#ware.key==stockIn.warehouseId">
							<s:property value="#ware.value" />
						</s:if>
					</s:iterator>
				</td>
				<td width="10%"><strong>状态：</strong> <s:if
						test="stockIn.status==0">未审核</s:if> <s:elseif
						test="stockIn.status==1">已审核</s:elseif> <s:else>已采购</s:else>
				</td>

				<td width="10%"><strong>入库类型:</strong> <s:iterator
						value="#request.stockInType" id="inType">
						<s:if test="#inType.key==stockIn.type">
							<s:property value="#inType.value" />
						</s:if>
					</s:iterator></td>

			</tr>

			<tr>
				<td width="10%"><strong>审核人姓名:</strong> <s:property
						value="stockIn.auditUserName" /></td>
				<td width="10%"><strong>审核日期:</strong> <s:date
						name="stockIn.auditDate" format="yyyy-MM-dd" /></td>
				<td width="10%"><strong>创建日期:</strong> <s:date
						name="stockIn.createDate" format="yyyy-MM-dd" /></td>
				<td width="10%"><strong>经手人:</strong> <s:property
						value="stockIn.userName" /></td>
				<td width="10%"><strong>录入人姓名:</strong> <s:property
						value="stockIn.createUserName" /></td>
			</tr>
			
		</table>
		<!-- 数据编辑区域 -->
		<table id="mytable" width="98%" class="list_table" align="center"
			cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2"
			style="border-collapse: collapse; font-size: 12px;">
			<!-- error message -->
			<s:if test="rtnMessage != null">
				<tr>
					<td colspan="7" align="center"><font color="red"><s:property
								value='rtnMessage' /> </font></td>
				</tr>
			</s:if>

			<tr>
				<th align="center" class="eidt_rowTitle" width="10%"   >产品SKU码</th>
				<th align="center" class="eidt_rowTitle" width="13%"       >产品名称</th>
				<th align="center" class="eidt_rowTitle"  width="10%"   >产品编号</th>
				<th align="center" class="eidt_rowTitle"  width="8%"   >入库数量</th>
				<th align="center" class="eidt_rowTitle"  width="8%"    >入库单价</th>
				<th align="center" class="eidt_rowTitle" width="10%" >小计</th>
				<th align="center" class="eidt_rowTitle" width="15%"      >备注</th>
			</tr>
			<s:iterator value="page.dataList">
				<tr>
					<td align="center"><s:property value="productSkuValue" /></td>
					<td align="center"><s:property value="productName" /></td>
					<td align="center"><s:property value="productNo" /></td>
					<td align="center"><s:property value="quantity" /></td>
					<td align="center"><s:property value="price" /></td>
					<td align="center"><s:property value="price*quantity" /></td>
					<td align="center"><s:property value="remark" /></td>
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


		<br />
		<br />
		

	</s:form>

</body>
<script   >

var stockOutNotExsist=$("#stockOutNotExsist").val();

if(stockOutNotExsist==1){
alert("资料信息不存在");
parent.closeThis();

}

</script>


</html>


