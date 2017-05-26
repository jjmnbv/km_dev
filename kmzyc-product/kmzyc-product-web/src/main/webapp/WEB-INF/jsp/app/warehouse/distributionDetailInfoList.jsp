<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配送细目单管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js"
	type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="/etc/js/warehouse/distributionInfo.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>

</head>
<body>

<s:set name="parent_name" value="'配送细目单管理'" scope="request" />
<s:set name="name" value="'配送细目单列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

	
<s:form  action="toDistributionDetails" method="post"  namespace='/app' id="distributionInfofrm" name='distributionInfofrm'>
	<input id="distributionId" type="hidden" value="<s:property value='distributionInfo.distributionId'/>" name="distributionId" />
<table  width="98%" class="content_table" align="center" height="100" cellpadding="0" cellspacing="0" >		
	<tr>
	  <td width="10%"><strong>配送单号:</strong><s:property value="distributionInfo.distributionNo"/></td>
	  <td width="10%"><strong>配送日期:</strong><s:date name="distributionInfo.logisticsDate" format="yyyy-MM-dd" /></td>
	  <td width="10%"><strong>物流单位:</strong><s:property value="distributionInfo.logisticsCompany"/> </td>
	  <td width="10%"><strong>物流单号:</strong><s:property value="distributionInfo.logisticsNo"/> </td>
	  <td width="10%"><strong>仓库:</strong>
			<s:iterator value="#request.warehouseInfoMap" id="ware">
				<s:if test="#ware.key==distributionInfo.warehouseId">
					<s:property value="value"/>
				</s:if>
			</s:iterator>
	  </td>
	   <td width="10%"><strong>状态：</strong>
				<s:iterator value="#request.distriButionInfoMap" id="stat">
						<s:if test="#stat.key==distributionInfo.isDeliveries">
							<s:property value="value"/>
						</s:if>
				</s:iterator>
      </td>
      </tr>
     
      
     
     <tr>
	  <td width="10%"><strong>出库单号:</strong><s:property value="distributionInfo.billNo"/> </td>
	  <td width="10%"><strong>配送总数:</strong><s:property value="distributionInfo.totalQuantity"/> </td>
	  <td width="10%"><strong>配送总额:</strong><s:property value="distributionInfo.totalSum"/> </td>
	  <td width="10%"><strong>修改人:</strong><s:property value="distributionInfo.createUserName"/> </td>
	  <td width="10%"><strong>修改日期:</strong><s:date name="distributionInfo.createDate" format="yyyy-MM-dd" /></td>
	</tr>

    <tr>
      <td><strong>订单号:</strong><s:property value="distributionInfo.orderNo"/> </td>
      <td width="10%"><strong>收货人:</strong><s:property value="distributionInfo.userName"/> </td>
	  <td width="10%"><strong>收货电话:</strong><s:property value="distributionInfo.tel"/> </td>
	  <td colspan="2"><strong>收货地址:</strong><s:property value="distributionInfo.deliveryAddress"/> </td>
    </tr>
</table>	
	<!-- 数据编辑区域 -->
	<table width="98%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">	
		<!-- error message -->
		
	
		
		<s:if test="rtnMessage != null">
			<tr>
				<td colspan="8" align="center"><font color="red"><s:property
					value='rtnMessage' /></font></td>
			</tr>
		</s:if>
		
		<tr>
			<th  align="center" class="eidt_rowTitle">产品SKU码</th>
			<th  align="center" class="eidt_rowTitle">产品标题</th>
			<th  align="center" class="eidt_rowTitle">产品编号</th>
			<th  align="center" class="eidt_rowTitle">配送数量</th>
			<th  align="center" class="eidt_rowTitle">配送单价</th>
			<th  align="center" class="eidt_rowTitle">小计</th>
			<th  align="center" class="eidt_rowTitle">生产批次号</th>
			<th  align="center" class="eidt_rowTitle">备注</th>
		</tr>
		<s:iterator  value="page.dataList" >
			<tr>
				<td align="center"><s:property value="productSkuValue" /></td>					
				<td align="center">
					<s:property value='product.productTitle'/>
					<font color="red">
						<s:if test="product.productType==1">[药品]</s:if>
						<s:elseif test="product.productType==2">[医疗器械]</s:elseif>
					</font>
				</td>
				<td align="center"><s:property value="productNo" /></td>
				<td align="center"><s:property value="quantity" /></td>
				<td align="center"><s:property value="price" /></td>
				<td align="center"><s:property value="sum" /></td>
				<td align="center"><font color="red"><s:property value="batchNo" /></font></td>
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


	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center">
				<input type="button" class="btn-custom btnStyle" value="返回" onClick="gotodistributionInfoList();" />
			<s:if test="distributionInfo.isDeliveries==0">
		           <input class="btn-custom btnStyle"  type="button" value="修改" onClick="toEditdistributionInfoDetail(<s:property value='distributionInfo.distributionId'/>);" />
		    </s:if>
		    </td>
		</tr>
	</table>
	<br/> 	<br/>

	<s:hidden name="checkedId" id="checkedId"/>
  <input   type="hidden"   name="queryDistribution.isDeliveries"  value="<s:property value='queryDistribution.isDeliveries'/>"   />
  <input   type="hidden"   name="queryDistribution.logisticsDate"  value="<s:property value='queryDistribution.logisticsDate'/>"   />
 <input   type="hidden"   name="endDate"  value="<s:property value='endDate'/>"   />
 <input   type="hidden"   name="queryDistribution.warehouseId"  value="<s:property value='queryDistribution.warehouseId'/>"   />
 <input   type="hidden"   name="queryDistribution.distributionNo"  value="<s:property value='queryDistribution.distributionNo'/>"   />
 <input   type="hidden"   name="queryDistribution.orderNo"  value="<s:property value='queryDistribution.orderNo'/>"   />

</s:form>

</body>
</html>


