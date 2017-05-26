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
<s:set name="name" value="'品类审核'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<form action="/basedata/drugCategoryShow.action?type=audit" method="POST"  namespace='/basedata' id="frm" name='frm'>

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

<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />

<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert(document.getElementById("rtnMsg").value);
	//-->
	</SCRIPT>
</s:if>
<!-- 查询条件区域 -->
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">
			编号：
		    <input name="drugCate.code" class="input_style" type="text" value="<s:property value='drugCate.code'/>">
		</td>
		<td align="right">
			名称：
			<input name="drugCate.name" class="input_style" type="text" value="<s:property value='drugCate.name'/>">
		</td>
		<td align="right">
			状态：
			<s:select name="drugCate.status" headerKey="" headerValue="--全部--" list="#request.isValidMap"></s:select>
		</td>
		<td align="center">
			<INPUT TYPE="button" onClick="doSearch()" class="queryBtn" value="">
            <INPUT class="btn-custom" TYPE="button" value="通过" onClick="gotoAudit(1);">
			<input class="btn-custom" type="button" value="不通过"  onclick="gotoAudit(2);">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table id="mytable" width="98%" class="list_table"  cellpadding="3" align="center" cellspacing="0" border="1">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox' onclick='checkAll(this)'>
		</th>
		<th align="center" width="15%">编号</th>
		<th align="center" width="15%">名称</th>
		<th align="center" width="10%">状态</th>
		<th align="center" width="10%">审核状态</th>
		<th align="center" width="20%">备注</th>
		<th align="center" width="10%">操作</th>
	</tr>
	<s:iterator id="custiterator" value="page.dataList">
	<tr>
	    <td align="center" width="5%">
			<input type="checkbox" name="id"  value='<s:property value="id"/>'>
			<input type="hidden" name="name" value='<s:property value="name"/>' />
			<input type="hidden" name="code" value='<s:property value="code"/>' />
		</td>
		<td align="center"><s:property value="code"/></td>
		<td align="center"><s:property value="name"/></td>
		<td align="center"><s:property value="#request.isValidMap[status]" /></td>
		<td align="center"> 
			<s:property value="#request.auditStatusMap[auditStatus]" />
		</td>
		<td align="center"><s:property value="remark"/></td>
		<td align="center">
			<img title="查看" style="cursor: pointer;" src="/etc/images/view.png"  onclick="gotoView(<s:property value='id'/>)" />
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


</form>
<SCRIPT LANGUAGE="JavaScript">
<!--


function gotoAudit(arg){
	if($("input[type='checkbox'][name='id']:checked").length==0){
		alert("请选择要审核的品类！");
		return;
	}
	
	$("input[type='checkbox'][name='id']:checked").each(function(i){
		this.name = "drugCates["+i+"].id";
		$(this).siblings("input[type='hidden'][name='name']").attr("name","drugCates["+i+"].name");
		$(this).siblings("input[type='hidden'][name='code']").attr("name","drugCates["+i+"].code");
	});
	document.forms['frm'].action="/basedata/drugCategoryToAudit.action?statusArg="+arg;
	document.forms['frm'].submit();
}


function gotoView(id){
	location.href="/basedata/drugCategoryToUpdate.action?drugCateId="+id+"&type=viewAudit";
}

function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}


</SCRIPT>

</BODY>
</HTML>