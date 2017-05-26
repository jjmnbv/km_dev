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
<Script language="JavaScript" src="/etc/js/Form.js"
	type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript" src="/etc/js/warehouse/stockOut.js"></script>
</head>
<body>

<s:form action="queryStockOutDetailByStockOutId" method="post"  namespace='/app' id="stockOutfrm" name='stockOutfrm'>
	<!-- 查询条件区域 -->	
<table  width="98%" class="content_table" align="center" height="100" cellpadding="0" cellspacing="0" >		
  
  <input type="hidden"  id="stockOutNotExsist"  value="<s:property value='stockOutNotExsist' />"   />
  <input  type="hidden"  name="billNo"  value="<s:property value='billNo'/>" />

	<input id="stockOutId" type="hidden" value="<s:property value='stockOut.stockOutId'/>" name="stockOutId" />
	<tr>
	  <td width="10%"><strong>出库单号:</strong><s:property value="stockOut.stockOutNo"/></td>
	  <td width="10%"><strong>出库日期:</strong><s:date name="stockOut.modifiyDate" format="yyyy-MM-dd" /></td>
	  <td width="10%"><strong>类型:</strong>
	  		<s:iterator value="#request.stockOutTypeMap" id="stockType">
				<s:if test="#stockType.key==stockOut.type">
					<s:property value="value"/>
				</s:if>
			</s:iterator>
	  </td>
	  <td width="10%"><strong>仓库:</strong>
			<s:iterator value="#request.warehouseInfoMap" id="ware">
				<s:if test="#ware.key==stockOut.warehouseId">
					<s:property value="value"/>
				</s:if>
			</s:iterator>
	  </td>
	   <td width="10%"><strong>状态：</strong>
				<s:iterator value="#request.stockOutStatusMap" id="stoc">
						<s:if test="#stoc.key==stockOut.status">
							<s:property value="value"/>
						</s:if>
				</s:iterator>
      </td>
	</tr>
	  
	  <tr>
	  <td width="10%"><strong>创建人:</strong><s:property value="stockOut.createUserName"/></td>
	  <td width="10%"><strong>创建日期:</strong><s:date name="stockOut.createDate" format="yyyy-MM-dd" /></td>
	  <td width="10%"><strong>经手人:</strong><s:property value="stockOut.userName"/></td>
	  <td width="10%"><strong>审核人:</strong><s:property value="stockOut.checkUserName"/></td>
	  <td width="10%"><strong>审核日期:</strong><s:date name="stockOut.auditDate" format="yyyy-MM-dd" /></td>
    </tr><!--
    <tr>
	  <td colspan="5"><strong>备注:</strong><s:property value="stockOut.remark"/></td>
    </tr>
--></table>	
	<!-- 数据编辑区域 -->
	<table width="98%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">	
		<!-- error message -->
		<s:if test="rtnMessage != null">
			<tr>
				<td colspan="8" align="center"><font color="red">
				<s:property	value='rtnMessage' /></font></td>
			</tr>
		</s:if>
		
		<tr>
			<th  align="center" class="eidt_rowTitle">产品SKU码</th>
			<th  align="center" class="eidt_rowTitle">产品名称</th>
			<th  align="center" class="eidt_rowTitle">产品编号</th>
			<th  align="center" class="eidt_rowTitle">条形码</th>
			<th  align="center" class="eidt_rowTitle">出库数量</th>
			<th  align="center" class="eidt_rowTitle">出库单价</th>
			<th  align="center" class="eidt_rowTitle">小计</th>
			<th  align="center" class="eidt_rowTitle">备注</th>
		</tr>
		<s:iterator  value="page.dataList" >
			<tr>
				<td align="center"><s:property value="productSkuValue" /></td>					
				<td align="center"><s:property value="productName" /></td>
				<td align="center"><s:property value="productNo" /></td>
				<td align="center"><s:property value="barCode" /></td>
				<td align="center"><s:property value="quantity" /></td>
				<td align="center"><s:property value="price" /></td>
				<td align="center"><s:property value="sum" /></td>
				<td align="center"><s:property value="remark" /></td>
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


