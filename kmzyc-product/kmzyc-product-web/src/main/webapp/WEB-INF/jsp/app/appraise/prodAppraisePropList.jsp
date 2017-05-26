<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
    <script src="/etc/js/dialog.js"></script>
    <script src="/etc/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <style type="text/css">
        .tableStyle1{font-size:12px;}
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:set name="parent_name" value="'评价管理'" scope="request" />
<s:set name="name" value="'属性管理'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/app/prodAppraisePropShow.action" method="POST"  namespace='/app' id="frm" name='frm'>
<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />
<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert(document.getElementById("rtnMsg").value);
	//-->
	</SCRIPT>
</s:if>
<!-- 查询条件区域 -->
<table width="98%" class="content_table"   align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">属性名称：</td>
		<td align="left">
			<input name="prop.propName" class="input_style" type="text" value="<s:property value='prop.propName'/>">
		</td>
		<td>
			<input class="queryBtn" type="button" onClick="doSearch()"/>
            <input class="addBtn" TYPE="button" onClick="gotoAdd()"/>
			<input class="delBtn" type="button" onclick="deleteSelected('delId')"/>
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table id="mytable" width="98%" class="list_table" cellpadding="3" align="center" cellspacing="0" border="1">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox' onclick='checkAll(this)'>
		</th>
		<th align="center" width="15%">属性名</th>
		<th align="center" width="65%">属性值</th>
		<th align="center" width="15%">操作</th>
	</tr>
	<s:iterator id="custiterator" value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox" name="delId"  value='<s:property value="appraisePropId"/>'>
		</td>
		<td align="center"><s:property value="propName"/></td>
		<td align="center">
			<s:iterator value="valList" >
				<s:property value="point" />：<s:property value="propVal" />&nbsp;
			</s:iterator>
		</td>
		<td align="center">
			<img title="修改" style="cursor: pointer;" src="/etc/images/button_new/modify.png"  onclick="gotoView(<s:property value='appraisePropId'/>)" />
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
<script LANGUAGE="JavaScript">

function gotoAdd(){
    location.href="/app/gotoProdAppraisePropAdd.action";
}

function gotoView(id){
    location.href="/app/gotoProdAppraisePropUpdate.action?propId="+id;
}

function doDelete(name){
	if(confirm("删除属性，会直接取消绑定,确定吗？")){
		document.forms['frm'].action="/app/deleteProdAppraiseProp.action";
		document.forms['frm'].submit();
	}
}

function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}
</script>
</body>
</html>