<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加部门</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
</head>
<body onkeydown="changeKey();">
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form name="popForm"  action="saveSysDept.action" method="POST"  namespace='/sys' onsubmit="javasript:Validator.Validate(this,3);parent.closePopDiv2();">

<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<INPUT TYPE="hidden" name="upDeptid" id="upDeptid" value="<s:property value='model.upDeptid'/>">

<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="modeltitle">部门信息</th>
	</tr>
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
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
		<th align="right">备注aa：</th>
		<td> 
			<textarea name="deptRemark" cols="40" rows="4" wrap="PHYSICAL" require="false" dataType="LimitB" max="200" msg="备注不要超过100个汉字"><s:property value='model.deptRemark'/></textarea> 
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="submit" class="btngreen" style="height:30px" value="  保存  " />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="btngreen" style="height:30px" value=" 关闭 " onClick="closeDiv();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

		</td>
	</tr>
</table>


</s:form>



<SCRIPT LANGUAGE="JavaScript">
<!--
function saveDept(){
	if(Validator.Validate(window.document.popForm,3)){
		window.document.popForm.submit();
		parent.closePopDiv2();
	}
}

function closeDiv(){
    parent.closePopDiv();
}

//光标移动
function changeKey()
{
	var tr=event.srcElement.getAttribute("type");
	if("textarea"!=tr && "button" != tr)
	{
			if(13 == event.keyCode)
			{
				event.keyCode=9;
			}
    }
}

//-->
</SCRIPT>
</BODY>
</HTML>