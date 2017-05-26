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
<s:set name="name" value="'品牌管理'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/basedata/prodBrandShow.action" method="POST"  namespace='/basedata' id="frm" name='frm'>

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
<table  width="98%" class="content_table"  height="100" align="center" cellpadding="0" cellspacing="0" >
	<tr> 
		<!-- 根据查询字段的多少判断colspan-->
	    <td width="80%" valign="middle" colspan="4">
            <INPUT class="addBtn" TYPE="button" value="" onclick="gotoAdd();">
			<input class="delBtn" type="button" value=""  onclick="deleteSelected('delId');">
		</td>
	</tr>
	<tr>
		<td>名称：<input name="brandName" class="input_style" type="text" value="<s:property value='model.brandName'/>">
		</td>
		<td align="right">英文名称：
		    <input name="engName" class="input_style" type="text" value="<s:property value='model.engName'/>">
		</td>
		<td align="right">中文拼音：
		     <input name="chnSpell" class="input_style" type="text" value="<s:property value='model.chnSpell'/>">
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
		<th align="center" width="15%">名称</th>
		<th align="center" width="5%">国籍</th>
		<th align="center" width="29%">图片</th>
		<th align="center" width="10%">英文名称</th>
		<th align="center" width="10%">中文拼音</th>
		<th align="center" width="13%">主页</th>
		<th align="center" width="7%">是否有效</th>
		<th align="center" width="11%">操作</th>
	</tr>
	<s:iterator id="custiterator" value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox" name="delId"  value='<s:property value="brandId"/>'>
		</td>
		<td align="center"><s:property value="brandName"/></td>
		<td align="center"><s:property value="nation"/></td>
		<td align="center"><img width="142" height="50" alt="<s:property value="brandName"/>" title="<s:property value="brandName"/>" src="<s:property value="path" /><s:property value="logoPath"/>" /></td>
		<td align="center"> 
			<s:property value="engName"/>
		</td>
		<td align="center">
			<s:property value="chnSpell"/>
		</td>
		<td align="center">
			<s:property value="homepage"/>
		</td>
		<td align="center">
			<s:if test="isValid == 1">有效</s:if>
			<s:if test="isValid == 0">无效</s:if>
		</td>
		<td align="center">
			<img title="修改" style="cursor: pointer;" src="/etc/images/little_icon/xiugai.png"  onclick="gotoUpdate(<s:property value='brandId'/>)" />
			<img title="查看" style="cursor: pointer;" src="/etc/images/little_icon/search.png"  onclick="gotoView(<s:property value='brandId'/>)" />
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


//返回我的桌面界面
function gotoList(){
    location.href="/basedata/gotoSysMain.action";
}

function gotoAdd(){
    location.href="/basedata/toAddProdBrand.action";
}

function gotoUpdate(id){
    location.href="/basedata/gotoProdBrandUpdate.action?brandId="+id;
}

function gotoView(id){
	location.href="/basedata/gotoProdBrandView.action?brandId="+id;
}

function doDelete(name){
	document.forms['frm'].action="/basedata/deleteProdBrand.action";
	document.forms['frm'].submit();
}

function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}


</SCRIPT>

</BODY>
</HTML>