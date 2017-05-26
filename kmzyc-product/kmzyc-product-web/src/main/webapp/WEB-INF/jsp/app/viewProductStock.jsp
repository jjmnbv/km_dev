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
<script src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>

</head>
<body>


	<s:form action="/app/quertyInList.action" method="POST"
		namespace='/app' id="stockInfrm" name='stockInfrm'>
		<s:hidden name="checkedId" id="checkedId" />
		  <input type="hidden"  id="stockOutNotExsist"  value="<s:property value='stockOutNotExsist' />"   />
		<!-- 查询条件区域 -->
		<table width="98%" class="content_table" align="center" height="100"
			cellpadding="0" cellspacing="0">


		</table>
		<!-- 数据编辑区域 -->
		<table id="mytable" width="98%" class="list_table" align="center"
			cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
			<!-- error message -->
			<s:if test="rtnMessage != null">
				<tr>
					<td colspan="2" align="center"><font color="red"><s:property
								value='rtnMessage' /> </font>
					</td>
				</tr>
			</s:if>

			<tr>
				
				<th width="10%" align="center">产品名称</th>
				<th width="10%" align="center">产品编号</th>
				<th width="10%" align="center">产品货号</th>
				<th width="8%" align="center">仓库</th>
				<th width="8%" align="center">库存数量</th>
				<th width="6%" align="center">订购数量</th>
				<th width="10%" align="right">销售总量</th>
				
				
			</tr>

			<tr>


				<td align="center"><s:property value="productStock.productName" />
			


				<td align="center"><s:property value="productStock.productNo" />
				</td>
				<td align="center"><s:property value="productStock.skuAttValue" />
				</td>
				<td align="center"><s:iterator
							value="#request.warehouseInfoMap" id="ware">
							<s:if test="#ware.key==productStock.warehouseId">
								<s:property value="#ware.value" />
							</s:if>
						</s:iterator>
				</td>
				<td align="right"><s:property value="productStock.stockQuality" />
				</td>
				<td align="center"> <s:property value="productStock.orderQuality" /></td>


				<td align="center"><s:property value="productStock.totalSales" />
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


