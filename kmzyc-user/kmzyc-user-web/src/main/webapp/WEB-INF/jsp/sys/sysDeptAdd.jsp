<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加部门</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<Script  src="/etc/js/sys/sysDeptAdd.js" type="text/javascript"></Script>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.min.js"></script>

</head>
<body onkeydown="changeKey();">
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form name="popForm" class="popForm"  action="saveSysDept.action" method="POST"  namespace='/sys' onsubmit="return Validator.Validate(this,3)">

<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<INPUT TYPE="hidden" name="upDeptid" id="upDeptid" value="<s:property value='model.upDeptid'/>">

<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="modeltitle">部门信息</th>
	</tr>
	<tr> 
		<th width="25%" align="right"><font color="red">*</font>部门名称：</th>
		<td width="75%"> 
			<input name="deptName" type="text" require="true" dataType="LimitB" max="40" min="1" msg="部门名称必输，且不超过20个汉字" value="<s:property value='model.deptName'/>"/>  (如：人事部)
		</td>
	</tr>

	<tr> 
		<th align="right"><font color="red">*</font>部门编号：</th>
		<td> 
			<input name="deptCd" type="text" require="true" dataType="LimitB" max="20" min="1" msg="部门编号必输，且不超过20个字符" value="<s:property value='model.deptCd'/>"/>
		</td>
	</tr>
	
	<tr> 
		<th align="right">备注：</th>
		<td> 
			<textarea name="deptRemark" cols="40" rows="4" wrap="PHYSICAL" require="false" dataType="LimitB" max="200" msg="备注不要超过100个汉字"><s:property value='model.deptRemark'/></textarea> 
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="button" onclick="saveDept()" class="btngreen" value="  保存  " />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="btngreen" value=" 关闭 " onclick="closeDiv();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

		</td>
	</tr>
</table>


</s:form>

</BODY>
</HTML>