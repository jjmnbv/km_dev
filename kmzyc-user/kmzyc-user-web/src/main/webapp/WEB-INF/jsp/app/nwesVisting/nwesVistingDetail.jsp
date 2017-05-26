<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>拜访记录</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" >
</script>
</head>
<body >
<!-- 导航栏 -->
<s:set name="parent_name" value="'客户信息'" scope="request"/>
<s:set name="name" value="'拜访记录'" scope="request"/>
<s:set name="son_name" value="'详情'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:scroll; " >
<s:form id="nwesVistingAdd" action="visting_add.action" method="post"  namespace="/nwesVisting" >
<s:token/>
<!-- 数据编辑区域 -->
<table width="60%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<td colspan="2" align="left" class="edit_title">拜访记录</td>
		  <input type="hidden" name="nwesVisting.vistingId"  id="vistingId" value="<s:property value='nwesVisting.vistingId'/>"/>
          <input type="hidden"  id="customerTypeId" name="nwesVisting.customerTypeId"   value="<s:property value='nwesVisting.customerTypeId'/>"/>	
	</tr>	
	<tr> 
		<td width="15%" align="right"><font color="red">*</font>客户姓名：</td>
		<td width="80%"> 
		 <s:property value="nwesVisting.loginName"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>拜访内容：</td>
		<td width="80%"> 
		<s:property value="nwesVisting.content"/>
	    </td>
	</tr>
    <tr> 
		<td width="20%" align="right"><font color="red">*</font>客户建议：</td>
		<td width="80%"> 
		<s:property value="nwesVisting.advice"/>
		</td>
	</tr>
	  <tr> 
        <td width="20%"   align="right"><font color="red">*</font>拜访日期：</td>
		<td width="80%"> 
			<s:date   name="nwesVisting.vistingDate"  format="yyyy-MM-dd"/>
		</td>
	</tr>
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
		
			<input class="backBtn"  onclick="gotoList()" type="button" value=" ">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>
<br><br>
	
</s:form>
</div>
<SCRIPT LANGUAGE="JavaScript">
function gotoList(){
    location.href="/nwesVisting/visting_pageList.action";
}
</SCRIPT>
</BODY><!-- 消息提示 -->
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>