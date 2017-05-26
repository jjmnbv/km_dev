<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商管理</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
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

<s:if test='"updateOK".equals(rtnMessage)'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert("修改供应商信息成功!");
	//-->
	</SCRIPT>
</s:if>
<s:if test='"saveOk".equals(rtnMessage)'>
    <SCRIPT LANGUAGE="JavaScript">
	<!--
		alert("新增供应商成功!");
	//-->
	</SCRIPT>
</s:if>
<s:if test='"deleteOK".equals(rtnMessage)'>
    <SCRIPT LANGUAGE="JavaScript">
	<!--
		alert("删除供应商成功!");
	//-->
	</SCRIPT>
</s:if>

<!-- 标题条 -->
<div class="pagetitle">供应商管理:</div>


<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="button" value="+ 新增 " onclick="gotoAdd();">
			<input class="btngreen" type="button" value="- 删除 "  onclick="deleteSelected('delId');">
		</td>
	    <td width="10%" align="center"><!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a--></td>
	</tr>
</table>


<!-- 查询条件区域 -->
<table  width="98%" class="searcharea" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td>供应商名称：</td>
		<td>
			<input name="supplierName" type="text" value="<s:property value='model."supplierName"'/>">
		</td>
		<td align="right">供应商名称：</td>
        <td>
		    <input name="engName" type="text" value="<s:property value='model.address'/>">
		</td>
		<td align="right">中文拼音：</td>
		<td>
		     <input name="chnSpell" type="text" value="<s:property value='model.chnSpell'/>">
		</td>
		<td>
			<INPUT TYPE="submit" class="button-blue-1" value=" 查询 ">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="tableStyle1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox' onclick='checkAll(this)'>
		</th>
		<th align="center" width="15%">名称</th>
		<th align="center" width="15%">国籍</th>
		<th align="center" width="10%">图片</th>
		<th align="center" width="10%">英文名称</th>
		<th align="center" width="20%">中文拼音</th>
		<th align="center" width="15%">主页</th>
		<th align="center" width="10%">操作</th>
	</tr>
	<s:iterator id="custiterator" value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox" name="nbrandId"  value='<s:property value="nbrandId"/>'>
		</td>
		<td align="center"><s:property value="brandName"/></td>
		<td align="center"><s:property value="nation"/></td>
		<td align="center"><s:property value="logoPath"/></td>
		<td align="center">
			<s:property value="engName"/>
		</td>
		<td align="center">
			<s:property value="chnSpell"/>
		</td>
		<td align="center">
			<s:property value="homePage"/>
		</td>
		<td align="center"><INPUT TYPE="button" class="btngray" value=" 修改 " onclick="gotoUpdate(<s:property value='nbrandId'/>)"></td>
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

//返回我的桌面界面
function gotoList(){
    location.href="/sys/gotoSysMain.action";
}

function gotoAdd(){
    location.href="/sys/gotoSysUserAdd.action";
}

function gotoUpdate(userId){
    location.href="/sys/gotoSysUserUpdate.action?userId="+userId;
}
function doDelete(name){
	document.forms['frm'].action="/sys/deleteSysuser.action";
	document.forms['frm'].submit();
}
</SCRIPT>

</BODY>
</HTML>