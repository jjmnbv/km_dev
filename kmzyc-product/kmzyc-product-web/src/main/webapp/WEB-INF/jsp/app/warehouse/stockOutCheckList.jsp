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
<title>出库单管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js"
	type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="/etc/js/warehouse/stockOut.js"></script>


</head>
<body >

<s:set name="parent_name" value="'出库单管理'" scope="request" />
<s:set name="name" value="'出库单审核列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	
<s:form action="toStockOutCheck" method="post"  namespace='/app' id="stockOutfrm" 
	name='purchaseInfofrm' onsubmit="return Validator.Validate(this,3);">
	<!-- 查询条件区域 -->	
	<s:hidden name="checkedId" id="checkedId"/>
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >	
	<tr>
		<td>
		 	<!-- 审核按钮-->
     	 
		</td>
	</tr>

	<tr>
	 <td>仓库:
	  	<s:select list="#request.warehouseInfoMap"   name="queryStockOut.warehouseId"  id="warehouseId1" headerKey="" headerValue="--请选择仓库--"/>
	  </td>
       <td >
	           创建日期: <input id="d4311" value ="<s:date name='queryStockOut.createDate' format='yyyy-MM-dd' />" class="Wdate" readOnly="readOnly" name="queryStockOut.createDate" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'%y-%M-%d\'}',dateFmt:'yyyy-MM-dd'})"/>
		</td>
	   <td>
		至: <input type="text" value="<s:date name='endDate' format='yyyy-MM-dd'/>" id="d4312"  class="Wdate" readOnly="readOnly"  name="endDate" dataType="Date" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"/>
		</td>
	   
	</tr> 
    <tr> 
     <td >
	          类型:
              <s:select list="#request.stockOutTypeMap" name="queryStockOut.type"  id="stockOuttype" headerKey="" headerValue="--全部类型--"></s:select>
	</td>
	<td>
		出库单号:&nbsp;<input type="text" name="queryStockOut.stockOutNo" id="stockOutNo"  value="<s:property value='queryStockOut.stockOutNo'/>"/>
	</td>
	<td>
		订(退)单号:&nbsp;<input type="text" name="queryStockOut.billNo" id="billNo"   value="<s:property value='queryStockOut.billNo' />"      />
	</td>
	  <td><input class="queryBtn" type="button" onClick="searchStockOut();"  />	<input class="btn-custom btnStyle" type="button" value="审核" onClick="gotoCheckStockOut();" /></td>
	</tr>
</table>	
	<!-- 数据编辑区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
		<!-- error message -->
		<s:if test="rtnMessage != null">
			<tr>
				<td colspan="2" align="center"><font color="red"><s:property
					value='rtnMessage' /></font></td>
			</tr>
		</s:if>
		
		<tr>
		 	<th align="center" width="3%">
           		 <input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)'>	
            </th>
			<th width="12%" align="center" >出库单号</th>
			<th width="10%" align="center" >类型</th>
			<th width="12%" align="center" >仓库</th>
			<th width="12%"  align="center" >订单编号</th>
			<th width="9%"  align="center" >出库总数</th>
			<th width="10%" align="center"  >创建人</th>
			<th width="12%" align="center" >创建日期</th>
			<th width="10%"  align="center" >状态</th>
			<th width="10%" align="center"  >操作</th>
		</tr>
		<s:iterator id="pdata" value="page.dataList" >
			<tr>
			
			 	<s:if test="status==0">
		   	 	 <td align="center" width="3%"><input type="checkbox" name="stockOutChk"  value='<s:property value="stockOutId"/>'></td>
		   	 	</s:if>
		   	 	
		   	 	<s:elseif test="status!=0"> <td align="center" width="3%"><input type="checkbox" disabled="true"   name="stockOutChk"  value='<s:property value="stockOutId"/>'></td></s:elseif>
		   	 	
		   	 	
		   	 
				<td align="center"><s:property value="stockOutNo" /></td>
				<td align="center"><s:property value="#request.stockOutTypeMap[type]"/></td>
				<td align="center"><s:property value="#request.warehouseInfoMap[warehouseId]"/> </td>
				<td align="center"><s:property value="billNo" /></td>
				<td align="center"><s:property value="totalQuantity" /></td>
				<td align="center"><s:property value="createUserName" /></td>
				<td align="center"><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td align="center"><s:property value="#request.stockOutStatusMap[status]" /></td>
				<td align="center">
					<img title="查看" style="cursor: pointer;" src="/etc/images/view.png"  onclick="toCheckStockOutDetail(<s:property value='stockOutId'/>)" />
				</td>
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
</html>


