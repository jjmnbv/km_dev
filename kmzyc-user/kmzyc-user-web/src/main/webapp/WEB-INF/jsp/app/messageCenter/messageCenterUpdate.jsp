<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改客户头衔信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
</head>

<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'客户头衔'" scope="request"/>
<s:set name="name" value="'客户头衔'" scope="request"/>
<s:set name="son_name" value="'添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<INPUT TYPE="hidden" name="isEnable" value="1">
<s:form action="rank_update.action" id="rankUpdate" method="POST"  namespace='/rank'  >

<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">



<!-- 数据编辑区域 -->
<table width="80%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<th colspan="2" align="left" class="edit_title">修改客户头衔信息</th>
		<input name="rank.rankId" type="hidden" require="true" 
		dataType="LimitB" max="15" value="<s:property value='rank.rankId'/>"/>
	</tr>
   <tr> 
		<th width="20%" align="right"><font color="red">*</font>头衔编号：</th>
		<td width="80%"> 
			<input name="rank.code" type="text" require="true" 
			dataType="LimitB" max="15" min="1" msg="头衔编号，不能为汉字" value="<s:property value='rank.code'/>"/>
		</td>
	</tr>
	
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>头衔名称：</th>
		<td width="80%"> 
			<input name="rank.rankName" type="text" require="true" 
			dataType="LimitB" max="16" min="1" msg="头衔名称不能为空，且不超过10个汉字" value="<s:property value='rank.rankName'/>"/>
		</td>
	</tr>
	
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>客户类型：</th>
		<td width="80%"> 
		<SELECT name="rank.customerTypeId">
		<option value="0" <s:if test="rank.customerTypeId==0">selected</s:if>>个人</option>
		<option value="1" <s:if test="rank.customerTypeId==1">selected</s:if> >专家</option>
		<option value="2" <s:if test="rank.customerTypeId==2">selected</s:if>>商户</option>
		</SELECT>
		</td>
	</tr>
	
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>积分/经验值最小值：</th>
		<td width="80%"> 
			<input name="rank.scoreMin" type="text" require="true" 
			dataType="LimitB" max="40" min="1" msg="积分/经验值最大值，不能为汉字" value="<s:property value='rank.scoreMin'/>"/>
		</td>
	</tr>
	
    <tr> 
		<th width="20%" align="right"><font color="red">*</font>积分/经验值最大值：</th>
		<td width="80%"> 
			<input name="rank.scoreMax" type="text" require="true"
			 dataType="LimitB" max="22" min="1" msg="积分/经验值最大值，不能为汉字" value="<s:property value='rank.scoreMin'/>"/>
		</td>
	</tr>
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="saveBtn" type="submit" value=" ">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value=" ">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form>
<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoList(){
    location.href="/rank/rank_pageList.action";
}

</SCRIPT>
</BODY>
</HTML>