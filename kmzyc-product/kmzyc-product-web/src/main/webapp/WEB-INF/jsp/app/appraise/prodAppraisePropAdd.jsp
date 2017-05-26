<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看品牌</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>

<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>

<style type="text/css">
	.eidt_rowTitle{padding:0px;}
</style>
</head>
<body>


<!-- 导航栏 -->
<s:set name="parent_name" value="'产品咨询评价管理'" scope="request"/>
<s:set name="name" value="'评价属性'" scope="request"/>
<s:set name="son_name" value="'添加评价属性'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/prodAppraisePropAdd.action" method="POST" namespace='/app' id="frm" name='frm'>
<s:token></s:token>
<!-- 数据编辑区域 -->
<table width="45%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;">
	<tr> 
		<th colspan="4" align="left" class="edit_title">基本信息</th>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">属性名：</th>
		<td height="40"> 
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="text" class="input_style {required:true}" name="prop.propName" value="" maxlength="10" />
		</td>
	</tr>
	<tr>
		<th align="right" class="eidt_rowTitle" rowspan="5">属性值：</th>
		<td> 
			<p>
				<input name="prop.valList[<s:property value='0'/>].point" type="hidden" value="<s:property value='1'/>"/>
				<s:property value="1" />分：<input type="text" name="prop.valList[<s:property value='0'/>].propVal" class="input_style {required:true}" value=""  maxlength="16" />
			</p>
		</td>
	</tr>
	<s:iterator status="st" begin="1" end="4" >
		<tr>
			<td> 
				<p>
					<input name="prop.valList[<s:property value='#st.count'/>].point" type="hidden" value="<s:property value='#st.index + 2'/>"  />
					<s:property value="#st.index + 2" />分：<input type="text" name="prop.valList[<s:property value='#st.index + 1'/>].propVal" class="input_style {required:true}" value="" maxlength="16"  />
				</p>
			</td>
		</tr>
	</s:iterator>
</table>

<!-- 底部 按钮条 -->
<table width="45%" align="center" class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0" style="font-size:12px;">
	<tr> 
		<td align="center">
			<INPUT class="saveBtn" TYPE="submit" value="">
			<input type="button" class="backBtn" onclick="gotoList()" />
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

</s:form>
<SCRIPT LANGUAGE="JavaScript">

function gotoList(){
    location.href="/app/prodAppraisePropShow.action";
}

$(function(){
	$("#frm").validate({});
});

</SCRIPT>


</BODY>
</HTML>


