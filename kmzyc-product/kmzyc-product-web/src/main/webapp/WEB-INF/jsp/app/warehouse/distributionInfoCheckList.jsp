<%@page contentType="text/html;charset=UTF-8"%>
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
<title>配送单管理</title>
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
<body >

<s:set name="parent_name" value="'配送单管理'" scope="request" />
<s:set name="name" value="'配送单审核'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	
<s:form action="distriListForCheck" method="post"  namespace='/app' id="distributionInfofrm" 
	name='distributionInfofrm' onsubmit="return Validator.Validate(this,3);">
	<!-- 查询条件区域 -->	
	<s:hidden name="checkedId" id="checkedId"/>
<table  width="98%" class="content_table" align="center" height="100" cellpadding="0" cellspacing="0" >		
	<tr> 
		<!-- 根据查询字段的多少判断colspan-->
	    <td width="80%" valign="middle" colspan="5">
          	<!-- 审核按钮-->
     		 <input class="btnStyle" type="button" value="审核" onclick="toCheckDistrubution();" />
		</td>
	</tr>

	<tr>
       <td >
	          配送日期: <input id="d4311" value ="<s:date name='queryDistribution.logisticsDate' format='yyyy-MM-dd' />" class="Wdate" readOnly="readOnly" name="queryDistribution.logisticsDate" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'%y-%M-%d\'}',dateFmt:'yyyy-MM-dd'})"/>
		</td>
	   <td>
		至:<input value="<s:date name='endDate' format='yyyy-MM-dd'/>" id="d4312"  class="Wdate" readOnly="readOnly"  name="endCreateDate" dataType="Date" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"/>
		</td>
		 <td>仓库:
		  	<s:select list="#request.warehouseInfoMap" id="warehouseId" name="queryDistribution.warehouseId"  headerKey="" headerValue="--请选择仓库--"></s:select>		  
		  </td>
    </tr>
	<tr>
	 <td>配送单号:<input    name="queryDistribution.distributionNo" id="distributionNo"  value="<s:property value='queryDistribution.distributionNo' />"   /></td>
	  <td>订(退)单号:<input name="queryDistribution.orderNo" id="orderNo"     value="<s:property value='queryDistribution.orderNo' />"         /></td>  
	  <td><input class="queryBtn" type="button" onclick="searchDistributionInfo();"  />	</td>
    </tr>  
	  
    
</table>

	<!-- 数据编辑区域 -->
<table width="98%" class="list_table" id="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
		<!-- error message -->
		<s:if test="rtnMessage != null">
			<tr>
				<td colspan="2" align="center"><font color="red"><s:property
					value='rtnMessage' /></font></td>
			</tr>
		</s:if>
		
		<tr>
		 	<th align="center" width="4%">
           		 <input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)'>	
            </th>
			<th width="12%" align="center" >配送单号</th>
			<th width="8%" align="center"  >仓库</th>
			<th width="8%"  align="center" >物流单位</th>
			<th width="10%" align="center"  >物流单号</th>
			<th width="6%"  align="center" >配送总数</th>
			<th width="10%"  align="right" >配送总额</th>
			<th width="10%" align="center" >出库单号</th>
			<th width="8%"  align="center" >收货人</th>
			<th width="10%" align="center" >创建日期</th>
			<th width="8%" align="center" >送达</th>
			<th width="8%" align="center"  >操作</th>
		</tr>
		<s:iterator id="pdata" value="page.dataList" >
			<tr>
		
			 	<s:if test="isDeliveries==0 && (null != logisticsNo)">
		   	 	 <td align="center" width="4%"><input type="checkbox" name="distributionInfoChk"  value='<s:property value="distributionId"/>'>	 </td>
		   	 	</s:if>
		    <s:elseif test="isDeliveries!=0" > <td align="center" width="4%"><input type="checkbox" name="distributionInfoChk" disabled="true" value='<s:property value="distributionId"/>'>	 </td></s:elseif>
		    <s:else><td></td></s:else>
		   
		   
				<td align="center"><s:property value="distributionNo" /></td>
				<td align="center">
					<s:iterator value="#request.warehouseInfoMap" id="ware">
						<s:if test="#ware.key==#pdata.warehouseId">
							<s:property value="value"/>
						</s:if>
					</s:iterator>
			    </td>
				<td align="center"><s:property value="logisticsCompany" /></td>
				<td align="center"><s:property value="logisticsNo" /></td>
				<td align="right"><s:property value="totalQuantity" /></td>
				<td align="right"><s:property value="totalSum" /></td>
				<td align="center"><s:property value="billNo" /></td>
				<td align="center"><s:property value="userName" /></td>
				<td align="center"><s:date name="logisticsDate" format="yyyy-MM-dd" /></td>
				<td align="center">
					<s:iterator value="#request.distriButionInfoMap" id="stat">
						<s:if test="#stat.key==isDeliveries">
							<s:property value="value"/>
						</s:if>
					</s:iterator>
				</td>
				<td align="center">
					<img title="查看" style="cursor: pointer;" src="/etc/images/view.png"  onclick="searchCheckDistribuDetails(<s:property value='distributionId'/>)" />
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


