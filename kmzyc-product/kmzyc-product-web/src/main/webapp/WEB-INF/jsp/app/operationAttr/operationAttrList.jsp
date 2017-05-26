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


<s:set name="parent_name" value="'基础数据'" scope="request" />
<s:set name="name" value="'运营属性管理'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/basedata/busiAttributeList.action" method="POST"  namespace='/basedata' id="frm" name='frm'>

<!-- 标题条(备) -->
<!--div id="mainbar" align="center"> 
	<ul class="mainbarlayout">
		<li> 
			<div class="orangebar_on"><div class="orangebarwords_on">操作员管理</div></div>
		<li>
			<div class="orangebar_off"> 
			    <div class="orangebarwords_off" onClick="location.href='#s02'" onMouseOver="this.style.cursor='hand'">更多</div>
			</div>
	</ul>
</div-->

<input type="hidden" id="rtnMsg" value="<s:property value="rtnMsg"/>" />

<s:if test='!rtnMsg.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert(document.getElementById("rtnMsg").value);
	//-->
	</SCRIPT>
</s:if>
<!-- 查询条件区域 -->
<table  width="98%" class="content_table"  height="100" align="center" cellpadding="0" cellspacing="0" >
	<tr> 
		<!-- 根据查询字段的多少判断colspan-->
	    <td width="80%" valign="middle" colspan="4">
            <INPUT class="addBtn" TYPE="button" value="" onclick="gotoAdd();">
			<input class="delBtn" type="button" value=""  onclick="deleteSelected('delId');">
		</td>
	</tr>
	<tr>
		<td>名称：<input name="attr.operationAttrName" class="input_style" type="text" value="<s:property value='attr.operationAttrName'/>">
		</td>
		<td>
			状态：
			<s:select name="attr.status" headerKey="" headerValue="--全部--" list="#request.isValidMap"></s:select>
		</td>
		<td>
			是否导航属性：
			<s:select name="attr.isNav" headerKey="-1" headerValue="--全部--" list="#{1:'是',0:'否'}"></s:select>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<INPUT TYPE="button" onclick="doSearch()" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table id="mytable" width="98%" class="list_table"  cellpadding="3" align="center" cellspacing="0" border="1">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox' onclick='checkAll(this)'>
		</th>
		<th align="center" width="20%" >名称</th>
		<th align="center" width="15%" >有效状态</th>
		<th align="center" width="15%" >是否导航属性</th>
		<th align="center" width="35%" >备注</th>
		<th align="center" width="15%" >操作</th>
	</tr>
	<s:iterator id="custiterator" value="page.dataList">
	<tr>
	    <td align="center" width="5%">
	    	<s:if test='operationAttrId!=1'>
		   		<input type="checkbox" name="delId"  value='<s:property value="operationAttrId"/>'>
		   	</s:if>
		</td>
		<td align="center"><s:property value="operationAttrName"/></td>
		<td align="center"><s:property value="#request.isValidMap[status]" /></td>
		<td align="center">
			<s:if test="isNav == 1">是</s:if>
			<s:if test="isNav == 0">否</s:if>
		</td>
		<td align="center"><s:property value="remark"/></td>
		<td align="center">
			<s:if test='operationAttrId!=1'>
				<img title="修改" style="cursor: pointer;" src="/etc/images/button_new/modify.png"  onclick="gotoUpdate(<s:property value='operationAttrId'/>)" />
			</s:if>
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
<!--

function gotoAdd(){
	location.href="/basedata/gotoOprationAttrAdd.action";
}

function gotoUpdate(id){
    location.href="/basedata/gotoOprationAttrUpdate.action?operationAttrId="+id;
}


function doDelete(name){
	document.forms['frm'].action="/basedata/deleteOprationAttr.action";
	document.forms['frm'].submit();
}

function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}


</SCRIPT>

</BODY>
</HTML>