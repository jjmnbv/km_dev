<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看品牌</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>

<style type="text/css">
	.eidt_rowTitle{padding:0px;}
</style>
</head>
<body>


<!-- 导航栏 -->
<s:set name="parent_name" value="'评价管理'" scope="request"/>
<s:set name="name" value="'评价属性'" scope="request"/>
<s:set name="son_name" value="'添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/prodAppraisePropUpdate.action" method="POST" namespace='/app' onsubmit="return Validator.Validate(this,2)"  id="frm" name='frm'>



<!-- 数据编辑区域 -->
<table width="45%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;">
	<tr> 
		<th colspan="4" align="left" class="edit_title">基本信息</th>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">属性名：</th>
		<td height="40"> 
			<input type="hidden" name="prop.appraisePropId" value="<s:property value='prop.appraisePropId'/>" />
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="text" class="input_style" dataType="Require" require="true" msg="属性名必填" name="prop.propName" value="<s:property value='prop.propName'/>" />
		</td>
	</tr>
	<tr>
		<th align="right" class="eidt_rowTitle">属性值：</th>
		<td> 
			<s:iterator value="prop.valList" status="st" >
				<p>
					<input name="prop.valList[<s:property value='#st.index'/>].appraisePropValId" type="hidden" value="<s:property value='appraisePropValId'/>" />
					<s:property value="point" />分：<input type="text" dataType="Require" require="true" msg="属性值必填" name="prop.valList[<s:property value='#st.index'/>].propVal" class="input_style" value="<s:property value='propVal'/>" />
				</p>
			</s:iterator>
		</td>
	</tr>
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

</SCRIPT>


</BODY>
</HTML>


