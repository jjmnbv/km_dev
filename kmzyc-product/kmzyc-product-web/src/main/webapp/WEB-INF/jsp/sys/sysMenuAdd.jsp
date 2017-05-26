<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加菜单</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form name="popForm" action="saveSysMenu.action" method="POST"  namespace='/sys' onsubmit="javasript:Validator.Validate(this,3);parent.closePopDiv2();">

<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<INPUT TYPE="hidden" name="menuUpid" value="<s:property value='model.menuUpid'/>">
<INPUT TYPE="hidden" name="menuLv" value="<s:property value='model.menuLv'/>">
<INPUT TYPE="hidden" name="createUser" value="<s:property value="#session['sysUser'].userId"/>">

	<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
		<tr> 
			<th colspan="2" align="left" class="modeltitle">新增菜单</th>
		</tr>
		<tr> 
			<th width="30%" align="right"><font color="red">*</font>菜单名称：</th>
			<td width="70%" align="left"> 
				<input name="menuName" size="40" type="text" require="true" dataType="LimitB" max="40" min="1" msg="菜单类名称必输，且不超过20个汉字" value="<s:property value='model.menuName'/>"/>  
			</td>
		</tr>
		<tr> 
			<th align="right">菜单图片：</th>
			<td align="left"> 
				<input name="menuImg" size="30"  msg="非法的文件格式" type="file" accept="jpg,gif,png" value="<s:property value='model.menuImg'/>"/>
			</td>
		</tr>
		<tr> 
			<th align="right">链接地址：</th>
			<td align="left"> 
				<input type="text" name="menuLink" size="40"  value="<s:property value='model.menuLink'/>"/>  
			</td>
		</tr>
		<tr> 
			<th align="right">菜单序号：</th>
			<td align="left"> 
				<input type="text" name="sortno" size="5"  value="<s:property value='model.sortno'/>" require="false" dataType="Integer" msg="序号请输入整数" />  
			</td>
		</tr>
		<tr>
			<th align="right">菜单备注</th>
			<td align="left"> 
				<textarea rows="4" cols="40" name="menuRemark" ><s:property value='model.menuRemark'/></textarea>
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
function saveClass(){
	if(Validator.Validate(window.document.popForm,3)){
		window.document.popForm.submit();
		parent.closePopDiv2();
	}
}

function closeDiv(){
    parent.closePopDiv();
}
</SCRIPT>
</BODY>
</HTML>