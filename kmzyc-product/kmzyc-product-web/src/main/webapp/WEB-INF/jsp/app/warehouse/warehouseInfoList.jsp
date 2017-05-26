<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>仓库管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
	.tableStyle1{font-size:12px;}
</style>
<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/warehouse/warehouseInfo.js"></script>

<script type="text/javascript">

</script>

</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:set name="parent_name" value="'仓库管理'" scope="request" />
<s:set name="name" value="'仓库列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/warehouseShow.action" method="POST"  namespace='/app' id="frm" name='frm'>
<s:hidden name="checkedId" id="checkedId"/>
<input type="hidden" id="rtnMsg" value="${msg}" />
<!-- 查询条件区域 -->
<table  width="98%" class="content_table" align="center"cellpadding="0" cellspacing="0" >
	

	
	<tr>
	  <td>仓库编码：
	     <s:textfield name="warehouseForSelectPara.warehouseNo" cssClass="input_style" id="warehouseNo" />
	  </td>
	  <td>名称：<s:textfield name="warehouseForSelectPara.warehouseName" cssClass="input_style" id="warehouseName" /></td>
	  <td>状态：
		  <s:select list="#request.warehouseStatusMap" name="warehouseForSelectPara.status" id="status" headerKey="" headerValue="--全部状态--"></s:select>
      </td>
    </tr>
	<tr>
		<td>所在地区：
		  	<s:select list="#request.areaList" id="areaId1" name="warehouseForSelectPara.pAreaId" listKey="areaId" listValue="areaName" headerKey="" headerValue="--请选择省份--"
		  		onchange="choiceArea('areaId1','areaId2')"></s:select>
		  	<s:select list="#request.cAeaList" id="areaId2" name="warehouseForSelectPara.areaId"
		  		 listKey="areaId" listValue="areaName" 
		  		 headerKey="" headerValue="--请选择城市--"></s:select>
		</td>
		<td>覆盖区域：
		  <s:select list="#request.areaList" name="warehouseForSelectPara.overlayAreaId" listKey="areaId" listValue="areaName" headerKey="" headerValue="--请选择城市--">
		  </s:select>
		<td align="right">
			<INPUT TYPE="button" onClick="doSearch()" class="queryBtn" value="">
             <INPUT class="addBtn" TYPE="button" value="" onClick="gotoAdd();">
           <INPUT class="btn-custom btnStyle" TYPE="button" value="+ 启用" onClick="startUp();">
           <INPUT class="btn-custom btnStyle" TYPE="button" value="- 停用" onClick="stopDown();">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)'>		</th>
		<th align="center" width="15%">仓库名称</th>
		<th align="center" width="15%">编码</th>
		<th align="center" width="10%">状态</th>
		<th align="center" width="15%">所在地区</th>
		<th align="center" width="15%">覆盖区域</th>
		<th align="center" width="20%">操作</th>
	</tr>
	<s:iterator id="warehouseInfoiterator" value="page.dataList">
	<tr>
	    <td align="center" width="5%"><input type="checkbox" name="warehouseChk"  value='<s:property value="warehouseId"/>_<s:property value="status"/>'></td>
		<td align="center"><s:property value="warehouseName" /></td>
		<td align="center"><s:property value="warehouseNo" /></td>
		<td align="center"><s:property value="#request.warehouseStatusMap[status]" /></td>
		<td align="center"><s:property value="areaName" /></td>
		<td align="center"><s:property value="overlayArea" /></td>
		<td align="center">
			<img title="修改" style="cursor: pointer;" src="/etc/images/button_new/modify.png"  onclick="gotoUpdate(<s:property value='warehouseId'/>)" />
			<img title="查看" style="cursor: pointer;" src="/etc/images/button_new/select.png"  onclick="gotoViewWarehouse(<s:property value='warehouseId'/>)" />		</td>
	</tr>
	<s:property value="#request.warehouseInfoMap[warehouseId]" />
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
<s:form action="/app/warehouseStart.action" method="POST"  namespace='/app' id="startForm" name='startForm'>
</s:form>
<s:form action="/app/warehouseStop.action" method="POST"  namespace='/app' id="stopForm" name='stopForm'>
</s:form>
<s:form action="/app/warehouseShow.action" method="POST" namespace='/app' id="warehouseListForm" name='warehouseListForm'>
	<s:hidden name="checkedId" id="checkedId"/>
	<s:hidden name="warehouseForSelectPara.status" id="warehouseStatus"/>
</s:form>
</BODY>
</HTML>